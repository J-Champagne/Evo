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
 * The test class for the {@link BCIActivityService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {BCIActivityService.class, BCIActivity.class})
public class BCIActivityServiceTest extends AbstractServiceTest {
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
        bciActivity.addParty(role);
        // Create a BCI Activity.
        bciActivityService.create(bciActivity);

        bciActivity2.setName("Testing 2 - BCIActivity Test");
        bciActivity2.setDescription("Testing training 2 - BCIActivity Test");
        bciActivity2.setType(ActivityType.LEARNING);
        bciActivity2.setPreconditions("Testing Preconditions 2 - BCIActivity Test");
        bciActivity2.setPostconditions("Testing Post-conditions 2 - BCIActivity Test");
        bciActivity2.addParty(role2);
        // Create a BCI Activity.
        bciActivityService.create(bciActivity2);

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

        // Update BCIActivity.
        bciActivity.addDevelops(develops);
        bciActivity.addContent(content);
        bciActivity.addContent(content2);
        bciActivity.addRequires(requires);
        bciActivity.addRequires(requires1);
        bciActivityService.update(bciActivity);

        bciActivity2.addDevelops(develops);
        bciActivity2.addContent(content);
        bciActivity2.addContent(content2);
        bciActivity2.addRequires(requires);
        bciActivity2.addRequires(requires1);
        bciActivityService.update(bciActivity2);
    }

    @AfterEach
    void afterEach(){
        // Delete a BCI Activity.
        bciActivityService.deleteById(bciActivity.getId());
        bciActivityService.deleteById(bciActivity2.getId());
        // Delete a Role.
        roleService.deleteById(role.getId());
        // Delete a Skill.
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
        bciActivity.setName("Programming 312 - BCIActivity Test");
        bciActivity.setDescription("Programming language training - BCIActivity Test");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.setPreconditions("Preconditions - BCIActivity Test");
        bciActivity.setPostconditions("Post-conditions - BCIActivity Test");
        bciActivity.addParty(role);
        // Create a BCI Activity.
        BCIActivity bciActivitySaved = bciActivityService.create(bciActivity);

        // Checks if the BCIActivity was saved.
        assert bciActivitySaved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Create a BCI Activity.
        BCIActivity bciActivitySaved = new BCIActivity();
        bciActivitySaved.setId(bciActivity.getId());
        bciActivitySaved.setName("Database - BCIActivity Test");
        bciActivitySaved.setDescription("Database training - BCIActivity Test");
        bciActivitySaved.setPreconditions("Preconditions - BCIActivity Test");
        bciActivitySaved.setPostconditions("Post-conditions - BCIActivity Test");
        bciActivitySaved.setType(bciActivity.getType());
        bciActivitySaved.setParties(bciActivity.getParties());

        // Update a BCI Activity.
        BCIActivity bciActivityUpdated = bciActivityService.update(bciActivitySaved);

        // Checks if the BCI Activity id saved is the same of the BCI Activity updated.
        assertEquals(bciActivitySaved.getId(), bciActivityUpdated.getId());
        // Checks if the BCI Activity name is different.
        assertNotEquals("Programming 2 - BCIActivity Test", bciActivityUpdated.getName());
        assertEquals("Database - BCIActivity Test", bciActivityUpdated.getName());
        assertEquals("Database training - BCIActivity Test", bciActivityUpdated.getDescription());
    }

    @Test
    @Override
    public void testFindById() {
        // Create a BCI Activity.
        BCIActivity bciActivity = new BCIActivity();
        bciActivity.setName("Database - BCIActivity Test");
        bciActivity.setDescription("Database training - BCIActivity Test");
        bciActivity.setPreconditions("Preconditions - BCIActivity Test");
        bciActivity.setPostconditions("Post-conditions - BCIActivity Test");
        bciActivity.setType(ActivityType.LEARNING);

        // Create a BCI Activity.
        BCIActivity bciActivitySaved = bciActivityService.create(bciActivity);
        BCIActivity bciActivityFound = bciActivityService.findById(bciActivitySaved.getId());
        assertEquals(bciActivitySaved.getId(), bciActivityFound.getId());
    }

    @Test
    void testFindByName() {
        // Checks if the name is equals.
        assertEquals(bciActivity.getName(), bciActivityService.findByName(bciActivity.getName()).get(0).getName());
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
        bciActivityService.create(bciActivitySaved);
        // Delete a BCI Activity.
        bciActivityService.deleteById(bciActivitySaved.getId());
        // Checks if the BCI Activity was deleted.
        assertFalse(bciActivityService.existsById(bciActivitySaved.getId()));
    }

    @Test
    void testFindType() {
        List<BCIActivity> bciActivities = bciActivityService.findByType(ActivityType.LEARNING);

        assertEquals(2, bciActivities.size());
        assertEquals(bciActivity.getId(), bciActivities.get(0).getId());
        assertEquals(bciActivity.getType(), bciActivities.get(0).getType());
        assertEquals(bciActivity2.getId(), bciActivities.get(1).getId());
        assertEquals(bciActivity2.getType(), bciActivities.get(1).getType());
    }

    @Test
    void existsByName() {
        assertEquals(bciActivity.getName(), bciActivityService.findByName(bciActivity.getName()).getFirst().getName());
    }

    @Test
    void findByDevelops() {
        List<BCIActivity> result = bciActivityService.findByDevelops(develops.getId());
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals("Programming 2 - BCIActivity Test", result.get(0).getName());
        System.out.println("BCIActivity name: " + result.get(0).getName());
    }

    @Test
    void findByRequires() {
        List<BCIActivity> result = bciActivityService.findByRequires(requires1.getId());
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals("Testing 2 - BCIActivity Test", result.get(0).getName());
        System.out.println("BCIActivity name: " + result.get(0).getName());
    }

    @Test
    void findByContent() {
        List<BCIActivity> result = bciActivityService.findByContent(content.getId());
        // Assert that the result
        assertEquals(2, result.size());
        assertEquals("Programming 2 - BCIActivity Test", result.get(0).getName());
        System.out.println("BCIActivity name: " + result.get(0).getName());
    }

    @Test
    void findByRole() {
        List<BCIActivity> result = bciActivityService.findByRole(role.getId());
        // Assert that the result
        assertEquals(1, result.size());
        assertNotEquals("Testing 2 - BCIActivity Test", result.get(0).getName());
        System.out.println("BCIActivity name: " + result.get(0).getName());
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
        bciActivityService.create(bciActivity3);

        // Find all bciActivities.
        List<BCIActivity> bciActivities = bciActivityService.findAll();

        // Tests.
        assertEquals(3, bciActivities.size());
        assertTrue(bciActivities.contains(bciActivity));
        assertTrue(bciActivities.contains(bciActivity2));
        assertTrue(bciActivities.contains(bciActivity3));
    }
}
