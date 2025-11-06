package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMedium;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMode;
import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.model.Interaction;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.model.Requires;
import ca.uqam.latece.evo.server.core.model.Content;
import ca.uqam.latece.evo.server.core.model.Develops;
import ca.uqam.latece.evo.server.core.repository.InteractionRepository;
import ca.uqam.latece.evo.server.core.service.InteractionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The Interaction Controller test class for the {@link InteractionController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = InteractionController.class)
@ContextConfiguration(classes = {InteractionController.class, InteractionService.class, Interaction.class})
public class InteractionControllerTest extends AbstractControllerTest {

    @MockitoBean
    private InteractionRepository interactionRepository;

    private Interaction interaction = new Interaction();
    private Develops develops = new Develops();
    private Requires requires = new Requires();
    private Requires requires1 = new Requires();
    private Role role = new Role();
    private Role initiatorRole = new Role();
    private Role party1 = new Role();
    private Role party2 = new Role();
    private Role party3 = new Role();
    private Role party4 = new Role();
    private Skill skill = new Skill();
    private Content content = new Content();
    private Content content2 = new Content();

    private static final String URL = "/interaction";
    private static final String URL_SPLITTER = "/interaction/";
    private static final String URL_FIND = "/interaction/find/";


    @BeforeEach
    void setUp() {
        // Create the role associated with Interaction.
        List<Role> roles = new ArrayList<>();
        role.setId(1L);
        role.setName("Participant");
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("e-Facilitator");

        roles.add(role);
        roles.add(role2);

        initiatorRole.setId(3L);
        initiatorRole.setName("Initiator Role - Interaction Test");
        initiatorRole.setDescription("Initiator Role - Interaction Test");

        party1.setId(4L);
        party1.setName("Party 1 Role - Interaction Test");
        party1.setDescription("Party 1 Role - Interaction Test");
        party2.setId(5L);
        party2.setName("Party 2 Role - Interaction Test");
        party2.setDescription("Party 2 Role - Interaction Test");
        party3.setId(6L);
        party3.setName("Party 3 Role - Interaction Test");
        party3.setDescription("Party 3 Role - Interaction Test");
        party4.setId(7L);
        party4.setName("Party 4 Role - Interaction Test");
        party4.setDescription("Party 4 Role - Interaction Test");

        // Create a Skill.
        skill.setId(8L);
        skill.setName("Skill Name - Interaction Test");
        skill.setDescription("Skill Description - Interaction Test");
        skill.setType(SkillType.BCT);

        // Create an Interaction.
        interaction.setId(9L);
        interaction.setName("Interaction Test");
        interaction.setDescription("Interaction Test");
        interaction.setType(ActivityType.LEARNING);
        interaction.setPreconditions("Preconditions 2 - Interaction Test");
        interaction.setPostconditions("Post-conditions 2 - Interaction Test");
        interaction.setParties(roles);
        interaction.setInteractionMedium1(InteractionMedium.MESSAGING);
        interaction.setInteractionMedium2(InteractionMedium.EMAIL);
        interaction.setInteractionMedium3(InteractionMedium.VIDEO);
        interaction.setInteractionMedium4(InteractionMedium.VOICE);
        interaction.setInteractionMode(InteractionMode.ASYNCHRONOUS);
        interaction.setInteractionInitiatorRole(initiatorRole);

        // Save in the database.
        when(interactionRepository.save(interaction)).thenReturn(interaction);

        // Create a Requires.
        requires.setId(10L);
        requires.setLevel(SkillLevel.ADVANCED);
        requires.setRole(role);
        requires.setSkill(skill);
        requires.setBciActivity(interaction);

        // Creates a Develops.
        develops.setId(11L);
        develops.setLevel(SkillLevel.INTERMEDIATE);
        develops.setRole(role2);
        develops.setSkill(skill);
        develops.setBciActivity(interaction);

        // Create Content.
        content.setId(12L);
        content.setName("Content Name - Interaction Test");
        content.setDescription("Content Description - Interaction Test");
        content.setType("Content Video - Interaction Test");
        content.addBCIActivity(interaction);

        // Update Interaction.
        interaction.addDevelops(develops);
        interaction.addContent(content);
        interaction.addContent(content2);
        interaction.addRequires(requires);
        interaction.addRequires(requires1);

        // Save in the database.
        when(interactionRepository.save(interaction)).thenReturn(interaction);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        // Perform a POST request to test the controller.
        performCreateRequest(URL, interaction);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        Interaction interaction = new Interaction();
        interaction.setId(13L);
        // Perform a POST request to test the controller.
        performCreateRequestBadRequest(URL, interaction);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update a new Interaction
        interaction.setName("Update Interaction");
        // Save in the database.
        when(interactionRepository.save(interaction)).thenReturn(interaction);
        // Mock behavior for findById().
        when(interactionRepository.findById(interaction.getId())).thenReturn(Optional.of(interaction));
        // Perform a PUT request to test the controller.
        performUpdateRequest(URL, interaction,"$.name", interaction.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        // Perform a DELETE request to test the controller.
        performDeleteRequest(URL_SPLITTER + interaction.getId(), interaction);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create an Interaction.
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction)).thenReturn(interaction);
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);
        // Mock behavior for interactionRepository.findById().
        when(interactionRepository.findById(interaction2.getId())).thenReturn(Optional.of(interaction2));
        when(interactionRepository.findById(interaction.getId())).thenReturn(Optional.of(interaction));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + interaction2.getId(), "$.name", interaction2.getName());
        performGetRequest(URL_FIND + interaction.getId(), "$.name", interaction.getName());
    }

    @Test
    void testFindByName() throws Exception {
        // Create an Interaction.
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction)).thenReturn(interaction);
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);
        // Mock behavior for interactionRepository.findByName().
        when(interactionRepository.findByName(interaction.getName())).thenReturn(Collections.singletonList(interaction));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "name/" + interaction.getName(),"$[0].name", interaction.getName());
        // Mock behavior for interactionRepository.findByName().
        when(interactionRepository.findByName(interaction2.getName())).thenReturn(Collections.singletonList(interaction2));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "name/" + interaction2.getName(),"$[0].name", interaction2.getName());
    }

    @Test
    void testFindByType() throws Exception {
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction)).thenReturn(interaction);
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);
        // Mock behavior for interactionRepository.findByType().
        when(interactionRepository.findByType(interaction.getType())).thenReturn(Collections.singletonList(interaction));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "type/" + interaction.getType(),"$[0].type", interaction.getType().toString());
        // Mock behavior for interactionRepository.findByType().
        when(interactionRepository.findByType(interaction2.getType())).thenReturn(Collections.singletonList(interaction2));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "type/" + interaction2.getType(), "$[0].type", interaction2.getType().toString());
    }

    @Test
    void testFindByDevelops() throws Exception {
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);

        Develops develops = interaction2.getDevelops().getFirst();

        // Mock behavior for findByDevelopsBCIActivity_Id.
        when(interactionRepository.findByDevelopsBCIActivity_Id(develops.getId())).thenReturn(Collections.singletonList(interaction2));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "develops/" + develops.getId(), "$[0].name", interaction2.getName());
    }

    @Test
    void testFindByRequires() throws Exception {
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);
        // Mock behavior for findByRequiresBCIActivities_Id.
        when(interactionRepository.findByRequiresBCIActivities_Id(requires.getId())).thenReturn(Collections.singletonList(interaction2));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "requires/" + requires.getId(), "$[0].name", interaction2.getName());
    }

    @Test
    void testFindByContent() throws Exception {
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);

        Content content = interaction2.getContent().getFirst();

        // Mock behavior for findByContentBCIActivities_Id().
        when(interactionRepository.findByContentBCIActivities_Id(content.getId())).thenReturn(Collections.singletonList(interaction2));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "content/" + content.getId(), "$[0].name", interaction2.getName());
    }

    private Interaction dataToPerformTheFindTest() throws Exception {
        List<Role> roles = new ArrayList<>();
        Role role2 = new Role();
        role2.setId(14L);
        role2.setName("e-Facilitator Role");

        roles.add(role);
        roles.add(role2);

        Develops develops = new Develops();
        develops.setId(15L);
        develops.setLevel(SkillLevel.BEGINNER);
        develops.setSkill(skill);
        develops.setRole(role);

        requires.setId(16L);
        requires.setLevel(SkillLevel.BEGINNER);
        requires.setSkill(skill);
        requires.setRole(role);

        Content content = new Content();
        content.setId(17L);
        content.setName("My Content");
        content.setType("My Content type");
        content.setDescription("My Content description");

        // Create an Interaction.
        Interaction interaction1 = new Interaction();
        interaction1.setId(18L);
        interaction1.setName("My Interaction Testing - Interaction Test");
        interaction1.setDescription("My InteractionTesting training - Interaction Test");
        interaction1.setType(ActivityType.LEARNING);
        interaction1.setPreconditions("My InteractionTesting Preconditions 2 - Interaction Test");
        interaction1.setPostconditions("My InteractionTesting Post-conditions 2 - Interaction Test");
        interaction1.setParties(roles);
        interaction1.setInteractionMedium1(InteractionMedium.VIDEO);
        interaction1.setInteractionMedium2(InteractionMedium.EMAIL);
        interaction1.setInteractionMedium3(InteractionMedium.VIDEO);
        interaction1.setInteractionMedium4(InteractionMedium.VOICE);
        interaction1.setInteractionMode(InteractionMode.SYNCHRONOUS);
        interaction1.setInteractionInitiatorRole(initiatorRole);
        interaction1.addDevelops(develops);
        interaction1.addContent(content);
        interaction1.addContent(content2);
        interaction1.addRequires(requires);
        interaction1.addRequires(requires1);

        return interaction1;
    }

    @Test
    void testFindByInteractionInitiatorRoleId() throws Exception {
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);

        // Get Initiator Role.
        Role initiatorRole = interaction2.getInteractionInitiatorRole();

        // Mock behavior for findByInteractionInitiatorRole_Id().
        when(interactionRepository.findByInteractionInitiatorRole_Id(initiatorRole.getId())).thenReturn(Collections.singletonList(interaction2));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "initiatorrole/" + initiatorRole.getId(), "$[0].name", interaction2.getName());
    }


    @Test
    void testFindByPartiesId() throws Exception {
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);

        // Get Parties.
        Role parties = interaction2.getParties().stream().findFirst().get();

        // Mock behavior for findByParties_Id().
        when(interactionRepository.findByParties_Id(parties.getId())).thenReturn(Collections.singletonList(interaction2));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "parties/" + parties.getId(), "$[0].name", interaction2.getName());
    }

    @Test
    void testFindByInteractionMode() throws Exception {
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);
        // Mock behavior for findByInteractionMode().
        when(interactionRepository.findByInteractionMode(InteractionMode.SYNCHRONOUS)).thenReturn(Collections.singletonList(interaction2));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "mode/" + InteractionMode.SYNCHRONOUS, "$[0].name", interaction2.getName());
    }

    @Test
    void testFindByInteractionMedium1() throws Exception {
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);
        // Mock behavior for findByInteractionMedium1().
        when(interactionRepository.findByInteractionMedium1(InteractionMedium.VIDEO)).thenReturn(Collections.singletonList(interaction2));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "interactionmedium1/" + InteractionMedium.VIDEO, "$[0].name", interaction2.getName());
    }

    @Test
    void testFindByInteractionMedium2() throws Exception {
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);
        // Mock behavior for findByInteractionMedium2().
        when(interactionRepository.findByInteractionMedium2(InteractionMedium.EMAIL)).thenReturn(Collections.singletonList(interaction2));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "interactionmedium2/" + InteractionMedium.EMAIL, "$[0].name", interaction2.getName());
    }

    @Test
    void testFindByInteractionMedium3() throws Exception {
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);
        // Mock behavior for findByInteractionMedium3().
        when(interactionRepository.findByInteractionMedium3(InteractionMedium.VIDEO)).thenReturn(Collections.singletonList(interaction2));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "interactionmedium3/" + InteractionMedium.VIDEO, "$[0].name", interaction2.getName());
    }

    @Test
    void testFindByInteractionMedium4() throws Exception {
        Interaction interaction2 = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction2)).thenReturn(interaction2);
        // Mock behavior for findByInteractionMedium4().
        when(interactionRepository.findByInteractionMedium4(InteractionMedium.VOICE)).thenReturn(Collections.singletonList(interaction2));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "interactionmedium4/" + InteractionMedium.VOICE, "$[0].name", interaction2.getName());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Create an Interaction.
        Interaction interaction = dataToPerformTheFindTest();
        // Mock behavior for interactionRepository.save
        when(interactionRepository.save(interaction)).thenReturn(interaction);
        // Mock behavior for interactionRepository.findAll().
        when(interactionRepository.findAll()).thenReturn(Collections.singletonList(interaction));
        // Perform a GET request to test the controller.
        performGetRequest(URL, "$[0].id", interaction.getId());
    }
}
