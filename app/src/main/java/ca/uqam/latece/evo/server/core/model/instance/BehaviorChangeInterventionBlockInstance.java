package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BehaviorChangeInterventionBlock instance class.
 * @author Julien Champagne.
 */
@Entity
@JsonPropertyOrder({"stage"})
@Table(name = "bci_block_instance")
@PrimaryKeyJoinColumn(name="bci_block_instance_id", referencedColumnName = "activity_instance_id")
public class BehaviorChangeInterventionBlockInstance extends ActivityInstance {
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bci_block_instance_stage", length = 128, nullable = false)
    private TimeCycle stage;

    @NotNull
    @ManyToMany
    @JoinTable(
            name = "bci_block_instance_activities",
            joinColumns = @JoinColumn(name = "bci_block_instance_activities_block_id", referencedColumnName="bci_block_instance_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_block_instance_activities_activity_id", referencedColumnName="bci_activity_instance_id"))
    private List<BCIActivityInstance> activities = new ArrayList<>();

    public BehaviorChangeInterventionBlockInstance() {}

    public BehaviorChangeInterventionBlockInstance(String status) {
        super(status);
    }

    public BehaviorChangeInterventionBlockInstance(String status, TimeCycle stage) {
       this(status);
       this.stage = stage;
    }

    public BehaviorChangeInterventionBlockInstance(String status, TimeCycle stage, List<BCIActivityInstance> activities) {
        this(status, stage);
        this.activities = activities;
    }

    public BehaviorChangeInterventionBlockInstance(String status, LocalDate entryDate, LocalDate exitDate, TimeCycle stage,
                                                   List<BCIActivityInstance> activities) {
        super(status, entryDate, exitDate);
        this.stage = stage;
        this.activities = activities;
    }

    public TimeCycle getStage() {
        return stage;
    }

    public void setStage(TimeCycle stage) {
        this.stage = stage;
    }

    public List<BCIActivityInstance> getActivities() {
        return activities;
    }

    public void setActivities(List<BCIActivityInstance> activities) {
        this.activities = activities;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            BehaviorChangeInterventionBlockInstance bciBlockInstance = (BehaviorChangeInterventionBlockInstance) object;
            return Objects.equals(this.getStage(), bciBlockInstance.getStage()) &&
                    Objects.equals(this.getActivities(), bciBlockInstance.getActivities());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getStage(), this.getActivities());
    }
}
