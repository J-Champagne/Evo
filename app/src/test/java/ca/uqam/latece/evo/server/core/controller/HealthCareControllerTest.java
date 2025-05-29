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
 * Tests methods found in HealthCareProfessionalController using WebMvcTest, and repository queries using MockMvc (Mockito).
 * @author Julien Champagne.
 */
@WebMvcTest(controllers = HealthCareControllerTest.class)
@ContextConfiguration(classes = {HealthCareProfessionalController.class, HealthCareProfessionalService.class, HealthCareProfessional.class})
public class HealthCareControllerTest extends AbstractControllerTest {
    @MockBean
    HealthCareProfessionalRepository healthCareProfessionalRepository;

    @MockBean
    RoleRepository roleRepository;

    private Role role = new Role("Administrator");

    HealthCareProfessional hcp = new HealthCareProfessional("Bob", "Bobross@gmail.com", "514-222-2222", role,
            "Chief Painter", "CIUSSS", "Healthcare");

    private final String url = "/healthcareprofessional";

    @BeforeEach
    @Override
    void setUp() {
        role.setId(1L);
        hcp.setId(1L);
        when(roleRepository.save(role)).thenReturn(role);
        when(healthCareProfessionalRepository.save(hcp)).thenReturn(hcp);
    }

    @Test
    @Override
    void testCreate() throws Exception {
        performCreateRequest(url, hcp);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        HealthCareProfessional hcpUpdated = new HealthCareProfessional(hcp.getName(), hcp.getEmail(), hcp.getContactInformation(),
                hcp.getRole(), hcp.getPosition(), hcp.getAffiliation(), "Everything");
        hcpUpdated.setId(hcp.getId());
        when(healthCareProfessionalRepository.save(hcpUpdated)).thenReturn(hcpUpdated);
        when(healthCareProfessionalRepository.findById(hcpUpdated.getId())).thenReturn(Optional.of(hcpUpdated));

        performUpdateRequest(url, hcpUpdated,"$.specialties", hcpUpdated.getSpecialties());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        performDeleteRequest(url + "/" + hcp.getId(), hcp);
    }

    @Test
    @Override
    void testFindAll() throws Exception {
        when(healthCareProfessionalRepository.findAll()).thenReturn(Collections.singletonList(hcp));

        performGetRequest(url,"$[0].id", hcp.getId());
    }

    @Test
    @Override
    void testFindById() throws Exception {
        when(healthCareProfessionalRepository.findById(hcp.getId())).thenReturn(Optional.of(hcp));

        performGetRequest(url + "/find/" + hcp.getId(), "$.id", hcp.getId());
    }

    @Test
    void testFindByName() throws Exception {
        when(healthCareProfessionalRepository.findByName(hcp.getName())).thenReturn(Collections.singletonList(hcp));

        performGetRequest(url + "/find/name/" + hcp.getName(),
                "$[0].name", hcp.getName());
    }

    @Test
    void testFindByEmail() throws Exception {
        when(healthCareProfessionalRepository.findByEmail(hcp.getEmail())).thenReturn((hcp));

        performGetRequest(url + "/find/email/" + hcp.getEmail(),
                "$.email", hcp.getEmail());
    }

    @Test
    void testFindByContactInformation() throws Exception {
        when(healthCareProfessionalRepository.findByContactInformation(hcp.getContactInformation())).thenReturn(Collections.singletonList(hcp));

        performGetRequest(url + "/find/contactinformation/" + hcp.getContactInformation(),
                "$[0].contactInformation", hcp.getContactInformation());
    }

    @Test
    void findByRole() throws Exception {
        when(healthCareProfessionalRepository.findByRole(hcp.getRole())).thenReturn(Collections.singletonList(hcp));

        performGetRequest(url + "/find/role", hcp.getRole(),"$[0].role.id", hcp.getRole().getId());
    }

    @Test
    void findByRoleId() throws Exception {
        when(healthCareProfessionalRepository.findByRoleId(hcp.getRole().getId())).thenReturn(Collections.singletonList(hcp));

        performGetRequest(url + "/find/role/" + hcp.getRole().getId(),
                "$[0].role.id", hcp.getRole().getId());
    }

    @Test
    void testFindByPosition() throws Exception {
        when(healthCareProfessionalRepository.findByPosition(hcp.getPosition())).thenReturn(Collections.singletonList(hcp));

        performGetRequest(url + "/find/position/" + hcp.getPosition(),
                "$[0].position", hcp.getPosition());
    }

    @Test
    void testFindByAffiliation() throws Exception {
        when(healthCareProfessionalRepository.findByAffiliation(hcp.getAffiliation())).thenReturn(Collections.singletonList(hcp));

        performGetRequest(url + "/find/affiliation/" + hcp.getAffiliation(),
                "$[0].affiliation", hcp.getAffiliation());
    }

    @Test
    void testFindBySpecialties() throws Exception {
        when(healthCareProfessionalRepository.findBySpecialties(hcp.getSpecialties())).thenReturn(Collections.singletonList(hcp));

        performGetRequest(url + "/find/specialties/" + hcp.getSpecialties(),
                "$[0].specialties", hcp.getSpecialties());
    }
}
