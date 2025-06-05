package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.repository.RoleRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Role Service.
 * @since 27.01.2025.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class RoleService extends AbstractEvoService<Role> {
    private static final Logger logger = LogManager.getLogger(RoleService.class);
    private static final String ERROR_NULL_MESSAGE = "The role object is null!";
    private static final String ERROR_EMPTY_MESSAGE = "The role name is empty!";

    @Autowired
    private RoleRepository roleRepository;


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
            throw createDuplicateRoleException(role);
        } else {
            roleSeved = this.save(role);
            logger.info("Role created: {}", roleSeved);
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
        Role roleSeved = null;

        // Validate the object.
        ObjectValidator.validateObject(role);
        ObjectValidator.validateId(role.getId());
        ObjectValidator.validateObject(role.getName());

        // Checks if the Role exists in the database.
        Role found = this.findById(role.getId());

        if (found == null) {
            throw new IllegalArgumentException("Role "+ role.getName() + " not found!");
        } else {
            if (role.getName().equals(found.getName())) {
                roleSeved = this.save(role);
            } else {
                if (this.existsByName(role.getName())) {
                    throw createDuplicateRoleException(role);
                } else {
                    roleSeved = this.save(role);
                }
            }

            logger.info("Role update: {}", roleSeved);
        }

        return roleSeved;
    }

    /**
     * Create duplicate Role Exception.
     * @param role the Role entity.
     * @return an exception object.
     */
    private IllegalArgumentException createDuplicateRoleException(Role role) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_NAME_ALREADY_REGISTERED +
                " Role Name: " + role.getName());
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    /**
     * Saves the given role into the repository.
     * @param role the role object to be saved.
     * @return the saved role object.
     */
    @Transactional
    protected Role save(Role role){
        return roleRepository.save(role);
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
        logger.info("Role deleted: {}", id);
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
     * @throws EntityNotFoundException if the role not found.
     */
    @Override
    public Role findById(Long id) {
        ObjectValidator.validateId(id);
        return roleRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Role not found!"));
    }

    /**
     * Retrieves a list of Role entities that match the specified BCI Activity Id.
     * @param bciActivityId The BCI Activity Id to filter Role entities by, must not be null.
     * @return a list of Role entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    public List<Role> findByBCIActivityId(Long bciActivityId) {
        ObjectValidator.validateId(bciActivityId);
        return roleRepository.findByBciActivitiesRoleId(bciActivityId);
    }

    public List<Role> findByBCIActivity(BCIActivity bciActivitiesRole) {
        ObjectValidator.validateObject(bciActivitiesRole);
        ObjectValidator.validateId(bciActivitiesRole.getId());
        return roleRepository.findByBciActivitiesRole(bciActivitiesRole);
    }
}

