package ca.uqam.latece.evo.server.core.util;

import ca.uqam.latece.evo.server.core.exceptions.StorageException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class LocalStorageTest {
    @TempDir
    private Path tempRoot;

    @Test
    void store() throws Exception {
        LocalStorage localStorage = new LocalStorage(tempRoot.toString(), "0");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Evo+".getBytes()
        );
        String filename = localStorage.store(file);

        Path storedFile = localStorage.getRoot().resolve(filename);

        assertThat(Files.exists(storedFile)).isTrue();
        assertThat(Files.readString(storedFile)).isEqualTo("Evo+");
    }

    @Test
    void rejectsPathTraversal() {
        LocalStorage localStorage = new LocalStorage(tempRoot.toString(), "0");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "../illegal*.txt",
                "text/plain",
                "data".getBytes()
        );

        assertThatThrownBy(() -> localStorage.store(file))
                .isInstanceOf(StorageException.class);
    }

    @Test
    void rejectsFilenameNoExtension() {
        LocalStorage localStorage = new LocalStorage(tempRoot.toString(), "0");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "../illegalt../xt",
                "text/plain",
                "data".getBytes()
        );

        assertThatThrownBy(() -> localStorage.store(file))
                .isInstanceOf(StorageException.class);
    }

    @Test
    void rejectsEmptyFile() {
        LocalStorage localStorage = new LocalStorage(tempRoot.toString(), "0");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "empty.txt",
                "text/plain",
                new byte[0]
        );

        assertThatThrownBy(() -> localStorage.store(file))
                .isInstanceOf(StorageException.class);
    }

    @Test
    void rejectsNullFilename() {
        LocalStorage localStorage = new LocalStorage(tempRoot.toString(), "0");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                null,
                "text/plain",
                "data".getBytes()
        );

        assertThatThrownBy(() -> localStorage.store(file))
                .isInstanceOf(StorageException.class);
    }

    @Test
    void delete() {
        LocalStorage localStorage = new LocalStorage(tempRoot.toString(), "0");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Evo+".getBytes()
        );
        String filename = localStorage.store(file);
        localStorage.delete(filename);

        Path storedFile = localStorage.getRoot().resolve(filename);

        assertThat(Files.exists(storedFile)).isFalse();
    }

    @Test
    void deleteAll() {
        LocalStorage localStorage = new LocalStorage(tempRoot.toString(), "0");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Evo+".getBytes()
        );
        String filename = localStorage.store(file);

        MockMultipartFile file2 = new MockMultipartFile(
                "file2",
                "test2.txt",
                "text/plain",
                "Evo++".getBytes()
        );
        String filename2 = localStorage.store(file2);
        localStorage.deleteAll();

        Path storedFile = localStorage.getRoot().resolve(filename);
        Path storedFile2 = localStorage.getRoot().resolve(filename2);

        assertThat(Files.exists(storedFile)).isFalse();
        assertThat(Files.exists(storedFile2)).isFalse();
    }

    @Test
    void load() {
        LocalStorage localStorage = new LocalStorage(tempRoot.toString(), "0");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Evo+".getBytes()
        );
        String filename = localStorage.store(file);
        Path path = localStorage.load(filename);

        assertThat(path).isNotNull();
        assertThat(path.getFileName().toString()).isEqualTo("test.txt");
        assertThat(path.toString()).contains(localStorage.getRoot().toString());
    }

    @Test
    void loadAsResource() {
        LocalStorage localStorage = new LocalStorage(tempRoot.toString(), "0");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Evo+".getBytes()
        );
        String filename = localStorage.store(file);
        Resource resource = localStorage.loadAsResource(filename);

        assertThat(resource).isNotNull();
        assertThat(resource.exists()).isTrue();
        assertThat(resource.isReadable()).isTrue();
        assertThat(resource.getFilename()).isEqualTo("test.txt");
    }

    @Test
    void sanitizeFilename() {
        String filename = "../../../";
        LocalStorage localStorage = new LocalStorage(tempRoot.toString(), "0");

        assertThatThrownBy(() -> localStorage.sanitizeFilename(filename))
                .isInstanceOf(StorageException.class);
    }
}
