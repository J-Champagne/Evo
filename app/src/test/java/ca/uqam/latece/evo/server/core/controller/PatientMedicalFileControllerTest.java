package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.PatientMedicalFileController;
import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.repository.instance.PatientMedicalFileRepository;
import ca.uqam.latece.evo.server.core.service.instance.PatientMedicalFileService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Tests methods found in PatientMedicalFileController using WebMvcTest, and repository queries using MockMvc (Mockito).
 * @author Julien Champagne.
 */
@WebMvcTest(controllers = PatientMedicalFileController.class)
@ContextConfiguration(classes = {PatientMedicalFile.class, PatientMedicalFileService.class, PatientMedicalFileController.class})
public class PatientMedicalFileControllerTest extends AbstractControllerTest {
    @MockitoBean
    private PatientMedicalFileRepository patientMedicalFileRepository;

    private PatientMedicalFile medicalFile = new PatientMedicalFile("Healthy");

    private final String url = "/patientmedicalfile";

    @BeforeEach
    @Override
    void setUp() {
        medicalFile.setId(1L);
        when(patientMedicalFileRepository.save(medicalFile)).thenReturn(medicalFile);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(url, medicalFile);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        medicalFile.setMedicalHistory("Sick");
        when(patientMedicalFileRepository.save(medicalFile)).thenReturn(medicalFile);
        when(patientMedicalFileRepository.findById(medicalFile.getId())).thenReturn(Optional.of(medicalFile));

        performUpdateRequest(url, medicalFile,"$.medicalHistory",
                medicalFile.getMedicalHistory());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(url + "/" + medicalFile.getId(), medicalFile);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(patientMedicalFileRepository.findAll()).thenReturn(Collections.singletonList(medicalFile));

        performGetRequest(url,"$[0].id", 1);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(patientMedicalFileRepository.findById(medicalFile.getId())).thenReturn(Optional.of(medicalFile));

        performGetRequest(url + "/find/" + medicalFile.getId(), "$.id", medicalFile.getId());
    }

    @Test
    void testFindByDate() throws Exception {
        String date = medicalFile.getDate().toString();
        when(patientMedicalFileRepository.findByDate(medicalFile.getDate())).thenReturn(Collections.singletonList(medicalFile));

        performGetRequest(url + "/find/date/" + date,
                "$[0].date", date);
    }

    @Test
    void testFindByMedicalHistory() throws Exception {
        when(patientMedicalFileRepository.findByMedicalHistory(medicalFile.getMedicalHistory())).thenReturn(Collections.singletonList(medicalFile));

        performGetRequest(url + "/find/medicalhistory/" + medicalFile.getMedicalHistory(),
                "$[0].medicalHistory", medicalFile.getMedicalHistory());
    }
}
