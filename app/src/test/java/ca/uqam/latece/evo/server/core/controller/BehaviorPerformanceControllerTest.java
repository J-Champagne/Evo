package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.repository.BehaviorPerformanceRepository;
import ca.uqam.latece.evo.server.core.service.BehaviorPerformanceService;
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
 * The Behavior Performance Controller test class for the {@link BehaviorPerformanceController}, responsible for
 * testing its various functionalities. This class includes integration tests for CRUD operations supported the
 * controller class, using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = BehaviorPerformanceController.class)
@ContextConfiguration(classes = {BehaviorPerformanceController.class, BehaviorPerformanceService.class, BehaviorPerformance.class})
public class BehaviorPerformanceControllerTest extends AbstractControllerTest {
    @MockBean
    private BehaviorPerformanceRepository behaviorPerformanceRepository;

    private BehaviorPerformance behaviorPerformance = new BehaviorPerformance();
    private Develops develops = new Develops();
    private Requires requires = new Requires();
    private Role role = new Role();
    private Skill skill = new Skill();
    private Content content = new Content();

    @BeforeEach
    void setUp() {
        // Create the role associated with Behavior Performance.
        List<Role> roles = new ArrayList<>();
        role.setId(1L);
        role.setName("Participant - Behavior Performance");
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("e-Facilitator - Behavior Performance");

        roles.add(role);
        roles.add(role2);

        // Create a Behavior Performance.
        behaviorPerformance.setId(1L);
        behaviorPerformance.setName("Programming - Behavior Performance");
        behaviorPerformance.setDescription("Programming language training - Behavior Performance");
        behaviorPerformance.setType(ActivityType.LEARNING);

        // Save in the database.
        when(behaviorPerformanceRepository.save(behaviorPerformance)).thenReturn(behaviorPerformance);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        // Creates Skill.
        skill.setId(1L);
        skill.setName("Skill name - Behavior Performance");
        skill.setDescription("Skill Description - Behavior Performance");
        skill.setType(SkillType.BCT);

        // Creates Develops
        develops.setId(1L);
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);
        develops.setBciActivity(behaviorPerformance);

        // Create the Content.
        content.setId(1L);
        content.setName("Content - Behavior Performance");
        content.setType("Content type - Behavior Performance");
        content.setDescription("Content description - Behavior Performance");

        performCreateRequest("/behaviorperformance", behaviorPerformance);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update a new behavior performance
        behaviorPerformance.setName("Programming 2");
        // Save in the database.
        when(behaviorPerformanceRepository.save(behaviorPerformance)).thenReturn(behaviorPerformance);
        // Perform a PUT request to test the controller.
        performUpdateRequest("/behaviorperformance",
                behaviorPerformance,"$.name", behaviorPerformance.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/behaviorperformance/" + behaviorPerformance.getId(), behaviorPerformance);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create a Behavior Performance.
        BehaviorPerformance bPerformance = dataToPerformTheFindTest();

        // Mock behavior for behaviorPerformanceRepository.save
        when(behaviorPerformanceRepository.save(behaviorPerformance)).thenReturn(behaviorPerformance);
        when(behaviorPerformanceRepository.save(bPerformance)).thenReturn(bPerformance);

        // Mock behavior for behaviorPerformanceRepository.findById().
        when(behaviorPerformanceRepository.findById(bPerformance.getId())).thenReturn(Optional.of(bPerformance));
        when(behaviorPerformanceRepository.findById(behaviorPerformance.getId())).thenReturn(Optional.of(behaviorPerformance));
        // Perform a GET request to test the controller.
        performGetRequest("/behaviorperformance/find/" + bPerformance.getId(), "$.name", bPerformance.getName());
        performGetRequest("/behaviorperformance/find/" + behaviorPerformance.getId(), "$.name", behaviorPerformance.getName());
    }

    @Test
    void testFindByName() throws Exception {
        // Create a Behavior Performance.
        BehaviorPerformance bPerformance = dataToPerformTheFindTest();
        // Mock behavior for behaviorPerformanceRepository.save
        when(behaviorPerformanceRepository.save(behaviorPerformance)).thenReturn(behaviorPerformance);
        when(behaviorPerformanceRepository.save(bPerformance)).thenReturn(bPerformance);

        // Mock behavior for behaviorPerformanceRepository.findByName().
        when(behaviorPerformanceRepository.findByName(behaviorPerformance.getName())).
                thenReturn(Collections.singletonList(behaviorPerformance));
        // Perform a GET request to test the controller.
        performGetRequest("/behaviorperformance/find/name/" + behaviorPerformance.getName(),
                "$[0].name", behaviorPerformance.getName());

        // Mock behavior for behaviorPerformanceRepository.findByName().
        when(behaviorPerformanceRepository.findByName(bPerformance.getName())).
                thenReturn(Collections.singletonList(bPerformance));
        // Perform a GET request to test the controller.
        performGetRequest("/behaviorperformance/find/name/" + bPerformance.getName(),
                "$[0].name", bPerformance.getName());
    }

    @Test
    void testFindByType() throws Exception {
        // Create a Behavior Performance.
        BehaviorPerformance bPerformance = dataToPerformTheFindTest();

        // Mock behavior for behaviorPerformanceRepository.save
        when(behaviorPerformanceRepository.save(behaviorPerformance)).thenReturn(behaviorPerformance);
        when(behaviorPerformanceRepository.save(bPerformance)).thenReturn(bPerformance);

        // Mock behavior for behaviorPerformanceRepository.findByType().
        when(behaviorPerformanceRepository.findByType(bPerformance.getType())).thenReturn(Collections.singletonList(bPerformance));
        // Perform a GET request to test the controller.
        performGetRequest("/behaviorperformance/find/type/" + bPerformance.getType(),
                "$[0].type", bPerformance.getType().toString());

        // Mock behavior for behaviorPerformanceRepository.findByType().
        when(behaviorPerformanceRepository.findByType(bPerformance.getType())).thenReturn(Collections.singletonList(bPerformance));
        // Perform a GET request to test the controller.
        performGetRequest("/behaviorperformance/find/type/" + bPerformance.getType(),
                "$[0].type", bPerformance.getType().toString());
    }

    private BehaviorPerformance dataToPerformTheFindTest() throws Exception {
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

        // Create a Behavior Performance.
        BehaviorPerformance bPerformance = new BehaviorPerformance();
        bPerformance.setId(2L);
        bPerformance.setName("Database Design 2");
        bPerformance.setDescription("Database Design training");
        bPerformance.setType(ActivityType.DIAGNOSING);
        bPerformance.setRole(roles);
        bPerformance.addContent(content);
        bPerformance.addDevelops(develops);
        bPerformance.addRequires(requires);

        return bPerformance;
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Create a Behavior Performance.
        BehaviorPerformance bPerformance = new BehaviorPerformance();

        // Mock behavior for behaviorPerformanceRepository.save
        when(behaviorPerformanceRepository.save(bPerformance)).thenReturn(bPerformance);

        // Mock behavior for behaviorPerformanceRepository.findAll().
        when(behaviorPerformanceRepository.findAll()).thenReturn(Collections.singletonList(bPerformance));
        // Perform a GET request to test the controller.
        performGetRequest("/behaviorperformance", "$[0].id", bPerformance.getId());
    }

}
