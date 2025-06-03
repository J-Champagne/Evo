package ca.uqam.latece.evo.server.core.controller;


import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.BCIModule;
import ca.uqam.latece.evo.server.core.model.ModuleComposedActivity;
import ca.uqam.latece.evo.server.core.service.ModuleComposedActivityService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ModuleComposedActivity Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/modulecomposedactivity")
public class ModuleComposedActivityController extends AbstractEvoController <ModuleComposedActivity> {
    private static final Logger logger = LoggerFactory.getLogger(ModuleComposedActivityController.class);

    @Autowired
    private ModuleComposedActivityService moduleComposedActivityService;

    /**
     * Creates a ModuleComposedActivity in the database.
     * @param model The ModuleComposedActivity entity.
     * @return The saved ModuleComposedActivity.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    @Override
    public ResponseEntity<ModuleComposedActivity> create(@Valid @RequestBody ModuleComposedActivity model) {
        ResponseEntity<ModuleComposedActivity> response;
        try{
            ModuleComposedActivity saved = moduleComposedActivityService.create(model);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new ModuleComposedActivity: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.error("Failed to create new ModuleComposedActivity.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new ModuleComposedActivity. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates a ModuleComposedActivity in the database.
     * @param model The ModuleComposedActivity entity.
     * @return The saved ModuleComposedActivity.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<ModuleComposedActivity> update(@RequestBody ModuleComposedActivity model) {
        ResponseEntity<ModuleComposedActivity> response;

        try{
            ModuleComposedActivity updated = moduleComposedActivityService.update(model);

            if (updated != null && updated.getId() > 0) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated new ModuleComposedActivity: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.error("Failed to Updated new ModuleComposedActivity.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to Updated new ModuleComposedActivity. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the ModuleComposedActivity with the given id.
     * <p>
     * If the ModuleComposedActivity is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the ModuleComposedActivity to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //
    @Override
    public void deleteById(@PathVariable Long id) {
        moduleComposedActivityService.deleteById(id);
        logger.info("ModuleComposedActivity deleted: {}", id);
    }

    /**
     * Retrieves a ModuleComposedActivity by its id.
     * @param id The ModuleComposedActivity Id to filter ModuleComposedActivity entities by, must not be null.
     * @return the ModuleComposedActivity with the given id.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<ModuleComposedActivity> findById(@PathVariable Long id) {
        ResponseEntity<ModuleComposedActivity> response;

        try {
            ModuleComposedActivity found = moduleComposedActivityService.findById(id);

            if (found != null && found.getId().equals(id)) {
                response = new ResponseEntity<>(found, HttpStatus.OK);
                logger.info("Found ModuleComposedActivity by id: {}", found);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find ModuleComposedActivity by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find ModuleComposedActivity. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of ModuleComposedActivity entities by the BCIModule Id.
     * @param id The BCIModule Id.
     * @return the ModuleComposedActivity with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/bcimodule/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<ModuleComposedActivity>> findByComposedActivityBciModuleId(@PathVariable Long id) {
        ResponseEntity<List<ModuleComposedActivity>> response;

        try {
            List<ModuleComposedActivity> foundList = moduleComposedActivityService.findByComposedActivityBciModuleId(id);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found ModuleComposedActivity by BciModule id: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find ModuleComposedActivity by BciModuleI id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find ModuleComposedActivity BciModule id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of ModuleComposedActivity entities by the BCIModule association.
     * @param bciModule The BCIModule object.
     * @return the ModuleComposedActivity with the given BCIModule.
     * @throws IllegalArgumentException if module is null.
     */
    @GetMapping("/find/bcimodule")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<ModuleComposedActivity>> findByComposedActivityBciModule(@RequestBody BCIModule bciModule) {
        ResponseEntity<List<ModuleComposedActivity>> response;

        try {
            List<ModuleComposedActivity> foundList = moduleComposedActivityService.findByComposedActivityBciModule(bciModule);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found ModuleComposedActivity by BCIModule: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find ModuleComposedActivity by BCIModule: {}", bciModule);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find ModuleComposedActivity BCIModule. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of ModuleComposedActivity entities by the BCIActivity association.
     * @param bciActivity The BCIActivity object.
     * @return the ModuleComposedActivity with the given BCIActivity.
     * @throws IllegalArgumentException if module is null.
     */
    @GetMapping("/find/bciactivity")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<ModuleComposedActivity>> findByComposedModuleBciActivity(@RequestBody BCIActivity bciActivity) {
        ResponseEntity<List<ModuleComposedActivity>> response;

        try {
            List<ModuleComposedActivity> foundList = moduleComposedActivityService.findByComposedModuleBciActivity(bciActivity);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found ModuleComposedActivity by BCIActivity: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find ModuleComposedActivity by BCIActivity: {}", bciActivity);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find ModuleComposedActivity BCIActivity. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of ModuleComposedActivity entities by the BCIActivity Id.
     * @param id The BCIActivity Id.
     * @return the ModuleComposedActivity with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @GetMapping("/find/bciactivity/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<ModuleComposedActivity>> findByComposedModuleBciActivityId(@PathVariable Long id) {
        ResponseEntity<List<ModuleComposedActivity>> response;

        try {
            List<ModuleComposedActivity> foundList = moduleComposedActivityService.findByComposedModuleBciActivityId(id);

            if (foundList != null && !foundList.isEmpty()) {
                response = new ResponseEntity<>(foundList, HttpStatus.OK);
                logger.info("Found ModuleComposedActivity by BCIActivity Id: {}", foundList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find ModuleComposedActivity by BCIActivity Id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find ModuleComposedActivity BCIActivity Id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all ModuleComposedActivity.
     * @return all ModuleComposedActivity.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<List<ModuleComposedActivity>> findAll() {
        ResponseEntity<List<ModuleComposedActivity>> response;

        try {
            List<ModuleComposedActivity> found = moduleComposedActivityService.findAll();

            if (found != null && !found.isEmpty()) {
                response = new ResponseEntity<>(found, HttpStatus.OK);
                logger.info("Found all ModuleComposedActivity : {}", found);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all ModuleComposedActivity list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all ModuleComposedActivity list. Error: {}", e.getMessage());
        }

        return response;
    }
}
