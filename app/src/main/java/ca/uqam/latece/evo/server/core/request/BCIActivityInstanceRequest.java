package ca.uqam.latece.evo.server.core.request;

import lombok.Builder;

/**
 * Represents a request for a bci activity instance.
 * It includes information related to changes from the frontend to the execution status of a BCI activity
 *
 * @author Julien Champagne.
 */
public class BCIActivityInstanceRequest extends EvoRequest {
    Long bciBlockInstanceId;

    Long bciPhaseInstanceId;

    Long bciInstanceId;

    @Builder(builderMethodName = "bciActivityInstanceRequestBuilder")
    public BCIActivityInstanceRequest(Long bciActivityInstanceId, Long bciBlockInstanceId, Long bciPhaseInstanceId,
                                   Long bciInstanceId) {
        super(bciActivityInstanceId);
        this.bciBlockInstanceId = bciBlockInstanceId;
        this.bciPhaseInstanceId = bciPhaseInstanceId;
        this.bciInstanceId = bciInstanceId;
    }

    public Long getBciBlockInstanceId() {
        return bciBlockInstanceId;
    }

    public void setBciBlockInstanceId(Long bciBlockInstanceId) {
        this.bciBlockInstanceId = bciBlockInstanceId;
    }

    public Long getBciPhaseInstanceId() {
        return bciPhaseInstanceId;
    }

    public void setBciPhaseInstanceId(Long bciPhaseInstanceId) {
        this.bciPhaseInstanceId = bciPhaseInstanceId;
    }

    public Long getBciInstanceId() {
        return bciInstanceId;
    }

    public void setBciInstanceId(Long bciInstanceId) {
        this.bciInstanceId = bciInstanceId;
    }
}
