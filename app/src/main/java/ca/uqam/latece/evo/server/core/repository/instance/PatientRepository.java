package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Patient repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
 */
@Repository
public interface PatientRepository extends EvoRepository<Patient> {
    /**
     * Finds Patient entities by their name.
     * @param name String.
     * @return Patient with the given name.
     * @throws IllegalArgumentException if name is null or blank.
     */
    List<Patient> findByName(String name);

    /**
     * Finds a Patient by its email.
     * @param email String.
     * @return Patient with the given email.
     * @throws IllegalArgumentException if email is null or blank.
     */
    Patient findByEmail(String email);

    /**
     * Finds Patient entities by their contactInformation.
     * @param contactInformation String.
     * @return List<Patient> with the given contactInformation.
     * @throws IllegalArgumentException if contactInformation is null or blank.
     */
    List<Patient> findByContactInformation(String contactInformation);

    /**
     * Finds Patient entities by their Role.
     * @param role Role.
     * @return List<Patient> with the specified Role.
     * @throws IllegalArgumentException if role is null.
     */
    List<Patient> findByRole (Role role);

    /**
     * Finds Patient entities by their Role id.
     * @param id Long.
     * @return List<Patient> with the specified Role id.
     * @throws IllegalArgumentException if id is null.
     */
    List<Patient> findByRoleId (Long id);

    /**
     * Finds Patient entities by their birthdate.
     * @param birthdate String.
     * @return List<Patient> with the given birthdate.
     * @throws IllegalArgumentException if birthdate is null or blank.
     */
    List<Patient> findByBirthdate (String birthdate);

    /**
     * Finds Patient entities by their occupation.
     * @param occupation String.
     * @return List<Patient> with the given occupation.
     * @throws IllegalArgumentException if occupation is null or blank.
     */
    List<Patient> findByOccupation (String occupation);

    /**
     * Finds Patient entities by their address.
     * @param address String.
     * @return List<Patient> with the given address.
     * @throws IllegalArgumentException if address is null or blank.
     */
    List<Patient> findByAddress(String address);

    /**
     * Finds a Patient by its PatientMedicalFile.
     * @param pmf PatientMedicalFile.
     * @return Patient with the given PatientMedicalFile.
     * @throws IllegalArgumentException if pmf is null.
     */
    Patient findByMedicalFile(PatientMedicalFile pmf);

    /**
     * Finds a Patient by its PatientMedicalFile id.
     * @param id Long.
     * @return Patient with the given PatientMedicalFile id.
     * @throws IllegalArgumentException if id is null.
     */
    Patient findByMedicalFileId(Long id);

    /**
     * Checks if a Patient exists in the database by its email
     * @param email String
     * @return boolean
     * @throws IllegalArgumentException if email is null or blank.
     */
    boolean existsByEmail(String email);
}
