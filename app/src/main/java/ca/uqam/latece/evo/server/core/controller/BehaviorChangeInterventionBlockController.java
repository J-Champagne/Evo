package ca.uqam.latece.evo.server.core.controller;

import java.util.List;

import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionBlock;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionBlockService;
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
 * BehaviorChangeInterventionBlock Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/behaviorchangeinterventionblock")
public class BehaviorChangeInterventionBlockController extends AbstractEvoController<BehaviorChangeInterventionBlock> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorChangeInterventionBlockController.class);

    @Autowired
    private BehaviorChangeInterventionBlockService behaviorChangeInterventionBlockService;

    /**
     * Creates a new behavior change intervention block if it does not already exist in the repository.
     * @param intervention the behavior change intervention object to be created and saved.
     * @return the saved behavior change intervention object.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<BehaviorChangeInterventionBlock> create(@RequestBody BehaviorChangeInterventionBlock intervention) {
        ResponseEntity<BehaviorChangeInterventionBlock> response;

        try {
            ObjectValidator.validateObject(intervention);
            BehaviorChangeInterventionBlock saved = behaviorChangeInterventionBlockService.create(intervention);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new Behavior Change Intervention Block: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new Behavior Change Intervention Block.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new Behavior Change Intervention Block. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates an existing behavior change intervention block in the repository. Validates a behavior
     * change intervention block object before saving the updated behavior change intervention block.
     * @param intervention the behavior change intervention block object containing updated information to be saved.
     * @return the updated and saved behavior change intervention block object.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeInterventionBlock> update(@RequestBody BehaviorChangeInterventionBlock intervention) {
        ResponseEntity<BehaviorChangeInterventionBlock> response;

        try {
            ObjectValidator.validateObject(intervention);
            BehaviorChangeInterventionBlock updated = behaviorChangeInterventionBlockService.update(intervention);

            if (updated != null && updated.getId().equals(intervention.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated new Behavior Change Intervention Block: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.error("Failed to update new Behavior Change Intervention Block: {}", intervention);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update new Behavior Change Intervention Block. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a behavior change intervention block from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior change intervention block to be deleted.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        behaviorChangeInterventionBlockService.deleteById(id);
        logger.info("Behavior Change Intervention Block deleted: {}", id);
    }

    /**
     * Retrieves a behavior change intervention block by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the behavior change intervention block
     * from the repository.
     * @param id the unique identifier of the behavior change intervention block to be retrieved.
     * @return the behavior change intervention block corresponding to the specified identifier.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<BehaviorChangeInterventionBlock> findById(@PathVariable Long id) {
        ResponseEntity<BehaviorChangeInterventionBlock> response;

        try {
            ObjectValidator.validateId(id);
            BehaviorChangeInterventionBlock interventionBlock = behaviorChangeInterventionBlockService.findById(id);

            if (interventionBlock != null && interventionBlock.getId().equals(id)) {
                response = new ResponseEntity<>(interventionBlock, HttpStatus.OK);
                logger.info("Found Behavior Change Intervention Block: {}", interventionBlock);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Behavior Change Intervention Block by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Behavior Change Intervention Block. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a Behavior Change Intervention Block by entry conditions.
     * @param entry the entry condition of the behavior change intervention block to be retrieved.
     * @return the behavior change intervention block corresponding to the specified identifier.
     */
    @GetMapping("/find/entrycond/{entry}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionBlock>> findByEntryConditions(@PathVariable String entry) {
        ResponseEntity<List<BehaviorChangeInterventionBlock>> response;

        try {
            ObjectValidator.validateString(entry);
            List<BehaviorChangeInterventionBlock> interventionBlockList = behaviorChangeInterventionBlockService.
                    findByEntryConditions(entry);

            if (interventionBlockList != null && !interventionBlockList.isEmpty()) {
                response = new ResponseEntity<>(interventionBlockList, HttpStatus.OK);
                logger.info("Found Behavior Change Intervention Block by Entry Conditions: {}", interventionBlockList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Behavior Change Intervention Block by Entry Conditions: {}", entry);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Behavior Change Intervention Block by Entry Conditions. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a BehaviorChangeInterventionBlock by exit conditions.
     * @param exit the exit condition of the behavior change intervention block to be retrieved.
     * @return the behavior change intervention block corresponding to the specified identifier.
     */
    @GetMapping("/find/exitcond/{exit}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionBlock>> findByExitConditions(@PathVariable String exit) {
        ResponseEntity<List<BehaviorChangeInterventionBlock>> response;

        try {
            ObjectValidator.validateString(exit);
            List<BehaviorChangeInterventionBlock> interventionBlockList = behaviorChangeInterventionBlockService.
                    findByExitConditions(exit);

            if (interventionBlockList != null && !interventionBlockList.isEmpty()) {
                response = new ResponseEntity<>(interventionBlockList, HttpStatus.OK);
                logger.info("Found Behavior Change Intervention Block by Exit Conditions: {}", interventionBlockList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Behavior Change Intervention Block by Exit Conditions: {}", exit);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Behavior Change Intervention Block by Exit Conditions. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionBlock with association with BehaviorChangeInterventionPhase.
     * @param id The Behavior Change Intervention Phase id.
     * @return the behavior change intervention phase corresponding with association with Behavior Change Intervention
     * Phase Id specified.
     */
    @GetMapping("/find/behaviorchangeinterventionphase/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionBlock>> findByBehaviorChangeInterventionPhaseId(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionBlock>> response;

        try {
            ObjectValidator.validateId(id);
            List<BehaviorChangeInterventionBlock> interventionBlockList = behaviorChangeInterventionBlockService.
                    findByBehaviorChangeInterventionPhaseId(id);

            if (interventionBlockList != null && !interventionBlockList.isEmpty()) {
                response = new ResponseEntity<>(interventionBlockList, HttpStatus.OK);
                logger.info("Found Behavior Change Intervention Block with Behavior Change Intervention Phase association: {}",
                        interventionBlockList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Behavior Change Intervention Block with Behavior Change Intervention Phase association. Id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Behavior Change Intervention Block with Behavior Change Intervention Phase association. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all behavior change intervention block.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorChangeInterventionBlock>> findAll() {
        ResponseEntity<List<BehaviorChangeInterventionBlock>> response;

        try {
            List<BehaviorChangeInterventionBlock> interventionBlockList = behaviorChangeInterventionBlockService.findAll();

            if (interventionBlockList != null && !interventionBlockList.isEmpty()) {
                response = new ResponseEntity<>(interventionBlockList, HttpStatus.OK);
                logger.info("Found all Behavior Change Intervention Block list: {}", interventionBlockList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all Behavior Change Intervention Block list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all Behavior Change Intervention Block list. Error: {}", e.getMessage());
        }

        return response;
    }
}