package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Actor repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@Repository
public interface ActorRepository extends EvoRepository<Actor> {
	/**
	 * Finds a list of Actor entities by their name.
	 * @param name the name of the Actor to search for.
	 * @return the Actor with the given name or Optional#empty() if none found.
	 * @throws IllegalArgumentException if the name is null.
	 */
	List<Actor> findByName(String name);

	/**
	 * Finds an Actor by its email.
	 * @param email must not be null.
	 * @return the Actor with the given id or Optional#empty() if none found.
	 * @throws IllegalArgumentException if email is null.
	 */
	List<Actor> findByEmail(String email);

	/**
	 * Finds a list of Actor entities by their contact information.
	 * @param contactInformation must not be null.
	 * @return the Actor with the given id or Optional#empty() if none found.
	 * @throws IllegalArgumentException if contactInformation is null.
	 */
	List<Actor> findByContactInformation(String contactInformation);

	/**
	 * Retrieves a list of Actor entities that match the specified Role Id.
	 * @param roleId The Role Id to filter Develops entities by, must not be null.
	 * @return a list of Actor entities that have the specified Role id, or an empty list if no matches are found.
	 */
	@Query(value = "SELECT ac.*, hcp.*, 0 AS clazz_ FROM actor AS ac " +
			"LEFT OUTER JOIN healthcareprofessional hcp ON (ac.actor_id = hcp.healthcareprofessional_actor_id) " +
			"WHERE ac.actor_role_id = :role_id",
			nativeQuery = true)
	List<Actor> findByRole (@Param("role_id") Long roleId);

	/**
	 * Checks if an Actor entity with the specified name exists in the repository.
	 * @param name the name of the Actor to check for existence, must not be null.
	 * @return true if an Actor with the specified name exists, false otherwise.
	 * @throws IllegalArgumentException if the name is null.
	 */
	boolean existsByName(String name);

	/**
	 * Checks if an Actor entity with the specified email exists in the repository.
	 * @param email the email of the Actor to check for existence, must not be null.
	 * @return true if an Actor with the specified email exists, false otherwise.
	 * @throws IllegalArgumentException if the email is null.
	 */
	boolean existsByEmail(String email);

	/**
	 * Checks if an Actor entity with the specified contact information exists in the repository.
	 * @param contactInformation the contact information of the Actor to check for existence, must not be null.
	 * @return true if an Actor with the specified contact information exists, false otherwise.
	 * @throws IllegalArgumentException if the contactInformation is null.
	 */
	boolean existsByContactInformation(String contactInformation);
}
