package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;

import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import jakarta.validation.constraints.NotNull;

import java.time.Clock;

/**
 * Represents an event corresponding to a ClientEvent in relation to a BCIPhaseInstance.
 * This class is designed to be handled by BCIPhaseInstanceService in the context of handling requests related to
 * BehaviorChangeIntervention entities from the client side of the application
 * @author Julien Champagne.
 */
public class BCIPhaseInstanceClientEvent extends EvoClientEvent<BehaviorChangeInterventionBlockInstance> {
    Long bciPhaseInstanceId;

    Long bciInstanceId;

    public BCIPhaseInstanceClientEvent(@NotNull BehaviorChangeInterventionBlockInstance activityInstance,
                                       @NotNull ClientEvent clientEvent,
                                       @NotNull ClientEventResponse response,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId) {
        super(activityInstance, clientEvent, response);
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    public BCIPhaseInstanceClientEvent(@NotNull BehaviorChangeInterventionBlockInstance activityInstance,
                                       @NotNull ClientEvent clientEvent,
                                       @NotNull ClientEventResponse response,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId,
                                       @NotNull Clock clock) {
        super(activityInstance, clientEvent, response, clock);
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    public Long getBciPhaseInstanceId() {
        return bciPhaseInstanceId;
    }

    public Long getBciInstanceId() {
        return bciInstanceId;
    }
}