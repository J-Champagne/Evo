package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import jakarta.validation.constraints.NotNull;

import java.time.Clock;

/**
 * Represents an event corresponding to a ClientEvent in relation to a BCIActivityInstance.
 * This class is designed to be handled by BCIActivityInstanceService in the context of handling requests related to
 * BehaviorChangeIntervention entities from the client side of the application
 * @author Julien Champagne.
 */
public class BCIActivityInstanceClientEvent extends EvoEvent<BCIActivityInstance> {
    ClientEvent clientEvent;

    Long bciActivityInstanceId;

    Long bciBlockInstanceId;

    Long bciPhaseInstanceId;

    Long bciInstanceId;

    public BCIActivityInstanceClientEvent(@NotNull BCIActivityInstance evoModel,
                                          @NotNull ClientEvent clientEvent,
                                          @NotNull Long bciActivityInstanceId,
                                          @NotNull Long bciBlockInstanceId,
                                          @NotNull Long bciPhaseInstanceId,
                                          @NotNull Long bciInstanceId) {
        super(evoModel);
        this.clientEvent = clientEvent;
        this.bciActivityInstanceId = bciActivityInstanceId;
        this.bciBlockInstanceId = bciBlockInstanceId;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    public BCIActivityInstanceClientEvent(@NotNull BCIActivityInstance evoModel,
                                          @NotNull ClientEvent clientEvent,
                                          @NotNull Long bciActivityInstanceId,
                                          @NotNull Long bciBlockInstanceId,
                                          @NotNull Long bciPhaseInstanceId,
                                          @NotNull Long bciInstanceId,
                                          @NotNull Clock clock) {
        super(evoModel,clock);
        this.clientEvent = clientEvent;
        this.bciActivityInstanceId = bciActivityInstanceId;
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
}