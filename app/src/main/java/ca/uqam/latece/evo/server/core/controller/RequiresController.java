package ca.uqam.latece.evo.server.core.controller;


import ca.uqam.latece.evo.server.core.model.Requires;
import ca.uqam.latece.evo.server.core.service.RequiresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Requires Controller.
 * @since 22.01.2025.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/requires")
public class RequiresController {
    @Autowired
    private RequiresService requiresService;

    /**
     * Creates an Actor in the database.
     */
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public Requires create(Requires requires) {
        return requiresService.create(requires);
    }

    /**
     * Updates a Requires entity in the database.
     */
    @PutMapping
    public Requires update(Requires requires) {
        return requiresService.update(requires);
    }

    /**
     * Delete a Requires entity.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        requiresService.deleteById(id);
    }

    /**
     * Gets all Requires entity.
     */
    @GetMapping
    public List<Requires> findAll() {
        return requiresService.findAll();
    }

    /**
     * Finds a Requires entity by its id.
     */
    @GetMapping("/find/{id}")
    public Requires findById(Long id) {
        return requiresService.findById(id);
    }
}
