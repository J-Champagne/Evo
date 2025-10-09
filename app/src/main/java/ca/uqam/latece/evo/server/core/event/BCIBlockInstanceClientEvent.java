package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;

import jakarta.validation.constraints.NotNull;

import java.time.Clock;

/**
 * Represents an event corresponding to a ClientEvent in relation to a BCIBlockInstance.
 */
public class BCIBlockInstanceClientEvent<A extends BCIActivityInstance> extends EvoClientEvent<A> {
    Long bciBlockInstanceId;

    Long bciPhaseInstanceId;

    Long bciInstanceId;

    Long newBlockInstanceId;

    Long newPhaseInstanceId;

    public BCIBlockInstanceClientEvent(@NotNull A activityInstance,
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

    public BCIBlockInstanceClientEvent(@NotNull A evoModel,
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

    public BCIBlockInstanceClientEvent(@NotNull A activityInstance,
                                       @NotNull ClientEvent clientEvent,
                                       @NotNull ClientEventResponse response,
                                       @NotNull Long bciBlockInstanceId,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId,
                                       @NotNull Long newBlockInstanceId,
                                       @NotNull Long newPhaseInstanceId) {
        this(activityInstance, clientEvent, response, bciBlockInstanceId, bciPhaseInstanceId, bciInstanceId);
        this.newBlockInstanceId = newBlockInstanceId;
        this.newPhaseInstanceId = newPhaseInstanceId;
    }

    public BCIBlockInstanceClientEvent(@NotNull A activityInstance,
                                       @NotNull ClientEvent clientEvent,
                                       @NotNull ClientEventResponse response,
                                       @NotNull Long bciBlockInstanceId,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId,
                                       @NotNull Long newBlockInstanceId,
                                       @NotNull Long newPhaseInstanceId,
                                       @NotNull Clock clock) {
        this(activityInstance, clientEvent, response, bciBlockInstanceId, bciPhaseInstanceId, bciInstanceId, clock);
        this.newBlockInstanceId = newBlockInstanceId;
        this.newPhaseInstanceId = newPhaseInstanceId;
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

    public Long getNewBlockInstanceId() {
        return newBlockInstanceId;
    }

    public Long getNewPhaseInstanceId() {
        return newPhaseInstanceId;
    }
}
