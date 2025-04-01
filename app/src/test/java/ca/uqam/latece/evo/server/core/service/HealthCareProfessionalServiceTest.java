package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link HealthCareProfessional}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * <p>
 * The tests in this class ensure the proper functionality of HealthCareProfessionalService
 * and its interaction with the database using test containers to provide
 * a consistent test environment. Each test verifies specific business rules
 * or data retrieval criteria related to the HealthCareProfessional entity.
 * <p>
 * Annotations used for test setup include:
 * - @ContextConfiguration: Specifies test-specific configurations.
 * <p>
 * Dependencies injected into this test include:
 * - HealthCareProfessionalService to perform business logic operations specific to Actor entities.
 * - RoleService to associate roles during testing.
 * <p>
 * @version 1.0
 * @author Julien Champagne.
 */
@ContextConfiguration(classes = {HealthCareProfessionalService.class, RoleService.class, HealthCareProfessional.class})
public class HealthCareProfessionalServiceTest extends AbstractServiceTest {
    @Autowired
    HealthCareProfessionalService healthCareProfessionalService;

    @Autowired
    RoleService roleService;

    /**
     * Tests the save functionality of the HealthCareProfessionalService
     * The method verifies if a new HealthCareProfessional entity can be persisted into the database and
     * ensures that the returned saved entity contains a valid generated ID.
     * <p>
     * Steps:
     * 1. Creates a new HealthCareProfessional instance with all of its fields populated.
     * 2. Persists the HealthCareProfessional entity using the save method of HealthCareProfessionalRepository.
     * 3. Asserts that the saved HealthCareProfessional has a valid ID greater than zero.
     */
    @Test
    @Override
    void testSave() {
        HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional hcpSaved = healthCareProfessionalService.save(hcp);
        assert hcpSaved.getId() > 0;
    }

    /**
     * Tests the update functionality for the HealthCareProfessionalService.
     * This test verifies that an existing HealthCareProfessional entity can be updated in the database
     * with new attributes.
     * <p>
     * Steps:
     * 1. Creates a new HealthCareProfessional instance with all attributes filled and saves it.
     * 2. Creates a new Role instance and saves it to be associated with the updated HealthCareProfessional.
     * 3. Updates the HealthCareProfessional with new fields.
     * 4. Validates that the ID of the updated HealthCareProfessional remains unchanged.
     * 5. Ensures that the updated fields reflect the expected changes.
     */
    @Test
    @Override
    void testUpdate() {
        //Add the HealthCareProfessional to the database
        HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional hcpSaved = healthCareProfessionalService.create(hcp);
        System.out.println(hcpSaved);

        //Gather old information for comparison
        Long oldId = hcpSaved.getId();
        String oldName = hcpSaved.getName();
        String oldEmail = hcpSaved.getEmail();
        String oldContactInformation = hcpSaved.getContactInformation();
        String oldPosition = hcpSaved.getPosition();
        String oldAffiliation = hcpSaved.getAffiliation();
        String oldSpecialties = hcpSaved.getSpecialties();

        //Create a Role for the updated HealthCareProfessional
        String roleName = "e-Facilitator";
        Role role = new Role(roleName);
        Role roleCreated = roleService.create(role);

        //Update the HealthCareProfessional in the database
        hcp.setName("Dali");
        hcp.setEmail("Salvadord@gmail.com");
        hcp.setSpecialties("Painting");
        hcp.setRole(roleCreated);
        HealthCareProfessional hcpUpdated = healthCareProfessionalService.update(hcp);

        //Assertions
        assertEquals(oldId, hcpUpdated.getId());
        assertNotEquals(oldName, hcpUpdated.getName());
        assertNotEquals(oldEmail, hcpUpdated.getEmail());
        assertEquals(oldContactInformation, hcpUpdated.getContactInformation());
        assertEquals(oldPosition, hcpUpdated.getPosition());
        assertEquals(oldAffiliation, hcpUpdated.getAffiliation());
        assertNotEquals(oldSpecialties, hcpUpdated.getSpecialties());
        assertEquals(hcpUpdated.getRole().getName(), roleName);
    }

    /**
     * Tests the testFindById functionality of the HealthCareProfessionalRepository.
     * This method verifies that HealthCareProfessional can be successfully queried from the repository by their id.
     * <p>
     * The test includes the following steps:
     * 1. Creates and sets up a new HealthCareProfessional instance with all fields filled.
     * 2. Persists the created HealthCareProfessional instance into the repository.
     * 3. Queries the repository to retrieve an HealthCareProfessional by the specified id.
     * 4. Asserts that the resulting HealthCareProfessional saved is equals to HealthCareProfessional found id,
     *    confirming that the HealthCareProfessional was correctly retrieved.
     */
    @Test
    @Override
    void testFindById() {
        HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Healthcare");

        HealthCareProfessional hcpSaved = healthCareProfessionalService.save(hcp);
        HealthCareProfessional hcpFound = healthCareProfessionalService.findById(hcpSaved.getId());

        assertEquals(hcpSaved.getId(), hcpFound.getId());
    }

