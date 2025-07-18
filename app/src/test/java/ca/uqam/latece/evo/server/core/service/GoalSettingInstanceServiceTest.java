package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.GoalSettingInstance;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.service.instance.BCIActivityInstanceService;
import ca.uqam.latece.evo.server.core.service.instance.GoalSettingInstanceService;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;
import ca.uqam.latece.evo.server.core.service.instance.ParticipantService;
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
 * The test class for the {@link GoalSettingInstanceService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@ContextConfiguration(classes = {GoalSettingInstanceService.class, GoalSettingInstance.class})
public class GoalSettingInstanceServiceTest extends AbstractServiceTest {
    @Autowired
    private GoalSettingInstanceService goalSettingInstanceService;

    @Autowired
    private BCIActivityInstanceService bciActivityInstanceService;

    @Autowired
    private GoalSettingService goalSettingService;

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
    private HealthCareProfessionalService healthCareProfessionalService;

    @Autowired
    private ParticipantService participantService;

    private GoalSettingInstance goalSettingInstance = new GoalSettingInstance();
    private BCIActivityInstance bciActivityInstance = new BCIActivityInstance();
    private GoalSetting goalSetting = new GoalSetting();
    private GoalSetting goalSetting2 = new GoalSetting();
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
    private HealthCareProfessional hcp = new HealthCareProfessional();
    private Participant participant = new Participant();

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
        bciActivity.addParty(role);
        // Create a BCI Activity.
        bciActivityService.create(bciActivity);

        // Create a BCI Activity.
        bciActivity2.setName("Programming 21");
        bciActivity2.setDescription("Programming language training 21");
        bciActivity2.setType(ActivityType.LEARNING);
        bciActivity2.setPreconditions("Preconditions 21");
        bciActivity2.setPostconditions("Post-conditions 21");
        bciActivity2.addParty(role);
        // Create a BCI Activity.
        bciActivityService.create(bciActivity2);

        // Create a Goal Setting.
        goalSetting.setName("Programming 222");
        goalSetting.setDescription("Programming language training 2");
        goalSetting.setType(ActivityType.LEARNING);
        goalSetting.setPreconditions("Preconditions 2");
        goalSetting.setPostconditions("Post-conditions 2");
        goalSetting.addParty(role);
        goalSetting.setBciActivity(bciActivity);
        // Create a Goal Setting.
        goalSettingService.create(goalSetting);

        goalSetting2.setName("Testing 233");
        goalSetting2.setDescription("Testing training 2");
        goalSetting2.setType(ActivityType.LEARNING);
        goalSetting2.setPreconditions("Testing Preconditions 2");
        goalSetting2.setPostconditions("Testing Post-conditions 2");
        goalSetting2.addParty(role2);
        goalSetting2.setBciActivity(bciActivity);
        // Create a Goal Setting.
        goalSettingService.create(goalSetting2);

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
        content.setName("Content Name");
        content.setDescription("Content Description");
        content.setType("Content Video");
        content.addBCIActivity(bciActivity);

        content2.setName("Content2");
        content2.setDescription("Content 2 Description");
        content2.setType("Content Test");
        content2.addBCIActivity(bciActivity2);

        // Save the Content.
        contentService.create(content);
        contentService.create(content2);

        // Create and save an Actor
        hcp.setName("Bob");
        hcp.setEmail("bob@gmail.com");
        hcp.setContactInformation("222-2222");
        hcp.setAffiliation("CIUSSS");
        hcp.setPosition("Chief");
        hcp.setSpecialties("None");
        healthCareProfessionalService.create(hcp);

        // Create and save a Participant
        participant.setRole(role);
        participant.setActor(hcp);
        participantService.create(participant);

        // Create BCIActivityInstance.
        bciActivityInstance.setStatus("BCIActivity Instance Java");
        bciActivityInstance.setEntryDate(localEntryDate);
        bciActivityInstance.setExitDate(localExitDate);
        bciActivityInstanceService.create(bciActivityInstance);

        // Save a GoalSettingInstance
        goalSettingInstance.setStatus("Status - Goal Setting Instance Test");
        goalSettingInstance.setGoalSetting(goalSetting);
        goalSettingInstance.setEntryDate(localEntryDate);
        goalSettingInstance.setExitDate(localExitDate);
        goalSettingInstance.addParticipant(participant);
        goalSettingInstanceService.create(goalSettingInstance);
    }

    @AfterEach
    void afterEach(){
        // Delete GoalSettingInstance
        goalSettingInstanceService.deleteById(goalSettingInstance.getId());
        // Delete a Goal Setting.
        goalSettingService.deleteById(goalSetting.getId());
        goalSettingService.deleteById(goalSetting2.getId());
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
        // Delete the bciActivity
        bciActivityService.deleteById(bciActivity.getId());
        // Delete bciActivityInstance
        bciActivityInstanceService.deleteById(bciActivityInstance.getId());
    }

    @Test
    @Override
    void testSave() {
        // Create a GoalSettingInstance.
        GoalSettingInstance goalSettingInstance = new GoalSettingInstance();
        goalSettingInstance.setStatus("Status 12 - Goal Setting Instance Test");
        goalSettingInstance.setGoalSetting(goalSetting);
        goalSettingInstance.setEntryDate(localEntryDate);
        goalSettingInstance.setExitDate(localExitDate);
        // Save the Goal Setting Instance.
        GoalSettingInstance saved = goalSettingInstanceService.create(goalSettingInstance);

        // Checks if the goal setting was saved.
        assert saved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Create a GoalSettingInstance.
        GoalSettingInstance instance = new GoalSettingInstance();
        instance.setId(goalSettingInstance.getId());
        instance.setStatus("Status - Goal Setting Instance Test - Test 1");
        instance.setGoalSetting(goalSetting2);
        instance.setEntryDate(localEntryDate);
        instance.setExitDate(localExitDate);
        // Updated the Goal Setting Instance.
        GoalSettingInstance updated = goalSettingInstanceService.update(instance);

        // Checks if the Behavior Setting id saved is the same of the Behavior Setting updated.
        assertEquals(instance.getId(), updated.getId());
        // Checks if the Behavior Performance Instance status is different.
        assertNotEquals("Status - Goal Setting Instance Test", updated.getStatus());

        assertEquals(localEntryDate, updated.getEntryDate());
        assertEquals(localExitDate, updated.getExitDate());
    }

    @Test
    @Override
    void testFindById() {
        GoalSettingInstance found = goalSettingInstanceService.findById(goalSettingInstance.getId());
        assertEquals(goalSettingInstance.getId(), found.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        // Create a GoalSettingInstance.
        GoalSettingInstance goalSettingInstance = new GoalSettingInstance();
        goalSettingInstance.setStatus("Status - Goal Setting Instance Test 2");
        goalSettingInstance.setGoalSetting(goalSetting);
        goalSettingInstance.setEntryDate(localEntryDate);
        goalSettingInstance.setExitDate(localExitDate);

        // Save the Goal Setting Instance.
        GoalSettingInstance saved = goalSettingInstanceService.create(goalSettingInstance);

        // Delete a Goal Setting.
        goalSettingInstanceService.deleteById(saved.getId());

        // Checks if the Goal Setting Instance was deleted.
        assertFalse(goalSettingInstanceService.existsById(saved.getId()));
    }

    @Test
    void testFindByStatus() {
        // Create a GoalSettingInstance.
        GoalSettingInstance goalSettingInstance = new GoalSettingInstance();
        goalSettingInstance.setStatus("Status-Goal Setting Instance 2");
        goalSettingInstance.setGoalSetting(goalSetting);
        goalSettingInstance.setEntryDate(localEntryDate);
        goalSettingInstance.setExitDate(localExitDate);

        // Save the Goal Setting Instance.
        GoalSettingInstance saved = goalSettingInstanceService.create(goalSettingInstance);

        // Find all GoalSettingInstance.
        List<GoalSettingInstance> found = goalSettingInstanceService.findByStatus(saved.getStatus());

        // Tests.
        assertEquals(1, found.size());
        assertEquals(goalSettingInstance.getStatus(), found.getFirst().getStatus());
    }

    @Test
    @Override
    void testFindAll() {
        // Create a Goal Setting.
        GoalSetting goalSetting = new GoalSetting();
        goalSetting.setName("GoalSetting 222");
        goalSetting.setDescription("GoalSetting training 2");
        goalSetting.setType(ActivityType.LEARNING);
        goalSetting.setPreconditions("GoalSetting Preconditions 2");
        goalSetting.setPostconditions("GoalSetting Post-conditions 2");
        goalSetting.addParty(role);
        goalSetting.setBciActivity(bciActivity);

        // Create a Goal Setting.
        GoalSetting savedGoalSetting = goalSettingService.create(goalSetting);

        // Create a GoalSettingInstance.
        GoalSettingInstance settingInstance = new GoalSettingInstance();
        settingInstance.setStatus("Status - Goal Setting Instance Test 22");
        settingInstance.setGoalSetting(savedGoalSetting);
        settingInstance.setEntryDate(localEntryDate);
        settingInstance.setExitDate(localExitDate);

        // Save the Goal Setting Instance.
        GoalSettingInstance saved = goalSettingInstanceService.create(settingInstance);

        // Find all Goal Setting Instance.
        List<GoalSettingInstance> found = goalSettingInstanceService.findAll();

        // Tests.
        assertEquals(2, found.size());
        assertTrue(found.contains(goalSettingInstance));
        assertTrue(found.contains(saved));
    }

    @Test
    void testFindByParticipantsId() {
        // Find by ParticipantId
        GoalSettingInstance goalSettingInstanceFound = goalSettingInstanceService.findByParticipantsId(participant.getId());
        assertEquals(1, goalSettingInstanceFound.getParticipants().size());
        assertEquals(participant.getId(), goalSettingInstanceFound.getParticipants().getFirst().getId());
    }
}
