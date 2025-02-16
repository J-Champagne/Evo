package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.SkillType;
import ca.uqam.latece.evo.server.core.model.Skill;
import ca.uqam.latece.evo.server.core.repository.SkillRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Skill Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class SkillService extends AbstractEvoService<Skill> {
    @Autowired
    private SkillRepository skillRepository;

    public SkillService() {}

    @Override
    public Skill create(Skill skill) {
        return this.save(skill);
    }

    @Override
    public Skill update(Skill skill) {
        return this.save(skill);
    }

    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return skillRepository.existsById(id);
    }

    private Skill save (Skill skill) {
        ObjectValidator.validateObject(skill);
        ObjectValidator.validateString(skill.getName());
        ObjectValidator.validateString(skill.getDescription());
        return skillRepository.save(skill);
    }

    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        skillRepository.deleteById(id);
    }

    @Override
    public Skill findById(Long id) {
        ObjectValidator.validateId(id);
        return skillRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Skill not found!"));
    }

    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    public List<Skill> findByName(String name){
        ObjectValidator.validateString(name);
        return skillRepository.findByName(name);
    }

    public List<Skill> findByType(SkillType type){
        ObjectValidator.validateObject(type);
        return skillRepository.findByType(type);
    }

}
