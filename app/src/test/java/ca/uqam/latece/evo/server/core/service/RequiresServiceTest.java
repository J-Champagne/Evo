package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Requires;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.Skill;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test class for verifying the functionality of the {@link RequiresService}.
 * This class extends the AbstractServiceTest to inherit common test scaffolding and enforces
 * the implementation of core operations such as creating, updating, retrieving, and deleting entities.
 *
 * The class is configured with Spring's @ContextConfiguration to load the RequiresService and its
 * dependencies (Requires, Role, and Skill classes) into the test context.
 *
 * The test lifecycle is managed using @BeforeEach and @AfterEach annotations to set up a Role and Skill
 * prior to each test execution and clean up the created entities afterward. This ensures a consistent
 * and isolated test environment.
 *
 * The class includes tests for:
 * 1. Creating and saving a Requires entity to the database.
 * 2. Updating the properties of an existing Requires entity.
 * 3. Finding Requires entities by their skill level.
 * 4. Deleting a Requires entity by its identifier.
 * 5. Retrieving all existing Requires entities from the database.
 *
 * Each test verifies the correctness of the corresponding service method by performing assertions
 * on the results.

 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {RequiresService.class, Requires.class})
public class RequiresServiceTest extends AbstractServiceTest  {
    @Autowired
    private RequiresService requiresService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SkillService skillService;

    private Role role = new Role();
    private Skill skill = new Skill();

    @BeforeEach
    void beforeEach(){
        // Create a Role.
        role.setName("Admin");

        // Create a Skill.
        skill.setName("Java");
        skill.setDescription("Programming language");
        skill.setType(SkillType.BCT);

        // Create a Role.
        roleService.create(role);
        // Create a Skill.
        skillService.create(skill);
    }

    @AfterEach
    void afterEach(){
        // Create a Role.
        roleService.deleteById(role.getId());
        // Create a Skill.
        skillService.deleteById(skill.getId());
    }

    @Test
    @Override
    public void testSave() {
        // Create a Requires.
        Requires requires = new Requires();
        requires.setLevel(SkillLevel.BEGINNER);
        requires.setRole(role);
        requires.setSkill(skill);
        // Save.
        Requires requiresSaved = requiresService.create(requires);
        // Checks if the Requires was saved.
        assert requiresSaved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Create a Requires.
        Requires requires = new Requires();
        requires.setLevel(SkillLevel.INTERMEDIATE);
        requires.setRole(role);
        requires.setSkill(skill);

        // Save the requires.
        Requires requiresSaved = requiresService.create(requires);
        // Update the requires level.
        Requires requiresUpdate = new Requires();
        requiresUpdate.setId(requiresSaved.getId());
        requiresUpdate.setLevel(SkillLevel.ADVANCED);
        // Update the database.
        Requires requiresUpdated = requiresService.update(requiresUpdate);

        // Checks if the Requires id saved is the same of the Requires to be updated.
        assertEquals(requiresSaved.getId(), requiresUpdated.getId());
        // Checks if the Requires level was updated.
        assertEquals(SkillLevel.ADVANCED, requiresUpdated.getLevel());
        assertNotEquals(SkillLevel.INTERMEDIATE, requiresSaved.getLevel());
    }

    @Test
    @Override
    void testFindByName() {
        // Create a Requires.
        Requires requires = new Requires();
        requires.setLevel(SkillLevel.INTERMEDIATE);
        requires.setRole(role);
        requires.setSkill(skill);

        // Save the requires.
        Requires requiresSaved = requiresService.create(requires);

        assert !requiresService.findByLevel(SkillLevel.INTERMEDIATE).isEmpty();
        assertEquals(SkillLevel.INTERMEDIATE, requiresService.findByLevel(SkillLevel.INTERMEDIATE).get(0).getLevel());
    }

    @Test
    @Override
    void testDeleteById() {
        // Create a Requires.
        Requires requires = new Requires();
        requires.setLevel(SkillLevel.BEGINNER);
        requires.setRole(role);
        requires.setSkill(skill);

        // Save the requires.
        Requires requiresSaved = requiresService.create(requires);

        // Should be true the result.
        assertTrue(requiresService.existsById(requiresSaved.getId()));

        // Delete.
        requiresService.deleteById(requiresSaved.getId());
        // Should be empty.
        assertTrue(requiresService.findAll().isEmpty());
    }

    @Test
    @Override
    void testFindAll() {
        // Create a Requires.
        Requires requires = new Requires();
        requires.setLevel(SkillLevel.ADVANCED);
        requires.setRole(role);
        requires.setSkill(skill);

        // Create a Requires.
        Requires requires1 = new Requires();
        requires1.setLevel(SkillLevel.BEGINNER);
        requires1.setRole(role);
        requires1.setSkill(skill);

        // Save the requires.
        requiresService.create(requires);
        requiresService.create(requires1);

        List<Requires> result = requiresService.findAll();
        // Assert that the result should be two Requires.
        assertEquals(2, result.size());
    }
}
