package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.Content;
import ca.uqam.latece.evo.server.core.repository.ContentRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Content Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
public class ContentService extends AbstractEvoService<Content> {
    @Autowired
    private ContentRepository contentRepository;

    public ContentService() {}

    @Override
    public Content create(Content content){
        return this.save(content);
    }

    @Override
    public Content update(Content content){
        return this.save(content);
    }

    private Content save(Content content){
        ObjectValidator.validateObject(content);
        ObjectValidator.validateString(content.getName());
        ObjectValidator.validateString(content.getDescription());
        ObjectValidator.validateString(content.getType());
        return contentRepository.save(content);
    }

    @Override
    public void deleteById(Long id){
        ObjectValidator.validateId(id);
        contentRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return contentRepository.existsById(id);
    }

    public boolean existsByName(String name) {
        return contentRepository.existsByName(name);
    }

    @Override
    public Content findById(Long id){
        ObjectValidator.validateId(id);
        return contentRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Content not found!"));
    }


    public List<Content> findByName(String name){
        ObjectValidator.validateString(name);
        return contentRepository.findByName(name);
    }

    public List<Content> findByType(String type){
        ObjectValidator.validateString(type);
        return contentRepository.findByType(type);
    }

    @Override
    public List<Content> findAll(){
        return contentRepository.findAll();
    }
}
