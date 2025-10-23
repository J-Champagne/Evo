package ca.uqam.latece.evo.server.core.testsFactory.receipes;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
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


    public BCIActivity getFirstReceipeWithTrueConditions() {
        return bciActivityService.create(new BCIActivity("Running", "Description", ActivityType.BCI_ACTIVITY,
                "true", "true"));
    }

    public BCIActivity getSecondReceipeWithTrueConditions() {
        return bciActivityService.create(new BCIActivity("Swiming", "Description", ActivityType.BCI_ACTIVITY,
                "true", "true"));
    }

    public BCIActivity getThirdReceipeWithTrueConditions() {
        return bciActivityService.create(new BCIActivity("Climbing", "Description", ActivityType.BCI_ACTIVITY,
                "true", "true"));
    }
}
