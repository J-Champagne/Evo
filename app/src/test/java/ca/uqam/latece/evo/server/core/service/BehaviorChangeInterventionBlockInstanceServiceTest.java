package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.event.BCIBlockInstanceClientEvent;
import ca.uqam.latece.evo.server.core.event.BCIPhaseInstanceClientEvent;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.service.instance.BCIActivityInstanceService;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionBlockInstanceService;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;
import ca.uqam.latece.evo.server.core.service.instance.ParticipantService;
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
 * Tests methods found in BehaviorChangeInterventionBlockInstanceService in a containerized setup.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@RecordApplicationEvents
@ApplicationScope
@ContextConfiguration(classes = {BehaviorChangeInterventionBlockInstance.class, BehaviorChangeInterventionBlockInstanceService.class})
public class BehaviorChangeInterventionBlockInstanceServiceTest extends AbstractServiceTest {
    @Autowired
    BehaviorChangeInterventionBlockInstanceService behaviorChangeInterventionBlockInstanceService;

    @Autowired
    private BCIActivityService bciActivityService;

    @Autowired
    private BCIActivityInstanceService bciActivityInstanceService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private HealthCareProfessionalService healthCareProfessionalService;

    @Autowired
    private BehaviorChangeInterventionBlockService behaviorChangeInterventionBlockService;

    @Autowired
    private ApplicationEvents applicationEvents;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

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

