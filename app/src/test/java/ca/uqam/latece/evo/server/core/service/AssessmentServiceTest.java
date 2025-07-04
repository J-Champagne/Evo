package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.*;
import ca.uqam.latece.evo.server.core.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link AssessmentService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {AssessmentService.class, Assessment.class})
public class AssessmentServiceTest extends AbstractServiceTest {
    @Autowired
    private AssessmentService assessmentService;

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
    private ComposedOfService composedOfService;

    @Autowired
    private BehaviorChangeInterventionBlockService interventionBlockService;


    private Assessment assessment;
    private Assessment assessmentSelf;
    private Role role = new Role();
    private Role role2 = new Role();
    private Skill skill = new Skill();
    private Requires requires = new Requires();
    private Requires requires1 = new Requires();
    private Content content = new Content();
    private Content content2 = new Content();
    private Develops develops = new Develops();
    private ComposedOf composedOf = new ComposedOf();
    private BehaviorChangeInterventionBlock behaviorChangeInterventionBlock = new BehaviorChangeInterventionBlock();

    @BeforeEach
    void beforeEach(){
        // Create a Role.
        role.setName("Admin - Assessment Test");
        role2.setName("Participant - Assessment Test");
        // Create a Role.
        roleService.create(role);
        roleService.create(role2);

        // Create a Skill.
        skill.setName("Skill Name - Assessment Test");
        skill.setDescription("Skill Assessment Test Description");
        skill.setType(SkillType.BCT);
        // Create a Skill.
        skillService.create(skill);

        assessment = new Assessment(role, role2, skill);
        assessment.setName("Assessment Test");
        assessment.setDescription("Assessment Test Description");
        assessment.setType(ActivityType.LEARNING);
        assessment.setPreconditions("Preconditions 2 - Assessment Test");
        assessment.setPostconditions("Post-conditions 2 - Assessment Test");
        assessment.addParty(role);
        assessment.addParty(role2);
        assessment.setAssessmentScale(Scale.LETTER);
        assessment.setAssessmentScoringFunction("Assessment Scoring Function - Assessment Test");
        assessmentService.create(assessment);

        assessmentSelf = new Assessment(role2, role2, skill);
        assessmentSelf.setName("Assessment Self - Test");
        assessmentSelf.setDescription("AssessmentSelf -  Test Description");
        assessmentSelf.setType(ActivityType.BCI_ACTIVITY);
        assessmentSelf.setPreconditions("Preconditions 2 - Assessment Self - Test");
        assessmentSelf.setPostconditions("Post-conditions 2 - Assessment Self - Test");
        assessmentSelf.addParty(role);
        assessmentSelf.addParty(role2);
        assessmentSelf.setAssessmentScale(Scale._100);
        assessmentSelf.setAssessmentScoringFunction("Assessment Scoring Function - Assessment Self - Test");
        assessmentService.create(assessmentSelf);

        // Create a Requires.
        requires.setLevel(SkillLevel.ADVANCED);
        requires.setRole(role);
        requires.setSkill(skill);
        requires.setBciActivity(assessment);

        requires1.setLevel(SkillLevel.INTERMEDIATE);
        requires1.setRole(role2);
        requires1.setSkill(skill);
        requires1.setBciActivity(assessment);

        // Save the requires.
        requiresService.create(requires);
        requiresService.create(requires1);

        develops.setLevel(SkillLevel.INTERMEDIATE);
        develops.setRole(role2);
        develops.setSkill(skill);
        develops.setBciActivity(assessment);
        developsService.create(develops);

        // Create Content.
        content.setName("Content Name - Assessment Test");
        content.setDescription("Content Description - Assessment Test");
        content.setType("Content Video - Assessment Test");
        content.addBCIActivity(assessment);

        content2.setName("Content2 - Assessment Test");
        content2.setDescription("Content 2 Description - Assessment Test");
        content2.setType("Content Test - Assessment Test");
        content2.addBCIActivity(assessment);

        // Save the Content.
        contentService.create(content);
        contentService.create(content2);

        // Create the BehaviorChangeInterventionBlock
        behaviorChangeInterventionBlock.setEntryConditions("entryConditions - ComposedOf");
        behaviorChangeInterventionBlock.setExitConditions("exitConditions - ComposedOf");
        interventionBlockService.create(behaviorChangeInterventionBlock);

        // Create a ComposedOf.
        composedOf.setTiming(TimeCycle.UNSPECIFIED);
        composedOf.setOrder(10);
        composedOf.setBciActivity(assessment);
        composedOf.setBciBlock(behaviorChangeInterventionBlock);

        // Save the ComposedOf.
        composedOfService.create(composedOf);

        // Update Assessment.
        assessment.addDevelops(develops);
        assessment.addContent(content);
        assessment.addContent(content2);
        assessment.addRequires(requires);
        assessment.addRequires(requires1);
        assessment.setComposedOf(composedOf);
        assessment.setAssessmentSelfRelationship(assessmentSelf);
        assessmentService.update(assessment);
    }

