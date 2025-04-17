package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.Content;
import ca.uqam.latece.evo.server.core.model.Skill;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link ContentService} class. This test class is responsible for verifying
 * the correctness and functionality of methods in the ContentService, covering core operations
 * such as saving, updating, deleting, and retrieving Content entities.
 * <p>
 * The tests in this class are executed in the context of the Spring Framework, with dependency
 * injection and configuration provided by the {@link ContextConfiguration} annotation.
 * The required service and entity beans are loaded and injected to facilitate comprehensive testing.
 * <p>
 * This class extends {@link AbstractServiceTest}, inheriting abstract methods that are implemented
 * to test specific service-level functionalities. It ensures:
 * - Entities are created, updated, and deleted as expected.
 * - Retrieval operations yield correct and consistent results.
 * - All interactions comply with validation and business logic constraints.
 * <p>
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

    @Autowired
    private BCIActivityService bciActivityService;

    @Autowired
    private SkillService skillService;

    private BCIActivity bciActivity = new BCIActivity();


    @BeforeEach
    void beforeEach(){
        // Create a BCI Activity.
        bciActivity.setName("Programming 2");
        bciActivity.setDescription("Programming language training 2");
        bciActivity.setType(ActivityType.LEARNING);
        // Create a BCI Activity.
        bciActivityService.create(bciActivity);
    }

    @AfterEach
    void afterEach(){
        // Delete a BCI Activity.
        bciActivityService.deleteById(bciActivity.getId());
    }

    @Test
    @Override
    void testSave() {
        Content content = new Content();
        content = new Content();
        content.setName("Content Name");
        content.setDescription("Content Description");
        content.setType("Content Video");
        content.addBCIActivity(bciActivity);
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
        content.addBCIActivity(bciActivity);
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
    public void testFindById() {
        Content content = new Content();
        content.setName("Content Work");
        content.setDescription("Content Description Work");
        content.setType("Content Video Work");
        content.addBCIActivity(bciActivity);
        // Create the Content.
        Content contentSaved = contentService.create(content);
        Content contentfound = contentService.findById(contentSaved.getId());
        assertEquals(content.getId(), contentfound.getId());
    }

    @Test
    void testFindByName() {
        Content content = new Content();
        content.setName("Content Work");
        content.setDescription("Content Description Work");
        content.setType("Content Video Work");
        content.addBCIActivity(bciActivity);
        // Create the Content.
        Content contentCreated = contentService.create(content);
        //
        assertEquals(contentCreated.getName(), contentService.findByName(contentCreated.getName()).get(0).getName());
    }

    @Test
    void testFindByType(){
        // Create the Content.
        Content content = new Content();
        content.setName("Content 1");
        content.setDescription("Content Description 1");
        content.setType("MyType");
        content.addBCIActivity(bciActivity);
        // Save in the database.
        Content contentCreated = contentService.create(content);

        // Create the Content.
        Content content2 = new Content();
        content2.setName("Content 2");
        content2.setDescription("Content Description 2");
        content2.setType("MyType 2");
        content2.addBCIActivity(bciActivity);
        // Save in the database.
        Content contentCreated2 = contentService.create(content2);

        assertEquals(1, contentService.findByType(contentCreated.getType()).size());
        assertEquals(1, contentService.findByType(contentCreated2.getType()).size());
    }

    @Test
    void findByBCIActivity() {
        Content content = new Content();
        content = new Content();
        content.setName("Content Name");
        content.setDescription("Content Description");
        content.setType("Content Video");
        // Checks if the content was created.
        Content contentSaved = contentService.create(content);

        // Create a BCI Activity.
        BCIActivity bciActivity = new BCIActivity();
        bciActivity.setName("Programming2");
        bciActivity.setDescription("Programming language training 2");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.addContent(contentSaved);
        // Create a BCI Activity.
        bciActivityService.create(bciActivity);

        // Find by BCI Activity Id.
        List<Content> result = contentService.findByBCIActivity(bciActivity.getId());

        // Assert that the result.
        assertEquals(1, result.size());
        assertEquals(contentSaved.getName(), result.get(0).getName());
    }

    @Test
    void findBySkill() {
        Content content = new Content();
        content = new Content();
        content.setName("Content Name");
        content.setDescription("Content Description");
        content.setType("Content Video");
        // Checks if the content was created.
        Content contentSaved = contentService.create(content);

        // Create a Skill.
        Skill skill = new Skill();
        skill.setName("Skill 2");
        skill.setDescription("Skill Description 2");
        skill.addContent(contentSaved);
        skill.setType(SkillType.PHYSICAL);
        Skill skillSaved = skillService.create(skill);

        // Find by Skill Id.
        List<Content> result = contentService.findBySkill(skillSaved.getId());

        // Assert that the result.
        assertEquals(1, result.size());
        assertEquals(contentSaved.getName(), result.get(0).getName());
    }

    @Test
    @Override
    void testDeleteById() {
        Content content = new Content();
        content.setName("Work 1");
        content.setDescription("Content Work 1");
        content.setType("Video Work 1");
        content.addBCIActivity(bciActivity);

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
        content.addBCIActivity(bciActivity);

        // Create the Content.
        Content contentCreated = contentService.create(content);

        Content content1 = new Content();
        content1.setName("Work Java");
        content1.setDescription("Content Work Java");
        content1.setType("Video Work javac");
        content1.addBCIActivity(bciActivity);

        // Create the Content.
        Content contentCreated1 = contentService.create(content1);

        // Assert that the result should be two Content.
        assertEquals(2,contentService.findAll().size());
    }
}
