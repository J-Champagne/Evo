package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.BehaviorChangeInterventionPhaseInstanceController;
import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.repository.instance.BCIModuleInstanceRepository;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionBlockInstanceRepository;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionPhaseInstanceRepository;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionPhaseInstanceService;

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
 * Tests methods found in BehaviorChangeInterventionPhaseInstanceController using WebMvcTest, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = BehaviorChangeInterventionPhaseInstanceController.class)
@ContextConfiguration(classes = {BehaviorChangeInterventionPhaseInstance.class, BehaviorChangeInterventionPhaseInstanceService.class,
        BehaviorChangeInterventionPhaseInstanceController.class})
public class BehaviorChangeInterventionPhaseInstanceControllerTest extends AbstractControllerTest {
    private static final String PHASE_ENTRY_CONDITION = "Intervention Phase ENTRY";

    private static final String PHASE_EXIT_CONDITION = "Intervention Phase EXIT";

    @MockBean
    BehaviorChangeInterventionPhaseInstanceRepository bciPhaseInstanceRepository;

    @MockBean
    BehaviorChangeInterventionBlockInstanceRepository bciBlockInstanceRepository;

    @MockBean
    BCIModuleInstanceRepository bciModuleInstanceRepository;

    private Role role = new Role("Administrator");

    private HealthCareProfessional hcp = new HealthCareProfessional("Bob", "bob@gmail.com",
            "222-2222", "Student", "New-York", "Health");

    private Participant participant = new Participant(role, hcp);

    private List<Participant> participants = List.of(participant);

    private BCIActivity bciActivity = new BCIActivity("Programming", "Description", ActivityType.BCI_ACTIVITY,
            "ENTRY_CONDITION", "EXIT_CONDITION");

