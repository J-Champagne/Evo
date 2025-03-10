package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.Actor;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.repository.ActorRepository;
import ca.uqam.latece.evo.server.core.repository.RoleRepository;
import ca.uqam.latece.evo.server.core.service.ActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;


/**
 * The Actor Controller test class for the {@link ActorController}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations supported the controller class, using WebMvcTes, and
 * repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = ActorController.class)
@ContextConfiguration(classes = {ActorController.class, ActorService.class, Actor.class})
public class ActorControllerTest extends AbstractControllerTest {

    @MockBean
    private ActorRepository actorRepository;
    @MockBean
    private RoleRepository roleRepository;

    private Actor actor = new Actor();

    @BeforeEach
    void setUp() {
        actor.setId(1L);
        actor.setName("Bernard");
        actor.setEmail("bernard@gmail.com");

        // Mock behavior for actorRepository.save().
        when(actorRepository.save(actor)).thenReturn(actor);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest("/actors", actor);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        System.out.println(actor);
        // Create the Actor.
        Actor actorToUpdate = new Actor();
        actorToUpdate.setId(actor.getId());
        actorToUpdate.setName("Bernard 2");
        actorToUpdate.setEmail("bernard@gmail.com");

        // Mock behavior for actorRepository.save().
        when(actorRepository.save(actorToUpdate)).thenReturn(actorToUpdate);

        // Mock behavior for actorRepository.findById().
        when(actorRepository.findById(actorToUpdate.getId())).thenReturn(Optional.of(actorToUpdate));

        // Perform a PUT request to test the controller.
        performUpdateRequest("/actors", actorToUpdate,"$.name",actorToUpdate.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/actors/" + actor.getId(), actor);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create the Actor.
        actor.setId(3L);
        actor.setName("Bernard 2");
        actor.setEmail("bernard2@gmail.com");

        // Save the actor.
        when(actorRepository.save(actor)).thenReturn(actor);

        // Mock behavior for actorRepository.findById().
        when(actorRepository.findById(actor.getId())).thenReturn(Optional.of(actor));

        // Perform a GET request to test the controller.
        performGetRequest("/actors/find/" + actor.getId(),
                "$.name", actor.getName());
    }

    @Test
    void testFindByName() throws Exception {
        // Create the Actor.
        actor.setId(4L);
        actor.setName("Bernardo");
        actor.setEmail("bernardo@gmail.com");

        // Save the actor.
        when(actorRepository.save(actor)).thenReturn(actor);

        // Mock behavior for actorRepository.findByName().
        when(actorRepository.findByName(actor.getName())).thenReturn(Collections.singletonList(actor));
        // Perform a GET request to test the controller.
        performGetRequest("/actors/find/name/" + actor.getName(),
                "$[0].name", actor.getName());
    }

    @Test
    void findByEmail() throws Exception {
        // Create the Actor.
        actor.setId(4L);
        actor.setName("Bernardo");
        actor.setEmail("bernardo@gmail.com");

        // Save the actor.
        when(actorRepository.save(actor)).thenReturn(actor);

        // Mock behavior for actorRepository.findByEmail().
        when(actorRepository.findByEmail(actor.getEmail())).thenReturn(Collections.singletonList(actor));
        // Perform a GET request to test the controller.
        performGetRequest("/actors/find/email/" + actor.getEmail(),
                "$[0].email", actor.getEmail());
    }

    @Test
    void findByRole() throws Exception {
        // Create a Role that will be used to update the Actor.
        Role actorRole = new Role("e-Facilitator");
        actorRole.setId(1L);
        // Save the actor.
        when(roleRepository.save(actorRole)).thenReturn(actorRole);

        // Create the Actor.
        actor.setId(4L);
        actor.setName("Bernardo");
        actor.setEmail("bernardo@gmail.com");
        actor.setRole(actorRole);

        // Save the actor.
        when(actorRepository.save(actor)).thenReturn(actor);

        // Mock behavior for actorRepository.findByRole().
        when(actorRepository.findByRole(actor.getRole().getId())).thenReturn(Collections.singletonList(actor));
        // Perform a GET request to test the controller.
        performGetRequest("/actors/find/role/" + actor.getRole().getId(),
                "$[0].role.id", actor.getRole().getId());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for actorRepository.findAll().
        when(actorRepository.findAll()).thenReturn(Collections.singletonList(actor));

        // Perform a GET request to test the controller.
        performGetRequest("/actors","$[0].id",1);
    }
}
