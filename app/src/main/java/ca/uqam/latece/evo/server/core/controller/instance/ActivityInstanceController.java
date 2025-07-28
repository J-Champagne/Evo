package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.ActivityInstance;
import ca.uqam.latece.evo.server.core.service.instance.ActivityInstanceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * ActivityInstance Controller.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/activityinstance")
public class ActivityInstanceController extends AbstractEvoController<ActivityInstance> {
    private static final Logger logger = LoggerFactory.getLogger(ActivityInstanceController.class);

    @Autowired
    private ActivityInstanceService activityInstanceService;

    /**
     * Creates an ActivityInstance in the database.
     * @param model ActivityInstance.
     * @return The created ActivityInstance in JSON format.
     * @throws IllegalArgumentException if model is null.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<ActivityInstance> create(@RequestBody ActivityInstance model) {
        ResponseEntity<ActivityInstance> response;

        try {
            ActivityInstance saved = activityInstanceService.create(model);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created ActivityInstance: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new ActivityInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new ActivityInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates an ActivityInstance in the database.
     * @param model ActivityInstance.
     * @return The updated ActivityInstance in JSON format.
     * @throws IllegalArgumentException if model is null.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<ActivityInstance> update(@RequestBody ActivityInstance model) {
        ResponseEntity<ActivityInstance> response;

        try {
            ActivityInstance updated = activityInstanceService.update(model);

            if (updated != null && updated.getId().equals(model.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated ActivityInstance: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to update ActivityInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update ActivityInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes an ActivityInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable Long id) {
        activityInstanceService.deleteById(id);
        logger.info("Deleted ActivityInstance: {}", id);
    }

    /**
     * Finds all ActivityInstance entities.
     * @return List<ActivityInstance> in JSON format.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<List<ActivityInstance>> findAll() {
        ResponseEntity<List<ActivityInstance>> response;

        try {
            List<ActivityInstance> result = activityInstanceService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all ActivityInstance: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all ActivityInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all ActivityInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a ActivityInstance by its id.
     * @param id Long.
     * @return ActivityInstance in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<ActivityInstance> findById(@PathVariable Long id) {
        ResponseEntity<ActivityInstance> response;

        try {
            ActivityInstance result = activityInstanceService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found ActivityInstance: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find ActivityInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find ActivityInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds ActivityInstance entities by their status.
     * @param status The execution status used as a filter criteria.
     * @return A ResponseEntity containing a list of ActivityInstance entities matching the provided status
     *         if found, or an appropriate HTTP status (NOT_FOUND or BAD_REQUEST) if no matching entities
     *         are found or if an error occurs.
     */
    @GetMapping("/find/status/{status}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<ActivityInstance>> findStatus(@PathVariable ExecutionStatus status) {
        ResponseEntity<List<ActivityInstance>> response;

        try {
            List<ActivityInstance> foundList = activityInstanceService.findByStatus(status);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found ActivityInstance entities by status: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find ActivityInstance entities by status.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find ActivityInstance entities by status. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds ActivityInstance entities by their entryDate.
     * @param entryDate LocalDate.
     * @return List<ActivityInstance> in JSON format.
     * @throws IllegalArgumentException if entryDate is null.
     */
    @GetMapping("/find/entrydate/{entryDate}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<ActivityInstance>> findByEntryDate(@PathVariable String entryDate) {
        ResponseEntity<List<ActivityInstance>> response;

        try {
            LocalDate paramEntryDate = LocalDate.parse(entryDate);
            List<ActivityInstance> foundList = activityInstanceService.findByEntryDate(paramEntryDate);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found ActivityInstance entities by entryDate: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find ActivityInstance entities by entryDate.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find ActivityInstance entities by entryDate. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds ActivityInstance entities by their exitDate.
     * @param exitDate LocalDate.
     * @return List<ActivityInstance> in JSON format.
     * @throws IllegalArgumentException if exitDate is null.
     */
    @GetMapping("/find/exitdate/{exitDate}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<ActivityInstance>> findByExitDate(@PathVariable String exitDate) {
        ResponseEntity<List<ActivityInstance>> response;

        try {
            LocalDate paramExitDate = LocalDate.parse(exitDate);
            List<ActivityInstance> foundList = activityInstanceService.findByExitDate(paramExitDate);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found ActivityInstance entities by exitDate: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find ActivityInstance entities by exitDate.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find ActivityInstance entities by exitDate. Error: {}", e.getMessage());
        }

        return response;
    }
}