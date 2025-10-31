package ca.uqam.latece.evo.server.core.poc.factoryBis.recipes;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.util.ConditionConverter;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionPhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * BCIPhaseRecipeTestFactory is a Spring (@Component) factory used to create
 * preconfigured BehaviorChangeInterventionPhase (recipe) objects for testing purposes.
 * <p>
 * Each method returns a BehaviorChangeInterventionPhase with specified
 * conditions (true or false).
 *
 * @author Mohamed Djawad Abi Ayad
 * @version 1.0
 */
@Component
public class BCIPhaseRecipeTestFactory {

    @Autowired
    private BehaviorChangeInterventionPhaseService behaviorChangeInterventionPhaseService;

    @Autowired
    private ConditionConverter conditionConverter;


    public BehaviorChangeInterventionPhase getPhaseRecipeWithTrueConditions() {
        List<BehaviorChangeInterventionPhase> phases =  behaviorChangeInterventionPhaseService.findByEntryConditions("x->true");
        for (BehaviorChangeInterventionPhase phase : phases) {
            if(phase.getExitConditions().equals("x->true"))
                return convertConditionsToBoolean(phase);
        }
        throw new RuntimeException("No BCI phase with both entry and exit true condition found");
    }

    public BehaviorChangeInterventionPhase getPhaseWithFalseConditions() {
        List<BehaviorChangeInterventionPhase> phases = behaviorChangeInterventionPhaseService.findByEntryConditions("x->true");
        for (BehaviorChangeInterventionPhase phase : phases) {
            if(phase.getExitConditions().equals("x->false"))
                return convertConditionsToBoolean(phase);
        }
        throw new RuntimeException("No BCI phase with both entry and exit false condition found");


    }

    private BehaviorChangeInterventionPhase convertConditionsToBoolean(BehaviorChangeInterventionPhase behaviorChangeInterventionPhase){

        String entryCondition = behaviorChangeInterventionPhase.getEntryConditions();
        String exitCondition = behaviorChangeInterventionPhase.getExitConditions();
        behaviorChangeInterventionPhase.setEntryConditions(conditionConverter.convertCondition(entryCondition));
        behaviorChangeInterventionPhase.setExitConditions(conditionConverter.convertCondition(exitCondition));
        return behaviorChangeInterventionPhase;

    }


}
