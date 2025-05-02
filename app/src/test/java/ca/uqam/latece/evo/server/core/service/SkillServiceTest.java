package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Skill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link SkillService} class to validate CRUD functionality and domain-specific queries.
 * This test class extends AbstractServiceTest, inheriting base setup and infrastructure for
 * testing database interactions through Spring's testing framework.
 * <p>
 * The class includes tests for the following functionalities:
 * <p>
 * - Saving a Skill entity to the database and verifying persistence.
 * - Updating an existing Skill entity and validating field value changes.
 * - Finding Skill entities by their name and ensuring correctness in results.
 * - Deleting Skill entities by their identifiers and ensuring proper deletion.
 * - Retrieving all Skill entities from the database and validating completeness.
 * - Finding Skill entities by their type and verifying query results.
 * <p>
 *  @version 1.0
 *  @author Edilton Lima dos Santos.
 */
@ContextConfiguration(classes = {SkillService.class, Skill.class})
public class SkillServiceTest extends AbstractServiceTest {
    @Autowired
    private SkillService skillService;

    @Test
    @Override
    public void testSave() {
        Skill skill = new Skill();
        skill.setName("Java");
        skill.setDescription("Programming language");
        skill.setType(SkillType.BCT);
        skillService.create(skill);

        assert skill.getId() > 0;
    }

    @Test
    @Override
    public void testUpdate() {
        Skill skill0 = new Skill();
        skill0.setName("Java 0");
        skill0.setDescription("Programming language 0");
        skill0.setType(SkillType.BCT);
        skillService.create(skill0);

        Skill skill = new Skill();
        skill.setName("Java");
        skill.setDescription("Programming language");
        skill.setType(SkillType.BCT);
        skill.addRequiredSkill(skill0);
        skillService.create(skill);

        // Update the Skill name.
        Skill skillToUpdate = new Skill();
        skillToUpdate.setId(skill.getId());
        skillToUpdate.setName("Lisp adsg");
        skillToUpdate.setDescription(skill.getDescription());
        skillToUpdate.setType(skill.getType());
        skillToUpdate.setRequiredSkill(skill.getRequiredSkill());

        Skill skillUpdated = skillService.update(skillToUpdate);

        assertNotEquals("Java", skillUpdated.getName());
    }

    @Test
    @Override
    public void testFindById() {
        Skill skill = new Skill();
        skill.setName("Python 2");
        skill.setDescription("Programming language 2");
        skill.setType(SkillType.BCT);
        Skill skillSaved = skillService.create(skill);
        Skill skillFound = skillService.findById(skillSaved.getId());
        assertEquals(skill.getId(), skillFound.getId());
    }

    @Test
    public void testFindByName() {
        Skill skill = new Skill();
        skill.setName("Python 2");
        skill.setDescription("Programming language 2");
        skill.setType(SkillType.BCT);
        skillService.create(skill);

        Skill skill2 = new Skill();
        skill2.setName("C++");
        skill2.setDescription("Programming language 3");
        skill2.setType(SkillType.BCT);
        skillService.create(skill2);

        // Get Skill by name.
        Skill skill3 = skillService.findByName("Python 2").get(0);
        assertEquals(skill.getName(), skill3.getName());
    }

    @Test
    void findByRequiredSkill() {
        Skill skill = new Skill();
        skill.setName("Python 2");
        skill.setDescription("Programming language 2");
        skill.setType(SkillType.BCT);
        skillService.create(skill);

        Skill skill2 = new Skill();
        skill2.setName("Java 3");
        skill2.setDescription("Programming language 3");
        skill2.setType(SkillType.BCT);
        skillService.create(skill2);

        Skill skill3 = new Skill();
        skill3.setName("C++");
        skill3.setDescription("Programming language 4");
        skill3.setType(SkillType.BCT);
        skill3.addRequiredSkill(skill);
        skill3.addRequiredSkill(skill2);
        skillService.create(skill3);

        // Get the Skill with association with skill "Python 2".
        List<Skill> skills = skillService.findByRequiredSkill(skill.getId());
        // Should be not null.
        assertNotNull(skills);

        // Get the Skill found.
        Skill skillFound = skills.get(0);

        // Should be only one skill with association with "Python 2".
        assertEquals(1, skills.size());
        // Should be the Skill "C++".
        assertEquals(skill3.getName(), skillFound.getName());
        // The skill required should be "Python 2".
        assertEquals(skill.getName(), skillFound.getRequiredSkill().get(0).getName());
    }

    @Test
    void findBySubSkill(){
        Skill skill = new Skill();
        skill.setName("Python 2");
        skill.setDescription("Programming language 2");
        skill.setType(SkillType.BCT);
        skillService.create(skill);

        Skill skill2 = new Skill();
        skill2.setName("Java 3");
        skill2.setDescription("Programming language 3");
        skill2.setType(SkillType.BCT);
        skill2.setSubSkill(skill);
        skillService.create(skill2);

        // Get the Skill with association with skill "Python 2".
        List<Skill> skills = skillService.findBySubSkill(skill.getId());
        // Should be not null.
        assertNotNull(skills);

        // Get the Skill found.
        Skill skillFound = skills.get(0);

        // Should be only one skill with association with "Python 2".
        assertEquals(1, skills.size());
        // Should be the Skill "C++".
        assertEquals(skill2.getName(), skillFound.getName());
        // The skill required should be "Python 2".
        assertEquals(skill.getName(), skillFound.getSubSkill().getName());

    }

    @Test
    @Override
    public void testDeleteById(){
        Skill skill = new Skill();
        skill.setName("C#");
        skill.setDescription("Microsoft");
        skill.setType(SkillType.BCT);
        skillService.create(skill);

        Skill skill2 = new Skill();
        skill2.setName("Java 3");
        skill2.setDescription("Programming language 3");
        skill2.setType(SkillType.BCT);
        skill2.addRequiredSkill(skill);
        skillService.create(skill2);

        // Delete a Skill.
        skillService.deleteById(skill2.getId());
        // Get All Skill.
        List<Skill> skillFound = skillService.findAll();

        // Should be empty.
        assertEquals(1, skillFound.size());
        // Should be equals.
        assertEquals(skill.getName(), skillFound.get(0).getName());
    }

    @Test
    @Override
    public void testFindAll(){
        Skill skill = new Skill();
        skill.setName("C++");
        skill.setDescription("Borland");
        skill.setType(SkillType.BCT);
        // Persist the Skill before querying.
        skillService.create(skill);

        Skill skill1 = new Skill();
        skill1.setName("C#");
        skill1.setDescription("Microsoft");
        skill1.setType(SkillType.BCT);
        skill1.addRequiredSkill(skill);
        // Persist the Skill before querying.
        skillService.create(skill1);

        // Assert that the result should be two Skill.
        assertEquals(2,skillService.findAll().size());
    }

    @Test
    public void testFindByType(){
        Skill skill = new Skill();
        skill.setName("Java");
        skill.setDescription("Oracle");
        skill.setType(SkillType.BCT);
        // Persist the Skill before querying.
        skillService.create(skill);

        Skill skill1 = new Skill();
        skill1.setName("Architecture");
        skill1.setDescription("Architecture");
        skill1.setType(SkillType.PHYSICAL);
        skill1.addRequiredSkill(skill);
        // Persist the Skill before querying.
        skillService.create(skill1);

        assertEquals(1,skillService.findByType(SkillType.PHYSICAL).size());
    }
}
