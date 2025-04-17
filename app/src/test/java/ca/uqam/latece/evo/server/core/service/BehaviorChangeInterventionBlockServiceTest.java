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
 * The test class for the {@link BehaviorChangeInterventionBlockService}, responsible for testing its
 * various functionalities. This class includes integration tests for CRUD operations and other
 * repository queries using a PostgreSQL database in a containerized setup.
 * <p>
 * The tests in this class ensure the proper functionality of BehaviorChangeInterventionService
 * and its interaction with the database using test containers to provide
 * a consistent test environment. Each test verifies specific business rules
 * or data retrieval criteria related to the BehaviorChangeInterventionBlock entity.
 * <p>
 * Annotations used for test setup include:
 * - @ContextConfiguration: Specifies test-specific configurations.
 * <p>
 * Dependencies injected into this test include:
 * - BehaviorChangeInterventionBlockService to perform business logic operations specific to
 * BehaviorChangeInterventionBlock entities.
 * <p>
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {BehaviorChangeInterventionBlockService.class, BehaviorChangeInterventionBlock.class})
public class BehaviorChangeInterventionBlockServiceTest extends AbstractServiceTest {
    @Autowired
    private BehaviorChangeInterventionBlockService interventionBlockService;

    @Autowired
    private BehaviorChangeInterventionPhaseService interventionPhaseService;

    @Autowired
    private BehaviorChangeInterventionService interventionService;

    private BehaviorChangeInterventionBlock interventionBlock;
    private static final String ENTRY_CONDITION = "Intervention Block ENTRY";
    private static final String EXIT_CONDITION = "Intervention Block EXIT";

    @BeforeEach
    void beforeEach(){
        interventionBlock = new BehaviorChangeInterventionBlock();
        interventionBlock.setEntryConditions(ENTRY_CONDITION);
        interventionBlock.setExitConditions(EXIT_CONDITION);
        interventionBlockService.create(interventionBlock);
    }

    @AfterEach
    void afterEach(){
        interventionBlockService.deleteById(interventionBlock.getId());
    }

    @Test
    @Override
    void testSave() {
        // Checks if the entity was saved.
        assert interventionBlock.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        BehaviorChangeInterventionBlock intervention = new BehaviorChangeInterventionBlock();
        intervention.setId(interventionBlock.getId());
        intervention.setEntryConditions("Block ENTRY");
        intervention.setExitConditions("Block EXIT");
        interventionBlockService.update(intervention);

        // Checks the intervention update.
        assertNotEquals(ENTRY_CONDITION, intervention.getEntryConditions());
        assertNotEquals(EXIT_CONDITION, intervention.getExitConditions());
    }

    @Test
    @Override
    void testFindById() {
        BehaviorChangeInterventionBlock interventionFound = interventionBlockService.findById(
                interventionBlock.getId());

        // Checks if the Behavior Change Intervention Block are the same.
        assertEquals(interventionBlock.getId(), interventionFound.getId());
    }

    @Test
    @Override
    void testDeleteById() {
        // Ensure the database contains the Behavior Change Intervention Block, so the test is isolated.
        BehaviorChangeInterventionBlock intervention = new BehaviorChangeInterventionBlock();
        intervention.setEntryConditions("Block ENTRY Delete");
        intervention.setExitConditions("Block EXIT Delete");

        // Persist the Behavior Change Intervention Block before querying.
        interventionBlockService.create(intervention);

        // Deletes the Behavior Change Intervention Block.
        interventionBlockService.deleteById(intervention.getId());

        // Checks if the Behavior Change Intervention Block was deleted.
        assertFalse(interventionBlockService.existsById(intervention.getId()));
    }

    @Test
    void testFindByEntryConditions() {
        // Query the Behavior Change Intervention Block by Entry Condition.
        List<BehaviorChangeInterventionBlock> result = interventionBlockService.findByEntryConditions(
                interventionBlock.getEntryConditions());

        // Assert that the result is not empty.
        assertFalse(result.isEmpty(), "Behavior Change Intervention Block list should not be empty!");
    }

    @Test
    void testFindByExitConditions() {
        // Query the Behavior Change Intervention Block by Exit Condition.
        List<BehaviorChangeInterventionBlock> result = interventionBlockService.findByExitConditions(
                interventionBlock.getExitConditions());

        // Assert that the result is not empty.
        assertFalse(result.isEmpty(), "Behavior Change Intervention Block list should not be empty!");
    }

