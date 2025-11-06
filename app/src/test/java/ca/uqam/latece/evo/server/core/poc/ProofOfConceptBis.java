package ca.uqam.latece.evo.server.core.poc;

import ca.uqam.latece.evo.server.core.config.EvoDataLoader;
import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.event.BCIInstanceClientEvent;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.poc.factoryBis.actors.PatientTestFactory;
import ca.uqam.latece.evo.server.core.poc.factoryBis.actors.RoleTestFactory;
import ca.uqam.latece.evo.server.core.poc.factoryBis.instances.BCIActivityInstanceTestFactory;
import ca.uqam.latece.evo.server.core.poc.factoryBis.instances.BCIBlocInstanceTestFactory;
import ca.uqam.latece.evo.server.core.poc.factoryBis.instances.BCIInstanceTestFactory;
import ca.uqam.latece.evo.server.core.poc.factoryBis.instances.BCIPhaseInstanceTestFactory;
import ca.uqam.latece.evo.server.core.poc.factoryBis.recipes.BCIActivityRecipeTestFactory;
import ca.uqam.latece.evo.server.core.poc.factoryBis.recipes.BCIBlocRecipeTestFactory;
import ca.uqam.latece.evo.server.core.poc.factoryBis.recipes.BCIPhaseRecipeTestFactory;
import ca.uqam.latece.evo.server.core.poc.factoryBis.recipes.BCIRecipeTestFactory;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProofOfConceptBis extends EvoDataLoader {

    @Autowired
    private PatientTestFactory patientTestFactory;

    @Autowired
    private RoleTestFactory roleTestFactory;

    @Autowired
    private BCIActivityRecipeTestFactory bciActivityRecipeTestFactory;

    @Autowired
    private BCIBlocRecipeTestFactory bciBlocRecipeTestFactory;

    @Autowired
    private BCIPhaseRecipeTestFactory bciPhaseRecipeTestFactory;

    @Autowired
    private BCIRecipeTestFactory bciRecipeTestFactory;

    @Autowired
    private BCIActivityInstanceTestFactory bciActivityInstanceTestFactory;

    @Autowired
    private BCIBlocInstanceTestFactory bciBlocInstanceTestFactory;

    @Autowired
    private BCIPhaseInstanceTestFactory bciPhaseInstanceTestFactory;

    @Autowired
    private BCIInstanceTestFactory bciInstanceTestFactory;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final Long ID_3 = 3L;
    private static final Long ID_4 = 4L;
    private static final int SIZE_1 = 1;
    private static final int SIZE_2 = 2;
    private static final int INDEX_0 = 0;
    private static final int INDEX_1 = 1;

    @Test
    @Order(1)
    void testFetchRecipesDatasFromDBAndCreateInstancesWithSave(){

        //1.Arrange

        //Fetch actors

        Patient patient = patientTestFactory.getPatient();
        Role role = roleTestFactory.getRoleService();

        //Fetch Recipes

        BCIActivity activtyRecipe = bciActivityRecipeTestFactory.getFirstReceipeWithTrueConditions();
        BehaviorChangeInterventionBlock blocRecipe = bciBlocRecipeTestFactory.getBlocRecipeWithTrueConditions();
        BehaviorChangeInterventionPhase phaseRecipe = bciPhaseRecipeTestFactory.getPhaseRecipeWithTrueConditions();
        BehaviorChangeIntervention interventionRecipe = bciRecipeTestFactory.getRecipe();

        //2.Act and Assert

        //Create,Save instances and assert IDs
        BCIActivityInstance expectedActivyInstance = bciActivityInstanceTestFactory.getActivityInstance(patient, role, activtyRecipe);
        assertTrue(expectedActivyInstance.getId() > 0);

        BehaviorChangeInterventionBlockInstance expectedBlocInstance = bciBlocInstanceTestFactory.getBlockInstance(blocRecipe,expectedActivyInstance);
        assertTrue(expectedBlocInstance.getId() > 0);

        BehaviorChangeInterventionPhaseInstance expectedPhaseInstance = bciPhaseInstanceTestFactory.getPhaseInstance(phaseRecipe,expectedBlocInstance);
        assertTrue(expectedPhaseInstance.getId() > 0);

        BehaviorChangeInterventionInstance expectedInterventionInstance = bciInstanceTestFactory.getIntervention(interventionRecipe, patient,expectedPhaseInstance);
        assertTrue(expectedInterventionInstance.getId() > 0);

    }


    @Disabled
    @Test
    @Order(2)
    void testHandleBCIInstanceClientEventFinishedFirstBlocAndTriggerSecondOne() {

        //Arrange

        //1. Actors
        Patient patient = patientTestFactory.getPatient();
        Role role = roleTestFactory.getRoleService();

        //2. Activities
        BCIActivityInstance activityInstance1 = bciActivityInstanceTestFactory.getFirstActivityWithTrueConditions(patient,role);
        BCIActivityInstance activityInstance2 = bciActivityInstanceTestFactory.getSecondActivityWithTrueConditions(patient,role);
        BCIActivityInstance ActivityInstance3 = bciActivityInstanceTestFactory.getThirdActivityWithTrueConditions(patient,role);

        //3. Blocks
        BehaviorChangeInterventionBlockInstance blockInstance1 = bciBlocInstanceTestFactory.getBlocWithTrueConditions(activityInstance1);
        BehaviorChangeInterventionBlockInstance blockInstance2 = bciBlocInstanceTestFactory.getBlocWithTrueConditions(activityInstance2,ActivityInstance3);

        //4. Phase
        BehaviorChangeInterventionPhaseInstance phaseInstance = bciPhaseInstanceTestFactory.getPhaseWithTrueConditions(blockInstance1,blockInstance2);

        //5. The whole intervention
        BehaviorChangeInterventionInstance bciInstance = bciInstanceTestFactory.getIntervention(patient,phaseInstance);


        //Act
        ClientEventResponse response = new ClientEventResponse();
        ClientEvent clientEvent = ClientEvent.FINISH;
        BCIInstanceClientEvent eventTrigger = new BCIInstanceClientEvent(
                clientEvent,
                response,
                bciInstance.getId(),
                phaseInstance
        );
        applicationEventPublisher.publishEvent(eventTrigger);


        //Assert
        assertEquals(ExecutionStatus.FINISHED, activityInstance1.getStatus());
        assertEquals(ExecutionStatus.FINISHED, blockInstance1.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, activityInstance2.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, ActivityInstance3.getStatus());
        assertEquals(ExecutionStatus.IN_PROGRESS, blockInstance2.getStatus());
        assertTrue(response.isSuccess());

    }

    @Test
    @Order(3)
    void testInterventionInstanceScenarioThreeLoadData() {
        BehaviorChangeInterventionInstance bciInstance = bciInstanceTestFactory
                .getInterventionWithTwoPhases_Phase1Has1BlockAnd1Activity_Phase2Has2BlocksAnd2Activities();

        assertNotNull(bciInstance);
    }

    /**
     * This test was disabled because has a bug in the event implementation.
     */
    @Disabled
    @Test
    @Order(4)
    void testInterventionInstanceScenarioThree() {
        BehaviorChangeInterventionInstance bciInstance = bciInstanceTestFactory
                .getInterventionWithTwoPhases_Phase1Has1BlockAnd1Activity_Phase2Has2BlocksAnd2Activities();

        assertNotNull(bciInstance);

        ClientEventResponse response = bciInstanceTestFactory.checkIntervention(bciInstance);

        //Fetch updated instances
        BehaviorChangeInterventionInstance bciInstanceUpdated = bciInstanceTestFactory.getInterventionById(bciInstance.getId());
        BehaviorChangeInterventionPhaseInstance phaseInstanceUpdated = bciInstanceUpdated.getActivities().getFirst();
        BehaviorChangeInterventionBlockInstance blockInstanceUpdated = phaseInstanceUpdated.getActivities().getFirst();
        BCIActivityInstance activityInstanceUpdated = blockInstanceUpdated.getActivities().getFirst();

        //Assert intervention is finished
        Assertions.assertTrue(response.isSuccess());
        assertEquals(ExecutionStatus.FINISHED, activityInstanceUpdated.getStatus());
        assertEquals(ExecutionStatus.FINISHED, blockInstanceUpdated.getStatus());
        assertEquals(ExecutionStatus.FINISHED, phaseInstanceUpdated.getStatus());
        assertEquals(ExecutionStatus.FINISHED, bciInstanceUpdated.getStatus());

    }

    @Test
    @Order(5)
    void testInterventionInstanceScenarioThreeDataVerification() {
        // Gets the intervention 2.
        BehaviorChangeIntervention intervention = bciRecipeTestFactory.getBehaviorChangeInterventionById(ID_2);

        // Gets intervention phases.
        List<BehaviorChangeInterventionPhase> phases = intervention.getBehaviorChangeInterventionPhases();
        BehaviorChangeInterventionPhase firstPhase = findPhaseByIndex(phases, INDEX_0);
        BehaviorChangeInterventionPhase secondPhase = findPhaseByIndex(phases, INDEX_1);

        // Gets intervention blocks of the first phase.
        List<BehaviorChangeInterventionBlock> blocksOfFirstPhase = firstPhase.getBehaviorChangeInterventionBlocks();

        // Gets composed of list of the first block of the first phase.
        List<ComposedOf> composedOfBlocksPhase1 = blocksOfFirstPhase.stream()
                .map(BehaviorChangeInterventionBlock::getComposedOfList)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected composedOf list for first block of first phase!"));

        // Gets activities in composed of list of the first block of the first phase.
        List<BCIActivity> activitiesInComposedOfBlocksPhase1 = composedOfBlocksPhase1.stream()
                .map(ComposedOf::getBciActivity)
                .toList();
        // Gets activity with id 1 in composed of list of the first block of the first phase.
        BCIActivity activityWithId1InPhase1 = findActivityById(activitiesInComposedOfBlocksPhase1, ID_1);

        List<BehaviorChangeInterventionBlock> blocksOfSecondPhase = secondPhase.getBehaviorChangeInterventionBlocks();
        BehaviorChangeInterventionBlock firstBlockPhase2 = firstBlock(blocksOfSecondPhase);
        List<ComposedOf> composedOfBlock1Phase2 = firstBlockPhase2.getComposedOfList();
        List<BCIActivity> activitiesInBlock1Phase2 = composedOfBlock1Phase2.stream()
                .map(ComposedOf::getBciActivity)
                .toList();
        BCIActivity activityWithId2InBlock1Phase2 = findActivityById(activitiesInBlock1Phase2, ID_2);

        BehaviorChangeInterventionBlock secondBlockPhase2 = findPhaseByIndex(blocksOfSecondPhase, INDEX_1);
        List<ComposedOf> composedOfBlock2Phase2 = secondBlockPhase2.getComposedOfList();
        List<BCIActivity> activitiesInBlock2Phase2 = composedOfBlock2Phase2.stream()
                .map(ComposedOf::getBciActivity)
                .toList();
        BCIActivity activityWithId3InBlock2Phase2 = findActivityById(activitiesInBlock2Phase2, ID_3);
        BCIActivity activityWithId4InBlock2Phase2 = findActivityById(activitiesInBlock2Phase2, ID_4);

        assertNotNull(intervention);
        assertEquals(SIZE_2, phases.size());
        assertEquals(SIZE_1, blocksOfFirstPhase.size());
        assertEquals(SIZE_1, composedOfBlocksPhase1.size());
        assertEquals(SIZE_1, activitiesInComposedOfBlocksPhase1.size());
        assertEquals(ID_1, activityWithId1InPhase1.getId());
        assertEquals(SIZE_2, blocksOfSecondPhase.size());
        assertEquals(SIZE_1, composedOfBlock1Phase2.size());
        assertEquals(SIZE_1, activitiesInBlock1Phase2.size());
        assertEquals(ID_2, activityWithId2InBlock1Phase2.getId());
        assertEquals(SIZE_2, composedOfBlock2Phase2.size());
        assertEquals(SIZE_2, activitiesInBlock2Phase2.size());
        assertEquals(ID_3, activityWithId3InBlock2Phase2.getId());
        assertEquals(ID_4, activityWithId4InBlock2Phase2.getId());
    }

    /**
     * Finds and retrieves the element at the specified index from a given list.
     * Throws an AssertionError if the list is null or if the specified index is out of bounds.
     * @param <T> the type of elements in the list.
     * @param list the list from which to retrieve the element.
     * @param index the index of the element to retrieve.
     * @return the element at the specified index in the list.
     * @throws AssertionError if the list is null or the index is out of bounds.
     */
    private <T> T findPhaseByIndex(List<T> list, int index) {
        if (list == null || list.size() <= index) {
            throw new AssertionError("Expected element at index " + index + " but list was null or too small: " + list);
        }
        return list.get(index);
    }

    /**
     * Retrieves the first element of the provided list.
     * Throws an AssertionError if the list is null or empty.
     * @param <T> the type of elements in the list
     * @param list the list from which to retrieve the first element
     * @return the first element of the list
     * @throws AssertionError if the list is null or empty
     */
    private <T> T firstBlock(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new AssertionError("Expected at least one block but none found!");
        }
        return list.getFirst();
    }

    /**
     * Finds and retrieves a BCIActivity object from a given list of BCIActivity objects based on the specified ID.
     * Throws an AssertionError if no activity with the given ID exists in the list.
     * @param activities the list of BCIActivity objects to search through
     * @param id the ID of the activity to find
     * @return the BCIActivity object with the specified ID
     * @throws AssertionError if no activity with the specified ID is found in the list
     */
    private BCIActivity findActivityById(List<BCIActivity> activities, long id) {
        return activities.stream()
                .filter(bci -> bci.getId() != null && bci.getId() == id)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected activity with id " + id + " in list: " + activities));
    }

}
