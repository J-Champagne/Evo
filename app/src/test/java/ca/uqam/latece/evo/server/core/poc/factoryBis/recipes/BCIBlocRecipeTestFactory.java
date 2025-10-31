package ca.uqam.latece.evo.server.core.poc.factoryBis.recipes;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.util.ConditionConverter;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * BCIBlocRecipeTestFactory is a Spring (@Component) factory used to create
 * preconfigured BehaviorChangeInterventionBloc (recipe) objects for testing purposes.
 * <p>
 * Each method returns a BehaviorChangeInterventionBloc with specified
 * conditions (true or false).
 *
 * @author Mohamed Djawad Abi Ayad
 * @version 1.0
 */
@Component
public class BCIBlocRecipeTestFactory {

    @Autowired
    private BehaviorChangeInterventionBlockService behaviorChangeInterventionBlockService;

    @Autowired
    private ConditionConverter conditionConverter;


    public BehaviorChangeInterventionBlock getBlocRecipeWithTrueConditions() {
        List<BehaviorChangeInterventionBlock> blocks = behaviorChangeInterventionBlockService.findByEntryConditions("x->true");
        for (BehaviorChangeInterventionBlock block : blocks) {
            if(block.getExitConditions().equals("x->true"))
                return convertConditionsToBoolean(block);
        }
        throw new RuntimeException("No BCI block with both entry and exit true condition found");
    }

    public BehaviorChangeInterventionBlock getBlocRecipeWithFalseConditions() {
        List<BehaviorChangeInterventionBlock> blocks = behaviorChangeInterventionBlockService.findByEntryConditions("x->false");
        for (BehaviorChangeInterventionBlock block : blocks) {
            if(block.getExitConditions().equals("x->false"))
                return convertConditionsToBoolean(block);
        }
        throw new RuntimeException("No BCI block with both entry and exit false condition found");
    }

    private BehaviorChangeInterventionBlock convertConditionsToBoolean(BehaviorChangeInterventionBlock behaviorChangeInterventionBlock){

        String entryCondition = behaviorChangeInterventionBlock.getEntryConditions();
        String exitCondition = behaviorChangeInterventionBlock.getExitConditions();
        behaviorChangeInterventionBlock.setEntryConditions(conditionConverter.convertCondition(entryCondition));
        behaviorChangeInterventionBlock.setExitConditions(conditionConverter.convertCondition(exitCondition));
        return behaviorChangeInterventionBlock;
    }


}
