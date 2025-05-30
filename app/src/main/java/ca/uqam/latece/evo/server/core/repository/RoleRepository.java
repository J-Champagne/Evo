package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Role repository creates CRUD implementation at runtime automatically.
 * @since 22.01.2025
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface RoleRepository extends EvoRepository<Role> {

    /**
     * Finds a list of Role entities by their name.
     * @param name the name of the Role to search for.
     * @return the Role with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    List<Role> findByName(String name);

    /**
     * Checks if a Role entity with the specified name exists in the repository.
     * @param name the name of the Role to check for existence, must not be null.
     * @return true if a Role with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    boolean existsByName(String name);

    /**
     * Retrieves a list of Role entities that match the specified BCI Activity Id.
     * @param bciActivityId The BCI Activity Id to filter Role entities by, must not be null.
     * @return a list of Role entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    @Query(value = "SELECT ro.* FROM role AS ro " +
            "JOIN bci_activity_role bro ON (ro.role_id = bro.bci_activity_role_role_id) " +
            "WHERE bro.bci_activity_role_bci_activity_id = :bci_activity_id",
            nativeQuery = true)
    List<Role> findByBCIActivity(@Param("bci_activity_id") Long bciActivityId);
}
