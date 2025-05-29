package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientAssessment;
import ca.uqam.latece.evo.server.core.repository.instance.PatientAssessmentRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

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
    private static final Logger logger = LoggerFactory.getLogger(PatientAssessmentService.class);

    @Autowired
    private PatientAssessmentRepository patientAssessmentRepository;

    /**
     * Creates a PatientAssessment in the database.
     * @param patientAssessment PatientAssessment.
     * @return The created PatientAssessment.
     * @throws IllegalArgumentException if patientAssessment is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public PatientAssessment create(PatientAssessment patientAssessment) {
        PatientAssessment pa = null;

        ObjectValidator.validateObject(patientAssessment);
        ObjectValidator.validateObject(patientAssessment.getDate());
        ObjectValidator.validateString(patientAssessment.getAssessment());
        ObjectValidator.validateObject(patientAssessment.getPatient());

        pa = this.patientAssessmentRepository.save(patientAssessment);
        logger.info("PatientAssessment created: {}", pa);
        return pa;
    }

    /**
     * Updates a PatientAssessment in the database.
     * @param patientAssessment PatientAssessment.
     * @return The updated PatientAssessment.
     * @throws IllegalArgumentException if patientAssessment is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
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
            paUpdated = this.patientAssessmentRepository.save(paFound);
        }
        return paUpdated;
    }

    /**
     * Saves the given PatientAssessment in the database.
     * @param patientAssessment PatientAssessment.
     * @return The saved PatientAssessment.
     * @throws IllegalArgumentException if patientAssessment is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public PatientAssessment save(PatientAssessment patientAssessment) {
        return this.patientAssessmentRepository.save(patientAssessment);
    }

    /**
     * Deletes a PatientAssessment by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        patientAssessmentRepository.deleteById(id);
        logger.info("PatientAssessment deleted {}", id);
    }

    /**
     * Finds all PatientAssessment entities.
     * @return List<PatientAssessment>.
     */
    public List<PatientAssessment> findAll() {
        return this.patientAssessmentRepository.findAll();
    }

    /**
     * Finds a PatientAssessment by its id.
     * @param id Long.
     * @return PatientAssessment with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public PatientAssessment findById(Long id) {
        ObjectValidator.validateId(id);
        return this.patientAssessmentRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("PatientAssessment not found"));
    }

    /**
     * Finds PatientAssessment entities by their date.
     * @param date LocalDate.
     * @return List<PatientAssessment> with the given date.
     * @throws IllegalArgumentException if date is null.
     */
    public List<PatientAssessment> findByDate(LocalDate date) {
        ObjectValidator.validateObject(date);
        return this.patientAssessmentRepository.findByDate(date);
    }

    /**
     * Finds PatientAssessment entities by their assessment.
     * @param assessment String.
     * @return List<PatientAssessment> with the given assessment.
     * @throws IllegalArgumentException if assessment is blank or null.
     */
    public List<PatientAssessment> findByAssessment(String assessment) {
        ObjectValidator.validateString(assessment);
        return this.patientAssessmentRepository.findByAssessment(assessment);
    }

    /**
     * Finds PatientAssessment entities by their Patient.
     * @param patient Patient.
     * @return List<PatientAssessment> with the given Patient.
     * @throws IllegalArgumentException if patient is null.
     */
    public List<PatientAssessment> findByPatient(Patient patient) {
        ObjectValidator.validateObject(patient);
        return this.patientAssessmentRepository.findByPatient(patient);
    }

    /**
     * Finds PatientAssessment entities by their Patient id.
     * @param id Long.
     * @return List<PatientAssessment> with the given Patient id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<PatientAssessment> findByPatientId(Long id) {
        ObjectValidator.validateId(id);
        return this.patientAssessmentRepository.findByPatientId(id);
    }

    /**
     * Checks if a PatientAssessment exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.patientAssessmentRepository.existsById(id);
    }
}
