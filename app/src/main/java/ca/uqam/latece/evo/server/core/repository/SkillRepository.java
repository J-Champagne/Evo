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
     * Finds and retrieves a list of skills based on their subskills.
     * @param subSkillId the id of the subskills to search for.
     * @return a list of skills matching the given subskill id.
     */
    List<Skill> findBySubSkill_Id(Long subSkillId);

    /**
     * Finds and retrieves a list of skills based on their required skills.
     * @param requiredSkillId the required skill id to search for.
     * @return a list of skills matching the given id.
     */
    List<Skill> findByRequiredSkill_Id(Long requiredSkillId);

    /**
     * Finds and retrieves a list of skills based on their assessment.
     * @param assessmentId the id of the assessment to search for.
     * @return a list of skills matching the given assessment id.
     */
    List<Skill> findByAssessments_Id(Long assessmentId);

    /**
     * Finds and retrieves a list of skills based on composed of skill id.
     * @param id the composed o skill id to search for.
     * @return a list of skills matching the given composed skill id.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Query(value = "SELECT s.* FROM skill AS s " +
            "WHERE s.skill_composed_of_skill_id = :composed_skill_id",
            nativeQuery = true)
    List<Skill> findBySkillComposedOfSkillId(@Param("composed_skill_id") Long id);
}
