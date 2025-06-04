package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.BCIModule;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionPhaseRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * BehaviorChangeInterventionPhase Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class BehaviorChangeInterventionPhaseService extends AbstractEvoService<BehaviorChangeInterventionPhase> {
    private static final Logger logger = LogManager.getLogger(BehaviorChangeInterventionPhaseService.class);

    @Autowired
    private BehaviorChangeInterventionPhaseRepository behaviorChangeInterventionPhaseRepository;

    /**
     * Creates a new BehaviorChangeInterventionPhase if it does not already exist in the repository.
     * @param interventionPhase the BehaviorChangeInterventionPhase object to be created and saved.
     * @return the saved BehaviorChangeInterventionPhase object.
     * @throws IllegalArgumentException if the BehaviorChangeInterventionPhase name already exists or
     * if the BehaviorChangeInterventionPhase object or its name is null.
     */
    @Override
    public BehaviorChangeInterventionPhase create(BehaviorChangeInterventionPhase interventionPhase) {
        BehaviorChangeInterventionPhase intervention = this.save(interventionPhase);
        logger.info("Behavior Change Intervention Phase created: {}", intervention);
        return intervention;
    }

    /**
     * Update a BehaviorChangeInterventionPhase in the database.
     * @param interventionBlock the BehaviorChangeInterventionPhase object to be created and saved.
     * @return the saved BehaviorChangeInterventionPhase object.
     * @throws IllegalArgumentException if the BehaviorChangeInterventionPhase name already exists or
     * if the BehaviorChangeInterventionPhase object or its name is null.
     */
    @Override
    public BehaviorChangeInterventionPhase update(BehaviorChangeInterventionPhase interventionBlock) {
        BehaviorChangeInterventionPhase intervention = this.save(interventionBlock);
        logger.info("Behavior Change Intervention Block updated: {}", intervention);
        return intervention;
    }

    /**
     * Creates and saves a new BehaviorChangeInterventionPhase if it does not already exist in the repository.
     * <p>Note: <p/>Validates the BehaviorChangeInterventionPhase object and its name before saving.
     * @param evoModel the BehaviorChangeInterventionPhase object to be created and saved.
     * @return the saved BehaviorChangeInterventionPhase object.
     * @throws IllegalArgumentException if the BehaviorChangeInterventionPhase name already exists or
     * if the BehaviorChangeInterventionPhase object or its name is null.
     */
    @Override
    protected BehaviorChangeInterventionPhase save(BehaviorChangeInterventionPhase evoModel) {
        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateString(evoModel.getEntryConditions());
        ObjectValidator.validateString(evoModel.getExitConditions());
        return behaviorChangeInterventionPhaseRepository.save(evoModel);
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
        return behaviorChangeInterventionPhaseRepository.existsById(id);
    }

    /**
     * Deletes a behavior change intervention phase from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior change intervention phase to be deleted.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        behaviorChangeInterventionPhaseRepository.deleteById(id);
        logger.info("Behavior Change Intervention Phase deleted: {}", id);
    }

    /**
     * Retrieves a BehaviorChangeInterventionPhase by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the role from the repository.
     * @param id the unique identifier of the behavior change intervention phase to be retrieved.
     * @return the behavior change intervention phase corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     * @throws EntityNotFoundException if the behavior change intervention phase not found.
     */
    @Override
    public BehaviorChangeInterventionPhase findById(Long id) {
        ObjectValidator.validateId(id);
        return behaviorChangeInterventionPhaseRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Behavior Change Intervention Phase not found!"));
    }

    /**
     * Retrieves a BehaviorChangeInterventionPhase by entry conditions.
     * <p>Note: <p/>Validates the provided identifier before fetching the behavior change intervention phase
     * from the repository.
     * @param entryConditions the entry condition of the behavior change intervention phase to be retrieved.
     * @return the behavior change intervention phase corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided entryConditions is null or invalid.
     */
    public List<BehaviorChangeInterventionPhase> findByEntryConditions(String entryConditions) {
        ObjectValidator.validateString(entryConditions);
        return behaviorChangeInterventionPhaseRepository.findByEntryConditions(entryConditions);
    }

    /**
     * Retrieves a BehaviorChangeInterventionPhase by entry conditions.
     * <p>Note: <p/>Validates the provided identifier before fetching the behavior change intervention phase
     * from the repository.
     * @param exitConditions the exit condition of the behavior change intervention phase to be retrieved.
     * @return the behavior change intervention phase corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided exitConditions is null or invalid.
     */
    public List<BehaviorChangeInterventionPhase> findByExitConditions(String exitConditions) {
        ObjectValidator.validateString(exitConditions);
        return behaviorChangeInterventionPhaseRepository.findByExitConditions(exitConditions);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionPhase with association with BehaviorChangeIntervention.
     * @param id The Behavior Change Intervention id.
     * @return the behavior change intervention phase corresponding with association with Behavior Change Intervention
     * Id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    public List<BehaviorChangeInterventionPhase> findByBehaviorChangeInterventionId(Long id) {
        ObjectValidator.validateId(id);
        return behaviorChangeInterventionPhaseRepository.findByBehaviorChangeInterventionId(id);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionPhase with association with BehaviorChangeInterventionBlock.
     * @param id The Behavior Change Intervention Block id.
     * @return the behavior change intervention phase corresponding with association with Behavior Change Intervention
     * Block Id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    public List<BehaviorChangeInterventionPhase> findByBehaviorChangeInterventionBlockId(Long id) {
        ObjectValidator.validateId(id);
        return behaviorChangeInterventionPhaseRepository.findByBehaviorChangeInterventionBlockId(id);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionPhase by BCIModule object.
     * @param bciModule The BCIModule.
     * @return the behavior change intervention phase associated with BCIModule specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    public List<BehaviorChangeInterventionPhase> findByBciModules(BCIModule bciModule) {
        ObjectValidator.validateObject(bciModule);
        ObjectValidator.validateId(bciModule.getId());
        ObjectValidator.validateString(bciModule.getName());
        return behaviorChangeInterventionPhaseRepository.findByBciModules(bciModule);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionPhase by BCIModule id.
     * @param id The BCIModule id.
     * @return the behavior change intervention phase associated with BCIModule id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    public List<BehaviorChangeInterventionPhase> findByBCIModulesId(Long id) {
        ObjectValidator.validateObject(id);
        return behaviorChangeInterventionPhaseRepository.findByBciModulesId(id);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionPhase by BCIModule name.
     * @param name The BCIModule name.
     * @return the behavior change intervention phase associated with BCIModule name specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    public List<BehaviorChangeInterventionPhase> findByBCIModulesName(String name) {
        ObjectValidator.validateString(name);
        return behaviorChangeInterventionPhaseRepository.findByBciModulesName(name);
    }

    /**
     * Retrieves all behavior change intervention phase from the repository.
     * @return a list of all behavior change intervention phase present in the repository.
     */
    @Override
    public List<BehaviorChangeInterventionPhase> findAll() {
        return behaviorChangeInterventionPhaseRepository.findAll().stream().toList();
    }
}