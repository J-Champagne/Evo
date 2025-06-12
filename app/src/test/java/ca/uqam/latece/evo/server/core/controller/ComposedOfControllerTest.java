package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.repository.ComposedOfRepository;
import ca.uqam.latece.evo.server.core.service.ComposedOfService;
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
 * The ComposedOf Controller test class for the {@link ComposedOfController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = ComposedOfController.class)
@ContextConfiguration(classes = {ComposedOfController.class, ComposedOfService.class, ComposedOf.class})
public class ComposedOfControllerTest extends AbstractControllerTest {
    @MockBean
    private ComposedOfRepository composedOfRepository;

    private ComposedOf composedOf = new ComposedOf();
    private BCIActivity bciActivity = new BCIActivity();
    private BehaviorChangeInterventionBlock behaviorChangeInterventionBlock = new BehaviorChangeInterventionBlock();
    private Develops develops = new Develops();
    private Role role = new Role();
    private Skill skill = new Skill();
    private Content content = new Content();


    @BeforeEach
    @Override
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

        // Creates Skill.
        skill.setId(3L);
        skill.setName("Skill name");
        skill.setDescription("Skill Description");
        skill.setType(SkillType.BCT);

        // Create the Content.
        content.setId(4L);
        content.setName("Content");
        content.setType("Content type");
        content.setDescription("Content description");
        content.setSkills(Collections.singletonList(skill));

        // Create the BehaviorChangeInterventionBlock
        behaviorChangeInterventionBlock.setId(5L);
        behaviorChangeInterventionBlock.setEntryConditions("entryConditions - ComposedOf");
        behaviorChangeInterventionBlock.setExitConditions("exitConditions - ComposedOf");

        // Create the BCIActivity
        bciActivity.setId(6L);
        bciActivity.setPreconditions("preconditions");
        bciActivity.setPostconditions("postconditions");
        bciActivity.setName("BCIActivity Name");
        bciActivity.setDescription("BCIActivity Description");
        bciActivity.setType(ActivityType.BCI_ACTIVITY);
        bciActivity.setRole(roles);
        bciActivity.addContent(content);

