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
 * The test class for the {@link InteractionService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {InteractionService.class, Interaction.class})
public class InteractionServiceTest extends AbstractServiceTest {

    @Autowired
    private InteractionService interactionService;

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

    private Interaction interaction = new Interaction();
    private Interaction interaction2 = new Interaction();
    private Role role = new Role();
    private Role role2 = new Role();
    private Role initiatorRole = new Role();
    private Role party1 = new Role();
    private Role party2 = new Role();
    private Role party3 = new Role();
    private Role party4 = new Role();
    private Skill skill = new Skill();
    private Requires requires = new Requires();
    private Requires requires1 = new Requires();
    private Content content = new Content();
    private Content content2 = new Content();
    private Develops develops = new Develops();


    @BeforeEach
    void beforeEach(){
        // Create a Role.
        role.setName("Admin - Interaction Test");
        role2.setName("Participant - Interaction Test");
        initiatorRole.setName("Initiator Role - Interaction Test");
        initiatorRole.setDescription("Initiator Role - Interaction Test");
        party1.setName("Party 1 Role - Interaction Test");
        party1.setDescription("Party 1 Role - Interaction Test");
        party2.setName("Party 2 Role - Interaction Test");
        party2.setDescription("Party 2 Role - Interaction Test");
        party3.setName("Party 3 Role - Interaction Test");
        party3.setDescription("Party 3 Role - Interaction Test");
        party4.setName("Party 4 Role - Interaction Test");
        party4.setDescription("Party 4 Role - Interaction Test");

        // Create a Skill.
        skill.setName("Skill Name - Interaction Test");
        skill.setDescription("Skill Description - Interaction Test");
        skill.setType(SkillType.BCT);

        // Create a Role.
        roleService.create(role);
        roleService.create(role2);
        roleService.create(initiatorRole);
        roleService.create(party1);
        roleService.create(party2);
        roleService.create(party3);
        roleService.create(party4);
        // Create a Skill.
        skillService.create(skill);

        // Create an Interaction.
        interaction.setName("Interaction Test");
        interaction.setDescription("Interaction Test");
        interaction.setType(ActivityType.LEARNING);
        interaction.setPreconditions("Preconditions 2 - Interaction Test");
        interaction.setPostconditions("Post-conditions 2 - Interaction Test");
        interaction.addParty(party1, party2);
        interaction.setInteractionMedium1(InteractionMedium.MESSAGING);
        interaction.setInteractionMedium2(InteractionMedium.EMAIL);
        interaction.setInteractionMedium3(InteractionMedium.VIDEO);
        interaction.setInteractionMedium4(InteractionMedium.VOICE);
        interaction.setInteractionMode(InteractionMode.ASYNCHRONOUS);
        interaction.setInteractionInitiatorRole(initiatorRole);
        // Create an Interaction.
        interactionService.create(interaction);

        interaction2.setName("Testing - Interaction Test");
        interaction2.setDescription("Testing training - Interaction Test");
        interaction2.setType(ActivityType.LEARNING);
        interaction2.setPreconditions("Testing Preconditions 2 - Interaction Test");
        interaction2.setPostconditions("Testing Post-conditions 2 - Interaction Test");
        interaction2.addParty(party3, party4);
        interaction2.setInteractionMedium1(InteractionMedium.VIDEO);
        interaction2.setInteractionMedium2(InteractionMedium.VOICE);
        interaction2.setInteractionMedium3(InteractionMedium.MESSAGING);
        interaction2.setInteractionMedium4(InteractionMedium.EMAIL);
        interaction2.setInteractionMode(InteractionMode.SYNCHRONOUS);
        interaction2.setInteractionInitiatorRole(initiatorRole);
        // Create an Interaction.
        interactionService.create(interaction2);

        // Create a Requires.
        requires.setLevel(SkillLevel.ADVANCED);
        requires.setRole(role);
        requires.setSkill(skill);
        requires.setBciActivity(interaction);

        requires1.setLevel(SkillLevel.INTERMEDIATE);
        requires1.setRole(role2);
        requires1.setSkill(skill);
        requires1.setBciActivity(interaction2);

        // Save the requires.
        requiresService.create(requires);
        requiresService.create(requires1);

        develops.setLevel(SkillLevel.INTERMEDIATE);
        develops.setRole(role2);
        develops.setSkill(skill);
        develops.setBciActivity(interaction);
        developsService.create(develops);

        // Create Content.
        content.setName("Content Name - Interaction Test");
        content.setDescription("Content Description - Interaction Test");
        content.setType("Content Video - Interaction Test");
        content.addBCIActivity(interaction);

        content2.setName("Content2 - Interaction Test");
        content2.setDescription("Content 2 Description - Interaction Test");
        content2.setType("Content Test - Interaction Test");
        content2.addBCIActivity(interaction2);

        // Save the Content.
        contentService.create(content);
        contentService.create(content2);

        // Update Interaction.
        interaction.addDevelops(develops);
        interaction.addContent(content);
        interaction.addContent(content2);
        interaction.addRequires(requires);
        interaction.addRequires(requires1);
        interactionService.update(interaction);

        interaction2.addDevelops(develops);
        interaction2.addContent(content);
        interaction2.addContent(content2);
        interaction2.addRequires(requires);
        interaction2.addRequires(requires1);
        interactionService.update(interaction2);
    }

