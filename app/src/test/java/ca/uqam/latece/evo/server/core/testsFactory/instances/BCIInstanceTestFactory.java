package ca.uqam.latece.evo.server.core.testsFactory.instances;


import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionInstanceService;
import ca.uqam.latece.evo.server.core.testsFactory.receipes.BCIRecipeTestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * BCITestFactory is a Spring (@Component) factory used to create
 * preconfigured BehaviorChangeInterventionInstance objects for testing purposes.
 * <p>
 * Each method returns a BehaviorChangeInterventionInstance with specified
 * conditions (true or false) and associated phases for a given patient.
 *
 * @author Mohamed Djawad Abi Ayad
 * @version 1.0
 */
@Component
public class BCIInstanceTestFactory {

    @Autowired
    BCIRecipeTestFactory BCIRecipeTestFactory;

    @Autowired
    private BehaviorChangeInterventionInstanceService bciInstanceService;


    public BehaviorChangeInterventionInstance getIntervention(Patient patient, BehaviorChangeInterventionPhaseInstance... phases) {


        List<BehaviorChangeInterventionPhaseInstance> listPhases = new ArrayList<>();
        Optional<BehaviorChangeInterventionPhaseInstance> firstPhase = Arrays.stream(phases).findFirst();

        Collections.addAll(listPhases, phases);

        BehaviorChangeIntervention bciReceipe = BCIRecipeTestFactory.getReceipe();

        return bciInstanceService.create(new BehaviorChangeInterventionInstance(ExecutionStatus.READY, patient,
                firstPhase.get(), listPhases, bciReceipe));
    }

}






