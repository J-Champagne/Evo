package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BCIActivityInstanceRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BCIActivity Instance Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
public class BCIActivityInstanceService extends AbstractEvoService<BCIActivityInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BCIActivityInstanceService.class);

    @Autowired
    private BCIActivityInstanceRepository bciActivityInstanceRepository;

    /**
     * Inserts a BCIActivityInstance in the database.
     * @param evoModel The BCIActivityInstance entity.
     * @return The saved BCIActivityInstance.
     * @throws IllegalArgumentException in case the given BCIActivityInstance is null.
     * @throws OptimisticLockingFailureException when the BCIActivityInstance uses optimistic locking and has a version
     * attribute with a different value from that found in the persistence store. Also thrown if the entity is assumed
     * to be present but does not exist in the database.
     */
    public BCIActivityInstance create (BCIActivityInstance evoModel) {
        BCIActivityInstance bciActivityInstance = null;

        // Save the BCIActivityInstance.
        bciActivityInstance = this.bciActivityInstanceRepository.save(evoModel);
        logger.info("BCIActivityInstance created: {}", bciActivityInstance);

        return bciActivityInstance;
    }

    /**
     * Updates a BCIActivityInstance in the database.
     * @param evoModel The BCIActivityInstance entity.
     * @return The saved BCIActivityInstance.
     * @throws IllegalArgumentException in case the given BCIActivityInstance is null.
     * @throws OptimisticLockingFailureException when the BCIActivityInstance uses optimistic locking and has a version
     * attribute with a different value from that found in the persistence store. Also thrown if the entity is assumed
     * to be present but does not exist in the database.
     */
    public BCIActivityInstance update (BCIActivityInstance evoModel) {
        BCIActivityInstance bciActivityInstance = null;

        // Checks the mandatory properties.
        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateId(evoModel.getId());

        // Save the BCIActivityInstance.
        bciActivityInstance = this.bciActivityInstanceRepository.save(evoModel);
        logger.info("BCIActivityInstance updated: {}", bciActivityInstance);

        return bciActivityInstance;
    }

    /**
     * Method used to create or update an BCIActivityInstance.
     * @param evoModel he BCIActivityInstance entity.
     * @return The BCIActivityInstance.
     */
    @Transactional
    @Override
    protected BCIActivityInstance save(BCIActivityInstance evoModel) {
        // Checks the mandatory properties.
        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateString(evoModel.getStatus());
        ObjectValidator.validateObject(evoModel.getEntryDate());
        ObjectValidator.validateObject(evoModel.getExitDate());
        ObjectValidator.validateObject(evoModel.getBciActivity());
        return bciActivityInstanceRepository.save(evoModel);
    }

    /**
     * Checks if a BCIActivityInstance entity with the specified id exists in the repository.
     * @param id the id of the BCIActivityInstance to check for existence, must not be null.
     * @return true if a BCIActivityInstance with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    public boolean existsById(Long id) {
        return this.bciActivityInstanceRepository.existsById(id);
    }

    /**
     * Deletes the BCIActivityInstance with the given id.If the BCIActivityInstance is not found in the persistence
     * store it is silently ignored.
     * @param id the unique identifier of the BCIActivityInstance to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Transactional
    public void deleteById(Long id) {
        this.bciActivityInstanceRepository.deleteById(id);
        logger.info("BCIActivityInstance deleted: {}", id);
    }

    /**
     * Retrieves a BCIActivityInstance by its id.
     * @param id The BCIActivityInstance Id to filter BCIActivityInstance entities by, must not be null.
     * @return the BCIActivityInstance with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     * @throws RuntimeException if the BCIActivityInstance not found.
     */
    public BCIActivityInstance findById(Long id) {
        return this.bciActivityInstanceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("BCI Activity Instance not found!"));
    }

    /**
     * Checks if a BCIActivityInstance entity with the specified status exists in the repository.
     * @param status the status of the BCIActivityInstance to check for existence, must not be null.
     * @return A list of BCIActivityInstance with the specified status.
     * @throws IllegalArgumentException if the status is null.
     */
    public List<BCIActivityInstance> findByStatus(String status) {
        ObjectValidator.validateString(status);
        return this.bciActivityInstanceRepository.findByStatus(status);
    }

    /**
     * Gets all BCIActivityInstance.
     * @return all BCIActivityInstance.
     */
    public List<BCIActivityInstance> findAll() {
        return this.bciActivityInstanceRepository.findAll();
    }

    /**
     * Finds a BCIActivityInstance by its Participant id.
     * @param id Long.
     * @return BCIActivityInstance with the given Participant id.
     * @throws IllegalArgumentException if id is null.
     */
    public BCIActivityInstance findByParticipantsId(Long id) {
        return this.bciActivityInstanceRepository.findByParticipantsId(id);
    }
}
