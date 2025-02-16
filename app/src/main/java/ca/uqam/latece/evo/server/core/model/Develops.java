package ca.uqam.latece.evo.server.core.model;

import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import jakarta.persistence.*;

/**
 * Develops model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "develops")
public class Develops extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="develops_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "develops_level", nullable = false)
    private SkillLevel level;

    @OneToOne
    @JoinColumn(name = "develops_role_id", referencedColumnName = "role_id")
    private Role role;

    @OneToOne
    @JoinColumn(name = "develops_skill_id", referencedColumnName = "skill_id")
    private Skill skill;

    public Develops() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public SkillLevel getLevel() {
        return level;
    }

    public void setLevel(SkillLevel skillLevel) {
        this.level = skillLevel;
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

    /**
     * Converts the current Develops object into its JSON string representation.
     * The JSON includes the object's id, level, role, and skill attributes.
     * If the role or skill is not set, it represents them as empty.
     * @return A JSON string representation of the Develops object.
     */
    @Override
    public String toString() {
        StringBuilder roleJson = new StringBuilder();
        roleJson.append("{\"id\":").
                append(this.id).
                append(",\"level\":\"").
                append(this.level).
                append("\",\"role\":");

        if (this.role != null) {
            // Starts the actor collection.
            roleJson.append("[").
                    append("{\"id\":").
                        append(this.role.getId()).
                        append(",\"name\":\"").
                        append(this.role.getName()).
                        append("\"}]");
        } else {
            roleJson.append("[]");
        }

        if (this.skill != null) {
            roleJson.append(",\"skill\":[").
                    append("{\"id\":").
                    append(this.skill.getId()).
                    append(",\"name\":\"").
                    append(this.skill.getName()).
                    append("\"}]");
        } else {
            roleJson.append(",\"skill\":[]");
        }

        return roleJson.append("}").toString();
    }
}
