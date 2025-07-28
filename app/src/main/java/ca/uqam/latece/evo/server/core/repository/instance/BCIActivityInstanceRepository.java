package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * BCIActivityInstance repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@Repository
public interface BCIActivityInstanceRepository extends EvoRepository<BCIActivityInstance> {

    /**
     * Finds BCIActivityInstance entities by their status.
     * @param status the execution status to filter BCIActivityInstance entities.
     * @return a list of BCIActivityInstance entities with the specified execution status.
     * @throws IllegalArgumentException if the provided status is null.
     */
    List<BCIActivityInstance> findByStatus(ExecutionStatus status);

    /**
     * Finds a list of BCIActivityInstance entities based on their entry date.
     *
     * @param entryDate the LocalDate to filter BCIActivityInstance entities.
     * @return a list of BCIActivityInstance entities with the specified entry date.
     */
    List<BCIActivityInstance> findByEntryDate(LocalDate entryDate);

    /**
     * Finds a list of BCIActivityInstance entities based on their exit date.
     * @param exitDate the LocalDate to filter BCIActivityInstance entities.
     * @return a list of BCIActivityInstance entities with the specified exit date.
     */
    List<BCIActivityInstance> findByExitDate(LocalDate exitDate);

    /**
     * Finds a list of BCIActivityInstance entities that are associated with a specific participant ID.
     * @param id the ID of the participant to filter BCIActivityInstance entities.
     * @return a list of BCIActivityInstance entities associated with the specified participant ID.
     */
    List<BCIActivityInstance> findByParticipantsId(Long id);
}