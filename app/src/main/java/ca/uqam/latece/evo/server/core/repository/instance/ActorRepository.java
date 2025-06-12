package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Actor repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
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
	 * Checks if an Actor exists in the database by its email
	 * @param email String
	 * @return boolean
	 * @throws IllegalArgumentException if email is null or blank.
	 */
	boolean existsByEmail(String email);
}
