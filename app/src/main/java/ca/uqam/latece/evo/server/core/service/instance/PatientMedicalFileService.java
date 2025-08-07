package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.repository.instance.PatientMedicalFileRepository;

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
 * PatientMedicalFile Service.
 * @author Julien Champagne.
 */
@Service
@Transactional
public class PatientMedicalFileService extends AbstractEvoService<PatientMedicalFile> {
    private static final Logger logger = LoggerFactory.getLogger(PatientMedicalFileService.class);

    @Autowired
    private PatientMedicalFileRepository patientMedicalFileRepository;

    /**
     * Creates a PatientMedicalFile in the database.
     * @param patientMedicalFile PatientMedicalFile.
     * @return The created PatientMedicalFile.
     * @throws IllegalArgumentException if patientMedicalFile is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public PatientMedicalFile create(PatientMedicalFile patientMedicalFile) {
        PatientMedicalFile patientMedicalFileCreated;

        ObjectValidator.validateObject(patientMedicalFile.getDate());
        ObjectValidator.validateString(patientMedicalFile.getMedicalHistory());
        patientMedicalFileCreated = patientMedicalFileRepository.save(patientMedicalFile);

        logger.info("PatientMedicalFile created: {}", patientMedicalFileCreated);
        return patientMedicalFileCreated;
    }

    /**
     * Updates a PatientMedicalFile in the database.
     * @param patientMedicalFile PatientMedicalFile.
     * @return The updated PatientMedicalFile.
     * @throws IllegalArgumentException if patientMedicalFile is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public PatientMedicalFile update(PatientMedicalFile patientMedicalFile) {
        PatientMedicalFile medicalFileUpdated = null;
        PatientMedicalFile PatientMedicalFileFound = findById(patientMedicalFile.getId());

        ObjectValidator.validateObject(patientMedicalFile.getDate());
        ObjectValidator.validateString(patientMedicalFile.getMedicalHistory());
        if (PatientMedicalFileFound != null) {
            medicalFileUpdated = patientMedicalFileRepository.save(patientMedicalFile);
        }

        return medicalFileUpdated;
    }

    /**
     * Saves the given PatientMedicalFile in the database.
     * @param patientMedicalFile PatientMedicalFile.
     * @return The saved PatientMedicalFile.
     * @throws IllegalArgumentException if patientMedicalFile is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    @Transactional
    public PatientMedicalFile save(PatientMedicalFile patientMedicalFile) {
        return this.patientMedicalFileRepository.save(patientMedicalFile);
    }

    /**
     * Deletes a PatientMedicalFile by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        this.patientMedicalFileRepository.deleteById(id);
        logger.info("PatientMedicalFile deleted {}", id);
    }

    /**
     * Finds all PatientMedicalFile entities.
     * @return List<PatientMedicalFile>.
     */
    @Override
    public List<PatientMedicalFile> findAll() {
        return this.patientMedicalFileRepository.findAll();
    }

    /**
     * Finds a PatientMedicalFile by its id.
     * @param id Long.
     * @return the PatientMedicalFile with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public PatientMedicalFile findById(Long id) {
        ObjectValidator.validateId(id);
        return this.patientMedicalFileRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("PatientMedicalFile not found"));
    }

    /**
     * Finds PatientMedicalFile entities by their date.
     * @param date LocalDate.
     * @return List<PatientMedicalFile> with the given date.
     * @throws IllegalArgumentException if date is null.
     */
    public List<PatientMedicalFile> findByDate(LocalDate date) {
        ObjectValidator.validateObject(date);
        return this.patientMedicalFileRepository.findByDate(date);
    }

    /**
     * Finds PatientMedicalFile entities by their medicalHistory.
     * @param medicalHistory String.
     * @return List<PatientMedicalFile> with the given medicalHistory.
     * @throws IllegalArgumentException if medicalHistory is null or blank.
     */
    public List<PatientMedicalFile> findByMedicalHistory(String medicalHistory) {
        ObjectValidator.validateString(medicalHistory);
        return this.patientMedicalFileRepository.findByMedicalHistory(medicalHistory);
    }

    /**
     * Checks if a PatientMedicalFile exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.patientMedicalFileRepository.existsById(id);
    }
}
