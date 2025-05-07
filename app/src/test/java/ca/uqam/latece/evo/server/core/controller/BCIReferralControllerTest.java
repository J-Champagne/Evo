package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.BCIReferralController;
import ca.uqam.latece.evo.server.core.model.instance.BCIReferral;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientAssessment;
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
 * The BCIReferral Controller test class for the {@link BCIReferralController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Julien Champagne.
 */
@WebMvcTest(controllers = BCIReferralController.class)
@ContextConfiguration(classes = {BCIReferral.class, BCIReferralService.class, BCIReferralController.class})
public class BCIReferralControllerTest extends AbstractControllerTest {
    @MockBean
    private BCIReferralRepository bciReferralRepository;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private PatientAssessmentRepository patientAssessmentRepository;

    @MockBean
    private HealthCareProfessionalRepository healthCareProfessionalRepository;

    private Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
            "3 December 455", "King", "Camelot, Britain");

    private PatientAssessment pa = new PatientAssessment("Ready", patient);

    private HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
            "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare");

    private HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
            "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare");

    private BCIReferral bciReferral = new BCIReferral("In need of change", patient, pa, referringProfessional, behaviorInterventionist);

    @BeforeEach
    @Override
    void setUp() {
        patient.setId(1L);
        pa.setId(1L);
        referringProfessional.setId(1L);
        behaviorInterventionist.setId(1L);
        bciReferral.setId(1L);
        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientAssessmentRepository.save(pa)).thenReturn(pa);
        when(healthCareProfessionalRepository.save(referringProfessional)).thenReturn(referringProfessional);
        when(healthCareProfessionalRepository.save(behaviorInterventionist)).thenReturn(behaviorInterventionist);
        when(bciReferralRepository.save(bciReferral)).thenReturn(bciReferral);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest("/bcireferral", bciReferral);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        BCIReferral bciRefToUpdate = new BCIReferral("Another reason", patient, pa, referringProfessional, behaviorInterventionist);
        bciRefToUpdate.setId(bciReferral.getId());

        when(bciReferralRepository.save(bciRefToUpdate)).thenReturn(bciRefToUpdate);

        // Mock behavior for findById().
        when(bciReferralRepository.findById(bciRefToUpdate.getId())).thenReturn(Optional.of(bciRefToUpdate));
        performUpdateRequest("/bcireferral", bciRefToUpdate, "$.reason", bciRefToUpdate.getReason());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/bcireferral/" + bciReferral.getId(), bciReferral);
    }

    @Override
    @Test
    void testFindAll() throws Exception {
        //Mock behavior for findByPatientAssessment()
        when(bciReferralRepository.findAll()).thenReturn(Collections.singletonList(bciReferral));

        //Perform a GET request to test the controller.
        performGetRequest("/bcireferral", "$[0].id", 1);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        //Mock behavior for findById()
        when(bciReferralRepository.findById(bciReferral.getId())).thenReturn(Optional.of(bciReferral));

        //Perform a GET request to test the controller.
        performGetRequest("/bcireferral/find/" + bciReferral.getId(), "$.id", bciReferral.getId());
    }

    @Test
    void testFindByDate() throws Exception {
        //Mock behavior for findByDate()
        String date = bciReferral.getDate().toString();
        when(bciReferralRepository.findByDate(bciReferral.getDate())).thenReturn(Collections.singletonList(bciReferral));

        //Perform a GET request to test the controller.
        performGetRequest("/bcireferral/find/date/" + date, "$[0].date", date);
    }

    @Test
    void testFindByReason() throws Exception {
        //Mock behavior for findByReason()
        when(bciReferralRepository.findByReason(bciReferral.getReason())).thenReturn(Collections.singletonList(bciReferral));

        //Perform a GET request to test the controller.
        performGetRequest("/bcireferral/find/reason/" + bciReferral.getReason(), "$[0].reason", bciReferral.getReason());
    }

    @Test
    void testFindByPatient() throws Exception {
        //Mock behavior for findByReason()
        when(bciReferralRepository.findByPatient(patient.getId())).thenReturn(Collections.singletonList(bciReferral));

        //Perform a GET request to test the controller.
        performGetRequest("/bcireferral/find/patient/" + patient.getId(), "$[0].id", bciReferral.getId());
    }

    @Test
    void testFindByPatientAssessment() throws Exception {
        //Mock behavior for findByPatientAssessment()
        when(bciReferralRepository.findByPatientAssessment(bciReferral.getPatientAssessment().getId())).thenReturn(bciReferral);

        //Perform a GET request to test the controller.
        performGetRequest("/bcireferral/find/patientassessment/" + bciReferral.getPatientAssessment().getId(),
                "$.patientAssessment.id", bciReferral.getPatientAssessment().getId());
    }

    @Test
    void testFindByReferringProfessional() throws Exception {
        //Mock behavior for findByReferringProfessional()
        when(bciReferralRepository.findByReferringProfessional(bciReferral.getReferringProfessional().getId())).thenReturn(bciReferral);

        //Perform a GET request to test the controller.
        performGetRequest("/bcireferral/find/referringprofessional/" + bciReferral.getReferringProfessional().getId(),
                "$.referringProfessional.id", bciReferral.getReferringProfessional().getId());
    }

    @Test
    void testFindByBehaviorChangeInterventionist() throws Exception {
        //Mock behavior for findByBehaviorChangeInterventionist()
        when(bciReferralRepository.findByBehaviorChangeInterventionist(bciReferral.getBehaviorChangeInterventionist().getId())).thenReturn(bciReferral);

        //Perform a GET request to test the controller.
        performGetRequest("/bcireferral/find/behaviorchangeinterventionist/" + bciReferral.getBehaviorChangeInterventionist().getId(),
                "$.behaviorChangeInterventionist.id", bciReferral.getBehaviorChangeInterventionist().getId());
    }
}
