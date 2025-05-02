package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.repository.SkillRepository;
import ca.uqam.latece.evo.server.core.service.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;


/**
 * The Skill Controller test class for the {@link SkillController}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations supported the controller class, using WebMvcTes, and
 * repository queries using MockMvc (Mockito).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = SkillController.class)
@ContextConfiguration(classes = {Skill.class, SkillController.class, SkillService.class})
public class SkillControllerTest extends AbstractControllerTest  {

    @MockBean
    private SkillRepository skillRepository;

    private Skill skill = new Skill();


    @BeforeEach
    void setUp() {
        skill.setId(1L);
        skill.setName("Skill 1");
        skill.setDescription("Skill Description");
        skill.setType(SkillType.PHYSICAL);

        when(skillRepository.save(skill)).thenReturn(skill);
    }

    @Test
    public void testCreate() throws Exception {
        performCreateRequest("/skills", skill);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update Requires
        skill.setName("Skill 2");
        // Save in the database.
        when(skillRepository.save(skill)).thenReturn(skill);
        // Perform a PUT request to test the controller.
        performUpdateRequest("/skills", skill, "$.name", skill.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        // Perform a DELETE request to test the controller.
        performDeleteRequest("/skills/" + skill.getId(), skill);
    }

    @Test
    public void testFindAll() throws Exception {
        // Mock behavior for skillRepository.findAll().
        when(skillRepository.findAll()).thenReturn(Collections.singletonList(skill));

        // Perform a GET request to test the controller.
        performGetRequest("/skills", "$[0].id", skill.getId());
    }

    @Test
    public void testFindByName() throws Exception {
        skill.setId(4L);
        skill.setName("3Skill");
        skill.setDescription("Skill Description 3");
        skill.setType(SkillType.BCT);
        when(skillRepository.save(skill)).thenReturn(skill);

        // Mock behavior for skillRepository.findByName().
        when(skillRepository.findByName(skill.getName())).thenReturn(Collections.singletonList(skill));
        // Perform a GET request to test the controller.
        performGetRequest("/skills/find/name/" + skill.getName(), "$[0].name", skill.getName());
    }

    @Test
    void findByRequiredSkill () throws Exception {
        skill.setId(5L);
        skill.setName("5Skill");
        skill.setDescription("Skill Description 5");
        skill.setType(SkillType.BCT);
        when(skillRepository.save(skill)).thenReturn(skill);

        Skill skill2 = new Skill();
        skill2.setId(6L);
        skill2.setName("6Skill");
        skill2.setDescription("Skill Description 6");
        skill2.setType(SkillType.BCT);
        skill2.addRequiredSkill(skill);
        when(skillRepository.save(skill2)).thenReturn(skill2);

        Skill requiredSkillResult = new Skill();

        // Mock behavior for skillRepository.findByRequiredSkill().
        when(skillRepository.findByRequiredSkill(skill.getId())).thenReturn(Collections.singletonList(requiredSkillResult));
        // Perform a GET request to test the controller.
        performGetRequest("/skills/find/requiredskill/" + skill.getId(), "$[0].name", requiredSkillResult.getName());
        performGetRequest("/skills/find/requiredskill/" + skill.getId(), "$[0].id", requiredSkillResult.getId());
    }

    @Test
    void findBySubSkill() throws Exception {
        skill.setId(89L);
        skill.setName("5Skill");
        skill.setDescription("Skill Description 5");
        skill.setType(SkillType.BCT);
        when(skillRepository.save(skill)).thenReturn(skill);

        Skill skill2 = new Skill();
        skill2.setId(86L);
        skill2.setName("6Skill");
        skill2.setDescription("Skill Description 6");
        skill2.setType(SkillType.BCT);
        skill2.setSubSkill(skill);
        when(skillRepository.save(skill2)).thenReturn(skill2);

        Skill subSkillResult = new Skill();

        // Mock behavior for skillRepository.findBySubSkill().
        when(skillRepository.findBySubSkill(skill.getId())).thenReturn(Collections.singletonList(subSkillResult));
        // Perform a GET request to test the controller.
        performGetRequest("/skills/find/subskill/" + skill.getId(), "$[0].name", subSkillResult.getName());
        performGetRequest("/skills/find/subskill/" + skill.getId(), "$[0].id", subSkillResult.getId());

    }

    @Test
    public void testFindByType() throws Exception {
        skill.setId(4L);
        skill.setName("3Skill");
        skill.setDescription("Skill Description 3");
        skill.setType(SkillType.PHYSICAL);
        when(skillRepository.save(skill)).thenReturn(skill);

        // Mock behavior for skillRepository.findByType().
        when(skillRepository.findByType(skill.getType())).thenReturn(Collections.singletonList(skill));
        // Perform a GET request to test the controller.
        performGetRequest("/skills/find/type/" + skill.getType(), "$[0].name", skill.getName());
    }

    @Test
    public void testFindById() throws Exception {
        skill.setId(2L);
        skill.setName("Skill 2");
        skill.setDescription("Skill Description 2");
        skill.setType(SkillType.BCT);
        when(skillRepository.save(skill)).thenReturn(skill);

        // Mock behavior for skillRepository.findAll().
        when(skillRepository.findById(skill.getId())).thenReturn(Optional.of(skill));
        // Perform a GET request to test the controller.
        performGetRequest("/skills/find/" + skill.getId(), "$.name", skill.getName());
    }
}
