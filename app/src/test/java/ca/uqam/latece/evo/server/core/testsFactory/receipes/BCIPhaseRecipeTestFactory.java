package ca.uqam.latece.evo.server.core.testsFactory.receipes;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionPhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


    public BehaviorChangeInterventionPhase getPhaseWithTrueConditions() {
        return behaviorChangeInterventionPhaseService.create(new BehaviorChangeInterventionPhase("true",
                "true"));
    }

    public BehaviorChangeInterventionPhase getPhaseWithFalseConditions() {
        return behaviorChangeInterventionPhaseService.create(new BehaviorChangeInterventionPhase("false",
                "false"));
    }
}
