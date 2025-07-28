package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ChangeAspect;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.event.BCIPhaseInstanceEvent;
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
    private BehaviorChangeInterventionPhaseInstanceService behaviorChangeInterventionPhaseInstanceService;

    @Autowired
    private BehaviorChangeInterventionBlockInstanceService behaviorChangeInterventionBlockInstanceService;
    @Autowired
    private BCIActivityInstanceService bciActivityInstanceService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private HealthCareProfessionalService healthCareProfessionalService;

    @Autowired
    private BCIModuleInstanceService bciModuleInstanceService;

    private BehaviorChangeInterventionPhaseInstance phaseInstance;

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
                ExecutionStatus.IN_PROGRESS, LocalDate.now(), DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"),
                participants));
        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(activityInstance);

        BCIModuleInstance moduleInstance = bciModuleInstanceService.create(new BCIModuleInstance(ExecutionStatus.STALLED,
                OutcomeType.SUCCESSFUL, activities));
        List<BCIModuleInstance> modules = new ArrayList<>();
        modules.add(moduleInstance);

        BehaviorChangeInterventionBlockInstance blockInstance = behaviorChangeInterventionBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.STALLED, TimeCycle.BEGINNING, activities));
        List<BehaviorChangeInterventionBlockInstance> blocks = new ArrayList<>();
        blocks.add(blockInstance);

        phaseInstance = behaviorChangeInterventionPhaseInstanceService.create
                (new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.STALLED, blockInstance, blocks, modules));
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
        assertThrows(EntityNotFoundException.class, () -> behaviorChangeInterventionPhaseInstanceService.
                findById(phaseInstance.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        List<BCIModuleInstance> modules = new ArrayList<>(phaseInstance.getModules());
        List<BehaviorChangeInterventionBlockInstance> blocks = new ArrayList<>(phaseInstance.getActivities());
        behaviorChangeInterventionPhaseInstanceService.create(new BehaviorChangeInterventionPhaseInstance(
                ExecutionStatus.STALLED, phaseInstance.getCurrentBlock(), blocks, modules));
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
    void testFindByIdAndCurrentBlockId() {
        BehaviorChangeInterventionPhaseInstance result = behaviorChangeInterventionPhaseInstanceService.
                findByIdAndCurrentBlockId(phaseInstance.getId(), phaseInstance.getCurrentBlock().getId());
        assertEquals(phaseInstance.getId(), result.getId());
        assertEquals(phaseInstance.getCurrentBlock().getId(), result.getCurrentBlock().getId());
    }

    @Test
    void testPublishEvent() {
        phaseInstance.getCurrentBlock().setStage(TimeCycle.BEGINNING);
        BehaviorChangeInterventionPhaseInstance updated = behaviorChangeInterventionPhaseInstanceService.update(phaseInstance);
        assertEquals(phaseInstance.getCurrentBlock().getStage(), updated.getCurrentBlock().getStage());

        assertEquals(1, applicationEvents.stream(BCIPhaseInstanceEvent.class).
                filter(event -> event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                        event.getCurrentBlock().getStage().equals(TimeCycle.BEGINNING)).count());
    }
}
