package ca.uqam.latece.evo.server.core.poc.factory;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.service.RoleService;
import ca.uqam.latece.evo.server.core.service.instance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BCIInstanceFactory {

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
    private InteractionInstanceService interactionInstanceService;

    @Autowired
    private BehaviorChangeInterventionBlockInstanceService bciBlockInstanceService;

    @Autowired
    private BehaviorChangeInterventionPhaseInstanceService bciPhaseInstanceService;

    @Autowired
    private BehaviorChangeInterventionInstanceService bciInstanceService;

    private Role roleHealthCareProfessional;

    private Role rolePatient;

    private PatientMedicalFile patientMedicalFile;

    private Patient patient;

    private HealthCareProfessional healthCareProfessional;

    private BCIInstanceFactory() {}

    public Patient createPatient() {
        if (patientMedicalFile == null && patient == null) {
            patientMedicalFile = patientMedicalFileService.create(new PatientMedicalFile("Heavy smoker"));
            patient = patientService.create(new Patient("Bob Ross", "bobross@gmail.com", "722-5222", "October 29, 1942",
                    "Therapist Painter", "123 Paint Ave", patientMedicalFile));
        }

        return patient;
    }

    public HealthCareProfessional createHealthCareProfessional() {
        if (healthCareProfessional == null) {
            healthCareProfessional = healthCareProfessionalService.create(new HealthCareProfessional("Jack Black", "jblack@gmail.com", "222-2222",
                    "Teacher", "Rock university", "Roadie"));
        }

        return healthCareProfessional;
    }

    public Participant createParticipantHealthCareProfessional(HealthCareProfessional hcp) {
        if (roleHealthCareProfessional == null) {
            roleHealthCareProfessional = roleService.create(new Role("Health Care Professional"));
        }

        return participantService.create(new Participant(roleHealthCareProfessional, hcp));
    }

    public Participant createParticipantPatient(Patient patient) {
        if (rolePatient == null) {
            rolePatient = roleService.create(new Role("Patient"));
        }

        return participantService.create(new Participant(rolePatient, patient));
    }

    public InteractionInstance createInteractionInstance(ExecutionStatus status,
                                                         Interaction recipe) {
        List<Participant> participants = new ArrayList<>();
        participants.add(createParticipantPatient(createPatient()));

        return interactionInstanceService.create(new InteractionInstance(status, participants, recipe));
    }

    public InteractionInstance createInteractionInstance(ExecutionStatus status,
                                                         List<Actor> actors,
                                                         Interaction recipe) {
        List<Participant> participants = new ArrayList<>();
        for (Actor actor : actors) {
            Participant participant = null;

            if (actor.getClass().equals(HealthCareProfessional.class)) {
                participant = createParticipantHealthCareProfessional((HealthCareProfessional) actor);
            } else if (actor.getClass().equals(Patient.class)) {
                participant = createParticipantPatient((Patient) actor);
            }

            participants.add(participant);
        }

        return interactionInstanceService.create(new InteractionInstance(status, participants, recipe));
    }

    public BehaviorChangeInterventionBlockInstance createBCIBlockInstance(ExecutionStatus status,
                                                                          List<BCIActivityInstance> activities,
                                                                          BehaviorChangeInterventionBlock recipe) {
        return bciBlockInstanceService.create(new BehaviorChangeInterventionBlockInstance
                (status, TimeCycle.BEGINNING, activities, recipe));
    }

    public BehaviorChangeInterventionPhaseInstance createBCIPhaseInstance(ExecutionStatus status,
                                                                          List<BehaviorChangeInterventionBlockInstance> activities,
                                                                          BehaviorChangeInterventionBlockInstance currentBlock,
                                                                          BehaviorChangeInterventionPhase recipe) {
        List<BCIModuleInstance> moduleInstances = new ArrayList<>();
        return bciPhaseInstanceService.create(new BehaviorChangeInterventionPhaseInstance
                (status, currentBlock, activities, moduleInstances, recipe));
    }

    public BehaviorChangeInterventionInstance createBCIInstance(ExecutionStatus status,
                                                                Patient patient,
                                                                BehaviorChangeInterventionPhaseInstance currentPhase,
                                                                List<BehaviorChangeInterventionPhaseInstance> activities,
                                                                BehaviorChangeIntervention recipe) {
        return bciInstanceService.create(new BehaviorChangeInterventionInstance
                (status, patient, currentPhase, activities, recipe));
    }

    public BehaviorChangeInterventionInstance createBCIInstance(ExecutionStatus status,
                                                                BehaviorChangeInterventionPhaseInstance currentPhase,
                                                                List<BehaviorChangeInterventionPhaseInstance> activities,
                                                                BehaviorChangeIntervention recipe) {
        Patient patient = createPatient();
        return bciInstanceService.create(new BehaviorChangeInterventionInstance
                (status, patient, currentPhase, activities, recipe));
    }

    /**
     * Creates an intervention from recipes.
     * The execution of the 1st set of activities created has their execution status set to IN_PROGRESS, the others to READY.
     *
     * @param bciRecipe Recipe for intervention
     * @param phaseRecipes Recipes for the phases
     * @param blockRecipes Recipes for the blocks
     * @param interactionRecipes Recipes for the interactions
     * @param amountActivitiesPerInstance How many activity per instance should be created
     * @return the instance of the intervention
     */
    public BehaviorChangeInterventionInstance createBCIInstanceFromRecipes(int amountActivitiesPerInstance,
                                                                           BehaviorChangeIntervention bciRecipe,
                                                                           List<BehaviorChangeInterventionPhase> phaseRecipes,
                                                                           List<BehaviorChangeInterventionBlock> blockRecipes,
                                                                           List<Interaction> interactionRecipes) {
        List<BehaviorChangeInterventionPhaseInstance> phaseActivities = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        ExecutionStatus phaseStatus = ExecutionStatus.IN_PROGRESS;
        ExecutionStatus blockStatus = ExecutionStatus.IN_PROGRESS;
        ExecutionStatus activityStatus = ExecutionStatus.IN_PROGRESS;
        Patient patient = createPatient();
        actors.add(patient);

        for (int countPhase = 0; countPhase < amountActivitiesPerInstance; countPhase++) {
            List<BehaviorChangeInterventionBlockInstance> blockActivities = new ArrayList<>();
            if (countPhase > 0) {phaseStatus = ExecutionStatus.READY;}

            int maxBlock = amountActivitiesPerInstance + (amountActivitiesPerInstance * countPhase);
            for (int countBlock = amountActivitiesPerInstance * countPhase; countBlock < maxBlock; countBlock++) {
                List<BCIActivityInstance> bciActivities = new ArrayList<>();
                if (countBlock > 0) {blockStatus = ExecutionStatus.READY;}

                int maxActivity = amountActivitiesPerInstance + (amountActivitiesPerInstance * countBlock);
                for (int countActivity = amountActivitiesPerInstance * countBlock; countActivity < maxActivity; countActivity++) {
                    Interaction recipe = interactionRecipes.get(countActivity);
                    if (countBlock > 0 || recipe.getPreconditions().equals("x->false")) {activityStatus = ExecutionStatus.READY;}

                    InteractionInstance interactionInstance = createInteractionInstance(activityStatus, actors, recipe);
                    if (activityStatus == ExecutionStatus.IN_PROGRESS) {interactionInstance.setEntryDate(LocalDate.now());}
                    bciActivities.add(interactionInstance);
                }

                BehaviorChangeInterventionBlockInstance blockInstance = createBCIBlockInstance(blockStatus, bciActivities, blockRecipes.get(countBlock));
                if (blockStatus == ExecutionStatus.IN_PROGRESS) {blockInstance.setEntryDate(LocalDate.now());}
                blockActivities.add(blockInstance);
            }

            BehaviorChangeInterventionPhaseInstance phaseInstance = createBCIPhaseInstance(phaseStatus, blockActivities,
                    blockActivities.getFirst(), phaseRecipes.get(countPhase));
            if (phaseStatus == ExecutionStatus.IN_PROGRESS) {phaseInstance.setEntryDate(LocalDate.now());}
            phaseActivities.add(phaseInstance);
        }

        return createBCIInstance(ExecutionStatus.IN_PROGRESS, patient, phaseActivities.getFirst(), phaseActivities, bciRecipe);
    }
}
