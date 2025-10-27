package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.service.instance.ActorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link ActorService}, responsible for testing its various functionalities. This class includes
 * integration tests for CRUD operations and other repository queries using a PostgreSQL database in a containerized setup.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@ContextConfiguration(classes = {Actor.class, ActorService.class})
public class ActorServiceTest extends AbstractServiceTest {
    @Autowired
    private ActorService actorService;

    private Actor actorSaved;

    @BeforeEach
    void setup() {
        actorSaved = actorService.create(new Actor("Bob", "bob@gmail.com", "222-2222"));
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

        assertFalse(actorService.existsById(actorSaved.getId()));
    }

    @Test
    @Override
    public void testFindAll() {
        actorService.create(new Actor("Bob2", "bob2@gmail.com", "222-2222"));
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
        assertEquals(actorSaved.getId(), result.getFirst().getId());
        assertEquals(actorSaved.getName(), result.getFirst().getName());
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
        assertEquals(actorSaved.getId(), result.getFirst().getId());
        assertEquals(actorSaved.getContactInformation(), result.getFirst().getContactInformation());
    }
}
