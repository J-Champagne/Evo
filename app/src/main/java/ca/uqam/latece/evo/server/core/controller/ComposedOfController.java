package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.ComposedOf;
import ca.uqam.latece.evo.server.core.service.ComposedOfService;
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
 * ComposedOf Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/composedof")
public class ComposedOfController extends AbstractEvoController <ComposedOf> {
    private static final Logger logger = LoggerFactory.getLogger(ComposedOfController.class);

    @Autowired
    private ComposedOfService composedOfService;

    /**
     * Inserts a ComposedOf in the database.
     * @param model The ComposedOf entity.
     * @return The saved ComposedOf.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<ComposedOf> create(@RequestBody ComposedOf model) {
        ResponseEntity<ComposedOf> response;

        try{
            ObjectValidator.validateObject(model);
            ComposedOf saved = composedOfService.create(model);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new ComposedOf: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new ComposedOf.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new ComposedOf. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a ComposedOf in the database.
     * @param model The ComposedOf entity.
     * @return The saved ComposedOf.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<ComposedOf> update(@RequestBody ComposedOf model) {
        ResponseEntity<ComposedOf> response;

        try {
            ObjectValidator.validateObject(model);
            ComposedOf updated = composedOfService.update(model);

            if (updated != null && updated.getId().equals(model.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated ComposedOf: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update ComposedOf.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update ComposedOf. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the ComposedOf with the given id.
     * <p>
     * If the ComposedOf is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the ComposedOf to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        composedOfService.deleteById(id);
        logger.info("ComposedOf deleted: {}", id);
    }

    /**
     * Retrieves a ComposedOf by its id.
     * @param id The ComposedOf Id to filter ComposedOf entities by, must not be null.
     * @return the ComposedOf with the given id or Optional#empty() if none found.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<ComposedOf> findById(@PathVariable Long id) {
        ResponseEntity<ComposedOf> response;

        try {
            ObjectValidator.validateId(id);
            ComposedOf composedOf = composedOfService.findById(id);

            if (composedOf != null && composedOf.getId().equals(id)) {
                response = new ResponseEntity<>(composedOf, HttpStatus.OK);
                logger.info("Found ComposedOf by id: {}", composedOf);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find ComposedOf by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find ComposedOf. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of ComposedOf entities by their timing.
     * @param timing the timing of the ComposedOf to search for.
     * @return a list of ComposedOf entities matching the specified timing.
     */
    @GetMapping("/find/timing/{timing}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<ComposedOf>> findByTiming(@PathVariable TimeCycle timing) {
        ResponseEntity<List<ComposedOf>> response;

        try {
            List<ComposedOf> composedOfList = composedOfService.findByTiming(timing);

            if (composedOfList != null && !composedOfList.isEmpty()) {
                response = new ResponseEntity<>(composedOfList, HttpStatus.OK);
                logger.info("Found a ComposedOf by Timing: {}", composedOfList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find ComposedOf list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find ComposedOf list. Error: {}", e.getMessage());
        }

        return response;

    }

    /**
     * Finds a list of ComposedOf entities by their order.
     * @param order the order of the ComposedOf to search for.
     * @return a list of ComposedOf entities matching the specified order.
     */
    @GetMapping("/find/order/{order}")
    @ResponseStatus(HttpStatus.OK) //
    public ResponseEntity<List<ComposedOf>> findByOrder(@PathVariable int order) {
        ResponseEntity<List<ComposedOf>> response;

        try {
            List<ComposedOf> composedOfList = composedOfService.findByOrder(order);

            if (composedOfList != null && !composedOfList.isEmpty()) {
                response = new ResponseEntity<>(composedOfList, HttpStatus.OK);
                logger.info("Found a ComposedOf by Order: {}", composedOfList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find ComposedOf list by Order.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find ComposedOf list by Order. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all ComposedOf.
     * @return all ComposedOf.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<ComposedOf>> findAll() {
        ResponseEntity<List<ComposedOf>> response;

        try {
            List<ComposedOf> composedOfList = composedOfService.findAll();

            if (composedOfList != null && !composedOfList.isEmpty()) {
                response = new ResponseEntity<>(composedOfList, HttpStatus.OK);
                logger.info("Found all ComposedOf : {}", composedOfList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all ComposedOf list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all ComposedOf list. Error: {}", e.getMessage());
        }

        return response;
    }
}
