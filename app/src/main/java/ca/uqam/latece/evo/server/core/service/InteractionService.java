package ca.uqam.latece.evo.server.core.service;


import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMedium;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMode;
import ca.uqam.latece.evo.server.core.model.Interaction;
import ca.uqam.latece.evo.server.core.repository.InteractionRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Interaction Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class InteractionService extends AbstractEvoService<Interaction> {
    private static final Logger logger = LoggerFactory.getLogger(InteractionService.class);
    
    @Autowired
    private InteractionRepository interactionRepository;


    /**
     * Inserts an Interaction in the database.
     * @param evoModel The Interaction entity.
     * @return The saved Interaction.
     * @throws IllegalArgumentException in case the given Interaction is null.
     */
    @Override
    public Interaction create(Interaction evoModel) {
        Interaction bciActivity = null;

        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateString(evoModel.getName());

        // The name should be unique.
        if(this.existsByName(evoModel.getName())) {
            throw this.createDuplicatedNameException(evoModel, evoModel.getName());
        } else {
            bciActivity = this.save(evoModel);
            logger.info("Interaction created: {}", bciActivity);
        }

        return bciActivity;
    }

    /**
     * Updates an Interaction in the database.
     * @param evoModel The Interaction entity.
     * @return The saved Interaction.
     * @throws IllegalArgumentException in case the given Interaction is null.
     */
    @Override
    public Interaction update(Interaction evoModel) {
        Interaction bciActivity = null;

        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateId(evoModel.getId());
        ObjectValidator.validateString(evoModel.getName());

        // Checks if the Interaction exists in the database.
        Interaction found = this.findById(evoModel.getId());

        if (found == null) {
            throw new IllegalArgumentException("Interaction " + evoModel.getName() + " not found!");
        } else {
            if (evoModel.getName().equals(found.getName())) {
                bciActivity = this.save(evoModel);
            } else {
                if (this.existsByName(evoModel.getName())) {
                    throw this.createDuplicatedNameException(evoModel, evoModel.getName());
                } else {
                    bciActivity = this.save(evoModel);
                }
            }

            logger.info("Interaction updated: {}", bciActivity);
        }

        return bciActivity;
    }

    /**
     * Method used to create or update an Interaction.
     * @param evoModel The Interaction entity.
     * @return The Interaction.
     */
    @Transactional
    protected Interaction save(Interaction evoModel) {
        return interactionRepository.save(evoModel);
    }

    /**
     * Checks if an Interaction entity with the specified id exists in the repository.
     * @param id the id of the Interaction to check for existence, must not be null.
     * @return true if an Interaction with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return interactionRepository.existsById(id);
    }

    /**
     * Checks if an Interaction entity with the specified name exists in the repository.
     * @param name the name of the Interaction to check for existence, must not be null.
     * @return true if an Interaction with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    public boolean existsByName(String name) {
        ObjectValidator.validateString(name);
        return interactionRepository.existsByName(name);
    }

    /**
     * Deletes the Interaction with the given id.
     * <p>
     * If the Interaction is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the Interaction to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        interactionRepository.deleteById(id);
        logger.info("Interaction deleted: {}", id);
    }

    /**
     * Retrieves an Interaction by its id.
     * @param id The Interaction Id to filter Interaction entities by, must not be null.
     * @return the Interaction with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     * @throws EntityNotFoundException if the Interaction not found.
     */
    @Override
    public Interaction findById(Long id) {
        ObjectValidator.validateId(id);
        return interactionRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Interaction not found!"));
    }

    /**
     * Finds a list of Interaction entities by their name.
     * @param name the type of the Interaction to search for.
     * @return a list of Interaction entities matching the specified name.
     */
    public List<Interaction> findByName(String name) {
        ObjectValidator.validateString(name);
        return interactionRepository.findByName(name);
    }

    /**
     * Finds a list of Interaction entities by their type.
     * @param type the type of the Interaction to search for.
     * @return a list of Interaction entities matching the specified type.
     */
    public List<Interaction> findByType(ActivityType type) {
        ObjectValidator.validateObject(type);
        return interactionRepository.findByType(type);
    }

    /**
     * Retrieves a list of Interaction entities that match the specified Develops Id.
     * @param developsId The Develops Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified Develops id, or an empty list if no matches are found.
     */
    public List<Interaction> findByDevelops(Long developsId) {
        ObjectValidator.validateId(developsId);
        return interactionRepository.findByDevelopsBCIActivity_Id(developsId);
    }

    /**
     * Retrieves a list of Interaction entities that match the specified Requires Id.
     * @param requiresId The Requires Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified Requires id, or an empty list if no matches are found.
     */
    public List<Interaction> findByRequires(Long requiresId) {
        ObjectValidator.validateId(requiresId);
        return interactionRepository.findByRequiresBCIActivities_Id(requiresId);
    }

    /**
     * Retrieves a list of Interaction entities that match the specified Content Id.
     * @param contentId The Content Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified Content id, or an empty list if no matches are found.
     */
    public List<Interaction> findByContent(Long contentId) {
        ObjectValidator.validateId(contentId);
        return interactionRepository.findByContentBCIActivities_Id(contentId);
    }

    /**
     * Retrieves a list of Interaction entities that match the specified Role Id.
     * @param roleId The Role Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified Role id, or an empty list if no matches are found.
     */
    public List<Interaction> findByInteractionInitiatorRole_Id(Long roleId) {
        ObjectValidator.validateId(roleId);
        return interactionRepository.findByInteractionInitiatorRole_Id(roleId);
    }

    /**
     * Retrieves a list of Interaction entities that match the specified parties Id (Role Id).
     * @param partiesId The parties Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified parties id, or an empty list if no matches are found.
     */
    public List<Interaction> findByParties_Id(Long partiesId) {
        ObjectValidator.validateId(partiesId);
        return interactionRepository.findByParties_Id(partiesId);
    }

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMode.
     * @param interactionMode The interactionMode to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMode, or an empty list if no matches are found.
     */
    public List<Interaction> findByInteractionMode(InteractionMode interactionMode) {
        ObjectValidator.validateObject(interactionMode);
        return interactionRepository.findByInteractionMode(interactionMode);
    }

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMedium.
     * @param interactionMedium The interactionMedium to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMedium, or an empty list if no matches are found.
     */
    public List<Interaction> findByInteractionMedium1(InteractionMedium interactionMedium) {
        ObjectValidator.validateObject(interactionMedium);
        return interactionRepository.findByInteractionMedium1(interactionMedium);
    }

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMedium.
     * @param interactionMedium The interactionMedium to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMedium, or an empty list if no matches are found.
     */
    public List<Interaction> findByInteractionMedium2(InteractionMedium interactionMedium) {
        ObjectValidator.validateObject(interactionMedium);
        return interactionRepository.findByInteractionMedium2(interactionMedium);
    }

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMedium.
     * @param interactionMedium The interactionMedium to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMedium, or an empty list if no matches are found.
     */
    public List<Interaction> findByInteractionMedium3(InteractionMedium interactionMedium) {
        ObjectValidator.validateObject(interactionMedium);
        return interactionRepository.findByInteractionMedium3(interactionMedium);
    }

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMedium.
     * @param interactionMedium The interactionMedium to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMedium, or an empty list if no matches are found.
     */
    public List<Interaction> findByInteractionMedium4(InteractionMedium interactionMedium) {
        ObjectValidator.validateObject(interactionMedium);
        return interactionRepository.findByInteractionMedium4(interactionMedium);
    }

    /**
     * Gets all Interaction.
     * @return all Interaction.
     */
    @Override
    public List<Interaction> findAll() {
        return interactionRepository.findAll().stream().toList();
    }
}
