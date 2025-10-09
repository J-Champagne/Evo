package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.*;
import ca.uqam.latece.evo.server.core.event.BCIInstanceClientEvent;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.service.instance.*;
import ca.uqam.latece.evo.server.core.util.DateFormatter;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods found in BehaviorChangeInterventionInstanceService in a containerized setup.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@RecordApplicationEvents
@ApplicationScope
@ContextConfiguration(classes = {BehaviorChangeInterventionInstance.class, BehaviorChangeInterventionInstanceService.class})
public class BehaviorChangeInterventionInstanceServiceTest extends AbstractServiceTest {
    @Autowired
    private BehaviorChangeInterventionInstanceService bciInstanceService;

    @Autowired
    private BehaviorChangeInterventionPhaseInstanceService bciPhaseInstanceService;

    @Autowired
    private BehaviorChangeInterventionBlockInstanceService bciBlockInstanceService;

    @Autowired
    private BCIActivityInstanceService bciActivityInstanceService;

    @Autowired
    private BCIActivityService bciActivityService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private BCIModuleInstanceService bciModuleInstanceService;

    @Autowired
    private BehaviorChangeInterventionService behaviorChangeInterventionService;

    @Autowired
    private BehaviorChangeInterventionPhaseService behaviorChangeInterventionPhaseService;

    @Autowired
    private BehaviorChangeInterventionBlockService behaviorChangeInterventionBlockService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private BCIActivityInstance activityInstance;

    private BehaviorChangeInterventionBlockInstance blockInstance;

    private BehaviorChangeInterventionPhaseInstance phaseInstance;

    private BehaviorChangeInterventionInstance bciInstance;

    private BehaviorChangeIntervention behaviorChangeIntervention;

    private static final String INTERVENTION_NAME = "Behavior Change Intervention - BCI Instance Test";
    private static final String PHASE_ENTRY_CONDITION = "Intervention Phase ENTRY";
    private static final String PHASE_EXIT_CONDITION = "Intervention Phase EXIT";

    @Autowired
    private ApplicationEvents applicationEvents;

