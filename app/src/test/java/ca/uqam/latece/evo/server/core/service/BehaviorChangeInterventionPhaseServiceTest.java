package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link BehaviorChangeInterventionPhaseService}, responsible for testing its
 * various functionalities. This class includes integration tests for CRUD operations and other
 * repository queries using a PostgreSQL database in a containerized setup.
 * <p>
 * The tests in this class ensure the proper functionality of BehaviorChangeInterventionPhaseService
 * and its interaction with the database using test containers to provide
 * a consistent test environment. Each test verifies specific business rules
 * or data retrieval criteria related to the BehaviorChangeInterventionPhase entity.
 * <p>
 * Annotations used for test setup include:
 * - @ContextConfiguration: Specifies test-specific configurations.
 * <p>
 * Dependencies injected into this test include:
 * - BehaviorChangeInterventionPhaseService to perform business logic operations specific to
 * BehaviorChangeInterventionPhase entities.
 * <p>
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {BehaviorChangeInterventionPhaseService.class, BehaviorChangeInterventionPhase.class})
public class BehaviorChangeInterventionPhaseServiceTest extends AbstractServiceTest {
    @Autowired
    private BehaviorChangeInterventionPhaseService interventionPhaseService;
    
    @Autowired
    private BehaviorChangeInterventionBlockService interventionBlockService;

    @Autowired
    private BehaviorChangeInterventionService interventionService;

    private BehaviorChangeInterventionPhase interventionPhase;
    private BehaviorChangeInterventionBlock interventionBlock;
    private static final String PHASE_ENTRY_CONDITION = "Intervention Phase ENTRY";
    private static final String PHASE_EXIT_CONDITION = "Intervention Phase EXIT";
    private static final String BLOCK_ENTRY_CONDITION = "Intervention Block ENTRY";
    private static final String BLOCK_EXIT_CONDITION = "Intervention Block EXIT";


    @BeforeEach
    void beforeEach(){
        // Creates intervention Block.
        interventionBlock = new BehaviorChangeInterventionBlock();
        interventionBlock.setEntryConditions(BLOCK_ENTRY_CONDITION);
        interventionBlock.setExitConditions(BLOCK_EXIT_CONDITION);
        BehaviorChangeInterventionBlock block = interventionBlockService.create(interventionBlock);

        // Creates intervention Phase.
        interventionPhase = new BehaviorChangeInterventionPhase();
        interventionPhase.setEntryConditions(PHASE_ENTRY_CONDITION);
        interventionPhase.setExitConditions(PHASE_EXIT_CONDITION);
        interventionPhase.addBehaviorChangeInterventionBlock(block);
        BehaviorChangeInterventionPhase phase = interventionPhaseService.create(interventionPhase);
    }

    @AfterEach
    void afterEach(){
        interventionBlockService.deleteById(interventionBlock.getId());
        interventionPhaseService.deleteById(interventionPhase.getId());
    }

    @Test
    @Override
    void testSave() {
        // Checks if the entity was saved.
        assert interventionPhase.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        BehaviorChangeInterventionPhase intervention = new BehaviorChangeInterventionPhase();
        intervention.setId(interventionPhase.getId());
        intervention.setEntryConditions("Phase ENTRY");
        intervention.setExitConditions("Phase EXIT");
        interventionPhaseService.update(intervention);

        // Checks the intervention update.
        assertNotEquals(PHASE_ENTRY_CONDITION, intervention.getEntryConditions());
        assertNotEquals(PHASE_EXIT_CONDITION, intervention.getExitConditions());
    }

