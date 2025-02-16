package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.Actor;
import ca.uqam.latece.evo.server.core.repository.ActorRepository;
import ca.uqam.latece.evo.server.core.service.ActorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;


/**
 * Actor Controller Test class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = ActorController.class)
@ContextConfiguration(classes = {ActorController.class, ActorService.class, Actor.class})
public class ActorControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorRepository actorRepository;

    private Actor actor = new Actor();

    @BeforeEach
    void setUp() {
        actor.setId(1L);
        actor.setName("Bernard");
        actor.setEmail("bernard@gmail.com");

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
        // Create the Actor.
        actor.setId(2L);
        actor.setName("Bernard 2");
        actor.setEmail("bernard2@gmail.com");

        // Save in the database.
        when(actorRepository.save(actor)).thenReturn(actor);
        // Perform a PUT request to test the controller.
        performUpdateRequest("/actors", actor,
                "$.name",actor.getName());
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

        // Mock behavior for actorRepository.findByName().
        when(actorRepository.findByEmail(actor.getEmail())).thenReturn(Collections.singletonList(actor));
        // Perform a GET request to test the controller.
        performGetRequest("/actors/find/email/" + actor.getEmail(),
                "$[0].email", actor.getEmail());
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
