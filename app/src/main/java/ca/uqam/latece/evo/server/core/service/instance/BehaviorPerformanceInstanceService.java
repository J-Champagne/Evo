package ca.uqam.latece.evo.server.core.service.instance;


import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.BehaviorPerformance;
import ca.uqam.latece.evo.server.core.service.BehaviorPerformanceService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Behavior Performance Instance Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Getter
@Service
public class BehaviorPerformanceInstanceService extends BCIActivityInstanceService {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorPerformanceInstanceService.class);

    private BehaviorPerformance behaviorPerformance;
    @Autowired
    private BehaviorPerformanceService behaviorPerformanceService;

    public BehaviorPerformanceInstanceService() {
        behaviorPerformance = new BehaviorPerformance();
    }

    public BehaviorPerformanceInstanceService(BehaviorPerformance behaviorPerformance) {
        ObjectValidator.validateObject(behaviorPerformance);
        this.behaviorPerformance = behaviorPerformance;
    }

    public void setBehaviorPerformance(BehaviorPerformance behaviorPerformance) {
        ObjectValidator.validateObject(behaviorPerformance);
        this.behaviorPerformance = behaviorPerformance;
    }

    /**
     * Inserts a BehaviorPerformanceInstance in the database.
     * @param behaviorPerformance The BehaviorPerformanceInstance entity.
     * @return The saved BehaviorPerformanceInstance.
     * @throws IllegalArgumentException in case the given BehaviorPerformanceInstance is null.
     */
    public BehaviorPerformance create (BehaviorPerformance behaviorPerformance) {
        return this.behaviorPerformanceService.create(behaviorPerformance);
    }

    /**
     * Updates a BehaviorPerformance in the database.
     * @param behaviorPerformance The BehaviorPerformance entity.
     * @return The saved BehaviorPerformance.
     * @throws IllegalArgumentException in case the given BehaviorPerformance is null.
     */
    public BehaviorPerformance update (BehaviorPerformance behaviorPerformance) {
        return this.behaviorPerformanceService.update(behaviorPerformance);
    }

    /**
     * Checks if an entity with the specified identifier exists in the repository.
     * @param id the unique identifier of the entity to be checked for existence.
     * @return true if an entity with the given identifier exists, false otherwise.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @Override
    public boolean existsById(Long id) {
        return this.behaviorPerformanceService.existsById(id);
    }

    /**
     * Checks if a BehaviorPerformance entity with the specified name exists in the repository.
     * @param name the name of the BehaviorPerformance to check for existence, must not be null.
     * @return true if a BehaviorPerformance with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        return this.behaviorPerformanceService.existsByName(name);
    }

    /**
     * Deletes a behavior performance from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior performance to be deleted.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @Override
    public void deleteById(Long id) {
        this.behaviorPerformanceService.deleteById(id);
    }

    /**
     * Retrieves a BehaviorPerformance by id.
     * @param id the unique identifier of the behavior performance instance to be retrieved.
     * @return the behavior performance corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     * @throws EntityNotFoundException if the behavior performance instance not found.
     */
    @Override
    public BehaviorPerformance findById(Long id) {
        return this.behaviorPerformanceService.findById(id);
    }

    /**
     * Finds a list of BehaviorPerformance entities by their name.
     * @param name the name of the BehaviorPerformance to search for.
     * @return the BehaviorPerformance with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    public List<BehaviorPerformance> findByName(String name) {
        return this.behaviorPerformanceService.findByName(name);
    }

    /**
     * Finds a list of BehaviorPerformance entities by their type.
     * @param type the type of the BehaviorPerformance to search for.
     * @return a list of BehaviorPerformance entities matching the specified type.
     */
    public List<BehaviorPerformance> findByType(ActivityType type) {
        return this.behaviorPerformanceService.findByType(type);
    }
}
