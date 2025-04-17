package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.service.instance.PatientMedicalFileService;
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
import java.util.Date;
import java.util.List;

/**
 * PatientMedicalFile Controller.
 * @author Julien Champagne.
 */
@Controller
@RequestMapping("/patientmedicalfile")
public class PatientMedicalFileController extends AbstractEvoController <PatientMedicalFile> {
    private static final Logger logger = LoggerFactory.getLogger(PatientMedicalFileController.class);

    @Autowired
    private PatientMedicalFileService patientMedicalFileService;

    /**
     * Inserts an PatientMedicalFile in the database.
     * @param medicalFile the PatientMedicalFile entity.
     * @return The inserted PatientMedicalFile.
     * @throws IllegalArgumentException in case the given PatientMedicalFile is null.
     * @throws OptimisticLockingFailureException when the PatientMedicalFile uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<PatientMedicalFile> create(@RequestBody PatientMedicalFile medicalFile) {
        ResponseEntity<PatientMedicalFile> response;

        try {
            ObjectValidator.validateObject(medicalFile);
            PatientMedicalFile saved = patientMedicalFileService.create(medicalFile);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created PatientMedicalFile: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to create new PatientMedicalFile");
            }
        } catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new PatientMedicalFile. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates an PatientMedicalFile in the database.
     * @param medicalFile the PatientMedicalFile entity.
     * @return The updated PatientMedicalFile.
     * @throws IllegalArgumentException in case the given PatientMedicalFile is null.
     * @throws OptimisticLockingFailureException when the PatientMedicalFile uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<PatientMedicalFile> update(@RequestBody PatientMedicalFile medicalFile) {
        ResponseEntity<PatientMedicalFile> response;

        try {
            ObjectValidator.validateObject(medicalFile);
            PatientMedicalFile updated = patientMedicalFileService.update(medicalFile);

            if (updated != null && updated.getId().equals(medicalFile.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated PatientMedicalFile: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update PatientMedicalFile");
            }
        } catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update PatientMedicalFile. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the PatientMedicalFile with the given id.
     * If the PatientMedicalFile is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the PatientMedicalFile to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        patientMedicalFileService.deleteById(id);
        logger.info("PatientMedicalFile deleted: {}", id);
    }

    /**
     * Gets all PatientMedicalFile entities.
     * @return all PatientMedicalFile.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<PatientMedicalFile>> findAll() {
        ResponseEntity<List<PatientMedicalFile>> response;

        try {
            List<PatientMedicalFile> result = patientMedicalFileService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all PatientMedicalFile entities: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all PatientMedicalFile entities.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all PatientMedicalFile entities. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds an PatientMedicalFile by its id.
     * @param id must not be null or invalid.
     * @return the PatientMedicalFile with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if the id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<PatientMedicalFile> findById(@PathVariable Long id) {
        ResponseEntity<PatientMedicalFile> response;

        try {
            ObjectValidator.validateId(id);
            PatientMedicalFile result = patientMedicalFileService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found PatientMedicalFile: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find PatientMedicalFile: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find PatientMedicalFile. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds an PatientMedicalFile by its date of creation.
     * @param date must not be null.
     * @return the PatientMedicalFile with the given date or Optional#empty() if none found.
     * @throws IllegalArgumentException if the date is null.
     */
    @GetMapping("/find/date/{date}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<PatientMedicalFile>> findByDate(@PathVariable String date) {
        ResponseEntity<List<PatientMedicalFile>> response;

        try {
            LocalDate paramDate = LocalDate.parse(date);
            List<PatientMedicalFile> result = patientMedicalFileService.findByDate(paramDate);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found PatientMedicalFile entities by date: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find PatientMedicalFile entities by date.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find PatientMedicalFile entities by date. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Finds an PatientMedicalFile by its medical history.
     * @param medicalHistory must not be null or blank.
     * @return the PatientMedicalFile with the given medicalHistory or Optional#empty() if none found.
     * @throws IllegalArgumentException if the medicalHistory is null or blank.
     */
    @GetMapping("/find/medicalhistory/{medicalHistory}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<PatientMedicalFile>> findByMedicalHistory(@PathVariable String medicalHistory) {
        ResponseEntity<List<PatientMedicalFile>> response;

        try {
            List<PatientMedicalFile> result = patientMedicalFileService.findByMedicalHistory(medicalHistory);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found PatientMedicalFile entities by medicalHistory: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find PatientMedicalFile entities by medicalHistory.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find PatientMedicalFile entities by medicalHistory. Error: {}", e.getMessage());
        }
        return response;
    }
}
