package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.BCIReferral;
import ca.uqam.latece.evo.server.core.repository.instance.BCIReferralRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;

import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * BCIReferral Service.
 * @author Julien Champagne.
 */
@Service
@Transactional
public class BCIReferralService extends AbstractEvoService<BCIReferral> {
    private final Logger logger = LoggerFactory.getLogger(BCIReferralService.class);

    @Autowired
    private BCIReferralRepository bciReferralRepository;

    /**
     * Inserts a BCIReferral in the database.
     * @param bcir the BCIReferral entity.
     * @return The saved BCIReferral.
     * @throws IllegalArgumentException in case the given BCIReferral is null.
     * @throws OptimisticLockingFailureException when the BCIReferral uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public BCIReferral create(BCIReferral bcir) {
        BCIReferral saved = null;

        ObjectValidator.validateObject(bcir);
        ObjectValidator.validateObject(bcir.getDate());
        ObjectValidator.validateString(bcir.getReason());
        ObjectValidator.validateObject(bcir.getPatientAssessment());
        ObjectValidator.validateObject(bcir.getReferringProfessional());

        saved = this.bciReferralRepository.save(bcir);
        logger.info("BCIReferral created: {}", saved);
        return saved;
    }

    /**
     * Updates a BCIReferral in the database.
     * @param bcir the BCIReferral entity.
     * @return The updated BCIReferral or null if not found within the database.
     * @throws IllegalArgumentException in case the given BCIReferral is null.
     * @throws OptimisticLockingFailureException when the BCIReferral uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public BCIReferral update(BCIReferral bcir) {
        BCIReferral updated = null;
        BCIReferral found = findById(bcir.getId());

        ObjectValidator.validateObject(bcir);
        ObjectValidator.validateObject(bcir.getDate());
        ObjectValidator.validateString(bcir.getReason());
        ObjectValidator.validateObject(bcir.getPatientAssessment());
        ObjectValidator.validateObject(bcir.getReferringProfessional());

        if (found != null) {
            updated = this.bciReferralRepository.save(bcir);
        }
        return updated;
    }

    /**
     * Method used to create or update a BCIReferral.
     * @param bcir the BCIReferral entity.
     * @return The inserted or updated BCIReferral.
     * @throws IllegalArgumentException in case the given BCIReferral is null.
     * @throws OptimisticLockingFailureException when the BCIReferral uses optimistic locking and has a version attribute with
     *          a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *          present but does not exist in the database.
     */
    @Override
    public BCIReferral save(BCIReferral bcir) {
        return this.bciReferralRepository.save(bcir);
    }

    /**
     * Checks if a BCIReferral entity with the specified id exists in the repository.
     * @param id the id of the BCIReferral to check for existence, must not be null.
     * @return true if an BCIReferral with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciReferralRepository.existsById(id);
    }

    /**
     * Checks if a BCIReferral entity with the specified date exists in the repository.
     * @param date the date of the BCIReferral to check for existence, must not be null.
     * @return true if an BCIReferral with the specified date exists, false otherwise.
     * @throws IllegalArgumentException if the date is null.
     */
    public boolean existsByDate(LocalDate date) {
        ObjectValidator.validateObject(date);
        return this.bciReferralRepository.existsByDate(date);
    }

    /**
     * Finds a BCIReferral by its id.
     * @param id the unique identifier of the BCIReferral to be retrieved; must not be null or invalid.
     * @return the BCIReferral with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public BCIReferral findById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciReferralRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("BCIReferral not found"));
    }

    /**
     * Finds a BCIReferral by its date.
     * @param date must not be null or invalid.
     * @return List<BCIReferral> with the given date or Optional#empty() if none found.
     * @throws IllegalArgumentException if date is null.
     */
    public List<BCIReferral> findByDate(LocalDate date) {
        ObjectValidator.validateObject(date);
        return this.bciReferralRepository.findByDate(date);
    }

    /**
     * Finds a BCIReferral by its id.
     * @param reason must not be null or invalid.
     * @return the BCIReferral with the given reason or Optional#empty() if none found.
     * @throws IllegalArgumentException if reason is null or blank.
     */
    public List<BCIReferral> findByReason(String reason) {
        ObjectValidator.validateString(reason);
        return this.bciReferralRepository.findByReason(reason);
    }

    /**
     * Finds all BCIReferral entities by the id of their patient.
     * @param id must not be null or invalid.
     * @return List<BCIReferral> with the given patient or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null or blank.
     */
    public List<BCIReferral> findByPatient(Long id) {
        ObjectValidator.validateId(id);
        return this.bciReferralRepository.findByPatient(id);
    }

    /**
     * Finds a BCIReferral by the id of its patient assessment.
     * @param id must not be null or invalid.
     * @return List<BCIReferral> with the given patient assessment or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null or invalid.
     */
    public BCIReferral findByPatientAssessment(Long id) {
        ObjectValidator.validateObject(id);
        return this.bciReferralRepository.findByPatientAssessment(id);
    }

    /**
     * Finds a BCIReferral by the id of its referring health care professional.
     * @param id must not be null or invalid.
     * @return List<BCIReferral> with the given referring health care professional or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null or invalid.
     */
    public BCIReferral findByReferringProfessional(Long id) {
        ObjectValidator.validateObject(id);
        return this.bciReferralRepository.findByReferringProfessional(id);
    }

    /**
     * Finds a BCIReferral by the id of its behavior interventionist health care professional.
     * @param id must not be null or invalid.
     * @return List<BCIReferral> with the given behavior interventionist or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null or invalid.
     */
    public BCIReferral findByBehaviorChangeInterventionist(Long id) {
        ObjectValidator.validateObject(id);
        return this.bciReferralRepository.findByBehaviorChangeInterventionist(id);
    }

    /**
     * Deletes the BCIReferral with the given id.
     * If the BCIReferral is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the BCIReferral to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        bciReferralRepository.deleteById(id);
        logger.info("BCIReferral deleted {}", id);
    }

    /**
     * Gets all BCIReferral entities.
     * @return all BCIReferral.
     */
    public List<BCIReferral> findAll() {
        return this.bciReferralRepository.findAll().stream().toList();
    }
}
