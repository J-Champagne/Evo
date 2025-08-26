package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.BehaviorChangeInterventionInstanceController;
import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionPhaseRepository;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionRepository;
import ca.uqam.latece.evo.server.core.repository.instance.*;
import ca.uqam.latece.evo.server.core.request.BCIInstanceRequest;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionInstanceService;
import ca.uqam.latece.evo.server.core.util.DateFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @MockBean
    BehaviorChangeInterventionRepository behaviorChangeInterventionRepository;

    @MockBean
    BehaviorChangeInterventionPhaseRepository behaviorChangeInterventionPhaseRepository;

    private BehaviorChangeIntervention behaviorChangeIntervention = new BehaviorChangeIntervention("My Intervention");

    private BehaviorChangeInterventionPhase behaviorChangeInterventionPhase = new BehaviorChangeInterventionPhase(PHASE_ENTRY_CONDITION,
            PHASE_EXIT_CONDITION);

    private Role role = new Role("Administrator");

    private Patient patient = new Patient("Bob", "bob@gmail.com",
            "222-2222", "1 January 1970", "Student", "1234 Street");

    private Participant participant = new Participant(role, patient);

    private List<Participant> participants = List.of(participant);

    private BCIActivity bciActivity = new BCIActivity("Programming", "Description", ActivityType.BCI_ACTIVITY,
            "ENTRY_CONDITION", "EXIT_CONDITION");

    private BCIActivityInstance activityInstance = new BCIActivityInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
            DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants, bciActivity);

    private List<BCIActivityInstance> activities = List.of(activityInstance);

    private BCIModuleInstance moduleInstance = new BCIModuleInstance(ExecutionStatus.STALLED, OutcomeType.SUCCESSFUL, activities);

    private List<BCIModuleInstance> modules = List.of(moduleInstance);

    private BehaviorChangeInterventionBlock bciBlock = new BehaviorChangeInterventionBlock("ENTRY_CONDITION",
            "EXIT_CONDITION");

    private BehaviorChangeInterventionBlockInstance blockInstance = new BehaviorChangeInterventionBlockInstance(
            ExecutionStatus.STALLED, TimeCycle.BEGINNING, activities, bciBlock);

    private List<BehaviorChangeInterventionBlockInstance> blocks = List.of(blockInstance);

    private BehaviorChangeInterventionPhaseInstance phaseInstance = new BehaviorChangeInterventionPhaseInstance(
            ExecutionStatus.STALLED, blockInstance, blocks, modules, behaviorChangeInterventionPhase);

    private List<BehaviorChangeInterventionPhaseInstance> phases = List.of(phaseInstance);

    private BehaviorChangeInterventionInstance bciInstance = new BehaviorChangeInterventionInstance(ExecutionStatus.UNKNOWN,
            patient, phaseInstance, phases, behaviorChangeIntervention);

    private static final String PHASE_ENTRY_CONDITION = "Intervention Phase ENTRY";
    
    private static final String PHASE_EXIT_CONDITION = "Intervention Phase EXIT";

    private BCIInstanceRequest bciInstanceRequest;

    private static final String URL = "/behaviorchangeinterventioninstance";

    @BeforeEach
    @Override
    public void setUp() {
        patient.setId(1L);
        blockInstance.setId(2L);
        moduleInstance.setId(3L);
        phaseInstance.setId(4L);
        behaviorChangeIntervention.setId(5L);
        behaviorChangeInterventionPhase.setId(6L);
        behaviorChangeInterventionPhase.setBehaviorChangeIntervention(behaviorChangeIntervention);
        bciInstance.setId(7L);
        bciBlock.setId(8L);

        bciInstanceRequest = BCIInstanceRequest.bciInstanceRequestBuilder().
                id(bciInstance.getId()).
                status(bciInstance.getStatus()).
                patient(patient).
                build();

        when(patientRepository.save(patient)).thenReturn(patient);
        when(bciBlockInstanceRepository.save(blockInstance)).thenReturn(blockInstance);
        when(bciModuleInstanceRepository.save(moduleInstance)).thenReturn(moduleInstance);
        when(bciPhaseInstanceRepository.save(phaseInstance)).thenReturn(phaseInstance);
        when(behaviorChangeInterventionRepository.save(behaviorChangeIntervention)).thenReturn(behaviorChangeIntervention);
        when(behaviorChangeInterventionPhaseRepository.save(behaviorChangeInterventionPhase)).thenReturn(behaviorChangeInterventionPhase);
        when(bciInstanceRepository.save(bciInstance)).thenReturn(bciInstance);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(URL, bciInstance);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        BehaviorChangeInterventionInstance updated = new BehaviorChangeInterventionInstance(ExecutionStatus.READY,
                bciInstance.getPatient(), bciInstance.getCurrentPhase(), bciInstance.getActivities());
        updated.setId(bciInstance.getId());

        when(bciInstanceRepository.save(updated)).thenReturn(updated);
        when(bciInstanceRepository.findById(updated.getId())).thenReturn(Optional.of(updated));
        performUpdateRequest(URL, updated, "$.status", updated.getStatus().toString());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(URL + "/" + bciInstance.getId(), bciInstance);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(bciInstanceRepository.findAll()).thenReturn(Collections.singletonList(bciInstance));
        performGetRequest(URL, "$[0].id", bciInstance.getId());
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(bciInstanceRepository.findById(bciInstance.getId())).thenReturn(Optional.of(bciInstance));
        performGetRequest(URL + "/find/" + bciInstance.getId(), "$.id", bciInstance.getId());
    }

    @Test
    void testFindByPatientId() throws Exception {
        when(bciInstanceRepository.findByPatientId(bciInstance.getPatient().getId())).thenReturn(Collections.singletonList(bciInstance));
        performGetRequest(URL + "/find/patient/" + bciInstance.getPatient().getId(), "$[0].id", bciInstance.getId());
    }

    @Test
    void testFindByCurrentPhaseId() throws Exception {
        when(bciInstanceRepository.findByCurrentPhaseId(bciInstance.getCurrentPhase().getId())).thenReturn(Collections.singletonList(bciInstance));
        performGetRequest(URL + "/find/currentphase/" + bciInstance.getCurrentPhase().getId(), "$[0].id", bciInstance.getId());
    }

    @Test
    void testFindByActivitiesId() throws Exception {
        when(bciInstanceRepository.findByActivitiesId(bciInstance.getActivities().getFirst().getId())).thenReturn(Collections.singletonList(bciInstance));
        performGetRequest(URL + "/find/activities/" + bciInstance.getActivities().getFirst().getId(), "$[0].id", bciInstance.getId());
    }

    private BehaviorChangeInterventionInstance instanceBuilder(BehaviorChangeInterventionPhaseInstance currentPhase){
        // Create a new mutable list instead of using List.of()
        List<BehaviorChangeInterventionPhaseInstance> mutablePhases= new ArrayList<>(phases);
        mutablePhases.add(currentPhase);

        BehaviorChangeInterventionInstance updated = new BehaviorChangeInterventionInstance(ExecutionStatus.IN_PROGRESS,
                patient, currentPhase, mutablePhases);
        updated.setId(bciInstance.getId());

        when(bciInstanceRepository.save(updated)).thenReturn(updated);
        when(bciInstanceRepository.findById(updated.getId())).thenReturn(Optional.of(updated));

        return updated;
    }

    @Test
    void testChangeCurrentPhase() throws Exception {
        BehaviorChangeInterventionPhaseInstance currentPhase = new BehaviorChangeInterventionPhaseInstance(
                ExecutionStatus.STALLED, phaseInstance.getCurrentBlock(), phaseInstance.getActivities(), phaseInstance.getModules(), behaviorChangeInterventionPhase);
        currentPhase.setId(10L);
        when(bciPhaseInstanceRepository.save(currentPhase)).thenReturn(currentPhase);

        BehaviorChangeInterventionInstance updated = instanceBuilder(currentPhase);

        performGetRequest(URL + "/changeCurrentPhase/bciInstance", updated, "$.currentPhase.id", 10L);
    }

    @Test
    void testChangeCurrentPhaseWithPhaseIdAndCurrentPhase() throws Exception {
        BehaviorChangeInterventionPhaseInstance currentPhase = new BehaviorChangeInterventionPhaseInstance(
                ExecutionStatus.STALLED, phaseInstance.getCurrentBlock(), phaseInstance.getActivities(), phaseInstance.getModules(), behaviorChangeInterventionPhase);
        currentPhase.setId(11L);

        BehaviorChangeInterventionInstance updated = instanceBuilder(currentPhase);

        performGetRequest(URL + "/changeCurrentPhase/" + updated.getId() + "/currentPhase", currentPhase,
                "$.currentPhase.id", currentPhase.getId());
    }

    @Test
    void testFindByBehaviorChangeInterventionId() throws Exception {
        when(bciInstanceRepository.findByBehaviorChangeInterventionId(bciInstance.getBehaviorChangeIntervention().getId())).thenReturn(Collections.singletonList(bciInstance));
        performGetRequest(URL + "/find/behaviorchangeintervention/" + bciInstance.getBehaviorChangeIntervention().getId(), "$[0].behaviorChangeIntervention.id", bciInstance.getBehaviorChangeIntervention().getId());
    }

    @Test
    void testFindByIdAndStatusAndPatientId() throws Exception {
        when(bciInstanceRepository.findByIdAndStatusAndPatientId(bciInstanceRequest.getId(), bciInstanceRequest.getStatus(), bciInstanceRequest.resolvePatientId())).thenReturn(bciInstance);
        performGetRequest(URL + "/find/bciinstanceidstatuspatientid/instanceRequest", bciInstanceRequest, "$.id", bciInstance.getId());
    }

    @Test
    void testFindByIdAndStatusAndPatient() throws Exception {
        when(bciInstanceRepository.findByIdAndStatusAndPatient(bciInstanceRequest.getId(), bciInstanceRequest.getStatus(), bciInstanceRequest.getPatient())).thenReturn(bciInstance);
        performGetRequest(URL + "/find/bciinstanceidstatuspatient/instanceRequest", bciInstanceRequest, "$.id", bciInstance.getId());
    }

    @Test
    void testFindByIdAndPatient() throws Exception {
        when(bciInstanceRepository.findByIdAndPatient(bciInstanceRequest.getId(), bciInstanceRequest.getPatient())).thenReturn(bciInstance);
        performGetRequest(URL + "/find/bciinstanceidandpatient/instanceRequest", bciInstanceRequest, "$.id", bciInstance.getId());
    }

    @Test
    void testFindByIdAndPatientId() throws Exception {
        when(bciInstanceRepository.findByIdAndPatientId(bciInstanceRequest.getId(), bciInstanceRequest.resolvePatientId())).thenReturn(bciInstance);
        performGetRequest(URL + "/find/bciinstanceidandpatientid/instanceRequest", bciInstanceRequest, "$.id", bciInstance.getId());
    }
}
