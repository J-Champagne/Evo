package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.Reporting;
import ca.uqam.latece.evo.server.core.repository.ReportingRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Reporting Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class ReportingService extends AbstractEvoService<Reporting> {
    private static final Logger logger = LoggerFactory.getLogger(ReportingService.class);

    @Autowired
    private ReportingRepository reportingRepository;

    /**
     * Inserts a Reporting in the database.
     * @param reporting The Reporting entity.
     * @return The saved Reporting.
     * @throws IllegalArgumentException in case the given Reporting is null.
     * @throws OptimisticLockingFailureException when the Reporting uses optimistic locking and has a version
     *         attribute with a different value from that found in the persistence store. Also thrown if the
     *         entity is assumed to be present but does not exist in the database.
     */
    @Override
    public Reporting create(Reporting reporting) {
        Reporting reportingSaved = new Reporting();

        ObjectValidator.validateObject(reporting);
        ObjectValidator.validateObject(reporting.getName());

        // The name should be unique.
        if(this.existsByName(reporting.getName())) {
            throw this.createDuplicateException(reporting);
        } else {
            reportingSaved = this.save(reporting);
            logger.info("Reporting created: {}", reportingSaved);
        }

        return reportingSaved;
    }

    /**
     * Create duplicate Reporting Exception.
     * @param reporting the Reporting entity.
     * @return an exception object.
     */
    private IllegalArgumentException createDuplicateException(Reporting reporting) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_NAME_ALREADY_REGISTERED +
                " Reporting Name: " + reporting.getName());
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    /**
     * Updates a Reporting in the database.
     * @param reporting The Reporting entity.
     * @return The saved Reporting.
     * @throws IllegalArgumentException in case the given Reporting is null.
     * @throws OptimisticLockingFailureException when the Reporting uses optimistic locking and has a version
     *     attribute with a different value from that found in the persistence store. Also thrown if the entity
     *     is assumed to be present but does not exist in the database.
     */
    @Override
    public Reporting update(Reporting reporting) {
        Reporting reportingSaved = new Reporting();

        ObjectValidator.validateObject(reporting);
        ObjectValidator.validateObject(reporting.getName());

        // The name should be unique.
        if(this.existsByName(reporting.getName())) {
            throw this.createDuplicateException(reporting);
        } else {
            reportingSaved = this.save(reporting);
            logger.info("Reporting updated: {}", reportingSaved);
        }

        return reportingSaved;
    }

    /**
     * Method used to create or update a Reporting.
     * @param reporting the Reporting entity.
     * @return The Reporting.
     */
    @Transactional
    protected Reporting save(Reporting reporting) {
        ObjectValidator.validateObject(reporting);
        return reportingRepository.save(reporting);
    }

    /**
     * Checks if a Reporting entity with the specified name exists in the repository.
     * @param name the name of the Reporting to check for existence, must not be null.
     * @return true if a Reporting with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        ObjectValidator.validateString(name);
        return reportingRepository.existsByName(name);
    }

    /**
     * Checks if a Reporting entity with the specified id exists in the repository.
     * @param id the id of the Reporting to check for existence, must not be null.
     * @return true if a Reporting with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return reportingRepository.existsById(id);
    }

    /**
     * Deletes the Reporting with the given id.
     * <p>
     * If the Reporting is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Reporting to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        reportingRepository.deleteById(id);
        logger.info("Goal Setting deleted: {}", id);
    }

    /**
     * Retrieves a Reporting by its id.
     * @param id The Reporting Id to filter Reporting entities by, must not be null.
     * @return the Reporting with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    @Override
    public Reporting findById(Long id) {
        ObjectValidator.validateId(id);
        return reportingRepository.findById(id).orElse(null);
    }

    /**
     * Finds a list of Reporting entities by their name.
     * @param name the type of the Reporting to search for.
     * @return a list of Reporting entities matching the specified name.
     */
    public List<Reporting> findByName(String name) {
        ObjectValidator.validateString(name);
        return reportingRepository.findByName(name);
    }

    /**
     * Finds a list of Reporting entities by their type.
     * @param type the type of the Reporting to search for.
     * @return a list of Reporting entities matching the specified type.
     */
    public List<Reporting> findByType(ActivityType type) {
        ObjectValidator.validateObject(type);
        return reportingRepository.findByType(type);
    }

    /**
     * Gets all Reporting.
     * @return all Reporting.
     */
    @Override
    public List<Reporting> findAll() {
        return reportingRepository.findAll();
    }
}
