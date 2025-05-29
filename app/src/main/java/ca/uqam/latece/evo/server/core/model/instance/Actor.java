package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import ca.uqam.latece.evo.server.core.model.Role;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Actor instance class.
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@Entity
@Table(name = "actor")
@Inheritance(strategy = InheritanceType.JOINED)
@Transactional
@JsonPropertyOrder({"id", "name", "email", "contactInformation", "role"})
public class Actor extends AbstractEvoModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="actor_id")
	private Long id;

	@NotNull
	@Column(name = "actor_name", nullable = false)
	private String name;

	@NotNull
	@Email
	@Column(name = "actor_email", nullable = false, unique = true)
	private String email;

	@NotNull
	@Column(name = "actor_contact_information", nullable = false)
	private String contactInformation;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "actor_role_id", referencedColumnName = "role_id")
	private Role role;

	public Actor() {}

	public Actor(@NotNull String name, @NotNull String email, @NotNull String contactInformation, @NotNull Role role) {
		this.name = name;
		this.email = email;
		this.contactInformation = contactInformation;
		this.role = role;
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

	public String getContactInformation() {
		return this.contactInformation;
	}

	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		Actor actor = (Actor) object;

		return  Objects.equals(this.getName(), actor.getName()) &&
				Objects.equals(this.getEmail(), actor.getEmail()) &&
				Objects.equals(this.getContactInformation(), actor.getContactInformation()) &&
				Objects.equals(this.getRole(), actor.getRole());
	}
}