    /**
     * Tests the findByName functionality of the HealthCareProfessionalRepository.
     * This method verifies that HealthCareProfessional entities can be successfully queried from the repository by their name.
     * <p>
     * The test includes the following steps:
     * 1. Creates and sets up a new HealthCareProfessional instance with all fields filled.
     * 2. Persists the created HealthCareProfessional instance into the repository.
     * 3. Queries the repository to retrieve a list of HealthCareProfessionals by the specified name.
     * 4. Asserts that the resulting list is not empty, confirming that the HealthCareProfessional was correctly retrieved.
     */
    @Override
    @Test
    void testFindByName() {
        HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Healthcare");

        HealthCareProfessional hcpSaved = healthCareProfessionalService.save(hcp);
        List<HealthCareProfessional> results = healthCareProfessionalService.findByName(hcpSaved.getName());

        assertFalse(results.isEmpty());
    }

    /**
     * Tests the findByEmail functionality of the HealthCareProfessionalRepository.
     * This method verifies that HealthCareProfessional entities can be successfully queried from the repository by their email.
     * <p>
     * The test includes the following steps:
     * 1. Creates and sets up a new HealthCareProfessional instance with all fields filled.
     * 2. Persists the created HealthCareProfessional instance into the repository.
     * 3. Queries the repository to retrieve a list of HealthCareProfessionals by the specified email.
     * 4. Asserts that the resulting list is not empty, confirming that the HealthCareProfessional was correctly retrieved.
     */
    @Test
    void testFindByEmail() {
        HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Healthcare");

        HealthCareProfessional hcpSaved = healthCareProfessionalService.save(hcp);
        List<HealthCareProfessional> results = healthCareProfessionalService.findByEmail(hcpSaved.getEmail());

        assertFalse(results.isEmpty(), "HealthCareProfessional list should not be empty!");
    }

    /**
     * Tests the findByContactInformation functionality of the HealthCareProfessionalRepository.
     * This method verifies that HealthCareProfessional entities can be successfully queried from the repository by their contactInformation.
     * <p>
     * The test includes the following steps:
     * 1. Creates and sets up a new HealthCareProfessional instance with all fields filled.
     * 2. Persists the created HealthCareProfessional instance into the repository.
     * 3. Queries the repository to retrieve a list of HealthCareProfessionals by the specified contactInformation.
     * 4. Asserts that the resulting list is not empty, confirming that the HealthCareProfessional was correctly retrieved.
     */
    @Test
    void testFindByContactInformation() {
        HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Healthcare");

        HealthCareProfessional hcpSaved = healthCareProfessionalService.save(hcp);
        List<HealthCareProfessional> results = healthCareProfessionalService.findByContactInformation(hcpSaved.getContactInformation());

        assertFalse(results.isEmpty(), "HealthCareProfessional list should not be empty!");
    }

    /**
     * Tests the testFindByRole functionality of the HealthCareProfessionalRepository.
     * This test ensures that an actor can be successfully queried from the repository by its role.
     * <p>
     * The following test steps are performed:
     * 1. Creates a new HealthCareProfessional instance.
     * 2. Creates a new role instance.
     * 3. Persists the created Role instance into the repository using the save method.
     * 4. Add the created Role to the HealthCareProfessional instance.
     * 4. Persists the created HealthCareProfessional instance into the repository using the save method.
     * 5. Queries the repository to retrieve a list of HealthCareProfessional instances by the defined id of its role.
     * 6. Asserts that the resulting list is not empty, which confirms that the HealthCareProfessional was successfully retrieved.
     * 7. Asserts that the role's name and id are the same as the role of the saved HealthCareProfessional.
     */
   /* @Test
    void testFindByRole() {
        HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Healthcare");

        //Create a Role and update the HealthCareProfessional entity
        String roleName = "e-Facilitator";
        Role role = new Role(roleName);
        Role roleCreated = roleService.create(role);
        hcp.setRole(roleCreated);

        HealthCareProfessional hcpSaved = healthCareProfessionalService.save(hcp);
        List<HealthCareProfessional> results = healthCareProfessionalService.findByRole(roleCreated.getId());

        assertFalse(results.isEmpty(), "HealthCareProfessional list should not be empty!");
        assertEquals(hcpSaved.getRole().getId(), results.get(0).getRole().getId());
        assertEquals(hcpSaved.getRole().getName(), results.get(0).getRole().getName());
    }
*/
    /**
     * Tests the findByPosition functionality of the HealthCareProfessionalRepository.
     * This method verifies that HealthCareProfessional entities can be successfully queried from the repository by their position.
     * <p>
     * The test includes the following steps:
     * 1. Creates and sets up a new HealthCareProfessional instance with all fields filled.
     * 2. Persists the created HealthCareProfessional instance into the repository.
     * 3. Queries the repository to retrieve a list of HealthCareProfessionals by the specified position.
     * 4. Asserts that the resulting list is not empty, confirming that the HealthCareProfessional was correctly retrieved.
     */
    @Test
    void testFindByPosition() {
        HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Healthcare");

        HealthCareProfessional hcpSaved = healthCareProfessionalService.save(hcp);
        List<HealthCareProfessional> results = healthCareProfessionalService.findByPosition(hcpSaved.getPosition());

        assertFalse(results.isEmpty(), "HealthCareProfessional list should not be empty!");
    }

