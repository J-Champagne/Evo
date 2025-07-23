package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.event.EvoEvent;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.service.instance.*;

import ca.uqam.latece.evo.server.core.util.DateFormatter;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private BCIActivityInstanceService bciActivityInstanceService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private HealthCareProfessionalService healthCareProfessionalService;

    private BehaviorChangeInterventionBlockInstance blockInstance;

    @Autowired
    private ApplicationEvents applicationEvents;

    @BeforeEach
    public void setUp() {
        Role role = roleService.create(new Role("Administrator"));
        HealthCareProfessional hcp = healthCareProfessionalService.create(new HealthCareProfessional("Bob", "bob@gmail.com",
                "222-2222", "Student", "New-York", "Health"));
        Participant participant = participantService.create(new Participant(role, hcp));
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        BCIActivityInstance activityInstance = bciActivityInstanceService.create(new BCIActivityInstance(
                "In progress", LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants));
        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(activityInstance);

        blockInstance = behaviorChangeInterventionBlockInstanceService.
                    create(new BehaviorChangeInterventionBlockInstance(TimeCycle.MIDDLE, activities));
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
        behaviorChangeInterventionBlockInstanceService.create(new BehaviorChangeInterventionBlockInstance(TimeCycle.MIDDLE));
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

    @Test
    void testPublishEvent() {
        blockInstance.setStage(TimeCycle.BEGINNING);
        BehaviorChangeInterventionBlockInstance updated = behaviorChangeInterventionBlockInstanceService.update(blockInstance);
        assertEquals(blockInstance.getStage(), updated.getStage());

        assertEquals(1, applicationEvents.stream(EvoEvent.class).
                filter(event -> event.getTimeCycle().equals(TimeCycle.BEGINNING)).count());
    }
}
