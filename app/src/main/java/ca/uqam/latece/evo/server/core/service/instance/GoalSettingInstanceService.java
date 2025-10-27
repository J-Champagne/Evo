package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.GoalSettingInstance;
import ca.uqam.latece.evo.server.core.repository.instance.GoalSettingInstanceRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * GoalSetting Instance Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
public class GoalSettingInstanceService extends AbstractEvoService<GoalSettingInstance> {
    private static final Logger logger = LoggerFactory.getLogger(GoalSettingInstanceService.class);

    @Autowired
    private GoalSettingInstanceRepository goalSettingInstanceRepository;

    /**
     * Inserts a GoalSettingInstance in the database.
     * @param goalSettingInstance The GoalSetting entity.
     * @return The saved GoalSettingInstance.
     * @throws IllegalArgumentException in case the given GoalSettingInstance is null.
     */
    @Transactional
    public GoalSettingInstance create(GoalSettingInstance goalSettingInstance) {
        GoalSettingInstance saved = null;
        ObjectValidator.validateObject(goalSettingInstance);
        saved = this.save(goalSettingInstance);
        logger.info("Goal Setting Instance created: {}", saved);
        return saved;
    }

    /**
     * Updates a GoalSettingInstance in the database.
     * @param goalSettingInstance The GoalSettingInstance entity.
     * @return The saved GoalSettingInstance.
     * @throws IllegalArgumentException in case the given GoalSettingInstance is null.
     */
    @Transactional
    public GoalSettingInstance update(GoalSettingInstance goalSettingInstance) {
        GoalSettingInstance updated = null;
        ObjectValidator.validateObject(goalSettingInstance);
        ObjectValidator.validateId(goalSettingInstance.getId());
        updated = this.save(goalSettingInstance);
        logger.info("Goal Setting Instance updated: {}", updated);
        return updated;
    }

    /**
     * Method used to create or update an GoalSettingInstance.
     * @param evoModel he GoalSettingInstance entity.
     * @return The GoalSettingInstance.
     */
    @Transactional
    @Override
    protected GoalSettingInstance save(GoalSettingInstance evoModel) {
        ObjectValidator.validateObject(evoModel);
        return goalSettingInstanceRepository.save(evoModel);
    }

    /**
     * Checks if a GoalSettingInstance entity with the specified id exists in the repository.
     * @param id the id of the GoalSettingInstance to check for existence, must not be null.
     * @return true if a GoalSettingInstance with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.goalSettingInstanceRepository.existsById(id);
    }

    /**
     * Deletes the GoalSettingInstance with the given id.
     * <p>
     * If the GoalSettingInstance is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the GoalSettingInstance to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Transactional
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        this.goalSettingInstanceRepository.deleteById(id);
        logger.info("Goal Setting Instance deleted: {}", id);
    }

    /**
     * Retrieves a GoalSettingInstance by its id.
     * @param id The GoalSettingInstance Id to filter GoalSettingInstance entities by, must not be null.
     * @return the GoalSettingInstance with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    public GoalSettingInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.goalSettingInstanceRepository.findById(id).orElse(null);
    }

    /**
     * Checks if a GoalSettingInstance entity with the specified status exists in the repository.
     * @param status the status of the GoalSettingInstance to check for existence, must not be null.
     * @return A list of GoalSettingInstance with the specified status.
     * @throws IllegalArgumentException if the status is null.
     */
    public List<GoalSettingInstance> findByStatus(ExecutionStatus status) {
        ObjectValidator.validateObject(status);
        return this.goalSettingInstanceRepository.findByStatus(status);
    }

    /**
     * Gets all GoalSettingInstance.
     * @return all GoalSettingInstance.
     */
    @Override
    public List<GoalSettingInstance> findAll() {
        return this.goalSettingInstanceRepository.findAll();
    }

    /**
     * Finds a GoalSettingInstance by its Participant id.
     * @param id Long.
     * @return GoalSettingInstance with the given Participant id.
     * @throws IllegalArgumentException if id is null.
     */
    public GoalSettingInstance findByParticipantsId(Long id) {
        return this.goalSettingInstanceRepository.findByParticipantsId(id);
    }
}
