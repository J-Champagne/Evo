package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import jakarta.validation.constraints.NotNull;

import java.time.Clock;

public class BCIActivityClientEvent extends EvoClientEvent<BCIActivityInstance>  {
    Long bciActivityInstanceId;

    Long bciBlockInstanceId;

    Long bciPhaseInstanceId;

    Long bciInstanceId;

    Long newActivityInstanceId;

    Long newBlockInstanceId;

    Long newPhaseInstanceId;


    public BCIActivityClientEvent(     @NotNull ClientEvent clientEvent,
                                       @NotNull Long bciActivityInstanceId,
                                       @NotNull Long bciBlockInstanceId,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId) {
        super(clientEvent, new ClientEventResponse(clientEvent));
        this.bciActivityInstanceId = bciActivityInstanceId;
        this.bciBlockInstanceId = bciBlockInstanceId;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    public BCIActivityClientEvent(     @NotNull ClientEvent clientEvent,
                                       @NotNull Long bciActivityInstanceId,
                                       @NotNull Long bciBlockInstanceId,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId,
                                       @NotNull Clock clock) {
        super(clientEvent, new ClientEventResponse(clientEvent), clock);
        this.bciActivityInstanceId = bciActivityInstanceId;
        this.bciBlockInstanceId = bciBlockInstanceId;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    public BCIActivityClientEvent(@NotNull ClientEvent clientEvent,
                                  @NotNull Long bciActivityInstanceId,
                                  @NotNull Long bciBlockInstanceId,
                                  @NotNull Long bciPhaseInstanceId,
                                  @NotNull Long bciInstanceId,
                                  @NotNull Long newActivityInstanceId,
                                  @NotNull Long newBlockInstanceId,
                                  @NotNull Long newPhaseInstanceId) {
        this(clientEvent, bciActivityInstanceId, bciBlockInstanceId, bciPhaseInstanceId, bciInstanceId);
        this.newActivityInstanceId = newActivityInstanceId;
        this.newBlockInstanceId = newBlockInstanceId;
        this.newPhaseInstanceId = newPhaseInstanceId;
    }

    public BCIActivityClientEvent(@NotNull ClientEvent clientEvent,
                                  @NotNull Long bciActivityInstanceId,
                                  @NotNull Long bciBlockInstanceId,
                                  @NotNull Long bciPhaseInstanceId,
                                  @NotNull Long bciInstanceId,
                                  @NotNull Long newActivityInstanceId,
                                  @NotNull Long newBlockInstanceId,
                                  @NotNull Long newPhaseInstanceId,
                                  @NotNull Clock clock) {
        this(clientEvent, bciActivityInstanceId, bciBlockInstanceId, bciPhaseInstanceId, bciInstanceId, clock);
        this.newActivityInstanceId = newActivityInstanceId;
        this.newBlockInstanceId = newBlockInstanceId;
        this.newPhaseInstanceId = newPhaseInstanceId;
    }

    public Long getBciActivityInstanceId() {
        return bciActivityInstanceId;
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

    public Long getNewActivityInstanceId() {
        return newActivityInstanceId;
    }

    public Long getNewBlockInstanceId() {
        return newBlockInstanceId;
    }

    public Long getNewPhaseInstanceId() {
        return newPhaseInstanceId;
    }
}
