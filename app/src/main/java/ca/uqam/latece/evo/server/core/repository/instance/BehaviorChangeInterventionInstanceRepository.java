package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BehaviorChangeInterventionInstance repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface BehaviorChangeInterventionInstanceRepository extends EvoRepository<BehaviorChangeInterventionInstance> {
    /**
     * Finds BehaviorChangeInterventionInstance entities by their patient id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> with the given patient id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BehaviorChangeInterventionInstance> findByPatientId(Long id);

    /**
     * Finds BehaviorChangeInterventionInstance entities by their currentPhase id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> with the given currentPhase id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BehaviorChangeInterventionInstance> findByCurrentPhaseId(Long id);

    /**
     * Finds BehaviorChangeInterventionInstance entities by their phases id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> with the given phases id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BehaviorChangeInterventionInstance> findByActivitiesId(Long id);

    /**
     * Retrieves a BehaviorChangeInterventionInstance based on its id and the id of its current phase.
     * @param id the id of the BehaviorChangeInterventionInstance to retrieve.
     * @param currentPhaseId the id of the current phase associated with the intervention instance.
     * @return the BehaviorChangeInterventionInstance matching the specified id and currentPhaseId, or null if no such
     * instance exists
     * @throws IllegalArgumentException if id or currentPhaseId is null.
     */
    BehaviorChangeInterventionInstance findByIdAndCurrentPhaseId(Long id, Long currentPhaseId);

    /**
     * Finds BehaviorChangeInterventionInstance entities by their associated BehaviorChangeIntervention id.
     * @param id the id of the BehaviorChangeIntervention associated with the intervention instances.
     * @return a list of BehaviorChangeInterventionInstance objects associated with the specified BehaviorChangeIntervention id.
     * @throws IllegalArgumentException if the id is null.
     */
    List<BehaviorChangeInterventionInstance> findByBehaviorChangeInterventionId(Long id);

    /**
     * Finds a list of BehaviorChangeInterventionInstance entities based on their status and the associated patient ID.
     * @param status the status of the BehaviorChangeInterventionInstance (READY, IN_PROGRESS, SUSPENDED, STALLED, FINISHED,
     *               or UNKNOWN). Must not be null.
     * @param patientId the unique identifier of the patient associated with the BehaviorChangeInterventionInstance.
     *                  Must not be null.
     * @return a list of BehaviorChangeInterventionInstance objects that match the given status and patient ID, or an empty
     * list if no matching entities are found.
     * @throws IllegalArgumentException if status or patientId is null.
     */
    List<BehaviorChangeInterventionInstance> findByStatusAndPatientId(@NotNull ExecutionStatus status,
                                                                      @NotNull Long patientId);

    /**
     * Finds a list of BehaviorChangeInterventionInstance objects that match the provided ID, execution status, and patient ID.
     * @param id the unique identifier of the behavior change intervention instance; must not be null.
     * @param status the execution status (READY, IN_PROGRESS, SUSPENDED, STALLED, FINISHED, or UNKNOWN) of the intervention instance;
     *               must not be null.
     * @param patientId the identifier of the patient associated with the intervention instance; must not be null.
     * @return a BehaviorChangeInterventionInstance that match the given ID, status, and patient ID. Otherwise, null.
     * @throws IllegalArgumentException if id, status or patientId is null.
     */
    BehaviorChangeInterventionInstance findByIdAndStatusAndPatientId(@NotNull Long id,
                                                                     @NotNull ExecutionStatus status,
                                                                     @NotNull Long patientId);

    /**
     * Retrieves a BehaviorChangeInterventionInstance based on its unique identifier, execution status, and the associated patient entity.
     * @param id the unique identifier of the BehaviorChangeInterventionInstance; must not be null.
     * @param status the execution status (READY, IN_PROGRESS, SUSPENDED, STALLED, FINISHED, or UNKNOWN) of the
     *               BehaviorChangeInterventionInstance; must not be null.
     * @param patient the patient entity associated with the BehaviorChangeInterventionInstance; must not be null.
     * @return the matching BehaviorChangeInterventionInstance, or null if no matching entity is found.
     * @throws IllegalArgumentException if any of the parameters (id, status, or patient) are null.
     */
    BehaviorChangeInterventionInstance findByIdAndStatusAndPatient(@NotNull Long id,
                                                                   @NotNull ExecutionStatus status,
                                                                   @NotNull Patient patient);

    /**
     * Retrieves a BehaviorChangeInterventionInstance based on its unique identifier and the associated patient entity.
     * @param id the unique identifier of the BehaviorChangeInterventionInstance to retrieve; must not be null.
     * @param patient the patient entity associated with the BehaviorChangeInterventionInstance; must not be null.
     * @return the matching BehaviorChangeInterventionInstance, or null if no such instance exists.
     * @throws IllegalArgumentException if id or patient is null.
     */
    BehaviorChangeInterventionInstance findByIdAndPatient(@NotNull Long id, @NotNull Patient patient);

    /**
     * Retrieves a BehaviorChangeInterventionInstance by its unique identifier and associated patient identifier.
     * @param id the unique identifier of the BehaviorChangeInterventionInstance, must not be null.
     * @param patientId the unique identifier of the associated patient, must not be null.
     * @return the BehaviorChangeInterventionInstance that matches the given id and patientId, or null if no match is found.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    BehaviorChangeInterventionInstance findByIdAndPatientId(@NotNull Long id, @NotNull Long patientId);

    /**
     * Finds a list of BehaviorChangeInterventionInstance entities based on the specified status, patient ID, and current phase ID.
     * @param status the execution status of the intervention instances to be retrieved.
     * @param patientId the ID of the patient associated with the intervention instances to be retrieved.
     * @param currentPhaseId the ID of the current phase associated with the intervention instances to be retrieved.
     * @return a list of BehaviorChangeInterventionInstance objects matching the specified criteria.
     * @throws IllegalArgumentException if status, patientId, or currentPhaseId is null.
     */
    List<BehaviorChangeInterventionInstance> findByStatusAndPatientIdAndCurrentPhaseId(@NotNull ExecutionStatus status,
                                                                                       @NotNull Long patientId,
                                                                                       @NotNull Long currentPhaseId);

    /**
     * Finds a list of BehaviorChangeInterventionInstance entities that match the specified criteria: ID, status, patient ID,
     * and current phase ID.
     * @param id the unique identifier of the BehaviorChangeInterventionInstance must not be null.
     * @param status the execution status of the BehaviorChangeInterventionInstance must not be null.
     * @param patientId the unique identifier of the patient associated with the BehaviorChangeInterventionInstance must not be null.
     * @param currentPhaseId the current phase id associated with the BehaviorChangeInterventionInstance must not be null.
     * @return a BehaviorChangeInterventionInstance entity that satisfies the specified criteria. Otherwise, null.
     * @throws IllegalArgumentException if id, status, patientId, or currentPhaseId is null.
     */
    BehaviorChangeInterventionInstance findByIdAndStatusAndPatientIdAndCurrentPhaseId(@NotNull Long id,
                                                                                      @NotNull ExecutionStatus status,
                                                                                      @NotNull Long patientId,
                                                                                      @NotNull Long currentPhaseId);

    /**
     * Finds a {@link BehaviorChangeInterventionInstance} entity that matches the specified ID, execution status, associated patient,
     * and current phase ID.
     * @param id the unique identifier of the BehaviorChangeInterventionInstance. Must not be null.
     * @param status the execution status (READY, IN_PROGRESS, SUSPENDED, STALLED, FINISHED, or UNKNOWN) of the
     *               BehaviorChangeInterventionInstance. Must not be null.
     * @param patient the patient associated with the BehaviorChangeInterventionInstance. Must not be null.
     * @param currentPhaseId the ID of the current phase associated with the BehaviorChangeInterventionInstance. Must not be null.
     * @return a BehaviorChangeInterventionInstance that matches the given criteria, or null if no matching entity is found.
     * @throws IllegalArgumentException if any parameter is null.
     */
    BehaviorChangeInterventionInstance findByIdAndStatusAndPatientAndCurrentPhaseId(@NotNull Long id,
                                                                                    @NotNull ExecutionStatus status,
                                                                                    @NotNull Patient patient,
                                                                                    @NotNull Long currentPhaseId);

    /**
     * Finds a list of BehaviorChangeInterventionInstance objects matching the specified status, patient ID, current phase ID,
     * and current phase status.
     * @param status the execution status (READY, IN_PROGRESS, SUSPENDED, STALLED, FINISHED, or UNKNOWN) of the BehaviorChangeInterventionInstance to search for, must not be null.
     * @param patientId the ID of the patient associated with the BehaviorChangeInterventionInstance must not be null.
     * @param currentPhaseId the ID of the current phase associated with the BehaviorChangeInterventionInstance must not be null.
     * @param currentPhaseStatus the execution status (READY, IN_PROGRESS, SUSPENDED, STALLED, FINISHED, or UNKNOWN) of the current phase associated with the BehaviorChangeInterventionInstance,
     *                           must not be null.
     * @return a list of BehaviorChangeInterventionInstance objects matching the given criteria, or an empty list if no
     * matching instances are found.
     * @throws IllegalArgumentException if status, patientId, currentPhaseId, or currentPhaseStatus is null.
     */
    List<BehaviorChangeInterventionInstance> findByStatusAndPatientIdAndCurrentPhaseIdAndCurrentPhaseStatus(@NotNull ExecutionStatus status,
                                                                                                            @NotNull Long patientId,
                                                                                                            @NotNull Long currentPhaseId,
                                                                                                            @NotNull ExecutionStatus currentPhaseStatus);
}
