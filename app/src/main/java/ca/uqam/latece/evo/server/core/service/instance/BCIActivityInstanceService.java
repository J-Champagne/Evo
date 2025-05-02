package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.GoalSetting;
import ca.uqam.latece.evo.server.core.service.BCIActivityService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BCIActivity Instance Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
public class BCIActivityInstanceService {
    private static final Logger logger = LoggerFactory.getLogger(BCIActivityInstanceService.class);

    private BCIActivity bciActivity;
    private GoalSetting goalSetting;

    @Autowired
    private BCIActivityService bciActivityService;
    private GoalSettingInstanceService goalSettingInstanceService;


    public BCIActivityInstanceService() {
    }

    /**
     * Creates a BCIActivity without association.
     * @param bciActivity
     */
    public BCIActivityInstanceService(BCIActivity bciActivity) {
        this.bciActivitySetUp(bciActivity);
    }

    /**
     * Creates the BCIActivity associated with GoalSetting.
     * @param bciActivity The BCIActivity entity.
     * @param goalSetting The GoalSetting entity.
     */
    public BCIActivityInstanceService(BCIActivity bciActivity, GoalSetting goalSetting) {
        this.bciActivitySetUp(bciActivity);

        if (goalSetting == null) {
            logger.error("goalSetting cannot be null!");
            throw new IllegalArgumentException("goalSetting cannot be null!");
        } else {
            this.setGoalSetting(goalSetting);
            this.goalSettingInstanceService = new GoalSettingInstanceService(this.getGoalSetting(), this.getBciActivity());
        }
    }

    private void bciActivitySetUp(BCIActivity bciActivity){
        if (bciActivity == null) {
            logger.error("bciActivity cannot be null!");
            throw new IllegalArgumentException("bciActivity cannot be null!");
        } else {
            this.bciActivity = bciActivity;
        }
    }

    public void setBCIActivity(@NotNull BCIActivity bciActivity) {
        this.bciActivity = bciActivity;
    }

    public BCIActivity getBciActivity() {
        return bciActivity;
    }

    public void setGoalSetting(@NotNull GoalSetting goalSetting) {
        this.goalSetting = goalSetting;

        if (bciActivity != null) {
            this.goalSetting.setBciActivity(this.bciActivity);
        }
    }

    public GoalSetting getGoalSetting() {
        return this.goalSetting;
    }

    /**
     * Inserts a BCIActivity in the database.
     * @param bciActivity The BCIActivity entity.
     * @return The saved BCIActivity.
     * @throws IllegalArgumentException in case the given BCIActivity is null.
     */
    public BCIActivity create (BCIActivity bciActivity) {
        return this.bciActivityService.create(bciActivity);
    }

    /**
     * Updates a BCIActivity in the database.
     * @param bciActivity The BCIActivity entity.
     * @return The saved BCIActivity.
     * @throws IllegalArgumentException in case the given BCIActivity is null.
     */
    public BCIActivity update (BCIActivity bciActivity) {
        return this.bciActivityService.update(bciActivity);
    }

    /**
     * Checks if a BCIActivity entity with the specified id exists in the repository.
     * @param id the id of the BCIActivity to check for existence, must not be null.
     * @return true if a BCIActivity with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    public boolean existsById(Long id) {
        return this.bciActivityService.existsById(id);
    }

    /**
     * Deletes the BCIActivity with the given id.If the BCIActivity is not found in the persistence
     * store it is silently ignored.
     * @param id the unique identifier of the BCIActivity to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    public void deleteById(Long id) {
        this.bciActivityService.deleteById(id);
    }

    /**
     * Retrieves a BCIActivity by its id.
     * @param id The BCIActivity Id to filter BCIActivity entities by, must not be null.
     * @return the BCIActivity with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     * @throws RuntimeException if the BCIActivity not found.
     */
    public BCIActivity findById(Long id) {
        return this.bciActivityService.findById(id);
    }

    /**
     * Gets all BCIActivity.
     * @return all BCIActivity.
     */
    public List<BCIActivity> findAll() {
        return this.bciActivityService.findAll();
    }
}
