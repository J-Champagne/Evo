package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.service.instance.PatientMedicalFileService;
import ca.uqam.latece.evo.server.core.service.instance.PatientService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods found in ActorService in a containerized setup.
 * @author Julien Champagne.
 */
@ContextConfiguration(classes = {Patient.class, PatientService.class, RoleService.class})
public class PatientServiceTest extends AbstractServiceTest {
    @Autowired
    private PatientService patientService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PatientMedicalFileService patientMedicalFileService;

    private Patient patientSaved;

    private Role roleSaved;

    @BeforeEach
    void setUp() {
        roleSaved = roleService.create(new Role("Administrator"));
        PatientMedicalFile pmf = patientMedicalFileService.create(new PatientMedicalFile("Healthy"));
        patientSaved = patientService.create(new Patient("Bob", "bob@gmail.com", "222-2222",
                roleSaved, "1901-01-01", "Participant", "3333 Street", pmf));
    }

    @Test
    @Override
    void testSave() {
        assert patientSaved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        patientSaved.setAddress("222 Other Street");
        Patient patientUpdated = patientService.update(patientSaved);

        assertEquals(patientSaved.getId(), patientUpdated.getId());
        assertEquals("222 Other Street", patientUpdated.getAddress());
    }

    @Test
    @Override
    void testDeleteById() {
        patientService.deleteById(patientSaved.getId());

        assertThrows(EntityNotFoundException.class, () -> patientService.
                findById(patientSaved.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        patientService.create(new Patient("Bob2", "bob2@gmail.com", "444-4444", roleSaved,
                        "1901-01-01", "Participant", "Brussels"));
        List<Patient> results = patientService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    @Override
    void testFindById() {
        Patient patientFound = patientService.findById(patientSaved.getId());

        assertEquals(patientSaved.getId(), patientFound.getId());
    }

    @Test
    void findByName() {
        List<Patient> results = patientService.findByName(patientSaved.getName());

        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
        assertEquals(patientSaved.getName(), results.get(0).getName());
    }

    @Test
    void findByEmail() {
        Patient patientFound = patientService.findByEmail(patientSaved.getEmail());

        assertEquals(patientSaved.getId(), patientFound.getId());
        assertEquals(patientSaved.getEmail(), patientFound.getEmail());
    }

    @Test
    void testFindByContactInformation() {
        List<Patient> results = patientService.findByContactInformation(patientSaved.getContactInformation());

        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
        assertEquals(patientSaved.getContactInformation(), results.get(0).getContactInformation());
    }

    @Test
    void testFindByRole() {
        List<Patient> results = patientService.findByRole(patientSaved.getRole());

        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
        assertEquals(patientSaved.getRole().getId(), results.get(0).getRole().getId());
    }

    @Test
    void testFindByRoleId() {
        List<Patient> results = patientService.findByRoleId(patientSaved.getRole().getId());

        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
        assertEquals(patientSaved.getRole().getId(), results.get(0).getRole().getId());
    }

    @Test
    void testFindByBirthdate() {
        List<Patient> results = patientService.findByBirthdate(patientSaved.getBirthdate());

        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
        assertEquals(patientSaved.getBirthdate(), results.get(0).getBirthdate());
    }

    @Test
    void testFindByOccupation() {
        List<Patient> results = patientService.findByOccupation(patientSaved.getOccupation());

        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
        assertEquals(patientSaved.getOccupation(), results.get(0).getOccupation());
    }

    @Test
    void testFindByAddress() {
        List<Patient> results = patientService.findByAddress(patientSaved.getAddress());

        assertFalse(results.isEmpty());
        assertEquals(patientSaved.getId(), results.get(0).getId());
        assertEquals(patientSaved.getAddress(), results.get(0).getAddress());
    }

    @Test
    void testFindByPatientMedicalFile() {
        Patient patientFound = patientService.findByPatientMedicalFile(patientSaved.getMedicalFile());

        assertEquals(patientSaved.getId(), patientFound.getId());
        assertEquals(patientSaved.getMedicalFile().getId(), patientFound.getMedicalFile().getId());
    }

    @Test
    void testFindByPatientMedicalFileId() {
        Patient patientFound = patientService.findByPatientMedicalFileId(patientSaved.getMedicalFile().getId());

        assertEquals(patientSaved.getId(), patientFound.getId());
        assertEquals(patientSaved.getMedicalFile().getId(), patientFound.getMedicalFile().getId());
    }
}
