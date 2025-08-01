package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BehaviorChangeInterventionInstance repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
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
    List<BehaviorChangeInterventionInstance> findByActivitiesId(Long id);

    /**
     * Retrieves a BehaviorChangeInterventionInstance based on its id and the id of its current phase.
     * @param id the id of the BehaviorChangeInterventionInstance to retrieve.
     * @param currentPhaseId the id of the current phase associated with the intervention instance.
     * @return the BehaviorChangeInterventionInstance matching the specified id and currentPhaseId, or null if no such
     * instance exists
     * @throws IllegalArgumentException if id or currentPhaseId is null.
     */
    BehaviorChangeInterventionInstance findByIdAndCurrentPhaseId(Long id, Long currentPhaseId);
}
