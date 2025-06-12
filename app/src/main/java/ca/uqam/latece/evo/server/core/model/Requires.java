package ca.uqam.latece.evo.server.core.model;

import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Requires model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "requires")
@JsonPropertyOrder({"id", "level"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Requires extends AbstractEvoModel {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="requires_id")
    private Long id;

    @NotNull
    @JsonProperty("level")
    @Enumerated(EnumType.STRING)
    @Column(name = "requires_level", nullable = false)
    private SkillLevel level;

    @NotNull
    @JsonProperty("role")
    @OneToOne
    @JoinColumn(name = "requires_role_id", referencedColumnName = "role_id", nullable = false)
    private Role role;

    @NotNull
    @JsonProperty("skill")
    @OneToOne
    @JoinColumn(name = "requires_skill_id", referencedColumnName = "skill_id", nullable = false)
    private Skill skill;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "requires_bci_activity_id", referencedColumnName = "bci_activity_id", nullable = false) // Ensures foreign key setup in the database
    private BCIActivity bciActivityRequires;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRole(Role role) {
       this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }
    
    public void setLevel(SkillLevel skillLevel) {
        this.level = skillLevel;
    }

    public SkillLevel getLevel() {
        return level;
    }

    public void setBciActivity(BCIActivity bciActivity) {
        this.bciActivityRequires = bciActivity;
    }

    public BCIActivity getBciActivity() {
        return this.bciActivityRequires;
    }

}
