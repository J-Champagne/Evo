package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.Actor;
import ca.uqam.latece.evo.server.core.model.Role;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for testing the functionalities of {@link RoleService}. This class
 * contains test cases to validate the creation, retrieval, update, deletion,
 * and existence-check operations on Role entities. The tests ensure that the
 * RoleService performs these operations as expected with the associated data.
 *
 * Annotations:
 * - @ContextConfiguration: Loads the specified classes into the test context for dependency injection.
 *
 * @since 22.01.2025
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {RoleService.class, Role.class})
public class RoleServiceTest extends AbstractServiceTest {
    @Autowired
    private RoleService roleService;

    /**
     * Tests the save functionality of the RoleService by creating a Role entity
     * and associating it with a set of Actor entities. The test ensures that
     * the created Role is persisted correctly and assigned a valid ID.
     *
     * The method performs the following steps:
     * 1. Creates a set of Actor objects with distinct names and emails.
     * 2. Associates the actors with a Role object.
     * 3. Persists the Role object using the RoleService.
     * 4. Validates that the saved Role object has a non-null, positive ID.
     *
     * Assertions:
     * - The persisted Role object must have an ID greater than 0.
     */
    @Test
    public void testSave(){
        // Creates the Actors.
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Pierre", "pierre@gmail.com"));
        actors.add(new Actor("Yves", "yves@gmail.com"));

        // Create a Role.
        Role role = new Role("Admin");
        role.setActors(actors);
        Role roleSaved = roleService.create(role);

        // Checks if the role was saved.
        assert roleSaved.getId() > 0;
    }

    /**
     * Tests the update functionality of the RoleService. This method verifies that
     * an existing Role entity can be updated successfully and ensures that the
     * updated entity retains its original ID while reflecting the updated attributes.
     *
     * The test performs the following steps:
     * 1. Creates a new Role object with the name "Admin" and persists it using the RoleService.
     * 2. Updates the name of the newly persisted Role object to "e-Facilitator" while retaining its ID.
     * 3. Persists the updated Role object using the RoleService update method.
     * 4. Asserts that the ID of the saved Role matches the ID of the updated Role.
     * 5. Asserts that the name of the updated Role differs from the original Role name.
     *
     * Assertions:
     * - The ID of the initial saved Role and the updated Role should be the same.
     * - The name of the updated Role should be different from the original Role name.
     */
    @Test
    public void testUpdate(){
        // Create a role.
        Role role = new Role("Admin");
        // Save the role.
        Role roleSaved = roleService.create(role);

        // Update the role.
        Role roleToUpdate = new Role("e-Facilitator");
        roleToUpdate.setId(roleSaved.getId());
        Role roleUpdated = roleService.update(roleToUpdate);

        // Checks if the role id saved is the same of the role updated.
        assertEquals(roleSaved.getId(), roleUpdated.getId());
        // Checks if the role name are different.
        assertNotEquals("Admin", roleUpdated.getName());
    }

    /**
     * Tests the findByName functionality of the RoleService. This method verifies
     * that a Role entity can be successfully retrieved from the database by its name.
     *
     * The test performs the following steps:
     * 1. Creates a new Role object with the name "e-Facilitator".
     * 2. Persists the new Role object using the RoleService create method.
     * 3. Retrieves the Role object by its name using the RoleService findByName method.
     * 4. Asserts that the name of the retrieved Role matches the name of the created Role.
     *
     * Assertions:
     * - The name of the retrieved Role must match the name of the initially saved Role.
     */
    @Test
    public void testFindByName() {
        // Create a role.
        Role role = new Role("e-Facilitator");
        // Save the role.
        Role roleSaved = roleService.create(role);
        // Checks if the role name are equals.
        assertEquals(roleSaved.getName(), roleService.findByName(roleSaved.getName()).get(0).getName());
    }

    /**
     * Tests the delete functionality of the RoleService by removing a Role entity
     * by its identifier. The method ensures that the Role is properly deleted
     * and verifies that it no longer exists in the repository.
     *
     * The test performs the following steps:
     * 1. Creates a set of Actor objects with distinct names and emails.
     * 2. Associates the Actor objects with a Role entity.
     * 3. Saves the Role entity via the RoleService.
     * 4. Deletes the persisted Role entity by its identifier using the RoleService.
     * 5. Verifies that the Role entity no longer exists in the repository.
     *
     * Assertions:
     * - The Role entity should not exist in the repository after the deletion operation.
     */
    @Test
    public void testDeleteById(){
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor("Pierre", "pierre@gmail.com"));
        actors.add(new Actor("Yves", "yves@gmail.com"));

        Role role = new Role("Admin");
        role.setActors(actors);
        Role roleSaved = roleService.create(role);

        // Delete a Role
        roleService.deleteById(roleSaved.getId());

