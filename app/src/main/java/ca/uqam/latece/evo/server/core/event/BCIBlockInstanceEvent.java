package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import jakarta.validation.constraints.NotNull;

import java.time.Clock;


/**
 * Represents an event corresponding to a {@code BehaviorChangeInterventionBlockInstance}.
 * This class inherits from {@code EvoEvent} and is designed to handle events specifically tied
 * to the {@code BehaviorChangeInterventionBlockInstance} entity within the Evo+ framework.
 * It provides constructors for initializing the event with optional time cycle or clock information.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public class BCIBlockInstanceEvent extends EvoEvent<BehaviorChangeInterventionBlockInstance>{

    public BCIBlockInstanceEvent(@NotNull BehaviorChangeInterventionBlockInstance evoModel) {
        super(evoModel, evoModel.getStage());
    }

    public BCIBlockInstanceEvent(@NotNull BehaviorChangeInterventionBlockInstance evoModel, TimeCycle timeCycle) {
        super(evoModel, timeCycle);
    }

    public BCIBlockInstanceEvent(@NotNull BehaviorChangeInterventionBlockInstance evoModel, @NotNull Clock clock) {
        super(evoModel, clock, evoModel.getStage());
    }

    public BCIBlockInstanceEvent(@NotNull BehaviorChangeInterventionBlockInstance evoModel, @NotNull Clock clock, TimeCycle timeCycle) {
        super(evoModel, clock, timeCycle);
    }

    @Override
    public BehaviorChangeInterventionBlockInstance getEvoModel() {
        return super.getEvoModel();
    }
}