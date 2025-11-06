package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.Scale;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.Assessment;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.model.ComposedOf;
import ca.uqam.latece.evo.server.core.model.Content;
import ca.uqam.latece.evo.server.core.model.Develops;
import ca.uqam.latece.evo.server.core.model.Requires;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.repository.AssessmentRepository;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionBlockRepository;
import ca.uqam.latece.evo.server.core.repository.ComposedOfRepository;
import ca.uqam.latece.evo.server.core.repository.ContentRepository;
import ca.uqam.latece.evo.server.core.repository.DevelopsRepository;
import ca.uqam.latece.evo.server.core.repository.RequiresRepository;
import ca.uqam.latece.evo.server.core.repository.RoleRepository;
import ca.uqam.latece.evo.server.core.repository.SkillRepository;
import ca.uqam.latece.evo.server.core.service.AssessmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The Assessment Controller test class for the {@link AssessmentController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = AssessmentController.class)
@ContextConfiguration(classes = {AssessmentController.class, AssessmentService.class, Assessment.class})
public class AssessmentControllerTest extends AbstractControllerTest {
    @MockitoBean
    private AssessmentRepository assessmentRepository;
    @MockitoBean
    private RoleRepository roleRepository;
    @MockitoBean
    private SkillRepository skillRepository;
    @MockitoBean
    private RequiresRepository requiresRepository;
    @MockitoBean
    private ContentRepository contentRepository;
    @MockitoBean
    private DevelopsRepository developsRepository;
    @MockitoBean
    private ComposedOfRepository composedOfRepository;
    @MockitoBean
    private BehaviorChangeInterventionBlockRepository behaviorChangeInterventionBlockRepository;

    private Assessment assessment = new Assessment();
    private Assessment assessmentSelf = new Assessment();
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

    private static final String URL = "/assessment";
    private static final String URL_SPLITTER = "/assessment/";
    private static final String URL_FIND = "/assessment/find/";

