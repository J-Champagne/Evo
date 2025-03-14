package ca.uqam.latece.evo.server.core.controller;


import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionPhaseRepository;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionRepository;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


/**
 * The BehaviorChangeIntervention Controller test class for the {@link BehaviorChangeInterventionController}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations supported the controller class, using WebMvcTes, and
 * repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = BehaviorChangeInterventionController.class)
@ContextConfiguration(classes = {BehaviorChangeInterventionController.class,
        BehaviorChangeInterventionService.class, BehaviorChangeIntervention.class})
public class BehaviorChangeInterventionControllerTest extends AbstractControllerTest {
    @MockBean
    private BehaviorChangeInterventionRepository behaviorChangeInterventionRepository;

    @MockBean
    private BehaviorChangeInterventionPhaseRepository behaviorChangeInterventionPhaseRepository;

    private BehaviorChangeIntervention behaviorChangeIntervention ;


    @BeforeEach
    @Override
    void setUp() {
        behaviorChangeIntervention = new BehaviorChangeIntervention();
        behaviorChangeIntervention.setId(1L);
        behaviorChangeIntervention.setName("Intervention");

        // Mock behavior for behaviorChangeInterventionRepository.save().
        when(behaviorChangeInterventionRepository.save(behaviorChangeIntervention)).
                thenReturn(behaviorChangeIntervention);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest("/bahaviorchangeintervention", behaviorChangeIntervention);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Create a new Behavior Change Intervention.
        BehaviorChangeIntervention interventionToUpdate = new BehaviorChangeIntervention();
        interventionToUpdate.setId(behaviorChangeIntervention.getId());
        interventionToUpdate.setName("Intervention Update");

        // Mock behavior for behaviorChangeInterventionRepository.save().
        when(behaviorChangeInterventionRepository.save(interventionToUpdate)).thenReturn(interventionToUpdate);

        // Mock behavior for behaviorChangeInterventionRepository.findById().
        when(behaviorChangeInterventionRepository.findById(interventionToUpdate.getId())).
                thenReturn(Optional.of(interventionToUpdate));

        // Perform a PUT request to test the controller.
        performUpdateRequest("/bahaviorchangeintervention", interventionToUpdate,"$.name",
                interventionToUpdate.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/bahaviorchangeintervention/" + behaviorChangeIntervention.getId(),
                behaviorChangeIntervention);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create a new Behavior Change Intervention.
        BehaviorChangeIntervention intervention = new BehaviorChangeIntervention();
        intervention.setId(2L);
        intervention.setName("Intervention Update");

        // Mock behavior for behaviorChangeInterventionRepository.save().
        when(behaviorChangeInterventionRepository.save(intervention)).
                thenReturn(intervention);

        // Mock behavior for behaviorChangeInterventionRepository.findById().
        when(behaviorChangeInterventionRepository.findById(intervention.getId())).
                thenReturn(Optional.of(intervention));

        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeintervention/find/" + intervention.getId(),
                "$.name", intervention.getName());
    }

    @Test
    void testFindByName() throws Exception {
        // Create a new Behavior Change Intervention.
        BehaviorChangeIntervention intervention = new BehaviorChangeIntervention();
        intervention.setId(2L);
        intervention.setName("Intervention Update");

        // Mock behavior for behaviorChangeInterventionRepository.save().
        when(behaviorChangeInterventionRepository.save(intervention)).
                thenReturn(intervention);

        // Mock behavior for behaviorChangeInterventionRepository.findById().
        when(behaviorChangeInterventionRepository.findByName(intervention.getName())).
                thenReturn(Collections.singletonList(intervention));

        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeintervention/find/name/" + intervention.getName(),
                "$[0].name", intervention.getName());
    }

    @Test
    void testFindByBehaviorChangeInterventionPhase() throws Exception {
        // Creates a BehaviorChangeInterventionPhase.
        BehaviorChangeInterventionPhase phase1 = new BehaviorChangeInterventionPhase();
        phase1.setId(6L);
        phase1.setEntryConditions("test entry conditions 1");
        phase1.setExitConditions("test exit conditions 1");

        // Creates a BehaviorChangeInterventionPhase.
        BehaviorChangeInterventionPhase phase2 = new BehaviorChangeInterventionPhase();
        phase2.setId(7L);
        phase2.setEntryConditions("test entry conditions 2");
        phase2.setExitConditions("test exit conditions 2");

        List<BehaviorChangeInterventionPhase> behaviorChangeInterventionPhases = new ArrayList<>();
        behaviorChangeInterventionPhases.add(phase1);
        behaviorChangeInterventionPhases.add(phase2);

        // Creates a BehaviorChangeIntervention.
        BehaviorChangeIntervention intervention = new BehaviorChangeIntervention();
        intervention.setId(12L);
        intervention.setName("Intervention 4");
        intervention.setBehaviorChangeInterventionPhases(behaviorChangeInterventionPhases);

        // Mock behavior for behaviorChangeInterventionRepository.save().
        when(behaviorChangeInterventionRepository.save(intervention)).thenReturn(intervention);

        // Mock behavior for behaviorChangeInterventionRepository.findByBehaviorChangeInterventionPhase().
        when(behaviorChangeInterventionRepository.findByBehaviorChangeInterventionPhase(phase1.getId())).
                thenReturn(Collections.singletonList(intervention));

        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeintervention/find/behaviorchangeinterventionphase/" + phase1.getId(),
                "$[0].name", intervention.getName());

        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeintervention/find/behaviorchangeinterventionphase/" + phase1.getId(),
                "$[0].behaviorChangeInterventionPhases.[0].id", intervention.getBehaviorChangeInterventionPhases().get(0).getId());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for behaviorChangeInterventionRepository.findAll().
        when(behaviorChangeInterventionRepository.findAll()).
                thenReturn(Collections.singletonList(behaviorChangeIntervention));

        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeintervention","$[0].id",1);
    }
}