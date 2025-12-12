package ca.uqam.latece.evo.server.core.poc;

import ca.uqam.latece.evo.server.core.config.EvoDataLoader;
import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.event.BCIActivityClientEvent;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.model.Interaction;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.poc.factory.BCIInstanceFactory;
import ca.uqam.latece.evo.server.core.poc.factory.BCIRecipeFactory;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionInstanceService;
import ca.uqam.latece.evo.server.core.service.instance.InteractionInstanceService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProofOfConcept extends EvoDataLoader {

    @Autowired
    private BCIInstanceFactory bciInstanceFactory;

    @Autowired
    private BCIRecipeFactory bciRecipeFactory;

    @Autowired
    private InteractionInstanceService interactionInstanceService;

    @Autowired
    private BehaviorChangeInterventionInstanceService bciInstanceService;

    /**
     * Scenario one for the POC.
     * Goal: Create instances from recipes and saving them in the database
     */
    @Test
    @Order(1)
    void testProofOfConceptScenarioOne() {
        //Create recipes
        bciRecipeFactory.createBciInteractions(1, true, true);
        bciRecipeFactory.createBciBlocks(1, true, true);
        bciRecipeFactory.createBciPhases(1, true, true);
        BehaviorChangeIntervention bciRecipe = bciRecipeFactory.createBciIntervention(true, true);


        //Fetch recipes
        List<Interaction> interactionRecipes = bciRecipeFactory.findAllInteractions();
        List<BehaviorChangeInterventionBlock> blockRecipes = bciRecipeFactory.findAllBciBlocks();
        List<BehaviorChangeInterventionPhase> phaseRecipes = bciRecipeFactory.findAllBciPhases();
        bciRecipe = bciRecipeFactory.findBci(bciRecipe.getId());

        //Create instances
        BehaviorChangeInterventionInstance bciInstance = bciInstanceFactory.createBCIInstanceFromRecipes
                (1, bciRecipe, phaseRecipes, blockRecipes, interactionRecipes);

        //Fetch instances
        bciInstance = bciInstanceService.findById(bciInstance.getId());

        //Assert persistence
        BehaviorChangeInterventionPhaseInstance phaseInstance = bciInstance.getActivities().getFirst();
        BehaviorChangeInterventionBlockInstance blockInstance = phaseInstance.getActivities().getFirst();
        BCIActivityInstance activityInstanceSaved = blockInstance.getActivities().getFirst();

        assertNotNull(bciInstance.getId());
        assertNotNull(phaseInstance.getId());
        assertNotNull(blockInstance.getId());
        assertNotNull(activityInstanceSaved.getId());
    }

    /**
     * Scenario two for the POC.
     * Goal:    Finish an entire intervention with a simple structure
     * Context: Simple intervention structure (1 phase, 1 block, 1 activity)
     * Send:    ClientEvent FINISH
     * Verify:  The status of the JSON response is set to success and that the exitDates are set
     *          All the instances are set to FINISHED
     */
    @Test
    @Order(2)
    void testProofOfConceptScenarioTwo() {
        //Create recipes
        List<Interaction> interactionRecipes = bciRecipeFactory.createBciInteractions(1, true, true);
        List<BehaviorChangeInterventionBlock> blockRecipes = bciRecipeFactory.createBciBlocks(1, true, true);
        List<BehaviorChangeInterventionPhase> phaseRecipes = bciRecipeFactory.createBciPhases(1, true, true);
        BehaviorChangeIntervention bciRecipe = bciRecipeFactory.createBciIntervention(true, true);

        //Create instances
        BehaviorChangeInterventionInstance bciInstance = bciInstanceFactory.createBCIInstanceFromRecipes
                (1, bciRecipe, phaseRecipes, blockRecipes, interactionRecipes);

        //Create and handle the client event
        BehaviorChangeInterventionPhaseInstance phaseInstance = bciInstance.getActivities().getFirst();
        BehaviorChangeInterventionBlockInstance blockInstance = phaseInstance.getActivities().getFirst();
        BCIActivityInstance activityInstance = blockInstance.getActivities().getFirst();

        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(ClientEvent.FINISH,
                activityInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        //Fetch instances
        bciInstance = bciInstanceService.findById(bciInstance.getId());
        phaseInstance = bciInstance.getActivities().getFirst();
        blockInstance = phaseInstance.getActivities().getFirst();
        activityInstance = blockInstance.getActivities().getFirst();

        //Assert intervention is finished
        Assertions.assertTrue(response.isSuccess());
        assertEquals(ExecutionStatus.FINISHED, activityInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, blockInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, bciInstance.getStatus());

        assertNotNull(bciInstance.getEntryDate());
        assertNotNull(bciInstance.getExitDate());
        assertNotNull(phaseInstance.getExitDate());
        assertNotNull(blockInstance.getExitDate());
        assertNotNull(activityInstance.getExitDate());
    }

    /**
     * Scenario three for the POC.
     * Goal:    Finish only 1 activity in a block in a complex structure
     * Context: Complex intervention structure (with 3 phases, each with 3 blocks, each with 3 activity)
     * Send:    ClientEvent FINISH
     * Verify:  The status of the JSON response is set to success
     *          Only one activityInstance in the currentBlock of the currentPhase of the intervention is FINISHED
     *          The current phase, along with its currentBlock, and all other bciActivities of that block, are IN_PROGRESS
     *          The intervention is still IN_PROGRESS and its currentPhase should be the 1st phase
     */
    @Test
    @Order(3)
    void testProofOfConceptScenarioThree() {
        //Create recipes with entry and exit conditions set to the right value for this scenario
        List<Interaction> interactionRecipesConditionSuccess = bciRecipeFactory.createBciInteractions(1, true, true);
        List<Interaction> interactionRecipesConditionFail = bciRecipeFactory.createBciInteractions(26, false, false);
        List<Interaction> interactionRecipes = new ArrayList<>(interactionRecipesConditionSuccess);
        interactionRecipes.addAll(interactionRecipesConditionFail);

        List<BehaviorChangeInterventionBlock> blockRecipes = bciRecipeFactory.createBciBlocks(9, false, false);
        List<BehaviorChangeInterventionPhase> phaseRecipes = bciRecipeFactory.createBciPhases(3, false, false);
        BehaviorChangeIntervention bciRecipe = bciRecipeFactory.createBciIntervention(true, true);

        //Create the instances
        BehaviorChangeInterventionInstance bciInstance = bciInstanceFactory.createBCIInstanceFromRecipes
                (3, bciRecipe, phaseRecipes, blockRecipes, interactionRecipes);


        //Create and handle the client event
        BehaviorChangeInterventionPhaseInstance phaseInstance = bciInstance.getActivities().getFirst();
        BehaviorChangeInterventionBlockInstance blockInstance = phaseInstance.getActivities().getFirst();
        BCIActivityInstance activityInstance = blockInstance.getActivities().getFirst();

        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(ClientEvent.FINISH,
                activityInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);


        //Fetch and get the instances
        bciInstance = bciInstanceService.findById(bciInstance.getId());
        phaseInstance = bciInstance.getCurrentPhase();
        blockInstance = phaseInstance.getActivities().getFirst();
        activityInstance = blockInstance.getActivities().getFirst();

        //Asserts
        Assertions.assertTrue(response.isSuccess());
        assertEquals(ExecutionStatus.FINISHED, activityInstance.getStatus());
        assertEquals(ExecutionStatus.READY, blockInstance.getActivities().get(1).getStatus());
        assertEquals(ExecutionStatus.READY, blockInstance.getActivities().get(2).getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, blockInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getStatus());
        assertEquals(bciInstance.getCurrentPhase().getId(), phaseInstance.getId());
    }

    /**
     * Scenario four for the POC.
     * Goal:    Finish a block in a complex structure
     * Context: Complex intervention structure (with 3 phases, each with 3 blocks, each with 3 activity)
     * Send:    ClientEvent FINISH
     * Verify:  The status of the JSON response is set to success
     *          Only one activityInstance in the old currentBlock of the currentPhase of the intervention is FINISHED
     *          The old currentBlock of the currentPhase of the intervention is FINISHED.
     *          The current phase, along with its new currentBlock, and all bciActivities of that new currentBlock, are IN_PROGRESS
     *          The intervention is still IN_PROGRESS and its currentPhase should be the 1st phase
     */
    @Test
    @Order(4)
    void testProofOfConceptScenarioFour() {
        //Create recipes with entry and exit conditions set to the right value for this scenario
        List<Interaction> interactionRecipesConditionSuccess = bciRecipeFactory.createBciInteractions(1, true, true);
        List<Interaction> interactionRecipesConditionFail = bciRecipeFactory.createBciInteractions(26, true, false);
        List<Interaction> interactionRecipes = new ArrayList<>(interactionRecipesConditionSuccess);
        interactionRecipes.addAll(interactionRecipesConditionFail);

        List<BehaviorChangeInterventionBlock> blockRecipesSuccess = bciRecipeFactory.createBciBlocks(1, true, true);
        List<BehaviorChangeInterventionBlock> blockRecipesFail = bciRecipeFactory.createBciBlocks(8, true, false);
        List<BehaviorChangeInterventionBlock> blockRecipes = new ArrayList<>(blockRecipesSuccess);
        blockRecipes.addAll(blockRecipesFail);

        List<BehaviorChangeInterventionPhase> phaseRecipes = bciRecipeFactory.createBciPhases(3, false, false);
        BehaviorChangeIntervention bciRecipe = bciRecipeFactory.createBciIntervention(true, true);

        //Create the instances
        BehaviorChangeInterventionInstance bciInstance = bciInstanceFactory.createBCIInstanceFromRecipes
                (3, bciRecipe, phaseRecipes, blockRecipes, interactionRecipes);


        //Create and handle the client event
        BehaviorChangeInterventionPhaseInstance phaseInstance = bciInstance.getActivities().getFirst();
        BehaviorChangeInterventionBlockInstance blockInstance = phaseInstance.getActivities().getFirst();
        BCIActivityInstance activityInstance = blockInstance.getActivities().getFirst();

        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(ClientEvent.FINISH,
                activityInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        //Fetch and get the instances
        bciInstance = bciInstanceService.findById(bciInstance.getId());
        phaseInstance = bciInstance.getActivities().getFirst();
        blockInstance = phaseInstance.getActivities().getFirst();
        activityInstance = blockInstance.getActivities().getFirst();

        //Asserts
        Assertions.assertTrue(response.isSuccess());
        assertEquals(ExecutionStatus.FINISHED, activityInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, blockInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getStatus());
        assertEquals(bciInstance.getCurrentPhase().getId(), phaseInstance.getId());
        assertEquals(phaseInstance.getCurrentBlock().getId(), phaseInstance.getActivities().get(1).getId());
    }

    /**
     * Scenario five for the POC.
     * Goal:    Finish a phase in a complex structure
     * Context: Complex intervention structure (with 3 phases, each with 3 blocks, each with 3 activity)
     * Send: ClientEvent FINISH
     * Verify: The status of the JSON response is set to success
     *         The 1st phase, along with its currentBlock, and one activityInstance of the currentBlock are FINISHED
     *         The 2nd phase (Along with its currentBlock, and all bciActivities of that block) are IN_PROGRESS
     *         The intervention is still IN_PROGRESS and its currentPhase should be the 2nd phase
     *         The rest of the instances should still be READY
     */
    @Test
    @Order(5)
    void testProofOfConceptScenarioFive() {
        //Create recipes with entry and exit conditions set to the right value for this scenario
        List<Interaction> interactionRecipes = bciRecipeFactory.createBciInteractions(27, true, true);
        List<BehaviorChangeInterventionBlock> blockRecipes = bciRecipeFactory.createBciBlocks(9, true, true);
        List<BehaviorChangeInterventionPhase> phaseRecipes = bciRecipeFactory.createBciPhases(3, true, true);
        BehaviorChangeIntervention bciRecipe = bciRecipeFactory.createBciIntervention(true, true);

        //Create the instances
        BehaviorChangeInterventionInstance bciInstance = bciInstanceFactory.createBCIInstanceFromRecipes
                (3, bciRecipe, phaseRecipes, blockRecipes, interactionRecipes);


        //Create and handle the client event
        BehaviorChangeInterventionPhaseInstance phaseInstance = bciInstance.getActivities().getFirst();
        BehaviorChangeInterventionBlockInstance blockInstance = phaseInstance.getActivities().getFirst();
        BCIActivityInstance activityInstance = blockInstance.getActivities().getFirst();

        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(ClientEvent.FINISH,
                activityInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);


        //Fetch and get the instances
        bciInstance = bciInstanceService.findById(bciInstance.getId());
        phaseInstance = bciInstance.getActivities().getFirst();
        blockInstance = phaseInstance.getActivities().getFirst();
        activityInstance = blockInstance.getActivities().getFirst();

        //Asserts
        Assertions.assertTrue(response.isSuccess());
        assertEquals(ExecutionStatus.FINISHED, activityInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, blockInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getCurrentPhase().getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getCurrentPhase().getCurrentBlock().getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getCurrentPhase().getCurrentBlock().getActivities().getFirst().getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getStatus());
    }

    /**
     * Scenario six for the POC.
     * Goal:    Finish an intervention in a complex structure
     * Context: Complex intervention structure (with 3 phases, each with 3 blocks, each with 3 activity)
     * Send: ClientEvent FINISH
     * Verify: The status of the JSON response is set to success
     *         Every activity with passing entry/exit condition should be FINISHED
     *         The intervention should be FINISHED
     */
    @Test
    @Order(6)
    void testProofOfConceptScenarioSix() {
        //Create recipes with entry and exit conditions set to the right value for this scenario
        List<Interaction> interactionRecipes = bciRecipeFactory.createBciInteractions(27, true, true);
        List<BehaviorChangeInterventionBlock> blockRecipes = bciRecipeFactory.createBciBlocks(9, true, true);
        List<BehaviorChangeInterventionPhase> phaseRecipes = bciRecipeFactory.createBciPhases(3, true, true);
        BehaviorChangeIntervention bciRecipe = bciRecipeFactory.createBciIntervention(true, true);

        //Create the instances
        BehaviorChangeInterventionInstance bciInstance = bciInstanceFactory.createBCIInstanceFromRecipes
                (3, bciRecipe, phaseRecipes, blockRecipes, interactionRecipes);

        //Create and handle the client event
        ClientEventResponse response = null;
        for (BehaviorChangeInterventionPhaseInstance phaseInstance : bciInstance.getActivities()) {
            BehaviorChangeInterventionBlockInstance blockInstance = phaseInstance.getActivities().getFirst();
            BCIActivityInstance activityInstance = blockInstance.getActivities().getFirst();

            BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(ClientEvent.FINISH,
                    activityInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
            response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);
        }


        //Fetch and get the instances
        bciInstance = bciInstanceService.findById(bciInstance.getId());
        BehaviorChangeInterventionPhaseInstance phaseInstance = bciInstance.getCurrentPhase();
        BehaviorChangeInterventionBlockInstance blockInstance = phaseInstance.getActivities().getFirst();
        BCIActivityInstance activityInstance = blockInstance.getActivities().getFirst();

        //Asserts
        assertNotNull(response);
        Assertions.assertTrue(response.isSuccess());
        assertEquals(ExecutionStatus.FINISHED, activityInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, blockInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.FINISHED, bciInstance.getStatus());
    }

    /**
     * Scenario seven for the POC.
     * Goal:    Finish a block in a mildly complex structure
     * Context: Mildly complex intervention structure (with 2 phases, each with 2 blocks, each with 2 activity)
     * Send:    ClientEvent FINISH
     * Verify:  The status of the JSON response is set to success
     *          One bci activity and the (old) currentBlock of the 1st phase should be finished.
     *          All bci activities of the new currentBlock, as well as the new currentBlock itself, should be IN_PROGRESS
     *
     */
    @Test
    @Order(7)
    void testProofOfConceptScenarioSeven() {
        //Create recipes with entry and exit conditions set to the right value for this scenario
        List<Interaction> interactionRecipes = bciRecipeFactory.createBciInteractions(8, true, true);
        List<BehaviorChangeInterventionBlock> blockRecipes = bciRecipeFactory.createBciBlocks(4, true, true);
        List<BehaviorChangeInterventionPhase> phaseRecipesFail = bciRecipeFactory.createBciPhases(1, true, false);
        List<BehaviorChangeInterventionPhase> phaseRecipesSuccess = bciRecipeFactory.createBciPhases(1, true, true);
        List<BehaviorChangeInterventionPhase> phaseRecipes = new ArrayList<>(phaseRecipesFail);
        phaseRecipes.addAll(phaseRecipesSuccess);
        BehaviorChangeIntervention bciRecipe = bciRecipeFactory.createBciIntervention(true, true);

        //Create the instances
        BehaviorChangeInterventionInstance bciInstance = bciInstanceFactory.createBCIInstanceFromRecipes
                (2, bciRecipe, phaseRecipes, blockRecipes, interactionRecipes);

        //Create and handle the client event
        BehaviorChangeInterventionPhaseInstance phaseInstance = bciInstance.getCurrentPhase();
        BehaviorChangeInterventionBlockInstance blockInstance = phaseInstance.getCurrentBlock();
        BCIActivityInstance activityInstance = blockInstance.getActivities().getFirst();

        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(ClientEvent.FINISH,
                activityInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        //Fetch and get the instances
        bciInstance = bciInstanceService.findById(bciInstance.getId());
        phaseInstance = bciInstance.getCurrentPhase();
        BehaviorChangeInterventionBlockInstance oldCurrentBlock = phaseInstance.getActivities().getFirst();
        BehaviorChangeInterventionBlockInstance newCurrentBlock = phaseInstance.getCurrentBlock();

        //Asserts
        Assertions.assertTrue(response.isSuccess());
        assertEquals(ExecutionStatus.FINISHED, oldCurrentBlock.getActivities().getFirst().getStatus());
        assertEquals(ExecutionStatus.FINISHED, oldCurrentBlock.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, newCurrentBlock.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, newCurrentBlock.getActivities().getFirst().getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, newCurrentBlock.getActivities().get(1).getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getStatus());
    }

    /**
     * Scenario eight for the POC.
     * Goal: When changing to a new currentBlock, test if a bci activity with entry condition that fail do not change status
     * Context: Mildly complex intervention structure (with 2 phases, each with 2 blocks, each with 2 activity)
     * Send: ClientEvent FINISH
     * Verify: The status of the JSON response is set to success
     *         One bci activity and the (old) currentBlock of the 1st phase should be finished.
     *         Only one bci activity of the new currentBlock, as well as the new currentBlock itself, should be IN_PROGRESS
     *         The other bci activity should be READY
     *
     */
    @Test
    @Order(8)
    void testProofOfConceptScenarioEight() {
        //Create recipes with entry and exit conditions set to the right value for this scenario
        List<Interaction> interactionRecipesSuccess = bciRecipeFactory.createBciInteractions(3, true, true);
        List<Interaction> interactionRecipesFail = bciRecipeFactory.createBciInteractions(5, false, true);
        List<Interaction> interactionRecipes = new ArrayList<>(interactionRecipesSuccess);
        interactionRecipes.addAll(interactionRecipesFail);

        List<BehaviorChangeInterventionBlock> blockRecipes = bciRecipeFactory.createBciBlocks(4, true, true);
        List<BehaviorChangeInterventionPhase> phaseRecipesFail = bciRecipeFactory.createBciPhases(1, true, false);
        List<BehaviorChangeInterventionPhase> phaseRecipesSuccess = bciRecipeFactory.createBciPhases(1, true, true);
        List<BehaviorChangeInterventionPhase> phaseRecipes = new ArrayList<>(phaseRecipesFail);
        phaseRecipes.addAll(phaseRecipesSuccess);
        BehaviorChangeIntervention bciRecipe = bciRecipeFactory.createBciIntervention(true, true);

        //Create the instances
        BehaviorChangeInterventionInstance bciInstance = bciInstanceFactory.createBCIInstanceFromRecipes
                (2, bciRecipe, phaseRecipes, blockRecipes, interactionRecipes);

        //Create and handle the client event
        BehaviorChangeInterventionPhaseInstance phaseInstance = bciInstance.getCurrentPhase();
        BehaviorChangeInterventionBlockInstance blockInstance = phaseInstance.getCurrentBlock();
        BCIActivityInstance activityInstance = blockInstance.getActivities().getFirst();

        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(ClientEvent.FINISH,
                activityInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        //Fetch and get the instances
        bciInstance = bciInstanceService.findById(bciInstance.getId());
        phaseInstance = bciInstance.getCurrentPhase();
        BehaviorChangeInterventionBlockInstance oldCurrentBlock = phaseInstance.getActivities().getFirst();
        BehaviorChangeInterventionBlockInstance newCurrentBlock = phaseInstance.getCurrentBlock();

        //Asserts
        Assertions.assertTrue(response.isSuccess());
        assertEquals(ExecutionStatus.FINISHED, oldCurrentBlock.getActivities().getFirst().getStatus());
        assertEquals(ExecutionStatus.FINISHED, oldCurrentBlock.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, newCurrentBlock.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, newCurrentBlock.getActivities().getFirst().getStatus());
        assertEquals(ExecutionStatus.READY, newCurrentBlock.getActivities().get(1).getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, phaseInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getStatus());
    }

    /**
     * Scenario nine for the POC.
     * Goal: When a new activity is set to IN_PROGRESS, its entryDate should be set to a value.
     * Context: Mildly complex intervention structure (with 2 phases, each with 2 blocks, each with 2 activities)
     * Send: ClientEvent FINISH
     * Verify: The entryDate of the new currentPhase, the new currentBlock, and its activities with passing
     *         entry conditions was set to a value.
     *
     */
    @Test
    @Order(9)
    void testProofOfConceptScenarioNine() {
        //Create recipes with entry and exit conditions set to the right value for this scenario
        List<Interaction> interactionRecipes = bciRecipeFactory.createBciInteractions(8, true, true);
        List<BehaviorChangeInterventionBlock> blockRecipes = bciRecipeFactory.createBciBlocks(4, true, true);
        List<BehaviorChangeInterventionPhase> phaseRecipes = bciRecipeFactory.createBciPhases(2, true, true);
        BehaviorChangeIntervention bciRecipe = bciRecipeFactory.createBciIntervention(true, true);

        //Create the instances
        BehaviorChangeInterventionInstance bciInstance = bciInstanceFactory.createBCIInstanceFromRecipes
                (2, bciRecipe, phaseRecipes, blockRecipes, interactionRecipes);

        //Create and handle the client event
        BehaviorChangeInterventionPhaseInstance phaseInstance = bciInstance.getActivities().getFirst();
        BehaviorChangeInterventionBlockInstance blockInstance = phaseInstance.getActivities().getFirst();
        BCIActivityInstance activityInstance = blockInstance.getActivities().getFirst();

        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(ClientEvent.FINISH,
                activityInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        //Fetch and get the instances
        bciInstance = bciInstanceService.findById(bciInstance.getId());

        //Asserts
        Assertions.assertTrue(response.isSuccess());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getCurrentPhase().getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getCurrentPhase().getCurrentBlock().getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getCurrentPhase().getCurrentBlock().getActivities().getFirst().getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, bciInstance.getCurrentPhase().getCurrentBlock().getActivities().get(1).getStatus());

        assertNotNull(bciInstance.getCurrentPhase().getEntryDate());
        assertNotNull(bciInstance.getCurrentPhase().getCurrentBlock().getEntryDate());
        assertNotNull(bciInstance.getCurrentPhase().getCurrentBlock().getActivities().getFirst());
        assertNotNull(bciInstance.getCurrentPhase().getCurrentBlock().getActivities().get(1));
    }
}