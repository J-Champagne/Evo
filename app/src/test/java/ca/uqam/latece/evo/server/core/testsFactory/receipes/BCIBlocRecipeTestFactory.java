package ca.uqam.latece.evo.server.core.testsFactory.receipes;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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


    public BehaviorChangeInterventionBlock getBlocReceipeWithTrueConditions() {
        return behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("true", "true"));
    }

    public BehaviorChangeInterventionBlock getBlocReceipeWithFalseConditions() {
        return behaviorChangeInterventionBlockService.create(new BehaviorChangeInterventionBlock
                ("false", "false"));
    }
}
