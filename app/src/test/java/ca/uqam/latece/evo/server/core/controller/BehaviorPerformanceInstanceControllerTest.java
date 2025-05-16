package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.BehaviorPerformanceInstanceController;
import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorPerformanceInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorPerformanceInstanceRepository;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorPerformanceInstanceService;
import ca.uqam.latece.evo.server.core.util.DateFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The Behavior Performance Controller test class for the {@link BehaviorPerformanceInstanceController}, responsible for
 * testing its various functionalities. This class includes integration tests for CRUD operations supported the
 * controller class, using WebMvcTes, and repository queries using MockMvc (Mockito).
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = BehaviorPerformanceInstanceController.class)
@ContextConfiguration(classes = {BehaviorPerformanceInstanceController.class, BehaviorPerformanceInstanceService.class, BehaviorPerformanceInstance.class})
public class BehaviorPerformanceInstanceControllerTest extends AbstractControllerTest {

    @MockBean
    private BehaviorPerformanceInstanceRepository behaviorPerformanceInstanceRepository;
    private BehaviorPerformanceInstance behaviorPerformanceInstance = new BehaviorPerformanceInstance();
    private BehaviorPerformance behaviorPerformance = new BehaviorPerformance();
    private BCIActivity bciActivity  = new BCIActivity();
    private Role role = new Role();

    private LocalDate localEntryDate = DateFormatter.convertDateStrTo_yyyy_MM_dd("2020/01/08");
    private LocalDate localExitDate = LocalDate.now();

    @BeforeEach
    @Override
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

        // Create a BCI Activity.
        bciActivity.setId(6L);
        bciActivity.setName("Programming 2 - BCIActivity Test");
        bciActivity.setDescription("Programming language training 2 - BCIActivity Test");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.setPreconditions("Preconditions 2 - BCIActivity Test");
        bciActivity.setPostconditions("Post-conditions 2 - BCIActivity Test");
        bciActivity.addRole(role);

        // Create a Behavior Performance.
        behaviorPerformance.setId(1L);
        behaviorPerformance.setName("Programming - Behavior Performance");
        behaviorPerformance.setDescription("Programming language training - Behavior Performance");
        behaviorPerformance.setType(ActivityType.LEARNING);
        behaviorPerformance.setPreconditions("Preconditions 2 - Behavior Performance");
        behaviorPerformance.setPostconditions("Post-conditions 2 - Behavior Performance");
        behaviorPerformance.setRole(roles);

        // Create a Behavior Performance Instance.
        behaviorPerformanceInstance.setId(2L);
        behaviorPerformanceInstance.setBehaviorPerformance(behaviorPerformance);
        behaviorPerformanceInstance.setStatus("Behavior Performance Instance Java");
        behaviorPerformanceInstance.setEntryDate(localEntryDate);
        behaviorPerformanceInstance.setExitDate(localExitDate);
        behaviorPerformanceInstance.setBciActivity(bciActivity);

        // Save in the database.
        when(behaviorPerformanceInstanceRepository.save(behaviorPerformanceInstance)).thenReturn(behaviorPerformanceInstance);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        // Create a Behavior Performance Instance.
        BehaviorPerformanceInstance performanceInstance = new BehaviorPerformanceInstance();
        performanceInstance.setId(6L);
        performanceInstance.setBehaviorPerformance(behaviorPerformance);
        performanceInstance.setStatus("Behavior Performance Instance Java");
        performanceInstance.setEntryDate(localEntryDate);
        performanceInstance.setExitDate(localExitDate);
        performanceInstance.setBciActivity(bciActivity);

        // Save in the database.
        when(behaviorPerformanceInstanceRepository.save(performanceInstance)).thenReturn(performanceInstance);

        // Perform a Create request to test the controller.
        performCreateRequest("/behaviorperformanceinstance", performanceInstance);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        // Create a Behavior Performance Instance.
        BehaviorPerformanceInstance performanceInstance = new BehaviorPerformanceInstance();
        performanceInstance.setId(32L);

        // Perform a Create request to test the controller.
        performCreateRequestBadRequest("/behaviorperformanceinstance", performanceInstance);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update a Behavior Performance Instance.
        behaviorPerformanceInstance.setStatus("Performance Instance 2");

        // Save in the database.
        when(behaviorPerformanceInstanceRepository.save(behaviorPerformanceInstance)).thenReturn(behaviorPerformanceInstance);

        // Perform a PUT request to test the controller.
        performUpdateRequest("/behaviorperformanceinstance", behaviorPerformanceInstance, "$.status",
                behaviorPerformanceInstance.getStatus());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        // Perform a Delete request to test the controller.
        performDeleteRequest("/behaviorperformanceinstance/" + behaviorPerformanceInstance.getId(),
                behaviorPerformanceInstance);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Mock behavior for behaviorPerformanceInstanceRepository.save
        when(behaviorPerformanceInstanceRepository.save(behaviorPerformanceInstance)).thenReturn(behaviorPerformanceInstance);

        // Mock behavior for behaviorPerformanceInstanceRepository.findById().
        when(behaviorPerformanceInstanceRepository.findById(behaviorPerformanceInstance.getId())).
                thenReturn(Optional.of(behaviorPerformanceInstance));

        // Perform a GET request to test the controller.
        performGetRequest("/behaviorperformanceinstance/find/" + behaviorPerformanceInstance.getId(),
                "$.status", behaviorPerformanceInstance.getStatus());
    }

    @Test
    void testFindByStatus() throws Exception {
        // Mock behavior for behaviorPerformanceInstanceRepository.save
        when(behaviorPerformanceInstanceRepository.save(behaviorPerformanceInstance)).thenReturn(behaviorPerformanceInstance);

        // Mock behavior for behaviorPerformanceInstanceRepository.findById().
        when(behaviorPerformanceInstanceRepository.findByStatus(behaviorPerformanceInstance.getStatus())).
                thenReturn(Collections.singletonList(behaviorPerformanceInstance));

        // Perform a GET request to test the controller.
        performGetRequest("/behaviorperformanceinstance/find/status/" + behaviorPerformanceInstance.getStatus(),
                "$[0].status", behaviorPerformanceInstance.getStatus());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for behaviorPerformanceInstanceRepository.save
        when(behaviorPerformanceInstanceRepository.save(behaviorPerformanceInstance)).thenReturn(behaviorPerformanceInstance);

        // Mock behavior for behaviorPerformanceInstanceRepository.findAll().
        when(behaviorPerformanceInstanceRepository.findAll()).thenReturn(Collections.singletonList(behaviorPerformanceInstance));

        // Perform a GET request to test the controller.
        performGetRequest("/behaviorperformanceinstance", "$[0].id", behaviorPerformanceInstance.getId());
    }
}
