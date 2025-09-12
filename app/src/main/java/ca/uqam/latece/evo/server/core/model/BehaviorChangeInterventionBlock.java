package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A public class BehaviorChangeInterventionBlock stores information about Behavior Change Intervention Block.
 * This class is mapped to the database entity "BehaviorChangeInterventionBlock". An instance of
 * BehaviorChangeInterventionBlock consists of an ID, entry conditions, and exit conditions.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 * @author Julien Champagne
 */
@Entity
@Table(name = "behavior_change_intervention_block")
@JsonPropertyOrder({"id", "entryConditions", "exitConditions"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BehaviorChangeInterventionBlock extends AbstractEvoModel{
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "behavior_change_intervention_block_id", nullable = false)
    private Long id;

    @NotNull
    @JsonProperty("entryConditions")
    @Column(name = "behavior_change_intervention_block_entry_conditions", nullable = false, length = 256)
    private String entryConditions;

    @NotNull
    @JsonProperty("exitConditions")
    @Column(name = "behavior_change_intervention_block_exit_conditions", nullable = false, length = 256)
    private String exitConditions;

    /**
     * Represents the many-to-many relationship between BehaviorChangeInterventionBlock and
     * BehaviorChangeInterventionPhase entities.
     * This relationship is mapped to the "compose_of_phase_block" join table in the database.
     * Each BehaviorChangeInterventionBlock can have multiple associated BehaviorChangeInterventionPhase entities,
     * and each BehaviorChangeInterventionBlock can be associated with multiple BehaviorChangeInterventionPhase entities.
     * The join table "compose_of_phase_block" consists of the following columns:
     * - "compose_of_phase_block_bci_block_id" as a foreign key referencing "behavior_change_intervention_block_id"
     *    in the "behavior_change_intervention_block" table.
     * - "compose_of_phase_block_bci_phase_id" as a foreign key referencing "behavior_change_intervention_phase_id"
     *   in the "behavior_change_intervention_phase" table.
     * "FetchType.LAZY" ensures that the associated BehaviorChangeInterventionPhase entities are loaded lazily.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "compose_of_phase_block",
            joinColumns = @JoinColumn(name = "compose_of_phase_block_bci_block_id",
                    referencedColumnName= "behavior_change_intervention_block_id"),
            inverseJoinColumns = @JoinColumn(name = "compose_of_phase_block_bci_phase_id",
                    referencedColumnName="behavior_change_intervention_phase_id")
    )
    @JsonProperty("blockBehaviorChangeInterventionPhases")
    private List<BehaviorChangeInterventionPhase> blockBehaviorChangeInterventionPhases = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "bciBlockComposedOf", orphanRemoval = true, targetEntity = ComposedOf.class)
    private List<ComposedOf> composedOfList = new ArrayList<>();

    public BehaviorChangeInterventionBlock() {}

    public BehaviorChangeInterventionBlock(String entryConditions, String exitConditions) {
        this.entryConditions = entryConditions;
        this.exitConditions = exitConditions;
    }

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

    public List<BehaviorChangeInterventionPhase> getBlockBehaviorChangeInterventionPhases() {
        return blockBehaviorChangeInterventionPhases;
    }

    public void setBlockBehaviorChangeInterventionPhases(List<BehaviorChangeInterventionPhase> behaviorChangeInterventionPhase) {
        if (blockBehaviorChangeInterventionPhases != null) {
            this.blockBehaviorChangeInterventionPhases = behaviorChangeInterventionPhase;
        }
    }

    public void addBehaviorChangeInterventionPhase(BehaviorChangeInterventionPhase behaviorChangeInterventionPhase){
        if (behaviorChangeInterventionPhase != null) {
            this.blockBehaviorChangeInterventionPhases.add(behaviorChangeInterventionPhase);
        }
    }

    public void removeBehaviorChangeInterventionPhase(BehaviorChangeInterventionPhase behaviorChangeInterventionPhase){
        if (behaviorChangeInterventionPhase != null) {
            if (!this.blockBehaviorChangeInterventionPhases.isEmpty()) {
                this.blockBehaviorChangeInterventionPhases.remove(behaviorChangeInterventionPhase);
            }
        }
    }

    public List<ComposedOf> getComposedOfList() {
        return composedOfList;
    }

    public void setComposedOfList(List<ComposedOf> composedOf) {
        if (composedOf != null) {
            this.composedOfList = composedOf;
        }
    }
}