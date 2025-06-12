package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.repository.instance.ActorRepository;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Actor Service.
 * @since 22.01.2025.
 * @version 1.0
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@Service
@Transactional
public class ActorService extends AbstractEvoService<Actor> {
	private static final Logger logger = LoggerFactory.getLogger(ActorService.class);
	private static final String ERROR_EMAIL_ALREADY_REGISTERED = "Actor already registered with the same email!";

	@Autowired
	private ActorRepository actorRepository;
	
	/**
	 * Creates an Actor in the database.
	 * @param actor Actor.
	 * @return The created Actor.
	 * @throws IllegalArgumentException if an actor is null or if another Actor was saved with the same email.
	 */
	@Override
	public Actor create(Actor actor) {
		Actor actorSaved;

		ObjectValidator.validateObject(actor);
		ObjectValidator.validateEmail(actor.getEmail());
		ObjectValidator.validateString(actor.getName());
		ObjectValidator.validateString(actor.getContactInformation());

		// The email should be unique.
		if (existsByEmail(actor.getEmail())) {
			throw this.createDuplicateActorException(actor);
		} else {
			actorSaved = this.save(actor);
			logger.info("Actor created: {}", actorSaved);
		}

		return actorSaved;
	}
	
	/**
	 * Updates an Actor in the database.
	 * @param actor Actor.
	 * @return The updated Actor.
	 * @throws IllegalArgumentException if an actor is null or if another Actor was saved with the same email.
	 */
	@Override
	public Actor update(Actor actor) {
		Actor actorUpdated;
		Actor actorFound = this.findById(actor.getId());

		ObjectValidator.validateObject(actor);
		ObjectValidator.validateEmail(actor.getEmail());
		ObjectValidator.validateString(actor.getName());
		ObjectValidator.validateString(actor.getContactInformation());

		// The email should be the same or unique.
		if (actorFound.getEmail().equalsIgnoreCase(actor.getEmail())) {
			actorUpdated = this.save(actor);
		} else {
			if (existsByEmail(actor.getEmail())) {
				throw this.createDuplicateActorException(actor);
			} else {
				actorUpdated = this.save(actor);
			}
		}

		logger.info("Actor updated: {}", actorUpdated);
		return actorUpdated;
	}

	/**
	 * Saves the given Actor in the database.
	 * @param actor Actor.
	 * @return The saved Actor.
	 * @throws IllegalArgumentException if an actor is null or if another Actor was saved with the same email.
	 */
	@Override
	@Transactional
	protected Actor save(Actor actor) {
		return this.actorRepository.save(actor);
	}

	/**
	 * Deletes an Actor by its id.
	 * Silently ignored if not found.
	 * @param id Long.
	 * @throws IllegalArgumentException if id is null.
	 */
	@Override
	public void deleteById(Long id) {
		ObjectValidator.validateId(id);
		actorRepository.deleteById(id);
		logger.info("Actor deleted: {}", id);
	}
	
	/**
	 * Finds all Actor entities.
	 * @return List of Actor.
	 */
	@Override
	public List<Actor> findAll() {
		return actorRepository.findAll();
	}
	
	/**
	 * Finds an Actor by its id.
	 * @param id The Actor id.
	 * @return Actor with the given id.
	 * @throws IllegalArgumentException if id is null.
	 */
	@Override
	public Actor findById(Long id) {
		ObjectValidator.validateId(id);
		return actorRepository.findById(id).
				orElseThrow(() -> new EntityNotFoundException("Actor not found!"));
	}

	/**
	 * Finds Actor entities by their name.
	 * @param name The Actor name.
	 * @return Actor with the given name.
	 * @throws IllegalArgumentException if name is null or blank.
	 */
	public List<Actor> findByName(String name){
		ObjectValidator.validateString(name);
		return actorRepository.findByName(name);
	}

	/**
	 * Finds an Actor by its email.
	 * @param email The Actor email.
	 * @return Actor with the given email.
	 * @throws IllegalArgumentException if email is null or blank.
	 */
	public Actor findByEmail(String email){
		ObjectValidator.validateEmail(email);
		return actorRepository.findByEmail(email);
	}

	/**
	 * Finds Actor entities by their contactInformation.
	 * @param contactInformation The Actor contact information.
	 * @return List of Actor with the given contactInformation.
	 * @throws IllegalArgumentException if contactInformation is null or blank.
	 */
	public List<Actor> findByContactInformation(String contactInformation) {
		ObjectValidator.validateString(contactInformation);
		return this.actorRepository.findByContactInformation(contactInformation);
	}

	/**
	 * Checks if an Actor exists in the database by its id
	 * @param id the Actor id.
	 * @return true if exists, otherwise false.
	 * @throws IllegalArgumentException if id is null.
	 */
	@Override
	public boolean existsById(Long id) {
		ObjectValidator.validateId(id);
		return actorRepository.existsById(id);
	}

	/**
	 * Checks if an Actor exists in the database by its email
	 * @param email The Actor email.
	 * @return true if exists, otherwise false.
	 * @throws IllegalArgumentException if email is null or blank.
	 */
	public boolean existsByEmail(String email) {
		ObjectValidator.validateEmail(email);
		return actorRepository.existsByEmail(email);
	}

	/**
	 * Creates an IllegalArgumentException with a message indicating that an Actor with the same email was found.
	 * @param actor Actor.
	 * @return IllegalArgumentException indicating that an Actor with the same email was found.
	 */
	private IllegalArgumentException createDuplicateActorException(Actor actor) {
		IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_EMAIL_ALREADY_REGISTERED +
				" Actor Name: " + actor.getName() +
				". Actor Email: " + actor.getEmail());
		logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
		return illegalArgumentException;
	}
}
