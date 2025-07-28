package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.ActivityInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * ActivityInstance repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface ActivityInstanceRepository extends EvoRepository<ActivityInstance> {

    /**
     * Finds ActivityInstance entities by their status.
     * @param status the execution status used as a filter criterion.
     * @return a list of ActivityInstance objects matching the provided status.
     * @throws IllegalArgumentException if the provided status is null.
     */
    List<ActivityInstance> findByStatus(ExecutionStatus status);

    /**
     * Finds ActivityInstance entities by their entryDate.
     * @param entryDate the entry date to filter ActivityInstance entities.
     * @return a list of ActivityInstance objects that match the provided entry date.
     * @throws IllegalArgumentException if the provided entryDate is null.
     */
    List<ActivityInstance> findByEntryDate(LocalDate entryDate);

    /**
     * Finds ActivityInstance entities by their exitDate.
     * @param exitDate the exit date used as a filter criterion.
     * @return a list of ActivityInstance objects that match the given exit date.
     * @throws IllegalArgumentException if the provided exitDate is null.
     */
    List<ActivityInstance> findByExitDate(LocalDate exitDate);
}
