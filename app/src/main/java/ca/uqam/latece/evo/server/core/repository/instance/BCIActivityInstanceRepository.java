package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * BCIActivityInstance repository creates CRUD implementation at runtime automatically.
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@Repository
public interface BCIActivityInstanceRepository extends EvoRepository<BCIActivityInstance> {
    /**
     * Finds BCIActivityInstance entities by their status.
     * @param status String.
     * @return List<BCIActivityInstance> with the given status.
     * @throws IllegalArgumentException if outcome is null.
     */
    List<BCIActivityInstance> findByStatus(String status);

    /**
     * Finds BCIActivityInstance entities by their entryDate.
     * @param entryDate LocalDate.
     * @return List<BCIActivityInstance> with the given entryDate.
     * @throws IllegalArgumentException if outcome is null.
     */
    List<BCIActivityInstance> findByEntryDate(LocalDate entryDate);

    /**
     * Finds BCIActivityInstance entities by their exitDate.
     * @param exitDate LocalDate.
     * @return List<BCIActivityInstance> with the given exitDate.
     * @throws IllegalArgumentException if outcome is null.
     */
    List<BCIActivityInstance> findByExitDate(LocalDate exitDate);

    /**
     * Finds a BCIActivityInstance by a Participant id.
     * @param id Long.
     * @return BCIActivityInstance with the given Participant id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BCIActivityInstance> findByParticipantsId(Long id);
}