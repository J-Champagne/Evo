package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ChangeAspect;
import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.event.*;
import ca.uqam.latece.evo.server.core.model.instance.BCIModuleInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionPhaseInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BehaviorChangeInterventionPhaseInstanceRepository;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.util.FailedConditions;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import ca.uqam.latece.evo.server.core.util.StringToLambdaConverter;
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
 * BehaviorChangeInterventionPhaseInstance Service.
 * @version 1.0
 * @author Julien Champagne.
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class BehaviorChangeInterventionPhaseInstanceService extends AbstractBCIInstanceService<BehaviorChangeInterventionPhaseInstance, BCIPhaseInstanceClientEvent> {
    private static final Logger logger = LoggerFactory.getLogger(BehaviorChangeInterventionPhaseInstanceService.class);

    @Autowired
    private BehaviorChangeInterventionPhaseInstanceRepository bciPhaseInstanceRepository;

    /**
     * Creates a BehaviorChangeInterventionPhaseInstance in the database.
     * @param phaseInstance BehaviorChangeInterventionPhaseInstance.
     * @return The created BehaviorChangeInterventionPhaseInstance.
     * @throws IllegalArgumentException if phaseInstance is null.
     */
    @Override
    public BehaviorChangeInterventionPhaseInstance create(BehaviorChangeInterventionPhaseInstance phaseInstance) {
        BehaviorChangeInterventionPhaseInstance saved = null;

        ObjectValidator.validateObject(phaseInstance);
        ObjectValidator.validateObject(phaseInstance.getActivities());
        ObjectValidator.validateObject(phaseInstance.getModules());

        saved = this.bciPhaseInstanceRepository.save(phaseInstance);
        logger.info("BehaviorChangeInterventionPhaseInstance created: {}", saved);
        return saved;
    }

    /**
     * Updates a BehaviorChangeInterventionPhaseInstance in the database.
     * @param phaseInstance BehaviorChangeInterventionPhaseInstance.
     * @return The updated BehaviorChangeInterventionPhaseInstance.
     * @throws IllegalArgumentException if phaseInstance is null.
     */
    @Override
    public BehaviorChangeInterventionPhaseInstance update(BehaviorChangeInterventionPhaseInstance phaseInstance) {
        BehaviorChangeInterventionPhaseInstance updated = null;
        BehaviorChangeInterventionPhaseInstance found = findById(phaseInstance.getId());

        ObjectValidator.validateObject(phaseInstance);
        ObjectValidator.validateObject(phaseInstance.getActivities());
        ObjectValidator.validateObject(phaseInstance.getModules());

        if (found != null) {
            updated = this.bciPhaseInstanceRepository.save(phaseInstance);
        }
        return updated;
    }

    /**
     * Change the current block of a given BehaviorChangeInterventionPhaseInstance.
     * @param phaseInstance the instance of BehaviorChangeInterventionPhaseInstance to update
     * @return the updated BehaviorChangeInterventionPhaseInstance with its current block updated
     */
    public BehaviorChangeInterventionPhaseInstance changeCurrentBlock(BehaviorChangeInterventionPhaseInstance phaseInstance) {
        return this.changeCurrentBlock(phaseInstance.getId(), phaseInstance.getCurrentBlock());
    }

    /**
     * Change the current block of a given BehaviorChangeInterventionPhaseInstance. If the current block is different
     * from the previously set block, the previous block's status, stage, and exit date are updated appropriately. This
     * method ensures the provided phase instance id and block instance are validated and updates the state and associations
     * of the given phase instance accordingly.
     * @param phaseId the BehaviorChangeInterventionPhaseInstance id to be updated.
     * @param currentBlock the BehaviorChangeInterventionBlockInstance to be set as the current block in the phase instance.
     * @return the updated BehaviorChangeInterventionPhaseInstance, or null if the phase instance was not found.
     */
    public BehaviorChangeInterventionPhaseInstance changeCurrentBlock(Long phaseId,
                                                                      BehaviorChangeInterventionBlockInstance currentBlock) {
        BehaviorChangeInterventionPhaseInstance updated = null;
        BehaviorChangeInterventionPhaseInstance found = this.findById(phaseId);

        ObjectValidator.validateObject(phaseId);
        ObjectValidator.validateObject(currentBlock);
        ObjectValidator.validateId(currentBlock.getId());

        if (found != null) {
            // If the current block is different, update the block status to Finished, change the stage to End, and
            // set the Exit date.
            if (found.getCurrentBlock() != null && !found.getCurrentBlock().getId().equals(currentBlock.getId())) {
                // Get the current block.
                BehaviorChangeInterventionBlockInstance block = found.getCurrentBlock();

                // Change the status of the current block.
                block.setStatus(ExecutionStatus.FINISHED);
                block.setStage(TimeCycle.END);
                block.setExitDate(LocalDate.now());

                // Publish the current block updated.
                this.publishEvent(new BCIBlockInstanceEvent(block), block.getStage());
            }

            // Set the new the current block properties.
            currentBlock.setStatus(ExecutionStatus.IN_PROGRESS);
            currentBlock.setStage(TimeCycle.BEGINNING);
            currentBlock.setEntryDate(LocalDate.now());
            // Set the current block in the Phase.
            found.setCurrentBlock(currentBlock);
            // Update the BehaviorChangeInterventionPhaseInstance in the database.
            updated = this.update(found);
        }

        return updated;
    }

    /**
     * Changes the status of a specified module instance to "IN_PROGRESS" within a given phase instance.
     * @param phaseId the behavior change intervention phase instance id that contains the module.
     * @param moduleInstance the module instance whose status needs to be updated.
     * @return the updated behavior change intervention phase instance after the module status change.
     */
    public BehaviorChangeInterventionPhaseInstance changeModuleStatusToInProgress(
            Long phaseId, BCIModuleInstance moduleInstance){
        return this.changeModuleStatus(phaseId, moduleInstance, ExecutionStatus.IN_PROGRESS, TimeCycle.BEGINNING);
    }

    /**
     * Changes the status of a specified module instance to 'FINISHED' and sets its time cycle to 'END' within the given
     * behavior change intervention phase instance.
     * @param phaseId the behavior change intervention phase instance id containing the module to update.
     * @param moduleInstance the module instance whose status is to be set to 'FINISHED'.
     * @return the updated behavior change intervention phase instance reflecting the change in module status.
     */
    public BehaviorChangeInterventionPhaseInstance changeModuleStatusToFinished(
            Long phaseId, BCIModuleInstance moduleInstance){
        return this.changeModuleStatus(phaseId, moduleInstance, ExecutionStatus.FINISHED, TimeCycle.END);
    }

    /**
     * Updates the status of a module in the specified BehaviorChangeInterventionPhaseInstance. Validates the input module
     * instance and its ID before updating. Publishes an event related to the module status change and updates the phase
     * instance.
     * @param phaseId the BehaviorChangeInterventionPhaseInstance id where the module's status needs to be updated.
     * @param moduleInstance the module whose status is to be changed.
     * @param status the new ExecutionStatus to be assigned to the module.
     * @param timeCycle the time cycle associated with the module status update for event publishing.
     * @return the updated BehaviorChangeInterventionPhaseInstance after modifying the module's status, or null if the
     * specified phase instance is not found
     */
    private BehaviorChangeInterventionPhaseInstance changeModuleStatus(Long phaseId,
                                                                       BCIModuleInstance moduleInstance, ExecutionStatus status,
                                                                       TimeCycle timeCycle) {
        BehaviorChangeInterventionPhaseInstance updated = null;
        BehaviorChangeInterventionPhaseInstance found = this.findById(phaseId);
        ObjectValidator.validateObject(moduleInstance);
        ObjectValidator.validateId(moduleInstance.getId());

        if (found != null) {
            // Get the selected module.
            List<BCIModuleInstance> modules = found.getModules().stream().filter(
                    m -> m.getId().equals(moduleInstance.getId())).toList();

            if (!modules.isEmpty()){
                for (BCIModuleInstance module : modules) {
                    if (module != null) {
                        if (moduleInstance.getId().equals(module.getId())) {
                            // Update the module status.
                            module.setStatus(status);
                            // Publish the module updates.
                            this.publishEvent(new BCIModuleInstanceEvent(module), timeCycle);
                            break;
                        }
                    }
                }
            }

            // Update the BehaviorChangeInterventionPhaseInstance.
            updated = this.update(found);
        }

        return updated;
    }

    /**
     * Saves the given BehaviorChangeInterventionPhaseInstance in the database.
     * @param phaseInstance BehaviorChangeInterventionPhaseInstance.
     * @return The saved BehaviorChangeInterventionPhaseInstance.
     * @throws IllegalArgumentException if phaseInstance is null.
     */
    @Override
    public BehaviorChangeInterventionPhaseInstance save(BehaviorChangeInterventionPhaseInstance phaseInstance) {
        return this.bciPhaseInstanceRepository.save(phaseInstance);
    }

    /**
     * Deletes a BehaviorChangeInterventionPhaseInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        bciPhaseInstanceRepository.deleteById(id);
        logger.info("BehaviorChangeInterventionPhaseInstance deleted {}", id);
    }

    /**
     * Finds all BehaviorChangeInterventionPhaseInstance entities.
     * @return List<BehaviorChangeInterventionPhaseInstance>.
     */
    public List<BehaviorChangeInterventionPhaseInstance> findAll() {
        return this.bciPhaseInstanceRepository.findAll();
    }

    /**
     * Finds a BehaviorChangeInterventionPhaseInstance by its id.
     * @param id Long.
     * @return BehaviorChangeInterventionPhaseInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public BehaviorChangeInterventionPhaseInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciPhaseInstanceRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("BehaviorChangeInterventionPhaseInstance not found"));
    }

    /**
     * Finds BehaviorChangeInterventionPhaseInstance entities by their currentBlock id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionPhaseInstance> with the given currentBlock id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionPhaseInstance> findByCurrentBlockId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciPhaseInstanceRepository.findByCurrentBlockId(id);
    }

    /**
     * Finds BehaviorChangeInterventionPhaseInstance entities by a BehaviorChangeInterventionBlockInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionPhaseInstance> with the given BCIBlocksInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionPhaseInstance> findByActivitiesId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciPhaseInstanceRepository.findByActivitiesId(id);
    }

    /**
     * Finds BehaviorChangeInterventionPhaseInstance entities by a BCIModuleInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionPhaseInstance> with the given BCIModuleInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<BehaviorChangeInterventionPhaseInstance> findByModulesId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciPhaseInstanceRepository.findByModulesId(id);
    }

    /**
     * Checks if a BehaviorChangeInterventionPhaseInstance exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciPhaseInstanceRepository.existsById(id);
    }

    /**
     * Finds a BehaviorChangeInterventionPhaseInstance entity by its unique id and the id of its current block.
     * @param id the unique identifier of the BehaviorChangeInterventionPhaseInstance.
     * @param currentBlockId the unique identifier of the current block associated with the phase instance.
     * @return the BehaviorChangeInterventionPhaseInstance entity that matches both the id and currentBlockId, or null
     *         if no such entity exists.
     * @throws IllegalArgumentException if id or currentBlockId is null.
     */
    public BehaviorChangeInterventionPhaseInstance findByIdAndCurrentBlockId(Long id, Long currentBlockId) {
        ObjectValidator.validateId(id);
        ObjectValidator.validateId(currentBlockId);
        return this.bciPhaseInstanceRepository.findByIdAndCurrentBlockId(id, currentBlockId);
    }

    /**
     * Finds and retrieves a list of BehaviorChangeInterventionPhaseInstance entities
     * associated with the specified behavior change intervention phase ID.
     *
     * @param id the ID of the behavior change intervention phase to find associated instances for;
     *           must not be null or invalid.
     * @return a list of BehaviorChangeInterventionPhaseInstance entities associated with the given ID.
     *         Returns an empty list if no instances are found.
     * @throws IllegalArgumentException if the id is null.
     */
    public List<BehaviorChangeInterventionPhaseInstance> findByBehaviorChangeInterventionPhaseId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciPhaseInstanceRepository.findByBehaviorChangeInterventionPhaseId(id);
    }

    /**
     * Handles events related to BCI Phase Instance. This method listens for BCIPhaseInstanceEvent and processes it based
     * on its change aspects, execution status, and time cycle.
     * @param event the BCIPhaseInstanceEvent to process. It contains details about the change aspect, execution status,
     *              evolutionary model, and time cycle.
     */
    @EventListener(BCIPhaseInstanceEvent.class)
    public void handleBCIPhaseInstanceEvents(BCIPhaseInstanceEvent event) {
        if (event.getChangeAspect().equals(ChangeAspect.STARTED) &&
                event.getEvoModel().getStatus().equals(ExecutionStatus.FINISHED) &&
                event.getTimeCycle().equals(TimeCycle.END)) {
            this.update(event.getEvoModel());
            event.setChangeAspect(ChangeAspect.TERMINATED);
        }
    }

    /**
     * Handles BCIPhaseInstanceClientEvent by updating the corresponding BehaviorChangeInterventionPhaseInstance
     * when specific conditions related to its execution status are met.
     * @param event the BCIPhaseInstanceClientEvent to be processed, which contains information about the
     *              BCIPhaseInstance and its state changes.
     */
    @Override
    @EventListener(BCIPhaseInstanceClientEvent.class)
    public ClientEventResponse handleClientEvent(BCIPhaseInstanceClientEvent event) {
        BehaviorChangeInterventionPhaseInstance phaseInstance = null;
        BehaviorChangeInterventionBlockInstance blockInstance = null;
        BehaviorChangeInterventionPhaseInstance updated = null;
        ClientEventResponse response = null;
        FailedConditions failedConditions = new FailedConditions();
        boolean statusUpdated = false;
        boolean blockUpdated = false;

        if (event != null && event.getClientEvent() != null && event.getBCIBlockInstance() != null && event.getBciPhaseInstanceId() != null
                && event.getResponse() != null) {
            response = event.getResponse();
            blockInstance = event.getBCIBlockInstance();
            phaseInstance = findById(event.getBciPhaseInstanceId());

            if (phaseInstance != null) {
                //Handle ClientEvents
                switch (event.getClientEvent()) {
                    case ClientEvent.FINISH -> {
                            statusUpdated = super.handleClientEventFinish(phaseInstance, failedConditions);
                            blockUpdated = setNextCurrentBlock(phaseInstance, blockInstance, phaseInstance.getActivities());
                            if (blockUpdated) {
                                checkEntryConditionsForBlockInPhase(event.getClientEvent(), response, phaseInstance);
                            }
                    }

                    case IN_PROGRESS -> {
                        if (!event.getBciPhaseInstanceId().equals(event.getEntryConditionEvent().getNewBCIPhaseInstanceId())) {
                            statusUpdated = super.handleClientEventInProgress(phaseInstance);

                        } else if (event.getEntryConditionEvent().getNewBCIBlockInstance() != null) {
                            phaseInstance.setCurrentBlock(event.getEntryConditionEvent().getNewBCIBlockInstance());
                            blockUpdated = true;
                        }
                    }
                }

                //Update the response with information from BCIPhaseInstance
                event.getResponse().addResponse(BehaviorChangeInterventionPhaseInstance.class.getSimpleName(), phaseInstance.getId(),
                         phaseInstance.getStatus(), failedConditions.getFailedEntryConditions(), failedConditions.getFailedExitConditions());
            }
        }

        //Update the entity
        if (statusUpdated || blockUpdated) {
            updated = this.update(phaseInstance);
            if (updated != null) {
                BCIInstanceClientEvent bciInstanceClientEvent = new BCIInstanceClientEvent(event.getClientEvent(), response,
                        event.getBciInstanceId(), updated);
                if (event.getEntryConditionEvent() != null) {
                    bciInstanceClientEvent.setEntryConditionEvent(event.getEntryConditionEvent());
                }
                this.publishEvent(bciInstanceClientEvent);
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
    @EventListener(BCIPhaseInstanceCheckEntryConditionsClientEvent.class)
    protected void checkAllEntryConditions(BCIPhaseInstanceCheckEntryConditionsClientEvent event) {
        BCIActivityCheckEntryConditionsClientEvent entryConditionClientEvent = event.getEntryConditionClientEvent();
        FailedConditions failedConditions = new FailedConditions();

        if (!entryConditionClientEvent.getBCIPhaseInstanceId().equals(entryConditionClientEvent.getNewBCIPhaseInstanceId())) {
            BehaviorChangeInterventionPhaseInstance newPhaseInstance = findById(entryConditionClientEvent.getNewBCIPhaseInstanceId());

            if (newPhaseInstance != null) {
                failedConditions.setFailedEntryConditions(checkEntryConditions(newPhaseInstance));

                if (failedConditions.getFailedEntryConditions().isEmpty()) {
                    entryConditionClientEvent.setNewBCIPhaseInstance(newPhaseInstance);
                    newPhaseInstance.setStatus(ExecutionStatus.IN_PROGRESS);
                    newPhaseInstance.setCurrentBlock(entryConditionClientEvent.getNewBCIBlockInstance());
                    update(newPhaseInstance);

                } else {
                    entryConditionClientEvent.setNoFailedEntryConditions(false);
                }

                entryConditionClientEvent.getResponse().addResponse("new" + BehaviorChangeInterventionBlockInstance.class.getSimpleName(),
                        newPhaseInstance.getId(), newPhaseInstance.getStatus(), failedConditions.getFailedEntryConditions(),
                        failedConditions.getFailedExitConditions());
            }
        }
    }

    /**
     *
     * @param clientEvent
     * @param response
     * @param newPhaseInstance
     */
    protected void checkEntryConditionsForBlockInPhase(ClientEvent clientEvent, ClientEventResponse response,
                                                BehaviorChangeInterventionPhaseInstance newPhaseInstance) {
        //Check entry conditions for Blocks was omitted because activities in phase are assumed to be sequential
        publishEvent(new BCIPhaseInstanceToBlockCheckEntryConditionClientEvent(clientEvent, response,
                        newPhaseInstance.getCurrentBlock()));
    }

    /**
     * Event listener for changes related to phases and originating from BCIInstance.
     * Helps with updating relevant entities when a phase is FINISHED
     * @param event
     */
    @EventListener(BCIInstanceToPhaseCheckEntryConditionsClientEvent.class)
    protected void checkEntryConditionsForBlockInPhase(BCIInstanceToPhaseCheckEntryConditionsClientEvent event){
        checkEntryConditionsForBlockInPhase(event.getClientEvent(), event.getResponse(), event.getPhaseInstance());
    }

    /**
     * Checks if a BCIPhaseInstance has met its entry conditions.
     * @param bciInstance The BCIActivity to retrieve the entry conditions from.
     * @return All the entry conditions that were not met.
     */
    @Override
    public String checkEntryConditions(BehaviorChangeInterventionPhaseInstance bciInstance) {
        String condition = bciInstance.getBehaviorChangeInterventionPhase().getEntryConditions();

        if (StringToLambdaConverter.convertConditionStringToLambda(condition)) {
            condition = "";
        }

        return condition;
    }

    /**
     * Checks if a BCIPhaseInstance has met its exit conditions.
     * @param bciInstance The BCIActivity to retrieve the exit conditions from.
     * @return All the exit conditions that were not met.
     */
    @Override
    public  String checkExitConditions(BehaviorChangeInterventionPhaseInstance bciInstance) {
        String condition = bciInstance.getBehaviorChangeInterventionPhase().getExitConditions();

        if (StringToLambdaConverter.convertConditionStringToLambda(condition)) {
            condition = "";
        }

        return condition;
    }

    /**
     * Updates the currentBlock with the next one present in the list of activities of a BehaviorChangeInterventionPhaseInstance.
     * The currentBlock will not be updated if currentBlock is the last one present in the activities of the BehaviorChangeInterventionPhaseInstance.
     * @param phaseInstance the BehaviorChangeInterventionPhaseInstance to be updated
     * @param blockInstance the new currentBlock
     * @param activities the list of BCIBlockInstance of phaseInstance
     * @return true if the currentBlock was updated
     */
    private boolean setNextCurrentBlock(BehaviorChangeInterventionPhaseInstance phaseInstance, BehaviorChangeInterventionBlockInstance blockInstance,
                                            List<BehaviorChangeInterventionBlockInstance> activities) {
        boolean blockUpdated = false;

        for (int i = 0; i < activities.size(); i++) {
            BehaviorChangeInterventionBlockInstance activity = activities.get(i);

            if (activity.getId().equals(blockInstance.getId())) {
                int nextIndex = i + 1;
                if (nextIndex < activities.size()) {
                    BehaviorChangeInterventionBlockInstance newBlockInstance = activities.get(nextIndex);
                    newBlockInstance.setStatus(ExecutionStatus.IN_PROGRESS);
                    phaseInstance.setCurrentBlock(newBlockInstance);
                    blockUpdated = true;
                }

                break;
            }
        }

        return blockUpdated;
    }
}
