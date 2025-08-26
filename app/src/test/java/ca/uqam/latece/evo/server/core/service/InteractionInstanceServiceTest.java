package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMedium;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMode;
import ca.uqam.latece.evo.server.core.model.Interaction;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.InteractionInstance;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;
import ca.uqam.latece.evo.server.core.service.instance.InteractionInstanceService;
import ca.uqam.latece.evo.server.core.service.instance.ParticipantService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests methods found in InteractionInstanceService in a containerized setup.
 * @author Julien Champagne.
 */
@ContextConfiguration(classes = {InteractionInstance.class, InteractionInstanceService.class})
public class InteractionInstanceServiceTest extends AbstractServiceTest {
    @Autowired
    RoleService roleService;

    @Autowired
    HealthCareProfessionalService healthCareProfessionalService;

    @Autowired
    ParticipantService participantService;

    @Autowired
    InteractionInstanceService interactionInstanceService;

    @Autowired
    InteractionService interactionService;

    Role role;

    HealthCareProfessional hcp;

    Participant participant;

    InteractionInstance interactionInstance;
    
    @BeforeEach
    public void setUp() {
        role = roleService.create(new Role("Admin"));
        hcp = healthCareProfessionalService.create(new HealthCareProfessional("Bob", "Bobross@gmail.com",
                "514-222-2222", "Chief Painter", "CIUSSS","Healthcare"));

        participant = participantService.create(new Participant(role, hcp));
        List<Participant> participants = new ArrayList<>();
        participants.add(participant);

        Interaction interaction = interactionService.create(new Interaction("Interaction", "Description",
                ActivityType.BCI_ACTIVITY, "precondition", "postcondition", InteractionMode.ASYNCHRONOUS,
                role, InteractionMedium.VIDEO));

        interactionInstance = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.READY, participants,
                interaction));
    }

    @Test
    @Override
    public void testSave() {
        assert interactionInstance.getId() > 0;
    }

    @Test
    @Override
    public void testUpdate() {
        interactionInstance.setStatus(ExecutionStatus.FINISHED);
        InteractionInstance updated = interactionInstanceService.update(interactionInstance);
        assertEquals(ExecutionStatus.FINISHED, updated.getStatus());
    }

    @Test
    @Override
    void testFindById() {
        InteractionInstance found = interactionInstanceService.findById(interactionInstance.getId());
        assertEquals(interactionInstance.getId(), found.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        interactionInstanceService.deleteById(interactionInstance.getId());
        assertThrows(EntityNotFoundException.class, () -> interactionInstanceService.
                findById(interactionInstance.getId()));
    }

    @Test
    @Override
    void testFindAll() {
        InteractionInstance interactionInstance2 = interactionInstanceService.create(new InteractionInstance(ExecutionStatus.FINISHED,
                interactionInstance.getParticipants(), interactionInstance.getInteraction()));
        List<InteractionInstance> results = interactionInstanceService.findAll();

        assertEquals(2, results.size());
    }

    @Test
    void testFindByStatus() {
        List<InteractionInstance> results = interactionInstanceService.findByStatus(interactionInstance.getStatus());
        assertEquals(1, results.size());
    }

    @Test
    void testFindByEntryDate() {
        List<InteractionInstance> results = interactionInstanceService.findByEntryDate(interactionInstance.getEntryDate());
        assertEquals(1, results.size());
    }

    @Test
    void testFindByExitDate() {
        interactionInstance.setExitDate(LocalDate.now());
        List<InteractionInstance> results = interactionInstanceService.findByExitDate(interactionInstance.getExitDate());
        assertEquals(1, results.size());
    }

    @Test
    void testFindByParticipantsId() {
        List<InteractionInstance> bciActivities = interactionInstanceService.findByParticipantsId(participant.getId());

        assertEquals(1, bciActivities.getFirst().getParticipants().size());
        assertEquals(participant.getId(), bciActivities.getFirst().getParticipants().getFirst().getId());
    }

    @Test
    void testAddParticipantMoreThan3Fail() {
        Participant participant2 = new Participant(role, hcp);
        Participant participant3 = new Participant(role, hcp);
        Participant participant4 = new Participant(role, hcp);

        interactionInstance.addParticipant(participant2);
        interactionInstance.addParticipant(participant3);
        assertThrows(IndexOutOfBoundsException.class, () -> interactionInstance.addParticipant(participant4));
    }
}
