package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.service.instance.PatientMedicalFileService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link PatientMedicalFile}.
 * Contains tests for CRUD operations and other repository queries using a PostgreSQL database in a containerized setup.
 * @author Julien Champagne.
 */
@ContextConfiguration(classes = {PatientMedicalFile.class, PatientMedicalFileService.class})
public class PatientMedicalFileServiceTest extends AbstractServiceTest {
    @Autowired
    PatientMedicalFileService patientMedicalFileService;

    /**
     * Tests save() of PatientMedicalFileService
     * Verifies if a new PatientMedicalFile entity can be persisted into the database.
     */
    @Test
    @Override
    void testSave() {
        PatientMedicalFile medicalFile = new PatientMedicalFile("Healthy");

        PatientMedicalFile medicalFileSaved = patientMedicalFileService.save(medicalFile);
        assert medicalFileSaved.getId() > 0;
    }

    /**
     * Tests update() for the PatientMedicalFileService.
     * Verifies that an existing PatientMedicalFile entity can be updated with new attributes.
     */
    @Test
    @Override
    void testUpdate() {
        PatientMedicalFile medicalFile = new PatientMedicalFile("Healthy");

        PatientMedicalFile medicalFileSaved = patientMedicalFileService.save(medicalFile);
        medicalFileSaved.setMedicalHistory("Sick");
        PatientMedicalFile medicalFileUpdated = patientMedicalFileService.update(medicalFileSaved);

        assertEquals(medicalFileSaved.getId(), medicalFileUpdated.getId());
        assertEquals(medicalFileUpdated.getMedicalHistory(), medicalFileSaved.getMedicalHistory());
    }

    /**
     * Tests testFindById() of the PatientMedicalFileService.
     * Verifies that a PatientMedicalFile can be successfully retrieved by its id.
     */
    @Test
    @Override
    void testFindById() {
        PatientMedicalFile medicalFile = new PatientMedicalFile("Healthy");

        PatientMedicalFile medicalFileSaved = patientMedicalFileService.save(medicalFile);
        PatientMedicalFile medicalFileFound = patientMedicalFileService.findById(medicalFile.getId());

        assertEquals(medicalFileSaved.getId(), medicalFileFound.getId());
    }

    /**
     * Tests findByDate() of PatientMedicalFileService.
     * Verifies that PatientMedicalFile entities can be successfully retrieved by their date of creation.
     */
    @Test
    void testFindByDate() {
        PatientMedicalFile medicalFile = new PatientMedicalFile("Healthy");

        PatientMedicalFile medicalFileSaved = patientMedicalFileService.save(medicalFile);
        List<PatientMedicalFile> results = patientMedicalFileService.findByDate(medicalFileSaved.getDate());

        assertFalse(results.isEmpty());
        assertEquals(medicalFileSaved.getId(), results.get(0).getId());
    }

    /**
     * Tests findByMedicalHistory() of PatientMedicalFileService.
     * Verifies that PatientMedicalFileService entities can be successfully retrieved by their medicalHistory.
     */
    @Test
    void testFindByMedicalHistory() {
        PatientMedicalFile medicalFile = new PatientMedicalFile("Healthy");

        PatientMedicalFile medicalFileSaved = patientMedicalFileService.save(medicalFile);
        List<PatientMedicalFile> results = patientMedicalFileService.findByMedicalHistory(medicalFile.getMedicalHistory());

        assertFalse(results.isEmpty());
        assertEquals(medicalFileSaved.getId(), results.get(0).getId());
    }

    /**
     * Tests deleteById() of PatientMedicalFileService.
     * This method ensures that an PatientMedicalFile entity can be successfully deleted
     * from the database by its ID.
     * <p>
     * @Asserts EntityNotFoundException is thrown when searching for the deleted PatientMedicalFileService.
     */
    @Test
    @Override
    void testDeleteById() {
        PatientMedicalFile medicalFile = new PatientMedicalFile("Healthy");

        // Persist and delete the entity
        PatientMedicalFile medicalFileSaved = patientMedicalFileService.save(medicalFile);
        patientMedicalFileService.deleteById(medicalFileSaved.getId());

        // EntityNotFoundException should be thrown by a failing findById()
        Exception e = assertThrows(EntityNotFoundException.class, () -> patientMedicalFileService.
                findById(medicalFileSaved.getId()));
    }

    /**
     * Tests findAll() of PatientMedicalFileService.
     * Verifies that all PatientMedicalFile entities can be successfully retrieved.
     */
    @Test
    @Override
    void testFindAll() {
        PatientMedicalFile medicalFile = new PatientMedicalFile("Healthy");
        PatientMedicalFile medicalFile2 = new PatientMedicalFile("Sick");

        PatientMedicalFile medicalFileSaved = patientMedicalFileService.save(medicalFile);
        PatientMedicalFile medicalFileSaved2 = patientMedicalFileService.save(medicalFile2);
        List<PatientMedicalFile> results = patientMedicalFileService.findAll();

        assertEquals(2, results.size());
        assertEquals(medicalFileSaved.getId(), results.get(0).getId());
        assertEquals(medicalFileSaved2.getId(), results.get(1).getId());
    }
}
