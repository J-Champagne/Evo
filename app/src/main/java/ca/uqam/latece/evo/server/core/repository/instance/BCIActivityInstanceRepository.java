package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BCIActivityInstance repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@Repository
public interface BCIActivityInstanceRepository extends EvoRepository<BCIActivityInstance> {
    /**
     * Checks if a BCIActivityInstance entity with the specified status exists in the repository.
     * @param status the status of the BCIActivityInstance to check for existence, must not be null.
     * @return A list of BCIActivityInstance with the specified status.
     * @throws IllegalArgumentException if the status is null.
     */
    List<BCIActivityInstance> findByStatus(String status);

    /**
     * Finds a BCIActivityInstance by its Participant id.
     * @param id Long.
     * @return BCIActivityInstance with the given Participant id.
     * @throws IllegalArgumentException if id is null.
     */
    BCIActivityInstance findByParticipantsId(Long id);
}
