package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.event.BCIBlockInstanceClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.repository.instance.BCIActivityInstanceRepository;
import ca.uqam.latece.evo.server.core.request.BCIActivityInstanceRequest;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * BCIActivity Instance Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 * @author Julien Champagne
 */
@Service
public class BCIActivityInstanceService extends AbstractEvoService<BCIActivityInstance> {
    private static final Logger logger = LoggerFactory.getLogger(BCIActivityInstanceService.class);

    @Autowired
    private BCIActivityInstanceRepository bciActivityInstanceRepository;

    /**
     * Creates a BCIActivityInstance in the database.
     * @param bciActivityInstance BCIActivityInstance.
     * @return The created BCIActivityInstance.
     * @throws IllegalArgumentException if bciActivityInstance is null.
     */
    public BCIActivityInstance create (BCIActivityInstance bciActivityInstance) {
        BCIActivityInstance bciBCIActivityInstance = null;

        ObjectValidator.validateObject(bciActivityInstance);

        bciBCIActivityInstance = this.bciActivityInstanceRepository.save(bciActivityInstance);
        logger.info("BCIActivityInstance created: {}", bciBCIActivityInstance);

        return bciBCIActivityInstance;
    }

    /**
     * Updates a BCIActivityInstance in the database.
     * @param bciActivityInstance BCIActivityInstance.
     * @return The updated BCIActivityInstance.
     * @throws IllegalArgumentException if bciActivityInstance is null.
     */
    public BCIActivityInstance update (BCIActivityInstance bciActivityInstance) {
        BCIActivityInstance bciBCIActivityInstance = null;

        ObjectValidator.validateObject(bciActivityInstance);
        ObjectValidator.validateId(bciActivityInstance.getId());

        bciBCIActivityInstance = this.bciActivityInstanceRepository.save(bciActivityInstance);
        logger.info("BCIActivityInstance updated: {}", bciBCIActivityInstance);

        return bciBCIActivityInstance;
    }

    /**
     * Saves the given BCIActivityInstance in the database.
     * @param bciActivityInstance BCIActivityInstance.
     * @return The saved BCIActivityInstance.
     * @throws IllegalArgumentException if bciActivityInstance is null.
     */
    @Override
    protected BCIActivityInstance save(BCIActivityInstance bciActivityInstance) {
        ObjectValidator.validateObject(bciActivityInstance.getStatus());
        ObjectValidator.validateObject(bciActivityInstance.getEntryDate());
        ObjectValidator.validateObject(bciActivityInstance.getExitDate());
        ObjectValidator.validateObject(bciActivityInstance.getParticipants());
        return bciActivityInstanceRepository.save(bciActivityInstance);
    }

