package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.event.BCIActivityInstanceEvent;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BCIActivityInstanceRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * BCIActivity Instance Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 * @author Julien Champagne
 */
@Service
public class BCIActivityInstanceService extends AbstractEvoService<BCIActivityInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BCIActivityInstanceService.class);

    @Autowired
    private BCIActivityInstanceRepository bciActivityInstanceRepository;

    /**
     * Creates a BCIActivityInstance in the database.
     * @param bciActivityInstance BCIActivityInstance.
     * @return The created BCIActivityInstance.
     * @throws IllegalArgumentException if bciActivityInstance is null.
     */
    public BCIActivityInstance create (BCIActivityInstance bciActivityInstance) {
        BCIActivityInstance bciBCIActivityInstance = null;

        ObjectValidator.validateObject(bciActivityInstance);

        bciBCIActivityInstance = this.bciActivityInstanceRepository.save(bciActivityInstance);
        logger.info("BCIActivityInstance created: {}", bciBCIActivityInstance);

        return bciBCIActivityInstance;
    }

    /**
     * Updates a BCIActivityInstance in the database.
     * @param bciActivityInstance BCIActivityInstance.
     * @return The updated BCIActivityInstance.
     * @throws IllegalArgumentException if bciActivityInstance is null.
     */
    public BCIActivityInstance update (BCIActivityInstance bciActivityInstance) {
        BCIActivityInstance bciBCIActivityInstance = null;

        ObjectValidator.validateObject(bciActivityInstance);
        ObjectValidator.validateId(bciActivityInstance.getId());

        bciBCIActivityInstance = this.bciActivityInstanceRepository.save(bciActivityInstance);
        logger.info("BCIActivityInstance updated: {}", bciBCIActivityInstance);

        return bciBCIActivityInstance;
    }

    /**
     * Saves the given BCIActivityInstance in the database.
     * @param bciActivityInstance BCIActivityInstance.
     * @return The saved BCIActivityInstance.
     * @throws IllegalArgumentException if bciActivityInstance is null.
     */
    @Override
    protected BCIActivityInstance save(BCIActivityInstance bciActivityInstance) {
        ObjectValidator.validateObject(bciActivityInstance.getStatus());
        ObjectValidator.validateObject(bciActivityInstance.getEntryDate());
        ObjectValidator.validateObject(bciActivityInstance.getExitDate());
        ObjectValidator.validateObject(bciActivityInstance.getParticipants());
        return bciActivityInstanceRepository.save(bciActivityInstance);
    }

    /**
     * Deletes a BCIActivityInstance by its id.
     * Silently ignored if the instance with the given id is not found.
     * @param id the id of the BCIActivityInstance to delete. Must not be null.
     * @throws IllegalArgumentException if id is null or invalid.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        this.bciActivityInstanceRepository.deleteById(id);
        logger.info("BCIActivityInstance deleted: {}", id);
    }

    /**
     * Finds a BCIActivityInstance by its id.
     * @param id the id of the BCIActivityInstance to find. Must not be null.
     * @return the BCIActivityInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     * @throws EntityNotFoundException if no BCIActivityInstance is found with the given id.
     */
    public BCIActivityInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciActivityInstanceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("BCIActivityInstance not found!"));
    }

    /**
     * Finds BCIActivityInstance entities by their status.
     * @param status the status of the BCIActivityInstance entities to find.
     * @return a list of BCIActivityInstance entities with the given status.
     * @throws IllegalArgumentException if the status is null or invalid.
     */
    public List<BCIActivityInstance> findByStatus(ExecutionStatus status) {
        ObjectValidator.validateObject(status);
        return this.bciActivityInstanceRepository.findByStatus(status);
    }

    /**
     * Fetches a list of BCIActivityInstance entities by their entry date.
     * @param entryDate the entry date of the BCIActivityInstance entities to retrieve. Must not be null.
     * @return a list of BCIActivityInstance entities corresponding to the given entry date.
     * @throws IllegalArgumentException if entryDate is null.
     */
    public List<BCIActivityInstance> findByEntryDate(LocalDate entryDate) {
        ObjectValidator.validateObject(entryDate);
        return this.bciActivityInstanceRepository.findByEntryDate(entryDate);
    }

    /**
     * Finds BCIActivityInstance entities by their exit date.
     * @param exitDate the exit date of the BCIActivityInstance entities to retrieve. Must not be null.
     * @return a list of BCIActivityInstance entities with the specified exit date.
     * @throws IllegalArgumentException if exitDate is null.
     */
    public List<BCIActivityInstance> findByExitDate(LocalDate exitDate) {
        ObjectValidator.validateObject(exitDate);
        return this.bciActivityInstanceRepository.findByEntryDate(exitDate);
    }

    /**
     * Retrieves a list of BCIActivityInstance entities associated with a specific participant ID.
     * @param id the ID of the participant to find associated activity instances for. Must not be null.
     * @return a list of BCIActivityInstance entities associated with the given participant ID.
     * @throws IllegalArgumentException if the provided ID is null or invalid.
     */
    public List<BCIActivityInstance> findByParticipantsId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciActivityInstanceRepository.findByParticipantsId(id);
    }

    /**
     * Checks whether a BCIActivityInstance exists with the given ID.
     * @param id the ID of the BCIActivityInstance to check. Must not be null.
     * @return true if a BCIActivityInstance exists with the given ID, false otherwise.
     * @throws IllegalArgumentException if the ID is null or invalid.
     */
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciActivityInstanceRepository.existsById(id);
    }

    /**
     * Finds all BCIActivityInstance entities.
     * @return a list of all BCIActivityInstance entities.
     */
    public List<BCIActivityInstance> findAll() {
        return this.bciActivityInstanceRepository.findAll();
    }

    /**
     * Finds BCIActivityInstance entities by their associated BCIActivity id.
     * @param id the id of the BCIActivity associated with the intervention instances.
     * @return a list of BCIActivityInstance objects associated with the specified BCIActivity id.
     * @throws IllegalArgumentException if the id is null.
     */
    public List<BCIActivityInstance> findByBciActivityId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciActivityInstanceRepository.findByBciActivityId(id);
    }

    /**
     * Updates the current status of a BCIActivityInstance.
     * If the specified execution status is FINISHED, the exitDate of the BCIActivityInstance will be set.
     * @param id the BCIActivityInstance id to be updated.
     * @param status the execution status to be assigned to the BCIActivityInstance.
     * @return the updated BCIActivityInstance, or null if the instance was not found
     */
    public BCIActivityInstance updateStatus(Long id, ExecutionStatus status) {
        BCIActivityInstance found = findById(id);
        BCIActivityInstance updated = null;

        ObjectValidator.validateId(id);
        ObjectValidator.validateObject(status);

        if (found != null && status != null) {
            found.setStatus(status);

            switch (status) {
                case FINISHED -> {
                    found.setExitDate(LocalDate.now());
                }

                default -> {
                    return null;
                }
            }

            updated = this.update(found);
            if (updated != null) {
                this.publishEvent(new BCIActivityInstanceEvent(updated));
            }
        }
        return updated;
    }
}