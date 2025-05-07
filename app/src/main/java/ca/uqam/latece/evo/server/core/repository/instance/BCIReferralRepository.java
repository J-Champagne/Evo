package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.BCIReferral;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * BCIReferral repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
 */
@Repository
public interface BCIReferralRepository extends EvoRepository<BCIReferral> {
    /**
     * Checks if a BCIReferral entity with the specified date exists in the repository.
     * @param date the date of the BCIReferral to check for existence, must not be null.
     * @return true if a BCIReferral with the specified date exists, false otherwise.
     * @throws IllegalArgumentException if the date is null.
     */
    boolean existsByDate(LocalDate date);

    /**
     * Finds a list of BCIReferral by their Date.
     * @param date must not be null.
     * @return List<BCIReferral> with entities that match the given date or Optional#empty() if none found.
     * @throws IllegalArgumentException if date is null.
     */
    List<BCIReferral> findByDate(LocalDate date);

    /**
     * Finds a list of BCIReferral by their reason.
     * @param reason must not be null or blank.
     * @return List<BCIReferral> with entities that match the given assessment or Optional#empty() if none found.
     * @throws IllegalArgumentException if reason is null or blank.
     */
    List<BCIReferral> findByReason(String reason);

    /**
     * Finds a BCIReferral by their patient id.
     * @param id must not be null or blank.
     * @return List<BCIReferral> that match the given patient id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null or blank.
     */
    @Query(value = "SELECT bcir.*, pt.* FROM bci_referral AS bcir " +
            "JOIN patient pt ON (bcir.bci_referral_patient = pt.patient_id) ",
            nativeQuery = true)
    List<BCIReferral> findByPatient(Long id);

    /**
     * Finds a BCIReferral by their patientAssessment id.
     * @param id must not be null or blank.
     * @return BCIReferral the entity that match the given patientAssessment id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null or blank.
     */
    @Query(value = "SELECT bcir.*, pa.* FROM bci_referral AS bcir " +
            "JOIN patient_assessment pa ON (bcir.bci_referral_patient_assessment = pa.patient_assessment_id) ",
            nativeQuery = true)
    BCIReferral findByPatientAssessment(Long id);

    /**
     * Finds a BCIReferral by their referring Healthcare professional id.
     * @param id must not be null or blank.
     * @return BCIReferral the entity that match the given referring Healthcare professional id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null or blank.
     */
    @Query(value = "SELECT bcir.*, hcp.* FROM bci_referral AS bcir " +
            "JOIN healthcare_professional hcp ON (bcir.bci_referral_professional = hcp.healthcare_professional_id) ",
            nativeQuery = true)
    BCIReferral findByReferringProfessional(Long id);

    /**
     * Finds a BCIReferral by their Healthcare Professional interventionist id.
     * @param id must not be null or blank.
     * @return BCIReferral the entity that match the given referring Healthcare professional id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null or blank.
     */
    @Query(value = "SELECT bcir.*, hcp.* FROM bci_referral AS bcir " +
            "JOIN healthcare_professional hcp ON (bcir.bci_referral_interventionist = hcp.healthcare_professional_id) ",
            nativeQuery = true)
    BCIReferral findByBehaviorChangeInterventionist(Long id);
}
