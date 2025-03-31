package ca.uqam.latece.evo.server.core.controller;

import java.util.List;
import java.util.Optional;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.GoalSetting;
import ca.uqam.latece.evo.server.core.service.GoalSettingService;
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
 * GoalSetting Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/goalsetting")
public class GoalSettingController extends AbstractEvoController <GoalSetting> {
    private static final Logger logger = LoggerFactory.getLogger(GoalSettingController.class);

    @Autowired
    private GoalSettingService goalSettingService;

    /**
     * Inserts a GoalSetting in the database.
     * @param model The GoalSetting entity.
     * @return The saved GoalSetting.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<GoalSetting> create(@RequestBody GoalSetting model) {
        ResponseEntity<GoalSetting> response;

        try {
            ObjectValidator.validateObject(model);
            GoalSetting saved = goalSettingService.create(model);

            if (Optional.ofNullable(saved).isPresent()) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new goal setting: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new goal setting.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new goal setting. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a GoalSetting in the database.
     * @param model The GoalSetting entity.
     * @return The saved GoalSetting.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<GoalSetting> update(@RequestBody GoalSetting model) {
        ResponseEntity<GoalSetting> response;

        try {
            ObjectValidator.validateObject(model);
            GoalSetting updated = goalSettingService.update(model);

            if (Optional.ofNullable(updated).isPresent()) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated goal setting: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update goal setting.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update goal setting. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the GoalSetting with the given id.
     * <p>
     * If the GoalSetting is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the GoalSetting to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        goalSettingService.deleteById(id);
        logger.info("Goal Setting deleted: {}", id);
    }

    /**
     * Retrieves a GoalSetting by its id.
     * @param id The GoalSetting Id to filter GoalSetting entities by, must not be null.
     * @return the GoalSetting with the given id or Optional#empty() if none found.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<GoalSetting> findById(@PathVariable Long id) {
        ResponseEntity<GoalSetting> response;

        try {
            ObjectValidator.validateId(id);
            GoalSetting goalSetting = goalSettingService.findById(id);

            if (Optional.ofNullable(goalSetting).isPresent()) {
                response = new ResponseEntity<>(goalSetting, HttpStatus.OK);
                logger.info("Found goal setting: {}", goalSetting);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find goal setting by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find goal setting. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of GoalSetting entities by their name.
     * @param name the type of the GoalSetting to search for.
     * @return a list of GoalSetting entities matching the specified name.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<GoalSetting>> findByName(@PathVariable String name) {
        ResponseEntity<List<GoalSetting>> response;

        try {
            ObjectValidator.validateString(name);
            List<GoalSetting> goalSettings = goalSettingService.findByName(name);

            if (Optional.ofNullable(goalSettings).isPresent()) {
                response = new ResponseEntity<>(goalSettings, HttpStatus.OK);
                logger.info("Found goal setting: {}", goalSettings);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find goal setting by name: {}", name);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find goal setting ny name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of GoalSetting entities by their type.
     * @param type the type of the GoalSetting to search for.
     * @return a list of GoalSetting entities matching the specified type.
     */
    @GetMapping("/find/type/{type}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<GoalSetting>> findByType(@PathVariable ActivityType type) {
        ResponseEntity<List<GoalSetting>> response;

        try {
            List<GoalSetting> goalSetting = goalSettingService.findByType(type);

            if (Optional.ofNullable(goalSetting).isPresent()) {
                response = new ResponseEntity<>(goalSetting, HttpStatus.OK);
                logger.info("Found goal setting: {}", goalSetting);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find goal setting by type: {}", type);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find goal setting by type. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all GoalSetting.
     * @return all GoalSetting.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<GoalSetting>> findAll() {
        ResponseEntity<List<GoalSetting>> response;

        try {
            List<GoalSetting> goalSettingList = goalSettingService.findAll();

            if (Optional.ofNullable(goalSettingList).isPresent()) {
                response = new ResponseEntity<>(goalSettingList, HttpStatus.OK);
                logger.info("Found all goal setting: {}", goalSettingList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all goal setting list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all goal setting list. Error: {}", e.getMessage());
        }

        return response;
    }
}