        BehaviorChangeInterventionBlock bciBlock = behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("Intervention ENTRY", "Intervention EXIT"));

        blockInstance = behaviorChangeInterventionBlockInstanceService.
                    create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
                            DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), TimeCycle.MIDDLE, activities, bciBlock));
    }

    @Test
    @Override
    public void testSave() {
        assert blockInstance.getId() > 0;
    }

    @Test
    @Override
    public void testUpdate() {
        blockInstance.setStage(TimeCycle.END);
        BehaviorChangeInterventionBlockInstance updated = behaviorChangeInterventionBlockInstanceService.update(blockInstance);
        assertEquals(blockInstance.getStage(), updated.getStage());
    }

    @Test
    @Override
    void testFindById() {
        BehaviorChangeInterventionBlockInstance found = behaviorChangeInterventionBlockInstanceService.findById(blockInstance.getId());
        assertEquals(blockInstance.getId(), found.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        behaviorChangeInterventionBlockInstanceService.deleteById(blockInstance.getId());
        assertThrows(EntityNotFoundException.class, () -> behaviorChangeInterventionBlockInstanceService.
                findById(blockInstance.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        behaviorChangeInterventionBlockInstanceService.create(new BehaviorChangeInterventionBlockInstance(
                ExecutionStatus.STALLED, TimeCycle.MIDDLE, blockInstance.getBehaviorChangeInterventionBlock()));
        List<BehaviorChangeInterventionBlockInstance> results = behaviorChangeInterventionBlockInstanceService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void testFindByStage() {
        List<BehaviorChangeInterventionBlockInstance> found = behaviorChangeInterventionBlockInstanceService.findByStage(TimeCycle.MIDDLE);

        assertEquals(1, found.size());
        assertEquals(blockInstance.getId(), found.getFirst().getId());
    }

    @Test
    void testFindByActivitiesId() {
        List<BehaviorChangeInterventionBlockInstance> found = behaviorChangeInterventionBlockInstanceService.
                findByActivitiesId(blockInstance.getActivities().getFirst().getId());

        assertEquals(1, found.size());
        assertEquals(blockInstance.getId(), found.getFirst().getId());
    }

    /* Disabled because of how change to how we update our BCI entities from the frontend
    @Test
    void testPublishEvent() {
        blockInstance.setStage(TimeCycle.BEGINNING);
        blockInstance.setStatus(ExecutionStatus.IN_PROGRESS);
        BehaviorChangeInterventionBlockInstance updated = behaviorChangeInterventionBlockInstanceService.update(blockInstance);
        assertEquals(blockInstance.getStage(), updated.getStage());

        assertEquals(1, applicationEvents.stream(BCIBlockInstanceEvent.class).
                filter(event -> event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                        event.getEvoModel().getStage().equals(TimeCycle.BEGINNING)).count());
    }

    @Test
    void handleBCIBlockInstanceEvents() {
        // Update the blockInstance.
        blockInstance.setStage(TimeCycle.END);
        blockInstance.setStatus(ExecutionStatus.FINISHED);
        BehaviorChangeInterventionBlockInstance updated = behaviorChangeInterventionBlockInstanceService.update(blockInstance);
        assertEquals(blockInstance.getStage(), updated.getStage());

        // Creates the BCIBlockInstanceEvent
        BCIBlockInstanceEvent blockInstanceEvent = new BCIBlockInstanceEvent(updated, updated.getStage());

        // Publish the event.
        applicationEventPublisher.publishEvent(blockInstanceEvent);

        // Test.
        assertEquals(1, applicationEvents.stream(BCIBlockInstanceEvent.class).
                filter(event -> event.getChangeAspect().equals(ChangeAspect.TERMINATED) &&
                        event.getEvoModel().getStage().equals(TimeCycle.END) &&
                        event.getEvoModel().getStatus().equals(ExecutionStatus.FINISHED)).count());
    }*/

    @Test
    void handleBCIBlockInstanceClientEventFinish() {
        ClientEvent clientEvent = ClientEvent.FINISH;

        // Update the BCIActivityInstance and BCIBlockInstance
        activityInstance.setStatus(ExecutionStatus.FINISHED);
        blockInstance.getBehaviorChangeInterventionBlock().setExitConditions("");
        bciActivityInstanceService.update(activityInstance);
        behaviorChangeInterventionBlockInstanceService.update(blockInstance);

        // Creates the BCIBlockInstanceClientEvent
        BCIBlockInstanceClientEvent<BCIActivityInstance> blockInstanceClientEvent = new BCIBlockInstanceClientEvent<>(activityInstance, clientEvent,
                new ClientEventResponse(), blockInstance.getId(), null, null);

        // Publish the event
        applicationEventPublisher.publishEvent(blockInstanceClientEvent);

        // Test
        assertEquals(ExecutionStatus.FINISHED, blockInstance.getStatus());
        assertEquals(1, applicationEvents.stream(BCIPhaseInstanceClientEvent.class).count());
    }

    @Test
    void handleBCIBlockInstanceClientEventsFail() {
        // Update the BCIActivityInstance and BCIBlockInstance
        activityInstance.setStatus(ExecutionStatus.FINISHED);
        bciActivityInstanceService.update(activityInstance);

        // Creates the BCIBlockInstanceClientEvent
        BCIBlockInstanceClientEvent<BCIActivityInstance> blockInstanceClientEvent = new BCIBlockInstanceClientEvent<>(activityInstance, ClientEvent.FINISH,
                new ClientEventResponse(), blockInstance.getId(), null, null);

        // Publish the event
        applicationEventPublisher.publishEvent(blockInstanceClientEvent);

        // Test
        assertNotEquals(ExecutionStatus.FINISHED, blockInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, blockInstance.getStatus());
        assertEquals(0, applicationEvents.stream(BCIPhaseInstanceClientEvent.class).count());
    }

    @Test
    void handleBCIBlockInstanceClientEventsInProgress() {
        ClientEvent clientEvent = ClientEvent.IN_PROGRESS;

        // Update the BCIActivityInstance and BCIBlockInstance
        activityInstance.setStatus(ExecutionStatus.SUSPENDED);
        bciActivityInstanceService.update(activityInstance);

        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivity newBCIActivity = bciActivityService.create(new BCIActivity("new Programming", "Description",
                ActivityType.BCI_ACTIVITY, "Intervention ENTRY", "Intervention EXIT"));
        BCIActivityInstance newActivityInstance = bciActivityInstanceService.create(new BCIActivityInstance(
                ExecutionStatus.IN_PROGRESS, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"),
                participants, newBCIActivity));

        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(newActivityInstance);

        BehaviorChangeInterventionBlock newBCIBlock = behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("Intervention ENTRY", "Intervention EXIT"));
        BehaviorChangeInterventionBlockInstance newBlockInstance = behaviorChangeInterventionBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
                        DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), TimeCycle.MIDDLE, activities, newBCIBlock));

        newBlockInstance.getBehaviorChangeInterventionBlock().setEntryConditions("");
        behaviorChangeInterventionBlockInstanceService.update(newBlockInstance);
        behaviorChangeInterventionBlockInstanceService.update(blockInstance);

        // Creates the BCIBlockInstanceClientEvent
        BCIBlockInstanceClientEvent<BCIActivityInstance> blockInstanceClientEvent = new BCIBlockInstanceClientEvent<>(activityInstance, clientEvent,
                new ClientEventResponse(), blockInstance.getId(), null, null, newBlockInstance.getId(), null);

        // Publish the event
        applicationEventPublisher.publishEvent(blockInstanceClientEvent);

        // Test
        assertEquals(ExecutionStatus.SUSPENDED, blockInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, newBlockInstance.getStatus());
        assertEquals(1, applicationEvents.stream(BCIPhaseInstanceClientEvent.class).count());
    }
}
