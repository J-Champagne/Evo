package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * BehaviorChangeInterventionBlock repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface BehaviorChangeInterventionBlockRepository extends EvoRepository<BehaviorChangeInterventionBlock> {

    /**
     * Retrieves a BehaviorChangeInterventionBlock by entry conditions.
     * @param entryConditions the entry condition of the behavior change intervention block to be retrieved.
     * @return the behavior change intervention block corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided entryConditions is null or invalid.
     */
    List<BehaviorChangeInterventionBlock> findByEntryConditions(@NotNull String entryConditions);

    /**
     * Retrieves a BehaviorChangeInterventionBlock by entry conditions.
     * @param exitConditions the exit condition of the behavior change intervention block to be retrieved.
     * @return the behavior change intervention block corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided exitConditions is null or invalid.
     */
    List<BehaviorChangeInterventionBlock> findByExitConditions(@NotNull String exitConditions);

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionBlock with association with BehaviorChangeInterventionPhase.
     * @param id The Behavior Change Intervention Phase id.
     * @return the behavior change intervention phase corresponding with association with Behavior Change Intervention
     * Phase Id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @Query(value = "SELECT bcib.* FROM behavior_change_intervention_block AS bcib " +
            "JOIN compose_of_phase_block cpb on (bcib.behavior_change_intervention_block_id = cpb.compose_of_phase_block_bci_block_id) " +
            "JOIN behavior_change_intervention_phase bcip on (cpb.compose_of_phase_block_bci_phase_id = bcip.behavior_change_intervention_phase_bci_id) " +
            "WHERE cpb.compose_of_phase_block_bci_phase_id = :bci_phase_id",
            nativeQuery = true)
    List<BehaviorChangeInterventionBlock> findByBehaviorChangeInterventionPhaseId(@NotNull @Param("bci_phase_id") Long id);

}
