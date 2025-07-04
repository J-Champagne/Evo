package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * BCIActivity repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface BCIActivityRepository extends EvoRepository<BCIActivity> {

    /**
     * Finds a list of BCIActivity entities by their name.
     * @param name the name of the BCIActivity to search for.
     * @return the BCIActivity with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    List<BCIActivity> findByName(String name);

    /**
     * Checks if a BCIActivity entity with the specified name exists in the repository.
     * @param name the name of the BCIActivity to check for existence, must not be null.
     * @return true if a BCIActivity with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    boolean existsByName(String name);

    /**
     * Finds a list of BCIActivity entities by their type.
     * @param type the type of the BCIActivity to search for.
     * @return a list of BCIActivity entities matching the specified type.
     */
    List<BCIActivity> findByType(ActivityType type);

     /**
     * Retrieves a list of BCIActivity entities that match the specified Develops Id.
     * @param developsBCIActivityId The Develops Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Develops id, or an empty list if no matches are found.
     */
    List<BCIActivity> findByDevelopsBCIActivity_Id(Long developsBCIActivityId);

    /**
     * Retrieves a list of BCIActivity entities that match the specified Requires Id.
     * @param requiresId The Requires Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Requires id, or an empty list if no matches are found.
     */
    List<BCIActivity> findByRequiresBCIActivities_Id(Long requiresId);

    /**
     * Retrieves a list of BCIActivity entities that match the specified Role id.
     * @param partiesId The Role od to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Role id, or an empty list if no matches are found.
     */
    List<BCIActivity> findByParties_Id(Long partiesId);

    /**
     * Retrieves a list of BCIActivity entities that match the specified Content Id.
     * @param contentId The Content Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Content id, or an empty list if no matches are found.
     */
    List<BCIActivity> findByContentBCIActivities_Id(Long contentId);
}
