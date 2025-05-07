package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.PatientAssessment;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * PatientAssessment repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
 */
@Repository
public interface PatientAssessmentRepository extends EvoRepository<PatientAssessment> {
    /**
     * Finds a list of PatientAssessment by their Date.
     * @param date must not be null.
     * @return List<PatientAssessment> with entities that match the given date or Optional#empty() if none found.
     * @throws IllegalArgumentException if date is null.
     */
    List<PatientAssessment> findByDate(LocalDate date);

    /**
     * Finds a list of PatientAssessment by their assessment.
     * @param assessment must not be null or blank.
     * @return List<PatientAssessment> with entities that match the given assessment or Optional#empty() if none found.
     * @throws IllegalArgumentException if assessment is null or blank.
     */
    List<PatientAssessment> findByAssessment(String assessment);

    /**
     * Finds a list of PatientAssessment by their patient id.
     * @param id must not be null or blank.
     * @return List<PatientAssessment> with entities that match the given patient id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null or blank.
     */
    @Query(value = "SELECT pa.*, pt.* FROM patient_assessment AS pa " +
            "JOIN patient pt ON (pa.patient_assessment_patient = pt.patient_id) ",
            nativeQuery = true)
    List<PatientAssessment> findByPatient(Long id);

    /**
     * Checks if a PatientAssessment entity with the specified date exists in the repository.
     * @param date the date of the PatientAssessment to check for existence, must not be null.
     * @return true if a PatientAssessment with the specified date exists, false otherwise.
     * @throws IllegalArgumentException if the date is null.
     */
    boolean existsByDate(LocalDate date);
}