    /**
     * Tests the findByAffiliation functionality of the HealthCareProfessionalRepository.
     * This method verifies that HealthCareProfessional entities can be successfully queried from the repository by their affiliation.
     * <p>
     * The test includes the following steps:
     * 1. Creates and sets up a new HealthCareProfessional instance with all fields filled.
     * 2. Persists the created HealthCareProfessional instance into the repository.
     * 3. Queries the repository to retrieve a list of HealthCareProfessionals by the specified affiliation.
     * 4. Asserts that the resulting list is not empty, confirming that the HealthCareProfessional was correctly retrieved.
     */
    @Test
    void testFindByAffiliation() {
        HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Healthcare");

        HealthCareProfessional hcpSaved = healthCareProfessionalService.save(hcp);
        List<HealthCareProfessional> results = healthCareProfessionalService.findByAffiliation(hcpSaved.getAffiliation());

        assertFalse(results.isEmpty(), "HealthCareProfessional list should not be empty!");
    }

    /**
     * Tests the findBySpecialties functionality of the HealthCareProfessionalRepository.
     * This method verifies that HealthCareProfessional entities can be successfully queried from the repository by their specialties.
     * <p>
     * The test includes the following steps:
     * 1. Creates and sets up a new HealthCareProfessional instance with all fields filled.
     * 2. Persists the created HealthCareProfessional instance into the repository.
     * 3. Queries the repository to retrieve a list of HealthCareProfessionals by the specified specialties.
     * 4. Asserts that the resulting list is not empty, confirming that the HealthCareProfessional was correctly retrieved.
     */
    @Test
    void testFindBySpecialties() {
        HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Healthcare");

        HealthCareProfessional hcpSaved = healthCareProfessionalService.save(hcp);
        List<HealthCareProfessional> results = healthCareProfessionalService.findBySpecialties(hcpSaved.getSpecialties());

        assertFalse(results.isEmpty(), "HealthCareProfessional list should not be empty!");
    }

    /**
     * Tests the deleteById functionality of the HealthCareProfessionalRepository.
     * This method ensures that an HealthCareProfessional entity can be successfully deleted
     * from the database by its ID.
     * <p>
     * Steps:
     * 1. Creates and sets up a new HealthCareProfessional instance with all fields filled.
     * 2. Persists the HealthCareProfessional instance into the repository for setup.
     * 3. Deletes the HealthCareProfessional entity by invoking the deleteById method using the HealthCareProfessional's ID.
     * 4. Confirms the deletion by asserting that querying the repository for the same ID returns an empty result.
     */
    @Test
    @Override
    void testDeleteById() {
        HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Healthcare");

        // Persist and delete the entity
        HealthCareProfessional hcpSaved = healthCareProfessionalService.save(hcp);
        healthCareProfessionalService.deleteById(hcpSaved.getId());

        // EntityNotFoundException should be thrown by a failing findById()
        Exception e = assertThrows(EntityNotFoundException.class, () -> healthCareProfessionalService.findById(hcpSaved.getId()));
    }

    /**
     * Tests the findAll functionality of the HealthCareProfessionalRepository.
     * <p>
     * This test verifies that all HealthCareProfessional entities saved in the repository
     * can be successfully retrieved.
     * <p>
     * Test workflow:
     * 1. Creates two HealthCareProfessional instances with distinct fields.
     * 2. Persists the HealthCareProfessional instances into the repository using the save method.
     * 3. Retrieves all saved HealthCareProfessional entities using the findAll method.
     * 4. Asserts that the size of the retrieved list matches the number of saved entities.
     */
    @Test
    @Override
    void testFindAll() {
        HealthCareProfessional hcpBob = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional hcp0 = healthCareProfessionalService.save(hcpBob);

        HealthCareProfessional hcpDali = new HealthCareProfessional("Dali", "Salvadord@gmail.com", "514-333-3333",
                "Chief Painter", "CIUSSS", "Healthcare");
        HealthCareProfessional hcp1 = healthCareProfessionalService.save(hcpDali);

        List<HealthCareProfessional> result = healthCareProfessionalService.findAll();
        assertEquals(2, result.size());
    }
}
