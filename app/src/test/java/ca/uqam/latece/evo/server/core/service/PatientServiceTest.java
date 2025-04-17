package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.service.instance.PatientMedicalFileService;
import ca.uqam.latece.evo.server.core.service.instance.PatientService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link Patient}.
 * Contains tests for CRUD operations and other repository queries using a PostgreSQL database in a containerized setup.
 * @author Julien Champagne.
 */
@ContextConfiguration(classes = {Patient.class, PatientService.class})
public class PatientServiceTest extends AbstractServiceTest {
    @Autowired
    PatientService patientService;

    @Autowired
    PatientMedicalFileService patientMedicalFileService;

    /**
     * Tests save() of PatientService
     * Verifies if a new Patient entity can be persisted into the database.
     */
    @Test
    @Override
    void testSave() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);
        Patient patientSaved = patientService.save(patient);

        assert patientSaved.getId() > 0;
    }

    /**
     * Tests update() for the PatientService.
     * Verifies that an existing Patient entity can be updated with new attributes.
     */
    @Test
    @Override
    void testUpdate() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);
        Patient patientSaved = patientService.save(patient);

        patientSaved.setOccupation("Squire");
        Patient patientUpdated = patientService.update(patientSaved);

        assertEquals(patientSaved.getId(), patientUpdated.getId());
        assertEquals(patientUpdated.getOccupation(), patientSaved.getOccupation());
    }

    /**
     * Tests deleteById() of Patient.
     * This method ensures that a Patient entity can be successfully deleted
     * from the database by its ID.
     * <p>
     * @Asserts EntityNotFoundException is thrown when searching for the deleted Patient.
     */
    @Test
    @Override
    void testDeleteById() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);

        Patient patientSaved = patientService.save(patient);

        patientService.deleteById(patientSaved.getId());

        // EntityNotFoundException should be thrown by a failing findById()
        Exception e = assertThrows(EntityNotFoundException.class, () -> patientService.
                findById(patientSaved.getId()));
    }

    /**
     * Tests findAll() of Patient.
     * Verifies that all Patient entities can be successfully retrieved.
     */
    @Test
    @Override
    void testFindAll() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);
        PatientMedicalFile pmf2 = new PatientMedicalFile("Sick");
        PatientMedicalFile pmfSaved2 = patientMedicalFileService.save(pmf2);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);
        Patient patient2 = new Patient("Sir Lancelot", "guinevere@gmail.com", "438-111-1212",
                "6 April 457", "Knight", "Camelot, Britain", pmfSaved2);
        Patient patientSaved = patientService.save(patient);
        Patient patientSaved2 = patientService.save(patient2);

        List<Patient> results = patientService.findAll();
        assertEquals(2, results.size());
        assertEquals(patientSaved.getId(), results.get(0).getId());
        assertEquals(patientSaved2.getId(), results.get(1).getId());
    }

    /**
     * Tests testFindById() of the PatientService.
     * Verifies that a Patient can be successfully retrieved by its id.
     */
    @Test
    @Override
    void testFindById() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);
        Patient patientSaved = patientService.save(patient);
        Patient patientFound = patientService.findById(patient.getId());

        assertEquals(patientSaved.getId(), patientFound.getId());
    }

    /**
     * Tests findByName() of PatientService.
     * Verifies that Patient entities can be successfully retrieved by their name.
     */
    @Test
    void findByName() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);
        Patient patientSaved = patientService.save(patient);
        List<Patient> results = patientService.findByName(patient.getName());

        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
    }

    /**
     * Tests findByEmail() of PatientService.
     * Verifies that Patient entities can be successfully retrieved by their email.
     */
    @Test
    void findByEmail() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);
        Patient patientSaved = patientService.save(patient);
        List<Patient> results = patientService.findByEmail(patient.getEmail());

        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
    }

    /**
     * Tests findByContactInformation() of PatientService.
     * Verifies that Patient entities can be successfully retrieved by their contactInformation.
     */
    @Test
    void testFindByContactInformation() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);
        Patient patientSaved = patientService.save(patient);

        List<Patient> results = patientService.findByContactInformation(patient.getContactInformation());
        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
    }

    /**
     * Tests findByBirthdate() of PatientService.
     * Verifies that Patient entities can be successfully retrieved by their contactInformation.
     */
    @Test
    void testFindByBirthdate() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);
        Patient patientSaved = patientService.save(patient);

        List<Patient> results = patientService.findByBirthdate(patient.getBirthdate());
        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
    }

    /**
     * Tests findByOccupation() of PatientService.
     * Verifies that Patient entities can be successfully retrieved by their contactInformation.
     */
    @Test
    void testFindByOccupation() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);
        Patient patientSaved = patientService.save(patient);

        List<Patient> results = patientService.findByOccupation(patient.getOccupation());
        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
    }

    /**
     * Tests findByAddress() of PatientService.
     * Verifies that Patient entities can be successfully retrieved by their contactInformation.
     */
    @Test
    void testFindByAddress() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);
        Patient patientSaved = patientService.save(patient);

        List<Patient> results = patientService.findByAddress(patient.getAddress());
        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
    }

    @Test
    void testFindByPatientMedicalFile() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);
        Patient patientSaved = patientService.save(patient);

        Patient result = patientService.findByPatientMedicalFile(pmfSaved.getId());
        assertEquals(result.getMedicalFile().getId(), pmfSaved.getId());
    }

    /**
     * Tests testFindByMedicalHistory() of PatientService.
     * Verifies that Patient entities can be successfully retrieved by the medicalHistory from their PatientMedicalFile.
     */
    @Test
    void testFindByMedicalHistory() {
        PatientMedicalFile pmf = new PatientMedicalFile("Healthy");
        PatientMedicalFile pmfSaved = patientMedicalFileService.save(pmf);
        PatientMedicalFile pmf2 = new PatientMedicalFile("Sick");
        PatientMedicalFile pmfSaved2 = patientMedicalFileService.save(pmf2);

        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain", pmfSaved);
        Patient patient2 = new Patient("Sir Lancelot", "camelot@gmail.com", "222-222-2222",
                "4 April 457", "Knight", "Camelot, Britain", pmfSaved2);
        Patient patientSaved = patientService.save(patient);
        Patient patientSaved2 = patientService.save(patient2);

        List<Patient> results = patientService.findByMedicalHistory("Healthy");
        assertEquals(results.size(), 1);
        assertEquals(patientSaved.getId(), results.get(0).getId());
    }
}
