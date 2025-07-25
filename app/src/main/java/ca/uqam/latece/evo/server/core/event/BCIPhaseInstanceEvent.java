package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.instance.BCIModuleInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import jakarta.validation.constraints.NotNull;

import java.time.Clock;
import java.util.List;

/**
 * Represents a specialized event in the Evo+ framework for handling events associated with the
 * BehaviorChangeInterventionPhaseInstance model. This class extends the {@code EvoEvent} framework and provides additional
 * constructors and context specifically for {@code BehaviorChangeInterventionPhaseInstance}.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public class BCIPhaseInstanceEvent extends EvoEvent<BehaviorChangeInterventionPhaseInstance> {

    public BCIPhaseInstanceEvent(@NotNull BehaviorChangeInterventionPhaseInstance evoModel) {
        super(evoModel);
    }

    public BCIPhaseInstanceEvent(@NotNull BehaviorChangeInterventionPhaseInstance evoModel, TimeCycle timeCycle) {
        super(evoModel, timeCycle);
    }

    public BCIPhaseInstanceEvent(@NotNull BehaviorChangeInterventionPhaseInstance evoModel, @NotNull Clock clock) {
        super(evoModel, clock);
    }

    public BCIPhaseInstanceEvent(@NotNull BehaviorChangeInterventionPhaseInstance evoModel, @NotNull Clock clock, TimeCycle timeCycle) {
        super(evoModel, clock, timeCycle);
    }

    @Override
    public BehaviorChangeInterventionPhaseInstance getEvoModel() {
        return super.getEvoModel();
    }

    public BehaviorChangeInterventionBlockInstance getCurrentBlock() {
        return this.getEvoModel().getCurrentBlock();
    }

    public List<BCIModuleInstance> getModules(){
        return this.getEvoModel().getModules();
    }

    public BehaviorChangeInterventionBlockInstance getNextBlock() {
        return new BehaviorChangeInterventionBlockInstance();
    }

    public List<BehaviorChangeInterventionBlockInstance> getBlocks() {
        return this.getEvoModel().getActivities();
    }

}
