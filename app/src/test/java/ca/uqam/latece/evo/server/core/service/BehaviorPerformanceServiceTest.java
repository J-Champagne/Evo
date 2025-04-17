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
 * The test class for the {@link BehaviorPerformanceService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {BehaviorPerformanceService.class, BehaviorPerformance.class})
public class BehaviorPerformanceServiceTest extends AbstractServiceTest {

    @Autowired
    private BehaviorPerformanceService behaviorPerformanceService;

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

    private BehaviorPerformance behaviorPerformance = new BehaviorPerformance();
    private BehaviorPerformance behaviorPerformance2 = new BehaviorPerformance();
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

        // Create a behavior performance.
        behaviorPerformance.setName("Programming 2");
        behaviorPerformance.setDescription("Programming language training 2");
        behaviorPerformance.setType(ActivityType.LEARNING);
        behaviorPerformance.setPreconditions("Preconditions 2");
        behaviorPerformance.setPostconditions("Post-conditions 2");
        behaviorPerformance.addRole(role);
        behaviorPerformanceService.create(behaviorPerformance);

        behaviorPerformance2.setName("Testing 2");
        behaviorPerformance2.setDescription("Testing training 2");
        behaviorPerformance2.setType(ActivityType.LEARNING);
        behaviorPerformance2.setPreconditions("Testing Preconditions 2");
        behaviorPerformance2.setPostconditions("Testing Post-conditions 2");
        behaviorPerformance2.addRole(role2);
        // Create a behavior performance.
        behaviorPerformanceService.create(behaviorPerformance2);

        // Create a Requires.
        requires.setLevel(SkillLevel.ADVANCED);
        requires.setRole(role);
        requires.setSkill(skill);
        requires.setBciActivity(behaviorPerformance);

        requires1.setLevel(SkillLevel.INTERMEDIATE);
        requires1.setRole(role2);
        requires1.setSkill(skill);
        requires1.setBciActivity(behaviorPerformance2);

        // Save the requires.
        requiresService.create(requires);
        requiresService.create(requires1);

        develops.setLevel(SkillLevel.INTERMEDIATE);
        develops.setRole(role2);
        develops.setSkill(skill);
        develops.setBciActivity(behaviorPerformance);
        developsService.create(develops);

        // Create Content.
        content.setName("Content Name");
        content.setDescription("Content Description");
        content.setType("Content Video");
        content.addBCIActivity(behaviorPerformance);

        content2.setName("Content2");
        content2.setDescription("Content 2 Description");
        content2.setType("Content Test");
        content2.addBCIActivity(behaviorPerformance2);

        // Save the Content.
        contentService.create(content);
        contentService.create(content2);
    }

    @AfterEach
    void afterEach(){
        // Delete a behavior performance.
        behaviorPerformanceService.deleteById(behaviorPerformance.getId());
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
        // Create a Behavior Performance.
        BehaviorPerformance behaviorPerformance = new BehaviorPerformance();
        behaviorPerformance.setName("Programming 1112 - BehaviorPerformance");
        behaviorPerformance.setDescription("Programming language training 1112 - BehaviorPerformance");
        behaviorPerformance.setType(ActivityType.LEARNING);
        behaviorPerformance.setPreconditions("Preconditions 1112");
        behaviorPerformance.setPostconditions("Post-conditions 1112");
        behaviorPerformance.addRole(role);
        // Create a behavior performance.
        BCIActivity bciActivitySaved = behaviorPerformanceService.create(behaviorPerformance);

        // Checks if the Behavior Performance was saved.
        assert bciActivitySaved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Create a Behavior Performance.
        BehaviorPerformance saved = new BehaviorPerformance();
        saved.setId(behaviorPerformance.getId());
        saved.setName("Database - Behavior Performance Test");
        saved.setDescription("Database training - Behavior Performance Test");
        saved.setPreconditions("Preconditions - Behavior Performance Test");
        saved.setPostconditions("Post-conditions - Behavior Performance Test");
        saved.setType(behaviorPerformance.getType());
        saved.setRole(behaviorPerformance.getRole());

        // Update a Behavior Performance.
        BehaviorPerformance updated = behaviorPerformanceService.update(saved);

        // Checks if the Behavior Performance id saved is the same of the Behavior Performance updated.
        assertEquals(saved.getId(), updated.getId());
        // Checks if the Behavior Performance name is different.
        assertNotEquals("Programming 2 - Behavior Performance Test", updated.getName());
        assertEquals("Database - Behavior Performance Test", updated.getName());
        assertEquals("Database training - Behavior Performance Test", updated.getDescription());
    }

    @Test
    @Override
    void testFindById() {
        BehaviorPerformance found = behaviorPerformanceService.findById(behaviorPerformance.getId());
        assertEquals(behaviorPerformance.getId(), found.getId());
    }

    @Test
    void testFindByName() {
        // Checks if the name is equals.
        assertEquals(behaviorPerformance.getName(),
                behaviorPerformanceService.findByName(behaviorPerformance.getName()).get(0).getName());
    }

    @Test
    @Override
    void testDeleteById() {
        // Create a Behavior Performance.
        BehaviorPerformance saved = new BehaviorPerformance();
        saved.setName("Database ioi - Behavior Performance Test");
        saved.setDescription("Database training oioi - Behavior Performance Test");
        saved.setType(ActivityType.LEARNING);
        saved.setPreconditions("Preconditions - Behavior Performance Test");
        saved.setPostconditions("Post-conditions - Behavior Performance Test");
        behaviorPerformanceService.create(saved);
        // Delete a Behavior Performance.
        behaviorPerformanceService.deleteById(saved.getId());
        // Checks if the Behavior Performance was deleted.
        assertFalse(behaviorPerformanceService.existsById(saved.getId()));
    }

    @Test
    void testFindType() {
        // Find a Behavior Performance by type.
        List<BehaviorPerformance> found = behaviorPerformanceService.findByType(ActivityType.LEARNING);

        assertEquals(2, found.size());
        assertEquals(behaviorPerformance.getId(), found.get(0).getId());
        assertEquals(behaviorPerformance.getType(), found.get(0).getType());
        assertEquals(behaviorPerformance2.getId(), found.get(1).getId());
        assertEquals(behaviorPerformance2.getType(), found.get(1).getType());
    }

    @Test
    void existsByName() {
        assertEquals(behaviorPerformance.getName(),
                behaviorPerformanceService.findByName(behaviorPerformance.getName()).get(0).getName());
    }

    @Test
    @Override
    void testFindAll() {
        // Create a Behavior Performance.
        BehaviorPerformance saved = new BehaviorPerformance();
        saved.setName("Database 3 - Behavior Performance Test");
        saved.setDescription("Database training 3 - Behavior Performance Test");
        saved.setType(ActivityType.LEARNING);
        saved.setPreconditions("Preconditions DB 3 - Behavior Performance Test");
        saved.setPostconditions("Post-conditions DB 3 - Behavior Performance Test");
        behaviorPerformanceService.create(saved);

        // Find all Behavior Performance.
        List<BehaviorPerformance> found = behaviorPerformanceService.findAll();

        // Tests.
        assertEquals(3, found.size());
        assertTrue(found.contains(behaviorPerformance));
        assertTrue(found.contains(behaviorPerformance2));
        assertTrue(found.contains(saved));

    }
}
