package ca.uqam.latece.evo.server.core.poc;

import ca.uqam.latece.evo.server.core.config.EvoDataLoader;
import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.event.BCIActivityClientEvent;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.poc.factory.BCIInstanceFactory;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.service.*;
import ca.uqam.latece.evo.server.core.service.instance.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProofOfConcept extends EvoDataLoader {

    private final BCIInstanceFactory bciInstanceFactory = BCIInstanceFactory.getInstance();

    private static Long testOneBCIInstanceId;

    @Autowired
    private ActorService actorService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientMedicalFileService patientMedicalFileService;

    @Autowired
    private HealthCareProfessionalService healthCareProfessionalService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private InteractionService interactionService;

    @Autowired
    private BehaviorChangeInterventionBlockService behaviorChangeInterventionBlockService;

    @Autowired
    private BehaviorChangeInterventionPhaseService behaviorChangeInterventionPhaseService;

    @Autowired
    private BehaviorChangeInterventionService behaviorChangeInterventionService;

    @Autowired
    private InteractionInstanceService interactionInstanceService;

    @Autowired
    private BehaviorChangeInterventionBlockInstanceService behaviorChangeInterventionBlockInstanceService;

    @Autowired
    private BehaviorChangeInterventionPhaseInstanceService behaviorChangeInterventionPhaseInstanceService;

    @Autowired
    private BehaviorChangeInterventionInstanceService behaviorChangeInterventionInstanceService;

    /**
     * Scenario one for the POC.
     * Goal: Create instances from recipes and saving them in the database
     */
    @Test
    @Order(1)
    void testProofOfConceptScenarioOne() {
        //Fetch recipes
        List<Interaction> interactionRecipes = interactionService.findAll();
        List<BehaviorChangeInterventionBlock> blockRecipes = behaviorChangeInterventionBlockService.findAll();
        List<BehaviorChangeInterventionPhase> phaseRecipes = behaviorChangeInterventionPhaseService.findAll();
        List<BehaviorChangeIntervention> bciRecipes = behaviorChangeInterventionService.findAll();

        //Create instances
        BehaviorChangeInterventionInstance bciInstance = bciInstanceFactory.createBCIInstanceFromRecipesOneActivityPerInstance
                (bciRecipes.getFirst(), phaseRecipes.getFirst(), blockRecipes.getFirst(), interactionRecipes.getFirst());

        //Save instances
        saveInstances(bciInstance);

        //Fetch instances
        BehaviorChangeInterventionInstance bciInstanceSaved = behaviorChangeInterventionInstanceService.findById(bciInstance.getId());
        testOneBCIInstanceId = bciInstanceSaved.getId();

        //Assert persistence
        BehaviorChangeInterventionPhaseInstance phaseInstanceSaved = bciInstanceSaved.getActivities().getFirst();
        BehaviorChangeInterventionBlockInstance blockInstanceSaved = phaseInstanceSaved.getActivities().getFirst();
        BCIActivityInstance activityInstanceSaved = blockInstanceSaved.getActivities().getFirst();

        assertNotNull(bciInstanceSaved.getId());
        assertNotNull(phaseInstanceSaved.getId());
        assertNotNull(blockInstanceSaved.getId());
        assertNotNull(activityInstanceSaved.getId());
    }

    /**
     * Scenario two for the POC.
     * Goal: Using instances created in Scenario One, send ClientEvent FINISH to intervention and verify that all instances are finished
     */
    @Test
    @Order(2)
    void testProofOfConceptScenarioTwo() {
        //Fetch instances
        BehaviorChangeInterventionInstance bciInstance = behaviorChangeInterventionInstanceService.findById(testOneBCIInstanceId);
        BehaviorChangeInterventionPhaseInstance phaseInstance = bciInstance.getActivities().getFirst();
        BehaviorChangeInterventionBlockInstance blockInstance = phaseInstance.getActivities().getFirst();
        BCIActivityInstance activityInstance = blockInstance.getActivities().getFirst();

        //Create and handle the event
        BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(ClientEvent.FINISH,
                activityInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        ClientEventResponse response = interactionInstanceService.handleClientEvent(bciActivityClientEvent);

        //Fetch updated instances
        BehaviorChangeInterventionInstance bciInstanceUpdated = behaviorChangeInterventionInstanceService.findById(testOneBCIInstanceId);
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

    /**
     * Saves all the instances found in an intervention into the database.
     * Checks if the Actors and Roles present are already persisted. If so, they will be ignored.
     *
     * @param bciInstance The intervention to be saved
     */
    private void saveInstances(BehaviorChangeInterventionInstance bciInstance) {
        for (BehaviorChangeInterventionPhaseInstance phaseInstance : bciInstance.getActivities()) {

            for (BehaviorChangeInterventionBlockInstance blockInstance : phaseInstance.getActivities()) {

                for (BCIActivityInstance activityInstance : blockInstance.getActivities()) {

                    for (Participant participant : activityInstance.getParticipants()) {
                        Role role = participant.getRole();
                        if (!roleService.existsByName(role.getName())) {
                            roleService.create(participant.getRole());
                        }

                        if (!actorService.existsByEmail(participant.getActor().getEmail())) {
                            if (participant.getActor().getClass().equals(Patient.class)) {
                                Patient patient = (Patient) participant.getActor();
                                patientMedicalFileService.create(patient.getMedicalFile());
                                patientService.create(patient);

                            } else if (participant.getActor().getClass().equals(HealthCareProfessional.class)) {
                                HealthCareProfessional healthCareProfessional = (HealthCareProfessional) participant.getActor();
                                healthCareProfessionalService.create(healthCareProfessional);
                            }
                        }

                        participantService.create(participant);
                    }

                    if(activityInstance.getClass().equals(InteractionInstance.class)) {
                        interactionInstanceService.create((InteractionInstance) activityInstance);
                    }
                }

                behaviorChangeInterventionBlockInstanceService.create(blockInstance);
            }

            behaviorChangeInterventionPhaseInstanceService.create(phaseInstance);
        }

        behaviorChangeInterventionInstanceService.create(bciInstance);
    }

}
