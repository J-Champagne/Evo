package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.model.instance.BCIModuleInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * BCIModuleInstance repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
 */
@Repository
public interface BCIModuleInstanceRepository extends EvoRepository<BCIModuleInstance> {
    /**
     * Finds BCIModuleInstance entities by their status.
     * @param status String.
     * @return List<BCIModuleInstance> with the given status.
     * @throws IllegalArgumentException if outcome is null.
     */
    List<BCIModuleInstance> findByStatus(String status);

    /**
     * Finds BCIModuleInstance entities by their entryDate.
     * @param entryDate LocalDate.
     * @return List<BCIModuleInstance> with the given entryDate.
     * @throws IllegalArgumentException if outcome is null.
     */
    List<BCIModuleInstance> findByEntryDate(LocalDate entryDate);

    /**
     * Finds BCIModuleInstance entities by their exitDate.
     * @param exitDate LocalDate.
     * @return List<BCIModuleInstance> with the given exitDate.
     * @throws IllegalArgumentException if outcome is null.
     */
    List<BCIModuleInstance> findByExitDate(LocalDate exitDate);

    /**
     * Finds BCIModuleInstance entities by their outcome.
     * @param outcome OutcomeType.
     * @return List<BCIModuleInstance> with the given outcome.
     * @throws IllegalArgumentException if outcome is null.
     */
    List<BCIModuleInstance> findByOutcome(OutcomeType outcome);

    /**
     * Finds BCIModuleInstance entities by their BCIActivityInstance id.
     * @param id Long.
     * @return List<BCIModuleInstance> with the given BCIActivityInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BCIModuleInstance> findByActivitiesId(Long id);
}
