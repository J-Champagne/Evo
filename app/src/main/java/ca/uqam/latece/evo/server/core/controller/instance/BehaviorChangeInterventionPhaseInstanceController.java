package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionPhaseInstanceService;

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
 * BehaviorChangeInterventionPhaseInstance Controller.
 * @author Julien Champagne.
 */
@Controller
@RequestMapping("/behaviorchangeinterventionphaseinstance")
public class BehaviorChangeInterventionPhaseInstanceController extends AbstractEvoController<BehaviorChangeInterventionPhaseInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorChangeInterventionPhaseInstanceController.class);

    @Autowired
    private BehaviorChangeInterventionPhaseInstanceService bciPhaseInstanceService;

    /**
     * Creates a BehaviorChangeInterventionPhaseInstance in the database.
     * @param phaseInstance BehaviorChangeInterventionPhaseInstance.
     * @return The created BehaviorChangeInterventionPhaseInstance in JSON format.
     * @throws IllegalArgumentException if phaseInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<BehaviorChangeInterventionPhaseInstance> create(@RequestBody BehaviorChangeInterventionPhaseInstance phaseInstance) {
        ResponseEntity<BehaviorChangeInterventionPhaseInstance> response;

        try {
            BehaviorChangeInterventionPhaseInstance saved = bciPhaseInstanceService.create(phaseInstance);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created BehaviorChangeInterventionPhaseInstance: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new BehaviorChangeInterventionPhaseInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new BehaviorChangeInterventionPhaseInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a BehaviorChangeInterventionPhaseInstance in the database.
     * @param phaseInstance BehaviorChangeInterventionPhaseInstance.
     * @return The updated BehaviorChangeInterventionPhaseInstance in JSON format.
     * @throws IllegalArgumentException if phaseInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<BehaviorChangeInterventionPhaseInstance> update(@RequestBody BehaviorChangeInterventionPhaseInstance phaseInstance) {
        ResponseEntity<BehaviorChangeInterventionPhaseInstance> response;

        try {
            BehaviorChangeInterventionPhaseInstance updated = bciPhaseInstanceService.update(phaseInstance);

            if (updated != null && updated.getId().equals(phaseInstance.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated BehaviorChangeInterventionPhaseInstance: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to update BehaviorChangeInterventionPhaseInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update BehaviorChangeInterventionPhaseInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a BehaviorChangeInterventionPhaseInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable Long id) {
        bciPhaseInstanceService.deleteById(id);
        logger.info("Deleted BehaviorChangeInterventionPhaseInstance: {}", id);
    }

    /**
     * Finds all BehaviorChangeInterventionPhaseInstance entities.
     * @return List<BehaviorChangeInterventionPhaseInstance> in JSON format.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<List<BehaviorChangeInterventionPhaseInstance>> findAll() {
        ResponseEntity<List<BehaviorChangeInterventionPhaseInstance>> response;

        try {
            List<BehaviorChangeInterventionPhaseInstance> result = bciPhaseInstanceService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all BehaviorChangeInterventionPhaseInstance: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all BehaviorChangeInterventionPhaseInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all BehaviorChangeInterventionPhaseInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a BehaviorChangeInterventionPhaseInstance by its id.
     * @param id Long.
     * @return BehaviorChangeInterventionPhaseInstance in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<BehaviorChangeInterventionPhaseInstance> findById(@PathVariable Long id) {
        ResponseEntity<BehaviorChangeInterventionPhaseInstance> response;

        try {
            BehaviorChangeInterventionPhaseInstance result = bciPhaseInstanceService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionPhaseInstance: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionPhaseInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionPhaseInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BehaviorChangeInterventionPhaseInstance entities by their currentBlock id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionPhaseInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/currentblock/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionPhaseInstance>> findByCurrentBlock(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionPhaseInstance>> response;

        try {
            List<BehaviorChangeInterventionPhaseInstance> result = bciPhaseInstanceService.findByCurrentBlockId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionPhaseInstance entities by currentBlock id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionPhaseInstance entities by currentBlock id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionPhaseInstance entities by currentBlock id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BehaviorChangeInterventionPhaseInstance entities by a BehaviorChangeInterventionBlockInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionPhaseInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/blocks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionPhaseInstance>> findByBlocksId(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionPhaseInstance>> response;

        try {
            List<BehaviorChangeInterventionPhaseInstance> result = bciPhaseInstanceService.findByBlocksId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionPhaseInstance entities by Blocks Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionPhaseInstance entities by Blocks Id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionPhaseInstance entities by Blocks Id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BehaviorChangeInterventionPhaseInstance entities by a BCIModuleInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionPhaseInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/modules/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionPhaseInstance>> findByModulesId(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionPhaseInstance>> response;

        try {
            List<BehaviorChangeInterventionPhaseInstance> result = bciPhaseInstanceService.findByModulesId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionPhaseInstance entities by Modules Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionPhaseInstance entities by Modules Id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionPhaseInstance entities by Modules Id. Error: {}", e.getMessage());
        }

        return response;
    }
}
