package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.service.instance.BCIActivityInstanceService;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorPerformanceInstanceService;
import ca.uqam.latece.evo.server.core.model.*;
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
 * The test class for the {@link BehaviorPerformanceInstanceService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@ContextConfiguration(classes = {BehaviorPerformanceInstanceService.class, BehaviorPerformanceInstance.class})
public class BehaviorPerformanceInstanceServiceTest extends AbstractServiceTest {
    @Autowired
    private BehaviorPerformanceInstanceService behaviorPerformanceInstanceService;

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
    private BehaviorPerformanceService behaviorPerformanceService;

    @Autowired
    private HealthCareProfessionalService healthCareProfessionalService;

    @Autowired
    private ParticipantService participantService;

    private BehaviorPerformanceInstance behaviorPerformanceInstance = new BehaviorPerformanceInstance();
    private BCIActivityInstance bciActivityInstance = new BCIActivityInstance();
    private BehaviorPerformance behaviorPerformance = new BehaviorPerformance();
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
        bciActivity.addRole(role);
        // Create a BCI Activity.
        bciActivityService.create(bciActivity);

        // Create a BCI Activity.
        bciActivity2.setName("Programming 21");
        bciActivity2.setDescription("Programming language training 21");
        bciActivity2.setType(ActivityType.LEARNING);
        bciActivity2.setPreconditions("Preconditions 21");
        bciActivity2.setPostconditions("Post-conditions 21");
        bciActivity2.addRole(role);
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
        bciActivityInstance.setBciActivity(bciActivity);
        bciActivityInstanceService.create(bciActivityInstance);

        // Create a behavior performance.
        behaviorPerformance.setName("behavior Performance");
        behaviorPerformance.setDescription("behavior Performance training 2");
        behaviorPerformance.setType(ActivityType.LEARNING);
        behaviorPerformance.setPreconditions("Preconditions 2");
        behaviorPerformance.setPostconditions("Post-conditions 2");
        behaviorPerformance.addRole(role);
        behaviorPerformanceService.create(behaviorPerformance);

        // Create a BehaviorPerformanceInstance.
        behaviorPerformanceInstance.setStatus("Status Testing - Behavior Performance Instance Test");
        behaviorPerformanceInstance.setEntryDate(localEntryDate);
        behaviorPerformanceInstance.setExitDate(localExitDate);
        behaviorPerformanceInstance.setBciActivity(bciActivity);
        behaviorPerformanceInstance.setBehaviorPerformance(behaviorPerformance);
        behaviorPerformanceInstance.addParticipant(participant);
        behaviorPerformanceInstanceService.create(behaviorPerformanceInstance);
    }

    @AfterEach
    void afterEach(){
        // Delete a behavior performance Instance.
        behaviorPerformanceInstanceService.deleteById(behaviorPerformanceInstance.getId());
        // Delete a behavior performance.
        behaviorPerformanceService.deleteById(behaviorPerformance.getId());
        // Delete a bciActivityInstanceService.
        bciActivityInstanceService.deleteById(bciActivity.getId());
        //  Delete a bciActivityService.
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
        // Create a Behavior Performance instance.
        BehaviorPerformanceInstance behaviorPerformanceInstance = new BehaviorPerformanceInstance();
        behaviorPerformanceInstance.setStatus("Status - Behavior Performance Instance Test - Save");
        behaviorPerformanceInstance.setBciActivity(bciActivity);
        behaviorPerformanceInstance.setEntryDate(localEntryDate);
        behaviorPerformanceInstance.setExitDate(localExitDate);
        behaviorPerformanceInstance.setBehaviorPerformance(behaviorPerformance);

        // Create a behavior performance instance.
        BehaviorPerformanceInstance saved = behaviorPerformanceInstanceService.create(behaviorPerformanceInstance);

        // Checks if the Behavior Performance instance was saved.
        assert saved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Create a Behavior Performance Instance.
        BehaviorPerformanceInstance saved = new BehaviorPerformanceInstance();
        saved.setId(behaviorPerformanceInstance.getId());
        saved.setStatus("Status Update - Behavior Performance Instance Test - Update");
        saved.setBciActivity(bciActivity2);
        saved.setEntryDate(localEntryDate);
        saved.setExitDate(localExitDate);
        saved.setBehaviorPerformance(behaviorPerformance);

        // Update a Behavior Performance Instance.
        BehaviorPerformanceInstance updated = behaviorPerformanceInstanceService.update(saved);

        // Checks if the Behavior Performance id saved is the same of the Behavior Performance updated.
        assertEquals(saved.getId(), updated.getId());

        // Checks if the Behavior Performance Instance status is different.
        assertNotEquals("Status Testing - Behavior Performance Instance Test", updated.getStatus());
        assertEquals(localEntryDate, updated.getEntryDate());
        assertEquals(localExitDate, updated.getExitDate());
        assertEquals(behaviorPerformance.getId(), updated.getBehaviorPerformance().getId());
    }

    @Test
    @Override
    void testFindById() {
        BehaviorPerformanceInstance found = behaviorPerformanceInstanceService.findById(behaviorPerformanceInstance.getId());
        assertEquals(behaviorPerformanceInstance.getId(), found.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        // Create a Behavior Performance instance.
        BehaviorPerformanceInstance behaviorPerformanceInstance = new BehaviorPerformanceInstance();
        behaviorPerformanceInstance.setStatus("Status 123 - Behavior Performance Instance Test");
        behaviorPerformanceInstance.setBciActivity(bciActivity);
        behaviorPerformanceInstance.setEntryDate(localEntryDate);
        behaviorPerformanceInstance.setExitDate(localExitDate);
        behaviorPerformanceInstance.setBehaviorPerformance(behaviorPerformance);

        // Create a behavior performance instance.
        BehaviorPerformanceInstance saved = behaviorPerformanceInstanceService.create(behaviorPerformanceInstance);

        // Delete a Behavior Performance instance.
        behaviorPerformanceInstanceService.deleteById(saved.getId());
        // Checks if the Behavior Performance was deleted.
        assertFalse(behaviorPerformanceInstanceService.existsById(saved.getId()));
    }

    @Test
    void testFindByStatus() {
        // Create a Behavior Performance instance.
        BehaviorPerformanceInstance behaviorPerformanceInstance = new BehaviorPerformanceInstance();
        behaviorPerformanceInstance.setStatus("Status-Behavior Performance Instance Test");
        behaviorPerformanceInstance.setBciActivity(bciActivity);
        behaviorPerformanceInstance.setEntryDate(localEntryDate);
        behaviorPerformanceInstance.setExitDate(localExitDate);
        behaviorPerformanceInstance.setBehaviorPerformance(behaviorPerformance);

        // Create a behavior performance instance.
        BehaviorPerformanceInstance saved = behaviorPerformanceInstanceService.create(behaviorPerformanceInstance);

        // Find all behavior performance instance.
        List<BehaviorPerformanceInstance> found = behaviorPerformanceInstanceService.findByStatus(saved.getStatus());
        // Tests.
        assertEquals(1, found.size());
        assertEquals(behaviorPerformanceInstance.getStatus(), found.getFirst().getStatus());
    }

    @Test
    @Override
    void testFindAll() {
        // Create a Behavior Performance instance.
        BehaviorPerformanceInstance behaviorPerformanceInstance = new BehaviorPerformanceInstance();
        behaviorPerformanceInstance.setStatus("Status 3 - Behavior Performance Instance Test");
        behaviorPerformanceInstance.setBciActivity(bciActivity2);
        behaviorPerformanceInstance.setEntryDate(localEntryDate);
        behaviorPerformanceInstance.setExitDate(localExitDate);
        behaviorPerformanceInstance.setBehaviorPerformance(behaviorPerformance);

        // Create a behavior performance instance.
        BehaviorPerformanceInstance saved = behaviorPerformanceInstanceService.create(behaviorPerformanceInstance);

        // Find all Behavior Performance instance.
        List<BehaviorPerformanceInstance> found = behaviorPerformanceInstanceService.findAll();

        // Tests.
        assertEquals(2, found.size());
        assertTrue(found.contains(behaviorPerformanceInstance));
        assertTrue(found.contains(saved));
    }

    @Test
    void testFindByParticipantsId() {
        // Find by ParticipantId
        BehaviorPerformanceInstance BehaviorPerformanceFound = behaviorPerformanceInstanceService.findByParticipantsId(participant.getId());
        assertEquals(1, BehaviorPerformanceFound.getParticipants().size());
        assertEquals(participant.getId(), BehaviorPerformanceFound.getParticipants().getFirst().getId());
    }
}
