package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.service.instance.ActorService;
import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

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
import java.util.Optional;

/**
 * Actor Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@RestController
@RequestMapping("/actors")
public class ActorController extends AbstractEvoController<Actor> {
	
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
		ObjectValidator.validateObject(actor);
		return Optional.ofNullable(actorService.create(actor)).isPresent() ?
				new ResponseEntity<>(actor, HttpStatus.CREATED) :
				new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        return Optional.ofNullable(actorService.update(actor)).isPresent() ?
				new ResponseEntity<>(actor, HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
	}

	/**
	 * Gets all actors.
	 * @return all actors.
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK) // 200
	public ResponseEntity<List<Actor>> findAll() {
		return Optional.ofNullable(actorService.findAll()).isPresent() ?
				new ResponseEntity<>(actorService.findAll(), HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
		return Optional.ofNullable(actorService.findById(id)).isPresent() ?
				new ResponseEntity<>(actorService.findById(id), HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
		return Optional.ofNullable(actorService.findByName(name)).isPresent() ?
				new ResponseEntity<>(actorService.findByName(name), HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
		return Optional.ofNullable(actorService.findByEmail(email)).isPresent() ?
				new ResponseEntity<>(actorService.findByEmail(email), HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
		return Optional.ofNullable(actorService.findByRole(id)).isPresent() ?
				new ResponseEntity<>(actorService.findByRole(id), HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Finds an Actor by its contact information.
	 * @param contactInformation must not be null.
	 * @return the Actor with the given contactInformation or Optional#empty() if none found.
	 * @throws IllegalArgumentException if the contactInformation is null or blank.
	 */
	@GetMapping("/find/contactInformation/{contactInformation}")
	@ResponseStatus(HttpStatus.OK) // 200
	public ResponseEntity<List<Actor>> findByContactInformation(@PathVariable String contactInformation) {
		return Optional.ofNullable(actorService.findByContactInformation(contactInformation)).isPresent() ?
				new ResponseEntity<>(actorService.findByContactInformation(contactInformation), HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
