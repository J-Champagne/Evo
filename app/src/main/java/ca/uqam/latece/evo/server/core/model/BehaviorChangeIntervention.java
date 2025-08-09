package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A Behavior Change Intervention stores information about behavior change intervention program.
 * This class is mapped to the database entity "BehaviorChangeIntervention". An instance of BehaviorChangeIntervention
 * consists of an ID, and a name.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "behavior_change_intervention")
@JsonPropertyOrder({"id", "name"})
public class BehaviorChangeIntervention extends AbstractEvoModel {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "behavior_change_intervention_id", nullable = false)
    private Long id;

    @NotNull
    @JsonProperty("name")
    @Column(name = "behavior_change_intervention_name", nullable = false, unique = true, length = 256)
    private String name;

    @OneToMany(mappedBy = "behaviorChangeInterventionPhaseBci")
    private List<BehaviorChangeInterventionPhase> behaviorChangeInterventionPhases = new ArrayList<>();

    public BehaviorChangeIntervention() {}

    public BehaviorChangeIntervention(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BehaviorChangeInterventionPhase> getBehaviorChangeInterventionPhases() {
        return behaviorChangeInterventionPhases;
    }

    public void setBehaviorChangeInterventionPhases(List<BehaviorChangeInterventionPhase> behaviorChangeInterventionPhases) {
        if (behaviorChangeInterventionPhases != null) {
            this.behaviorChangeInterventionPhases = behaviorChangeInterventionPhases;
        }
    }

    public void addBehaviorChangeInterventionPhase(BehaviorChangeInterventionPhase phase) {
        if (behaviorChangeInterventionPhases != null) {
            this.behaviorChangeInterventionPhases.add(phase);
        }
    }

    public void removeBehaviorChangeInterventionPhase(BehaviorChangeInterventionPhase phase) {
        if (behaviorChangeInterventionPhases != null) {
            if (!behaviorChangeInterventionPhases.isEmpty() && phase != null) {
                this.behaviorChangeInterventionPhases.remove(phase);
            }
        }
    }
}