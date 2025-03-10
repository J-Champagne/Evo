package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.model.Requires;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    /**
     * Retrieves a list of Requires entities that match the specified Role Id.
     * @param roleId The Role Id to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified Role id, or an empty list if no matches are found.
     */
    @Query(value = "SELECT re.* FROM requires AS re WHERE re.requires_role_id = :role_id",
            nativeQuery = true)
    List<Requires> findByRoleId(@Param("role_id") Long roleId);

    /**
     * Retrieves a list of Requires entities that match the specified Skill Id.
     * @param skillId The Skill Id to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified Skill id, or an empty list if no matches are found.
     */
    @Query(value = "SELECT re.* FROM requires AS re WHERE re.requires_skill_id = :skill_id",
            nativeQuery = true)
    List<Requires> findBySkillId(@Param("skill_id") Long skillId);

    /**
     * Retrieves a list of Requires entities that match the specified BCI Activity Id.
     * @param bciActivityId The BCI Activity Id to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    @Query(value = "SELECT re.* FROM requires AS re WHERE re.requires_bci_activity_id = :bci_activity_id",
            nativeQuery = true)
    List<Requires> findByBCIActivityId(@Param("bci_activity_id") Long bciActivityId);

}
