package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
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
 * The test class for the {@link GoalSettingService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {GoalSettingService.class, GoalSetting.class})
public class GoalSettingServiceTest extends AbstractServiceTest {
    @Autowired
    private GoalSettingService goalSettingService;

    @Autowired
    private BCIActivityService bciActivityService;

    @Autowired
    private RequiresService requiresService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private DevelopsService developsService;

    private GoalSetting goalSetting = new GoalSetting();
    private GoalSetting goalSetting2 = new GoalSetting();
    private BCIActivity bciActivity = new BCIActivity();
    private Role role = new Role();
    private Role role2 = new Role();
    private Skill skill = new Skill();
    private Requires requires = new Requires();
    private Requires requires1 = new Requires();
    private Content content = new Content();
    private Content content2 = new Content();
    private Develops develops = new Develops();

    @BeforeEach
    void beforeEach(){
        // Create a Role.
        role.setName("Admin");
        role2.setName("Participant");

        // Create a Skill.
        skill.setName("Java");
        skill.setDescription("Programming language");
        skill.setType(SkillType.BCT);

        // Create a Role.
        roleService.create(role);
        roleService.create(role2);
        // Create a Skill.
        skillService.create(skill);

        // Create a BCI Activity.
        bciActivity.setName("Programming 2");
        bciActivity.setDescription("Programming language training 2");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.setPreconditions("Preconditions 2");
        bciActivity.setPostconditions("Post-conditions 2");
        bciActivity.addRole(role);
        // Create a BCI Activity.
        bciActivityService.create(bciActivity);

        // Create a Goal Setting.
        goalSetting.setName("Programming 222");
        goalSetting.setDescription("Programming language training 2");
        goalSetting.setType(ActivityType.LEARNING);
        goalSetting.setPreconditions("Preconditions 2");
        goalSetting.setPostconditions("Post-conditions 2");
        goalSetting.addRole(role);
        goalSetting.setBciActivity(bciActivity);
        // Create a Goal Setting.
        goalSettingService.create(goalSetting);

        goalSetting2.setName("Testing 233");
        goalSetting2.setDescription("Testing training 2");
        goalSetting2.setType(ActivityType.LEARNING);
        goalSetting2.setPreconditions("Testing Preconditions 2");
        goalSetting2.setPostconditions("Testing Post-conditions 2");
        goalSetting2.addRole(role2);
        goalSetting2.setBciActivity(bciActivity);
        // Create a Goal Setting.
        goalSettingService.create(goalSetting2);

        // Create a Requires.
        requires.setLevel(SkillLevel.ADVANCED);
        requires.setRole(role);
        requires.setSkill(skill);
        requires.setBciActivity(goalSetting);

        requires1.setLevel(SkillLevel.INTERMEDIATE);
        requires1.setRole(role2);
        requires1.setSkill(skill);
        requires1.setBciActivity(goalSetting2);

        // Save the requires.
        requiresService.create(requires);
        requiresService.create(requires1);

        develops.setLevel(SkillLevel.INTERMEDIATE);
        develops.setRole(role2);
        develops.setSkill(skill);
        develops.setBciActivity(goalSetting);
        developsService.create(develops);

        // Create Content.
        content.setName("Content Name");
        content.setDescription("Content Description");
        content.setType("Content Video");
        content.addBCIActivity(goalSetting);

        content2.setName("Content2");
        content2.setDescription("Content 2 Description");
        content2.setType("Content Test");
        content2.addBCIActivity(goalSetting2);

        // Save the Content.
        contentService.create(content);
        contentService.create(content2);
    }

    @AfterEach
    void afterEach(){
        // Delete a Goal Setting.
        goalSettingService.deleteById(goalSetting.getId());
        goalSettingService.deleteById(goalSetting2.getId());
        // Create a Role.
        roleService.deleteById(role.getId());
        // Create a Skill.
        skillService.deleteById(skill.getId());
        // Delete the Requires.
        requiresService.deleteById(requires.getId());
        requiresService.deleteById(requires1.getId());
        // Delete Develops.
        developsService.deleteById(develops.getId());
        // Delete the Content.
        contentService.deleteById(content.getId());
        contentService.deleteById(content2.getId());
    }

