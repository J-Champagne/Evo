package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.Content;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.repository.ContentRepository;
import ca.uqam.latece.evo.server.core.service.ContentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The Content Controller test class for the {@link ContentController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = ContentController.class)
@ContextConfiguration(classes = {ContentController.class, ContentService.class, Content.class})
public class ContentControllerTest extends AbstractControllerTest {

    @MockBean
    private ContentRepository contentRepository;

    private Content content = new Content();
    private Skill skill = new Skill();

    private static final String URL = "/contents";
    private static final String URL_SPLITTER = "/contents/";
    private static final String URL_FIND = "/contents/find/";

    @BeforeEach
    void setUp() {
        // Create the Content.
        content.setId(1L);
        content.setName("Content");
        content.setType("Content type");
        content.setDescription("Content description");

        // Create the Skill.
        skill.setId(2L);
        skill.setName("Skill Name 1");
        skill.setDescription("Skill Description 1");
        skill.setType(SkillType.PHYSICAL);

        // Add the skill in the content.
        content.addSkill(skill);

        // Save in the database.
        when(contentRepository.save(content)).thenReturn(content);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(URL, content);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        // Creates a Content invalid.
        Content content = new Content();
        content.setId(999L);
        // Perform a POST with a Bad Request to test the controller.
        performCreateRequestBadRequest(URL, content);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Create the Content.
        content.setId(1L);
        content.setName("Content 2");
        content.setType("Content type 2");
        content.setDescription("Content description 2");

        // Save in the database.
        when(contentRepository.save(content)).thenReturn(content);
        // Perform a PUT request to test the controller.
        performUpdateRequest(URL, content, "$.name",content.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(URL_SPLITTER + content.getId(), content);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create the Content.
        content.setId(2L);
        content.setName("Content 2");
        content.setType("Content type 2");
        content.setDescription("Content description 2");

        // Save the content.
        when(contentRepository.save(content)).thenReturn(content);

        // Mock behavior for contentRepository.findAll().
        when(contentRepository.findById(content.getId())).thenReturn(Optional.of(content));

        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + content.getId(), "$.name", content.getName());
    }

    @Test
    void testFindByName() throws Exception {
        // Create the Content.
        Content content = new Content();
        content.setId(3L);
        content.setName("Content2");
        content.setType("Content Type2");
        content.setDescription("Content description2");

        // Save the content.
        when(contentRepository.save(content)).thenReturn(content);

        // Mock behavior for contentRepository.findByName().
        when(contentRepository.findByName(content.getName())).thenReturn(Collections.singletonList(content));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "name/" + content.getName(), "$[0].name", content.getName());
    }

    @Test
    void testFindByType() throws Exception {
        // Create the Content.
        Content content = new Content();
        content.setId(2L);
        content.setName("Content 2");
        content.setType("Type");
        content.setDescription("Content description 2");

        // Save the content.
        when(contentRepository.save(content)).thenReturn(content);

        // Mock behavior for contentRepository.findByType().
        when(contentRepository.findByType(content.getType())).thenReturn(Collections.singletonList(content));

        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "type/" + content.getType(), "$[0].type", content.getType());
    }

    @Test
    void testFindBySkill() throws Exception {
        // Create the Content.
        Content content = new Content();
        content.setId(3L);
        content.setName("Content 22");
        content.setType("Type22");
        content.setDescription("Content description 22");
        // Add the skill in the content.
        content.addSkill(skill);

        // Save the content.
        when(contentRepository.save(content)).thenReturn(content);

        // Mock behavior for contentRepository.findByType().
        when(contentRepository.findBySkill(skill.getId())).thenReturn(Collections.singletonList(content));

        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "skill/" + skill.getId(), "$[0].name", content.getName());
    }

    @Test
    void testFindByBCIActivity() throws Exception {
        // Create the Content.
        Content content = new Content();
        content.setId(3L);
        content.setName("Content 223");
        content.setType("Type223");
        content.setDescription("Content description 223");
        // Add the skill in the content.
        content.addSkill(skill);

        BCIActivity bciActivity = new BCIActivity();
        bciActivity.setId(6L);
        bciActivity.setName("BCI Activity 6");
        bciActivity.setDescription("BCI Activity 6");
        bciActivity.setType(ActivityType.LEARNING);

        // Add the BCIActivity in the content.
        content.addBCIActivity(bciActivity);

        // Save the content.
        when(contentRepository.save(content)).thenReturn(content);

        // Mock behavior for contentRepository.findByType().
        when(contentRepository.findByBCIActivity(bciActivity.getId())).thenReturn(Collections.singletonList(content));

        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "bciactivityid/" + bciActivity.getId(), "$[0].name", content.getName());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for contentRepository.findAll().
        when(contentRepository.findAll()).thenReturn(Collections.singletonList(content));

        // Perform a GET request to test the controller.
        performGetRequest(URL,"$[0].id",1);
    }

    @Test
    void testFindAllNotFound() throws Exception {
        // Mock behavior for contentRepository.findAll().
        when(contentRepository.findAll()).thenReturn(Collections.emptyList());

        // Perform a GET request to test the controller.
        performGetRequestNotFound(URL,"$[0].id");
    }
}
