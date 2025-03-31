package ca.uqam.latece.evo.server.core.controller;

import java.util.List;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.BehaviorPerformance;
import ca.uqam.latece.evo.server.core.service.BehaviorPerformanceService;
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
 * BehaviorPerformance Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/behaviorperformance")
public class BehaviorPerformanceController extends AbstractEvoController <BehaviorPerformance> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorPerformanceController.class);

    @Autowired
    private BehaviorPerformanceService behaviorPerformanceService;

    /**
     * Inserts a BehaviorPerformance in the database.
     * @param model The BehaviorPerformance entity.
     * @return The saved BehaviorPerformance.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    @Override
    public ResponseEntity<BehaviorPerformance> create(@RequestBody BehaviorPerformance model) {
        ResponseEntity<BehaviorPerformance> response;

        try {
            ObjectValidator.validateObject(model);

            BehaviorPerformance saved = behaviorPerformanceService.create(model);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(model, HttpStatus.CREATED);
                logger.info("Created new behavior performance: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.error("Failed to create new behavior performance.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new behavior performance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a BehaviorPerformance in the database.
     * @param model The BehaviorPerformance entity.
     * @return The saved BehaviorPerformance.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<BehaviorPerformance> update(@RequestBody BehaviorPerformance model) {
        ResponseEntity<BehaviorPerformance> response;

        try {
            ObjectValidator.validateObject(model);
            BehaviorPerformance updated = behaviorPerformanceService.update(model);

            if (updated != null && updated.getId().equals(model.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated new behavior performance: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.error("Failed to update new behavior performance: {}", updated);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update new behavior performance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a behavior performance from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior performance to be deleted.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        behaviorPerformanceService.deleteById(id);
        logger.info("Behavior Performance deleted: {}", id);
    }

    /**
     * Retrieves a BehaviorPerformance by id.
     * @param id the unique identifier of the behavior performance to be retrieved.
     * @return the behavior performance corresponding to the specified identifier.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<BehaviorPerformance> findById(@PathVariable Long id) {
        ResponseEntity<BehaviorPerformance> response;

        try {
            BehaviorPerformance behaviorPerformance = behaviorPerformanceService.findById(id);

            if (behaviorPerformance != null && behaviorPerformance.getId().equals(id)) {
                response = new ResponseEntity<>(behaviorPerformance, HttpStatus.OK);
                logger.info("Found behavior performance: {}", behaviorPerformance);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find behavior performance by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find behavior performance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of BehaviorPerformance entities by their name.
     * @param name the name of the BehaviorPerformance to search for.
     * @return the BehaviorPerformance with the given name or Optional#empty() if none found.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorPerformance>> findByName(@PathVariable String name) {
        ResponseEntity<List<BehaviorPerformance>> response;

        try {
            List<BehaviorPerformance> behaviorPerformances = behaviorPerformanceService.findByName(name);

            if (behaviorPerformances != null && !behaviorPerformances.isEmpty()) {
                response = new ResponseEntity<>(behaviorPerformances, HttpStatus.OK);
                logger.info("Found behavior performances by name: {}", behaviorPerformances);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find behavior performance by name: {}", name);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find behavior performance. Error description: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of BehaviorPerformance entities by their type.
     * @param type the type of the BehaviorPerformance to search for.
     * @return a list of BehaviorPerformance entities matching the specified type.
     */
    @GetMapping("/find/type/{type}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorPerformance>> findByType(@PathVariable ActivityType type) {
        ResponseEntity<List<BehaviorPerformance>> response;

        try {
            List<BehaviorPerformance> behaviorPerformances = behaviorPerformanceService.findByType(type);

            if (behaviorPerformances != null && !behaviorPerformances.isEmpty()) {
                response = new ResponseEntity<>(behaviorPerformances, HttpStatus.OK);
                logger.info("Found behavior performances by type: {}", behaviorPerformances);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find behavior performance by type: {}", type);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find behavior performance by type. Error description: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves all behavior performance from the repository.
     * @return a list of all behavior performance present in the repository.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BehaviorPerformance>> findAll() {
        ResponseEntity<List<BehaviorPerformance>> response;

        try {
            List<BehaviorPerformance> behaviorPerformances = behaviorPerformanceService.findAll();

            if (behaviorPerformances != null && !behaviorPerformances.isEmpty()) {
                response = new ResponseEntity<>(behaviorPerformances, HttpStatus.OK);
                logger.info("Found all behavior performances: {}", behaviorPerformances);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all behavior performances.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all behavior performances. Error description: {}", e.getMessage());
        }

        return response;
    }
}
