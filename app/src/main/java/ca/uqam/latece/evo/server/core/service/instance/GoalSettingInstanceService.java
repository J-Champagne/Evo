package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.GoalSetting;
import ca.uqam.latece.evo.server.core.service.GoalSettingService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * GoalSetting Instance Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
public class GoalSettingInstanceService extends BCIActivityInstanceService {
    private static final Logger logger = LoggerFactory.getLogger(GoalSettingInstanceService.class);

    private GoalSetting goalSetting;
    private BCIActivity bciActivity;

    @Autowired
    private GoalSettingService goalSettingService;
    @Qualifier("BCIActivityInstanceService")
    @Autowired
    private BCIActivityInstanceService activityInstanceService;


    /**
     * Creates GoalSetting without association with BCIActivity.
     */
    public GoalSettingInstanceService() {
        this.goalSetting = new GoalSetting();
        this.buildBCIActivity();
    }

    /**
     * Build the BCIActivity.
     */
    private void buildBCIActivity() {
        this.bciActivity = new BCIActivity();
    }

    /**
     * Creates the GoalSetting associated with BCIActivity.
     * @param goalSetting The GoalSetting entity.
     * @param bciActivity The BCIActivity entity.
     */
    public GoalSettingInstanceService(GoalSetting goalSetting, BCIActivity bciActivity) {
        ObjectValidator.validateObject(goalSetting);
        this.verifyBCIActivity(bciActivity);
        this.goalSetting = goalSetting;
        this.buildBCIActivity();
    }

    /**
     * Verify the BCIActivity before set.
     * @param bciActivity The BCIActivity entity.
     */
    private void verifyBCIActivity(BCIActivity bciActivity) {
        if (bciActivity == null) {
            logger.error("BCIActivity cannot be null!");
            throw new IllegalArgumentException("BCIActivity cannot be null!");
        } else {
            if (bciActivity.getId() == null) {
                logger.error("BCIActivity id cannot be null!");
                throw new IllegalArgumentException("BCIActivity id cannot be null!");
            } else {
                if (this.activityInstanceService.existsById(bciActivity.getId())) {
                    this.bciActivity = bciActivity;
                } else {
                    logger.error("BCIActivity not found! BCIActivity data: {}", bciActivity);
                    throw new IllegalArgumentException("BCIActivity not found!");
                }
            }
        }
    }

    public GoalSetting getGoalSetting() {
        return goalSetting;
    }

    public void setGoalSetting(GoalSetting goalSetting) {
        ObjectValidator.validateObject(goalSetting);
        this.goalSetting = goalSetting;
    }

    @Override
    public BCIActivity getBciActivity() {
        return bciActivity;
    }

    public void setBciActivity(BCIActivity bciActivity) {
        this.verifyBCIActivity(bciActivity);
    }

    public BCIActivityInstanceService getActivityInstanceService() {
        return activityInstanceService;
    }

    /**
     * Inserts a GoalSetting in the database.
     * @param goalSetting The GoalSetting entity.
     * @return The saved GoalSetting.
     * @throws IllegalArgumentException in case the given GoalSetting is null.
     */
    public GoalSetting create(GoalSetting goalSetting) {
        this.goalSetting = this.goalSettingService.create(goalSetting);
        return this.goalSetting;
    }

    /**
     * Inserts a GoalSetting in the database with a BCIActivity.
     * @param goalSetting The GoalSetting entity.
     * @param bciActivity The BCIActivity entity.
     * @return The saved GoalSetting.
     * @throws IllegalArgumentException in case the given GoalSetting or BCIActivity are null.
     */
    public GoalSetting create(GoalSetting goalSetting, BCIActivity bciActivity) {
       this.verifyBCIActivity(bciActivity);

        if (goalSetting == null) {
            logger.error("GoalSetting cannot be null!");
            throw new IllegalArgumentException("GoalSetting cannot be null!");
        } else {
            goalSetting.setBciActivity(this.getBciActivity());
            this.goalSetting = this.goalSettingService.create(goalSetting);
            logger.info("Created goal setting: {}", this.goalSetting);
        }

        return this.goalSetting;
    }

    /**
     * Updates a GoalSetting in the database.
     * @param goalSetting The GoalSetting entity.
     * @return The saved GoalSetting.
     * @throws IllegalArgumentException in case the given GoalSetting is null.
     */
    public GoalSetting update(GoalSetting goalSetting, BCIActivity bciActivity) {
        this.verifyBCIActivity(bciActivity);

        if (goalSetting == null) {
            logger.error("GoalSetting cannot be null!");
            throw new IllegalArgumentException("GoalSetting cannot be null!");
        } else {
            if (goalSetting.getId() == null) {
                logger.error("GoalSetting id cannot be null!");
                throw new IllegalArgumentException("GoalSetting id cannot be null!");
            } else {
                goalSetting.setBciActivity(this.getBciActivity());
                this.goalSetting = this.goalSettingService.create(goalSetting);
                logger.info("Updated goal setting with BCIActivity: {}", this.goalSetting);
            }
        }

        return this.goalSetting;
    }

    /**
     * Updates a GoalSetting in the database.
     * @param goalSetting The GoalSetting entity.
     * @return The saved GoalSetting.
     * @throws IllegalArgumentException in case the given GoalSetting is null.
     */
    public GoalSetting update(GoalSetting goalSetting) {
        this.goalSetting = this.goalSettingService.update(goalSetting);
        logger.info("Updated goal setting: {}", this.goalSetting);
        return this.goalSetting;
    }


    /**
     * Checks if a GoalSetting entity with the specified id exists in the repository.
     * @param id the id of the GoalSetting to check for existence, must not be null.
     * @return true if a GoalSetting with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    public boolean existsById(Long id) {
        return this.goalSettingService.existsById(id);
    }

    /**
     * Checks if a GoalSetting entity with the specified name exists in the repository.
     * @param name the name of the GoalSetting to check for existence, must not be null.
     * @return true if a GoalSetting with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        return this.goalSettingService.existsByName(name);
    }

    /**
     * Deletes the GoalSetting with the given id.
     * <p>
     * If the GoalSetting is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the GoalSetting to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    public void deleteById(Long id) {
        this.goalSettingService.deleteById(id);
    }

    /**
     * Retrieves a GoalSetting by its id.
     * @param id The GoalSetting Id to filter GoalSetting entities by, must not be null.
     * @return the GoalSetting with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     * @throws RuntimeException if the goal setting not found.
     */
    public GoalSetting findById(Long id) {
        return this.goalSettingService.findById(id);
    }

    /**
     * Finds a list of GoalSetting entities by their name.
     * @param name the type of the GoalSetting to search for.
     * @return a list of GoalSetting entities matching the specified name.
     */
    public List<GoalSetting> findByName(String name) {
        return this.goalSettingService.findByName(name);
    }

    /**
     * Finds a list of GoalSetting entities by their type.
     * @param type the type of the GoalSetting to search for.
     * @return a list of GoalSetting entities matching the specified type.
     */
    public List<GoalSetting> findByType(ActivityType type) {
        return this.goalSettingService.findByType(type);
    }
}
