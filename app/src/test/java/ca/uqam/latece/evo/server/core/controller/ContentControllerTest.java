package ca.uqam.latece.evo.server.core.controller;


import ca.uqam.latece.evo.server.core.enumeration.SkillType;
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
 * Content Controller Test class.
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

    @BeforeEach
    void setUp() {
        // Create the Content.
        content.setId(1L);
        content.setName("Content");
        content.setType("Content type");
        content.setDescription("Content description");

        // Create the Skill.
        skill.setId(1L);
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
        performCreateRequest("/contents", content);
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
        performUpdateRequest("/contents", content,
                "$.name",content.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/contents/{id}", content);
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
        performGetRequest("/contents/find/" + content.getId(),
                "$.name", content.getName());
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
        performGetRequest("/contents/find/name/" + content.getName(),
                "$[0].name", content.getName());
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
        performGetRequest("/contents/find/type/" + content.getType(),
                "$[0].type", content.getType());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for contentRepository.findAll().
        when(contentRepository.findAll()).thenReturn(Collections.singletonList(content));

        // Perform a GET request to test the controller.
        performGetRequest("/contents","$[0].id",1);
    }
}
