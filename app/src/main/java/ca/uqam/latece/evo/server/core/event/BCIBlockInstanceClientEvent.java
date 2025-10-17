package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import jakarta.validation.constraints.NotNull;

/**
 * Represents an event corresponding to a ClientEvent in relation to a BCIBlockInstance.
 */
public class BCIBlockInstanceClientEvent extends EvoClientEvent {
    Long bciBlockInstanceId;

    Long bciPhaseInstanceId;

    Long bciInstanceId;

    BCIActivityCheckEntryConditionsClientEvent entryConditionEvent;

    public BCIBlockInstanceClientEvent(@NotNull ClientEvent clientEvent,
                                       @NotNull ClientEventResponse response,
                                       @NotNull Long bciBlockInstanceId,
                                       @NotNull Long bciPhaseInstanceId,
                                       @NotNull Long bciInstanceId) {
        super(clientEvent, response);
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

    public BCIActivityCheckEntryConditionsClientEvent getEntryConditionEvent() {
        return entryConditionEvent;
    }

    public void setEntryConditionEvent(BCIActivityCheckEntryConditionsClientEvent entryConditionEvent) {
        this.entryConditionEvent = entryConditionEvent;
    }
}
