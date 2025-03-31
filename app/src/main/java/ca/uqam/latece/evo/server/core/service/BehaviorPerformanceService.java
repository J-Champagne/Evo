package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.BehaviorPerformance;
import ca.uqam.latece.evo.server.core.repository.BehaviorPerformanceRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BehaviorPerformance Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class BehaviorPerformanceService extends AbstractEvoService<BehaviorPerformance> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorPerformanceService.class);

    @Autowired
    private BehaviorPerformanceRepository behaviorPerformanceRepository;

    /**
     * Inserts a BehaviorPerformance in the database.
     * @param evoModel The BehaviorPerformance entity.
     * @return The saved BehaviorPerformance.
     * @throws IllegalArgumentException in case the given BehaviorPerformance is null.
     * @throws OptimisticLockingFailureException when the BehaviorPerformance uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public BehaviorPerformance create(BehaviorPerformance evoModel) {
        BehaviorPerformance behaviorPerformance = null;

        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateObject(evoModel.getName());

        // The name should be unique.
        if(this.existsByName(evoModel.getName())) {
            throw this.createDuplicateException(evoModel);
        } else {
            behaviorPerformance = this.save(evoModel);
            logger.info("Behavior Performance created: {}", behaviorPerformance);
        }

        return behaviorPerformance;
    }

    /**
     * Updates a BehaviorPerformance in the database.
     * @param evoModel The BehaviorPerformance entity.
     * @return The saved BehaviorPerformance.
     * @throws IllegalArgumentException in case the given BehaviorPerformance is null.
     * @throws OptimisticLockingFailureException when the BehaviorPerformance uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public BehaviorPerformance update(BehaviorPerformance evoModel) {
        BehaviorPerformance behaviorPerformance = null;

        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateObject(evoModel.getName());

        // The name should be unique.
        if(this.existsByName(evoModel.getName())) {
            throw this.createDuplicateException(evoModel);
        } else {
            behaviorPerformance = this.save(evoModel);
            logger.info("BehaviorPerformance updated: {}", behaviorPerformance);
        }

        return behaviorPerformance;
    }

    /**
     * Create duplicate BehaviorPerformance Exception.
     * @param evoModel the BehaviorPerformance entity.
     * @return an exception object.
     */
    private IllegalArgumentException createDuplicateException(BehaviorPerformance evoModel) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_NAME_ALREADY_REGISTERED +
                " Behavior Performance Name: " + evoModel.getName());
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    /**
     * Creates and saves a new BehaviorPerformance if it does not already exist in the repository.
     * @param evoModel the BehaviorPerformance object to be created and saved.
     * @return the saved BehaviorPerformance object.
     * @throws IllegalArgumentException if the BehaviorPerformance name already exists or
     * if the BehaviorPerformance object or its name is null.
     */
    @Override
    protected BehaviorPerformance save(BehaviorPerformance evoModel) {
        ObjectValidator.validateObject(evoModel);
        return behaviorPerformanceRepository.save(evoModel);
    }

    /**
     * Checks if a BehaviorPerformance entity with the specified name exists in the repository.
     * @param name the name of the BehaviorPerformance to check for existence, must not be null.
     * @return true if a BehaviorPerformance with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        ObjectValidator.validateString(name);
        return behaviorPerformanceRepository.existsByName(name);
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
        return behaviorPerformanceRepository.existsById(id);
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
        behaviorPerformanceRepository.deleteById(id);
        logger.info("Behavior Performance deleted: {}", id);
    }

    /**
     * Retrieves a BehaviorPerformance by id.
     * @param id the unique identifier of the behavior performance to be retrieved.
     * @return the behavior performance corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     * @throws EntityNotFoundException if the behavior performance not found.
     */
    @Override
    public BehaviorPerformance findById(Long id) {
        ObjectValidator.validateId(id);
        return behaviorPerformanceRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Behavior Performance not found!"));
    }

    /**
     * Finds a list of BehaviorPerformance entities by their name.
     * @param name the name of the BehaviorPerformance to search for.
     * @return the BehaviorPerformance with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    public List<BehaviorPerformance> findByName(String name) {
        ObjectValidator.validateString(name);
        return behaviorPerformanceRepository.findByName(name);
    }

    /**
     * Finds a list of BehaviorPerformance entities by their type.
     * @param type the type of the BehaviorPerformance to search for.
     * @return a list of BehaviorPerformance entities matching the specified type.
     */
    public List<BehaviorPerformance> findByType(ActivityType type) {
        ObjectValidator.validateObject(type);
        return behaviorPerformanceRepository.findByType(type);
    }

    /**
     * Retrieves all behavior performance from the repository.
     * @return a list of all behavior performance present in the repository.
     */
    @Override
    public List<BehaviorPerformance> findAll() {
        return behaviorPerformanceRepository.findAll().stream().toList();
    }
}
