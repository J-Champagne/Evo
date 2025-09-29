package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.event.BCIActivityClientEvent;
import ca.uqam.latece.evo.server.core.event.BCIBlockInstanceClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.InteractionInstance;
import ca.uqam.latece.evo.server.core.repository.instance.InteractionInstanceRepository;
import ca.uqam.latece.evo.server.core.request.BCIActivityInstanceRequest;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.util.FailedConditions;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * InteractionInstance Service.
 * @version 1.0
 * @author Julien Champagne.
 */
@Service
@Transactional
public class InteractionInstanceService extends AbstractBCIInstanceService<InteractionInstance, BCIActivityClientEvent> {
    private static final Logger logger = LoggerFactory.getLogger(InteractionInstanceService.class);

    @Autowired
    InteractionInstanceRepository interactionInstanceRepository;

    /**
     * Creates an InteractionInstance in the database.
     * @param interactionInstance InteractionInstance.
     * @return The created InteractionInstance.
     * @throws IllegalArgumentException if interactionInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public InteractionInstance create(InteractionInstance interactionInstance) {
        InteractionInstance interactionInstanceCreated;

        ObjectValidator.validateObject(interactionInstance);
        interactionInstanceCreated = interactionInstanceRepository.save(interactionInstance);

        logger.info("InteractionInstance created: {}", interactionInstanceCreated);
        return interactionInstanceCreated;
    }

    /**
     * Updates a InteractionInstance in the database.
     * @param interactionInstance InteractionInstance.
     * @return The updated InteractionInstance.
     * @throws IllegalArgumentException if interactionInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    public InteractionInstance update(InteractionInstance interactionInstance) {
        InteractionInstance interactionInstanceUpdated = null;
        InteractionInstance InteractionInstanceFound = findById(interactionInstance.getId());

        ObjectValidator.validateObject(interactionInstance);
        if (InteractionInstanceFound != null) {
            interactionInstanceUpdated = interactionInstanceRepository.save(interactionInstance);
        }

        return interactionInstanceUpdated;
    }

    /**
     * Saves the given InteractionInstance in the database.
     * @param interactionInstance InteractionInstance.
     * @return The saved InteractionInstance.
     * @throws IllegalArgumentException if interactionInstance is null.
     * @throws OptimisticLockingFailureException when optimistic locking is used and has information with
     *          different values from the database. Also thrown if assumed to be present but does not exist in the database.
     */
    @Override
    @Transactional
    public InteractionInstance save(InteractionInstance interactionInstance) {
        return this.interactionInstanceRepository.save(interactionInstance);
    }

    /**
     * Deletes a InteractionInstance by its id.
     * Silently ignored if not found.
     * @param id Long.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        this.interactionInstanceRepository.deleteById(id);
        logger.info("InteractionInstance deleted {}", id);
    }

    /**
     * Finds all InteractionInstance entities.
     * @return List<InteractionInstance>.
     */
    @Override
    public List<InteractionInstance> findAll() {
        return this.interactionInstanceRepository.findAll();
    }

    /**
     * Finds an InteractionInstance by its id.
     * @param id Long.
     * @return the InteractionInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public InteractionInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.interactionInstanceRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("InteractionInstance not found"));
    }

    /**
     * Finds InteractionInstance entities by their status.
     * @param status ExecutionStatus representing the status of the InteractionInstance.
     * @return List of InteractionInstance entities with the given status.
     * @throws IllegalArgumentException if the status is null.
     */
    public List<InteractionInstance> findByStatus(ExecutionStatus status) {
        ObjectValidator.validateObject(status);
        return this.interactionInstanceRepository.findByStatus(status);
    }

    /**
     * Finds InteractionInstance entities by their entryDate.
     * @param entryDate LocalDate.
     * @return List<InteractionInstance> with the given entryDate.
     * @throws IllegalArgumentException if entryDate is null.
     */
    public List<InteractionInstance> findByEntryDate(LocalDate entryDate) {
        ObjectValidator.validateObject(entryDate);
        return this.interactionInstanceRepository.findByEntryDate(entryDate);
    }

    /**
     * Finds InteractionInstance entities by their exitDate.
     * @param exitDate LocalDate.
     * @return List<InteractionInstance> with the given exitDate.
     * @throws IllegalArgumentException if exitDate is null.
     */
    public List<InteractionInstance> findByExitDate(LocalDate exitDate) {
        ObjectValidator.validateObject(exitDate);
        return this.interactionInstanceRepository.findByExitDate(exitDate);
    }

    /**
     * Retrieves a list of InteractionInstance entities associated with a specific participant ID.
     * @param id the ID of the participant to find associated activity instances for. Must not be null.
     * @return a list of InteractionInstance entities associated with the given participant ID.
     * @throws IllegalArgumentException if the provided ID is null or invalid.
     */
    public List<InteractionInstance> findByParticipantsId(Long id) {
        ObjectValidator.validateId(id);
        return this.interactionInstanceRepository.findByParticipantsId(id);
    }

