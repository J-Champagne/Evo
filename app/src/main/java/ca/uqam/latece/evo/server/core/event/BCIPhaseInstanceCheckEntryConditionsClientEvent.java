package ca.uqam.latece.evo.server.core.event;

public class BCIPhaseInstanceCheckEntryConditionsClientEvent extends EvoClientEvent {
    private BCIActivityCheckEntryConditionsClientEvent entryConditionClientEvent;

    public BCIPhaseInstanceCheckEntryConditionsClientEvent(BCIActivityCheckEntryConditionsClientEvent entryConditionClientEvent) {
        super(entryConditionClientEvent.getClientEvent(), entryConditionClientEvent.getResponse());
        this.entryConditionClientEvent = entryConditionClientEvent;
    }

   public BCIActivityCheckEntryConditionsClientEvent getEntryConditionClientEvent() {
        return entryConditionClientEvent;
    }
}