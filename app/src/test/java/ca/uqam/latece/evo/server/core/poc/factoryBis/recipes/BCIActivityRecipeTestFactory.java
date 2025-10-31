package ca.uqam.latece.evo.server.core.poc.factoryBis.recipes;

import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.util.ConditionConverter;
import ca.uqam.latece.evo.server.core.service.BCIActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * BCIActivityRecipeTestFactory is a Spring (@Component) factory used to create
 * preconfigured BehaviorChangeInterventionActivity (recipe) objects for testing purposes.
 * <p>
 * Each method returns a personalized  BehaviorChangeInterventionActivity with specified
 * conditions (true or false).
 *
 * @author Mohamed Djawad Abi Ayad
 * @version 1.0
 */
@Component
public class BCIActivityRecipeTestFactory {


    @Autowired
    private BCIActivityService bciActivityService;

    @Autowired
    private ConditionConverter conditionConverter;

    public BCIActivity getFirstReceipeWithTrueConditions() {
        BCIActivity bciActivity = bciActivityService.findByName("Activity POC 1").getFirst();
        return ConvertConditionsToBoolean(bciActivity);
    }

    public BCIActivity getSecondReceipeWithTrueConditions() {
        BCIActivity bciActivity = bciActivityService.findByName("Activity POC 2").getFirst();
        return ConvertConditionsToBoolean(bciActivity);
    }

    public BCIActivity getThirdReceipeWithTrueConditions() {

       BCIActivity bciActivity = bciActivityService.findByName("Activity POC 3").getFirst();
       return ConvertConditionsToBoolean(bciActivity);

    }

    private BCIActivity ConvertConditionsToBoolean(BCIActivity bciActivity){

        String preconditions = bciActivity.getPreconditions();
        String postconditions = bciActivity.getPostconditions();
        bciActivity.setPreconditions(conditionConverter.convertCondition(preconditions));
        bciActivity.setPostconditions(conditionConverter.convertCondition(postconditions));
        return bciActivity;

    }


}
