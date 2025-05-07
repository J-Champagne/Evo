package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.instance.BCIReferral;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientAssessment;
import ca.uqam.latece.evo.server.core.service.instance.BCIReferralService;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;
import ca.uqam.latece.evo.server.core.service.instance.PatientAssessmentService;
import ca.uqam.latece.evo.server.core.service.instance.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link BCIReferral}.
 * Contains tests for CRUD operations and other repository queries using a PostgreSQL database in a containerized setup.
 * @author Julien Champagne.
 */
@ContextConfiguration(classes = {BCIReferral.class, BCIReferralService.class})
public class BCIReferralServiceTest extends AbstractServiceTest {
    @Autowired
    BCIReferralService bciReferralService;

    @Autowired
    PatientService patientService;

    @Autowired
    PatientAssessmentService patientAssessmentService;

    @Autowired
    HealthCareProfessionalService healthCareProfessionalService;

    /**
     * Tests save() of BCIReferralService
     * Verifies if a new BCIReferral entity can be persisted into the database.
     */
    @Test
    @Override
    void testSave() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Difficult", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional rpSaved = healthCareProfessionalService.save(referringProfessional);

        HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
                "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional biSaved = healthCareProfessionalService.save(behaviorInterventionist);

        BCIReferral bciRef = new BCIReferral("Reason", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRefSaved = bciReferralService.save(bciRef);

        assert bciRefSaved.getId() > 0;
    }

    /**
     * Tests update() of BCIReferralService
     * Verifies if a new BCIReferral entity can be updated into the database.
     */
    @Test
    @Override
    void testUpdate() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Difficult", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional rpSaved = healthCareProfessionalService.save(referringProfessional);

        HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
                "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional biSaved = healthCareProfessionalService.save(behaviorInterventionist);

        BCIReferral bciRef = new BCIReferral("Reason", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRefSaved = bciReferralService.save(bciRef);

        bciRef.setReason("Reason2");
        BCIReferral bciUpdate = bciReferralService.update(bciRef);

        assertEquals(bciRefSaved.getId(), bciUpdate.getId());
        assertEquals(bciRefSaved.getReason(), bciRef.getReason());
    }

    /**
     * Tests testFindById() of the BCIReferralService.
     * Verifies that a BCIReferral can be successfully retrieved by its id.
     */
    @Test
    @Override
    void testFindById() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Difficult", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional rpSaved = healthCareProfessionalService.save(referringProfessional);

        HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
                "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional biSaved = healthCareProfessionalService.save(behaviorInterventionist);

        BCIReferral bciRef = new BCIReferral("Reason", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRefSaved = bciReferralService.save(bciRef);

        BCIReferral bciRefFound = bciReferralService.findById(bciRef.getId());
        assertEquals(bciRefSaved.getId(), bciRefFound.getId());
    }

    /**
     * Tests findByDate() of BCIReferralService.
     * Verifies that BCIReferral entities can be successfully retrieved by their date of creation.
     */
    @Test
    void testFindByDate() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Difficult", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional rpSaved = healthCareProfessionalService.save(referringProfessional);

        HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
                "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional biSaved = healthCareProfessionalService.save(behaviorInterventionist);

        BCIReferral bciRef = new BCIReferral("Reason", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRefSaved = bciReferralService.save(bciRef);

        List<BCIReferral> result = bciReferralService.findByDate(bciRef.getDate());
        assertFalse(result.isEmpty());
        assertEquals(result.get(0).getId(), bciRef.getId());
    }

    /**
     * Tests findByReason() of BCIReferralService.
     * Verifies that BCIReferral entities can be successfully retrieved by their reason.
     */
    @Test
    void findByReason() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Difficult", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional rpSaved = healthCareProfessionalService.save(referringProfessional);

        HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
                "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional biSaved = healthCareProfessionalService.save(behaviorInterventionist);

        BCIReferral bciRef = new BCIReferral("Reason", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRefSaved = bciReferralService.save(bciRef);

        List<BCIReferral> result = bciReferralService.findByReason(bciRef.getReason());
        assertFalse(result.isEmpty());
        assertEquals(result.get(0).getReason(), bciRef.getReason());
    }

    /**
     * Tests findByPatient() of BCIReferralService.
     * Verifies that BCIReferral entities can be successfully retrieved by their patient.
     */
    @Test
    void findByPatient() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Difficult", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional rpSaved = healthCareProfessionalService.save(referringProfessional);

        HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
                "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional biSaved = healthCareProfessionalService.save(behaviorInterventionist);

