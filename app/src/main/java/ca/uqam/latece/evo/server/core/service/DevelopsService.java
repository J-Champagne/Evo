package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.SkillLevel;
import ca.uqam.latece.evo.server.core.model.Develops;
import ca.uqam.latece.evo.server.core.repository.DevelopsRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Develops Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
public class DevelopsService extends AbstractEvoService<Develops> {

    @Autowired
    private DevelopsRepository developsRepository;

    public DevelopsService() {}

    @Override
    public Develops create(Develops develops) {
        ObjectValidator.validateObject(develops);
        return this.saveDevelops(develops);
    }

    @Override
    public Develops findById(Long id) {
        ObjectValidator.validateId(id);
        return developsRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Develops not found!"));
    }

    public List<Develops> findByLevel(SkillLevel level){
        ObjectValidator.validateObject(level);
        return developsRepository.findByLevel(level);
    }

    @Override
    public Develops update(Develops develops) {
        ObjectValidator.validateObject(develops);
        return this.saveDevelops(develops);
    }

    private Develops saveDevelops(Develops develops){
        return developsRepository.save(develops);
    }

    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        developsRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return developsRepository.existsById(id);
    }

    @Override
    public List<Develops> findAll(){
        return developsRepository.findAll();
    }
}
