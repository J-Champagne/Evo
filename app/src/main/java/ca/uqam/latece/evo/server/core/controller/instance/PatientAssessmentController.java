package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
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
     * Creates a PatientAssessment in the database.
     * @param pa PatientAssessment.
     * @return The created PatientAssessment in JSON format.
     * @throws IllegalArgumentException if pa is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<PatientAssessment> create(@RequestBody PatientAssessment pa) {
        ResponseEntity<PatientAssessment> response;

        try {
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
     * @param pa PatientAssessment.
     * @return The updated PatientAssessment in JSON format.
     * @throws IllegalArgumentException if pa is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<PatientAssessment> update(@RequestBody PatientAssessment pa) {
        ResponseEntity<PatientAssessment> response;

        try {
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
     * Deletes a PatientAssessment by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable Long id) {
        patientAssessmentService.deleteById(id);
        logger.info("PatientAssessment deleted: {}", id);
    }

    /**
     * Finds all PatientAssessment entities.
     * @return List<PatientAssessment> in JSON format.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
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
     * Finds a PatientAssessment by its id.
     * @param id Long.
     * @return PatientAssessment in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
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
     * Finds PatientAssessment entities by their date.
     * @param date String.
     * @return List<PatientAssessment> in JSON format.
     * @throws IllegalArgumentException if date is null.
     */
    @GetMapping("/find/date/{date}")
    @ResponseStatus(HttpStatus.OK)
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
     * Finds PatientAssessment entities by their assessment.
     * @param assessment String.
     * @return List<PatientAssessment> in JSON format.
     * @throws IllegalArgumentException if assessment is null or blank.
     */
    @GetMapping("/find/assessment/{assessment}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PatientAssessment>> findByAssessment(@PathVariable String assessment) {
        ResponseEntity<List<PatientAssessment>> response;

        try {
            List<PatientAssessment> result = patientAssessmentService.findByAssessment(assessment);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found PatientAssessment entities by assessment: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find PatientAssessment entities by assessment.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find PatientAssessment entities by assessment. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds PatientAssessment entities by their Patient.
     * @param patient Patient.
     * @return List<PatientAssessment> in JSON format.
     * @throws IllegalArgumentException if patient is null.
     */
    @GetMapping("/find/patient")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PatientAssessment>> findByPatient(@RequestBody Patient patient) {
        ResponseEntity<List<PatientAssessment>> response;

        try {
            List<PatientAssessment> result = patientAssessmentService.findByPatient(patient);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found PatientAssessment entities by Patient: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find PatientAssessment entities by Patient.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find PatientAssessment entities by Patient. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds PatientAssessment entities by their Patient id.
     * @param id Long.
     * @return List<PatientAssessment> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PatientAssessment>> findByPatient(@PathVariable Long id) {
        ResponseEntity<List<PatientAssessment>> response;

        try {
            List<PatientAssessment> result = patientAssessmentService.findByPatientId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found PatientAssessment entities by Patient id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find PatientAssessment entities by Patient id.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find PatientAssessment entities by Patient id. Error: {}", e.getMessage());
        }
        return response;
    }
}
