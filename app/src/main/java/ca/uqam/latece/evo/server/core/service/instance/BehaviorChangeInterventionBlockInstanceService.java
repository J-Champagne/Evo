package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionBlockInstanceRepository;

import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
<<<<<<< HEAD
 * BehaviorChangeInterventionBlockInstance Service.
=======
 * BCIReferral Service.
>>>>>>> 338f438 (Im plementation of BehaviorChangeInterventionBlockInstanceService)
 * @author Julien Champagne.
 */
@Service
@Transactional
public class BehaviorChangeInterventionBlockInstanceService extends AbstractEvoService<BehaviorChangeInterventionBlockInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorChangeInterventionBlockInstanceService.class);

    @Autowired
    BehaviorChangeInterventionBlockInstanceRepository BCIBlockInstanceRepository;

    /**
     * Creates a BehaviorChangeInterventionBlockInstance in the database.
     * @param blockInstance BehaviorChangeInterventionBlockInstance.
     * @return The created BehaviorChangeInterventionBlockInstance.
     * @throws IllegalArgumentException if blockInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public BehaviorChangeInterventionBlockInstance create(BehaviorChangeInterventionBlockInstance blockInstance) {
        BehaviorChangeInterventionBlockInstance saved = null;

        ObjectValidator.validateObject(blockInstance);
        ObjectValidator.validateObject(blockInstance.getStage());
        ObjectValidator.validateObject(blockInstance.getActivities());

        saved = this.BCIBlockInstanceRepository.save(blockInstance);
        logger.info("BehaviorChangeInterventionBlockInstance created: {}", saved);
        return saved;
    }

    /**
     * Updates a BehaviorChangeInterventionBlockInstance in the database.
     * @param blockInstance BehaviorChangeInterventionBlockInstance.
     * @return The updated BehaviorChangeInterventionBlockInstance.
     * @throws IllegalArgumentException if blockInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public BehaviorChangeInterventionBlockInstance update(BehaviorChangeInterventionBlockInstance blockInstance) {
        BehaviorChangeInterventionBlockInstance updated = null;
        BehaviorChangeInterventionBlockInstance found = findById(blockInstance.getId());

        ObjectValidator.validateObject(blockInstance);
        ObjectValidator.validateObject(blockInstance.getStage());
        ObjectValidator.validateObject(blockInstance.getActivities());

        if (found != null) {
            updated = this.BCIBlockInstanceRepository.save(blockInstance);
        }
        return updated;
    }

    /**
     * Saves the given BehaviorChangeInterventionBlockInstance in the database.
     * @param blockInstance BehaviorChangeInterventionBlockInstance.
     * @return The saved BehaviorChangeInterventionBlockInstance.
     * @throws IllegalArgumentException if blockInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public BehaviorChangeInterventionBlockInstance save(BehaviorChangeInterventionBlockInstance blockInstance) {
        return this.BCIBlockInstanceRepository.save(blockInstance);
    }

    /**
     * Deletes a BehaviorChangeInterventionBlockInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        BCIBlockInstanceRepository.deleteById(id);
        logger.info("BehaviorChangeInterventionBlockInstance deleted {}", id);
    }

    /**
     * Finds all BehaviorChangeInterventionBlockInstance entities.
     * @return List<BehaviorChangeInterventionBlockInstance>.
     */
    public List<BehaviorChangeInterventionBlockInstance> findAll() {
        return this.BCIBlockInstanceRepository.findAll();
    }

    /**
     * Finds a BehaviorChangeInterventionBlockInstance by its id.
     * @param id Long.
     * @return BehaviorChangeInterventionBlockInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public BehaviorChangeInterventionBlockInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.BCIBlockInstanceRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("BehaviorChangeInterventionBlockInstance not found"));
    }

    /**
     * Finds BehaviorChangeInterventionBlockInstance entities by their stage.
     * @param stage TimeCycle.
     * @return List<BehaviorChangeInterventionBlockInstance> with the given stage.
     * @throws IllegalArgumentException if stage is null.
     */
    public List<BehaviorChangeInterventionBlockInstance> findByStage(TimeCycle stage) {
        ObjectValidator.validateObject(stage);
        return this.BCIBlockInstanceRepository.findByStage(stage);
    }

    /**
     * Finds BehaviorChangeInterventionBlockInstance entities by their BCIActivityInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionBlockInstance> with the given BCIActivityInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionBlockInstance> findByActivitiesId(Long id) {
        ObjectValidator.validateId(id);
        return this.BCIBlockInstanceRepository.findByActivitiesId(id);
    }

    /**
     * Checks if a BehaviorChangeInterventionBlockInstance exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.BCIBlockInstanceRepository.existsById(id);
    }
}
