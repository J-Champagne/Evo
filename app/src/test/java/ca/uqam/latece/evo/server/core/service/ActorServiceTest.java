package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.Actor;
import ca.uqam.latece.evo.server.core.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link ActorService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 *
 * The tests in this class ensure the proper functionality of ActorService
 * and its interaction with the database using test containers to provide
 * a consistent test environment. Each test verifies specific business rules
 * or data retrieval criteria related to the Actor entity.
 *
 * Annotations used for test setup include:
 * - @ContextConfiguration: Specifies test-specific configurations.
 *
 * Dependencies injected into this test include:
 * - ActorService to perform business logic operations specific to Actor entities.
 * - RoleService to associate roles with actors during testing.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {ActorService.class, RoleService.class, Actor.class})
public class ActorServiceTest extends AbstractServiceTest {
    @Autowired
    private ActorService actorService;

    @Autowired
    private RoleService roleService;

    /**
     * Tests the save functionality of the ActorRepository.
     * The method verifies if a new Actor entity can be persisted into the database and
     * ensures that the returned saved entity contains a valid generated ID.
     *
     * Steps:
     * 1. Creates a new Actor instance with name and email fields populated.
     * 2. Persists the Actor entity using the save method of ActorRepository.
     * 3. Asserts that the saved Actor has a valid ID greater than zero.
     */
    @Test
    public void testSave(){
        Actor actor = new Actor();
        actor.setName("Pierre");
        actor.setEmail("pierre@gmail.com");
        Actor actorSaved = actorService.create(actor);
        assert actorSaved.getId() > 0;
    }

    /**
     * Tests the update functionality for the ActorService.
     * This test verifies that an existing Actor entity can be updated in the database
     * with new details, including a different name, email, and associated role.
     *
     * Steps:
     * 1. Creates a new Actor instance with initial name and email and saves it.
     * 2. Creates a new Role instance and saves it to be associated with the updated Actor.
     * 3. Updates the Actor with a new name, email, and role and persists the changes.
     * 4. Validates that the ID of the updated Actor remains unchanged.
     * 5. Ensures that the updated fields, including name, email, and role, reflect the expected changes.
     */
    @Test
    public void testUpdate(){
        // Creates an Actor without Role association.
        Actor actor = new Actor();
        actor.setName("Pierre");
        actor.setEmail("pierre@gmail.com");
        Actor actorSaved = actorService.create(actor);

        // Create a Role that will be used to update the Actor.
        Role actorRole = new Role("e-Facilitator");
        Role roleCreated = roleService.create(actorRole);

        // Update the Actor.
        Actor actorToUpdate = new Actor();
        actorToUpdate.setId(actorSaved.getId());
        actorToUpdate.setName("Pierre2");
        actorToUpdate.setEmail("pierre2@gmail.com");
        actorToUpdate.setRole(roleCreated);
        Actor actorUpdated = actorService.update(actorToUpdate);

        // Checks if the Actor id saved is the same of the Actor updated.
        assertEquals(actorSaved.getId(), actorUpdated.getId());
        // Checks the Actor updates.
        assertNotEquals("Pierre", actorUpdated.getName());
        assertNotEquals("pierre@gmail.com", actorUpdated.getName());
        assertFalse(actorUpdated.getRole().getName().isBlank());
    }

    /**
     * Tests the findByName functionality of the ActorRepository.
     * This method verifies that actors can be successfully queried from the repository by their name.
     *
     * The test includes the following steps:
     * 1. Creates and sets up a new Actor instance with a name and email.
     * 2. Persists the created Actor instance into the repository.
     * 3. Queries the repository to retrieve a list of actors by the specified name.
     * 4. Asserts that the resulting list is not empty, confirming that the actor was correctly retrieved.
     */
    @Test
    public void testFindByName() {
        // Ensure the database contains the actor, so the test is isolated.
        Actor actor = new Actor();
        actor.setName("Marie");
        actor.setEmail("marie@gmail.com");
        // Persist the actor before querying.
        actorService.create(actor);

        // Query the actor by name.
        List<Actor> result = actorService.findByName(actor.getName());

        // Assert that the result is not empty.
        assertFalse(result.isEmpty(), "Actor list should not be empty!");
    }

    /**
     * Tests the findByEmail functionality of the ActorRepository.
     *
     * This test ensures that an actor can be successfully queried from the repository by its email address.
     *
     * The following test steps are performed:
     * 1. Creates a new Actor instance with a name and email.
     * 2. Persists the created Actor instance into the repository using the save method.
     * 3. Queries the repository to retrieve a list of actors by the defined email.
     * 4. Asserts that the resulting list is not empty, which confirms that the actor was successfully retrieved.
     */
    @Test
    public void testFindByEmail() {
        // Ensure the database contains the actor, so the test is isolated.
        Actor actor = new Actor();
        actor.setName("Marianne");
        actor.setEmail("marianne@gmail.com");
        // Persist the actor before querying.
        actorService.create(actor);

        // Query the actor by email.
        List<Actor> result = actorService.findByEmail(actor.getEmail());

        // Assert that the result is not empty.
        assertFalse(result.isEmpty(), "Actor list should not be empty!");
    }

    /**
     * Tests the deleteById functionality of the ActorRepository.
     *
     * This method ensures that an Actor entity can be successfully deleted
     * from the database by its ID.
     *
     * Steps:
     * 1. Creates a new Actor instance with name and email fields populated.
     * 2. Persists the Actor instance into the repository for setup.
     * 3. Deletes the Actor entity by invoking the deleteById method using the Actor's ID.
     * 4. Confirms the deletion by asserting that querying the repository for the same ID returns an empty result.
     */
    @Test
    public void testDeleteById() {
        // Ensure the database contains the actor, so the test is isolated.
        Actor actor = new Actor();
        actor.setName("Marie");
        actor.setEmail("marie@gmail.com");
        // Persist the actor before querying.
        actorService.create(actor);

        // Delete an Actor.
        actorService.deleteById(actor.getId());
        // Should be empty.
        assertTrue(actorService.findActorById(actor.getId()).isEmpty());
    }

    /**
     * Tests the findAll functionality of the ActorRepository.
     *
     * This test verifies that all Actor entities saved in the repository
     * can be successfully retrieved.
     *
     * Test workflow:
     * 1. Creates two Actor instances with distinct "name" and "email" fields.
     * 2. Persists the Actor instances into the repository using the save method.
     * 3. Retrieves all saved Actor entities using the findAll method.
     * 4. Asserts that the size of the retrieved list matches the number of saved entities.
     */
    @Test
    public void testFindAll() {
        Actor actor0 = new Actor();
        actor0.setName("Juan");
        actor0.setEmail("juan@gmail.com");
        // Persist the actor before querying.
        actorService.create(actor0);

        Actor actor1 = new Actor();
        actor1.setName("Maria");
        actor1.setEmail("maria@gmail.com");
        // Persist the actor before querying.
        actorService.create(actor1);

        // Assert that the result should be two Actors.
        List<Actor> result = actorService.findAll();
        assertEquals(2, result.size());
    }

}
