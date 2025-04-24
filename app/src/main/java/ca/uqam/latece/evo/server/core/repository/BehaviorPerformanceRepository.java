package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.BehaviorPerformance;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BehaviorPerformance repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface BehaviorPerformanceRepository extends EvoRepository<BehaviorPerformance>{
    /**
     * Finds a list of BehaviorPerformance entities by their name.
     * @param name the name of the BehaviorPerformance to search for.
     * @return the BehaviorPerformance with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    List<BehaviorPerformance> findByName(String name);

    /**
     * Checks if a BehaviorPerformance entity with the specified name exists in the repository.
     * @param name the name of the BehaviorPerformance to check for existence, must not be null.
     * @return true if a BehaviorPerformance with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    boolean existsByName(String name);

    /**
     * Finds a list of BehaviorPerformance entities by their type.
     * @param type the type of the BehaviorPerformance to search for.
     * @return a list of BehaviorPerformance entities matching the specified type.
     */
    List<BehaviorPerformance> findByType(ActivityType type);

}
