package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientAssessment;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

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
     * Finds PatientAssessment entities by their date.
     * @param date LocalDate.
     * @return List<PatientAssessment> with the given date.
     * @throws IllegalArgumentException if date is null.
     */
    List<PatientAssessment> findByDate(LocalDate date);

    /**
     * Finds PatientAssessment entities by their assessment.
     * @param assessment String.
     * @return List<PatientAssessment> with the given assessment.
     * @throws IllegalArgumentException if assessment is blank or null.
     */
    List<PatientAssessment> findByAssessment(String assessment);

    /**
     * Finds PatientAssessment entities by their Patient.
     * @param patient Patient.
     * @return List<PatientAssessment> with the given patient.
     * @throws IllegalArgumentException if patient is null.
     */
    List<PatientAssessment> findByPatient(Patient patient);

    /**
     * Finds PatientAssessment entities by their Patient id.
     * @param id Long.
     * @return List<PatientAssessment> with the given Patient id.
     * @throws IllegalArgumentException if id is null.
     */
    List<PatientAssessment> findByPatientId(Long id);
}
