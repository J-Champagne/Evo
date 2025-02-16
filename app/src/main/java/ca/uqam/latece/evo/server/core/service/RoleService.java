package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.repository.RoleRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Role Service.
 * @since 27.01.2025.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
public class RoleService extends AbstractEvoService<Role> {
    @Autowired
    private RoleRepository roleRepository;

    private static final String ERROR_NULL_MESSAGE = "The role object is null!";
    private static final String ERROR_EMPTY_MESSAGE = "The role name is empty!";

    public RoleService() {}

    /**
     * Creates and saves a new role if it does not already exist in the repository.
     * <p>Note: <p/>Validates the role object and its name before saving.
     * @param role the role object to be created and saved.
     * @return the saved role object.
     * @throws IllegalArgumentException if the role name already exists or if the role object or its name is null.
     */
    @Override
    public Role create(Role role) {
        Role roleSeved = null;

        // Validate the object.
        ObjectValidator.validateObject(role);
        ObjectValidator.validateObject(role.getName());

        if (this.existsByName(role.getName())) {
            throw new IllegalArgumentException("Role already registered!");
        } else {
            roleSeved = save(role);
        }

        return roleSeved;
    }

    /**
     * Updates an existing role in the repository. Validates the role object before saving the updated role.
     * @param role the role object containing updated information to be saved.
     * @return the updated and saved role object.
     * @throws IllegalArgumentException if the role object is invalid or null.
     */

    public Role update(Role role) {
        ObjectValidator.validateObject(role);
        return save(role);
    }

    /**
     * Deletes a role from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the role to be deleted.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        roleRepository.deleteById(id);
    }

    /**
     * Checks if an entity with the specified identifier exists in the repository.
     * @param id the unique identifier of the entity to be checked for existence.
     * @return true if an entity with the given identifier exists, false otherwise.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return roleRepository.existsById(id);
    }

    /**
     * Checks if a role with the specified name exists in the repository.
     * @param name the name of the role to check for existence.
     * @return true if a role with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the provided name is null or empty.
     */
    public boolean existsByName(String name) {
        ObjectValidator.validateString(name, ERROR_NULL_MESSAGE, ERROR_EMPTY_MESSAGE);
        return roleRepository.existsByName(name);
    }

    /**
     * Checks if a role with the specified name exists in the repository.
     * <p>Note: <p/> Validates that the provided name is not null or empty before proceeding with the search.
     * @param name the name of the role(s) to be retrieved.
     * @return a list of roles that match the specified name.
     * @throws IllegalArgumentException if the provided name is null or empty.
     */
    public List<Role> findByName(String name) {
        ObjectValidator.validateString(name);
        return roleRepository.findByName(name);
    }

    /**
     * Saves the given role into the repository.
     * @param role the role object to be saved.
     * @return the saved role object.
     */
    private Role save(Role role){
        ObjectValidator.validateObject(role);
        return roleRepository.save(role);
    }

    /**
     * Retrieves all roles from the repository.
     * @return a list of all roles present in the repository.
     */
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll().stream().toList();
    }

    /**
     * Retrieves a role by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the role from the repository.
     * @param id the unique identifier of the role to be retrieved.
     * @return the role corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     */
    @Override
    public Role findById(Long id) {
        ObjectValidator.validateId(id);
        return roleRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Role not found!"));

    }
}
