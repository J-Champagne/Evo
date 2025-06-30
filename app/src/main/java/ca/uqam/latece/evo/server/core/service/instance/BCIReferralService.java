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
    private static final Logger logger = LoggerFactory.getLogger(BCIReferralService.class);

    @Autowired
    private BCIReferralRepository bciReferralRepository;

    /**
     * Creates a BCIReferral in the database.
     * @param bcir BCIReferral.
     * @return The created BCIReferral.
     * @throws IllegalArgumentException if bcir is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
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
     * @param bcir BCIReferral.
     * @return The updated BCIReferral.
     * @throws IllegalArgumentException if bcir is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
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
     * Saves the given BCIReferral in the database.
     * @param bcir BCIReferral.
     * @return The saved BCIReferral.
     * @throws IllegalArgumentException if bcir is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public BCIReferral save(BCIReferral bcir) {
        return this.bciReferralRepository.save(bcir);
    }

    /**
     * Deletes a BCIReferral by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        bciReferralRepository.deleteById(id);
        logger.info("BCIReferral deleted {}", id);
    }

    /**
     * Finds all BCIReferral entities.
     * @return List<BCIReferral>.
     */
    public List<BCIReferral> findAll() {
        return this.bciReferralRepository.findAll();
    }

    /**
     * Finds a BCIReferral by its id.
     * @param id Long.
     * @return BCIReferral with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public BCIReferral findById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciReferralRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("BCIReferral not found"));
    }

    /**
     * Finds BCIReferral entities by their Patient.
     * @param date LocalDate.
     * @return List<BCIReferral> with the given date.
     * @throws IllegalArgumentException if date is null.
     */
    public List<BCIReferral> findByDate(LocalDate date) {
        ObjectValidator.validateObject(date);
        return this.bciReferralRepository.findByDate(date);
    }

    /**
     * Finds BCIReferral entities by their reason.
     * @param reason String.
     * @return List<BCIReferral> with the given reason.
     * @throws IllegalArgumentException if reason is blank or null.
     */
    public List<BCIReferral> findByReason(String reason) {
        ObjectValidator.validateString(reason);
        return this.bciReferralRepository.findByReason(reason);
    }

    /**
     * Finds BCIReferral entities by their Patient id.
     * @param id Long.
     * @return List<BCIReferral> with the given Patient id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BCIReferral> findByPatientId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciReferralRepository.findByPatientId(id);
    }

    /**
     * Finds BCIReferral entities by their PatientAssessment id.
     * @param id Long.
     * @return List<BCIReferral> with the given PatientAssessment id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BCIReferral> findByPatientAssessmentId(Long id) {
        ObjectValidator.validateObject(id);
        return this.bciReferralRepository.findByPatientAssessmentId(id);
    }

    /**
     * Finds BCIReferral entities by their ReferringProfessional id.
     * @param id Long.
     * @return List<BCIReferral> with the given ReferringProfessional id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BCIReferral> findByReferringProfessionalId(Long id) {
        ObjectValidator.validateObject(id);
        return this.bciReferralRepository.findByReferringProfessionalId(id);
    }

    /**
     * Finds BCIReferral entities by their BehaviorChangeInterventionist id.
     * @param id Long.
     * @return List<BCIReferral> with the given BehaviorChangeInterventionist id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BCIReferral> findByBehaviorChangeInterventionistId(Long id) {
        ObjectValidator.validateObject(id);
        return this.bciReferralRepository.findByBehaviorChangeInterventionistId(id);
    }

    /**
     * Finds BCIReferral entities by a BehaviorChangeInterventionInstance id.
     * @param id Long.
     * @return List<BCIReferral> with the given BehaviorChangeInterventionInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BCIReferral> findByInterventionsId(Long id) {
        ObjectValidator.validateObject(id);
        return this.bciReferralRepository.findByInterventionsId(id);
    }

    /**
     * Checks if a BCIReferral exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciReferralRepository.existsById(id);
    }
}
