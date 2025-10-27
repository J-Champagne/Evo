package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ChangeAspect;
import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.event.*;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionInstanceRepository;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.util.FailedConditions;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import ca.uqam.latece.evo.server.core.util.StringToLambdaConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class providing business operations for BehaviorChangeInterventionInstance entities.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class BehaviorChangeInterventionInstanceService extends AbstractBCIInstanceService<BehaviorChangeInterventionInstance, BCIInstanceClientEvent> {
    private final static Logger logger =  LoggerFactory.getLogger(BehaviorChangeInterventionInstanceService.class);

    @Autowired
    private BehaviorChangeInterventionInstanceRepository bciInstanceRepository;

    /**
     * Creates a BehaviorChangeInterventionInstance in the database.
     * @param bciInstance BehaviorChangeInterventionInstance.
     * @return The created BehaviorChangeInterventionInstance.
     * @throws IllegalArgumentException if bciInstance is null.
     */
    @Override
    public BehaviorChangeInterventionInstance create(BehaviorChangeInterventionInstance bciInstance) {
        BehaviorChangeInterventionInstance saved = null;

        saved = this.bciInstanceRepository.save(bciInstance);
        logger.info("BehaviorChangeInterventionInstance created: {}", saved);
        return saved;
    }

    /**
     * Updates a BehaviorChangeInterventionInstance in the database.
     * @param bciInstance BehaviorChangeInterventionInstance.
     * @return The updated BehaviorChangeInterventionInstance.
     * @throws IllegalArgumentException if bciInstance is null.
     */
    @Override
    public BehaviorChangeInterventionInstance update(BehaviorChangeInterventionInstance bciInstance) {
        BehaviorChangeInterventionInstance updated = null;
        BehaviorChangeInterventionInstance found = findById(bciInstance.getId());

//        ObjectValidator.validateObject(bciInstance);
//        ObjectValidator.validateObject(bciInstance.getStage());
//        ObjectValidator.validateObject(bciInstance.getActivities());

        if (found != null) {
            updated = this.bciInstanceRepository.save(bciInstance);
        }
        return updated;
    }

    /**
     * Updates the current phase of the given BehaviorChangeInterventionInstance. If the provided phase is different
     * from the current phase of the instance, the existing phase status is updated to Finished, and its exit date is set.
     * The new phase is marked as In Progress and assigned to the BehaviorChangeInterventionInstance.
     * @param bciInstance the BehaviorChangeInterventionInstance that needs to have its current phase updated.
     * @return the updated BehaviorChangeInterventionInstance with the applied phase change, or null if the instance was not found
     */
    public BehaviorChangeInterventionInstance changeCurrentPhase(BehaviorChangeInterventionInstance bciInstance){
        return this.changeCurrentPhase(bciInstance.getId(), bciInstance.getCurrentPhase());
    }

    /**
     * Updates the current phase of the given BehaviorChangeInterventionInstance id. If the provided phase is different
     * from the current phase of the instance, the existing phase status is updated to Finished, and its exit date is set.
     * The new phase is marked as In Progress and assigned to the BehaviorChangeInterventionInstance.
     * @param bciInstanceId the BehaviorChangeInterventionInstance id that needs to have its current phase updated.
     * @param currentPhase  the new BehaviorChangeInterventionPhaseInstance to be set as the current phase.
     * @return the updated BehaviorChangeInterventionInstance with the applied phase change, or null if the instance was not found
     */
    public BehaviorChangeInterventionInstance changeCurrentPhase(Long bciInstanceId,
                                                                 BehaviorChangeInterventionPhaseInstance currentPhase) {
        BehaviorChangeInterventionInstance updated = null;
        BehaviorChangeInterventionInstance found = findById(bciInstanceId);

        ObjectValidator.validateObject(currentPhase);
        ObjectValidator.validateId(currentPhase.getId());

        if (found != null) {
            // If the current phase is different, update the phase status to Finished, change the stage to End, and
            // set the Exit date.
            if (found.getCurrentPhase() != null && !found.getCurrentPhase().getId().equals(currentPhase.getId())) {
                // Get the current phase.
                BehaviorChangeInterventionPhaseInstance phase = found.getCurrentPhase();

                // Change the status of the current phase to Finished and update the Exit date.
                phase.setStatus(ExecutionStatus.FINISHED);
                phase.setExitDate(LocalDate.now());

                // Publish the current phase updated.
                this.publishEvent(new BCIPhaseInstanceEvent(phase), TimeCycle.END);
            }

            // Set the new the current phase properties.
            currentPhase.setStatus(ExecutionStatus.IN_PROGRESS);
            currentPhase.setEntryDate(LocalDate.now());
            // Set the current block in the Phase.
            found.setCurrentPhase(currentPhase);
            // Update the BehaviorChangeInterventionInstance in the database.
            updated = this.update(found);
        }

        return updated;
    }

    /**
     * Saves the given BehaviorChangeInterventionInstance in the database.
     * @param bciInstance BehaviorChangeInterventionInstance.
     * @return The saved BehaviorChangeInterventionInstance.
     * @throws IllegalArgumentException if bciInstance is null.
     */
    @Override
    public BehaviorChangeInterventionInstance save(BehaviorChangeInterventionInstance bciInstance) {
        return this.bciInstanceRepository.save(bciInstance);
    }

    /**
     * Deletes a BehaviorChangeInterventionInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        bciInstanceRepository.deleteById(id);
        logger.info("BehaviorChangeInterventionInstance deleted {}", id);
    }

    /**
     * Finds all BehaviorChangeInterventionInstance entities.
     * @return List<BehaviorChangeInterventionInstance>.
     */
    public List<BehaviorChangeInterventionInstance> findAll() {
        return this.bciInstanceRepository.findAll();
    }

    /**
     * Finds a BehaviorChangeInterventionInstance by its id.
     * @param id Long.
     * @return BehaviorChangeInterventionInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public BehaviorChangeInterventionInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciInstanceRepository.findById(id).orElse(null);
    }

    /**
     * Finds a BehaviorChangeInterventionInstance by their patient id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> with the given patient id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionInstance> findByPatientId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciInstanceRepository.findByPatientId(id);
    }

    /**
     * Finds a BehaviorChangeInterventionInstance by their currentPhase id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> with the given currentPhase id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionInstance> findByCurrentPhaseId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciInstanceRepository.findByCurrentPhaseId(id);
    }

    /**
     * Finds BehaviorChangeInterventionInstance entities by a BehaviorChangeInterventionPhaseInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> with the given BehaviorChangeInterventionPhaseInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionInstance> findByActivitiesId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciInstanceRepository.findByActivitiesId(id);
    }

    /**
     * Checks if a BehaviorChangeInterventionInstance exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciInstanceRepository.existsById(id);
    }

    /**
     * Retrieves a BehaviorChangeInterventionInstance based on its id and the id of its current phase.
     * @param id the id of the BehaviorChangeInterventionInstance to retrieve.
     * @param currentPhaseId the id of the current phase associated with the intervention instance.
     * @return the BehaviorChangeInterventionInstance matching the specified id and currentPhaseId, or null if no such
     * instance exists
     * @throws IllegalArgumentException if id or currentPhaseId is null.
     */
    public BehaviorChangeInterventionInstance findByIdAndCurrentPhaseId(Long id, Long currentPhaseId) {
        ObjectValidator.validateId(id);
        ObjectValidator.validateId(currentPhaseId);
        return this.bciInstanceRepository.findByIdAndCurrentPhaseId(id, currentPhaseId);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionInstance entities
     * associated with the specified behavior change intervention ID.
     *
     * @param id the ID of the behavior change intervention to find associated instances for;
     *           must not be null or invalid.
     * @return a list of BehaviorChangeInterventionInstance entities associated with the given ID.
     *         Returns an empty list if no instances are found.
     * @throws IllegalArgumentException if the id is null.
     */
    public List<BehaviorChangeInterventionInstance> findByBehaviorChangeInterventionId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciInstanceRepository.findByBehaviorChangeInterventionId(id);
    }

    /**
     * Retrieves a list of BehaviorChangeInterventionInstance entities that have a status of READY and match the specified
     * patient ID.
     * @param patientId the unique identifier of the patient for which to find instances.
     * @return a list of BehaviorChangeInterventionInstance entities with a status of READY associated with the specified
     * patient ID.
     * @throws IllegalArgumentException if the patientId is null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusReadyAndPatientId(Long patientId) {
        return this.findByStatusAndPatientId(ExecutionStatus.READY, patientId);
    }

    /**
     * Retrieves a list of BehaviorChangeInterventionInstance entities that have a status of IN_PROGRESS and match the
     * specified patient ID.
     * @param patientId the unique identifier of the patient for which to find instances.
     * @return a list of BehaviorChangeInterventionInstance entities with a status of IN_PROGRESS associated with the
     * specified patient ID.
     * @throws IllegalArgumentException if the patientId is null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusInProgressAndPatientId(Long patientId) {
        return this.findByStatusAndPatientId(ExecutionStatus.IN_PROGRESS, patientId);
    }

    /**
     * Retrieves a list of BehaviorChangeInterventionInstance entities that have a status of FINISHED and match the
     * specified patient ID.
     * @param patientId the unique identifier of the patient for which to find instances.
     * @return a list of BehaviorChangeInterventionInstance entities with a status of FINISHED associated with the
     * specified patient ID.
     * @throws IllegalArgumentException if the patientId is null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusFinishedAndPatientId(Long patientId) {
        return this.findByStatusAndPatientId(ExecutionStatus.FINISHED, patientId);
    }

    /**
     * Retrieves a list of BehaviorChangeInterventionInstance entities that have a status of UNKNOWN and match the
     * specified patient ID.
     * @param patientId the unique identifier of the patient for which to find instances.
     * @return a list of BehaviorChangeInterventionInstance entities with a status of UNKNOWN associated with the
     * specified patient ID.
     * @throws IllegalArgumentException if the patientId is null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusUnknowAndPatientId(Long patientId) {
        return this.findByStatusAndPatientId(ExecutionStatus.UNKNOWN, patientId);
    }

    /**
     * Retrieves a list of BehaviorChangeInterventionInstance entities that have a status of STALLED and match the
     * specified patient ID.
     * @param patientId the unique identifier of the patient for which to find instances.
     * @return a list of BehaviorChangeInterventionInstance entities with a status of STALLED associated with the
     * specified patient ID.
     * @throws IllegalArgumentException if the patientId is null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusStalledAndPatientId(Long patientId) {
        return this.findByStatusAndPatientId(ExecutionStatus.STALLED, patientId);
    }

    /**
     * Retrieves a list of BehaviorChangeInterventionInstance entities that have a status of SUSPENDED and match the
     * specified patient ID.
     * @param patientId the unique identifier of the patient for which to find instances.
     * @return a list of BehaviorChangeInterventionInstance entities with a status of SUSPENDED associated with the
     * specified patient ID.
     * @throws IllegalArgumentException if the patientId is null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusSuspendedAndPatientId(Long patientId) {
        return this.findByStatusAndPatientId(ExecutionStatus.SUSPENDED, patientId);
    }

    /**
     * Retrieves a list of BehaviorChangeInterventionInstance objects based on the provided status and patient ID.
     * @param status the execution status to filter the instances by, must not be null.
     * @param patientId the ID of the patient whose instances are to be retrieved, must not be null.
     * @return a list of BehaviorChangeInterventionInstance objects matching the provided status and patient ID.
     * @throws IllegalArgumentException if status or patientId is null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusAndPatientId(ExecutionStatus status, Long patientId) {
        ObjectValidator.validateObject(status);
        ObjectValidator.validateId(patientId);
        return this.bciInstanceRepository.findByStatusAndPatientId(status, patientId);
    }

    /**
     * Finds a list of BehaviorChangeInterventionInstances filtered by status set to IN_PROGRESS, the given patient ID,
     * and the given current phase ID.
     * @param patientId the unique identifier of the patient whose intervention instances are being searched.
     * @param currentPhaseId the unique identifier of the current phase to filter the intervention instances.
     * @return a list of BehaviorChangeInterventionInstance objects matching the specified criteria.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusInProgressAndPatientIdAndCurrentPhaseId(Long patientId,
                                                                                                        Long currentPhaseId) {
        return this.findByStatusAndPatientIdAndCurrentPhaseId(ExecutionStatus.IN_PROGRESS, patientId, currentPhaseId);
    }

    /**
     * Finds a list of BehaviorChangeInterventionInstances filtered by status set to FINISHED, the given patient ID,
     * and the given current phase ID.
     * @param patientId the unique identifier of the patient whose intervention instances are being searched.
     * @param currentPhaseId the unique identifier of the current phase to filter the intervention instances.
     * @return a list of BehaviorChangeInterventionInstance objects matching the specified criteria.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusFinishedAndPatientIdAndCurrentPhaseId(Long patientId,
                                                                                                      Long currentPhaseId) {
        return this.findByStatusAndPatientIdAndCurrentPhaseId(ExecutionStatus.FINISHED, patientId, currentPhaseId);
    }

    /**
     * Finds a list of BehaviorChangeInterventionInstances filtered by status set to READY, the given patient ID,
     * and the given current phase ID.
     * @param patientId the unique identifier of the patient whose intervention instances are being searched.
     * @param currentPhaseId the unique identifier of the current phase to filter the intervention instances.
     * @return a list of BehaviorChangeInterventionInstance objects matching the specified criteria.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusReadyAndPatientIdAndCurrentPhaseId(Long patientId,
                                                                                                   Long currentPhaseId) {
        return this.findByStatusAndPatientIdAndCurrentPhaseId(ExecutionStatus.READY, patientId, currentPhaseId);
    }

    /**
     * Finds a list of BehaviorChangeInterventionInstances filtered by status set to SUSPENDED, the given patient ID,
     * and the given current phase ID.
     * @param patientId the unique identifier of the patient whose intervention instances are being searched.
     * @param currentPhaseId the unique identifier of the current phase to filter the intervention instances.
     * @return a list of BehaviorChangeInterventionInstance objects matching the specified criteria.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusSuspendedAndPatientIdAndCurrentPhaseId(Long patientId,
                                                                                                       Long currentPhaseId) {
        return this.findByStatusAndPatientIdAndCurrentPhaseId(ExecutionStatus.SUSPENDED, patientId, currentPhaseId);
    }

    /**
     * Finds a list of BehaviorChangeInterventionInstances filtered by status set to STALLED, the given patient ID,
     * and the given current phase ID.
     * @param patientId the unique identifier of the patient whose intervention instances are being searched.
     * @param currentPhaseId the unique identifier of the current phase to filter the intervention instances.
     * @return a list of BehaviorChangeInterventionInstance objects matching the specified criteria.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusStalledAndPatientIdAndCurrentPhaseId(Long patientId,
                                                                                                     Long currentPhaseId) {
        return this.findByStatusAndPatientIdAndCurrentPhaseId(ExecutionStatus.STALLED, patientId, currentPhaseId);
    }

    /**
     * Finds a list of BehaviorChangeInterventionInstances filtered by status set to UNKNOWN, the given patient ID,
     * and the given current phase ID.
     * @param patientId the unique identifier of the patient whose intervention instances are being searched.
     * @param currentPhaseId the unique identifier of the current phase to filter the intervention instances.
     * @return a list of BehaviorChangeInterventionInstance objects matching the specified criteria.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusUnknowAndPatientIdAndCurrentPhaseId(Long patientId,
                                                                                                    Long currentPhaseId) {
        return this.findByStatusAndPatientIdAndCurrentPhaseId(ExecutionStatus.UNKNOWN, patientId, currentPhaseId);
    }

    /**
     * Retrieves a list of BehaviorChangeInterventionInstance objects filtered by the given status, patient ID, and current phase ID.
     * @param status The execution status used as a filter for the query.
     * @param patientId The ID of the patient used as a filter for the query.
     * @param currentPhaseId The ID of the current phase used as a filter for the query.
     * @return A list of BehaviorChangeInterventionInstance objects that match the specified status, patient ID, and current phase ID.
     *         Returns an empty list if no matching objects are found.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusAndPatientIdAndCurrentPhaseId(ExecutionStatus status,
                                                                                              Long patientId,
                                                                                              Long currentPhaseId) {
        ObjectValidator.validateObject(status);
        ObjectValidator.validateId(patientId);
        ObjectValidator.validateId(currentPhaseId);
        return this.bciInstanceRepository.findByStatusAndPatientIdAndCurrentPhaseId(status, patientId, currentPhaseId);
    }

    /**
     * Retrieves a list of BehaviorChangeInterventionInstance entities based on their execution status, associated patient ID,
     * current phase ID, and the status of the current phase.
     * @param status the execution status (READY, IN_PROGRESS, SUSPENDED, STALLED, FINISHED, or UNKNOWN) of the behavior change
     *               intervention instances to retrieve.
     * @param patientId the patient id for whom the behavior change interventions are associated.
     * @param currentPhaseId The ID of the current phase used as a filter for the query.
     * @param currentPhaseStatus the execution status of the current phase of the behavior change interventions.
     * @return a list of BehaviorChangeInterventionInstance entities that meet the specified criteria or an empty list
     * if no instances match the provided parameters.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public List<BehaviorChangeInterventionInstance> findByStatusAndPatientIdAndCurrentPhaseIdAndCurrentPhaseStatus(
            ExecutionStatus status, Long patientId, Long currentPhaseId, ExecutionStatus currentPhaseStatus) {
        ObjectValidator.validateObject(status);
        ObjectValidator.validateId(patientId);
        ObjectValidator.validateId(currentPhaseId);
        ObjectValidator.validateObject(currentPhaseStatus);
        return this.bciInstanceRepository.findByStatusAndPatientIdAndCurrentPhaseIdAndCurrentPhaseStatus(status, patientId,
                currentPhaseId, currentPhaseStatus);
    }

    /**
     * Retrieves a BehaviorChangeInterventionInstance object by its ID, status, and associated patient ID.
     * @param id the unique identifier of the behavior change intervention instance.
     * @param status the execution status (READY, IN_PROGRESS, SUSPENDED, STALLED, FINISHED, or UNKNOWN) of the behavior
     *               change intervention instance.
     * @param patientId the unique identifier of the associated patient.
     * @return the BehaviorChangeInterventionInstance matching the specified criteria, or null if no match is found.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public BehaviorChangeInterventionInstance findByIdAndStatusAndPatientId(Long id, ExecutionStatus status, Long patientId) {
        ObjectValidator.validateId(id);
        ObjectValidator.validateObject(status);
        ObjectValidator.validateId(patientId);
        return this.bciInstanceRepository.findByIdAndStatusAndPatientId(id, status, patientId);
    }

    /**
     * Retrieves a BehaviorChangeInterventionInstance based on its ID, status, and associated patient.
     * @param id the unique identifier of the BehaviorChangeInterventionInstance
     * @param status the execution status (READY, IN_PROGRESS, SUSPENDED, STALLED, FINISHED, or UNKNOWN) of the
     *               BehaviorChangeInterventionInstance.
     * @param patient the patient associated with the BehaviorChangeInterventionInstance.
     * @return the matching BehaviorChangeInterventionInstance if found, or null if no match is found.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public BehaviorChangeInterventionInstance findByIdAndStatusAndPatient(Long id, ExecutionStatus status, Patient patient) {
        ObjectValidator.validateId(id);
        ObjectValidator.validateObject(status);
        ObjectValidator.validateObject(patient);
        return this.bciInstanceRepository.findByIdAndStatusAndPatient(id, status, patient);
    }

    /**
     * Finds a BehaviorChangeInterventionInstance by its unique identifier and associated patient.
     * @param id the unique identifier of the BehaviorChangeInterventionInstance to retrieve.
     * @param patient the patient associated with the BehaviorChangeInterventionInstance.
     * @return the BehaviorChangeInterventionInstance that matches the given id and patient, or null if no match is found.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public BehaviorChangeInterventionInstance findByIdAndPatient(Long id, Patient patient) {
        ObjectValidator.validateId(id);
        ObjectValidator.validateObject(patient);
        return this.bciInstanceRepository.findByIdAndPatient(id, patient);
    }

    /**
     * Retrieves a BehaviorChangeInterventionInstance by its unique identifier and associated patient ID.
     * @param id the unique identifier of the BehaviorChangeInterventionInstance to be retrieved.
     * @param patientId the unique identifier of the patient associated with the instance.
     * @return the BehaviorChangeInterventionInstance that matches the provided id and patientId, or null if no match is found.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public BehaviorChangeInterventionInstance findByIdAndPatientId(Long id, Long patientId) {
        ObjectValidator.validateId(id);
        ObjectValidator.validateId(patientId);
        return this.bciInstanceRepository.findByIdAndPatientId(id, patientId);
    }

    /**
     * Finds a BehaviorChangeInterventionInstance by its unique identifier, execution status, associated patient ID, and
     * the ID of the current phase.
     * @param id the unique identifier of the BehaviorChangeInterventionInstance.
     * @param status the execution status of the BehaviorChangeInterventionInstance.
     * @param patientId the unique identifier of the associated patient.
     * @param currentPhaseId the unique identifier of the current phase.
     * @return the BehaviorChangeInterventionInstance that matches the specified criteria, or null if no such instance is found.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public BehaviorChangeInterventionInstance findByIdAndStatusAndPatientIdAndCurrentPhaseId(Long id, ExecutionStatus status,
                                                                                             Long patientId,
                                                                                             Long currentPhaseId) {
        ObjectValidator.validateId(id);
        ObjectValidator.validateObject(status);
        ObjectValidator.validateId(patientId);
        ObjectValidator.validateId(currentPhaseId);
        return this.bciInstanceRepository.findByIdAndStatusAndPatientIdAndCurrentPhaseId(id, status, patientId, currentPhaseId);
    }

    /**
     * Finds a BehaviorChangeInterventionInstance by its unique ID, status, associated patient, and the current phase ID.
     * @param id the unique identifier of the BehaviorChangeInterventionInstance.
     * @param status the execution status (READY, IN_PROGRESS, SUSPENDED, STALLED, FINISHED, or UNKNOWN) of the intervention instance.
     * @param patient the patient associated with the intervention instance.
     * @param currentPhaseId the identifier of the current phase of the intervention instance.
     * @return the matching BehaviorChangeInterventionInstance, or null if no match is found.
     * @throws IllegalArgumentException if any of the parameters are null.
     */
    public BehaviorChangeInterventionInstance findByIdAndStatusAndPatientAndCurrentPhaseId(Long id, ExecutionStatus status,
                                                                                           Patient patient,
                                                                                           Long currentPhaseId) {
        ObjectValidator.validateId(id);
        ObjectValidator.validateObject(status);
        ObjectValidator.validateObject(patient);
        ObjectValidator.validateId(currentPhaseId);
        return this.bciInstanceRepository.findByIdAndStatusAndPatientAndCurrentPhaseId(id, status, patient, currentPhaseId);

    }

    /**
     * Handles events of type BCIInstanceEvent and processes them under certain conditions. Updates the corresponding
     * model when the event's change aspect is TERMINATED and the time cycle is END.
     * @param event the BCIInstanceEvent to be handled. It contains information about the change aspect, time cycle,
     *              and the associated model.
     */
    @EventListener(BCIInstanceEvent.class)
    public void handleBCIInstanceEvents(BCIInstanceEvent event) {
        if (event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                event.getEvoModel().getStatus().equals(ExecutionStatus.FINISHED) &&
                event.getTimeCycle().equals(TimeCycle.END)) {
            this.update(event.getEvoModel());
            event.setChangeAspect(ChangeAspect.TERMINATED);
        }
    }

    /**
     * Handles BCIInstanceClientEvent by updating the corresponding BehaviorChangeInterventionInstance
     * when specific conditions related to its execution status are met.
     * @param event the BCIInstanceClientEvent to be processed, which contains information about the
     *              BCIInstance and its state changes.
     */
    @Override
    @EventListener(BCIInstanceClientEvent.class)
    protected ClientEventResponse handleClientEvent(BCIInstanceClientEvent event) {
        BehaviorChangeInterventionInstance bciInstance = null;
        BehaviorChangeInterventionPhaseInstance phaseInstance = null;
        ClientEventResponse response = null;
        ClientEvent clientEvent = null;
        FailedConditions failedConditions = new FailedConditions();
        boolean statusUpdated = false;
        boolean phaseUpdated = false;

        if (event != null && event.getBCIPhaseInstance() != null && event.getClientEvent() != null && event.getBciInstanceId() != null
                && event.getResponse() != null) {
            bciInstance = findById(event.getBciInstanceId());
            phaseInstance = event.getBCIPhaseInstance();
            response = event.getResponse();

            if (bciInstance != null) {

                //Handle ClientEvents
                clientEvent = event.getClientEvent();
                switch(clientEvent) {
                    case ClientEvent.FINISH -> {
                        phaseUpdated = setNextCurrentPhase(bciInstance, phaseInstance, bciInstance.getActivities());
                        if (!phaseUpdated) {
                            if(checkIfAllPhaseAreFinished(bciInstance)) {
                                statusUpdated = super.handleClientEventFinish(bciInstance, failedConditions);
                            }
                        } else {
                            checkEntryConditionsForPhaseInBCIInstance(clientEvent, response, bciInstance.getCurrentPhase());
                        }
                    }

                    case ClientEvent.IN_PROGRESS -> {
                        if (event.getEntryConditionEvent().getNewBCIPhaseInstance() != null) {
                            bciInstance.setCurrentPhase(event.getEntryConditionEvent().getNewBCIPhaseInstance());
                            phaseUpdated = true;
                        }
                    }
                }

                //Update the response with information from BCIInstance
                response.addResponse(BehaviorChangeInterventionInstance.class.getSimpleName(), bciInstance.getId(), bciInstance.getStatus(),
                        failedConditions.getFailedEntryConditions(), failedConditions.getFailedExitConditions());

                //Update the entity
                if (statusUpdated || phaseUpdated) {
                    this.update(bciInstance);
                }
            }
        }

        return response;
    }

    /**
     * Checks if a BCIBlockInstance has met its entry conditions.
     * @param bciInstance The BCIActivity to retrieve the entry conditions from.
     * @return All the entry conditions that were not met.
     */
    @Override
    public String checkEntryConditions(BehaviorChangeInterventionInstance bciInstance) {
        String condition = bciInstance.getBehaviorChangeIntervention().getEntryConditions();

        if (StringToLambdaConverter.convertConditionStringToLambda(condition)) {
            condition = "";
        }

        return condition;
    }

    /**
     * Checks if a BCIBlockInstance has met its exit conditions.
     * @param bciInstance The BCIActivity to retrieve the exit conditions from.
     * @return All the exit conditions that were not met.
     */
    @Override
    public String checkExitConditions(BehaviorChangeInterventionInstance bciInstance) {
        String condition = bciInstance.getBehaviorChangeIntervention().getExitConditions();

        if (StringToLambdaConverter.convertConditionStringToLambda(condition)) {
            condition = "";
        }

        return condition;
    }

    /**
     *
     * @param phaseInstance
     */
    protected void checkEntryConditionsForPhaseInBCIInstance(ClientEvent event, ClientEventResponse response,
                                                             BehaviorChangeInterventionPhaseInstance phaseInstance) {
        //Check entry conditions for Phases was omitted because activities in bciInstance are assumed to be sequential
        publishEvent(new BCIInstanceToPhaseCheckEntryConditionsClientEvent(event, response, phaseInstance));

    }

    /**
     * Updates the currentPhase with the next one present in the list of activities of a BCIInstance.
     * The currentPhase will not be updated if currentPhase is the last one present in the activities of the BCIInstance.
     * @param bciInstance the BCIInstance to be updated
     * @param phaseInstance the new currentPhase
     * @param activities the list of BCIPhaseInstance of bciInstance
     * @return true if the currentPhase was updated
     */
    private boolean setNextCurrentPhase(BehaviorChangeInterventionInstance bciInstance, BehaviorChangeInterventionPhaseInstance phaseInstance,
                                     List<BehaviorChangeInterventionPhaseInstance> activities) {
        boolean phaseUpdated = false;

        for (int i = 0; i < activities.size(); i++) {
            BehaviorChangeInterventionPhaseInstance activity = activities.get(i);

            if (activity.getId().equals(phaseInstance.getId())) {
                int nextIndex = i + 1;
                if (nextIndex < activities.size()) {
                    BehaviorChangeInterventionPhaseInstance newPhaseInstance = activities.get(nextIndex);
                    newPhaseInstance.setStatus(ExecutionStatus.IN_PROGRESS);
                    bciInstance.setCurrentPhase(newPhaseInstance);
                    phaseUpdated = true;

                }

                break;
            }
        }

        return phaseUpdated;
    }

    /**
     * Checks if every phaseInstance in a BCIInstance have the status of FINISHED
     * @return true if every phase instance has status of FINISHED
     */
    private boolean checkIfAllPhaseAreFinished(BehaviorChangeInterventionInstance bciInstance) {
        for (BehaviorChangeInterventionPhaseInstance phaseInstance : bciInstance.getActivities()) {
            if (phaseInstance.getStatus() != ExecutionStatus.FINISHED) {
                return false;
            }
        }

        return true;
    }
}
