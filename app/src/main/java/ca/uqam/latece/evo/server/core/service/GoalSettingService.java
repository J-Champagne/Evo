package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.GoalSetting;
import ca.uqam.latece.evo.server.core.repository.GoalSettingRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * GoalSetting Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class GoalSettingService extends AbstractEvoService<GoalSetting> {
    private static final Logger logger = LoggerFactory.getLogger(GoalSettingService.class);

    @Autowired
    private GoalSettingRepository goalSettingRepository;

    /**
     * Inserts a GoalSetting in the database.
     * @param goalSetting The GoalSetting entity.
     * @return The saved GoalSetting.
     * @throws IllegalArgumentException in case the given GoalSetting is null.
     * @throws OptimisticLockingFailureException when the GoalSetting uses optimistic locking and has a version
     *         attribute with a different value from that found in the persistence store. Also thrown if the
     *         entity is assumed to be present but does not exist in the database.
     */
    @Override
    public GoalSetting create(GoalSetting goalSetting) {
        GoalSetting goalSettingSaved = new GoalSetting();

        ObjectValidator.validateObject(goalSetting);
        ObjectValidator.validateObject(goalSetting.getName());

        // The name should be unique.
        if(this.existsByName(goalSetting.getName())) {
            throw this.createDuplicateException(goalSetting);
        } else {
            goalSettingSaved = this.save(goalSetting);
            logger.info("Goal Setting created: {}", goalSettingSaved);
        }

        return goalSettingSaved;
    }

    /**
     * Create duplicate GoalSetting Exception.
     * @param goalSetting the GoalSetting entity.
     * @return an exception object.
     */
    private IllegalArgumentException createDuplicateException(GoalSetting goalSetting) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_NAME_ALREADY_REGISTERED +
                " Goal Setting Name: " + goalSetting.getName());
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    /**
     * Updates a GoalSetting in the database.
     * @param goalSetting The GoalSetting entity.
     * @return The saved GoalSetting.
     * @throws IllegalArgumentException in case the given GoalSetting is null.
     * @throws OptimisticLockingFailureException when the GoalSetting uses optimistic locking and has a version
     *     attribute with a different value from that found in the persistence store. Also thrown if the entity
     *     is assumed to be present but does not exist in the database.
     */
    @Override
    public GoalSetting update(GoalSetting goalSetting) {
        GoalSetting goalSettingSaved = new GoalSetting();

        ObjectValidator.validateObject(goalSetting);
        ObjectValidator.validateObject(goalSetting.getName());

        // The name should be unique.
        if(this.existsByName(goalSetting.getName())) {
            throw this.createDuplicateException(goalSetting);
        } else {
            goalSettingSaved = this.save(goalSetting);
            logger.info("Goal Setting updated: {}", goalSettingSaved);
        }

        return goalSettingSaved;
    }

    /**
     * Method used to create or update an GoalSetting.
     * @param evoModel the GoalSetting entity.
     * @return The GoalSetting.
     */
    @Transactional
    @Override
    protected GoalSetting save(GoalSetting evoModel) {
        ObjectValidator.validateObject(evoModel);
        return goalSettingRepository.save(evoModel);
    }

    /**
     * Checks if a GoalSetting entity with the specified name exists in the repository.
     * @param name the name of the GoalSetting to check for existence, must not be null.
     * @return true if a GoalSetting with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        ObjectValidator.validateString(name);
        return goalSettingRepository.existsByName(name);
    }

    /**
     * Checks if a GoalSetting entity with the specified id exists in the repository.
     * @param id the id of the GoalSetting to check for existence, must not be null.
     * @return true if a GoalSetting with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return goalSettingRepository.existsById(id);
    }

    /**
     * Deletes the GoalSetting with the given id.
     * <p>
     * If the GoalSetting is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the GoalSetting to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        goalSettingRepository.deleteById(id);
        logger.info("Goal Setting deleted: {}", id);
    }

    /**
     * Retrieves a GoalSetting by its id.
     * @param id The GoalSetting Id to filter GoalSetting entities by, must not be null.
     * @return the GoalSetting with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    @Override
    public GoalSetting findById(Long id) {
        ObjectValidator.validateId(id);
        return goalSettingRepository.findById(id).orElse(null);
    }

    /**
     * Finds a list of GoalSetting entities by their name.
     * @param name the type of the GoalSetting to search for.
     * @return a list of GoalSetting entities matching the specified name.
     */
    public List<GoalSetting> findByName(String name) {
        ObjectValidator.validateString(name);
        return goalSettingRepository.findByName(name);
    }

    /**
     * Finds a list of GoalSetting entities by their type.
     * @param type the type of the GoalSetting to search for.
     * @return a list of GoalSetting entities matching the specified type.
     */
    public List<GoalSetting> findByType(ActivityType type) {
        ObjectValidator.validateObject(type);
        return goalSettingRepository.findByType(type);
    }

    /**
     * Gets all GoalSetting.
     * @return all GoalSetting.
     */
    @Override
    public List<GoalSetting> findAll() {
        return goalSettingRepository.findAll();
    }
}
