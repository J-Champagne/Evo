package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ChangeAspect;
import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.event.BCIBlockInstanceClientEvent;
import ca.uqam.latece.evo.server.core.event.BCIBlockInstanceEvent;
import ca.uqam.latece.evo.server.core.event.BCIPhaseInstanceClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionBlockInstanceRepository;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * BehaviorChangeInterventionBlockInstance Service.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class BehaviorChangeInterventionBlockInstanceService extends AbstractEvoService<BehaviorChangeInterventionBlockInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorChangeInterventionBlockInstanceService.class);

    @Autowired
    private BehaviorChangeInterventionBlockInstanceRepository bciBlockInstanceRepository;

    /**
     * Creates a BehaviorChangeInterventionBlockInstance in the database.
     * @param blockInstance BehaviorChangeInterventionBlockInstance.
     * @return The created BehaviorChangeInterventionBlockInstance.
     * @throws IllegalArgumentException if blockInstance is null.
     */
    @Override
    public BehaviorChangeInterventionBlockInstance create(BehaviorChangeInterventionBlockInstance blockInstance) {
        BehaviorChangeInterventionBlockInstance saved = null;

        ObjectValidator.validateObject(blockInstance);
        ObjectValidator.validateObject(blockInstance.getStage());
        ObjectValidator.validateObject(blockInstance.getActivities());

        saved = this.bciBlockInstanceRepository.save(blockInstance);
        logger.info("BehaviorChangeInterventionBlockInstance created: {}", saved);
        return saved;
    }

    /**
     * Updates a BehaviorChangeInterventionBlockInstance in the database.
     * @param blockInstance BehaviorChangeInterventionBlockInstance.
     * @return The updated BehaviorChangeInterventionBlockInstance.
     * @throws IllegalArgumentException if blockInstance is null.
     */
    @Override
    public BehaviorChangeInterventionBlockInstance update(BehaviorChangeInterventionBlockInstance blockInstance) {
        BehaviorChangeInterventionBlockInstance updated = null;
        BehaviorChangeInterventionBlockInstance found = findById(blockInstance.getId());

        ObjectValidator.validateObject(blockInstance);
        ObjectValidator.validateObject(blockInstance.getStage());
        ObjectValidator.validateObject(blockInstance.getActivities());

        if (found != null) {
            updated = this.bciBlockInstanceRepository.save(blockInstance);
        }
        return updated;
    }

    /**
     * Saves the given BehaviorChangeInterventionBlockInstance in the database.
     * @param blockInstance BehaviorChangeInterventionBlockInstance.
     * @return The saved BehaviorChangeInterventionBlockInstance.
     * @throws IllegalArgumentException if blockInstance is null.
     */
    @Override
    public BehaviorChangeInterventionBlockInstance save(BehaviorChangeInterventionBlockInstance blockInstance) {
        return this.bciBlockInstanceRepository.save(blockInstance);
    }

    /**
     * Deletes a BehaviorChangeInterventionBlockInstance by its id.
     * Silently ignored if not found.
     * @param id Behavior Change Intervention Block Instance id.
     * @throws IllegalArgumentException if id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        bciBlockInstanceRepository.deleteById(id);
        logger.info("BehaviorChangeInterventionBlockInstance deleted {}", id);
    }

    /**
     * Finds a BehaviorChangeInterventionBlockInstance by its id.
     * @param id Behavior Change Intervention Block Instance id.
     * @return BehaviorChangeInterventionBlockInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public BehaviorChangeInterventionBlockInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciBlockInstanceRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("BehaviorChangeInterventionBlockInstance not found."));
    }

    /**
     * Finds BehaviorChangeInterventionBlockInstance entities by their stage.
     * @param stage TimeCycle.
     * @return List of BehaviorChangeInterventionBlockInstance with the given stage.
     * @throws IllegalArgumentException if the stage is null.
     */
    public List<BehaviorChangeInterventionBlockInstance> findByStage(TimeCycle stage) {
        ObjectValidator.validateObject(stage);
        return this.bciBlockInstanceRepository.findByStage(stage);
    }

    /**
     * Finds BehaviorChangeInterventionBlockInstance entities by their BCIActivityInstance id.
     * @param id Behavior Change Intervention Block Instance id.
     * @return List of BehaviorChangeInterventionBlockInstance with the given BCIActivityInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionBlockInstance> findByActivitiesId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciBlockInstanceRepository.findByActivitiesId(id);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionBlockInstance entities
     * associated with the specified behavior change intervention block ID.
     *
     * @param id the ID of the behavior change intervention block to find associated instances for;
     *           must not be null or invalid.
     * @return a list of BehaviorChangeInterventionBlockInstance entities associated with the given ID.
     *         Returns an empty list if no instances are found.
     * @throws IllegalArgumentException if the id is null.
     */
    public List<BehaviorChangeInterventionBlockInstance> findByBehaviorChangeInterventionBlockId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciBlockInstanceRepository.findByBehaviorChangeInterventionBlockId(id);
    }

    /**
     * Checks if a BehaviorChangeInterventionBlockInstance exists in the database by its id
     * @param id Behavior Change Intervention Block Instance id.
     * @return True if existed, otherwise false.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciBlockInstanceRepository.existsById(id);
    }

    /**
     * Finds all BehaviorChangeInterventionBlockInstance entities.
     * @return List of BehaviorChangeInterventionBlockInstance.
     */
    public List<BehaviorChangeInterventionBlockInstance> findAll() {
        return this.bciBlockInstanceRepository.findAll();
    }

    /**
     * Handles BCIBlockInstanceEvent by updating the corresponding BehaviorChangeInterventionBlockInstance
     * when specific conditions related to its execution status, change aspect, and time cycle are met.
     * @param event the BCIBlockInstanceEvent to be processed, which contains information about the
     *              BehaviorChangeInterventionBlockInstance and its state changes.
     */
    @EventListener(BCIBlockInstanceEvent.class)
    public void handleBCIBlockInstanceEvents(BCIBlockInstanceEvent event) {
        if (event !=null) {
            // If this BehaviorChangeInterventionBlockInstance was finished by the BehaviorChangeInterventionPhaseInstance.
            if (event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                    event.getEvoModel().getStatus().equals(ExecutionStatus.FINISHED) &&
                    event.getEvoModel().getStage().equals(TimeCycle.END) &&
                    event.getTimeCycle().equals(TimeCycle.END)) {

                // Update the block.
                this.update(event.getEvoModel());
                event.setChangeAspect(ChangeAspect.TERMINATED);
            }
        }
    }

    /**
     * Handles the specified clientEvent for a given BCIBlockInstance.
     * Updates the related BCI Entities according to the clientEvent and checks entry/exit conditions when needed.
     * May Publish an event to other services in order to correctly update its related BCI entities.
     *
     * @param event The clientEvent indicating the action the client wishes to perform.
     * @throws IllegalArgumentException if the request does not contain every required field to handle the clientEvent.
     */
    @EventListener(BCIBlockInstanceClientEvent.class)
    public void handleClientEvent(BCIBlockInstanceClientEvent event) {
        BehaviorChangeInterventionBlockInstance blockInstance = null;
        BehaviorChangeInterventionBlockInstance updated = null;
        String failedEntryConditions = "";
        String failedExitConditions = "";
        boolean wasUpdated = false;

        if (event != null && event.getActivityInstance() != null && event.getClientEvent() != null && event.getBciBlockInstanceId() != null
                && event.getResponse() != null) {
            ClientEventResponse response = event.getResponse();
            blockInstance = findById(event.getBciBlockInstanceId());

            if (blockInstance != null) {
                //Handle ClientEvents
                BCIActivityInstance bciActivityInstance = event.getActivityInstance();
                ClientEvent clientEvent = event.getClientEvent();

                switch(clientEvent) {
                    case ClientEvent.FINISH -> {
                        if (bciActivityInstance.getStatus() == ExecutionStatus.FINISHED) {
                            failedExitConditions = checkExitConditions(blockInstance);

                            if (failedExitConditions.isEmpty()) {
                                blockInstance.setStatus(ExecutionStatus.FINISHED);
                                blockInstance.setExitDate(LocalDate.now());
                                wasUpdated = true;
                            }
                        }
                    }
                }

                //Update the response with information from BCIBlockInstance
                response.addResponse(BehaviorChangeInterventionBlockInstance.class.getSimpleName(), blockInstance.getId(), blockInstance.getStatus(),
                        failedEntryConditions, failedExitConditions);

                //Update the entity
                if (wasUpdated) {
                    updated = update(blockInstance);
                    if (updated != null) {
                        publishEvent(new BCIPhaseInstanceClientEvent(blockInstance, clientEvent,
                                response, event.getBciPhaseInstanceId(), event.getBciInstanceId()));
                    }
                }
            }
        }
    }

    /**
     * Checks if a BCIBlockInstance has met its entry conditions.
     * @param bciInstance The BCIActivity to retrieve the entry conditions from.
     * @return All the entry conditions that were not met.
     */
    private String checkEntryConditions(BehaviorChangeInterventionBlockInstance bciInstance) {
        return bciInstance.getBehaviorChangeInterventionBlock().getEntryConditions();
    }

    /**
     * Checks if a BCIBlockInstance has met its exit conditions.
     * @param bciInstance The BCIActivity to retrieve the exit conditions from.
     * @return All the exit conditions that were not met.
     */
    private String checkExitConditions(BehaviorChangeInterventionBlockInstance bciInstance) {
        return bciInstance.getBehaviorChangeInterventionBlock().getExitConditions();
    }
}
