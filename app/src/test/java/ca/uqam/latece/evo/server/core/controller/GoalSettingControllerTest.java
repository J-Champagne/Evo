package ca.uqam.latece.evo.server.core.controller;


import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.GoalSetting;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.Content;
import ca.uqam.latece.evo.server.core.model.Develops;
import ca.uqam.latece.evo.server.core.model.Requires;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.repository.GoalSettingRepository;
import ca.uqam.latece.evo.server.core.service.GoalSettingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The GoalSetting Controller test class for the {@link GoalSettingController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = GoalSettingController.class)
@ContextConfiguration(classes = {GoalSettingController.class, GoalSettingService.class, GoalSetting.class})
public class GoalSettingControllerTest extends AbstractControllerTest {
    @MockitoBean
    private GoalSettingRepository goalSettingRepository;

    private GoalSetting goalSetting = new GoalSetting();
    private BCIActivity bciActivity = new BCIActivity();
    private Develops develops = new Develops();
    private Requires requires = new Requires();
    private Role role = new Role();
    private Skill skill = new Skill();
    private Content content = new Content();

    private static final String URL = "/goalsetting";
    private static final String URL_SPLITTER = "/goalsetting/";
    private static final String URL_FIND = "/goalsetting/find/";

    @BeforeEach
    void setUp() {
        // Create the role associated with Goal Setting.
        List<Role> roles = new ArrayList<>();
        role.setId(1L);
        role.setName("Participant - Goal Setting");
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("e-Facilitator - Goal Setting");

        roles.add(role);
        roles.add(role2);

        // Create a BCI Activity.
        bciActivity.setId(3L);
        bciActivity.setName("Programming 2");
        bciActivity.setDescription("Programming language training 2");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.setPreconditions("Preconditions 2");
        bciActivity.setPostconditions("Post-conditions 2");
        bciActivity.addParty(role);

        // Create a Goal Setting.
        goalSetting.setId(4L);
        goalSetting.setName("Programming - Goal Setting");
        goalSetting.setDescription("Programming language training - Goal Setting");
        goalSetting.setType(ActivityType.LEARNING);
        goalSetting.setPreconditions("Preconditions 2");
        goalSetting.setPostconditions("Post-conditions 2");
        goalSetting.addParty(role);
        goalSetting.setBciActivity(bciActivity);

        // Save in the database.
        when(goalSettingRepository.save(goalSetting)).thenReturn(goalSetting);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        // Creates Skill.
        skill.setId(5L);
        skill.setName("Skill name - Goal Setting");
        skill.setDescription("Skill Description - Goal Setting");
        skill.setType(SkillType.BCT);

        // Creates Develops
        develops.setId(6L);
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);
        develops.setBciActivity(goalSetting);

        // Create the Content.
        content.setId(7L);
        content.setName("Content - Goal Setting");
        content.setType("Content type - Goal Setting");
        content.setDescription("Content description - Goal Setting");

        performCreateRequest(URL, goalSetting);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        GoalSetting goalSetting = new GoalSetting();
        goalSetting.setId(99L);
        // Perform a POST request with Bad request to test the controller.
        performCreateRequestBadRequest(URL, goalSetting);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update a new Goal Setting
        goalSetting.setName("Programming 2 - Goal Setting");
        // Save in the database.
        when(goalSettingRepository.save(goalSetting)).thenReturn(goalSetting);
        // Perform a PUT request to test the controller.
        performUpdateRequest(URL, goalSetting,"$.name", goalSetting.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(URL_SPLITTER + goalSetting.getId(), goalSetting);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create a Goal Setting.
        GoalSetting saved = dataToPerformTheFindTest();

        // Mock behavior for goalSettingRepository.save
        when(goalSettingRepository.save(goalSetting)).thenReturn(goalSetting);
        when(goalSettingRepository.save(saved)).thenReturn(saved);

        // Mock behavior for goalSettingRepository.findById().
        when(goalSettingRepository.findById(saved.getId())).thenReturn(Optional.of(saved));
        when(goalSettingRepository.findById(goalSetting.getId())).thenReturn(Optional.of(goalSetting));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + saved.getId(), "$.name", saved.getName());
        performGetRequest(URL_FIND + goalSetting.getId(), "$.name", goalSetting.getName());
    }

    @Test
    void testFindByName() throws Exception {
        // Create a Goal Setting.
        GoalSetting saved = dataToPerformTheFindTest();
        // Mock behavior for goalSettingRepository.save
        when(goalSettingRepository.save(goalSetting)).thenReturn(goalSetting);
        when(goalSettingRepository.save(saved)).thenReturn(saved);

        // Mock behavior for goalSettingRepository.findByName().
        when(goalSettingRepository.findByName(goalSetting.getName())).thenReturn(Collections.singletonList(goalSetting));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "name/" + goalSetting.getName(), "$[0].name", goalSetting.getName());

        // Mock behavior for goalSettingRepository.findByName().
        when(goalSettingRepository.findByName(saved.getName())).thenReturn(Collections.singletonList(saved));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "name/" + saved.getName(), "$[0].name", saved.getName());
    }

    @Test
    void testFindByType() throws Exception {
        // Create a GoalSetting.
        GoalSetting saved = dataToPerformTheFindTest();

        // Mock behavior for goalSettingRepository.save
        when(goalSettingRepository.save(goalSetting)).thenReturn(goalSetting);
        when(goalSettingRepository.save(saved)).thenReturn(saved);

        // Mock behavior for goalSettingRepository.findByType().
        when(goalSettingRepository.findByType(goalSetting.getType())).thenReturn(Collections.singletonList(goalSetting));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "type/" + goalSetting.getType(), "$[0].type", goalSetting.getType().toString());

        // Mock behavior for goalSettingRepository.findByType().
        when(goalSettingRepository.findByType(saved.getType())).thenReturn(Collections.singletonList(saved));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "type/" + saved.getType(), "$[0].type", saved.getType().toString());
    }

    private GoalSetting dataToPerformTheFindTest() throws Exception {
        List<Role> roles = new ArrayList<>();
        Role role2 = new Role();
        role2.setId(8L);
        role2.setName("e-Facilitator 2");

        roles.add(role);
        roles.add(role2);

        Develops develops = new Develops();
        develops.setId(9L);
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);

        requires.setId(10L);
        requires.setLevel(SkillLevel.BEGINNER);
        requires.setSkill(skill);
        requires.setRole(role);

        Content content = new Content();
        content.setId(11L);
        content.setName("Content - Goal Setting");
        content.setType("Content type - Goal Setting");
        content.setDescription("Content description - Goal Setting");

        // Create a Goal Setting.
        GoalSetting saved = new GoalSetting();
        saved.setId(12L);
        saved.setName("Database Design 2 - Goal Setting");
        saved.setDescription("Database Design training - Goal Setting");
        saved.setPostconditions("Post-conditions Goal Setting");
        saved.setPreconditions("Preconditions Goal Setting");
        saved.setType(ActivityType.DIAGNOSING);
        saved.setBciActivity(bciActivity);
        saved.setParties(roles);
        saved.addContent(content);
        saved.addDevelops(develops);
        saved.addRequires(requires);

        return saved;
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Create a Goal Setting.
        GoalSetting saved = dataToPerformTheFindTest();

        // Mock behavior for goalSettingRepository.save
        when(goalSettingRepository.save(saved)).thenReturn(saved);

        // Mock behavior for goalSettingRepository.findAll().
        when(goalSettingRepository.findAll()).thenReturn(Collections.singletonList(saved));
        // Perform a GET request to test the controller.
        performGetRequest(URL, "$[0].id", saved.getId());
    }
}
