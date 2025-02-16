package ca.uqam.latece.evo.server.core.model;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a Skill entity having a unique ID, name, description, and type.
 * The Skill entity is associated with a collection of Content entities through
 * a many-to-many relationship.
 * This class is mapped to the "skill" table in the database with columns for:
 * - `skill_id` (primary key)
 * - `skill_name` (name of the skill, must be unique and is mandatory)
 * - `skill_description` (optional description of the skill)
 * - `skill_type` (type of the skill)
 * - skill_skill_id (Auto relationship for skill table. Foreign key to identify a sub-skill.)
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "skill")
public class Skill extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="skill_id")
    private Long id;

    @Column(name = "skill_name", nullable = false, unique = true, length = 128)
    private String name;

    @Column(name = "skill_description", nullable = true, length = 256)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_type", nullable = false)
    private SkillType type;

    /**
     * Auto relationship for skill table. Foreign key to identify a sub-skill.
     */
   // @Column(name = "skill_skill_id", nullable = true)
   // private Skill requiredSkill;

    /**
     * Represents the many-to-many relationship between Skill and Content entities.
     * This relationship is mapped to the "skill_content" join table in the database.
     * Each Skill can have multiple associated Content entities, and each Content can be associated with multiple Skill entities.
     * The join table "skill_content" consists of the following columns:
     * - "skill_content_skill_id" as a foreign key referencing "skill_id" in the "skill" table.
     * - "skill_content_content_id" as a foreign key referencing "content_id" in the "content" table.
     * "FetchType.LAZY" ensures that the associated Content entities are loaded lazily.
     */
    @ManyToMany(
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "skill_content",
            joinColumns = @JoinColumn(name = "skill_content_skill_id", referencedColumnName="skill_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_content_content_id", referencedColumnName="content_id"))
    private List<Content> contents = new ArrayList<>();

    public Skill() {}

    /**
     * Constructs a new Skill instance with the specified parameters.
     * @param name the name of the skill.
     * @param description a brief description of the skill.
     * @param type the type of the skill.
     */
    public Skill(String name, String description, SkillType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SkillType getType() {
        return type;
    }

    public void setType(SkillType type) {
        this.type = type;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public void addContent(Content content) {
        this.contents.add(content);
    }

    public void removeContent(Content content) {
        this.contents.remove(content);
    }

    @Override
    public String toString() {
        StringBuilder json = new StringBuilder();
        json.append("{\"id\":").
                append(this.getId()).
                append(",\"name\":\"").
                append(this.getName()).
                append("\",\"description\":\"").
                append(this.getDescription()).
                append("\",\"type\":\"").
                append(this.getType()).
                append("\",\"contents\":").
                append("[");

        if (this.contents != null && !this.contents.isEmpty()) {
            // Starts the content collection.
            for (Content content : this.contents){
                json.append("{\"id\":").
                        append(content.getId()).
                        append(",\"name\":\"").
                        append(content.getName()).
                        append("\",\"description\":\"").
                        append(content.getDescription()).
                        append("\",\"type\":\"").
                        append(content.getType()).
                        append("\"},");
            }
            // Remover the last virgule.
            json.setLength(json.length() - 1);
        }

        return json.append("]}").toString();
    }
}
