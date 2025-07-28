package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.BehaviorPerformanceInstanceController;
import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorPerformanceInstance;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
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
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
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
    private HealthCareProfessional hcp = new HealthCareProfessional();
    private Participant participant = new Participant();
    private LocalDate localEntryDate = DateFormatter.convertDateStrTo_yyyy_MM_dd("2020/01/08");
    private LocalDate localExitDate = LocalDate.now();

    private static final String URL = "/behaviorperformanceinstance";
    private static final String URL_SPLITTER = "/behaviorperformanceinstance/";
    private static final String URL_FIND = "/behaviorperformanceinstance/find/";

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

        // Create Actor and Participant
        hcp.setId(3L);
        hcp.setName("Bob");
        hcp.setEmail("bob@gmail.com");
        hcp.setContactInformation("222-2222");
        hcp.setAffiliation("CIUSSS");
        hcp.setPosition("Chief");
        hcp.setSpecialties("None");

        participant.setId(4L);
        participant.setRole(role);
        participant.setActor(hcp);

        // Create a BCI Activity.
        bciActivity.setId(5L);
        bciActivity.setName("Programming 2 - BCIActivity Test");
        bciActivity.setDescription("Programming language training 2 - BCIActivity Test");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.setPreconditions("Preconditions 2 - BCIActivity Test");
        bciActivity.setPostconditions("Post-conditions 2 - BCIActivity Test");
        bciActivity.addParty(role);

        // Create a Behavior Performance.
        behaviorPerformance.setId(6L);
        behaviorPerformance.setName("Programming - Behavior Performance");
        behaviorPerformance.setDescription("Programming language training - Behavior Performance");
        behaviorPerformance.setType(ActivityType.LEARNING);
        behaviorPerformance.setPreconditions("Preconditions 2 - Behavior Performance");
        behaviorPerformance.setPostconditions("Post-conditions 2 - Behavior Performance");
        behaviorPerformance.setParties(roles);

        // Create a Behavior Performance Instance.
        behaviorPerformanceInstance.setId(7L);
        behaviorPerformanceInstance.setBehaviorPerformance(behaviorPerformance);
        behaviorPerformanceInstance.setStatus(ExecutionStatus.STALLED);
        behaviorPerformanceInstance.setEntryDate(localEntryDate);
        behaviorPerformanceInstance.setExitDate(localExitDate);
        behaviorPerformanceInstance.addParticipant(participant);

        // Save in the database.
        when(behaviorPerformanceInstanceRepository.save(behaviorPerformanceInstance)).thenReturn(behaviorPerformanceInstance);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        // Create a Behavior Performance Instance.
        BehaviorPerformanceInstance performanceInstance = new BehaviorPerformanceInstance();
        performanceInstance.setId(8L);
        performanceInstance.setBehaviorPerformance(behaviorPerformance);
        performanceInstance.setStatus(ExecutionStatus.IN_PROGRESS);
        performanceInstance.setEntryDate(localEntryDate);
        performanceInstance.setExitDate(localExitDate);

        // Save in the database.
        when(behaviorPerformanceInstanceRepository.save(performanceInstance)).thenReturn(performanceInstance);

        // Perform a Create request to test the controller.
        performCreateRequest(URL, performanceInstance);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        // Create a Behavior Performance Instance.
        BehaviorPerformanceInstance performanceInstance = new BehaviorPerformanceInstance();
        performanceInstance.setId(32L);

        // Perform a Create request to test the controller.
        performCreateRequestBadRequest(URL, performanceInstance);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update a Behavior Performance Instance.
        behaviorPerformanceInstance.setStatus(ExecutionStatus.FINISHED);

        // Save in the database.
        when(behaviorPerformanceInstanceRepository.save(behaviorPerformanceInstance)).thenReturn(behaviorPerformanceInstance);

        // Perform a PUT request to test the controller.
        performUpdateRequest(URL, behaviorPerformanceInstance, "$.status", behaviorPerformanceInstance.getStatus().toString());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        // Perform a Delete request to test the controller.
        performDeleteRequest(URL_SPLITTER + behaviorPerformanceInstance.getId(), behaviorPerformanceInstance);
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
        performGetRequest(URL_FIND + behaviorPerformanceInstance.getId(), "$.status",
                behaviorPerformanceInstance.getStatus().toString());
    }

    @Test
    void testFindByStatus() throws Exception {
        // Mock behavior for behaviorPerformanceInstanceRepository.save
        when(behaviorPerformanceInstanceRepository.save(behaviorPerformanceInstance)).thenReturn(behaviorPerformanceInstance);

        // Mock behavior for behaviorPerformanceInstanceRepository.findById().
        when(behaviorPerformanceInstanceRepository.findByStatus(behaviorPerformanceInstance.getStatus())).
                thenReturn(Collections.singletonList(behaviorPerformanceInstance));

        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "status/" + behaviorPerformanceInstance.getStatus(), "$[0].status",
                behaviorPerformanceInstance.getStatus().toString());
    }

    @Test
    void testFindParticipantsId() throws Exception {
        // Mock behavior for goalSettingInstanceRepository.save
        when(behaviorPerformanceInstanceRepository.save(behaviorPerformanceInstance)).thenReturn(behaviorPerformanceInstance);
        // Mock behavior for goalSettingInstanceRepository.findByParticipantsId().
        when(behaviorPerformanceInstanceRepository.findByParticipantsId(participant.getId())).thenReturn((behaviorPerformanceInstance));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND+ "participants/" + participant.getId(), "$.participants.[0].id",
                participant.getId());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for behaviorPerformanceInstanceRepository.save
        when(behaviorPerformanceInstanceRepository.save(behaviorPerformanceInstance)).thenReturn(behaviorPerformanceInstance);

        // Mock behavior for behaviorPerformanceInstanceRepository.findAll().
        when(behaviorPerformanceInstanceRepository.findAll()).thenReturn(Collections.singletonList(behaviorPerformanceInstance));

        // Perform a GET request to test the controller.
        performGetRequest(URL, "$[0].id", behaviorPerformanceInstance.getId());
    }
}
