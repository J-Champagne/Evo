package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BehaviorChangeInterventionInstance repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
 */
@Repository
public interface BehaviorChangeInterventionInstanceRepository extends EvoRepository<BehaviorChangeInterventionInstance> {
    /**
     * Finds BehaviorChangeInterventionInstance entities by their patient id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> with the given patient id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BehaviorChangeInterventionInstance> findByPatientId(Long id);

    /**
     * Finds BehaviorChangeInterventionInstance entities by their currentPhase id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> with the given currentPhase id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BehaviorChangeInterventionInstance> findByCurrentPhaseId(Long id);

    /**
     * Finds BehaviorChangeInterventionInstance entities by their phases id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> with the given phases id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BehaviorChangeInterventionInstance> findByPhasesId(Long id);
}
