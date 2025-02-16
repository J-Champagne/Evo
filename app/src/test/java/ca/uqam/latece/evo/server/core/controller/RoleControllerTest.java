package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.Role;
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
 * Role Controller Test class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = RoleController.class)
@ContextConfiguration(classes = {RoleController.class, RoleService.class, Role.class})
public class RoleControllerTest extends AbstractControllerTest {

    @MockBean
    private RoleRepository roleRepository;

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

    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/roles/" + role.getId(), role);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        // Create the Content.
        Role role = new Role();
        role.setId(2L);
        role.setName("e-Facilitator");

        // Save the content.
        when(roleRepository.save(role)).thenReturn(role);

        // Mock behavior for roleRepository.findAll().
        when(roleRepository.findById(2L)).thenReturn(Optional.of(role));

        // Perform a GET request to test the controller.
        performGetRequest("/roles/find/2","$.name","e-Facilitator");
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName("Admin 2");

        when(roleRepository.save(role2)).thenReturn(role2);

        // Mock behavior for roleRepository.findAll().
        when(roleRepository.findAll()).thenReturn(Collections.singletonList(role2));

        // Perform a GET request to test the controller.
        performGetRequest("/roles","$[0].id",2);
    }

}
