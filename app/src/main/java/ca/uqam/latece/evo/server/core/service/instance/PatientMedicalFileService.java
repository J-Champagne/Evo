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
import java.util.Date;
import java.util.List;

/**
 * PatientMedicalFile Service.
 * @version 1.0
 * @author Julien Champagne.
 */
@Service
@Transactional
public class PatientMedicalFileService extends AbstractEvoService<PatientMedicalFile> {
    private final Logger logger = LoggerFactory.getLogger(PatientMedicalFileService.class);

    @Autowired
    private PatientMedicalFileRepository patientMedicalFileRepository;

    /**
     * Inserts a PatientMedicalFile in the database.
     * @param medicalFile the PatientMedicalFile entity.
     * @return The saved PatientMedicalFile.
     * @throws IllegalArgumentException in case the given PatientMedicalFile is null.
     * @throws OptimisticLockingFailureException when the PatientMedicalFile uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public PatientMedicalFile create(PatientMedicalFile medicalFile) {
        PatientMedicalFile medicalFileCreated = null;
        ObjectValidator.validateObject(medicalFile.getDate());
        ObjectValidator.validateString(medicalFile.getMedicalHistory());

        medicalFileCreated = patientMedicalFileRepository.save(medicalFile);
        logger.info("PatientMedicalFile created: {}", medicalFileCreated);
        return medicalFileCreated;
    }

    /**
     * Updates a PatientMedicalFile in the database.
     * @param medicalFile the PatientMedicalFile entity.
     * @return The updated PatientMedicalFile or null is the PatientMedicalFile to be updated is not found within the database.
     * @throws IllegalArgumentException in case the given PatientMedicalFile is null.
     * @throws OptimisticLockingFailureException when the PatientMedicalFile uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public PatientMedicalFile update(PatientMedicalFile medicalFile) {
        PatientMedicalFile medicalFileUpdated = null;
        PatientMedicalFile PatientMedicalFileFound = findById(medicalFile.getId());

        ObjectValidator.validateObject(medicalFile.getDate());
        ObjectValidator.validateString(medicalFile.getMedicalHistory());
        if (PatientMedicalFileFound != null) {
            medicalFileUpdated = patientMedicalFileRepository.save(medicalFile);
        }

        return medicalFileUpdated;
    }

    /**
     * Method used to create or update a PatientMedicalFile.
     * @param medicalFile the PatientMedicalFile entity.
     * @return The inserted or updated PatientMedicalFile.
     * @throws IllegalArgumentException in case the given PatientMedicalFile is null.
     * @throws OptimisticLockingFailureException when the PatientMedicalFile uses optimistic locking and has a version attribute with
     *          a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *          present but does not exist in the database.
     */
    @Override
    @Transactional
    public PatientMedicalFile save(PatientMedicalFile medicalFile) {
        return this.patientMedicalFileRepository.save(medicalFile);
    }

    /**
     * Checks if a PatientMedicalFile entity with the specified id exists in the repository.
     * @param id the id of the PatientMedicalFile to check for existence, must not be null.
     * @return true if an PatientMedicalFile with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.patientMedicalFileRepository.existsById(id);
    }

    /**
     * Checks if a PatientMedicalFile entity with the specified date exists in the repository.
     * @param date the date of the PatientMedicalFile to check for existence, must not be null.
     * @return true if an PatientMedicalFile with the specified date exists, false otherwise.
     * @throws IllegalArgumentException if the date is null.
     */
    public boolean existsByDate(Date date) {
        ObjectValidator.validateObject(date);
        return this.patientMedicalFileRepository.existsByDate(date);
    }

    /**
     * Checks if a PatientMedicalFile entity with the specified medicalHistory exists in the repository.
     * @param medicalHistory the medicalHistory of the PatientMedicalFile to check for existence, must not be null or blank.
     * @return true if an PatientMedicalFile with the specified medicalHistory exists, false otherwise.
     * @throws IllegalArgumentException if the medicalHistory is null.
     */
    public boolean existsByMedicalHistory(String medicalHistory) {
        ObjectValidator.validateString(medicalHistory);
        return this.patientMedicalFileRepository.existsByMedicalHistory(medicalHistory);
    }

    /**
     * Finds a PatientMedicalFile by its id.
     * @param id the unique identifier of the PatientMedicalFile to be retrieved; must not be null or invalid.
     * @return the PatientMedicalFile with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public PatientMedicalFile findById(Long id) {
        ObjectValidator.validateId(id);
        return this.patientMedicalFileRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("PatientMedicalFile not found"));
    }

    /**
     * Finds a PatientMedicalFile by its date.
     * @param date must not be null.
     * @return the PatientMedicalFile with the given date or Optional#empty() if none found.
     * @throws IllegalArgumentException if the date is null.
     */
    public List<PatientMedicalFile> findByDate(LocalDate date) {
        ObjectValidator.validateObject(date);
        return this.patientMedicalFileRepository.findByDate(date);
    }

    /**
     * Finds a PatientMedicalFile by its medicalHistory.
     * @param medicalHistory must not be null.
     * @return the PatientMedicalFile with the given medicalHistory or Optional#empty() if none found.
     * @throws IllegalArgumentException if the medicalHistory is null.
     */
    public List<PatientMedicalFile> findByMedicalHistory(String medicalHistory) {
        ObjectValidator.validateString(medicalHistory);
        return this.patientMedicalFileRepository.findByMedicalHistory(medicalHistory);
    }

    /**
     * Deletes the PatientMedicalFile with the given id.
     * If the PatientMedicalFile is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the PatientMedicalFile to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        this.patientMedicalFileRepository.deleteById(id);
        logger.info("PatientMedicalFile deleted {}", id);
    }

    /**
     * Gets all PatientMedicalFile entities.
     * @return all PatientMedicalFile.
     */
    @Override
    public List<PatientMedicalFile> findAll() {
        return this.patientMedicalFileRepository.findAll().stream().toList();
    }
}
