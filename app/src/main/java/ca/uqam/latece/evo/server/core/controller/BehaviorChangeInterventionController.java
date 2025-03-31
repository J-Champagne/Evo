package ca.uqam.latece.evo.server.core.controller;

import java.util.List;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeIntervention;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(BehaviorChangeInterventionController.class);

    @Autowired
    private BehaviorChangeInterventionService behaviorChangeInterventionService;

    /**
     * Creates a new behavior change intervention if it does not already exist in the repository.
     * @param intervention the behavior change intervention object to be created and saved.
     * @return the saved behavior change intervention object.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<BehaviorChangeIntervention> create(@RequestBody BehaviorChangeIntervention intervention) {
        ResponseEntity<BehaviorChangeIntervention> response;

        try {
            ObjectValidator.validateObject(intervention);
            BehaviorChangeIntervention saved = behaviorChangeInterventionService.create(intervention);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new Behavior Change Intervention: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new Behavior Change Intervention.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new Behavior Change Intervention. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates an existing behavior change intervention in the repository. Validates the behavior change intervention
     * object before saving the updated behavior change intervention.
     * @param intervention the behavior change intervention object containing updated information to be saved.
     * @return the updated and saved behavior change intervention object.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeIntervention> update(@RequestBody BehaviorChangeIntervention intervention) {
        ResponseEntity<BehaviorChangeIntervention> response;

        try {
            ObjectValidator.validateObject(intervention);
            BehaviorChangeIntervention updated = behaviorChangeInterventionService.update(intervention);

            if (updated != null && updated.getId().equals(intervention.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated new Behavior Change Intervention: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.error("Failed to update new Behavior Change Intervention: {}", intervention);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update new Behavior Change Intervention. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a behavior change intervention from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior change intervention to be deleted.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        behaviorChangeInterventionService.deleteById(id);
        logger.info("Behavior Change Intervention deleted: {}", id);
    }

    /**
     * Retrieves a behavior change intervention by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the behavior change intervention
     * from the repository.
     * @param id the unique identifier of the behavior change intervention to be retrieved.
     * @return the behavior change intervention corresponding to the specified identifier.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<BehaviorChangeIntervention> findById(@PathVariable Long id) {
        ResponseEntity<BehaviorChangeIntervention> response;

        try {
            ObjectValidator.validateId(id);
            BehaviorChangeIntervention intervention = behaviorChangeInterventionService.findById(id);

            if (intervention != null && intervention.getId().equals(id)) {
                response = new ResponseEntity<>(intervention, HttpStatus.OK);
                logger.info("Found Behavior Change Intervention: {}", intervention);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Behavior Change Intervention by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Behavior Change Intervention. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a behavior change intervention by its name.
     * @param name must not be null.
     * @return the behavior change intervention with the given name or Optional#empty() if none found.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeIntervention>> findByName(@PathVariable String name) {
        ResponseEntity<List<BehaviorChangeIntervention>> response;

        try {
            ObjectValidator.validateObject(name);
            List<BehaviorChangeIntervention> interventionList = behaviorChangeInterventionService.findByName(name);

            if (interventionList != null && !interventionList.isEmpty()) {
                response = new ResponseEntity<>(interventionList, HttpStatus.OK);
                logger.info("Found Behavior Change Intervention by name: {}", interventionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Behavior Change Intervention by name: {}", name);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Behavior Change Intervention by name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds and retrieves a list of BehaviorChangeIntervention with association with BehaviorChangeInterventionPhase.
     * @param id The Behavior Change Intervention Phase id.
     * @return the behavior change intervention corresponding with association with Behavior Change Intervention
     * Phase Id specified.
     */
    @GetMapping("/find/behaviorchangeinterventionphase/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeIntervention>> findByBehaviorChangeInterventionPhase(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeIntervention>> response;

        try {
            ObjectValidator.validateId(id);
            List<BehaviorChangeIntervention> interventionList = behaviorChangeInterventionService.
                    findByBehaviorChangeInterventionPhase(id);

            if (interventionList != null && !interventionList.isEmpty()) {
                response = new ResponseEntity<>(interventionList, HttpStatus.OK);
                logger.info("Found Behavior Change Intervention with BehaviorChangeInterventionPhase association: {}", interventionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Behavior Change Intervention with BehaviorChangeInterventionPhase association by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Behavior Change Intervention with BehaviorChangeInterventionPhase association. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all behavior change intervention.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeIntervention>> findAll() {
        ResponseEntity<List<BehaviorChangeIntervention>> response;

        try {
            List<BehaviorChangeIntervention> interventionList = behaviorChangeInterventionService.findAll();

            if (interventionList != null && !interventionList.isEmpty()) {
                response = new ResponseEntity<>(interventionList, HttpStatus.OK);
                logger.info("Found all Behavior Change Intervention list: {}", interventionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all Behavior Change Intervention list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all Behavior Change Intervention list. Error: {}", e.getMessage());
        }

        return response;
    }
}