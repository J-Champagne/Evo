package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.repository.instance.ActorRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Actor Service.
 * @version 1.0
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@Service
@Transactional
public class ActorService extends AbstractEvoService<Actor> {
	private static final Logger logger = LoggerFactory.getLogger(ActorService.class);
	private static final String ERROR_EMAIL_ALREADY_REGISTERED = "Actor already registered with the same email!";

	@Autowired
	private ActorRepository actorRepository;
	
	/**
	 * Inserts an Actor in the database.
	 * @param actor the Actor entity.
	 * @return The saved Actor.
	 * @throws IllegalArgumentException in case the given Actor is null or Actor already registered with the same email.
	 * @throws OptimisticLockingFailureException when the Actor uses optimistic locking and has a version attribute with
	 *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
	 *           present but does not exist in the database.
	 */
	@Override
	public Actor create(Actor actor) {
		Actor actorCreated = null;

		ObjectValidator.validateObject(actor);
		ObjectValidator.validateString(actor.getName());
		ObjectValidator.validateEmail(actor.getEmail());
		ObjectValidator.validateString(actor.getContactInformation());

		// The email should be unique.
		if (this.existsByEmail(actor.getEmail())) {
			throw this.createDuplicateActorException(actor);
		} else {
			actorCreated = this.save(actor);
			logger.info("Actor created: {}", actorCreated);
		}

		return actorCreated;
	}

	/**
	 * Create duplicate Actor Exception.
	 * @param actor the Actor entity.
	 * @return an exception object.
	 */
	private IllegalArgumentException createDuplicateActorException(Actor actor) {
		IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_EMAIL_ALREADY_REGISTERED +
				" Actor Name: " + actor.getName() +
				". Actor Email: " + actor.getEmail());
		logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
		return illegalArgumentException;
	}
	
	/**
	 * Updates an Actor in the database.
	 * @param actor the Actor entity.
	 * @return The updated Actor.
	 * @throws IllegalArgumentException in case the given Actor is null or Actor already registered with the same email.
	 * @throws OptimisticLockingFailureException when the Actor uses optimistic locking and has a version attribute with
	 *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
	 *           present but does not exist in the database.
	 */
	@Override
	public Actor update(Actor actor) {
		Actor actorUpdated = null;
		Actor actorFound = this.findById(actor.getId());

		ObjectValidator.validateObject(actor);
		ObjectValidator.validateString(actor.getName());
		ObjectValidator.validateEmail(actor.getEmail());
		ObjectValidator.validateString(actor.getContactInformation());
		
		if (actorFound.getEmail().equalsIgnoreCase(actor.getEmail())) {
			actorUpdated = this.save(actor);
		} else {
			List<Actor> actorEmailFound = this.findByEmail(actor.getEmail());

			// The email should be unique.
			if (actorEmailFound.isEmpty()) {
				actorUpdated = this.save(actor);
			} else {
				throw this.createDuplicateActorException(actor);
			}
		}

		logger.info("Actor updated: {}", actorUpdated);
		return actorUpdated;
	}

	/**
	 * Method used to create or update an Actor.
	 * @param actor the Actor entity.
	 * @return The inserted or updated Actor.
	 * @throws IllegalArgumentException in case the given Actor is null or Actor already registered with the same email.
	 * @throws OptimisticLockingFailureException when the Actor uses optimistic locking and has a version attribute with
	 *          a different value from that found in the persistence store. Also thrown if the entity is assumed to be
	 *          present but does not exist in the database.
	 */
	@Transactional
	protected Actor save(Actor actor) {
		return this.actorRepository.save(actor);
	}

	/**
	 * Checks if an Actor entity with the specified id exists in the repository.
	 * @param id the id of the Actor to check for existence, must not be null.
	 * @return true if an Actor with the specified id exists, false otherwise.
	 * @throws IllegalArgumentException if the id is null.
	 */
	@Override
	public boolean existsById(Long id) {
		ObjectValidator.validateId(id);
		return this.actorRepository.existsById(id);
	}

	/**
	 * Checks if an Actor entity with the specified name exists in the repository.
	 * @param name the name of the Actor to check for existence, must not be null.
	 * @return true if an Actor with the specified name exists, false otherwise.
	 * @throws IllegalArgumentException if the name is null.
	 */
	public boolean existsByName(String name) {
		ObjectValidator.validateString(name);
		return this.actorRepository.existsByName(name);
	}

	/**
	 * Checks if an Actor entity with the specified email exists in the repository.
	 * @param email the email of the Actor to check for existence, must not be null.
	 * @return true if an Actor with the specified email exists, false otherwise.
	 * @throws IllegalArgumentException if the email is null.
	 */
	public boolean existsByEmail(String email) {
		ObjectValidator.validateString(email);
		return this.actorRepository.existsByEmail(email);
	}

	/**
	 * Checks if an Actor entity with the specified contact information exists in the repository.
	 * @param contactInformation the contactInformation of the Actor to check for existence, must not be null.
	 * @return true if an Actor with the specified contactInformation exists, false otherwise.
	 * @throws IllegalArgumentException if the contactInformation is null or blank.
	 */
	public boolean existsByContactInformation(String contactInformation) {
		ObjectValidator.validateString(contactInformation);
		return this.actorRepository.existsByContactInformation(contactInformation);
	}

	/**
	 * Deletes the Actor with the given id.
	 * If the Actor is not found in the persistence store it is silently ignored.
	 * @param id the unique identifier of the Actor to be retrieved; must not be null or invalid.
	 * @throws IllegalArgumentException in case the given id is null.
	 */
	@Override
	public void deleteById(Long id) {
		ObjectValidator.validateId(id);
		actorRepository.deleteById(id);
		logger.info("Actor deleted: {}", id);
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
		return actorRepository.findById(id).
				orElseThrow(() -> new EntityNotFoundException("Actor not found!"));
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
	 * @return the Actor with the given name or Optional#empty() if none found.
	 * @throws IllegalArgumentException if the name is null.
	 */
	public List<Actor> findByName(String name){
		ObjectValidator.validateString(name);
		return actorRepository.findByName(name);
	} 
	
	/**
	 * Finds an Actor by its email.
	 * @param email must not be null.
	 * @return the Actor with the given email or Optional#empty() if none found.
	 * @throws IllegalArgumentException if email is null.
	 */
	public List<Actor> findByEmail(String email){
		ObjectValidator.validateEmail(email);
		return actorRepository.findByEmail(email);
	}

	/**
	 * Retrieves a list of Actor entities that match the specified contactInformation.
	 * @param contactInformation must not be null or blank.
	 * @return List<Actor> with the given contactInformation or Optional#empty() if none found.
	 * @throws IllegalArgumentException if contactInformation is null or blank.
	 */
	public List<Actor> findByContactInformation(String contactInformation) {
		ObjectValidator.validateString(contactInformation);
		return this.actorRepository.findByContactInformation(contactInformation);
	}

	/**
	 * Retrieves a list of Actor entities that match the specified Role Id.
	 * @param roleId The Role Id to filter Develops entities by, must not be null.
	 * @return List<Actor> that have the specified Role id, or an empty list if no matches are found.
	 */
	public List<Actor> findByRole(Long roleId) {
		ObjectValidator.validateId(roleId);
		return actorRepository.findByRole(roleId);
	}
}
