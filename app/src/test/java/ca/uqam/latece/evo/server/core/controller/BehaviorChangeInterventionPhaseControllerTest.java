package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionBlockRepository;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionPhaseRepository;
import ca.uqam.latece.evo.server.core.repository.BehaviorChangeInterventionRepository;
import ca.uqam.latece.evo.server.core.service.BehaviorChangeInterventionPhaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;


/**
 * The BehaviorChangeInterventionPhase Controller test class for the {@link BehaviorChangeInterventionPhaseController},
 * responsible for testing its various functionalities. This class includes integration tests for CRUD operations
 * supported the controller class, using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = BehaviorChangeInterventionPhaseController.class)
@ContextConfiguration(classes = {BehaviorChangeInterventionPhaseController.class,
        BehaviorChangeInterventionPhaseService.class, BehaviorChangeInterventionPhase.class})
public class BehaviorChangeInterventionPhaseControllerTest extends AbstractControllerTest {
    @MockBean
    private BehaviorChangeInterventionPhaseRepository interventionPhaseRepository;

    @MockBean
    private BehaviorChangeInterventionRepository behaviorChangeInterventionRepository;

    @MockBean
    private BehaviorChangeInterventionBlockRepository interventionBlockRepository;

    private BehaviorChangeInterventionPhase interventionPhase;
    private BehaviorChangeIntervention intervention;
    private BehaviorChangeInterventionBlock interventionBlock;
    private BCIModule module;
    private Skill skill;

    private static final String PRECONDITIONS = "Preconditions Module";
    private static final String POSTCONDITION = "Postcondition Module";

    @BeforeEach
    @Override
    void setUp() {
        // Creates the BehaviorChangeIntervention.
        intervention = new BehaviorChangeIntervention();
        intervention.setId(1L);
        intervention.setName("BCI");

        // Mock behavior for behaviorChangeInterventionRepository.save().
        when(behaviorChangeInterventionRepository.save(intervention)).
                thenReturn(intervention);

        skill = new Skill();
        skill.setId(35L);
        skill.setType(SkillType.BCT);
        skill.setName("Skill BCIModule - Phase");
        skill.setDescription("Description Test - Phase");

        // Creates BCIModule.
        module = new BCIModule();
        module.setId(99L);
        module.setName("Module BCI - Phase");
        module.setPreconditions(PRECONDITIONS);
        module.setPostconditions(POSTCONDITION);
        module.setDescription("Module BCI Description - Phase");
        module.setSkills(skill);

        // Creates the BehaviorChangeInterventionPhase.
        interventionPhase = new BehaviorChangeInterventionPhase();
        interventionPhase.setId(1L);
        interventionPhase.setEntryConditions("EntryConditions");
        interventionPhase.setExitConditions("ExitConditions");
        interventionPhase.setBehaviorChangeIntervention(intervention);
        interventionPhase.setBciModules(module);

        // Mock behavior for interventionPhaseRepository.save().
        when(interventionPhaseRepository.save(interventionPhase)).
                thenReturn(interventionPhase);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest("/bahaviorchangeinterventionphase", interventionPhase);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Create a new Behavior Change Intervention Phase.
        BehaviorChangeInterventionPhase interventionToUpdate = new BehaviorChangeInterventionPhase();
        interventionToUpdate.setId(interventionPhase.getId());
        interventionToUpdate.setEntryConditions("EntryConditions 2");
        interventionToUpdate.setExitConditions("ExitConditions 2");

        // Mock behavior for interventionPhaseRepository.save().
        when(interventionPhaseRepository.save(interventionToUpdate)).thenReturn(interventionToUpdate);

        // Mock behavior for interventionPhaseRepository.findById().
        when(interventionPhaseRepository.findById(interventionToUpdate.getId())).
                thenReturn(Optional.of(interventionToUpdate));

        // Perform a PUT request to test the controller.
        performUpdateRequest("/bahaviorchangeinterventionphase", interventionToUpdate,
                "$.entryConditions",
                interventionToUpdate.getEntryConditions());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/bahaviorchangeinterventionphase/" + interventionPhase.getId(),
                interventionPhase);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create a new Behavior Change Intervention Phase.
        BehaviorChangeInterventionPhase intervention = new BehaviorChangeInterventionPhase();
        intervention.setId(2L);
        intervention.setEntryConditions("EntryConditions 25");
        intervention.setExitConditions("ExitConditions 25");

        // Mock behavior for interventionPhaseRepository.save().
        when(interventionPhaseRepository.save(intervention)).
                thenReturn(intervention);

        // Mock behavior for interventionPhaseRepository.findById().
        when(interventionPhaseRepository.findById(intervention.getId())).
                thenReturn(Optional.of(intervention));

        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeinterventionphase/find/" + intervention.getId(),
                "$.entryConditions", intervention.getEntryConditions());
    }

    @Test
    void testFindByEntryConditions() throws Exception {
        // Create a new Behavior Change Intervention Phase.
        BehaviorChangeInterventionPhase intervention = new BehaviorChangeInterventionPhase();
        intervention.setId(3L);
        intervention.setEntryConditions("EntryConditions 325");
        intervention.setExitConditions("ExitConditions 325");

        // Mock behavior for interventionPhaseRepository.save().
        when(interventionPhaseRepository.save(intervention)).
                thenReturn(intervention);

        // Mock behavior for interventionPhaseRepository.findByEntryConditions().
        when(interventionPhaseRepository.findByEntryConditions(intervention.getEntryConditions())).
                thenReturn(Collections.singletonList(intervention));

        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeinterventionphase/find/entrycond/" +
                        intervention.getEntryConditions(), "$[0].entryConditions",
                intervention.getEntryConditions());
    }

    @Test
    void testFindByExitConditions() throws Exception {
        // Create a new Behavior Change Intervention Phase.
        BehaviorChangeInterventionPhase intervention = new BehaviorChangeInterventionPhase();
        intervention.setId(3L);
        intervention.setEntryConditions("EntryConditions 325");
        intervention.setExitConditions("ExitConditions 325");

        // Mock behavior for interventionPhaseRepository.save().
        when(interventionPhaseRepository.save(intervention)).
                thenReturn(intervention);

        // Mock behavior for interventionPhaseRepository.findByExitConditions().
        when(interventionPhaseRepository.findByExitConditions(intervention.getExitConditions())).
                thenReturn(Collections.singletonList(intervention));

        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeinterventionphase/find/exitcond/" + intervention.getExitConditions(),
                "$[0].exitConditions", intervention.getExitConditions());
    }

    @Test
    void findByBehaviorChangeInterventionId() throws Exception {
        BehaviorChangeInterventionPhase interventionPhaseResult = new BehaviorChangeInterventionPhase();

        // Mock behavior for interventionPhaseRepository.findByBehaviorChangeInterventionId().
        when(interventionPhaseRepository.findByBehaviorChangeInterventionId(intervention.getId())).
                thenReturn(Collections.singletonList(interventionPhaseResult));

        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeinterventionphase/find/behaviorchangeintervention/" +
                        intervention.getId(), "$[0].exitConditions",
                interventionPhaseResult.getExitConditions());
    }

    @Test
    void findByBehaviorChangeInterventionBlockId() throws Exception {
        // Creates a BehaviorChangeInterventionBlock.
        interventionBlock = new BehaviorChangeInterventionBlock();
        interventionBlock.setId(3L);
        interventionBlock.setEntryConditions("EntryConditions findByBehaviorChangeInterventionBlockId");
        interventionBlock.setExitConditions("ExitConditions findByBehaviorChangeInterventionBlockId");
        interventionBlock.addBehaviorChangeInterventionPhase(interventionPhase);

        // Mock behavior for interventionBlockRepository.save().
        when(interventionBlockRepository.save(interventionBlock)).thenReturn(interventionBlock);

        // Phase result object.
        BehaviorChangeInterventionPhase interventionPhaseResult = new BehaviorChangeInterventionPhase();

        // Mock behavior for interventionPhaseRepository.findByBehaviorChangeInterventionBlockId().
        when(interventionPhaseRepository.findByBehaviorChangeInterventionBlockId(interventionBlock.getId())).
                thenReturn(Collections.singletonList(interventionPhaseResult));

        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeinterventionphase/find/behaviorchangeinterventionblock/" +
                        interventionBlock.getId(), "$[0].exitConditions",
                interventionPhaseResult.getExitConditions());
    }

    @Test
    void findByBciModules() throws Exception {
        // Phase result object.
        BehaviorChangeInterventionPhase interventionPhaseResult = new BehaviorChangeInterventionPhase();

        // Mock behavior for findByBciModules().
        when(interventionPhaseRepository.findByBciModules(module)).thenReturn(Collections.singletonList(interventionPhaseResult));
        // Perform a GET request to test the controller.
        performGetRequestWithObject("/bahaviorchangeinterventionphase/find/bcimodules", module, "$[0].id", interventionPhaseResult.getId());
    }

    @Test
    void findByBciModulesId() throws Exception {
        // Phase result object.
        BehaviorChangeInterventionPhase interventionPhaseResult = new BehaviorChangeInterventionPhase();

        // Mock behavior for findByBciModulesId().
        when(interventionPhaseRepository.findByBciModulesId(module.getId())).thenReturn(Collections.singletonList(interventionPhaseResult));
        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeinterventionphase/find/bcimodules/" + module.getId(), "$[0].id", interventionPhaseResult.getId());
    }

    @Test
    void findByBCIModulesName() throws Exception {
        // Phase result object.
        BehaviorChangeInterventionPhase interventionPhaseResult = new BehaviorChangeInterventionPhase();

        // Mock behavior for findByBciModulesName().
        when(interventionPhaseRepository.findByBciModulesName(module.getName())).thenReturn(Collections.singletonList(interventionPhaseResult));
        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeinterventionphase/find/bcimodules/name/" + module.getName(), "$[0].id", interventionPhaseResult.getId());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for interventionPhaseRepository.findAll().
        when(interventionPhaseRepository.findAll()).
                thenReturn(Collections.singletonList(interventionPhase));

        // Perform a GET request to test the controller.
        performGetRequest("/bahaviorchangeinterventionphase","$[0].id",1);
    }
}