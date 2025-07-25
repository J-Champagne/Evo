package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.BehaviorChangeInterventionInstanceController;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.repository.instance.*;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionInstanceService;
import ca.uqam.latece.evo.server.core.util.DateFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Tests methods found in BehaviorChangeInterventionInstanceController using WebMvcTest, and repository queries using MockMvc (Mockito).
 * @author Julien Champagne.
 */
@WebMvcTest(controllers = BehaviorChangeInterventionInstanceController.class)
@ContextConfiguration(classes = {BehaviorChangeInterventionInstance.class, BehaviorChangeInterventionInstanceService.class,
        BehaviorChangeInterventionInstanceController.class})
public class BehaviorChangeInterventionInstanceControllerTest extends AbstractControllerTest {
    @MockBean
    BehaviorChangeInterventionInstanceRepository bciInstanceRepository;

    @MockBean
    BehaviorChangeInterventionPhaseInstanceRepository bciPhaseInstanceRepository;

    @MockBean
    BehaviorChangeInterventionBlockInstanceRepository bciBlockInstanceRepository;

    @MockBean
    BCIModuleInstanceRepository bciModuleInstanceRepository;

    @MockBean
    PatientRepository patientRepository;

    private Role role = new Role("Administrator");

    private Patient patient = new Patient("Bob", "bob@gmail.com",
            "222-2222", "1 January 1970", "Student", "1234 Street");

    private Participant participant = new Participant(role, patient);

    private List<Participant> participants = List.of(participant);

    private BCIActivityInstance activityInstance = new BCIActivityInstance("In progress", LocalDate.now(),
            DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants);

    private List<BCIActivityInstance> activities = List.of(activityInstance);

    private BCIModuleInstance moduleInstance = new BCIModuleInstance("NOTSTARTED", OutcomeType.SUCCESSFUL, activities);

    private List<BCIModuleInstance> modules = List.of(moduleInstance);

    private BehaviorChangeInterventionBlockInstance blockInstance = new BehaviorChangeInterventionBlockInstance("NOTSTARTED",
            TimeCycle.BEGINNING, activities);

    private List<BehaviorChangeInterventionBlockInstance> blocks = List.of(blockInstance);

    private BehaviorChangeInterventionPhaseInstance phaseInstance = new BehaviorChangeInterventionPhaseInstance("NOTSTARTED",
            blockInstance, blocks, modules);

    private List<BehaviorChangeInterventionPhaseInstance> phases = List.of(phaseInstance);

    private BehaviorChangeInterventionInstance bciInstance = new BehaviorChangeInterventionInstance(patient, phaseInstance, phases);

    private static final String url = "/behaviorchangeinterventioninstance";

    @BeforeEach
    @Override
    public void setUp() {
        patient.setId(1L);
        blockInstance.setId(1L);
        moduleInstance.setId(1L);
        phaseInstance.setId(1L);
        bciInstance.setId(1L);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(bciBlockInstanceRepository.save(blockInstance)).thenReturn(blockInstance);
        when(bciModuleInstanceRepository.save(moduleInstance)).thenReturn(moduleInstance);
        when(bciPhaseInstanceRepository.save(phaseInstance)).thenReturn(phaseInstance);
        when(bciInstanceRepository.save(bciInstance)).thenReturn(bciInstance);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(url, bciInstance);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        BehaviorChangeInterventionPhaseInstance bciPhaseInstance2 = new BehaviorChangeInterventionPhaseInstance(
                "NOTSTARTED", phaseInstance.getCurrentBlock(), phaseInstance.getBlocks(), phaseInstance.getModules());
        bciPhaseInstance2.setId(2L);
        when(bciPhaseInstanceRepository.save(bciPhaseInstance2)).thenReturn(bciPhaseInstance2);

        BehaviorChangeInterventionInstance updated = new BehaviorChangeInterventionInstance(
                patient, bciPhaseInstance2, phases);
        updated.setId(phaseInstance.getId());

        when(bciInstanceRepository.save(updated)).thenReturn(updated);
        when(bciInstanceRepository.findById(updated.getId())).thenReturn(Optional.of(updated));
        performUpdateRequest(url, updated, "$.currentPhase.id", updated.getCurrentPhase().getId());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(url + "/" + bciInstance.getId(), bciInstance);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(bciInstanceRepository.findAll()).thenReturn(Collections.singletonList(bciInstance));
        performGetRequest(url, "$[0].id", 1);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(bciInstanceRepository.findById(bciInstance.getId())).thenReturn(Optional.of(bciInstance));
        performGetRequest(url + "/find/" + bciInstance.getId(), "$.id", bciInstance.getId());
    }

    @Test
    void testFindByPatientId() throws Exception {
        when(bciInstanceRepository.findByPatientId(bciInstance.getPatient().getId())).thenReturn(Collections.singletonList(bciInstance));
        performGetRequest(url + "/find/patient/" + bciInstance.getPatient().getId(), "$[0].id", bciInstance.getId());
    }

    @Test
    void testFindByCurrentPhaseId() throws Exception {
        when(bciInstanceRepository.findByCurrentPhaseId(bciInstance.getCurrentPhase().getId())).thenReturn(Collections.singletonList(bciInstance));
        performGetRequest(url + "/find/currentphase/" + bciInstance.getCurrentPhase().getId(), "$[0].id", bciInstance.getId());
    }

    @Test
    void testFindByPhasesId() throws Exception {
        when(bciInstanceRepository.findByPhasesId(bciInstance.getPhases().get(0).getId())).thenReturn(Collections.singletonList(bciInstance));
        performGetRequest(url + "/find/phases/" + bciInstance.getPhases().get(0).getId(), "$[0].id", bciInstance.getId());
    }
}
