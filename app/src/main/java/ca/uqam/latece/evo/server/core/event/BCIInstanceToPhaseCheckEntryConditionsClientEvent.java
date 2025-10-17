package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;

public class BCIInstanceToPhaseCheckEntryConditionsClientEvent extends EvoClientEvent {
    BehaviorChangeInterventionPhaseInstance phaseInstance;

    public BCIInstanceToPhaseCheckEntryConditionsClientEvent(ClientEvent clientEvent, ClientEventResponse response,
                                                                     BehaviorChangeInterventionPhaseInstance phaseInstance) {
        super(clientEvent, response);
        this.phaseInstance = phaseInstance;
    }

    public BehaviorChangeInterventionPhaseInstance getPhaseInstance() {
        return phaseInstance;
    }
}
