package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.model.Develops;
import ca.uqam.latece.evo.server.core.repository.DevelopsRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Develops Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class DevelopsService extends AbstractEvoService<Develops> {
    private static final Logger logger = LoggerFactory.getLogger(DevelopsService.class);

    @Autowired
    private DevelopsRepository developsRepository;

    public DevelopsService() {}

    /**
     * Inserts a Develops in the database.
     * @param develops the Develops entity.
     * @return The saved Develops.
     * @throws IllegalArgumentException in case the given Develops is null.
     * @throws OptimisticLockingFailureException when the Develops uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public Develops create(Develops develops) {
        Develops saved = this.save(develops);
        logger.info("Develops created: {}", saved);
        return saved;
    }

    /**
     * Retrieves a Develops by its id.
     * @param id The Content Id to filter Develops entities by, must not be null.
     * @return the Develops with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    @Override
    public Develops findById(Long id) {
        ObjectValidator.validateId(id);
        return developsRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves a list of Develops entities that match the specified skill level.
     * @param level the SkillLevel to filter Develops entities by, must not be null.
     * @return a list of Develops entities that have the specified skill level, or an empty list if no matches are found.
     */
    public List<Develops> findByLevel(SkillLevel level){
        ObjectValidator.validateObject(level);
        return developsRepository.findByLevel(level);
    }

    /**
     * Retrieves a list of Develops entities that match the specified Role Id.
     * @param roleId The Role Id to filter Develops entities by, must not be null.
     * @return a list of Develops entities that have the specified Role id, or an empty list if no matches are found.
     */
    public List<Develops> findByRoleId(Long roleId) {
        ObjectValidator.validateObject(roleId);
        return developsRepository.findByRoleId(roleId);
    }

    /**
     * Retrieves a list of Develops entities that match the specified Skill Id.
     * @param skillId The Skill Id to filter Develops entities by, must not be null.
     * @return a list of Develops entities that have the specified Skill id, or an empty list if no matches are found.
     */
    public List<Develops> findBySkillId(Long skillId) {
        ObjectValidator.validateObject(skillId);
        return developsRepository.findBySkillId(skillId);
    }

    /**
     * Retrieves a list of Develops entities that match the specified BCI Activity Id.
     * @param bciActivityId The BCI Activity Id to filter Develops entities by, must not be null.
     * @return a list of Develops entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    public List<Develops> findByBCIActivityId(Long bciActivityId) {
        ObjectValidator.validateObject(bciActivityId);
        return developsRepository.findByBCIActivityId(bciActivityId);
    }

    /**
     * Update a Develops in the database.
     * @param develops the Develops entity.
     * @return The saved Develops.
     * @throws IllegalArgumentException in case the given Develops is null.
     * @throws OptimisticLockingFailureException when the Develops uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public Develops update(Develops develops) {
        Develops saved = this.save(develops);
        logger.info("Develops updated: {}", saved);
        return saved;
    }

    /**
     * Insert or Update a Develops in the database.
     * @param develops the Develops entity.
     * @return The saved Develops.
     * @throws IllegalArgumentException in case the given Develops is null.
     * @throws OptimisticLockingFailureException when the Develops uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    protected Develops save(Develops develops){
        ObjectValidator.validateObject(develops);
        ObjectValidator.validateString(develops.getLevel().toString());
        ObjectValidator.validateObject(develops.getRole());
        ObjectValidator.validateObject(develops.getSkill());
        ObjectValidator.validateObject(develops.getBciActivity());
        return this.developsRepository.save(develops);
    }

    /**
     * Deletes the Develops with the given id.
     * If the Develops is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Develops to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        developsRepository.deleteById(id);
        logger.info("Develops deleted: {}", id);
    }

    /**
     * Checks if a Develops entity with the specified id exists in the repository.
     * @param id the id of the Develops to check for existence, must not be null.
     * @return true if a Develops with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return developsRepository.existsById(id);
    }

    /**
     * Gets all Develops.
     * @return all Develops.
     */
    @Override
    public List<Develops> findAll(){
        return developsRepository.findAll();
    }
}
