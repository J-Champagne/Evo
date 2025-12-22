package ca.uqam.latece.evo.server.core.util;

import ca.uqam.latece.evo.server.core.exceptions.StorageException;
import ca.uqam.latece.evo.server.core.exceptions.StorageFileNotFoundException;
import ca.uqam.latece.evo.server.core.interfaces.StorageService;
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

public class LocalStorage implements StorageService {
    private final static String BASE_FOLDER = "./app/files";

    private final static String[] ILLEGAL_CHARS = new String[]{"/", "\\", "."};

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

        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new StorageException("Could not create local storage directory " + root, e);
        }
    }

    public String getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    @Override
    public String store(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
            throw new StorageException("File is empty or does not have a name");
        }

        String filename = Path.of(file.getOriginalFilename()).getFileName().toString();
        String sanitizedFilename = sanitizeFilename(filename);

        return this.store(file, sanitizedFilename);
    }

    @Override
    public void delete(String filename) {
        try {
            Path file = root.resolve(filename).normalize().toAbsolutePath();
            Files.deleteIfExists(file);
            //TODO
        } catch (IOException e) {
            throw new StorageException("Failed to delete file", e);
        }
    }

    @Override
    public void deleteAll() {
        Path folder = root.toAbsolutePath().normalize();
        try (Stream<Path> files = Files.list(folder)) {
            files.forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                } catch (IOException e) {
                    throw new StorageException("Failed to delete file " + path.getFileName(), e);
                }
            });
        } catch (IOException e) {
            throw new StorageException("Failed to open folder when deleting files", e);
        }
    }

    @Override
    public Path load(String fileName) {
        return root.resolve(fileName);
    }

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

    private String extractFilename(String filename) {
        String filenameWithoutExtension = "";

        if (filename != null && !filename.isEmpty()) {
            filenameWithoutExtension = filename.substring(0, filename.lastIndexOf('.'));
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