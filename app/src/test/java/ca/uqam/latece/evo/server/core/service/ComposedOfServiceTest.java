package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link ComposedOfService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {ComposedOfService.class, ComposedOf.class})
public class ComposedOfServiceTest extends AbstractServiceTest {

    @Autowired
    private ComposedOfService composedOfService;

    @Autowired
    private BCIActivityService bciActivityService;

    @Autowired
    private BehaviorChangeInterventionBlockService interventionBlockService;

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

    private ComposedOf composedOf = new ComposedOf();
    private BCIActivity bciActivity = new BCIActivity();
    private BehaviorChangeInterventionBlock behaviorChangeInterventionBlock = new BehaviorChangeInterventionBlock();
    private Develops develops = new Develops();
    private Requires requires = new Requires();
    private Role role = new Role();
    private Skill skill = new Skill();
    private Content content = new Content();

    @BeforeEach
    void beforeEach(){
        // Create the role associated with BCI Activity.
        List<Role> roles = new ArrayList<>();
        role.setName("Participant");
        roleService.create(role);

        Role role2 = new Role();
        role2.setName("e-Facilitator");
        roleService.create(role2);

        // Add roles in the List.
        roles.add(role);
        roles.add(role2);

        // Creates Skill.
        skill.setName("Skill name");
        skill.setDescription("Skill Description");
        skill.setType(SkillType.BCT);
        skillService.create(skill);

        // Create the Content.
        content.setName("Content");
        content.setType("Content type");
        content.setDescription("Content description");
        contentService.create(content);

        // Create the BehaviorChangeInterventionBlock
        behaviorChangeInterventionBlock.setEntryConditions("entryConditions - ComposedOf");
        behaviorChangeInterventionBlock.setExitConditions("exitConditions - ComposedOf");
        interventionBlockService.create(behaviorChangeInterventionBlock);

        // Creates BCIActivity.
        bciActivity.setPreconditions("preconditions");
        bciActivity.setPostconditions("postconditions");
        bciActivity.setName("BCIActivity Name");
        bciActivity.setDescription("BCIActivity Description");
        bciActivity.setType(ActivityType.BCI_ACTIVITY);
        bciActivity.setParties(roles);
        bciActivity.addContent(content);
        bciActivity.addDevelops(develops);
        bciActivity.addRequires(requires);
        bciActivityService.create(bciActivity);

        // Creates the Requires.
        requires.setLevel(SkillLevel.BEGINNER);
        requires.setRole(role);
        requires.setSkill(skill);
        requires.setBciActivity(bciActivity);
        requiresService.create(requires);

        // Creates Develops
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);
        develops.setBciActivity(bciActivity);
        developsService.create(develops);

        // Create a ComposedOf.
        composedOf.setTiming(TimeCycle.BEGINNING);
        composedOf.setOrder(1);
        composedOf.setBciActivity(bciActivity);
        composedOf.setBciBlock(behaviorChangeInterventionBlock);

        // Save the ComposedOf.
        composedOfService.create(composedOf);
    }

    @AfterEach
    void afterEach(){
        // Delete a ComposedOf;
        composedOfService.deleteById(composedOf.getId());
        // Delete a BCI Activity.
        bciActivityService.deleteById(bciActivity.getId());
        // Delete a BehaviorChangeInterventionBlock
        interventionBlockService.deleteById(behaviorChangeInterventionBlock.getId());
        // Delete a Role.
        roleService.deleteById(role.getId());
        // Delete a Skill.
        skillService.deleteById(skill.getId());
        // Delete the Requires.
        requiresService.deleteById(requires.getId());
        // Delete Develops.
        developsService.deleteById(develops.getId());
        // Delete the Content.
        contentService.deleteById(content.getId());
    }

    @Test
    @Override
    void testSave() {
        // Creates a new ComposedOf.
        ComposedOf composedOf = new ComposedOf();
        composedOf.setTiming(TimeCycle.BEGINNING);
        composedOf.setOrder(2);
        composedOf.setBciActivity(bciActivity);
        composedOf.setBciBlock(behaviorChangeInterventionBlock);
        // Save the ComposedOf.
        ComposedOf composedOfSaved = composedOfService.create(composedOf);
        // Checks if the ComposedOf was saved.
        assert composedOfSaved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Update a ComposedOf.
        ComposedOf composedOfUpdate = new ComposedOf();
        composedOfUpdate.setId(composedOf.getId());
        composedOfUpdate.setTiming(TimeCycle.UNSPECIFIED);
        composedOfUpdate.setOrder(2);
        // Update the ComposedOf.
        ComposedOf composedOfUpdated = composedOfService.update(composedOfUpdate);
        // Checks if the ComposedOf id saved is the same of the ComposedOf updated.
        assertEquals(composedOfUpdate.getId(), composedOfUpdated.getId());
        // Checks if the ComposedOf order is different.
        assertNotEquals(1, composedOfUpdated.getOrder());
        assertNotEquals(TimeCycle.BEGINNING, composedOfUpdated.getTiming());
    }

    @Test
    @Override
    void testFindById() {
        // Look for ComposedOf by id.
        ComposedOf ComposedOfFound = composedOfService.findById(composedOf.getId());
        assertEquals(composedOf.getId(), ComposedOfFound.getId());
    }

    @Test
    void testFindByName() {
        // Checks if the Timing is equals.
        assertEquals(composedOf.getTiming(), composedOfService.findByTiming(TimeCycle.BEGINNING).get(0).getTiming());
    }

    @Test
    void findByOrder(){
        // Checks if the order are equals.
        assertEquals(composedOf.getOrder(), composedOfService.findByOrder(1).get(0).getOrder());
    }

    @Test
    @Override
    void testDeleteById() {
        // Creates a new ComposedOf.
        ComposedOf composedOf = new ComposedOf();
        composedOf.setTiming(TimeCycle.UNSPECIFIED);
        composedOf.setOrder(5);
        composedOf.setBciActivity(bciActivity);
        composedOf.setBciBlock(behaviorChangeInterventionBlock);
        // Save the ComposedOf.
        ComposedOf composedOfSaved = composedOfService.create(composedOf);
        // Checks if the ComposedOf was saved.
        assert composedOfSaved.getId() > 0;

        // Delete a ComposedOf.
        composedOfService.deleteById(composedOfSaved.getId());
        // Checks if the ComposedOf was deleted.
        assertFalse(composedOfService.existsById(composedOfSaved.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        // Creates a new ComposedOf.
        ComposedOf composedOfsaved = new ComposedOf();
        composedOfsaved.setTiming(TimeCycle.UNSPECIFIED);
        composedOfsaved.setOrder(5);
        composedOfsaved.setBciActivity(bciActivity);
        composedOfsaved.setBciBlock(behaviorChangeInterventionBlock);
        // Save the ComposedOf.
        ComposedOf composedOfSaved = composedOfService.create(composedOfsaved);
        // Find all ComposedOf.
        List<ComposedOf> composedOfList = composedOfService.findAll();
        // Tests.
        assertEquals(2, composedOfList.size());
        assertTrue(composedOfList.contains(composedOf));
        assertTrue(composedOfList.contains(composedOfsaved));
    }
}
