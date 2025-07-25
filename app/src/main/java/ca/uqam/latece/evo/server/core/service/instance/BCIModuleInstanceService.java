package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.event.BCIModuleInstanceEvent;
import ca.uqam.latece.evo.server.core.model.instance.BCIModuleInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BCIModuleInstanceRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BCIModuleInstance Service.
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
            this.publishEvent(new BCIModuleInstanceEvent(updated));
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
     * @throws IllegalArgumentException if outcome is null.
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
}
