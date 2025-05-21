package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.repository.BCIModuleRepository;
import ca.uqam.latece.evo.server.core.service.BCIModuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The BCIModuleController test class for the {@link BCIModuleController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = BCIModuleController.class)
@ContextConfiguration(classes = {BCIModuleController.class, BCIModuleService.class, BCIModule.class})
public class BCIModuleControllerTest extends AbstractControllerTest {
    @MockBean
    private BCIModuleRepository bciModuleRepository;

    private BCIModule bciModule = new BCIModule();
    private Skill skill = new Skill();
    private BehaviorChangeInterventionPhase behaviorChangePhase = new BehaviorChangeInterventionPhase();

    @BeforeEach
    @Override
    void setUp() {
        // Creates a BehaviorChangeInterventionPhase.
        behaviorChangePhase.setId(55L);
        behaviorChangePhase.setEntryConditions("Phase Entry Conditions");
        behaviorChangePhase.setExitConditions("Phase Exit Conditions");

        // Creates a Skill.
        skill.setId(3L);
        skill.setName("Skill 1");
        skill.setDescription("Skill Description");
        skill.setType(SkillType.BCT);

        //  Creates a BCIModule.
        bciModule.setId(1L);
        bciModule.setName("Test Module");
        bciModule.setDescription("Test Module Description");
        bciModule.setPreconditions("Test Preconditions");
        bciModule.setPostconditions("Test Post conditions");
        bciModule.setSkills(skill);
        bciModule.setBehaviorChangeInterventionPhases(behaviorChangePhase);

        // Save in the database.
        when(bciModuleRepository.save(bciModule)).thenReturn(bciModule);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        // Perform a POST request to test the controller.
        performCreateRequest("/bcimodule", bciModule);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        // Creates an BCIModule invalid.
        BCIModule bciModuleBadRequest = new BCIModule();
        bciModuleBadRequest.setId(2L);
        // Perform a POST with a Bad Request to test the controller.
        performCreateRequestBadRequest("/bcimodule", bciModuleBadRequest);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update a BCIModule.
        bciModule.setName("Module 44");
        // Save in the database.
        when(bciModuleRepository.save(bciModule)).thenReturn(bciModule);
        // Perform a PUT request to test the controller.
        performUpdateRequest("/bcimodule", bciModule,"$.name", bciModule.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        // perform a DELETE request to test the controller.
        performDeleteRequest("/bcimodule/" + bciModule.getId(), bciModule);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Mock behavior for bciModuleRepository.save
        when(bciModuleRepository.save(bciModule)).thenReturn(bciModule);
        // Mock behavior for bciModuleRepository.findById().
        when(bciModuleRepository.findById(bciModule.getId())).thenReturn(Optional.of(bciModule));
        // Perform a GET request to test the controller.
        performGetRequest("/bcimodule/find/" + bciModule.getId(), "$.name", bciModule.getName());
    }

    @Test
    void testFindByName() throws Exception {
        // Mock behavior for bciModuleRepository.findByName().
        when(bciModuleRepository.findByName(bciModule.getName())).thenReturn(Collections.singletonList(bciModule));
        // Perform a GET request to test the controller.
        performGetRequest("/bcimodule/find/name/" + bciModule.getName(),"$[0].name", bciModule.getName());
    }

    @Test
    void findBySkill() throws Exception {
        // Mock behavior for bciModuleRepository.findBySkills().
        when(bciModuleRepository.findBySkills(skill)).thenReturn(Collections.singletonList(bciModule));
        // Perform a GET request to test the controller.
        performCreateRequest_Status_Ok("/bcimodule/find/skill", skill);
    }

    @Test
    void findByBehaviorChangeInterventionPhases() throws Exception {
        // Mock behavior for bciModuleRepository.findByBehaviorChangeInterventionPhases().
        when(bciModuleRepository.findByBehaviorChangeInterventionPhases(behaviorChangePhase)).thenReturn(Collections.singletonList(bciModule));
        // Perform a GET request to test the controller.
        performCreateRequest_Status_Ok("/bcimodule/find/behaviorchangeinterventionphase", behaviorChangePhase);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for bciModuleRepository.findAll().
        when(bciModuleRepository.findAll()).thenReturn(Collections.singletonList(bciModule));
        // Perform a GET request to test the controller.
        performGetRequest("/bcimodule", "$[0].id", bciModule.getId());
    }
}