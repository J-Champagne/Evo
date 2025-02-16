package ca.uqam.latece.evo.server.core.controller;


import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.model.Develops;
import ca.uqam.latece.evo.server.core.service.DevelopsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Develops Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/develops")
public class DevelopsController {
    @Autowired
    private DevelopsService developsService;

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public Develops create(@RequestBody Develops develops) {
        return developsService.create(develops);
    }

    @PutMapping
    public Develops update(@RequestBody Develops develops) {
        return developsService.update(develops);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        developsService.deleteById(id);
    }

    @GetMapping
    public List<Develops> findAll() {
        return developsService.findAll();
    }

    @GetMapping("/find/{id}")
    public Develops findById(@PathVariable Long id) {
        return developsService.findById(id);
    }

    @GetMapping("/find/level/{level}")
    public List<Develops> findByLevel(@PathVariable SkillLevel level){
        return developsService.findByLevel(level);
    }
}
