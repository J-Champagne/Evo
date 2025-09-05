package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import jakarta.validation.constraints.NotNull;

import java.time.Clock;

public class BCIBlockInstanceClientEvent extends EvoEvent<BCIActivityInstance> {
    ClientEvent clientEvent;

    Long bciBlockInstanceId;

    Long bciPhaseInstanceId;

    Long bciInstanceId;

    public BCIBlockInstanceClientEvent(@NotNull BCIActivityInstance evoModel,
                                    @NotNull ClientEvent clientEvent,
                                    @NotNull Long bciBlockInstanceId,
                                    @NotNull Long bciPhaseInstanceId,
                                    @NotNull Long bciInstanceId) {
        super(evoModel);
        this.clientEvent = clientEvent;
        this.bciBlockInstanceId = bciBlockInstanceId;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    public BCIBlockInstanceClientEvent(@NotNull BCIActivityInstance evoModel,
                                    @NotNull ClientEvent clientEvent,
                                    @NotNull Long bciBlockInstanceId,
                                    @NotNull Long bciPhaseInstanceId,
                                    @NotNull Long bciInstanceId,
                                    @NotNull Clock clock) {
        super(evoModel,clock);
        this.clientEvent = clientEvent;
        this.bciBlockInstanceId = bciBlockInstanceId;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    @Override
    public BCIActivityInstance getEvoModel() {
        return super.getEvoModel();
    }

    public ClientEvent getClientEvent() {
        return this.clientEvent;
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
