package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BehaviorChangeInterventionBlock instance class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "bci_block_instance")
@JsonPropertyOrder({"id", "stage"})
public class BehaviorChangeInterventionBlockInstance extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bci_block_instance_id")
    private Long id;

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
    private List<BCIActivityInstance> activities;

    public BehaviorChangeInterventionBlockInstance() {
        activities = new ArrayList<>();
    }

    public BehaviorChangeInterventionBlockInstance(TimeCycle stage) {
        this.stage = stage;
        this.activities = new ArrayList<>();
    }

    public BehaviorChangeInterventionBlockInstance(TimeCycle stage, List<BCIActivityInstance> activities) {
        this.stage = stage;
        this.activities = activities;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
        return Objects.hash(this.getId(), this.getStage(), this.getActivities());
    }
}
