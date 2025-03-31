package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.Requires;
import ca.uqam.latece.evo.server.core.service.RequiresService;
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

import java.util.List;

/**
 * Requires Controller.
 * @since 22.01.2025.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/requires")
public class RequiresController extends AbstractEvoController <Requires> {
    private static final Logger logger = LoggerFactory.getLogger(RequiresController.class);

    @Autowired
    private RequiresService requiresService;

    /**
     * Inserts a Requires in the database.
     * @param requires The Requires entity.
     * @return The saved Requires.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Requires> create(@RequestBody Requires requires) {
        ResponseEntity<Requires> response;

        try {
            ObjectValidator.validateObject(requires);
            Requires saved = requiresService.create(requires);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new requires: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new requires.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new requires. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Update a Requires in the database.
     * @param requires The Requires entity.
     * @return The saved Requires.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Requires> update (@RequestBody Requires requires) {
        ResponseEntity<Requires> response;

        try {
            ObjectValidator.validateObject(requires);
            Requires updated = requiresService.update(requires);

            if (updated != null && updated.getId().equals(requires.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated requires: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update requires.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update requires. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the Requires with the given id.
     * <p>
     * If the Requires is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Requires to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id){
        requiresService.deleteById(id);
        logger.info("Requires deleted: {}", id);
    }

     /**
     * Retrieves a requires by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the role from the repository.
     * @param id the unique identifier of the requires to be retrieved.
     * @return the requires corresponding to the specified identifier.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Requires> findById(@PathVariable Long id) {
        ResponseEntity<Requires> response;

        try {
            ObjectValidator.validateId(id);
            Requires requires = requiresService.findById(id);

            if (requires != null && requires.getId().equals(id)) {
                response = new ResponseEntity<>(requires, HttpStatus.OK);
                logger.info("Found requires: {}", requires);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find requires by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find reporting. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Requires entities that match the specified Role Id.
     * @param id The Role Id to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified Role id, or an empty list if no matches are found.
     */
    @GetMapping("/find/roleid/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Requires>> findByRoleId(@PathVariable Long id) {
        ResponseEntity<List<Requires>> response;

        try {
            ObjectValidator.validateId(id);
            List<Requires> requiresList = requiresService.findByRoleId(id);

            if (requiresList != null && !requiresList.isEmpty()) {
                response = new ResponseEntity<>(requiresList, HttpStatus.OK);
                logger.info("Found Requires list by Role id: {}", requiresList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Requires list by Role id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Requires list by Role id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Requires entities that match the specified Skill Id.
     * @param id The Skill Id to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified Skill id, or an empty list if no matches are found.
     */
    @GetMapping("/find/skillid/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Requires>> findBySkillId(@PathVariable Long id) {
        ResponseEntity<List<Requires>> response;

        try {
            ObjectValidator.validateId(id);
            List<Requires> requiresList = requiresService.findBySkillId(id);

            if (requiresList != null && !requiresList.isEmpty()) {
                response = new ResponseEntity<>(requiresList, HttpStatus.OK);
                logger.info("Found Requires list by Skill id: {}", requiresList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Requires list by Skill id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Requires list by Skill id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Requires entities that match the specified BCI Activity Id.
     * @param id The BCI Activity Id to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    @GetMapping("/find/bciactivityid/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Requires>> findByBCIActivityId(@PathVariable Long id) {
        ResponseEntity<List<Requires>> response;

        try {
            ObjectValidator.validateId(id);
            List<Requires> requiresList = requiresService.findByBCIActivityId(id);

            if (requiresList != null && !requiresList.isEmpty()) {
                response = new ResponseEntity<>(requiresList, HttpStatus.OK);
                logger.info("Found Requires list by BCIActivity id: {}", requiresList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Requires list by BCIActivity id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Requires list by BCIActivity id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all Requires.
     * @return all Requires.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Requires>> findAll() {
        ResponseEntity<List<Requires>> response;

        try {
            List<Requires> requiresList = requiresService.findAll();

            if (requiresList != null && !requiresList.isEmpty()) {
                response = new ResponseEntity<>(requiresList, HttpStatus.OK);
                logger.info("Found all Requires: {}", requiresList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all Requires list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all Requires list. Error: {}", e.getMessage());
        }

        return response;
    }
}
