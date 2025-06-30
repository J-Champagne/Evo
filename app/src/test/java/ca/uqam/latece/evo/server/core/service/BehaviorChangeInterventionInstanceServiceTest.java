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
 * Tests methods found in BehaviorChangeInterventionInstanceService in a containerized setup.
 * @author Julien Champagne.
 */
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

    @BeforeEach
    public void setUp() {
        Role role = roleService.create(new Role("Administrator"));
        Patient patient = patientService.create(new Patient("Bob", "bob@gmail.com",
                "222-2222", "1 January 1970", "Student", "1234 Street"));
        Participant participant = participantService.create(new Participant(role, patient));
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

        BehaviorChangeInterventionBlockInstance blockInstance = bciBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(TimeCycle.BEGINNING, activities));
        List<BehaviorChangeInterventionBlockInstance> blocks = new ArrayList<>();
        blocks.add(blockInstance);

        BehaviorChangeInterventionPhaseInstance phaseInstance = bciPhaseInstanceService.create
                (new BehaviorChangeInterventionPhaseInstance(blockInstance, blocks, modules));
        List<BehaviorChangeInterventionPhaseInstance> phases = new ArrayList<>();
        phases.add(phaseInstance);

        bciInstance = bciInstanceService.create(new BehaviorChangeInterventionInstance(patient, phaseInstance, phases));
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
        List<BehaviorChangeInterventionPhaseInstance> phases = new ArrayList<>(bciInstance.getPhases());
        bciInstanceService.create(new BehaviorChangeInterventionInstance(bciInstance.getPatient(), bciInstance.getCurrentPhase(), phases));
        List<BehaviorChangeInterventionInstance> results = bciInstanceService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void testFindByCurrentPatientId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByPatientId(bciInstance.getPatient().getId());

        assertFalse(result.isEmpty());
        assertEquals(bciInstance.getId(), result.get(0).getId());
    }

    @Test
    void testFindByCurrentPhaseId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByCurrentPhaseId(bciInstance.getCurrentPhase().getId());

        assertFalse(result.isEmpty());
        assertEquals(bciInstance.getId(), result.get(0).getId());
    }

    @Test
    void testFindByPhasesId() {
        List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByPhasesId(bciInstance.getPhases().get(0).getId());

        assertFalse(result.isEmpty());
        assertEquals(bciInstance.getId(), result.get(0).getId());
    }
}
