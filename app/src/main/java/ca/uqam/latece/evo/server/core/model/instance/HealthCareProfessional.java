package ca.uqam.latece.evo.server.core.model.instance;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

/**
 * HealthCareProfessional instance class.
 * @version 1.0
 * @author Julien Champagne.
 */
@Entity
@Table(name = "healthcareprofessional")
@PrimaryKeyJoinColumn(name="healthcareprofessional_id", referencedColumnName = "actor_id")
@Transactional
@JsonPropertyOrder({"position", "affiliation", "specialties"})
public class HealthCareProfessional extends Actor {
    @Column(name = "healthcareprofessional_position", length = 256)
    private String position;

    @Column(name = "healthcareprofessional_affiliation", length = 256)
    private String affiliation;

    @Column(name = "healthcareprofessional_specialties", length = 256)
    private String specialties;

    public HealthCareProfessional() {}

    public HealthCareProfessional(@NotNull String name, @NotNull String email, @NotNull String contactInformation) {
        super(name, email, contactInformation);
    }

    public HealthCareProfessional(@NotNull String name, @NotNull String email, @NotNull String contactInformation,
                                  String position, String affiliation, String specialties) {
        super(name, email, contactInformation);
        this.position = position;
        this.affiliation = affiliation;
        this.specialties = specialties;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }
}