    /**
     * Checks if a InteractionInstance exists in the database by its id
     * @param id Long
     * @return boolean
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.interactionInstanceRepository.existsById(id);
    }

    /**
     * Handles the specified clientEvent for a given InteractionInstance.
     * Updates the related BCI Entities according to the clientEvent and checks entry/exit conditions when needed.
     * May Publish an event to other services in order to correctly update its related BCI entities.
     *
     * @param event The clientEvent indicating the action the client wishes to perform.
     * @return a ClientEventResponse with information on the success of the request as well as the updated entities in JSON format.
     * @throws IllegalArgumentException if the request does not contain every required field to handle the clientEvent.
     */
    @Override
    public ClientEventResponse handleClientEvent(BCIActivityClientEvent event) {
        InteractionInstance found = null;
        InteractionInstance updated = null;
        ClientEventResponse response = null;
        FailedConditions failedConditions = new FailedConditions();
        boolean wasUpdated = false;

        if (event != null && event.getClientEvent() != null && event.getBciInstanceId() != null && event.getResponse() != null) {
            found = findById(event.getBciActivityInstanceId());
            response = event.getResponse();

            if (found != null) {
                //Handle ClientEvents
                switch (event.getClientEvent()) {
                    case FINISH -> wasUpdated = super.handleClientEventFinish(found, failedConditions);
                    case IN_PROGRESS -> wasUpdated = handleClientEventInProgress(found, event.getNewActivityInstanceId(), response);
                }

                //Update the response with information from BCIActivityInstance
                response.addResponse(InteractionInstance.class.getSimpleName(), found.getId(), found.getStatus(),
                        failedConditions.getFailedEntryConditions(), failedConditions.getFailedExitConditions());
            }

            //Update the entity
            if (wasUpdated) {
                updated = this.update(found);

                if (updated != null) {
                    //Blocking, will wait until all listeners are triggered and processed
                    super.publishEvent(new BCIBlockInstanceClientEvent<>(updated, event.getClientEvent(), response, event.getBciBlockInstanceId(),
                            event.getBciPhaseInstanceId(), event.getBciInstanceId(), event.getNewBlockInstanceId(), event.getNewPhaseInstanceId()));
                    response.setSuccess(true);
                }
            }
        }

        return response;
    }

    /**
     * Handles a ClientEvent IN_PROGRESS by updating the corresponding ActivityInstances when specific conditions
     * related to its entry conditions are met. If the entry conditions are met, the oldActivityInstance will be set to
     * SUSPENDED while the newActivityInstance will be set to IN_PROGRESS.
     * @param oldActivityInstance the ActivityInstance no longer being progressed.
     * @param newActivityInstanceId the id of the ActivityInstance that will be progressed.
     * @param response a response object containing information on the updated entities in JSON format.
     * @return true if the new activityInstances was updated.
     */
    public boolean handleClientEventInProgress(InteractionInstance oldActivityInstance, Long newActivityInstanceId,
                                               ClientEventResponse response) {
        FailedConditions failedConditions = new FailedConditions();
        boolean wasUpdated = false;

        if (!oldActivityInstance.getId().equals(newActivityInstanceId)) {
            InteractionInstance newActivityInstance = findById(newActivityInstanceId);

            if (newActivityInstance != null) {
                failedConditions.setFailedEntryConditions(checkEntryConditions(newActivityInstance));

                if (failedConditions.getFailedEntryConditions().isEmpty()) {
                    newActivityInstance.setStatus(ExecutionStatus.IN_PROGRESS);
                    oldActivityInstance.setStatus(ExecutionStatus.SUSPENDED);
                    wasUpdated = update(newActivityInstance) != null;
                }

                response.addResponse(InteractionInstance.class.getSimpleName(), newActivityInstance.getId(),
                        newActivityInstance.getStatus(), failedConditions.getFailedEntryConditions(),
                        failedConditions.getFailedExitConditions());
            }
        }

        return wasUpdated;
    }

    /**
     * Checks if a BCIActivityInstance has met its entry conditions.
     * @param bciInstance The BCIActivity to retrieve the entry conditions from.
     * @return All the entry conditions that were not met.
     */
    @Override
    public String checkEntryConditions(InteractionInstance bciInstance) {
        return bciInstance.getBciActivity().getPreconditions();
    }

    /**
     * Checks if a BCIActivityInstance has met its exit conditions.
     * @param bciInstance The BCIActivity to retrieve the exit conditions from.
     * @return All the exit conditions that were not met.
     */
    @Override
    public String checkExitConditions(InteractionInstance bciInstance) {
        return bciInstance.getBciActivity().getPostconditions();
    }

    /**
     * Validates the required information found in a BCIActivityInstanceRequest for a clientEvent.
     * @param clientEvent The client event
     * @param request The BCIActivityRequest
     * @throws IllegalArgumentException if any of the validation fails to pass.
     */
    public void validateClientEvent(ClientEvent clientEvent, BCIActivityInstanceRequest request) {
        ObjectValidator.validateObject(clientEvent);
        ObjectValidator.validateObject(request);
        ObjectValidator.validateId(request.getId());
        ObjectValidator.validateId(request.getBciBlockInstanceId());
        ObjectValidator.validateId(request.getBciPhaseInstanceId());
        ObjectValidator.validateId(request.getBciInstanceId());
    }
}
