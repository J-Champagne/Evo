package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.service.instance.ActorService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests methods found in ActorService in a containerized setup.
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@ContextConfiguration(classes = {Actor.class, ActorService.class, RoleService.class})
public class ActorServiceTest extends AbstractServiceTest {
    @Autowired
    private ActorService actorService;

    @Autowired
    private RoleService roleService;

    private Role roleSaved;

    private Actor actorSaved;

    @BeforeEach
    void setup() {
        roleSaved = roleService.create(new Role("Administrator"));
        actorSaved = actorService.create(new Actor("Bob", "bob@gmail.com", "222-2222", roleSaved));
    }

    @Test
    @Override
    public void testSave(){
        assert actorSaved.getId() > 0;
    }

    @Test
    @Override
    public void testUpdate(){
        actorSaved.setContactInformation("111-1111");
        Actor actorUpdated = actorService.update(actorSaved);

        assertEquals(actorSaved.getId(), actorUpdated.getId());
        assertEquals("111-1111", actorUpdated.getContactInformation());
    }

    @Test
    @Override
    public void testDeleteById() {
        actorService.deleteById(actorSaved.getId());

        assertThrows(EntityNotFoundException.class, () -> actorService.
                findById(actorSaved.getId()));
    }

    @Test
    @Override
    public void testFindAll() {
        actorService.create(new Actor("Bob2", "bob2@gmail.com", "222-2222", roleSaved));
        List<Actor> result = actorService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    @Override
    public void testFindById() {
        Actor actorFound = actorService.findById(actorSaved.getId());

        assertEquals(actorSaved.getId(), actorFound.getId());
    }

    @Test
    public void testFindByName() {
        List<Actor> result = actorService.findByName(actorSaved.getName());

        assertFalse(result.isEmpty());
        assertEquals(actorSaved.getId(), result.get(0).getId());
        assertEquals(actorSaved.getName(), result.get(0).getName());
    }

    @Test
    public void testFindByEmail() {
        Actor actorFound = actorService.findByEmail(actorSaved.getEmail());

        assertEquals(actorSaved.getId(), actorFound.getId());
        assertEquals(actorSaved.getEmail(), actorFound.getEmail());
    }

    @Test
    public void testFindByContactInformation() {
        List<Actor> result = actorService.findByContactInformation(actorSaved.getContactInformation());

        assertFalse(result.isEmpty());
        assertEquals(actorSaved.getId(), result.get(0).getId());
        assertEquals(actorSaved.getContactInformation(), result.get(0).getContactInformation());
    }

    @Test
    public void testFindByRole() {
        List<Actor> result = actorService.findByRole(roleSaved);

        assertFalse(result.isEmpty());
        assertEquals(actorSaved.getId(), result.get(0).getId());
        assertEquals(actorSaved.getRole().getId(), result.get(0).getRole().getId());
    }

    @Test
    public void testFindByRoleId() {
        List<Actor> result = actorService.findByRoleId(roleSaved.getId());

        assertFalse(result.isEmpty());
        assertEquals(actorSaved.getId(), result.get(0).getId());
        assertEquals(actorSaved.getRole().getId(), result.get(0).getRole().getId());
    }
}
