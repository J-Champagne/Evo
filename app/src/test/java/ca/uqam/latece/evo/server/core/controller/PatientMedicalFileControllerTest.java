package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.PatientMedicalFileController;
import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.repository.instance.PatientMedicalFileRepository;
import ca.uqam.latece.evo.server.core.service.instance.PatientMedicalFileService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = PatientMedicalFileController.class)
@ContextConfiguration(classes = {PatientMedicalFile.class, PatientMedicalFileService.class, PatientMedicalFileController.class})
public class PatientMedicalFileControllerTest extends AbstractControllerTest {
    @MockBean
    private PatientMedicalFileRepository patientMedicalFileRepository;

    private PatientMedicalFile medicalFile = new PatientMedicalFile("Healthy");

    @BeforeEach
    @Override
    void setUp() {
        medicalFile.setId(1L);
        when(patientMedicalFileRepository.save(medicalFile)).thenReturn(medicalFile);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest("/patientmedicalfile", medicalFile);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        PatientMedicalFile medicalFileUpdated = new PatientMedicalFile("Sick");
        medicalFileUpdated.setId(1L);

        when(patientMedicalFileRepository.save(medicalFileUpdated)).thenReturn(medicalFileUpdated);
        when(patientMedicalFileRepository.findById(medicalFileUpdated.getId())).thenReturn(Optional.of(medicalFileUpdated));

        performUpdateRequest("/patientmedicalfile", medicalFileUpdated,"$.medicalHistory",
                medicalFileUpdated.getMedicalHistory());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/patientmedicalfile/" + medicalFile.getId(), medicalFile);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        //Mock behavior for patientMedicalFileRepository.findById()
        when(patientMedicalFileRepository.findById(medicalFile.getId())).thenReturn(Optional.of(medicalFile));

        //Perform a GET request to test the controller
        performGetRequest("/patientmedicalfile/find/" + medicalFile.getId(), "$.id", medicalFile.getId());
    }

    @Test
    void testFindByDate() throws Exception {
        //Mock behavior for patientMedicalFileRepository.findByDate()
        String date = medicalFile.getDate().toString();
        when(patientMedicalFileRepository.findByDate(medicalFile.getDate())).thenReturn(Collections.singletonList(medicalFile));

        //Perform a GET request to test the controller.
        performGetRequest("/patientmedicalfile/find/date/" + date,
                "$[0].date", date);
    }

    @Test
    void testFindByMedicalHistory() throws Exception {
        //Mock behavior for patientMedicalFileRepository.findByMedicalHistory().
        when(patientMedicalFileRepository.findByMedicalHistory(medicalFile.getMedicalHistory())).thenReturn(Collections.singletonList(medicalFile));

        //Perform a GET request to test the controller.
        performGetRequest("/patientmedicalfile/find/medicalhistory/" + medicalFile.getMedicalHistory(),
                "$[0].medicalHistory", medicalFile.getMedicalHistory());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        //Mock behavior for patientMedicalFileRepository.findAll().
        when(patientMedicalFileRepository.findAll()).thenReturn(Collections.singletonList(medicalFile));

        //Perform a GET request to test the controller.
        performGetRequest("/patientmedicalfile","$[0].id", 1);
    }
}
