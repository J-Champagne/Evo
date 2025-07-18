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

import java.time.LocalDate;
import java.util.List;

/**
 * BCIActivity Instance Service.
 * @author Edilton Lima dos Santos.
 * @author Julien Champagne
 */
@Service
public class BCIActivityInstanceService extends AbstractEvoService<BCIActivityInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BCIActivityInstanceService.class);

    @Autowired
    private BCIActivityInstanceRepository bciActivityInstanceRepository;

    /**
     * Creates a BCIActivityInstance in the database.
     * @param bciActivityInstance BCIActivityInstance.
     * @return The created BCIActivityInstance.
     * @throws IllegalArgumentException if bciActivityInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    public BCIActivityInstance create (BCIActivityInstance bciActivityInstance) {
        BCIActivityInstance bciBCIActivityInstance = null;

        ObjectValidator.validateObject(bciActivityInstance);

        bciBCIActivityInstance = this.bciActivityInstanceRepository.save(bciActivityInstance);
        logger.info("BCIActivityInstance created: {}", bciBCIActivityInstance);

        return bciBCIActivityInstance;
    }

    /**
     * Updates a BCIActivityInstance in the database.
     * @param bciActivityInstance BCIActivityInstance.
     * @return The updated BCIActivityInstance.
     * @throws IllegalArgumentException if bciActivityInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    public BCIActivityInstance update (BCIActivityInstance bciActivityInstance) {
        BCIActivityInstance bciBCIActivityInstance = null;

        ObjectValidator.validateObject(bciActivityInstance);
        ObjectValidator.validateId(bciActivityInstance.getId());

        bciBCIActivityInstance = this.bciActivityInstanceRepository.save(bciActivityInstance);
        logger.info("BCIActivityInstance updated: {}", bciBCIActivityInstance);

        return bciBCIActivityInstance;
    }

    /**
     * Saves the given BCIActivityInstance in the database.
     * @param bciActivityInstance BCIActivityInstance.
     * @return The saved BCIActivityInstance.
     * @throws IllegalArgumentException if bciActivityInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    protected BCIActivityInstance save(BCIActivityInstance bciActivityInstance) {
        ObjectValidator.validateString(bciActivityInstance.getStatus());
        ObjectValidator.validateObject(bciActivityInstance.getEntryDate());
        ObjectValidator.validateObject(bciActivityInstance.getExitDate());
        ObjectValidator.validateObject(bciActivityInstance.getParticipants());
        return bciActivityInstanceRepository.save(bciActivityInstance);
    }

    /**
     * Deletes a BCIActivityInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        this.bciActivityInstanceRepository.deleteById(id);
        logger.info("BCIActivityInstance deleted: {}", id);
    }

    /**
     * Finds all BCIActivityInstance entities.
     * @return List<BCIActivityInstance>.
     */
    public List<BCIActivityInstance> findAll() {
        return this.bciActivityInstanceRepository.findAll();
    }

    /**
     * Finds a BCIActivityInstance by its id.
     * @param id Long.
     * @return BCIActivityInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    public BCIActivityInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciActivityInstanceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("BCIActivityInstance not found!"));
    }

    /**
     * Finds BCIActivityInstance entities by their status.
     * @param status String.
     * @return List<BCIActivityInstance> with the given status.
     * @throws IllegalArgumentException if status is null.
     */
    public List<BCIActivityInstance> findByStatus(String status) {
        ObjectValidator.validateString(status);
        return this.bciActivityInstanceRepository.findByStatus(status);
    }

    /**
     * Finds BCIActivityInstance entities by their entryDate.
     * @param entryDate LocalDate.
     * @return List<BCIActivityInstance> with the given entryDate.
     * @throws IllegalArgumentException if entryDate is null.
     */
    public List<BCIActivityInstance> findByEntryDate(LocalDate entryDate) {
        ObjectValidator.validateObject(entryDate);
        return this.bciActivityInstanceRepository.findByEntryDate(entryDate);
    }

    /**
     * Finds BCIActivityInstance entities by their exitDate.
     * @param exitDate LocalDate.
     * @return List<BCIActivityInstance> with the given exitDate.
     * @throws IllegalArgumentException if exitDate is null.
     */
    public List<BCIActivityInstance> findByExitDate(LocalDate exitDate) {
        ObjectValidator.validateObject(exitDate);
        return this.bciActivityInstanceRepository.findByEntryDate(exitDate);
    }

    /**
     * Finds BCIActivityInstance entities by the id of their Participant.
     * @param id Long.
     * @return List<BCIActivityInstance> with the given Participant id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BCIActivityInstance> findByParticipantsId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciActivityInstanceRepository.findByParticipantsId(id);
    }

    /**
     * Checks if an BCIActivityInstance exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciActivityInstanceRepository.existsById(id);
    }
}