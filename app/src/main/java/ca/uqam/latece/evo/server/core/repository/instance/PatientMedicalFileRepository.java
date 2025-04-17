package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PatientMedicalFileRepository extends EvoRepository <PatientMedicalFile> {
    /**
     * Finds a list of PatientMedicalFile entities by their date.
     * @param date the date of the PatientMedicalFile to search for.
     * @return a list of PatientMedicalFile with the given date or Optional#empty() if none found.
     * @throws IllegalArgumentException if the date is null.
     */
    List<PatientMedicalFile> findByDate (LocalDate date);

    /**
     * Finds a list of PatientMedicalFile entities by their medicalHistory.
     * @param medicalHistory the medicalHistory of the PatientMedicalFile to search for.
     * @return a list of PatientMedicalFile with the given medicalHistory or Optional#empty() if none found.
     * @throws IllegalArgumentException if the medicalHistory is null or blank.
     */
    List<PatientMedicalFile> findByMedicalHistory (String medicalHistory);

    /**
     * Checks if a PatientMedicalFile with the specified date exists in the database.
     * @param date the date of the PatientMedicalFile to check for.
     * @return true if the PatientMedicalFile with the specified date exists in the database.
     * @throws IllegalArgumentException if the date is null or blank.
     */
    boolean existsByDate(Date date);

    /**
     * Checks if a PatientMedicalFile with the specified medicalHistory exists in the database.
     * @param medicalHistory the medicalHistory of the PatientMedicalFile to check for.
     * @return true if the PatientMedicalFile with the specified medicalHistory exists in the database.
     * @throws IllegalArgumentException if the medicalHistory is null or blank.
     */
    boolean existsByMedicalHistory(String medicalHistory);
}
