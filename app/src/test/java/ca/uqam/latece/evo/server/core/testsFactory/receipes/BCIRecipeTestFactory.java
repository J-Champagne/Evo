package ca.uqam.latece.evo.server.core.testsFactory.receipes;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
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


    public BehaviorChangeIntervention getReceipe() {
        return behaviorChangeInterventionService.create(new BehaviorChangeIntervention("intervention",
                "true", "true"));
    }
}
