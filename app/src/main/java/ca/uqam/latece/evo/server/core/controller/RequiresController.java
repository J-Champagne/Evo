package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.Requires;
import ca.uqam.latece.evo.server.core.service.RequiresService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
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

import java.util.List;
import java.util.Optional;

/**
 * Requires Controller.
 * @since 22.01.2025.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/requires")
public class RequiresController extends AbstractEvoController <Requires> {
    @Autowired
    private RequiresService requiresService;

    /**
     * Inserts a Requires in the database.
     * @param requires The Requires entity.
     * @return The saved Requires.
     * @throws IllegalArgumentException in case the given Requires is null.
     * @throws OptimisticLockingFailureException when the Requires uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Requires> create(@RequestBody Requires requires) {
        ObjectValidator.validateObject(requires);
        return Optional.ofNullable(requiresService.create(requires)).isPresent() ?
                new ResponseEntity<>(requires, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Update a Requires in the database.
     * @param requires The Requires entity.
     * @return The saved Requires.
     * @throws IllegalArgumentException in case the given Requires is null.
     * @throws OptimisticLockingFailureException when the Requires uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Requires> update (@RequestBody Requires requires) {
        ObjectValidator.validateObject(requires);
        return Optional.ofNullable(requiresService.update(requires)).isPresent() ?
                new ResponseEntity<>(requires, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes the Requires with the given id.
     * <p>
     * If the Requires is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Requires to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id){
        requiresService.deleteById(id);
    }

    /**
     * Gets all Requires.
     * @return all Requires.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Requires>> findAll() {
        return Optional.ofNullable(requiresService.findAll()).isPresent() ?
                new ResponseEntity<>(requiresService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

     /**
     * Retrieves a requires by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the role from the repository.
     * @param id the unique identifier of the requires to be retrieved.
     * @return the requires corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     * @throws EntityNotFoundException if the requires not found.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Requires> findById(@PathVariable Long id) {
        return Optional.ofNullable(requiresService.findById(id)).isPresent() ?
                new ResponseEntity<>(requiresService.findById(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of Requires entities that match the specified Role Id.
     * @param id The Role Id to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified Role id, or an empty list if no matches are found.
     */
    @GetMapping("/find/roleid/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Requires>> findByRoleId(@PathVariable Long id) {
        return Optional.ofNullable(requiresService.findByRoleId(id)).isPresent() ?
                new ResponseEntity<>(requiresService.findByRoleId(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of Requires entities that match the specified Skill Id.
     * @param id The Skill Id to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified Skill id, or an empty list if no matches are found.
     */
    @GetMapping("/find/skillid/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Requires>> findBySkillId(@PathVariable Long id) {
        return Optional.ofNullable(requiresService.findBySkillId(id)).isPresent() ?
                new ResponseEntity<>(requiresService.findBySkillId(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of Requires entities that match the specified BCI Activity Id.
     * @param id The BCI Activity Id to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    @GetMapping("/find/bciactivityid/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Requires>> findByBCIActivityId(@PathVariable Long id) {
        return Optional.ofNullable(requiresService.findByBCIActivityId(id)).isPresent() ?
                new ResponseEntity<>(requiresService.findByBCIActivityId(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
