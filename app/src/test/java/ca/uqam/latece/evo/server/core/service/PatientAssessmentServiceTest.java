package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientAssessment;
import ca.uqam.latece.evo.server.core.service.instance.PatientAssessmentService;
import ca.uqam.latece.evo.server.core.service.instance.PatientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link PatientAssessmentService}, responsible for testing its various functionalities. This
 * class includes integration tests for CRUD operations and other repository queries using a PostgreSQL database in a
 * containerized setup.
 *
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {PatientAssessment.class, PatientAssessmentService.class})
public class PatientAssessmentServiceTest extends AbstractServiceTest {
    @Autowired
    private PatientAssessmentService patientAssessmentService;

    @Autowired
    private PatientService patientService;

    private Patient patientSaved;

    private PatientAssessment paSaved;

    @BeforeEach
    void setUp() {
        patientSaved = patientService.create(new Patient("Arthur Pendragon", "kingarthur@gmail.com",
                "438-333-3333","3 December 455", "King", "Camelot, Britain"));
        paSaved = patientAssessmentService.create(new PatientAssessment("Good to go", patientSaved));
    }

    @Test
    @Override
    void testSave() {
        assert paSaved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        paSaved.setAssessment("Not ready");
        PatientAssessment paUpdated = patientAssessmentService.update(paSaved);

        assertEquals(paSaved.getId(), paUpdated.getId());
        assertEquals(paSaved.getAssessment(), paUpdated.getAssessment());
    }

    @Test
    @Override
    void testDeleteById() {
        patientAssessmentService.deleteById(paSaved.getId());

        assertFalse(patientAssessmentService.existsById(paSaved.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        patientAssessmentService.create(new PatientAssessment("Not ready", patientSaved));
        List<PatientAssessment> result = patientAssessmentService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    @Override
    void testFindById() {
        PatientAssessment paFound = patientAssessmentService.findById(paSaved.getId());
        assertEquals(paSaved.getId(), paFound.getId());
    }

    @Test
    void testFindByDate() {
        List<PatientAssessment> result = patientAssessmentService.findByDate(paSaved.getDate());

        assertFalse(result.isEmpty());
        assertEquals(paSaved.getId(), result.getFirst().getId());
        assertEquals(paSaved.getDate(), result.getFirst().getDate());
    }

    @Test
    void testFindByPatient() {
        List<PatientAssessment> result = patientAssessmentService.findByPatient(paSaved.getPatient());

        assertFalse(result.isEmpty());
        assertEquals(paSaved.getId(), result.getFirst().getId());
        assertEquals(paSaved.getPatient().getId(), result.getFirst().getPatient().getId());
    }

    @Test
    void testFindByPatientId() {
        List<PatientAssessment> result = patientAssessmentService.findByPatientId(paSaved.getPatient().getId());

        assertFalse(result.isEmpty());
        assertEquals(paSaved.getId(), result.getFirst().getId());
        assertEquals(paSaved.getPatient().getId(), result.getFirst().getPatient().getId());
    }
}