    @Test
    @Override
    void testSave() {
        // Create a Goal Setting.
        GoalSetting goalSetting = new GoalSetting();
        goalSetting.setName("Programming 111002");
        goalSetting.setDescription("Programming language training 22223");
        goalSetting.setType(ActivityType.LEARNING);
        goalSetting.setPreconditions("Preconditions 1112");
        goalSetting.setPostconditions("Post-conditions 1112");
        goalSetting.addRole(role);
        // Create a Goal Setting.
        GoalSetting goalSettingSaved = goalSettingService.create(goalSetting);

        // Checks if the goal setting was saved.
        assert goalSettingSaved.getId() > 0;
        assert goalSetting2.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Create a Goal Setting.
        GoalSetting saved = new GoalSetting();
        saved.setId(goalSetting.getId());
        saved.setName("Database - Goal Setting Test");
        saved.setDescription("Database training - Goal Setting Test");
        saved.setPreconditions("Preconditions - Goal Setting Test");
        saved.setPostconditions("Post-conditions - Goal Setting Test");
        saved.setType(goalSetting.getType());
        saved.setRole(goalSetting.getRole());

        // Update a Behavior Setting.
        GoalSetting updated = goalSettingService.update(saved);

        // Checks if the Behavior Setting id saved is the same of the Behavior Setting updated.
        assertEquals(saved.getId(), updated.getId());
        // Checks if the Behavior Setting name is different.
        assertNotEquals("Programming 2 - Goal Setting Test", updated.getName());
        assertEquals("Database - Goal Setting Test", updated.getName());
        assertEquals("Database training - Goal Setting Test", updated.getDescription());
    }

    @Test
    @Override
    void testFindById() {
        GoalSetting found = goalSettingService.findById(goalSetting.getId());
        assertEquals(goalSetting.getId(), found.getId());
    }

    @Test
    @Override
    void testFindByName() {
        // Checks if the name is equals.
        assertEquals(goalSetting.getName(),goalSettingService.findByName(goalSetting.getName()).get(0).getName());
    }

    @Test
    @Override
    void testDeleteById() {
        // Create a Goal Setting.
        GoalSetting saved = new GoalSetting();
        saved.setName("Database ioi - Goal Setting Test");
        saved.setDescription("Database training oioi - Goal Setting Test");
        saved.setType(ActivityType.LEARNING);
        saved.setPreconditions("Preconditions - Goal Setting Test");
        saved.setPostconditions("Post-conditions - Goal Setting Test");
        goalSettingService.create(saved);
        // Delete a Goal Setting.
        goalSettingService.deleteById(saved.getId());
        // Checks if the Goal Setting was deleted.
        assertFalse(goalSettingService.existsById(saved.getId()));
    }

    @Test
    void testFindType() {
        // Find a Goal Setting by type.
        List<GoalSetting> found = goalSettingService.findByType(ActivityType.LEARNING);

        assertEquals(2, found.size());
        assertEquals(goalSetting.getId(), found.get(0).getId());
        assertEquals(goalSetting.getType(), found.get(0).getType());
        assertEquals(goalSetting2.getId(), found.get(1).getId());
        assertEquals(goalSetting2.getType(), found.get(1).getType());
    }

    @Test
    void existsByName() {
        assertEquals(goalSetting.getName(),
                goalSettingService.findByName(goalSetting.getName()).get(0).getName());
    }

    @Test
    @Override
    void testFindAll() {
        // Create a Goal Setting.
        GoalSetting saved = new GoalSetting();
        saved.setName("Database 3 - Goal Setting Test");
        saved.setDescription("Database training 3 - Goal Setting Test");
        saved.setType(ActivityType.LEARNING);
        saved.setPreconditions("Preconditions DB 3 - Goal Setting Test");
        saved.setPostconditions("Post-conditions DB 3 - Goal Setting Test");
        goalSettingService.create(saved);

        // Find all Goal Setting.
        List<GoalSetting> found = goalSettingService.findAll();

        // Tests.
        assertEquals(3, found.size());
        assertTrue(found.contains(goalSetting));
        assertTrue(found.contains(goalSetting2));
        assertTrue(found.contains(saved));
    }
}
