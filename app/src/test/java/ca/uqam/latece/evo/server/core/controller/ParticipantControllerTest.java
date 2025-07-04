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
 * The Participant Controller test class for the {@link ParticipantController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class, using
 * WebMvcTes, and repository queries using MockMvc (Mockito).
 *
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
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

    private HealthCareProfessional hcp = new HealthCareProfessional("Bob", "bob@gmail.com", "222-2222",
            "Student", "New-York", "Health");

    private Participant participant = new Participant(role, hcp);

    private static final String URL = "/participant";

    @BeforeEach
    @Override
    void setUp() {
        role.setId(1L);
        hcp.setId(2L);
        participant.setId(3L);
        when(roleRepository.save(role)).thenReturn(role);
        when(healthCareProfessionalRepository.save(hcp)).thenReturn(hcp);
        when(participantRepository.save(participant)).thenReturn(participant);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(URL, participant);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        HealthCareProfessional hcpUpdated = new HealthCareProfessional("Bob2", "bob2@gmail.com", "222-2222",
                "Student", "New-York", "Health");
        Participant updated = new Participant(role, hcpUpdated);
        hcpUpdated.setId(hcp.getId());
        updated.setId(participant.getId());

        when(healthCareProfessionalRepository.save(hcpUpdated)).thenReturn(hcpUpdated);
        when(participantRepository.save(updated)).thenReturn(updated);
        when(participantRepository.findById(updated.getId())).thenReturn(Optional.of(updated));

        performUpdateRequest(URL, updated, "$.actor.name", updated.getActor().getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(URL + "/" + participant.getId(), participant);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(participantRepository.findAll()).thenReturn(Collections.singletonList(participant));
        performGetRequest(URL, "$[0].id", participant.getId());
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(participantRepository.findById(participant.getId())).thenReturn(Optional.of(participant));
        performGetRequest(URL + "/find/" + participant.getId(), "$.id", participant.getId());
    }

    @Test
    void testFindByRoleId() throws Exception {
        when(participantRepository.findByRoleId(participant.getRole().getId())).thenReturn(Collections.singletonList(participant));
        performGetRequest(URL + "/find/role/" + participant.getRole().getId(), "$[0].role.id",
                participant.getRole().getId());
    }

    @Test
    void testFindByActorId() throws Exception {
        when(participantRepository.findByActorId(participant.getActor().getId())).thenReturn(Collections.singletonList(participant));
        performGetRequest(URL + "/find/actor/" + participant.getActor().getId(), "$[0].actor.id",
                participant.getActor().getId());
    }
}
