package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.BCIReferral;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
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
     * Finds BCIReferral entities by their date.
     * @param date LocalDate.
     * @return List<BCIReferral> with the given date.
     * @throws IllegalArgumentException if date is null.
     */
    List<BCIReferral> findByDate(LocalDate date);

    /**
     * Finds BCIReferral entities by their reason.
     * @param reason String.
     * @return List<BCIReferral> with the given reason.
     * @throws IllegalArgumentException if reason is blank or null.
     */
    List<BCIReferral> findByReason(String reason);

    /**
     * Finds BCIReferral entities by their Patient id.
     * @param id Long.
     * @return List<BCIReferral> with the given Patient id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BCIReferral> findByPatientId(Long id);

    /**
     * Finds BCIReferral entities by their PatientAssessment id.
     * @param id Long.
     * @return List<BCIReferral> with the given PatientAssessment id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BCIReferral> findByPatientAssessmentId(Long id);

    /**
     * Finds BCIReferral entities by their ReferringProfessional id.
     * @param id Long.
     * @return List<BCIReferral> with the given ReferringProfessional id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BCIReferral> findByReferringProfessionalId(Long id);

    /**
     * Finds BCIReferral entities by their BehaviorChangeInterventionis id.
     * @param id Long.
     * @return List<BCIReferral> with the given BehaviorChangeInterventionis id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BCIReferral> findByBehaviorChangeInterventionistId(Long id);

    /**
     * Finds BCIReferral entities by a BehaviorChangeInterventionInstance id.
     * @param id Long.
     * @return List<BCIReferral> with the given BehaviorChangeInterventionInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BCIReferral> findByInterventionsId(Long id);
}
