package ca.uqam.latece.evo.server.core.testsFactory.instances;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BCIModuleInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.service.instance.BCIModuleInstanceService;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionPhaseInstanceService;
import ca.uqam.latece.evo.server.core.testsFactory.receipes.BCIPhaseRecipeTestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * BCIPhaseTestFactory is a Spring (@Component) factory used to create
 * preconfigured BehaviorChangeInterventionPhaseInstance objects for testing purposes.
 * <p>
 * Each method returns a BehaviorChangeInterventionPhaseInstance with specified
 * conditions ("true" or "false") and associated blocks ands modules.
 *
 * @author Mohamed Djawad Abi Ayad
 * @version 1.0
 */
@Component
public class BCIPhaseInstanceTestFactory {

    @Autowired
    private BCIModuleInstanceService bciModuleInstanceService;

    @Autowired
    BCIPhaseRecipeTestFactory bciPhaseRecipeTestFactory;

    @Autowired
    private BehaviorChangeInterventionPhaseInstanceService bciPhaseInstanceService;

    public BehaviorChangeInterventionPhaseInstance getPhaseWithTrueConditions(BehaviorChangeInterventionBlockInstance... blocs) {

        List<BehaviorChangeInterventionBlockInstance> listBlocs = new ArrayList<>();
        Collections.addAll(listBlocs, blocs);

        Optional<BehaviorChangeInterventionBlockInstance> firstBlock = Arrays.stream(blocs).findFirst();
        List<BCIModuleInstance> modules = buildModules(listBlocs);


        BehaviorChangeInterventionPhase behaviorChangeInterventionPhase = bciPhaseRecipeTestFactory.getPhaseWithTrueConditions();


        return bciPhaseInstanceService.create
                (new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.STALLED, firstBlock.get(), listBlocs, modules, behaviorChangeInterventionPhase));
    }


    public BehaviorChangeInterventionPhaseInstance getPhaseWithFalseConditions(BehaviorChangeInterventionBlockInstance... blocs) {

        List<BehaviorChangeInterventionBlockInstance> listBlocs = new ArrayList<>();
        Collections.addAll(listBlocs, blocs);

        Optional<BehaviorChangeInterventionBlockInstance> firstBlock = Arrays.stream(blocs).findFirst();
        List<BCIModuleInstance> modules = buildModules(listBlocs);

        BehaviorChangeInterventionPhase behaviorChangeInterventionPhase = bciPhaseRecipeTestFactory.getPhaseWithFalseConditions();


        return bciPhaseInstanceService.create
                (new BehaviorChangeInterventionPhaseInstance(ExecutionStatus.STALLED, firstBlock.get(), listBlocs, modules, behaviorChangeInterventionPhase));
    }


    private List<BCIModuleInstance> buildModules(List<BehaviorChangeInterventionBlockInstance> blocs) {
        List<BCIModuleInstance> modules = new ArrayList<>();
        for (BehaviorChangeInterventionBlockInstance bloc : blocs) {

            List<BCIActivityInstance> listActivities = bloc.getActivities();
            modules.add(bciModuleInstanceService.create(new BCIModuleInstance(ExecutionStatus.STALLED,
                    OutcomeType.SUCCESSFUL, listActivities)));
        }
        return modules;
    }


}
