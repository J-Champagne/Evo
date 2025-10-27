package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link HealthCareProfessionalService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a PostgreSQL database in
 * a containerized setup.
 *
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos
 */
@ContextConfiguration(classes = {HealthCareProfessionalService.class, HealthCareProfessional.class})
public class HealthCareProfessionalServiceTest extends AbstractServiceTest {
    @Autowired
    private HealthCareProfessionalService healthCareProfessionalService;

    private HealthCareProfessional hcpSaved;

    @BeforeEach
    void setup() {
        hcpSaved = healthCareProfessionalService.save(new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS","Healthcare"));
    }

    @Test
    @Override
    void testSave() {
        assert hcpSaved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        hcpSaved.setAffiliation("Uqam");
        HealthCareProfessional hcpUpdated = healthCareProfessionalService.update(hcpSaved);

        assertEquals(hcpSaved.getId(), hcpUpdated.getId());
        assertEquals("Uqam", hcpUpdated.getAffiliation());
    }

    @Test
    @Override
    void testDeleteById() {
        healthCareProfessionalService.deleteById(hcpSaved.getId());

        assertFalse(healthCareProfessionalService.existsById(hcpSaved.getId()));
    }

    @Test
    @Override
    void testFindAll() {
       healthCareProfessionalService.save(new HealthCareProfessional("Bob2", "Bobross2@gmail.com",
               "514-222-2222", "Student", "CIUSSS","Healthcare"));
        List<HealthCareProfessional> result = healthCareProfessionalService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    @Override
    void testFindById() {
        HealthCareProfessional hcpFound = healthCareProfessionalService.findById(hcpSaved.getId());

        assertEquals(hcpSaved.getId(), hcpFound.getId());
    }

    @Test
    void testFindByName() {
        List<HealthCareProfessional> results = healthCareProfessionalService.findByName(hcpSaved.getName());

        assertFalse(results.isEmpty());
        assertEquals(hcpSaved.getId(), results.getFirst().getId());
        assertEquals(hcpSaved.getName(), results.getFirst().getName());
    }

    @Test
    void testFindByEmail() {
        HealthCareProfessional hcpFound = healthCareProfessionalService.findByEmail(hcpSaved.getEmail());

        assertEquals(hcpSaved.getId(), hcpFound.getId());
        assertEquals(hcpSaved.getEmail(), hcpFound.getEmail());
    }

    @Test
    void testFindByContactInformation() {
        List<HealthCareProfessional> results = healthCareProfessionalService.findByContactInformation(hcpSaved.getContactInformation());

        assertFalse(results.isEmpty());
        assertEquals(hcpSaved.getId(), results.getFirst().getId());
        assertEquals(hcpSaved.getContactInformation(), results.getFirst().getContactInformation());
    }

    @Test
    void testFindByPosition() {
        List<HealthCareProfessional> results = healthCareProfessionalService.findByPosition(hcpSaved.getPosition());

        assertFalse(results.isEmpty());
        assertEquals(hcpSaved.getId(), results.getFirst().getId());
        assertEquals(hcpSaved.getPosition(), results.getFirst().getPosition());
    }

    @Test
    void testFindByAffiliation() {
        List<HealthCareProfessional> results = healthCareProfessionalService.findByAffiliation(hcpSaved.getAffiliation());

        assertFalse(results.isEmpty());
        assertEquals(hcpSaved.getId(), results.getFirst().getId());
        assertEquals(hcpSaved.getAffiliation(), results.getFirst().getAffiliation());
    }

    @Test
    void testFindBySpecialties() {
        List<HealthCareProfessional> results = healthCareProfessionalService.findBySpecialties(hcpSaved.getSpecialties());

        assertFalse(results.isEmpty());
        assertEquals(hcpSaved.getId(), results.getFirst().getId());
        assertEquals(hcpSaved.getSpecialties(), results.getFirst().getSpecialties());
    }
}