        // Checks if the role was deleted.
        assertFalse(roleService.existsById(roleSaved.getId()));
    }

    /**
     * Tests the retrieval functionality of the RoleService by verifying that a role
     * can be retrieved by its unique identifier after being persisted in the repository.
     *
     * The test performs the following steps:
     * 1. Creates a set of Actor objects with unique names and emails.
     * 2. Associates the Actor objects with a new Role entity named "Admin".
     * 3. Persists the Role entity using the RoleService.
     * 4. Retrieves the persisted Role entity by its unique identifier using the RoleService.
     * 5. Asserts that the identifier of the retrieved Role matches the identifier
     *    of the originally persisted Role.
     *
     * Assertions:
     * - The identifier of the retrieved Role must match the identifier of the persisted Role.
     */
    @Test
    public void testFindById(){
        // Create the Actors list.
        List<Actor> actorsAdm = new ArrayList<>();
        actorsAdm.add(new Actor("Pierre", "pierre@gmail.com"));
        actorsAdm.add(new Actor("Yves", "yves@gmail.com"));

        // Create the role.
        Role role = new Role("Admin");
        role.setActors(actorsAdm);
        Role roleSaved = roleService.create(role);

        // Checks if the created role exist in the database by Id.
        assertEquals(roleSaved.getId(), roleService.findById(roleSaved.getId()).getId());
    }

    /**
     * Tests the findByName functionality of the RoleService by verifying that a role
     * can be retrieved using its name from the database.
     *
     * The test performs the following steps:
     * 1. Creates a new Role object with the name "Admin5".
     * 2. Persists the Role object using the RoleService create method.
     * 3. Retrieves the persisted Role object using the RoleService findByName method.
     * 4. Asserts that the name of the retrieved Role matches the name of the created Role.
     *
     * Assertions:
     * - The name of the retrieved Role must match the name of the initially saved Role.
     */
    @Test
    public void findByName(){
        // Create the role.
        Role role = new Role("Admin5");
        Role roleSaved = roleService.create(role);

        // Checks if the created role exist in the database.
        assertEquals("Admin5", roleService.findByName(roleSaved.getName()).get(0).getName());
    }

    /**
     * Tests the existence-check functionality of the RoleService by verifying that a role with a specified
     * name can be identified as existing in the repository.
     *
     * This test performs the following steps:
     * 1. Creates a new Role object with a specific name (e.g., "Admin3").
     * 2. Persists the created Role using the RoleService.
     * 3. Verifies that the Role with the given name exists in the database by invoking the existsByName method.
     *
     * Assertions:
     * - The result of the existsByName method should return true for the persisted Role's name.
     */
    @Test
    public void existsByName(){
        // Create the role.
        Role role = new Role("Admin3");
        Role roleSaved = roleService.create(role);

        // Checks if the created role exist in the database.
        assertTrue(roleService.existsByName(roleSaved.getName()));
    }

    /**
     * Tests the findAll functionality of the RoleService by retrieving all persisted roles
     * and verifying the integrity of the returned data.
     *
     * This test performs the following steps:
     * 1. Creates two sets of Actor objects with unique names and emails.
     * 2. Associates the first set of actors with a Role named "Admin".
     * 3. Associates the second set of actors with a Role named "e-Facilitator".
     * 4. Persists both Role entities using the RoleService.
     * 5. Retrieves all persisted roles using the RoleService findAll method.
     * 6. Validates the size, IDs, names, and associated actors of the retrieved roles to
     *    ensure they match the initially persisted roles.
     *
     * Assertions:
     * - The number of retrieved roles must match the number of roles created.
     * - The IDs and names of the retrieved roles must match the IDs and names of
     *   the originally persisted roles.
     * - The number of associated actors in the retrieved roles must equal the number
     *   of actors in the respective persisted roles.
     * - The IDs of the associated actors in the retrieved roles must match the IDs
     *   of the actors in the respective persisted roles.
     */
    @Test
    public void testFindAll(){
        List<Actor> actorsAdm = new ArrayList<>();
        actorsAdm.add(new Actor("Pierre", "pierre@gmail.com"));
        actorsAdm.add(new Actor("Yves", "yves@gmail.com"));

        List<Actor> actorseFacilitator = new ArrayList<>();
        actorseFacilitator.add(new Actor("Marie", "marie@gmail.com"));
        actorseFacilitator.add(new Actor("Pierre-Yves", "pierre-yves@gmail.com"));


        Role role0 = new Role("Admin");
        role0.setActors(actorsAdm);

        Role role1 = new Role("e-Facilitator");
        role1.setActors(actorseFacilitator);

        roleService.create(role0);
        roleService.create(role1);

        List<Role> roles = roleService.findAll();
        assertEquals(2, roles.size());
        assertEquals(role0.getId(), roles.get(0).getId());
        assertEquals(role1.getId(), roles.get(1).getId());
        assertEquals(role0.getName(), roles.get(0).getName());
        assertEquals(role1.getName(), roles.get(1).getName());
        assertEquals(role0.getActors().size(), roles.get(0).getActors().size());
        assertEquals(role1.getActors().size(), roles.get(1).getActors().size());
        assertEquals(role0.getActors().iterator().next().getId(), roles.get(0).getActors().iterator().next().getId());
        assertEquals(role1.getActors().iterator().next().getId(), roles.get(1).getActors().iterator().next().getId());
    }

    /**
     * Tests the findAll functionality of the RoleService when no roles are present in the repository.
     *
     * This method verifies that the RoleService correctly handles an empty repository by returning an empty list.
     *
     * Assertions:
     * - The size of the retrieved roles list should be 0.
     */
    @Test
    public void testFindAll_EmptyList() {
        List<Role> roles = roleService.findAll();
        assertEquals(0, roles.size());
    }
}
