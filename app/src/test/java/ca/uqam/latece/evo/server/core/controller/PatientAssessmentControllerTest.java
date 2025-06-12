package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.PatientAssessmentController;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientAssessment;
import ca.uqam.latece.evo.server.core.repository.instance.PatientAssessmentRepository;
import ca.uqam.latece.evo.server.core.repository.instance.PatientRepository;
import ca.uqam.latece.evo.server.core.service.instance.PatientAssessmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The Patient Assessment controller test class for the {@link PatientAssessmentController}, responsible for testing its
 * various functionalities. This class includes integration tests for CRUD operations supported the controller class, using
 * WebMvcTes, and repository queries using MockMvc (Mockito).
 *
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = PatientAssessmentController.class)
@ContextConfiguration(classes = {PatientAssessment.class, PatientAssessmentService.class, PatientAssessmentController.class})
public class PatientAssessmentControllerTest extends AbstractControllerTest {
    @MockBean
    private PatientAssessmentRepository patientAssessmentRepository;

    @MockBean
    private PatientRepository patientRepository;

    private Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
            "3 December 455", "King", "Camelot, Britain");

    private PatientAssessment pa = new PatientAssessment("Ready", patient);

    private final String url = "/patientassessment";

    @BeforeEach
    @Override
    void setUp() {
        patient.setId(1L);
        pa.setId(1L);

        when(patientRepository.save(patient)).thenReturn(patient);
        when(patientAssessmentRepository.save(pa)).thenReturn(pa);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(url, pa);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        pa.setAssessment("Not Ready");
        when(patientAssessmentRepository.save(pa)).thenReturn(pa);
        when(patientAssessmentRepository.findById(pa.getId())).thenReturn(Optional.of(pa));

        performUpdateRequest(url, pa, "$.assessment", pa.getAssessment());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(url + "/" + pa.getId(), pa);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(patientAssessmentRepository.findAll()).thenReturn(Collections.singletonList(pa));

        performGetRequest(url, "$[0].id", 1);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(patientAssessmentRepository.findById(pa.getId())).thenReturn(Optional.of(pa));

        performGetRequest(url + "/find/" + pa.getId(), "$.id", pa.getId());
    }

    @Test
    void testFindByDate() throws Exception {
        String date = pa.getDate().toString();
        when(patientAssessmentRepository.findByDate(pa.getDate())).thenReturn(Collections.singletonList(pa));

        performGetRequest(url + "/find/date/" + date, "$[0].date", date);
    }

    @Test
    void testFindByAssessment() throws Exception {
        when(patientAssessmentRepository.findByAssessment(pa.getAssessment())).thenReturn(Collections.singletonList(pa));

        performGetRequest(url + "/find/assessment/" + pa.getAssessment(), "$[0].assessment",
                pa.getAssessment());
    }

    @Test
    void testFindByPatient() throws Exception {
        when(patientAssessmentRepository.findByPatient(pa.getPatient())).thenReturn(Collections.singletonList(pa));

        performGetRequest(url + "/find/patient", pa.getPatient(),"$[0].patient.id",
                pa.getPatient().getId());
    }

    @Test
    void testFindByPatientId() throws Exception {
        when(patientAssessmentRepository.findByPatientId(pa.getPatient().getId())).thenReturn(Collections.singletonList(pa));

        performGetRequest(url + "/find/patient/" + pa.getPatient().getId(), "$[0].patient.id",
                pa.getPatient().getId());
    }
}
