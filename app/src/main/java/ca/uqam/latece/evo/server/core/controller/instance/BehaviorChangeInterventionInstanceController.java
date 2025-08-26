package ca.uqam.latece.evo.server.core.controller.instance;

import ca.uqam.latece.evo.server.core.controller.AbstractEvoController;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.request.BCIInstanceRequest;
import ca.uqam.latece.evo.server.core.service.instance.BehaviorChangeInterventionInstanceService;

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

/**
 * BehaviorChangeInterventionInstance Controller.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.e.
 */
@Controller
@RequestMapping("/behaviorchangeinterventioninstance")
public class BehaviorChangeInterventionInstanceController extends AbstractEvoController<BehaviorChangeInterventionInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorChangeInterventionInstanceController.class);
    
    @Autowired
    private BehaviorChangeInterventionInstanceService bciInstanceService;

    /**
     * Creates a BehaviorChangeInterventionInstance in the database.
     * @param bciInstance BehaviorChangeInterventionInstance.
     * @return The created BehaviorChangeInterventionInstance in JSON format.
     * @throws IllegalArgumentException if bciInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<BehaviorChangeInterventionInstance> create(@RequestBody BehaviorChangeInterventionInstance bciInstance) {
        ResponseEntity<BehaviorChangeInterventionInstance> response;

        try {
            BehaviorChangeInterventionInstance saved = bciInstanceService.create(bciInstance);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created BehaviorChangeInterventionInstance: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new BehaviorChangeInterventionInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new BehaviorChangeInterventionInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a BehaviorChangeInterventionInstance in the database.
     * @param bciInstance BehaviorChangeInterventionInstance.
     * @return The updated BehaviorChangeInterventionInstance in JSON format.
     * @throws IllegalArgumentException if bciInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<BehaviorChangeInterventionInstance> update(@RequestBody BehaviorChangeInterventionInstance bciInstance) {
        ResponseEntity<BehaviorChangeInterventionInstance> response;

        try {
            BehaviorChangeInterventionInstance updated = bciInstanceService.update(bciInstance);

            if (updated != null && updated.getId().equals(bciInstance.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated BehaviorChangeInterventionInstance: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to update BehaviorChangeInterventionInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update BehaviorChangeInterventionInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates the current phase of a BehaviorChangeInterventionInstance.
     * @param bciInstance the BehaviorChangeInterventionInstance object containing the data to update the current phase.
     * @return a ResponseEntity containing the updated BehaviorChangeInterventionInstance if successful, or a ResponseEntity
     * with an appropriate HTTP status if an error occurs.
     */
    @GetMapping("/changeCurrentPhase/bciInstance")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeInterventionInstance> changeCurrentPhase(@RequestBody BehaviorChangeInterventionInstance bciInstance){
        ResponseEntity<BehaviorChangeInterventionInstance> response;

        try {
            BehaviorChangeInterventionInstance updated = bciInstanceService.changeCurrentPhase(bciInstance);

            if (updated != null && updated.getId().equals(bciInstance.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated current phase of BehaviorChangeInterventionInstance: {}", bciInstance);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to update current phase of BehaviorChangeInterventionInstance.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update current phase of BehaviorChangeInterventionInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates the current phase of a BehaviorChangeInterventionInstance identified by its ID.
     * @param bciInstanceId the unique identifier of the BehaviorChangeInterventionInstance to update.
     * @param currentPhase the new current phase to be set for the specified BehaviorChangeInterventionInstance.
     * @return a ResponseEntity containing the updated BehaviorChangeInterventionInstance and an HTTP status of OK if successful,
     *         or an HTTP status of BAD_REQUEST if the operation fails.
     */
    @GetMapping("/changeCurrentPhase/{bciInstanceId}/currentPhase")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeInterventionInstance> changeCurrentPhase(@PathVariable Long bciInstanceId,
                                                                                 @RequestBody BehaviorChangeInterventionPhaseInstance currentPhase) {
        ResponseEntity<BehaviorChangeInterventionInstance> response;

        try {
            BehaviorChangeInterventionInstance updated = bciInstanceService.changeCurrentPhase(bciInstanceId, currentPhase);

            if (updated != null && updated.getId().equals(bciInstanceId)) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("The BehaviorChangeInterventionInstance id {} updated the current phase to {}", bciInstanceId, updated.getCurrentPhase());
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to update the BehaviorChangeInterventionInstance id {} with the new current phase: {}",bciInstanceId, currentPhase);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update the BehaviorChangeInterventionInstance id {} with the new current phase: {}. Error: {}", bciInstanceId, currentPhase, e.getMessage());
        }

        return response;
    }

    @GetMapping("/find/bciinstanceidstatuspatientid/instanceRequest")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeInterventionInstance> findByIdAndStatusAndPatientId(@RequestBody BCIInstanceRequest instanceRequest) {
        ResponseEntity<BehaviorChangeInterventionInstance> response;

        try {

            ObjectValidator.validateObject(instanceRequest);
            BehaviorChangeInterventionInstance found = bciInstanceService.findByIdAndStatusAndPatientId(instanceRequest.getId(),
                    instanceRequest.getStatus(), instanceRequest.resolvePatientId());

            if (found != null && found.getId().equals(instanceRequest.getId())) {
                response = ResponseEntity.ok(found);
                logger.info("The BehaviorChangeInterventionInstance id: {} associated with the participant id: {}",
                        found.getId(), found.getPatient());
            } else {
                response = ResponseEntity.badRequest().build();
                logger.info("Failed to find the BehaviorChangeInterventionInstance id: {} associated with the participant id: {}",
                        instanceRequest.getId(), instanceRequest.resolvePatientId());
            }
        } catch (Exception e) {
            response = ResponseEntity.badRequest().build();
            logger.error("Failed to find the BehaviorChangeInterventionInstance id: {} associated with the participant id: {}. Error: {}",
                    instanceRequest.getId(), instanceRequest.resolvePatientId(), e.getMessage());
        }

        return response;
    }

    @GetMapping("/find/bciinstanceidstatuspatient/instanceRequest")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeInterventionInstance> findByIdAndStatusAndPatient(@RequestBody BCIInstanceRequest instanceRequest) {
        ResponseEntity<BehaviorChangeInterventionInstance> response;

        try {

            ObjectValidator.validateObject(instanceRequest);
            BehaviorChangeInterventionInstance found = bciInstanceService.findByIdAndStatusAndPatient(instanceRequest.getId(),
                    instanceRequest.getStatus(), instanceRequest.getPatient());

            if (found != null && found.getId().equals(instanceRequest.getId())) {
                response = ResponseEntity.ok(found);
                logger.info("The BehaviorChangeInterventionInstance id: {} and status: {} associated with the participant: {}",
                        found.getId(),found.getStatus(), found.getPatient());
            } else {
                response = ResponseEntity.badRequest().build();
                logger.info("Failed to find the BehaviorChangeInterventionInstance id: {} and status: {} associated with the participant: {}",
                        instanceRequest.getId(), instanceRequest.getStatus(), instanceRequest.getPatient());
            }
        } catch (Exception e) {
            response = ResponseEntity.badRequest().build();
            logger.error("Failed to find the BehaviorChangeInterventionInstance id: {} and status: {} associated with the participant: {}. Error: {}",
                    instanceRequest.getId(), instanceRequest.getStatus(), instanceRequest.getPatient(), e.getMessage());
        }

        return response;
    }

    @GetMapping("/find/bciinstanceidandpatient/instanceRequest")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeInterventionInstance> findByIdAndPatient(@RequestBody BCIInstanceRequest instanceRequest) {
        ResponseEntity<BehaviorChangeInterventionInstance> response;

        try {

            ObjectValidator.validateObject(instanceRequest);
            BehaviorChangeInterventionInstance found = bciInstanceService.findByIdAndPatient(instanceRequest.getId(),
                    instanceRequest.getPatient());

            if (found != null && found.getId().equals(instanceRequest.getId())) {
                response = ResponseEntity.ok(found);
                logger.info("The BehaviorChangeInterventionInstance id: {} associated with the participant: {}",
                        found.getId(), found.getPatient());
            } else {
                response = ResponseEntity.badRequest().build();
                logger.info("Failed to find the BehaviorChangeInterventionInstance id: {} associated with the participant: {}",
                        instanceRequest.getId(), instanceRequest.getPatient());
            }
        } catch (Exception e) {
            response = ResponseEntity.badRequest().build();
            logger.error("Failed to find the BehaviorChangeInterventionInstance id: {} associated with the participant: {}. Error: {}",
                    instanceRequest.getId(), instanceRequest.getPatient(), e.getMessage());
        }

        return response;
    }

    @GetMapping("/find/bciinstanceidandpatientid/instanceRequest")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BehaviorChangeInterventionInstance> findByIdAndPatientId(@RequestBody BCIInstanceRequest instanceRequest) {
        ResponseEntity<BehaviorChangeInterventionInstance> response;

        try {

            ObjectValidator.validateObject(instanceRequest);
            ObjectValidator.validateObject(instanceRequest.getPatient());
            BehaviorChangeInterventionInstance found = bciInstanceService.findByIdAndPatientId(instanceRequest.getId(),
                    instanceRequest.getPatient().getId());

            if (found != null && found.getId().equals(instanceRequest.getId())) {
                response = ResponseEntity.ok(found);
                logger.info("The Behavior Change Intervention Instance id: {} associated with the participant id: {}",
                        found.getId(), found.getPatient().getId());
            } else {
                response = ResponseEntity.badRequest().build();
                logger.info("Failed to find the Behavior Change Intervention Instance id: {} associated with the participant id: {}",
                        instanceRequest.getId(), instanceRequest.getPatientId());
            }
        } catch (Exception e) {
            response = ResponseEntity.badRequest().build();
            logger.error("Failed to find the Behavior Change Intervention Instance id: {} associated with the participant id: {}. Error: {}",
                    instanceRequest.getId(), instanceRequest.getPatientId(), e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a BehaviorChangeInterventionInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteById(@PathVariable Long id) {
        bciInstanceService.deleteById(id);
        logger.info("Deleted BehaviorChangeInterventionInstance: {}", id);
    }

    /**
     * Finds all BehaviorChangeInterventionInstance entities.
     * @return List<BehaviorChangeInterventionInstance> in JSON format.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<List<BehaviorChangeInterventionInstance>> findAll() {
        ResponseEntity<List<BehaviorChangeInterventionInstance>> response;

        try {
            List<BehaviorChangeInterventionInstance> result = bciInstanceService.findAll();

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found all BehaviorChangeInterventionInstance: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all BehaviorChangeInterventionInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all BehaviorChangeInterventionInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a BehaviorChangeInterventionInstance by its id.
     * @param id Long.
     * @return BehaviorChangeInterventionInstance in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<BehaviorChangeInterventionInstance> findById(@PathVariable Long id) {
        ResponseEntity<BehaviorChangeInterventionInstance> response;

        try {
            BehaviorChangeInterventionInstance result = bciInstanceService.findById(id);

            if (result != null && result.getId().equals(id)) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionInstance: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionInstance");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionInstance. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BehaviorChangeInterventionInstance entities by their patient id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/patient/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionInstance>> findByPatientId(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionInstance>> response;

        try {
            List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByPatientId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionInstance entities by patient Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionInstance entities by patient Id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionInstance entities by patient Id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BehaviorChangeInterventionInstance entities by a BCIActivityInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/currentphase/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionInstance>> findByCurrentPhaseId(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionInstance>> response;

        try {
            List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByCurrentPhaseId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionInstance entities by currentPhase Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionInstance entities by currentPhase Id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionInstance entities by currentPhase Id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds BehaviorChangeInterventionInstance entities by a BehaviorChangeInterventionPhaseInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/activities/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionInstance>> findByActivitiesId(@PathVariable Long id) {
        ResponseEntity<List<BehaviorChangeInterventionInstance>> response;

        try {
            List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByActivitiesId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionInstance entities by Activities Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionInstance entities by Activities Id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionInstance entities by Activities Id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a BehaviorChangeInterventionInstance by the id of a behaviorchangeintervention.
     * @param id Long.
     * @return List<BehaviorChangeInterventionInstance> in JSON format.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/behaviorchangeintervention/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<BehaviorChangeInterventionInstance>> findByBehaviorChangeInterventionId(@PathVariable Long id){
        ResponseEntity<List<BehaviorChangeInterventionInstance>> response;

        try {
            List<BehaviorChangeInterventionInstance> result = bciInstanceService.findByBehaviorChangeInterventionId(id);

            if (result != null && !result.isEmpty()) {
                response = new ResponseEntity<>(result, HttpStatus.OK);
                logger.info("Found BehaviorChangeInterventionInstance by Behavior Change Intervention Id: {}", result);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BehaviorChangeInterventionInstance by Behavior Change Intervention Id");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BehaviorChangeInterventionInstance by Behavior Change Intervention Id. Error: {}", e.getMessage());
        }

        return response;
    }
}
