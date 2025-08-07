package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.*;
import ca.uqam.latece.evo.server.core.model.instance.InteractionInstance;
import ca.uqam.latece.evo.server.core.model.instance.InteractionInstance;
import ca.uqam.latece.evo.server.core.model.instance.InteractionInstance;
import ca.uqam.latece.evo.server.core.repository.instance.InteractionInstanceRepository;

import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * InteractionInstance Service.
 * @version 1.0
 * @author Julien Champagne.
 */
@Service
@Transactional
public class InteractionInstanceService extends AbstractEvoService<InteractionInstance> {
    private static final Logger logger = LoggerFactory.getLogger(InteractionInstanceService.class);

    @Autowired
    InteractionInstanceRepository interactionInstanceRepository;

    /**
     * Creates an InteractionInstance in the database.
     * @param interactionInstance InteractionInstance.
     * @return The created InteractionInstance.
     * @throws IllegalArgumentException if interactionInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public InteractionInstance create(InteractionInstance interactionInstance) {
        InteractionInstance interactionInstanceCreated;

        ObjectValidator.validateObject(interactionInstance);
        interactionInstanceCreated = interactionInstanceRepository.save(interactionInstance);

        logger.info("InteractionInstance created: {}", interactionInstanceCreated);
        return interactionInstanceCreated;
    }

    /**
     * Updates a InteractionInstance in the database.
     * @param interactionInstance InteractionInstance.
     * @return The updated InteractionInstance.
     * @throws IllegalArgumentException if interactionInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public InteractionInstance update(InteractionInstance interactionInstance) {
        InteractionInstance interactionInstanceUpdated = null;
        InteractionInstance InteractionInstanceFound = findById(interactionInstance.getId());

        ObjectValidator.validateObject(interactionInstance);
        if (InteractionInstanceFound != null) {
            interactionInstanceUpdated = interactionInstanceRepository.save(interactionInstance);
        }

        return interactionInstanceUpdated;
    }

    /**
     * Saves the given InteractionInstance in the database.
     * @param interactionInstance InteractionInstance.
     * @return The saved InteractionInstance.
     * @throws IllegalArgumentException if interactionInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    @Transactional
    public InteractionInstance save(InteractionInstance interactionInstance) {
        return this.interactionInstanceRepository.save(interactionInstance);
    }

    /**
     * Deletes a InteractionInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        this.interactionInstanceRepository.deleteById(id);
        logger.info("InteractionInstance deleted {}", id);
    }

    /**
     * Finds all InteractionInstance entities.
     * @return List<InteractionInstance>.
     */
    @Override
    public List<InteractionInstance> findAll() {
        return this.interactionInstanceRepository.findAll();
    }

    /**
     * Finds an InteractionInstance by its id.
     * @param id Long.
     * @return the InteractionInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public InteractionInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.interactionInstanceRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("InteractionInstance not found"));
    }

    /**
     * Finds InteractionInstance entities by their status.
     * @param status ExecutionStatus representing the status of the InteractionInstance.
     * @return List of InteractionInstance entities with the given status.
     * @throws IllegalArgumentException if the status is null.
     */
    public List<InteractionInstance> findByStatus(ExecutionStatus status) {
        ObjectValidator.validateObject(status);
        return this.interactionInstanceRepository.findByStatus(status);
    }

    /**
     * Finds InteractionInstance entities by their entryDate.
     * @param entryDate LocalDate.
     * @return List<InteractionInstance> with the given entryDate.
     * @throws IllegalArgumentException if entryDate is null.
     */
    public List<InteractionInstance> findByEntryDate(LocalDate entryDate) {
        ObjectValidator.validateObject(entryDate);
        return this.interactionInstanceRepository.findByEntryDate(entryDate);
    }

    /**
     * Finds InteractionInstance entities by their exitDate.
     * @param exitDate LocalDate.
     * @return List<InteractionInstance> with the given exitDate.
     * @throws IllegalArgumentException if exitDate is null.
     */
    public List<InteractionInstance> findByExitDate(LocalDate exitDate) {
        ObjectValidator.validateObject(exitDate);
        return this.interactionInstanceRepository.findByExitDate(exitDate);
    }

    /**
     * Retrieves a list of InteractionInstance entities associated with a specific participant ID.
     * @param id the ID of the participant to find associated activity instances for. Must not be null.
     * @return a list of InteractionInstance entities associated with the given participant ID.
     * @throws IllegalArgumentException if the provided ID is null or invalid.
     */
    public List<InteractionInstance> findByParticipantsId(Long id) {
        ObjectValidator.validateId(id);
        return this.interactionInstanceRepository.findByParticipantsId(id);
    }

    /**
     * Checks if a InteractionInstance exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.interactionInstanceRepository.existsById(id);
    }
}
