package ca.uqam.latece.evo.server.core.repository;


import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.Scale;
import ca.uqam.latece.evo.server.core.model.Assessment;
import ca.uqam.latece.evo.server.core.model.Develops;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Assessment repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface AssessmentRepository extends EvoRepository<Assessment> {

    /**
     * Finds a list of BCIActivity entities by their name.
     * @param name the name of the BCIActivity to search for.
     * @return the BCIActivity with the given name or empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    List<Assessment> findByName(String name);

    /**
     * Checks if an Assessment entity with the specified name exists in the repository.
     * @param name the name of the Assessment to check for existence, must not be null.
     * @return true if an Assessment with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    boolean existsByName(String name);

    /**
     * Finds a list of Assessment entities by their type.
     * @param type the type of the Assessment to search for.
     * @return a list of Assessment entities matching the specified type.
     * @throws IllegalArgumentException if the type is null.
     */
    List<Assessment> findByType(ActivityType type);

    /**
     * Finds a list of Assessment entities by Develops Id.
     * @param id The Assessment id to filter Assessment entities by must not be null.
     * @return the Assessment with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    List<Assessment> findByDevelopsBCIActivity_Id(Long id);

    /**
     * Finds a list of Assessment entities by Requires Id.
     * @param id The Requires id to filter Assessment entities by must not be null.
     * @return the Assessment with the given Requires id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if Requires id is null.
     */
    List<Assessment> findByRequiresBCIActivities_Id(Long id);

    /**
     * Finds a list of Assessment entities by Content Id.
     * @param id The Content id to filter Assessment entities by must not be null.
     * @return the Assessment with the given Content id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if Content id is null.
     */
    List<Assessment> findByContentBCIActivities_Id(Long id);

    /**
     * Finds a list of Assessment entities by Role id.
     * @param id The Role id to filter Assessment entities by must not be null.
     * @return the Assessment with the given Role id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if Role id is null.
     */
    List<Assessment> findByRoleBCIActivities_Id(Long id);

    /**
     * Finds a list of Assessment entities by ComposedOf id.
     * @param id The ComposedOf id to filter Assessment entities by must not be null.
     * @return the Assessment with the given composedOf id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if composedOf id is null.
     */
    List<Assessment> findByComposedOfList_Id(Long id);

    /**
     * Finds a list of Assessment entities by AssesseeRole id.
     * @param assessmentAssesseeRoleId The AssesseeRole id to filter Assessment entities by must not be null.
     * @return the Assessment with the given AssesseeRole id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if AssesseeRole id is null.
     */
    List<Assessment> findByAssessmentAssesseeRole_Id(Long assessmentAssesseeRoleId);

    /**
     * Finds a list of Assessment entities by AssessorRole is.
     * @param assessmentAssessorRoleId The AssessorRole id to filter Assessment entities by must not be null.
     * @return the Assessment with the given AssessorRole id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if AssessorRole id is null.
     */
    List<Assessment> findByAssessmentAssessorRole_Id(Long assessmentAssessorRoleId);

    /**
     * Finds a list of Assessment entities by Scale.
     * @param assessmentScale The Scale to filter Assessment entities by must not be null.
     * @return the Assessment with the given Scale or Optional#empty() if none found.
     * @throws IllegalArgumentException – if Scale is null.
     */
    List<Assessment> findByAssessmentScale(Scale assessmentScale);

    /**
     * Finds a list of Assessment entities by scoring function.
     * @param assessmentScoringFunction The Assessment scoring function to filter Assessment entities by must not be null.
     * @return the Assessment with the given scoring function or Optional#empty() if none found.
     * @throws IllegalArgumentException – if scoring function is null.
     */
    List<Assessment> findByAssessmentScoringFunction(String assessmentScoringFunction);

    /**
     * Finds a list of Assessment entities by SelfRelationship id.
     * @param id The SelfRelationship id to filter Assessment entities by must not be null.
     * @return the Assessment with the given SelfRelationship id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if SelfRelationship id is null.
     */
    List<Assessment> findByAssessmentSelfRelationship_Id(Long id);

    /**
     * Finds a list of Assessment entities by assessment self-relationship.
     * @param assessmentSelfRelationship The Assessment to filter Assessment entities by must not be null.
     * @return the Assessment with the given Assessment or Optional#empty() if none found.
     * @throws IllegalArgumentException – if Assessment is null.
     */
    List<Assessment> findByAssessmentSelfRelationship(Assessment assessmentSelfRelationship);
}
