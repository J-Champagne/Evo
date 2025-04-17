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
 * The test class for the {@link ReportingService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {ReportingService.class, Reporting.class})
public class ReportingServiceTest extends AbstractServiceTest {
    @Autowired
    private ReportingService reportingService;

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

    private Reporting reporting = new Reporting();
    private Reporting reporting2 = new Reporting();
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

        // Create a reporting.
        reporting.setName("Programming Reporting");
        reporting.setDescription("Programming language Reporting");
        reporting.setType(ActivityType.LEARNING);
        reporting.setPreconditions("Preconditions");
        reporting.setPostconditions("Post-conditions");
        reporting.setFrequency("Frequency");
        reporting.addRole(role);
        reporting.setBciActivity(bciActivity);
        // Create a reporting.
        reportingService.create(reporting);

        reporting2.setName("Reporting Training 2");
        reporting2.setDescription("Reporting Training 2");
        reporting2.setType(ActivityType.LEARNING);
        reporting2.setPreconditions("Preconditions 2");
        reporting2.setPostconditions("Post-conditions 2");
        reporting2.setFrequency("Frequency 2");
        reporting2.addRole(role2);
        reporting2.setBciActivity(bciActivity);
        // Create a reporting.
        reportingService.create(reporting2);

        // Create a Requires.
        requires.setLevel(SkillLevel.ADVANCED);
        requires.setRole(role);
        requires.setSkill(skill);
        requires.setBciActivity(reporting);

        requires1.setLevel(SkillLevel.INTERMEDIATE);
        requires1.setRole(role2);
        requires1.setSkill(skill);
        requires1.setBciActivity(reporting2);

        // Save the requires.
        requiresService.create(requires);
        requiresService.create(requires1);

        develops.setLevel(SkillLevel.INTERMEDIATE);
        develops.setRole(role2);
        develops.setSkill(skill);
        develops.setBciActivity(reporting);
        developsService.create(develops);

        // Create Content.
        content.setName("Content Name");
        content.setDescription("Content Description");
        content.setType("Content Video");
        content.addBCIActivity(reporting);

        content2.setName("Content2");
        content2.setDescription("Content 2 Description");
        content2.setType("Content Test");
        content2.addBCIActivity(reporting2);

        // Save the Content.
        contentService.create(content);
        contentService.create(content2);
    }

    @AfterEach
    void afterEach(){
        // Delete a reporting.
        reportingService.deleteById(reporting.getId());
        reportingService.deleteById(reporting2.getId());
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
        // Create a reporting.
        Reporting reporting = new Reporting();
        reporting.setName("Programming Language Reporting 002");
        reporting.setDescription("Programming Language Reporting 22223");
        reporting.setType(ActivityType.LEARNING);
        reporting.setPreconditions("Preconditions");
        reporting.setPostconditions("Post-conditions");
        reporting.setFrequency("Frequency 002");
        reporting.setBciActivity(bciActivity);
        reporting.addRole(role);
        // Create a reporting.
        Reporting reportingSaved = reportingService.create(reporting);

        // Checks if the reporting was saved.
        assert reportingSaved.getId() > 0;
        assert reporting2.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Create a reporting.
        Reporting saved = new Reporting();
        saved.setId(reporting.getId());
        saved.setName("Reporting - Test");
        saved.setDescription("Reporting training - Test");
        saved.setPreconditions("Preconditions - Test");
        saved.setPostconditions("Post-conditions - Test");
        saved.setFrequency("Frequency 003");
        saved.setType(reporting.getType());
        saved.setRole(reporting.getRole());

        // Update a reporting.
        Reporting updated = reportingService.update(saved);

        // Checks if the reporting id saved is the same of the reporting updated.
        assertEquals(saved.getId(), updated.getId());
        // Checks if the reporting name is different.
        assertNotEquals("Programming Reporting", updated.getName());
        assertEquals("Reporting - Test", updated.getName());
        assertEquals("Reporting training - Test", updated.getDescription());
    }

    @Test
    @Override
    void testFindById() {
        Reporting found = reportingService.findById(reporting.getId());
        assertEquals(reporting.getId(), found.getId());
    }

    @Test
    void testFindByName() {
        // Checks if the name is equals.
        assertEquals(reporting.getName(),reportingService.findByName(reporting.getName()).get(0).getName());
    }

    @Test
    @Override
    void testDeleteById() {
        // Create a Reporting.
        Reporting saved = new Reporting();
        saved.setName("Reporting - Test");
        saved.setDescription("Reporting training Test");
        saved.setType(ActivityType.LEARNING);
        saved.setPreconditions("Preconditions - Reporting Test");
        saved.setPostconditions("Post-conditions - Reporting Test");
        saved.setFrequency("Frequency - Reporting Test");
        reportingService.create(saved);
        // Delete a Reporting.
        reportingService.deleteById(saved.getId());
        // Checks if the Reporting was deleted.
        assertFalse(reportingService.existsById(saved.getId()));
    }

    @Test
    void testFindType() {
        // Find a Reporting by type.
        List<Reporting> found = reportingService.findByType(ActivityType.LEARNING);

        assertEquals(2, found.size());
        assertEquals(reporting.getId(), found.get(0).getId());
        assertEquals(reporting.getType(), found.get(0).getType());
        assertEquals(reporting2.getId(), found.get(1).getId());
        assertEquals(reporting2.getType(), found.get(1).getType());
    }

    @Test
    void existsByName() {
        assertEquals(reporting.getName(),
                reportingService.findByName(reporting.getName()).get(0).getName());
    }

    @Test
    @Override
    void testFindAll() {
        // Create a Reporting.
        Reporting saved = new Reporting();
        saved.setName("Reporting Test");
        saved.setDescription("Reporting Database training 3 Test");
        saved.setType(ActivityType.LEARNING);
        saved.setPreconditions("Preconditions Reporting Test");
        saved.setPostconditions("Post-conditions Reporting Test");
        saved.setBciActivity(bciActivity);
        saved.setFrequency("Frequency - Reporting Test 2");
        reportingService.create(saved);

        // Find all Reporting.
        List<Reporting> found = reportingService.findAll();

        // Tests.
        assertEquals(3, found.size());
        assertTrue(found.contains(reporting));
        assertTrue(found.contains(reporting2));
        assertTrue(found.contains(saved));
    }

}
