package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.BCIReferral;
import ca.uqam.latece.evo.server.core.service.instance.BCIReferralService;

import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * BCIReferral Controller.
 * @author Julien Champagne.
 */
@Controller
@RequestMapping("/bcireferral")
public class BCIReferralController extends AbstractEvoController<BCIReferral> {
    private static final Logger logger = LoggerFactory.getLogger(BCIReferralController.class);

    @Autowired
    BCIReferralService bciReferralService;

    /**
     * Creates a BCIReferral in the database.
     * @param bcir BCIReferral.
     * @return The created BCIReferral in JSON format.
     * @throws IllegalArgumentException if bcir is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BCIReferral> create(@RequestBody BCIReferral bcir) {
        ResponseEntity<BCIReferral> response;

        try {
            ObjectValidator.validateObject(bcir);
            BCIReferral saved = bciReferralService.create(bcir);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created BCIReferral: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to create new BCIReferral");
            }
        } catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new BCIReferral. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a BCIReferral in the database.
     * @param bcir BCIReferral.
     * @return The updated BCIReferral in JSON format.
     * @throws IllegalArgumentException if bcir is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BCIReferral> update(@RequestBody BCIReferral bcir) {
        ResponseEntity<BCIReferral> response;

        try {
            ObjectValidator.validateObject(bcir);
            BCIReferral updated = bciReferralService.update(bcir);

            if (updated != null && updated.getId().equals(bcir.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated BCIReferral: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update BCIReferral");
            }
        } catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update BCIReferral. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a BCIReferral by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        bciReferralService.deleteById(id);
        logger.info("BCIReferral deleted: {}", id);
    }

    /**
     * Finds all BCIReferral entities.
     * @return List<BCIReferral> in JSON format.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BCIReferral>> findAll() {
        ResponseEntity<List<BCIReferral>> response;

        try {
            List<BCIReferral> result = bciReferralService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all BCIReferral entities: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all BCIReferral entities.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all BCIReferral entities. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds a BCIReferral by its id.
     * @param id Long.
     * @return List<BCIReferral> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BCIReferral> findById(@PathVariable Long id) {
        ResponseEntity<BCIReferral> response;

        try {
            ObjectValidator.validateId(id);
            BCIReferral result = bciReferralService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BCIReferral: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIReferral: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIReferral. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds BCIReferral entities by their Patient.
     * @param date LocalDate.
     * @return List<BCIReferral> in JSON format.
     * @throws IllegalArgumentException if date is null.
     */
    @GetMapping("/find/date/{date}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BCIReferral>> findByDate(@PathVariable String date) {
        ResponseEntity<List<BCIReferral>> response;

        try {
            LocalDate paramDate = LocalDate.parse(date);
            List<BCIReferral> result = bciReferralService.findByDate(paramDate);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BCIReferral entities by date: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIReferral entities by date.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIReferral entities by date. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds BCIReferral entities by their reason.
     * @param reason String.
     * @return List<BCIReferral> in JSON format.
     * @throws IllegalArgumentException if reason is blank or null.
     */
    @GetMapping("/find/reason/{reason}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BCIReferral>> findByReason(@PathVariable String reason) {
        ResponseEntity<List<BCIReferral>> response;

        try {
            List<BCIReferral> result = bciReferralService.findByReason(reason);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BCIReferral entities by reason: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIReferral entities by reason.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIReferral entities by reason. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds BCIReferral entities by their Patient id.
     * @param id Long.
     * @return List<BCIReferral> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BCIReferral>> findByPatient(@PathVariable Long id) {
        ResponseEntity<List<BCIReferral>> response;

        try {
            List<BCIReferral> result = bciReferralService.findByPatientId(id);

            if (result != null) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BCIReferral entity by patient: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIReferral entity by patient.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIReferral entity by patient " +
                    ". Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds BCIReferral entities by their PatientAssessment id.
     * @param id Long.
     * @return List<BCIReferral> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/patientassessment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BCIReferral>> findByPatientAssessment(@PathVariable Long id) {
        ResponseEntity<List<BCIReferral>> response;

        try {
            List<BCIReferral> result = bciReferralService.findByPatientAssessmentId(id);

            if (result != null) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BCIReferral entity by patient assessment: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIReferral entity by patient assessment.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIReferral entity by patient assessment. " +
                    "Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds BCIReferral entities by their ReferringProfessional id.
     * @param id Long.
     * @return List<BCIReferral> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/referringprofessional/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BCIReferral>> findByReferringProfessional(@PathVariable Long id) {
        ResponseEntity<List<BCIReferral>> response;

        try {
            List<BCIReferral> result = bciReferralService.findByReferringProfessionalId(id);

            if (result != null) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BCIReferral entity by referring health care professional: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIReferral entity by referring health care professional.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIReferral entity by patient referring health care " +
                    "professional. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds BCIReferral entities by their BehaviorChangeInterventionist id.
     * @param id Long.
     * @return List<BCIReferral> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/behaviorchangeinterventionist/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BCIReferral>> findByBehaviorChangeInterventionist(@PathVariable Long id) {
        ResponseEntity<List<BCIReferral>> response;

        try {
            List<BCIReferral> result = bciReferralService.findByBehaviorChangeInterventionistId(id);

            if (result != null) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BCIReferral entity by health care professional interventionist: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIReferral entity by health care professional interventionist.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIReferral entity by health care professional interventionist " +
                    ". Error: {}", e.getMessage());
        }
        return response;
    }
}
