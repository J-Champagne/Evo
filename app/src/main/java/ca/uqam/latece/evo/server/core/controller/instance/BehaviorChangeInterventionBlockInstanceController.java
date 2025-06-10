package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionBlockInstanceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BehaviorChangeInterventionBlockInstance Controller.
 * @author Julien Champagne.
 */
@Controller
@RequestMapping("/behaviorchangeinterventionblockinstance")
public class BehaviorChangeInterventionBlockInstanceController extends AbstractEvoController<BehaviorChangeInterventionBlockInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorChangeInterventionBlockInstanceController.class);

    @Autowired
    private BehaviorChangeInterventionBlockInstanceService bciBlockInstanceService;

    /**
     * Creates a BehaviorChangeInterventionBlockInstance in the database.
     * @param blockInstance BehaviorChangeInterventionBlockInstance.
     * @return The created BehaviorChangeInterventionBlockInstance in JSON format.
     * @throws IllegalArgumentException if pa is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BehaviorChangeInterventionBlockInstance> create(@RequestBody BehaviorChangeInterventionBlockInstance blockInstance) {
        ResponseEntity<BehaviorChangeInterventionBlockInstance> response;

        try {
            BehaviorChangeInterventionBlockInstance saved = bciBlockInstanceService.create(blockInstance);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created BehaviorChangeInterventionBlockInstance: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new BehaviorChangeInterventionBlockInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new BehaviorChangeInterventionBlockInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a BehaviorChangeInterventionBlockInstance in the database.
     * @param blockInstance BehaviorChangeInterventionBlockInstance.
     * @return The updated BehaviorChangeInterventionBlockInstance in JSON format.
     * @throws IllegalArgumentException if pa is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeInterventionBlockInstance> update(@RequestBody BehaviorChangeInterventionBlockInstance blockInstance) {
        ResponseEntity<BehaviorChangeInterventionBlockInstance> response;

        try {
            BehaviorChangeInterventionBlockInstance updated = bciBlockInstanceService.update(blockInstance);

            if (updated != null && updated.getId().equals(blockInstance.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated BehaviorChangeInterventionBlockInstance: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to update BehaviorChangeInterventionBlockInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update BehaviorChangeInterventionBlockInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a BehaviorChangeInterventionBlockInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        bciBlockInstanceService.deleteById(id);
        logger.info("Deleted BehaviorChangeInterventionBlockInstance: {}", id);
    }

    /**
     * Finds all BehaviorChangeInterventionBlockInstance entities.
     * @return List<BehaviorChangeInterventionBlockInstance> in JSON format.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionBlockInstance>> findAll() {
        ResponseEntity<List<BehaviorChangeInterventionBlockInstance>> response;

        try {
            List<BehaviorChangeInterventionBlockInstance> result = bciBlockInstanceService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all BehaviorChangeInterventionBlockInstance: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all BehaviorChangeInterventionBlockInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all BehaviorChangeInterventionBlockInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a BehaviorChangeInterventionBlockInstance by its id.
     * @param id Long.
     * @return BehaviorChangeInterventionBlockInstance in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeInterventionBlockInstance> findById(@PathVariable Long id) {
        ResponseEntity<BehaviorChangeInterventionBlockInstance> response;

        try {
            BehaviorChangeInterventionBlockInstance result = bciBlockInstanceService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionBlockInstance: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionBlockInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionBlockInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BehaviorChangeInterventionBlockInstance entities by their stage.
     * @param stage TimeCycle.
     * @return List<BehaviorChangeInterventionBlockInstance> in JSON format.
     * @throws IllegalArgumentException if stage is null.
     */
    @GetMapping("/find/stage/{stage}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionBlockInstance>> findByStage(@PathVariable TimeCycle stage) {
        ResponseEntity<List<BehaviorChangeInterventionBlockInstance>> response;

        try {
            List<BehaviorChangeInterventionBlockInstance> result = bciBlockInstanceService.findByStage(stage);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionBlockInstance entities by stage: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionBlockInstance entities by stage");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionBlockInstance entities by stage. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BehaviorChangeInterventionBlockInstance entities by a BCIActivityInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionBlockInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/activities/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionBlockInstance>> findByActivitiesId(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionBlockInstance>> response;

        try {
            List<BehaviorChangeInterventionBlockInstance> result = bciBlockInstanceService.findByActivitiesId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionBlockInstance entities by Activities Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionBlockInstance entities by Activities Id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionBlockInstance entities by Activities Id. Error: {}", e.getMessage());
        }

        return response;
    }
}
