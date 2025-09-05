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
import java.util.ArrayList;
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
        this.publishEvent(new BCIBlockInstanceEvent(saved, saved.getStage()));
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

            // Publish the event only if BehaviorChangeInterventionBlockInstance Status equals in progress or Finished.
            if ((updated.getStatus().equals(ExecutionStatus.IN_PROGRESS) || updated.getStatus().equals(ExecutionStatus.FINISHED)) &&
                    !updated.getStage().equals(found.getStage())) {
                this.publishEvent(new BCIBlockInstanceEvent(updated, updated.getStage()));
            }
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
     * Handles BCIBlockInstanceClientEvent by updating the corresponding BehaviorChangeInterventionBlockInstance
     * when specific conditions related to its execution status are met.
     * @param event the BCIBlockInstanceClientEvent to be processed, which contains information about the
     *              BCIBlockInstance and its state changes.
     */
    @EventListener(BCIBlockInstanceClientEvent.class)
    public void handleClientEvent(BCIBlockInstanceClientEvent event) {
        if (event != null && event.getEvoModel() != null && event.getClientEvent() != null && event.getBciBlockInstanceId() != null) {
            BehaviorChangeInterventionBlockInstance blockInstance = findById(event.getBciBlockInstanceId());

            if (blockInstance != null) {
                //Handle ClientEvents
                BCIActivityInstance bciActivityInstance = event.getEvoModel();
                ClientEvent clientEvent = event.getClientEvent();

                switch(clientEvent) {
                    case ClientEvent.FINISH -> {
                        if (bciActivityInstance.getStatus() == ExecutionStatus.FINISHED) {
                            List<String> failedExitConditions = checkExitConditions(blockInstance);

                            if (failedExitConditions.isEmpty()) {
                                blockInstance.setStatus(ExecutionStatus.FINISHED);
                                blockInstance.setExitDate(LocalDate.now());
                            }
                        }
                    }
                }

                this.update(blockInstance);
                this.publishEvent(new BCIPhaseInstanceClientEvent(blockInstance, clientEvent,
                        event.getBciPhaseInstanceId(), event.getBciInstanceId()));

            } else {
                // TODO Error, BCIBlockInstanceId is NULL

            }
        }
    }

    /**
     * Checks if a BCIBlockInstance has met its exit conditions.
     * @param bciBlockInstance The BCIBlockInstance to retrieve the exit conditions from.
     * @return A List of exit conditions that were not met.
     */
    private List<String> checkExitConditions(BehaviorChangeInterventionBlockInstance bciBlockInstance) {
        List<String> exitConditions = new ArrayList<>();
        String exitCondition = bciBlockInstance.getBehaviorChangeInterventionBlock().getExitConditions();

        if (!exitCondition.isEmpty()) {
            exitConditions.add(exitCondition);
        }

        return exitConditions;
    }
}