    @AfterEach
    void afterEach(){
        // Delete an Assessment.
        assessmentService.deleteById(assessment.getId());
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
        // Delete ComposedOf.
        composedOfService.deleteById(composedOf.getId());
        // Delete BehaviorChangeInterventionBlock
        interventionBlockService.deleteById(behaviorChangeInterventionBlock.getId());
    }

    @Test
    @Override
    void testSave() {
        // Checks if the Assessment was saved.
        assert assessment.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Creates an Assessment.
        Assessment assessmentSaved = new Assessment(role, role2, skill);
        assessmentSaved.setId(assessment.getId());
        assessmentSaved.setName("Assessment Test 1");
        assessmentSaved.setDescription("Assessment Test Description 1");
        assessmentSaved.setType(ActivityType.PERFORMING);
        assessmentSaved.setPreconditions("Preconditions 1 - Assessment Test");
        assessmentSaved.setPostconditions("Post-conditions 1 - Assessment Test");
        assessmentSaved.setAssessmentScale(Scale._100);
        assessmentSaved.setAssessmentScoringFunction("Assessment Scoring Function - Assessment Test 1");

        // Update an Assessment.
        Assessment assessmentUpdated = assessmentService.update(assessmentSaved);

        // Checks if the Assessment id saved is the same of the Assessment updated.
        assertEquals(assessmentSaved.getId(), assessmentUpdated.getId());
        // Checks if the Assessment name is different.
        assertNotEquals("Assessment Test", assessmentUpdated.getName());
        assertEquals("Assessment Test 1", assessmentUpdated.getName());
        assertEquals("Assessment Test Description 1", assessmentUpdated.getDescription());
        assertEquals(Scale._100, assessmentUpdated.getAssessmentScale());
        assertEquals("Assessment Scoring Function - Assessment Test 1", assessmentUpdated.getAssessmentScoringFunction());
    }

    @Test
    @Override
    public void testFindById() {
        Assessment assessmentFound = assessmentService.findById(assessment.getId());
        assertEquals(assessment.getId(), assessmentFound.getId());
    }

    @Test
    void testFindByName() {
        // Checks if the name is equals.
        assertEquals(assessment.getName(), assessmentService.findByName(assessment.getName()).getFirst().getName());
    }

    @Test
    @Override
    void testDeleteById() {
        // Creates an Assessment.
        Assessment assessmentSave = new Assessment(role2, role2, skill);
        assessmentSave.setName("Assessment Test 123");
        assessmentSave.setDescription("Assessment Test Description 123");
        assessmentSave.setType(ActivityType.PERFORMING);
        assessmentSave.setPreconditions("Preconditions 1 - Assessment Test23");
        assessmentSave.setPostconditions("Post-conditions 1 - Assessment Test23");
        assessmentSave.setAssessmentScale(Scale._100);
        assessmentSave.setAssessmentScoringFunction("Assessment Scoring Function - Assessment Test 123");

        // Saved an Assessment.
        Assessment assessmentSaved = assessmentService.create(assessmentSave);

        // Delete an Assessment.
        assessmentService.deleteById(assessmentSaved.getId());
        // Checks if the BCI Activity was deleted.
        assertFalse(assessmentService.existsById(assessmentSaved.getId()));
    }

    @Test
    void testFindType() {
        List<Assessment> assessmentList = assessmentService.findByType(ActivityType.LEARNING);

        assertEquals(1, assessmentList.size());
        assertEquals(assessment.getId(), assessmentList.getFirst().getId());
        assertEquals(assessment.getName(), assessmentList.getFirst().getName());
        assertEquals(assessment.getType(), assessmentList.getFirst().getType());
        assertEquals(assessment.getAssessmentScale(), assessmentList.getFirst().getAssessmentScale());
        assertEquals(assessment.getAssessmentScoringFunction(), assessmentList.getFirst().getAssessmentScoringFunction());
        assertEquals(assessment.getAssessmentAssesseeRole(), assessmentList.getFirst().getAssessmentAssesseeRole());
        assertEquals(assessment.getAssessmentAssessorRole(), assessmentList.getFirst().getAssessmentAssessorRole());
        assertEquals(assessment.getSkills(), assessmentList.getFirst().getSkills());
    }

