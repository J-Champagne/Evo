package ca.uqam.latece.evo.server.core.controller;


import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.repository.SkillRepository;
import ca.uqam.latece.evo.server.core.service.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Skill Controller Test class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@WebMvcTest(controllers = SkillController.class)
@ContextConfiguration(classes = {Skill.class, SkillController.class, SkillService.class})
public class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        mockMvc.perform(post("/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(skill.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void testFindAll() throws Exception {
        // Mock behavior for skillRepository.findAll().
        when(skillRepository.findAll()).thenReturn(Collections.singletonList(skill));

        // Perform a GET request to test the controller.
        mockMvc.perform(get("/skills"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType. APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    public void testFindById() throws Exception {

        skill.setId(2L);
        skill.setName("Skill 2");
        skill.setDescription("Skill Description 2");
        skill.setType(SkillType.BCT);
        when(skillRepository.save(skill)).thenReturn(skill);

        // Mock behavior for skillRepository.findAll().
        when(skillRepository.findById(2L)).thenReturn(Optional.of(skill));

        // Perform a GET request to test the controller.
        mockMvc. perform(get("/skills/find/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType. APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Skill 2"));
    }
}
