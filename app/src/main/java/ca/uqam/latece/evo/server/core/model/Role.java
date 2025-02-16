package ca.uqam.latece.evo.server.core.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * A Role is associated with a unique name and may have multiple associated Actors.
 * This class is mapped to the database entity "role". An instance of Role consists of an
 * ID, a name, and a list of Actors associated with it.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "role")
public class Role extends AbstractEvoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true, length = 128)
    private String name;

    @OneToMany(mappedBy = "actorRole", orphanRemoval = true, targetEntity = Actor.class)
    private List<Actor> actors;

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

    /**
     * Converts the Role object into its JSON string representation.
     * The JSON includes the Role's ID, name, and a list of associated actors
     * (each actor represented as a JSON object with its own ID, name, and email).
     * If the list of actors is null, an empty array is included in the JSON.
     *
     * @return A string representing the Role object in JSON format.
     */
    @Override
    public String toString() {
        StringBuilder roleJson = new StringBuilder();
        roleJson.append("{\"id\":").
                append(this.id).
                append(",\"name\":\"").
                append(this.name).
                append("\",\"actors\":").
                append("[");

        if (this.actors != null) {
            // Starts the actor collection.
            for (Actor actor : this.actors) {
                roleJson.append("{\"id\":").
                        append(actor.getId()).
                        append(",\"name\":\"").
                        append(actor.getName()).
                        append(",\"email\":\"").
                        append(actor.getEmail()).
                        append("\"},");
            }
            // Remover the last virgule.
            roleJson.setLength(roleJson.length() - 1);
        }

        return roleJson.append("]}").toString();
    }
}
