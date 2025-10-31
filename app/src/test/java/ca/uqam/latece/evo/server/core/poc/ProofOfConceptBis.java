package ca.uqam.latece.evo.server.core.poc;

import ca.uqam.latece.evo.server.core.config.EvoDataLoader;
import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.event.BCIInstanceClientEvent;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.*;
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



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

}
