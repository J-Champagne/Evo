package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.GoalSetting;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * GoalSettingInstance model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "goal_setting_instance")
@PrimaryKeyJoinColumn(name="goal_setting_instance_id", referencedColumnName = "bci_activity_instance_id") // Foreign key to bci_activity_instance table used to represent the super class BCIActivityInstance in the database.
@JsonPropertyOrder({"id", "status", "entryDate", "exitDate", "bciConcernsInstance", "goalSetting"})
public class GoalSettingInstance extends BCIActivityInstance {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_setting_instance_bci_concerns_instance_id")
    private BCIActivityInstance bciConcernsInstance;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "goal_setting_instance_goal_setting_id", nullable = false)
    private GoalSetting goalSetting;


    public void setBCIActivityInstance(BCIActivityInstance bciActivityInstance) {
        bciConcernsInstance = bciActivityInstance;
    }

    public BCIActivityInstance getBCIActivityInstance(){
        return bciConcernsInstance;
    }

    public void setGoalSetting(GoalSetting goalSetting){
        this.goalSetting = goalSetting;
    }

    public GoalSetting getGoalSetting(){
        return this.goalSetting;
    }
}