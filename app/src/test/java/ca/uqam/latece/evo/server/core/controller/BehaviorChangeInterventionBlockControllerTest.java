package ca.uqam.latece.evo.server.core.controller;


import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionBlockRepository;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionPhaseRepository;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionRepository;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionBlockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;


/**
 * The BehaviorChangeInterventionBlock Controller test class for the {@link BehaviorChangeInterventionBlockController}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations supported the controller class, using WebMvcTes, and
 * repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = BehaviorChangeInterventionBlockController.class)
@ContextConfiguration(classes = {BehaviorChangeInterventionBlockController.class,
        BehaviorChangeInterventionBlockService.class, BehaviorChangeInterventionBlock.class})
public class BehaviorChangeInterventionBlockControllerTest extends AbstractControllerTest {
    @MockitoBean
    private BehaviorChangeInterventionBlockRepository interventionBlockRepository;

    @MockitoBean
    private BehaviorChangeInterventionRepository behaviorChangeInterventionRepository;

    @MockitoBean
    private BehaviorChangeInterventionPhaseRepository interventionPhaseRepository;

    private BehaviorChangeInterventionBlock interventionBlock;


    @BeforeEach
    @Override
    void setUp() {
        interventionBlock = new BehaviorChangeInterventionBlock();
        interventionBlock.setId(1L);
        interventionBlock.setEntryConditions("EntryConditions");
        interventionBlock.setExitConditions("ExitConditions");

        // Mock behavior for interventionBlockRepository.save().
        when(interventionBlockRepository.save(interventionBlock)).
                thenReturn(interventionBlock);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest("/behaviorchangeinterventionblock", interventionBlock);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Create a new Behavior Change Intervention Block.
        BehaviorChangeInterventionBlock interventionToUpdate = new BehaviorChangeInterventionBlock();
        interventionToUpdate.setId(interventionBlock.getId());
        interventionToUpdate.setEntryConditions("EntryConditions 2");
        interventionToUpdate.setExitConditions("ExitConditions 2");

        // Mock behavior for interventionBlockRepository.save().
        when(interventionBlockRepository.save(interventionToUpdate)).thenReturn(interventionToUpdate);

        // Mock behavior for interventionBlockRepository.findById().
        when(interventionBlockRepository.findById(interventionToUpdate.getId())).
                thenReturn(Optional.of(interventionToUpdate));

        // Perform a PUT request to test the controller.
        performUpdateRequest("/behaviorchangeinterventionblock", interventionToUpdate,
                "$.entryConditions",
                interventionToUpdate.getEntryConditions());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/behaviorchangeinterventionblock/" + interventionBlock.getId(),
                interventionBlock);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create a new Behavior Change Intervention Block.
        BehaviorChangeInterventionBlock intervention = new BehaviorChangeInterventionBlock();
        intervention.setId(2L);
        intervention.setEntryConditions("EntryConditions 25");
        intervention.setExitConditions("ExitConditions 25");


        // Mock behavior for interventionBlockRepository.save().
        when(interventionBlockRepository.save(intervention)).
                thenReturn(intervention);

        // Mock behavior for interventionBlockRepository.findById().
        when(interventionBlockRepository.findById(intervention.getId())).
                thenReturn(Optional.of(intervention));

        // Perform a GET request to test the controller.
        performGetRequest("/behaviorchangeinterventionblock/find/" + intervention.getId(),
                "$.entryConditions", intervention.getEntryConditions());
    }

    @Test
    void testFindByEntryConditions() throws Exception {
        // Create a new Behavior Change Intervention Block.
        BehaviorChangeInterventionBlock intervention = new BehaviorChangeInterventionBlock();
        intervention.setId(3L);
        intervention.setEntryConditions("EntryConditions 325");
        intervention.setExitConditions("ExitConditions 325");

        // Mock behavior for interventionBlockRepository.save().
        when(interventionBlockRepository.save(intervention)).
                thenReturn(intervention);

        // Mock behavior for interventionBlockRepository.findByEntryConditions().
        when(interventionBlockRepository.findByEntryConditions(intervention.getEntryConditions())).
                thenReturn(Collections.singletonList(intervention));

        // Perform a GET request to test the controller.
        performGetRequest("/behaviorchangeinterventionblock/find/entrycond/" + intervention.getEntryConditions(),
                "$[0].entryConditions", intervention.getEntryConditions());
    }

    @Test
    void testFindByExitConditions() throws Exception {
        // Create a new Behavior Change Intervention Block.
        BehaviorChangeInterventionBlock intervention = new BehaviorChangeInterventionBlock();
        intervention.setId(3L);
        intervention.setEntryConditions("EntryConditions 325");
        intervention.setExitConditions("ExitConditions 325");

        // Mock behavior for interventionBlockRepository.save().
        when(interventionBlockRepository.save(intervention)).
                thenReturn(intervention);

        // Mock behavior for interventionBlockRepository.findByExitConditions().
        when(interventionBlockRepository.findByExitConditions(intervention.getExitConditions())).
                thenReturn(Collections.singletonList(intervention));

        // Perform a GET request to test the controller.
        performGetRequest("/behaviorchangeinterventionblock/find/exitcond/" + intervention.getExitConditions(),
                "$[0].exitConditions", intervention.getExitConditions());
    }

    @Test
    void testFindByBehaviorChangeInterventionPhaseId() throws Exception {
        // Creates the BehaviorChangeIntervention.
        BehaviorChangeIntervention intervention = new BehaviorChangeIntervention();
        intervention.setId(1L);
        intervention.setName("BCI");

        // Mock behavior for behaviorChangeInterventionRepository.save().
        when(behaviorChangeInterventionRepository.save(intervention)).thenReturn(intervention);

        // Creates a BehaviorChangeInterventionPhase.
        BehaviorChangeInterventionPhase interventionPhase = new BehaviorChangeInterventionPhase();
        interventionPhase.setId(56L);
        interventionPhase.setEntryConditions("EntryConditions 56");
        interventionPhase.setExitConditions("ExitConditions 56");
        interventionPhase.setBehaviorChangeIntervention(intervention);

        // Mock behavior for interventionPhaseRepository.save().
        when(interventionPhaseRepository.save(interventionPhase)).thenReturn(interventionPhase);

        // Creates a BehaviorChangeInterventionBlock.
        interventionBlock = new BehaviorChangeInterventionBlock();
        interventionBlock.setId(6L);
        interventionBlock.setEntryConditions("EntryConditions findByBehaviorChangeInterventionBlock");
        interventionBlock.setExitConditions("ExitConditions findByBehaviorChangeInterventionBlock");
        interventionBlock.addBehaviorChangeInterventionPhase(interventionPhase);

        // Mock behavior for interventionBlockRepository.save().
        when(interventionBlockRepository.save(interventionBlock)).thenReturn(interventionBlock);

        // Block result object.
        BehaviorChangeInterventionBlock interventionBlockResult = new BehaviorChangeInterventionBlock();

        // Mock behavior for interventionBlockRepository.findByBehaviorChangeInterventionPhaseId().
        when(interventionBlockRepository.findByBehaviorChangeInterventionPhaseId(interventionPhase.getId())).
                thenReturn(Collections.singletonList(interventionBlockResult));

        // Perform a GET request to test the controller.
        performGetRequest(
                "/behaviorchangeinterventionblock/find/behaviorchangeinterventionphase/" + interventionPhase.getId(),
                "$[0].exitConditions",
                interventionBlockResult.getExitConditions());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for behaviorChangeInterventionRepository.findAll().
        when(interventionBlockRepository.findAll()).
                thenReturn(Collections.singletonList(interventionBlock));

        // Perform a GET request to test the controller.
        performGetRequest("/behaviorchangeinterventionblock","$[0].id",1);
    }
}