    @Test
    void testFindByName() {
        BehaviorChangeInterventionBlock intervention = new BehaviorChangeInterventionBlock();
        intervention.setEntryConditions("Block ENTRY Find ALL");
        intervention.setExitConditions("Block EXIT Find All");
        interventionBlockService.create(intervention);

        // Executes the query.
        List<BehaviorChangeInterventionBlock> result = interventionBlockService.findAll();

        // Tests.
        assertEquals(2, result.size());
        assertEquals(interventionBlock.getId(), result.get(0).getId());
        assertEquals(interventionBlock.getEntryConditions(), result.get(0).getEntryConditions());
        assertEquals(interventionBlock.getExitConditions(), result.get(0).getExitConditions());
        assertEquals(intervention.getId(), result.get(1).getId());
        assertEquals(intervention.getEntryConditions(), result.get(1).getEntryConditions());
        assertEquals(intervention.getExitConditions(), result.get(1).getExitConditions());

    }

    @Test
    void testFindByBehaviorChangeInterventionPhaseId() {
        // Creates the BehaviorChangeIntervention.
        BehaviorChangeIntervention bci = new BehaviorChangeIntervention();
        bci.setName("BCI");
        BehaviorChangeIntervention bciResult = interventionService.create(bci);

        // Create the BehaviorChangeInterventionBlock.
        BehaviorChangeInterventionBlock bciBlock = new BehaviorChangeInterventionBlock();
        bciBlock.setEntryConditions("Block ENTRY Find BehaviorChangeInterventionBlock");
        bciBlock.setExitConditions("Block EXIT Find BehaviorChangeInterventionBlock");
        BehaviorChangeInterventionBlock block = interventionBlockService.create(bciBlock);

        List<BehaviorChangeInterventionBlock> bcibList = new ArrayList<>();
        bcibList.add(interventionBlock);
        bcibList.add(block);

        // Creates the BehaviorChangeInterventionPhase.
        BehaviorChangeInterventionPhase intervention = new BehaviorChangeInterventionPhase();
        intervention.setEntryConditions("ENTRY Find BehaviorChangeInterventionPhaseId");
        intervention.setExitConditions("EXIT Find BehaviorChangeInterventionPhaseId");
        intervention.setBehaviorChangeIntervention(bciResult);
        intervention.setBehaviorChangeInterventionBlocks(bcibList);
        BehaviorChangeInterventionPhase bciPhase = interventionPhaseService.create(intervention);

        // Creates a BehaviorChangeInterventionPhase list.
        List<BehaviorChangeInterventionPhase> bcipList = new ArrayList<>();
        bcipList.add(bciPhase);
        // Add the BehaviorChangeInterventionPhase list in the block.
        block.setBlockBehaviorChangeInterventionPhases(bcipList);
        //Update the block.
        interventionBlockService.update(block);

        // Executes the query.
        List<BehaviorChangeInterventionBlock> result = interventionBlockService.findByBehaviorChangeInterventionPhaseId(bciPhase.getId());

        // Tests.
        assertFalse(result.isEmpty(), "Behavior Change Intervention Block list should not be empty!");
        assertEquals(1, result.size());
        assertEquals(block.getId(), result.get(0).getId());
        assertEquals(block.getEntryConditions(), result.get(0).getEntryConditions());
        assertEquals(block.getExitConditions(), result.get(0).getExitConditions());
        assertEquals(block.getBlockBehaviorChangeInterventionPhases().get(0).getId(),
                result.get(0).getBlockBehaviorChangeInterventionPhases().get(0).getId());

    }

    @Test
    @Override
    void testFindAll() {
        BehaviorChangeInterventionBlock intervention = new BehaviorChangeInterventionBlock();
        intervention.setEntryConditions("Block ENTRY Find ALL");
        intervention.setExitConditions("Block EXIT Find All");
        interventionBlockService.create(intervention);

        // Executes the query.
        List<BehaviorChangeInterventionBlock> result = interventionBlockService.findAll();

        // Tests.
        assertFalse(result.isEmpty(), "Behavior Change Intervention Block list should not be empty!");
        assertEquals(2, result.size());
        assertEquals(interventionBlock.getId(), result.get(0).getId());
        assertEquals(interventionBlock.getEntryConditions(), result.get(0).getEntryConditions());
        assertEquals(interventionBlock.getExitConditions(), result.get(0).getExitConditions());
        assertEquals(intervention.getId(), result.get(1).getId());
        assertEquals(intervention.getEntryConditions(), result.get(1).getEntryConditions());
        assertEquals(intervention.getExitConditions(), result.get(1).getExitConditions());
    }
}