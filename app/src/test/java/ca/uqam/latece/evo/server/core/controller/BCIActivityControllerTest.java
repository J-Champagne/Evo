package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.repository.BCIActivityRepository;
import ca.uqam.latece.evo.server.core.service.BCIActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The BCIActivity Controller test class for the {@link BCIActivityController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = BCIActivityController.class)
@ContextConfiguration(classes = {BCIActivityController.class, BCIActivityService.class, BCIActivity.class})
public class BCIActivityControllerTest extends AbstractControllerTest {
    @MockBean
    private
     BCIActivityRepository bciActivityRepository;

    private BCIActivity bciActivity  = new BCIActivity();
    private Develops develops = new Develops();
    private Requires requires = new Requires();
    private Role role = new Role();
    private Skill skill = new Skill();
    private Content content = new Content();


    @BeforeEach
    void setUp() {
        // Create the role associated with BCI Activity.
        List<Role> roles = new ArrayList<>();
        role.setId(1L);
        role.setName("Participant");
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("e-Facilitator");

        roles.add(role);
        roles.add(role2);

        // Create a BCI Activity.
        bciActivity.setId(1L);
        bciActivity.setName("Programming");
        bciActivity.setDescription("Programming language training");
        bciActivity.setType(ActivityType.LEARNING);
        //bciActivity.setRole(roles);

        // Save in the database.
        when(bciActivityRepository.save(bciActivity)).thenReturn(bciActivity);
    }

    @Test
    @Override
    void testCreate() throws Exception {
       // Creates Skill.
        skill.setId(1L);
        skill.setName("Skill name");
        skill.setDescription("Skill Description");
        skill.setType(SkillType.BCT);

        // Creates Develops
        develops.setId(1L);
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);
        develops.setBciActivity(bciActivity);

        // Create the Content.
        content.setId(1L);
        content.setName("Content");
        content.setType("Content type");
        content.setDescription("Content description");

        performCreateRequest("/bciactivity", bciActivity);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        BCIActivity bciActivity  = new BCIActivity();
        bciActivity.setId(1L);

        performCreateRequestBadRequest("/bciactivity", bciActivity);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update a new BCIActivity
        bciActivity.setName("Programming 2");
        // Save in the database.
        when(bciActivityRepository.save(bciActivity)).thenReturn(bciActivity);
        // Perform a PUT request to test the controller.
        performUpdateRequest("/bciactivity", bciActivity,"$.name", bciActivity.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/bciactivity/" + bciActivity.getId(), bciActivity);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create a BCI Activity.
        BCIActivity bciActivity2 = dataToPerformTheFindTest();

        // Mock behavior for bciActivityRepository.save
        when(bciActivityRepository.save(bciActivity)).thenReturn(bciActivity);
        when(bciActivityRepository.save(bciActivity2)).thenReturn(bciActivity2);

        // Mock behavior for bciActivityRepository.findById().
        when(bciActivityRepository.findById(bciActivity2.getId())).thenReturn(Optional.of(bciActivity2));
        when(bciActivityRepository.findById(bciActivity.getId())).thenReturn(Optional.of(bciActivity));
        // Perform a GET request to test the controller.
        performGetRequest("/bciactivity/find/" + bciActivity2.getId(), "$.name", bciActivity2.getName());
        performGetRequest("/bciactivity/find/" + bciActivity.getId(), "$.name", bciActivity.getName());
    }

    @Test
    void testFindByName() throws Exception {
        // Create a BCI Activity.
        BCIActivity bciActivity2 = dataToPerformTheFindTest();
        // Mock behavior for bciActivityRepository.save
        when(bciActivityRepository.save(bciActivity)).thenReturn(bciActivity);
        when(bciActivityRepository.save(bciActivity2)).thenReturn(bciActivity2);

        // Mock behavior for bciActivityRepository.findByName().
        when(bciActivityRepository.findByName(bciActivity.getName())).thenReturn(Collections.singletonList(bciActivity));
        // Perform a GET request to test the controller.
        performGetRequest("/bciactivity/find/name/" + bciActivity.getName(),
                "$[0].name", bciActivity.getName());

        // Mock behavior for bciActivityRepository.findByName().
        when(bciActivityRepository.findByName(bciActivity2.getName())).thenReturn(Collections.singletonList(bciActivity2));
        // Perform a GET request to test the controller.
        performGetRequest("/bciactivity/find/name/" + bciActivity2.getName(),
                "$[0].name", bciActivity2.getName());
    }

