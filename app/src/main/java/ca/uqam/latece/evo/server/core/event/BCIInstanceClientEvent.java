package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import jakarta.validation.constraints.NotNull;

import java.time.Clock;

/**
 * Represents an event corresponding to a ClientEvent in relation to a BCIInstance.
 * This class is designed to be handled by BCIInstanceService in the context of handling requests related to
 * BehaviorChangeIntervention entities from the client side of the application
 * @author Julien Champagne.
 */
public class BCIInstanceClientEvent extends EvoClientEvent<BehaviorChangeInterventionPhaseInstance> {
    Long bciInstanceId;

    public BCIInstanceClientEvent(@NotNull BehaviorChangeInterventionPhaseInstance evoModel,
                                  @NotNull ClientEvent clientEvent,
                                  @NotNull ClientEventResponse response,
                                  @NotNull Long bciInstanceId) {
        super(evoModel, clientEvent, response);
        this.bciInstanceId = bciInstanceId;
    }

    public BCIInstanceClientEvent(@NotNull BehaviorChangeInterventionPhaseInstance evoModel,
                                  @NotNull ClientEvent clientEvent,
                                  @NotNull ClientEventResponse response,
                                  @NotNull Long bciInstanceId,
                                  @NotNull Clock clock) {
        super(evoModel,clientEvent, response, clock);
        this.bciInstanceId = bciInstanceId;
    }

    public Long getBciInstanceId() {
        return bciInstanceId;
    }
}