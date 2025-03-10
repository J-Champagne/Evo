package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.service.SkillService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;

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
import java.util.Optional;

/**
 * Skill Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/skills")
public class SkillController extends AbstractEvoController<Skill> {

    @Autowired
    private SkillService skillService;

    /**
     * Creates an Actor in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Skill> create(@RequestBody Skill skill) {
        ObjectValidator.validateObject(skill);
        return Optional.ofNullable(skillService.create(skill)).isPresent() ?
                new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates an existing skill in the database.
     * @param skill the skill object containing updated information.
     * @return the updated skill object.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Skill> update(@RequestBody Skill skill) {
        ObjectValidator.validateObject(skill);
        return Optional.ofNullable(skillService.update(skill)).isPresent() ?
                new ResponseEntity<>(skill, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a skill identified by its unique id.
     * @param id the unique identifier of the skill to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        skillService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Skill> findById(@PathVariable Long id) {
        return Optional.ofNullable(skillService.findById(id)).isPresent() ?
                new ResponseEntity<>(skillService.findById(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds and retrieves a list of skills based on their required skills.
     * @param id the id of the skills to search for.
     * @return a list of skills matching the given id.
     */
    @GetMapping("/find/requiredskill/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Skill>> findByRequiredSkill(@PathVariable Long id) {
        return Optional.ofNullable(skillService.findByRequiredSkill(id)).isPresent() ?
                new ResponseEntity<>(skillService.findByRequiredSkill(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Gets all skills.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Skill>> findAll() {
        return Optional.ofNullable(skillService.findAll()).isPresent() ?
                new ResponseEntity<>(skillService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Skill>> findByName(@PathVariable String name) {
        return Optional.ofNullable(skillService.findByName(name)).isPresent() ?
                new ResponseEntity<>(skillService.findByName(name), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/type/{type}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Skill>> findByType(@PathVariable SkillType type) {
        return Optional.ofNullable(skillService.findByType(type)).isPresent() ?
                new ResponseEntity<>(skillService.findByType(type), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
