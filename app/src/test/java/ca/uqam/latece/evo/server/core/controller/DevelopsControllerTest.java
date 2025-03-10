package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.Develops;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.repository.DevelopsRepository;
import ca.uqam.latece.evo.server.core.service.DevelopsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;


import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;


/**
 * The Develops Controller test class for the {@link DevelopsController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = DevelopsController.class)
@ContextConfiguration(classes = {DevelopsController.class, DevelopsService.class, Develops.class})
public class DevelopsControllerTest extends AbstractControllerTest {

    @MockBean
    private DevelopsRepository developsRepository;

    private Develops develops = new Develops();
    private Skill skill = new Skill();
    private Role role = new Role();
    private BCIActivity activity = new BCIActivity();


    @BeforeEach
    @Override
    void setUp() {
        // Create a Role.
        role.setId(1L);
        role.setName("Admin");

        // Creates Skill.
        skill.setId(1L);
        skill.setName("Skill name");
        skill.setDescription("Skill Description");
        skill.setType(SkillType.BCT);

        // Create a BCI Activity.
        activity.setId(1L);
        activity.setName("Programming");
        activity.setDescription("Programming language training");
        activity.setType(ActivityType.LEARNING);

        // Creates Develops.
        develops.setId(1L);
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);
        develops.setBciActivity(activity);

        // Save in the database.
        when(developsRepository.save(develops)).thenReturn(develops);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest("/develops", develops);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update develops
        develops.setLevel(SkillLevel.INTERMEDIATE);

        // Save in the database.
        when(developsRepository.save(develops)).thenReturn(develops);
        // Perform a PUT request to test the controller.
        performUpdateRequest("/develops", develops, "$.level", develops.getLevel().toString());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/develops/" + develops.getId(), develops);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        Develops develops2 = dataToPerformTheFindTest();

        // Mock behavior for developsRepository.save
        when(developsRepository.save(develops)).thenReturn(develops);
        when(developsRepository.save(develops2)).thenReturn(develops2);

        // Mock behavior for developsRepository.findById().
        when(developsRepository.findById(develops2.getId())).thenReturn(Optional.of(develops2));
        when(developsRepository.findById(develops.getId())).thenReturn(Optional.of(develops));

        // Perform a GET request to test the controller.
        performGetRequest("/develops/find/" + develops2.getId(), "$.level", develops2.getLevel().toString());
        performGetRequest("/develops/find/" + develops.getId(), "$.level", develops.getLevel().toString());
    }

    private Develops dataToPerformTheFindTest() {
        // Creates Role.
        role.setId(1L);
        role.setName("Participant");

        // Creates BCIActivity.
        activity.setId(1L);
        activity.setName("Programming");
        activity.setDescription("Programming language training");
        activity.setType(ActivityType.LEARNING);
        activity.addRole(role);

        // Add Role and BCIActivity to Develops.
        develops.setRole(role);
        develops.setBciActivity(activity);

        Develops develops = new Develops();
        Skill skill = new Skill();
        Role role1 = new Role();
        BCIActivity activity1 = new BCIActivity();

        // Creates Skill.
        skill.setId(2L);
        skill.setName("Skill 1");
        skill.setDescription("Skill Description 1");
        skill.setType(SkillType.PHYSICAL);

        // Creates Role.
        role1.setId(2L);
        role1.setName("e-Facitalitor");

        // Creates BCIActivity.
        activity1.setId(2L);
        activity1.setName("Database");
        activity1.setDescription("Database training");
        activity1.setType(ActivityType.LEARNING);
        activity1.addRole(role1);

        // Creates Develops.
        develops.setId(2L);
        develops.setLevel(SkillLevel.ADVANCED);
        develops.setSkill(skill);
        develops.setRole(role1);
        develops.setBciActivity(activity1);

        return develops;
    }

    @Test
    void testFindByBCIActivityId() throws Exception {
        Develops develops2 = dataToPerformTheFindTest();

        // Mock behavior for developsRepository.save
        when(developsRepository.save(develops)).thenReturn(develops);
        when(developsRepository.save(develops2)).thenReturn(develops2);

        // Mock behavior for developsRepository.findByBCIActivityId().
        when(developsRepository.findByBCIActivityId(develops2.getBciActivity().getId())).thenReturn(Collections.singletonList(develops2));
        when(developsRepository.findByBCIActivityId(develops.getBciActivity().getId())).thenReturn(Collections.singletonList(develops));

        // Perform a GET request to test the controller.
        performGetRequest("/develops/find/bciactivityid/" + develops2.getBciActivity().getId(), "$[0].level", develops2.getLevel().toString());
        performGetRequest("/develops/find/bciactivityid/" + develops.getBciActivity().getId(), "$[0].level", develops.getLevel().toString());
    }

    @Test
    void testFindByRoleId() throws Exception {
        Develops develops2 = dataToPerformTheFindTest();

        // Mock behavior for developsRepository.save
        when(developsRepository.save(develops)).thenReturn(develops);
        when(developsRepository.save(develops2)).thenReturn(develops2);

        // Mock behavior for developsRepository.findByRoleId().
        when(developsRepository.findByRoleId(develops2.getRole().getId())).thenReturn(Collections.singletonList(develops2));
        when(developsRepository.findByRoleId(develops.getRole().getId())).thenReturn(Collections.singletonList(develops));

        // Perform a GET request to test the controller.
        performGetRequest("/develops/find/roleid/" + develops2.getRole().getId(), "$[0].level", develops2.getLevel().toString());
        performGetRequest("/develops/find/roleid/" + develops.getRole().getId(), "$[0].level", develops.getLevel().toString());
    }

    @Test
    void testFindBySkillId () throws Exception {
        Develops develops2 = dataToPerformTheFindTest();

        // Mock behavior for developsRepository.save
        when(developsRepository.save(develops)).thenReturn(develops);
        when(developsRepository.save(develops2)).thenReturn(develops2);

        // Mock behavior for developsRepository.findBySkillId().
        when(developsRepository.findBySkillId(develops2.getSkill().getId())).thenReturn(Collections.singletonList(develops2));
        when(developsRepository.findBySkillId(develops.getSkill().getId())).thenReturn(Collections.singletonList(develops));

        // Perform a GET request to test the controller.
        performGetRequest("/develops/find/skillid/" + develops2.getSkill().getId(), "$[0].level", develops2.getLevel().toString());
        performGetRequest("/develops/find/skillid/" + develops.getSkill().getId(), "$[0].level", develops.getLevel().toString());
    }

    @Test
    void testFindByLevel() throws Exception {
        Develops develops2 = dataToPerformTheFindTest();
        when(developsRepository.save(develops)).thenReturn(develops);

        when(developsRepository.save(develops2)).thenReturn(develops2);
        when(developsRepository.findByLevel(develops2.getLevel())).thenReturn(Collections.singletonList(develops2));
        when(developsRepository.findByLevel(develops.getLevel())).thenReturn(Collections.singletonList(develops));

        // Perform a GET request to test the controller.
        performGetRequest("/develops/find/level/" + develops2.getLevel().toString(), "$[0].level", develops2.getLevel().toString());
        performGetRequest("/develops/find/level/" + develops.getLevel().toString(), "$[0].level", develops.getLevel().toString());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        Develops develops2 = dataToPerformTheFindTest();

        // Mock behavior for developsRepository.save
        when(developsRepository.save(develops)).thenReturn(develops);
        when(developsRepository.save(develops2)).thenReturn(develops2);

        // Mock behavior for developsRepository.findAll().
        when(developsRepository.findAll()).thenReturn(Collections.singletonList(develops2));
        // Perform a GET request to test the controller.
        performGetRequest("/develops", "$[0].level", develops2.getLevel().toString());
    }
}
