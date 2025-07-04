package ca.uqam.latece.evo.server.core.controller;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMedium;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMode;
import ca.uqam.latece.evo.server.core.model.Interaction;
import ca.uqam.latece.evo.server.core.service.InteractionService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Interaction Controller.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@RestController
@RequestMapping("/interaction")
public class InteractionController extends AbstractEvoController <Interaction> {
    private static final Logger logger = LoggerFactory.getLogger(InteractionController.class);

    @Autowired
    private InteractionService interactionService;


    /**
     * Inserts an Interaction in the database.
     * @param model The Interaction entity.
     * @return The saved Interaction.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // 201
    public ResponseEntity<Interaction> create(@Valid @RequestBody Interaction model) {
        ResponseEntity<Interaction> response;

        try{
            Interaction saved = interactionService.create(model);

            if (saved != null && saved.getId() > 0) {
                response = new ResponseEntity<>(saved, HttpStatus.CREATED);
                logger.info("Created new Interaction: {}", saved);
            } else {
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                logger.error("Failed to create new Interaction.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to create new Interaction. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Updates an Interaction in the database.
     * @param model The Interaction entity.
     * @return The saved Interaction.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Interaction> update(@RequestBody Interaction model) {
        ResponseEntity<Interaction> response;

        try {
            Interaction updated = interactionService.update(model);

            if (updated != null && updated.getId().equals(model.getId())) {
                response = new ResponseEntity<>(updated, HttpStatus.OK);
                logger.info("Updated Interaction: {}", updated);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.error("Failed to update Interaction.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to update Interaction. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Deletes the Interaction with the given id.
     * <p>
     * If the Interaction is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Interaction to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void deleteById(@PathVariable Long id) {
        interactionService.deleteById(id);
        logger.info("Interaction deleted: {}", id);
    }

    /**
     * Retrieves an Interaction by its id.
     * @param id The Interaction Id to filter Interaction entities by, must not be null.
     * @return the Interaction with the given id.
     */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<Interaction> findById(@PathVariable Long id) {
        ResponseEntity<Interaction> response;

        try {
            Interaction interaction = interactionService.findById(id);

            if (interaction != null && interaction.getId().equals(id)) {
                response = new ResponseEntity<>(interaction, HttpStatus.OK);
                logger.info("Found Interaction by id: {}", interaction);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction by id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Interaction entities by their name.
     * @param name the type of the Interaction to search for.
     * @return a list of Interaction entities matching the specified name.
     */
    @GetMapping("/find/name/{name}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findByName(@PathVariable String name) {
        ResponseEntity<List<Interaction>> response;

        try {
            List<Interaction> interactionList = interactionService.findByName(name);

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found Interaction: {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction by name: {}", name);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction by name. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Finds a list of Interaction entities by their type.
     * @param type the type of the Interaction to search for.
     * @return a list of Interaction entities matching the specified type.
     */
    @GetMapping("/find/type/{type}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findByType(@PathVariable ActivityType type) {
        ResponseEntity<List<Interaction>> response;

        try {
            List<Interaction> interactionList = interactionService.findByType(type);

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found Interaction list: {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction list by type: {}", type);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction list by type. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Interaction entities that match the specified Develops Id.
     * @param id The Develops Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified Develops id, or an empty list if no matches are found.
     */
    @GetMapping("/find/develops/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findByDevelops(@PathVariable Long id) {
        ResponseEntity<List<Interaction>> response;

        try {
            ObjectValidator.validateObject(id);
            List<Interaction> interactionList = interactionService.findByDevelops(id);

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found Interaction with Develops association: {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction by Develops: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction by Develops. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Interaction entities that match the specified Requires Id.
     * @param id The Requires Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified Requires id, or an empty list if no matches are found.
     */
    @GetMapping("/find/requires/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findByRequires(@PathVariable Long id) {
        ResponseEntity<List<Interaction>> response;

        try {
            List<Interaction> interactionList = interactionService.findByRequires(id);

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found Interaction with Requires association: {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction by Requires: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction by Requires. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Interaction entities that match the specified Content Id.
     * @param id The Content Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified Content id, or an empty list if no matches are found.
     */
    @GetMapping("/find/content/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findByContent(@PathVariable Long id) {
        ResponseEntity<List<Interaction>> response;

        try {
            List<Interaction> interactionList = interactionService.findByContent(id);

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found Interaction with Content association: {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction with Content association: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction with Content association. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Interaction entities that match the specified Initiator Role Id.
     * @param id The Initiator Role Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified Role id.
     */
    @GetMapping("/find/initiatorrole/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findByInteractionInitiatorRoleId(@PathVariable Long id) {
        ResponseEntity<List<Interaction>> response;

        try {
            List<Interaction> interactionList = interactionService.findByInteractionInitiatorRole_Id(id);

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found Interaction with Initiator Role: {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction with Initiator Role Id: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction with Initiator Role. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Interaction entities that match the specified parties Id (Role Id).
     * @param id The parties Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified parties id.
     */
    @GetMapping("/find/parties/{id}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findByPartiesId(@PathVariable Long id) {
        ResponseEntity<List<Interaction>> response;

        try {
            List<Interaction> interactionList = interactionService.findByParties_Id(id);

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found Interaction with Parties: {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction with Parties: {}", id);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction with Parties. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMode.
     * @param mode The interactionMode to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMode.
     */
    @GetMapping("/find/mode/{mode}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findByInteractionMode(@PathVariable InteractionMode mode) {
        ResponseEntity<List<Interaction>> response;

        try {
            List<Interaction> interactionList = interactionService.findByInteractionMode(mode);

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found Interaction with mode: {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction with mode: {}", mode);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction with mode. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMedium1.
     * @param medium1 The medium to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMedium.
     */
    @GetMapping("/find/interactionmedium1/{medium1}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findByInteractionMedium1(@PathVariable InteractionMedium medium1) {
        ResponseEntity<List<Interaction>> response;

        try {
            List<Interaction> interactionList = interactionService.findByInteractionMedium1(medium1);

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found Interaction with InteractionMedium 1: {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction with InteractionMedium 1: {}", medium1);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction with InteractionMedium 1. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMedium2.
     * @param medium2 The medium to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMedium.
     */
    @GetMapping("/find/interactionmedium2/{medium2}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findByInteractionMedium2(@PathVariable InteractionMedium medium2) {
        ResponseEntity<List<Interaction>> response;

        try {
            List<Interaction> interactionList = interactionService.findByInteractionMedium2(medium2);

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found Interaction with InteractionMedium 2: {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction with InteractionMedium 2: {}", medium2);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction with InteractionMedium 2. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMedium 3.
     * @param medium3 The medium to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMedium.
     */
    @GetMapping("/find/interactionmedium3/{medium3}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findByInteractionMedium3(@PathVariable InteractionMedium medium3) {
        ResponseEntity<List<Interaction>> response;

        try {
            List<Interaction> interactionList = interactionService.findByInteractionMedium3(medium3);

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found Interaction with InteractionMedium 3: {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction with InteractionMedium 3: {}", medium3);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction with InteractionMedium 3. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMedium 4.
     * @param medium4 The medium to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMedium.
     */
    @GetMapping("/find/interactionmedium4/{medium4}")
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findByInteractionMedium4(@PathVariable InteractionMedium medium4) {
        ResponseEntity<List<Interaction>> response;

        try {
            List<Interaction> interactionList = interactionService.findByInteractionMedium4(medium4);

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found Interaction with InteractionMedium 4: {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find Interaction with InteractionMedium 4: {}", medium4);
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find Interaction with InteractionMedium 4. Error: {}", e.getMessage());
        }

        return response;
    }

    /**
     * Gets all Interaction.
     * @return all Interaction.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 200
    public ResponseEntity<List<Interaction>> findAll() {
        ResponseEntity<List<Interaction>> response;

        try {
            List<Interaction> interactionList = interactionService.findAll();

            if (interactionList != null && !interactionList.isEmpty()) {
                response = new ResponseEntity<>(interactionList, HttpStatus.OK);
                logger.info("Found all Interaction : {}", interactionList);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                logger.info("Failed to find all Interaction list.");
            }
        } catch (Exception e) {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            logger.error("Failed to find all Interaction list. Error: {}", e.getMessage());
        }

        return response;
    }
}
