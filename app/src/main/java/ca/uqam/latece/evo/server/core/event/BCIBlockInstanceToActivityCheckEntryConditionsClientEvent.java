package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.ActivityInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;

public class BCIBlockInstanceToActivityCheckEntryConditionsClientEvent<A extends ActivityInstance> extends EvoClientEvent {
    A activityInstance;

    public BCIBlockInstanceToActivityCheckEntryConditionsClientEvent(ClientEvent clientEvent, ClientEventResponse response,
                                                                     A blockInstance) {
        super(clientEvent, response);
        this.activityInstance = blockInstance;
    }

    public A getActivityInstance() {
        return activityInstance;
    }
}
