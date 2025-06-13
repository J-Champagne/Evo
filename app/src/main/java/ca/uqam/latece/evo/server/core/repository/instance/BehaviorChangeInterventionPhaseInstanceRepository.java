package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BehaviorChangeInterventionPhaseInstance repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
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
}
