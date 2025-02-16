package ca.uqam.latece.evo.server.core.model;

import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import jakarta.persistence.*;

/**
 * Requires model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "requires")
public class Requires extends AbstractEvoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="requires_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "requires_level", nullable = false)
    private SkillLevel level;

    @OneToOne
    @JoinColumn(name = "requires_role_id", referencedColumnName = "role_id")
    private Role role;

    @OneToOne
    @JoinColumn(name = "requires_skill_id", referencedColumnName = "skill_id")
    private Skill skill;

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

}
