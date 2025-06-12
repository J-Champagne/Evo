package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;
import ca.uqam.latece.evo.server.core.service.instance.ParticipantService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link ParticipantService}, responsible for testing its various functionalities. This
 * class includes integration tests for CRUD operations and other repository queries using a PostgreSQL database in a
 * containerized setup.
 *
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {Participant.class, ParticipantService.class})
public class ParticipantServiceTest extends AbstractServiceTest {
    @Autowired
    ParticipantService participantService;

    @Autowired
    RoleService roleService;

    @Autowired
    HealthCareProfessionalService healthcareProfessionalService;

    private Role roleSaved;

    private HealthCareProfessional hcpSaved;

    private Participant participantSaved;

    @BeforeEach
    void setUp() {
        roleSaved = roleService.create(new Role("Administrator"));
        hcpSaved = healthcareProfessionalService.create(new HealthCareProfessional("Bob", "bob@gmail.com",
                "222-2222", "Student","CIUSSS", "Health"));
        participantSaved = participantService.create(new Participant(roleSaved, hcpSaved));
    }

    @Test
    @Override
    void testSave() {
        assert participantSaved.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        participantSaved.getActor().setContactInformation("444-4444");
        Participant paUpdated = participantService.update(participantSaved);

        assertEquals(participantSaved.getId(), paUpdated.getId());
        assertEquals(participantSaved.getActor().getContactInformation(), paUpdated.getActor().getContactInformation());
    }

    @Test
    @Override
    void testFindById() {
        Participant paFound = participantService.findById(participantSaved.getId());
        assertEquals(participantSaved.getId(), paFound.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        participantService.deleteById(participantSaved.getId());
        assertThrows(EntityNotFoundException.class, () -> participantService.
                findById(participantSaved.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        participantService.create(new Participant(roleSaved, hcpSaved));
        List<Participant> results = participantService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void testFindByRoleId() {
        List<Participant> results = participantService.findByRoleId(participantSaved.getRole().getId());

        assertFalse(results.isEmpty());
        assertEquals(participantSaved.getId(), results.getFirst().getId());
    }

    @Test
    void testFindByActorId() {
        List<Participant> results = participantService.findByActorId(participantSaved.getActor().getId());

        assertFalse(results.isEmpty());
        assertEquals(participantSaved.getId(), results.getFirst().getId());
    }
}
