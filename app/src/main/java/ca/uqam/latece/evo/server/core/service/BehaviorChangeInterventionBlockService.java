package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionBlockRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * BehaviorChangeInterventionBlock Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class BehaviorChangeInterventionBlockService extends AbstractEvoService<BehaviorChangeInterventionBlock> {
    private static final Logger logger = LogManager.getLogger(BehaviorChangeInterventionBlockService.class);

    @Autowired
    private BehaviorChangeInterventionBlockRepository behaviorChangeInterventionBlockRepository;

    /**
     * Creates a new BehaviorChangeInterventionBlock if it does not already exist in the repository.
     * @param interventionBlock the BehaviorChangeInterventionBlock object to be created and saved.
     * @return the saved BehaviorChangeInterventionBlock object.
     * @throws IllegalArgumentException if the BehaviorChangeInterventionBlock name already exists or
     * if the BehaviorChangeInterventionBlock object or its name is null.
     */
    @Override
    public BehaviorChangeInterventionBlock create(BehaviorChangeInterventionBlock interventionBlock) {
        BehaviorChangeInterventionBlock intervention = this.save(interventionBlock);
        logger.info("Behavior Change Intervention Block created: {}", intervention);
        return intervention;
    }

    /**
     * Update a BehaviorChangeInterventionBlock in the database.
     * @param interventionBlock the BehaviorChangeInterventionBlock object to be created and saved.
     * @return the saved BehaviorChangeInterventionBlock object.
     * @throws IllegalArgumentException if the BehaviorChangeInterventionBlock name already exists or
     * if the BehaviorChangeInterventionBlock object or its name is null.
     */
    @Override
    public BehaviorChangeInterventionBlock update(BehaviorChangeInterventionBlock interventionBlock) {
        BehaviorChangeInterventionBlock intervention = this.save(interventionBlock);
        logger.info("Behavior Change Intervention Block updated: {}", intervention);
        return intervention;
    }

    /**
     * Creates and saves a new BehaviorChangeInterventionBlock if it does not already exist in the repository.
     * <p>Note: <p/>Validates the BehaviorChangeInterventionBlock object and its name before saving.
     * @param evoModel the BehaviorChangeInterventionBlock object to be created and saved.
     * @return the saved BehaviorChangeInterventionBlock object.
     * @throws IllegalArgumentException if the BehaviorChangeInterventionBlock name already exists or
     * if the BehaviorChangeInterventionBlock object or its name is null.
     */
    @Override
    protected BehaviorChangeInterventionBlock save(BehaviorChangeInterventionBlock evoModel) {
        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateString(evoModel.getEntryConditions());
        ObjectValidator.validateString(evoModel.getExitConditions());
        return behaviorChangeInterventionBlockRepository.save(evoModel);
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
        return behaviorChangeInterventionBlockRepository.existsById(id);
    }

    /**
     * Deletes a behavior change intervention block from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior change intervention block to be deleted.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        behaviorChangeInterventionBlockRepository.deleteById(id);
        logger.info("Behavior Change Intervention Block deleted: {}", id);
    }

    /**
     * Retrieves a BehaviorChangeInterventionBlock by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the role from the repository.
     * @param id the unique identifier of the behavior change intervention block to be retrieved.
     * @return the behavior change intervention block corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @Override
    public BehaviorChangeInterventionBlock findById(Long id) {
        ObjectValidator.validateId(id);
        return behaviorChangeInterventionBlockRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves a BehaviorChangeInterventionBlock by entry conditions.
     * <p>Note: <p/>Validates the provided identifier before fetching the behavior change intervention block
     * from the repository.
     * @param entryConditions the entry condition of the behavior change intervention block to be retrieved.
     * @return the behavior change intervention block corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided entryConditions is null or invalid.
     */
    public List<BehaviorChangeInterventionBlock> findByEntryConditions(String entryConditions) {
        ObjectValidator.validateString(entryConditions);
        return behaviorChangeInterventionBlockRepository.findByEntryConditions(entryConditions);
    }

    /**
     * Retrieves a BehaviorChangeInterventionBlock by entry conditions.
     * <p>Note: <p/>Validates the provided identifier before fetching the behavior change intervention block
     * from the repository.
     * @param exitConditions the exit condition of the behavior change intervention block to be retrieved.
     * @return the behavior change intervention block corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided exitConditions is null or invalid.
     */
    public List<BehaviorChangeInterventionBlock> findByExitConditions(String exitConditions) {
        ObjectValidator.validateString(exitConditions);
        return behaviorChangeInterventionBlockRepository.findByExitConditions(exitConditions);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionBlock with association with BehaviorChangeInterventionPhase.
     * @param id The Behavior Change Intervention Phase id.
     * @return the behavior change intervention phase corresponding with association with Behavior Change Intervention
     * Phase Id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    public List<BehaviorChangeInterventionBlock> findByBehaviorChangeInterventionPhaseId(Long id) {
        ObjectValidator.validateId(id);
        return behaviorChangeInterventionBlockRepository.findByBehaviorChangeInterventionPhaseId(id);
    }

    /**
     * Retrieves all behavior change intervention block from the repository.
     * @return a list of all behavior change intervention block present in the repository.
     */
    @Override
    public List<BehaviorChangeInterventionBlock> findAll() {
        return behaviorChangeInterventionBlockRepository.findAll().stream().toList();
    }
}