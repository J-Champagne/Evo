package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.model.Develops;
import ca.uqam.latece.evo.server.core.service.DevelopsService;

import java.util.List;

import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;

/**
 * Develops Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/develops")
public class DevelopsController extends AbstractEvoController<Develops> {
    private static final Logger logger = LoggerFactory.getLogger(DevelopsController.class);

    @Autowired
    private DevelopsService developsService;

    /**
     * Inserts a Develops in the database.
     * @param develops the Develops entity.
     * @return The saved Develops.
     * @throws IllegalArgumentException in case the given Develops is null.
     * @throws OptimisticLockingFailureException when the Develops uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Develops> create(@RequestBody Develops develops) {
        ResponseEntity<Develops> response;

        try {
            ObjectValidator.validateObject(develops);
            Develops saved = developsService.create(develops);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new Develops: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new Develops.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new Develops. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Update a Develops in the database.
     * @param develops the Develops entity.
     * @return The saved Develops.
     * @throws IllegalArgumentException in case the given Develops is null.
     * @throws OptimisticLockingFailureException when the Develops uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Develops> update(@RequestBody Develops develops) {
        ResponseEntity<Develops> response;

        try {
            ObjectValidator.validateObject(develops);
            Develops updated = developsService.update(develops);

            if (updated != null && updated.getId().equals(develops.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated Develops: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update Develops.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update Develops. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the Develops with the given id.
     * If the Develops is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Develops to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        developsService.deleteById(id);
        logger.info("Develops deleted: {}", id);
    }

    /**
     * Retrieves a Develops by its id.
     * @param id The Content Id to filter Develops entities by, must not be null.
     * @return the Develops with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     * @throws EntityNotFoundException in case the given Develops not found.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Develops> findById(@PathVariable Long id) {
        ResponseEntity<Develops> response;

        try {
            ObjectValidator.validateId(id);
            Develops develops = developsService.findById(id);

            if (develops != null && develops.getId().equals(id)) {
                response = new ResponseEntity<>(develops, HttpStatus.OK);
                logger.info("Found develops: {}", develops);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find develops by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find develops by id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Develops entities that match the specified skill level.
     * @param level the SkillLevel to filter Develops entities by, must not be null.
     * @return a list of Develops entities that have the specified skill level, or an empty list if no matches are found.
     */
    @GetMapping("/find/level/{level}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Develops>> findByLevel(@PathVariable SkillLevel level){
        ResponseEntity<List<Develops>> response;

        try {
            List<Develops> developsList = developsService.findByLevel(level);

            if (developsList != null && !developsList.isEmpty()) {
                response = new ResponseEntity<>(developsList, HttpStatus.OK);
                logger.info("Found develops list by Skill level: {}", developsList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find develops list by Skill level: {}", level);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find develops list by Skill level. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Develops entities that match the specified Role Id.
     * @param id The Role Id to filter Develops entities by, must not be null.
     * @return a list of Develops entities that have the specified Role id, or an empty list if no matches are found.
     */
    @GetMapping("/find/roleid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Develops>> findByRoleId(@PathVariable Long id) {
        ResponseEntity<List<Develops>> response;

        try {
            List<Develops> developsList = developsService.findByRoleId(id);

            if (developsList != null && !developsList.isEmpty()) {
                response = new ResponseEntity<>(developsList, HttpStatus.OK);
                logger.info("Found develops list by Role id: {}", developsList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find develops list by Role id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find develops list by Role id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Develops entities that match the specified Skill Id.
     * @param id The Skill Id to filter Develops entities by, must not be null.
     * @return a list of Develops entities that have the specified Skill id, or an empty list if no matches are found.
     */
    @GetMapping("/find/skillid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Develops>> findBySkillId(@PathVariable Long id) {
        ResponseEntity<List<Develops>> response;

        try {
            List<Develops> developsList = developsService.findBySkillId(id);

            if (developsList != null && !developsList.isEmpty()) {
                response = new ResponseEntity<>(developsList, HttpStatus.OK);
                logger.info("Found develops list by Skill id: {}", developsList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find develops list by Skill id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find develops list by Skill id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Develops entities that match the specified BCI Activity Id.
     * @param id The BCI Activity Id to filter Develops entities by, must not be null.
     * @return a list of Develops entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    @GetMapping("/find/bciactivityid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Develops>> findByBCIActivityId(@PathVariable Long id) {
        ResponseEntity<List<Develops>> response;

        try {
            List<Develops> developsList = developsService.findByBCIActivityId(id);

            if (developsList != null && !developsList.isEmpty()) {
                response = new ResponseEntity<>(developsList, HttpStatus.OK);
                logger.info("Found develops list by BCIActivity id: {}", developsList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find develops list by BCIActivity id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find develops list by BCIActivity id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all Develops.
     * @return all Develops.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Develops>> findAll() {
        ResponseEntity<List<Develops>> response;

        try {
            List<Develops> developsList = developsService.findAll();

            if (developsList != null && !developsList.isEmpty()) {
                response = new ResponseEntity<>(developsList, HttpStatus.OK);
                logger.info("Found all develops: {}", developsList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all develops.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all develops. Error: {}", e.getMessage());
        }

        return response;
    }
}
