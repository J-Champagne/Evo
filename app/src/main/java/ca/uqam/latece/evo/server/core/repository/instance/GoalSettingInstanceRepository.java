package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.GoalSettingInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * GoalSettingInstance repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@Repository
public interface GoalSettingInstanceRepository extends EvoRepository<GoalSettingInstance> {
    /**
     * Checks if a GoalSettingInstance entity with the specified status exists in the repository.
     * @param status the status of the GoalSettingInstance to check for existence, must not be null.
     * @return A list of GoalSettingInstance with the specified status.
     * @throws IllegalArgumentException if the status is null.
     */
    List<GoalSettingInstance> findByStatus(ExecutionStatus status);


    /**
     * Retrieves a GoalSettingInstance associated with the specified participant's ID.
     * @param id the ID of the participant whose GoalSettingInstance is to be retrieved, must not be null.
     * @return the GoalSettingInstance associated with the specified participant's ID, or null if not found.
     * @throws IllegalArgumentException if the provided ID is null.
     */
    GoalSettingInstance findByParticipantsId(Long id);
}
