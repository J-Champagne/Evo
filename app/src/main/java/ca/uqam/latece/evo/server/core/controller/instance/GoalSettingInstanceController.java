package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.GoalSettingInstance;
import ca.uqam.latece.evo.server.core.service.instance.GoalSettingInstanceService;
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
 * GoalSettingInstance Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 * @author Julien Champagne.
 */
@RestController
@RequestMapping("/goalsettinginstance")
public class GoalSettingInstanceController extends AbstractEvoController<GoalSettingInstance> {
    private static final Logger logger = LoggerFactory.getLogger(GoalSettingInstanceController.class);

    @Autowired
    private GoalSettingInstanceService goalSettingInstanceService;

    /**
     * Inserts a GoalSettingInstance in the database.
     * @param model The GoalSettingInstance entity.
     * @return The saved GoalSettingInstance.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    @Override
    public ResponseEntity<GoalSettingInstance> create(@Valid @RequestBody GoalSettingInstance model) {
        ResponseEntity<GoalSettingInstance> response;

        try{
            ObjectValidator.validateObject(model);
            GoalSettingInstance saved = goalSettingInstanceService.create(model);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new GoalSettingInstance: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.error("Failed to create new GoalSettingInstance.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new GoalSettingInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a GoalSettingInstance in the database.
     * @param model The GoalSettingInstance entity.
     * @return The saved GoalSettingInstance.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<GoalSettingInstance> update(@Valid @RequestBody GoalSettingInstance model) {
        ResponseEntity<GoalSettingInstance> response;

        try {
            ObjectValidator.validateObject(model);
            GoalSettingInstance updated = goalSettingInstanceService.update(model);

            if (updated != null && updated.getId().equals(model.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated GoalSettingInstance: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.error("Failed to update GoalSettingInstance.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update GoalSettingInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the GoalSettingInstance with the given id.
     * If the GoalSettingInstance is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the GoalSettingInstance to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @Override
    public void deleteById(@PathVariable Long id) {
        goalSettingInstanceService.deleteById(id);
        logger.info("GoalSettingInstance deleted: {}", id);
    }

    /**
     * Retrieves a GoalSettingInstance by its id.
     * @param id The GoalSettingInstance Id to filter GoalSettingInstance entities by, must not be null.
     * @return the GoalSettingInstance with the given id or Optional#empty() if none found.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<GoalSettingInstance> findById(@PathVariable Long id) {
        ResponseEntity<GoalSettingInstance> response;

        try {
            ObjectValidator.validateId(id);
            GoalSettingInstance found = goalSettingInstanceService.findById(id);

            if (found != null && found.getId().equals(id)) {
                response = new ResponseEntity<>(found, HttpStatus.OK);
                logger.info("Found GoalSettingInstance: {}", found);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.error("Failed to find GoalSettingInstance with id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find GoalSettingInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Checks if a GoalSettingInstance entity with the specified status exists in the repository.
     * @param status the status of the GoalSettingInstance to check for existence, must not be null.
     * @return A list of GoalSettingInstance with the specified status.
     * @throws IllegalArgumentException if the status is null.
     */
    @GetMapping("/find/status/{status}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<GoalSettingInstance>> findByStatus(@PathVariable ExecutionStatus status) {
        ResponseEntity<List<GoalSettingInstance>> response;

        try {
            ObjectValidator.validateObject(status);
            List<GoalSettingInstance> foundList = goalSettingInstanceService.findByStatus(status);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found GoalSettingInstance list by status: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find GoalSettingInstance list by status.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find GoalSettingInstance list by status. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a GoalSettingInstance by its Participant id.
     * @param id The unique identifier of the Participant whose GoalSettingInstance is to be retrieved; must not be null.
     * @return A ResponseEntity containing the found GoalSettingInstance if it exists.
     */
    @GetMapping("/find/participants/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<GoalSettingInstance> findByParticipantsId(@PathVariable Long id) {
        ResponseEntity<GoalSettingInstance> response;

        try {
            GoalSettingInstance result = goalSettingInstanceService.findByParticipantsId(id);

            if (result != null) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found GoalSettingInstance by participant id : {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find GoalSettingInstance by participant id.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find GoalSettingInstance by participant id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all GoalSettingInstance.
     * @return all GoalSettingInstance.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<List<GoalSettingInstance>> findAll() {
        ResponseEntity<List<GoalSettingInstance>> response;

        try {
            List<GoalSettingInstance> instanceList = goalSettingInstanceService.findAll();

            if (instanceList != null && !instanceList.isEmpty()) {
                response = new ResponseEntity<>(instanceList, HttpStatus.OK);
                logger.info("Found all GoalSettingInstance : {}", instanceList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all GoalSettingInstance list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all GoalSettingInstance list. Error: {}", e.getMessage());
        }

        return response;
    }
}
