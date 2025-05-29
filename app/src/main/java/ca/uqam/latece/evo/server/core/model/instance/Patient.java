package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.Role;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Patient instance class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "patient")
@PrimaryKeyJoinColumn(name="patient_id", referencedColumnName = "actor_id")
@Transactional
@JsonPropertyOrder({"birthdate", "occupation", "address", "medicalFile"})
public class Patient extends Actor {
    @Column(name = "patient_birthdate")
    private String birthdate;

    @Column(name = "patient_occupation")
    private String occupation;

    @Column(name = "patient_address")
    private String address;

    @OneToOne
    @JoinColumn(name = "patient_patient_medical_file_id", referencedColumnName = "patient_medicalfile_id")
    private PatientMedicalFile medicalFile;

    public Patient() {}

    public Patient(@NotNull String name, @NotNull String email, @NotNull String contactInformation, @NotNull Role role) {
        super(name, email, contactInformation, role);
    }

    public Patient(@NotNull String name, @NotNull String email, @NotNull String contactInformation, @NotNull Role role,
                   String birthdate, String occupation, String address) {
        this(name, email, contactInformation, role);
        this.birthdate = birthdate;
        this.occupation = occupation;
        this.address = address;
    }

    public Patient(@NotNull String name, @NotNull String email, @NotNull String contactInformation, @NotNull Role role,
                   String birthdate, String occupation, String address, PatientMedicalFile medicalFile) {
        this(name, email, contactInformation, role, birthdate, occupation, address);
        this.medicalFile = medicalFile;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PatientMedicalFile getMedicalFile() {
        return this.medicalFile;
    }

    public void setMedicalFile(PatientMedicalFile medicalFile) {
        this.medicalFile = medicalFile;
    }

    @Override
    public boolean equals (Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (super.equals(object)) {
            Patient patient = (Patient) object;
            return Objects.equals(this.getBirthdate(), patient.getBirthdate()) &&
                    Objects.equals(this.getOccupation(), patient.getOccupation()) &&
                    Objects.equals(this.getAddress(), patient.getAddress()) &&
                    Objects.equals(this.getMedicalFile(), patient.getMedicalFile());
        } else {
            return false;
        }
    }
}
