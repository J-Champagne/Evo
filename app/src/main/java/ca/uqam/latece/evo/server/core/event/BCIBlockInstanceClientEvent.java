package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;

import jakarta.validation.constraints.NotNull;

import java.time.Clock;

/**
 * Represents an event corresponding to a ClientEvent in relation to a BCIBlockInstance.
 */
public class BCIBlockInstanceClientEvent extends EvoClientEvent<BCIActivityInstance> {
    Long bciBlockInstanceId;

    Long bciPhaseInstanceId;

    Long bciInstanceId;

    public BCIBlockInstanceClientEvent(@NotNull BCIActivityInstance activityInstance,
                                       @NotNull ClientEvent clientEvent,
                                       @NotNull ClientEventResponse response,
                                       @NotNull Long bciBlockInstanceId,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId) {
        super(activityInstance, clientEvent, response);
        this.bciBlockInstanceId = bciBlockInstanceId;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    public BCIBlockInstanceClientEvent(@NotNull BCIActivityInstance evoModel,
                                       @NotNull ClientEvent clientEvent,
                                       @NotNull ClientEventResponse response,
                                       @NotNull Long bciBlockInstanceId,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId,
                                       @NotNull Clock clock) {
        super(evoModel,clientEvent, response, clock);
        this.bciBlockInstanceId = bciBlockInstanceId;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    public Long getBciBlockInstanceId() {
        return bciBlockInstanceId;
    }

    public Long getBciPhaseInstanceId() {
        return bciPhaseInstanceId;
    }

    public Long getBciInstanceId() {
        return bciInstanceId;
    }
}
