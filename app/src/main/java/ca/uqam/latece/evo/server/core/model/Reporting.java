package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Reporting model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "reporting")
@PrimaryKeyJoinColumn(name="reporting_id", referencedColumnName = "bci_activity_id") // Foreign key to bci_activity table used to represent the super class BCIActivity in the database.
public class Reporting extends BCIActivity {

    @NotNull
    @Column(name = "reporting_frequency", nullable = false, length = 256)
    private String frequency;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "reporting_concerns_bci_activity_id", referencedColumnName = "bci_activity_id", nullable = true)
    private BCIActivity bciActivity;

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setBciActivity(BCIActivity bciActivity) {
        this.bciActivity = bciActivity;
    }

    public BCIActivity getBciActivity() {
        return bciActivity;
    }

}
