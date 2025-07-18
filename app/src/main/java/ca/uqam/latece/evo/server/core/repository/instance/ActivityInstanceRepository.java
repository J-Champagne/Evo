package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.ActivityInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * ActivityInstance repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
 */
@Repository
public interface ActivityInstanceRepository extends EvoRepository<ActivityInstance> {
    /**
     * Finds ActivityInstance entities by their status.
     * @param status String.
     * @return List<ActivityInstance> with the given status.
     * @throws IllegalArgumentException if outcome is null.
     */
    List<ActivityInstance> findByStatus(String status);

    /**
     * Finds ActivityInstance entities by their entryDate.
     * @param entryDate LocalDate.
     * @return List<ActivityInstance> with the given entryDate.
     * @throws IllegalArgumentException if outcome is null.
     */
    List<ActivityInstance> findByEntryDate(LocalDate entryDate);

    /**
     * Finds ActivityInstance entities by their exitDate.
     * @param exitDate LocalDate.
     * @return List<ActivityInstance> with the given exitDate.
     * @throws IllegalArgumentException if outcome is null.
     */
    List<ActivityInstance> findByExitDate(LocalDate exitDate);
}