    @BeforeEach
    @Override
    void setUp() {
        // Create a Role.
        role.setId(1L);
        role.setName("Admin - Assessment Test");
        // Save in the database.
        when(roleRepository.save(role)).thenReturn(role);

        role2.setId(2L);
        role2.setName("Participant - Assessment Test");
        // Save in the database.
        when(roleRepository.save(role2)).thenReturn(role2);

        // Create a Skill.
        skill.setId(3L);
        skill.setName("Skill Name - Assessment Test");
        skill.setDescription("Skill Assessment Test Description");
        skill.setType(SkillType.BCT);
        // Save in the database.
        when(skillRepository.save(skill)).thenReturn(skill);

        // Create an Assessment.
        assessment.setId(4L);
        assessment.setAssessmentAssesseeRole(role);
        assessment.setAssessmentAssessorRole(role2);
        assessment.setSkills(skill);
        assessment.setName("Assessment Test");
        assessment.setDescription("Assessment Test Description");
        assessment.setType(ActivityType.LEARNING);
        assessment.setPreconditions("Preconditions 2 - Assessment Test");
        assessment.setPostconditions("Post-conditions 2 - Assessment Test");
        assessment.addParty(role);
        assessment.addParty(role2);
        assessment.setAssessmentScale(Scale.LETTER);
        assessment.setAssessmentScoringFunction("Assessment Scoring Function - Assessment Test");
        // Save in the database.
        when(assessmentRepository.save(assessment)).thenReturn(assessment);

        // Create an Assessment.
        assessmentSelf.setId(5L);
        assessmentSelf.setAssessmentAssesseeRole(role2);
        assessmentSelf.setAssessmentAssessorRole(role2);
        assessmentSelf.setSkills(skill);
        assessmentSelf.setName("Assessment Self - Test");
        assessmentSelf.setDescription("AssessmentSelf -  Test Description");
        assessmentSelf.setType(ActivityType.BCI_ACTIVITY);
        assessmentSelf.setPreconditions("Preconditions 2 - Assessment Self - Test");
        assessmentSelf.setPostconditions("Post-conditions 2 - Assessment Self - Test");
        assessmentSelf.addParty(role);
        assessmentSelf.addParty(role2);
        assessmentSelf.setAssessmentScale(Scale._100);
        assessmentSelf.setAssessmentScoringFunction("Assessment Scoring Function - Assessment Self - Test");
        // Save in the database.
        when(assessmentRepository.save(assessmentSelf)).thenReturn(assessmentSelf);

        // Create a Requires.
        requires.setId(6L);
        requires.setLevel(SkillLevel.ADVANCED);
        requires.setRole(role);
        requires.setSkill(skill);
        requires.setBciActivity(assessment);
        // Save in the database.
        when(requiresRepository.save(requires)).thenReturn(requires);

        requires1.setId(7L);
        requires1.setLevel(SkillLevel.INTERMEDIATE);
        requires1.setRole(role2);
        requires1.setSkill(skill);
        requires1.setBciActivity(assessment);
        // Save in the database.
        when(requiresRepository.save(requires1)).thenReturn(requires1);

        // Creates Develops.
        develops.setId(8L);
        develops.setLevel(SkillLevel.INTERMEDIATE);
        develops.setRole(role2);
        develops.setSkill(skill);
        develops.setBciActivity(assessment);
        // Save in the database.
        when(developsRepository.save(develops)).thenReturn(develops);

        // Creates Content.
        content.setId(9L);
        content.setName("Content Name - Assessment Test");
        content.setDescription("Content Description - Assessment Test");
        content.setType("Content Video - Assessment Test");
        content.addBCIActivity(assessment);
        // Save in the database.
        when(contentRepository.save(content)).thenReturn(content);

        content2.setId(10L);
        content2.setName("Content2 - Assessment Test");
        content2.setDescription("Content 2 Description - Assessment Test");
        content2.setType("Content Test - Assessment Test");
        content2.addBCIActivity(assessment);
        // Save in the database.
        when(contentRepository.save(content2)).thenReturn(content2);

        // Create the BehaviorChangeInterventionBlock
        behaviorChangeInterventionBlock.setId(11L);
        behaviorChangeInterventionBlock.setEntryConditions("entryConditions - ComposedOf");
        behaviorChangeInterventionBlock.setExitConditions("exitConditions - ComposedOf");
        // Save in the database.
        when(behaviorChangeInterventionBlockRepository.save(behaviorChangeInterventionBlock)).thenReturn(behaviorChangeInterventionBlock);

        // Create a ComposedOf.
        composedOf.setId(12L);
        composedOf.setTiming(TimeCycle.UNSPECIFIED);
        composedOf.setOrder(10);
        composedOf.setBciActivity(assessment);
        composedOf.setBciBlock(behaviorChangeInterventionBlock);
        // Save in the database.
        when(composedOfRepository.save(composedOf)).thenReturn(composedOf);

        // Update Assessment.
        assessment.setId(4L);
        assessment.addDevelops(develops);
        assessment.addContent(content);
        assessment.addContent(content2);
        assessment.addRequires(requires);
        assessment.addRequires(requires1);
        assessment.setComposedOf(composedOf);
        assessment.setAssessmentSelfRelationship(assessmentSelf);
        // Save in the database.
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        // Perform a POST request to test the controller.
        performCreateRequest(URL, assessment);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        // Creates an Assessment invalid.
        Assessment assessmentBadRequest = new Assessment();
        assessmentBadRequest.setId(999L);
        // Perform a POST with a Bad Request to test the controller.
        performCreateRequestBadRequest(URL, assessmentBadRequest);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Create an Assessment.
        Assessment saved = new Assessment();
        saved.setId(585L);
        saved.setName("Assessment - Test Update");
        saved.setDescription("Assessment - Test Update - Description");
        saved.setType(ActivityType.GOAL_SETTING);
        saved.setPreconditions("Preconditions 2 - Assessment - Test Update");
        saved.setPostconditions("Post-conditions 2 - Assessment - Test Update");
        saved.setAssessmentScale(Scale._20);
        saved.setAssessmentScoringFunction("Assessment Scoring Function - Assessment - Test Update");
        saved.setAssessmentAssesseeRole(role);
        saved.setAssessmentAssessorRole(role2);
        saved.setSkills(skill);

        // Save in the database.
        when(assessmentRepository.save(saved)).thenReturn(saved);

        saved.setAssessmentScale(Scale._100);
        saved.setAssessmentScoringFunction("Updated");
        // Save in the database.
        when(assessmentRepository.save(saved)).thenReturn(saved);

        // Mock behavior for findById().
        when(assessmentRepository.findById(saved.getId())).thenReturn(Optional.of(saved));
        // Perform a PUT request to test the controller.
        performUpdateRequest(URL, saved,"$.name", saved.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        // perform a DELETE request to test the controller.
        performDeleteRequest(URL_SPLITTER + assessment.getId(), assessment);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Save in the database.
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Mock behavior for findById().
        when(assessmentRepository.findById(assessment.getId())).thenReturn(Optional.of(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + assessment.getId(), "$.name", assessment.getName());
    }

    @Test
    void testFindByName() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Mock behavior for assessmentRepository.findByName().
        when(assessmentRepository.findByName(assessment.getName())).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "name/" + assessment.getName(),"$[0].name", assessment.getName());
    }

