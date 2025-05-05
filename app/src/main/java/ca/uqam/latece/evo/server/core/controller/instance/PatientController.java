package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.service.instance.PatientService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController extends AbstractEvoController<Patient> {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    /**
     * Inserts a Patient in the database.
     * @param patient the Patient entity.
     * @return Patient instance that was saved.
     * @throws IllegalArgumentException in case the given Patient is null.
     * @throws OptimisticLockingFailureException when the Patient uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Patient> create(@RequestBody Patient patient) {
        ResponseEntity<Patient> response;

        try {
            ObjectValidator.validateObject(patient);
            Patient saved = patientService.create(patient);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created Patient: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to create new Patient");
            }
        } catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new Patient. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a Patient in the database.
     * @param patient the Patient entity.
     * @return Patient instance that was updated.
     * @throws IllegalArgumentException in case the given Patient is null.
     * @throws OptimisticLockingFailureException when the Patient uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Patient> update(@RequestBody Patient patient) {
        ResponseEntity<Patient> response;

        try {
            ObjectValidator.validateObject(patient);
            Patient updated = patientService.update(patient);

            if (updated != null && updated.getId().equals(patient.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated Patient: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update Patient");
            }
        } catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update Patient. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the Patient with the given id.
     * If the Patient is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Patient to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        patientService.deleteById(id);
        logger.info("Patient deleted: {}", id);
    }

    /**
     * Gets all instances of Patient.
     * @return List<Patient>.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Patient>> findAll() {
        ResponseEntity<List<Patient>> response;

        try {
            List<Patient> result = patientService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all Patient entities: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all Patient entities.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all Patient entities. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a Patient by its id.
     * @param id the unique identifier of the Patient to be retrieved; must not be null or invalid.
     * @return Patient instance with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Patient> findById(@PathVariable Long id) {
        ResponseEntity<Patient> response;

        try {
            ObjectValidator.validateId(id);
            Patient result = patientService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Patient entity: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Patient entity: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Patient entity. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds Patient entities by their name.
     * @param name must not be null.
     * @return List<Patient> with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Patient>> findByName(@PathVariable String name) {
        ResponseEntity<List<Patient>> response;

        try {
            List<Patient> result = patientService.findByName(name);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Patient entities by name: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Patient entities by name.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Patient entities by name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a Patient by its email.
     * @param email must not be null.
     * @return Patient instance with the given email or Optional#empty() if none found.
     * @throws IllegalArgumentException if email is null.
     */
    @GetMapping("/find/email/{email}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Patient>> findByEmail(@PathVariable String email) {
        ResponseEntity<List<Patient>> response;

        try {
            List<Patient> result = patientService.findByEmail(email);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Patient entity by email: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Patient entity by email.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Patient entity by email. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds Patient entities by their contactInformation.
     * @param contactInformation must not be null or blank.
     * @return List<Patient> with the given contactInformation or Optional#empty() if none found.
     * @throws IllegalArgumentException if the contactInformation is null or blank.
     */
    @GetMapping("/find/contactInformation/{contactInformation}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Patient>> findByContactInformation(@PathVariable String contactInformation) {
        ResponseEntity<List<Patient>> response;

        try {
            List<Patient> result = patientService.findByContactInformation(contactInformation);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Patient entities by contactInformation: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Patient entities by contactInformation.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Patient entities by contactInformation. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds Patient entities by their role id.
     * @param roleId must not be null.
     * @return List<Patient> with the given roleId or Optional#empty() if none found.
     * @throws IllegalArgumentException if roleId is null.
     */
    @GetMapping("/find/role/{roleId}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Patient>> findByRole(@PathVariable Long roleId) {
        ResponseEntity<List<Patient>> response;

        try {
            List<Patient> result = patientService.findByRole(roleId);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Patient entities by roleId: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Patient entities by roleId.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Patient entities by roleId. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds Patient entities by their birthdate.
     * @param birthdate must not be null or blank.
     * @return List<Patient> with the given birthdate or Optional#empty() if none found.
     * @throws IllegalArgumentException if the birthdate is null or blank.
     */
    @GetMapping("/find/birthdate/{birthdate}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Patient>> findByBirthdate(@PathVariable String birthdate) {
        ResponseEntity<List<Patient>> response;

        try {
            List<Patient> result = patientService.findByBirthdate(birthdate);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Patient entities by birthdate: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Patient entities by birthdate.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Patient entities by birthdate. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds Patient entities by their occupation.
     * @param occupation must not be null or blank.
     * @return List<Patient> with the given occupation or Optional#empty() if none found.
     * @throws IllegalArgumentException if the occupation is null or blank.
     */
    @GetMapping("/find/occupation/{occupation}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Patient>> findByOccupation(@PathVariable String occupation) {
        ResponseEntity<List<Patient>> response;

        try {
            List<Patient> result = patientService.findByOccupation(occupation);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Patient entities by occupation: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Patient entities by occupation.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Patient entities by occupation. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds Patient entities by their address.
     * @param address must not be null or blank.
     * @return List<Patient> with the given address or Optional#empty() if none found.
     * @throws IllegalArgumentException if the address is null or blank.
     */
    @GetMapping("/find/address/{address}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Patient>> findByAddress(@PathVariable String address) {
        ResponseEntity<List<Patient>> response;

        try {
            List<Patient> result = patientService.findByAddress(address);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Patient entities by address: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Patient entities by address.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Patient entities by address. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a Patient by the id of its medical file.
     * @param id the unique identifier of the medical file of the patient to be retrieved; must not be null or invalid.
     * @return Patient instance with the given patient medical file id or Optional#empty() if none found.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/patientmedicalfile/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Patient> findByPatientMedicalFile(@PathVariable Long id) {
        ResponseEntity<Patient> response;

        try {
            ObjectValidator.validateId(id);
            Patient result = patientService.findByPatientMedicalFile(id);

            if (result != null && result.getMedicalFile().getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Patient entity by PatientMedicalFile: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Patient entity by PatientMedicalFile: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Patient entity by PatientMedicalFile. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds Patient entities by the medicalHistory of their PatientMedicalFile.
     * @param medicalHistory must not be null or blank.
     * @return List<Patient> with the given medicalHistory or Optional#empty() if none found.
     * @throws IllegalArgumentException if the medicalHistory is null or blank.
     */
    @GetMapping("/find/patientmedicalfile/medicalhistory/{medicalHistory}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Patient>> findByMedicalHistory(@PathVariable String medicalHistory) {
        ResponseEntity<List<Patient>> response;

        try {
            List<Patient> result = patientService.findByMedicalHistory(medicalHistory);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Patient entities by medicalHistory: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Patient entities by medicalHistory.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Patient entities by medicalHistory. Error: {}", e.getMessage());
        }

        return response;
    }
}
