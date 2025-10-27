package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ChangeAspect;
import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.event.*;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionBlockInstanceRepository;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.util.FailedConditions;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import ca.uqam.latece.evo.server.core.util.StringToLambdaConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BehaviorChangeInterventionBlockInstance Service.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class BehaviorChangeInterventionBlockInstanceService extends AbstractBCIInstanceService<BehaviorChangeInterventionBlockInstance, BCIBlockInstanceClientEvent> {
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
        return this.bciBlockInstanceRepository.findById(id).orElse(null);
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
    @Override
    @EventListener(BCIBlockInstanceClientEvent.class)
    public ClientEventResponse handleClientEvent(BCIBlockInstanceClientEvent event) {
        BehaviorChangeInterventionBlockInstance blockInstance = null;
        BehaviorChangeInterventionBlockInstance updated = null;
        ClientEventResponse response = null;
        ClientEvent clientEvent = null;
        FailedConditions failedConditions = new FailedConditions();
        boolean wasUpdated = false;

        if (event != null && event.getClientEvent() != null && event.getBciBlockInstanceId() != null
                && event.getResponse() != null) {
            response = event.getResponse();
            blockInstance = findById(event.getBciBlockInstanceId());

            if (blockInstance != null) {
                switch(event.getClientEvent()) {
                    case FINISH ->  {
                        checkEntryConditionsForBCIActivitiesInBlock(event.getClientEvent(), response, blockInstance);
                        wasUpdated = super.handleClientEventFinish(blockInstance, failedConditions);
                    }

                    case IN_PROGRESS -> {
                        if (!event.getBciBlockInstanceId().equals(event.getEntryConditionEvent().getNewBCIBlockInstanceId())) {
                            wasUpdated = super.handleClientEventInProgress(blockInstance);
                        }
                    }
                }

                //Update the response with information from BCIBlockInstance
                response.addResponse(BehaviorChangeInterventionBlockInstance.class.getSimpleName(), blockInstance.getId(), blockInstance.getStatus(),
                        failedConditions.getFailedEntryConditions(), failedConditions.getFailedExitConditions());

                //Update the entity
                if (wasUpdated) {
                    updated = update(blockInstance);
                    if (updated != null) {
                        BCIPhaseInstanceClientEvent phaseInstanceClientEvent = new BCIPhaseInstanceClientEvent(event.getClientEvent(),
                                response, event.getBciPhaseInstanceId(), event.getBciInstanceId(), updated);
                        if (event.getEntryConditionEvent() != null) {
                            phaseInstanceClientEvent.setEntryConditionEvent(event.getEntryConditionEvent());
                        }
                        publishEvent(phaseInstanceClientEvent);
                    }
                }
            }
        }

        return response;
    }

    /**
     * Checks entry conditions for an ActivityInstance and its related entities. When all the entry conditions are satisfied,
     * the execution status of the ActivityInstance is set to IN_PROGRESS and is updated in the database. If some, or all,
     * entry conditions are not satisfied, then the event is updated to reflect those failures and the ActivityInstance is not updated.
     * In either case, a response in JSON is also generated in the event.
     * @param event the event containing information necessary to check all the entry conditions
     */
    @EventListener(BCIBlockInstanceCheckEntryConditionsClientEvent.class)
    protected void checkAllEntryConditions(BCIBlockInstanceCheckEntryConditionsClientEvent event) {
        BCIActivityCheckEntryConditionsClientEvent entryConditionClientEvent = event.getEntryConditionClientEvent();
        BCIPhaseInstanceCheckEntryConditionsClientEvent newEvent = new BCIPhaseInstanceCheckEntryConditionsClientEvent(entryConditionClientEvent);
        FailedConditions failedConditions = new FailedConditions();

        if (!entryConditionClientEvent.getBCIBlockInstanceId().equals(entryConditionClientEvent.getNewBCIBlockInstanceId())) {
            BehaviorChangeInterventionBlockInstance newBlockInstance = findById(entryConditionClientEvent.getNewBCIBlockInstanceId());

            if (newBlockInstance != null) {
                failedConditions.setFailedEntryConditions(checkEntryConditions(newBlockInstance));
                if (failedConditions.getFailedEntryConditions().isEmpty()) {
                    entryConditionClientEvent.setNewBCIBlockInstance(newBlockInstance);

                    //Check entry conditions of new phase the new activity if is a different phase
                    if (!entryConditionClientEvent.getBCIPhaseInstanceId().equals(entryConditionClientEvent.getNewBCIPhaseInstanceId())) {
                        super.publishEvent(newEvent);
                    }

                    //If all entry condition of all related entities pass, then newEvent.NoFailedEntryConditions will be true
                    if (entryConditionClientEvent.isNoFailedEntryConditions()) {
                        newBlockInstance.setStatus(ExecutionStatus.IN_PROGRESS);
                        update(newBlockInstance);
                    }

                } else {
                    entryConditionClientEvent.setNoFailedEntryConditions(false);
                }

                entryConditionClientEvent.getResponse().addResponse("new" + BehaviorChangeInterventionBlockInstance.class.getSimpleName(),
                        newBlockInstance.getId(), newBlockInstance.getStatus(), failedConditions.getFailedEntryConditions(),
                        failedConditions.getFailedExitConditions());
            }
        }
    }

    /**
     * Checks the entry conditions of all BCIActivities inside a block by publishing an event to BCIActivityService.
     * those events are published to any activity with the status of READY. If all the entry conditions of those activities,
     * pass, their status is set to IN_PROGRESS and are updated in the database.
     * @param clientEvent the ClientEvent.
     * @param response a response object containing information on failed conditions and newly updated activities in JSON.
     * @param blockInstance the block with the aggregate of bciActivities
     */
    private void checkEntryConditionsForBCIActivitiesInBlock(ClientEvent clientEvent, ClientEventResponse response,
                                                      BehaviorChangeInterventionBlockInstance blockInstance) {
        for (BCIActivityInstance activityInstance : blockInstance.getActivities()) {
            if (activityInstance.getStatus() == ExecutionStatus.READY) {
                publishEvent(new BCIBlockInstanceToActivityCheckEntryConditionsClientEvent<>(clientEvent, response, activityInstance));
            }
        }
    }

    /**
     * Event listener for changes related to blocks and originating from BCIPhaseInstance.
     * Helps with updating relevant entities when a block is FINISHED
     * @param event
     */
    @EventListener(BCIPhaseInstanceToBlockCheckEntryConditionClientEvent.class)
    protected void checkEntryConditionsForBCIActivitiesInBlock(BCIPhaseInstanceToBlockCheckEntryConditionClientEvent event) {
        event.getBlockInstance().setStatus(ExecutionStatus.IN_PROGRESS);
        if (update(event.getBlockInstance()) != null) {
            checkEntryConditionsForBCIActivitiesInBlock(event.getClientEvent(), event.getResponse(), event.getBlockInstance());
        }
    }

    /**
     * Checks if a BCIBlockInstance has met its entry conditions.
     * @param bciInstance The BCIActivity to retrieve the entry conditions from.
     * @return All the entry conditions that were not met.
     */
    @Override
    protected String checkEntryConditions(BehaviorChangeInterventionBlockInstance bciInstance) {
        String condition = bciInstance.getBehaviorChangeInterventionBlock().getEntryConditions();

        if (StringToLambdaConverter.convertConditionStringToLambda(condition)) {
            condition = "";
        }

        return condition;
    }

    /**
     * Checks if a BCIBlockInstance has met its exit conditions.
     * @param bciInstance The BCIActivity to retrieve the exit conditions from.
     * @return All the exit conditions that were not met.
     */
    @Override
    protected String checkExitConditions(BehaviorChangeInterventionBlockInstance bciInstance) {
        String condition = bciInstance.getBehaviorChangeInterventionBlock().getExitConditions();

        if (StringToLambdaConverter.convertConditionStringToLambda(condition)) {
            condition = "";
        }

        return condition;
    }
}
