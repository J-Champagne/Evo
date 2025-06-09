package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.repository.ReportingRepository;
import ca.uqam.latece.evo.server.core.service.ReportingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The Reporting Controller test class for the {@link ReportingController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = ReportingController.class)
@ContextConfiguration(classes = {ReportingController.class, ReportingService.class, Reporting.class})
public class ReportingControllerTest extends AbstractControllerTest {
    @MockBean
    private ReportingRepository reportingRepository;

    private Reporting reporting = new Reporting();
    private BCIActivity bciActivity = new BCIActivity();
    private Develops develops = new Develops();
    private Requires requires = new Requires();
    private Role role = new Role();
    private Skill skill = new Skill();
    private Content content = new Content();

    @BeforeEach
    void setUp() {
        // Create the role associated with reporting.
        List<Role> roles = new ArrayList<>();
        role.setId(1L);
        role.setName("Participant - reporting");
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("e-Facilitator - reporting");

        roles.add(role);
        roles.add(role2);

        // Create a BCI Activity.
        bciActivity.setId(1L);
        bciActivity.setName("Programming 2");
        bciActivity.setDescription("Programming language training 2");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.setPreconditions("Preconditions 2");
        bciActivity.setPostconditions("Post-conditions 2");
        bciActivity.addRole(role);

        // Create a reporting.
        reporting.setId(1L);
        reporting.setName("Programming Reporting");
        reporting.setDescription("Programming language Reporting");
        reporting.setType(ActivityType.DIAGNOSING);
        reporting.setPreconditions("Preconditions");
        reporting.setPostconditions("Post-conditions");
        reporting.setFrequency("Frequency");
        reporting.addRole(role);
        reporting.setBciActivity(bciActivity);

        // Save in the database.
        when(reportingRepository.save(reporting)).thenReturn(reporting);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        // Creates Skill.
        skill.setId(2L);
        skill.setName("Skill name - reporting");
        skill.setDescription("Skill Description - reporting");
        skill.setType(SkillType.BCT);

        // Creates Develops
        develops.setId(2L);
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);
        develops.setBciActivity(bciActivity);

        // Create the Content.
        content.setId(2L);
        content.setName("Content - reporting");
        content.setType("Content type - reporting");
        content.setDescription("Content description - reporting");

        // Create a reporting.
        reporting.setId(3L);
        reporting.setName("Programming Reporting");
        reporting.setDescription("Programming language Reporting");
        reporting.setType(ActivityType.DIAGNOSING);
        reporting.setPreconditions("Preconditions");
        reporting.setPostconditions("Post-conditions");
        reporting.setFrequency("Frequency");
        reporting.addRole(role);
        reporting.setBciActivity(bciActivity);
        reporting.addDevelops(develops);

        performCreateRequest("/reporting", reporting);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update the new reporting.
        reporting.setName("Programming 2 - reporting");
        // Save in the database.
        when(reportingRepository.save(reporting)).thenReturn(reporting);
        // Perform a PUT request to test the controller.
        performUpdateRequest("/reporting", reporting,"$.name", reporting.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/reporting/" + reporting.getId(), reporting);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create a reporting.
        Reporting saved = dataToPerformTheFindTest();

        // Mock behavior for reportingRepository.save
        when(reportingRepository.save(reporting)).thenReturn(reporting);
        when(reportingRepository.save(saved)).thenReturn(saved);

        // Mock behavior for reportingRepository.findById().
        when(reportingRepository.findById(saved.getId())).thenReturn(Optional.of(saved));
        when(reportingRepository.findById(reporting.getId())).thenReturn(Optional.of(reporting));
        // Perform a GET request to test the controller.
        performGetRequest("/reporting/find/" + saved.getId(), "$.name", saved.getName());
        performGetRequest("/reporting/find/" + reporting.getId(), "$.name", reporting.getName());
    }

    @Test
    void testFindByName() throws Exception {
        // Create a reporting.
        Reporting saved = dataToPerformTheFindTest();
        // Mock behavior for reportingRepository.save
        when(reportingRepository.save(reporting)).thenReturn(reporting);
        when(reportingRepository.save(saved)).thenReturn(saved);

        // Mock behavior for reportingRepository.findByName().
        when(reportingRepository.findByName(reporting.getName())).thenReturn(Collections.singletonList(reporting));
        // Perform a GET request to test the controller.
        performGetRequest("/reporting/find/name/" + reporting.getName(),
                "$[0].name", reporting.getName());

        // Mock behavior for reportingRepository.findByName().
        when(reportingRepository.findByName(saved.getName())).thenReturn(Collections.singletonList(saved));
        // Perform a GET request to test the controller.
        performGetRequest("/reporting/find/name/" + saved.getName(),
                "$[0].name", saved.getName());
    }

    @Test
    void testFindByType() throws Exception {
        // Create a reporting.
        Reporting saved = dataToPerformTheFindTest();

        // Mock behavior for reportingRepository.save
        when(reportingRepository.save(reporting)).thenReturn(reporting);
        when(reportingRepository.save(saved)).thenReturn(saved);

        // Mock behavior for reportingRepository.findByType().
        when(reportingRepository.findByType(reporting.getType())).thenReturn(Collections.singletonList(reporting));
        // Perform a GET request to test the controller.
        performGetRequest("/reporting/find/type/" + reporting.getType(),
                "$[0].type", reporting.getType().toString());

        // Mock behavior for reportingRepository.findByType().
        when(reportingRepository.findByType(saved.getType())).thenReturn(Collections.singletonList(saved));
        // Perform a GET request to test the controller.
        performGetRequest("/reporting/find/type/" + saved.getType(),
                "$[0].type", saved.getType().toString());
    }

    private Reporting dataToPerformTheFindTest() throws Exception {
        List<Role> roles = new ArrayList<>();
        Role role2 = new Role();
        role2.setId(3L);
        role2.setName("e-Facilitator 2");

        roles.add(role);
        roles.add(role2);

        Develops develops = new Develops();
        develops.setId(4L);
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);

        requires.setId(6L);
        requires.setLevel(SkillLevel.BEGINNER);
        requires.setSkill(skill);
        requires.setRole(role);

        Content content = new Content();
        content.setId(5L);
        content.setName("Content reporting");
        content.setType("Content type reporting");
        content.setDescription("Content description reporting");

        // Create a reporting.
        Reporting saved = new Reporting();
        saved.setId(2L);
        saved.setName("Database Design 2 reporting");
        saved.setDescription("Database Design training reporting");
        saved.setType(ActivityType.PERFORMING);
        saved.setRole(roles);
        saved.addContent(content);
        saved.addDevelops(develops);
        saved.addRequires(requires);
        saved.setBciActivity(bciActivity);
        saved.setFrequency("Frequency reporting saved");
        saved.setPostconditions("Post-conditions reporting saved");
        saved.setPreconditions("Preconditions reporting saved");

        return saved;
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Create a reporting.
        Reporting saved = dataToPerformTheFindTest();

        // Mock behavior for reportingRepository.save
        when(reportingRepository.save(saved)).thenReturn(saved);

        // Mock behavior for reportingRepository.findAll().
        when(reportingRepository.findAll()).thenReturn(Collections.singletonList(saved));
        // Perform a GET request to test the controller.
        performGetRequest("/reporting", "$[0].id", saved.getId());
    }
}
