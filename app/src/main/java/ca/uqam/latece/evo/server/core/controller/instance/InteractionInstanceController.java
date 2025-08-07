package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.instance.InteractionInstance;
import ca.uqam.latece.evo.server.core.service.instance.InteractionInstanceService;

import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * InteractionInstance Controller.
 * @author Julien Champagne.
 */
@Controller
@RequestMapping("/interactioninstance")
public class InteractionInstanceController extends AbstractEvoController<InteractionInstance> {
    private static final Logger logger = LoggerFactory.getLogger(InteractionInstanceController.class);

    @Autowired
    InteractionInstanceService interactionInstanceService;

    /**
     * Creates a InteractionInstance in the database.
     * @param interactionInstance InteractionInstance.
     * @return The created InteractionInstance in JSON format.
     * @throws IllegalArgumentException if interactionInstance is null.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<InteractionInstance> create(@RequestBody InteractionInstance interactionInstance) {
        ResponseEntity<InteractionInstance> response;

        try {
            InteractionInstance saved = interactionInstanceService.create(interactionInstance);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created InteractionInstance: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to create new InteractionInstance.");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new InteractionInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a InteractionInstance in the database.
     * @param interactionInstance InteractionInstance.
     * @return The updated InteractionInstance in JSON format.
     * @throws IllegalArgumentException if interactionInstance is null.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<InteractionInstance> update(@RequestBody InteractionInstance interactionInstance) {
        ResponseEntity<InteractionInstance> response;

        try {
            InteractionInstance updated = interactionInstanceService.update(interactionInstance);

            if (updated != null && updated.getId().equals(interactionInstance.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated InteractionInstance: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update InteractionInstance.");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update InteractionInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a InteractionInstance by its id.
     * Silently ignored if not found.
     * @param id the InteractionInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        interactionInstanceService.deleteById(id);
        logger.info("InteractionInstance deleted: {}", id);
    }

    /**
     * Finds all InteractionInstance entities.
     * @return List of InteractionInstances in JSON format.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<List<InteractionInstance>> findAll() {
        ResponseEntity<List<InteractionInstance>> response;

        try {
            List<InteractionInstance> result = interactionInstanceService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all InteractionInstance entities: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to Found all InteractionInstance entities.");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all InteractionInstance entities. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a InteractionInstance by its id.
     * @param id the InteractionInstance id.
     * @return InteractionInstance in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<InteractionInstance> findById(@PathVariable Long id) {
        ResponseEntity<InteractionInstance> response;

        try {
            InteractionInstance result = interactionInstanceService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found InteractionInstance entity: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find InteractionInstance entity: {}", id);
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find InteractionInstance entity. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds InteractionInstance entities by their status.
     * @param status the execution status used as a filter to find InteractionInstance entities
     * @return a ResponseEntity containing a list of InteractionInstance entities matching the specified status.
     */
    @GetMapping("/find/status/{status}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<InteractionInstance>> findByStatus(@PathVariable ExecutionStatus status) {
        ResponseEntity<List<InteractionInstance>> response;

        try {
            ObjectValidator.validateObject(status);
            List<InteractionInstance> foundList = interactionInstanceService.findByStatus(status);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found InteractionInstance entities by status: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find InteractionInstance entities by status.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find InteractionInstance entities by status. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds InteractionInstance entities by their entryDate.
     * @param entryDate the date in string format (yyyy-MM-dd) for which the activity instances should be found.
     * @return a ResponseEntity containing a list of InteractionInstance entities if found.
     */
    @GetMapping("/find/entrydate/{entryDate}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<InteractionInstance>> findByEntryDate(@PathVariable String entryDate) {
        ResponseEntity<List<InteractionInstance>> response;

        try {
            LocalDate paramEntryDate = LocalDate.parse(entryDate);
            List<InteractionInstance> foundList = interactionInstanceService.findByEntryDate(paramEntryDate);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found InteractionInstance entities by entryDate: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find InteractionInstance entities by entryDate.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find InteractionInstance entities by entryDate. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds InteractionInstance entities by their exitDate.
     * @param exitDate the exit date to filter InteractionInstance entities by, expected in the format yyyy-MM-dd
     * @return a ResponseEntity containing a list of InteractionInstance entities with the provided exit date.
     */
    @GetMapping("/find/exitdate/{exitDate}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<InteractionInstance>> findByExitDate(@PathVariable String exitDate) {
        ResponseEntity<List<InteractionInstance>> response;

        try {
            LocalDate paramExitDate = LocalDate.parse(exitDate);
            List<InteractionInstance> foundList = interactionInstanceService.findByExitDate(paramExitDate);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found InteractionInstance entities by exitDate: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find InteractionInstance entities by exitDate.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find InteractionInstance entities by exitDate. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds InteractionInstance entities by the id of a Participant.
     * @param id the unique identifier of the participant whose activity instances are to be retrieved
     * @return ResponseEntity containing a list of InteractionInstance if found.
     */
    @GetMapping("/find/participants/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<InteractionInstance>> findByParticipantsId(@PathVariable Long id) {
        ResponseEntity<List<InteractionInstance>> response;

        try {
            List<InteractionInstance> result = interactionInstanceService.findByParticipantsId(id);

            if (result != null) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found InteractionInstance by participant id : {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find InteractionInstance by participant id.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find InteractionInstance by participant id. Error: {}", e.getMessage());
        }

        return response;
    }
}
