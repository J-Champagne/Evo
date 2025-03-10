package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.Content;
import ca.uqam.latece.evo.server.core.service.ContentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
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

    /**
     * Gets all Content.
     * @return all Content.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findAll() {
        return Optional.ofNullable(contentService.findAll()).isPresent() ?
                new ResponseEntity<>(contentService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Inserts a Content in the database.
     * @param content the Content entity.
     * @return The saved Content.
     * @throws IllegalArgumentException in case the given Content is null.
     * @throws OptimisticLockingFailureException when the Content uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Content> create(@Valid @RequestBody Content content) {
        return Optional.ofNullable(contentService.create(content)).isPresent() ?
                new ResponseEntity<>(content, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates the Content in the database.
     * @param content the Content entity.
     * @return The saved Content.
     * @throws IllegalArgumentException in case the given Content is null.
     * @throws OptimisticLockingFailureException when the Content uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Content> update(@Valid @RequestBody Content content) {
        return Optional.ofNullable(contentService.update(content)).isPresent() ?
                new ResponseEntity<>(content, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes the content with the given id.
     * <p>
     * If the content is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the content to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        contentService.deleteById(id);
    }

    /**
     * Retrieves a Content by its id.
     * @param id The Content Id to filter Content entities by, must not be null.
     * @return the Content with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Content> findById(@PathVariable Long id) {
        return Optional.ofNullable(contentService.findById(id)).isPresent() ?
                new ResponseEntity<>(contentService.findById(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds a list of Content entities by their name.
     * @param name the name of the T to search for.
     * @return the Content with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findByName(@PathVariable String name) {
        return Optional.ofNullable(contentService.findByName(name)).isPresent() ?
                new ResponseEntity<>(contentService.findByName(name), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of Content entities that match the specified BCI Activity Id.
     * @param id The BCI Activity Id to filter Content entities by, must not be null.
     * @return a list of Content entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    @GetMapping("/find/bciactivityid/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findByBCIActivity(@PathVariable Long id) {
        return Optional.ofNullable(contentService.findByBCIActivity(id)).isPresent() ?
                new ResponseEntity<>(contentService.findByBCIActivity(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of Content entities that match the specified Skill Id.
     * @param id The Skill Id to filter Content entities by, must not be null.
     * @return a list of Content entities that have the specified Skill Id, or an empty list if no matches are found.
     */
    @GetMapping("/find/skill/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findBySkill(@PathVariable Long id) {
        return Optional.ofNullable(contentService.findBySkill(id)).isPresent() ?
                new ResponseEntity<>(contentService.findBySkill(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds a list of Content entities by their type.
     * @param type the type of the content to search for.
     * @return a list of Content entities matching the specified type.
     */
    @GetMapping("/find/type/{type}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findByType(@PathVariable String type) {
        return Optional.ofNullable(contentService.findByType(type)).isPresent() ?
                new ResponseEntity<>(contentService.findByType(type), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
