package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.BehaviorChangeInterventionPhaseInstanceController;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.repository.instance.BCIModuleInstanceRepository;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionBlockInstanceRepository;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionPhaseInstanceRepository;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionPhaseInstanceService;

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
 * Tests methods found in BehaviorChangeInterventionPhaseInstanceController using WebMvcTest, and repository queries using MockMvc (Mockito).
 * @author Julien Champagne.
 */
@WebMvcTest(controllers = BehaviorChangeInterventionPhaseInstanceController.class)
@ContextConfiguration(classes = {BehaviorChangeInterventionPhaseInstance.class, BehaviorChangeInterventionPhaseInstanceService.class,
        BehaviorChangeInterventionPhaseInstanceController.class})
public class BehaviorChangeInterventionPhaseInstanceControllerTest extends AbstractControllerTest {
    @MockBean
    BehaviorChangeInterventionPhaseInstanceRepository bciPhaseInstanceRepository;

    @MockBean
    BehaviorChangeInterventionBlockInstanceRepository bciBlockInstanceRepository;

    @MockBean
    BCIModuleInstanceRepository bciModuleInstanceRepository;

    private Role role = new Role("Administrator");

    private HealthCareProfessional hcp = new HealthCareProfessional("Bob", "bob@gmail.com",
            "222-2222", "Student", "New-York", "Health");

    private Participant participant = new Participant(role, hcp);

    private List<Participant> participants = List.of(participant);

    private BCIActivityInstance activityInstance = new BCIActivityInstance("In progress", LocalDate.now(),
            DateFormatter.convertDateStrTo_yyyy_MM_dd("2026/01/08"), participants);

    private List<BCIActivityInstance> activities = List.of(activityInstance);

    private BCIModuleInstance moduleInstance = new BCIModuleInstance(OutcomeType.SUCCESSFUL, activities);

    private List<BCIModuleInstance> modules = List.of(moduleInstance);

    private BehaviorChangeInterventionBlockInstance blockInstance = new BehaviorChangeInterventionBlockInstance(TimeCycle.BEGINNING,
            activities);

    private List<BehaviorChangeInterventionBlockInstance> blocks = List.of(blockInstance);

    private BehaviorChangeInterventionPhaseInstance phaseInstance = new BehaviorChangeInterventionPhaseInstance(blockInstance, blocks, modules);

    private static final String url = "/behaviorchangeinterventionphaseinstance";

    @BeforeEach
    @Override
    public void setUp() {
        blockInstance.setId(1L);
        moduleInstance.setId(1L);
        phaseInstance.setId(1L);
        when(bciBlockInstanceRepository.save(blockInstance)).thenReturn(blockInstance);
        when(bciModuleInstanceRepository.save(moduleInstance)).thenReturn(moduleInstance);
        when(bciPhaseInstanceRepository.save(phaseInstance)).thenReturn(phaseInstance);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(url, phaseInstance);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        BehaviorChangeInterventionBlockInstance blockInstance2 = new BehaviorChangeInterventionBlockInstance(TimeCycle.END,
                activities);
        blockInstance2.setId(2L);
        when(bciBlockInstanceRepository.save(blockInstance2)).thenReturn(blockInstance2);

        BehaviorChangeInterventionPhaseInstance updated = new BehaviorChangeInterventionPhaseInstance(
                blockInstance2, blocks, modules);
        updated.setId(phaseInstance.getId());

        when(bciPhaseInstanceRepository.save(updated)).thenReturn(updated);
        when(bciPhaseInstanceRepository.findById(updated.getId())).thenReturn(Optional.of(updated));
        performUpdateRequest(url, updated, "$.currentBlock.stage", updated.getCurrentBlock().getStage().toString());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(url + "/" + phaseInstance.getId(), phaseInstance);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(bciPhaseInstanceRepository.findAll()).thenReturn(Collections.singletonList(phaseInstance));
        performGetRequest(url, "$[0].id", 1);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(bciPhaseInstanceRepository.findById(phaseInstance.getId())).thenReturn(Optional.ofNullable(phaseInstance));
        performGetRequest(url + "/find/" + phaseInstance.getId(), "$.id", phaseInstance.getId());
    }

    @Test
    void testFindByCurrentBlockId() throws Exception {
        when(bciPhaseInstanceRepository.findByCurrentBlockId(phaseInstance.getCurrentBlock().getId())).thenReturn(Collections.singletonList(phaseInstance));
        performGetRequest(url + "/find/currentblock/" + phaseInstance.getCurrentBlock().getId(), "$[0].id", phaseInstance.getId());
    }

    @Test
    void testFindByBlocksId() throws Exception {
        when(bciPhaseInstanceRepository.findByBlocksId(phaseInstance.getBlocks().get(0).getId())).thenReturn(Collections.singletonList(phaseInstance));
        performGetRequest(url + "/find/blocks/" + phaseInstance.getBlocks().get(0).getId(), "$[0].id", phaseInstance.getId());
    }

    @Test
    void testFindByModuleId() throws Exception {
        when(bciPhaseInstanceRepository.findByModulesId(phaseInstance.getModules().get(0).getId())).thenReturn(Collections.singletonList(phaseInstance));
        performGetRequest(url + "/find/modules/" + phaseInstance.getModules().get(0).getId(), "$[0].id", phaseInstance.getId());
    }
}
