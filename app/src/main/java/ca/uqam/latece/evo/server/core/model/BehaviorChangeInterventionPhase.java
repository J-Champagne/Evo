package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * A Behavior Change Intervention Phase stores information about behavior change intervention phase
 * program. This class is mapped to the database entity "BehaviorChangeInterventionPhase".
 * An instance of BehaviorChangeIntervention consists of an ID, entry conditions, exit conditions,
 * behavior change intervention id, and association with behavior change intervention block using the
 * join table compose_of_phase_block.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "behavior_change_intervention_phase")
@JsonPropertyOrder({"id", "entryConditions", "exitConditions"})
public class BehaviorChangeInterventionPhase extends AbstractEvoModel {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "behavior_change_intervention_phase_id", nullable = false)
    private Long id;

    @Size(max = 256)
    @NotNull
    @JsonProperty("entryConditions")
    @Column(name = "behavior_change_intervention_phase_entry_conditions", nullable = false, length = 256)
    private String entryConditions;

    @Size(max = 256)
    @NotNull
    @JsonProperty("exitConditions")
    @Column(name = "behavior_change_intervention_phase_exit_conditions", nullable = false, length = 256)
    private String exitConditions;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "behavior_change_intervention_phase_bci_id", nullable = true)
    private BehaviorChangeIntervention behaviorChangeInterventionPhaseBci;

    /**
     * Represents a collection of associated BehaviorChangeInterventionBlock entities linked to the BehaviorChangeInterventionPhase entity
     * via a many-to-many relationship.
     * The relationship is managed on the "blockBehaviorChangeInterventionPhases" side defined in the BehaviorChangeInterventionBlock entity.
     * Cascade operations include PERSIST and MERGE, ensuring changes in BehaviorChangeInterventionPhase
     * propagate to associated BehaviorChangeInterventionBlock accordingly. The fetch type is LAZY, meaning
     * the associated BehaviorChangeInterventionBlock are fetched only when explicitly accessed.
     */
    @ManyToMany(mappedBy = "blockBehaviorChangeInterventionPhases",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BehaviorChangeInterventionBlock> behaviorChangeInterventionBlocks = new ArrayList<>();;


    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getEntryConditions() {
        return entryConditions;
    }

    public void setEntryConditions(String entryConditions) {
        this.entryConditions = entryConditions;
    }

    public String getExitConditions() {
        return exitConditions;
    }

    public void setExitConditions(String exitConditions) {
        this.exitConditions = exitConditions;
    }

    public BehaviorChangeIntervention getBehaviorChangeIntervention() {
        return behaviorChangeInterventionPhaseBci;
    }

    public void setBehaviorChangeIntervention(BehaviorChangeIntervention behaviorChangeIntervention) {
        this.behaviorChangeInterventionPhaseBci = behaviorChangeIntervention;
    }

    public List<BehaviorChangeInterventionBlock> getBehaviorChangeInterventionBlocks() {
        return behaviorChangeInterventionBlocks;
    }

    public void setBehaviorChangeInterventionBlocks(List<BehaviorChangeInterventionBlock> behaviorChangeInterventionBlocks) {
        if (behaviorChangeInterventionBlocks != null) {
            this.behaviorChangeInterventionBlocks = behaviorChangeInterventionBlocks;
        }
    }

    public void addBehaviorChangeInterventionBlock(BehaviorChangeInterventionBlock behaviorChangeInterventionBlock) {
        if (this.behaviorChangeInterventionBlocks != null) {
            this.behaviorChangeInterventionBlocks.add(behaviorChangeInterventionBlock);
        }
    }

    public void removeBehaviorChangeInterventionBlock(BehaviorChangeInterventionBlock behaviorChangeInterventionBlock) {
        if (this.behaviorChangeInterventionBlocks != null) {
            if (!this.behaviorChangeInterventionBlocks.isEmpty()) {
                this.behaviorChangeInterventionBlocks.remove(behaviorChangeInterventionBlock);
            }
        }
    }
}