package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BehaviorChangeInterventionPhase repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface BehaviorChangeInterventionPhaseRepository extends EvoRepository<BehaviorChangeInterventionPhase> {
    /**
     * Retrieves a BehaviorChangeInterventionPhase by entry conditions.
     * @param entryConditions the entry condition of the behavior change intervention phase to be retrieved.
     * @return the behavior change intervention phase corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided entryConditions is null or invalid.
     */
    List<BehaviorChangeInterventionPhase> findByEntryConditions(@NotNull String entryConditions);

    /**
     * Retrieves a BehaviorChangeInterventionPhase by entry conditions.
     * @param exitConditions the exit condition of the behavior change intervention phase to be retrieved.
     * @return the behavior change intervention phase corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided exitConditions is null or invalid.
     */
    List<BehaviorChangeInterventionPhase> findByExitConditions(@NotNull String exitConditions);

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionPhase with association with BehaviorChangeIntervention.
     * @param id The Behavior Change Intervention id.
     * @return the behavior change intervention phase corresponding with association with Behavior Change Intervention
     * Id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @Query(value = "SELECT bcip.* FROM behavior_change_intervention_phase AS bcip " +
            "JOIN behavior_change_intervention bci on (bcip.behavior_change_intervention_phase_bci_id = bci.behavior_change_intervention_id) " +
            "WHERE bci.behavior_change_intervention_id = :bci_id",
            nativeQuery = true)
    List<BehaviorChangeInterventionPhase> findByBehaviorChangeInterventionId(@NotNull @Param("bci_id") Long id);

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionPhase with association with BehaviorChangeInterventionBlock.
     * @param id The Behavior Change Intervention Block id.
     * @return the behavior change intervention phase corresponding with association with Behavior Change Intervention
     * Block Id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @Query(value = "SELECT bcip.* FROM behavior_change_intervention_phase AS bcip " +
            "JOIN compose_of_phase_block cpb on (bcip.behavior_change_intervention_phase_id = cpb.compose_of_phase_block_bci_phase_id) " +
            "JOIN behavior_change_intervention_block bcib on (cpb.compose_of_phase_block_bci_block_id = bcib.behavior_change_intervention_block_id) " +
            "WHERE bcib.behavior_change_intervention_block_id = :bci_block_id",
            nativeQuery = true)
    List<BehaviorChangeInterventionPhase> findByBehaviorChangeInterventionBlockId(@NotNull @Param("bci_block_id") Long id);
}
