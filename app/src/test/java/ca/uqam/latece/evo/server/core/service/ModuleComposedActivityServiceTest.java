package ca.uqam.latece.evo.server.core.service;


import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
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
 * The test class for the {@link ModuleComposedActivityService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {ModuleComposedActivityService.class, ModuleComposedActivity.class})
public class ModuleComposedActivityServiceTest extends AbstractServiceTest {
    @Autowired
    private ModuleComposedActivityService moduleComposedActivityService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private BCIActivityService bciActivityService;

    @Autowired
    private BCIModuleService bciModuleService;

    private ModuleComposedActivity moduleComposedActivity = new ModuleComposedActivity();
    private BCIModule bciModule = new BCIModule();
    private Skill skill = new Skill();
    private Role role = new Role();
    private BCIActivity bciActivity = new BCIActivity();

    @BeforeEach
    void beforeEach(){
        // Create a Role.
        role.setName("Admin - BCI Activity - Module");
        roleService.create(role);

        // Creates a BCIActivity.
        bciActivity.setType(ActivityType.GOAL_SETTING);
        bciActivity.setName("BCI Activity - Module");
        bciActivity.setDescription("BCI Activity Description - Module");
        bciActivity.addRole(role);
        bciActivityService.create(bciActivity);

        // Creates a Skill.
        skill.setName("Skill 1");
        skill.setDescription("Skill Description");
        skill.setType(SkillType.BCT);
        skillService.create(skill);

        // Creates a BCIModule.
        bciModule.setName("Test Module");
        bciModule.setDescription("Test Module Description");
        bciModule.setPreconditions("Test Preconditions");
        bciModule.setPostconditions("Test Post conditions");
        bciModule.setSkills(skill);
        bciModuleService.create(bciModule);

        // Creates a ModuleComposedActivity.
        moduleComposedActivity.setComposedActivityBciModule(bciModule);
        moduleComposedActivity.setComposedModuleBciActivity(bciActivity);
        moduleComposedActivity.setOrder(10);
        moduleComposedActivityService.create(moduleComposedActivity);
    }

    @AfterEach
    void afterEach(){
        roleService.deleteById(role.getId());
        bciActivityService.deleteById(bciActivity.getId());
        skillService.deleteById(skill.getId());
        bciModuleService.deleteById(bciModule.getId());
        moduleComposedActivityService.deleteById(moduleComposedActivity.getId());
    }

    @Test
    @Override
    void testSave() {
        // Checks if the ModuleComposedActivity was saved.
        assert moduleComposedActivity.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Update a ModuleComposedActivity.
        ModuleComposedActivity toUpdate = new ModuleComposedActivity();
        toUpdate.setId(moduleComposedActivity.getId());
        toUpdate.setComposedActivityBciModule(bciModule);
        toUpdate.setComposedModuleBciActivity(bciActivity);
        toUpdate.setOrder(15);

        // Runs the update in the database.
        ModuleComposedActivity updated = moduleComposedActivityService.update(toUpdate);

        // Testing.
        assertEquals(moduleComposedActivity.getId(), updated.getId());
        assertNotEquals(10, updated.getOrder());
        assertEquals(moduleComposedActivity.getComposedActivityBciModule().getId(),
                updated.getComposedActivityBciModule().getId());
        assertEquals(moduleComposedActivity.getComposedModuleBciActivity().getId(),
                updated.getComposedModuleBciActivity().getId());
    }

    @Test
    @Override
    void testFindById() {
        ModuleComposedActivity result = moduleComposedActivityService.findById(moduleComposedActivity.getId());
        assertEquals(moduleComposedActivity.getId(), result.getId());
    }

    @Test
    void findByComposedActivityBciModuleId() {
        List<ModuleComposedActivity> result = moduleComposedActivityService.findByComposedActivityBciModuleId(bciModule.getId());
        assertEquals(moduleComposedActivity.getId(), result.get(0).getId());
        assertEquals(moduleComposedActivity.getComposedActivityBciModule().getId(),
                result.get(0).getComposedActivityBciModule().getId());
        assertEquals(moduleComposedActivity.getComposedModuleBciActivity().getId(),
                result.get(0).getComposedModuleBciActivity().getId());
    }

    @Test
    void findByComposedActivityBciModule() {
        List<ModuleComposedActivity> result = moduleComposedActivityService.findByComposedActivityBciModule(bciModule);
        assertEquals(moduleComposedActivity.getId(), result.get(0).getId());
        assertEquals(moduleComposedActivity.getComposedActivityBciModule().getId(),
                result.get(0).getComposedActivityBciModule().getId());
        assertEquals(moduleComposedActivity.getComposedModuleBciActivity().getId(),
                result.get(0).getComposedModuleBciActivity().getId());
    }

    @Test
    void findByComposedModuleBciActivity() {
        List<ModuleComposedActivity> result = moduleComposedActivityService.findByComposedModuleBciActivity(bciActivity);
        assertEquals(moduleComposedActivity.getId(), result.get(0).getId());
        assertEquals(moduleComposedActivity.getComposedModuleBciActivity().getId(),
                result.get(0).getComposedModuleBciActivity().getId());
        assertEquals(moduleComposedActivity.getComposedActivityBciModule().getId(),
                result.get(0).getComposedActivityBciModule().getId());
    }

    @Test
    void findByComposedModuleBciActivityId() {
        List<ModuleComposedActivity> result = moduleComposedActivityService.findByComposedModuleBciActivityId(bciActivity.getId());
        assertEquals(moduleComposedActivity.getId(), result.get(0).getId());
        assertEquals(moduleComposedActivity.getComposedModuleBciActivity().getId(),
                result.get(0).getComposedModuleBciActivity().getId());
        assertEquals(moduleComposedActivity.getComposedActivityBciModule().getId(),
                result.get(0).getComposedActivityBciModule().getId());
    }

    @Test
    @Override
    void testDeleteById() {
        // Creates a ModuleComposedActivity.
        ModuleComposedActivity module = new ModuleComposedActivity();
        module.setComposedActivityBciModule(bciModule);
        module.setComposedModuleBciActivity(bciActivity);
        module.setOrder(111);

        // Runs the insert in the database.
        ModuleComposedActivity saved = moduleComposedActivityService.create(module);
        moduleComposedActivityService.deleteById(saved.getId());

        // Checks if the ModuleComposedActivity was deleted.
        assertFalse(moduleComposedActivityService.existsById(saved.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        // Creates a ModuleComposedActivity.
        ModuleComposedActivity module = new ModuleComposedActivity();
        module.setComposedActivityBciModule(bciModule);
        module.setComposedModuleBciActivity(bciActivity);
        module.setOrder(222);
        // Runs the insert in the database.
        moduleComposedActivityService.create(module);

        // Find all.
        List<ModuleComposedActivity> result = moduleComposedActivityService.findAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(module));
        assertTrue(result.contains(moduleComposedActivity));
    }
}
