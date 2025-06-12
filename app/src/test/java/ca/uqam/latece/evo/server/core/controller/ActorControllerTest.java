package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.ActorController;
import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.repository.instance.ActorRepository;
import ca.uqam.latece.evo.server.core.service.instance.ActorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

/**
 * The Actor Controller test class for the {@link ActorController}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations supported the controller class, using WebMvcTes, and
 * repository queries using MockMvc (Mockito).
 *
 * @version 1.0
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@WebMvcTest(controllers = ActorController.class)
@ContextConfiguration(classes = {ActorController.class, ActorService.class, Actor.class})
public class ActorControllerTest extends AbstractControllerTest {
    @MockBean
    private ActorRepository actorRepository;

    private Actor actor = new Actor("Bernard", "bernard@gmail.com", "222-222-2222");

    private final String url = "/actor";

    @BeforeEach
    @Override
    void setUp() {
        actor.setId(1L);
        when(actorRepository.save(actor)).thenReturn(actor);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(url, actor);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        Actor actorUpdated = new Actor("Bernard 2", actor.getEmail(), actor.getContactInformation());
        actorUpdated.setId(actor.getId());
        when(actorRepository.save(actorUpdated)).thenReturn(actorUpdated);
        when(actorRepository.findById(actorUpdated.getId())).thenReturn(Optional.of(actorUpdated));

        performUpdateRequest(url, actorUpdated,"$.name",actorUpdated.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(url + "/" + actor.getId(), actor);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(actorRepository.findAll()).thenReturn(Collections.singletonList(actor));

        performGetRequest(url,"$[0].id", actor.getId());
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(actorRepository.findById(actor.getId())).thenReturn(Optional.of(actor));
        performGetRequest(url + "/find/" + actor.getId(),
                "$.name", actor.getName());
    }

    @Test
    void testFindByName() throws Exception {
        when(actorRepository.findByName(actor.getName())).thenReturn(Collections.singletonList(actor));
        performGetRequest(url + "/find/name/" + actor.getName(),
                "$[0].name", actor.getName());
    }

    @Test
    void findByEmail() throws Exception {
        when(actorRepository.findByEmail(actor.getEmail())).thenReturn(actor);
        performGetRequest(url + "/find/email/" + actor.getEmail(),
                "$.email", actor.getEmail());
    }

    @Test
    void findByContactInformation() throws Exception {
        when(actorRepository.findByContactInformation(actor.getContactInformation())).thenReturn(Collections.singletonList(actor));
        performGetRequest(url + "/find/contactinformation/" + actor.getContactInformation(),
                "$[0].contactInformation", actor.getContactInformation());
    }
}
