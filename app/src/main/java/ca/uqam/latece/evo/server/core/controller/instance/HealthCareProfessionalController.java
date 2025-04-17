package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * HealthCareProfessional Controller.
 * @version 1.0
 * @author Julien Champagne.
 */
@Controller
@RequestMapping("/healthcareprofessional")
public class HealthCareProfessionalController extends AbstractEvoController<HealthCareProfessional> {
    private static final Logger logger = LoggerFactory.getLogger(HealthCareProfessionalController.class);

    @Autowired
    HealthCareProfessionalService hcpService;

    /**
     * Inserts a HealthCareProfessional in the database.
     * @param hcp the HealthCareProfessional entity.
     * @return The saved HealthCareProfessional.
     * @throws IllegalArgumentException in case the given HealthCareProfessional is null.
     * @throws OptimisticLockingFailureException when the HealthCareProfessional uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HealthCareProfessional> create(@RequestBody HealthCareProfessional hcp) {
        ResponseEntity<HealthCareProfessional> response;

        try {
            HealthCareProfessional saved = hcpService.create(hcp);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created HealthCareProfessional: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to create new HealthCareProfessional");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new HealthCareProfessional. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a HealthCareProfessional in the database.
     * @param hcp the HealthCareProfessional entity.
     * @return The updated HealthCareProfessional.
     * @throws IllegalArgumentException in case the given HealthCareProfessional is null.
     * @throws OptimisticLockingFailureException when the HealthCareProfessional uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<HealthCareProfessional> update(@RequestBody HealthCareProfessional hcp) {
        ResponseEntity<HealthCareProfessional> response;

        try {
            HealthCareProfessional updated = hcpService.update(hcp);

            if (updated != null && updated.getId() > 0) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated HealthCareProfessional: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update HealthCareProfessional");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update HealthCareProfessional. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the HealthCareProfessional with the given id.
     * If the HealthCareProfessional is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the HealthCareProfessional to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        hcpService.deleteById(id);
        logger.info("HealthCareProfessional deleted: {}", id);
    }

    /**
     * Gets all instances of HealthCareProfessional.
     * @return List<HealthCareProfessional>.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findAll() {
        ResponseEntity<List<HealthCareProfessional>> response;

        try {
            List<HealthCareProfessional> result = hcpService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all HealthCareProfessional entities: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to Found all HealthCareProfessional entities");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all HealthCareProfessional entities. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a HealthCareProfessional by its id.
     * @param id the unique identifier of the HealthCareProfessional to be retrieved; must not be null or invalid.
     * @return the HealthCareProfessional entity with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<HealthCareProfessional> findById(@PathVariable Long id) {
        ResponseEntity<HealthCareProfessional> response;

        try {
            HealthCareProfessional result = hcpService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found HealthCareProfessional entity: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find HealthCareProfessional entity: {}", id);
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find HealthCareProfessional entity. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds HealthCareProfessional entities by their name.
     * @param name must not be null.
     * @return the HealthCareProfessional entities with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findByName(@PathVariable String name) {
        ResponseEntity<List<HealthCareProfessional>> response;

        try {
            List<HealthCareProfessional> result = hcpService.findByName(name);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all HealthCareProfessional entities by name: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to Found all HealthCareProfessional entities by name");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all HealthCareProfessional entities by name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a HealthCareProfessional by its email.
     * @param email must not be null.
     * @return the HealthCareProfessional with the given email or Optional#empty() if none found.
     * @throws IllegalArgumentException if email is null.
     */
    @GetMapping("/find/email/{email}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findByEmail(@PathVariable String email) {
        ResponseEntity<List<HealthCareProfessional>> response;

        try {
            List<HealthCareProfessional> result = hcpService.findByEmail(email);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all HealthCareProfessional entities by email: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to Found all HealthCareProfessional entities by email");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all HealthCareProfessional entities by email. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds all instances of HealthCareProfessional by their role id.
     * @param id must not be null.
     * @return a List<HealthCareProfessional> with the given role id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/role/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findByRole(@PathVariable Long id) {
        ResponseEntity<List<HealthCareProfessional>> response;

        try {
            List<HealthCareProfessional> result = hcpService.findByRole(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found HealthCareProfessional entities by role: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find HealthCareProfessional entities by role.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find HealthCareProfessional entities by role. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds all instances of HealthCareProfessional by their contact information.
     * @param contactInformation must not be null or blank.
     * @return a List<HealthCareProfessional> with the given contactInformation or Optional#empty() if none found.
     * @throws IllegalArgumentException if the contactInformation is null or blank.
     */
    @GetMapping("/find/contactinformation/{contactInformation}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findByContactInformation(@PathVariable String contactInformation) {
        ResponseEntity<List<HealthCareProfessional>> response;

        try {
            List<HealthCareProfessional> result = hcpService.findByContactInformation(contactInformation);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found HealthCareProfessional entities by contactInformation: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find HealthCareProfessional entities by contactInformation.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find HealthCareProfessional entities by contactInformation. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds all instances of HealthCareProfessional by their position.
     * @param position the unique identifier of the HealthCareProfessional to be retrieved; must not be null or invalid.
     * @return a List<HealthCareProfessional> with the given position or Optional#empty() if none found.
     * @throws IllegalArgumentException if position is null or blank.
     */
    @GetMapping("/find/position/{position}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findByPosition(@PathVariable String position) {
        ResponseEntity<List<HealthCareProfessional>> response;

        try {
            List<HealthCareProfessional> result = hcpService.findByPosition(position);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found HealthCareProfessional entities by position: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find HealthCareProfessional entities by position.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find HealthCareProfessional entities by position. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds all instances of HealthCareProfessional by their affiliation.
     * @param affiliation the unique identifier of the HealthCareProfessional to be retrieved; must not be null or invalid.
     * @return a List<HealthCareProfessional> with the given affiliation or Optional#empty() if none found.
     * @throws IllegalArgumentException if affiliation is null or blank.
     */
    @GetMapping("/find/affiliation/{affiliation}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findByAffiliation(@PathVariable String affiliation) {
        ResponseEntity<List<HealthCareProfessional>> response;

        try {
            List<HealthCareProfessional> result = hcpService.findByAffiliation(affiliation);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found HealthCareProfessional entities by affiliation: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find HealthCareProfessional entities by affiliation.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find HealthCareProfessional entities by affiliation. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds all instances of HealthCareProfessional by their specialties.
     * @param specialties the unique identifier of the HealthCareProfessional to be retrieved; must not be null or invalid.
     * @return a List<HealthCareProfessional> with the given specialties or Optional#empty() if none found.
     * @throws IllegalArgumentException if specialties is null or blank.
     */
    @GetMapping("/find/specialties/{specialties}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<HealthCareProfessional>> findBySpecialties(@PathVariable String specialties) {
        ResponseEntity<List<HealthCareProfessional>> response;

        try {
            List<HealthCareProfessional> result = hcpService.findBySpecialties(specialties);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found HealthCareProfessional entities by specialties: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find HealthCareProfessional entities by specialties.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find HealthCareProfessional entities by specialties. Error: {}", e.getMessage());
        }

        return response;
    }
}
