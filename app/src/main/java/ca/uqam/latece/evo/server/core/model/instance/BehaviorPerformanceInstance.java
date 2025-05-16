package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.BehaviorPerformance;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;

@Entity
@Table(name = "behavior_performance_instance")
@PrimaryKeyJoinColumn(name="behavior_performance_instance_id", referencedColumnName = "bci_activity_instance_id") // Foreign key to bci_activity_instance table used to represent the super class BCIActivityInstance in the database.
@JsonPropertyOrder({"id", "status", "entryDate", "exitDate", "behaviorPerformance"})
public class BehaviorPerformanceInstance extends BCIActivityInstance {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "behavior_performance_instance_behavior_performance_id", nullable = false)
    private BehaviorPerformance behaviorPerformance;

    public void setBehaviorPerformance(BehaviorPerformance behaviorPerformance) {
        this.behaviorPerformance = behaviorPerformance;
    }

    public BehaviorPerformance getBehaviorPerformance() {
        return behaviorPerformance;
    }
}
