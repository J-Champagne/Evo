package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.BehaviorChangeInterventionBlockInstanceController;
import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.repository.instance.BCIActivityInstanceRepository;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionBlockInstanceRepository;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionBlockInstanceService;
import ca.uqam.latece.evo.server.core.util.DateFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Tests methods found in BehaviorChangeInterventionBlockInstanceController using WebMvcTest, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = BehaviorChangeInterventionBlockInstanceController.class)
@ContextConfiguration(classes = {BehaviorChangeInterventionBlockInstance.class, BehaviorChangeInterventionBlockInstanceService.class,
        BehaviorChangeInterventionBlockInstanceController.class})
public class BehaviorChangeInterventionBlockInstanceControllerTest extends AbstractControllerTest {
    @MockBean
    private BehaviorChangeInterventionBlockInstanceRepository bciBlockInstanceRepository;

    @MockBean
    private BCIActivityInstanceRepository bciActivityInstanceRepo;

    private Role role = new Role("Administrator");

    private Participant participant = new Participant(role, new HealthCareProfessional("Bob", "bob@gmail.com",
            "222-2222", "Student", "New-York", "Health"));

    private List<Participant> participants = List.of(participant);

    private BCIActivity bciActivity = new BCIActivity("Programming", "Description", ActivityType.BCI_ACTIVITY, "ENTRY_CONDITION",
            "EXIT_CONDITION");

    private BCIActivityInstance activityInstance = new BCIActivityInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
            DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants, bciActivity);

    private List<BCIActivityInstance> activities = List.of(activityInstance);

    private BehaviorChangeInterventionBlock bciBlock = new BehaviorChangeInterventionBlock("ENTRY_CONDITION",
            "EXIT_CONDITION");

    private BehaviorChangeInterventionBlockInstance blockInstance = new BehaviorChangeInterventionBlockInstance(
            ExecutionStatus.STALLED, TimeCycle.BEGINNING, activities, bciBlock);

    private static final String url = "/behaviorchangeinterventionblockinstance";

    @BeforeEach
    @Override
    public void setUp() {
        activityInstance.setId(1L);
        blockInstance.setId(2L);
        bciBlock.setId(3L);
        when(bciActivityInstanceRepo.save(activityInstance)).thenReturn(activityInstance);
        when(bciBlockInstanceRepository.save(blockInstance)).thenReturn(blockInstance);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(url, blockInstance);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        BehaviorChangeInterventionBlockInstance updated = new BehaviorChangeInterventionBlockInstance(ExecutionStatus.STALLED,
                TimeCycle.END, blockInstance.getActivities(), bciBlock);
        updated.setId(blockInstance.getId());

        when(bciBlockInstanceRepository.save(updated)).thenReturn(updated);
        when(bciBlockInstanceRepository.findById(updated.getId())).thenReturn(Optional.of(updated));
        performUpdateRequest(url, updated, "$.stage", updated.getStage().toString());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(url + "/" + blockInstance.getId(), blockInstance);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(bciBlockInstanceRepository.findAll()).thenReturn(Collections.singletonList(blockInstance));
        performGetRequest(url, "$[0].id", blockInstance.getId());
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(bciBlockInstanceRepository.findById(blockInstance.getId())).thenReturn(Optional.ofNullable(blockInstance));
        performGetRequest(url + "/find/" + blockInstance.getId(), "$.id", blockInstance.getId());
    }

    @Test
    void testFindByStage() throws Exception {
        when(bciBlockInstanceRepository.findByStage(blockInstance.getStage())).thenReturn(Collections.singletonList(blockInstance));
        performGetRequest(url + "/find/stage/" + blockInstance.getStage(), "$[0].id", blockInstance.getId());
    }

    @Test
    void testFindByActivitiesId() throws Exception {
        when(bciBlockInstanceRepository.findByActivitiesId(blockInstance.getActivities().getFirst().getId())).thenReturn(Collections.singletonList(blockInstance));
        performGetRequest(url + "/find/activities/" + blockInstance.getActivities().getFirst().getId(),
                "$[0].id", blockInstance.getId());
    }

    @Test
    void testFindByBehaviorChangeInterventionBlockId() throws Exception {
        when(bciBlockInstanceRepository.findByBehaviorChangeInterventionBlockId(blockInstance.getBehaviorChangeInterventionBlock().getId()))
                .thenReturn(Collections.singletonList(blockInstance));
        performGetRequest(url + "/find/behaviorchangeinterventionblock/" + blockInstance.getBehaviorChangeInterventionBlock().getId(),
                "$[0].behaviorChangeInterventionBlock.id", blockInstance.getBehaviorChangeInterventionBlock().getId());
    }
}