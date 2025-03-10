package ca.uqam.latece.evo.server.core.controller;

import java.util.List;
import java.util.Optional;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.service.BCIActivityService;
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

/**
 * BCIActivity Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/bciactivity")
public class BCIActivityController extends AbstractEvoController <BCIActivity> {

    @Autowired
    private BCIActivityService bciActivityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<BCIActivity> create(@RequestBody BCIActivity model) {
        ObjectValidator.validateObject(model);

        return Optional.ofNullable(bciActivityService.create(model)).isPresent() ?
                new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<BCIActivity> update(@RequestBody BCIActivity model) {
        ObjectValidator.validateObject(model);

        return Optional.ofNullable(bciActivityService.update(model)).isPresent() ?
                new ResponseEntity<>(model, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        bciActivityService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<BCIActivity> findById(@PathVariable Long id) {
        return Optional.ofNullable(bciActivityService.findById(id)).isPresent() ?
                new ResponseEntity<>(bciActivityService.findById(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findByName(@PathVariable String name) {
        return Optional.ofNullable(bciActivityService.findByName(name)).isPresent() ?
                new ResponseEntity<>(bciActivityService.findByName(name), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/type/{type}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findByType(@PathVariable ActivityType type) {
        return Optional.ofNullable(bciActivityService.findByType(type)).isPresent() ?
                new ResponseEntity<>(bciActivityService.findByType(type), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/develops/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findByDevelops(@PathVariable Long id) {
        return Optional.ofNullable(bciActivityService.findByDevelops(id)).isPresent() ?
                new ResponseEntity<>(bciActivityService.findByDevelops(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/requires/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findByRequires(@PathVariable Long id) {
        return Optional.ofNullable(bciActivityService.findByRequires(id)).isPresent() ?
                new ResponseEntity<>(bciActivityService.findByRequires(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/role/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findByRole(@PathVariable Long id) {
        return Optional.ofNullable(bciActivityService.findByRole(id)).isPresent() ?
                new ResponseEntity<>(bciActivityService.findByRole(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/find/content/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findByContent(@PathVariable Long id) {
        return Optional.ofNullable(bciActivityService.findByContent(id)).isPresent() ?
                new ResponseEntity<>(bciActivityService.findByContent(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<BCIActivity>> findAll() {
        return Optional.ofNullable(bciActivityService.findAll()).isPresent() ?
                new ResponseEntity<>(bciActivityService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
