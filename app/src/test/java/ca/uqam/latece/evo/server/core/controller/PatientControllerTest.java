package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.PatientController;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.repository.instance.PatientMedicalFileRepository;
import ca.uqam.latece.evo.server.core.repository.instance.PatientRepository;
import ca.uqam.latece.evo.server.core.service.instance.PatientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The Patient Controller test class for the {@link PatientController}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations supported the controller class, using WebMvcTes, and repository
 * queries using MockMvc (Mockito).
 *
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = PatientController.class)
@ContextConfiguration(classes = {PatientController.class, PatientService.class, Patient.class})
public class PatientControllerTest extends AbstractControllerTest {
    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private PatientMedicalFileRepository patientMedicalFileRepository;

    private PatientMedicalFile pmf = new PatientMedicalFile("Healthy");

    private Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
            "3 December 455", "King", "Camelot, Britain", pmf);

    private final String url = "/patient";

    @BeforeEach
    @Override
    void setUp() {
        patient.setId(1L);
        pmf.setId(1L);
        when(patientMedicalFileRepository.save(pmf)).thenReturn(pmf);
        when(patientRepository.save(patient)).thenReturn(patient);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(url, patient);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        Patient patientUpdated = new Patient("Sir Lancelot", patient.getEmail(), patient.getContactInformation(),
                patient.getBirthdate(), patient.getOccupation(), patient.getAddress(), patient.getMedicalFile());
        patientUpdated.setId(patient.getId());
        when(patientRepository.save(patientUpdated)).thenReturn(patientUpdated);
        when(patientRepository.findById(patientUpdated.getId())).thenReturn(Optional.of(patientUpdated));

        performUpdateRequest(url, patientUpdated, "$.name", patientUpdated.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(url + "/" + patient.getId(), patient);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(patientRepository.findAll()).thenReturn(Collections.singletonList(patient));

        performGetRequest(url, "$[0].id", patient.getId());
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));

        performGetRequest(url + "/find/" + patient.getId(), "$.id", patient.getId());
    }

    @Test
    void testFindByName() throws Exception {
        when(patientRepository.findByName(patient.getName())).thenReturn(Collections.singletonList(patient));

        performGetRequest(url + "/find/name/" + patient.getName(),"$[0].name", patient.getName());
    }

    @Test
    void testFindByEmail() throws Exception {
        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(patient);

        performGetRequest(url + "/find/email/" + patient.getEmail(),"$.email", patient.getEmail());
    }

    @Test
    void testFindByContactInformation() throws Exception {
        when(patientRepository.findByContactInformation(patient.getContactInformation())).thenReturn(Collections.singletonList(patient));

        performGetRequest(url + "/find/contactinformation/" + patient.getContactInformation(),
                "$[0].contactInformation", patient.getContactInformation());
    }

    @Test
    void testFindByBirthdate() throws Exception {
        when(patientRepository.findByBirthdate(patient.getBirthdate())).thenReturn(Collections.singletonList(patient));

        performGetRequest(url + "/find/birthdate/" + patient.getBirthdate(), "$[0].birthdate", patient.getBirthdate());
    }

    @Test
    void testFindByOccupation() throws Exception {
        when(patientRepository.findByOccupation(patient.getOccupation())).thenReturn(Collections.singletonList(patient));

        performGetRequest(url + "/find/occupation/" + patient.getOccupation(),"$[0].occupation", patient.getOccupation());
    }

    @Test
    void testFindByAddress() throws Exception {
        when(patientRepository.findByAddress(patient.getAddress())).thenReturn(Collections.singletonList(patient));

        performGetRequest(url + "/find/address/" + patient.getAddress(), "$[0].address", patient.getAddress());
    }

    @Test
    void testFindByMedicalFile() throws Exception {
        when(patientRepository.findByMedicalFile(patient.getMedicalFile())).thenReturn(patient);

        performGetRequest(url + "/find/patientmedicalfile", pmf, "$.id", patient.getId());
    }

    @Test
    void testFindByMedicalFileId() throws Exception {
        when(patientRepository.findByMedicalFileId(patient.getMedicalFile().getId())).thenReturn(patient);

        performGetRequest(url + "/find/patientmedicalfile/" + patient.getMedicalFile().getId(),"$.id",
                patient.getId());
    }
}
