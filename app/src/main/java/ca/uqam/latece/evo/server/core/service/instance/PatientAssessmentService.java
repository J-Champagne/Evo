package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientAssessment;
import ca.uqam.latece.evo.server.core.repository.instance.PatientAssessmentRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;

import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * PatientAssessment Service.
 * @author Julien Champagne.
 */
@Service
@Transactional
public class PatientAssessmentService extends AbstractEvoService<PatientAssessment> {
    private final Logger logger = LoggerFactory.getLogger(PatientAssessmentService.class);

    @Autowired
    private PatientAssessmentRepository patientAssessmentRepository;

    /**
     * Inserts a PatientAssessment in the database.
     * @param patientAssessment the PatientAssessment entity.
     * @return The saved PatientAssessment.
     * @throws IllegalArgumentException in case the given PatientAssessment is null.
     * @throws OptimisticLockingFailureException when the PatientAssessment uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public PatientAssessment create(PatientAssessment patientAssessment) {
        PatientAssessment pa = null;

        ObjectValidator.validateObject(patientAssessment);
        ObjectValidator.validateObject(patientAssessment.getDate());
        ObjectValidator.validateString(patientAssessment.getAssessment());
        ObjectValidator.validateObject(patientAssessment.getPatient());

        pa = patientAssessmentRepository.save(patientAssessment);
        logger.info("PatientAssessment created: {}", pa);
        return pa;
    }

    /**
     * Updates a PatientAssessment in the database.
     * @param patientAssessment the PatientAssessment entity.
     * @return The updated PatientAssessment or null is the PatientAssessment to be updated is not found within the database.
     * @throws IllegalArgumentException in case the given PatientAssessment is null.
     * @throws OptimisticLockingFailureException when the PatientAssessment uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public PatientAssessment update(PatientAssessment patientAssessment) {
        PatientAssessment paUpdated = null;
        PatientAssessment paFound = findById(patientAssessment.getId());

        ObjectValidator.validateObject(patientAssessment);
        ObjectValidator.validateObject(patientAssessment.getDate());
        ObjectValidator.validateString(patientAssessment.getAssessment());
        ObjectValidator.validateObject(patientAssessment.getPatient());

        if (paFound != null) {
            paUpdated = patientAssessmentRepository.save(paFound);
        }
        return paUpdated;
    }

    /**
     * Method used to create or update a PatientAssessment.
     * @param patientAssessment the PatientAssessment entity.
     * @return The inserted or updated PatientAssessment.
     * @throws IllegalArgumentException in case the given PatientAssessment is null.
     * @throws OptimisticLockingFailureException when the PatientAssessment uses optimistic locking and has a version attribute with
     *          a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *          present but does not exist in the database.
     */
    @Override
    public PatientAssessment save(PatientAssessment patientAssessment) {
        return this.patientAssessmentRepository.save(patientAssessment);
    }

    /**
     * Checks if a PatientAssessment entity with the specified id exists in the repository.
     * @param id the id of the PatientAssessment to check for existence, must not be null.
     * @return true if an PatientAssessment with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.patientAssessmentRepository.existsById(id);
    }

    /**
     * Checks if a PatientAssessment entity with the specified date exists in the repository.
     * @param date the date of the PatientAssessment to check for existence, must not be null.
     * @return true if an PatientAssessment with the specified date exists, false otherwise.
     * @throws IllegalArgumentException if the date is null.
     */
    public boolean existsByDate(LocalDate date) {
        ObjectValidator.validateObject(date);
        return this.patientAssessmentRepository.existsByDate(date);
    }

    /**
     * Checks if a PatientAssessment entity with the specified assessment exists in the repository.
     * @param assessment the assessment of the PatientAssessment to check for existence, must not be null or blank.
     * @return true if an PatientAssessment with the specified assessment exists, false otherwise.
     * @throws IllegalArgumentException if the assessment is null or blank.
     */
    public boolean existsByAssessment(String assessment) {
        ObjectValidator.validateString(assessment);
        return this.patientAssessmentRepository.existsByAssessment(assessment);
    }

    /**
     * Checks if a PatientAssessment entity with the specified patient exists in the repository.
     * @param patient the patient of the PatientAssessment to check for existence, must not be null.
     * @return true if an PatientAssessment with the specified patient exists, false otherwise.
     * @throws IllegalArgumentException if the patient is null.
     */
    public boolean existsByPatient(Patient patient) {
        ObjectValidator.validateObject(patient);
        return this.patientAssessmentRepository.existsByPatient(patient);
    }

    /**
     * Finds a PatientAssessment by its id.
     * @param id the unique identifier of the PatientAssessment to be retrieved; must not be null or invalid.
     * @return the PatientAssessment with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public PatientAssessment findById(Long id) {
        ObjectValidator.validateId(id);
        return this.patientAssessmentRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("PatientAssessment not found"));
    }

    /**
     * Finds an PatientAssessment by its date.
     * @param date must not be null.
     * @return the PatientAssessment with the given date or Optional#empty() if none found.
     * @throws IllegalArgumentException if the date is null.
     */
    public List<PatientAssessment> findByDate(LocalDate date) {
        ObjectValidator.validateObject(date);
        return this.patientAssessmentRepository.findByDate(date);
    }

    /**
     * Finds an PatientAssessment by its assessment.
     * @param assessment must not be null or blank.
     * @return the PatientAssessment with the given assessment or Optional#empty() if none found.
     * @throws IllegalArgumentException if the assessment is null or blank.
     */
    public List<PatientAssessment> findByAssessment(String assessment) {
        ObjectValidator.validateString(assessment);
        return this.patientAssessmentRepository.findByAssessment(assessment);
    }

    /**
     * Finds an PatientAssessment by its patient id.
     * @param id must not be null.
     * @return the PatientAssessment with the given patient id or Optional#empty() if none found.
     * @throws IllegalArgumentException if the id is null.
     */
    public List<PatientAssessment> findByPatient(Long id) {
        ObjectValidator.validateId(id);
        return this.patientAssessmentRepository.findByPatient(id);
    }

    /**
     * Deletes the PatientAssessment with the given id.
     * If the PatientAssessment is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the PatientAssessment to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        patientAssessmentRepository.deleteById(id);
        logger.info("PatientAssessment deleted {}", id);
    }

    /**
     * Gets all PatientAssessment entities.
     * @return all PatientAssessment.
     */
    public List<PatientAssessment> findAll() {
        return this.patientAssessmentRepository.findAll().stream().toList();
    }
}
