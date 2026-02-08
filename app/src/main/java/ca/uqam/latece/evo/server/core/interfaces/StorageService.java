package ca.uqam.latece.evo.server.core.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    String store(MultipartFile file);

    void delete(String filename);

    void deleteAll();

    Path load(String fileName);

    Resource loadAsResource(String filename);
}
