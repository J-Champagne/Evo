package ca.uqam.latece.evo.server.core.controller;

import java.util.List;
import java.util.Optional;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionPhaseService;
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
 * BehaviorChangeInterventionPhase Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/bahaviorchangeinterventionphase")
public class BehaviorChangeInterventionPhaseController extends AbstractEvoController<BehaviorChangeInterventionPhase>  {
    @Autowired
    private BehaviorChangeInterventionPhaseService behaviorChangeInterventionPhaseService;

    /**
     * Creates a new behavior change intervention phase if it does not already exist in the repository.
     * @param intervention the behavior change intervention phase object to be created and saved.
     * @return the saved behavior change intervention object.
     * @throws IllegalArgumentException if the behavior change phase intervention name already exists or
     * if the role object or its name is null.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<BehaviorChangeInterventionPhase> create(@RequestBody BehaviorChangeInterventionPhase intervention) {
        ObjectValidator.validateObject(intervention);
        return Optional.ofNullable(behaviorChangeInterventionPhaseService.create(intervention)).isPresent() ?
                new ResponseEntity<>(intervention, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates an existing behavior change intervention phase in the repository. Validates a behavior
     * change intervention phase object before saving the updated behavior change intervention phase.
     * @param intervention the behavior change intervention phase object containing updated information to be saved.
     * @return the updated and saved behavior change intervention phase object.
     * @throws IllegalArgumentException if the behavior change intervention phase object is invalid or null.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeInterventionPhase> update(@RequestBody BehaviorChangeInterventionPhase intervention) {
        ObjectValidator.validateObject(intervention);
        return Optional.ofNullable(behaviorChangeInterventionPhaseService.update(intervention)).isPresent() ?
                new ResponseEntity<>(intervention, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a behavior change intervention phase from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior change intervention phase to be deleted.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        behaviorChangeInterventionPhaseService.deleteById(id);
    }

    /**
     * Retrieves a behavior change intervention phase by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the behavior change intervention phase
     * from the repository.
     * @param id the unique identifier of the behavior change intervention phase to be retrieved.
     * @return the behavior change intervention phase corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<BehaviorChangeInterventionPhase> findById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        return Optional.ofNullable(behaviorChangeInterventionPhaseService.findById(id)).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionPhaseService.findById(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a Behavior Change Intervention Phase by entry conditions.
     * @param entry the entry condition of the behavior change intervention phase to be retrieved.
     * @return the behavior change intervention phase corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided entry conditions are null or invalid.
     */
    @GetMapping("/find/entrycond/{entry}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionPhase>> findByEntryConditions(@PathVariable String entry) {
        ObjectValidator.validateString(entry);
        return Optional.ofNullable(behaviorChangeInterventionPhaseService.findByEntryConditions(entry)).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionPhaseService.findByEntryConditions(entry), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a BehaviorChangeInterventionPhase by exit conditions.
     * @param exit the exit condition of the behavior change intervention phase to be retrieved.
     * @return the behavior change intervention phase corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided exitConditions is null or invalid.
     */
    @GetMapping("/find/exitcond/{exit}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionPhase>> findByExitConditions(@PathVariable String exit) {
        ObjectValidator.validateString(exit);
        return Optional.ofNullable(behaviorChangeInterventionPhaseService.findByExitConditions(exit)).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionPhaseService.findByExitConditions(exit), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionPhase with association with BehaviorChangeIntervention.
     * @param id The Behavior Change Intervention id.
     * @return the behavior change intervention phase corresponding with association with Behavior Change Intervention
     * Id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @GetMapping("/find/behaviorchangeintervention/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionPhase>> findByBehaviorChangeInterventionId(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        return Optional.ofNullable(behaviorChangeInterventionPhaseService.findByBehaviorChangeInterventionId(id)).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionPhaseService.findByBehaviorChangeInterventionId(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionPhase with association with BehaviorChangeInterventionBlock.
     * @param id The Behavior Change Intervention Block id.
     * @return the behavior change intervention phase corresponding with association with Behavior Change Intervention
     * Block Id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @GetMapping("/find/behaviorchangeinterventionblock/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionPhase>> findByBehaviorChangeInterventionBlockId(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        return Optional.ofNullable(behaviorChangeInterventionPhaseService.findByBehaviorChangeInterventionBlockId(id)).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionPhaseService.findByBehaviorChangeInterventionBlockId(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Gets all behavior change intervention phase.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionPhase>> findAll() {
        return Optional.ofNullable(behaviorChangeInterventionPhaseService.findAll()).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionPhaseService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}