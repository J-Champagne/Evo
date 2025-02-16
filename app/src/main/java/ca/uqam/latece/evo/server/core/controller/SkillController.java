package ca.uqam.latece.evo.server.core.controller;


import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Skill Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/skills")
public class SkillController {
    @Autowired
    private SkillService skillService;

    /**
     * Creates an Actor in the database.
     */
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public Skill create(@RequestBody Skill skill) {
        return skillService.create(skill);
    }

    /**
     * Updates an existing skill in the database.
     * @param skill the skill object containing updated information.
     * @return the updated skill object.
     */
    @PutMapping
    public Skill update(@RequestBody Skill skill) {
        return skillService.update(skill);
    }

    /**
     * Deletes a skill identified by its unique id.
     * @param id the unique identifier of the skill to delete.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        skillService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public Skill findById(@PathVariable Long id) {
        return skillService.findById(id);
    }

    /**
     * Gets all skills.
     */
    @GetMapping
    public List<Skill> findAll() {
        return skillService.findAll();
    }

    @GetMapping("/find/name/{name}")
    public List<Skill> findByName(@PathVariable String name) {
        return skillService.findByName(name);
    }

    @GetMapping("/find/type/{type}")
    public List<Skill> findByType(@PathVariable SkillType type) {
        return skillService.findByType(type);
    }
}
