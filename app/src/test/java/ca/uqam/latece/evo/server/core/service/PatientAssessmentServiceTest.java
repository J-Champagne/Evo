package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientAssessment;
import ca.uqam.latece.evo.server.core.service.instance.PatientAssessmentService;
import ca.uqam.latece.evo.server.core.service.instance.PatientService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link PatientAssessment}.
 * Contains tests for CRUD operations and other repository queries using a PostgreSQL database in a containerized setup.
 * @author Julien Champagne.
 */
@ContextConfiguration(classes = {PatientAssessment.class, PatientAssessmentService.class})
public class PatientAssessmentServiceTest extends AbstractServiceTest {
    @Autowired
    PatientAssessmentService patientAssessmentService;

    @Autowired
    private PatientService patientService;

    /**
     * Tests save() of PatientAssessmentService
     * Verifies if a new PatientAssessment entity can be persisted into the database.
     */
    @Test
    @Override
    void testSave() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Not Ready", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        assert paSaved.getId() > 0;
    }

    /**
     * Tests update() for the PatientAssessmentService.
     * Verifies that an existing PatientAssessment entity can be updated with new attributes.
     */
    @Test
    @Override
    void testUpdate() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Not Ready", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        paSaved.setAssessment("Ready");
        PatientAssessment paUpdated = patientAssessmentService.update(paSaved);

        assertEquals(paSaved.getId(), paUpdated.getId());
        assertEquals(paSaved.getAssessment(), paUpdated.getAssessment());
    }

    /**
     * Tests testFindById() of the PatientAssessmentService.
     * Verifies that a PatientAssessment can be successfully retrieved by its id.
     */
    @Test
    @Override
    void testFindById() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Not Ready", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        PatientAssessment paFound = patientAssessmentService.findById(paSaved.getId());
        assertEquals(paSaved.getId(), paFound.getId());
    }

    /**
     * Tests findByDate() of PatientAssessmentService.
     * Verifies that PatientAssessment entities can be successfully retrieved by their date of creation.
     */
    @Test
    void testFindByDate() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Not Ready", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        List<PatientAssessment> result = patientAssessmentService.findByDate(paSaved.getDate());
        assertFalse(result.isEmpty());
        assertEquals(paSaved.getId(), result.get(0).getId());
    }

    /**
     * Tests testFindByPatient() of the PatientAssessmentService.
     * Verifies that a PatientAssessment can be successfully retrieved by its patient id.
     */
    @Test
    void testFindByPatient() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Not Ready", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        List<PatientAssessment> result = patientAssessmentService.findByPatient(paSaved.getPatient().getId());
        assertFalse(result.isEmpty());
        assertEquals(paSaved.getId(), result.get(0).getId());
    }

    /**
     * Tests deleteById() of PatientAssessmentService.
     * This method ensures that an PatientAssessment entity can be successfully deleted
     * from the database by its ID.
     * <p>
     * @Asserts EntityNotFoundException is thrown when searching for the deleted PatientAssessmentService.
     */
    @Test
    @Override
    void testDeleteById() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Not Ready", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        patientAssessmentService.deleteById(paSaved.getId());

        // EntityNotFoundException should be thrown by a failing findById()
        Exception e = assertThrows(EntityNotFoundException.class, () -> patientAssessmentService.
                findById(paSaved.getId()));
    }

    /**
     * Tests findAll() of PatientAssessmentService.
     * Verifies that all PatientAssessment entities can be successfully retrieved.
     */
    @Test
    @Override
    void testFindAll() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Not Ready", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);
        PatientAssessment pa2 = new PatientAssessment("Problematic", patientSaved);
        PatientAssessment paSaved2 = patientAssessmentService.save(pa2);

        List<PatientAssessment> result = patientAssessmentService.findAll();
        assertFalse(result.isEmpty());
        assertEquals(paSaved.getId(), result.get(0).getId());
        assertEquals(paSaved2.getId(), result.get(1).getId());
    }
}
