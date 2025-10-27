package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.BCIModule;
import ca.uqam.latece.evo.server.core.model.BehaviorChangeInterventionPhase;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.repository.BCIModuleRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * BCIModule Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class BCIModuleService extends AbstractEvoService<BCIModule> {
    private static final Logger logger = LoggerFactory.getLogger(BCIModuleService.class);

    @Autowired
    private BCIModuleRepository moduleRepository;

    /**
     * Inserts a BCIModule in the database.
     * @param bciModule The BCIModule entity.
     * @return The saved BCIModule.
     * @throws IllegalArgumentException in case the given BCIModule is null.
     * @throws OptimisticLockingFailureException when the BCIModule uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    public BCIModule create(BCIModule bciModule) {
        BCIModule saved = null;

        ObjectValidator.validateObject(bciModule);
        ObjectValidator.validateString(bciModule.getName());
        ObjectValidator.validateString(bciModule.getPreconditions());
        ObjectValidator.validateString(bciModule.getPostconditions());
        this.validateSkills(bciModule);

        // The name should be unique.
        if (this.existsByName(bciModule.getName())) {
            throw this.createDuplicatedNameException(bciModule, bciModule.getName());
        } else {
            saved = this.save(bciModule);
                logger.info("BCIModule created: {}", saved);
        }

        return saved;
    }

    /**
     * Update a BCIModule in the database.
     * @param bciModule The BCIModule entity.
     * @return The saved BCIModule.
     * @throws IllegalArgumentException in case the given BCIModule is null.
     * @throws OptimisticLockingFailureException when the BCIModule uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    public BCIModule update(BCIModule bciModule) {
        BCIModule saved = null;

        ObjectValidator.validateObject(bciModule);
        ObjectValidator.validateId(bciModule.getId());
        ObjectValidator.validateString(bciModule.getName());
        ObjectValidator.validateString(bciModule.getPreconditions());
        ObjectValidator.validateString(bciModule.getPostconditions());
        this.validateSkills(bciModule);

        // Checks if the BCIModule exist in the database.
        BCIModule found = this.findById(bciModule.getId());

        if (found == null) {
            throw new IllegalArgumentException("Module "+ bciModule.getName() + " not found!");
        } else {
            if (bciModule.getName().equals(found.getName())) {
                saved = this.save(bciModule);
            } else {
                if (this.existsByName(bciModule.getName())) {
                    throw this.createDuplicatedNameException(bciModule, bciModule.getName());
                } else {
                    saved = this.save(bciModule);
                }
            }

            logger.info("BCIModule updated: {}", saved);
        }

        return saved;
    }

    /**
     * Method used to validate the relationship between BCIModule and Skill.
     * @param bciModule The BCIModule entity.
     */
    private void validateSkills(BCIModule bciModule) {
        if (bciModule.getSkills().isEmpty()) {
            throw new IllegalArgumentException("The Module '" + bciModule.getName() +
                    "' needs to be associated with one or more Skills!");
        } else {
            for (Skill skill : bciModule.getSkills()) {
                if (skill == null) {
                    throw new IllegalArgumentException("The Module '" + bciModule.getName() +
                            "' has been associated with a Skill null!");
                } else {
                    if (skill.getId() == null) {
                        throw new IllegalArgumentException("The Module '" + bciModule.getName() +
                                "' has been associated with a Skill that has a null ID");
                    }
                }
            }
        }
    }

    /**
     * Method used to create or update an BCIModule.
     * @param evoModel he BCIModule entity.
     * @return The BCIModule.
     */
    @Transactional
    @Override
    protected BCIModule save(BCIModule evoModel) {
        return this.moduleRepository.save(evoModel);
    }

    /**
     * Checks if a BCIModule entity with the specified id exists in the repository.
     * @param id the id of the BCIModule to check for existence, must not be null.
     * @return true if a BCIModule with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.moduleRepository.existsById(id);
    }

    /**
     * Checks if a BCIModule entity with the specified name exists in the repository.
     * @param name the name of the BCIModule to check for existence, must not be null.
     * @return true if a BCIModule with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        ObjectValidator.validateString(name);
        return this.moduleRepository.existsByName(name);
    }

    /**
     * Deletes the BCIModule with the given id.
     * <p>
     * If the BCIModule is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the BCIModule to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        this.moduleRepository.deleteById(id);
        logger.info("BCIModule deleted: {}", id);
    }

    /**
     * Retrieves a BCIModule by its id.
     * @param id The BCIModule Id to filter BCIModule entities by, must not be null.
     * @return the BCIModule with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    @Override
    public BCIModule findById(Long id) {
        ObjectValidator.validateId(id);
        return moduleRepository.findById(id).orElse(null);
    }

    /**
     * Finds a list of BCIModule entities by their name.
     * @param name the type of the BCIModule to search for.
     * @return a list of BCIModule entities matching the specified name.
     */
    public List<BCIModule> findByName(String name) {
        ObjectValidator.validateString(name);
        return moduleRepository.findByName(name);
    }

    /**
     * Finds a BCIModule list by Skill Id.
     * @param id the skill id used to search for BCIModule.
     * @return the BCIModule with the given name or null if none found.
     */
    public List<BCIModule> findBySkillId(Long id) {
        ObjectValidator.validateId(id);
        return moduleRepository.findBySkillsId(id);
    }

    /**
     * Finds a list of BCIModule entities by Skill.
     * @param skill the skill of the BCIModule to search for.
     * @return the BCIModule with the given name or null if none found.
     * @throws IllegalArgumentException if skill is null.
     */
    public List<BCIModule> findBySkills(Skill skill) {
        List<BCIModule> foundModules = new ArrayList<>();

        if (skill == null) {
            throw new IllegalArgumentException("Skill cannot be null!");
        } else {
            if ((skill.getId() == null) && (skill.getName() == null)) {
                throw new IllegalArgumentException("Skill id and name cannot be null!");
            } else {
                foundModules = moduleRepository.findBySkills(skill);
            }
        }

        return foundModules;
    }

    /**
     * Finds a list of BCIModule entities by their BehaviorChangeInterventionPhase Id.
     * @param id the BehaviorChangeInterventionPhase id used to search for BCIModule.
     * @return the BCIModule with the given name or null if none found.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BCIModule> findByBehaviorChangeInterventionPhasesId(Long id) {
        ObjectValidator.validateId(id);
        return moduleRepository.findByBehaviorChangeInterventionPhasesId(id);
    }

    /**
     * Finds a list of BCIModule entities by their BehaviorChangeInterventionPhase.
     * @param behaviorChangeInterventionPhases the BehaviorChangeInterventionPhase of the BCIModule to search for.
     * @return the BCIModule with the given name or null if none found.
     * @throws IllegalArgumentException if BehaviorChangeInterventionPhase is null.
     */
    public List<BCIModule> findByBehaviorChangeInterventionPhases(BehaviorChangeInterventionPhase behaviorChangeInterventionPhases){
        List<BCIModule> foundModules = new ArrayList<>();

        if (behaviorChangeInterventionPhases == null) {
            throw new IllegalArgumentException("BehaviorChangeInterventionPhases cannot be null!");
        } else {
            if (behaviorChangeInterventionPhases.getId() == null) {
                throw new IllegalArgumentException("BehaviorChangeInterventionPhases id cannot be null!");
            } else {
                foundModules = moduleRepository.findByBehaviorChangeInterventionPhases(behaviorChangeInterventionPhases);
            }
        }

        return foundModules;
    }

    /**
     * Gets all BCIModule.
     * @return all BCIModule.
     */
    @Override
    public List<BCIModule> findAll() {
        return moduleRepository.findAll().stream().toList();
    }
}