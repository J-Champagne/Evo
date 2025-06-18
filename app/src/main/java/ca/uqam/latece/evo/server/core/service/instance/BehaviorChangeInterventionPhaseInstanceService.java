package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionPhaseInstanceRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
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
 * BehaviorChangeInterventionPhaseInstance Service.
 * @author Julien Champagne.
 */
@Service
@Transactional
public class BehaviorChangeInterventionPhaseInstanceService extends AbstractEvoService<BehaviorChangeInterventionPhaseInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorChangeInterventionPhaseInstanceService.class);

    @Autowired
    private BehaviorChangeInterventionPhaseInstanceRepository bciPhaseInstanceRepository;

    /**
     * Creates a BehaviorChangeInterventionPhaseInstance in the database.
     * @param phaseInstance BehaviorChangeInterventionPhaseInstance.
     * @return The created BehaviorChangeInterventionPhaseInstance.
     * @throws IllegalArgumentException if phaseInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public BehaviorChangeInterventionPhaseInstance create(BehaviorChangeInterventionPhaseInstance phaseInstance) {
        BehaviorChangeInterventionPhaseInstance saved = null;

        ObjectValidator.validateObject(phaseInstance);
        ObjectValidator.validateObject(phaseInstance.getBlocks());
        ObjectValidator.validateObject(phaseInstance.getModules());

        saved = this.bciPhaseInstanceRepository.save(phaseInstance);
        logger.info("BehaviorChangeInterventionPhaseInstance created: {}", saved);
        return saved;
    }

    /**
     * Updates a BehaviorChangeInterventionPhaseInstance in the database.
     * @param phaseInstance BehaviorChangeInterventionPhaseInstance.
     * @return The updated BehaviorChangeInterventionPhaseInstance.
     * @throws IllegalArgumentException if phaseInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public BehaviorChangeInterventionPhaseInstance update(BehaviorChangeInterventionPhaseInstance phaseInstance) {
        BehaviorChangeInterventionPhaseInstance updated = null;
        BehaviorChangeInterventionPhaseInstance found = findById(phaseInstance.getId());

        ObjectValidator.validateObject(phaseInstance);
        ObjectValidator.validateObject(phaseInstance.getBlocks());
        ObjectValidator.validateObject(phaseInstance.getModules());

        if (found != null) {
            updated = this.bciPhaseInstanceRepository.save(phaseInstance);
        }
        return updated;
    }

    /**
     * Saves the given BehaviorChangeInterventionPhaseInstance in the database.
     * @param phaseInstance BehaviorChangeInterventionPhaseInstance.
     * @return The saved BehaviorChangeInterventionPhaseInstance.
     * @throws IllegalArgumentException if phaseInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public BehaviorChangeInterventionPhaseInstance save(BehaviorChangeInterventionPhaseInstance phaseInstance) {
        return this.bciPhaseInstanceRepository.save(phaseInstance);
    }

    /**
     * Deletes a BehaviorChangeInterventionPhaseInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        bciPhaseInstanceRepository.deleteById(id);
        logger.info("BehaviorChangeInterventionPhaseInstance deleted {}", id);
    }

    /**
     * Finds all BehaviorChangeInterventionPhaseInstance entities.
     * @return List<BehaviorChangeInterventionPhaseInstance>.
     */
    public List<BehaviorChangeInterventionPhaseInstance> findAll() {
        return this.bciPhaseInstanceRepository.findAll();
    }

    /**
     * Finds a BehaviorChangeInterventionPhaseInstance by its id.
     * @param id Long.
     * @return BehaviorChangeInterventionPhaseInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public BehaviorChangeInterventionPhaseInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciPhaseInstanceRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("BehaviorChangeInterventionPhaseInstance not found"));
    }

    /**
     * Finds BehaviorChangeInterventionPhaseInstance entities by their currentBlock id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionPhaseInstance> with the given currentBlock id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionPhaseInstance> findByCurrentBlockId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciPhaseInstanceRepository.findByCurrentBlockId(id);
    }

    /**
     * Finds BehaviorChangeInterventionPhaseInstance entities by a BehaviorChangeInterventionBlockInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionPhaseInstance> with the given BCIBlocksInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionPhaseInstance> findByBlocksId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciPhaseInstanceRepository.findByBlocksId(id);
    }

    /**
     * Finds BehaviorChangeInterventionPhaseInstance entities by a BCIModuleInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionPhaseInstance> with the given BCIModuleInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionPhaseInstance> findByModulesId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciPhaseInstanceRepository.findByModulesId(id);
    }

    /**
     * Checks if a BehaviorChangeInterventionPhaseInstance exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciPhaseInstanceRepository.existsById(id);
    }
}
