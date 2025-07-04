package ca.uqam.latece.evo.server.core.controller;


import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.*;
import ca.uqam.latece.evo.server.core.repository.*;
import ca.uqam.latece.evo.server.core.service.ModuleComposedActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The ModuleComposedActivityController test class for the {@link ModuleComposedActivityController}, responsible for testing its various
 * functionalities. This class includes integration tests for CRUD operations supported the controller class,
 * using WebMvcTes, and repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = ModuleComposedActivityController.class)
@ContextConfiguration(classes = {ModuleComposedActivityController.class, ModuleComposedActivityService.class, ModuleComposedActivity.class})
public class ModuleComposedActivityControllerTest extends AbstractControllerTest {
    @MockBean
    private ModuleComposedActivityRepository moduleComposedActivityRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private BCIModuleRepository bciModuleRepository;
    @MockBean
    private SkillRepository skillRepository;
    @MockBean
    private BCIActivityRepository bciActivityRepository;


    private ModuleComposedActivity moduleComposedActivity = new ModuleComposedActivity();
    private BCIModule bciModule = new BCIModule();
    private Skill skill = new Skill();
    private Role role = new Role();
    private BCIActivity bciActivity = new BCIActivity();

    private static final String URL = "/modulecomposedactivity";
    private static final String URL_SPLITER = "/modulecomposedactivity/";
    private static final String URL_FIND = "/modulecomposedactivity/find/";


    @BeforeEach
    @Override
    void setUp() {
        // Create a Role.
        role.setId(55L);
        role.setName("Admin - BCI Activity - Module");
        // Save in the database.
        when(roleRepository.save(role)).thenReturn(role);

        // Creates a BCIActivity.
        bciActivity.setId(35L);
        bciActivity.setType(ActivityType.GOAL_SETTING);
        bciActivity.setName("BCI Activity - Module");
        bciActivity.setDescription("BCI Activity Description - Module");
        bciActivity.addParty(role);
        // Save in the database.
        when(bciActivityRepository.save(bciActivity)).thenReturn(bciActivity);

        // Creates a Skill.
        skill.setId(91L);
        skill.setName("Skill 1");
        skill.setDescription("Skill Description");
        skill.setType(SkillType.BCT);
        // Save in the database.
        when(skillRepository.save(skill)).thenReturn(skill);

        // Creates a BCIModule.
        bciModule.setId(79L);
        bciModule.setName("Module");
        bciModule.setDescription("Module Description");
        bciModule.setPreconditions("Preconditions");
        bciModule.setPostconditions("Post conditions");
        bciModule.setSkills(skill);
        // Save in the database.
        when(bciModuleRepository.save(bciModule)).thenReturn(bciModule);


        // Creates a ModuleComposedActivity.
        moduleComposedActivity.setId(4L);
        moduleComposedActivity.setComposedActivityBciModule(bciModule);
        moduleComposedActivity.setComposedModuleBciActivity(bciActivity);
        moduleComposedActivity.setOrder(10);

        // Save in the database.
        when(moduleComposedActivityRepository.save(moduleComposedActivity)).thenReturn(moduleComposedActivity);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        System.out.println("Module Composed Activity: "+ moduleComposedActivity);
        // Perform a POST request to test the controller.
        performCreateRequest(URL, moduleComposedActivity);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        // Creates a ModuleComposedActivity invalid.
        ModuleComposedActivity badRequest = new ModuleComposedActivity();
        badRequest.setId(2L);
        // Perform a POST with a Bad Request to test the controller.
        performCreateRequestBadRequest(URL, badRequest);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update a ModuleComposedActivity.
        moduleComposedActivity.setOrder(1511);
        // Save in the database.
        when(moduleComposedActivityRepository.save(moduleComposedActivity)).thenReturn(moduleComposedActivity);
        // Perform a PUT request to test the controller.
        performUpdateRequest(URL, moduleComposedActivity,"$.order", moduleComposedActivity.getOrder());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        // perform a DELETE request to test the controller.
        performDeleteRequest(URL_SPLITER + moduleComposedActivity.getId(), moduleComposedActivity);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Mock behavior for moduleComposedActivityRepository.save
        when(moduleComposedActivityRepository.save(moduleComposedActivity)).thenReturn(moduleComposedActivity);
        // Mock behavior for moduleComposedActivityRepository.findById().
        when(moduleComposedActivityRepository.findById(moduleComposedActivity.getId())).thenReturn(Optional.of(moduleComposedActivity));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + moduleComposedActivity.getId(), "$.id", moduleComposedActivity.getId());
    }

    @Test
    void testFindByComposedActivityBciModuleId() throws Exception {
        // Save in the database.
        when(bciModuleRepository.save(bciModule)).thenReturn(bciModule);
        // Mock behavior for testFindByComposedActivityBciModuleId().
        when(moduleComposedActivityRepository.findByComposedActivityBciModuleId(bciModule.getId())).thenReturn(Collections.singletonList(moduleComposedActivity));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "bcimodule/" + bciModule.getId(), "$[0].order", moduleComposedActivity.getOrder());
    }

    @Test
    void testFindByComposedActivityBciModule() throws Exception {
        // Mock behavior for testFindByComposedActivityBciModule().
        when(moduleComposedActivityRepository.findByComposedActivityBciModule(bciModule)).thenReturn(Collections.singletonList(moduleComposedActivity));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "bcimodule", bciModule, "$[0].id", moduleComposedActivity.getId());
    }

    @Test
    void findByComposedModuleBciActivityId() throws Exception {
        // Mock behavior for findByComposedModuleBciActivity().
        when(moduleComposedActivityRepository.findByComposedModuleBciActivityId(bciActivity.getId())).thenReturn(Collections.singletonList(moduleComposedActivity));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "bciactivity/" + bciActivity.getId(), "$[0].id", moduleComposedActivity.getId());
    }

    @Test
    void findByComposedModuleBciActivity() throws Exception {
        // Mock behavior for findByComposedModuleBciActivity().
        when(moduleComposedActivityRepository.findByComposedModuleBciActivity(bciActivity)).thenReturn(Collections.singletonList(moduleComposedActivity));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "bciactivity", bciActivity, "$[0].id", moduleComposedActivity.getId());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Mock behavior for moduleComposedActivityRepository.findAll().
        when(moduleComposedActivityRepository.findAll()).thenReturn(Collections.singletonList(moduleComposedActivity));
        // Perform a GET request to test the controller.
        performGetRequest(URL, "$[0].id", moduleComposedActivity.getId());
    }
}
