package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionInstanceService;

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
 * BehaviorChangeInterventionInstance Controller.
 * @author Julien Champagne.
 */
@Controller
@RequestMapping("/behaviorchangeinterventioninstance")
public class BehaviorChangeInterventionInstanceController extends AbstractEvoController<BehaviorChangeInterventionInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorChangeInterventionInstanceController.class);
    
    @Autowired
    private BehaviorChangeInterventionInstanceService bciInstanceService;

    /**
     * Creates a BehaviorChangeInterventionInstance in the database.
     * @param bciInstance BehaviorChangeInterventionInstance.
     * @return The created BehaviorChangeInterventionInstance in JSON format.
     * @throws IllegalArgumentException if bciInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<BehaviorChangeInterventionInstance> create(@RequestBody BehaviorChangeInterventionInstance bciInstance) {
        ResponseEntity<BehaviorChangeInterventionInstance> response;

        try {
            BehaviorChangeInterventionInstance saved = bciInstanceService.create(bciInstance);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created BehaviorChangeInterventionInstance: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new BehaviorChangeInterventionInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new BehaviorChangeInterventionInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a BehaviorChangeInterventionInstance in the database.
     * @param bciInstance BehaviorChangeInterventionInstance.
     * @return The updated BehaviorChangeInterventionInstance in JSON format.
     * @throws IllegalArgumentException if bciInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<BehaviorChangeInterventionInstance> update(@RequestBody BehaviorChangeInterventionInstance bciInstance) {
        ResponseEntity<BehaviorChangeInterventionInstance> response;

        try {
            BehaviorChangeInterventionInstance updated = bciInstanceService.update(bciInstance);

            if (updated != null && updated.getId().equals(bciInstance.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated BehaviorChangeInterventionInstance: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to update BehaviorChangeInterventionInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update BehaviorChangeInterventionInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a BehaviorChangeInterventionInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable Long id) {
        bciInstanceService.deleteById(id);
        logger.info("Deleted BehaviorChangeInterventionInstance: {}", id);
    }

    /**
     * Finds all BehaviorChangeInterventionInstance entities.
     * @return List<BehaviorChangeInterventionInstance> in JSON format.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<List<BehaviorChangeInterventionInstance>> findAll() {
        ResponseEntity<List<BehaviorChangeInterventionInstance>> response;

        try {
            List<BehaviorChangeInterventionInstance> result = bciInstanceService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all BehaviorChangeInterventionInstance: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all BehaviorChangeInterventionInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all BehaviorChangeInterventionInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a BehaviorChangeInterventionInstance by its id.
     * @param id Long.
     * @return BehaviorChangeInterventionInstance in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<BehaviorChangeInterventionInstance> findById(@PathVariable Long id) {
        ResponseEntity<BehaviorChangeInterventionInstance> response;

        try {
            BehaviorChangeInterventionInstance result = bciInstanceService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionInstance: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BehaviorChangeInterventionInstance entities by their patient id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionInstance>> findByPatientId(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionInstance>> response;

        try {
            List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByPatientId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionInstance entities by patient Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionInstance entities by patient Id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionInstance entities by patient Id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BehaviorChangeInterventionInstance entities by their currentPhase id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/currentphase/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionInstance>> findByCurrentPhaseId(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionInstance>> response;

        try {
            List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByCurrentPhaseId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionInstance entities by currentPhase Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionInstance entities by currentPhase Id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionInstance entities by currentPhase Id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BehaviorChangeInterventionInstance entities by a BehaviorChangeInterventionPhaseInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/phases/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionInstance>> findByPhasesId(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionInstance>> response;

        try {
            List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByPhasesId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionInstance entities by Phases Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionInstance entities by Phases Id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionInstance entities by Phases Id. Error: {}", e.getMessage());
        }

        return response;
    }
}
