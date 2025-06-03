package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link BCIModuleService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {BCIModuleService.class, BCIModule.class})
public class BCIModuleServiceTest extends AbstractServiceTest {

    @Autowired
    private BCIModuleService bciModuleService;

    @Autowired
    private SkillService skillService;

    private BCIModule bciModule = new BCIModule();
    private Skill skill = new Skill();

    @BeforeEach
    void beforeEach(){
        // Creates a Skill.
        skill.setName("Skill 1");
        skill.setDescription("Skill Description");
        skill.setType(SkillType.BCT);

        // Save in the database.
        skillService.save(skill);

        //  Creates a BCIModule.
        bciModule.setName("Test Module");
        bciModule.setDescription("Test Module Description");
        bciModule.setPreconditions("Test Preconditions");
        bciModule.setPostconditions("Test Post conditions");
        bciModule.setSkills(skill);

        // Save in the database.
        bciModuleService.save(bciModule);
    }

    @AfterEach
    void afterEach(){
        bciModuleService.deleteById(bciModule.getId());
    }

    @Test
    @Override
    void testSave() {
        BCIModule module = new BCIModule();
        module.setName("Test Module 1");
        module.setDescription("Test Module Description 1");
        module.setPreconditions("Test Module Preconditions 1");
        module.setPostconditions("Post conditions 1");
        module.setSkills(skill);

        // Save in the database.
        bciModuleService.save(module);

        // Checks if the role was saved.
        assert module.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        BCIModule module = new BCIModule();
        module.setId(bciModule.getId());
        module.setName("Test Module 2");
        module.setDescription("Test Module Description 12");
        module.setPreconditions("Test Module Preconditions 12");
        module.setPostconditions("Post conditions 12");
        module.setSkills(skill);

        // Updated the BCIModule.
        BCIModule updated = bciModuleService.update(module);

        // Checks if the BCI Module id saved is the same of the BCI Module updated.
        assertEquals(module.getId(), updated.getId());
        assertNotEquals("Test Module", updated.getName());
        assertNotEquals("Test Module Description", updated.getDescription());
        assertNotEquals("Test Preconditions", updated.getPreconditions());
        assertNotEquals("Test Post conditions", updated.getPostconditions());
    }

    @Test
    @Override
    void testFindById() {
        BCIModule module = new BCIModule();
        module.setName("Module 1");
        module.setDescription("Description Module 1");
        module.setPreconditions("Preconditions Module 1");
        module.setPostconditions("Post conditions Module 1");
        module.setSkills(skill);

        // Creates the BCIModule.
        BCIModule saved = bciModuleService.create(module);

        // Look for BCIModule by ID.
        BCIModule found = bciModuleService.findById(saved.getId());

        // Tests.
        assertEquals(module.getId(), found.getId());
        assertEquals(module.getName(), found.getName());
        assertEquals(module.getDescription(), found.getDescription());
        assertEquals(module.getPreconditions(), found.getPreconditions());
        assertEquals(module.getPostconditions(), found.getPostconditions());
    }

    @Test
    void testFindByName() {
        // Checks if the name is equals.
        assertEquals(bciModule.getName(), bciModuleService.findByName(bciModule.getName()).get(0).getName());
    }

    @Test
    void existsByName() {
        assertEquals(bciModule.getName(), bciModuleService.findByName(bciModule.getName()).get(0).getName());
    }

    @Test
    @Override
    void testDeleteById() {
        BCIModule module = new BCIModule();
        module.setName("Module 2");
        module.setDescription("Description Module 2");
        module.setPreconditions("Preconditions Module 2");
        module.setPostconditions("Post conditions Module 2");
        module.setSkills(skill);

        // Creates the BCIModule.
        BCIModule saved = bciModuleService.create(module);
        // Delete the BCIModule.
        bciModuleService.deleteById(saved.getId());
        // Checks if the BCIModule was deleted.
        assertFalse(bciModuleService.existsById(saved.getId()));
    }

    @Test
    void findBySkill() {
        BCIModule module = new BCIModule();
        module.setName("Module 3");
        module.setDescription("Description Module 3");
        module.setPreconditions("Preconditions Module 3");
        module.setPostconditions("Post conditions Module 3");
        module.setSkills(skill);

        // Creates the BCIModule.
        BCIModule saved = bciModuleService.create(module);
        List<BCIModule> modules = bciModuleService.findBySkill(skill);

        assertEquals(2, modules.size());
    }

   /* @Test
    void findBySkills () {
        BCIModule module = new BCIModule();
        module.setName("Module 4");
        module.setDescription("Description Module 4");
        module.setPreconditions("Preconditions Module 4");
        module.setPostconditions("Post conditions Module 4");
        module.setSkills(skill);

        // Creates the BCIModule.
        BCIModule saved = bciModuleService.create(module);

        List<BCIModule> modules = bciModuleService.findBySkills(skill);

        assertEquals(2, modules.size());
    }*/

    @Test
    @Override
    void testFindAll() {
        BCIModule module = new BCIModule();
        module.setName("Module 5");
        module.setDescription("Description Module 5");
        module.setPreconditions("Preconditions Module 5");
        module.setPostconditions("Post conditions Module 5");
        module.setSkills(skill);

        // Creates the BCIModule.
        BCIModule saved = bciModuleService.create(module);

        // Find all BCIModule.
        List<BCIModule> modules = bciModuleService.findAll();
        assertEquals(2, modules.size());
        assertEquals(bciModule.getName(), modules.get(0).getName());
        assertEquals(bciModule.getDescription(), modules.get(0).getDescription());
        assertEquals(bciModule.getPreconditions(), modules.get(0).getPreconditions());
        assertEquals(bciModule.getPostconditions(), modules.get(0).getPostconditions());
        assertEquals(module.getName(), modules.get(1).getName());
        assertEquals(module.getDescription(), modules.get(1).getDescription());
        assertEquals(module.getPreconditions(), modules.get(1).getPreconditions());
        assertEquals(module.getPostconditions(), modules.get(1).getPostconditions());
    }
}