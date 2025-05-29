package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;

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
@ContextConfiguration(classes = {HealthCareProfessionalService.class, HealthCareProfessional.class})
public class HealthCareProfessionalServiceTest extends AbstractServiceTest {
    @Autowired
    private HealthCareProfessionalService healthCareProfessionalService;

    @Autowired
    private RoleService roleService;

    private HealthCareProfessional hcpSaved;

    private Role roleSaved;

    @BeforeEach
    void setup() {
        roleSaved = roleService.create(new Role("Administrator"));
        hcpSaved = healthCareProfessionalService.save(new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", roleSaved, "Chief Painter", "CIUSSS",
                "Healthcare"));
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

        assertThrows(EntityNotFoundException.class, () -> healthCareProfessionalService.
                findById(hcpSaved.getId()));
    }

    @Test
    @Override
    void testFindAll() {
       healthCareProfessionalService.save(new HealthCareProfessional("Bob2", "Bobross2@gmail.com",
               "514-222-2222", roleSaved, "Student", "CIUSSS",
               "Healthcare"));
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
        assertEquals(hcpSaved.getId(), results.get(0).getId());
        assertEquals(hcpSaved.getName(), results.get(0).getName());
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
        assertEquals(hcpSaved.getId(), results.get(0).getId());
        assertEquals(hcpSaved.getContactInformation(), results.get(0).getContactInformation());
    }

    @Test
    void testFindByRole() {
        List<HealthCareProfessional> results = healthCareProfessionalService.findByRole(roleSaved);

        assertFalse(results.isEmpty());
        assertEquals(hcpSaved.getId(), results.get(0).getId());
        assertEquals(hcpSaved.getRole().getId(), results.get(0).getRole().getId());
    }

    @Test
    void testFindByRoleId() {
        List<HealthCareProfessional> results = healthCareProfessionalService.findByRoleId(roleSaved.getId());

        assertFalse(results.isEmpty());
        assertEquals(hcpSaved.getId(), results.get(0).getId());
        assertEquals(hcpSaved.getRole().getId(), results.get(0).getRole().getId());
    }

    @Test
    void testFindByPosition() {
        List<HealthCareProfessional> results = healthCareProfessionalService.findByPosition(hcpSaved.getPosition());

        assertFalse(results.isEmpty());
        assertEquals(hcpSaved.getId(), results.get(0).getId());
        assertEquals(hcpSaved.getPosition(), results.get(0).getPosition());
    }

    @Test
    void testFindByAffiliation() {
        List<HealthCareProfessional> results = healthCareProfessionalService.findByAffiliation(hcpSaved.getAffiliation());

        assertFalse(results.isEmpty());
        assertEquals(hcpSaved.getId(), results.get(0).getId());
        assertEquals(hcpSaved.getAffiliation(), results.get(0).getAffiliation());
    }

    @Test
    void testFindBySpecialties() {
        List<HealthCareProfessional> results = healthCareProfessionalService.findBySpecialties(hcpSaved.getSpecialties());

        assertFalse(results.isEmpty());
        assertEquals(hcpSaved.getId(), results.get(0).getId());
        assertEquals(hcpSaved.getSpecialties(), results.get(0).getSpecialties());
    }
}
