package ca.uqam.latece.evo.server.core.poc.factory;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.*;

import java.util.ArrayList;
import java.util.List;

public class BCIInstanceFactory {

    private BCIInstanceFactory() {}

    private static final BCIInstanceFactory factory = new BCIInstanceFactory();

    private final Role roleHealthCareProfessional = new Role("Health Care Professional");

    private final Role rolePatient = new Role("Patient");

    public static BCIInstanceFactory getInstance() {
        return factory;
    }

    public Patient createPatient() {
        PatientMedicalFile patientMedicalFile = new PatientMedicalFile("Heavy smoker");
        return new Patient("Bob Ross", "bobross@gmail.com", "722-5222", "October 29, 1942",
                "Therapist Painter", "123 Paint Ave", patientMedicalFile);
    }

    public Participant createParticipantHealthCareProfessional(Actor actor) {
        return new Participant(roleHealthCareProfessional, actor);
    }

    public Participant createParticipantPatient(Actor actor) {
        return new Participant(rolePatient, actor);
    }

    public InteractionInstance createInteractionInstance(Interaction recipe) {
        List<Participant> participants = new ArrayList<>();
        participants.add(createParticipantPatient(createPatient()));

        return new InteractionInstance(ExecutionStatus.READY, participants, recipe);
    }

    public InteractionInstance createInteractionInstance(List<Actor> actors,
                                                         Interaction recipe) {
        List<Participant> participants = new ArrayList<>();
        for (Actor actor : actors) {
            Participant participant = null;

            if (actor.getClass().equals(HealthCareProfessional.class)) {
                participant = createParticipantHealthCareProfessional(actor);
            } else {
                participant = createParticipantPatient(actor);
            }

            participants.add(participant);
        }

        return new InteractionInstance(ExecutionStatus.READY, participants, recipe);
    }

    public BehaviorChangeInterventionBlockInstance createBCIBlockInstance(List<BCIActivityInstance> activities,
                                                                          BehaviorChangeInterventionBlock recipe) {
        return new BehaviorChangeInterventionBlockInstance(ExecutionStatus.READY, TimeCycle.BEGINNING, activities, recipe);
    }

    public BehaviorChangeInterventionPhaseInstance createBCIPhaseInstance(List<BehaviorChangeInterventionBlockInstance> activities,
                                                                          BehaviorChangeInterventionBlockInstance currentBlock,
                                                                          BehaviorChangeInterventionPhase recipe) {
        List<BCIModuleInstance> moduleInstances = new ArrayList<>();
        return new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.READY, currentBlock, activities, moduleInstances, recipe);
    }

    public BehaviorChangeInterventionInstance createBCIInstance(Patient patient,
                                                                BehaviorChangeInterventionPhaseInstance currentPhase,
                                                                List<BehaviorChangeInterventionPhaseInstance> activities,
                                                                BehaviorChangeIntervention recipe) {
        return new BehaviorChangeInterventionInstance(ExecutionStatus.IN_PROGRESS, patient, currentPhase, activities, recipe);
    }

    public BehaviorChangeInterventionInstance createBCIInstance(BehaviorChangeInterventionPhaseInstance currentPhase,
                                                                List<BehaviorChangeInterventionPhaseInstance> activities,
                                                                BehaviorChangeIntervention recipe) {
        Patient patient = createPatient();
        return new BehaviorChangeInterventionInstance(ExecutionStatus.IN_PROGRESS, patient, currentPhase, activities, recipe);
    }

    /**
     * Creates an intervention from recipes.
     *
     * @param bciRecipe Recipes for intervention
     * @param phaseRecipes Recipes for the phase
     * @param blockRecipes Recipes for the block
     * @param activityRecipes Recipes for the bciActivity
     * @return the instance of the intervention
     */
    public BehaviorChangeInterventionInstance createBCIInstanceFromRecipes(BehaviorChangeIntervention bciRecipe,
                                                                           List<BehaviorChangeInterventionPhase> phaseRecipes,
                                                                           List<BehaviorChangeInterventionBlock> blockRecipes,
                                                                           List<BCIActivity> activityRecipes) {
        //Créer une intenvention au complet à partir de plusieurs recettes
        //Pas certain comment attribuer quels block dans quelle phase et quelles activités dans quel block
        return null;
    }

    /**
     * Creates an intervention from recipes.
     * This intervention will only have 1 activity per ActivityInstance (1 phase, 1 block, and 1 bciActivity).
     * The execution status of all ActivityInstance will be set to IN_PROGRESS.
     *
     * @param bciRecipe Recipe for intervention
     * @param phaseRecipe Recipe for the phase
     * @param blockRecipe Recipe for the block
     * @param interactionRecipe Recipe for the interaction
     * @return the instance of the intervention
     */
    public BehaviorChangeInterventionInstance createBCIInstanceFromRecipesOneActivityPerInstance(BehaviorChangeIntervention bciRecipe,
                                                                                                 BehaviorChangeInterventionPhase phaseRecipe,
                                                                                                 BehaviorChangeInterventionBlock blockRecipe,
                                                                                                 Interaction interactionRecipe) {
        Patient patient = createPatient();
        List<Actor> actors = new ArrayList<>();
        actors.add(patient);

        InteractionInstance activityInstance = createInteractionInstance(actors, interactionRecipe);
        activityInstance.setStatus(ExecutionStatus.IN_PROGRESS);
        List<BCIActivityInstance> bciActivities = new ArrayList<>();
        bciActivities.add(activityInstance);

        BehaviorChangeInterventionBlockInstance blockInstance = createBCIBlockInstance(bciActivities, blockRecipe);
        blockInstance.setStatus(ExecutionStatus.IN_PROGRESS);
        List<BehaviorChangeInterventionBlockInstance> blockActivities = new ArrayList<>();
        blockActivities.add(blockInstance);

        BehaviorChangeInterventionPhaseInstance phaseInstance = createBCIPhaseInstance(blockActivities, blockInstance, phaseRecipe);
        phaseInstance.setStatus(ExecutionStatus.IN_PROGRESS);
        List<BehaviorChangeInterventionPhaseInstance> phaseActivities = new ArrayList<>();
        phaseActivities.add(phaseInstance);

        return createBCIInstance(patient, phaseInstance, phaseActivities, bciRecipe);
    }
}
