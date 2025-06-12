package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.Patient;
import ca.uqam.latece.evo.server.core.model.instance.PatientMedicalFile;
import ca.uqam.latece.evo.server.core.service.instance.PatientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Patient Controller.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Controller
@RequestMapping("/patient")
public class PatientController extends AbstractEvoController<Patient> {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    /**
     * Creates a Patient in the database.
     * @param patient Patient.
     * @return The created Patient in JSON format.
     * @throws IllegalArgumentException if a patient is null or if another Patient was saved with the same email.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Patient> create(@RequestBody Patient patient) {
        ResponseEntity<Patient> response;

        try {
            Patient saved = patientService.create(patient);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created Patient: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to create new Patient.");
            }
        } catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new Patient. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a Patient in the database.
     * @param patient Patient.
     * @return The updated Patient in JSON format.
     * @throws IllegalArgumentException if a patient is null or if another Patient was saved with the same email.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Patient> update(@RequestBody Patient patient) {
        ResponseEntity<Patient> response;

        try {
            Patient updated = patientService.update(patient);

            if (updated != null && updated.getId().equals(patient.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated Patient: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update Patient.");
            }
        } catch (Exception e){
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update Patient. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a Patient by its id.
     * Silently ignored if not found.
     * @param id the patient id.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        patientService.deleteById(id);
        logger.info("Patient deleted: {}", id);
    }

    /**
     * Finds all Patient entities.
     * @return List of Patients in JSON format.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
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
     * @param id the patient id.
     * @return Patient in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Patient> findById(@PathVariable Long id) {
        ResponseEntity<Patient> response;

        try {
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
     * @param name the patient name.
     * @return List of Patients with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK)
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
     * @param email the patient email.
     * @return Patient in JSON format.
     * @throws IllegalArgumentException if email is null or blank.
     */
    @GetMapping("/find/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Patient> findByEmail(@PathVariable String email) {
        ResponseEntity<Patient> response;

        try {
            Patient result = patientService.findByEmail(email);

            if (result != null) {
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
     * @param contactInformation the patient contact information.
     * @return List of Patients in JSON format.
     * @throws IllegalArgumentException if contactInformation is null or blank.
     */
    @GetMapping("/find/contactinformation/{contactInformation}")
    @ResponseStatus(HttpStatus.OK)
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
     * Finds Patient entities by their birthdate.
     * @param birthdate the patient birthdate.
     * @return List of Patients in JSON format.
     * @throws IllegalArgumentException if birthdate is null or blank.
     */
    @GetMapping("/find/birthdate/{birthdate}")
    @ResponseStatus(HttpStatus.OK)
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
     * @param occupation the patient occupation.
     * @return List of Patients in JSON format.
     * @throws IllegalArgumentException if occupation is null or blank.
     */
    @GetMapping("/find/occupation/{occupation}")
    @ResponseStatus(HttpStatus.OK)
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
     * @param address the patient address.
     * @return List of Patients in JSON format.
     * @throws IllegalArgumentException if an address is null or blank.
     */
    @GetMapping("/find/address/{address}")
    @ResponseStatus(HttpStatus.OK)
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
     * Finds a Patient by its PatientMedicalFile.
     * @param pmf the Patient Medical File.
     * @return Patient in JSON format.
     * @throws IllegalArgumentException if pmf is null.
     */
    @GetMapping("/find/patientmedicalfile")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Patient> findByPatientMedicalFile(@RequestBody PatientMedicalFile pmf) {
        ResponseEntity<Patient> response;

        try {
            Patient result = patientService.findByPatientMedicalFile(pmf);

            if (result != null) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Patient entity by PatientMedicalFile: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Patient entity by PatientMedicalFile.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Patient entity by PatientMedicalFile. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a Patient by its PatientMedicalFile id.
     * @param id the Patient Medical File id.
     * @return Patient in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/patientmedicalfile/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Patient> findByPatientMedicalFileId(@PathVariable Long id) {
        ResponseEntity<Patient> response;

        try {
            Patient result = patientService.findByPatientMedicalFileId(id);

            if (result != null) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found Patient entity by PatientMedicalFile id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Patient entity by PatientMedicalFile id.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Patient entity by PatientMedicalFile id. Error: {}", e.getMessage());
        }

        return response;
    }
}
