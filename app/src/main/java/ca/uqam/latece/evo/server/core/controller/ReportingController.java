package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.Reporting;
import ca.uqam.latece.evo.server.core.service.ReportingService;
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
 * Reporting Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/reporting")
public class ReportingController extends AbstractEvoController <Reporting> {
    private static final Logger logger = LoggerFactory.getLogger(ReportingController.class);

    @Autowired
    private ReportingService reportingService;

    /**
     * Inserts a Reporting in the database.
     * @param model The Reporting entity.
     * @return The saved Reporting.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Reporting> create(@RequestBody Reporting model) {
        ResponseEntity<Reporting> response;

        try {
            ObjectValidator.validateObject(model);
            Reporting saved = reportingService.create(model);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new a reporting: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create a new reporting.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create a new reporting. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a Reporting in the database.
     * @param model The Reporting entity.
     * @return The saved Reporting.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Reporting> update(@RequestBody Reporting model) {
        ResponseEntity<Reporting> response;

        try {
            ObjectValidator.validateObject(model);
            Reporting updated = reportingService.update(model);

            if (updated != null && updated.getId().equals(model.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated a reporting: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update a reporting.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update a reporting. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the Reporting with the given id.
     * <p>
     * If the Reporting is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Reporting to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        reportingService.deleteById(id);
        logger.info("Reporting deleted: {}", id);
    }

    /**
     * Retrieves a Reporting by its id.
     * @param id The Reporting Id to filter Reporting entities by, must not be null.
     * @return the Reporting with the given id or Optional#empty() if none found.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Reporting> findById(@PathVariable Long id) {
        ResponseEntity<Reporting> response;

        try {
            ObjectValidator.validateId(id);
            Reporting reporting = reportingService.findById(id);

            if (reporting != null && reporting.getId().equals(id)) {
                response = new ResponseEntity<>(reporting, HttpStatus.OK);
                logger.info("Found reporting: {}", reporting);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find reporting by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find reporting. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Reporting entities by their name.
     * @param name the type of the Reporting to search for.
     * @return a list of Reporting entities matching the specified name.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Reporting>> findByName(@PathVariable String name) {
        ResponseEntity<List<Reporting>> response;

        try {
            ObjectValidator.validateString(name);
            List<Reporting> reportingList = reportingService.findByName(name);

            if (reportingList != null && !reportingList.isEmpty()) {
                response = new ResponseEntity<>(reportingList, HttpStatus.OK);
                logger.info("Found reporting list by name: {}", reportingList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find reporting list by name: {}", name);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find reporting list by name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Reporting entities by their type.
     * @param type the type of the Reporting to search for.
     * @return a list of Reporting entities matching the specified type.
     */
    @GetMapping("/find/type/{type}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Reporting>> findByType(@PathVariable ActivityType type) {
        ResponseEntity<List<Reporting>> response;

        try {
            List<Reporting> reportingList = reportingService.findByType(type);

            if (reportingList != null && !reportingList.isEmpty()) {
                response = new ResponseEntity<>(reportingList, HttpStatus.OK);
                logger.info("Found reporting list by type: {}", reportingList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find reporting list by type: {}", type);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find reporting list by type. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all Reporting.
     * @return all Reporting.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Reporting>> findAll() {
        ResponseEntity<List<Reporting>> response;

        try {
            List<Reporting> reportingList = reportingService.findAll();

            if (reportingList != null && !reportingList.isEmpty()) {
                response = new ResponseEntity<>(reportingList, HttpStatus.OK);
                logger.info("Found all reporting: {}", reportingList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all reporting list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all reporting list. Error: {}", e.getMessage());
        }

        return response;
    }
}
