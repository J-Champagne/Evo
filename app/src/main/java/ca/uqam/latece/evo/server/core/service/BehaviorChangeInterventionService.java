package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * BehaviorChangeIntervention Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class BehaviorChangeInterventionService extends AbstractEvoService<BehaviorChangeIntervention> {
    private static final Logger logger = LogManager.getLogger(BehaviorChangeInterventionService.class);
    private static final String UPDATE_ERROR_MESSAGE = "Update failed - Behavior Change Intervention not found!";

    @Autowired
    private BehaviorChangeInterventionRepository behaviorChangeInterventionRepository;


    /**
     * Creates a new BehaviorChangeIntervention if it does not already exist in the repository.
     * @param intervention the BehaviorChangeIntervention object to be created and saved.
     * @return the saved BehaviorChangeIntervention object.
     * @throws IllegalArgumentException if the BehaviorChangeIntervention name already exists or
     * if the BehaviorChangeIntervention object or its name is null.
     */
    @Override
    public BehaviorChangeIntervention create(BehaviorChangeIntervention intervention) {
        BehaviorChangeIntervention interventionCreated = null;

        ObjectValidator.validateObject(intervention);
        ObjectValidator.validateString(intervention.getName());

        if (this.existsByName(intervention.getName())) {
            throw this.createDuplicateException(intervention);
        } else {
            interventionCreated = this.save(intervention);
            logger.info("Behavior Change Intervention created: {}", interventionCreated);
        }

        return interventionCreated;
    }

    /**
     * Update a BehaviorChangeIntervention in the database.
     * @param intervention the BehaviorChangeIntervention object to be created and saved.
     * @return the saved BehaviorChangeIntervention object.
     * @throws IllegalArgumentException if the BehaviorChangeIntervention name already exists or
     * if the BehaviorChangeIntervention object or its name is null.
     */
    @Override
    public BehaviorChangeIntervention update(BehaviorChangeIntervention intervention) {
        BehaviorChangeIntervention interventionUpdated = null;
        BehaviorChangeIntervention interventionFound = this.findById(intervention.getId());

        ObjectValidator.validateObject(intervention);
        ObjectValidator.validateString(intervention.getName());

        if (interventionFound != null) {
            if (interventionFound.getName().equals(intervention.getName())) {
                interventionUpdated = this.save(intervention);
            } else {
                if (this.existsByName(intervention.getName())) {
                    throw this.createDuplicateException(intervention);
                } else {
                    interventionUpdated = this.save(intervention);
                }
            }
            logger.info("Behavior Change Intervention updated: {}", interventionUpdated);
        } else {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(UPDATE_ERROR_MESSAGE);
            logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
            throw illegalArgumentException;
        }

        return interventionUpdated;
    }

    /**
     * Create duplicate BehaviorChangeIntervention Exception.
     * @param intervention the BehaviorChangeIntervention entity.
     * @return an exception object.
     */
    private IllegalArgumentException createDuplicateException(BehaviorChangeIntervention intervention) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_NAME_ALREADY_REGISTERED +
                " Behavior Change Intervention Name: " + intervention.getName());
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    /**
     * Creates and saves a new BehaviorChangeIntervention if it does not already exist in the repository.
     * @param evoModel the BehaviorChangeIntervention object to be created and saved.
     * @return the saved BehaviorChangeIntervention object.
     * @throws IllegalArgumentException if the BehaviorChangeIntervention name already exists or
     * if the BehaviorChangeIntervention object or its name is null.
     */
    @Override
    protected BehaviorChangeIntervention save(BehaviorChangeIntervention evoModel) {
        return behaviorChangeInterventionRepository.save(evoModel);
    }

    /**
     * Deletes a behavior change intervention from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior change intervention to be deleted.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        behaviorChangeInterventionRepository.deleteById(id);
        logger.info("Behavior Change Intervention deleted: {}", id);
    }

    /**
     * Checks if a BehaviorChangeIntervention entity with the specified name exists in the repository.
     * @param name the name of the BehaviorChangeIntervention to check for existence, must not be null.
     * @return true if a BehaviorChangeIntervention with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        ObjectValidator.validateString(name);
        return behaviorChangeInterventionRepository.existsByName(name);
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
        return behaviorChangeInterventionRepository.existsById(id);
    }

    /**
     * Retrieves a BehaviorChangeIntervention by id.
     * @param id the unique identifier of the behavior change intervention to be retrieved.
     * @return the behavior change intervention corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     * @throws EntityNotFoundException if the behavior change intervention not found.
     */
    @Override
    public BehaviorChangeIntervention findById(Long id) {
        ObjectValidator.validateId(id);
        return behaviorChangeInterventionRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Behavior Change Intervention not found!"));
    }

    /**
     * Finds a list of BehaviorChangeIntervention entities by their name.
     * @param name the name of the BehaviorChangeIntervention to search for.
     * @return the BehaviorChangeIntervention with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    public List<BehaviorChangeIntervention> findByName(String name) {
        ObjectValidator.validateString(name);
        return behaviorChangeInterventionRepository.findByName(name);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeIntervention with association with BehaviorChangeInterventionPhase.
     * @param id The Behavior Change Intervention Phase id.
     * @return the behavior change intervention corresponding with association with Behavior Change Intervention
     * Phase Id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    public List<BehaviorChangeIntervention> findByBehaviorChangeInterventionPhase(Long id) {
        ObjectValidator.validateObject(id);
        return behaviorChangeInterventionRepository.findByBehaviorChangeInterventionPhase(id);
    }

    /**
     * Retrieves all behavior change intervention from the repository.
     * @return a list of all behavior change intervention present in the repository.
     */
    @Override
    public List<BehaviorChangeIntervention> findAll() {
        return behaviorChangeInterventionRepository.findAll().stream().toList();
    }
}