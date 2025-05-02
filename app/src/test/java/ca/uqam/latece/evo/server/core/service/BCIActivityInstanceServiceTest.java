package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.service.instance.BCIActivityInstanceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The test class for the {@link BCIActivityInstanceService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {BCIActivityInstanceService.class, BCIActivity.class})
public class BCIActivityInstanceServiceTest extends AbstractServiceTest {
    @Qualifier("BCIActivityInstanceService")
    @Autowired
    private BCIActivityInstanceService bciActivityInstanceService;

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

    @Autowired
    private GoalSettingService goalSettingService;

    private BCIActivity bciActivity = new BCIActivity();
    private BCIActivity bciActivity2 = new BCIActivity();
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
        role.setName("Admin - BCIActivity Test");
        role2.setName("Participant - BCIActivity Test");

        // Create a Skill.
        skill.setName("Java - BCIActivity Test");
        skill.setDescription("Programming language - BCIActivity Test");
        skill.setType(SkillType.BCT);

        // Create a Role.
        roleService.create(role);
        roleService.create(role2);
        // Create a Skill.
        skillService.create(skill);

        // Create a BCI Activity.
        bciActivity.setName("Programming 2 - BCIActivity Test");
        bciActivity.setDescription("Programming language training 2 - BCIActivity Test");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.setPreconditions("Preconditions 2 - BCIActivity Test");
        bciActivity.setPostconditions("Post-conditions 2 - BCIActivity Test");
        bciActivity.addRole(role);
        // Create a BCI Activity Instance.
        bciActivityInstanceService.create(bciActivity);

        bciActivity2.setName("Testing 2 - BCIActivity Test");
        bciActivity2.setDescription("Testing training 2 - BCIActivity Test");
        bciActivity2.setType(ActivityType.LEARNING);
        bciActivity2.setPreconditions("Testing Preconditions 2 - BCIActivity Test");
        bciActivity2.setPostconditions("Testing Post-conditions 2 - BCIActivity Test");
        bciActivity2.addRole(role2);
        // Create a BCI Activity Instance.
        bciActivityInstanceService.create(bciActivity2);

        // Create a Requires.
        requires.setLevel(SkillLevel.ADVANCED);
        requires.setRole(role);
        requires.setSkill(skill);
        requires.setBciActivity(bciActivity);

        requires1.setLevel(SkillLevel.INTERMEDIATE);
        requires1.setRole(role2);
        requires1.setSkill(skill);
        requires1.setBciActivity(bciActivity2);

        // Save the requires.
        requiresService.create(requires);
        requiresService.create(requires1);

        develops.setLevel(SkillLevel.INTERMEDIATE);
        develops.setRole(role2);
        develops.setSkill(skill);
        develops.setBciActivity(bciActivity);
        developsService.create(develops);

        // Create Content.
        content.setName("Content Name - BCIActivity Test");
        content.setDescription("Content Description - BCIActivity Test");
        content.setType("Content Video - BCIActivity Test");
        content.addBCIActivity(bciActivity);

        content2.setName("Content2 - BCIActivity Test");
        content2.setDescription("Content 2 Description - BCIActivity Test");
        content2.setType("Content Test - BCIActivity Test");
        content2.addBCIActivity(bciActivity2);

