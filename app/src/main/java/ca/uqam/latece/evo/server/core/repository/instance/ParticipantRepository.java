package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Participant repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
 */
@Repository
public interface ParticipantRepository extends EvoRepository<Participant> {
    /**
     * Finds Participant entities by their Role id.
     * @param id Long.
     * @return List<Participant> with the given Role id.
     * @throws IllegalArgumentException if id is null.
     */
    List<Participant> findByRoleId(Long id);

    /**
     * Finds Participant entities by their Actor id.
     * @param id Long.
     * @return List<Participant> with the given Actor id.
     * @throws IllegalArgumentException if id is null.
     */
    List<Participant> findByActorId(Long id);
}
