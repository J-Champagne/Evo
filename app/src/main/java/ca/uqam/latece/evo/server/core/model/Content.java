package ca.uqam.latece.evo.server.core.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Content model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "content")
public class Content extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="content_id")
    private Long id;

    @Column(name = "content_name", nullable = false, length = 128)
    private String name;

    @Column(name = "content_description", nullable = false, length = 256)
    private String description;

    @Column(name = "content_type", nullable = false, length = 256)
    private String type;

    /**
     * Represents a collection of associated Skill entities linked to the Content entity
     * via a many-to-many relationship.
     * The relationship is managed on the "contents" side defined in the Skill entity.
     * Cascade operations include PERSIST and MERGE, ensuring changes in Content
     * propagate to associated Skills accordingly. The fetch type is LAZY, meaning
     * the associated Skills are fetched only when explicitly accessed.
     */
    @ManyToMany(mappedBy = "contents",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    private List<Skill> skills = new ArrayList<>();

    public Content() {}

    public Content(String name, String description, String type, List<Skill> skills) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.skills = skills;
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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void addSkill(Skill skill) {
        if (skill != null) {
            this.skills.add(skill);
        }
    }

    public void removeSkill(Skill skill) {
        if (skill != null) {
            this.skills.remove(skill);
        }
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    /**
     * Method used at @ContentControllertest.class to @ContentController.class.
     * @return
     */
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
                append("\",\"skills\":").
                append("[");

        if (this.getSkills() != null) {
            // Starts the skill collection.
            for (Skill skill : this.getSkills()) {
                if (skill != null) {
                    json.append("{\"id\":").
                            append(skill.getId()).
                            append(",\"name\":\"").
                            append(skill.getName()).
                            append("\",\"description\":\"").
                            append(skill.getDescription()).
                            append("\",\"type\":\"").
                            append(skill.getType()).
                            append("\"},");
                }
            }

            // Remover the last virgule.
            json.setLength(json.length() - 1);
        }

        return json.append("]}").toString();
    }
}
