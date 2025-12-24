package ca.uqam.latece.evo.server.core.util;

import ca.uqam.latece.evo.server.core.exceptions.StorageException;
import ca.uqam.latece.evo.server.core.exceptions.StorageFileNotFoundException;
import ca.uqam.latece.evo.server.core.interfaces.StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

/**
 * Class responsible for local storage of files.
 * Files will be stored at the folder specified by root, which is at `./app/files` by default.
 * Files associated to an entity should be stored at a location that represents that relationship.
 * For example, a Content entity with ID=1 should store files at `./app/files/content/1`.
 */
public class LocalStorage implements StorageService {
    private static final Logger logger = LogManager.getLogger(LocalStorage.class);

    private static final String BASE_FOLDER = "files";

    private static final String[] ILLEGAL_CHARS = new String[]{"/", "\\", ".", "*"};

    private final Path root;

    private final String location;

    private final String id;

    public LocalStorage() {
        root = Path.of(BASE_FOLDER);
        location = "";
        id = "";
    }

    public LocalStorage(String location, String id) {
        this.location = location;
        this.id = id;
        root = Path.of(BASE_FOLDER, location, id);
    }

    public static String getBaseFolder() {
        return BASE_FOLDER;
    }

    public Path getRoot() {
        return root;
    }

    public String getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    /**
     * Stores a file at folder specified by root (BASE_FOLDER/location/id) after verifying that its name does not
     * contain any illegal characters.
     * @param file the file to be stored
     * @return String representing the path of the file
     * @throws StorageException if the file could not be stored or if its name contains illegal characters
     */
    @Override
    public String store(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
            throw new StorageException("File is empty or does not have a name");
        }

        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new StorageException("Could not create local storage directory " + root, e);
        }

        String filename = Path.of(file.getOriginalFilename()).getFileName().toString();
        String sanitizedFilename = sanitizeFilename(filename);
        String storedFile = this.store(file, sanitizedFilename);

        String storedMsg = "File "+ sanitizedFilename + " stored at " + root;
        logger.info(storedMsg);
        return storedFile;
    }

    /**
     * Deletes a file
     * @param filename the name of the file to be deleted
     */
    @Override
    public void delete(String filename) {
        try {
            Path file = root.resolve(filename).normalize().toAbsolutePath();
            Files.deleteIfExists(file);

            String deleteMsg = "File "+ filename + " deleted at " + root;
            logger.info(deleteMsg);

        } catch (IOException e) {
            throw new StorageException("Failed to delete file", e);
        }
    }

    /**
     * Deletes all files at folder specified by root (BASE_FOLDER/location/id)
     */
    @Override
    public void deleteAll() throws StorageException {
        Path folder = root.toAbsolutePath().normalize();
        try (Stream<Path> files = Files.list(folder)) {
            files.forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    throw new StorageException("Failed to delete file " + path.getFileName(), e);
                }
            });
            String deleteAllMsg = "Files deleted at " + root;
            logger.info(deleteAllMsg);

        } catch (IOException e) {
            String noDirectoryMsg = "Directory does not exist at " + root;
            logger.info(noDirectoryMsg);
        }
    }

    /**
     * Returns a Path pointing to a file specified by filename.
     * The file must be at the folder specified by root (BASE_FOLDER/location/id).
     * @param fileName the name of the file
     * @return a Path pointing to the file
     */
    @Override
    public Path load(String fileName) {
        return root.resolve(fileName);
    }

    /**
     * Loads a file as a Resource usable by the Spring framework
     * @param filename the name of the file
     * @return the file in the form of a Resource
     */
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        }

        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    /**
     * Breaks down a filename into its name and its extension in order to verify the presence of illegal characters
     * that could present a security risk.
     * @param filename the filename to be analyzed
     * @return a filename containing to illegal characters
     * @throws StorageException if the filename contained illegal characters
     */
    public String sanitizeFilename(String filename) {
        String sanitizedFilename;
        String name = extractFilename(filename);
        String extension = extractExtension(filename);

        if (!containsIllegalCharacters(name) && !containsIllegalCharacters(extension)) {
            sanitizedFilename = name + "." + extension;
        } else {
            throw new StorageException("The file's name contains illegal characters");
        }

        return sanitizedFilename;
    }

    private String store(MultipartFile file, String filename) {
        if (file.isEmpty() || filename.isEmpty()) {throw new StorageException("File is empty or has no name");}

        Path path = root.resolve(Path.of(filename)).normalize().toAbsolutePath();

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }

        return filename;
    }

    private String extractFilename(String filename) {
        String filenameWithoutExtension = "";

        if (filename != null && !filename.isEmpty()) {
            int index = filename.lastIndexOf('.');
            if (index != -1) {
                filenameWithoutExtension = filename.substring(0, filename.lastIndexOf('.'));
            } else {
                throw new StorageException("Filename does not contain an extension");
            }
        }

        return filenameWithoutExtension;
    }

    private String extractExtension(String filename) {
        String extension = "";

        if (filename != null && !filename.isEmpty()) {
            int lastDotIndex = filename.lastIndexOf('.');

            if (lastDotIndex > 0 && lastDotIndex < filename.length() - 1) {
                extension = filename.substring(lastDotIndex + 1);
            }
        }

        return extension;
    }

    private boolean containsIllegalCharacters(String filename) {
        for (String illegalChar : ILLEGAL_CHARS) {
            if (filename.contains(illegalChar)) {
                return true;
            }
        }
        return false;
    }
}