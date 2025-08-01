package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.model.instance.BCIModuleInstance;
import ca.uqam.latece.evo.server.core.service.instance.BCIModuleInstanceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BCIModuleInstance Controller.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Controller
@RequestMapping("/bcimoduleinstance")
public class BCIModuleInstanceController extends AbstractEvoController<BCIModuleInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BCIModuleInstanceController.class);

    @Autowired
    private BCIModuleInstanceService bciModuleInstanceService;

    /**
     * Creates a BCIModuleInstance in the database.
     * @param moduleInstance BCIModuleInstance.
     * @return The created BCIModuleInstance in JSON format.
     * @throws IllegalArgumentException if moduleInstance is null.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<BCIModuleInstance> create(@RequestBody BCIModuleInstance moduleInstance) {
        ResponseEntity<BCIModuleInstance> response;

        try {
            BCIModuleInstance saved = bciModuleInstanceService.create(moduleInstance);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created BCIModuleInstance: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new BCIModuleInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new BCIModuleInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a BCIModuleInstance in the database.
     * @param moduleInstance BCIModuleInstance.
     * @return The updated BCIModuleInstance in JSON format.
     * @throws IllegalArgumentException if moduleInstance is null.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<BCIModuleInstance> update(@RequestBody BCIModuleInstance moduleInstance) {
        ResponseEntity<BCIModuleInstance> response;

        try {
            BCIModuleInstance updated = bciModuleInstanceService.update(moduleInstance);

            if (updated != null && updated.getId().equals(moduleInstance.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated BCIModuleInstance: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to update BCIModuleInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update BCIModuleInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Changes the status of the specified BCIModuleInstance to FINISHED. Also sets the exit date to the current date,
     * then updates the instance status in the database.
     * @param module the BCIModuleInstance whose status is to be changed to FINISHED.
     * @return the updated BCIModuleInstance with the FINISHED status and updated exit date.
     */
    @GetMapping("/changeStatusToFinished/module")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BCIModuleInstance> changeStatusToFinished(@RequestBody BCIModuleInstance module) {
        ResponseEntity<BCIModuleInstance> response;
        BCIModuleInstance updated = null;

        try {
            updated = bciModuleInstanceService.changeStatusToFinished(module);
            if (updated != null && updated.getId().equals(module.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated BCIModuleInstance status to finished: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to update BCIModuleInstance status to finished!");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update BCIModuleInstance status to finished. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Changes the status of the specified BCIModuleInstance to IN_PROGRESS. Also sets the entry date to the current
     * date, then updates the instance status in the database.
     * @param module the BCIModuleInstance whose status is to be changed to IN_PROGRESS.
     * @return the updated BCIModuleInstance with the IN_PROGRESS status and updated entry date.
     */
    @GetMapping("/changeStatusToInProgress/module")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BCIModuleInstance> changeStatusToInProgress(@RequestBody BCIModuleInstance module) {
        ResponseEntity<BCIModuleInstance> response;
        BCIModuleInstance updated = null;

        try {
            updated = bciModuleInstanceService.changeStatusToInProgress(module);
            if (updated != null && updated.getId().equals(module.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated BCIModuleInstance status to in progress: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to update BCIModuleInstance status to in progress!");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update BCIModuleInstance status to in progress. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a BCIModuleInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable Long id) {
        bciModuleInstanceService.deleteById(id);
        logger.info("Deleted BCIModuleInstance: {}", id);
    }

    /**
     * Finds all BCIModuleInstance entities.
     * @return List<BCIModuleInstance> in JSON format.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<List<BCIModuleInstance>> findAll() {
        ResponseEntity<List<BCIModuleInstance>> response;

        try {
            List<BCIModuleInstance> result = bciModuleInstanceService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all BCIModuleInstance: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all BCIModuleInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all BCIModuleInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a BCIModuleInstance by its id.
     * @param id Long.
     * @return BCIModuleInstance in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<BCIModuleInstance> findById(@PathVariable Long id) {
        ResponseEntity<BCIModuleInstance> response;

        try {
            BCIModuleInstance result = bciModuleInstanceService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BCIModuleInstance: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIModuleInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIModuleInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BCIModuleInstance entities by their outcome.
     * @param outcome OutcomeType.
     * @return List<BCIModuleInstance> in JSON format.
     * @throws IllegalArgumentException if an outcome is null.
     */
    @GetMapping("/find/outcome/{outcome}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BCIModuleInstance>> findByOutcome(@PathVariable OutcomeType outcome) {
        ResponseEntity<List<BCIModuleInstance>> response;

        try {
            List<BCIModuleInstance> result = bciModuleInstanceService.findByOutcome(outcome);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BCIModuleInstance entities by outcome: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIModuleInstance entities by outcome");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIModuleInstance entities by outcome. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BCIModuleInstance entities by a BCIActivityInstance id.
     * @param id the BCIActivityInstance id.
     * @return List<BCIModuleInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/activities/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BCIModuleInstance>> findByActivitiesId(@PathVariable Long id) {
        ResponseEntity<List<BCIModuleInstance>> response;

        try {
            List<BCIModuleInstance> result = bciModuleInstanceService.findByActivitiesId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BCIModuleInstance entities by Activities Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIModuleInstance entities by Activities Id.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIModuleInstance entities by Activities Id. Error: {}", e.getMessage());
        }

        return response;
    }
}
