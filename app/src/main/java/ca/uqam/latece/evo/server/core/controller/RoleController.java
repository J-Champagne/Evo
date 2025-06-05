package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.service.RoleService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Role Controller.
 * @since 22.01.2025.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/roles")
public class RoleController extends AbstractEvoController <Role> {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    /**
     * Creates a new role if it does not already exist in the repository.
     * <p>Note: <p/>Validates the role object and its name before saving.
     * @param role the role object to be created and saved.
     * @return the saved role object.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Role> create(@RequestBody Role role) {
        ResponseEntity<Role> response;

        try {
            ObjectValidator.validateObject(role);
            Role saved = roleService.create(role);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new role: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new role.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new role. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates an existing role in the repository. Validates the role object before saving the updated role.
     * @param role the role object containing updated information to be saved.
     * @return the updated and saved role object.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Role> update(@RequestBody Role role) {
        ResponseEntity<Role> response;

        try {
            ObjectValidator.validateObject(role);
            Role updated = roleService.update(role);

            if (updated != null && updated.getId().equals(role.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated role: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update role.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update role. Error: {}", e.getMessage());
        }

        return response;
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
        logger.info("Role deleted: {}", id);
    }

    /**
     * Retrieves a role by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the role from the repository.
     * @param id the unique identifier of the role to be retrieved.
     * @return the role corresponding to the specified identifier.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Role> findById(@PathVariable Long id) {
        ResponseEntity<Role> response;

        try {
            ObjectValidator.validateId(id);
            Role role = roleService.findById(id);

            if (role != null && role.getId().equals(id)) {
                response = new ResponseEntity<>(role, HttpStatus.OK);
                logger.info("Found role: {}", role);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find role by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find role. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a role by its name.
     * @param name must not be null.
     * @return the role with the given name or Optional#empty() if none found.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Role>> findByName(@PathVariable String name) {
        ResponseEntity<List<Role>> response;

        try {
            ObjectValidator.validateString(name);
            List<Role> roleList = roleService.findByName(name);

            if (roleList != null && !roleList.isEmpty()) {
                response = new ResponseEntity<>(roleList, HttpStatus.OK);
                logger.info("Found role list by name: {}", roleList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find role list by name: {}", name);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find role list by name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Role entities that match the specified BCI Activity.
     * @param bciActivity The BCI Activity to filter Role entities by, must not be null.
     * @return a list of Role entities that have the specified BCI Activity, or an empty list if no matches are found.
     */
    @GetMapping("/find/bciactivity")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Role>> findByBCIActivity(@RequestBody BCIActivity bciActivity) {
        ResponseEntity<List<Role>> response;

        try {
            ObjectValidator.validateObject(bciActivity);
            List<Role> roleList = roleService.findByBCIActivity(bciActivity);

            if (roleList != null && !roleList.isEmpty()) {
                response = new ResponseEntity<>(roleList, HttpStatus.OK);
                logger.info("Found role list by BCIActivity: {}", roleList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find role list by BCIActivity: {}", bciActivity);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find role list by BCIActivity. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Role entities that match the specified BCI Activity Id.
     * @param id The BCI Activity Id to filter Role entities by, must not be null.
     * @return a list of Role entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    @GetMapping("/find/bciactivity/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Role>> findByBCIActivityId(@PathVariable Long id) {
        ResponseEntity<List<Role>> response;

        try {
            ObjectValidator.validateId(id);
            List<Role> roleList = roleService.findByBCIActivityId(id);

            if (roleList != null && !roleList.isEmpty()) {
                response = new ResponseEntity<>(roleList, HttpStatus.OK);
                logger.info("Found role list by BCIActivity id: {}", roleList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find role list by BCIActivity id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find role list by BCIActivity id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all roles.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Role>> findAll() {
        ResponseEntity<List<Role>> response;

        try {
            List<Role> reportingList = roleService.findAll();

            if (reportingList != null && !reportingList.isEmpty()) {
                response = new ResponseEntity<>(reportingList, HttpStatus.OK);
                logger.info("Found all role: {}", reportingList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all role list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all role list. Error: {}", e.getMessage());
        }

        return response;
    }
}
