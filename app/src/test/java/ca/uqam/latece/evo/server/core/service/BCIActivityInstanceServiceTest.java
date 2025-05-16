package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.service.instance.BCIActivityInstanceService;
import ca.uqam.latece.evo.server.core.util.DateFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
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
    @Autowired
    private BCIActivityInstanceService bciActivityInstanceService;

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

    @Autowired
    private GoalSettingService goalSettingService;

    private BCIActivityInstance bciActivityInstance = new BCIActivityInstance();
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

    private LocalDate localEntryDate = DateFormatter.convertDateStrTo_yyyy_MM_dd("2020/01/08");
    private LocalDate localExitDate = LocalDate.now();

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
        // Create a BCI Activity.
        bciActivityService.create(bciActivity);

        bciActivity2.setName("Testing 2 - BCIActivity Test");
        bciActivity2.setDescription("Testing training 2 - BCIActivity Test");
        bciActivity2.setType(ActivityType.LEARNING);
        bciActivity2.setPreconditions("Testing Preconditions 2 - BCIActivity Test");
        bciActivity2.setPostconditions("Testing Post-conditions 2 - BCIActivity Test");
        bciActivity2.addRole(role2);
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

        // Create BCIActivityInstance.
        bciActivityInstance.setStatus("BCIActivity Instance Java");
        bciActivityInstance.setEntryDate(localEntryDate);
        bciActivityInstance.setExitDate(localExitDate);
        bciActivityInstance.setBciActivity(bciActivity);
        bciActivityInstanceService.create(bciActivityInstance);
    }

    @AfterEach
    void afterEach(){
        // Delete a BCI Activity.
        bciActivityService.deleteById(bciActivity.getId());
        bciActivityService.deleteById(bciActivity2.getId());
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
        // Delete the BCIActivityInstance.
        bciActivityInstanceService.deleteById(bciActivityInstance.getId());
    }

    @Test
    @Override
    void testSave() {
        // Create BCIActivityInstance.
        BCIActivityInstance bciActivityInstance = new BCIActivityInstance();
        bciActivityInstance.setStatus("BCIActivity Instance Java");
        bciActivityInstance.setEntryDate(localEntryDate);
        bciActivityInstance.setExitDate(localExitDate);
        bciActivityInstance.setBciActivity(bciActivity);

        BCIActivityInstance saved = bciActivityInstanceService.create(bciActivityInstance);

        // Checks if the BCIActivityInstance was saved.
        assert saved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Update the BCIActivityInstance.
        BCIActivityInstance instance = new BCIActivityInstance();
        instance.setId(bciActivityInstance.getId());
        instance.setStatus("BCIActivity Instance Java 62");
        instance.setEntryDate(localEntryDate);
        instance.setExitDate(localExitDate);
        instance.setBciActivity(bciActivity);
        BCIActivityInstance saved = bciActivityInstanceService.update(instance);

        // Checks if the BCI Activity Instance id saved is the same of the BCI Activity Instance updated.
        assertEquals(saved.getId(), bciActivityInstance.getId());
        // Checks if the BCI Activity Instance Status is different.
        assertNotEquals("BCIActivity Instance Java", saved.getStatus());
    }

    @Test
    @Override
    void testFindById() {
        BCIActivityInstance instance = new BCIActivityInstance();
        instance.setStatus("BCIActivity Instance Java 63");
        instance.setEntryDate(localEntryDate);
        instance.setExitDate(localExitDate);
        instance.setBciActivity(bciActivity);

        BCIActivityInstance saved =  bciActivityInstanceService.create(instance);
        BCIActivityInstance bciInstanceFound = bciActivityInstanceService.findById(saved.getId());
        assertEquals(saved.getId(), bciInstanceFound.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        BCIActivityInstance instance = new BCIActivityInstance();
        instance.setStatus("BCIActivity Instance Java 79");
        instance.setEntryDate(localEntryDate);
        instance.setExitDate(localExitDate);
        instance.setBciActivity(bciActivity);
        BCIActivityInstance saved = bciActivityInstanceService.create(instance);

        // Delete a BCI Activity Instance.
        bciActivityInstanceService.deleteById(saved.getId());
        // Checks if the BCI Activity Instance was deleted.
        assertFalse(bciActivityInstanceService.existsById(saved.getId()));
    }

    @Test
    void testFindByStatus() {
        // Creates a BCIActivityInstance.
        BCIActivityInstance instance = new BCIActivityInstance();
        instance.setStatus("Status 1");
        instance.setEntryDate(localEntryDate);
        instance.setExitDate(localExitDate);
        instance.setBciActivity(bciActivity);
        BCIActivityInstance saved = bciActivityInstanceService.create(instance);

        // Find all BCIActivityInstance.
        List<BCIActivityInstance> found = bciActivityInstanceService.findByStatus(saved.getStatus());
        // Tests.
        assertEquals(1, found.size());
        assertEquals(instance.getStatus(), found.get(0).getStatus());
    }

    @Test
    @Override
    void testFindAll() {
        BCIActivityInstance instance = new BCIActivityInstance();
        instance.setStatus("BCIActivity Instance Java 99");
        instance.setEntryDate(localEntryDate);
        instance.setExitDate(localExitDate);
        instance.setBciActivity(bciActivity);
        BCIActivityInstance saved = bciActivityInstanceService.create(instance);

        // Find all bciActivities.
        List<BCIActivityInstance> bciActivities = bciActivityInstanceService.findAll();

        // Tests.
        assertEquals(2, bciActivities.size());
        assertTrue(bciActivities.contains(bciActivityInstance));
    }
}
