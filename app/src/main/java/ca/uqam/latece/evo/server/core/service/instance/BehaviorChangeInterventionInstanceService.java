package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ChangeAspect;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.event.BCIInstanceEvent;
import ca.uqam.latece.evo.server.core.event.BCIPhaseInstanceEvent;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionInstanceRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import jakarta.persistence.EntityNotFoundException;
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
public class BehaviorChangeInterventionInstanceService extends AbstractEvoService<BehaviorChangeInterventionInstance> {
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
            if (!updated.getStatus().equals(ExecutionStatus.UNKNOWN)) {
                this.publishEvent(new BCIInstanceEvent(updated));
            }
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
        return this.bciInstanceRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("BehaviorChangeInterventionInstance not found"));
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
}
