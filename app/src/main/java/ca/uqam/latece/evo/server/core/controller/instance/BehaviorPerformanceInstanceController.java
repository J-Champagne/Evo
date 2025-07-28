package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorPerformanceInstance;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorPerformanceInstanceService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BehaviorPerformanceInstance Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@RestController
@RequestMapping("/behaviorperformanceinstance")
public class BehaviorPerformanceInstanceController extends AbstractEvoController<BehaviorPerformanceInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorPerformanceInstanceController.class);

    @Autowired
    BehaviorPerformanceInstanceService behaviorPerformanceInstanceService;

    /**
     * Inserts a BehaviorPerformanceInstance in the database.
     * @param model The BehaviorPerformanceInstance entity.
     * @return The saved BehaviorPerformanceInstance.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    @Override
    public ResponseEntity<BehaviorPerformanceInstance> create(@Valid @RequestBody BehaviorPerformanceInstance model) {
        ResponseEntity<BehaviorPerformanceInstance> response;

        try{
            ObjectValidator.validateObject(model);
            BehaviorPerformanceInstance saved = behaviorPerformanceInstanceService.create(model);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new BehaviorPerformanceInstance: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.error("Failed to create new BehaviorPerformanceInstance.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new BehaviorPerformanceInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a BehaviorPerformanceInstance in the database.
     * @param model The BehaviorPerformanceInstance entity.
     * @return The saved BehaviorPerformanceInstance.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<BehaviorPerformanceInstance> update(@Valid @RequestBody BehaviorPerformanceInstance model) {
        ResponseEntity<BehaviorPerformanceInstance> response;

        try {
            ObjectValidator.validateObject(model);
            BehaviorPerformanceInstance updated = behaviorPerformanceInstanceService.update(model);

            if (updated != null && updated.getId().equals(model.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated BehaviorPerformanceInstance: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.error("Failed to update BehaviorPerformanceInstance.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update BehaviorPerformanceInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the BehaviorPerformanceInstance with the given id.
     * <p>
     * If the BehaviorPerformanceInstance is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the BehaviorPerformanceInstance to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @Override
    public void deleteById(@PathVariable Long id) {
        behaviorPerformanceInstanceService.deleteById(id);
        logger.info("BCIActivityInstance deleted: {}", id);
    }

    /**
     * Retrieves a BehaviorPerformanceInstance by its id.
     * @param id The BehaviorPerformanceInstance Id to filter BehaviorPerformanceInstance entities by, must not be null.
     * @return the BehaviorPerformanceInstance with the given id or Optional#empty() if none found.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<BehaviorPerformanceInstance> findById(@PathVariable Long id) {
        ResponseEntity<BehaviorPerformanceInstance> response;

        try {
            ObjectValidator.validateId(id);
            BehaviorPerformanceInstance found = behaviorPerformanceInstanceService.findById(id);

            if (found != null && found.getId().equals(id)) {
                response = new ResponseEntity<>(found, HttpStatus.OK);
                logger.info("Found BehaviorPerformanceInstance: {}", found);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.error("Failed to find BehaviorPerformanceInstance with id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorPerformanceInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Checks if a BehaviorPerformanceInstance entity with the specified status exists in the repository.
     * @param status the status of the BehaviorPerformanceInstance to check for existence, must not be null.
     * @return A list of BehaviorPerformanceInstance with the specified status.
     * @throws IllegalArgumentException if the status is null.
     */
    @GetMapping("/find/status/{status}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorPerformanceInstance>> findByStatus(@PathVariable ExecutionStatus status) {
        ResponseEntity<List<BehaviorPerformanceInstance>> response;

        try {
            ObjectValidator.validateObject(status);
            List<BehaviorPerformanceInstance> foundList = behaviorPerformanceInstanceService.findByStatus(status);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found BehaviorPerformanceInstance list by status: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorPerformanceInstance list by status.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorPerformanceInstance list by status. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all BehaviorPerformanceInstance.
     * @return all BehaviorPerformanceInstance.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<List<BehaviorPerformanceInstance>> findAll() {
        ResponseEntity<List<BehaviorPerformanceInstance>> response;

        try {
            List<BehaviorPerformanceInstance> performanceInstanceList = behaviorPerformanceInstanceService.findAll();

            if (performanceInstanceList != null && !performanceInstanceList.isEmpty()) {
                response = new ResponseEntity<>(performanceInstanceList, HttpStatus.OK);
                logger.info("Found all BehaviorPerformanceInstance: {}", performanceInstanceList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all BehaviorPerformanceInstance list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all BehaviorPerformanceInstance list. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a BehaviorPerformanceInstance by its Participant id.
     * @param id Long.
     * @return BehaviorPerformanceInstance in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/participants/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<BehaviorPerformanceInstance> findByParticipantsId(@PathVariable Long id) {
        ResponseEntity<BehaviorPerformanceInstance> response;

        try {
            BehaviorPerformanceInstance result = behaviorPerformanceInstanceService.findByParticipantsId(id);

            if (result != null) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorPerformanceInstance by participant id : {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorPerformanceInstance by participant id.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorPerformanceInstance by participant id. Error: {}", e.getMessage());
        }

        return response;
    }
}
