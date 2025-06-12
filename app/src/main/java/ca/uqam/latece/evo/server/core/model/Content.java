package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Content model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "content")
@JsonPropertyOrder({"id", "name", "description", "type"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Content extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="content_id")
    private Long id;

    @NotNull
    @Column(name = "content_name", nullable = false, length = 128)
    private String name;

    @NotNull
    @Column(name = "content_description", nullable = false, length = 256)
    private String description;

    @NotNull
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
    @JsonProperty("skill")
    private List<Skill> skills = new ArrayList<>();

    /**
     * Represents a collection of associated BCIActivity entities linked to the Content entity
     * via a many-to-many relationship.
     * The relationship is managed on the "contentBCIActivities" side defined in the BCIActivity entity.
     * Cascade operations include PERSIST and MERGE, ensuring changes in Content
     * propagate to associated BCIActivity accordingly. The fetch type is LAZY, meaning
     * the associated BCIActivity are fetched only when explicitly accessed.
     */
    @ManyToMany(mappedBy = "contentBCIActivities",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    private List<BCIActivity> bciActivitiesContent = new ArrayList<>();

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

    public List<BCIActivity> getBCIActivity() {
        return this.bciActivitiesContent;
    }

    public void addBCIActivity(BCIActivity bciActivity) {
        List<BCIActivity> bciActivityList = new ArrayList<>();

        if (bciActivity != null) {
            bciActivityList.add(bciActivity);
            this.addAllBCIActivity(bciActivityList);
        }
    }

    public void removeBCIActivity(BCIActivity bciActivity) {
        List<BCIActivity> bciActivityList = new ArrayList<>();

        if (bciActivity != null) {
            bciActivityList.add(bciActivity);
            this.removeAllBCIActivity(bciActivityList);
        }
    }

    public void addAllBCIActivity(List<BCIActivity> bciActivity) {
        if (bciActivity != null && !bciActivity.isEmpty()) {
            this.getBCIActivity().addAll(bciActivity);
        }
    }

    public void removeAllBCIActivity(List<BCIActivity> bciActivity) {
        if (bciActivity != null && !bciActivity.isEmpty()) {
            if (!this.getBCIActivity().isEmpty()) {
                this.getBCIActivity().removeAll(bciActivity);
            }
        }
    }

}