    @Test
    void testFindByType() throws Exception {
        BCIActivity bciActivity2 = dataToPerformTheFindTest();

        // Mock behavior for bciActivityRepository.save
        when(bciActivityRepository.save(bciActivity)).thenReturn(bciActivity);
        when(bciActivityRepository.save(bciActivity2)).thenReturn(bciActivity2);

        // Mock behavior for bciActivityRepository.findByType().
        when(bciActivityRepository.findByType(bciActivity.getType())).thenReturn(Collections.singletonList(bciActivity));
        // Perform a GET request to test the controller.
        performGetRequest("/bciactivity/find/type/" + bciActivity.getType(),
                "$[0].type", bciActivity.getType().toString());

        // Mock behavior for bciActivityRepository.findByType().
        when(bciActivityRepository.findByType(bciActivity2.getType())).thenReturn(Collections.singletonList(bciActivity2));
        // Perform a GET request to test the controller.
        performGetRequest("/bciactivity/find/type/" + bciActivity2.getType(),
                "$[0].type", bciActivity2.getType().toString());
    }

    @Test
    void testFindByDevelops() throws Exception {
        BCIActivity bciActivity2 = dataToPerformTheFindTest();

        // Mock behavior for bciActivityRepository.save
        when(bciActivityRepository.save(bciActivity2)).thenReturn(bciActivity2);

        Develops develops = bciActivity2.getDevelops().get(0);

        // Mock behavior for bciActivityRepository.findByDevelops().
        when(bciActivityRepository.findByDevelops(develops.getId())).thenReturn(Collections.singletonList(bciActivity2));
        // Perform a GET request to test the controller.
        performGetRequest("/bciactivity/find/develops/" + develops.getId(),
                "$[0].name", bciActivity2.getName());
    }

    @Test
    void testFindByRequires() throws Exception {
        BCIActivity bciActivity2 = dataToPerformTheFindTest();

        // Mock behavior for bciActivityRepository.save
        when(bciActivityRepository.save(bciActivity2)).thenReturn(bciActivity2);

        // Mock behavior for bciActivityRepository.findByRequires().
        when(bciActivityRepository.findByRequires(requires.getId())).thenReturn(Collections.singletonList(bciActivity2));
        // Perform a GET request to test the controller.
        performGetRequest("/bciactivity/find/requires/" + requires.getId(),
                "$[0].name", bciActivity2.getName());
    }

    @Test
    void testFindByRole() throws Exception {
        BCIActivity bciActivity2 = dataToPerformTheFindTest();

        // Mock behavior for bciActivityRepository.save
        when(bciActivityRepository.save(bciActivity2)).thenReturn(bciActivity2);

        // Mock behavior for bciActivityRepository.findByRole().
        when(bciActivityRepository.findByRole(role.getId())).thenReturn(Collections.singletonList(bciActivity2));
        // Perform a GET request to test the controller.
        performGetRequest("/bciactivity/find/role/" + role.getId(),
                "$[0].name", bciActivity2.getName());
    }

    @Test
    void testFindByContent() throws Exception {
        BCIActivity bciActivity2 = dataToPerformTheFindTest();

        // Mock behavior for bciActivityRepository.save
        when(bciActivityRepository.save(bciActivity2)).thenReturn(bciActivity2);

        Content content = bciActivity2.getContent().get(0);

        // Mock behavior for bciActivityRepository.findByContent().
        when(bciActivityRepository.findByContent(content.getId())).thenReturn(Collections.singletonList(bciActivity2));
        // Perform a GET request to test the controller.
        performGetRequest("/bciactivity/find/content/" + content.getId(),
                "$[0].name", bciActivity2.getName());
    }

    private BCIActivity dataToPerformTheFindTest() throws Exception {
        List<Role> roles = new ArrayList<>();
        Role role2 = new Role();
        role2.setId(3L);
        role2.setName("e-Facilitator 2");

        roles.add(role);
        roles.add(role2);

        Develops develops = new Develops();
        develops.setId(4L);
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);

        requires.setId(6L);
        requires.setLevel(SkillLevel.BEGINNER);
        requires.setSkill(skill);
        requires.setRole(role);

        Content content = new Content();
        content.setId(5L);
        content.setName("Content");
        content.setType("Content type");
        content.setDescription("Content description");

        // Create a BCI Activity.
        BCIActivity bciActivity2 = new BCIActivity();
        bciActivity2.setId(2L);
        bciActivity2.setName("Database Design 2");
        bciActivity2.setDescription("Database Design training");
        bciActivity2.setType(ActivityType.DIAGNOSING);
        bciActivity2.setRole(roles);
        bciActivity2.addContent(content);
        bciActivity2.addDevelops(develops);
        bciActivity2.addRequires(requires);

        return bciActivity2;
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Create a BCI Activity.
        BCIActivity bciActivity2 = dataToPerformTheFindTest();

        // Mock behavior for bciActivityRepository.save
        when(bciActivityRepository.save(bciActivity2)).thenReturn(bciActivity2);

        // Mock behavior for bciActivityRepository.findAll().
        when(bciActivityRepository.findAll()).thenReturn(Collections.singletonList(bciActivity2));
        // Perform a GET request to test the controller.
        performGetRequest("/bciactivity", "$[0].id", bciActivity2.getId());
    }
}
