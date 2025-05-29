package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * PatientMedicalFile instance class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "patient_medicalfile")
@Transactional
@JsonPropertyOrder({"id", "date", "medicalHistory"})
public class PatientMedicalFile extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="patient_medicalfile_id")
    private Long id;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "patient_medicalfile_date")
    private LocalDate date;

    @Column(name = "patient_medicalfile_medicalhistory")
    private String medicalHistory;

    public PatientMedicalFile() {}

    public PatientMedicalFile(String medicalHistory) {
        this.date = LocalDate.now();
        this.medicalHistory = medicalHistory;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getMedicalHistory() {
        return this.medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PatientMedicalFile pmf = (PatientMedicalFile) object;

        return Objects.equals(this.getDate(), pmf.getDate()) &&
                Objects.equals(this.getMedicalHistory(), pmf.getMedicalHistory());
    }
}
