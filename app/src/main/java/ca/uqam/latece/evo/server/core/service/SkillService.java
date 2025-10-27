package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.repository.SkillRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SkillRepository skillRepository;

    public SkillService() {}

    /**
     * Inserts a Skill in the database.
     * @param skill The Skill entity.
     * @return The saved Skill.
     * @throws IllegalArgumentException in case the given Skill is null.
     */
    @Override
    public Skill create(Skill skill) {
        Skill skillSaved = null;

        // Validate the object.
        ObjectValidator.validateObject(skill);
        ObjectValidator.validateString(skill.getName());
        ObjectValidator.validateObject(skill.getType());

        if (this.existsByName(skill.getName())) {
            throw this.createDuplicatedNameException(skill, skill.getName());
        } else {
            skillSaved = this.save(skill);
            logger.info("Skill created: {}", skillSaved);
        }

        return skillSaved;
    }

    /**
     * Update a Skill in the database.
     * @param skill The Skill entity.
     * @return The saved Skill.
     * @throws IllegalArgumentException in case the given Skill is null.
     */
    @Override
    public Skill update(Skill skill) {
        Skill skillSaved = null;

        // Validate the object.
        ObjectValidator.validateObject(skill);
        ObjectValidator.validateId(skill.getId());
        ObjectValidator.validateString(skill.getName());
        ObjectValidator.validateObject(skill.getType());

        // Checks if the Skill exist in the database.
        Skill found = this.findById(skill.getId());

        if (found == null) {
            throw new IllegalArgumentException("Skill "+ skill.getName() + " not found!");
        } else {
            if (skill.getName().equals(found.getName())) {
                skillSaved = this.save(skill);
            } else {
                if (this.existsByName(skill.getName())) {
                    throw this.createDuplicatedNameException(skill, skill.getName());
                } else {
                    skillSaved = this.save(skill);
                }
            }

            logger.info("Skill updated: {}", skillSaved);
        }

        return skillSaved;
    }

    /**
     * Method used to create or update a Skill.
     * @param skill The Skill entity.
     * @return The saved Skill.
     * @throws IllegalArgumentException if the skill is null.
     */
    protected Skill save (Skill skill) {
        return this.skillRepository.save(skill);
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
     */
    @Override
    public Skill findById(Long id) {
        ObjectValidator.validateId(id);
      return skillRepository.findById(id).orElse(null);
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
        return skillRepository.findByRequiredSkill_Id(id);
    }

    /**
     * Finds and retrieves a list of skills based on their subskills.
     * @param id the id of the subskills to search for.
     * @return a list of skills matching the given subskill id.
     */
    public List<Skill> findBySubSkill(Long id) {
        ObjectValidator.validateId(id);
        return skillRepository.findBySubSkill_Id(id);
    }

    /**
     * Finds and retrieves a list of skills based on composed of skills id.
     * @param id the composed o skill id to search for.
     * @return a list of skills matching the given composed skill.
     * @throws IllegalArgumentException in case the given id is null.
     */
    public List<Skill> findBySkillComposedOfSkillId(Long id) {
        ObjectValidator.validateId(id);
        return skillRepository.findBySkillComposedOfSkillId(id);
    }

    /**
     * Finds and retrieves a list of skills based on their assessment.
     * @param id the id of the assessment to search for.
     * @return a list of skills matching the given assessment id.
     */
    public List<Skill> findByAssessments_Id(Long id) {
        ObjectValidator.validateId(id);
        return skillRepository.findByAssessments_Id(id);
    }

    /**
     * Gets all skills.
     * @return all skills.
     */
    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }
}