    private BCIActivityInstance activityInstance = new BCIActivityInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
            DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants, bciActivity);

    private List<BCIActivityInstance> activities = List.of(activityInstance);

    private BCIModuleInstance moduleInstance = new BCIModuleInstance(ExecutionStatus.STALLED, OutcomeType.SUCCESSFUL, activities);

    private List<BCIModuleInstance> modules = List.of(moduleInstance);

    private BehaviorChangeInterventionBlock bciBlock = new BehaviorChangeInterventionBlock
            ("ENTRY_CONDITION", "EXIT_CONDITION");

    private BehaviorChangeInterventionBlockInstance blockInstance = new BehaviorChangeInterventionBlockInstance(
            ExecutionStatus.STALLED, TimeCycle.BEGINNING, activities, bciBlock);

    private List<BehaviorChangeInterventionBlockInstance> blocks = List.of(blockInstance);

    private BehaviorChangeInterventionPhase bciPhase = new BehaviorChangeInterventionPhase(PHASE_ENTRY_CONDITION,
            PHASE_EXIT_CONDITION);

    private BehaviorChangeInterventionPhaseInstance phaseInstance = new BehaviorChangeInterventionPhaseInstance(
            ExecutionStatus.STALLED, blockInstance, blocks, modules, bciPhase);

    private static final String URL = "/behaviorchangeinterventionphaseinstance";

    @BeforeEach
    @Override
    public void setUp() {
        blockInstance.setId(1L);
        moduleInstance.setId(2L);
        phaseInstance.setId(3L);
        bciPhase.setId(4L);
        bciBlock.setId(5L);
        when(bciBlockInstanceRepository.save(blockInstance)).thenReturn(blockInstance);
        when(bciModuleInstanceRepository.save(moduleInstance)).thenReturn(moduleInstance);
        when(bciPhaseInstanceRepository.save(phaseInstance)).thenReturn(phaseInstance);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(URL, phaseInstance);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        BehaviorChangeInterventionBlockInstance blockInstance2 = new BehaviorChangeInterventionBlockInstance(
                ExecutionStatus.STALLED, TimeCycle.END, activities, bciBlock);
        blockInstance2.setId(4L);
        when(bciBlockInstanceRepository.save(blockInstance2)).thenReturn(blockInstance2);

        BehaviorChangeInterventionPhaseInstance updated = new BehaviorChangeInterventionPhaseInstance(
                ExecutionStatus.STALLED, blockInstance2, blocks, modules, bciPhase);
        updated.setId(phaseInstance.getId());

        when(bciPhaseInstanceRepository.save(updated)).thenReturn(updated);
        when(bciPhaseInstanceRepository.findById(updated.getId())).thenReturn(Optional.of(updated));
        performUpdateRequest(URL, updated, "$.currentBlock.stage", updated.getCurrentBlock().getStage().toString());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(URL + "/" + phaseInstance.getId(), phaseInstance);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(bciPhaseInstanceRepository.findAll()).thenReturn(Collections.singletonList(phaseInstance));
        performGetRequest(URL, "$[0].id", phaseInstance.getId());
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(bciPhaseInstanceRepository.findById(phaseInstance.getId())).thenReturn(Optional.ofNullable(phaseInstance));
        performGetRequest(URL + "/find/" + phaseInstance.getId(), "$.id", phaseInstance.getId());
    }

    @Test
    void testFindByCurrentBlockId() throws Exception {
        when(bciPhaseInstanceRepository.findByCurrentBlockId(phaseInstance.getCurrentBlock().getId())).thenReturn(Collections.singletonList(phaseInstance));
        performGetRequest(URL + "/find/currentblock/" + phaseInstance.getCurrentBlock().getId(), "$[0].id", phaseInstance.getId());
    }

    @Test
    void testFindByBlocksId() throws Exception {
        when(bciPhaseInstanceRepository.findByActivitiesId(phaseInstance.getActivities().getFirst().getId())).thenReturn(Collections.singletonList(phaseInstance));
        performGetRequest(URL + "/find/activities/" + phaseInstance.getActivities().getFirst().getId(), "$[0].id", phaseInstance.getId());
    }

    @Test
    void testFindByModuleId() throws Exception {
        when(bciPhaseInstanceRepository.findByModulesId(phaseInstance.getModules().getFirst().getId())).thenReturn(Collections.singletonList(phaseInstance));
        performGetRequest(URL + "/find/modules/" + phaseInstance.getModules().getFirst().getId(), "$[0].id", phaseInstance.getId());
    }

    private BehaviorChangeInterventionPhaseInstance phaseInstanceBuilder(BehaviorChangeInterventionBlockInstance currentBlock) {
        // Create a new mutable list instead of using List.of()
        List<BehaviorChangeInterventionBlockInstance> mutableBlocks = new ArrayList<>(blocks);
        mutableBlocks.add(currentBlock);

        BehaviorChangeInterventionPhaseInstance updated = new BehaviorChangeInterventionPhaseInstance(
                ExecutionStatus.IN_PROGRESS, currentBlock, mutableBlocks, modules, bciPhase);
        updated.setId(phaseInstance.getId());

        when(bciPhaseInstanceRepository.save(updated)).thenReturn(updated);
        when(bciPhaseInstanceRepository.findById(updated.getId())).thenReturn(Optional.of(updated));

        return updated;
    }

    @Test
    void testChangeCurrentBlock() throws Exception {
        BehaviorChangeInterventionBlockInstance currentBlock = new BehaviorChangeInterventionBlockInstance(
                ExecutionStatus.STALLED, TimeCycle.END, activities, bciBlock);
        currentBlock.setId(7L);
        when(bciBlockInstanceRepository.save(currentBlock)).thenReturn(currentBlock);

        BehaviorChangeInterventionPhaseInstance updated = phaseInstanceBuilder(currentBlock);

        performGetRequest(URL + "/changeCurrentBlock/phaseInstance", updated, "$.currentBlock.id",
                currentBlock.getId());

    }

    @Test
    void testChangeCurrentBlockWithPhaseIdAndCurrentBlock() throws Exception {
        BehaviorChangeInterventionBlockInstance currentBlock = new BehaviorChangeInterventionBlockInstance(
                ExecutionStatus.STALLED, TimeCycle.END, activities, bciBlock);
        currentBlock.setId(8L);
        when(bciBlockInstanceRepository.save(currentBlock)).thenReturn(currentBlock);

        BehaviorChangeInterventionPhaseInstance updated = phaseInstanceBuilder(currentBlock);

        performGetRequest(URL + "/changeCurrentBlock/" + updated.getId() + "/currentBlock", currentBlock,
                "$.currentBlock.id", currentBlock.getId());
    }

    private BehaviorChangeInterventionPhaseInstance phaseInstanceBuilder(BCIModuleInstance module) {
        BehaviorChangeInterventionBlockInstance currentBlock = new BehaviorChangeInterventionBlockInstance(
                ExecutionStatus.STALLED, TimeCycle.END, activities, bciBlock);
        currentBlock.setId(10L);
        when(bciBlockInstanceRepository.save(currentBlock)).thenReturn(currentBlock);

        List<BehaviorChangeInterventionBlockInstance> mutableBlocks = new ArrayList<>();
        mutableBlocks.add(currentBlock);

        List<BCIModuleInstance> moduleInstanceList = new ArrayList<>();
        moduleInstanceList.add(module);

        BehaviorChangeInterventionPhaseInstance updated = new BehaviorChangeInterventionPhaseInstance(
                ExecutionStatus.IN_PROGRESS, currentBlock, mutableBlocks, moduleInstanceList, bciPhase);
        updated.setId(phaseInstance.getId());

        when(bciPhaseInstanceRepository.save(updated)).thenReturn(updated);
        when(bciPhaseInstanceRepository.findById(updated.getId())).thenReturn(Optional.of(updated));

        return updated;
    }

    @Test
    void testChangeModuleStatusToInProgress () throws Exception {
        BCIModuleInstance moduleToInProgress = new BCIModuleInstance(ExecutionStatus.READY, OutcomeType.SUCCESSFUL, activities);
        moduleToInProgress.setId(11L);
        when(bciModuleInstanceRepository.save(moduleToInProgress)).thenReturn(moduleToInProgress);

        BehaviorChangeInterventionPhaseInstance updated = phaseInstanceBuilder(moduleToInProgress);

        performGetRequest(URL + "/changeModuleStatusToInProgress/" + updated.getId() + "/moduleToInProgress", moduleToInProgress,
                "$.modules[0].status", ExecutionStatus.IN_PROGRESS.toString());
    }

    @Test
    void testChangeModuleStatusToFinished () throws Exception {
        BCIModuleInstance moduleToFinished = new BCIModuleInstance(ExecutionStatus.READY, OutcomeType.SUCCESSFUL, activities);
        moduleToFinished.setId(13L);
        when(bciModuleInstanceRepository.save(moduleToFinished)).thenReturn(moduleToFinished);

        BehaviorChangeInterventionPhaseInstance updated = phaseInstanceBuilder(moduleToFinished);

        performGetRequest(URL + "/changeModuleStatusToFinished/" + updated.getId() + "/moduleToFinished", moduleToFinished,
                "$.modules[0].status", ExecutionStatus.FINISHED.toString());
    }

    @Test
    void testFindByBehaviorChangeInterventionPhaseId() throws Exception {
        when(bciPhaseInstanceRepository.findByBehaviorChangeInterventionPhaseId(phaseInstance.getBehaviorChangeInterventionPhase().getId()))
                .thenReturn(Collections.singletonList(phaseInstance));
        performGetRequest(URL + "/find/behaviorchangeinterventionphase/" + phaseInstance.getBehaviorChangeInterventionPhase().getId(),
                "$[0].behaviorChangeInterventionPhase.id", phaseInstance.getBehaviorChangeInterventionPhase().getId());
    }
}
