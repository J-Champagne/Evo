package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
     * @param developsId The Develops Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Develops id, or an empty list if no matches are found.
     */
    @Query(value = "SELECT bci.*, gst.*, rep.*, asst.*, 0 AS clazz_ FROM bci_activity AS bci " +
            "JOIN develops dev ON (bci.bci_activity_id = dev.develops_bci_activity_id) " +
            "LEFT OUTER JOIN goal_setting gst ON (bci.bci_activity_id = gst.goal_setting_id) " +
            "LEFT OUTER JOIN reporting rep ON (bci.bci_activity_id = rep.reporting_id) " +
            "LEFT OUTER JOIN assessment asst ON (bci.bci_activity_id = asst.assessment_id) " +
            "WHERE dev.develops_id = :develops_id",
            nativeQuery = true)
    List<BCIActivity> findByDevelops(@Param("develops_id") Long developsId);

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
    @Query(value = "SELECT bci.*, gst.*, rep.*, asst.*, 0 AS clazz_ FROM bci_activity AS bci " +
            "JOIN requires req ON (bci.bci_activity_id = req.requires_bci_activity_id) " +
            "LEFT OUTER JOIN goal_setting gst ON (bci.bci_activity_id = gst.goal_setting_id) " +
            "LEFT OUTER JOIN reporting rep ON (bci.bci_activity_id = rep.reporting_id) " +
            "LEFT OUTER JOIN assessment asst ON (bci.bci_activity_id = asst.assessment_id) " +
            "WHERE req.requires_id = :requires_id",
            nativeQuery = true)
    List<BCIActivity> findByRequires(@Param("requires_id")Long requiresId);

    /**
     * Retrieves a list of BCIActivity entities that match the specified Requires Id.
     * @param requiresId The Requires Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Requires id, or an empty list if no matches are found.
     */
    List<BCIActivity> findByRequiresBCIActivities_Id(Long requiresId);

    /**
     * Retrieves a list of BCIActivity entities that match the specified Role Id.
     * @param roleId The Role Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Role id, or an empty list if no matches are found.
     */
    @Query(value = "SELECT bci.*, gst.*, rep.*, asst.*, 0 AS clazz_ FROM bci_activity AS bci " +
            "JOIN bci_activity_role bro ON (bci.bci_activity_id = bro.bci_activity_role_bci_activity_id) " +
            "LEFT OUTER JOIN goal_setting gst ON (bci.bci_activity_id = gst.goal_setting_id) " +
            "LEFT OUTER JOIN reporting rep ON (bci.bci_activity_id = rep.reporting_id) " +
            "LEFT OUTER JOIN assessment asst ON (bci.bci_activity_id = asst.assessment_id) " +
            "WHERE bro.bci_activity_role_role_id = :role_id",
            nativeQuery = true)
    List<BCIActivity> findByRole(@Param("role_id") Long roleId);

    /**
     * Retrieves a list of BCIActivity entities that match the specified Role id.
     * @param roleId The Role od to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Role id, or an empty list if no matches are found.
     */
    List<BCIActivity> findByRoleBCIActivities_Id(Long roleId);

    /**
     * Retrieves a list of BCIActivity entities that match the specified Content Id.
     * @param contentId The Content Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Content id, or an empty list if no matches are found.
     */
    @Query(value = "SELECT bci.*, gst.*, rep.*, asst.*, 0 AS clazz_ FROM bci_activity AS bci " +
            "JOIN bci_activity_content bco ON (bci.bci_activity_id = bco.bci_activity_content_bci_activity_id) " +
            "LEFT OUTER JOIN goal_setting gst ON (bci.bci_activity_id = gst.goal_setting_id) " +
            "LEFT OUTER JOIN reporting rep ON (bci.bci_activity_id = rep.reporting_id) " +
            "LEFT OUTER JOIN assessment asst ON (bci.bci_activity_id = asst.assessment_id) " +
            "WHERE bco.bci_activity_content_content_id = :content_id",
            nativeQuery = true)
    List<BCIActivity> findByContent(@Param("content_id") Long contentId);

    /**
     * Retrieves a list of BCIActivity entities that match the specified Content Id.
     * @param contentId The Content Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Content id, or an empty list if no matches are found.
     */
    List<BCIActivity> findByContentBCIActivities_Id(Long contentId);
}
