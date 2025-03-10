package ca.uqam.latece.evo.server.core.service;


import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.model.Requires;
import ca.uqam.latece.evo.server.core.repository.RequiresRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Requires Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class RequiresService extends AbstractEvoService<Requires> {
    private static final Logger logger = LoggerFactory.getLogger(RequiresService.class);

    @Autowired
    private RequiresRepository requiresRepository;

    public RequiresService() {}

    /**
     * Inserts a Requires in the database.
     * @param requires The Requires entity.
     * @return The saved Requires.
     * @throws IllegalArgumentException in case the given Requires is null.
     * @throws OptimisticLockingFailureException when the Requires uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public Requires create(Requires requires) {
        return this.save(requires);
    }

    /**
     * Update a Requires in the database.
     * @param requires The Requires entity.
     * @return The saved Requires.
     * @throws IllegalArgumentException in case the given Requires is null.
     * @throws OptimisticLockingFailureException when the Requires uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public Requires update(Requires requires) {
        return this.save(requires);
    }

    /**
     * Insert or Update a Requires in the database.
     * @param requires The Requires entity.
     * @return The saved Requires.
     * @throws IllegalArgumentException in case the given Requires is null.
     * @throws OptimisticLockingFailureException when the Requires uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Transactional
    protected Requires save(Requires requires) {
        ObjectValidator.validateString(requires.getLevel().toString());
        ObjectValidator.validateObject(requires.getRole());
        ObjectValidator.validateObject(requires.getSkill());
        ObjectValidator.validateObject(requires.getBciActivity());
        Requires saved = requiresRepository.save(requires);
        logger.info("Requires saved: {}", saved);
        return saved;
    }

    /**
     * Deletes the Requires with the given id.
     * <p>
     * If the Requires is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Requires to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        requiresRepository.deleteById(id);
        logger.info("Requires deleted: {}", id);
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
        return requiresRepository.existsById(id);
    }

    /**
     * Retrieves a requires by id.
     * <p>Note: <p/>Validates the provided identifier before fetching the role from the repository.
     * @param id the unique identifier of the requires to be retrieved.
     * @return the requires corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     * @throws EntityNotFoundException if the requires not found.
     */
    @Override
    public Requires findById(Long id) {
        ObjectValidator.validateId(id);
        return requiresRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Requires not found!"));
    }

    /**
     * Retrieves a list of Requires entities that match the specified skill level.
     * @param level the Requires to filter Develops entities by, must not be null.
     * @return a list of Requires entities that have the specified skill level, or an empty list if no matches are found.
     */
    public List<Requires> findByLevel(SkillLevel level){
        ObjectValidator.validateObject(level);
        return requiresRepository.findByLevel(level);
    }

    /**
     * Retrieves a list of Requires entities that match the specified Role Id.
     * @param roleId The Role Id to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified Role id, or an empty list if no matches are found.
     */
    public List<Requires> findByRoleId(Long roleId) {
        ObjectValidator.validateObject(roleId);
        return requiresRepository.findByRoleId(roleId);
    }

    /**
     * Retrieves a list of Requires entities that match the specified Skill Id.
     * @param skillId The Skill Id to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified Skill id, or an empty list if no matches are found.
     */
    public List<Requires> findBySkillId(Long skillId) {
        ObjectValidator.validateObject(skillId);
        return requiresRepository.findBySkillId(skillId);
    }

    /**
     * Retrieves a list of Requires entities that match the specified BCI Activity Id.
     * @param bciActivityId The BCI Activity Id to filter Requires entities by, must not be null.
     * @return a list of Requires entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    public List<Requires> findByBCIActivityId(Long bciActivityId) {
        ObjectValidator.validateObject(bciActivityId);
        return requiresRepository.findByBCIActivityId(bciActivityId);
    }

    /**
     * Gets all Requires.
     * @return all Requires.
     */
    @Override
    public List<Requires> findAll() {
        return requiresRepository.findAll();
    }

}