        // Save the Content.
        contentService.create(content);
        contentService.create(content2);
    }

    @AfterEach
    void afterEach(){
        // Delete a BCI Activity instance.
        bciActivityInstanceService. deleteById(bciActivity.getId());
        bciActivityInstanceService.deleteById(bciActivity2.getId());
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
        BCIActivity bciActivity = new BCIActivity();
        // Create a BCI Activity.
        bciActivity.setName("Programming - BCIActivity Test 1");
        bciActivity.setDescription("Programming language training - BCIActivity Test");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.setPreconditions("Preconditions - BCIActivity Test");
        bciActivity.setPostconditions("Post-conditions - BCIActivity Test");
        bciActivity.addRole(role);
        // Create a BCI Activity Instance.
        BCIActivity bciActivitySaved = bciActivityInstanceService.create(bciActivity);

        // Checks if the role was saved.
        assert bciActivitySaved.getId() > 0;
    }

    @Test
    void testSaveWithAssociationWithGoalSetting() {
        // Creates a GoalSetting.
        GoalSetting goalSetting = new GoalSetting();
        goalSetting.setName("Testing GoalSetting Name - BCIActivity Test");
        goalSetting.setDescription("Testing GoalSetting - BCIActivity Test");
        goalSetting.setType(ActivityType.GOAL_SETTING);
        goalSetting.setPostconditions("Post-conditions - GoalSetting Test");
        goalSetting.setPreconditions("Preconditions - GoalSetting Test");
        goalSetting.addRole(role);
        // Save a Goal Setting.
        GoalSetting goalSettingSaved = goalSettingService.create(goalSetting);

        // Create a BCI Activity.
        BCIActivity bciActivity = new BCIActivity();
        bciActivity.setName("Programming - BCIActivity Test 1");
        bciActivity.setDescription("Programming language training - BCIActivity Test");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.setPreconditions("Preconditions - BCIActivity Test");
        bciActivity.setPostconditions("Post-conditions - BCIActivity Test");
        // Create a BCI Activity Instance.
        bciActivityInstanceService.setGoalSetting(goalSettingSaved);
        BCIActivity bciActivitySaved = bciActivityInstanceService.create(bciActivity);

        // Checks if the role was saved.
        assert bciActivitySaved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Create a BCI Activity.
        BCIActivity bciActivitySaved = new BCIActivity();
        bciActivitySaved.setId(bciActivity.getId());
        bciActivitySaved.setName("Database - BCIActivity Test - Update");
        bciActivitySaved.setDescription("Database training - BCIActivity Test - Update");
        bciActivitySaved.setPreconditions("Preconditions - BCIActivity Test - Update");
        bciActivitySaved.setPostconditions("Post-conditions - BCIActivity Test - Update");
        bciActivitySaved.setType(bciActivity.getType());
        bciActivitySaved.setRole(bciActivity.getRole());

        // Update a BCI Activity.
        BCIActivity bciActivityUpdated = bciActivityInstanceService.update(bciActivitySaved);

        // Checks if the BCI Activity id saved is the same of the BCI Activity updated.
        assertEquals(bciActivitySaved.getId(), bciActivityUpdated.getId());
        // Checks if the BCI Activity name is different.
        assertNotEquals("Programming 2 - BCIActivity Test", bciActivityUpdated.getName());
        assertEquals("Database - BCIActivity Test - Update", bciActivityUpdated.getName());
        assertEquals("Database training - BCIActivity Test - Update", bciActivityUpdated.getDescription());
    }

    @Test
    @Override
    void testFindById() {
        // Create a BCI Activity.
        BCIActivity bciActivity = new BCIActivity();
        bciActivity.setName("Database - BCIActivity Test");
        bciActivity.setDescription("Database training - BCIActivity Test");
        bciActivity.setPreconditions("Preconditions - BCIActivity Test");
        bciActivity.setPostconditions("Post-conditions - BCIActivity Test");
        bciActivity.setType(ActivityType.LEARNING);

        // Update a BCI Activity.
        BCIActivity bciActivitySaved = bciActivityInstanceService.update(bciActivity);
        BCIActivity bciActivityFound = bciActivityInstanceService.findById(bciActivitySaved.getId());
        assertEquals(bciActivitySaved.getId(), bciActivityFound.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        // Create a BCI Activity.
        BCIActivity bciActivitySaved = new BCIActivity();
        bciActivitySaved.setName("Database ioi - BCIActivity Test");
        bciActivitySaved.setDescription("Database training oioi - BCIActivity Test");
        bciActivitySaved.setType(ActivityType.LEARNING);
        bciActivitySaved.setPreconditions("Preconditions - BCIActivity Test");
        bciActivitySaved.setPostconditions("Post-conditions - BCIActivity Test");
        bciActivityInstanceService.create(bciActivitySaved);
        // Delete a BCI Activity.
        bciActivityInstanceService.deleteById(bciActivitySaved.getId());
        // Checks if the BCI Activity was deleted.
        assertFalse(bciActivityInstanceService.existsById(bciActivitySaved.getId()));

    }

    @Test
    @Override
    void testFindAll() {
        // Create a BCI Activity.
        BCIActivity bciActivity3 = new BCIActivity();
        bciActivity3.setName("Database 3 - BCIActivity Test");
        bciActivity3.setDescription("Database training 3 - BCIActivity Test");
        bciActivity3.setType(ActivityType.LEARNING);
        bciActivity3.setPreconditions("Preconditions DB 3 - BCIActivity Test");
        bciActivity3.setPostconditions("Post-conditions DB 3 - BCIActivity Test");
        bciActivityInstanceService.create(bciActivity3);

        // Find all bciActivities.
        List<BCIActivity> bciActivities = bciActivityInstanceService.findAll();

        // Tests.
        assertEquals(3, bciActivities.size());
        assertTrue(bciActivities.contains(bciActivity));
        assertTrue(bciActivities.contains(bciActivity2));
        assertTrue(bciActivities.contains(bciActivity3));
    }
}