        // Creates Develops.
        develops.setId(7L);
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);
        develops.setBciActivity(bciActivity);

        // Creates ComposedOf.
        composedOf.setId(8L);
        composedOf.setOrder(1);
        composedOf.setTiming(TimeCycle.BEGINNING);
        composedOf.setBciActivity(bciActivity);
        composedOf.setBciBlock(behaviorChangeInterventionBlock);

        // Save in the database.
        when(composedOfRepository.save(composedOf)).thenReturn(composedOf);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        // Creates a new ComposedOf.
        ComposedOf composedOf = new ComposedOf();
        composedOf.setId(2L);
        composedOf.setOrder(2);
        composedOf.setTiming(TimeCycle.UNSPECIFIED);
        composedOf.setBciActivity(bciActivity);
        composedOf.setBciBlock(behaviorChangeInterventionBlock);

        // Save in the database.
        when(composedOfRepository.save(composedOf)).thenReturn(composedOf);
        // Perform a POST request to test the controller.
        performCreateRequest("/composedof", composedOf);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        ComposedOf composedOf = new ComposedOf();
        composedOf.setId(99L);
        // Perform a POST request with Bad request to test the controller.
        performCreateRequestBadRequest("/composedof", composedOf);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update the ComposedOf.
        composedOf.setOrder(3);
        composedOf.setTiming(TimeCycle.UNSPECIFIED);

        // Save in the database.
        when(composedOfRepository.save(composedOf)).thenReturn(composedOf);

        // Perform a PUT request to test the controller.
        performUpdateRequest("/composedof", composedOf,"$.timing", composedOf.getTiming().name());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/composedof/" + composedOf.getId(), composedOf);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create
        ComposedOf composedOf2 = new ComposedOf();
        composedOf2.setId(4L);
        composedOf2.setOrder(5);
        composedOf2.setTiming(TimeCycle.END);
        composedOf2.setBciActivity(bciActivity);
        composedOf2.setBciBlock(behaviorChangeInterventionBlock);

        // Mock behavior for composedOfRepository.save
        when(composedOfRepository.save(composedOf)).thenReturn(composedOf);
        when(composedOfRepository.save(composedOf2)).thenReturn(composedOf2);

        // Mock behavior for composedOfRepository.findById().
        when(composedOfRepository.findById(composedOf2.getId())).thenReturn(Optional.of(composedOf2));
        when(composedOfRepository.findById(composedOf.getId())).thenReturn(Optional.of(composedOf));

        // Perform a GET request to test the controller.
        performGetRequest("/composedof/find/" + composedOf2.getId(), "$.order", composedOf2.getOrder());
        performGetRequest("/composedof/find/" + composedOf2.getId(), "$.timing", composedOf2.getTiming().name());
        performGetRequest("/composedof/find/" + composedOf.getId(), "$.order", composedOf.getOrder());
        performGetRequest("/composedof/find/" + composedOf.getId(), "$.timing", composedOf.getTiming().name());
    }

    @Test
    void findByTiming() throws Exception {
        // Create
        ComposedOf composedOf2 = new ComposedOf();
        composedOf2.setId(4L);
        composedOf2.setOrder(5);
        composedOf2.setTiming(TimeCycle.END);
        composedOf2.setBciActivity(bciActivity);
        composedOf2.setBciBlock(behaviorChangeInterventionBlock);

        // Mock behavior for composedOfRepository.save
        when(composedOfRepository.save(composedOf)).thenReturn(composedOf);
        when(composedOfRepository.save(composedOf2)).thenReturn(composedOf2);

        // Mock behavior for composedOfRepository.findByTiming().
        when(composedOfRepository.findByTiming(composedOf2.getTiming())).thenReturn(Collections.singletonList(composedOf2));
        when(composedOfRepository.findByTiming(composedOf.getTiming())).thenReturn(Collections.singletonList(composedOf));

        // Perform a GET request to test the controller.
        performGetRequest("/composedof/find/timing/" + composedOf2.getTiming(), "$[0].order", composedOf2.getOrder());
        performGetRequest("/composedof/find/timing/" + composedOf2.getTiming(), "$[0].timing", composedOf2.getTiming().name());
        performGetRequest("/composedof/find/timing/" + composedOf.getTiming(), "$[0].order", composedOf.getOrder());
        performGetRequest("/composedof/find/timing/" + composedOf.getTiming(), "$[0].timing", composedOf.getTiming().name());
    }

    @Test
    void findByOrder() throws Exception {
        // Create
        ComposedOf composedOf2 = new ComposedOf();
        composedOf2.setId(5L);
        composedOf2.setOrder(6);
        composedOf2.setTiming(TimeCycle.BEGINNING);
        composedOf2.setBciActivity(bciActivity);
        composedOf2.setBciBlock(behaviorChangeInterventionBlock);

        // Mock behavior for composedOfRepository.save
        when(composedOfRepository.save(composedOf)).thenReturn(composedOf);
        when(composedOfRepository.save(composedOf2)).thenReturn(composedOf2);

        // Mock behavior for composedOfRepository.findByTiming().
        when(composedOfRepository.findByOrder(composedOf2.getOrder())).thenReturn(Collections.singletonList(composedOf2));
        when(composedOfRepository.findByOrder(composedOf.getOrder())).thenReturn(Collections.singletonList(composedOf));

        // Perform a GET request to test the controller.
        performGetRequest("/composedof/find/order/" + composedOf2.getOrder(), "$[0].order", composedOf2.getOrder());
        performGetRequest("/composedof/find/order/" + composedOf2.getOrder(), "$[0].timing", composedOf2.getTiming().name());
        performGetRequest("/composedof/find/order/" + composedOf.getOrder(), "$[0].order", composedOf.getOrder());
        performGetRequest("/composedof/find/order/" + composedOf.getOrder(), "$[0].timing", composedOf.getTiming().name());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        ComposedOf composedOf2 = new ComposedOf();
        composedOf2.setId(2L);
        composedOf2.setOrder(2);
        composedOf2.setTiming(TimeCycle.UNSPECIFIED);

        // Mock behavior for composedOfRepository.save
        when(composedOfRepository.save(composedOf2)).thenReturn(composedOf2);

        // Mock behavior for composedOfRepository.findAll().
        when(composedOfRepository.findAll()).thenReturn(Collections.singletonList(composedOf2));

        // Perform a GET request to test the controller.
        performGetRequest("/composedof", "$[0].id", composedOf2.getId());
    }
}
