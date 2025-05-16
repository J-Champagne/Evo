package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.service.instance.BCIActivityInstanceService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BCIActivityInstance Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/bciactivityinstance")
public class BCIActivityInstanceController extends AbstractEvoController<BCIActivityInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BCIActivityInstanceController.class);
    @Qualifier("BCIActivityInstanceService")
    @Autowired
    private BCIActivityInstanceService bciActivityInstanceService;

    /**
     * Inserts a BCIActivityInstance in the database.
     * @param model The BCIActivityInstance entity.
     * @return The saved BCIActivityInstance.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    @Override
    public ResponseEntity<BCIActivityInstance> create(@Valid @RequestBody BCIActivityInstance model) {
        ResponseEntity<BCIActivityInstance> response;

        try{
            ObjectValidator.validateObject(model);
            BCIActivityInstance saved = bciActivityInstanceService.create(model);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new BCIActivityInstance: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.error("Failed to create new BCIActivityInstance.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new BCIActivityInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a BCIActivityInstance in the database.
     * @param model The BCIActivityInstance entity.
     * @return The saved BCIActivityInstance.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<BCIActivityInstance> update(@Valid  @RequestBody BCIActivityInstance model) {
        ResponseEntity<BCIActivityInstance> response;

        try {
            ObjectValidator.validateObject(model);
            BCIActivityInstance updated = bciActivityInstanceService.update(model);

            if (updated != null && updated.getId().equals(model.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated BCIActivityInstance: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.error("Failed to update BCIActivityInstance.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update BCIActivityInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the BCIActivityInstance with the given id.
     * <p>
     * If the BCIActivityInstance is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the BCIActivityInstance to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @Override
    public void deleteById(@PathVariable Long id) {
        bciActivityInstanceService.deleteById(id);
        logger.info("BCIActivityInstance deleted: {}", id);
    }

    /**
     * Retrieves a BCIActivityInstance by its id.
     * @param id The BCIActivityInstance Id to filter BCIActivityInstance entities by, must not be null.
     * @return the BCIActivityInstance with the given id or Optional#empty() if none found.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<BCIActivityInstance> findById(@PathVariable Long id) {
        ResponseEntity<BCIActivityInstance> response;

        try {
            ObjectValidator.validateId(id);
            BCIActivityInstance found = bciActivityInstanceService.findById(id);

            if (found != null && found.getId().equals(id)) {
                response = new ResponseEntity<>(found, HttpStatus.OK);
                logger.info("Found BCIActivityInstance: {}", found);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.error("Failed to find BCIActivityInstance with id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivityInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Checks if a BCIActivityInstance entity with the specified status exists in the repository.
     * @param status the status of the BCIActivityInstance to check for existence, must not be null.
     * @return A list of BCIActivityInstance with the specified status.
     * @throws IllegalArgumentException if the status is null.
     */
    @GetMapping("/find/status/{status}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivityInstance>> findByStatus(@PathVariable String status) {
        ResponseEntity<List<BCIActivityInstance>> response;

        try {
            ObjectValidator.validateString(status);
            List<BCIActivityInstance> foundList = bciActivityInstanceService.findByStatus(status);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found BCIActivityInstance list by status: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIActivityInstance list by status.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivityInstance list by status. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all BCIActivityInstance.
     * @return all BCIActivityInstance.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<List<BCIActivityInstance>> findAll() {
        ResponseEntity<List<BCIActivityInstance>> response;

        try {
            List<BCIActivityInstance> bciActivityList = bciActivityInstanceService.findAll();

            if (bciActivityList != null && !bciActivityList.isEmpty()) {
                response = new ResponseEntity<>(bciActivityList, HttpStatus.OK);
                logger.info("Found all BCIActivityInstance : {}", bciActivityList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all BCIActivityInstance list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all BCIActivityInstance list. Error: {}", e.getMessage());
        }

        return response;
    }
}
