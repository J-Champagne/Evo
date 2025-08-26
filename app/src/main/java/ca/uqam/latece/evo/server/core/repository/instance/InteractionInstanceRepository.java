package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.InteractionInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * InteractionInstance repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
 */
@Repository
public interface InteractionInstanceRepository extends EvoRepository<InteractionInstance> {
    /**
     * Finds ActivityInstance entities by their status.
     * @param status the execution status used as a filter criterion.
     * @return a list of ActivityInstance objects matching the provided status.
     * @throws IllegalArgumentException if the provided status is null.
     */
    List<InteractionInstance> findByStatus(ExecutionStatus status);

    /**
     * Finds InteractionInstance entities by their entryDate.
     * @param entryDate the exit date used as a filter criterion.
     * @return a list of InteractionInstance objects that match the provided entry date.
     * @throws IllegalArgumentException if the provided entryDate is null.
     */
    List<InteractionInstance> findByEntryDate(LocalDate entryDate);

    /**
     * Finds InteractionInstance entities by their exitDate.
     * @param exitDate the exit date used as a filter criterion.
     * @return a list of InteractionInstance objects that match the given exit date.
     * @throws IllegalArgumentException if the provided exitDate is null.
     */
    List<InteractionInstance> findByExitDate(LocalDate exitDate);

    /**
     * Finds a list of InteractionInstance entities that are associated with a specific participant ID.
     * @param id the ID of the participant to filter InteractionInstance entities.
     * @return a list of InteractionInstance entities associated with the specified participant ID.
     */
    List<InteractionInstance> findByParticipantsId(Long id);
}
