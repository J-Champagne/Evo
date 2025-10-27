package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.Scale;
import ca.uqam.latece.evo.server.core.model.Assessment;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.repository.AssessmentRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Assessment Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class AssessmentService extends AbstractEvoService<Assessment> {
    private static final Logger logger = LoggerFactory.getLogger(AssessmentService.class);

    @Autowired
    private AssessmentRepository  assessmentRepository;


    /**
     * Creates an Assessment in the database.
     * @param assessment The Assessment entity.
     * @return The saved Assessment.
     * @throws IllegalArgumentException in case the given Assessment is null, or if Skills is empty,
     * AssesseeRole is null, or AssessorRole is null, or if the name is duplicated.
     */
    @Override
    public Assessment create(Assessment assessment) {
        Assessment saved = null;

        ObjectValidator.validateObject(assessment);
        ObjectValidator.validateString(assessment.getName());

        // The name should be unique.
        if (this.existsByName(assessment.getName())) {
            throw this.createDuplicatedNameException(assessment, assessment.getName());
        } else {
            saved = this.save(assessment);
            logger.info("Assessment created: {}", saved);
        }

        return saved;
    }

    /**
     * Updates an Assessment in the database.
     * @param evoModel The Assessment entity.
     * @return The saved Assessment.
     * @throws IllegalArgumentException in case the given Assessment is null.
     */
    @Override
    public Assessment update(Assessment evoModel) {
        Assessment assessment = null;

        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateId(evoModel.getId());
        ObjectValidator.validateString(evoModel.getName());

        // Checks if the BCIActivity exists in the database.
        BCIActivity found = this.findById(evoModel.getId());

        if (found == null) {
            throw new IllegalArgumentException("Assessment " + evoModel.getName() + " not found!");
        } else {
            if (evoModel.getName().equals(found.getName())) {
                assessment = this.save(evoModel);
            } else {
                if (this.existsByName(evoModel.getName())) {
                    throw this.createDuplicatedNameException(evoModel, evoModel.getName());
                } else {
                    assessment = this.save(evoModel);
                }
            }

            logger.info("BCIActivity updated: {}", assessment);
        }

        return assessment;
    }

    /**
     * Method used to validate the relationship between Assessment and Skill.
     * @param assessment The Assessment entity.
     */
    private void validateSkills(Assessment assessment) {
        if (assessment.getSkills().isEmpty()) {
            throw new IllegalArgumentException("The Assessment '" + assessment.getName() +
                    "' needs to be associated with one or more Skills!");
        } else {
            for (Skill skill : assessment.getSkills()) {
                if (skill == null) {
                    throw new IllegalArgumentException("The Assessment '" + assessment.getName() +
                            "' has been associated with a Skill null!");
                } else {
                    if (skill.getId() == null) {
                        throw new IllegalArgumentException("The Assessment '" + assessment.getName() +
                                "' has been associated with a Skill that has a null ID");
                    }
                }
            }
        }
    }

    /**
     * Method used to create or update an Assessment.
     * @param evoModel he Assessment entity.
     * @return The Assessment.
     */
    @Override
    protected Assessment save(Assessment evoModel) {
        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateObject(evoModel.getAssessmentAssesseeRole());
        ObjectValidator.validateId(evoModel.getAssessmentAssesseeRole().getId());
        ObjectValidator.validateObject(evoModel.getAssessmentAssessorRole());
        ObjectValidator.validateId(evoModel.getAssessmentAssessorRole().getId());
        ObjectValidator.validateObject(evoModel.getSkills());
        this.validateSkills(evoModel);

        return this.assessmentRepository.save(evoModel);
    }

    /**
     * Checks if an Assessment entity with the specified name exists in the repository.
     * @param name the name of the Assessment to check for existence, must not be null.
     * @return true if an Assessment with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        ObjectValidator.validateString(name);
        return this.assessmentRepository.existsByName(name);
    }

    /**
     * Checks if an Assessment entity with the specified id exists in the repository.
     * @param id the id of the Assessment to check for existence, must not be null.
     * @return true if an Assessment with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.assessmentRepository.existsById(id);
    }

    /**
     * Deletes the Assessment with the given id.
     * <p>
     * If the Assessment is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Assessment to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        this.assessmentRepository.deleteById(id);
        logger.info("Assessment deleted: {}", id);
    }

    /**
     * Retrieves an Assessment by its id.
     * @param id The Assessment id to filter Assessment entities by, must not be null.
     * @return the Assessment with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    @Override
    public Assessment findById(Long id) {
        ObjectValidator.validateId(id);
        return this.assessmentRepository.findById(id).orElse(null);
    }

    /**
     * Finds a list of Assessment entities by their name.
     * @param name the type of the Assessment to search for.
     * @return a list of Assessment entities matching the specified name.
     * @throws IllegalArgumentException – if the name is null.
     */
    public List<Assessment> findByName(String name) {
        ObjectValidator.validateString(name);
        return this.assessmentRepository.findByName(name);
    }

    /**
     * Finds a list of Assessment entities by their type.
     * @param type the type of the Assessment to search for.
     * @return a list of Assessment entities matching the specified type.
     * @throws IllegalArgumentException – if the type is null.
     */
    public List<Assessment> findByType(ActivityType type) {
        ObjectValidator.validateObject(type);
        return this.assessmentRepository.findByType(type);
    }

    /**
     * Finds a list of Assessment entities by Develops id.
     * @param id The Assessment id to filter Assessment entities by must not be null.
     * @return the Assessment with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    public List<Assessment> findByDevelopsBCIActivity_Id(Long id) {
        ObjectValidator.validateObject(id);
        return this.assessmentRepository.findByDevelopsBCIActivity_Id(id);
    }

    /**
     * Finds a list of Assessment entities by Requires id.
     * @param id The Requires id to filter Assessment entities by must not be null.
     * @return the Assessment with the given Requires id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if Requires id is null.
     */
    public List<Assessment> findByRequiresBCIActivities_Id(Long id) {
        ObjectValidator.validateObject(id);
        return this.assessmentRepository.findByRequiresBCIActivities_Id(id);
    }

    /**
     * Finds a list of Assessment entities by Content id.
     * @param id The Content id to filter Assessment entities by must not be null.
     * @return the Assessment with the given Content id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if Content id is null.
     */
    public List<Assessment> findByContentBCIActivities_Id(Long id) {
        ObjectValidator.validateObject(id);
        return this.assessmentRepository.findByContentBCIActivities_Id(id);
    }

    /**
     * Finds a list of Assessment entities by Role id.
     * @param id The Role id to filter Assessment entities by must not be null.
     * @return the Assessment with the given Role id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if Role id is null.
     */
    public List<Assessment> findByRoleBCIActivities_Id(Long id) {
        ObjectValidator.validateObject(id);
        return this.assessmentRepository.findByParties_Id(id);
    }

    /**
     * Finds a list of Assessment entities by ComposedOf id.
     * @param id The ComposedOf id to filter Assessment entities by must not be null.
     * @return the Assessment with the given composedOf id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if composedOf id is null.
     */
    public List<Assessment> findByComposedOfList_Id(Long id) {
        ObjectValidator.validateObject(id);
        return this.assessmentRepository.findByComposedOfList_Id(id);
    }

    /**
     * Finds a list of Assessment entities by AssesseeRole id.
     * @param id The AssesseeRole id to filter Assessment entities by must not be null.
     * @return the Assessment with the given AssesseeRole id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if AssesseeRole id is null.
     */
    public List<Assessment> findByAssessmentAssesseeRole_Id(Long id) {
        ObjectValidator.validateId(id);
        return this.assessmentRepository.findByAssessmentAssesseeRole_Id(id);
    }

    /**
     * Finds a list of Assessment entities by AssessorRole is.
     * @param id The AssessorRole id to filter Assessment entities by must not be null.
     * @return the Assessment with the given AssessorRole id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if AssessorRole id is null.
     */
    public List<Assessment> findByAssessmentAssessorRole_Id(Long id) {
        ObjectValidator.validateId(id);
        return this.assessmentRepository.findByAssessmentAssessorRole_Id(id);
    }

    /**
     * Finds a list of Assessment entities by Scale.
     * @param scale The Scale to filter Assessment entities by must not be null.
     * @return the Assessment with the given Scale or Optional#empty() if none found.
     * @throws IllegalArgumentException – if Scale is null.
     */
    public List<Assessment> findByAssessmentScale(Scale scale) {
        ObjectValidator.validateObject(scale);
        return this.assessmentRepository.findByAssessmentScale(scale);
    }

    /**
     * Finds a list of Assessment entities by scoring function.
     * @param scoringFunction The Assessment scoring function to filter Assessment entities by must not be null.
     * @return the Assessment with the given scoring function or Optional#empty() if none found.
     * @throws IllegalArgumentException – if scoring function is null.
     */
    public List<Assessment> findByAssessmentScoringFunction(String scoringFunction) {
        ObjectValidator.validateString(scoringFunction);
        return this.assessmentRepository.findByAssessmentScoringFunction(scoringFunction);
    }

    /**
     * Finds a list of Assessment entities by SelfRelationship id.
     * @param id The SelfRelationship id to filter Assessment entities by must not be null.
     * @return the Assessment with the given SelfRelationship id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if SelfRelationship id is null.
     */
    public List<Assessment> findByAssessmentSelfRelationship_Id(Long id) {
        ObjectValidator.validateId(id);
        return this.assessmentRepository.findByAssessmentSelfRelationship_Id(id);
    }

    /**
     * Finds a list of Assessment entities by assessment self-relationship.
     * @param assessment The Assessment to filter Assessment entities by must not be null.
     * @return the Assessment with the given Assessment or Optional#empty() if none found.
     * @throws IllegalArgumentException – if Assessment is null.
     */
    public List<Assessment> findByAssessmentSelfRelationship(Assessment assessment) {
        ObjectValidator.validateObject(assessment);
        return this.assessmentRepository.findByAssessmentSelfRelationship(assessment);
    }

    /**
     * Gets all Assessment.
     * @return all Assessment.
     */
    @Override
    public List<Assessment> findAll() {
        return this.assessmentRepository.findAll();
    }

}