    @Test
    @Override
    void testFindById() {
        // Look for intervention phase.
        BehaviorChangeInterventionPhase interventionFound = interventionPhaseService.findById(
                interventionPhase.getId());

        //Checks if the intervention phase was found.
        assertEquals(interventionPhase.getId(), interventionFound.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        // Ensure the database contains the Behavior Change Intervention Phase, so the test is isolated.
        BehaviorChangeInterventionPhase intervention = new BehaviorChangeInterventionPhase();
        intervention.setEntryConditions("Block ENTRY Delete");
        intervention.setExitConditions("Block EXIT Delete");

        // Persist the Behavior Change Intervention Phase before querying.
        interventionPhaseService.create(intervention);

        // Deletes the Behavior Change Intervention Phase.
        interventionPhaseService.deleteById(intervention.getId());

        // Checks if the Behavior Change Intervention Phase was deleted.
        assertFalse(interventionPhaseService.existsById(intervention.getId()));
    }

    @Test
    void testFindByEntryConditions() {
        // Query the Behavior Change Intervention Phase by Entry Condition.
        List<BehaviorChangeInterventionPhase> result = interventionPhaseService.findByEntryConditions(
                interventionPhase.getEntryConditions());

        // Assert that the result is not empty.
        assertFalse(result.isEmpty(), "Behavior Change Intervention Phase list should not be empty!");
    }

    @Test
    void testFindByExitConditions() {
        // Query the Behavior Change Intervention Phase by Exit Condition.
        List<BehaviorChangeInterventionPhase> result = interventionPhaseService.findByExitConditions(
                interventionPhase.getExitConditions());

        // Assert that the result is not empty.
        assertFalse(result.isEmpty(), "Behavior Change Intervention Phase list should not be empty!");
    }

    @Test
    void testFindByName() {
        // Creates the BehaviorChangeInterventionPhase.
        BehaviorChangeInterventionPhase intervention = new BehaviorChangeInterventionPhase();
        intervention.setEntryConditions("Block ENTRY Find ALL");
        intervention.setExitConditions("Block EXIT Find All");
        intervention.addBehaviorChangeInterventionBlock(interventionBlockService.create(interventionBlock));
        interventionPhaseService.create(intervention);

        // Executes the query.
        List<BehaviorChangeInterventionPhase> result = interventionPhaseService.findAll();

        // Tests.
        assertEquals(2, result.size());
        assertEquals(interventionPhase.getId(), result.get(0).getId());
        assertEquals(interventionPhase.getEntryConditions(), result.get(0).getEntryConditions());
        assertEquals(interventionPhase.getExitConditions(), result.get(0).getExitConditions());
        assertEquals(intervention.getId(), result.get(1).getId());
        assertEquals(intervention.getEntryConditions(), result.get(1).getEntryConditions());
        assertEquals(intervention.getExitConditions(), result.get(1).getExitConditions());
    }

    @Test
    void findByBehaviorChangeInterventionId() {
        // Creates the BehaviorChangeIntervention.
        BehaviorChangeIntervention bci = new BehaviorChangeIntervention();
        bci.setName("BCI");
        BehaviorChangeIntervention bciResult = interventionService.create(bci);

        // Creates the BehaviorChangeInterventionPhase.
        BehaviorChangeInterventionPhase intervention = new BehaviorChangeInterventionPhase();
        intervention.setEntryConditions("Block ENTRY Find BehaviorChangeInterventionId");
        intervention.setExitConditions("Block EXIT Find BehaviorChangeInterventionId");
        intervention.setBehaviorChangeIntervention(bciResult);
        BehaviorChangeInterventionPhase bciPhase = interventionPhaseService.create(intervention);

        // Executes the query.
        List<BehaviorChangeInterventionPhase> result = interventionPhaseService.findByBehaviorChangeInterventionId(bciResult.getId());

        // Tests.
        assertFalse(result.isEmpty(), "Behavior Change Intervention Phase list should not be empty!");
        assertEquals(1, result.size());
        assertEquals(bciPhase.getId(), result.get(0).getId());
        assertEquals(bciPhase.getEntryConditions(), result.get(0).getEntryConditions());
        assertEquals(bciPhase.getExitConditions(), result.get(0).getExitConditions());
        assertEquals(bciResult.getId(), result.get(0).getBehaviorChangeIntervention().getId());
    }

    @Test
    void findByBehaviorChangeInterventionBlockId() {
        // Creates the BehaviorChangeIntervention.
        BehaviorChangeIntervention bci = new BehaviorChangeIntervention();
        bci.setName("BCI");
        BehaviorChangeIntervention bciResult = interventionService.create(bci);

        // Create the BehaviorChangeInterventionBlock.
        BehaviorChangeInterventionBlock bciBlock = new BehaviorChangeInterventionBlock();
        bciBlock.setEntryConditions("Block ENTRY Find BehaviorChangeInterventionBlockId");
        bciBlock.setExitConditions("Block EXIT Find BehaviorChangeInterventionBlockId");
        BehaviorChangeInterventionBlock block = interventionBlockService.create(bciBlock);

        List<BehaviorChangeInterventionBlock> bcibList = new ArrayList<>();
        bcibList.add(interventionBlock);
        bcibList.add(block);

        // Creates the BehaviorChangeInterventionPhase.
        BehaviorChangeInterventionPhase intervention = new BehaviorChangeInterventionPhase();
        intervention.setEntryConditions("Block ENTRY Find BehaviorChangeInterventionId");
        intervention.setExitConditions("Block EXIT Find BehaviorChangeInterventionId");
        intervention.setBehaviorChangeIntervention(bciResult);
        intervention.setBehaviorChangeInterventionBlocks(bcibList);
        BehaviorChangeInterventionPhase bciPhase = interventionPhaseService.create(intervention);
        List<BehaviorChangeInterventionPhase> bcipList = new ArrayList<>();
        bcipList.add(bciPhase);
        block.setBlockBehaviorChangeInterventionPhases(bcipList);
        interventionBlockService.update(block);

        // Executes the query.
        List<BehaviorChangeInterventionPhase> result = interventionPhaseService.findByBehaviorChangeInterventionBlockId(block.getId());

        // Tests.
        assertFalse(result.isEmpty(), "Behavior Change Intervention Phase list should not be empty!");
        assertEquals(1, result.size());
        assertEquals(bciPhase.getId(), result.get(0).getId());
        assertEquals(bciPhase.getEntryConditions(), result.get(0).getEntryConditions());
        assertEquals(bciPhase.getExitConditions(), result.get(0).getExitConditions());
        assertEquals(bciResult.getId(), result.get(0).getBehaviorChangeIntervention().getId());
    }

    @Test
    @Override
    void testFindAll() {
        // Creates the BehaviorChangeInterventionPhase.
        BehaviorChangeInterventionPhase intervention = new BehaviorChangeInterventionPhase();
        intervention.setEntryConditions("Block ENTRY Find ALL");
        intervention.setExitConditions("Block EXIT Find All");
        interventionPhaseService.create(intervention);

        // Executes the query.
        List<BehaviorChangeInterventionPhase> result = interventionPhaseService.findAll();

        // Tests.
        assertFalse(result.isEmpty(), "Behavior Change Intervention Phase list should not be empty!");
        assertEquals(2, result.size());
        assertEquals(interventionPhase.getId(), result.get(0).getId());
        assertEquals(interventionPhase.getEntryConditions(), result.get(0).getEntryConditions());
        assertEquals(interventionPhase.getExitConditions(), result.get(0).getExitConditions());
        assertEquals(intervention.getId(), result.get(1).getId());
        assertEquals(intervention.getEntryConditions(), result.get(1).getEntryConditions());
        assertEquals(intervention.getExitConditions(), result.get(1).getExitConditions());
    }
}