package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.BCIReferral;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientAssessment;
import ca.uqam.latece.evo.server.core.service.instance.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link BCIReferralService}, responsible for testing its various functionalities. This
 * class includes integration tests for CRUD operations and other repository queries using a PostgreSQL database in a
 * containerized setup.
 *
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {BCIReferral.class, BCIReferralService.class})
public class BCIReferralServiceTest extends AbstractServiceTest {
    @Autowired
    private BCIReferralService bciReferralService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientAssessmentService patientAssessmentService;

    @Autowired
    private HealthCareProfessionalService healthCareProfessionalService;

    private BCIReferral bcirSaved;

    private Role roleSaved;

    private Patient patientSaved;

    private PatientAssessment paSaved;

    private HealthCareProfessional referringProfessionalSaved;

    private HealthCareProfessional behaviorInterventionistSaved;

    @BeforeEach
    public void setUp() {
        roleSaved = roleService.create(new Role("Administrator"));

        patientSaved = patientService.create(new Patient("Arthur Pendragon", "kingarthur@gmail.com",
                "438-333-3333", "3 December 455", "King", "Camelot, Britain"));

        paSaved = patientAssessmentService.create(new PatientAssessment("Good to go", patientSaved));

        referringProfessionalSaved = healthCareProfessionalService.create(new HealthCareProfessional("Bob",
                "Bobross@gmail.com", "514-222-2222", "Chief Painter", "CIUSSS", "Healthcare"));

        behaviorInterventionistSaved = healthCareProfessionalService.create(new HealthCareProfessional("Dali", "Salvadord@gmail.com",
                "514-333-3333", "Chief Painter", "CIUSSS", "Healthcare"));

        bcirSaved = bciReferralService.create(new BCIReferral("Good to go", patientSaved, paSaved,
                referringProfessionalSaved, behaviorInterventionistSaved));
    }

    @Test
    @Override
    void testSave() {
        assert bcirSaved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        bcirSaved.setReason("Because the world is round");
        BCIReferral bcirUpdated = bciReferralService.update(bcirSaved);

        assertEquals(bcirSaved.getId(), bcirUpdated.getId());
        assertEquals(bcirSaved.getReason(), bcirUpdated.getReason());
    }

    @Test
    @Override
    void testDeleteById() {
        bciReferralService.deleteById(bcirSaved.getId());

        assertThrows(EntityNotFoundException.class, () -> bciReferralService.
                findById(bcirSaved.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        bciReferralService.save( new BCIReferral("Because the world is round", patientSaved, paSaved, referringProfessionalSaved,
                behaviorInterventionistSaved));
        List<BCIReferral> result = bciReferralService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    @Override
    void testFindById() {
        BCIReferral bcirFound = bciReferralService.findById(bcirSaved.getId());
        assertEquals(bcirFound.getId(), bcirSaved.getId());
    }

    @Test
    void testFindByDate() {
        List<BCIReferral> result = bciReferralService.findByDate(bcirSaved.getDate());

        assertFalse(result.isEmpty());
        assertEquals(result.getFirst().getId(), bcirSaved.getId());
        assertEquals(result.getFirst().getDate(), bcirSaved.getDate());
    }

    @Test
    void findByReason() {
        List<BCIReferral> result = bciReferralService.findByReason(bcirSaved.getReason());

        assertFalse(result.isEmpty());
        assertEquals(result.getFirst().getId(), bcirSaved.getId());
        assertEquals(result.getFirst().getReason(), bcirSaved.getReason());
    }

    @Test
    void findByPatient() {
        List<BCIReferral> result = bciReferralService.findByPatientId(bcirSaved.getPatient().getId());

        assertFalse(result.isEmpty());
        assertEquals(result.getFirst().getId(), bcirSaved.getId());
        assertEquals(result.getFirst().getPatient().getId(), bcirSaved.getPatient().getId());
    }

    @Test
    void findByPatientAssessment() {
        List<BCIReferral> result = bciReferralService.findByPatientAssessmentId(bcirSaved.getPatientAssessment().getId());

        assertFalse(result.isEmpty());
        assertEquals(result.getFirst().getId(), bcirSaved.getId());
        assertEquals(result.getFirst().getPatientAssessment().getId(), bcirSaved.getPatientAssessment().getId());
    }

    @Test
    void findByReferringProfessional() {
        List<BCIReferral> result = bciReferralService.findByReferringProfessionalId(bcirSaved.getReferringProfessional().getId());

        assertFalse(result.isEmpty());
        assertEquals(result.getFirst().getId(), bcirSaved.getId());
        assertEquals(result.getFirst().getReferringProfessional().getId(), bcirSaved.getReferringProfessional().getId());
    }

    @Test
    void findByBehaviorChangeInterventionist() {
        List<BCIReferral> result = bciReferralService.findByBehaviorChangeInterventionistId(bcirSaved.getBehaviorChangeInterventionist().getId());

        assertFalse(result.isEmpty());
        assertEquals(result.getFirst().getId(), bcirSaved.getId());
        assertEquals(result.getFirst().getBehaviorChangeInterventionist().getId(), bcirSaved.getBehaviorChangeInterventionist().getId());
    }
}
