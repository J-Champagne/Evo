package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.service.RoleService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Role Controller.
 * @since 22.01.2025.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public Role create(@RequestBody Role role) {
        ObjectValidator.validateObject(role);
        return roleService.create(role);
    }

    @PutMapping
    public Role update(@RequestBody Role role) {
        ObjectValidator.validateObject(role);
        return roleService.create(role);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        roleService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public Role findById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        return roleService.findById(id);
    }

    /**
     * Gets all roles.
     */
    @GetMapping
    public List<Role> findAll() {
        return roleService.findAll();
    }
}
