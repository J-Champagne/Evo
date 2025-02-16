package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.model.Develops;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Develops repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface DevelopsRepository extends EvoRepository<Develops> {
    /**
     * Retrieves a list of Requires entities that match the specified skill level.
     * @param skillLevel the SkillLevel to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified skill level, or an empty list if no matches are found.
     */
    List<Develops> findByLevel(SkillLevel skillLevel);

}
