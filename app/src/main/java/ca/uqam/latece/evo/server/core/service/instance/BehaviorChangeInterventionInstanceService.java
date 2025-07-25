package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.event.BCIInstanceEvent;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionInstanceRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class providing business operations for BehaviorChangeInterventionInstance entities.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class BehaviorChangeInterventionInstanceService extends AbstractEvoService<BehaviorChangeInterventionInstance> {
    private final static Logger logger =  LoggerFactory.getLogger(BehaviorChangeInterventionInstanceService.class);

    @Autowired
    private BehaviorChangeInterventionInstanceRepository bciInstanceRepository;

    /**
     * Creates a BehaviorChangeInterventionInstance in the database.
     * @param bciInstance BehaviorChangeInterventionInstance.
     * @return The created BehaviorChangeInterventionInstance.
     * @throws IllegalArgumentException if bciInstance is null.
     */
    @Override
    public BehaviorChangeInterventionInstance create(BehaviorChangeInterventionInstance bciInstance) {
        BehaviorChangeInterventionInstance saved = null;

        saved = this.bciInstanceRepository.save(bciInstance);
        logger.info("BehaviorChangeInterventionInstance created: {}", saved);
        return saved;
    }

    /**
     * Updates a BehaviorChangeInterventionInstance in the database.
     * @param bciInstance BehaviorChangeInterventionInstance.
     * @return The updated BehaviorChangeInterventionInstance.
     * @throws IllegalArgumentException if bciInstance is null.
     */
    @Override
    public BehaviorChangeInterventionInstance update(BehaviorChangeInterventionInstance bciInstance) {
        BehaviorChangeInterventionInstance updated = null;
        BehaviorChangeInterventionInstance found = findById(bciInstance.getId());

//        ObjectValidator.validateObject(bciInstance);
//        ObjectValidator.validateObject(bciInstance.getStage());
//        ObjectValidator.validateObject(bciInstance.getActivities());

        if (found != null) {
            updated = this.bciInstanceRepository.save(bciInstance);
            this.publishEvent(new BCIInstanceEvent(updated));
        }
        return updated;
    }

    /**
     * Saves the given BehaviorChangeInterventionInstance in the database.
     * @param bciInstance BehaviorChangeInterventionInstance.
     * @return The saved BehaviorChangeInterventionInstance.
     * @throws IllegalArgumentException if bciInstance is null.
     */
    @Override
    public BehaviorChangeInterventionInstance save(BehaviorChangeInterventionInstance bciInstance) {
        return this.bciInstanceRepository.save(bciInstance);
    }

    /**
     * Deletes a BehaviorChangeInterventionInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        bciInstanceRepository.deleteById(id);
        logger.info("BehaviorChangeInterventionInstance deleted {}", id);
    }

    /**
     * Finds all BehaviorChangeInterventionInstance entities.
     * @return List<BehaviorChangeInterventionInstance>.
     */
    public List<BehaviorChangeInterventionInstance> findAll() {
        return this.bciInstanceRepository.findAll();
    }

    /**
     * Finds a BehaviorChangeInterventionInstance by its id.
     * @param id Long.
     * @return BehaviorChangeInterventionInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public BehaviorChangeInterventionInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciInstanceRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("BehaviorChangeInterventionInstance not found"));
    }

    /**
     * Finds a BehaviorChangeInterventionInstance by their patient id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> with the given patient id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionInstance> findByPatientId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciInstanceRepository.findByPatientId(id);
    }

    /**
     * Finds a BehaviorChangeInterventionInstance by their currentPhase id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> with the given currentPhase id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionInstance> findByCurrentPhaseId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciInstanceRepository.findByCurrentPhaseId(id);
    }

    /**
     * Finds BehaviorChangeInterventionInstance entities by a BehaviorChangeInterventionPhaseInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> with the given BehaviorChangeInterventionPhaseInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionInstance> findByPhasesId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciInstanceRepository.findByPhasesId(id);
    }

    /**
     * Checks if a BehaviorChangeInterventionInstance exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciInstanceRepository.existsById(id);
    }
}
