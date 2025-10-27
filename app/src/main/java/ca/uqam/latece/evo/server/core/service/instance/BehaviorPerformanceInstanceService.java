package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorPerformanceInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorPerformanceInstanceRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Behavior Performance Instance Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Getter
@Service
public class BehaviorPerformanceInstanceService extends AbstractEvoService<BehaviorPerformanceInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorPerformanceInstanceService.class);

    @Autowired
    private BehaviorPerformanceInstanceRepository behaviorPerformanceInstanceRepository;


    /**
     * Inserts a BehaviorPerformanceInstance in the database.
     * @param evoModel The BehaviorPerformanceInstance entity.
     * @return The saved BehaviorPerformanceInstance.
     * @throws IllegalArgumentException in case the given BehaviorPerformanceInstance is null.
     */
    @Transactional
    public BehaviorPerformanceInstance create (BehaviorPerformanceInstance evoModel) {
        BehaviorPerformanceInstance instance = null;

        // Checks the mandatory properties.
        ObjectValidator.validateObject(evoModel);

        // Save.
        instance = this.save(evoModel);
        logger.info("BehaviorPerformanceInstance created: {}", instance);

        return instance;
    }

    /**
     * Updates a BehaviorPerformance in the database.
     * @param evoModel The BehaviorPerformance entity.
     * @return The saved BehaviorPerformance.
     * @throws IllegalArgumentException in case the given BehaviorPerformance is null.
     */
    @Transactional
    public BehaviorPerformanceInstance update (BehaviorPerformanceInstance evoModel) {
        BehaviorPerformanceInstance instance = null;

        // Checks the mandatory properties.
        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateId(evoModel.getId());

        // Save.
        instance = this.save(evoModel);
        logger.info("BehaviorPerformanceInstance updated: {}", instance);

        return instance;

    }

    /**
     * Method used to create or update an BehaviorPerformanceInstance.
     * @param evoModel he BehaviorPerformanceInstance entity.
     * @return The BehaviorPerformanceInstance.
     */
    @Transactional
    @Override
    protected BehaviorPerformanceInstance save(BehaviorPerformanceInstance evoModel) {
        return behaviorPerformanceInstanceRepository.save(evoModel);
    }

    /**
     * Checks if an entity with the specified identifier exists in the repository.
     * @param id the unique identifier of the entity to be checked for existence.
     * @return true if an entity with the given identifier exists, false otherwise.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.behaviorPerformanceInstanceRepository.existsById(id);
    }

    /**
     * Deletes a behavior performance from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior performance to be deleted.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        this.behaviorPerformanceInstanceRepository.deleteById(id);
        logger.info("Behavior Performance Instance deleted: {}", id);
    }

    /**
     * Retrieves a BehaviorPerformanceInstance by id.
     * @param id the unique identifier of the behavior performance instance to be retrieved.
     * @return the behavior performance instance corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @Override
    public BehaviorPerformanceInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.behaviorPerformanceInstanceRepository.findById(id).orElse(null);
    }

    /**
     * Checks if a BehaviorPerformanceInstance entity with the specified status exists in the repository.
     * @param status the status of the BehaviorPerformanceInstance to check for existence, must not be null.
     * @return A list of BehaviorPerformanceInstance with the specified status.
     * @throws IllegalArgumentException if the status is null.
     */
    public List<BehaviorPerformanceInstance> findByStatus(ExecutionStatus status) {
        ObjectValidator.validateObject(status);
        return this.behaviorPerformanceInstanceRepository.findByStatus(status);
    }

    /**
     * Gets all BehaviorPerformanceInstance.
     * @return all BehaviorPerformanceInstance.
     */
    @Override
    public List<BehaviorPerformanceInstance> findAll() {
        return this.behaviorPerformanceInstanceRepository.findAll();
    }

    /**
     * Finds a BehaviorPerformanceInstance by its Participant id.
     * @param id Long.
     * @return BehaviorPerformanceInstance with the given Participant id.
     * @throws IllegalArgumentException if id is null.
     */
    public BehaviorPerformanceInstance findByParticipantsId(Long id) {
        return this.behaviorPerformanceInstanceRepository.findByParticipantsId(id);
    }
}
