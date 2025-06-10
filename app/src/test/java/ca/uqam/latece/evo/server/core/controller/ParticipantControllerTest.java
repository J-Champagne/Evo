package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.ParticipantController;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.repository.RoleRepository;
import ca.uqam.latece.evo.server.core.repository.instance.HealthCareProfessionalRepository;
import ca.uqam.latece.evo.server.core.repository.instance.ParticipantRepository;
import ca.uqam.latece.evo.server.core.service.instance.ParticipantService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Tests methods found in ParticipantController using WebMvcTest, and repository queries using MockMvc (Mockito).
 * @author Julien Champagne.
 */
@WebMvcTest(controllers = ParticipantController.class)
@ContextConfiguration(classes = {Participant.class, ParticipantService.class, ParticipantController.class})
public class ParticipantControllerTest extends AbstractControllerTest {
    @MockBean
    private ParticipantRepository participantRepository;

    @MockBean
    private HealthCareProfessionalRepository healthCareProfessionalRepository;

    @MockBean
    private RoleRepository roleRepository;

    private Role role = new Role("Administrator");

    private HealthCareProfessional hcp = new HealthCareProfessional("Bob", "bob@gmail.com", "222-2222", role,
            "Student", "New-York", "Health");

    private Participant participant = new Participant(role, hcp);

    private static final String url = "/participant";

    @BeforeEach
    @Override
    void setUp() {
        role.setId(1L);
        hcp.setId(1L);
        participant.setId(1L);
        when(roleRepository.save(role)).thenReturn(role);
        when(healthCareProfessionalRepository.save(hcp)).thenReturn(hcp);
        when(participantRepository.save(participant)).thenReturn(participant);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(url, participant);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        HealthCareProfessional hcpUpdated = new HealthCareProfessional("Bob2", "bob2@gmail.com", "222-2222",
                role, "Student", "New-York", "Health");
        Participant updated = new Participant(role, hcpUpdated);
        hcpUpdated.setId(2L);
        updated.setId(1L);

        when(healthCareProfessionalRepository.save(hcpUpdated)).thenReturn(hcpUpdated);
        when(participantRepository.save(updated)).thenReturn(updated);
        when(participantRepository.findById(updated.getId())).thenReturn(Optional.of(updated));

        performUpdateRequest(url, updated, "$.actor.name", updated.getActor().getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(url + "/" + participant.getId(), participant);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(participantRepository.findAll()).thenReturn(Collections.singletonList(participant));
        performGetRequest(url, "$[0].id", 1);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(participantRepository.findById(participant.getId())).thenReturn(Optional.of(participant));
        performGetRequest(url + "/find/" + participant.getId(), "$.id", participant.getId());
    }

    @Test
    void testFindByRoleId() throws Exception {
        when(participantRepository.findByRoleId(participant.getRole().getId())).thenReturn(Collections.singletonList(participant));
        performGetRequest(url + "/find/role/" + participant.getRole().getId(), "$[0].role.id",
                participant.getRole().getId());
    }

    @Test
    void testFindByActorId() throws Exception {
        when(participantRepository.findByActorId(participant.getActor().getId())).thenReturn(Collections.singletonList(participant));
        performGetRequest(url + "/find/actor/" + participant.getActor().getId(), "$[0].actor.id",
                participant.getActor().getId());
    }
}
