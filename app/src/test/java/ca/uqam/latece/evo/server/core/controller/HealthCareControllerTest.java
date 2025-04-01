package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.controller.instance.HealthCareProfessionalController;
import ca.uqam.latece.evo.server.core.model.Role;
import ca.uqam.latece.evo.server.core.model.instance.HealthCareProfessional;
import ca.uqam.latece.evo.server.core.repository.RoleRepository;
import ca.uqam.latece.evo.server.core.repository.instance.HealthCareProfessionalRepository;
import ca.uqam.latece.evo.server.core.service.instance.HealthCareProfessionalService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

/**
 * The HealthCareProfessional Controller test class for the {@link HealthCareProfessionalController}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations supported the controller class, using WebMvcTes, and
 * repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Julien Champagne.
 */
@WebMvcTest(controllers = HealthCareControllerTest.class)
@ContextConfiguration(classes = {HealthCareProfessionalController.class, HealthCareProfessionalService.class, HealthCareProfessional.class})
public class HealthCareControllerTest extends AbstractControllerTest {
    @MockBean
    HealthCareProfessionalRepository healthCareProfessionalRepository;

    @MockBean
    RoleRepository roleRepository;

    HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222",
            "Chief Painter", "CIUSSS", "Healthcare");

    @BeforeEach
    @Override
    void setUp() {
        Role role = new Role("e-Facilitator");
        role.setId(1L);
        hcp.setId(1L);
        hcp.setRole(role);
        when(healthCareProfessionalRepository.save(hcp)).thenReturn(hcp);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest("/HealthCareProfessional", hcp);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        HealthCareProfessional hcpToUpdate = new HealthCareProfessional("Dali", "Salvadord@gmail.com", "514-222-2222",
                "Chief Painter", "CIUSSS", "Painting");
        hcpToUpdate.setId(1L);

        when(healthCareProfessionalRepository.save(hcpToUpdate)).thenReturn(hcpToUpdate);

        when(healthCareProfessionalRepository.findById(hcpToUpdate.getId())).thenReturn(Optional.of(hcpToUpdate));
        performUpdateRequest("/HealthCareProfessional", hcpToUpdate,"$.name",hcpToUpdate.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest("/HealthCareProfessional/" + hcp.getId(), hcp);
    }

    @Test
    @Override
    void testFindById() throws Exception {
        //Mock behavior for healthCareProfessionalRepository.findById()
        when(healthCareProfessionalRepository.findById(hcp.getId())).thenReturn(Optional.of(hcp));

        //Perform a GET request to test the controller
        performGetRequest("/HealthCareProfessional/find/" + hcp.getId(), "$.name", hcp.getName());
    }

    @Test
    void testFindByname() throws Exception {
        //Mock behavior for healthCareProfessionalRepository.findByName().
        when(healthCareProfessionalRepository.findByName(hcp.getName())).thenReturn(Collections.singletonList(hcp));

        //Perform a GET request to test the controller.
        performGetRequest("/HealthCareProfessional/find/name/" + hcp.getName(),
                "$[0].name", hcp.getName());
    }

    @Test
    void testFindByEmail() throws Exception {
        //Mock behavior for healthCareProfessionalRepository.findByEmail().
        when(healthCareProfessionalRepository.findByEmail(hcp.getEmail())).thenReturn(Collections.singletonList(hcp));

        //Perform a GET request to test the controller.
        performGetRequest("/HealthCareProfessional/find/email/" + hcp.getEmail(),
                "$[0].email", hcp.getEmail());
    }

    @Test
    void testFindByContactInformation() throws Exception {
        //Mock behavior for healthCareProfessionalRepository.findByContactInformation().
        when(healthCareProfessionalRepository.findByContactInformation(hcp.getContactInformation())).thenReturn(Collections.singletonList(hcp));

        //Perform a GET request to test the controller.
        performGetRequest("/HealthCareProfessional/find/contactInformation/" + hcp.getContactInformation(),
                "$[0].contactInformation", hcp.getContactInformation());
    }

    @Test
    void findByRole() throws Exception {
        when(healthCareProfessionalRepository.findByRole(hcp.getRole().getId())).thenReturn(Collections.singletonList(hcp));

        //Perform a GET request to test the controller.
        performGetRequest("/HealthCareProfessional/find/role/" + hcp.getRole().getId(),
                "$[0].role.id", hcp.getRole().getId());
    }

    @Test
    void testFindByPosition() throws Exception {
        //Mock behavior for healthCareProfessionalRepository.findByPosition().
        when(healthCareProfessionalRepository.findByPosition(hcp.getPosition())).thenReturn(Collections.singletonList(hcp));

        //Perform a GET request to test the controller.
        performGetRequest("/HealthCareProfessional/find/position/" + hcp.getPosition(),
                "$[0].position", hcp.getPosition());
    }

    @Test
    void testFindByAffiliation() throws Exception {
        //Mock behavior for healthCareProfessionalRepository.findByAffiliation().
        when(healthCareProfessionalRepository.findByAffiliation(hcp.getAffiliation())).thenReturn(Collections.singletonList(hcp));

        //Perform a GET request to test the controller.
        performGetRequest("/HealthCareProfessional/find/affiliation/" + hcp.getAffiliation(),
                "$[0].affiliation", hcp.getAffiliation());
    }

    @Test
    void testFindBySpecialties() throws Exception {
        //Mock behavior for healthCareProfessionalRepository.findBySpecialties().
        when(healthCareProfessionalRepository.findBySpecialties(hcp.getSpecialties())).thenReturn(Collections.singletonList(hcp));

        //Perform a GET request to test the controller.
        performGetRequest("/HealthCareProfessional/find/specialties/" + hcp.getSpecialties(),
                "$[0].specialties", hcp.getSpecialties());
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        //Mock behavior for healthCareProfessionalRepository.findAll().
        when(healthCareProfessionalRepository.findAll()).thenReturn(Collections.singletonList(hcp));

        //Perform a GET request to test the controller.
        performGetRequest("/HealthCareProfessional","$[0].id",1);
    }
}
