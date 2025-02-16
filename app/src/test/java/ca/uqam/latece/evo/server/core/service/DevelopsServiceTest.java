package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Develops;
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
 * Test class for validating the functionalities of the {@link DevelopsService} class.
 * This class extends AbstractServiceTest to leverage its testing framework and implements
 * service-specific test methods for CRUD operations on the Develops entity.
 *
 * The class is annotated with @ContextConfiguration to specify the Spring context configuration,
 * which includes the DevelopsService and Develops as test components. It uses Spring's built-in
 * testing annotations and dependency injection capabilities to perform integration testing.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {DevelopsService.class, Develops.class})
public class DevelopsServiceTest extends AbstractServiceTest {
    @Autowired
    private DevelopsService developsService;

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
        skill.setType(SkillType.PHYSICAL);

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
    void testSave() {
        Develops develops = new Develops();
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);
        //Save.
        developsService.create(develops);

        // Checks if the Develops was saved.
        assert develops.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Create.
        Develops develops = new Develops();
        develops.setLevel(SkillLevel.INTERMEDIATE);
        develops.setSkill(skill);
        develops.setRole(role);
        Develops developsSaved = developsService.create(develops);

        // Update.
        Develops developsUpdate = new Develops();
        developsUpdate.setLevel(SkillLevel.ADVANCED);
        developsUpdate.setId(developsSaved.getId());
        Develops developsUpdated = developsService.update(developsUpdate);

        // Checks if the Develops id saved is the same of the Develops to be updated.
        assertEquals(developsSaved.getId(), developsUpdated.getId());
        // Checks if the Develops level was updated.
        assertEquals(SkillLevel.ADVANCED, developsUpdated.getLevel());
        assertNotEquals(SkillLevel.INTERMEDIATE, developsSaved.getLevel());
    }

    @Test
    @Override
    void testFindByName() {
        // Create.
        Develops develops = new Develops();
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);
        Develops developsSaved = developsService.create(develops);

        // Result empty.
        assertTrue(developsService.findByLevel(SkillLevel.INTERMEDIATE).isEmpty());
        // Result not empty.
        assertEquals(SkillLevel.BEGINNER, developsService.findByLevel(SkillLevel.BEGINNER).get(0).getLevel());
    }

    @Test
    @Override
    void testDeleteById() {
        // Create.
        Develops develops = new Develops();
        develops.setLevel(SkillLevel.INTERMEDIATE);
        develops.setSkill(skill);
        develops.setRole(role);
        Develops developsSaved = developsService.create(develops);

        // Should be true the result.
        assertTrue(developsService.existsById(developsSaved.getId()));

        // Delete.
        developsService.deleteById(developsSaved.getId());

        // Should be empty.
        assertTrue(developsService.findAll().isEmpty());
    }

    @Test
    @Override
    void testFindAll() {
        // Create.
        Develops develops = new Develops();
        develops.setLevel(SkillLevel.INTERMEDIATE);
        develops.setSkill(skill);
        develops.setRole(role);
        developsService.create(develops);

        Develops develops1 = new Develops();
        develops1.setLevel(SkillLevel.ADVANCED);
        develops1.setSkill(skill);
        develops1.setRole(role);
        developsService.create(develops1);

        // Find all.
        List<Develops> result = developsService.findAll();

        // Assert that the result should be two Requires.
        assertEquals(2, result.size());
    }
}
