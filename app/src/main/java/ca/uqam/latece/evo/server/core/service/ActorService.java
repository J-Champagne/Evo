package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import ca.uqam.latece.evo.server.core.model.Actor;
import ca.uqam.latece.evo.server.core.repository.ActorRepository;

/**
 * Actor Service.
 * @since 22.01.2025.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
public class ActorService extends AbstractEvoService<Actor> {
	
	@Autowired
	private ActorRepository actorRepository;
	
	/**
	 * Inserts an Actor in the database.
	 * @param actor
	 * @return The saved Actor.
	 * @throws IllegalArgumentException in case the given Actor is null.
	 * @throws OptimisticLockingFailureException when the Actor uses optimistic locking and has a version attribute with
	 *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
	 *           present but does not exist in the database.
	 */
	@Override
	public Actor create(Actor actor) {
		return this.save(actor);
	}
	
	/**
	 * Update an Actor in the database.
	 * @param actor
	 * @return The updated Actor.
	 * @throws IllegalArgumentException in case the given Actor is null.
	 * @throws OptimisticLockingFailureException when the Actor uses optimistic locking and has a version attribute with
	 *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
	 *           present but does not exist in the database.
	 */
	@Override
	public Actor update(Actor actor) {
		return this.save(actor);
	}
	
	/**
	 * Method used to creates or update an Actor.
	 * @param actor
	 * @return The Actor.
	 */
	private Actor save(Actor actor) {
		ObjectValidator.validateObject(actor);
		ObjectValidator.validateString(actor.getName());
		ObjectValidator.validateEmail(actor.getEmail());
		return actorRepository.save(actor);
	}

	@Override
	public boolean existsById(Long id) {
		return this.actorRepository.existsById(id);
	}
	
	public boolean existsByName(String name) {
		return this.actorRepository.existsByName(name);
	}

	/**
	 * Deletes the Actor with the given id.
	 * <p>
	 * If the Actor is not found in the persistence store it is silently ignored.
	 *
	 * @param id the unique identifier of the actor to be retrieved; must not be null or invalid.
	 * @throws IllegalArgumentException in case the given id is null.
	 */
	@Override
	public void deleteById(Long id) {
		ObjectValidator.validateId(id);
		actorRepository.deleteById(id);
	}
	
	/**
	 * Gets all Actor.
	 * @return all Actors.
	 */
	@Override
	public List<Actor> findAll() {
		return actorRepository.findAll().stream().toList();
	}
	
	/**
	 * Finds an Actor by its id.
	 * @param id the unique identifier of the actor to be retrieved; must not be null or invalid.
	 * @return the Actor with the given id or Optional#empty() if none found.
	 * @throws IllegalArgumentException if id is null.
	 */
	@Override
	public Actor findById(Long id) {
		ObjectValidator.validateId(id);
		return actorRepository.findById(id).get();
	}

	/**
	 * Finds an actor by its id.
	 * @param id the unique identifier of the actor to be retrieved; must not be null or invalid.
	 * @return an Optional containing the Actor if found, or an empty Optional if no actor exists with the given id
	 */
	public Optional<Actor> findActorById(Long id){
		ObjectValidator.validateId(id);
		return actorRepository.findById(id).isPresent() ? actorRepository.findById(id) : Optional.empty();
	}
	
	/**
	 * Finds an Actor by its name.
	 * @param name must not be null.
	 * @return the Actor with the given id or Optional#empty() if none found.
	 * @throws IllegalArgumentException if name is null.
	 */
	public List<Actor> findByName(String name){
		ObjectValidator.validateString(name);
		return actorRepository.findByName(name);
	} 
	
	/**
	 * Finds an Actor by its email.
	 * @param email must not be null.
	 * @return the Actor with the given id or Optional#empty() if none found.
	 * @throws IllegalArgumentException if email is null.
	 */
	public List<Actor> findByEmail(String email){
		ObjectValidator.validateEmail(email);
		return actorRepository.findByEmail(email);
	}

}
