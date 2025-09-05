package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.interfaces.ProcessInstance;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bci_block_instance_behavior_change_intervention_block_id", referencedColumnName = "behavior_change_intervention_block_id",
            nullable = false)
    private BehaviorChangeInterventionBlock behaviorChangeInterventionBlock;

    @ManyToMany (mappedBy = "activities")
    private List<BehaviorChangeInterventionPhaseInstance> phases = new ArrayList<>();

    public BehaviorChangeInterventionBlockInstance() {}

    public BehaviorChangeInterventionBlockInstance(@NotNull ExecutionStatus status) {
        super(status);
    }

    public BehaviorChangeInterventionBlockInstance(@NotNull ExecutionStatus status,
                                                   LocalDate entryDate,
                                                   LocalDate exitDate) {
        super(status, entryDate, exitDate);
    }

    public BehaviorChangeInterventionBlockInstance(@NotNull ExecutionStatus status,
                                                   @NotNull TimeCycle stage,
                                                   @NotNull BehaviorChangeInterventionBlock bciBlock) {
       this(status);
       this.stage = stage;
       this.behaviorChangeInterventionBlock = bciBlock;
    }

    public BehaviorChangeInterventionBlockInstance(@NotNull ExecutionStatus status,
                                                   @NotNull TimeCycle stage,
                                                   @NotNull List<BCIActivityInstance> activities,
                                                   @NotNull BehaviorChangeInterventionBlock bciBloc) {
        this(status, stage, bciBloc);
        this.addActivities(activities);
    }

    public BehaviorChangeInterventionBlockInstance(@NotNull ExecutionStatus status,
                                                   LocalDate entryDate,
                                                   LocalDate exitDate,
                                                   @NotNull TimeCycle stage,
                                                   @NotNull List<BCIActivityInstance> activities,
                                                   @NotNull BehaviorChangeInterventionBlock bciBloc) {
        super(status, entryDate, exitDate);
        this.stage = stage;
        this.addActivities(activities);
        this.behaviorChangeInterventionBlock = bciBloc;
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

    public BehaviorChangeInterventionBlock getBehaviorChangeInterventionBlock() {
        return this.behaviorChangeInterventionBlock;
    }

    public void setBehaviorChangeInterventionBlock(BehaviorChangeInterventionBlock behaviorChangeInterventionBlock) {
        this.behaviorChangeInterventionBlock = behaviorChangeInterventionBlock;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            BehaviorChangeInterventionBlockInstance bciBlockInstance = (BehaviorChangeInterventionBlockInstance) object;
            return Objects.equals(this.getStage(), bciBlockInstance.getStage()) &&
                    Objects.equals(this.getActivities(), bciBlockInstance.getActivities()) &&
                    Objects.equals(this.getBehaviorChangeInterventionBlock(), bciBlockInstance.getBehaviorChangeInterventionBlock());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getStage(), this.getActivities(), this.getBehaviorChangeInterventionBlock());
    }
}
