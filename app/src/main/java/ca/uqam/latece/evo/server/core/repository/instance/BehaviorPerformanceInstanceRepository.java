package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorPerformanceInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BehaviorPerformanceInstance repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface BehaviorPerformanceInstanceRepository extends EvoRepository<BehaviorPerformanceInstance> {
    /**
     * Checks if a BehaviorPerformanceInstance entity with the specified status exists in the repository.
     * @param status the status of the BehaviorPerformanceInstance to check for existence, must not be null.
     * @return A list of BehaviorPerformanceInstance with the specified status.
     * @throws IllegalArgumentException if the status is null.
     */
    List<BehaviorPerformanceInstance> findByStatus(ExecutionStatus status);

    /**
     * Finds a BehaviorPerformanceInstance by its Participant id.
     * @param id Long.
     * @return BehaviorPerformanceInstance with the given Participant id.
     * @throws IllegalArgumentException if id is null.
     */
    BehaviorPerformanceInstance findByParticipantsId(Long id);
}