package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Abstract Evo Controller.
 * @param <T> the Evo model (AbstractEvoModel).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
public abstract class AbstractEvoController <T extends AbstractEvoModel>{

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public abstract ResponseEntity<T> create(@RequestBody T model);

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public abstract ResponseEntity<T> update(@RequestBody T model);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public abstract void deleteById(@PathVariable Long id);

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 302
    public abstract ResponseEntity<T> findById(@PathVariable Long id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 302
    public abstract ResponseEntity<List<T>> findAll();
}
