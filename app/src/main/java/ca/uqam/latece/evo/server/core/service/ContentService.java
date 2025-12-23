package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.Content;
import ca.uqam.latece.evo.server.core.repository.ContentRepository;
import ca.uqam.latece.evo.server.core.util.LocalStorage;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Content Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class ContentService extends AbstractEvoService<Content> {
    private static final Logger logger = LoggerFactory.getLogger(ContentService.class);

    @Autowired
    private ContentRepository contentRepository;

    public ContentService() {}

    /**
     * Inserts a Content in the database.
     * @param content the Content entity.
     * @return The saved Content.
     * @throws IllegalArgumentException in case the given Content is null.
     * @throws OptimisticLockingFailureException when the Content uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public Content create(Content content){
        Content contentCreated = null;

        ObjectValidator.validateObject(content);
        ObjectValidator.validateString(content.getName());
        ObjectValidator.validateString(content.getDescription());

        if (content.getFilename() != null && !content.getFilename().isEmpty()) {
            throw new IllegalArgumentException("Filename should be empty since no files were sent");
        }

        // Name should be unique.
        if (this.existsByName(content.getName())) {
            throw this.createDuplicateException(content);
        } else {
            contentCreated = this.save(content);
            logger.info("Content created: {}", contentCreated);
        }

        return contentCreated;
    }

    /**
     * Inserts a Content in the database and a file in the system.
     * @param content the Content entity.
     * @param file the file to be stored
     * @return The saved Content.
     * @throws IllegalArgumentException in case the given Content is null.
     * @throws OptimisticLockingFailureException when the Content uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    public Content create(Content content, MultipartFile file){
        Content contentCreated = null;
        String filename;

        ObjectValidator.validateObject(content);
        ObjectValidator.validateString(content.getName());
        ObjectValidator.validateString(content.getDescription());
        ObjectValidator.validateFilename(content.getFilename());

        if (!content.getFilename().equals(file.getOriginalFilename())) {
            throw new IllegalArgumentException("File should have the same name as the one specified in content");
        }

        // Name should be unique.
        if (this.existsByName(content.getName())) {
            throw this.createDuplicateException(content);
        } else {
            contentCreated = this.save(content);
            LocalStorage localStorage = new LocalStorage("content", content.getId().toString());
            filename = localStorage.store(file);

            logger.info("Content created and file stored: {} {}", contentCreated, filename);
        }

        return contentCreated;
    }

    /**
     * Create duplicate Content Exception.
     * @param content the Content entity.
     * @return an exception object.
     */
    private IllegalArgumentException createDuplicateException(Content content) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_NAME_ALREADY_REGISTERED +
                " Content Name: " + content.getName());
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    /**
     * Updates the Content in the database.
     * @param content the Content entity.
     * @return The saved Content.
     * @throws IllegalArgumentException in case the given Content is null.
     * @throws OptimisticLockingFailureException when the Content uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @Override
    public Content update(Content content) {
        Content contentUpdated = null;
        Content contentFound = findById(content.getId());

        ObjectValidator.validateObject(content);
        ObjectValidator.validateString(content.getName());
        ObjectValidator.validateString(content.getDescription());

        if (content.getFilename() != null && !content.getFilename().isEmpty()) {
            throw new IllegalArgumentException("Filename should be empty since no files were sent");

        } else if (contentFound == null) {
            throw new IllegalArgumentException("Content " + content.getName() + " not found!");

        } else if (!content.getName().equals(contentFound.getName()) && contentRepository.existsByName(content.getName())) {
            throw this.createDuplicateException(content);

        } else {
            contentUpdated = this.save(content);
            logger.info("Content updated: {}", contentUpdated);
        }

        return contentUpdated;
    }

    /**
     * Updates the Content in the database and its associated file in the system.
     * @param content the Content entity.
     * @param file the file to be stored
     * @return The saved Content.
     * @throws IllegalArgumentException in case the given Content is null.
     * @throws OptimisticLockingFailureException when the Content uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    public Content update(Content content, MultipartFile file) {
        Content contentUpdated = null;
        Content contentFound = findById(content.getId());

        ObjectValidator.validateObject(content);
        ObjectValidator.validateString(content.getName());
        ObjectValidator.validateString(content.getDescription());
        ObjectValidator.validateFilename(content.getFilename());

        if (!content.getFilename().equals(file.getOriginalFilename())) {
            throw new IllegalArgumentException("File should have the same name as the one specified in content");

        } else if (contentFound == null) {
            throw new IllegalArgumentException("Content " + content.getName() + " not found!");

        } else if (!content.getName().equals(contentFound.getName()) && contentRepository.existsByName(content.getName())) {
            throw this.createDuplicateException(content);

        } else {
            String oldFilename = contentFound.getFilename();
            contentUpdated = this.save(content);
            LocalStorage localStorage = new LocalStorage("content", contentUpdated.getId().toString());
            localStorage.delete(oldFilename);
            localStorage.store(file);
            logger.info("Content with file updated: {} {}", contentUpdated, content.getFilename());
        }

        return contentUpdated;
    }

    /**
     * Inserts or updates the Content in the database.
     * @param content the Content entity.
     * @return The saved Content.
     * @throws IllegalArgumentException in case the given Content is null.
     * @throws OptimisticLockingFailureException when the Content uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    protected Content save(Content content){
        return this.contentRepository.save(content);
    }

    /**
     * Deletes the content with the given id.
     * <p>
     * If the content is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the content to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        contentRepository.deleteById(id);
        LocalStorage localStorage = new LocalStorage("content", id.toString());
        localStorage.deleteAll();
        logger.info("Content deleted: {}", id);
    }

    /**
     * Checks if a content entity with the specified id exists in the repository.
     * @param id the id of the content to check for existence, must not be null.
     * @return true if a content with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return contentRepository.existsById(id);
    }

    /**
     * Checks if a content entity with the specified name exists in the repository.
     * @param name the name of the content to check for existence, must not be null.
     * @return true if a content with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        return contentRepository.existsByName(name);
    }

    /**
     * Retrieves a Content by its id.
     * @param id The Content Id to filter Content entities by, must not be null.
     * @return the Content with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    @Override
    public Content findById(Long id){
        ObjectValidator.validateId(id);
        return contentRepository.findById(id).orElse(null);
    }

    /**
     * Finds a list of Content entities by their name.
     * @param name the name of the T to search for.
     * @return the Content with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    public List<Content> findByName(String name){
        ObjectValidator.validateString(name);
        return contentRepository.findByName(name);
    }

    /**
     * Finds a list of Content entities by their type.
     * @param type the type of the content to search for.
     * @return a list of Content entities matching the specified type.
     * @throws IllegalArgumentException – if type is null.
     */
    public List<Content> findByType(String type){
        ObjectValidator.validateString(type);
        return contentRepository.findByType(type);
    }

    /**
     * Retrieves a list of Content entities that match the specified BCI Activity Id.
     * @param bciActivityId The BCI Activity Id to filter Content entities by, must not be null.
     * @return a list of Content entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     * @throws IllegalArgumentException if the bciActivityId is null.
     */
    public List<Content> findByBCIActivity(Long bciActivityId) {
        ObjectValidator.validateId(bciActivityId);
        return contentRepository.findByBCIActivity(bciActivityId);
    }

    /**
     * Retrieves a list of Content entities that match the specified Skill Id.
     * @param skillId The Skill Id to filter Content entities by, must not be null.
     * @return a list of Content entities that have the specified Skill Id, or an empty list if no matches are found.
     */
    public List<Content> findBySkill(Long skillId) {
        ObjectValidator.validateId(skillId);
        return contentRepository.findBySkill(skillId);
    }

    /**
     * Gets all Content.
     * @return all Content.
     */
    @Override
    public List<Content> findAll(){
        return contentRepository.findAll();
    }


    public Resource findFile(Long id, String fileName) {
        ObjectValidator.validateId(id);
        ObjectValidator.validateString(fileName);
        LocalStorage localStorage = new LocalStorage("content", id.toString());
        return localStorage.loadAsResource(fileName);
    }
}
