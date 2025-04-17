package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.Patient;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Patient Service.
 * @author Julien Champagne.
 */
@Service
@Transactional
public class PatientService extends AbstractEvoService<Patient> {
    private final Logger logger = LoggerFactory.getLogger(PatientService.class);
    private static final String ERROR_EMAIL_ALREADY_REGISTERED = "HealthCareProfessional already registered with the same email!";
    
    @Autowired
    private PatientRepository patientRepository;

    /**
     * Inserts a Patient in the database.
     * @param patient the Patient entity.
     * @return The saved Patient.
     * @throws IllegalArgumentException in case the given Patient is null or is already registered with the same email.
     * @throws OptimisticLockingFailureException when the Patient uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public Patient create(Patient patient) {
        Patient patientCreated = null;

        ObjectValidator.validateObject(patient);
        ObjectValidator.validateEmail(patient.getEmail());
        ObjectValidator.validateString(patient.getName());
        ObjectValidator.validateString(patient.getContactInformation());

        // The email should be unique.
        if (this.existsByEmail(patient.getEmail())) {
            throw this.createDuplicateHcpException(patient);
        } else {
            patientCreated = this.save(patient);
            logger.info("Patient created: {}", patientCreated);
        }

        return patientCreated;
    }

    /**
     * Create duplicate Patient Exception.
     * @param patient the Patient entity.
     * @return an exception object.
     */
    private IllegalArgumentException createDuplicateHcpException(Patient patient) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_EMAIL_ALREADY_REGISTERED +
                " Patient Name: " + patient.getName() +
                ". Patient Email: " + patient.getEmail());
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    /**
     * Updates a Patient in the database.
     * @param patient the Patient entity.
     * @return The updated Patient.
     * @throws IllegalArgumentException in case the given Patient is null or is already registered with the same email.
     * @throws OptimisticLockingFailureException when the Patient uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    public Patient update(Patient patient) {
        Patient patientUpdated = null;
        Patient patientFound = this.findById(patient.getId());

        ObjectValidator.validateObject(patient);
        ObjectValidator.validateEmail(patient.getEmail());
        ObjectValidator.validateString(patient.getName());
        ObjectValidator.validateString(patient.getContactInformation());

        if (patientFound.getEmail().equalsIgnoreCase(patient.getEmail())) {
            patientUpdated = this.save(patient);
        } else {
            List<Patient> hcpEmailFound = patientRepository.findByEmail(patient.getEmail());

            // The email should be unique
            if (hcpEmailFound.isEmpty()) {
                patientUpdated = this.save(patient);
            } else {
                throw this.createDuplicateHcpException(patient);
            }
        }

        logger.info("Patient updated: {}", patientUpdated);
        return patientUpdated;
    }

    /**
     * Method used to create or update a Patient.
     * @param patient the Patient entity.
     * @return The inserted or updated Patient.
     * @throws IllegalArgumentException in case the given Patient is null or is already registered with the same email.
     * @throws OptimisticLockingFailureException when the Patient uses optimistic locking and has a version attribute with
     *          a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *          present but does not exist in the database.
     */
    @Override
    @Transactional
    public Patient save(Patient patient) {
        return this.patientRepository.save(patient);
    }

    /**
     * Checks if a Patient entity with the specified id exists in the repository.
     * @param id the id of the Patient to check for existence, must not be null.
     * @return true if a Patient with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return patientRepository.existsById(id);
    }

    /**
     * Checks if a Patient entity with the specified name exists in the repository.
     * @param name the name of the Patient to check for existence, must not be null.
     * @return true if a Patient with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        ObjectValidator.validateString(name);
        return this.patientRepository.existsByName(name);
    }

    /**
     * Checks if a Patient entity with the specified email exists in the repository.
     * @param email the email of the Patient to check for existence, must not be null.
     * @return true if a Patient with the specified email exists, false otherwise.
     * @throws IllegalArgumentException if the email is null.
     */
    public boolean existsByEmail(String email) {
        ObjectValidator.validateString(email);
        return this.patientRepository.existsByEmail(email);
    }

    /**
     * Checks if a Patient entity with the specified contact information exists in the repository.
     * @param contactInformation the contactInformation of the Patient to check for existence, must not be null.
     * @return true if a Patient with the specified contactInformation exists, false otherwise.
     * @throws IllegalArgumentException if the contactInformation is null or blank.
     */
    public boolean existsByContactInformation(String contactInformation) {
        ObjectValidator.validateString(contactInformation);
        return this.patientRepository.existsByContactInformation(contactInformation);
    }

    /**
     * Checks if a Patient entity with the specified birthdate exists in the repository.
     * @param birthdate the birthdate of the Patient to check for existence, must not be null.
     * @return true if a Patient with the specified birthdate exists, false otherwise.
     * @throws IllegalArgumentException if the birthdate is null or blank.
     */
    public boolean existsByPosition(String birthdate) {
        ObjectValidator.validateString(birthdate);
        return patientRepository.existsByBirthdate(birthdate);
    }

    /**
     * Checks if a Patient entity with the specified occupation exists in the repository.
     * @param occupation the occupation of the Patient to check for existence, must not be null.
     * @return true if a Patient with the specified occupation exists, false otherwise.
     * @throws IllegalArgumentException if the occupation is null or blank.
     */
    public boolean existsByAffiliation(String occupation) {
        ObjectValidator.validateString(occupation);
        return patientRepository.existsByOccupation(occupation);
    }

    /**
     * Checks if a Patient entity with the specified address exists in the repository.
     * @param address the specialties of the Patient to check for existence, must not be null.
     * @return true if a Patient with the specified address exists, false otherwise.
     * @throws IllegalArgumentException if the address is null or blank.
     */
    public boolean existsBySpecialties(String address) {
        ObjectValidator.validateString(address);
        return patientRepository.existsByAddress(address);
    }

    /**
     * Deletes the Patient with the given id.
     * If the Patient is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Patient to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        patientRepository.deleteById(id);
        logger.info("Patient deleted {}", id);
    }

    /**
     * Gets all Patient.
     * @return all Patient.
     */
    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll().stream().toList();
    }

    /**
     * Finds a Patient by its id.
     * @param id the unique identifier of the Patient to be retrieved; must not be null or invalid.
     * @return the Patient with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public Patient findById(Long id) {
        ObjectValidator.validateId(id);
        return patientRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Patient not found"));
    }

    /**
     * Finds a Patient by its name.
     * @param name must not be null.
     * @return the Patient with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    public List<Patient> findByName(String name) {
        ObjectValidator.validateString(name);
        return patientRepository.findByName(name);
    }

    /**
     * Finds a Patient by its email.
     * @param email must not be null.
     * @return the Patient the given email or Optional#empty() if none found.
     * @throws IllegalArgumentException if email is null.
     */
    public List<Patient> findByEmail(String email) {
        ObjectValidator.validateString(email);
        return patientRepository.findByEmail(email);
    }

    /**
     * Retrieves a list of Patient entities that match the specified contactInformation.
     * @param contactInformation must not be null or blank.
     * @return List<Patient> with the given contactInformation or Optional#empty() if none found.
     * @throws IllegalArgumentException if contactInformation is null or blank.
     */
    public List<Patient> findByContactInformation(String contactInformation) {
        ObjectValidator.validateString(contactInformation);
        return patientRepository.findByContactInformation(contactInformation);
    }

    /**
     * Retrieves a list of Patient entities that match the specified Role Id.
     * @param roleId The Role Id to filter Patient entities by, must not be null.
     * @return List<Patient> that have the specified Role id, or an empty list if no matches are found.
     */
    public List<Patient> findByRole(Long roleId) {
        ObjectValidator.validateId(roleId);
        return patientRepository.findByRole(roleId);
    }

    /**
     * Retrieves a list of Patient entities that match the specified birthdate.
     * @param birthdate must not be null or blank.
     * @return List<Patient> with the given birthdate or Optional#empty() if none found.
     * @throws IllegalArgumentException if birthdate is null or blank.
     */
    public List<Patient> findByBirthdate(String birthdate) {
        ObjectValidator.validateString(birthdate);
        return patientRepository.findByBirthdate(birthdate);
    }

    /**
     * Retrieves a list of Patient entities that match the specified occupation.
     * @param occupation must not be null or blank.
     * @return List<Patient> with the given occupation or Optional#empty() if none found.
     * @throws IllegalArgumentException if occupation is null or blank.
     */
    public List<Patient> findByOccupation(String occupation) {
        ObjectValidator.validateString(occupation);
        return patientRepository.findByOccupation(occupation);
    }

    /**
     * Retrieves a list of Patient entities that match the specified address.
     * @param address must not be null or blank.
     * @return List<Patient> with the given address or Optional#empty() if none found.
     * @throws IllegalArgumentException if address is null or blank.
     */
    public List<Patient> findByAddress(String address) {
        ObjectValidator.validateString(address);
        return patientRepository.findByAddress(address);
    }

    /**
     * Retrieves a list of Patient entities that match the specified PatientMedicalFile id.
     * @param id must not be null or blank.
     * @return List<Patient> with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null or blank.
     */
    public Patient findByPatientMedicalFile(Long id) {
        Patient patientFound = null;
        List<Patient> allPatients;

        ObjectValidator.validateId(id);
        allPatients = this.findAll();
        for (Patient patient : allPatients) {
            if (patient.getMedicalFile().getId().equals(id)) {
                patientFound = patient;
            }
        }

        if (patientFound == null) {
            throw new EntityNotFoundException("Patient by PatientMedicalFile not found");
        }
        return patientFound;
    }

    /**
     * Retrieves a list of Patient entities that match the specified medicalHistory.
     * @param medicalHistory must not be null or blank.
     * @return List<Patient> with the given medicalHistory or Optional#empty() if none found.
     * @throws IllegalArgumentException if medicalHistory is null or blank.
     */
    public List<Patient> findByMedicalHistory(String medicalHistory) {
        List<Patient> results = new ArrayList<>();
        List<Patient> allPatients;

        ObjectValidator.validateString(medicalHistory);
        allPatients = this.findAll();
        for (Patient patient : allPatients) {
            if (patient.getMedicalFile().getMedicalHistory().equals(medicalHistory)) {
                results.add(patient);
            }
        }

        return results;
    }
}
