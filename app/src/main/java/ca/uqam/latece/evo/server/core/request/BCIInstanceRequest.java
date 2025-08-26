package ca.uqam.latece.evo.server.core.request;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import lombok.*;

import java.time.LocalDate;

/**
 * Represents a BCI Instance Request.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Getter
public class BCIInstanceRequest extends ActivityInstanceRequest {

    // Attributes.
    protected Long patientId;
    protected Long currentPhaseId;
    protected Long behaviorChangeInterventionId;

    // Objects
    protected Patient patient;
    protected BehaviorChangeInterventionPhaseInstance currentPhase;
    protected BehaviorChangeIntervention behaviorChangeIntervention;


    /**
     * Constructs a new instance of BCIInstanceRequest, representing a behavior change intervention instance request.
     * @param id The unique identifier of the instance request.
     * @param status The execution status of the intervention instance request.
     * @param entryDate The date when the intervention instance request was entered.
     * @param exitDate The date when the intervention instance request was exited.
     * @param patientId The unique identifier of the patient associated with the intervention instance.
     * @param currentPhaseId The unique identifier of the current phase within the behavior change intervention instance.
     * @param behaviorChangeInterventionId The unique identifier of the behavior change intervention associated with the instance.
     * @param patient The patient object associated with this intervention instance.
     * @param currentPhase The current phase instance of the behavior change intervention.
     * @param behaviorChangeIntervention The behavior change intervention associated with this instance.
     */
    @Builder(builderMethodName = "bciInstanceRequestBuilder")
    public BCIInstanceRequest(Long id,
                              ExecutionStatus status,
                              LocalDate entryDate,
                              LocalDate exitDate,
                              Long patientId,
                              Long currentPhaseId, Long behaviorChangeInterventionId,
                              Patient patient,
                              BehaviorChangeInterventionPhaseInstance currentPhase,
                              BehaviorChangeIntervention behaviorChangeIntervention) {
        super(id, status, entryDate, exitDate);
        this.patientId = patientId;
        this.currentPhaseId = currentPhaseId;
        this.behaviorChangeInterventionId = behaviorChangeInterventionId;
        this.patient = patient;
        this.currentPhase = currentPhase;
        this.behaviorChangeIntervention = behaviorChangeIntervention;
    }

    /**
     * Resolves and retrieves the patient ID associated with the Behavior Change Intervention Instance.
     * The method checks whether the patient ID is directly available. If not, it attempts to retrieve the ID from the
     * associated patient object. If neither is available, it returns null.
     *
     * @return the patient ID or null if no patient information is available.
     */
    public Long resolvePatientId() {
        if (this.getPatientId() != null) {
            return this.getPatientId();
        }
        return this.getPatient() != null ? this.getPatient().getId() : null;
    }

    /**
     * Resolves and retrieves the current phase ID of the Behavior Change Intervention Instance.
     * The method first checks if the currentPhaseId is directly available and returns it if present. If not available,
     * it attempts to retrieve the ID from the currentPhase object. If neither is available, it returns null.
     *
     * @return the current phase ID, or null if no phase information is available.
     */
    public Long resolveCurrentPhaseId() {
        if (this.getCurrentPhaseId() != null) {
            return this.getCurrentPhaseId();
        }
        return this.getCurrentPhase() != null ? this.getCurrentPhase().getId() : null;
    }

    /**
     * Resolves and retrieves the ID of the Behavior Change Intervention associated with the Behavior Change Intervention Instance.
     * The method first checks if the behaviorChangeInterventionId is directly available and returns it if present.
     * If not available, it attempts to retrieve the ID from the behaviorChangeIntervention object.
     * If neither is available, it returns null.
     *
     * @return the Behavior Change Intervention ID, or null if no ID information is available.
     */
    public Long resolveBehaviorChangeInterventionId() {
        if (this.getBehaviorChangeInterventionId() != null) {
            return this.getBehaviorChangeInterventionId();
        }
        return this.getBehaviorChangeIntervention() != null ? this.getBehaviorChangeIntervention().getId() : null;
    }

}
