package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import jakarta.validation.constraints.NotNull;

import java.time.Clock;

public class BCIActivityClientEvent extends EvoClientEvent<BCIActivityInstance>  {
    Long bciActivityId;

    Long bciBlockInstanceId;

    Long bciPhaseInstanceId;

    Long bciInstanceId;



    public BCIActivityClientEvent(     @NotNull ClientEvent clientEvent,
                                       @NotNull Long bciActivityId,
                                       @NotNull Long bciBlockInstanceId,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId) {
        super(clientEvent, new ClientEventResponse(clientEvent));
        this.bciActivityId = bciActivityId;
        this.bciBlockInstanceId = bciBlockInstanceId;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    public BCIActivityClientEvent(     @NotNull ClientEvent clientEvent,
                                       @NotNull Long bciActivityId,
                                       @NotNull Long bciBlockInstanceId,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId,
                                       @NotNull Clock clock) {
        super(clientEvent, new ClientEventResponse(clientEvent), clock);
        this.bciActivityId = bciActivityId;
        this.bciBlockInstanceId = bciBlockInstanceId;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    public Long getBciActivityId() {
        return bciActivityId;
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
