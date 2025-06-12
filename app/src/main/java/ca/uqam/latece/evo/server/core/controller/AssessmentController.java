package ca.uqam.latece.evo.server.core.controller;

import java.util.List;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.Scale;
import ca.uqam.latece.evo.server.core.model.Assessment;
import ca.uqam.latece.evo.server.core.service.AssessmentService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;

/**
 * Assessment Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/assessment")
public class AssessmentController extends AbstractEvoController <Assessment> {
    private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

    @Autowired
    private AssessmentService assessmentService;

    /**
     * Creates an Assessment in the database.
     * @param model The Assessment entity.
     * @return The saved Assessment.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    @Override
    public ResponseEntity<Assessment> create(@Valid @RequestBody Assessment model) {
        ResponseEntity<Assessment> response;

        try{
            ObjectValidator.validateObject(model);
            Assessment saved = assessmentService.create(model);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new Assessment: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.error("Failed to create new Assessment.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new Assessment. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates an Assessment in the database.
     * @param model The Assessment entity.
     * @return The saved Assessment.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<Assessment> update(@RequestBody Assessment model) {
        ResponseEntity<Assessment> response;

        try {
            ObjectValidator.validateObject(model);
            Assessment updated = assessmentService.update(model);

            if (updated != null && updated.getId().equals(model.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated Assessment: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.error("Failed to update Assessment.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update Assessment. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the Assessment with the given id.
     * <p>
     * If the Assessment is not found in the persistence store, it is silently ignored.
     * @param id the unique identifier of the Assessment to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @Override
    public void deleteById(@PathVariable Long id) {
        ObjectValidator.validateId(id);
        this.assessmentService.deleteById(id);
        logger.info("Assessment deleted: {}", id);
    }

    /**
     * Retrieves an Assessment by its id.
     * @param id The Assessment id to filter Assessment entities by, must not be null.
     * @return the Assessment with the given id or Optional#empty() if none found.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    @Override
    public ResponseEntity<Assessment> findById(@PathVariable Long id) {
        ResponseEntity<Assessment> response;

        try {
            ObjectValidator.validateId(id);
            Assessment assessment = assessmentService.findById(id);

            if (assessment != null && assessment.getId().equals(id)) {
                response = new ResponseEntity<>(assessment, HttpStatus.OK);
                logger.info("Found Assessment by id: {}", assessment);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Assessment entities by their name.
     * @param name the type of the Assessment to search for.
     * @return a list of Assessment entities matching the specified name.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByName(@PathVariable String name) {
        ResponseEntity<List<Assessment>> response;

        try {
            ObjectValidator.validateString(name);
            List<Assessment> assessmentList = assessmentService.findByName(name);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment by name: {}", name);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment by name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Assessment entities by their type.
     * @param type the type of the Assessment to search for.
     * @return a list of Assessment entities matching the specified type.
     */
    @GetMapping("/find/type/{type}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByType(@PathVariable ActivityType type) {
        ResponseEntity<List<Assessment>> response;

        try {
            ObjectValidator.validateObject(type);
            List<Assessment> assessmentList = assessmentService.findByType(type);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment list: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment list by type: {}", type);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment list by type. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Assessment entities that match the specified Develops id.
     * @param id The Develops id to filter Assessment entities by, must not be null.
     * @return a list of Assessment entities that have the specified Develops id, or an empty list if no matches are found.
     */
    @GetMapping("/find/develops/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByDevelops_Id(@PathVariable Long id) {
        ResponseEntity<List<Assessment>> response;

        try {
            ObjectValidator.validateId(id);
            List<Assessment> assessmentList = assessmentService.findByDevelopsBCIActivity_Id(id);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment with Develops association: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment by Develops Id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment by Develops Id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Assessment entities that match the specified Requires id.
     * @param id The Requires id to filter Assessment entities by, must not be null.
     * @return a list of Assessment entities that have the specified Requires id, or an empty list if no matches are found.
     */
    @GetMapping("/find/requires/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByRequires_Id(@PathVariable Long id) {
        ResponseEntity<List<Assessment>> response;

        try {
            ObjectValidator.validateId(id);
            List<Assessment> assessmentList = assessmentService.findByRequiresBCIActivities_Id(id);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment with Requires association: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment by Requires Id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment by Requires Id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Assessment entities that match the specified Role id.
     * @param id The Role id to filter Assessment entities by, must not be null.
     * @return a list of Assessment entities that have the specified Role id, or an empty list if no matches are found.
     */
    @GetMapping("/find/role/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByRole(@PathVariable Long id) {
        ResponseEntity<List<Assessment>> response;

        try {
            ObjectValidator.validateId(id);
            List<Assessment> assessmentList = assessmentService.findByRoleBCIActivities_Id(id);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment with Role association: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment by Role: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment by Role. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Assessment entities that match the specified Content id.
     * @param id The Content id to filter Assessment entities by, must not be null.
     * @return a list of Assessment entities that have the specified Content id, or an empty list if no matches are found.
     */
    @GetMapping("/find/content/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByContent(@PathVariable Long id) {
        ResponseEntity<List<Assessment>> response;

        try {
            ObjectValidator.validateId(id);
            List<Assessment> assessmentList = assessmentService.findByContentBCIActivities_Id(id);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment with Content association: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment with Content association: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment with Content association. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Assessment entities by ComposedOf id.
     * @param id The ComposedOf id to filter Assessment entities by must not be null.
     * @return the Assessment with the given composedOf id.
     */
    @GetMapping("/find/composedof/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByComposedOfList_Id(@PathVariable Long id) {
        ResponseEntity<List<Assessment>> response;

        try {
            ObjectValidator.validateId(id);
            List<Assessment> assessmentList = assessmentService.findByComposedOfList_Id(id);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment with ComposedOf association: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment with ComposedOf association: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment with ComposedOf association. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Assessment entities by AssesseeRole id.
     * @param id The AssesseeRole id to filter Assessment entities by must not be null.
     * @return the Assessment with the given AssesseeRole id.
     */
    @GetMapping("/find/assesseerole/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByAssessmentAssesseeRole_Id(@PathVariable Long id) {
        ResponseEntity<List<Assessment>> response;

        try {
            ObjectValidator.validateId(id);
            List<Assessment> assessmentList = assessmentService.findByAssessmentAssesseeRole_Id(id);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment with AssesseeRole association: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment with AssesseeRole association: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment with AssesseeRole association. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Assessment entities by AssessorRole is.
     * @param id The AssessorRole id to filter Assessment entities by must not be null.
     * @return the Assessment with the given AssessorRole id.
     */
    @GetMapping("/find/assessorrole/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByAssessmentAssessorRole_Id(@PathVariable Long id) {
        ResponseEntity<List<Assessment>> response;

        try {
            ObjectValidator.validateId(id);
            List<Assessment> assessmentList = assessmentService.findByAssessmentAssessorRole_Id(id);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment with AssessorRole association: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment with AssessorRole association: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment with AssessorRole association. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Assessment entities by Scale.
     * @param scale The Scale to filter Assessment entities by must not be null.
     * @return the Assessment with the given Scale.
     */
    @GetMapping("/find/scale/{scale}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByAssessmentScale(@PathVariable Scale scale) {
        ResponseEntity<List<Assessment>> response;

        try {
            ObjectValidator.validateObject(scale);
            List<Assessment> assessmentList = assessmentService.findByAssessmentScale(scale);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment with Scale: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment with Scale: {}", scale);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment with Scale. Error: {}", e.getMessage());
        }

        return response;

    }

    /**
     * Finds a list of Assessment entities by scoring function.
     * @param scoringFunction The Assessment scoring function to filter Assessment entities by must not be null.
     * @return the Assessment with the given scoring function or Optional#empty() if none found.
     */
    @GetMapping("/find/scoringfunction/{scoringFunction}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByAssessmentScoringfunction(@PathVariable String scoringFunction) {
        ResponseEntity<List<Assessment>> response;

        try {
            ObjectValidator.validateString(scoringFunction);
            List<Assessment> assessmentList = assessmentService.findByAssessmentScoringFunction(scoringFunction);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment with scoring function: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment with scoring function: {}", scoringFunction);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment with scoring function. Error: {}", e.getMessage());
        }

        return response;

    }

    /**
     * Finds a list of Assessment entities by their Assessment self-relationship id.
     * @param id The Assessment self-relationship id to filter Assessment entities by must not be null.
     * @return the Assessment with the given Assessment self-relationship id.
     */
    @GetMapping("/find/selfrelationship/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByAssessmentSelfRelationship_Id(@PathVariable Long id) {
        ResponseEntity<List<Assessment>> response;

        try {
            ObjectValidator.validateId(id);
            List<Assessment> assessmentList = assessmentService.findByAssessmentSelfRelationship_Id(id);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment with Assessment self-relationship id association: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment Assessment self-relationship id association: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment with Assessment self-relationship id association. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Assessment entities by assessment self-relationship.
     * @param assessment The Assessment to filter Assessment entities by must not be null.
     * @return the Assessment with the given Assessment.
     */
    @GetMapping("/find/selfrelationship")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findByAssessmentSelfRelationship(@RequestBody Assessment assessment) {
        ResponseEntity<List<Assessment>> response;

        try{
            ObjectValidator.validateObject(assessment);
            List<Assessment> assessmentList = assessmentService.findByAssessmentSelfRelationship(assessment);

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found Assessment with Assessment self-relationship association: {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Assessment with Assessment self-relationship association: {}", assessment);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Assessment with Assessment self-relationship association. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all Assessment.
     * @return all Assessment.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Assessment>> findAll() {
        ResponseEntity<List<Assessment>> response;

        try {
            List<Assessment> assessmentList = assessmentService.findAll();

            if (assessmentList != null && !assessmentList.isEmpty()) {
                response = new ResponseEntity<>(assessmentList, HttpStatus.OK);
                logger.info("Found all Assessment : {}", assessmentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all Assessment list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all Assessment list. Error: {}", e.getMessage());
        }

        return response;
    }
}
