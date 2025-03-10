package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;


/**
 * Actor model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "actor")
@Transactional
@JsonPropertyOrder({"id", "name", "email", "role"})
public class Actor extends AbstractEvoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="actor_id")
	private Long id;

	@NotNull
	@Column(name = "actor_name", nullable = false, length = 256)
	private String name;

	@NotNull
	@Email
	@Column(name = "actor_email", nullable = false, unique = true, length = 256)
	private String email;

	@ManyToOne
	@JoinColumn(name = "actor_role_id", referencedColumnName = "role_id") // Ensures foreign key setup in the database
	private Role actorRole;


	public Actor() {}

	public Actor(@NotNull String name, @NotNull String email) {
		this.name = name;
		this.email = email;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {		  
	   this.id = id;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public String getEmail() {
	    return email;
	}

	public void setEmail(String email) {
	    this.email = email;
	}

	public Role getRole() {
		return actorRole;
	}

	public void setRole(Role actorRole) {
		this.actorRole = actorRole;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		Actor actor = (Actor) object;
		return Objects.equals(this.getId(), actor.getId()) &&
			   Objects.equals(this.getName(), actor.getName()) &&
			   Objects.equals(this.getEmail(), actor.getEmail());
	}

}