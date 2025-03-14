package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link BehaviorChangeInterventionService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * <p>
 * The tests in this class ensure the proper functionality of BehaviorChangeInterventionService
 * and its interaction with the database using test containers to provide
 * a consistent test environment. Each test verifies specific business rules
 * or data retrieval criteria related to the BehaviorChangeIntervention entity.
 * <p>
 * Annotations used for test setup include:
 * - @ContextConfiguration: Specifies test-specific configurations.
 * <p>
 * Dependencies injected into this test include:
 * - BehaviorChangeInterventionService to perform business logic operations specific to BehaviorChangeIntervention entities.
 * <p>
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {BehaviorChangeInterventionService.class, BehaviorChangeIntervention.class})
public class BehaviorChangeInterventionServiceTest extends AbstractServiceTest {
    @Autowired
    private BehaviorChangeInterventionService behaviorChangeInterventionService;

    @Autowired
    private BehaviorChangeInterventionPhaseService behaviorChangeInterventionPhaseService;

    private BehaviorChangeIntervention behaviorChangeIntervention;
    private BehaviorChangeInterventionPhase behaviorChangeInterventionPhase;

    private static final String INTERVENTION_NAME = "Behavior Change Intervention Test";
    private static final String PHASE_ENTRY_CONDITION = "Intervention Phase ENTRY";
    private static final String PHASE_EXIT_CONDITION = "Intervention Phase EXIT";

    @BeforeEach
    void beforeEach(){
        // Creates a BehaviorChangeIntervention.
        behaviorChangeIntervention = new BehaviorChangeIntervention();
        behaviorChangeIntervention.setName(INTERVENTION_NAME);
        behaviorChangeInterventionService.create(behaviorChangeIntervention);

        // Creates a BehaviorChangeInterventionPhase.
        behaviorChangeInterventionPhase = new BehaviorChangeInterventionPhase();
        behaviorChangeInterventionPhase.setEntryConditions(PHASE_ENTRY_CONDITION);
        behaviorChangeInterventionPhase.setExitConditions(PHASE_EXIT_CONDITION);
        behaviorChangeInterventionPhase.setBehaviorChangeIntervention(behaviorChangeIntervention);
        behaviorChangeInterventionPhaseService.create(behaviorChangeInterventionPhase);
    }

    @AfterEach
    void afterEach(){
        behaviorChangeInterventionService.deleteById(behaviorChangeIntervention.getId());
    }

    @Test
    @Override
    void testSave() {
        // Checks if the entity was saved.
        assert behaviorChangeIntervention.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        BehaviorChangeIntervention intervention = new BehaviorChangeIntervention();
        intervention.setId(behaviorChangeIntervention.getId());
        intervention.setName("Intervention Test");
        behaviorChangeInterventionService.update(intervention);

        // Checks the intervention update.
        assertNotEquals(INTERVENTION_NAME, intervention.getName());
    }

    @Test
    @Override
    void testFindById() {
        BehaviorChangeIntervention interventionFound = behaviorChangeInterventionService.findById(
                behaviorChangeIntervention.getId());

        // Checks if the Behavior Change Intervention are the same.
        assertEquals(behaviorChangeIntervention.getId(), interventionFound.getId());
    }

    @Test
    @Override
    void testFindByName() {
        // Query the Behavior Change Intervention by name.
        List<BehaviorChangeIntervention> result = behaviorChangeInterventionService.findByName(
                behaviorChangeIntervention.getName());

        // Assert that the result is not empty.
        assertFalse(result.isEmpty(), "Behavior Change Intervention list should not be empty!");
    }

    @Test
    @Override
    void testDeleteById() {
        // Ensure the database contains the Behavior Change Intervention, so the test is isolated.
        BehaviorChangeIntervention intervention = new BehaviorChangeIntervention();
        intervention.setName("Intervention Test Delete");

        // Persist the Behavior Change Intervention before querying.
        behaviorChangeInterventionService.create(intervention);

        // Deletes the Behavior Change Intervention.
        behaviorChangeInterventionService.deleteById(intervention.getId());

        // Checks if the Behavior Change Intervention was deleted.
        assertFalse(behaviorChangeInterventionService.existsById(intervention.getId()));
    }

    @Test
    void findByBehaviorChangeInterventionPhase() {
        // Executes the query.
        List<BehaviorChangeIntervention> result =
                behaviorChangeInterventionService.findByBehaviorChangeInterventionPhase(behaviorChangeInterventionPhase.getId());

        // Tests.
        assertFalse(result.isEmpty(), "Behavior Change Intervention list should not be empty!");
        assertEquals(1, result.size());
        assertEquals(behaviorChangeIntervention.getId(), result.get(0).getId());
        assertEquals(behaviorChangeIntervention.getName(), result.get(0).getName());
    }

    @Test
    @Override
    void testFindAll() {
        BehaviorChangeIntervention intervention = new BehaviorChangeIntervention();
        intervention.setName("Intervention Test Find All");
        behaviorChangeInterventionService.create(intervention);

        // Executes the query.
        List<BehaviorChangeIntervention> result = behaviorChangeInterventionService.findAll();

        // Tests.
        assertFalse(result.isEmpty(), "Behavior Change Intervention list should not be empty!");
        assertEquals(2, result.size());
        assertEquals(behaviorChangeIntervention.getId(), result.get(0).getId());
        assertEquals(behaviorChangeIntervention.getName(), result.get(0).getName());
        assertEquals(intervention.getId(), result.get(1).getId());
        assertEquals(intervention.getName(), result.get(1).getName());
    }
}