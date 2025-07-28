package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.BCIModuleInstanceController;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BCIModuleInstance;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.repository.instance.BCIActivityInstanceRepository;
import ca.uqam.latece.evo.server.core.repository.instance.BCIModuleInstanceRepository;
import ca.uqam.latece.evo.server.core.service.instance.BCIModuleInstanceService;
import ca.uqam.latece.evo.server.core.util.DateFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The BCIModuleInstance Controller test class for the {@link BCIModuleInstanceController}, responsible for testing its
 * various functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 *
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = BCIModuleInstanceController.class)
@ContextConfiguration(classes = {BCIModuleInstance.class, BCIModuleInstanceService.class, BCIModuleInstanceController.class})
public class BCIModuleInstanceControllerTest extends AbstractControllerTest {
    @MockBean
    private BCIModuleInstanceRepository bciModuleInstanceRepository;

    @MockBean
    private BCIActivityInstanceRepository bciActivityInstanceRepo;

    private Role role = new Role("Administrator");

    private Participant participant = new Participant(role, new HealthCareProfessional("Bob", "bob@gmail.com",
            "222-2222", "Student", "New-York", "Health"));

    private List<Participant> participants = List.of(participant);

    private BCIActivityInstance activityInstance = new BCIActivityInstance(ExecutionStatus.IN_PROGRESS, LocalDate.now(),
            DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants);

    private List<BCIActivityInstance> activities = List.of(activityInstance);

    private BCIModuleInstance moduleInstance = new BCIModuleInstance(ExecutionStatus.STALLED, OutcomeType.SUCCESSFUL, activities);

    private static final String url = "/bcimoduleinstance";

    @BeforeEach
    @Override
    public void setUp() {
        activityInstance.setId(1L);
        moduleInstance.setId(2L);
        when(bciActivityInstanceRepo.save(activityInstance)).thenReturn(activityInstance);
        when(bciModuleInstanceRepository.save(moduleInstance)).thenReturn(moduleInstance);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(url, moduleInstance);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        BCIModuleInstance updated = new BCIModuleInstance(ExecutionStatus.STALLED, OutcomeType.UNSUCCESSFUL, moduleInstance.getActivities());
        updated.setId(moduleInstance.getId());

        when(bciModuleInstanceRepository.save(updated)).thenReturn(updated);
        when(bciModuleInstanceRepository.findById(updated.getId())).thenReturn(Optional.of(updated));
        performUpdateRequest(url, updated, "$.outcome", updated.getOutcome().toString());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(url + "/" + moduleInstance.getId(), moduleInstance);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(bciModuleInstanceRepository.findAll()).thenReturn(Collections.singletonList(moduleInstance));
        performGetRequest(url, "$[0].id", moduleInstance.getId());
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(bciModuleInstanceRepository.findById(moduleInstance.getId())).thenReturn(Optional.ofNullable(moduleInstance));
        performGetRequest(url + "/find/" + moduleInstance.getId(), "$.id", moduleInstance.getId());
    }

    @Test
    void testFindByStage() throws Exception {
        when(bciModuleInstanceRepository.findByOutcome(moduleInstance.getOutcome())).thenReturn(Collections.singletonList(moduleInstance));
        performGetRequest(url + "/find/outcome/" + moduleInstance.getOutcome(), "$[0].id", moduleInstance.getId());
    }

    @Test
    void testFindByActivitiesId() throws Exception {
        when(bciModuleInstanceRepository.findByActivitiesId(moduleInstance.getActivities().getFirst().getId())).thenReturn(Collections.singletonList(moduleInstance));
        performGetRequest(url + "/find/activities/" + moduleInstance.getActivities().getFirst().getId(),
                "$[0].id", moduleInstance.getId());
    }
}
