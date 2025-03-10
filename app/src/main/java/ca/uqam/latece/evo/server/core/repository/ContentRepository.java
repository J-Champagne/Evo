package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.model.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Content repository creates CRUD implementation at runtime automatically.
 * @since 22.01.2025
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface ContentRepository extends EvoRepository<Content> {

    /**
     * Finds a list of Content entities by their name.
     * @param name the name of the Content to search for.
     * @return the Content with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    List<Content> findByName(String name);

    /**
     * Checks if a content entity with the specified name exists in the repository.
     * @param name the name of the content to check for existence, must not be null.
     * @return true if a content with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    boolean existsByName(String name);

    /**
     * Finds a list of Content entities by their type.
     * @param type the type of the content to search for.
     * @return a list of Content entities matching the specified type.
     */
    List<Content> findByType(String type);

    /**
     * Retrieves a list of Content entities that match the specified BCI Activity Id.
     * @param bciActivityId The BCI Activity Id to filter Content entities by, must not be null.
     * @return a list of Content entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    @Query(value = "SELECT co.* FROM content AS co " +
            "JOIN bci_activity_content bcc ON (co.content_id = bcc.bci_activity_content_content_id) " +
            "WHERE bcc.bci_activity_content_bci_activity_id = :bci_activity_id",
            nativeQuery = true)
    List<Content> findByBCIActivity(@Param("bci_activity_id") Long bciActivityId);

    /**
     * Retrieves a list of Content entities that match the specified Skill Id.
     * @param skillId The Skill Id to filter Content entities by, must not be null.
     * @return a list of Content entities that have the specified Skill Id, or an empty list if no matches are found.
     */
    @Query(value = "SELECT co.* FROM content AS co " +
            "JOIN skill_content skc ON (co.content_id = skc.skill_content_content_id) " +
            "WHERE skc.skill_content_skill_id = :skill_id",
            nativeQuery = true)
    List<Content> findBySkill(@Param("skill_id") Long skillId);
}

