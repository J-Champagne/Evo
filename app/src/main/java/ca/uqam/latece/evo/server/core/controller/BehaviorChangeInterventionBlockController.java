package ca.uqam.latece.evo.server.core.controller;

import java.util.List;
import java.util.Optional;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionBlockService;
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

/**
 * BehaviorChangeInterventionBlock Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/behaviorchangeinterventionblock")
public class BehaviorChangeInterventionBlockController extends AbstractEvoController<BehaviorChangeInterventionBlock> {
    @Autowired
    private BehaviorChangeInterventionBlockService behaviorChangeInterventionBlockService;

    /**
     * Creates a new behavior change intervention block if it does not already exist in the repository.
     * @param intervention the behavior change intervention object to be created and saved.
     * @return the saved behavior change intervention object.
     * @throws IllegalArgumentException if the behavior change block intervention name already exists or
     * if the role object or its name is null.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<BehaviorChangeInterventionBlock> create(@RequestBody BehaviorChangeInterventionBlock intervention) {
        ObjectValidator.validateObject(intervention);
        return Optional.ofNullable(behaviorChangeInterventionBlockService.create(intervention)).isPresent() ?
                new ResponseEntity<>(intervention, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates an existing behavior change intervention block in the repository. Validates a behavior
     * change intervention block object before saving the updated behavior change intervention block.
     * @param intervention the behavior change intervention block object containing updated information to be saved.
     * @return the updated and saved behavior change intervention block object.
     * @throws IllegalArgumentException if the behavior change intervention block object is invalid or null.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeInterventionBlock> update(@RequestBody BehaviorChangeInterventionBlock intervention) {
        ObjectValidator.validateObject(intervention);
        return Optional.ofNullable(behaviorChangeInterventionBlockService.update(intervention)).isPresent() ?
                new ResponseEntity<>(intervention, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a behavior change intervention block from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior change intervention block to be deleted.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        behaviorChangeInterventionBlockService.deleteById(id);
    }

    /**
     * Retrieves a behavior change intervention block by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the behavior change intervention block
     * from the repository.
     * @param id the unique identifier of the behavior change intervention block to be retrieved.
     * @return the behavior change intervention block corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<BehaviorChangeInterventionBlock> findById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        return Optional.ofNullable(behaviorChangeInterventionBlockService.findById(id)).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionBlockService.findById(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a Behavior Change Intervention Block by entry conditions.
     * @param entry the entry condition of the behavior change intervention block to be retrieved.
     * @return the behavior change intervention block corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided entry conditions are null or invalid.
     */
    @GetMapping("/find/entrycond/{entry}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionBlock>> findByEntryConditions(@PathVariable String entry) {
        ObjectValidator.validateString(entry);
        return Optional.ofNullable(behaviorChangeInterventionBlockService.findByEntryConditions(entry)).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionBlockService.findByEntryConditions(entry), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a BehaviorChangeInterventionBlock by exit conditions.
     * @param exit the exit condition of the behavior change intervention block to be retrieved.
     * @return the behavior change intervention block corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided exitConditions is null or invalid.
     */
    @GetMapping("/find/exitcond/{exit}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionBlock>> findByExitConditions(@PathVariable String exit) {
        ObjectValidator.validateString(exit);
        return Optional.ofNullable(behaviorChangeInterventionBlockService.findByExitConditions(exit)).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionBlockService.findByExitConditions(exit), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionBlock with association with BehaviorChangeInterventionPhase.
     * @param id The Behavior Change Intervention Phase id.
     * @return the behavior change intervention phase corresponding with association with Behavior Change Intervention
     * Phase Id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @GetMapping("/find/behaviorchangeinterventionphase/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionBlock>>findByBehaviorChangeInterventionPhaseId(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        return Optional.ofNullable(behaviorChangeInterventionBlockService.findByBehaviorChangeInterventionPhaseId(id)).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionBlockService.findByBehaviorChangeInterventionPhaseId(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Gets all behavior change intervention block.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionBlock>> findAll() {
        return Optional.ofNullable(behaviorChangeInterventionBlockService.findAll()).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionBlockService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}