    @BeforeEach
    public void setUp() {
        // Creates a BehaviorChangeIntervention.
        behaviorChangeIntervention = behaviorChangeInterventionService.create(new BehaviorChangeIntervention(INTERVENTION_NAME,
                "entry", "exit condition"));
        // Creates a BehaviorChangeInterventionPhase.
        BehaviorChangeInterventionPhase behaviorChangeInterventionPhase = behaviorChangeInterventionPhaseService.create( new BehaviorChangeInterventionPhase(PHASE_ENTRY_CONDITION,
                PHASE_EXIT_CONDITION));
        behaviorChangeInterventionPhase.setBehaviorChangeIntervention(behaviorChangeIntervention);

        Role role = roleService.create(new Role("Administrator"));

        Patient patient = patientService.create(new Patient("Bob", "bob@gmail.com",
                "222-2222", "1 January 1970", "Student", "1234 Street"));

        Participant participant = participantService.create(new Participant(role, patient));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivity bciActivity = bciActivityService.create(new BCIActivity("Programming", "Description", ActivityType.BCI_ACTIVITY,
                "ENTRY_CONDITION", "EXIT_CONDITION"));

        activityInstance = bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.IN_PROGRESS, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"),
                participants, bciActivity));
        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(activityInstance);

        BCIModuleInstance moduleInstance = bciModuleInstanceService.create(new BCIModuleInstance(ExecutionStatus.STALLED,
                OutcomeType.SUCCESSFUL, activities));
        List<BCIModuleInstance> modules = new ArrayList<>();
        modules.add(moduleInstance);

        BehaviorChangeInterventionBlock bciBlock = behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("ENTRY_CONDITION", "EXIT_CONDITION"));

        blockInstance = bciBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.STALLED, TimeCycle.BEGINNING, activities, bciBlock));
        List<BehaviorChangeInterventionBlockInstance> blocks = new ArrayList<>();
        blocks.add(blockInstance);

        phaseInstance = bciPhaseInstanceService.create
                (new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.STALLED, blockInstance, blocks, modules, behaviorChangeInterventionPhase));
        List<BehaviorChangeInterventionPhaseInstance> phases = new ArrayList<>();
        phases.add(phaseInstance);

        bciInstance = bciInstanceService.create(new BehaviorChangeInterventionInstance(ExecutionStatus.READY, patient,
                phaseInstance, phases, behaviorChangeIntervention));
    }
    @Test
    @Override
    public void testSave() {
        assert bciInstance.getId() > 0;
    }

    @Test
    @Override
    public void testUpdate() {
        bciInstance.getPatient().setOccupation("Professional");
        BehaviorChangeInterventionInstance updated = bciInstanceService.update(bciInstance);
        assertEquals("Professional", updated.getPatient().getOccupation());
    }

    @Test
    @Override
    void testFindById() {
        BehaviorChangeInterventionInstance found = bciInstanceService.findById(bciInstance.getId());
        assertEquals(bciInstance.getId(), found.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        bciInstanceService.deleteById(bciInstance.getId());
        assertThrows(EntityNotFoundException.class, () -> bciInstanceService.
                findById(bciInstance.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        List<BehaviorChangeInterventionPhaseInstance> phases = new ArrayList<>(bciInstance.getActivities());
        bciInstanceService.create(new BehaviorChangeInterventionInstance(ExecutionStatus.FINISHED, bciInstance.getPatient(),
                bciInstance.getCurrentPhase(), phases, bciInstance.getBehaviorChangeIntervention()));
        List<BehaviorChangeInterventionInstance> results = bciInstanceService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void testFindByCurrentPatientId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByPatientId(bciInstance.getPatient().getId());

        assertFalse(result.isEmpty());
        assertEquals(bciInstance.getId(), result.getFirst().getId());
    }

    @Test
    void testFindByCurrentPhaseId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByCurrentPhaseId(bciInstance.getCurrentPhase().getId());

        assertFalse(result.isEmpty());
        assertEquals(bciInstance.getId(), result.getFirst().getId());
    }

    @Test
    void testFindByPhasesId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByActivitiesId(bciInstance
                .getActivities().getFirst().getId());

        assertFalse(result.isEmpty());
        assertEquals(bciInstance.getId(), result.getFirst().getId());
    }

    @Test
    void testFindByIdAndCurrentPhaseId() {
        BehaviorChangeInterventionInstance result = bciInstanceService.findByIdAndCurrentPhaseId(bciInstance.getId(),
                bciInstance.getCurrentPhase().getId() );
        assertEquals(bciInstance.getId(), result.getId());
        assertEquals(bciInstance.getCurrentPhase().getId(), result.getCurrentPhase().getId());
    }

    /* Disabled because of how change to how we update our BCI entities from the frontend
    @Test
    void testChangeCurrentPhase() {
        Role role = roleService.create(new Role("Participant"));

        Patient patient = patientService.create(new Patient("Marie", "marie@gmail.com",
                "77777", "10 April 1990", "Student", "18 Str"));

        Participant participant = participantService.create(new Participant(role, patient));
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivity bciActivity = bciActivityService.create(new BCIActivity("Programming2", "Description",
                ActivityType.BCI_ACTIVITY, "ENTRY_CONDITION", "EXIT_CONDITION"));

        BCIActivityInstance activityInstance = bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.IN_PROGRESS, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2025/07/30"),
                participants, bciActivity));
        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(activityInstance);

        BCIModuleInstance moduleInstance = bciModuleInstanceService.create(new BCIModuleInstance(ExecutionStatus.STALLED,
                OutcomeType.SUCCESSFUL, activities));
        List<BCIModuleInstance> modules = new ArrayList<>();
        modules.add(moduleInstance);

        BehaviorChangeInterventionBlock bciBlock = behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("ENTRY_CONDITION", "EXIT_CONDITION"));

        BehaviorChangeInterventionBlockInstance blockInstance = bciBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.STALLED, TimeCycle.BEGINNING, activities, bciBlock));
        List<BehaviorChangeInterventionBlockInstance> blocks = new ArrayList<>();
        blocks.add(blockInstance);

        BehaviorChangeInterventionPhase behaviorChangeInterventionPhase = behaviorChangeInterventionPhaseService.
                create (new BehaviorChangeInterventionPhase(PHASE_ENTRY_CONDITION, PHASE_EXIT_CONDITION));

        BehaviorChangeInterventionPhaseInstance phaseInstance = bciPhaseInstanceService.create
                (new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.STALLED, blockInstance, blocks, modules, behaviorChangeInterventionPhase));

        // Update the current phase.
        BehaviorChangeInterventionInstance updated = bciInstanceService.changeCurrentPhase(bciInstance.getId(), phaseInstance);

        // Check the current phase update.
        assertEquals(phaseInstance.getId(), updated.getCurrentPhase().getId());

        // Test the event (BCIPhaseInstanceEvent) publication.
        assertEquals(1, applicationEvents.stream(BCIPhaseInstanceEvent.class).
                filter(event -> event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                        event.getEvoModel().getStatus().equals(ExecutionStatus.FINISHED)).count());
    }*/

    @Test
    void testFindByBehaviorChangeInterventionId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByBehaviorChangeInterventionId(
                bciInstance.getBehaviorChangeIntervention().getId());

        assertEquals(bciInstance.getId(), result.getFirst().getId());
        assertEquals(bciInstance.getBehaviorChangeIntervention().getId(), result.getFirst().getBehaviorChangeIntervention().getId());
    }

    @Test
    void testFindByStatusAndPatientId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusAndPatientId(ExecutionStatus.READY,
                bciInstance.getPatient().getId());

        assertEquals(bciInstance.getId(), result.getFirst().getId());
        assertEquals(bciInstance.getStatus(), result.getFirst().getStatus());
        assertEquals(bciInstance.getPatient().getId(), result.getFirst().getPatient().getId());
    }

    @Test
    void testFindByStatusReadyAndPatientId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusReadyAndPatientId(
                bciInstance.getPatient().getId());

        assertEquals(bciInstance.getId(), result.getFirst().getId());
    }

    @Test
    void testFindByStatusInProgressAndPatientId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusInProgressAndPatientId(
                bciInstance.getPatient().getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByStatusFinishedAndPatientId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusFinishedAndPatientId(
                bciInstance.getPatient().getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByStatusUnknowAndPatientId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusUnknowAndPatientId(
                bciInstance.getPatient().getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByStatusStalledAndPatientId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusStalledAndPatientId(
                bciInstance.getPatient().getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByStatusSuspendedAndPatientId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusSuspendedAndPatientId(
                bciInstance.getPatient().getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByStatusInProgressAndPatientIdAndCurrentPhaseId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusInProgressAndPatientIdAndCurrentPhaseId(
                bciInstance.getPatient().getId(), bciInstance.getCurrentPhase().getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByStatusReadyAndPatientIdAndCurrentPhaseId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusReadyAndPatientIdAndCurrentPhaseId(
                bciInstance.getPatient().getId(), bciInstance.getCurrentPhase().getId());
        assertEquals(bciInstance.getId(), result.getFirst().getId());
        assertEquals(bciInstance.getStatus(), result.getFirst().getStatus());
        assertEquals(bciInstance.getPatient().getId(), result.getFirst().getPatient().getId());
        assertEquals(bciInstance.getCurrentPhase().getId(), result.getFirst().getCurrentPhase().getId());
    }

    @Test
    void testFindByStatusFinishedAndPatientIdAndCurrentPhaseId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusFinishedAndPatientIdAndCurrentPhaseId(
                bciInstance.getPatient().getId(), bciInstance.getCurrentPhase().getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByStatusSuspendedAndPatientIdAndCurrentPhaseId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusSuspendedAndPatientIdAndCurrentPhaseId(
                bciInstance.getPatient().getId(), bciInstance.getCurrentPhase().getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByStatusStalledAndPatientIdAndCurrentPhaseId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusStalledAndPatientIdAndCurrentPhaseId(
                bciInstance.getPatient().getId(), bciInstance.getCurrentPhase().getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByStatusUnknowAndPatientIdAndCurrentPhaseId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusUnknowAndPatientIdAndCurrentPhaseId(
                bciInstance.getPatient().getId(), bciInstance.getCurrentPhase().getId());

        assertTrue(result.isEmpty());
    }

    @Test
    void findByStatusAndPatientIdAndCurrentPhaseId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusAndPatientIdAndCurrentPhaseId(ExecutionStatus.READY,
                bciInstance.getPatient().getId(), bciInstance.getCurrentPhase().getId());

        assertEquals(bciInstance.getId(), result.getFirst().getId());
        assertEquals(bciInstance.getStatus(), result.getFirst().getStatus());
        assertEquals(bciInstance.getPatient().getId(), result.getFirst().getPatient().getId());
        assertEquals(bciInstance.getCurrentPhase().getId(), result.getFirst().getCurrentPhase().getId());
    }

    @Test
    void testFindByStatusAndPatientIdAndCurrentPhaseIdAndCurrentPhaseStatus() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByStatusAndPatientIdAndCurrentPhaseIdAndCurrentPhaseStatus(
                ExecutionStatus.READY, bciInstance.getPatient().getId(), bciInstance.getCurrentPhase().getId(), ExecutionStatus.STALLED);

        assertEquals(bciInstance.getId(), result.getFirst().getId());
        assertEquals(bciInstance.getStatus(), result.getFirst().getStatus());
        assertEquals(bciInstance.getPatient().getId(), result.getFirst().getPatient().getId());
        assertEquals(bciInstance.getCurrentPhase().getId(), result.getFirst().getCurrentPhase().getId());
        assertEquals(bciInstance.getCurrentPhase().getStatus(), result.getFirst().getCurrentPhase().getStatus());
    }

    @Test
    void testFindByIdAndStatusAndPatientId() {
        BehaviorChangeInterventionInstance result = bciInstanceService.findByIdAndStatusAndPatientId(bciInstance.getId(),
                ExecutionStatus.READY, bciInstance.getPatient().getId());

        assertEquals(bciInstance.getId(), result.getId());
        assertEquals(bciInstance.getStatus(), result.getStatus());
        assertEquals(bciInstance.getPatient().getId(), result.getPatient().getId());
    }

    @Test
    void testFindByIdAndStatusAndPatient() {
        BehaviorChangeInterventionInstance result = bciInstanceService.findByIdAndStatusAndPatient(bciInstance.getId(),
                ExecutionStatus.READY, bciInstance.getPatient());

        assertEquals(bciInstance.getId(), result.getId());
        assertEquals(bciInstance.getStatus(), result.getStatus());
        assertEquals(bciInstance.getPatient().getId(), result.getPatient().getId());
    }

    @Test
    void testFindByIdAndPatient() {
        BehaviorChangeInterventionInstance result = bciInstanceService.findByIdAndPatient(bciInstance.getId(),
                bciInstance.getPatient());

        assertEquals(bciInstance.getId(), result.getId());
        assertEquals(bciInstance.getPatient().getId(), result.getPatient().getId());
    }

    @Test
    void testFindByIdAndPatientId() {
        BehaviorChangeInterventionInstance result = bciInstanceService.findByIdAndPatientId(bciInstance.getId(),
                bciInstance.getPatient().getId());

        assertEquals(bciInstance.getId(), result.getId());
        assertEquals(bciInstance.getPatient().getId(), result.getPatient().getId());
    }

    @Test
    void testFindByIdAndStatusAndPatientIdAndCurrentPhaseId() {
        BehaviorChangeInterventionInstance result = bciInstanceService.findByIdAndStatusAndPatientIdAndCurrentPhaseId(bciInstance.getId(),
                ExecutionStatus.READY, bciInstance.getPatient().getId(), bciInstance.getCurrentPhase().getId());

        assertEquals(bciInstance.getId(), result.getId());
        assertEquals(bciInstance.getStatus(), result.getStatus());
        assertEquals(bciInstance.getPatient().getId(), result.getPatient().getId());
        assertEquals(bciInstance.getCurrentPhase().getId(), result.getCurrentPhase().getId());
    }

    @Test
    void testFindByIdAndStatusAndPatientAndCurrentPhaseId() {
        BehaviorChangeInterventionInstance result = bciInstanceService.findByIdAndStatusAndPatientAndCurrentPhaseId(bciInstance.getId(),
                ExecutionStatus.READY, bciInstance.getPatient(), bciInstance.getCurrentPhase().getId());

        assertEquals(bciInstance.getId(), result.getId());
        assertEquals(bciInstance.getStatus(), result.getStatus());
        assertEquals(bciInstance.getPatient().getId(), result.getPatient().getId());
        assertEquals(bciInstance.getCurrentPhase().getId(), result.getCurrentPhase().getId());
    }

    @Test
    void testHandleBCIInstanceClientEventFail() {
        activityInstance.setStatus(ExecutionStatus.FINISHED);
        blockInstance.setStatus(ExecutionStatus.FINISHED);
        phaseInstance.setStatus(ExecutionStatus.FINISHED);

        BCIInstanceClientEvent instanceClientEvent = new BCIInstanceClientEvent(ClientEvent.FINISH,
                new ClientEventResponse(), bciInstance.getId(), phaseInstance);

        applicationEventPublisher.publishEvent(instanceClientEvent);

        assertNotEquals(ExecutionStatus.FINISHED, bciInstance.getStatus());
        assertEquals(ExecutionStatus.READY, bciInstance.getStatus());
    }

    @Test
    void testHandleBCIInstanceClientEvent() {
        activityInstance.setStatus(ExecutionStatus.FINISHED);
        blockInstance.setStatus(ExecutionStatus.FINISHED);
        phaseInstance.setStatus(ExecutionStatus.FINISHED);

        BehaviorChangeInterventionPhaseInstance phaseInstance2 = bciPhaseInstanceService.create(new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.READY,
                phaseInstance.getCurrentBlock(), phaseInstance.getActivities(), phaseInstance.getModules(), phaseInstance.getBehaviorChangeInterventionPhase()));

        bciInstance.addActivity(phaseInstance2);
        bciInstance.getBehaviorChangeIntervention().setExitConditions("");

        BCIInstanceClientEvent instanceClientEvent = new BCIInstanceClientEvent(ClientEvent.FINISH,
                new ClientEventResponse(), bciInstance.getId(), phaseInstance);
        applicationEventPublisher.publishEvent(instanceClientEvent);

        assertEquals(ExecutionStatus.FINISHED, bciInstance.getStatus());
        assertEquals(phaseInstance2.getId(), bciInstance.getCurrentPhase().getId());
    }

}