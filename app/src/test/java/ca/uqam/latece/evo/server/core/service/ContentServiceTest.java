package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.Content;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ContentService} class. This test class is responsible for verifying
 * the correctness and functionality of methods in the ContentService, covering core operations
 * such as saving, updating, deleting, and retrieving Content entities.
 *
 * The tests in this class are executed in the context of the Spring Framework, with dependency
 * injection and configuration provided by the {@link ContextConfiguration} annotation.
 * The required service and entity beans are loaded and injected to facilitate comprehensive testing.
 *
 * This class extends {@link AbstractServiceTest}, inheriting abstract methods that are implemented
 * to test specific service-level functionalities. It ensures:
 * - Entities are created, updated, and deleted as expected.
 * - Retrieval operations yield correct and consistent results.
 * - All interactions comply with validation and business logic constraints.
 *
 * Dependencies:
 * - {@link ContentService}: The service class being tested.
 * - {@link Content}: The entity being manipulated and verified during tests.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {ContentService.class, Content.class})
public class ContentServiceTest extends AbstractServiceTest {
    @Autowired
    private ContentService contentService;


    @Test
    @Override
    void testSave() {
        Content content = new Content();
        content = new Content();
        content.setName("Content Name");
        content.setDescription("Content Description");
        content.setType("Content Video");
        // Checks if the content was created.
        assert contentService.create(content).getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        Content content = new Content();
        content.setName("Content Name 1");
        content.setDescription("Content Description 1");
        content.setType("Content Video 1");
        // Create the Content.
        Content contentCreated = contentService.create(content);

        Content contentUpdate = new Content();
        contentUpdate.setId(contentCreated.getId());
        contentUpdate.setName("Content Name Update");
        contentUpdate.setDescription("Content Description Update");
        contentUpdate.setType("Content Video Update");

        // Update the content.
        Content contentUpdated = contentService.update(contentUpdate);
        assertEquals(contentCreated.getId(), contentUpdated.getId());
        assertEquals("Content Name Update", contentUpdated.getName());
        assertEquals("Content Description Update", contentUpdated.getDescription());
        assertEquals("Content Video Update", contentUpdated.getType());
    }

    @Test
    @Override
    void testFindByName() {
        Content content = new Content();
        content.setName("Content Work");
        content.setDescription("Content Description Work");
        content.setType("Content Video Work");
        // Create the Content.
        Content contentCreated = contentService.create(content);
        //
        assertEquals(contentCreated.getName(), contentService.findByName(contentCreated.getName()).get(0).getName());
    }

    @Test
    void testFindByType(){
        Content content = new Content();
        content.setName("Content 1");
        content.setDescription("Content Description 1");
        content.setType("MyType");
        // Create the Content.
        Content contentCreated = contentService.create(content);

        Content content2 = new Content();
        content2.setName("Content 2");
        content2.setDescription("Content Description 2");
        content2.setType("MyType 2");

        Content contentCreated2 = contentService.create(content2);

        assertEquals(1, contentService.findByType(contentCreated.getType()).size());
    }

    @Test
    @Override
    void testDeleteById() {
        Content content = new Content();
        content.setName("Work 1");
        content.setDescription("Content Work 1");
        content.setType("Video Work 1");

        // Create the Content.
        Content contentCreated = contentService.create(content);
        Long id = contentCreated.getId();
        // Delete
        contentService.deleteById(id);
        assertFalse(contentService.existsById(id));
    }

    @Test
    @Override
    void testFindAll() {
        Content content = new Content();
        content.setName("Work");
        content.setDescription("Content Work");
        content.setType("Video Work");

        // Create the Content.
        Content contentCreated = contentService.create(content);

        Content content1 = new Content();
        content1.setName("Work Java");
        content1.setDescription("Content Work Java");
        content1.setType("Video Work javac");

        // Create the Content.
        Content contentCreated1 = contentService.create(content1);

        // Assert that the result should be two Content.
        assertEquals(2,contentService.findAll().size());
    }
}
