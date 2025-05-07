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
 * This class includes integration tests for CRUD operations supported the controller class, using WebMvcTes, and
 * repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Julien Champagne.
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
        performCreateRequest("/patient", patient);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        Patient patientUpdated = new Patient("Sir Lancelot", "guinevere@gmail.com", "438-111-1212",
                "6 April 457", "Knight", "Camelot, Britain");
        patientUpdated.setId(1L);

        when(patientRepository.save(patientUpdated)).thenReturn(patientUpdated);
        when(patientRepository.findById(patientUpdated.getId())).thenReturn(Optional.of(patientUpdated));

        performUpdateRequest("/patient", patientUpdated, "$.occupation",
                patientUpdated.getOccupation());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/patient/" + patient.getId(), patient);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        //Mock behavior for patientRepository.findAll().
        when(patientRepository.findAll()).thenReturn(Collections.singletonList(patient));

        //Perform a GET request to test the controller.
        performGetRequest("/patient", "$[0].id", 1);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        //Mock behavior for patientRepository.findById()
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));

        //Perform a GET request to test the controller
        performGetRequest("/patient/find/" + patient.getId(), "$.id", patient.getId());
    }

    @Test
    void testFindByName() throws Exception {
        //Mock behavior for patientRepository.findByName().
        when(patientRepository.findByName(patient.getName())).thenReturn(Collections.singletonList(patient));

        //Perform a GET request to test the controller.
        performGetRequest("/patient/find/name/" + patient.getName(),
                "$[0].name", patient.getName());
    }

    @Test
    void testFindByEmail() throws Exception {
        //Mock behavior for patientRepository.findByEmail().
        when(patientRepository.findByEmail(patient.getEmail())).thenReturn(Collections.singletonList(patient));

        //Perform a GET request to test the controller.
        performGetRequest("/patient/find/email/" + patient.getEmail(),
                "$[0].email", patient.getEmail());
    }

    @Test
    void testFindByContactInformation() throws Exception {
        //Mock behavior for patientRepository.findByContactInformation().
        when(patientRepository.findByContactInformation(patient.getContactInformation())).thenReturn(Collections.singletonList(patient));

        //Perform a GET request to test the controller.
        performGetRequest("/patient/find/contactInformation/" + patient.getContactInformation(),
                "$[0].contactInformation", patient.getContactInformation());
    }

    @Test
    void testFindByBirthdate() throws Exception {
        //Mock behavior for patientRepository.findByBirthdate().
        when(patientRepository.findByBirthdate(patient.getBirthdate())).thenReturn(Collections.singletonList(patient));

        //Perform a GET request to test the controller.
        performGetRequest("/patient/find/birthdate/" + patient.getBirthdate(),
                "$[0].birthdate", patient.getBirthdate());
    }

    @Test
    void testFindByOccupation() throws Exception {
        //Mock behavior for patientRepository.findByOccupation().
        when(patientRepository.findByOccupation(patient.getOccupation())).thenReturn(Collections.singletonList(patient));

        //Perform a GET request to test the controller.
        performGetRequest("/patient/find/occupation/" + patient.getOccupation(),
                "$[0].occupation", patient.getOccupation());
    }

    @Test
    void testFindByAddress() throws Exception {
        //Mock behavior for patientRepository.findByAddress().
        when(patientRepository.findByAddress(patient.getAddress())).thenReturn(Collections.singletonList(patient));

        //Perform a GET request to test the controller.
        performGetRequest("/patient/find/address/" + patient.getAddress(),
                "$[0].address", patient.getAddress());
    }

    @Test
    void testFindByPatientMedicalFile() throws Exception {
        //Mock behavior for patientRepository.findByPatientMedicalFile().
        when(patientRepository.findAll()).thenReturn(Collections.singletonList(patient));
        when(patientRepository.findByPatientMedicalFile(patient.getMedicalFile().getId())).thenReturn(patient);

        //Perform a GET request to test the controller.
        performGetRequest("/patient/find/patientmedicalfile/" + patient.getMedicalFile().getId(),
                "$.medicalFile.id", patient.getMedicalFile().getId());
    }

    @Test
    void testFindByMedicalHistory() throws Exception {
        //Mock behavior for patientRepository.findByMedicalHistory().
        when(patientRepository.findAll()).thenReturn(Collections.singletonList(patient));
        when(patientRepository.findByMedicalHistory(patient.getMedicalFile().getMedicalHistory())).thenReturn(Collections.singletonList(patient));

        //Perform a GET request to test the controller.
        performGetRequest("/patient/find/patientmedicalfile/medicalhistory/" + patient.getMedicalFile().getMedicalHistory(),
                "$[0].medicalFile.medicalHistory", patient.getMedicalFile().getMedicalHistory());
    }
}
