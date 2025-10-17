package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.*;
import ca.uqam.latece.evo.server.core.event.*;
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
    private BehaviorChangeInterventionPhaseService behaviorChangeInterventionPhaseService;

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

        interactionInstance = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.IN_PROGRESS, participants,
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

        BehaviorChangeInterventionPhase bciPhase = behaviorChangeInterventionPhaseService.create(new BehaviorChangeInterventionPhase("Intervention ENTRY", "Intervention EXIT"));

        phaseInstance = behaviorChangeInterventionPhaseInstanceService
                .create(new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.IN_PROGRESS, blockInstance, activitiesBlock, modules, bciPhase));

        List<BehaviorChangeInterventionPhaseInstance> phases = new ArrayList<>();
        phases.add(phaseInstance);

        BehaviorChangeIntervention behaviorChangeIntervention = behaviorChangeInterventionService.create(new BehaviorChangeIntervention("myProgram", "Entry Condition", "Exit Condition"));

        PatientMedicalFile pmf = patientMedicalFileService.create(new PatientMedicalFile("Healthy"));

        Patient patient = patientService.create(new Patient("Patient", "patient@gmail.com", "222-2222",
                "1901-01-01", "Participant", "3333 Street", pmf));

        bciInstance = behaviorChangeInterventionInstanceService.create(new BehaviorChangeInterventionInstance(ExecutionStatus.IN_PROGRESS, patient, phaseInstance, phases, behaviorChangeIntervention));
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
    void testAllEntitiesFinished() {
        ClientEvent clientEvent = ClientEvent.FINISH;

        //Set ExitConditions to blank in order to pass checkExitConditions()
        interactionInstance.getBciActivity().setPostconditions("");
        blockInstance.getBehaviorChangeInterventionBlock().setExitConditions("");
        phaseInstance.getBehaviorChangeInterventionPhase().setExitConditions("");
        bciInstance.getBehaviorChangeIntervention().setExitConditions("");

        //Update the entities
        interactionInstanceService.update(interactionInstance);
        behaviorChangeInterventionBlockInstanceService.update(blockInstance);
        behaviorChangeInterventionPhaseInstanceService.update(phaseInstance);
        behaviorChangeInterventionInstanceService.update(bciInstance);

        //Create and publish the ClientEvent
        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(clientEvent,
                interactionInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        //Tests
        assertEquals(ExecutionStatus.FINISHED, interactionInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, blockInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, bciInstance.getStatus());
        assertEquals(1, applicationEvents.stream(BCIBlockInstanceClientEvent.class).count());
        assertEquals(1, applicationEvents.stream(BCIPhaseInstanceClientEvent.class).count());
        assertEquals(1, applicationEvents.stream(BCIInstanceClientEvent.class).count());
        assertTrue(response.isSuccess());
    }

    @Test
    void testOnlyOnePhaseFinished() {
        ClientEvent clientEvent = ClientEvent.FINISH;

        //Create new entities
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        Interaction interaction2 = interactionService.create(new Interaction("newInteraction2", "Description",
                ActivityType.BCI_ACTIVITY, "precondition", "postcondition", InteractionMode.ASYNCHRONOUS,
                role, InteractionMedium.VIDEO));
        InteractionInstance interactionInstance2 = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.READY, participants,
                interaction2));
        Interaction interaction3 = interactionService.create(new Interaction("newInteraction3", "Description",
                ActivityType.BCI_ACTIVITY, "precondition", "postcondition", InteractionMode.ASYNCHRONOUS,
                role, InteractionMedium.VIDEO));
        InteractionInstance interactionInstance3 = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.READY, participants,
                interaction3));

        List<BCIActivityInstance> bciActivityList = new ArrayList<>();
        bciActivityList.add(interactionInstance2);
        bciActivityList.add(interactionInstance3);

        BehaviorChangeInterventionBlock behaviorChangeInterventionBlock2 = behaviorChangeInterventionBlockService.create(
                new BehaviorChangeInterventionBlock("entry Conditions", "Exit conditions"));
        BehaviorChangeInterventionBlockInstance behaviorChangeInterventionBlockInstance2 = behaviorChangeInterventionBlockInstanceService.create(
                new BehaviorChangeInterventionBlockInstance(ExecutionStatus.READY, TimeCycle.BEGINNING, bciActivityList, behaviorChangeInterventionBlock2));

        List<BehaviorChangeInterventionBlockInstance> bciBlockList = new ArrayList<>();
        bciBlockList.add(behaviorChangeInterventionBlockInstance2);

        List<BCIModuleInstance> moduleList = new ArrayList<>();

        BehaviorChangeInterventionPhase behaviorChangeInterventionPhase2 = behaviorChangeInterventionPhaseService.create(new BehaviorChangeInterventionPhase(
                "entry Conditions", "Exit conditions"));
        BehaviorChangeInterventionPhaseInstance behaviorChangeInterventionPhaseInstance2 = behaviorChangeInterventionPhaseInstanceService.create(
                new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.READY, behaviorChangeInterventionBlockInstance2, bciBlockList, moduleList, behaviorChangeInterventionPhase2));

        bciInstance.addActivity(behaviorChangeInterventionPhaseInstance2);

        //Set ExitConditions to blank in order to pass checkExitConditions() and checkEntryConditions()
        interactionInstance.getBciActivity().setPostconditions("");
        blockInstance.getBehaviorChangeInterventionBlock().setExitConditions("");
        phaseInstance.getBehaviorChangeInterventionPhase().setExitConditions("");
        interactionInstance2.getBciActivity().setPreconditions("");
        behaviorChangeInterventionBlockInstance2.getBehaviorChangeInterventionBlock().setEntryConditions("");
        behaviorChangeInterventionPhaseInstance2.getBehaviorChangeInterventionPhase().setEntryConditions("");

        //Update the entities
        interactionInstanceService.update(interactionInstance);
        interactionInstanceService.update(interactionInstance2);
        interactionInstanceService.update(interactionInstance3);
        behaviorChangeInterventionBlockInstanceService.update(blockInstance);
        behaviorChangeInterventionBlockInstanceService.update(behaviorChangeInterventionBlockInstance2);
        behaviorChangeInterventionPhaseInstanceService.update(phaseInstance);
        behaviorChangeInterventionPhaseInstanceService.update(behaviorChangeInterventionPhaseInstance2);
        behaviorChangeInterventionInstanceService.update(bciInstance);

        //Create and publish the ClientEvent
        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(clientEvent,
                interactionInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        //Tests
        assertEquals(ExecutionStatus.FINISHED, interactionInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, blockInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getStatus());

        assertEquals(ExecutionStatus.IN_PROGRESS, behaviorChangeInterventionPhaseInstance2.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, behaviorChangeInterventionBlockInstance2.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, interactionInstance2.getStatus());
        assertEquals(ExecutionStatus.READY, interactionInstance3.getStatus());

        assertEquals(1, applicationEvents.stream(BCIBlockInstanceClientEvent.class).count());
        assertEquals(1, applicationEvents.stream(BCIPhaseInstanceClientEvent.class).count());
        assertEquals(1, applicationEvents.stream(BCIInstanceClientEvent.class).count());
        assertEquals(1, applicationEvents.stream(BCIInstanceToPhaseCheckEntryConditionsClientEvent.class).count());
        assertEquals(1, applicationEvents.stream(BCIPhaseInstanceToBlockCheckEntryConditionClientEvent.class).count());
        assertEquals(2, applicationEvents.stream(BCIBlockInstanceToActivityCheckEntryConditionsClientEvent.class).count());
        assertTrue(response.isSuccess());
    }

    @Test
    void handleBCIBlockInstanceClientEventsOneActivityInBlockFinished() {
        ClientEvent clientEvent = ClientEvent.FINISH;

        //Create new entities
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        Interaction interaction2 = interactionService.create(new Interaction("newInteraction2", "Description",
                ActivityType.BCI_ACTIVITY, "precondition", "postcondition", InteractionMode.ASYNCHRONOUS,
                role, InteractionMedium.VIDEO));
        InteractionInstance interactionInstance2 = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.READY, participants,
                interaction2));
        Interaction interaction3 = interactionService.create(new Interaction("newInteraction3", "Description",
                ActivityType.BCI_ACTIVITY, "precondition", "postcondition", InteractionMode.ASYNCHRONOUS,
                role, InteractionMedium.VIDEO));
        InteractionInstance interactionInstance3 = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.READY, participants,
                interaction3));
        Interaction interaction4 = interactionService.create(new Interaction("newInteraction4", "Description",
                ActivityType.BCI_ACTIVITY, "precondition", "postcondition", InteractionMode.ASYNCHRONOUS,
                role, InteractionMedium.VIDEO));
        InteractionInstance interactionInstance4 = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.FINISHED, participants,
                interaction4));

        blockInstance.addActivity(interactionInstance2);
        blockInstance.addActivity(interactionInstance3);
        blockInstance.addActivity(interactionInstance4);


        //Set ExitConditions to blank in order to pass checkExitConditions()
        interactionInstance.getBciActivity().setPostconditions("");
        interactionInstance2.getBciActivity().setPreconditions("");

        //Update the entities
        interactionInstanceService.update(interactionInstance);
        interactionInstanceService.update(interactionInstance2);
        behaviorChangeInterventionBlockInstanceService.update(blockInstance);

        //Create and publish the ClientEvent
        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(clientEvent,
                interactionInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        //Tests
        assertEquals(ExecutionStatus.FINISHED, interactionInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, interactionInstance2.getStatus());
        assertEquals(ExecutionStatus.READY, interactionInstance3.getStatus());
        assertEquals(ExecutionStatus.FINISHED, interactionInstance4.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, blockInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getStatus());
        assertEquals(1, applicationEvents.stream(BCIBlockInstanceClientEvent.class).count());
        assertEquals(0, applicationEvents.stream(BCIPhaseInstanceClientEvent.class).count());
        assertEquals(0, applicationEvents.stream(BCIInstanceClientEvent.class).count());
        assertTrue(response.isSuccess());
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

        //Create new entities to simulate selecting a new Activity in a different Block and Phase
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        Interaction interaction = interactionService.create(new Interaction("newInteraction", "Description",
                ActivityType.BCI_ACTIVITY, "precondition", "postcondition", InteractionMode.ASYNCHRONOUS,
                role, InteractionMedium.VIDEO));
        InteractionInstance newInteractionInstance = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.READY, participants,
                interaction));

        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(interactionInstance);

        BehaviorChangeInterventionBlock bciBlock = behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("Intervention ENTRY", "Intervention EXIT"));
        BehaviorChangeInterventionBlockInstance newBlockInstance = behaviorChangeInterventionBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.READY, LocalDate.now(),
                        DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), TimeCycle.MIDDLE, activities, bciBlock));

        List<BehaviorChangeInterventionBlockInstance> activitiesBlock = new ArrayList<>();
        activitiesBlock.add(blockInstance);
        List<BCIModuleInstance> modules = new ArrayList<>();

        BehaviorChangeInterventionPhase bciPhase = behaviorChangeInterventionPhaseService.create(new BehaviorChangeInterventionPhase("Intervention ENTRY", "Intervention EXIT"));
        BehaviorChangeInterventionPhaseInstance newPhaseInstance = behaviorChangeInterventionPhaseInstanceService
                .create(new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.READY, blockInstance, activitiesBlock, modules, bciPhase));

        bciInstance.addActivity(newPhaseInstance);

        //Set EntryConditions to blank in order to pass checkEntryConditions()
        newInteractionInstance.getBciActivity().setPreconditions("");
        newBlockInstance.getBehaviorChangeInterventionBlock().setEntryConditions("");
        newPhaseInstance.getBehaviorChangeInterventionPhase().setEntryConditions("");

        //Update the entities
        interactionInstanceService.update(newInteractionInstance);
        behaviorChangeInterventionBlockInstanceService.update(newBlockInstance);
        behaviorChangeInterventionPhaseInstanceService.update(newPhaseInstance);
        behaviorChangeInterventionInstanceService.update(bciInstance);

        //Create and publish the ClientEvent
        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(clientEvent,
                interactionInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId(), newInteractionInstance.getId(),
                newBlockInstance.getId(), newPhaseInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        //Tests
        assertEquals(ExecutionStatus.SUSPENDED, interactionInstance.getStatus());
        assertEquals(ExecutionStatus.SUSPENDED, blockInstance.getStatus());
        assertEquals(ExecutionStatus.SUSPENDED, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, newInteractionInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, newBlockInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, newPhaseInstance.getStatus());
        assertEquals(newPhaseInstance.getCurrentBlock().getId(), newBlockInstance.getId());
        assertNotEquals(newPhaseInstance.getCurrentBlock().getId(), blockInstance.getId());
        assertEquals(bciInstance.getCurrentPhase().getId(), newPhaseInstance.getId());
        assertNotEquals(bciInstance.getCurrentPhase().getId(), phaseInstance.getId());
        assertTrue(response.isSuccess());
    }

    @Test
    void handleBCIActivityClientEventInProgressSuccessSamePhase() {
        ClientEvent clientEvent = ClientEvent.IN_PROGRESS;

        //Create new entities to simulate selecting a new Activity in a different Block and Phase
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        Interaction interaction = interactionService.create(new Interaction("newInteraction", "Description",
                ActivityType.BCI_ACTIVITY, "precondition", "postcondition", InteractionMode.ASYNCHRONOUS,
                role, InteractionMedium.VIDEO));
        InteractionInstance newInteractionInstance = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.READY, participants,
                interaction));

        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(interactionInstance);

        BehaviorChangeInterventionBlock bciBlock = behaviorChangeInterventionBlockService.create(
                new BehaviorChangeInterventionBlock("Intervention ENTRY", "Intervention EXIT"));
        BehaviorChangeInterventionBlockInstance newBlockInstance = behaviorChangeInterventionBlockInstanceService.create
                (new BehaviorChangeInterventionBlockInstance(ExecutionStatus.READY, LocalDate.now(),
                        DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), TimeCycle.MIDDLE, activities, bciBlock));

        List<BehaviorChangeInterventionBlockInstance> activitiesBlock = new ArrayList<>();
        activitiesBlock.add(blockInstance);

        List<BCIModuleInstance> modules = new ArrayList<>();

        BehaviorChangeInterventionPhase bciPhase = behaviorChangeInterventionPhaseService.create
                (new BehaviorChangeInterventionPhase("Intervention ENTRY", "Intervention EXIT"));
        BehaviorChangeInterventionPhaseInstance newPhaseInstance = behaviorChangeInterventionPhaseInstanceService.create
                (new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.READY, blockInstance, activitiesBlock, modules, bciPhase));

        bciInstance.addActivity(newPhaseInstance);

        //Set EntryConditions to blank in order to pass checkEntryConditions()
        newInteractionInstance.getBciActivity().setPreconditions("");
        newBlockInstance.getBehaviorChangeInterventionBlock().setEntryConditions("");
        newPhaseInstance.getBehaviorChangeInterventionPhase().setEntryConditions("");

        //Update the entities
        interactionInstanceService.update(newInteractionInstance);
        behaviorChangeInterventionBlockInstanceService.update(newBlockInstance);
        behaviorChangeInterventionPhaseInstanceService.update(newPhaseInstance);
        behaviorChangeInterventionInstanceService.update(bciInstance);

        //Create and publish the ClientEvent
        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(clientEvent,
                interactionInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId(), newInteractionInstance.getId(),
                newBlockInstance.getId(), phaseInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        //Tests
        assertEquals(ExecutionStatus.SUSPENDED, interactionInstance.getStatus());
        assertEquals(ExecutionStatus.SUSPENDED, blockInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, newInteractionInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, newBlockInstance.getStatus());
        assertEquals(ExecutionStatus.READY, newPhaseInstance.getStatus());
        assertEquals(phaseInstance.getCurrentBlock().getId(), newBlockInstance.getId());
        assertNotEquals(phaseInstance.getCurrentBlock().getId(), blockInstance.getId());
        assertEquals(bciInstance.getCurrentPhase().getId(), phaseInstance.getId());
        assertNotEquals(bciInstance.getCurrentPhase().getId(), newPhaseInstance.getId());
        assertTrue(response.isSuccess());
    }

    @Test
    void handleBCIActivityClientEventInProgressFailEntryConditionInPhase() {
        ClientEvent clientEvent = ClientEvent.IN_PROGRESS;

        //Create new entities to simulate selecting a new Activity in a different Block and Phase
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        Interaction interaction = interactionService.create(new Interaction("newInteraction", "Description",
                ActivityType.BCI_ACTIVITY, "precondition", "postcondition", InteractionMode.ASYNCHRONOUS,
                role, InteractionMedium.VIDEO));
        InteractionInstance newInteractionInstance = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.READY, participants,
                interaction));

        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(interactionInstance);

        BehaviorChangeInterventionBlock bciBlock = behaviorChangeInterventionBlockService
                .create(new BehaviorChangeInterventionBlock("Intervention ENTRY", "Intervention EXIT"));
        BehaviorChangeInterventionBlockInstance newBlockInstance = behaviorChangeInterventionBlockInstanceService
                .create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.READY, LocalDate.now(),
                        DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), TimeCycle.MIDDLE, activities, bciBlock));

        List<BehaviorChangeInterventionBlockInstance> activitiesBlock = new ArrayList<>();
        activitiesBlock.add(blockInstance);
        List<BCIModuleInstance> modules = new ArrayList<>();

        BehaviorChangeInterventionPhase bciPhase = behaviorChangeInterventionPhaseService
                .create(new BehaviorChangeInterventionPhase("Intervention ENTRY", "Intervention EXIT"));
        BehaviorChangeInterventionPhaseInstance newPhaseInstance = behaviorChangeInterventionPhaseInstanceService
                .create(new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.READY, blockInstance, activitiesBlock, modules, bciPhase));

        bciInstance.addActivity(newPhaseInstance);

        //Set EntryConditions to blank in order to pass checkEntryConditions()
        newInteractionInstance.getBciActivity().setPreconditions("");
        newBlockInstance.getBehaviorChangeInterventionBlock().setEntryConditions("");

        //Update the entities
        interactionInstanceService.update(newInteractionInstance);
        behaviorChangeInterventionBlockInstanceService.update(newBlockInstance);
        behaviorChangeInterventionPhaseInstanceService.update(newPhaseInstance);
        behaviorChangeInterventionInstanceService.update(bciInstance);

        //Create and publish the ClientEvent
        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(clientEvent,
                interactionInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId(),
                newInteractionInstance.getId(), newBlockInstance.getId(), newPhaseInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        //Tests
        assertEquals(ExecutionStatus.IN_PROGRESS, interactionInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, blockInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.READY, newInteractionInstance.getStatus());
        assertEquals(ExecutionStatus.READY, newBlockInstance.getStatus());
        assertEquals(ExecutionStatus.READY, newPhaseInstance.getStatus());
        assertEquals(phaseInstance.getCurrentBlock().getId(), blockInstance.getId());
        assertNotEquals(phaseInstance.getCurrentBlock().getId(), newBlockInstance.getId());
        assertEquals(bciInstance.getCurrentPhase().getId(), phaseInstance.getId());
        assertNotEquals(bciInstance.getCurrentPhase().getId(), newPhaseInstance.getId());
        assertFalse(response.isSuccess());
    }
}
