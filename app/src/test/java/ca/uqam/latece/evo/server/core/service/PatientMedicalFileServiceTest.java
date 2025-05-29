package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.service.instance.PatientMedicalFileService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods found in PatientMedicalFileService in a containerized setup.
 * @author Julien Champagne.
 */
@ContextConfiguration(classes = {PatientMedicalFile.class, PatientMedicalFileService.class})
public class PatientMedicalFileServiceTest extends AbstractServiceTest {
    @Autowired
    private PatientMedicalFileService patientMedicalFileService;

    private PatientMedicalFile medicalFileSaved;

    @BeforeEach
    void setUp() {
        medicalFileSaved = patientMedicalFileService.create(new PatientMedicalFile("Healthy"));
    }

    @Test
    @Override
    void testSave() {
        assert medicalFileSaved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        medicalFileSaved.setMedicalHistory("Sick");
        PatientMedicalFile medicalFileUpdated = patientMedicalFileService.update(medicalFileSaved);

        assertEquals(medicalFileSaved.getId(), medicalFileUpdated.getId());
        assertEquals(medicalFileSaved.getMedicalHistory(), medicalFileUpdated.getMedicalHistory());
    }

    @Test
    @Override
    void testDeleteById() {
        patientMedicalFileService.deleteById(medicalFileSaved.getId());

        assertThrows(EntityNotFoundException.class, () -> patientMedicalFileService.
                findById(medicalFileSaved.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        patientMedicalFileService.create(new PatientMedicalFile("Very Healthy"));
        List<PatientMedicalFile> results = patientMedicalFileService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    @Override
    void testFindById() {
        PatientMedicalFile medicalFileFound = patientMedicalFileService.findById(medicalFileSaved.getId());

        assertEquals(medicalFileSaved.getId(), medicalFileFound.getId());
    }

    @Test
    void testFindByDate() {
        List<PatientMedicalFile> results = patientMedicalFileService.findByDate(medicalFileSaved.getDate());

        assertFalse(results.isEmpty());
        assertEquals(medicalFileSaved.getId(), results.get(0).getId());
        assertEquals(medicalFileSaved.getDate(), results.get(0).getDate());
    }

    @Test
    void testFindByMedicalHistory() {
        List<PatientMedicalFile> results = patientMedicalFileService.findByMedicalHistory(medicalFileSaved.getMedicalHistory());

        assertFalse(results.isEmpty());
        assertEquals(medicalFileSaved.getId(), results.get(0).getId());
        assertEquals(medicalFileSaved.getMedicalHistory(), results.get(0).getMedicalHistory());
    }
}
