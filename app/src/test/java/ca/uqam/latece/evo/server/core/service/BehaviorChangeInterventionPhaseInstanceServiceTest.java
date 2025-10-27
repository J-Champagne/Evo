package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.*;
import ca.uqam.latece.evo.server.core.event.*;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.service.instance.*;

import ca.uqam.latece.evo.server.core.util.DateFormatter;
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
 * Tests methods found in BehaviorChangeInterventionBlockInstanceService in a containerized setup.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@RecordApplicationEvents
@ApplicationScope
@ContextConfiguration(classes = {BehaviorChangeInterventionPhaseInstance.class, BehaviorChangeInterventionPhaseInstanceService.class})
public class BehaviorChangeInterventionPhaseInstanceServiceTest extends AbstractServiceTest {
    @Autowired
    private BehaviorChangeInterventionPhaseService behaviorChangeInterventionPhaseService;

    @Autowired
    private BehaviorChangeInterventionPhaseInstanceService behaviorChangeInterventionPhaseInstanceService;

    @Autowired
    private BehaviorChangeInterventionBlockService behaviorChangeInterventionBlockService;

    @Autowired
    private BehaviorChangeInterventionBlockInstanceService behaviorChangeInterventionBlockInstanceService;

    @Autowired
    private BCIActivityInstanceService bciActivityInstanceService;

    @Autowired
    private BCIActivityService bciActivityService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private HealthCareProfessionalService healthCareProfessionalService;

    @Autowired
    private BCIModuleInstanceService bciModuleInstanceService;

    @Autowired
    private ApplicationEvents applicationEvents;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private BehaviorChangeInterventionPhaseInstance phaseInstance;

    private BehaviorChangeInterventionBlockInstance blockInstance;

    private BCIActivityInstance activityInstance;

    private Participant participant;