        BCIReferral bciRef = new BCIReferral("Reason", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRef2 = new BCIReferral("Reason2", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRefSaved = bciReferralService.save(bciRef);
        BCIReferral bciRefSaved2 = bciReferralService.save(bciRef2);

        List<BCIReferral> result = bciReferralService.findByPatient(patient.getId());
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(bciRef.getReason(), result.get(0).getReason());
    }

    /**
     * Tests findByPatientAssessment() of BCIReferralService.
     * Verifies that a BCIReferral entity can be successfully retrieved by its patient assessment.
     */
    @Test
    void findByPatientAssessment() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Difficult", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional rpSaved = healthCareProfessionalService.save(referringProfessional);

        HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
                "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional biSaved = healthCareProfessionalService.save(behaviorInterventionist);

        BCIReferral bciRef = new BCIReferral("Reason", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRefSaved = bciReferralService.save(bciRef);

        BCIReferral bciRefFound = bciReferralService.findByPatientAssessment(bciRefSaved.getPatientAssessment().getId());
        assertEquals(bciRefFound.getId(), bciRefSaved.getId());
    }

    /**
     * Tests findByReferringProfessional() of BCIReferralService.
     * Verifies that a BCIReferral entity can be successfully retrieved by its referring health care professional.
     */
    @Test
    void findByReferringProfessional() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Difficult", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional rpSaved = healthCareProfessionalService.save(referringProfessional);

        HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
                "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional biSaved = healthCareProfessionalService.save(behaviorInterventionist);

        BCIReferral bciRef = new BCIReferral("Reason", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRefSaved = bciReferralService.save(bciRef);

        BCIReferral bciRefFound = bciReferralService.findByReferringProfessional(bciRefSaved.getReferringProfessional().getId());
        assertEquals(bciRefFound.getId(), bciRefSaved.getId());
    }

    /**
     * Tests findByBehaviorChangeInterventionist() of BCIReferralService.
     * Verifies that a BCIReferral entity can be successfully retrieved by its referring health care behavior change interventionist.
     */
    @Test
    void findByBehaviorChangeInterventionist() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Difficult", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional rpSaved = healthCareProfessionalService.save(referringProfessional);

        HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
                "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional biSaved = healthCareProfessionalService.save(behaviorInterventionist);

        BCIReferral bciRef = new BCIReferral("Reason", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRefSaved = bciReferralService.save(bciRef);

        BCIReferral bciRefFound = bciReferralService.findByBehaviorChangeInterventionist(bciRefSaved.getBehaviorChangeInterventionist().getId());
        assertEquals(bciRefFound.getId(), bciRefSaved.getId());
    }

    /**
     * Tests deleteById() of BCIReferralService.
     * This method ensures that a BCIReferral entity can be successfully deleted
     * from the database by its ID.
     * <p>
     * @Asserts EntityNotFoundException is thrown when searching for the deleted BCIReferral.
     */
    @Test
    @Override
    void testDeleteById() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Difficult", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional rpSaved = healthCareProfessionalService.save(referringProfessional);

        HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
                "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional biSaved = healthCareProfessionalService.save(behaviorInterventionist);

        BCIReferral bciRef = new BCIReferral("Reason", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRefSaved = bciReferralService.save(bciRef);

        bciReferralService.deleteById(bciRefSaved.getId());

        // EntityNotFoundException should be thrown by a failing findById()
        Exception e = assertThrows(EntityNotFoundException.class, () -> bciReferralService.
                findById(bciRefSaved.getId()));
    }

    /**
     * Tests findAll() of BCIReferralService.
     * Verifies that all BCIReferral entities can be successfully retrieved.
     */
    @Test
    @Override
    void testFindAll() {
        Patient patient = new Patient("Arthur Pendragon", "kingarthur@gmail.com", "438-333-3333",
                "3 December 455", "King", "Camelot, Britain");
        Patient patientSaved = patientService.save(patient);

        PatientAssessment pa = new PatientAssessment("Difficult", patientSaved);
        PatientAssessment paSaved = patientAssessmentService.save(pa);

        HealthCareProfessional referringProfessional = new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional rpSaved = healthCareProfessionalService.save(referringProfessional);

        HealthCareProfessional behaviorInterventionist = new HealthCareProfessional("Dali", "Salvadord@gmail.com",
                "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional biSaved = healthCareProfessionalService.save(behaviorInterventionist);

        BCIReferral bciRef = new BCIReferral("Reason", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRefSaved = bciReferralService.save(bciRef);

        BCIReferral bciRef2 = new BCIReferral("Reason numero deux", patient, paSaved, rpSaved, biSaved);
        BCIReferral bciRefSaved2 = bciReferralService.save(bciRef2);

        List<BCIReferral> result = bciReferralService.findAll();
        assertFalse(result.isEmpty());
        assertEquals(bciRefSaved.getId(), result.get(0).getId());
        assertEquals(bciRefSaved2.getId(), result.get(1).getId());
    }
}