    @Test
    void testFindByType() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Mock behavior for assessmentRepository.findByType().
        when(assessmentRepository.findByType(assessment.getType())).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "type/" + assessment.getType(), "$[0].type", assessment.getType().toString());
    }

    @Test
    void testFindByDevelops() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Gets the Develops.
        Develops develops = assessment.getDevelops().getFirst();
        // Mock behavior for findByDevelopsBCIActivity_Id.
        when(assessmentRepository.findByDevelopsBCIActivity_Id(develops.getId())).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "develops/" + develops.getId(), "$[0].name", assessment.getName());
    }

    @Test
    void testFindByRequires() throws Exception {
        // Mock behavior for findByRequiresBCIActivities_Id.
        when(assessmentRepository.findByRequiresBCIActivities_Id(requires.getId())).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "requires/" + requires.getId(),"$[0].name", assessment.getName());
    }

    @Test
    void testFindByRole() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Mock behavior for findByRoleBCIActivities_Id().
        when(assessmentRepository.findByParties_Id(role.getId())).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "role/" + role.getId(),"$[0].name", assessment.getName());
    }

    @Test
    void testFindByContent() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Gets the Content.
        Content content = assessment.getContent().getFirst();
        // Mock behavior for findByContentBCIActivities_Id().
        when(assessmentRepository.findByContentBCIActivities_Id(content.getId())).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "content/" + content.getId(),"$[0].name", assessment.getName());
    }

    @Test
    void testFindByComposedOf() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Gets the ComposedOf,
        ComposedOf composedOf = assessment.getComposedOf().getFirst();
        // Mock behavior for findByComposedOfList_Id().
        when(assessmentRepository.findByComposedOfList_Id(composedOf.getId())).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "composedof/" + composedOf.getId(),"$[0].name", assessment.getName());
    }

    @Test
    void testFindByAssessmentAssesseeRole_Id() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Gets the Assessee.
        Role role = assessment.getAssessmentAssesseeRole();
        // Mock behavior for findByAssessmentAssesseeRole_Id().
        when(assessmentRepository.findByAssessmentAssesseeRole_Id(role.getId())).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "assesseerole/" + role.getId(),"$[0].name", assessment.getName());
    }

    @Test
    void testFindByAssessmentAssessorRole_Id() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Gets the Assessor.
        Role role = assessment.getAssessmentAssessorRole();
        // Mock behavior for findByAssessmentAssessorRole_Id().
        when(assessmentRepository.findByAssessmentAssessorRole_Id(role.getId())).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "assessorrole/" + role.getId(),"$[0].name", assessment.getName());
    }

    @Test
    void testFindByAssessmentScale() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Gets the Scale.
        Scale scale = assessment.getAssessmentScale();
        // Mock behavior for findByAssessmentScale().
        when(assessmentRepository.findByAssessmentScale(scale)).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "scale/" + scale,"$[0].name", assessment.getName());
    }

    @Test
    void testFindByAssessmentScoringfunction() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Mock behavior for findByAssessmentScoringFunction().
        when(assessmentRepository.findByAssessmentScoringFunction(assessment.getAssessmentScoringFunction())).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "scoringfunction/" + assessment.getAssessmentScoringFunction(),"$[0].name", assessment.getName());
    }

    @Test
    void testFindByAssessmentSelfRelationship_Id() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Mock behavior for findByAssessmentSelfRelationship_Id().
        when(assessmentRepository.findByAssessmentSelfRelationship_Id(assessmentSelf.getId())).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "selfrelationship/" + assessmentSelf.getId(),"$[0].name", assessment.getName());
    }

    @Test
    void testFindByAssessmentSelfRelationship() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Mock behavior for findByAssessmentSelfRelationship().
        when(assessmentRepository.findByAssessmentSelfRelationship(assessmentSelf)).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "selfrelationship", assessmentSelf,"$[0].name", assessment.getName());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for assessmentRepository.save
        when(assessmentRepository.save(assessment)).thenReturn(assessment);
        // Mock behavior for assessmentRepository.findAll().
        when(assessmentRepository.findAll()).thenReturn(Collections.singletonList(assessment));
        // Perform a GET request to test the controller.
        performGetRequest(URL, "$[0].id", assessment.getId());
    }
}
