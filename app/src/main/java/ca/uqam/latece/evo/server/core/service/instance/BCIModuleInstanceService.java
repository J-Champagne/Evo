package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ChangeAspect;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.event.BCIModuleInstanceEvent;
import ca.uqam.latece.evo.server.core.model.instance.BCIModuleInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BCIModuleInstanceRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * BCIModuleInstance Service.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class BCIModuleInstanceService extends AbstractEvoService<BCIModuleInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BCIModuleInstanceService.class);

    @Autowired
    private BCIModuleInstanceRepository bciModuleInstanceRepository;

    /**
     * Creates a BCIModuleInstance in the database.
     * @param moduleInstance BCIModuleInstance.
     * @return The created BCIModuleInstance.
     * @throws IllegalArgumentException if moduleInstance is null.
     */
    @Override
    public BCIModuleInstance create(BCIModuleInstance moduleInstance) {
        BCIModuleInstance saved = null;

        ObjectValidator.validateObject(moduleInstance);
        ObjectValidator.validateObject(moduleInstance.getOutcome());
        ObjectValidator.validateObject(moduleInstance.getActivities());

        saved = this.bciModuleInstanceRepository.save(moduleInstance);
        logger.info("BCIModuleInstance created: {}", saved);
        return saved;
    }

    /**
     * Updates a BCIModuleInstance in the database.
     * @param moduleInstance BCIModuleInstance.
     * @return The updated BCIModuleInstance.
     * @throws IllegalArgumentException if moduleInstance is null.
     */
    @Override
    public BCIModuleInstance update(BCIModuleInstance moduleInstance) {
        BCIModuleInstance updated = null;
        BCIModuleInstance found = findById(moduleInstance.getId());

        ObjectValidator.validateObject(moduleInstance);
        ObjectValidator.validateObject(moduleInstance.getOutcome());
        ObjectValidator.validateObject(moduleInstance.getActivities());

        if (found != null) {
            updated = this.bciModuleInstanceRepository.save(moduleInstance);

            if (!updated.getStatus().equals(ExecutionStatus.UNKNOWN)) {
                this.publishEvent(new BCIModuleInstanceEvent(updated));
            }
        }
        return updated;
    }

    /**
     * Changes the status of the specified BCIModuleInstance to FINISHED. Also sets the exit date to the current date,
     * then updates the instance status in the database.
     * @param moduleInstance the BCIModuleInstance whose status is to be changed to FINISHED.
     * @return the updated BCIModuleInstance with the FINISHED status and updated exit date.
     */
    public BCIModuleInstance changeStatusToFinished(BCIModuleInstance moduleInstance) {
        moduleInstance.setStatus(ExecutionStatus.FINISHED);
        moduleInstance.setExitDate(LocalDate.now());
        return this.updateStatus(moduleInstance);
    }

    /**
     * Changes the status of the specified BCIModuleInstance to IN_PROGRESS. Also sets the entry date to the current
     * date, then updates the instance status in the database.
     * @param moduleInstance the BCIModuleInstance whose status is to be changed to IN_PROGRESS.
     * @return the updated BCIModuleInstance with the IN_PROGRESS status and updated entry date.
     */
    public BCIModuleInstance changeStatusToInProgress(BCIModuleInstance moduleInstance) {
        moduleInstance.setStatus(ExecutionStatus.IN_PROGRESS);
        moduleInstance.setEntryDate(LocalDate.now());
        return this.updateStatus(moduleInstance);
    }

    /**
     * Updates the status of a given BCIModuleInstance. First, it validates the module instance's status object.
     * If the module instance exists in the database, its status is updated, and the updated instance is returned.
     * @param moduleInstance the BCIModuleInstance whose status needs to be updated.
     * @return the updated BCIModuleInstance, or null if the module instance does not exist.
     */
    private BCIModuleInstance updateStatus(BCIModuleInstance moduleInstance) {
        BCIModuleInstance updated = null;
        BCIModuleInstance found = this.findById(moduleInstance.getId());

        ObjectValidator.validateObject(moduleInstance.getStatus());

        if (found != null) {
            found.setStatus(moduleInstance.getStatus());
            updated = this.update(found);
        }
        return updated;
    }

    /**
     * Saves the given BCIModuleInstance in the database.
     * @param moduleInstance BCIModuleInstance.
     * @return The saved BCIModuleInstance.
     * @throws IllegalArgumentException if moduleInstance is null.
     */
    @Override
    public BCIModuleInstance save(BCIModuleInstance moduleInstance) {
        return this.bciModuleInstanceRepository.save(moduleInstance);
    }

    /**
     * Deletes a BCIModuleInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        bciModuleInstanceRepository.deleteById(id);
        logger.info("BCIModuleInstance deleted {}", id);
    }

    /**
     * Finds all BCIModuleInstance entities.
     * @return List<BCIModuleInstance>.
     */
    public List<BCIModuleInstance> findAll() {
        return this.bciModuleInstanceRepository.findAll();
    }

    /**
     * Finds a BCIModuleInstance by its id.
     * @param id Long.
     * @return BCIModuleInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public BCIModuleInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciModuleInstanceRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("BCIModuleInstance not found"));
    }

    /**
     * Finds BCIModuleInstance entities by their outcome.
     * @param outcome OutcomeType.
     * @return List<BCIModuleInstance> with the given outcome.
     * @throws IllegalArgumentException if an outcome is null.
     */
    public List<BCIModuleInstance> findByOutcome(OutcomeType outcome) {
        ObjectValidator.validateObject(outcome);
        return this.bciModuleInstanceRepository.findByOutcome(outcome);
    }

    /**
     * Finds BCIModuleInstance entities by their BCIActivityInstance id.
     * @param id Long.
     * @return List<BCIModuleInstance> with the given BCIActivityInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BCIModuleInstance> findByActivitiesId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciModuleInstanceRepository.findByActivitiesId(id);
    }

    /**
     * Checks if a BCIModuleInstance exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciModuleInstanceRepository.existsById(id);
    }

    /**
     * Handles BCIModuleInstance events received by the application context. The method listens for events of type
     * BCIModuleInstanceEvent and processes them based on specific conditions related to the event's change aspect,
     * execution status, and time cycle. Updates the associated EvoModel and modifies the event's change aspect
     * when applicable.
     * @param event the BCIModuleInstanceEvent to process. Must contain information to evaluate the change aspect
     *              (STARTED), the associated EvoModel's execution status (FINISHED), and the event's time cycle (END).
     */
    @EventListener(BCIModuleInstanceEvent.class)
    public void handleBCIModuleInstanceEvents(BCIModuleInstanceEvent event) {

        if (event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                event.getEvoModel().getStatus().equals(ExecutionStatus.FINISHED) &&
                event.getTimeCycle().equals(TimeCycle.END)) {
            this.changeStatusToFinished(event.getEvoModel());
            event.setChangeAspect(ChangeAspect.TERMINATED);

        } else if (event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                event.getEvoModel().getStatus().equals(ExecutionStatus.IN_PROGRESS) &&
                event.getTimeCycle().equals(TimeCycle.BEGINNING)) {
            this.changeStatusToInProgress(event.getEvoModel());
            event.setChangeAspect(ChangeAspect.TERMINATED);

        }
    }
}
