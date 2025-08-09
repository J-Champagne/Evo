package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ChangeAspect;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.event.BCIInstanceEvent;
import ca.uqam.latece.evo.server.core.event.BCIPhaseInstanceEvent;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.service.instance.*;
import ca.uqam.latece.evo.server.core.util.DateFormatter;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ParticipantService participantService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private BCIModuleInstanceService bciModuleInstanceService;

    private BehaviorChangeInterventionInstance bciInstance;

    @Autowired
    private BehaviorChangeInterventionService behaviorChangeInterventionService;

    @Autowired
    private BehaviorChangeInterventionPhaseService behaviorChangeInterventionPhaseService;

    private static final String INTERVENTION_NAME = "Behavior Change Intervention - BCI Instance Test";
    private static final String PHASE_ENTRY_CONDITION = "Intervention Phase ENTRY";
    private static final String PHASE_EXIT_CONDITION = "Intervention Phase EXIT";

    @Autowired
    private ApplicationEvents applicationEvents;

    @BeforeEach
    public void setUp() {
        Role role = roleService.create(new Role("Administrator"));
        Patient patient = patientService.create(new Patient("Bob", "bob@gmail.com",
                "222-2222", "1 January 1970", "Student", "1234 Street"));
        Participant participant = participantService.create(new Participant(role, patient));
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivityInstance activityInstance = bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.IN_PROGRESS, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"),
                participants));
        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(activityInstance);

        BCIModuleInstance moduleInstance = bciModuleInstanceService.create(new BCIModuleInstance(ExecutionStatus.STALLED,
                OutcomeType.SUCCESSFUL, activities));
        List<BCIModuleInstance> modules = new ArrayList<>();
        modules.add(moduleInstance);

        BehaviorChangeInterventionBlockInstance blockInstance = bciBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.STALLED, TimeCycle.BEGINNING, activities));
        List<BehaviorChangeInterventionBlockInstance> blocks = new ArrayList<>();
        blocks.add(blockInstance);

        BehaviorChangeInterventionPhaseInstance phaseInstance = bciPhaseInstanceService.create
                (new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.STALLED, blockInstance, blocks, modules));
        List<BehaviorChangeInterventionPhaseInstance> phases = new ArrayList<>();
        phases.add(phaseInstance);

        // Creates a BehaviorChangeIntervention.
        BehaviorChangeIntervention behaviorChangeIntervention = new BehaviorChangeIntervention(INTERVENTION_NAME);
        behaviorChangeInterventionService.create(behaviorChangeIntervention);

        // Creates a BehaviorChangeInterventionPhase.
        BehaviorChangeInterventionPhase behaviorChangeInterventionPhase = new BehaviorChangeInterventionPhase(PHASE_ENTRY_CONDITION,
                PHASE_EXIT_CONDITION);
        behaviorChangeInterventionPhase.setBehaviorChangeIntervention(behaviorChangeIntervention);
        behaviorChangeInterventionPhaseService.create(behaviorChangeInterventionPhase);

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

    @Test
    void testChangeCurrentPhase() {
        Role role = roleService.create(new Role("Participant"));
        Patient patient = patientService.create(new Patient("Marie", "marie@gmail.com",
                "77777", "10 April 1990", "Student", "18 Str"));
        Participant participant = participantService.create(new Participant(role, patient));
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivityInstance activityInstance = bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.IN_PROGRESS, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2025/07/30"),
                participants));
        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(activityInstance);

        BCIModuleInstance moduleInstance = bciModuleInstanceService.create(new BCIModuleInstance(ExecutionStatus.STALLED,
                OutcomeType.SUCCESSFUL, activities));
        List<BCIModuleInstance> modules = new ArrayList<>();
        modules.add(moduleInstance);

        BehaviorChangeInterventionBlockInstance blockInstance = bciBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.STALLED, TimeCycle.BEGINNING, activities));
        List<BehaviorChangeInterventionBlockInstance> blocks = new ArrayList<>();
        blocks.add(blockInstance);

        BehaviorChangeInterventionPhaseInstance phaseInstance = bciPhaseInstanceService.create
                (new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.STALLED, blockInstance, blocks, modules));

        // Update the current phase.
        BehaviorChangeInterventionInstance updated = bciInstanceService.changeCurrentPhase(bciInstance.getId(), phaseInstance);

        // Check the current phase update.
        assertEquals(phaseInstance.getId(), updated.getCurrentPhase().getId());

        // Test the event (BCIPhaseInstanceEvent) publication.
        assertEquals(1, applicationEvents.stream(BCIPhaseInstanceEvent.class).
                filter(event -> event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                        event.getEvoModel().getStatus().equals(ExecutionStatus.FINISHED)).count());
    }

    @Test
    void testPublishEvent() {
        bciInstance.getPatient().setOccupation("Professor");
        BehaviorChangeInterventionInstance updated = bciInstanceService.update(bciInstance);

        // Check the current phase update.
        assertEquals(bciInstance.getCurrentPhase().getId(), updated.getCurrentPhase().getId());

        // Test event publication.
        assertEquals(1, applicationEvents.stream(BCIInstanceEvent.class).
                filter(event -> event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                        event.getCurrentPhase().equals(bciInstance.getCurrentPhase())).count());
    }

    @Test
    void testFindByBehaviorChangeInterventionId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByBehaviorChangeInterventionId(bciInstance.getBehaviorChangeIntervention().getId());
        assertEquals(bciInstance.getId(), result.getFirst().getId());
        assertEquals(bciInstance.getBehaviorChangeIntervention().getId(), result.getFirst().getBehaviorChangeIntervention().getId());
    }
}
