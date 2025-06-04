package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.model.BCIModule;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.model.Skill;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BCIModule repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface BCIModuleRepository extends EvoRepository<BCIModule> {

    /**
     * Finds a list of BCIModule entities by their name.
     * @param name the name of the BCIModule to search for.
     * @return the BCIModule with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if name is null.
     */
    List<BCIModule> findByName(@NotNull String name);

    /**
     * Checks if a BCIModule entity with the specified name exists in the repository.
     * @param name the name of the BCIModule to check for existence, must not be null.
     * @return true if a BCIModule with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    boolean existsByName(@NotNull String name);

    /**
     * Finds a BCIModule list by Skill Id.
     * @param skillsId the skill id used to search for BCIModule.
     * @return the BCIModule with the given name or null if none found.
     */
    List<BCIModule> findBySkillsId(@NotNull Long skillsId);

    /**
     * Finds a list of BCIModule entities by their Skill.
     * @param skill the skill of the BCIModule to search for.
     * @return the BCIModule with the given name or null if none found.
     * @throws IllegalArgumentException if skill is null.
     */
    List<BCIModule> findBySkills(@NotNull Skill skill);

    /**
     * Finds a list of BCIModule entities by their BehaviorChangeInterventionPhase Id.
     * @param id the BehaviorChangeInterventionPhase Id of the BCIModule to search for.
     * @return the BCIModule with the given name or null if none found.
     * @throws IllegalArgumentException if id is null.
     */
    List<BCIModule> findByBehaviorChangeInterventionPhasesId(@NotNull Long id);

    /**
     * Finds a list of BCIModule entities by their BehaviorChangeInterventionPhase.
     * @param behaviorChangeInterventionPhases the BehaviorChangeInterventionPhase of the BCIModule to search for.
     * @return the BCIModule with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if BehaviorChangeInterventionPhase is null.
     */
    List<BCIModule> findByBehaviorChangeInterventionPhases(@NotNull BehaviorChangeInterventionPhase behaviorChangeInterventionPhases);
}