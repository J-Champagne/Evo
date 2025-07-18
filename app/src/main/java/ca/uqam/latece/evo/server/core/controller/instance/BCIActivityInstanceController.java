package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.service.instance.BCIActivityInstanceService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * BCIActivityInstance Controller.
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@RestController
@RequestMapping("/bciactivityinstance")
public class BCIActivityInstanceController extends AbstractEvoController<BCIActivityInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BCIActivityInstanceController.class);

    @Autowired
    private BCIActivityInstanceService bciActivityInstanceService;

    /**
     * Creates a BCIActivityInstance in the database.
     * @param model BCIActivityInstance.
     * @return The created BCIActivityInstance in JSON format.
     * @throws IllegalArgumentException if model is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
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
     * @param model BCIActivityInstance.
     * @return The updated BCIActivityInstance in JSON format.
     * @throws IllegalArgumentException if model is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
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
     * Deletes a BCIActivityInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @Override
    public void deleteById(@PathVariable Long id) {
        bciActivityInstanceService.deleteById(id);
        logger.info("BCIActivityInstance deleted: {}", id);
    }


    /**
     * Finds all BCIActivityInstance entities.
     * @return List<BCIActivityInstance> in JSON format.
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
                logger.info("Failed to find all BCIActivityInstance entities.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all BCIActivityInstance entities. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a BCIActivityInstance by its id.
     * @param id Long.
     * @return BCIActivityInstance in JSON format.
     * @throws IllegalArgumentException if id is null.
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
     * Finds BCIActivityInstance entities by their status.
     * @param status String.
     * @return List<BCIActivityInstance> in JSON format.
     * @throws IllegalArgumentException if status is null.
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
                logger.info("Found BCIActivityInstance entities by status: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIActivityInstance entities by status.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivityInstance entities by status. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BCIActivityInstance entities by their entryDate.
     * @param entryDate LocalDate.
     * @return List<BCIActivityInstance> in JSON format.
     * @throws IllegalArgumentException if stage is null.
     */
    @GetMapping("/find/entrydate/{entryDate}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivityInstance>> findByEntryDate(@PathVariable String entryDate) {
        ResponseEntity<List<BCIActivityInstance>> response;

        try {
            LocalDate paramEntryDate = LocalDate.parse(entryDate);
            List<BCIActivityInstance> foundList = bciActivityInstanceService.findByEntryDate(paramEntryDate);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found BCIActivityInstance entities by entryDate: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIActivityInstance entities by entryDate.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivityInstance entities by entryDate. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BCIActivityInstance entities by their exitDate.
     * @param exitDate LocalDate.
     * @return List<BCIActivityInstance> in JSON format.
     * @throws IllegalArgumentException if stage is null.
     */
    @GetMapping("/find/exitdate/{exitDate}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivityInstance>> findByExitDate(@PathVariable String exitDate) {
        ResponseEntity<List<BCIActivityInstance>> response;

        try {
            LocalDate paramExitDate = LocalDate.parse(exitDate);
            List<BCIActivityInstance> foundList = bciActivityInstanceService.findByExitDate(paramExitDate);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found BCIActivityInstance entities by exitDate: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIActivityInstance entities by exitDate.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivityInstance entities by exitDate. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BCIActivityInstance entities by the id of a Participant.
     * @param id Long.
     * @return List<BCIActivityInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/participants/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivityInstance>> findByParticipantsId(@PathVariable Long id) {
        ResponseEntity<List<BCIActivityInstance>> response;

        try {
            List<BCIActivityInstance> result = bciActivityInstanceService.findByParticipantsId(id);

            if (result != null) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BCIActivityInstance by participant id : {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIActivityInstance by participant id.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIActivityInstance by participant id. Error: {}", e.getMessage());
        }

        return response;
    }
}