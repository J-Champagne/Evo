package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.*;
import ca.uqam.latece.evo.server.core.event.BCIActivityClientEvent;
import ca.uqam.latece.evo.server.core.event.BCIBlockInstanceClientEvent;
import ca.uqam.latece.evo.server.core.event.BCIInstanceClientEvent;
import ca.uqam.latece.evo.server.core.event.BCIPhaseInstanceClientEvent;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.request.BCIActivityInstanceRequest;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
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
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests methods found in InteractionInstanceService in a containerized setup.
 * @author Julien Champagne.
 */
@RecordApplicationEvents
@ApplicationScope
@ContextConfiguration(classes = {InteractionInstance.class, InteractionInstanceService.class})
public class InteractionInstanceServiceTest extends AbstractServiceTest {
    @Autowired
    RoleService roleService;

    @Autowired
    HealthCareProfessionalService healthCareProfessionalService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    InteractionInstanceService interactionInstanceService;

    @Autowired
    InteractionService interactionService;

    @Autowired
    private PatientMedicalFileService patientMedicalFileService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private BehaviorChangeInterventionBlockService behaviorChangeInterventionBlockService;

    @Autowired
    private BehaviorChangeInterventionPhaseService bciPhaseService;

    @Autowired
    private BehaviorChangeInterventionService behaviorChangeInterventionService;


    @Autowired
    private BehaviorChangeInterventionBlockInstanceService behaviorChangeInterventionBlockInstanceService;

    @Autowired
    private BehaviorChangeInterventionPhaseInstanceService behaviorChangeInterventionPhaseInstanceService;

    @Autowired
    private BehaviorChangeInterventionInstanceService behaviorChangeInterventionInstanceService;

    @Autowired
    private ApplicationEvents applicationEvents;

    private Role role;
    private HealthCareProfessional hcp;
    private Participant participant;
    private InteractionInstance interactionInstance;
    private BehaviorChangeInterventionBlockInstance blockInstance;
    private BehaviorChangeInterventionPhaseInstance phaseInstance;
    private BehaviorChangeInterventionInstance bciInstance;
    
    @BeforeEach
    public void setUp() {
        role = roleService.create(new Role("Admin"));
        hcp = healthCareProfessionalService.create(new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS","Healthcare"));

        participant = participantService.create(new Participant(role, hcp));
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        Interaction interaction = interactionService.create(new Interaction("Interaction", "Description",
                ActivityType.BCI_ACTIVITY, "precondition", "postcondition", InteractionMode.ASYNCHRONOUS,
                role, InteractionMedium.VIDEO));

        interactionInstance = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.READY, participants,
                interaction));

        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(interactionInstance);

