package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;

public class BCIPhaseInstanceToBlockCheckEntryConditionClientEvent extends EvoClientEvent {
    BehaviorChangeInterventionBlockInstance blockInstance;

    public BCIPhaseInstanceToBlockCheckEntryConditionClientEvent(ClientEvent clientEvent, ClientEventResponse response,
                                                                 BehaviorChangeInterventionBlockInstance blockInstance) {
        super(clientEvent, response);
        this.blockInstance = blockInstance;
    }

    public BehaviorChangeInterventionBlockInstance getBlockInstance() {
        return blockInstance;
    }
}
