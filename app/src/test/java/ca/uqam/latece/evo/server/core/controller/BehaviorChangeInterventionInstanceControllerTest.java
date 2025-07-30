package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.BehaviorChangeInterventionInstanceController;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
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
 * Tests methods found in BehaviorChangeInterventionInstanceController using WebMvcTest, and repository queries using
 * MockMvc (Mockito).
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
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

    private BCIActivityInstance activityInstance = new BCIActivityInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
            DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants);

    private List<BCIActivityInstance> activities = List.of(activityInstance);

    private BCIModuleInstance moduleInstance = new BCIModuleInstance(ExecutionStatus.STALLED, OutcomeType.SUCCESSFUL, activities);

    private List<BCIModuleInstance> modules = List.of(moduleInstance);

    private BehaviorChangeInterventionBlockInstance blockInstance = new BehaviorChangeInterventionBlockInstance(
            ExecutionStatus.STALLED, TimeCycle.BEGINNING, activities);

    private List<BehaviorChangeInterventionBlockInstance> blocks = List.of(blockInstance);

    private BehaviorChangeInterventionPhaseInstance phaseInstance = new BehaviorChangeInterventionPhaseInstance(
            ExecutionStatus.STALLED, blockInstance, blocks, modules);

    private List<BehaviorChangeInterventionPhaseInstance> phases = List.of(phaseInstance);

    private BehaviorChangeInterventionInstance bciInstance = new BehaviorChangeInterventionInstance(ExecutionStatus.UNKNOWN,
            patient, phaseInstance, phases);

    private static final String url = "/behaviorchangeinterventioninstance";

    @BeforeEach
    @Override
    public void setUp() {
        patient.setId(1L);
        blockInstance.setId(2L);
        moduleInstance.setId(3L);
        phaseInstance.setId(4L);
        bciInstance.setId(5L);
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
        BehaviorChangeInterventionInstance updated = new BehaviorChangeInterventionInstance(ExecutionStatus.READY,
                bciInstance.getPatient(), bciInstance.getCurrentPhase(), bciInstance.getActivities());
        updated.setId(phaseInstance.getId());

        when(bciInstanceRepository.save(updated)).thenReturn(updated);
        when(bciInstanceRepository.findById(updated.getId())).thenReturn(Optional.of(updated));
        performUpdateRequest(url, updated, "$.status", updated.getStatus().toString());
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
        performGetRequest(url, "$[0].id", bciInstance.getId());
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
    void testFindByActivitiesId() throws Exception {
        when(bciInstanceRepository.findByActivitiesId(bciInstance.getActivities().getFirst().getId())).thenReturn(Collections.singletonList(bciInstance));
        performGetRequest(url + "/find/activities/" + bciInstance.getActivities().getFirst().getId(), "$[0].id", bciInstance.getId());
    }
}
