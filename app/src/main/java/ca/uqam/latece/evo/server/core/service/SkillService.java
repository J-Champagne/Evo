package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.repository.SkillRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Skill Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class SkillService extends AbstractEvoService<Skill> {
    private static final Logger logger = LogManager.getLogger(SkillService.class);
    private static final String ERROR_SKILL_ALREADY_REGISTERED = "Skill already registered!";

    @Autowired
    private SkillRepository skillRepository;

    public SkillService() {}

    /**
     * Inserts a Skill in the database.
     * @param skill The Skill entity.
     * @return The saved Skill.
     * @throws IllegalArgumentException in case the given Skill is null.
     * @throws OptimisticLockingFailureException when the Skill uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public Skill create(Skill skill) {
        Skill skillSaved = this.save(skill);
        logger.info("Skill created: {}", skillSaved.toString());
        return skillSaved;
    }

    /**
     * Update a Skill in the database.
     * @param skill The Skill entity.
     * @return The saved Skill.
     * @throws IllegalArgumentException in case the given Skill is null.
     * @throws OptimisticLockingFailureException when the Skill uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public Skill update(Skill skill) {
        Skill skillSaved = this.save(skill);
        logger.info("Skill updated: {}", skillSaved.toString());
        return skillSaved;
    }

    /**
     * Method used to create or update a Skill.
     * @param skill The Skill entity.
     * @return The saved Skill.
     */
    protected Skill save (Skill skill) {
        Skill skillSaved = null;

        // Validate the object.
        ObjectValidator.validateObject(skill);
        ObjectValidator.validateString(skill.getName());
        ObjectValidator.validateString(skill.getDescription());

        if (this.existsByName(skill.getName())) {
            throw this.createDuplicateSkillException(skill);
        } else {
            skillSaved = this.skillRepository.save(skill);
        }

        return skillSaved;
    }

    /**
     * Create duplicate Skill Exception.
     * @param skill the Skill entity.
     * @return an exception object.
     */
    private IllegalArgumentException createDuplicateSkillException (Skill skill) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_SKILL_ALREADY_REGISTERED +
                " Skill Name: " + skill.getName());
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    /**
     * Checks if a Skill entity with the specified id exists in the repository.
     * @param id the id of the Skill to check for existence, must not be null.
     * @return true if a Skill with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return skillRepository.existsById(id);
    }

    /**
     * Checks if a Skill entity with the specified name exists in the repository.
     * @param name the name of the Skill to check for existence, must not be null.
     * @return true if a Skill with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        ObjectValidator.validateString(name);
        return skillRepository.existsByName(name);
    }

    /**
     * Deletes the skills with the given id.
     * <p>
     * If the skills are not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the actor to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        skillRepository.deleteById(id);
    }

    /**
     * Finds and retrieves a list of skills based on their id.
     * @param id the id of the skills to search for.
     * @return a list of skills matching the given type.
     * @throws EntityNotFoundException in case the given Role not found.
     */
    @Override
    public Skill findById(Long id) {
      return skillRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Skill not found!"));
    }

    /**
     * Gets all skills.
     * @return all skills.
     */
    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    /**
     * Finds and retrieves a list of skills based on their name.
     * @param name the name of the skills to search for.
     * @return a list of skills matching the given type.
     */
    public List<Skill> findByName(String name){
        ObjectValidator.validateString(name);
        return skillRepository.findByName(name);
    }

    /**
     * Finds and retrieves a list of skills based on their type.
     * @param type the type of the skills to search for.
     * @return a list of skills matching the given type.
     */
    public List<Skill> findByType(SkillType type){
        ObjectValidator.validateObject(type);
        return skillRepository.findByType(type);
    }

    /**
     * Finds and retrieves a list of skills based on their required skills.
     * @param id the id of the skills to search for.
     * @return a list of skills matching the given id.
     */
    public List<Skill> findByRequiredSkill(Long id) {
        ObjectValidator.validateId(id);
        return skillRepository.findByRequiredSkill(id);
    }

    /**
     * Finds and retrieves a list of skills based on their subskills.
     * @param id the id of the subskills to search for.
     * @return a list of skills matching the given subskill id.
     */
    public List<Skill> findBySubSkill(Long id) {
        ObjectValidator.validateId(id);
        return skillRepository.findBySubSkill(id);
    }

}
