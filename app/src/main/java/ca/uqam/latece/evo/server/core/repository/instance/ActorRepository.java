package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Actor repository creates CRUD implementation at runtime automatically.
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@Repository
public interface ActorRepository extends EvoRepository<Actor> {
	/**
	 * Finds Actor entities by their name.
	 * @param name String.
	 * @return Actor with the given name.
	 * @throws IllegalArgumentException if name is null or blank.
	 */
	List<Actor> findByName(String name);

	/**
	 * Finds an Actor by its email.
	 * @param email String.
	 * @return Actor with the given email.
	 * @throws IllegalArgumentException if email is null or blank.
	 */
	Actor findByEmail(String email);

	/**
	 * Finds Actor entities by their contactInformation.
	 * @param contactInformation String.
	 * @return List<Actor> with the given contactInformation.
	 * @throws IllegalArgumentException if contactInformation is null or blank.
	 */
	List<Actor> findByContactInformation(String contactInformation);

	/**
	 * Finds Actor entities by their Role.
	 * @param role Role.
	 * @return List<Actor> with the specified Role.
	 * @throws IllegalArgumentException if role is null.
	 */
	List<Actor> findByRole (Role role);

	/**
	 * Finds Actor entities by their Role id.
	 * @param id Long.
	 * @return List<Actor> with the specified Role id.
	 * @throws IllegalArgumentException if id is null.
	 */
	List<Actor> findByRoleId (Long id);

	/**
	 * Checks if an Actor exists in the database by its email
	 * @param email String
	 * @return boolean
	 * @throws IllegalArgumentException if email is null or blank.
	 */
	boolean existsByEmail(String email);
}
