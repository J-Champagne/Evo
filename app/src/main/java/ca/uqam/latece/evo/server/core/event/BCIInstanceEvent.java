package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Represents an event associated with a specific instance of a Behavior Change Intervention (BCI). This class extends
 * {@code EvoEvent}, providing context for Behavior Change Intervention instances and facilitating event handling
 * within the Evo framework.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public class BCIInstanceEvent extends EvoEvent<BehaviorChangeInterventionInstance> {

    public BCIInstanceEvent(@NotNull BehaviorChangeInterventionInstance evoModel) {
        super(evoModel);
    }

    public Patient getPatient(){
        return this.getEvoModel().getPatient();
    }

    public BehaviorChangeInterventionPhaseInstance getCurrentPhase(){
        return this.getEvoModel().getCurrentPhase();
    }

    public List<BehaviorChangeInterventionPhaseInstance> getPhases() {
        return this.getEvoModel().getPhases();
    }

    @Override
    public BehaviorChangeInterventionInstance getEvoModel() {
        return super.getEvoModel();
    }
}
