package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.service.RoleService;
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
 * Role Controller.
 * @since 22.01.2025.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/roles")
public class RoleController extends AbstractEvoController <Role> {


    @Autowired
    RoleService roleService;

    /**
     * Creates a new role if it does not already exist in the repository.
     * <p>Note: <p/>Validates the role object and its name before saving.
     * @param role the role object to be created and saved.
     * @return the saved role object.
     * @throws IllegalArgumentException if the role name already exists or if the role object or its name is null.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Role> create(@RequestBody Role role) {
        ObjectValidator.validateObject(role);
        return Optional.ofNullable(roleService.create(role)).isPresent() ?
                new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates an existing role in the repository. Validates the role object before saving the updated role.
     * @param role the role object containing updated information to be saved.
     * @return the updated and saved role object.
     * @throws IllegalArgumentException if the role object is invalid or null.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Role> update(@RequestBody Role role) {
        ObjectValidator.validateObject(role);
        return Optional.ofNullable(roleService.update(role)).isPresent() ?
                new ResponseEntity<>(role, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a role from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the role to be deleted.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        roleService.deleteById(id);
    }

    /**
     * Retrieves a role by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the role from the repository.
     * @param id the unique identifier of the role to be retrieved.
     * @return the role corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Role> findById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        return Optional.ofNullable(roleService.findById(id)).isPresent() ?
                new ResponseEntity<>(roleService.findById(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Finds a role by its name.
     * @param name must not be null.
     * @return the role with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Role>> findByName(@PathVariable String name) {
        return Optional.ofNullable(roleService.findByName(name)).isPresent() ?
                new ResponseEntity<>(roleService.findByName(name), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves a list of Role entities that match the specified BCI Activity Id.
     * @param id The BCI Activity Id to filter Role entities by, must not be null.
     * @return a list of Role entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    @GetMapping("/find/bciactivity/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Role>> findByBCIActivity(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        return Optional.ofNullable(roleService.findByBCIActivity(id)).isPresent() ?
                new ResponseEntity<>(roleService.findByBCIActivity(id), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Gets all roles.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Role>> findAll() {
        return Optional.ofNullable(roleService.findAll()).isPresent() ?
                new ResponseEntity<>(roleService.findAll(), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
