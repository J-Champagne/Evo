package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.repository.instance.ParticipantRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Participant Service.
 * @author Julien Champagne.
 */
@Service
@Transactional
public class ParticipantService extends AbstractEvoService<Participant> {
    private static final Logger logger = LoggerFactory.getLogger(ParticipantService.class);

    @Autowired
    private ParticipantRepository participantRepository;

    /**
     * Creates a Participant in the database.
     * @param pt Participant.
     * @return The created Participant.
     * @throws IllegalArgumentException if pt is null or if another Participant was saved with the same email.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public Participant create(Participant pt) {
        Participant created;

        ObjectValidator.validateObject(pt);
        ObjectValidator.validateObject(pt.getActor());

        created = participantRepository.save(pt);
        logger.info("Participant created: " + created);
        return created;
    }

    /**
     * Updates a Participant in the database.
     * @param pt Participant.
     * @return The updated Participant.
     * @throws IllegalArgumentException if pt is null or if another Participant was saved with the same email.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public Participant update(Participant pt) {
        Participant updated = null;
        Participant found = findById(pt.getId());

        if (found != null) {
            ObjectValidator.validateObject(pt);
            ObjectValidator.validateObject(pt.getActor());

            updated = participantRepository.save(pt);
            logger.info("Participant updated: " + updated);
        }
        return updated;
    }

    /**
     * Saves the given Participant in the database.
     * @param pt Participant.
     * @return The saved Participant.
     * @throws IllegalArgumentException if pt is null or if another Participant was saved with the same email.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    @Transactional
    public Participant save(Participant pt) {
        return participantRepository.save(pt);
    }

    /**
     * Deletes a Participant by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        participantRepository.deleteById(id);
        logger.info("Participant deleted {}", id);
    }

    /**
     * Finds all Participant entities.
     * @return List<Participant>.
     */
    @Override
    public List<Participant> findAll() {
        return participantRepository.findAll();
    }

    /**
     * Finds a Participant by its id.
     * @param id Long.
     * @return Participant with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public Participant findById(Long id) {
        ObjectValidator.validateId(id);
        return participantRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Participant not found"));
    }

    /**
     * Finds a Participant by its Role id.
     * @param id Long.
     * @return Participant with the given Role id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<Participant> findByRoleId(Long id) {
        ObjectValidator.validateId(id);
        return participantRepository.findByRoleId(id);
    }

    /**
     * Finds a Participant by its Actor id.
     * @param id Long.
     * @return Participant with the given Actor id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<Participant> findByActorId(Long id) {
        ObjectValidator.validateId(id);
        return participantRepository.findByActorId(id);
    }

    /**
     * Checks if a Participant exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return participantRepository.existsById(id);
    }
}