    @Test
    void existsByName() {
        assertEquals(assessment.getName(), assessmentService.findByName(assessment.getName()).getFirst().getName());
    }

    @Test
    void findByDevelopsBCIActivity_Id() {
        List<Assessment> result = assessmentService.findByDevelopsBCIActivity_Id(develops.getId());
        Assessment assessmentFound = result.getFirst();

        // Assert that the result.
        assertEquals(1, result.size());
        assertEquals(assessment.getName(), result.getFirst().getName());
        assertEquals(assessment.getAssessmentScale(), result.getFirst().getAssessmentScale());
        assertEquals(assessment.getAssessmentScoringFunction(), result.getFirst().getAssessmentScoringFunction());
        assertEquals(assessment.getAssessmentAssessorRole(), result.getFirst().getAssessmentAssessorRole());
        assertEquals(assessment.getSkills(), result.getFirst().getSkills());
        assertEquals(assessment.getDevelops().getFirst().getId(), result.getFirst().getDevelops().getFirst().getId());
    }

    @Test
    void findByRequiresBCIActivities_Id() {
        List<Assessment> result = assessmentService.findByRequiresBCIActivities_Id(requires1.getId());
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(assessment.getName(), result.getFirst().getName());
        assertEquals(assessment.getAssessmentScale(), result.getFirst().getAssessmentScale());
        assertEquals(assessment.getAssessmentScoringFunction(), result.getFirst().getAssessmentScoringFunction());
        assertEquals(assessment.getAssessmentAssessorRole(), result.getFirst().getAssessmentAssessorRole());
        assertEquals(assessment.getRequires().getFirst().getId(), result.getFirst().getRequires().getFirst().getId());
    }

    @Test
    void findByContentBCIActivities_Id() {
        List<Assessment> result = assessmentService.findByContentBCIActivities_Id(content.getId());
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(assessment.getName(), result.getFirst().getName());
        assertEquals(assessment.getAssessmentScale(), result.getFirst().getAssessmentScale());
        assertEquals(assessment.getAssessmentScoringFunction(), result.getFirst().getAssessmentScoringFunction());
        assertEquals(assessment.getAssessmentAssessorRole(), result.getFirst().getAssessmentAssessorRole());
        assertEquals(assessment.getContent().getFirst().getId(), result.getFirst().getContent().getFirst().getId());
    }

    @Test
    void findByRoleBCIActivities_Id() {
        List<Assessment> result = assessmentService.findByRoleBCIActivities_Id(role.getId());
        // Assert that the result
        assertEquals(2, result.size());
        assertEquals(assessmentSelf.getName(), result.get(0).getName());
        assertEquals(assessmentSelf.getParties().get(0).getId(), result.getFirst().getParties().get(0).getId());
        assertEquals(assessment.getName(), result.get(1).getName());
        assertEquals(assessmentSelf.getParties().get(1).getId(), result.getFirst().getParties().get(1).getId());
    }

    @Test
    void findByComposedOfList_Id() {
        List<Assessment> result = assessmentService.findByComposedOfList_Id(composedOf.getId());
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(assessment.getName(), result.getFirst().getName());
        assertEquals(assessment.getAssessmentScale(), result.getFirst().getAssessmentScale());
        assertEquals(assessment.getAssessmentScoringFunction(), result.getFirst().getAssessmentScoringFunction());
        assertEquals(assessment.getAssessmentAssessorRole(), result.getFirst().getAssessmentAssessorRole());
        assertEquals(assessment.getComposedOf().getFirst().getId(), result.getFirst().getComposedOf().getFirst().getId());
    }

    @Test
    void findByAssessmentAssesseeRole_Id() {
        List<Assessment> result = assessmentService.findByAssessmentAssesseeRole_Id(role.getId());
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(assessment.getName(), result.getFirst().getName());
        assertEquals(assessment.getAssessmentScale(), result.getFirst().getAssessmentScale());
        assertEquals(assessment.getAssessmentScoringFunction(), result.getFirst().getAssessmentScoringFunction());
        assertEquals(assessment.getAssessmentAssesseeRole(), result.getFirst().getAssessmentAssesseeRole());
        assertEquals(assessment.getAssessmentAssessorRole(), result.getFirst().getAssessmentAssessorRole());
    }

