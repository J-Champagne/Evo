package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.GoalSettingInstanceController;
import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.GoalSettingInstance;
import ca.uqam.latece.evo.server.core.repository.instance.GoalSettingInstanceRepository;
import ca.uqam.latece.evo.server.core.service.instance.GoalSettingInstanceService;
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
 * The BCIActivityInstance Controller test class for the {@link GoalSettingInstanceController}, responsible for testing
 * its various functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = GoalSettingInstanceController.class)
@ContextConfiguration(classes = {GoalSettingInstanceController.class, GoalSettingInstanceService.class, GoalSettingInstance.class})
public class GoalSettingInstanceControllerTest extends AbstractControllerTest {

    @MockBean
    private GoalSettingInstanceRepository goalSettingInstanceRepository;
    private GoalSettingInstance goalSettingInstance = new GoalSettingInstance();
    private GoalSetting goalSetting = new GoalSetting();
    private BCIActivity bciActivity  = new BCIActivity();
    private Role role = new Role();


    private LocalDate localEntryDate = DateFormatter.convertDateStrTo_yyyy_MM_dd("2020/01/08");
    private LocalDate localExitDate = LocalDate.now();

    @BeforeEach
    @Override
    void setUp() {
        // Create the role associated with Behavior Performance.
        List<Role> roles = new ArrayList<>();
        role.setId(9L);
        role.setName("Participant - Behavior Performance");
        Role role2 = new Role();
        role2.setId(12L);
        role2.setName("e-Facilitator - Goal Setting");

        roles.add(role);
        roles.add(role2);

        // Create a BCI Activity.
        bciActivity.setId(7L);
        bciActivity.setName("Programming 2 - BCIActivity Test");
        bciActivity.setDescription("Programming language training 2 - BCIActivity Test");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.setPreconditions("Preconditions 2 - BCIActivity Test");
        bciActivity.setPostconditions("Post-conditions 2 - BCIActivity Test");
        bciActivity.addRole(role);

        // Create a BGoal Setting.
        goalSetting.setId(2L);
        goalSetting.setName("Goal Setting");
        goalSetting.setDescription("Programming language training - Goal Setting");
        goalSetting.setType(ActivityType.LEARNING);
        goalSetting.setPreconditions("Preconditions 2 - Goal Setting");
        goalSetting.setPostconditions("Post-conditions 2 - Goal Setting");
        goalSetting.setBciActivity(bciActivity);
        goalSetting.setRole(roles);

        // Create a Goal Setting Instance.
        goalSettingInstance.setId(1L);
        goalSettingInstance.setGoalSetting(goalSetting);
        goalSettingInstance.setStatus("Goal Setting Instance Java");
        goalSettingInstance.setEntryDate(localEntryDate);
        goalSettingInstance.setExitDate(localExitDate);
        goalSettingInstance.setBciActivity(bciActivity);

        // Save in the database.
        when(goalSettingInstanceRepository.save(goalSettingInstance)).thenReturn(goalSettingInstance);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        // Create a Goal Setting Instance.
        GoalSettingInstance goalSettingInstance = new GoalSettingInstance();
        goalSettingInstance.setId(1L);
        goalSettingInstance.setGoalSetting(goalSetting);
        goalSettingInstance.setStatus("Goal Setting Instance Java");
        goalSettingInstance.setEntryDate(localEntryDate);
        goalSettingInstance.setExitDate(localExitDate);
        goalSettingInstance.setBciActivity(bciActivity);

        // Save in the database.
        when(goalSettingInstanceRepository.save(goalSettingInstance)).thenReturn(goalSettingInstance);

        // Perform a Create request to test the controller.
        performCreateRequest("/goalsettinginstance", goalSettingInstance);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        // Create a Goal Setting Instance.
        GoalSettingInstance goalSettingInstance = new GoalSettingInstance();
        goalSettingInstance.setId(1L);

        // Perform a Create request to test the controller.
        performCreateRequestBadRequest("/goalsettinginstance", goalSettingInstance);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update a Goal Setting Instance.
        goalSettingInstance.setStatus("Setting Instance");

        // Save in the database.
        when(goalSettingInstanceRepository.save(goalSettingInstance)).thenReturn(goalSettingInstance);

        // Perform a PUT request to test the controller.
        performUpdateRequest("/goalsettinginstance", goalSettingInstance, "$.status",
                goalSettingInstance.getStatus());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        // Perform a Delete request to test the controller.
        performDeleteRequest("/goalsettinginstance/" + goalSettingInstance.getId(), goalSettingInstance);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Mock behavior for goalSettingInstanceRepository.save
        when(goalSettingInstanceRepository.save(goalSettingInstance)).thenReturn(goalSettingInstance);

        // Mock behavior for goalSettingInstanceRepository.findById().
        when(goalSettingInstanceRepository.findById(goalSettingInstance.getId())).thenReturn(Optional.of(goalSettingInstance));

        // Perform a GET request to test the controller.
        performGetRequest("/goalsettinginstance/find/" + goalSettingInstance.getId(), "$.status",
                goalSettingInstance.getStatus());
    }

    @Test
    void testFindByStatus() throws Exception {
        // Mock behavior for goalSettingInstanceRepository.save
        when(goalSettingInstanceRepository.save(goalSettingInstance)).thenReturn(goalSettingInstance);

        // Mock behavior for behaviorPerformanceInstanceRepository.findById().
        when(goalSettingInstanceRepository.findByStatus(goalSettingInstance.getStatus())).
                thenReturn(Collections.singletonList(goalSettingInstance));

        // Perform a GET request to test the controller.
        performGetRequest("/goalsettinginstance/find/status/" + goalSettingInstance.getStatus(),
                "$[0].status", goalSettingInstance.getStatus());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for goalSettingInstanceRepository.save
        when(goalSettingInstanceRepository.save(goalSettingInstance)).thenReturn(goalSettingInstance);

        // Mock behavior for goalSettingInstanceRepository.findAll().
        when(goalSettingInstanceRepository.findAll()).thenReturn(Collections.singletonList(goalSettingInstance));

        // Perform a GET request to test the controller.
        performGetRequest("/goalsettinginstance", "$[0].id", goalSettingInstance.getId());
    }
}