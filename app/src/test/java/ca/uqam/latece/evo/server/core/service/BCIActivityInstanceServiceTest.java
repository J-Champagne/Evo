package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.*;
import ca.uqam.latece.evo.server.core.event.BCIBlockInstanceClientEvent;
import ca.uqam.latece.evo.server.core.exceptions.ExitConditionException;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.request.BCIActivityInstanceRequest;
import ca.uqam.latece.evo.server.core.service.instance.BCIActivityInstanceService;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionBlockInstanceService;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;
import ca.uqam.latece.evo.server.core.service.instance.ParticipantService;
import ca.uqam.latece.evo.server.core.util.DateFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The test class for the {@link BCIActivityInstanceService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@RecordApplicationEvents
@ApplicationScope
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
    private ParticipantService participantService;

    @Autowired
    private HealthCareProfessionalService healthCareProfessionalService;

    @Autowired
    private BehaviorChangeInterventionBlockService behaviorChangeInterventionBlockService;

    @Autowired
    BehaviorChangeInterventionBlockInstanceService behaviorChangeInterventionBlockInstanceService;

    @Autowired
    private ApplicationEvents applicationEvents;

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
    private HealthCareProfessional hcp = new HealthCareProfessional();
    private Participant participant = new Participant();
    private LocalDate localEntryDate = DateFormatter.convertDateStrTo_yyyy_MM_dd("2020/01/08");
    private LocalDate localExitDate = LocalDate.now();
    private BehaviorChangeInterventionBlockInstance blockInstance;

    @BeforeEach
    void beforeEach() {
        // Create and save a Role
        role.setName("Admin - BCIActivity Test");
        role2.setName("Participant - BCIActivity Test");
        roleService.create(role);
        roleService.create(role2);

        // Create and save a Skill
        skill.setName("Java - BCIActivity Test");
        skill.setDescription("Programming language - BCIActivity Test");
        skill.setType(SkillType.BCT);
        skillService.create(skill);

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

        // Creates and saves BCI Activity
        bciActivity.setName("Programming 2 - BCIActivity Test");
        bciActivity.setDescription("Programming language training 2 - BCIActivity Test");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.setPreconditions("Preconditions 2 - BCIActivity Test");
        bciActivity.setPostconditions("Post-conditions 2 - BCIActivity Test");
        bciActivity.addParty(role);
        bciActivityService.create(bciActivity);

        bciActivity2.setName("Testing 2 - BCIActivity Test");
        bciActivity2.setDescription("Testing training 2 - BCIActivity Test");
        bciActivity2.setType(ActivityType.LEARNING);
        bciActivity2.setPreconditions("Testing Preconditions 2 - BCIActivity Test");
        bciActivity2.setPostconditions("Testing Post-conditions 2 - BCIActivity Test");
        bciActivity2.addParty(role2);
        bciActivityService.create(bciActivity2);

        // Creates and saves Requires
        requires.setLevel(SkillLevel.ADVANCED);
        requires.setRole(role);
        requires.setSkill(skill);
        requires.setBciActivity(bciActivity);
        requiresService.create(requires);

        requires1.setLevel(SkillLevel.INTERMEDIATE);
        requires1.setRole(role2);
        requires1.setSkill(skill);
        requires1.setBciActivity(bciActivity2);
        requiresService.create(requires1);

        // Create and save Develops
        develops.setLevel(SkillLevel.INTERMEDIATE);
        develops.setRole(role2);
        develops.setSkill(skill);
        develops.setBciActivity(bciActivity);
        developsService.create(develops);

        // Create and save Content
        content.setName("Content Name - BCIActivity Test");
        content.setDescription("Content Description - BCIActivity Test");
        content.setType("Content Video - BCIActivity Test");
        content.addBCIActivity(bciActivity);
        contentService.create(content);

        content2.setName("Content2 - BCIActivity Test");
        content2.setDescription("Content 2 Description - BCIActivity Test");
        content2.setType("Content Test - BCIActivity Test");
        content2.addBCIActivity(bciActivity2);
        contentService.create(content2);

        // Create and save a BCIActivityInstance
        bciActivityInstance.setStatus(ExecutionStatus.UNKNOWN);
        bciActivityInstance.setEntryDate(localEntryDate);
        bciActivityInstance.setExitDate(localExitDate);
        bciActivityInstance.setBciActivity(bciActivity);
        bciActivityInstance.addParticipant(participant);
        bciActivityInstanceService.create(bciActivityInstance);

        List<BCIActivityInstance> activities = new ArrayList<>();
        activities.add(bciActivityInstance);

        BehaviorChangeInterventionBlock bciBlock = behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("Intervention ENTRY", "Intervention EXIT"));

        blockInstance = behaviorChangeInterventionBlockInstanceService.
                create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
                        DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), TimeCycle.MIDDLE, activities, bciBlock));
    }

    @AfterEach
    void afterEach(){
        // Delete a BCI Activity.
        bciActivityService.deleteById(bciActivity.getId());
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
        // Delete the BCIActivityInstance.
        bciActivityInstanceService.deleteById(bciActivityInstance.getId());
    }

    @Test
    @Override
    void testSave() {
        // Create BCIActivityInstance.
        BCIActivityInstance bciActivityInstance = new BCIActivityInstance();
        bciActivityInstance.setStatus(ExecutionStatus.UNKNOWN);
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
        instance.setStatus(ExecutionStatus.IN_PROGRESS);
        instance.setEntryDate(localEntryDate);
        instance.setExitDate(localExitDate);
        instance.setBciActivity(bciActivity);
        BCIActivityInstance saved = bciActivityInstanceService.update(instance);

        // Checks if the BCI Activity Instance id saved is the same of the BCI Activity Instance updated.
        assertEquals(saved.getId(), bciActivityInstance.getId());
        // Checks if the BCI Activity Instance Status is equals.
        assertEquals(ExecutionStatus.IN_PROGRESS, saved.getStatus());
    }

    @Test
    @Override
    void testFindById() {
        BCIActivityInstance instance = new BCIActivityInstance();
        instance.setStatus(ExecutionStatus.IN_PROGRESS);
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
        instance.setStatus(ExecutionStatus.SUSPENDED);
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
        instance.setStatus(ExecutionStatus.IN_PROGRESS);
        instance.setEntryDate(localEntryDate);
        instance.setExitDate(localExitDate);
        instance.setBciActivity(bciActivity);
        BCIActivityInstance saved = bciActivityInstanceService.create(instance);

        // Find all BCIActivityInstance.
        List<BCIActivityInstance> found = bciActivityInstanceService.findByStatus(saved.getStatus());
        // Tests.
        assertEquals(1, found.size());
        assertEquals(instance.getStatus(), found.getFirst().getStatus());
    }

    @Test
    @Override
    void testFindAll() {
        BCIActivityInstance instance = new BCIActivityInstance();
        instance.setStatus(ExecutionStatus.FINISHED);
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

    @Test
    void testFindByParticipantsId() {
        BCIActivityInstance instance = new BCIActivityInstance();
        instance.setStatus(ExecutionStatus.IN_PROGRESS);
        instance.setEntryDate(localEntryDate);
        instance.setExitDate(localExitDate);
        instance.setBciActivity(bciActivity);
        instance.addParticipant(participant);
        BCIActivityInstance saved = bciActivityInstanceService.create(instance);

        // Find by ParticipantId
        List<BCIActivityInstance> bciActivities = bciActivityInstanceService.findByParticipantsId(participant.getId());
        assertEquals(1, bciActivities.getFirst().getParticipants().size());
        assertEquals(participant.getId(), bciActivities.getFirst().getParticipants().getFirst().getId());
    }

    @Test
    void testAddParticipantMoreThan3Fail() {
        Participant participant2 = new Participant(role2, hcp);
        Participant participant3 = new Participant(role2, hcp);
        Participant participant4 = new Participant(role, hcp);

        bciActivityInstance.addParticipant(participant2);
        bciActivityInstance.addParticipant(participant3);
        assertThrows(IndexOutOfBoundsException.class, () -> bciActivityInstance.addParticipant(participant4));
    }

    @Test
    void testHandleClientEventFinishFailExitConditionsNotMet() {
        BCIActivityInstanceRequest request = new BCIActivityInstanceRequest(bciActivityInstance.getId(),
                2L, 3L, 4L);
        assertThrows(ExitConditionException.class, () -> bciActivityInstanceService.handleClientEvent(ClientEvent.FINISH, request));
    }

    @Test
    void testHandleClientEventFinishFailNullId() {
        BCIActivityInstanceRequest request = new BCIActivityInstanceRequest(bciActivityInstance.getId(),
                2L, null, 4L);
        assertThrows(IllegalArgumentException.class, () -> bciActivityInstanceService.handleClientEvent(ClientEvent.FINISH, request));
    }

    @Test
    void testHandleClientEventFinishSuccess() {
        bciActivityInstance.getBciActivity().setPostconditions("");
        BCIActivityInstanceRequest request = new BCIActivityInstanceRequest(bciActivityInstance.getId(), blockInstance.getId(), 3L, 4L);
        BCIActivityInstance updated = bciActivityInstanceService.handleClientEvent(ClientEvent.FINISH, request);
        assertEquals(ExecutionStatus.FINISHED, updated.getStatus());
        assertNotNull(updated.getExitDate());
        assertEquals(1, applicationEvents.stream(BCIBlockInstanceClientEvent.class).count());
    }
}