    @Test
    void findByAssessmentAssessorRole_Id() {
        List<Assessment> result = assessmentService.findByAssessmentAssessorRole_Id(role2.getId());
        // Assert that the result
        assertEquals(2, result.size());
        assertEquals(assessmentSelf.getName(), result.get(0).getName());
        assertEquals(assessment.getName(), result.get(1).getName());
        assertEquals(assessmentSelf.getAssessmentAssessorRole(), result.get(0).getAssessmentAssessorRole());
        assertEquals(assessment.getAssessmentAssessorRole(), result.get(1).getAssessmentAssessorRole());
    }

    @Test
    void findByAssessmentScale() {
        List<Assessment> result = assessmentService.findByAssessmentScale(Scale.LETTER);
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(assessment.getName(), result.getFirst().getName());
        assertEquals(assessment.getAssessmentScale(), result.getFirst().getAssessmentScale());
        assertEquals(assessment.getAssessmentScoringFunction(), result.getFirst().getAssessmentScoringFunction());
        assertEquals(assessment.getAssessmentAssesseeRole(), result.getFirst().getAssessmentAssesseeRole());
        assertEquals(assessment.getAssessmentAssessorRole(), result.getFirst().getAssessmentAssessorRole());
    }

    @Test
    void findByAssessmentScoringFunction() {
        List<Assessment> result = assessmentService.findByAssessmentScoringFunction("Assessment Scoring Function - Assessment Test");
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(assessment.getName(), result.getFirst().getName());
        assertEquals(assessment.getAssessmentScale(), result.getFirst().getAssessmentScale());
        assertEquals(assessment.getAssessmentScoringFunction(), result.getFirst().getAssessmentScoringFunction());
        assertEquals(assessment.getAssessmentAssesseeRole(), result.getFirst().getAssessmentAssesseeRole());
        assertEquals(assessment.getAssessmentAssessorRole(), result.getFirst().getAssessmentAssessorRole());
    }

    @Test
    void findByAssessmentSelfRelationship_Id() {
        List<Assessment> result = assessmentService.findByAssessmentSelfRelationship_Id(assessmentSelf.getId());
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(assessment.getName(), result.getFirst().getName());
        assertEquals(assessment.getAssessmentScale(), result.getFirst().getAssessmentScale());
        assertEquals(assessment.getAssessmentScoringFunction(), result.getFirst().getAssessmentScoringFunction());
        assertEquals(assessment.getAssessmentAssesseeRole(), result.getFirst().getAssessmentAssesseeRole());
        assertEquals(assessment.getAssessmentAssessorRole(), result.getFirst().getAssessmentAssessorRole());
        assertEquals(assessment.getAssessmentSelfRelationship(), result.getFirst().getAssessmentSelfRelationship());
    }

    @Test
    void findByAssessmentSelfRelationship() {
        List<Assessment> result = assessmentService.findByAssessmentSelfRelationship(assessmentSelf);
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(assessment.getName(), result.getFirst().getName());
        assertEquals(assessment.getAssessmentScale(), result.getFirst().getAssessmentScale());
        assertEquals(assessment.getAssessmentScoringFunction(), result.getFirst().getAssessmentScoringFunction());
        assertEquals(assessment.getAssessmentAssesseeRole(), result.getFirst().getAssessmentAssesseeRole());
        assertEquals(assessment.getAssessmentAssessorRole(), result.getFirst().getAssessmentAssessorRole());
        assertEquals(assessment.getAssessmentSelfRelationship(), result.getFirst().getAssessmentSelfRelationship());
    }

    @Test
    @Override
    void testFindAll() {
        Assessment newAssessment = new Assessment(role, role2, skill);
        newAssessment.setName("Assessment Test - Find All");
        newAssessment.setDescription("Assessment Test Description - Find All");
        newAssessment.setType(ActivityType.BCI_ACTIVITY);
        newAssessment.setPreconditions("Preconditions 2 - Assessment Test - Find All");
        newAssessment.setPostconditions("Post-conditions 2 - Assessment Test - Find All");
        newAssessment.addParty(role);
        newAssessment.addParty(role2);
        newAssessment.setAssessmentScale(Scale._100);
        newAssessment.setAssessmentScoringFunction("Assessment Scoring Function - Assessment Test - Find All");
        newAssessment.addDevelops(develops);
        newAssessment.addContent(content);
        newAssessment.addContent(content2);
        newAssessment.addRequires(requires);
        newAssessment.addRequires(requires1);
        Assessment saved = assessmentService.create(newAssessment);

        // Find all Assessment.
        List<Assessment> result = assessmentService.findAll();

        // Tests.
        assertEquals(3, result.size());
        assertTrue(result.contains(saved));
        assertTrue(result.contains(assessment));
        assertTrue(result.contains(assessmentSelf));
    }
}
