package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.BCIReferralController;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.BCIReferral;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientAssessment;
import ca.uqam.latece.evo.server.core.repository.RoleRepository;
import ca.uqam.latece.evo.server.core.repository.instance.BCIReferralRepository;
import ca.uqam.latece.evo.server.core.repository.instance.HealthCareProfessionalRepository;
import ca.uqam.latece.evo.server.core.repository.instance.PatientAssessmentRepository;
import ca.uqam.latece.evo.server.core.repository.instance.PatientRepository;
import ca.uqam.latece.evo.server.core.service.instance.BCIReferralService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Tests methods found in PatientAssessmentController using WebMvcTest, and repository queries using MockMvc (Mockito).
 * @author Julien Champagne.
 */
@WebMvcTest(controllers = BCIReferralController.class)
@ContextConfiguration(classes = {BCIReferral.class, BCIReferralService.class, BCIReferralController.class})
public class BCIReferralControllerTest extends AbstractControllerTest {
    @MockBean
    private BCIReferralRepository bciReferralRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private PatientAssessmentRepository patientAssessmentRepository;

    @MockBean
    private HealthCareProfessionalRepository healthCareProfessionalRepository;

    private Role role = new Role("Administrator");

    private Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333", role,
            "3 December 455", "King", "Camelot, Britain");

    private PatientAssessment pa = new PatientAssessment("Ready", patient);

    private HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
            "514-222-2222", role, "Chief Painter", "CIUSSS", "Healthcare");

    private HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
            "514-333-3333", role, "Chief Painter", "CIUSSS", "Healthcare");

    private BCIReferral bciReferral = new BCIReferral("In need of change", patient, pa, referringProfessional, behaviorInterventionist);

    private final String url = "/bcireferral";

    @BeforeEach
    @Override
    void setUp() {
        role.setId(1L);
        patient.setId(1L);
        pa.setId(1L);
        referringProfessional.setId(1L);
        behaviorInterventionist.setId(1L);
        bciReferral.setId(1L);

        when(roleRepository.save(role)).thenReturn(role);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientAssessmentRepository.save(pa)).thenReturn(pa);
        when(healthCareProfessionalRepository.save(referringProfessional)).thenReturn(referringProfessional);
        when(healthCareProfessionalRepository.save(behaviorInterventionist)).thenReturn(behaviorInterventionist);
        when(bciReferralRepository.save(bciReferral)).thenReturn(bciReferral);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(url, bciReferral);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        bciReferral.setReason("New reason");
        BCIReferral bciReferralUpdated = new BCIReferral("New Reason", bciReferral.getPatient(), bciReferral.getPatientAssessment(),
                bciReferral.getReferringProfessional(), bciReferral.getBehaviorChangeInterventionist());
        bciReferralUpdated.setId(bciReferral.getId());
        when(bciReferralRepository.save(bciReferralUpdated)).thenReturn(bciReferralUpdated);
        when(bciReferralRepository.findById(bciReferralUpdated.getId())).thenReturn(Optional.of(bciReferralUpdated));

        performUpdateRequest(url, bciReferralUpdated, "$.reason", bciReferralUpdated.getReason());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(url + "/" + bciReferral.getId(), bciReferral);
    }

    @Override
    @Test
    void testFindAll() throws Exception {
        when(bciReferralRepository.findAll()).thenReturn(Collections.singletonList(bciReferral));

        performGetRequest(url, "$[0].id", bciReferral.getId());
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(bciReferralRepository.findById(bciReferral.getId())).thenReturn(Optional.of(bciReferral));

        performGetRequest(url + "/find/" + bciReferral.getId(), "$.id", bciReferral.getId());
    }

    @Test
    void testFindByDate() throws Exception {
        String date = bciReferral.getDate().toString();
        when(bciReferralRepository.findByDate(bciReferral.getDate())).thenReturn(Collections.singletonList(bciReferral));

        performGetRequest(url + "/find/date/" + date, "$[0].date", date);
    }

    @Test
    void testFindByReason() throws Exception {
        when(bciReferralRepository.findByReason(bciReferral.getReason())).thenReturn(Collections.singletonList(bciReferral));

        performGetRequest(url + "/find/reason/" + bciReferral.getReason(), "$[0].reason", bciReferral.getReason());
    }

    @Test
    void testFindByPatient() throws Exception {
        when(bciReferralRepository.findByPatientId(patient.getId())).thenReturn(Collections.singletonList(bciReferral));

        performGetRequest(url + "/find/patient/" + patient.getId(), "$[0].patient.id", bciReferral.getPatient().getId());
    }

    @Test
    void testFindByPatientAssessment() throws Exception {
        when(bciReferralRepository.findByPatientAssessmentId(bciReferral.getPatientAssessment().getId())).
                thenReturn(Collections.singletonList(bciReferral));

        performGetRequest(url + "/find/patientassessment/" + bciReferral.getPatientAssessment().getId(),
                "$[0].patientAssessment.id", bciReferral.getPatientAssessment().getId());
    }

    @Test
    void testFindByReferringProfessional() throws Exception {
        when(bciReferralRepository.findByReferringProfessionalId(bciReferral.getReferringProfessional().getId())).
                thenReturn(Collections.singletonList(bciReferral));

        performGetRequest(url + "/find/referringprofessional/" + bciReferral.getReferringProfessional().getId(),
                "$[0].referringProfessional.id", bciReferral.getReferringProfessional().getId());
    }

    @Test
    void testFindByBehaviorChangeInterventionist() throws Exception {
        when(bciReferralRepository.findByBehaviorChangeInterventionistId(bciReferral.getBehaviorChangeInterventionist().getId())).
                thenReturn(Collections.singletonList(bciReferral));

        performGetRequest(url + "/find/behaviorchangeinterventionist/" + bciReferral.getBehaviorChangeInterventionist().getId(),
                "$[0].behaviorChangeInterventionist.id", bciReferral.getBehaviorChangeInterventionist().getId());
    }
}
