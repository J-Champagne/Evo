package ca.uqam.latece.evo.server.core.service;


import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.model.Requires;
import ca.uqam.latece.evo.server.core.repository.RequiresRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Requires Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
public class RequiresService extends AbstractEvoService<Requires> {
    @Autowired
    private RequiresRepository requiresRepository;

    public RequiresService() {}

    @Override
    public Requires create(Requires requires) {
        return this.save(requires);
    }

    @Override
    public Requires update(Requires requires) {
        return this.save(requires);
    }

    private Requires save(Requires requires) {
        ObjectValidator.validateObject(requires);
        ObjectValidator.validateString (requires.getLevel().toString());
        return requiresRepository.save(requires);
    }

    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        requiresRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return requiresRepository.existsById(id);
    }

    @Override
    public Requires findById(Long id) {
        ObjectValidator.validateId(id);
        return requiresRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Requires not found!"));
    }

    public List<Requires> findByLevel(SkillLevel level){
        ObjectValidator.validateObject(level);
        return requiresRepository.findByLevel(level);
    }

    @Override
    public List<Requires> findAll() {
        return requiresRepository.findAll();
    }

}
