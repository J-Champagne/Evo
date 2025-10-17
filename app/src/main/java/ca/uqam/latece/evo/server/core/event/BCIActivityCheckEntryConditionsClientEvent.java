package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;

public class BCIActivityCheckEntryConditionsClientEvent extends EvoClientEvent {
    private Long bciActivityId;

    private Long bciBlockInstanceId;

    private Long bciPhaseInstanceId;

    private Long newBCIActivityId;

    private Long newBCIBlockInstanceId;

    private Long newBCIPhaseInstanceId;

    private boolean noFailedEntryConditions = true;

    private BehaviorChangeInterventionBlockInstance newBCIBlockInstance;

    private BehaviorChangeInterventionPhaseInstance newBCIPhaseInstance;

    public BCIActivityCheckEntryConditionsClientEvent(ClientEvent clientEvent, ClientEventResponse response,
                                                      Long bciActivityId, Long bciBlockInstanceId, Long bciPhaseInstanceId,
                                                      Long newBCIActivityId, Long newBCIBlockInstanceId, Long newBCIPhaseInstanceId) {
        super(clientEvent, response);
        this.bciActivityId = bciActivityId;
        this.bciBlockInstanceId = bciBlockInstanceId;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.newBCIActivityId = newBCIActivityId;
        this.newBCIBlockInstanceId = newBCIBlockInstanceId;
        this.newBCIPhaseInstanceId = newBCIPhaseInstanceId;
    }

    public Long getBciActivityId() {
        return bciActivityId;
    }

    public Long getBCIBlockInstanceId() {
        return bciBlockInstanceId;
    }

    public Long getBCIPhaseInstanceId() {
        return bciPhaseInstanceId;
    }

    public Long getNewBCIActivityId() {
        return newBCIActivityId;
    }

    public Long getNewBCIBlockInstanceId() {
        return newBCIBlockInstanceId;
    }

    public Long getNewBCIPhaseInstanceId() {
        return newBCIPhaseInstanceId;
    }

    public boolean isNoFailedEntryConditions() {
        return noFailedEntryConditions;
    }

    public void setNoFailedEntryConditions(boolean noFailedEntryConditions) {
        this.noFailedEntryConditions = noFailedEntryConditions;
    }

    public BehaviorChangeInterventionBlockInstance getNewBCIBlockInstance() {
        return newBCIBlockInstance;
    }

    public void setNewBCIBlockInstance(BehaviorChangeInterventionBlockInstance newBCIBlockInstance) {
        this.newBCIBlockInstance = newBCIBlockInstance;
    }

    public BehaviorChangeInterventionPhaseInstance getNewBCIPhaseInstance() {
        return newBCIPhaseInstance;
    }

    public void setNewBCIPhaseInstance(BehaviorChangeInterventionPhaseInstance newBCIPhaseInstance) {
        this.newBCIPhaseInstance = newBCIPhaseInstance;
    }
}