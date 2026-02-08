package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.model.Content;
import ca.uqam.latece.evo.server.core.service.ContentService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Content Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/contents")
public class ContentController extends AbstractEvoController<Content> {
    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private ContentService contentService;

    /**
     * Inserts a Content in the database.
     * @param content the Content entity.
     * @return The saved Content.
     * @throws IllegalArgumentException in case the given Content is null.
     * @throws OptimisticLockingFailureException when the Content uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Content> create(@Valid @RequestBody Content content) {
        ResponseEntity<Content> response;

        try {
            ObjectValidator.validateObject(content);
            Content saved = contentService.create(content);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new Content: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new Content.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new Content. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Inserts a Content in the database.
     * @param content the Content entity.
     * @param file the file to be stored
     * @return The saved Content.
     * @throws IllegalArgumentException in case the given Content is null.
     * @throws OptimisticLockingFailureException when the Content uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PostMapping(params = "file=true",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Content> create(@Valid @ModelAttribute Content content, @RequestParam("file") MultipartFile file) {
        ResponseEntity<Content> response;

        try {
            ObjectValidator.validateObject(content);
            Content saved = contentService.create(content, file);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new Content and stored file: {} {}", saved, saved.getFilename());
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.info("Failed to create new Content or to store file.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new Content or to store file. Error: {}", e.getMessage());
        }

        return response;
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
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Content> update(@Valid @RequestBody Content content) {
        ResponseEntity<Content> response;

        try {
            ObjectValidator.validateObject(content);
            Content updated = contentService.update(content);

            if (updated != null && updated.getId() > 0) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated Content: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update Content.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update Content. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates the Content in the database.
     * @param content the Content entity.
     * @param file the file to be stored
     * @return The saved Content.
     * @throws IllegalArgumentException in case the given Content is null.
     * @throws OptimisticLockingFailureException when the Content uses optimistic locking and has a version attribute with
     *           a different value from that found in the persistence store. Also thrown if the entity is assumed to be
     *           present but does not exist in the database.
     */
    @PutMapping(params = "file=true",
                consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Content> update(@Valid @ModelAttribute Content content, @RequestParam("file") MultipartFile file) {
        ResponseEntity<Content> response;

        try {
            ObjectValidator.validateObject(content);
            Content updated = contentService.update(content, file);

            if (updated != null && updated.getId() > 0) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated Content with file: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to update Content with file.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update Content with file. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the content with the given id.
     * <p>
     * If the content is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the content to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        contentService.deleteById(id);
        logger.info("Content deleted: {}", id);
    }

    /**
     * Retrieves a Content by its id.
     * @param id The Content Id to filter Content entities by, must not be null.
     * @return the Content with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Content> findById(@PathVariable Long id) {
        ResponseEntity<Content> response;

        try {
            ObjectValidator.validateId(id);
            Content content = contentService.findById(id);

            if (content != null && content.getId().equals(id)) {
                response = new ResponseEntity<>(content, HttpStatus.OK);
                logger.info("Found Content: {}", content);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Content by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Content by id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a Content and its associated file with an id.
     * @param id The Content id to filter Content entities by, must not be null.
     * @return the requested Content with its associated file or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    @GetMapping(value = "/find/file/{id}/{filename}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Resource> findFile(@PathVariable Long id, @PathVariable String filename) {
        ResponseEntity<Resource> response;

        try {
            Resource contentFile = contentService.findFile(id, filename);

            if (contentFile != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(ContentDisposition
                        .attachment()
                        .filename(filename)
                        .build());
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

                response = new ResponseEntity<>(contentFile, headers, HttpStatus.OK);
                logger.info("Found file of Content with id and name: {} {}", id, filename);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find file of Content with id and name: {} {}", id, filename);
            }

        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find file of Content. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Content entities by their name.
     * @param name the name of the T to search for.
     * @return the Content with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findByName(@PathVariable String name) {
        ResponseEntity<List<Content>> response;

        try {
            ObjectValidator.validateString(name);
            List<Content> contentList = contentService.findByName(name);

            if (contentList != null && !contentList.isEmpty()) {
                response = new ResponseEntity<>(contentList, HttpStatus.OK);
                logger.info("Found Content list by name: {}", contentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Content list by name: {}", name);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Content list name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Content entities that match the specified BCI Activity Id.
     * @param id The BCI Activity Id to filter Content entities by, must not be null.
     * @return a list of Content entities that have the specified BCI Activity Id, or an empty list if no matches are found.
     */
    @GetMapping("/find/bciactivityid/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findByBCIActivity(@PathVariable Long id) {
        ResponseEntity<List<Content>> response;

        try {
            ObjectValidator.validateId(id);
            List<Content> contentList = contentService.findByBCIActivity(id);

            if (contentList != null && !contentList.isEmpty()) {
                response = new ResponseEntity<>(contentList, HttpStatus.OK);
                logger.info("Found Content list by BCIActivity id: {}", contentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Content list by BCIActivity id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Content list by BCIActivity id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Content entities that match the specified Skill Id.
     * @param id The Skill Id to filter Content entities by, must not be null.
     * @return a list of Content entities that have the specified Skill Id, or an empty list if no matches are found.
     */
    @GetMapping("/find/skill/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findBySkill(@PathVariable Long id) {
        ResponseEntity<List<Content>> response;

        try {
            ObjectValidator.validateId(id);
            List<Content> contentList = contentService.findBySkill(id);

            if (contentList != null && !contentList.isEmpty()) {
                response = new ResponseEntity<>(contentList, HttpStatus.OK);
                logger.info("Found Content list by Skill id: {}", contentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Content list by Skill id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Content list by Skill id. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Content entities by their type.
     * @param type the type of the content to search for.
     * @return a list of Content entities matching the specified type.
     */
    @GetMapping("/find/type/{type}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findByType(@PathVariable String type) {
        ResponseEntity<List<Content>> response;

        try {
            List<Content> contentList = contentService.findByType(type);

            if (contentList != null && !contentList.isEmpty()) {
                response = new ResponseEntity<>(contentList, HttpStatus.OK);
                logger.info("Found Content list by type: {}", contentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Content list by type: {}", type);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Content list by type. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all Content.
     * @return all Content.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Content>> findAll() {
        ResponseEntity<List<Content>> response;

        try {
            List<Content> contentList = contentService.findAll();

            if (contentList != null && !contentList.isEmpty()) {
                response = new ResponseEntity<>(contentList, HttpStatus.OK);
                logger.info("Found all Content: {}", contentList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all Content list.");
                System.out.println("Failed to find all Content list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all Content list. Error: {}", e.getMessage());
        }

        return response;
    }
}
