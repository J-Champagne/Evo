package ca.uqam.latece.evo.server.core.controller;

import java.util.List;
import java.util.Optional;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionService;
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
 * BehaviorChangeIntervention Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/bahaviorchangeintervention")
public class BehaviorChangeInterventionController extends AbstractEvoController<BehaviorChangeIntervention> {

    @Autowired
    private BehaviorChangeInterventionService behaviorChangeInterventionService;

    /**
     * Creates a new behavior change intervention if it does not already exist in the repository.
     * @param intervention the behavior change intervention object to be created and saved.
     * @return the saved behavior change intervention object.
     * @throws IllegalArgumentException if the behavior change intervention name already exists or
     * if the role object or its name is null.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<BehaviorChangeIntervention> create(@RequestBody BehaviorChangeIntervention intervention) {
        ObjectValidator.validateObject(intervention);
        return Optional.ofNullable(behaviorChangeInterventionService.create(intervention)).isPresent() ?
                new ResponseEntity<>(intervention, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates an existing behavior change intervention in the repository. Validates the behavior change intervention
     * object before saving the updated behavior change intervention.
     * @param intervention the behavior change intervention object containing updated information to be saved.
     * @return the updated and saved behavior change intervention object.
     * @throws IllegalArgumentException if the behavior change intervention object is invalid or null.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeIntervention> update(@RequestBody BehaviorChangeIntervention intervention) {
        ObjectValidator.validateObject(intervention);
        return Optional.ofNullable(behaviorChangeInterventionService.update(intervention)).isPresent() ?
                new ResponseEntity<>(intervention, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a behavior change intervention from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior change intervention to be deleted.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        behaviorChangeInterventionService.deleteById(id);
    }

    /**
     * Retrieves a behavior change intervention by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the behavior change intervention
     * from the repository.
     * @param id the unique identifier of the behavior change intervention to be retrieved.
     * @return the behavior change intervention corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<BehaviorChangeIntervention> findById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        return Optional.ofNullable(behaviorChangeInterventionService.findById(id)).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionService.findById(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds a behavior change intervention by its name.
     * @param name must not be null.
     * @return the behavior change intervention with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeIntervention>> findByName(@PathVariable String name) {
        return Optional.ofNullable(behaviorChangeInterventionService.findByName(name)).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionService.findByName(name), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeIntervention with association with BehaviorChangeInterventionPhase.
     * @param id The Behavior Change Intervention Phase id.
     * @return the behavior change intervention corresponding with association with Behavior Change Intervention
     * Phase Id specified.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @GetMapping("/find/behaviorchangeinterventionphase/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeIntervention>> findByBehaviorChangeInterventionPhase(@PathVariable Long id) {
        ObjectValidator.validateObject(id);
        return Optional.ofNullable(behaviorChangeInterventionService.findByBehaviorChangeInterventionPhase(id)).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionService.findByBehaviorChangeInterventionPhase(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Gets all behavior change intervention.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeIntervention>> findAll() {
        return Optional.ofNullable(behaviorChangeInterventionService.findAll()).isPresent() ?
                new ResponseEntity<>(behaviorChangeInterventionService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}