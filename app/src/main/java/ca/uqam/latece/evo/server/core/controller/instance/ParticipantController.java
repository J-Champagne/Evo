package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.Participant;
import ca.uqam.latece.evo.server.core.service.instance.ParticipantService;

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
 * Participant Controller.
 * @author Julien Champagne.
 */
@Controller
@RequestMapping("/participant")
public class ParticipantController extends AbstractEvoController<Participant> {
    private static final Logger logger = LoggerFactory.getLogger(ParticipantController.class);

    @Autowired
    private ParticipantService participantService;

    /**
     * Creates a Participant in the database.
     * @param participant Participant.
     * @return The created Participant in JSON format.
     * @throws IllegalArgumentException if pa is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Participant> create(@RequestBody Participant participant) {
        ResponseEntity<Participant> response;

        try {
            Participant saved = participantService.create(participant);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created Participant: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new Participant");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new Participant. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a Participant in the database.
     * @param participant Participant.
     * @return The updated Participant in JSON format.
     * @throws IllegalArgumentException if pa is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Participant> update(@RequestBody Participant participant) {
        ResponseEntity<Participant> response;

        try {
            Participant updated = participantService.update(participant);

            if (updated != null && updated.getId().equals(participant.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated Participant: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to update Participant");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update Participant. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a Participant by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        participantService.deleteById(id);
        logger.info("Deleted Participant: {}", id);
    }

    /**
     * Finds all Participant entities.
     * @return List<Participant> in JSON format.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Participant>> findAll() {
        ResponseEntity<List<Participant>> response;

        try {
            List<Participant> result = participantService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all Participants: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all Participants");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all Participants. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a Participant by its id.
     * @param id Long.
     * @return Participant in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Participant> findById(@PathVariable Long id) {
        ResponseEntity<Participant> response;

        try {
            Participant result = participantService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Participant: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Participant");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Participant. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds Participant entities by their Role id.
     * @param id Long.
     * @return List<Participant> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/role/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Participant>> findByRoleId(@PathVariable Long id) {
        ResponseEntity<List<Participant>> response;

        try {
            List<Participant> result = participantService.findByRoleId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Participant entities by Role Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Participant entities by Role Id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Participant entities by Role Id. Error: {}", e.getMessage());
        }

        return response;
    }
    
     /**
     * Finds Participant entities by their Actor id.
     * @param id Long.
     * @return List<Participant> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
     @GetMapping("/find/actor/{id}")
     @ResponseStatus(HttpStatus.OK)
     public ResponseEntity<List<Participant>> findByActorId(@PathVariable Long id) {
        ResponseEntity<List<Participant>> response;

        try {
            List<Participant> result = participantService.findByActorId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Participant entities by Actor Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Participant entities by Actor Id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Participant entities by Actor Id. Error: {}", e.getMessage());
        }

        return response;
    }
}