        BehaviorChangeInterventionBlock bciBlock = behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("Intervention ENTRY", "Intervention EXIT"));

        blockInstance = behaviorChangeInterventionBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
                        DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), TimeCycle.MIDDLE, activities, bciBlock));

        List<BehaviorChangeInterventionBlockInstance> activitiesBlock = new ArrayList<>();
        activitiesBlock.add(blockInstance);

        List<BCIModuleInstance> modules = new ArrayList<>();

        BehaviorChangeInterventionPhase bciPhase = bciPhaseService.create(new BehaviorChangeInterventionPhase("Intervention ENTRY", "Intervention EXIT"));

        phaseInstance = behaviorChangeInterventionPhaseInstanceService
                .create(new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.READY, blockInstance, activitiesBlock, modules, bciPhase));

        List<BehaviorChangeInterventionPhaseInstance> phases = new ArrayList<>();
        phases.add(phaseInstance);

        BehaviorChangeIntervention behaviorChangeIntervention = behaviorChangeInterventionService.create(new BehaviorChangeIntervention("myProgram", "Entry Condition", "Exit Condition"));

        PatientMedicalFile pmf = patientMedicalFileService.create(new PatientMedicalFile("Healthy"));

        Patient patient = patientService.create(new Patient("Patient", "patient@gmail.com", "222-2222",
                "1901-01-01", "Participant", "3333 Street", pmf));

        bciInstance = behaviorChangeInterventionInstanceService.create(new BehaviorChangeInterventionInstance(ExecutionStatus.READY, patient, phaseInstance, phases, behaviorChangeIntervention));
    }

    @Test
    @Override
    public void testSave() {
        assert interactionInstance.getId() > 0;
    }

    @Test
    @Override
    public void testUpdate() {
        interactionInstance.setStatus(ExecutionStatus.FINISHED);
        InteractionInstance updated = interactionInstanceService.update(interactionInstance);
        assertEquals(ExecutionStatus.FINISHED, updated.getStatus());
    }

    @Test
    @Override
    void testFindById() {
        InteractionInstance found = interactionInstanceService.findById(interactionInstance.getId());
        assertEquals(interactionInstance.getId(), found.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        interactionInstanceService.deleteById(interactionInstance.getId());
        assertThrows(EntityNotFoundException.class, () -> interactionInstanceService.
                findById(interactionInstance.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        InteractionInstance interactionInstance2 = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.FINISHED,
                interactionInstance.getParticipants(), interactionInstance.getInteraction()));
        List<InteractionInstance> results = interactionInstanceService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void testFindByStatus() {
        List<InteractionInstance> results = interactionInstanceService.findByStatus(interactionInstance.getStatus());
        assertEquals(1, results.size());
    }

    @Test
    void testFindByEntryDate() {
        List<InteractionInstance> results = interactionInstanceService.findByEntryDate(interactionInstance.getEntryDate());
        assertEquals(1, results.size());
    }

    @Test
    void testFindByExitDate() {
        interactionInstance.setExitDate(LocalDate.now());
        List<InteractionInstance> results = interactionInstanceService.findByExitDate(interactionInstance.getExitDate());
        assertEquals(1, results.size());
    }

    @Test
    void testFindByParticipantsId() {
        List<InteractionInstance> bciActivities = interactionInstanceService.findByParticipantsId(participant.getId());

        assertEquals(1, bciActivities.getFirst().getParticipants().size());
        assertEquals(participant.getId(), bciActivities.getFirst().getParticipants().getFirst().getId());
    }

    @Test
    void testAddParticipantMoreThan3Fail() {
        Participant participant2 = new Participant(role, hcp);
        Participant participant3 = new Participant(role, hcp);
        Participant participant4 = new Participant(role, hcp);

        interactionInstance.addParticipant(participant2);
        interactionInstance.addParticipant(participant3);
        assertThrows(IndexOutOfBoundsException.class, () -> interactionInstance.addParticipant(participant4));
    }

    @Test
    void testHandleClientEventFinishFailNullId() {
        BCIActivityInstanceRequest request = new BCIActivityInstanceRequest(interactionInstance.getId(), blockInstance.getId(), null, null);

        assertThrows(IllegalArgumentException.class, () -> interactionInstanceService.validateClientEvent(ClientEvent.FINISH, request));
    }

    @Test
    void testHandleClientEventFinishSuccess() {
        interactionInstance.getBciActivity().setPostconditions("");

        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(ClientEvent.FINISH,
                interactionInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        assertTrue(response.isSuccess());
        assertFalse(response.getResponse().isEmpty());
    }

    @Test
    void handleBCIBlockInstanceClientEventsAllEntitiesFinished() {
        ClientEvent clientEvent = ClientEvent.FINISH;
        interactionInstance.getBciActivity().setPostconditions("");
        blockInstance.getBehaviorChangeInterventionBlock().setExitConditions("");
        phaseInstance.getBehaviorChangeInterventionPhase().setExitConditions("");
        bciInstance.getBehaviorChangeIntervention().setExitConditions("");

        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(clientEvent,
                interactionInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        assertEquals(ExecutionStatus.FINISHED, interactionInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, blockInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, bciInstance.getStatus());
        assertEquals(1, applicationEvents.stream(BCIBlockInstanceClientEvent.class).count());
        assertEquals(1, applicationEvents.stream(BCIPhaseInstanceClientEvent.class).count());
        assertEquals(1, applicationEvents.stream(BCIInstanceClientEvent.class).count());
    }

    @Test
    void handleBCIActivityClientEventInProgressNullNewActivityInstanceIdFail() {
        ClientEvent clientEvent = ClientEvent.IN_PROGRESS;

        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(clientEvent,
                interactionInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId(),
                null, null, null);
        assertThrows(IllegalArgumentException.class, () -> interactionInstanceService.handleClientEvent(bciActivityClientEvent));
    }

    @Test
    void handleBCIActivityClientEventInProgressSuccess() {
        ClientEvent clientEvent = ClientEvent.IN_PROGRESS;

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        Interaction interaction = interactionService.create(new Interaction("newInteraction", "Description",
                ActivityType.BCI_ACTIVITY, "precondition", "postcondition", InteractionMode.ASYNCHRONOUS,
                role, InteractionMedium.VIDEO));
        InteractionInstance newInteractionInstance = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.READY, participants,
                interaction));
        newInteractionInstance.getBciActivity().setPreconditions("");

        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(interactionInstance);

        BehaviorChangeInterventionBlock bciBlock = behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("Intervention ENTRY", "Intervention EXIT"));
        BehaviorChangeInterventionBlockInstance newBlockInstance = behaviorChangeInterventionBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
                        DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), TimeCycle.MIDDLE, activities, bciBlock));

        List<BehaviorChangeInterventionBlockInstance> activitiesBlock = new ArrayList<>();
        activitiesBlock.add(blockInstance);
        List<BCIModuleInstance> modules = new ArrayList<>();

        BehaviorChangeInterventionPhase bciPhase = bciPhaseService.create(new BehaviorChangeInterventionPhase("Intervention ENTRY", "Intervention EXIT"));
        BehaviorChangeInterventionPhaseInstance newPhaseInstance = behaviorChangeInterventionPhaseInstanceService
                .create(new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.READY, blockInstance, activitiesBlock, modules, bciPhase));

        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(clientEvent,
                interactionInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId(), newInteractionInstance.getId(),
                newBlockInstance.getId(), newBlockInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        assertEquals(ExecutionStatus.IN_PROGRESS, newInteractionInstance.getStatus());
        assertEquals(ExecutionStatus.SUSPENDED, interactionInstance.getStatus());
    }
}
