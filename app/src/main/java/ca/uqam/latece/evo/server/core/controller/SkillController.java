package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.service.SkillService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

/**
 * Skill Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/skills")
public class SkillController extends AbstractEvoController<Skill> {
    private static final Logger logger = LoggerFactory.getLogger(SkillController.class);

    @Autowired
    private SkillService skillService;

    /**
     * Inserts a Skill in the database.
     * @param skill The Skill entity.
     * @return The saved Skill.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Skill> create(@RequestBody Skill skill) {
        ResponseEntity<Skill> response;

        try {
            ObjectValidator.validateObject(skill);
            Skill saved = skillService.create(skill);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created a new skill: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new skill.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create a new skill. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates an existing skill in the database.
     * @param skill the skill object containing updated information.
     * @return the updated skill object.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Skill> update(@RequestBody Skill skill) {
        ResponseEntity<Skill> response;

        try {
            ObjectValidator.validateObject(skill);
            Skill updated = skillService.update(skill);

            if (updated != null && updated.getId().equals(skill.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated skill: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update skill.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update skill. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes a skill identified by its unique id.
     * @param id the unique identifier of the skill to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        skillService.deleteById(id);
        logger.info("Skill deleted: {}", id);
    }

    /**
     * Finds and retrieves a list of skills based on their id.
     * @param id the id of the skills to search for.
     * @return a list of skills matching the given type.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Skill> findById(@PathVariable Long id) {
        ResponseEntity<Skill> response;

        try {
            ObjectValidator.validateId(id);
            Skill skill = skillService.findById(id);

            if (skill != null && skill.getId().equals(id)) {
                response = new ResponseEntity<>(skill, HttpStatus.OK);
                logger.info("Found skill: {}", skill);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find skill by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find skill. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds and retrieves a list of skills based on their required skills.
     * @param id the id of the skills to search for.
     * @return a list of skills matching the given id.
     */
    @GetMapping("/find/requiredskill/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Skill>> findByRequiredSkill(@PathVariable Long id) {
        ResponseEntity<List<Skill>> response;

        try {
            ObjectValidator.validateId(id);
            List<Skill> skillList = skillService.findByRequiredSkill(id);

            if (skillList != null && !skillList.isEmpty()) {
                response = new ResponseEntity<>(skillList, HttpStatus.OK);
                logger.info("Found skill list by Requires id: {}", skillList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find skill list by Requires id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find skill list by Requires id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds and retrieves a list of skills based on their subskills.
     * @param id the id of the subskills to search for.
     * @return a list of skills matching the given subskill id.
     */
    @GetMapping("/find/subskill/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Skill>> findBySubSkill(@PathVariable Long id) {
        ResponseEntity<List<Skill>> response;

        try {
            ObjectValidator.validateId(id);
            List<Skill> skillList = skillService.findBySubSkill(id);

            if (skillList != null && !skillList.isEmpty()) {
                response = new ResponseEntity<>(skillList, HttpStatus.OK);
                logger.info("Found skill list by subskill id: {}", skillList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find skill list by subskill id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find skill list by subskill id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds and retrieves a list of skills based on their name.
     * @param name the name of the skills to search for.
     * @return a list of skills matching the given type.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Skill>> findByName(@PathVariable String name) {
        ResponseEntity<List<Skill>> response;

        try {
            ObjectValidator.validateString(name);
            List<Skill> skillList = skillService.findByName(name);

            if (skillList != null && !skillList.isEmpty()) {
                response = new ResponseEntity<>(skillList, HttpStatus.OK);
                logger.info("Found skill list by name: {}", skillList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find skill list by name: {}", name);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find skill list by name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds and retrieves a list of skills based on their type.
     * @param type the type of the skills to search for.
     * @return a list of skills matching the given type.
     */
    @GetMapping("/find/type/{type}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Skill>> findByType(@PathVariable SkillType type) {
        ResponseEntity<List<Skill>> response;

        try {
            List<Skill> skillList = skillService.findByType(type);

            if (skillList != null && !skillList.isEmpty()) {
                response = new ResponseEntity<>(skillList, HttpStatus.OK);
                logger.info("Found skill list by type: {}", skillList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find skill list by type: {}", type);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find skill list by type. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all skills.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Skill>> findAll() {
        ResponseEntity<List<Skill>> response;

        try {
            List<Skill> skillList = skillService.findAll();

            if (skillList != null && !skillList.isEmpty()) {
                response = new ResponseEntity<>(skillList, HttpStatus.OK);
                logger.info("Found all skill: {}", skillList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all skill list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all skill list. Error: {}", e.getMessage());
        }

        return response;
    }
}
