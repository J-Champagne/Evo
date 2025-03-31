package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.Reporting;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Reporting repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface ReportingRepository extends EvoRepository<Reporting> {

    /**
     * Finds a list of Reporting entities by their name.
     * @param name the name of the Reporting to search for.
     * @return the Reporting with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    List<Reporting> findByName(String name);

    /**
     * Checks if a Reporting entity with the specified name exists in the repository.
     * @param name the name of the Reporting to check for existence, must not be null.
     * @return true if a Reporting with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    boolean existsByName(String name);

    /**
     * Finds a list of Reporting entities by their type.
     * @param type the type of the Reporting to search for.
     * @return a list of Reporting entities matching the specified type.
     */
    List<Reporting> findByType(ActivityType type);

    /**
     * Finds a list of Reporting entities by their frequency.
     * @param frequency the frequency of the Reporting to search for.
     * @return a list of Reporting entities matching the specified type.
     */
    List<Reporting> findByFrequency(String frequency);
}
