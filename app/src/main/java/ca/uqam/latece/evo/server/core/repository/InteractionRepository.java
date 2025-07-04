package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMedium;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMode;
import ca.uqam.latece.evo.server.core.model.Interaction;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * Interaction repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface InteractionRepository extends EvoRepository<Interaction> {
    /**
     * Finds a list of Interaction entities by their name.
     * @param name the name of the Interaction to search for.
     * @return the Interaction with the given name or Optional#empty() if none found.
     * @throws IllegalArgumentException if the name is null.
     */
    List<Interaction> findByName(String name);

    /**
     * Checks if an Interaction entity with the specified name exists in the repository.
     * @param name the name of the Interaction to check for existence, must not be null.
     * @return true if an Interaction with the specified name exists, false otherwise.
     * @throws IllegalArgumentException if the name is null.
     */
    boolean existsByName(String name);

    /**
     * Finds a list of Interaction entities by their type.
     * @param type the type of the Interaction to search for.
     * @return a list of Interaction entities matching the specified type.
     */
    List<Interaction> findByType(ActivityType type);

    /**
     * Retrieves a list of Interaction entities that match the specified Develops Id.
     * @param developsBCIActivityId The Develops Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified Develops id, or an empty list if no matches are found.
     */
    List<Interaction> findByDevelopsBCIActivity_Id(Long developsBCIActivityId);

    /**
     * Retrieves a list of Interaction entities that match the specified Requires Id.
     * @param requiresId The Requires Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified Requires id, or an empty list if no matches are found.
     */
    List<Interaction> findByRequiresBCIActivities_Id(Long requiresId);

    /**
     * Retrieves a list of BCIActivity entities that match the specified Role id.
     * @param partiesId The Role od to filter BCIActivity entities by, must not be null.
     * @return a list of BCIActivity entities that have the specified Role id, or an empty list if no matches are found.
     */
    List<Interaction> findByParties_Id(Long partiesId);

    /**
     * Retrieves a list of Interaction entities that match the specified Content Id.
     * @param contentId The Content Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified Content id, or an empty list if no matches are found.
     */
    List<Interaction> findByContentBCIActivities_Id(Long contentId);

    /**
     * Retrieves a list of Interaction entities that match the specified Role Id.
     * @param roleId The Role Id to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified Role id, or an empty list if no matches are found.
     */
    List<Interaction> findByInteractionInitiatorRole_Id(@NotNull Long roleId);

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMode.
     * @param interactionMode The interactionMode to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMode, or an empty list if no matches are found.
     */
    List<Interaction> findByInteractionMode(@NotNull InteractionMode interactionMode);

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMedium1.
     * @param interactionMedium1 The interactionMode to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMedium, or an empty list if no matches are found.
     */
    List<Interaction> findByInteractionMedium1(@NotNull InteractionMedium interactionMedium1);

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMedium2.
     * @param interactionMedium2 The interactionMode to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMedium, or an empty list if no matches are found.
     */
    List<Interaction> findByInteractionMedium2(@NotNull InteractionMedium interactionMedium2);

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMedium3.
     * @param interactionMedium3 The interactionMode to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMedium, or an empty list if no matches are found.
     */
    List<Interaction> findByInteractionMedium3(@NotNull InteractionMedium interactionMedium3);

    /**
     * Retrieves a list of Interaction entities that match the specified InteractionMedium4.
     * @param interactionMedium4 The interactionMode to filter Interaction entities by, must not be null.
     * @return a list of Interaction entities that have the specified InteractionMedium, or an empty list if no matches are found.
     */
    List<Interaction> findByInteractionMedium4(@NotNull InteractionMedium interactionMedium4);
}
