package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Patient repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
 */
@Repository
public interface PatientRepository extends EvoRepository<Patient> {
    /**
     * Finds a list of Patient entities by their name.
     * @param name the name of the Patient to search for.
     * @return the Patient with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    List<Patient> findByName(String name);

    /**
     * Finds a Patient by its email.
     * @param email must not be null.
     * @return the Patient with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if email is null.
     */
    List<Patient> findByEmail(String email);

    /**
     * Finds a list of Patient by their contact information.
     * @param contactInformation must not be null.
     * @return List<Patient> with entities that match the given contactInformation or Optional#empty() if none found.
     * @throws IllegalArgumentException if contactInformation is null.
     */
    List<Patient> findByContactInformation(String contactInformation);

    /**
     * Retrieves a list of Patient entities that match the specified Role Id.
     * @param roleId The Role Id to filter Develops entities by, must not be null.
     * @return a list of Patient entities that have the specified Role id, or an empty list if no matches are found.
     */
    @Query(value = "SELECT ac.*, pt.*, 0 AS clazz_ FROM actor AS ac " +
            "JOIN patient pt ON (ac.actor_id = pt.Patient_actor_id) " +
            "WHERE ac.actor_role_id = :role_id",
            nativeQuery = true)
    List<Patient> findByRole (@Param("role_id") Long roleId);

    /**
     * Finds a list of Patient by their birthdate.
     * @param birthdate must not be null or blank.
     * @return List<Patient> with entities that match the given birthdate or Optional#empty() if none found.
     * @throws IllegalArgumentException if birthdate is null or blank.
     */
    List<Patient> findByBirthdate (String birthdate);

    /**
     * Finds a list of Patient by their occupation.
     * @param occupation must not be null or blank.
     * @return List<Patient> with entities that match the given occupation or Optional#empty() if none found.
     * @throws IllegalArgumentException if occupation is null or blank.
     */
    List<Patient> findByOccupation (String occupation);

    /**
     * Finds a list of Patient by their address.
     * @param address must not be null or blank.
     * @return List<Patient> with entities that match the given address or Optional#empty() if none found.
     * @throws IllegalArgumentException if address is null or blank.
     */
    List<Patient> findByAddress(String address);

    /**
     * Finds a list of Patient by their PatientMedicalFile id.
     * @param id must not be null or blank.
     * @return Patient that matches the given PatientMedicalFile id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null or blank.
     */
    @Query(value = "SELECT pt.*, pmf.* FROM patient AS pt " +
            "JOIN patient_medicalfile pmf ON (pt.patient_patient_medical_file_id = pmf.patient_medicalfile_id) ",
            nativeQuery = true)
    Patient findByPatientMedicalFile(Long id);

    /**
     * Finds a list of Patient by their PatientMedicalFile medicalHistory.
     * @param medicalHistory must not be null or blank.
     * @return List<Patient> with entities that match the given medicalHistory or Optional#empty() if none found.
     * @throws IllegalArgumentException if medicalHistory is null or blank.
     */
    @Query(value = "SELECT pt.*, pmf.* FROM patient AS pt " +
            "JOIN patient_medicalfile pmf ON (pt.patient_patient_medical_file_id = pmf.patient_medicalfile_id) ",
            nativeQuery = true)
    List<Patient> findByMedicalHistory(String medicalHistory);

    /**
     * Checks if a Patient entity with the specified name exists in the repository.
     * @param name the name of the Patient to check for existence, must not be null.
     * @return true if a Patient with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    boolean existsByName(String name);

    /**
     * Checks if a Patient entity with the specified email exists in the repository.
     * @param email the email of the Patient to check for existence, must not be null.
     * @return true if a Patient with the specified email exists, false otherwise.
     * @throws IllegalArgumentException if the email is null.
     */
    boolean existsByEmail(String email);

    /**
     * Checks if a Patient entity with the specified contact information exists in the repository.
     * @param contactInformation the contact information of the Patient to check for existence, must not be null.
     * @return true if a Patient with the specified contact information exists, false otherwise.
     * @throws IllegalArgumentException if the contactInformation is null.
     */
    boolean existsByContactInformation(String contactInformation);

    /**
     * Checks if a Patient entity with the specified birthdate exists in the repository.
     * @param birthdate
     * @return true if a Patient with the specified birthdate exists, false otherwise.
     * @throws IllegalArgumentException if the birthdate is null or blank.
     */
    boolean existsByBirthdate(String birthdate);

    /**
     * Checks if a Patient entity with the specified occupation exists in the repository.
     * @param occupation
     * @return true if a Patient with the specified occupation exists, false otherwise.
     * @throws IllegalArgumentException if the occupation is null or blank.
     */
    boolean existsByOccupation(String occupation);

    /**
     * Checks if a Patient entity with the specified address exists in the repository.
     * @param address
     * @return true if a Patient with the specified address exists, false otherwise.
     * @throws IllegalArgumentException if the address is null or blank.
     */
    boolean existsByAddress(String address);
}
