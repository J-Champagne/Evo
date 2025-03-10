package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.model.Develops;
import ca.uqam.latece.evo.server.core.service.DevelopsService;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
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

/**
 * Develops Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/develops")
public class DevelopsController extends AbstractEvoController<Develops> {
    @Autowired
    private DevelopsService developsService;

    /**
     * Inserts a Develops in the database.
     * @param develops the Develops entity.
     * @return The saved Develops.
     * @throws IllegalArgumentException in case the given Develops is null.
     * @throws OptimisticLockingFailureException when the Develops uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Develops> create(@RequestBody Develops develops) {
        return Optional.ofNullable(developsService.create(develops)).isPresent() ?
                new ResponseEntity<>(develops, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Update a Develops in the database.
     * @param develops the Develops entity.
     * @return The saved Develops.
     * @throws IllegalArgumentException in case the given Develops is null.
     * @throws OptimisticLockingFailureException when the Develops uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Develops> update(@RequestBody Develops develops) {
        return Optional.ofNullable(developsService.update(develops)).isPresent() ?
                new ResponseEntity<>(develops, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes the Develops with the given id.
     * If the Develops is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Develops to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        developsService.deleteById(id);
    }

    /**
     * Gets all Develops.
     * @return all Develops.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Develops>> findAll() {
        return Optional.ofNullable(developsService.findAll()).isPresent() ?
                new ResponseEntity<>(developsService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a Develops by its id.
     * @param id The Content Id to filter Develops entities by, must not be null.
     * @return the Develops with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     * @throws EntityNotFoundException in case the given Develops not found.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Develops> findById(@PathVariable Long id) {
        return Optional.ofNullable(developsService.findById(id)).isPresent() ?
                new ResponseEntity<>(developsService.findById(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of Develops entities that match the specified skill level.
     * @param level the SkillLevel to filter Develops entities by, must not be null.
     * @return a list of Develops entities that have the specified skill level, or an empty list if no matches are found.
     */
    @GetMapping("/find/level/{level}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Develops>> findByLevel(@PathVariable SkillLevel level){
        return Optional.ofNullable(developsService.findByLevel(level)).isPresent() ?
                new ResponseEntity<>(developsService.findByLevel(level), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of Develops entities that match the specified Role Id.
     * @param id The Role Id to filter Develops entities by, must not be null.
     * @return a list of Develops entities that have the specified Role id, or an empty list if no matches are found.
     */
    @GetMapping("/find/roleid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Develops>> findByRoleId(@PathVariable Long id) {
        return Optional.ofNullable(developsService.findByRoleId(id)).isPresent() ?
                new ResponseEntity<>(developsService.findByRoleId(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of Develops entities that match the specified Skill Id.
     * @param id The Skill Id to filter Develops entities by, must not be null.
     * @return a list of Develops entities that have the specified Skill id, or an empty list if no matches are found.
     */
    @GetMapping("/find/skillid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Develops>> findBySkillId(@PathVariable Long id) {
        return Optional.ofNullable(developsService.findBySkillId(id)).isPresent() ?
                new ResponseEntity<>(developsService.findBySkillId(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of Develops entities that match the specified BCI Activity Id.
     * @param id The BCI Activity Id to filter Develops entities by, must not be null.
     * @return a list of Develops entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    @GetMapping("/find/bciactivityid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Develops>> findByBCIActivityId(@PathVariable Long id) {
        return Optional.ofNullable(developsService.findByBCIActivityId(id)).isPresent() ?
                new ResponseEntity<>(developsService.findByBCIActivityId(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
