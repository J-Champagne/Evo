package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import java.time.LocalDate;
import java.util.List;

public interface PatientMedicalFileRepository extends EvoRepository <PatientMedicalFile> {
    /**
     * Finds Patient entities by their date.
     * @param date LocalDate.
     * @return List<Patient> with the given date.
     * @throws IllegalArgumentException if date is null.
     */
    List<PatientMedicalFile> findByDate (LocalDate date);

    /**
     * Finds Patient entities by their medicalHistory.
     * @param medicalHistory String.
     * @return List<Patient> with the given medicalHistory.
     * @throws IllegalArgumentException if medicalHistory is null or blank.
     */
    List<PatientMedicalFile> findByMedicalHistory (String medicalHistory);
}