    /**
     * Deletes a BCIActivityInstance by its id.
     * Silently ignored if the instance with the given id is not found.
     * @param id the id of the BCIActivityInstance to delete. Must not be null.
     * @throws IllegalArgumentException if id is null or invalid.
     */
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        this.bciActivityInstanceRepository.deleteById(id);
        logger.info("BCIActivityInstance deleted: {}", id);
    }

    /**
     * Finds a BCIActivityInstance by its id.
     * @param id the id of the BCIActivityInstance to find. Must not be null.
     * @return the BCIActivityInstance with the given id.
     * @throws IllegalArgumentException if id is null.
     * @throws EntityNotFoundException if no BCIActivityInstance is found with the given id.
     */
    public BCIActivityInstance findById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciActivityInstanceRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("BCIActivityInstance not found!"));
    }

    /**
     * Finds BCIActivityInstance entities by their status.
     * @param status the status of the BCIActivityInstance entities to find.
     * @return a list of BCIActivityInstance entities with the given status.
     * @throws IllegalArgumentException if the status is null or invalid.
     */
    public List<BCIActivityInstance> findByStatus(ExecutionStatus status) {
        ObjectValidator.validateObject(status);
        return this.bciActivityInstanceRepository.findByStatus(status);
    }

    /**
     * Fetches a list of BCIActivityInstance entities by their entry date.
     * @param entryDate the entry date of the BCIActivityInstance entities to retrieve. Must not be null.
     * @return a list of BCIActivityInstance entities corresponding to the given entry date.
     * @throws IllegalArgumentException if entryDate is null.
     */
    public List<BCIActivityInstance> findByEntryDate(LocalDate entryDate) {
        ObjectValidator.validateObject(entryDate);
        return this.bciActivityInstanceRepository.findByEntryDate(entryDate);
    }

    /**
     * Finds BCIActivityInstance entities by their exit date.
     * @param exitDate the exit date of the BCIActivityInstance entities to retrieve. Must not be null.
     * @return a list of BCIActivityInstance entities with the specified exit date.
     * @throws IllegalArgumentException if exitDate is null.
     */
    public List<BCIActivityInstance> findByExitDate(LocalDate exitDate) {
        ObjectValidator.validateObject(exitDate);
        return this.bciActivityInstanceRepository.findByEntryDate(exitDate);
    }

    /**
     * Retrieves a list of BCIActivityInstance entities associated with a specific participant ID.
     * @param id the ID of the participant to find associated activity instances for. Must not be null.
     * @return a list of BCIActivityInstance entities associated with the given participant ID.
     * @throws IllegalArgumentException if the provided ID is null or invalid.
     */
    public List<BCIActivityInstance> findByParticipantsId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciActivityInstanceRepository.findByParticipantsId(id);
    }

    /**
     * Checks whether a BCIActivityInstance exists with the given ID.
     * @param id the ID of the BCIActivityInstance to check. Must not be null.
     * @return true if a BCIActivityInstance exists with the given ID, false otherwise.
     * @throws IllegalArgumentException if the ID is null or invalid.
     */
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return this.bciActivityInstanceRepository.existsById(id);
    }

    /**
     * Finds all BCIActivityInstance entities.
     * @return a list of all BCIActivityInstance entities.
     */
    public List<BCIActivityInstance> findAll() {
        return this.bciActivityInstanceRepository.findAll();
    }

    /**
     * Finds BCIActivityInstance entities by their associated BCIActivity id.
     * @param id the id of the BCIActivity associated with the intervention instances.
     * @return a list of BCIActivityInstance objects associated with the specified BCIActivity id.
     * @throws IllegalArgumentException if the id is null.
     */
    public List<BCIActivityInstance> findByBciActivityId(Long id) {
        ObjectValidator.validateId(id);
        return this.bciActivityInstanceRepository.findByBciActivityId(id);
    }

    /**
     * Handles the specified clientEvent for a given BCIActivityInstance.
     * Updates the related BCI Entities according to the clientEvent and checks entry/exit conditions when needed.
     * May Publish an event to other services in order to correctly update its related BCI entities.
     *
     * @param clientEvent The clientEvent indicating the action the client wishes to perform.
     * @param request contains all the information required to properly handle the clientEvent.
     * @return a ClientEventResponse with information on the success of the request as well as the updated entities in JSON format.
     * @throws IllegalArgumentException if the request does not contain every required field to handle the clientEvent.
     */
    public ClientEventResponse handleClientEvent(ClientEvent clientEvent, BCIActivityInstanceRequest request) {
        BCIActivityInstance found = null;
        BCIActivityInstance updated = null;
        ClientEventResponse response = null;
        String failedEntryConditions = "";
        String failedExitConditions = "";

        validateClientEvent(clientEvent, request);
        response = new ClientEventResponse(clientEvent);
        found = findById(request.getId());

        if (found != null) {
            //Handle ClientEvents
            switch (clientEvent) {
                case FINISH -> {
                    failedExitConditions = checkExitConditions(found);
                    if (failedExitConditions.isEmpty()) {
                        response.setSuccess(true);
                        found.setStatus(ExecutionStatus.FINISHED);
                        found.setExitDate(LocalDate.now());
                    }
                }
            }

            //Update the response with information from BCIActivityInstance
            response.addResponse(BCIActivityInstance.class.getSimpleName(), found.getId(), found.getStatus(),
                    failedEntryConditions, failedExitConditions);

            //Update the entity
            if (response.isSuccess()) {
                updated = this.update(found);

                if (updated != null) {
                    //Will wait until all listeners are triggered
                    this.publishEvent(new BCIBlockInstanceClientEvent(updated, clientEvent, response, request.getBciBlockInstanceId(),
                            request.getBciPhaseInstanceId(), request.getBciInstanceId()));
                }
            }
        }

        return response;
    }

    /**
     * Validates the required information found in a BCIActivityInstanceRequest for a clientEvent.
     * @param clientEvent The client event
     * @param request The BCIActivityRequest
     * @throws IllegalArgumentException if any of the validation fails to pass.
     */
    private void validateClientEvent(ClientEvent clientEvent, BCIActivityInstanceRequest request) {
        ObjectValidator.validateObject(clientEvent);
        ObjectValidator.validateObject(request);
        ObjectValidator.validateId(request.getId());
        ObjectValidator.validateId(request.getBciBlockInstanceId());
        ObjectValidator.validateId(request.getBciPhaseInstanceId());
        ObjectValidator.validateId(request.getBciInstanceId());
    }

    /**
     * Checks if a BCIActivityInstance has met its entry conditions.
     * @param bciInstance The BCIActivity to retrieve the entry conditions from.
     * @return All the entry conditions that were not met.
     */
    private String checkEntryConditions(BCIActivityInstance bciInstance) {
        return bciInstance.getBciActivity().getPreconditions();
    }

    /**
     * Checks if a BCIActivityInstance has met its exit conditions.
     * @param bciInstance The BCIActivity to retrieve the exit conditions from.
     * @return All the exit conditions that were not met.
     */
    private String checkExitConditions(BCIActivityInstance bciInstance) {
        return bciInstance.getBciActivity().getPostconditions();
    }
}