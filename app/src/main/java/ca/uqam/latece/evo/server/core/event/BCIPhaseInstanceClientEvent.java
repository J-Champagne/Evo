package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import jakarta.validation.constraints.NotNull;

/**
 * Represents an event corresponding to a ClientEvent in relation to a BCIPhaseInstance.
 * This class is designed to be handled by BCIPhaseInstanceService in the context of handling requests related to
 * BehaviorChangeIntervention entities from the client side of the application
 * @author Julien Champagne.
 */
public class BCIPhaseInstanceClientEvent extends EvoClientEvent {
    Long bciPhaseInstanceId;

    Long bciInstanceId;

    BehaviorChangeInterventionBlockInstance bciBlockInstance;

    BCIActivityCheckEntryConditionsClientEvent entryConditionEvent;

    public BCIPhaseInstanceClientEvent(@NotNull ClientEvent clientEvent,
                                       @NotNull ClientEventResponse response,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId,
                                       @NotNull BehaviorChangeInterventionBlockInstance bciBlockInstance) {
        super(clientEvent, response);
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
        this.bciBlockInstance = bciBlockInstance;
    }

    public Long getBciPhaseInstanceId() {
        return bciPhaseInstanceId;
    }

    public Long getBciInstanceId() {
        return bciInstanceId;
    }

    public BehaviorChangeInterventionBlockInstance getBCIBlockInstance() {
        return bciBlockInstance;
    }

    public BCIActivityCheckEntryConditionsClientEvent getEntryConditionEvent() {
        return entryConditionEvent;
    }

    public void setEntryConditionEvent(BCIActivityCheckEntryConditionsClientEvent entryConditionEvent) {
        this.entryConditionEvent = entryConditionEvent;
    }
}