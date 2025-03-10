package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Skill repository creates CRUD implementation at runtime automatically.
 * @since 22.01.2025
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface SkillRepository extends EvoRepository<Skill> {

    /**
     * Finds a list of Skill entities by their name.
     * @param name the name of the Skill to search for.
     * @return the Skill with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    List<Skill> findByName(String name);

    /**
     * Checks if a Skill entity with the specified name exists in the repository.
     * @param name the name of the Skill to check for existence, must not be null.
     * @return true if a Skill with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    boolean existsByName(String name);

    /**
     * Finds and retrieves a list of skills based on their type.
     * @param type the type of the skills to search for.
     * @return a list of skills matching the given type.
     */
    List<Skill> findByType(SkillType type);


    /**
     * Finds and retrieves a list of skills based on their required skills.
     * @param id the id of the skills to search for.
     * @return a list of skills matching the given id.
     */
    @Query(value = "SELECT s.* FROM skill AS s WHERE s.skill_skill_id = :skill_skill_id",
            nativeQuery = true)
    List<Skill> findByRequiredSkill(@Param("skill_skill_id") Long id);

}
