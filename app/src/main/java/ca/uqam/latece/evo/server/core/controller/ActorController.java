package ca.uqam.latece.evo.server.core.controller;

import java.util.List;
import java.util.Optional;

import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
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

import ca.uqam.latece.evo.server.core.service.ActorService;
import ca.uqam.latece.evo.server.core.model.Actor;


/**
 * Actor Controller.
 * @since 22.01.2025.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/actors")
public class ActorController extends AbstractEvoController<Actor> {
	
	@Autowired
	private ActorService actorService;

	/**
	 * Creates an Actor in the database.
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
	 */
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Actor> update(@RequestBody Actor actor) {
        return Optional.ofNullable(actorService.update(actor)).isPresent() ?
				new ResponseEntity<>(actor, HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } 

	/**
	 * Delete an Actor.
	 */
    @DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
		actorService.deleteById(id);
	}
	
	/**
	 * Gets all actors.
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Actor>> findAll() {
		return Optional.ofNullable(actorService.findAll()).isPresent() ?
				new ResponseEntity<>(actorService.findAll(), HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Finds an Actor by its id.
	 */
	@GetMapping("/find/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Actor> findById(@PathVariable Long id) {
		return Optional.ofNullable(actorService.findById(id)).isPresent() ?
				new ResponseEntity<>(actorService.findById(id), HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Finds an Actor by its name.
	 * @param name must not be null.
	 * @return the Actor with the given id or Optional#empty() if none found.
	 * @throws IllegalArgumentException if name is null.
	 */
	@GetMapping("/find/name/{name}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Actor>> findByName(@PathVariable String name) {
		return Optional.ofNullable(actorService.findByName(name)).isPresent() ?
				new ResponseEntity<>(actorService.findByName(name), HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Finds an Actor by its email.
	 * @param email must not be null.
	 * @return the Actor with the given id or Optional#empty() if none found.
	 * @throws IllegalArgumentException if email is null.
	 */
	@GetMapping("/find/email/{email}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Actor>>  findByEmail(@PathVariable String email) {
		return Optional.ofNullable(actorService.findByEmail(email)).isPresent() ?
				new ResponseEntity<>(actorService.findByEmail(email), HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
