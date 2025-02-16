package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Skill;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link SkillService} class to validate CRUD functionality and domain-specific queries.
 * This test class extends AbstractServiceTest, inheriting base setup and infrastructure for
 * testing database interactions through Spring's testing framework.
 *
 * The class includes tests for the following functionalities:
 *
 * - Saving a Skill entity to the database and verifying persistence.
 * - Updating an existing Skill entity and validating field value changes.
 * - Finding Skill entities by their name and ensuring correctness in results.
 * - Deleting Skill entities by their identifiers and ensuring proper deletion.
 * - Retrieving all Skill entities from the database and validating completeness.
 * - Finding Skill entities by their type and verifying query results.
 *
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
        System.out.println(skill.getId());
        System.out.println(skill.getName());

        assert skill.getId() > 0;
    }

    @Test
    @Override
    public void testUpdate() {
        Skill skill = new Skill();
        skill.setName("Java");
        skill.setDescription("Programming language");
        skill.setType(SkillType.BCT);
        skillService.create(skill);

        // Update the Skill name.
        skill.setName("Python");
        Skill skillUpdated = skillService.update(skill);

        assertNotEquals("Java",skillUpdated.getName());
    }

    @Test
    @Override
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

        Skill skill3 = skillService.findByName("Python 2").get(0);
        assertEquals(skill.getName(),skill3.getName());
    }

    @Test
    @Override
    public void testDeleteById(){
        Skill skill = new Skill();
        skill.setName("C#");
        skill.setDescription("Microsoft");
        skill.setType(SkillType.BCT);
        skillService.create(skill);

        // Delete a Skill.
        skillService.deleteById(skill.getId());
        // Should be empty.
        assertEquals(0,skillService.findAll().size());
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
        // Persist the Skill before querying.
        skillService.create(skill1);

        assertEquals(1,skillService.findByType(SkillType.PHYSICAL).size());
    }
}
