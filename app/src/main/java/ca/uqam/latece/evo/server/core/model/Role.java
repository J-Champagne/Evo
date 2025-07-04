package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.*;

/**
 * A Role model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "role")
@JsonPropertyOrder({"id", "name","description"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @JsonProperty("description")
    @Column(name = "role_description", nullable = true, length = 250)
    private String description;

    @ManyToMany(mappedBy = "parties", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<BCIActivity> bciActivities = new ArrayList<>();

    @JsonProperty("interactionInitiator")
    @OneToMany(mappedBy = "interactionInitiatorRole", orphanRemoval = true, targetEntity = Interaction.class,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Interaction> interactionInitiator = new ArrayList<>();


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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List<BCIActivity> getBCIActivity() {
        return this.bciActivities;
    }

    public void setBCIActivity(List<BCIActivity> bciActivities) {
        if (bciActivities != null && !bciActivities.isEmpty()) {
            this.bciActivities = bciActivities;
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

    public List<Interaction> getInteractionInitiator() {
        return interactionInitiator;
    }

    public void setInteractionInitiator(List<Interaction> interactionInitiator) {
        this.interactionInitiator = interactionInitiator;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            Role role = (Role) object;
            return Objects.equals(this.getName(), role.getName()) &&
                    Objects.equals(this.getDescription(), role.getDescription()) &&
                    Objects.equals(this.getBCIActivity(), role.getBCIActivity()) &&
                    Objects.equals(this.getInteractionInitiator(), role.getInteractionInitiator());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName(), this.getDescription(), this.getBCIActivity(),
                this.getInteractionInitiator());
    }

}
