package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.PatientAssessment;
import ca.uqam.latece.evo.server.core.service.instance.PatientAssessmentService;
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
 * PatientMedicalFile Controller.
 * @author Julien Champagne.
 */
@Controller
@RequestMapping("/patientassessment")
public class PatientAssessmentController extends AbstractEvoController<PatientAssessment> {
    private static final Logger logger = LoggerFactory.getLogger(PatientAssessmentController.class);

    @Autowired
    PatientAssessmentService patientAssessmentService;

    /**
     * Inserts a PatientAssessment in the database.
     * @param pa the PatientAssessment entity.
     * @return PatientAssessment instance that was saved.
     * @throws IllegalArgumentException in case the given PatientAssessment is null.
     * @throws OptimisticLockingFailureException when the PatientAssessment uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<PatientAssessment> create(@RequestBody PatientAssessment pa) {
        ResponseEntity<PatientAssessment> response;

        try {
            ObjectValidator.validateObject(pa);
            PatientAssessment saved = patientAssessmentService.create(pa);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created PatientAssessment: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to create new PatientAssessment");
            }
        } catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new PatientAssessment. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a PatientAssessment in the database.
     * @param pa the PatientAssessment entity.
     * @return PatientAssessment instance that was updated.
     * @throws IllegalArgumentException in case the given PatientAssessment is null.
     * @throws OptimisticLockingFailureException when the PatientAssessment uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<PatientAssessment> update(@RequestBody PatientAssessment pa) {
        ResponseEntity<PatientAssessment> response;

        try {
            ObjectValidator.validateObject(pa);
            PatientAssessment updated = patientAssessmentService.update(pa);

            if (updated != null && updated.getId().equals(pa.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated PatientAssessment: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update PatientAssessment");
            }
        } catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update PatientAssessment. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the PatientAssessment with the given id.
     * If the PatientAssessment is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the PatientAssessment to be deleted; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        patientAssessmentService.deleteById(id);
        logger.info("PatientAssessment deleted: {}", id);
    }

    /**
     * Gets all PatientAssessment entities.
     * @return all PatientAssessment.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<PatientAssessment>> findAll() {
        ResponseEntity<List<PatientAssessment>> response;

        try {
            List<PatientAssessment> result = patientAssessmentService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all PatientAssessment entities: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all PatientAssessment entities.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all PatientAssessment entities. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds an PatientAssessment by its id.
     * @param id must not be null or invalid.
     * @return the PatientAssessment with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if the id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<PatientAssessment> findById(@PathVariable Long id) {
        ResponseEntity<PatientAssessment> response;

        try {
            ObjectValidator.validateId(id);
            PatientAssessment result = patientAssessmentService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found PatientAssessment: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find PatientAssessment: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find PatientAssessment. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds an PatientAssessment by its date of creation.
     * @param date must not be null.
     * @return the PatientAssessment with the given date or Optional#empty() if none found.
     * @throws IllegalArgumentException if the date is null.
     */
    @GetMapping("/find/date/{date}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<PatientAssessment>> findByDate(@PathVariable String date) {
        ResponseEntity<List<PatientAssessment>> response;

        try {
            LocalDate paramDate = LocalDate.parse(date);
            List<PatientAssessment> result = patientAssessmentService.findByDate(paramDate);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found PatientAssessment entities by date: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find PatientAssessment entities by date.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find PatientAssessment entities by date. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds an PatientAssessment by its medical assessment.
     * @param assessment must not be null or blank.
     * @return the PatientAssessment with the given assessment or Optional#empty() if none found.
     * @throws IllegalArgumentException if the assessment is null or blank.
     */
    @GetMapping("/find/assessment/{assessment}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<PatientAssessment>> findByAssessment(@PathVariable String assessment) {
        ResponseEntity<List<PatientAssessment>> response;

        try {
            List<PatientAssessment> result = patientAssessmentService.findByAssessment(assessment);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found PatientAssessment entities by medicalHistory: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find PatientAssessment entities by medicalHistory.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find PatientAssessment entities by medicalHistory. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds an PatientAssessment by the id of its patient.
     * @param id must not be null.
     * @return the PatientAssessment with the given patient id or Optional#empty() if none found.
     * @throws IllegalArgumentException if the id is null.
     */
    @GetMapping("/find/patient/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<PatientAssessment>> findByPatient(@PathVariable Long id) {
        ResponseEntity<List<PatientAssessment>> response;

        try {
            List<PatientAssessment> result = patientAssessmentService.findByPatient(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found PatientAssessment entities by patient: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find PatientAssessment entities by patient.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find PatientAssessment entities by patient. Error: {}", e.getMessage());
        }
        return response;
    }
}
