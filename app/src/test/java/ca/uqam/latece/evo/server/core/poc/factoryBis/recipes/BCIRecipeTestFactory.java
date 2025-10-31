package ca.uqam.latece.evo.server.core.poc.factoryBis.recipes;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.util.ConditionConverter;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * BCIRecipeTestFactory is a Spring (@Component) factory used to create
 * preconfigured BehaviorChangeIntervention (recipe) objects for testing purposes.
 * <p>
 * Each method returns a personalized BehaviorChangeIntervention .
 *
 * @author Mohamed Djawad Abi Ayad
 * @version 1.0
 */
@Component
public class BCIRecipeTestFactory {

    @Autowired
    private BehaviorChangeInterventionService behaviorChangeInterventionService;

    @Autowired
    private ConditionConverter conditionConverter;


    public BehaviorChangeIntervention getRecipe() {
        BehaviorChangeIntervention intervention =  behaviorChangeInterventionService.findByName("Intervention POC 1").getFirst();
        return convertConditionsToBoolean(intervention);
    }

    private BehaviorChangeIntervention convertConditionsToBoolean(BehaviorChangeIntervention intervention){

        String entryCondition = intervention.getEntryConditions();
        String exitCondition = intervention.getExitConditions();
        intervention.setEntryConditions(conditionConverter.convertCondition(entryCondition));
        intervention.setExitConditions(conditionConverter.convertCondition(exitCondition));
        return intervention;

    }



}
