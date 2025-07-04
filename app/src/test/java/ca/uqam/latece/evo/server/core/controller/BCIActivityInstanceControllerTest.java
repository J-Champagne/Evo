package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.BCIActivityInstanceController;
import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.repository.instance.BCIActivityInstanceRepository;
import ca.uqam.latece.evo.server.core.service.instance.BCIActivityInstanceService;
import ca.uqam.latece.evo.server.core.util.DateFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The BCIActivityInstance Controller test class for the {@link BCIActivityInstanceController}, responsible for testing
 * its various functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 *
 * @version 1.0
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@WebMvcTest(controllers = BCIActivityInstanceController.class)
@ContextConfiguration(classes = {BCIActivityInstanceController.class, BCIActivityInstanceService.class, BCIActivityInstance.class})
public class BCIActivityInstanceControllerTest extends AbstractControllerTest {
    @MockBean
    private BCIActivityInstanceRepository bciActivityInstanceRepository;

    private BCIActivityInstance bciActivityInstance = new BCIActivityInstance();
    private BCIActivity bciActivity  = new BCIActivity();
    private Role role = new Role();
    private HealthCareProfessional hcp = new HealthCareProfessional();
    private Participant participant = new Participant();

    private LocalDate localEntryDate = DateFormatter.convertDateStrTo_yyyy_MM_dd("2020/01/08");
    private LocalDate localExitDate = LocalDate.now();

    private static final String URL = "/bciactivityinstance";
    private static final String URL_SPLITTER = "/bciactivityinstance/";
    private static final String URL_FIND = "/bciactivityinstance/find/";

    @BeforeEach
    @Override
    void setUp() {
        // Create a Role.
        role.setId(1L);
        role.setName("Admin - BCIActivity Test");

        // Create an Actor
        hcp.setId(2L);
        hcp.setName("Bob");
        hcp.setEmail("bob@gmail.com");
        hcp.setContactInformation("222-2222");
        hcp.setAffiliation("CIUSSS");
        hcp.setPosition("Chief");
        hcp.setSpecialties("None");

        // Create a participant
        participant.setId(3L);
        participant.setRole(role);
        participant.setActor(hcp);

        // Create a BCI Activity.
        bciActivity.setId(4L);
        bciActivity.setName("Programming 2 - BCIActivity Test");
        bciActivity.setDescription("Programming language training 2 - BCIActivity Test");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.setPreconditions("Preconditions 2 - BCIActivity Test");
        bciActivity.setPostconditions("Post-conditions 2 - BCIActivity Test");
        bciActivity.addParty(role);

        // Create BCIActivityInstance.
        bciActivityInstance.setId(5L);
        bciActivityInstance.setStatus("BCIActivity Instance Java");
        bciActivityInstance.setEntryDate(localEntryDate);
        bciActivityInstance.setExitDate(localExitDate);
        bciActivityInstance.setBciActivity(bciActivity);
        bciActivityInstance.addParticipant(participant);

        // Save in the database.
        when(bciActivityInstanceRepository.save(bciActivityInstance)).thenReturn(bciActivityInstance);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        // Perform a Create request to test the controller.
        performCreateRequest(URL, bciActivityInstance);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        BCIActivityInstance instance  = new BCIActivityInstance();
        instance.setId(23L);

        // Perform a Create request to test the controller.
        performCreateRequestBadRequest(URL, instance);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update a BCIActivityInstance.
        bciActivityInstance.setStatus("BCIActivity Instance 13");
        // Save in the database.
        when(bciActivityInstanceRepository.save(bciActivityInstance)).thenReturn(bciActivityInstance);
        // Perform a PUT request to test the controller.
        performUpdateRequest(URL, bciActivityInstance, "$.status", bciActivityInstance.getStatus());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        // Perform a Delete request to test the controller.
        performDeleteRequest(URL_SPLITTER + bciActivityInstance.getId(), bciActivityInstance);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Mock behavior for bciActivityInstanceRepository.save
        when(bciActivityInstanceRepository.save(bciActivityInstance)).thenReturn(bciActivityInstance);
        // Mock behavior for bciActivityInstanceRepository.findById().
        when(bciActivityInstanceRepository.findById(bciActivityInstance.getId())).thenReturn(Optional.of(bciActivityInstance));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + bciActivityInstance.getId(), "$.status", bciActivityInstance.getStatus());
    }

    @Test
    void testFindByStatus() throws Exception {
        // Mock behavior for bciActivityInstanceRepository.save
        when(bciActivityInstanceRepository.save(bciActivityInstance)).thenReturn(bciActivityInstance);
        // Mock behavior for bciActivityInstanceRepository.findById().
        when(bciActivityInstanceRepository.findByStatus(bciActivityInstance.getStatus())).thenReturn(Collections.singletonList(bciActivityInstance));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "status/" + bciActivityInstance.getStatus(), "$[0].status", bciActivityInstance.getStatus());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for bciActivityInstanceRepository.save
        when(bciActivityInstanceRepository.save(bciActivityInstance)).thenReturn(bciActivityInstance);
        // Mock behavior for bciActivityInstanceRepository.findAll().
        when(bciActivityInstanceRepository.findAll()).thenReturn(Collections.singletonList(bciActivityInstance));
        // Perform a GET request to test the controller.
        performGetRequest(URL, "$[0].id", bciActivityInstance.getId());
    }

    @Test
    void testFindParticipantsId() throws Exception {
        // Mock behavior for bciActivityInstanceRepository.save
        when(bciActivityInstanceRepository.save(bciActivityInstance)).thenReturn(bciActivityInstance);
        // Mock behavior for bciActivityInstanceRepository.findAll().
        when(bciActivityInstanceRepository.findByParticipantsId(participant.getId())).thenReturn((bciActivityInstance));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "participants/" + participant.getId(), "$.participants.[0].id",
                participant.getId());
    }
}
