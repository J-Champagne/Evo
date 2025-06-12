package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.repository.BCIActivityRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BCIActivity Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class BCIActivityService extends AbstractEvoService<BCIActivity> {
    private static final Logger logger = LoggerFactory.getLogger(BCIActivityService.class);

    @Autowired
    private BCIActivityRepository bciActivityRepository;


    /**
     * Inserts a BCIActivity in the database.
     * @param evoModel The BCIActivity entity.
     * @return The saved BCIActivity.
     * @throws IllegalArgumentException in case the given BCIActivity is null.
     */
    @Override
    public BCIActivity create(BCIActivity evoModel) {
        BCIActivity bciActivity = null;

        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateString(evoModel.getName());

        // The name should be unique.
        if(this.existsByName(evoModel.getName())) {
            throw this.createDuplicatedNameException(evoModel, evoModel.getName());
        } else {
            bciActivity = this.save(evoModel);
            logger.info("BCIActivity created: {}", bciActivity);
        }

        return bciActivity;
    }

    /**
     * Updates a BCIActivity in the database.
     * @param evoModel The BCIActivity entity.
     * @return The saved BCIActivity.
     * @throws IllegalArgumentException in case the given BCIActivity is null.
     */
    @Override
    public BCIActivity update(BCIActivity evoModel) {
        BCIActivity bciActivity = null;

        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateId(evoModel.getId());
        ObjectValidator.validateString(evoModel.getName());

        // Checks if the BCIActivity exists in the database.
        BCIActivity found = this.findById(evoModel.getId());

        if (found == null) {
            throw new IllegalArgumentException("BCIActivity " + evoModel.getName() + " not found!");
        } else {
            if (evoModel.getName().equals(found.getName())) {
                bciActivity = this.save(evoModel);
            } else {
                if (this.existsByName(evoModel.getName())) {
                    throw this.createDuplicatedNameException(evoModel, evoModel.getName());
                } else {
                    bciActivity = this.save(evoModel);
                }
            }

            logger.info("BCIActivity updated: {}", bciActivity);
        }

        return bciActivity;
    }

    /**
     * Method used to create or update an BCIActivity.
     * @param evoModel he BCIActivity entity.
     * @return The BCIActivity.
     */
    @Transactional
    protected BCIActivity save(BCIActivity evoModel) {
        return bciActivityRepository.save(evoModel);
    }

    /**
     * Checks if a BCIActivity entity with the specified id exists in the repository.
     * @param id the id of the BCIActivity to check for existence, must not be null.
     * @return true if a BCIActivity with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return bciActivityRepository.existsById(id);
    }

    /**
     * Deletes the BCIActivity with the given id.
     * <p>
     * If the BCIActivity is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the BCIActivity to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        bciActivityRepository.deleteById(id);
        logger.info("BCIActivity deleted: {}", id);
    }

    /**
     * Retrieves a BCIActivity by its id.
     * @param id The BCIActivity Id to filter BCIActivity entities by, must not be null.
     * @return the BCIActivity with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     * @throws RuntimeException if the BCIActivity not found.
     */
    @Override
    public BCIActivity findById(Long id) {
        ObjectValidator.validateId(id);
        return bciActivityRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("BCI Activity not found!"));
    }

    /**
     * Finds a list of BCIActivity entities by their name.
     * @param name the type of the BCIActivity to search for.
     * @return a list of BCIActivity entities matching the specified name.
     */
    public List<BCIActivity> findByName(String name) {
        ObjectValidator.validateString(name);
        return bciActivityRepository.findByName(name);
    }

    /**
     * Finds a list of BCIActivity entities by their type.
     * @param type the type of the BCIActivity to search for.
     * @return a list of BCIActivity entities matching the specified type.
     */
    public List<BCIActivity> findByType(ActivityType type) {
        ObjectValidator.validateObject(type);
        return bciActivityRepository.findByType(type);
    }

    /**
     * Retrieves a list of BCIActivity entities that match the specified Develops Id.
     * @param developsId The Develops Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Develops id, or an empty list if no matches are found.
     */
    public List<BCIActivity> findByDevelops(Long developsId) {
        ObjectValidator.validateId(developsId);
        return bciActivityRepository.findByDevelopsBCIActivity_Id(developsId);
    }

    /**
     * Retrieves a list of BCIActivity entities that match the specified Requires Id.
     * @param requiresId The Requires Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Requires id, or an empty list if no matches are found.
     */
    public List<BCIActivity> findByRequires(Long requiresId) {
        ObjectValidator.validateId(requiresId);
        return bciActivityRepository.findByRequiresBCIActivities_Id(requiresId);
    }

    /**
     * Retrieves a list of BCIActivity entities that match the specified Role Id.
     * @param roleId The Role Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Role id, or an empty list if no matches are found.
     */
    public List<BCIActivity> findByRole(Long roleId) {
        ObjectValidator.validateId(roleId);
        return bciActivityRepository.findByRoleBCIActivities_Id(roleId);
    }

    /**
     * Retrieves a list of BCIActivity entities that match the specified Content Id.
     * @param contentId The Content Id to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Content id, or an empty list if no matches are found.
     */
    public List<BCIActivity> findByContent(Long contentId) {
        ObjectValidator.validateId(contentId);
        return bciActivityRepository.findByContentBCIActivities_Id(contentId);
    }

    /**
     * Gets all BCIActivity.
     * @return all BCIActivity.
     */
    @Override
    public List<BCIActivity> findAll() {
        return bciActivityRepository.findAll().stream().toList();
    }

    /**
     * Checks if a BCIActivity entity with the specified name exists in the repository.
     * @param name the name of the BCIActivity to check for existence, must not be null.
     * @return true if a BCIActivity with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        ObjectValidator.validateString(name);
        return bciActivityRepository.existsByName(name);
    }
}
