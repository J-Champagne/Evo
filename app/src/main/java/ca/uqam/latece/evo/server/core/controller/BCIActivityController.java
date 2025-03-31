package ca.uqam.latece.evo.server.core.controller;

import java.util.List;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.service.BCIActivityService;
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
 * BCIActivity Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/bciactivity")
public class BCIActivityController extends AbstractEvoController <BCIActivity> {
    private static final Logger logger = LoggerFactory.getLogger(BCIActivityController.class);

    @Autowired
    private BCIActivityService bciActivityService;

    /**
     * Inserts a BCIActivity in the database.
     * @param model The BCIActivity entity.
     * @return The saved BCIActivity.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<BCIActivity> create(@RequestBody BCIActivity model) {
        ResponseEntity<BCIActivity> response;

        try{
            ObjectValidator.validateObject(model);
            BCIActivity saved = bciActivityService.create(model);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new BCIActivity: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new BCIActivity.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new BCIActivity. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a BCIActivity in the database.
     * @param model The BCIActivity entity.
     * @return The saved BCIActivity.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<BCIActivity> update(@RequestBody BCIActivity model) {
        ResponseEntity<BCIActivity> response;

        try {
            ObjectValidator.validateObject(model);
            BCIActivity updated = bciActivityService.update(model);

            if (updated != null && updated.getId().equals(model.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated BCIActivity: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update BCIActivity.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update BCIActivity. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the BCIActivity with the given id.
     * <p>
     * If the BCIActivity is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the BCIActivity to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        bciActivityService.deleteById(id);
        logger.info("BCIActivity deleted: {}", id);
    }

    /**
     * Retrieves a BCIActivity by its id.
     * @param id The BCIActivity Id to filter BCIActivity entities by, must not be null.
     * @return the BCIActivity with the given id or Optional#empty() if none found.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<BCIActivity> findById(@PathVariable Long id) {
        ResponseEntity<BCIActivity> response;

        try {
            ObjectValidator.validateId(id);
            BCIActivity bciActivity = bciActivityService.findById(id);

            if (bciActivity != null && bciActivity.getId().equals(id)) {
                response = new ResponseEntity<>(bciActivity, HttpStatus.OK);
                logger.info("Found BCIActivity by id: {}", bciActivity);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIActivity by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivity. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of BCIActivity entities by their name.
     * @param name the type of the BCIActivity to search for.
     * @return a list of BCIActivity entities matching the specified name.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findByName(@PathVariable String name) {
        ResponseEntity<List<BCIActivity>> response;

        try {
            ObjectValidator.validateObject(name);
            List<BCIActivity> bciActivityList = bciActivityService.findByName(name);

            if (bciActivityList != null && !bciActivityList.isEmpty()) {
                response = new ResponseEntity<>(bciActivityList, HttpStatus.OK);
                logger.info("Found BCIActivity: {}", bciActivityList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIActivity by name: {}", name);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivity by name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of BCIActivity entities by their type.
     * @param type the type of the BCIActivity to search for.
     * @return a list of BCIActivity entities matching the specified type.
     */
    @GetMapping("/find/type/{type}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findByType(@PathVariable ActivityType type) {
        ResponseEntity<List<BCIActivity>> response;

        try {
            ObjectValidator.validateObject(type);
            List<BCIActivity> bciActivityList = bciActivityService.findByType(type);

            if (bciActivityList != null && !bciActivityList.isEmpty()) {
                response = new ResponseEntity<>(bciActivityList, HttpStatus.OK);
                logger.info("Found BCIActivity list: {}", bciActivityList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIActivity list by type: {}", type);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivity list by type. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of BCIActivity entities that match the specified Develops Id.
     * @param id The Develops Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Develops id, or an empty list if no matches are found.
     */
    @GetMapping("/find/develops/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findByDevelops(@PathVariable Long id) {
        ResponseEntity<List<BCIActivity>> response;

        try {
            ObjectValidator.validateObject(id);
            List<BCIActivity> bciActivityList = bciActivityService.findByDevelops(id);

            if (bciActivityList != null && !bciActivityList.isEmpty()) {
                response = new ResponseEntity<>(bciActivityList, HttpStatus.OK);
                logger.info("Found BCIActivity with Develops association: {}", bciActivityList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIActivity by Develops: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivity by Develops. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of BCIActivity entities that match the specified Requires Id.
     * @param id The Requires Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Requires id, or an empty list if no matches are found.
     */
    @GetMapping("/find/requires/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findByRequires(@PathVariable Long id) {
        ResponseEntity<List<BCIActivity>> response;

        try {
            ObjectValidator.validateObject(id);
            List<BCIActivity> bciActivityList = bciActivityService.findByRequires(id);

            if (bciActivityList != null && !bciActivityList.isEmpty()) {
                response = new ResponseEntity<>(bciActivityList, HttpStatus.OK);
                logger.info("Found BCIActivity with Requires association: {}", bciActivityList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIActivity by Requires: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivity by Requires. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of BCIActivity entities that match the specified Role Id.
     * @param id The Role Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Role id, or an empty list if no matches are found.
     */
    @GetMapping("/find/role/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findByRole(@PathVariable Long id) {
        ResponseEntity<List<BCIActivity>> response;

        try {
            ObjectValidator.validateObject(id);
            List<BCIActivity> bciActivityList = bciActivityService.findByRole(id);

            if (bciActivityList != null && !bciActivityList.isEmpty()) {
                response = new ResponseEntity<>(bciActivityList, HttpStatus.OK);
                logger.info("Found BCIActivity with Role association: {}", bciActivityList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIActivity by Role: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivity by Role. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of BCIActivity entities that match the specified Content Id.
     * @param id The Content Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Content id, or an empty list if no matches are found.
     */
    @GetMapping("/find/content/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findByContent(@PathVariable Long id) {
        ResponseEntity<List<BCIActivity>> response;

        try {
            ObjectValidator.validateObject(id);
            List<BCIActivity> bciActivityList = bciActivityService.findByContent(id);

            if (bciActivityList != null && !bciActivityList.isEmpty()) {
                response = new ResponseEntity<>(bciActivityList, HttpStatus.OK);
                logger.info("Found BCIActivity with Content association: {}", bciActivityList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIActivity with Content association: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivity with Content association. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all BCIActivity.
     * @return all BCIActivity.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findAll() {
        ResponseEntity<List<BCIActivity>> response;

        try {
            List<BCIActivity> bciActivityList = bciActivityService.findAll();

            if (bciActivityList != null && !bciActivityList.isEmpty()) {
                response = new ResponseEntity<>(bciActivityList, HttpStatus.OK);
                logger.info("Found all BCIActivity : {}", bciActivityList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all BCIActivity list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all BCIActivity list. Error: {}", e.getMessage());
        }

        return response;
    }
}
