package ca.uqam.latece.evo.server.core.poc.factoryBis.instances;


import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.event.BCIActivityClientEvent;
import ca.uqam.latece.evo.server.core.event.BCIInstanceClientEvent;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;

import ca.uqam.latece.evo.server.core.poc.factoryBis.recipes.BCIRecipeTestFactory;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;


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
    BCIRecipeTestFactory bciRecipeTestFactory;

    @Autowired
    private BehaviorChangeInterventionInstanceService bciInstanceService;

    public BehaviorChangeInterventionInstance getIntervention(BehaviorChangeIntervention bciReceipe, Patient patient, BehaviorChangeInterventionPhaseInstance... phases) {


        List<BehaviorChangeInterventionPhaseInstance> listPhases = new ArrayList<>();
        Optional<BehaviorChangeInterventionPhaseInstance> firstPhase = Arrays.stream(phases).findFirst();

        Collections.addAll(listPhases, phases);

        return bciInstanceService.create(new BehaviorChangeInterventionInstance(ExecutionStatus.READY, patient,
                firstPhase.get(), listPhases, bciReceipe));
    }

    public BehaviorChangeInterventionInstance getIntervention(Patient patient, BehaviorChangeInterventionPhaseInstance... phases) {


        List<BehaviorChangeInterventionPhaseInstance> listPhases = new ArrayList<>();
        Optional<BehaviorChangeInterventionPhaseInstance> firstPhase = Arrays.stream(phases).findFirst();

        Collections.addAll(listPhases, phases);

        BehaviorChangeIntervention bciReceipe = bciRecipeTestFactory.getRecipe();

        return bciInstanceService.create(new BehaviorChangeInterventionInstance(ExecutionStatus.READY, patient,
                firstPhase.get(), listPhases, bciReceipe));
    }

    public BehaviorChangeInterventionInstance getInterventionById(Long id) {
        return bciInstanceService.findById(id);
    }

    /**
     * Gets a predefined intervention instance with a specific structure:
     * - Phase 1 contains 1 block and 1 activity.
     * - Phase 2 contains 2 blocks and 2 activities.
     * <p>
     * Scenario details:
     * The scenario was created to test the happy path. Thus, the scenario involves patient Martin Aghion (id: 5) and a
     * Behavioral Change Intervention Instance (id: 108), which is an instance of Behavioral Change Intervention (id: 2)
     * with two phases:
     *      - Phase 1 (id: 106) was set to “IN_PROGRESS” and marked as the current phase. It contains one Block (id: 103),
     *      also “IN_PROGRESS” and at the “BEGINNING” stage. This Block contains an Activity (id: 99), likewise “IN_PROGRESS.”
     *      - Phase 2 (id: 107) was initialized as “READY.” It includes two blocks: Block 1 (id: 104), the Current Block,
     *      is at the 'UNSPECIFIED' stage and “READY” status with a single Activity (id: 100) also “READY.” Block 2 (id: 105),
     *      also 'UNSPECIFIED' and “READY,” contains two Activities (ids: 101 and 102), both with “READY” status.
     *
     * @return The `BehaviorChangeInterventionInstance` corresponding to the given predefined scenario.
     */
    public BehaviorChangeInterventionInstance getInterventionWithTwoPhases_Phase1Has1BlockAnd1Activity_Phase2Has2BlocksAnd2Activities(){
        return this.getInterventionById(108L);
    }

    public ClientEventResponse checkIntervention(BehaviorChangeInterventionInstance bciInstance) {
        BehaviorChangeInterventionPhaseInstance phaseInstance = bciInstance.getActivities().getFirst();
        BehaviorChangeInterventionBlockInstance blockInstance = phaseInstance.getActivities().getFirst();
        BCIActivityInstance activityInstance = blockInstance.getActivities().getFirst();

        // Create and handle the event BCIActivityClientEvent. I tested but not worked.
        /*BCIActivityClientEvent bciActivityClientEvent = new BCIActivityClientEvent(ClientEvent.FINISH,
                activityInstance.getId(), blockInstance.getId(), phaseInstance.getId(), bciInstance.getId());
        */

        // Create and handle the event BCIInstanceClientEvent.
        BCIInstanceClientEvent bciInstanceClientEvent = new BCIInstanceClientEvent(
                ClientEvent.FINISH,
                new ClientEventResponse(ClientEvent.FINISH),
                bciInstance.getId(),
                phaseInstance
        );

        return bciInstanceService.handleClientEvent(bciInstanceClientEvent);
    }
}






