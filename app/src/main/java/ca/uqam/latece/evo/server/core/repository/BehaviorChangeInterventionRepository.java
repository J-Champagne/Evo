package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BehaviorChangeIntervention repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface BehaviorChangeInterventionRepository extends EvoRepository<BehaviorChangeIntervention> {

    /**
     * Finds a list of BehaviorChangeIntervention entities by their name.
     * @param name the name of the BehaviorChangeIntervention to search for.
     * @return the BehaviorChangeIntervention with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    List<BehaviorChangeIntervention> findByName(@NotNull String name);

    /**
     * Checks if a BehaviorChangeIntervention entity with the specified name exists in the repository.
     * @param name the name of the BehaviorChangeIntervention to check for existence, must not be null.
     * @return true if a BehaviorChangeIntervention with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    boolean existsByName(@NotNull String name);

    /**
     * Finds and retrieves a list of BehaviorChangeIntervention with association with BehaviorChangeInterventionPhase.
     * @param id The Behavior Change Intervention Phase id.
     * @return the behavior change intervention corresponding with association with Behavior Change Intervention
     * Phase Id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @Query(value = "SELECT bci.* FROM behavior_change_intervention AS bci " +
            "JOIN behavior_change_intervention_phase bcip on " +
            "(bci.behavior_change_intervention_id = bcip.behavior_change_intervention_phase_bci_id) " +
            "WHERE bcip.behavior_change_intervention_phase_id = :bcip_id",
            nativeQuery = true)
    List<BehaviorChangeIntervention> findByBehaviorChangeInterventionPhase(@NotNull @Param("bcip_id") Long id);
}