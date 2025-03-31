package ca.uqam.latece.evo.server.core.controller;

import java.util.List;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionPhaseService;
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
 * BehaviorChangeInterventionPhase Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/bahaviorchangeinterventionphase")
public class BehaviorChangeInterventionPhaseController extends AbstractEvoController<BehaviorChangeInterventionPhase>  {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorChangeInterventionPhaseController.class);

    @Autowired
    private BehaviorChangeInterventionPhaseService behaviorChangeInterventionPhaseService;

    /**
     * Creates a new behavior change intervention phase if it does not already exist in the repository.
     * @param intervention the behavior change intervention phase object to be created and saved.
     * @return the saved behavior change intervention object.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<BehaviorChangeInterventionPhase> create(@RequestBody BehaviorChangeInterventionPhase intervention) {
        ResponseEntity<BehaviorChangeInterventionPhase> response;

        try {
            ObjectValidator.validateObject(intervention);
            BehaviorChangeInterventionPhase saved = behaviorChangeInterventionPhaseService.create(intervention);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new Behavior Change Intervention Phase: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new Behavior Change Intervention Phase.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new Behavior Change Intervention Phase. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates an existing behavior change intervention phase in the repository. Validates a behavior
     * change intervention phase object before saving the updated behavior change intervention phase.
     * @param intervention the behavior change intervention phase object containing updated information to be saved.
     * @return the updated and saved behavior change intervention phase object.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeInterventionPhase> update(@RequestBody BehaviorChangeInterventionPhase intervention) {
        ResponseEntity<BehaviorChangeInterventionPhase> response;

        try {
            ObjectValidator.validateObject(intervention);
            BehaviorChangeInterventionPhase updated = behaviorChangeInterventionPhaseService.update(intervention);

            if (updated != null && updated.getId().equals(intervention.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated new Behavior Change Intervention Phase: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.error("Failed to update new Behavior Change Intervention Phase: {}", intervention);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update new Behavior Change Intervention Phase. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a behavior change intervention phase from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior change intervention phase to be deleted.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        behaviorChangeInterventionPhaseService.deleteById(id);
        logger.info("Behavior Change Intervention Phase deleted: {}", id);
    }

    /**
     * Retrieves a behavior change intervention phase by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the behavior change intervention phase
     * from the repository.
     * @param id the unique identifier of the behavior change intervention phase to be retrieved.
     * @return the behavior change intervention phase corresponding to the specified identifier.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<BehaviorChangeInterventionPhase> findById(@PathVariable Long id) {
        ResponseEntity<BehaviorChangeInterventionPhase> response;

        try {
            ObjectValidator.validateId(id);
            BehaviorChangeInterventionPhase interventionPhaseList = behaviorChangeInterventionPhaseService.findById(id);

            if (interventionPhaseList != null && interventionPhaseList.getId().equals(id)) {
                response = new ResponseEntity<>(interventionPhaseList, HttpStatus.OK);
                logger.info("Found Behavior Change Intervention Phase: {}", interventionPhaseList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Behavior Change Intervention Phase by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Behavior Change Intervention Phase. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a Behavior Change Intervention Phase by entry conditions.
     * @param entry the entry condition of the behavior change intervention phase to be retrieved.
     * @return the behavior change intervention phase corresponding to the specified identifier.
     */
    @GetMapping("/find/entrycond/{entry}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionPhase>> findByEntryConditions(@PathVariable String entry) {
        ResponseEntity<List<BehaviorChangeInterventionPhase>> response;

        try {
            ObjectValidator.validateString(entry);
            List<BehaviorChangeInterventionPhase> interventionPhaseList = behaviorChangeInterventionPhaseService.
                    findByEntryConditions(entry);

            if (interventionPhaseList != null && !interventionPhaseList.isEmpty()) {
                response = new ResponseEntity<>(interventionPhaseList, HttpStatus.OK);
                logger.info("Found Behavior Change Intervention Phase by Entry Conditions: {}", interventionPhaseList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Behavior Change Intervention Phase by Entry Conditions: {}", entry);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Behavior Change Intervention Phase by Entry Conditions. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a BehaviorChangeInterventionPhase by exit conditions.
     * @param exit the exit condition of the behavior change intervention phase to be retrieved.
     * @return the behavior change intervention phase corresponding to the specified identifier.
     */
    @GetMapping("/find/exitcond/{exit}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionPhase>> findByExitConditions(@PathVariable String exit) {
        ResponseEntity<List<BehaviorChangeInterventionPhase>> response;

        try {
            ObjectValidator.validateString(exit);
            List<BehaviorChangeInterventionPhase> interventionPhaseList = behaviorChangeInterventionPhaseService.
                    findByExitConditions(exit);

            if (interventionPhaseList != null && !interventionPhaseList.isEmpty()) {
                response = new ResponseEntity<>(interventionPhaseList, HttpStatus.OK);
                logger.info("Found Behavior Change Intervention Phase by Exit Conditions: {}", interventionPhaseList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Behavior Change Intervention Phase by Exit Conditions: {}", exit);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Behavior Change Intervention Phase by Exit Conditions. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionPhase with association with BehaviorChangeIntervention.
     * @param id The Behavior Change Intervention id.
     * @return the behavior change intervention phase corresponding with association with Behavior Change Intervention
     * Id specified.
     */
    @GetMapping("/find/behaviorchangeintervention/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionPhase>> findByBehaviorChangeInterventionId(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionPhase>> response;

        try {
            ObjectValidator.validateId(id);
            List<BehaviorChangeInterventionPhase> interventionPhaseList = behaviorChangeInterventionPhaseService.
                    findByBehaviorChangeInterventionId(id);

            if (interventionPhaseList != null && !interventionPhaseList.isEmpty()) {
                response = new ResponseEntity<>(interventionPhaseList, HttpStatus.OK);
                logger.info("Found Behavior Change Intervention Phase with Behavior Change Intervention association: {}",
                        interventionPhaseList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Behavior Change Intervention Phase with Behavior Change Intervention association. Id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Behavior Change Intervention Phase with Behavior Change Intervention association. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionPhase with association with BehaviorChangeInterventionBlock.
     * @param id The Behavior Change Intervention Block id.
     * @return the behavior change intervention phase corresponding with association with Behavior Change Intervention
     * Block Id specified.
     */
    @GetMapping("/find/behaviorchangeinterventionblock/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionPhase>> findByBehaviorChangeInterventionBlockId(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionPhase>> response;

        try {
            ObjectValidator.validateId(id);
            List<BehaviorChangeInterventionPhase> interventionPhaseList = behaviorChangeInterventionPhaseService.
                    findByBehaviorChangeInterventionBlockId(id);

            if (interventionPhaseList != null && !interventionPhaseList.isEmpty()) {
                response = new ResponseEntity<>(interventionPhaseList, HttpStatus.OK);
                logger.info("Found Behavior Change Intervention Phase with Behavior Change Intervention Block association: {}",
                        interventionPhaseList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Behavior Change Intervention Phase with Behavior Change Intervention Block association. Id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Behavior Change Intervention Phase with Behavior Change Intervention Block association. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all behavior change intervention phase.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionPhase>> findAll() {
        ResponseEntity<List<BehaviorChangeInterventionPhase>> response;

        try {
            List<BehaviorChangeInterventionPhase> interventionPhaseList = behaviorChangeInterventionPhaseService.findAll();

            if (interventionPhaseList != null && !interventionPhaseList.isEmpty()) {
                response = new ResponseEntity<>(interventionPhaseList, HttpStatus.OK);
                logger.info("Found all Behavior Change Intervention Phase list: {}", interventionPhaseList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all Behavior Change Intervention Phase list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all Behavior Change Intervention Phase list. Error: {}", e.getMessage());
        }

        return response;
    }
}