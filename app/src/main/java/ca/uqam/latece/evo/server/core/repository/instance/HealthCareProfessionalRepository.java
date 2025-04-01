package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.instance.Actor;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * HealthCareProfessipnal repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos && Julien Champagne.
 */
@Repository
public interface HealthCareProfessionalRepository extends EvoRepository<HealthCareProfessional> {
        /**
         * Finds a list of HealthCareProfessional entities by their name.
         * @param name the name of the HealthCareProfessional to search for.
         * @return the HealthCareProfessional with the given name or Optional#empty() if none found.
         * @throws IllegalArgumentException if the name is null.
         */
        List<HealthCareProfessional> findByName(String name);

        /**
         * Finds a HealthCareProfessional by its email.
         * @param email must not be null.
         * @return the HealthCareProfessional with the given id or Optional#empty() if none found.
         * @throws IllegalArgumentException if email is null.
         */
        List<HealthCareProfessional> findByEmail(String email);

        /**
         * Finds a list of HealthCareProfessional by their contact information.
         * @param contactInformation must not be null.
         * @return List<HealthCareProfessional> with entities that match the given contactInformation or Optional#empty() if none found.
         * @throws IllegalArgumentException if contactInformation is null.
         */
        List<HealthCareProfessional> findByContactInformation(String contactInformation);

        /**
         * Retrieves a list of HealthCareProfessional entities that match the specified Role Id.
         * @param roleId The Role Id to filter Develops entities by, must not be null.
         * @return a list of HealthCareProfessional entities that have the specified Role id, or an empty list if no matches are found.
         */
        @Query(value = "SELECT ac.*, hcp.*, 0 AS clazz_ FROM actor AS ac " +
                "JOIN healthcareprofessional hcp ON (ac.actor_id = hcp.healthcareprofessional_actor_id) " +
                "WHERE ac.actor_role_id = :role_id",
                nativeQuery = true)
        List<HealthCareProfessional> findByRole (@Param("role_id") Long roleId);

        /**
         * Finds a list of HealthCareProfessional entities by their position.
         * @param position the position of the HealthCareProfessional to search for.
         * @return List<HealthCareProfessional> with entities that match the given position or Optional#empty() if none found.
         * @throws IllegalArgumentException if the position is null.
         */
        List <HealthCareProfessional> findByPosition(String position);

        /**
         * Finds a list of HealthCareProfessional entities by their affiliation.
         * @param affiliation the affiliation of the HealthCareProfessional to search for.
         * @return List<HealthCareProfessional> with entities that match the given affiliation or Optional#empty() if none found.
         * @throws IllegalArgumentException if the affiliation is null.
         */
        List <HealthCareProfessional> findByAffiliation(String affiliation);

        /**
         * Finds a list of HealthCareProfessional entities by their specialties.
         * @param specialties the specialties of the HealthCareProfessional to search for.
         * @return List<HealthCareProfessional> with entities that match the given specialties or Optional#empty() if none found.
         * @throws IllegalArgumentException if the specialties is null.
         */
        List <HealthCareProfessional> findBySpecialties(String specialties);

        /**
         * Checks if an HealthCareProfessional entity with the specified name exists in the repository.
         * @param name the name of the HealthCareProfessional to check for existence, must not be null.
         * @return true if an HealthCareProfessional with the specified name exists, false otherwise.
         * @throws IllegalArgumentException if the name is null.
         */
        boolean existsByName(String name);

        /**
         * Checks if an HealthCareProfessional entity with the specified email exists in the repository.
         * @param email the email of the HealthCareProfessional to check for existence, must not be null.
         * @return true if an HealthCareProfessional with the specified email exists, false otherwise.
         * @throws IllegalArgumentException if the email is null.
         */
        boolean existsByEmail(String email);

        /**
         * Checks if an HealthCareProfessional entity with the specified contact information exists in the repository.
         * @param contactInformation the contact information of the HealthCareProfessional to check for existence, must not be null.
         * @return true if an HealthCareProfessional with the specified contact information exists, false otherwise.
         * @throws IllegalArgumentException if the contactInformation is null.
         */
        boolean existsByContactInformation(String contactInformation);

        /**
         * Checks if an HealthCareProfessional entity with the specified position exists in the repository.
         * @param position the position of the HealthCareProfessional to check for existence, must not be null.
         * @return true if an HealthCareProfessional with the specified position exists, false otherwise.
         * @throws IllegalArgumentException if the position is null.
         */
        boolean existsByPosition(String position);

        /**
         * Checks if an HealthCareProfessional entity with the specified affiliation exists in the repository.
         * @param affiliation the affiliation of the HealthCareProfessional to check for existence, must not be null.
         * @return true if an HealthCareProfessional with the specified affiliation exists, false otherwise.
         * @throws IllegalArgumentException if the affiliation is null.
         */
        boolean existsByAffiliation(String affiliation);

        /**
         * Checks if an HealthCareProfessional entity with the specified specialties exists in the repository.
         * @param specialties the specialties of the HealthCareProfessional to check for existence, must not be null.
         * @return true if an HealthCareProfessional with the specified specialties exists, false otherwise.
         * @throws IllegalArgumentException if the specialties are null.
         */
        boolean existsBySpecialties(String specialties);
}

