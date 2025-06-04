package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The test class for the {@link BCIModuleService}, responsible for testing its various functionalities.
 * This class includes integration tests for CRUD operations and other repository queries using a
 * PostgreSQL database in a containerized setup.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {BCIModuleService.class, BCIModule.class})
public class BCIModuleServiceTest extends AbstractServiceTest {

    @Autowired
    private BCIModuleService bciModuleService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private BehaviorChangeInterventionPhaseService behaviorChangeInterventionPhaseService;

    private BCIModule bciModule = new BCIModule();
    private Skill skill = new Skill();
    private BehaviorChangeInterventionPhase behaviorChangePhase = new BehaviorChangeInterventionPhase();

    @BeforeEach
    void beforeEach(){
        // Creates a Skill.
        skill.setName("Skill 1");
        skill.setDescription("Skill Description");
        skill.setType(SkillType.BCT);
        skillService.create(skill);

        // Creates a Behavior Change Phase.
        behaviorChangePhase.setEntryConditions("Entry Conditions");
        behaviorChangePhase.setExitConditions("Exit Conditions");
        behaviorChangeInterventionPhaseService.create(behaviorChangePhase);

        //  Creates a BCIModule.
        bciModule.setName("Test Module");
        bciModule.setDescription("Test Module Description");
        bciModule.setPreconditions("Test Preconditions");
        bciModule.setPostconditions("Test Post conditions");
        bciModule.setBehaviorChangeInterventionPhases(behaviorChangePhase);
        bciModule.setSkills(skill);
        bciModuleService.create(bciModule);
    }

    @AfterEach
    void afterEach(){
        bciModuleService.deleteById(bciModule.getId());
    }

    @Test
    @Override
    void testSave() {
        // Checks if the BCIModule was saved.
        assert bciModule.getId() > 0;
    }

    @Test
    @Override
    void testUpdate() {
        BCIModule module = new BCIModule();
        module.setId(bciModule.getId());
        module.setName("Test Module 2");
        module.setDescription("Test Module Description 12");
        module.setPreconditions("Test Module Preconditions 12");
        module.setPostconditions("Post conditions 12");
        module.setSkills(skill);

        // Updated the BCIModule.
        BCIModule updated = bciModuleService.update(module);

        // Checks if the BCI Module id saved is the same of the BCI Module updated.
        assertEquals(module.getId(), updated.getId());
        assertNotEquals("Test Module", updated.getName());
        assertNotEquals("Test Module Description", updated.getDescription());
        assertNotEquals("Test Preconditions", updated.getPreconditions());
        assertNotEquals("Test Post conditions", updated.getPostconditions());
    }

    @Test
    @Override
    void testFindById() {
        BCIModule module = new BCIModule();
        module.setName("Module 1");
        module.setDescription("Description Module 1");
        module.setPreconditions("Preconditions Module 1");
        module.setPostconditions("Post conditions Module 1");
        module.setSkills(skill);

        // Creates the BCIModule.
        BCIModule saved = bciModuleService.create(module);

        // Look for BCIModule by ID.
        BCIModule found = bciModuleService.findById(saved.getId());
        BCIModule found2 = bciModuleService.findById(bciModule.getId());

        // Tests.
        assertEquals(module.getId(), found.getId());
        assertEquals(module.getName(), found.getName());
        assertEquals(module.getDescription(), found.getDescription());
        assertEquals(module.getPreconditions(), found.getPreconditions());
        assertEquals(module.getPostconditions(), found.getPostconditions());
        assertEquals(bciModule.getId(), found2.getId());
        assertEquals(bciModule.getName(), found2.getName());
        assertEquals(bciModule.getDescription(), found2.getDescription());
        assertEquals(bciModule.getPreconditions(), found2.getPreconditions());
        assertEquals(bciModule.getPostconditions(), found2.getPostconditions());
    }

    @Test
    void testFindByName() {
        // Checks if the name is equals.
        assertEquals(bciModule.getName(), bciModuleService.findByName(bciModule.getName()).get(0).getName());
    }

    @Test
    void existsByName() {
        assertEquals(bciModule.getName(), bciModuleService.findByName(bciModule.getName()).get(0).getName());
    }

    @Test
    @Override
    void testDeleteById() {
        BCIModule module = new BCIModule();
        module.setName("Module 2");
        module.setDescription("Description Module 2");
        module.setPreconditions("Preconditions Module 2");
        module.setPostconditions("Post conditions Module 2");
        module.setSkills(skill);

        // Creates the BCIModule.
        BCIModule saved = bciModuleService.create(module);
        // Delete the BCIModule.
        bciModuleService.deleteById(saved.getId());
        // Checks if the BCIModule was deleted.
        assertFalse(bciModuleService.existsById(saved.getId()));
    }

    @Test
    void findBySkills() {
        BCIModule module = new BCIModule();
        module.setName("Module 3");
        module.setDescription("Description Module 3");
        module.setPreconditions("Preconditions Module 3");
        module.setPostconditions("Post conditions Module 3");
        module.setSkills(skill);

        // Creates the BCIModule.
        BCIModule saved = bciModuleService.create(module);
        List<BCIModule> modules = bciModuleService.findBySkills(skill);

        assertEquals(2, modules.size());
    }

