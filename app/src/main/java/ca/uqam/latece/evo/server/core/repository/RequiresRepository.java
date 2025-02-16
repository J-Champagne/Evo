package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.model.Requires;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Requires repository creates CRUD implementation at runtime automatically.
 * @since 22.01.2025
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface RequiresRepository extends EvoRepository<Requires> {

    /**
     * Retrieves a list of Requires entities that match the specified skill level.
     * @param skillLevel the SkillLevel to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified skill level, or an empty list if no matches are found.
     */
    List<Requires> findByLevel(SkillLevel skillLevel);
}
