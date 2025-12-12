package ca.uqam.latece.evo.server.core.poc.factory;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMedium;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMode;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BCIRecipeFactory {

    @Autowired
    private InteractionService interactionService;

    @Autowired
    private BehaviorChangeInterventionBlockService bciBlockService;

    @Autowired
    private BehaviorChangeInterventionPhaseService bciPhaseService;

    @Autowired
    private BehaviorChangeInterventionService bciService;

    @Autowired
    private RoleService roleService;

    private Role roleInitiator;

    private final String passCondition = "x->true";

    private final String failCondition = "x->false";

    private int numberOfInteractions = 0;

    private int numberOfInterventions = 0;

    public Interaction createInteraction(boolean entryCondition, boolean exitCondition) {
        if (roleInitiator == null) {
            roleInitiator = roleService.create(new Role("Role of the initiator"));
        }

        String entryConditionStr = entryCondition ? passCondition : failCondition;
        String exitConditionStr = exitCondition ? passCondition : failCondition;
        Interaction interaction = interactionService.create(new Interaction
                ("Interaction_" + numberOfInteractions, "Description",
                ActivityType.BCI_ACTIVITY, entryConditionStr, exitConditionStr, InteractionMode.ASYNCHRONOUS,
                roleInitiator, InteractionMedium.VIDEO));

        numberOfInteractions++;
        return interaction;
    }

    public BehaviorChangeInterventionBlock createBciBlock(boolean entryCondition, boolean exitCondition) {
        String entryConditionStr = entryCondition ? passCondition : failCondition;
        String exitConditionStr = exitCondition ? passCondition : failCondition;
        BehaviorChangeInterventionBlock block = bciBlockService.create(new BehaviorChangeInterventionBlock
                (entryConditionStr, exitConditionStr));

        return block;
    }

    public BehaviorChangeInterventionPhase createBciPhase(boolean entryCondition, boolean exitCondition) {
        String entryConditionStr = entryCondition ? passCondition : failCondition;
        String exitConditionStr = exitCondition ? passCondition : failCondition;
        BehaviorChangeInterventionPhase phase = bciPhaseService.create(new BehaviorChangeInterventionPhase
                (entryConditionStr, exitConditionStr));

        return phase;
    }

    public BehaviorChangeIntervention createBciIntervention(boolean entryCondition, boolean exitCondition) {
        String entryConditionStr = entryCondition ? passCondition : failCondition;
        String exitConditionStr = exitCondition ? passCondition : failCondition;
        BehaviorChangeIntervention intervention = bciService.create(new BehaviorChangeIntervention
                ("Intervention_" + numberOfInterventions, entryConditionStr, exitConditionStr));

        numberOfInterventions++;
        return intervention;
    }

    public List<Interaction> createBciInteractions(int amountOfRecipes, boolean entryCondition, boolean exitCondition) {
        List<Interaction> interactions =  new ArrayList<>();
        for (int i = 1; i <= amountOfRecipes; i++) {
            interactions.add(createInteraction(entryCondition, exitCondition));
        }

        return interactions;
    }

    public List<BehaviorChangeInterventionBlock> createBciBlocks(int amountOfRecipes, boolean entryCondition, boolean exitCondition) {
        List<BehaviorChangeInterventionBlock> blocks = new ArrayList<>();
        for (int i = 1; i <= amountOfRecipes; i++) {
            blocks.add(createBciBlock(entryCondition, exitCondition));
        }

        return blocks;
    }

    public List<BehaviorChangeInterventionPhase> createBciPhases(int amountOfRecipes, boolean entryCondition, boolean exitCondition) {
        List<BehaviorChangeInterventionPhase> phases = new ArrayList<>();
        for (int i = 1; i <= amountOfRecipes; i++) {
            phases.add(createBciPhase(entryCondition, exitCondition));
        }

        return phases;
    }

    public List<Interaction> findAllInteractions() {
        return interactionService.findAll();
    }

    public List<BehaviorChangeInterventionBlock> findAllBciBlocks() {
        return bciBlockService.findAll();
    }

    public List<BehaviorChangeInterventionPhase> findAllBciPhases() {
        return bciPhaseService.findAll();
    }

    public BehaviorChangeIntervention findBci(Long id) {
        return bciService.findById(id);
    }
}
