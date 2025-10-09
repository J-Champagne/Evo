package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

/**
 * Actor instance class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "actor")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonPropertyOrder({"id", "name", "email", "contactInformation"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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


	public Actor() {}

	public Actor(@NotNull String name, @NotNull String email, @NotNull String contactInformation) {
		this.name = name;
		this.email = email;
		this.contactInformation = contactInformation;
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


	@Override
	public boolean equals(Object object) {
		if (super.equals(object)) {
			Actor actor = (Actor) object;
			return Objects.equals(this.getName(), actor.getName()) &&
					Objects.equals(this.getEmail(), actor.getEmail()) &&
					Objects.equals(this.getContactInformation(), actor.getContactInformation());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getId(), this.getName(), this.getEmail(), this.getContactInformation());
	}
}