package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Requires;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.repository.RequiresRepository;
import ca.uqam.latece.evo.server.core.service.RequiresService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;


/**
 * The Requires Controller test class for the {@link RequiresController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = RequiresController.class)
@ContextConfiguration(classes = {RequiresController.class, RequiresService.class, Requires.class})
public class RequiresControllerTest extends AbstractControllerTest {

    @MockBean
    private RequiresRepository requiresRepository;

    private Requires requires = new Requires();
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
        skill.setId(2L);
        skill.setName("Skill name");
        skill.setDescription("Skill Description");
        skill.setType(SkillType.BCT);

        // Create a BCI Activity.
        activity.setId(3L);
        activity.setName("Programming");
        activity.setDescription("Programming language training");
        activity.setType(ActivityType.LEARNING);

        // Creates Develops.
        requires.setId(4L);
        requires.setLevel(SkillLevel.BEGINNER);
        requires.setSkill(skill);
        requires.setRole(role);
        requires.setBciActivity(activity);

        when(requiresRepository.save(requires)).thenReturn(requires);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest("/requires", requires);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update Requires
        requires.setLevel(SkillLevel.INTERMEDIATE);
        // Save in the database.
        when(requiresRepository.save(requires)).thenReturn(requires);
        // Perform a PUT request to test the controller.
        performUpdateRequest("/requires", requires, "$.level", requires.getLevel().toString());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/requires/" + requires.getId(), requires);
    }

    private Requires dataToPerformTheFindTest() {
        // Creates Role.
        role.setId(19L);
        role.setName("Participant");

        // Creates BCIActivity.
        activity.setId(91L);
        activity.setName("Programming");
        activity.setDescription("Programming language training");
        activity.setType(ActivityType.LEARNING);
        activity.addRole(role);
        requires.setRole(role);
        requires.setBciActivity(activity);

        Requires requires1 = new Requires();
        Skill skill1 = new Skill();
        Role role1 = new Role();
        BCIActivity activity1 = new BCIActivity();

        // Creates Skill.
        skill1.setId(92L);
        skill1.setName("Skill 1");
        skill1.setDescription("Skill Description 1");
        skill1.setType(SkillType.PHYSICAL);

        // Creates Role.
        role1.setId(29L);
        role1.setName("e-Facitalitor");

        // Creates BCIActivity.
        activity1.setId(32L);
        activity1.setName("Database");
        activity1.setDescription("Database training");
        activity1.setType(ActivityType.LEARNING);
        activity1.addRole(role1);

        // Creates Requires.
        requires1.setId(62L);
        requires1.setLevel(SkillLevel.ADVANCED);
        requires1.setSkill(skill1);
        requires1.setRole(role1);
        requires1.setBciActivity(activity1);

        return requires1;
    }

    @Test
    @Override
    void testFindById() throws Exception {
        Requires requires2 = dataToPerformTheFindTest();

        // Mock behavior for requiresRepository.save
        when(requiresRepository.save(requires)).thenReturn(requires);
        when(requiresRepository.save(requires2)).thenReturn(requires2);

        // Mock behavior for requiresRepository.findById().
        when(requiresRepository.findById(requires2.getId())).thenReturn(Optional.of(requires2));
        when(requiresRepository.findById(requires.getId())).thenReturn(Optional.of(requires));

        // Perform a GET request to test the controller.
        performGetRequest("/requires/find/" + requires2.getId(), "$.level", requires2.getLevel().toString());
        performGetRequest("/requires/find/" + requires.getId(), "$.level", requires.getLevel().toString());
    }

    @Test
    void findByBCIActivityId() throws Exception {
        Requires requires2 = dataToPerformTheFindTest();

        // Mock behavior for requiresRepository.save
        when(requiresRepository.save(requires)).thenReturn(requires);
        when(requiresRepository.save(requires2)).thenReturn(requires2);

        // Mock behavior for requiresRepository.findByBCIActivityId().
        when(requiresRepository.findByBCIActivityId(requires2.getBciActivity().getId())).thenReturn(Collections.singletonList(requires2));
        when(requiresRepository.findByBCIActivityId(requires.getBciActivity().getId())).thenReturn(Collections.singletonList(requires));

        // Perform a GET request to test the controller.
        performGetRequest("/requires/find/bciactivityid/" + requires2.getBciActivity().getId(), "$[0].level", requires2.getLevel().toString());
        performGetRequest("/requires/find/bciactivityid/" + requires.getBciActivity().getId(), "$[0].level", requires.getLevel().toString());
    }

    @Test
    void findByRoleId() throws Exception {
        Requires requires2 = dataToPerformTheFindTest();

        // Mock behavior for requiresRepository.save
        when(requiresRepository.save(requires)).thenReturn(requires);
        when(requiresRepository.save(requires2)).thenReturn(requires2);

        // Mock behavior for requiresRepository.findByRoleId().
        when(requiresRepository.findByRoleId(requires2.getRole().getId())).thenReturn(Collections.singletonList(requires2));
        when(requiresRepository.findByRoleId(requires.getRole().getId())).thenReturn(Collections.singletonList(requires));

        // Perform a GET request to test the controller.
        performGetRequest("/requires/find/roleid/" + requires2.getRole().getId(), "$[0].level", requires2.getLevel().toString());
        performGetRequest("/requires/find/roleid/" + requires.getRole().getId(), "$[0].level", requires.getLevel().toString());
    }

    @Test
    void findBySkillId () throws Exception {
        Requires requires2 = dataToPerformTheFindTest();

        // Mock behavior for requiresRepository.save
        when(requiresRepository.save(requires)).thenReturn(requires);
        when(requiresRepository.save(requires2)).thenReturn(requires2);

        // Mock behavior for requiresRepository.findByRoleId().
        when(requiresRepository.findBySkillId(requires2.getSkill().getId())).thenReturn(Collections.singletonList(requires2));
        when(requiresRepository.findBySkillId(requires.getSkill().getId())).thenReturn(Collections.singletonList(requires));

        // Perform a GET request to test the controller.
        performGetRequest("/requires/find/skillid/" + requires2.getSkill().getId(), "$[0].level", requires2.getLevel().toString());
        performGetRequest("/requires/find/skillid/" + requires.getSkill().getId(), "$[0].level", requires.getLevel().toString());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        Requires requires2 = dataToPerformTheFindTest();

        // Mock behavior for requiresRepository.save
        when(requiresRepository.save(requires)).thenReturn(requires);
        when(requiresRepository.save(requires2)).thenReturn(requires2);

        // Mock behavior for requiresRepository.findAll().
        when(requiresRepository.findAll()).thenReturn(Collections.singletonList(requires2));
        // Perform a GET request to test the controller.
        performGetRequest("/requires", "$[0].level", requires2.getLevel().toString());
    }
}
