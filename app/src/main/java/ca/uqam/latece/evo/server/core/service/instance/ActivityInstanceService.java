package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.ActivityInstance;
import ca.uqam.latece.evo.server.core.repository.instance.ActivityInstanceRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * ActivityInstance Service.
 * @author Julien Champagne.
 */
@Service
@Transactional
public class ActivityInstanceService extends AbstractEvoService<ActivityInstance> {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInstanceService.class);

    @Autowired
    private ActivityInstanceRepository activityInstanceRepository;

    /**
     * Creates a ActivityInstance in the database.
     * @param activityInstance ActivityInstance.
     * @return The created ActivityInstance.
     * @throws IllegalArgumentException if activityInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public ActivityInstance create(ActivityInstance activityInstance) {
        ActivityInstance saved = null;

        ObjectValidator.validateObject(activityInstance);

        saved = this.activityInstanceRepository.save(activityInstance);
        logger.info("ActivityInstance created: {}", saved);
        return saved;
    }

    /**
     * Updates a ActivityInstance in the database.
     * @param activityInstance ActivityInstance.
     * @return The updated ActivityInstance.
     * @throws IllegalArgumentException if activityInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public ActivityInstance update(ActivityInstance activityInstance) {
        ActivityInstance updated = null;
        ActivityInstance found = findById(activityInstance.getId());

        ObjectValidator.validateObject(activityInstance);
        ObjectValidator.validateId(activityInstance.getId());

        if (found != null) {
            updated = this.activityInstanceRepository.save(activityInstance);
        }
        return updated;
    }

    /**
     * Saves the given ActivityInstance in the database.
     * @param activityInstance ActivityInstance.
     * @return The saved ActivityInstance.
     * @throws IllegalArgumentException if activityInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public ActivityInstance save(ActivityInstance activityInstance) {
        ObjectValidator.validateString(activityInstance.getStatus());
        ObjectValidator.validateObject(activityInstance.getEntryDate());
        ObjectValidator.validateObject(activityInstance.getExitDate());
        return this.activityInstanceRepository.save(activityInstance);
    }

    /**
     * Deletes a ActivityInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        activityInstanceRepository.deleteById(id);
        logger.info("ActivityInstance deleted {}", id);
    }

    /**
     * Finds all ActivityInstance entities.
     * @return List<ActivityInstance>.
     */
    public List<ActivityInstance> findAll() {
        return this.activityInstanceRepository.findAll();
    }

    /**
     * Finds a ActivityInstance by its id.
     * @param id Long.
     * @return ActivityInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public ActivityInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.activityInstanceRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("ActivityInstance not found"));
    }

    /**
     * Finds ActivityInstance entities by their status.
     * @param status String.
     * @return List<ActivityInstance> with the given status.
     * @throws IllegalArgumentException if status is null.
     */
    public List<ActivityInstance> findByStatus(String status) {
        ObjectValidator.validateString(status);
        return this.activityInstanceRepository.findByStatus(status);
    }

    /**
     * Finds ActivityInstance entities by their entryDate.
     * @param entryDate LocalDate.
     * @return List<ActivityInstance> with the given entryDate.
     * @throws IllegalArgumentException if entryDate is null.
     */
    public List<ActivityInstance> findByEntryDate(LocalDate entryDate) {
        ObjectValidator.validateObject(entryDate);
        return this.activityInstanceRepository.findByEntryDate(entryDate);
    }

    /**
     * Finds ActivityInstance entities by their exitDate.
     * @param exitDate LocalDate.
     * @return List<ActivityInstance> with the given exitDate.
     * @throws IllegalArgumentException if exitDate is null.
     */
    public List<ActivityInstance> findByExitDate(LocalDate exitDate) {
        ObjectValidator.validateObject(exitDate);
        return this.activityInstanceRepository.findByEntryDate(exitDate);
    }

    /**
     * Checks if an ActivityInstance exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.activityInstanceRepository.existsById(id);
    }
}