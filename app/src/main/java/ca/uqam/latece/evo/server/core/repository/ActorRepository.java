package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.model.Actor;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Actor repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface ActorRepository extends EvoRepository<Actor> {
	/**
	 * Finds a list of Actor entities by their name.
	 * @param name the name of the Actor to search for.
	 * @return the Actor with the given name or Optional#empty() if none found.
	 * @throws IllegalArgumentException if name is null.
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
	 * Checks if an Actor entity with the specified name exists in the repository.
	 * @param name the name of the Actor to check for existence, must not be null.
	 * @return true if an Actor with the specified name exists, false otherwise.
	 * @throws IllegalArgumentException if the name is null.
	 */
	boolean existsByName(String name);
}
