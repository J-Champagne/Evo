package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import jakarta.validation.constraints.NotNull;

/**
 * Represents an event corresponding to a ClientEvent in relation to a BCIInstance.
 * This class is designed to be handled by BCIInstanceService in the context of handling requests related to
 * BehaviorChangeIntervention entities from the client side of the application
 * @author Julien Champagne.
 */
public class BCIInstanceClientEvent extends EvoClientEvent {
    Long bciInstanceId;

    BehaviorChangeInterventionPhaseInstance bciPhaseInstance;

    BCIActivityCheckEntryConditionsClientEvent entryConditionEvent;

    public BCIInstanceClientEvent(@NotNull ClientEvent clientEvent,
                                  @NotNull ClientEventResponse response,
                                  @NotNull Long bciInstanceId,
                                  @NotNull BehaviorChangeInterventionPhaseInstance bciPhaseInstance) {
        super(clientEvent, response);
        this.bciInstanceId = bciInstanceId;
        this.bciPhaseInstance = bciPhaseInstance;
    }

    public Long getBciInstanceId() {
        return bciInstanceId;
    }

    public BehaviorChangeInterventionPhaseInstance getBCIPhaseInstance() {
        return bciPhaseInstance;
    }

    public BCIActivityCheckEntryConditionsClientEvent getEntryConditionEvent() {
        return entryConditionEvent;
    }

    public void setEntryConditionEvent(BCIActivityCheckEntryConditionsClientEvent entryConditionEvent) {
        this.entryConditionEvent = entryConditionEvent;
    }
}