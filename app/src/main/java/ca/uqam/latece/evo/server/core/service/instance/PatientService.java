package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.repository.instance.PatientRepository;
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
 * Patient Service.
 * @author Julien Champagne.
 */
@Service
@Transactional
public class PatientService extends AbstractEvoService<Patient> {
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    private static final String ERROR_EMAIL_ALREADY_REGISTERED = "Patient already registered with the same email!";
    
    @Autowired
    private PatientRepository patientRepository;

    /**
     * Creates a Patient in the database.
     * @param patient Patient.
     * @return The created Patient.
     * @throws IllegalArgumentException if patient is null or if another Patient was saved with the same email.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public Patient create(Patient patient) {
        Patient patientSaved;

        ObjectValidator.validateObject(patient);
        ObjectValidator.validateEmail(patient.getEmail());
        ObjectValidator.validateString(patient.getName());
        ObjectValidator.validateString(patient.getContactInformation());

        // The email should be unique.
        if (this.existsByEmail(patient.getEmail())) {
            throw this.createDuplicatePatientException(patient);
        } else {
            patientSaved = this.save(patient);
            logger.info("Patient created: {}", patientSaved);
        }

        return patientSaved;
    }

    /**
     * Updates a Patient in the database.
     * @param patient Patient.
     * @return The updated Patient.
     * @throws IllegalArgumentException if patient is null or if another Patient was saved with the same email.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    public Patient update(Patient patient) {
        Patient patientUpdated;
        Patient patientFound = this.findById(patient.getId());

        ObjectValidator.validateObject(patient);
        ObjectValidator.validateEmail(patient.getEmail());
        ObjectValidator.validateString(patient.getName());
        ObjectValidator.validateString(patient.getContactInformation());

        // The email should be the same or unique
        if (patientFound.getEmail().equalsIgnoreCase(patient.getEmail())) {
            patientUpdated = this.save(patient);
        } else {
            if (existsByEmail(patient.getEmail())) {
                throw this.createDuplicatePatientException(patient);
            } else {
                patientUpdated = this.save(patient);
            }
        }

        logger.info("Patient updated: {}", patientUpdated);
        return patientUpdated;
    }

    /**
     * Saves the given Patient in the database.
     * @param patient Patient.
     * @return The saved Patient.
     * @throws IllegalArgumentException if patient is null or if another Patient was saved with the same email.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    @Transactional
    public Patient save(Patient patient) {
        return this.patientRepository.save(patient);
    }

    /**
     * Deletes a Patient by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        patientRepository.deleteById(id);
        logger.info("Patient deleted {}", id);
    }

    /**
     * Finds all Patient entities.
     * @return List<Patient>.
     */
    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    /**
     * Finds a Patient by its id.
     * @param id Long.
     * @return Patient with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public Patient findById(Long id) {
        ObjectValidator.validateId(id);
        return patientRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    }

    /**
     * Finds Patient entities by their name.
     * @param name String.
     * @return Patient with the given name.
     * @throws IllegalArgumentException if name is null or blank.
     */
    public List<Patient> findByName(String name) {
        ObjectValidator.validateString(name);
        return patientRepository.findByName(name);
    }

    /**
     * Finds a Patient by its email.
     * @param email String.
     * @return Patient with the given email.
     * @throws IllegalArgumentException if email is null or blank.
     */
    public Patient findByEmail(String email) {
        ObjectValidator.validateString(email);
        return patientRepository.findByEmail(email);
    }

    /**
     * Finds Patient entities by their contactInformation.
     * @param contactInformation String.
     * @return List<Patient> with the given contactInformation.
     * @throws IllegalArgumentException if contactInformation is null or blank.
     */
    public List<Patient> findByContactInformation(String contactInformation) {
        ObjectValidator.validateString(contactInformation);
        return patientRepository.findByContactInformation(contactInformation);
    }

    /**
     * Finds Patient entities by their Role.
     * @param role Role.
     * @return List<Patient> with the specified Role.
     * @throws IllegalArgumentException if role is null.
     */
    public List<Patient> findByRole(Role role) {
        ObjectValidator.validateObject(role);
        return patientRepository.findByRole(role);
    }

    /**
     * Finds Patient entities by their Role id.
     * @param id Long.
     * @return List<Patient> with the specified Role id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<Patient> findByRoleId(Long id) {
        ObjectValidator.validateId(id);
        return patientRepository.findByRoleId(id);
    }

    /**
     * Finds Patient entities by their birthdate.
     * @param birthdate String.
     * @return List<Patient> with the given birthdate.
     * @throws IllegalArgumentException if birthdate is null or blank.
     */
    public List<Patient> findByBirthdate(String birthdate) {
        ObjectValidator.validateString(birthdate);
        return patientRepository.findByBirthdate(birthdate);
    }

    /**
     * Finds Patient entities by their occupation.
     * @param occupation String.
     * @return List<Patient> with the given occupation.
     * @throws IllegalArgumentException if occupation is null or blank.
     */
    public List<Patient> findByOccupation(String occupation) {
        ObjectValidator.validateString(occupation);
        return patientRepository.findByOccupation(occupation);
    }

    /**
     * Finds Patient entities by their address.
     * @param address String.
     * @return List<Patient> with the given address.
     * @throws IllegalArgumentException if address is null or blank.
     */
    public List<Patient> findByAddress(String address) {
        ObjectValidator.validateString(address);
        return patientRepository.findByAddress(address);
    }

    /**
     * Finds a Patient by its PatientMedicalFile.
     * @param pmf PatientMedicalFile.
     * @return Patient with the given PatientMedicalFile.
     * @throws IllegalArgumentException if pmf is null.
     */
    public Patient findByPatientMedicalFile(PatientMedicalFile pmf) {
       ObjectValidator.validateObject(pmf);
       return patientRepository.findByMedicalFile(pmf);
    }

    /**
     * Finds a Patient by its PatientMedicalFile id.
     * @param id Long.
     * @return Patient with the given PatientMedicalFile id.
     * @throws IllegalArgumentException if id is null.
     */
    public Patient findByPatientMedicalFileId(Long id) {
        ObjectValidator.validateObject(id);
        return patientRepository.findByMedicalFileId(id);
    }

    /**
     * Checks if a Patient exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return patientRepository.existsById(id);
    }

    /**
     * Checks if a Patient exists in the database by its email
     * @param email String
     * @return boolean
     * @throws IllegalArgumentException if email is null or blank.
     */
    public boolean existsByEmail(String email) {
        ObjectValidator.validateString(email);
        return this.patientRepository.existsByEmail(email);
    }

    /**
     * Creates an IllegalArgumentException with a message indicating that a Patient with the same email was found.
     * @param patient Patient.
     * @return IllegalArgumentException.
     */
    private IllegalArgumentException createDuplicatePatientException(Patient patient) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_EMAIL_ALREADY_REGISTERED +
                " Patient Name: " + patient.getName() +
                ". Patient Email: " + patient.getEmail());
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }
}
