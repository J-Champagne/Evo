package ca.uqam.latece.evo.server.core.model;

import jakarta.persistence.*;

/**
 * BehaviorPerformance model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "behavior_performance")
@PrimaryKeyJoinColumn(name="behavior_performance_id", referencedColumnName = "bci_activity_id") // Foreign key to bci_activity table used to represent the super class BCIActivity in the database.
public class BehaviorPerformance extends BCIActivity {

}
