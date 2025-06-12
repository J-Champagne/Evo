package ca.uqam.latece.evo.server.core.model.instance;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * HealthCareProfessional instance class.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "healthcare_professional")
@PrimaryKeyJoinColumn(name="healthcare_professional_id", referencedColumnName = "actor_id")
@Transactional
@JsonPropertyOrder({"position", "affiliation", "specialties"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class HealthCareProfessional extends Actor {
    @Column(name = "healthcare_professional_position")
    private String position;

    @Column(name = "healthcare_professional_affiliation")
    private String affiliation;

    @Column(name = "healthcare_professional_specialties")
    private String specialties;

    public HealthCareProfessional() {}

    public HealthCareProfessional(@NotNull String name, @NotNull String email, @NotNull String contactInformation) {
        super(name, email, contactInformation);
    }

    public HealthCareProfessional(@NotNull String name, @NotNull String email, @NotNull String contactInformation,
                                  String position, String affiliation, String specialties) {
        this(name, email, contactInformation);
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

    @Override
    public boolean equals (Object object) {
        if (super.equals(object)) {
            HealthCareProfessional hcp = (HealthCareProfessional) object;
            return Objects.equals(this.getPosition(), hcp.getPosition()) &&
                    Objects.equals(this.getAffiliation(), hcp.getAffiliation()) &&
                    Objects.equals(this.getSpecialties(), hcp.getSpecialties());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName(), this.getEmail(), this.getContactInformation(),
                this.getPosition(), this.getAffiliation(), this.getSpecialties());
    }
}
