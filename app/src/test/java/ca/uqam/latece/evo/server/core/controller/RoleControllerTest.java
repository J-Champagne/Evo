package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.repository.BCIActivityRepository;
import ca.uqam.latece.evo.server.core.repository.RoleRepository;
import ca.uqam.latece.evo.server.core.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The Role Controller test class for the {@link RoleController}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations supported the controller class, using WebMvcTes, and
 * repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = RoleController.class)
@ContextConfiguration(classes = {RoleController.class, RoleService.class, Role.class})
public class RoleControllerTest extends AbstractControllerTest {

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private BCIActivityRepository bciActivityRepository;

    private Role role = new Role();

    @BeforeEach
    void setUp() {
        role.setId(1L);
        role.setName("Admin");

        when(roleRepository.save(role)).thenReturn(role);
    }

    @Test
    public void testCreate() throws Exception {
        performCreateRequest("/roles", role);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update Requires
        role.setName("Participant");
        // Save in the database.
        when(roleRepository.save(role)).thenReturn(role);
        // Perform a PUT request to test the controller.
        performUpdateRequest("/roles", role, "$.name", role.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/roles/" + role.getId(), role);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create the Role.
        Role role = new Role();
        role.setId(2L);
        role.setName("e-Facilitator");

        // Save the role.
        when(roleRepository.save(role)).thenReturn(role);

        // Mock behavior for roleRepository.findById().
        when(roleRepository.findById(2L)).thenReturn(Optional.of(role));

        // Perform a GET request to test the controller.
        performGetRequest("/roles/find/" + role.getId(),"$.name","e-Facilitator");
    }

    @Test
    void testFindByName() throws Exception {
        // Create the Role.
        Role role = new Role();
        role.setId(3L);
        role.setName("e-Facilitator2");

        // Save the role.
        when(roleRepository.save(role)).thenReturn(role);

        // Mock behavior for roleRepository.findByName().
        when(roleRepository.findByName(role.getName())).thenReturn(Collections.singletonList(role));

        // Perform a GET request to test the controller.
        performGetRequest("/roles/find/name/" + role.getName(),"$[0].name", role.getName());
    }

    @Test
    void testFindByBCIActivity() throws Exception {
        // Create the Role.
        Role role = new Role();
        role.setId(2L);
        role.setName("e-Facilitator");

        // Save the role.
        when(roleRepository.save(role)).thenReturn(role);

        // Create the BCIActivity.
        BCIActivity bciActivity = new BCIActivity();
        bciActivity.setId(1L);
        bciActivity.setDescription("Programming language training 2");
        bciActivity.setType(ActivityType.LEARNING);
        bciActivity.addRole(role);

        // Save the BCIActivity.
        when(bciActivityRepository.save(bciActivity)).thenReturn(bciActivity);

        // Mock behavior for roleRepository.findAll().
        when(roleRepository.findByBCIActivity(bciActivity.getId())).thenReturn(Collections.singletonList(role));

        // Perform a GET request to test the controller.
        performGetRequest("/roles/find/bciactivity/" + bciActivity.getId(),"$[0].name",
                "e-Facilitator");
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        // Create the Role.
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("Admin 2");

        // Save the role.
        when(roleRepository.save(role2)).thenReturn(role2);

        // Mock behavior for roleRepository.findAll().
        when(roleRepository.findAll()).thenReturn(Collections.singletonList(role2));

        // Perform a GET request to test the controller.
        performGetRequest("/roles","$[0].id", role2.getId());
    }
}
