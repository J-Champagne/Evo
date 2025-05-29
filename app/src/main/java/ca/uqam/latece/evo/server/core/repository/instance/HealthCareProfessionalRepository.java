package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * HealthCareProfessional repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
 */
@Repository
public interface HealthCareProfessionalRepository extends EvoRepository<HealthCareProfessional> {
        /**
         * Finds HealthCareProfessional entities by their name.
         * @param name String.
         * @return HealthCareProfessional with the given name.
         * @throws IllegalArgumentException if name is null or blank.
         */
        List<HealthCareProfessional> findByName(String name);

        /**
         * Finds a HealthCareProfessional by its email.
         * @param email String.
         * @return HealthCareProfessional with the given email.
         * @throws IllegalArgumentException if email is null or blank.
         */
        HealthCareProfessional findByEmail(String email);

        /**
         * Finds HealthCareProfessional entities by their contactInformation.
         * @param contactInformation String.
         * @return List<HealthCareProfessional> with the given contactInformation.
         * @throws IllegalArgumentException if contactInformation is null or blank.
         */
        List<HealthCareProfessional> findByContactInformation(String contactInformation);

        /**
         * Finds HealthCareProfessional entities by their Role.
         * @param role Role.
         * @return List<HealthCareProfessional> with the specified Role.
         * @throws IllegalArgumentException if role is null.
         */
        List<HealthCareProfessional> findByRole (Role role);

        /**
         * Finds HealthCareProfessional entities by their Role id.
         * @param id Long.
         * @return List<HealthCareProfessional> with the specified Role id.
         * @throws IllegalArgumentException if id is null.
         */
        List<HealthCareProfessional> findByRoleId (Long id);

        /**
         * Finds HealthCareProfessional entities by their position.
         * @param position String.
         * @return List<HealthCareProfessional> with the given position.
         * @throws IllegalArgumentException if position is null or blank.
         */
        List <HealthCareProfessional> findByPosition(String position);

        /**
         * Finds HealthCareProfessional entities by their affiliation.
         * @param affiliation String.
         * @return List<HealthCareProfessional> with the given affiliation.
         * @throws IllegalArgumentException if affiliation is null or blank.
         */
        List <HealthCareProfessional> findByAffiliation(String affiliation);

        /**
         * Finds HealthCareProfessional entities by their specialties.
         * @param specialties String.
         * @return List<HealthCareProfessional> with the given specialties.
         * @throws IllegalArgumentException if specialties is null or blank.
         */
        List <HealthCareProfessional> findBySpecialties(String specialties);

        /**
         * Checks if a HealthCareProfessional exists in the database by its email
         * @param email String
         * @return boolean
         * @throws IllegalArgumentException if email is null or blank.
         */
        boolean existsByEmail(String email);
}

