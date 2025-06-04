package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.BCIModule;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.service.BCIModuleService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * BCIModule Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/bcimodule")
public class BCIModuleController extends AbstractEvoController <BCIModule> {
    private static final Logger logger = LoggerFactory.getLogger(BCIModuleController.class);

    @Autowired
    private BCIModuleService moduleService;

    /**
     * Inserts a BCIModule in the database.
     * @param model The BCIModule entity.
     * @return The saved BCIModule.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    @Override
    public ResponseEntity<BCIModule> create(@Valid @RequestBody BCIModule model) {
        ResponseEntity<BCIModule> response;
        try{
            ObjectValidator.validateObject(model);
            BCIModule saved = moduleService.create(model);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new BCIModule: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.error("Failed to create new BCIModule.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new BCIModule. Error: {}", e.getMessage());
        }
        return response;
    }

    /**
     * Updates a BCIModule in the database.
     * @param model The BCIModule entity.
     * @return The saved BCIModule.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<BCIModule> update(@RequestBody BCIModule model) {
        ResponseEntity<BCIModule> response;

        try {
            ObjectValidator.validateObject(model);
            BCIModule updated = moduleService.update(model);

            if (updated != null && updated.getId().equals(model.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated BCIModule: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.error("Failed to update BCIModule.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update BCIModule. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the BCIModule with the given id.
     * <p>
     * If the BCIModule is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the BCIModule to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //
    @Override
    public void deleteById(@PathVariable Long id) {
        moduleService.deleteById(id);
        logger.info("BCIModule deleted: {}", id);
    }

    /**
     * Retrieves a BCIModule by its id.
     * @param id The BCIModule Id to filter BCIModule entities by, must not be null.
     * @return the BCIModule with the given id or Optional#empty() if none found.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<BCIModule> findById(@PathVariable Long id) {
        ResponseEntity<BCIModule> response;

        try {
            ObjectValidator.validateId(id);
            BCIModule module = moduleService.findById(id);

            if (module != null && module.getId().equals(id)) {
                response = new ResponseEntity<>(module, HttpStatus.OK);
                logger.info("Found BCIModule by id: {}", module);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIModule by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIModule. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of BCIModule entities by their name.
     * @param name the type of the BCIModule to search for.
     * @return a list of BCIModule entities matching the specified name.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIModule>> findByName(@PathVariable String name) {
        ResponseEntity<List<BCIModule>> response;

        try {
            ObjectValidator.validateObject(name);
            List<BCIModule> moduleList = moduleService.findByName(name);

            if (moduleList != null && !moduleList.isEmpty()) {
                response = new ResponseEntity<>(moduleList, HttpStatus.OK);
                logger.info("Found BCIModule: {}", moduleList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIModule by name: {}", name);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIModule by name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of BCIModule entities by Skill.
     * @param id the skill id of the BCIModule to search for.
     * @return a list of BCIModule entities matching the specified Skill id.
     */
    @GetMapping("/find/skill/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIModule>>findBySkillId(@PathVariable Long id) {
        ResponseEntity<List<BCIModule>> response;

        try {
            ObjectValidator.validateId(id);
            List<BCIModule> moduleList = moduleService.findBySkillId(id);

            if (moduleList != null && !moduleList.isEmpty()) {
                response = new ResponseEntity<>(moduleList, HttpStatus.OK);
                logger.info("Found BCIModule by Skill Id: {}", moduleList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIModule by Skill Id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIModule by Skill Id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of BCIModule entities by Skill.
     * @param skill the skill of the BCIModule to search for.
     * @return a list of BCIModule entities matching the specified Skill.
     */
    @GetMapping("/find/skill")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIModule>> findBySkill(@RequestBody Skill skill) {
        ResponseEntity<List<BCIModule>> response;

        try {
            ObjectValidator.validateObject(skill);
            List<BCIModule> moduleList = moduleService.findBySkills(skill);

            if (moduleList != null && !moduleList.isEmpty()) {
                response = new ResponseEntity<>(moduleList, HttpStatus.OK);
                logger.info("Found BCIModule by Skill: {}", moduleList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIModule by skill: {}", skill);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIModule by skill. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of BCIModule entities by BehaviorChangeInterventionPhase id.
     * @param id the BehaviorChangeInterventionPhase id of the BCIModule to search for.
     * @return a list of BCIModule entities matching the specified BehaviorChangeInterventionPhase id.
     */
    @GetMapping("/find/behaviorchangeinterventionphase/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIModule>> findByBehaviorChangeInterventionPhasesId(@PathVariable Long id) {
        ResponseEntity<List<BCIModule>> response;

        try{
            ObjectValidator.validateId(id);
            List<BCIModule> moduleList = moduleService.findByBehaviorChangeInterventionPhasesId(id);

            if (moduleList != null && !moduleList.isEmpty()) {
                response = new ResponseEntity<>(moduleList, HttpStatus.OK);
                logger.info("Found BCIModule by Behavior ChangeIntervention Phase Id: {}", moduleList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIModule by Behavior ChangeIntervention Phase Id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIModule by Behavior ChangeIntervention Phase ID. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of BCIModule entities by BehaviorChangeInterventionPhase.
     * @param behaviorChangeInterventionPhases the BehaviorChangeInterventionPhase of the BCIModule to search for.
     * @return a list of BCIModule entities matching the specified BehaviorChangeInterventionPhase.
     */
    @GetMapping("/find/behaviorchangeinterventionphase")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIModule>> findByBehaviorChangeInterventionPhases(@RequestBody BehaviorChangeInterventionPhase behaviorChangeInterventionPhases) {
        ResponseEntity<List<BCIModule>> response;

        try{
            ObjectValidator.validateObject(behaviorChangeInterventionPhases);
            List<BCIModule> moduleList = moduleService.findByBehaviorChangeInterventionPhases(behaviorChangeInterventionPhases);

            if (moduleList != null && !moduleList.isEmpty()) {
                response = new ResponseEntity<>(moduleList, HttpStatus.OK);
                logger.info("Found BCIModule by Behavior ChangeIntervention Phase: {}", moduleList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find BCIModule by Behavior ChangeIntervention Phase: {}", behaviorChangeInterventionPhases);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find BCIModule by Behavior ChangeIntervention Phase. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all BCIModule.
     * @return all BCIModule.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<List<BCIModule>> findAll() {
        ResponseEntity<List<BCIModule>> response;

        try {
            List<BCIModule> moduleList = moduleService.findAll();

            if (moduleList != null && !moduleList.isEmpty()) {
                response = new ResponseEntity<>(moduleList, HttpStatus.OK);
                logger.info("Found all BCIModule : {}", moduleList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all BCIModule list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all BCIModule list. Error: {}", e.getMessage());
        }

        return response;
    }
}