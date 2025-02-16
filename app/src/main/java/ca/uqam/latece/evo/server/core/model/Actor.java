package ca.uqam.latece.evo.server.core.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


/**
 * Actor model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "actor")
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

	public Actor(String name, String email) {
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
	public String toString() {
		return "{\"id\":" + this.id + ",\"name\":\"" + this.name + "\",\"email\":\"" + this.email +
					"\",\"role\":" + (this.actorRole != null ? "{\"id\":" + actorRole.getId() + ",\"name\":\"" +
				actorRole.getName() + "\"}" : "null") + "}";
	}

}