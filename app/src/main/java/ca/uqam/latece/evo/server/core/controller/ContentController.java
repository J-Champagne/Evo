package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.Content;
import ca.uqam.latece.evo.server.core.service.ContentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Content Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/contents")
public class ContentController extends AbstractEvoController<Content> {
    @Autowired
    private ContentService contentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findAll() {
        return Optional.ofNullable(contentService.findAll()).isPresent() ?
                new ResponseEntity<>(contentService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Content> create(@Valid @RequestBody Content content) {
        return Optional.ofNullable(contentService.create(content)).isPresent() ?
                new ResponseEntity<>(content, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Content> update(@Valid @RequestBody Content content) {
        return Optional.ofNullable(contentService.update(content)).isPresent() ?
                new ResponseEntity<>(content, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        contentService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Content> findById(@PathVariable Long id) {
        return Optional.ofNullable(contentService.findById(id)).isPresent() ?
                new ResponseEntity<>(contentService.findById(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findByName(@PathVariable String name) {
        return Optional.ofNullable(contentService.findByName(name)).isPresent() ?
                new ResponseEntity<>(contentService.findByName(name), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/type/{type}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findByType(@PathVariable String type) {
        return Optional.ofNullable(contentService.findByType(type)).isPresent() ?
                new ResponseEntity<>(contentService.findByType(type), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
