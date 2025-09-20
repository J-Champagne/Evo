package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.event.EvoClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.ActivityInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import ca.uqam.latece.evo.server.core.service.AbstractEvoService;
import ca.uqam.latece.evo.server.core.util.FailedConditions;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

abstract public class AbstractBCIInstanceService <A extends ActivityInstance, E extends EvoClientEvent<?>> extends AbstractEvoService<A> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractBCIInstanceService.class);
    /**
     * Handles ClientEvent by updating the corresponding ActivityInstance when specific conditions related to its execution status are met.
     * @param event the EvoClientEvent to be processed, which contains information about the ActivityInstance and its state changes.
     */
    abstract ClientEventResponse handleClientEvent(E event);

    /**
     * Handles a ClientEvent FINISH by updating the corresponding ActivityInstance when specific conditions
     * related to its exit conditions are met.
     * @param activityInstance the ActivityInstance to be updated
     * @param failedConditions the object containing the failed entry/exit conditions
     * @return true if activityInstance was updated
     */
    public boolean handleClientEventFinish(A activityInstance, FailedConditions failedConditions) {
        boolean wasUpdated = false;

        failedConditions.setFailedExitConditions(checkExitConditions(activityInstance));
        if (failedConditions.getFailedExitConditions().isEmpty()) {
            activityInstance.setStatus(ExecutionStatus.FINISHED);
            activityInstance.setExitDate(LocalDate.now());
            wasUpdated = true;
        }

        return wasUpdated;
    }

    /**
     * Checks if an ActivityInstance has met its entry conditions.
     * @param bciInstance The ActivityInstance to retrieve the entry conditions from.
     * @return All the entry conditions that were not met.
     */
    abstract String checkEntryConditions(A bciInstance);

    /**
     * Checks if an ActivityInstance has met its exit conditions.
     * @param bciInstance The ActivityInstance to retrieve the exit conditions from.
     * @return All the exit conditions that were not met.
     */
    abstract String checkExitConditions(A bciInstance);

    /**
     * Publishes the given event using the application event publisher.
     * If the application event publisher is not initialized, an exception is thrown.
     * @param event the EvoClientEvent to be published.
     */
    public final void publishEvent(@NotNull EvoClientEvent<A> event) {
        if (super.applicationEventPublisher != null) {
            this.applicationEventPublisher.publishEvent(event);
            logger.info("{} created with event: {}", event.getActivityInstance().getClass().getSimpleName(), event);
        } else {
            throw this.buildEventException(event);
        }
    }
}