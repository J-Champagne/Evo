package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.Role;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
 * <p>
 * Annotations:
 * - @ContextConfiguration: Loads the specified classes into the test context for dependency injection.
 *
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {RoleService.class, Role.class})
public class RoleServiceTest extends AbstractServiceTest {
    @Autowired
    private RoleService roleService;

    @Autowired
    private BCIActivityService bciActivityService;

    private BCIActivity bciActivity = new BCIActivity();


    @BeforeEach
    void beforeEach(){
        // Create a BCI Activity.
        bciActivity.setName("Programming 2");
        bciActivity.setDescription("Programming language training 2");
        bciActivity.setType(ActivityType.LEARNING);
        // Create a BCI Activity.
        bciActivityService.create(bciActivity);
    }

    @AfterEach
    void afterEach(){
        // Delete a BCI Activity.
        bciActivityService.deleteById(bciActivity.getId());
    }

    /**
     * Tests the save functionality of the RoleService by creating a Role entity
     * and associating it with a set of Actor entities. The test ensures that
     * the created Role is persisted correctly and assigned a valid ID.
     */
    @Test
    public void testSave(){
        // Create a Role.
        Role role = new Role("Admin");
        role.addBCIActivity(bciActivity);
        Role roleSaved = roleService.create(role);

        // Checks if the role was saved.
        assert roleSaved.getId() > 0;
    }

    /**
     * Tests the update functionality of the RoleService. This method verifies that
     * an existing Role entity can be updated successfully and ensures that the
     * updated entity retains its original ID while reflecting the updated attributes.
     */
    @Test
    public void testUpdate(){
        // Create a role.
        Role role = new Role("Admin");
        role.addBCIActivity(bciActivity);
        // Save the role.
        Role roleSaved = roleService.create(role);

        // Update the role.
        Role roleToUpdate = new Role("e-Facilitator");
        roleToUpdate.setId(roleSaved.getId());
        Role roleUpdated = roleService.update(roleToUpdate);

        // Checks if the role id saved is the same of the role updated.
        assertEquals(roleSaved.getId(), roleUpdated.getId());
        // Checks if the role name is different.
        assertNotEquals("Admin", roleUpdated.getName());
    }

    /**
     * Tests the findByName functionality of the RoleService. This method verifies
     * that a Role entity can be successfully retrieved from the database by its name.
     */
    @Test
    public void testFindByName() {
        // Create a role.
        Role role = new Role("e-Facilitator");
        role.addBCIActivity(bciActivity);
        // Save the role.
        Role roleSaved = roleService.create(role);
        // Checks if the role name is equals.
        assertEquals(roleSaved.getName(), roleService.findByName(roleSaved.getName()).get(0).getName());
    }

    /**
     * Tests the findByBCIActivity functionality of the RoleService. This method verifies
     * that a Role entity can be successfully retrieved from the database by its BCIActivity.
     */
    @Test
    void testFindByBCIActivity() {
        // Create a role.
        Role roleBCI = new Role("Research");
        // Save the role.
        Role roleSaved = roleService.create(roleBCI);

        // Create a BCI Activity.
        BCIActivity bciActivityRole = new BCIActivity();
        bciActivityRole.setName("Research");
        bciActivityRole.setDescription("Research training");
        bciActivityRole.setType(ActivityType.LEARNING);
        bciActivityRole.addRole(roleSaved);
        // Save a BCI Activity.
        BCIActivity bciActivityCreated = bciActivityService.create(bciActivityRole);
        List<Role> result = roleService.findByBCIActivity(bciActivityCreated.getId());
        // Assert that the result
        assertEquals(1, result.size());
        // Checks if the role name is equals.
       assertEquals(roleSaved.getName(), result.get(0).getName());
    }

    /**
     * Tests the delete functionality of the RoleService. The method ensures that the Role is properly deleted
     * and verifies that it no longer exists in the repository.
     */
    @Test
    public void testDeleteById(){
        Role role = new Role("Admin");
        role.addBCIActivity(bciActivity);
        Role roleSaved = roleService.create(role);

        // Delete a Role
        roleService.deleteById(roleSaved.getId());

        // Checks if the role was deleted.
        assertFalse(roleService.existsById(roleSaved.getId()));
    }

    /**
     * Tests the retrieval functionality of the RoleService by verifying that a role
     * can be retrieved by its unique identifier after being persisted in the repository.
     */
    @Test
    public void testFindById(){
        // Create the role.
        Role role = new Role("Admin");
        role.addBCIActivity(bciActivity);
        Role roleSaved = roleService.create(role);

        // Checks if the created role exists in the database by Id.
        assertEquals(roleSaved.getId(), roleService.findById(roleSaved.getId()).getId());
    }

    /**
     * Tests the findByName functionality of the RoleService by verifying that a role
     * can be retrieved using its name from the database.
     */
    @Test
    public void findByName(){
        // Create the role.
        Role role = new Role("Admin5");
        Role roleSaved = roleService.create(role);
        role.addBCIActivity(bciActivity);

        // Checks if the created role exists in the database.
        assertEquals("Admin5", roleService.findByName(roleSaved.getName()).get(0).getName());
    }

    /**
     * Tests the existence-check functionality of the RoleService by verifying that a role with a specified
     * name can be identified as existing in the repository.
     */
    @Test
    public void existsByName(){
        // Create the role.
        Role role = new Role("Admin3");
        Role roleSaved = roleService.create(role);
        role.addBCIActivity(bciActivity);

        // Checks if the created role exists in the database.
        assertTrue(roleService.existsByName(roleSaved.getName()));
    }

    /**
     * Tests the findAll functionality of the RoleService by retrieving all persisted roles
     * and verifying the integrity of the returned data.
     */
    @Test
    public void testFindAll(){
         Role role0 = new Role("Admin");
        role0.addBCIActivity(bciActivity);

        Role role1 = new Role("e-Facilitator");
        role1.addBCIActivity(bciActivity);

        roleService.create(role0);
        roleService.create(role1);

        List<Role> roles = roleService.findAll();
        assertEquals(2, roles.size());
        assertEquals(role0.getId(), roles.get(0).getId());
        assertEquals(role1.getId(), roles.get(1).getId());
        assertEquals(role0.getName(), roles.get(0).getName());
        assertEquals(role1.getName(), roles.get(1).getName());
    }

    /**
     * Tests the findAll functionality of the RoleService when no roles are present in the repository.
     * This method verifies that the RoleService correctly handles an empty repository by returning an empty list.
     */
    @Test
    public void testFindAll_EmptyList() {
        List<Role> roles = roleService.findAll();
        assertEquals(0, roles.size());
    }
}
