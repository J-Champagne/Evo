package ca.uqam.latece.evo.server.core.model.instance;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

/**
 * Actor instance class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "patient")
@PrimaryKeyJoinColumn(name="patient_id", referencedColumnName = "actor_id")
@Transactional
@JsonPropertyOrder({"birthdate", "occupation", "address", "medicalFile"})
public class Patient extends Actor {
    @Column(name = "patient_birthdate", length = 256)
    private String birthdate;

    @Column(name = "patient_occupation", length = 256)
    private String occupation;

    @Column(name = "patient_address", length = 256)
    private String address;

    @OneToOne
    @JoinColumn(name = "patient_patient_medical_file_id", referencedColumnName = "patient_medicalfile_id")
    private PatientMedicalFile medicalFile;

    public Patient() {}

    public Patient(@NotNull String name, @NotNull String email, @NotNull String contactInformation) {
        super(name, email, contactInformation);
    }

    public Patient(@NotNull String name, @NotNull String email, @NotNull String contactInformation,
                   String birthdate, String occupation, String address) {
        super(name, email, contactInformation);
        this.birthdate = birthdate;
        this.occupation = occupation;
        this.address = address;
    }

    public Patient(@NotNull String name, @NotNull String email, @NotNull String contactInformation,
                   String birthdate, String occupation, String address, PatientMedicalFile medicalFile) {
        this(name, email, contactInformation, birthdate, occupation, address);
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
}