    @Test
    void findBySkillsIdAndNameNull() {
        // Test the IllegalArgumentException using a Skill with Id and Name null.
        assertThrows(IllegalArgumentException.class, () -> bciModuleService.findBySkills(new Skill()));
    }

    @Test
    void findBySkillsId() {
        Skill newSkill = new Skill();
        newSkill.setName("New Skill 1");
        newSkill.setDescription("New Skill Description 1");
        newSkill.setType(SkillType.BCT);
        skillService.create(newSkill);

        // This query will return only one BCIModule.
        List<BCIModule> modules = bciModuleService.findBySkillId(skill.getId());

        // This query not return BCIModule.
        List<BCIModule> noModule = bciModuleService.findBySkillId(newSkill.getId());

        // Test the first query.
        assertEquals(1, modules.size());
        assertEquals(modules.get(0).getName(), bciModule.getName());
        assertEquals(modules.get(0).getDescription(), bciModule.getDescription());
        assertEquals(modules.get(0).getPreconditions(), bciModule.getPreconditions());
        assertEquals(modules.get(0).getPostconditions(), bciModule.getPostconditions());

        // Test the second query.
        assertEquals(0, noModule.size());
    }

    @Test
    void findByBehaviorChangeInterventionPhasesId() {
        // Creates a Skill.
        Skill newSkill = new Skill();
        newSkill.setName("PHYSICAL Skill");
        newSkill.setDescription("PHYSICAL Skill Description");
        newSkill.setType(SkillType.PHYSICAL);
        skillService.create(newSkill);

        // Creates a Behavior Change Phase.
        BehaviorChangeInterventionPhase newBehaviorChangePhase = new BehaviorChangeInterventionPhase();
        newBehaviorChangePhase.setEntryConditions("Entry Conditions");
        newBehaviorChangePhase.setExitConditions("Exit Conditions");
        behaviorChangeInterventionPhaseService.create(newBehaviorChangePhase);

        //  Creates a BCIModule.
        BCIModule newModule = new BCIModule();
        newModule.setName("New Module");
        newModule.setDescription("New Module Description");
        newModule.setPreconditions("New Preconditions");
        newModule.setPostconditions("New Post conditions");
        newModule.setBehaviorChangeInterventionPhases(newBehaviorChangePhase);
        newModule.setSkills(newSkill);
        bciModuleService.create(newModule);

        // This query will return only one BCIModule.
        List<BCIModule> modules = bciModuleService.findByBehaviorChangeInterventionPhasesId(newBehaviorChangePhase.getId());
        assertEquals(1, modules.size());
        assertTrue(modules.get(0).getBehaviorChangeInterventionPhases().contains(newBehaviorChangePhase));
        assertEquals(modules.get(0).getPreconditions(), newModule.getPreconditions());
        assertEquals(modules.get(0).getPostconditions(), newModule.getPostconditions());
    }

    @Test
    void findByBehaviorChangeInterventionPhasesFieldsNull() {
        // Test the IllegalArgumentException using a BehaviorChangeInterventionPhase with EntryConditions and ExitConditions null.
        assertThrows(IllegalArgumentException.class, () -> bciModuleService.findByBehaviorChangeInterventionPhases(new BehaviorChangeInterventionPhase()));
    }

    @Test
    void findByBehaviorChangeInterventionPhases() {
        BehaviorChangeInterventionPhase phase = new BehaviorChangeInterventionPhase();
        phase.setEntryConditions("Phase - Entry Conditions - Phase");
        phase.setExitConditions("Phase - Exit Conditions - Phase");
        behaviorChangeInterventionPhaseService.create(phase);

        // This query will return only one BCIModule.
        List<BCIModule> modules = bciModuleService.findByBehaviorChangeInterventionPhases(behaviorChangePhase);
        assertEquals(1, modules.size());
        assertTrue(modules.get(0).getBehaviorChangeInterventionPhases().contains(behaviorChangePhase));
        assertEquals(modules.get(0).getPreconditions(), bciModule.getPreconditions());
        assertEquals(modules.get(0).getPostconditions(), bciModule.getPostconditions());

        // This query will return only one BCIModule.
        List<BCIModule> noModules = bciModuleService.findByBehaviorChangeInterventionPhases(phase);
        assertEquals(0, noModules.size());
    }

    @Test
    @Override
    void testFindAll() {
        BCIModule module = new BCIModule();
        module.setName("Module 5");
        module.setDescription("Description Module 5");
        module.setPreconditions("Preconditions Module 5");
        module.setPostconditions("Post conditions Module 5");
        module.setSkills(skill);

        // Creates the BCIModule.
        BCIModule saved = bciModuleService.create(module);

        // Find all BCIModule.
        List<BCIModule> modules = bciModuleService.findAll();
        assertEquals(2, modules.size());
        assertEquals(bciModule.getName(), modules.get(0).getName());
        assertEquals(bciModule.getDescription(), modules.get(0).getDescription());
        assertEquals(bciModule.getPreconditions(), modules.get(0).getPreconditions());
        assertEquals(bciModule.getPostconditions(), modules.get(0).getPostconditions());
        assertEquals(module.getName(), modules.get(1).getName());
        assertEquals(module.getDescription(), modules.get(1).getDescription());
        assertEquals(module.getPreconditions(), modules.get(1).getPreconditions());
        assertEquals(module.getPostconditions(), modules.get(1).getPostconditions());
    }
}