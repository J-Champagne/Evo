package ca.uqam.latece.evo.server.core.model;

import jakarta.persistence.*;


/**
 * GoalSetting model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "goal_setting")
@PrimaryKeyJoinColumn(name="goal_setting_bci_activity_id", referencedColumnName = "bci_activity_id")
public class GoalSetting extends BCIActivity {

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