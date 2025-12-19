package ca.uqam.latece.evo.server.core.util;

import ca.uqam.latece.evo.server.core.exceptions.StorageException;
import ca.uqam.latece.evo.server.core.exceptions.StorageFileNotFoundException;
import ca.uqam.latece.evo.server.core.interfaces.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class LocalStorage implements StorageService {
    private final Path root = Paths.get("./files");

    private String location = "activities";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void store(MultipartFile file) {
        ObjectValidator.validateFilepath(file.getOriginalFilename());

        try {
            if (file.isEmpty() || file.getOriginalFilename() == null) {
                throw new StorageException("File is empty or undefined");
            }

            Path filename = root.resolve(Paths.get(file.getOriginalFilename()))
                    .normalize()
                    .toAbsolutePath();

            if (filename.getParent().equals(root.toAbsolutePath())) {
                throw new StorageException("File needs to be in current directory");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filename, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            throw new StorageException("Failed to store file", e);
        }

    }

    @Override
    public Stream<Path> loadAll() {
        return Stream.empty();
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
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);
            }
        }

        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }
}