package ca.uqam.latece.evo.server.core.testsFactory.instances;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionBlockInstanceService;
import ca.uqam.latece.evo.server.core.testsFactory.receipes.BCIBlocRecipeTestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BCIBlocTestFactory is a Spring (@Component) factory used to create
 * preconfigured BehaviorChangeInterventionBlockInstance objects for testing purposes.
 * <p>
 * Each method returns a BehaviorChangeInterventionBlockInstance with specified
 * conditions ("true" or "false") and associated BCIActivityInstance objects.
 *
 * @author Mohamed Djawad Abi Ayad
 * @version 1.0
 */
@Component
public class BCIBlocInstanceTestFactory {


    @Autowired
    private BCIBlocRecipeTestFactory bciBlocRecipeTestFactory;

    @Autowired
    private BehaviorChangeInterventionBlockInstanceService bciBlockInstanceService;

    public BehaviorChangeInterventionBlockInstance getBlocWithTrueConditions(BCIActivityInstance... activities) {

        List<BCIActivityInstance> listActivities = new ArrayList<>();
        Collections.addAll(listActivities, activities);

        BehaviorChangeInterventionBlock bciBlock = bciBlocRecipeTestFactory.getBlocReceipeWithTrueConditions();

        return bciBlockInstanceService.create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.STALLED, TimeCycle.BEGINNING, listActivities, bciBlock));
    }

    public BehaviorChangeInterventionBlockInstance getBlocWithFalseConditions(BCIActivityInstance... activities) {

        List<BCIActivityInstance> listActivities = new ArrayList<>();
        Collections.addAll(listActivities, activities);

        BehaviorChangeInterventionBlock bciBlock = bciBlocRecipeTestFactory.getBlocReceipeWithFalseConditions();


        return bciBlockInstanceService.create(new BehaviorChangeInterventionBlockInstance(ExecutionStatus.STALLED, TimeCycle.BEGINNING, listActivities, bciBlock));
    }
}
