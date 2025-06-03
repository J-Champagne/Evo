package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;


/**
 * GoalSetting model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "goal_setting")
@PrimaryKeyJoinColumn(name="goal_setting_id", referencedColumnName = "bci_activity_id") // Foreign key to bci_activity table used to represent the super class BCIActivity in the database.
public class GoalSetting extends BCIActivity {

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "goal_setting_concerns_bci_activity_id", referencedColumnName = "bci_activity_id", nullable = true)
    private BCIActivity bciActivity;

    public void setBciActivity(BCIActivity bciActivity) {
        this.bciActivity = bciActivity;
    }

    public BCIActivity getBciActivity() {
        return bciActivity;
    }
}