    @AfterEach
    void afterEach(){
        // Delete an Interaction.
        interactionService.deleteById(interaction.getId());
        interactionService.deleteById(interaction2.getId());
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
        Interaction interaction = new Interaction();
        // Create an Interaction.
        interaction.setName("Name - Interaction Test");
        interaction.setDescription("Description - Interaction Test");
        interaction.setType(ActivityType.BCI_ACTIVITY);
        interaction.setPreconditions("Preconditions - Interaction Test");
        interaction.setPostconditions("PostConditions - Interaction Test");
        interaction.addParty(party1, party2, party3, party4);
        interaction.setInteractionMedium1(InteractionMedium.EMAIL);
        interaction.setInteractionMode(InteractionMode.ASYNCHRONOUS);
        interaction.setInteractionInitiatorRole(initiatorRole);
        // Create an Interaction.
        Interaction interactionSaved = interactionService.create(interaction);

        // Checks if the Interaction was saved.
        assert interactionSaved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        // Create an Interaction.
        Interaction interactionSaved = new Interaction();
        interactionSaved.setId(interaction.getId());
        interactionSaved.setName("Database - Interaction Test");
        interactionSaved.setDescription("Database training - Interaction Test");
        interactionSaved.setPreconditions("Preconditions - Interaction Test");
        interactionSaved.setPostconditions("Post-conditions - Interaction Test");
        interactionSaved.setType(interaction.getType());
        interactionSaved.setParties(interaction.getParties());
        interactionSaved.setInteractionMedium1(InteractionMedium.VIDEO);
        interactionSaved.setInteractionMode(InteractionMode.SYNCHRONOUS);
        interactionSaved.setInteractionInitiatorRole(initiatorRole);

        // Update an Interaction.
        Interaction interactionUpdated = interactionService.update(interactionSaved);

        // Checks if the Interaction id saved is the same of the Interaction updated.
        assertEquals(interactionSaved.getId(), interactionUpdated.getId());
        // Tests.
        assertEquals(interactionSaved.getName(), interactionUpdated.getName());
        assertEquals(interactionSaved.getName(), interactionUpdated.getName());
        assertEquals(interactionSaved.getDescription(), interactionUpdated.getDescription());
    }

    @Test
    @Override
    public void testFindById() {
        // Create an Interaction.
        Interaction interaction1 = new Interaction();
        interaction1.setId(interaction.getId());
        interaction1.setName("Database - Interaction Test");
        interaction1.setDescription("Database training - Interaction Test");
        interaction1.setPreconditions("Preconditions - Interaction Test");
        interaction1.setPostconditions("Post-conditions - Interaction Test");
        interaction1.setType(interaction.getType());
        interaction1.setParties(interaction.getParties());
        interaction1.setInteractionMedium1(InteractionMedium.VIDEO);
        interaction1.setInteractionMode(InteractionMode.SYNCHRONOUS);
        interaction1.setInteractionInitiatorRole(initiatorRole);

        // Create an Interaction.
        Interaction interactionSaved = interactionService.create(interaction1);
        Interaction interactionFound = interactionService.findById(interactionSaved.getId());
        assertEquals(interactionSaved.getId(), interactionFound.getId());
    }

    @Test
    void testFindByName() {
        // Checks if the name is equals.
        assertEquals(interaction.getName(), interactionService.findByName(interaction.getName()).getFirst().getName());
    }

