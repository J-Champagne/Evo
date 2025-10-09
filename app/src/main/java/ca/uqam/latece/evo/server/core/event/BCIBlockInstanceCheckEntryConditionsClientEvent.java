package ca.uqam.latece.evo.server.core.event;

public class BCIBlockInstanceCheckEntryConditionsClientEvent extends EvoClientEvent {
    private BCIActivityCheckEntryConditionsClientEvent entryConditionClientEvent;

    public BCIBlockInstanceCheckEntryConditionsClientEvent(BCIActivityCheckEntryConditionsClientEvent entryConditionClientEvent) {
        super(entryConditionClientEvent.getClientEvent(), entryConditionClientEvent.getResponse());
        this.entryConditionClientEvent = entryConditionClientEvent;
    }

    public BCIActivityCheckEntryConditionsClientEvent getEntryConditionClientEvent() {
        return entryConditionClientEvent;
    }
}