package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BehaviorChangeInterventionPhaseInstance repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface BehaviorChangeInterventionPhaseInstanceRepository extends EvoRepository<BehaviorChangeInterventionPhaseInstance> {
    /**
     * Finds BehaviorChangeInterventionPhaseInstance entities by their currentBlock id.
     * @param id Long.
     * @return List<BCIModuleInstance> with the given currentBlock id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BehaviorChangeInterventionPhaseInstance> findByCurrentBlockId(Long id);

    /**
     * Finds BehaviorChangeInterventionPhaseInstance entities by a BehaviorChangeInterventionBlockInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionPhaseInstance> with the given BCIBlocksInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BehaviorChangeInterventionPhaseInstance> findByBlocksId(Long id);

    /**
     * Finds BehaviorChangeInterventionPhaseInstance entities by a BCIModuleInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionPhaseInstance> with the given BCIModuleInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BehaviorChangeInterventionPhaseInstance> findByModulesId(Long id);

    /**
     * Finds a BehaviorChangeInterventionPhaseInstance entity by its unique id and the id of its current block.
     * @param id the unique identifier of the BehaviorChangeInterventionPhaseInstance.
     * @param currentBlockId the unique identifier of the current block associated with the phase instance.
     * @return the BehaviorChangeInterventionPhaseInstance entity that matches both the id and currentBlockId, or null
     *         if no such entity exists.
     * @throws IllegalArgumentException if id or currentBlockId is null.
     */
    BehaviorChangeInterventionPhaseInstance findByIdAndCurrentBlockId(Long id, Long currentBlockId);
}
