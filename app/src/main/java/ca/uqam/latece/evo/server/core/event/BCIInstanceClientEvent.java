package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import jakarta.validation.constraints.NotNull;

import java.time.Clock;

/**
 * Represents an event corresponding to a ClientEvent in relation to a BCIInstance.
 * This class is designed to be handled by BCIInstanceService in the context of handling requests related to
 * BehaviorChangeIntervention entities from the client side of the application
 * @author Julien Champagne.
 */
public class BCIInstanceClientEvent extends EvoEvent<BehaviorChangeInterventionPhaseInstance> {
    ClientEvent clientEvent;

    Long bciInstanceId;

    public BCIInstanceClientEvent(@NotNull BehaviorChangeInterventionPhaseInstance evoModel,
                                  @NotNull ClientEvent clientEvent,
                                  @NotNull Long bciInstanceId) {
        super(evoModel);
        this.clientEvent = clientEvent;
        this.bciInstanceId = bciInstanceId;
    }

    public BCIInstanceClientEvent(@NotNull BehaviorChangeInterventionPhaseInstance evoModel,
                                  @NotNull ClientEvent clientEvent,
                                  @NotNull Long bciInstanceId,
                                  @NotNull Clock clock) {
        super(evoModel,clock);
        this.clientEvent = clientEvent;
        this.bciInstanceId = bciInstanceId;
    }

    @Override
    public BehaviorChangeInterventionPhaseInstance getEvoModel() {
        return super.getEvoModel();
    }

    public ClientEvent getClientEvent() {
        return this.clientEvent;
    }

    public Long getBciInstanceId() {
        return bciInstanceId;
    }
}