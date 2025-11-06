package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.InteractionInstanceController;
import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMedium;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMode;
import ca.uqam.latece.evo.server.core.model.Interaction;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.InteractionInstance;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.repository.InteractionRepository;
import ca.uqam.latece.evo.server.core.repository.instance.InteractionInstanceRepository;
import ca.uqam.latece.evo.server.core.repository.instance.ParticipantRepository;
import ca.uqam.latece.evo.server.core.service.instance.InteractionInstanceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * Tests methods found in InteractionInstanceController using WebMvcTest, and repository queries using MockMvc (Mockito).
 * @author Julien Champagne.
 */
@WebMvcTest(controllers = InteractionInstanceController.class)
@ContextConfiguration(classes = {InteractionInstance.class, InteractionInstanceService.class,
        InteractionInstanceController.class})
public class InteractionInstanceControllerTest extends AbstractControllerTest {
    @MockitoBean
    private ParticipantRepository participantRepository;

    @MockitoBean
    private InteractionRepository interactionRepository;

    @MockitoBean
    private InteractionInstanceRepository interactionInstanceRepository;

    private Role role = new Role("Administrator");

    private Patient patient = new Patient("Bob", "bob@gmail.com",
            "222-2222", "1 January 1970", "Student", "1234 Street");

    private Participant participant = new Participant(role, patient);

    private List<Participant> participants = List.of(participant);

    private Interaction interaction = new Interaction("Interaction with system", "Description", ActivityType.BCI_ACTIVITY,
            "ENTRY_CONDITION", "EXIT_CONDITION", InteractionMode.ASYNCHRONOUS, role, InteractionMedium.VIDEO);

    private InteractionInstance interactionInstance = new InteractionInstance(ExecutionStatus.READY, participants, interaction);

    private static final String url = "/interactioninstance";

    @BeforeEach
    public void setUp() {
        interaction.setId(3L);
        participant.setId(2L);
        interactionInstance.setId(1L);

        when(interactionRepository.save(interaction)).thenReturn(interaction);
        when(participantRepository.save(participant)).thenReturn(participant);
        when(interactionInstanceRepository.save(interactionInstance)).thenReturn(interactionInstance);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(url, interactionInstance);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        InteractionInstance updated = new InteractionInstance(ExecutionStatus.FINISHED, interactionInstance.getEntryDate(),
                interactionInstance.getExitDate(), interactionInstance.getInteraction());
        updated.setId(interactionInstance.getId());

        when(interactionInstanceRepository.save(updated)).thenReturn(updated);
        when(interactionInstanceRepository.findById(updated.getId())).thenReturn(Optional.of(updated));
        performUpdateRequest(url, updated, "$.status", updated.getStatus().toString());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(url + "/" + interactionInstance.getId(), interactionInstance);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(interactionInstanceRepository.findById(interactionInstance.getId())).thenReturn(Optional.ofNullable(interactionInstance));
        performGetRequest(url + "/find/" + interactionInstance.getId(), "$.id", interactionInstance.getId());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(interactionInstanceRepository.findAll()).thenReturn(Collections.singletonList(interactionInstance));
        performGetRequest(url, "$[0].id", interactionInstance.getId());
    }

    @Test
    void testFindByStatus() throws Exception {
        when(interactionInstanceRepository.findByStatus(interactionInstance.getStatus())).thenReturn(Collections.singletonList(interactionInstance));
        performGetRequest(url + "/find/status/" + interactionInstance.getStatus(), "$[0].id", interactionInstance.getId());
    }

    @Test
    void testFindByEntryDate() throws Exception {
        when(interactionInstanceRepository.findByEntryDate(interactionInstance.getEntryDate())).thenReturn(Collections.singletonList(interactionInstance));
        performGetRequest(url + "/find/entrydate/" + interactionInstance.getEntryDate(), "$[0].id", interactionInstance.getId());
    }

    @Test
    void testFindByExitDate() throws Exception {
        interactionInstance.setExitDate(LocalDate.now());
        when(interactionInstanceRepository.save(interactionInstance)).thenReturn(interactionInstance);
        when(interactionInstanceRepository.findByExitDate(interactionInstance.getExitDate())).thenReturn(Collections.singletonList(interactionInstance));

        performGetRequest(url + "/find/exitdate/" + interactionInstance.getExitDate(), "$[0].id", interactionInstance.getId());
    }

    @Test
    void testFindByParticipantsId() throws Exception {
        when(interactionInstanceRepository.findByParticipantsId(interactionInstance.getParticipants().getFirst().getId()))
                .thenReturn(Collections.singletonList(interactionInstance));
        performGetRequest(url + "/find/participants/" + interactionInstance.getParticipants().getFirst().getId(),
                "$[0].id", interactionInstance.getId());
    }
}
