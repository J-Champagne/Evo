package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;

import ca.uqam.latece.evo.server.core.interfaces.ProcessInstance;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BehaviorChangeInterventionBlock instance class.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Entity
@JsonPropertyOrder({"stage"})
@Table(name = "bci_block_instance")
@PrimaryKeyJoinColumn(name="bci_block_instance_id", referencedColumnName = "activity_instance_id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BehaviorChangeInterventionBlockInstance extends ActivityInstance implements ProcessInstance<BCIActivityInstance> {
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

    @ManyToMany (mappedBy = "activities")
    private List<BehaviorChangeInterventionPhaseInstance> phases = new ArrayList<>();

    public BehaviorChangeInterventionBlockInstance() {}

    public BehaviorChangeInterventionBlockInstance(ExecutionStatus status) {
        super(status);
    }

    public BehaviorChangeInterventionBlockInstance(ExecutionStatus status, TimeCycle stage) {
       this(status);
       this.stage = stage;
    }

    public BehaviorChangeInterventionBlockInstance(ExecutionStatus status, TimeCycle stage, List<BCIActivityInstance> activities) {
        this(status, stage);
        this.activities = activities;
    }

    public BehaviorChangeInterventionBlockInstance(ExecutionStatus status, LocalDate entryDate, LocalDate exitDate, TimeCycle stage,
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

    @Override
    public List<BCIActivityInstance> getActivities() {
        return activities;
    }

    @Override
    public void addActivity(BCIActivityInstance activityInstance) {
        if (activityInstance != null) {
            this.activities.add(activityInstance);
        }
    }

    @Override
    public void addActivities(List<BCIActivityInstance> activityInstances){
        if (activityInstances != null) {
            this.activities.addAll(activityInstances);
        }
    }

    @Override
    public boolean removeActivity(BCIActivityInstance activityInstance) {
        boolean removed = false;

        if (activities != null) {
            removed = activities.remove(activityInstance);
        }
        return removed;
    }

    public List<BehaviorChangeInterventionPhaseInstance> getPhases() {
        return phases;
    }

    public void setPhases(List<BehaviorChangeInterventionPhaseInstance> phases) {
        this.phases.addAll(phases);
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
