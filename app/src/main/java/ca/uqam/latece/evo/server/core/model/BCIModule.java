package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BCIModule extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bci_module_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "bci_module_name", nullable = false, length = 128)
    private String name;

    @Column(name = "bci_module_description", length = 256)
    private String description;

    @NotNull
    @Column(name = "bci_module_preconditions", nullable = false, length = 256)
    private String preconditions;

    @NotNull
    @Column(name = "bci_module_postconditions", nullable = false, length = 256)
    private String postconditions;

    @ManyToMany
    @JoinTable(name = "bci_module_skill",
            joinColumns = @JoinColumn(name = "bci_module_skill_bci_module_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_module_skill_skill_id"))
    private Set<Skill> skills = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "bci_phase_contains_module",
            joinColumns = @JoinColumn(name = "bci_phase_contains_module_module_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_phase_contains_module_phase_id"))
    private Set<BehaviorChangeInterventionPhase> behaviorChangeInterventionPhases = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "composedActivityBciModule", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = ModuleComposedActivity.class)
    private Set<ModuleComposedActivity> moduleComposedActivities = new LinkedHashSet<>();


    public BCIModule() {}

    public BCIModule(@NotNull String name, @NotNull String preconditions, @NotNull String postconditions, @NotNull Set<Skill> skills, @NotNull ModuleComposedActivity... moduleComposedActivity) {
        this.name = name;
        this.preconditions = preconditions;
        this.postconditions = postconditions;
        this.addSkills(skills);
        this.setModuleComposedActivities(moduleComposedActivity);
    }

    private void addSkills(Set<Skill> skills) {
        if (skills.isEmpty()) {
            throw new IllegalArgumentException("The Module '" + name +
                    "' needs to be associated with one or more Skills!");
        } else {
            for (Skill skill : skills) {
                if (skill == null) {
                    throw new IllegalArgumentException("The Module '" + name +
                            "' has been associated with a Skill null!");
                } else {
                    if (skill.getId() == null) {
                        throw new IllegalArgumentException("The Module '" + name +
                                "' has been associated with a Skill that has a null ID");
                    } else {
                        this.skills.add(skill);
                    }
                }
            }
        }
    }


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

    public Set<ModuleComposedActivity> getModuleComposedActivities() {
        return moduleComposedActivities;
    }

    public void setModuleComposedActivities(ModuleComposedActivity... moduleComposedActivities) {
        if (moduleComposedActivities != null && moduleComposedActivities.length > 0) {
            this.moduleComposedActivities.addAll(List.of(moduleComposedActivities));
        }
    }
}