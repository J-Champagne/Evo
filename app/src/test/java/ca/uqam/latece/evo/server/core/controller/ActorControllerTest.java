package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.ActorController;
import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.repository.instance.ActorRepository;
import ca.uqam.latece.evo.server.core.repository.RoleRepository;
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
 * Tests methods found in ActorController using WebMvcTest, and repository queries using MockMvc (Mockito).
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@WebMvcTest(controllers = ActorController.class)
@ContextConfiguration(classes = {ActorController.class, ActorService.class, Actor.class})
public class ActorControllerTest extends AbstractControllerTest {
    @MockBean
    private ActorRepository actorRepository;

    @MockBean
    private RoleRepository roleRepository;

    private Role role = new Role("Administrator");

    private Actor actor = new Actor("Bernard", "bernard@gmail.com", "222-222-2222", role);

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
        Actor actorUpdated = new Actor("Bernard 2", actor.getEmail(), actor.getContactInformation(), actor.getRole());
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

    @Test
    void findByRole() throws Exception {
        Role role = new Role("Administrator");
        role.setId(1L);
        when(roleRepository.save(role)).thenReturn(role);

        Actor actor2 = new Actor("Bob2", "bb@gmail.com", "444-4444", role);
        when(actorRepository.save(actor2)).thenReturn(actor2);

        when(actorRepository.findByRole(role)).thenReturn(Collections.singletonList(actor2));
        performGetRequest(url + "/find/role", role,"$[0].id", actor2.getId());
    }

    @Test
    void findByRoleId() throws Exception {
        Role role = new Role("Administrator");
        role.setId(1L);
        when(roleRepository.save(role)).thenReturn(role);

        Actor actor2 = new Actor("Bob2", "bb@gmail.com", "444-4444", role);
        actor2.setId(2L);
        when(actorRepository.save(actor2)).thenReturn(actor2);

        when(actorRepository.findByRoleId(role.getId())).thenReturn(Collections.singletonList(actor2));
        performGetRequest(url + "/find/role/" + role.getId(), "$[0].id", actor2.getId());
    }
}
