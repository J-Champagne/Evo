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

    private static final String URL = "/skills";
    private static final String URL_SPLITTER = "/skills/";
    private static final String URL_FIND = "/skills/find/";


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
        performCreateRequest(URL, skill);
    }

    @Test
    void testCreateBadRequest() throws Exception {
        // Creates a Skill invalid.
        Skill skillBadRequest = new Skill();
        skillBadRequest.setId(999L);
        // Perform a POST with a Bad Request to test the controller.
        performCreateRequestBadRequest(URL, skillBadRequest);
    }

    @Test
    @Override
    void testUpdate() throws Exception {
        // Update Requires
        skill.setName("Skill 2");
        // Save in the database.
        when(skillRepository.save(skill)).thenReturn(skill);
        // Mock behavior for findById().
        when(skillRepository.findById(skill.getId())).thenReturn(Optional.of(skill));
        // Perform a PUT request to test the controller.
        performUpdateRequest(URL, skill, "$.name", skill.getName());
    }

    @Test
    @Override
    void testDeleteById() throws Exception {
        // Perform a DELETE request to test the controller.
        performDeleteRequest(URL_SPLITTER + skill.getId(), skill);
    }

    @Test
    public void testFindAll() throws Exception {
        // Mock behavior for skillRepository.findAll().
        when(skillRepository.findAll()).thenReturn(Collections.singletonList(skill));

        // Perform a GET request to test the controller.
        performGetRequest(URL, "$[0].id", skill.getId());
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
        performGetRequest(URL_FIND + "name/" + skill.getName(), "$[0].name", skill.getName());
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
        when(skillRepository.findByRequiredSkill_Id(skill.getId())).thenReturn(Collections.singletonList(requiredSkillResult));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "requiredskill/" + skill.getId(), "$[0].name", requiredSkillResult.getName());
        performGetRequest(URL_FIND + "requiredskill/" + skill.getId(), "$[0].id", requiredSkillResult.getId());
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

        // Mock behavior for skillRepository.findBySubSkill_Id().
        when(skillRepository.findBySubSkill_Id(skill.getId())).thenReturn(Collections.singletonList(subSkillResult));
        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "subskill/" + skill.getId(), "$[0].name", subSkillResult.getName());
        performGetRequest(URL_FIND + "subskill/" + skill.getId(), "$[0].id", subSkillResult.getId());
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
        performGetRequest(URL_FIND + "type/" + skill.getType(), "$[0].name", skill.getName());
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
        performGetRequest(URL_FIND + skill.getId(), "$.name", skill.getName());
    }

    @Test
    public void testFindBySkillComposedOfSkillId() throws Exception {
        Skill skill1 = new Skill();
        skill1.setId(1L);
        skill1.setName("New Skill 1");
        skill1.setDescription("New Skill 1 - Description");
        skill1.setType(SkillType.BCT);
        when(skillRepository.save(skill1)).thenReturn(skill1);

        Skill composedSkill = new Skill();
        composedSkill.setId(2L);
        composedSkill.setName("New Skill Composed Skill 1");
        composedSkill.setDescription("New Skill Composed Skill 1 - Description");
        composedSkill.setType(SkillType.MENTAL);
        when(skillRepository.save(composedSkill)).thenReturn(composedSkill);

        Skill skill2 = new Skill();
        skill2.setId(3L);
        skill2.setName("New Skill 2");
        skill2.setDescription("New Skill 2 - Description");
        skill2.setType(SkillType.PHYSICAL);
        skill2.setSubSkill(skill1);
        skill2.setSkillComposedOfSkill(composedSkill);
        skill2.setComposedSkills(composedSkill);
        when(skillRepository.save(skill2)).thenReturn(skill2);

        Skill skillResult = new Skill();

        // Mock behavior for findBySkillComposedOfSkillId().
        when(skillRepository.findBySkillComposedOfSkillId(composedSkill.getId())).thenReturn(Collections.singletonList(skillResult));

        // Perform a GET request to test the controller.
        performGetRequest(URL_FIND + "composedofskill/" + composedSkill.getId(), "$[0].name", skillResult.getName());
        performGetRequest(URL_FIND + "composedofskill/" + composedSkill.getId(), "$[0].id", skillResult.getId());
    }
}
