package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.service.instance.ActorService;
import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;

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
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@RestController
@RequestMapping("/actor")
public class ActorController extends AbstractEvoController<Actor> {
	private static final Logger logger = LoggerFactory.getLogger(ActorController.class);

	@Autowired
	private ActorService actorService;

	/**
	 * Creates an Actor in the database.
	 * @param actor Actor.
	 * @return The created Actor in JSON format.
	 * @throws IllegalArgumentException if actor is null or if another Actor was saved with the same email.
	 * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
	 *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
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
	 * @param actor Actor.
	 * @return The updated Actor in JSON format.
	 * @throws IllegalArgumentException if actor is null or if another Actor was saved with the same email.
	 * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
	 *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
	 */
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
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
	 * Deletes an Actor by its id.
	 * Silently ignored if not found.
	 * @param id Long.
	 * @throws IllegalArgumentException if id is null.
	 */
    @DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
		actorService.deleteById(id);
		logger.info("Actor deleted: {}", id);
	}

	/**
	 * Finds all Actor entities.
	 * @return List<Actor> in JSON format.
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
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
	 * @param id Long.
	 * @return Actor in JSON format.
	 * @throws IllegalArgumentException if id is null.
	 */
	@GetMapping("/find/{id}")
	@ResponseStatus(HttpStatus.OK)
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
	 * Finds Patient entities by their name.
	 * @param name String.
	 * @return List<Actor> in JSON format.
	 * @throws IllegalArgumentException if contactInformation is null or blank.
	 */
	@GetMapping("/find/name/{name}")
	@ResponseStatus(HttpStatus.OK)
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
	 * @param email String.
	 * @return Actor in JSON format.
	 * @throws IllegalArgumentException if email is null or blank.
	 */
	@GetMapping("/find/email/{email}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Actor> findByEmail(@PathVariable String email) {
		ResponseEntity<Actor> response;

		try {
			Actor result = actorService.findByEmail(email);

			if (result != null) {
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
	 * Finds Actor entities by their contact information.
	 * @param contactInformation String.
	 * @return List<Actor> in JSON format.
	 * @throws IllegalArgumentException if contactInformation is null or blank.
	 */
	@GetMapping("/find/contactinformation/{contactInformation}")
	@ResponseStatus(HttpStatus.OK)
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
	 * Finds Actor entities by their role.
	 * @param role Role.
	 * @return List<Actor> in JSON format.
	 * @throws IllegalArgumentException if role is null.
	 */
	@GetMapping("/find/role")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Actor>> findByRole(@RequestBody Role role) {
		ResponseEntity<List<Actor>> response;

		try {
			List<Actor> result = actorService.findByRole(role);

			if (result != null && !result.isEmpty()) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
				logger.info("Found Actor entities by Role: {}", result);
			} else {
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				logger.info("Failed to find Actor entities by Role.");
			}
		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			logger.error("Failed to find Actor entities by Role. Error: {}", e.getMessage());
		}

		return response;
	}

	/**
	 * Finds Actor entities by their role id.
	 * @param id Long.
	 * @return List<Actor> in JSON format.
	 * @throws IllegalArgumentException if id is null.
	 */
	@GetMapping("/find/role/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Actor>> findByRole(@PathVariable Long id) {
		ResponseEntity<List<Actor>> response;

		try {
			List<Actor> result = actorService.findByRoleId(id);

			if (result != null && !result.isEmpty()) {
				response = new ResponseEntity<>(result, HttpStatus.OK);
				logger.info("Found Actor entities by Role id: {}", result);
			} else {
				response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
				logger.info("Failed to find Actor entities by Role id.");
			}
		} catch (Exception e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			logger.error("Failed to find Actor entities by Role id. Error: {}", e.getMessage());
		}

		return response;
	}
}
