package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * BCIModule model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "bci_module")
@JsonPropertyOrder({"id", "name", "description", "preconditions", "postconditions", "skills"})
public class BCIModule extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bci_module_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "bci_module_name", nullable = false, length = 60)
    private String name;

    @Column(name = "bci_module_description", length = 250)
    private String description;

    @NotNull
    @Column(name = "bci_module_preconditions", nullable = false, length = 250)
    private String preconditions;

    @NotNull
    @Column(name = "bci_module_postconditions", nullable = false, length = 250)
    private String postconditions;

    @ManyToMany
    @JoinTable(name = "bci_module_skill",
            joinColumns = @JoinColumn(name = "bci_module_skill_bci_module_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_module_skill_skill_id"))
    private Set<Skill> skills = new LinkedHashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "bci_phase_contains_module",
            joinColumns = @JoinColumn(name = "bci_phase_contains_module_module_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_phase_contains_module_phase_id"))
    private Set<BehaviorChangeInterventionPhase> behaviorChangeInterventionPhases = new LinkedHashSet<>();


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPreconditions(String preconditions) {
        this.preconditions = preconditions;
    }

    public String getPreconditions() {
        return preconditions;
    }

    public void setPostconditions(String postconditions) {
        this.postconditions = postconditions;
    }

    public String getPostconditions() {
        return postconditions;
    }

    public void setSkills(Skill... skills) {
        if (skills != null && skills.length > 0) {
            this.skills.addAll(List.of(skills));
        }
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setBehaviorChangeInterventionPhases(BehaviorChangeInterventionPhase... behaviorChangeInterventionPhases) {
        if (behaviorChangeInterventionPhases != null && behaviorChangeInterventionPhases.length > 0) {
            this.behaviorChangeInterventionPhases.addAll(List.of(behaviorChangeInterventionPhases));
        }
    }

    public Set<BehaviorChangeInterventionPhase> getBehaviorChangeInterventionPhases() {
        return behaviorChangeInterventionPhases;
    }
}