    @BeforeEach
    public void setUp() {
        Role role = roleService.create(new Role("Administrator"));
        HealthCareProfessional hcp = healthCareProfessionalService.create(new HealthCareProfessional("Bob", "bob@gmail.com",
                "222-2222", "Student", "New-York", "Health"));
        participant = participantService.create(new Participant(role, hcp));
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivity bciActivity = bciActivityService.create(new BCIActivity("Programming", "Description",
                ActivityType.BCI_ACTIVITY, "Intervention ENTRY", "Intervention EXIT"));

        activityInstance = bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.IN_PROGRESS, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"),
                participants, bciActivity));
        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(activityInstance);

        BCIModuleInstance moduleInstance = bciModuleInstanceService.create(new BCIModuleInstance(ExecutionStatus.IN_PROGRESS,
                OutcomeType.SUCCESSFUL, activities));
        List<BCIModuleInstance> modules = new ArrayList<>();
        modules.add(moduleInstance);

        BehaviorChangeInterventionBlock bciBlock = behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("Intervention ENTRY", "Intervention EXIT"));

        blockInstance = behaviorChangeInterventionBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.IN_PROGRESS, TimeCycle.BEGINNING, activities, bciBlock));
        List<BehaviorChangeInterventionBlockInstance> blocks = new ArrayList<>();
        blocks.add(blockInstance);

        BehaviorChangeInterventionPhase bciPhase = behaviorChangeInterventionPhaseService.create((
                new BehaviorChangeInterventionPhase("Intervention Phase ENTRY", "Intervention Phase EXIT")));

        phaseInstance = behaviorChangeInterventionPhaseInstanceService.create
                (new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.IN_PROGRESS, blockInstance, blocks, modules, bciPhase));
    }

    @Test
    @Override
    public void testSave() {
        assert phaseInstance.getId() > 0;
    }

    @Test
    @Override
    public void testUpdate() {
        phaseInstance.getCurrentBlock().setStage(TimeCycle.END);
        BehaviorChangeInterventionPhaseInstance updated = behaviorChangeInterventionPhaseInstanceService.update(phaseInstance);
        assertEquals(TimeCycle.END, updated.getCurrentBlock().getStage());
    }

    @Test
    @Override
    void testFindById() {
        BehaviorChangeInterventionPhaseInstance found = behaviorChangeInterventionPhaseInstanceService.findById(phaseInstance.getId());
        assertEquals(phaseInstance.getId(), found.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        behaviorChangeInterventionPhaseInstanceService.deleteById(phaseInstance.getId());
        assertFalse(behaviorChangeInterventionPhaseInstanceService.existsById(phaseInstance.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        List<BCIModuleInstance> modules = new ArrayList<>(phaseInstance.getModules());
        List<BehaviorChangeInterventionBlockInstance> blocks = new ArrayList<>(phaseInstance.getActivities());
        behaviorChangeInterventionPhaseInstanceService.create(new BehaviorChangeInterventionPhaseInstance(
                ExecutionStatus.STALLED, phaseInstance.getCurrentBlock(), blocks, modules, phaseInstance.getBehaviorChangeInterventionPhase()));
        List<BehaviorChangeInterventionPhaseInstance> results = behaviorChangeInterventionPhaseInstanceService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void testFindByCurrentBlock() {
        List<BehaviorChangeInterventionPhaseInstance> result = behaviorChangeInterventionPhaseInstanceService
                .findByCurrentBlockId(phaseInstance.getCurrentBlock().getId());

        assertFalse(result.isEmpty());
        assertEquals(phaseInstance.getId(), result.getFirst().getId());
    }

    @Test
    void testFindByBlocksId() {
        List<BehaviorChangeInterventionPhaseInstance> result = behaviorChangeInterventionPhaseInstanceService
                .findByActivitiesId(phaseInstance.getActivities().getFirst().getId());

        assertFalse(result.isEmpty());
        assertEquals(phaseInstance.getId(), result.getFirst().getId());
    }

    @Test
    void testFindByModulesId() {
        List<BehaviorChangeInterventionPhaseInstance> result = behaviorChangeInterventionPhaseInstanceService
                .findByModulesId(phaseInstance.getModules().getFirst().getId());

        assertFalse(result.isEmpty());
        assertEquals(phaseInstance.getId(), result.getFirst().getId());
    }

    @Test
    void testFindByBehaviorChangeInterventionPhaseID() {
        List<BehaviorChangeInterventionPhaseInstance> result = behaviorChangeInterventionPhaseInstanceService
                .findByBehaviorChangeInterventionPhaseId(phaseInstance.getBehaviorChangeInterventionPhase().getId());
        assertEquals(phaseInstance.getId(), result.getFirst().getId());
        assertEquals(phaseInstance.getBehaviorChangeInterventionPhase().getId(), result.getFirst().getBehaviorChangeInterventionPhase().getId());
    }

    @Test
    void testFindByIdAndCurrentBlockId() {
        BehaviorChangeInterventionPhaseInstance result = behaviorChangeInterventionPhaseInstanceService.
                findByIdAndCurrentBlockId(phaseInstance.getId(), phaseInstance.getCurrentBlock().getId());
        assertEquals(phaseInstance.getId(), result.getId());
        assertEquals(phaseInstance.getCurrentBlock().getId(), result.getCurrentBlock().getId());
    }

    /* Disabled because of how change to how we update our BCI entities from the frontend
    @Test
    void testChangeCurrentBlock() {
        Role role = roleService.create(new Role("e-Health"));

        HealthCareProfessional hcp = healthCareProfessionalService.create(new HealthCareProfessional("Maria",
                "maria@gmail.com", "9999", "Professor", "Montreal", "Health"));

        Participant participant = participantService.create(new Participant(role, hcp));

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivity bciActivity = bciActivityService.create(new BCIActivity("Programming2", "Description", ActivityType.BCI_ACTIVITY,
                "Intervention ENTRY", "Intervention EXIT"));

        BCIActivityInstance activityInstance = bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.IN_PROGRESS, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2025/07/30"),
                participants, bciActivity));

        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(activityInstance);

        BehaviorChangeInterventionBlock bciBlock = behaviorChangeInterventionBlockService.create
                (new BehaviorChangeInterventionBlock("Intervention ENTRY", "Intervention EXIT"));

        BehaviorChangeInterventionBlockInstance blockInstance = behaviorChangeInterventionBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.STALLED, TimeCycle.BEGINNING, activities, bciBlock));

        // Set the current block.
        phaseInstance.setCurrentBlock(blockInstance);

        // Change the current block.
        BehaviorChangeInterventionPhaseInstance updated = behaviorChangeInterventionPhaseInstanceService.
                changeCurrentBlock(phaseInstance);

        // Test the update.
        assertEquals(TimeCycle.BEGINNING, updated.getCurrentBlock().getStage());

        // Test the event (BCIBlockInstanceEvent).
        assertEquals(1, applicationEvents.stream(BCIBlockInstanceEvent.class).
                filter(event -> event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                        event.getEvoModel().getId().equals(blockInstance.getId())).count());

    }
    */

    @Test
    void testChangeModuleStatusToFinished() {
        // Update the Module status to finished.
        BehaviorChangeInterventionPhaseInstance updated = behaviorChangeInterventionPhaseInstanceService.
                changeModuleStatusToFinished(phaseInstance.getId(), phaseInstance.getModules().getFirst());

        // Test the database updated.
        assertEquals(ExecutionStatus.FINISHED, updated.getModules().getFirst().getStatus());

        // Test the Event update.
        assertEquals(1, applicationEvents.stream(BCIModuleInstanceEvent.class).
                filter(event -> event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                        event.getEvoModel().getStatus().equals(ExecutionStatus.FINISHED)).count());
    }

    @Test
    void testChangeModuleStatusToInProgress() {
        // Update the Module status to in progress.
        BehaviorChangeInterventionPhaseInstance updated = behaviorChangeInterventionPhaseInstanceService.
                changeModuleStatusToInProgress(phaseInstance.getId(), phaseInstance.getModules().getFirst());

        // Test the database updated.
        assertEquals(ExecutionStatus.IN_PROGRESS, updated.getModules().getFirst().getStatus());

        // Test the Event update.
        assertEquals(1, applicationEvents.stream(BCIModuleInstanceEvent.class).
                filter(event -> event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                        event.getEvoModel().getStatus().equals(ExecutionStatus.IN_PROGRESS)).count());
    }

    /* Disabled because of how change to how we update our BCI entities from the frontend
    @Test
    void testPublishEvent() {
        phaseInstance.setStatus(ExecutionStatus.IN_PROGRESS);
        phaseInstance.getCurrentBlock().setStage(TimeCycle.BEGINNING);
        BehaviorChangeInterventionPhaseInstance updated = behaviorChangeInterventionPhaseInstanceService.update(phaseInstance);
        assertEquals(phaseInstance.getCurrentBlock().getStage(), updated.getCurrentBlock().getStage());

        assertEquals(1, applicationEvents.stream(BCIPhaseInstanceEvent.class).
                filter(event -> event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                        event.getEvoModel().getStatus().equals(ExecutionStatus.IN_PROGRESS)).count());
    }

    @Test
    void testHandleBCIPhaseInstanceEvents() {
        phaseInstance.setStatus(ExecutionStatus.FINISHED);
        phaseInstance.getCurrentBlock().setStage(TimeCycle.END);
        BehaviorChangeInterventionPhaseInstance updated = behaviorChangeInterventionPhaseInstanceService.update(phaseInstance);
        assertEquals(phaseInstance.getCurrentBlock().getStage(), updated.getCurrentBlock().getStage());

        // Creates the BCIPhaseInstanceEvent
        BCIPhaseInstanceEvent phaseInstanceEvent = new BCIPhaseInstanceEvent(updated, updated.getCurrentBlock().getStage());
        phaseInstanceEvent.setChangeAspect(ChangeAspect.TERMINATED);

        // Publish the event.
        applicationEventPublisher.publishEvent(phaseInstanceEvent);

        // Test.
        assertEquals(1, applicationEvents.stream(BCIPhaseInstanceEvent.class).
                filter(event -> event.getChangeAspect().equals(ChangeAspect.TERMINATED) &&
                        event.getEvoModel().getCurrentBlock().getStage().equals(TimeCycle.END) &&
                        event.getEvoModel().getStatus().equals(ExecutionStatus.FINISHED)).count());
    } */

    @Test
    void handleBCIPhaseInstanceClientEventsFinish() {
        ClientEvent clientEvent = ClientEvent.FINISH;

        //Create new entities to simulate finishing a Block and transitioning to the next one
        BehaviorChangeInterventionBlock bciBlock = behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("test", "test"));
        BehaviorChangeInterventionBlockInstance blockInstance2 = behaviorChangeInterventionBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.READY, TimeCycle.BEGINNING, blockInstance.getActivities(), bciBlock));
        phaseInstance.getActivities().add(blockInstance2);

        //Set EntryConditions to blank in order to pass checkEntryConditions()
        phaseInstance.getBehaviorChangeInterventionPhase().setExitConditions("");

        //Set ExecutionStatus to simulate previous chain of events from other services
        activityInstance.setStatus(ExecutionStatus.FINISHED);
        blockInstance.setStatus(ExecutionStatus.FINISHED);

        //Update the entities
        bciActivityInstanceService.update(activityInstance);
        behaviorChangeInterventionBlockInstanceService.update(blockInstance);
        behaviorChangeInterventionPhaseInstanceService.update(phaseInstance);

        //Create and publish the ClientEvent
        BCIPhaseInstanceClientEvent phaseInstanceClientEvent = new BCIPhaseInstanceClientEvent(clientEvent,
                new ClientEventResponse(), phaseInstance.getId(), null, blockInstance);
        applicationEventPublisher.publishEvent(phaseInstanceClientEvent);

        //Tests
        assertEquals(ExecutionStatus.FINISHED, phaseInstance.getStatus());
        assertEquals(phaseInstance.getCurrentBlock(), blockInstance2);
        assertEquals(1, applicationEvents.stream(BCIInstanceClientEvent.class).count());
    }

    @Test
    void handleBCIPhaseInstanceClientEventsFail() {
        ClientEvent clientEvent = ClientEvent.FINISH;

        //Set ExecutionStatus to simulate previous chain of events from other services
        activityInstance.setStatus(ExecutionStatus.FINISHED);
        blockInstance.setStatus(ExecutionStatus.FINISHED);

        //Update the entities
        bciActivityInstanceService.update(activityInstance);
        behaviorChangeInterventionBlockInstanceService.update(blockInstance);
        phaseInstance = behaviorChangeInterventionPhaseInstanceService.update(phaseInstance);

        //Create and publish the ClientEvent
        BCIPhaseInstanceClientEvent phaseInstanceClientEvent = new BCIPhaseInstanceClientEvent(clientEvent,
                new ClientEventResponse(), phaseInstance.getId(), null, blockInstance);
        applicationEventPublisher.publishEvent(phaseInstanceClientEvent);

        //Tests
        assertNotEquals(ExecutionStatus.FINISHED, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, phaseInstance.getStatus());
        assertEquals(0, applicationEvents.stream(BCIInstanceClientEvent.class).count());
    }

    @Test
    void checkAllEntryConditionsTest() {
        ClientEvent clientEvent = ClientEvent.IN_PROGRESS;

        //Create new entities to simulate selecting a new Activity in a different Phase
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivity newBCIActivity = bciActivityService.create(new BCIActivity("new Programming", "Description",
                ActivityType.BCI_ACTIVITY, "Intervention ENTRY", "Intervention EXIT"));
        BCIActivityInstance newActivityInstance = bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.READY, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"),
                participants, newBCIActivity));

        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(newActivityInstance);

        BehaviorChangeInterventionBlock newBCIBlock = behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("Intervention ENTRY", "Intervention EXIT"));
        BehaviorChangeInterventionBlockInstance newBlockInstance = behaviorChangeInterventionBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.READY, LocalDate.now(),
                        DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), TimeCycle.MIDDLE, activities, newBCIBlock));

        List<BehaviorChangeInterventionBlockInstance> newBlocks = new ArrayList<>();
        newBlocks.add(newBlockInstance);

        BCIModuleInstance moduleInstance = bciModuleInstanceService.create(new BCIModuleInstance(ExecutionStatus.IN_PROGRESS,
                OutcomeType.SUCCESSFUL, activities));

        List<BCIModuleInstance> newModules = new ArrayList<>();
        newModules.add(moduleInstance);

        BehaviorChangeInterventionPhase newBCIPhase = behaviorChangeInterventionPhaseService.create(new BehaviorChangeInterventionPhase(
                "Intervention Phase ENTRY", "Intervention Phase EXIT"));
        BehaviorChangeInterventionPhaseInstance newPhaseInstance = behaviorChangeInterventionPhaseInstanceService.create(
                new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.READY, newBlockInstance, newBlocks, newModules, newBCIPhase));

        //Set EntryConditions to blank in order to pass checkEntryConditions()
        newActivityInstance.getBciActivity().setPreconditions("");
        newBlockInstance.getBehaviorChangeInterventionBlock().setEntryConditions("");
        newPhaseInstance.getBehaviorChangeInterventionPhase().setEntryConditions("");

        //Update entities
        bciActivityInstanceService.update(activityInstance);
        behaviorChangeInterventionBlockInstanceService.update(newBlockInstance);
        behaviorChangeInterventionPhaseInstanceService.update(newPhaseInstance);

        //Create and publish the ClientEvent
        ClientEventResponse clientEventResponse = new ClientEventResponse();
        BCIActivityCheckEntryConditionsClientEvent entryConditionEvent = new BCIActivityCheckEntryConditionsClientEvent(clientEvent,
                clientEventResponse, activityInstance.getId(), blockInstance.getId(), phaseInstance.getId(), newActivityInstance.getId(),
                newBlockInstance.getId(), newPhaseInstance.getId());
        BCIPhaseInstanceCheckEntryConditionsClientEvent event = new BCIPhaseInstanceCheckEntryConditionsClientEvent(entryConditionEvent);
        applicationEventPublisher.publishEvent(event);

        //Tests
        assertEquals(ExecutionStatus.IN_PROGRESS, newPhaseInstance.getStatus());
    }
}
