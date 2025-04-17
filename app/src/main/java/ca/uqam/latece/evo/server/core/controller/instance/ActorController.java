package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.service.instance.ActorService;
import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Actor Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@RestController
@RequestMapping("/actors")
public class ActorController extends AbstractEvoController<Actor> {
	private static final Logger logger = LoggerFactory.getLogger(ActorController.class);

	@Autowired
	private ActorService actorService;

	/**
	 * Inserts an Actor in the database.
	 * @param actor the Actor entity.
	 * @return The saved Actor.
	 * @throws IllegalArgumentException in case the given Actor is null or Actor already registered with the same email.
	 * @throws OptimisticLockingFailureException when the Actor uses optimistic locking and has a version attribute with
	 *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
	 *           present but does not exist in the database.
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) // 201
	public ResponseEntity<Actor> create(@RequestBody Actor actor) {
		ResponseEntity<Actor> response;

		try {
			Actor saved = actorService.create(actor);

			if (saved != null && saved.getId() > 0) {
				response = new ResponseEntity<>(saved, HttpStatus.CREATED);
				logger.info("Created Actor: {}", saved);
			} else {
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				logger.info("Failed to create new Actor");
			}

		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			logger.error("Failed to create new Actor. Error: {}", e.getMessage());
		}

		return response;
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
	@PutMapping
	@ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Actor> update(@RequestBody Actor actor) {
		ResponseEntity<Actor> response;

		try {
			Actor updated = actorService.update(actor);

			if (updated != null && updated.getId().equals(actor.getId())) {
				response = new ResponseEntity<>(updated, HttpStatus.OK);
				logger.info("Updated Actor: {}", updated);
			} else {
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				logger.info("Failed to update new Actor");
			}

		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			logger.error("Failed to update new Actor. Error: {}", e.getMessage());
		}

		return response;
    }

	/**
	 * Deletes the Actor with the given id.
	 * If the Actor is not found in the persistence store it is silently ignored.
	 * @param id the unique identifier of the actor to be retrieved; must not be null or invalid.
	 * @throws IllegalArgumentException in case the given id is null.
	 */
    @DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
		actorService.deleteById(id);
		logger.info("Actor deleted: {}", id);
	}

	/**
	 * Gets all actors.
	 * @return all actors.
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK) // 200
	public ResponseEntity<List<Actor>> findAll() {
		ResponseEntity<List<Actor>> response;

		try {
			List<Actor> result = actorService.findAll();

			if (result != null && !result.isEmpty()) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
				logger.info("Found all Actor entities: {}", result);
			} else {
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				logger.info("Failed to find all Actor entities");
			}

		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			logger.error("Failed to find all Actor entities. Error: {}", e.getMessage());
		}

		return response;
	}

	/**
	 * Finds an Actor by its id.
	 * @param id the unique identifier of the actor to be retrieved; must not be null or invalid.
	 * @return the Actor with the given id or Optional#empty() if none found.
	 * @throws IllegalArgumentException if id is null.
	 */
	@GetMapping("/find/{id}")
	@ResponseStatus(HttpStatus.OK) // 200
	public ResponseEntity<Actor> findById(@PathVariable Long id) {
		ResponseEntity<Actor> response;

		try {
			Actor result = actorService.findById(id);

			if (result != null && result.getId().equals(id)) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
				logger.info("Found Actor entity: {}", result);
			} else {
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				logger.info("Failed to find Actor entity");
			}

		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			logger.error("Failed to find Actor entity. Error: {}", e.getMessage());
		}

		return response;
	}
	
	/**
	 * Finds an Actor by its name.
	 * @param name must not be null.
	 * @return the Actor with the given name or Optional#empty() if none found.
	 * @throws IllegalArgumentException if the name is null.
	 */
	@GetMapping("/find/name/{name}")
	@ResponseStatus(HttpStatus.OK) // 200
	public ResponseEntity<List<Actor>> findByName(@PathVariable String name) {
		ResponseEntity<List<Actor>> response;

		try {
			List<Actor> result = actorService.findByName(name);

			if (result != null && !result.isEmpty()) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
				logger.info("Found Actor entities by name: {}", result);
			} else {
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				logger.info("Failed to find Actor entities by name");
			}

		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			logger.error("Failed to find Actor entities by name. Error: {}", e.getMessage());
		}

		return response;
	}
	
	/**
	 * Finds an Actor by its email.
	 * @param email must not be null.
	 * @return the Actor with the given email or Optional#empty() if none found.
	 * @throws IllegalArgumentException if email is null.
	 */
	@GetMapping("/find/email/{email}")
	@ResponseStatus(HttpStatus.OK) // 200
	public ResponseEntity<List<Actor>> findByEmail(@PathVariable String email) {
		ResponseEntity<List<Actor>> response;

		try {
			List<Actor> result = actorService.findByEmail(email);

			if (result != null && !result.isEmpty()) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
				logger.info("Found Actor entity by email: {}", result);
			} else {
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				logger.info("Failed to find Actor entity by email");
			}

		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			logger.error("Failed to find Actor entity by email. Error: {}", e.getMessage());
		}

		return response;
	}

	/**
	 * Finds an Actor by its contact information.
	 * @param contactInformation must not be null.
	 * @return the Actor with the given contactInformation or Optional#empty() if none found.
	 * @throws IllegalArgumentException if the contactInformation is null or blank.
	 */
	@GetMapping("/find/contactinformation/{contactInformation}")
	@ResponseStatus(HttpStatus.OK) // 200
	public ResponseEntity<List<Actor>> findByContactInformation(@PathVariable String contactInformation) {
		ResponseEntity<List<Actor>> response;

		try {
			List<Actor> result = actorService.findByContactInformation(contactInformation);

			if (result != null && !result.isEmpty()) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
				logger.info("Found Actor entities by contact information: {}", result);
			} else {
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				logger.info("Failed to find Actor entities by contact information");
			}

		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			logger.error("Failed to find Actor entities by contact information. Error: {}", e.getMessage());
		}

		return response;
	}

	/**
	 * Finds an Actor by its role id.
	 * @param id must not be null.
	 * @return the Actor with the given role id or Optional#empty() if none found.
	 * @throws IllegalArgumentException if id is null.
	 */
	@GetMapping("/find/role/{id}")
	@ResponseStatus(HttpStatus.OK) // 200
	public ResponseEntity<List<Actor>> findByRole(@PathVariable Long id) {
		ResponseEntity<List<Actor>> response;

		try {
			List<Actor> result = actorService.findByRole(id);

			if (result != null && !result.isEmpty()) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
				logger.info("Found Actor entities by roleId: {}", result);
			} else {
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				logger.info("Failed to find Actor entities by roleId.");
			}
		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			logger.error("Failed to find Actor entities by roleId. Error: {}", e.getMessage());
		}

		return response;
	}
}
