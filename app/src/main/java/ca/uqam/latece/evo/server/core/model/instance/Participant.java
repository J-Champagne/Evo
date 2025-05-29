package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;

import ca.uqam.latece.evo.server.core.model.Role;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "particiant")
@Transactional
public class Participant extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "participant_role_id", referencedColumnName = "role_id")
    private Role role;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "participant_actor_id", referencedColumnName = "actor_id")
    private Actor actor;

    public Participant() {}

    public Participant(@NotNull Role role, @NotNull Actor actor) {
        this.role = role;
        this.actor = actor;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
