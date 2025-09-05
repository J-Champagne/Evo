package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;

import jakarta.validation.constraints.NotNull;

import java.time.Clock;

/**
 * Represents an event corresponding to a ClientEvent in relation to a BCIPhaseInstance.
 * This class is designed to be handled by BCIPhaseInstanceService in the context of handling requests related to
 * BehaviorChangeIntervention entities from the client side of the application
 * @author Julien Champagne.
 */
public class BCIPhaseInstanceClientEvent extends EvoEvent<BehaviorChangeInterventionBlockInstance>{
    ClientEvent clientEvent;

    Long bciPhaseInstanceId;

    Long bciInstanceId;

    public BCIPhaseInstanceClientEvent(@NotNull BehaviorChangeInterventionBlockInstance evoModel,
                                       @NotNull ClientEvent clientEvent,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId) {
        super(evoModel);
        this.clientEvent = clientEvent;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    public BCIPhaseInstanceClientEvent(@NotNull BehaviorChangeInterventionBlockInstance evoModel,
                                       @NotNull ClientEvent clientEvent,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId,
                                       @NotNull Clock clock) {
        super(evoModel,clock);
        this.clientEvent = clientEvent;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    @Override
    public BehaviorChangeInterventionBlockInstance getEvoModel() {
        return super.getEvoModel();
    }

    public ClientEvent getClientEvent() {
        return this.clientEvent;
    }

    public Long getBciPhaseInstanceId() {
        return bciPhaseInstanceId;
    }

    public Long getBciInstanceId() {
        return bciInstanceId;
    }
}