    @Test
    @Override
    void testDeleteById() {
        // Create an Interaction.
        Interaction interactionSaved = new Interaction();
        interactionSaved.setName("Delete - Interaction Test");
        interactionSaved.setDescription("Delete training - Interaction Test");
        interactionSaved.setPreconditions("Preconditions - Interaction Test");
        interactionSaved.setPostconditions("Post-conditions - Interaction Test");
        interactionSaved.setType(interaction.getType());
        interactionSaved.setParties(interaction.getParties());
        interactionSaved.setInteractionMedium1(InteractionMedium.VIDEO);
        interactionSaved.setInteractionMode(InteractionMode.SYNCHRONOUS);
        interactionSaved.setInteractionInitiatorRole(initiatorRole);

        interactionService.create(interactionSaved);
        // Delete an Interaction.
        interactionService.deleteById(interactionSaved.getId());
        // Checks if the Interaction was deleted.
        assertFalse(interactionService.existsById(interactionSaved.getId()));
    }

    @Test
    void testFindType() {
        List<Interaction> interactionList = interactionService.findByType(ActivityType.LEARNING);

        assertEquals(2, interactionList.size());
        assertEquals(interaction.getId(), interactionList.get(0).getId());
        assertEquals(interaction.getType(), interactionList.get(0).getType());
        assertEquals(interaction2.getId(), interactionList.get(1).getId());
        assertEquals(interaction2.getType(), interactionList.get(1).getType());
    }

    @Test
    void testExistsByName() {
        assertEquals(interaction.getName(), interactionService.findByName(interaction.getName()).getFirst().getName());
    }

    @Test
    void testFindByDevelops() {
        List<Interaction> result = interactionService.findByDevelops(develops.getId());
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(interaction.getName(), result.getFirst().getName());
    }

    @Test
    void testFindByRequires() {
        List<Interaction> result = interactionService.findByRequires(requires1.getId());
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(interaction2.getName(), result.getFirst().getName());
    }

    @Test
    void testFindByContent() {
        List<Interaction> result = interactionService.findByContent(content.getId());
        // Assert that the result
        assertEquals(2, result.size());
        assertEquals(interaction.getName(), result.getFirst().getName());
    }

    @Test
    void testFindByInteractionInitiatorRoleId() {
        List<Interaction> result = interactionService.findByInteractionInitiatorRole_Id(initiatorRole.getId());
        // Assert that the result
        assertEquals(2, result.size());
        assertEquals(interaction.getName(), result.getFirst().getName());
    }

   @Test
    void testFindByPartiesId() {
        List<Interaction> result = interactionService.findByParties_Id(party1.getId());
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(interaction.getName(), result.getFirst().getName());
    }

    @Test
    void testFindByInteractionMode() {
        List<Interaction> result = interactionService.findByInteractionMode(InteractionMode.ASYNCHRONOUS);
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(interaction.getName(), result.getFirst().getName());
    }

    @Test
    void testFindByInteractionMedium1() {
        List<Interaction> result = interactionService.findByInteractionMedium1(InteractionMedium.MESSAGING);
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(interaction.getName(), result.getFirst().getName());
    }

    @Test
    void testFindByInteractionMedium2() {
        List<Interaction> result = interactionService.findByInteractionMedium2(InteractionMedium.EMAIL);
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(interaction.getName(), result.getFirst().getName());
    }

    @Test
    void testFindByInteractionMedium3() {
        List<Interaction> result = interactionService.findByInteractionMedium3(InteractionMedium.VIDEO);
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(interaction.getName(), result.getFirst().getName());
    }

    @Test
    void testFindByInteractionMedium4() {
        List<Interaction> result = interactionService.findByInteractionMedium4(InteractionMedium.VOICE);
        // Assert that the result
        assertEquals(1, result.size());
        assertEquals(interaction.getName(), result.getFirst().getName());
    }

    @Test
    @Override
    void testFindAll() {
        Interaction interaction = new Interaction();
        // Create an Interaction.
        interaction.setName("Name - Interaction Test");
        interaction.setDescription("Description - Interaction Test");
        interaction.setType(ActivityType.BCI_ACTIVITY);
        interaction.setPreconditions("Preconditions - Interaction Test");
        interaction.setPostconditions("PostConditions - Interaction Test");
        interaction.addParty(role);
        interaction.setInteractionMedium1(InteractionMedium.EMAIL);
        interaction.setInteractionMode(InteractionMode.ASYNCHRONOUS);
        interaction.setInteractionInitiatorRole(initiatorRole);
        // Create an Interaction.
        Interaction interactionSaved = interactionService.create(interaction);

        // Find all interactions.
        List<Interaction> interactionList = interactionService.findAll();

        // Tests.
        assertEquals(3, interactionList.size());
        assertTrue(interactionList.contains(interaction));
        assertTrue(interactionList.contains(interaction2));
        assertTrue(interactionList.contains(interactionSaved));
    }
}
