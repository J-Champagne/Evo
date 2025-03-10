package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A Role is associated with a unique name and may have multiple associated Actors.
 * This class is mapped to the database entity "role". An instance of Role consists of an
 * ID, a name, and a list of Actors associated with it.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "role")
@JsonPropertyOrder({"id", "name", "actors"})
public class Role extends AbstractEvoModel {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long id;

    @NotNull
    @JsonProperty("name")
    @Column(name = "role_name", nullable = false, unique = true, length = 128)
    private String name;

    @JsonProperty("actors")
    @OneToMany(mappedBy = "actorRole", orphanRemoval = true, targetEntity = Actor.class)
    private List<Actor> actors  = new ArrayList<>();

    /**
     * Represents a collection of associated BCIActivity entities linked to the Role entity
     * via a many-to-many relationship.
     * The relationship is managed on the "roleBCIActivities" side defined in the BCIActivity entity.
     * Cascade operations include PERSIST and MERGE, ensuring changes in Content
     * propagate to associated BCIActivity accordingly. The fetch type is LAZY, meaning
     * the associated BCIActivity are fetched only when explicitly accessed.
     */
    @ManyToMany(mappedBy = "roleBCIActivities",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
    private List<BCIActivity> bciActivitiesRole = new ArrayList<>();

    public Role() {}

    public Role(String name) {
        this.name = name;
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

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<BCIActivity> getBCIActivity() {
        return this.bciActivitiesRole;
    }

    public void setBCIActivity(List<BCIActivity> bciActivities) {
        if (bciActivities != null && !bciActivities.isEmpty()) {
            this.bciActivitiesRole = bciActivities;
        }
    }

    public void addBCIActivity(BCIActivity bciActivity) {
        List<BCIActivity> bciActivityList = new ArrayList<>();

        if (bciActivity != null) {
            bciActivityList.add(bciActivity);
            this.addAllBCIActivity(bciActivityList);
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

    public void removeBCIActivity(BCIActivity bciActivity) {
        List<BCIActivity> bciActivityList = new ArrayList<>();

        if (bciActivity != null) {
            bciActivityList.add(bciActivity);
            this.removeAllBCIActivity(bciActivityList);
        }
    }

}
