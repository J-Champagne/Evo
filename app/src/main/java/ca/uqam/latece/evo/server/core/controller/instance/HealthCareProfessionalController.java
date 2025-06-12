package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HealthCareProfessional Controller.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Controller
@RequestMapping("/healthcareprofessional")
public class HealthCareProfessionalController extends AbstractEvoController<HealthCareProfessional> {
    private static final Logger logger = LoggerFactory.getLogger(HealthCareProfessionalController.class);

    @Autowired
    HealthCareProfessionalService hcpService;

    /**
     * Creates a HealthCareProfessional in the database.
     * @param hcp HealthCareProfessional.
     * @return The created HealthCareProfessional in JSON format.
     * @throws IllegalArgumentException if hcp is null or if another HealthCareProfessional was saved with the same email.
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
                logger.info("Failed to create new HealthCareProfessional.");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new HealthCareProfessional. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a HealthCareProfessional in the database.
     * @param hcp HealthCareProfessional.
     * @return The updated HealthCareProfessional in JSON format.
     * @throws IllegalArgumentException if hcp is null or if another HealthCareProfessional was saved with the same email.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HealthCareProfessional> update(@RequestBody HealthCareProfessional hcp) {
        ResponseEntity<HealthCareProfessional> response;

        try {
            HealthCareProfessional updated = hcpService.update(hcp);

            if (updated != null && updated.getId() > 0) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated HealthCareProfessional: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update HealthCareProfessional.");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update HealthCareProfessional. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a HealthCareProfessional by its id.
     * Silently ignored if not found.
     * @param id the HealthCareProfessional id.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        hcpService.deleteById(id);
        logger.info("HealthCareProfessional deleted: {}", id);
    }

    /**
     * Finds all HealthCareProfessional entities.
     * @return List of HealthCareProfessionals in JSON format.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HealthCareProfessional>> findAll() {
        ResponseEntity<List<HealthCareProfessional>> response;

        try {
            List<HealthCareProfessional> result = hcpService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all HealthCareProfessional entities: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to Found all HealthCareProfessional entities.");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all HealthCareProfessional entities. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a HealthCareProfessional by its id.
     * @param id the HealthCareProfessional id.
     * @return HealthCareProfessional in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
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
     * @param name the HealthCareProfessional name.
     * @return List of HealthCareProfessionals with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HealthCareProfessional>> findByName(@PathVariable String name) {
        ResponseEntity<List<HealthCareProfessional>> response;

        try {
            List<HealthCareProfessional> result = hcpService.findByName(name);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all HealthCareProfessional entities by name: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to Found all HealthCareProfessional entities by name.");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all HealthCareProfessional entities by name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a HealthCareProfessional by its email.
     * @param email the HealthCareProfessional email.
     * @return HealthCareProfessional in JSON format.
     * @throws IllegalArgumentException if email is null or blank.
     */
    @GetMapping("/find/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HealthCareProfessional> findByEmail(@PathVariable String email) {
        ResponseEntity<HealthCareProfessional> response;

        try {
            HealthCareProfessional result = hcpService.findByEmail(email);

            if (result != null) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found HealthCareProfessional entity by email: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find HealthCareProfessional entity by email.");
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find HealthCareProfessional entity by email. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds HealthCareProfessional entities by their contactInformation.
     * @param contactInformation the HealthCareProfessional contact information.
     * @return List of HealthCareProfessionals in JSON format.
     * @throws IllegalArgumentException if contactInformation is null or blank.
     */
    @GetMapping("/find/contactinformation/{contactInformation}")
    @ResponseStatus(HttpStatus.OK)
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
     * Finds HealthCareProfessional entities by their position.
     * @param position the HealthCareProfessional position.
     * @return List of HealthCareProfessionals in JSON format.
     * @throws IllegalArgumentException if position is null or blank.
     */
    @GetMapping("/find/position/{position}")
    @ResponseStatus(HttpStatus.OK)
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
     * Finds HealthCareProfessional entities by their affiliation.
     * @param affiliation the HealthCareProfessional affiliation.
     * @return List of HealthCareProfessionals in JSON format.
     * @throws IllegalArgumentException if affiliation is null or blank.
     */
    @GetMapping("/find/affiliation/{affiliation}")
    @ResponseStatus(HttpStatus.OK)
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
     * Finds HealthCareProfessional entities by their specialties.
     * @param specialties the HealthCareProfessional specialties.
     * @return List of HealthCareProfessionals in JSON format.
     * @throws IllegalArgumentException if specialties are null or blank.
     */
    @GetMapping("/find/specialties/{specialties}")
    @ResponseStatus(HttpStatus.OK)
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
