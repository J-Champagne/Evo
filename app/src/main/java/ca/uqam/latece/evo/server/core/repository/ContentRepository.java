package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.model.Content;
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
     * Finds a list of T entities by their name.
     * @param name the name of the T to search for.
     * @return the T with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if name is null.
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

}

