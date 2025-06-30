package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.service.instance.*;

import ca.uqam.latece.evo.server.core.util.DateFormatter;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods found in BehaviorChangeInterventionBlockInstanceService in a containerized setup.
 * @author Julien Champagne.
 */
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

        BCIModuleInstance moduleInstance = bciModuleInstanceService.create(new BCIModuleInstance("NOTSTARTED", OutcomeType.SUCCESSFUL,
                activities));
        List<BCIModuleInstance> modules = new ArrayList<>();
        modules.add(moduleInstance);

        BehaviorChangeInterventionBlockInstance blockInstance = behaviorChangeInterventionBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(TimeCycle.BEGINNING, activities));
        List<BehaviorChangeInterventionBlockInstance> blocks = new ArrayList<>();
        blocks.add(blockInstance);

        phaseInstance = behaviorChangeInterventionPhaseInstanceService.create
                (new BehaviorChangeInterventionPhaseInstance(blockInstance, blocks, modules));
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
        List<BehaviorChangeInterventionBlockInstance> blocks = new ArrayList<>(phaseInstance.getBlocks());
        behaviorChangeInterventionPhaseInstanceService.create(new BehaviorChangeInterventionPhaseInstance(
                phaseInstance.getCurrentBlock(), blocks, modules));
        List<BehaviorChangeInterventionPhaseInstance> results = behaviorChangeInterventionPhaseInstanceService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void testFindByCurrentBlock() {
        List<BehaviorChangeInterventionPhaseInstance> result = behaviorChangeInterventionPhaseInstanceService
            .findByCurrentBlockId(phaseInstance.getCurrentBlock().getId());

        assertFalse(result.isEmpty());
        assertEquals(phaseInstance.getId(), result.get(0).getId());
    }

    @Test
    void testFindByBlocksId() {
        List<BehaviorChangeInterventionPhaseInstance> result = behaviorChangeInterventionPhaseInstanceService
                .findByBlocksId(phaseInstance.getBlocks().get(0).getId());

        assertFalse(result.isEmpty());
        assertEquals(phaseInstance.getId(), result.get(0).getId());
    }

    @Test
    void testFindByModulesId() {
        List<BehaviorChangeInterventionPhaseInstance> result = behaviorChangeInterventionPhaseInstanceService
                .findByModulesId(phaseInstance.getModules().get(0).getId());

        assertFalse(result.isEmpty());
        assertEquals(phaseInstance.getId(), result.get(0).getId());
    }
}
