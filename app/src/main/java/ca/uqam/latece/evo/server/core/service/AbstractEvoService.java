package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.event.EvoClientEvent;
import ca.uqam.latece.evo.server.core.event.EvoEvent;
import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;

/**
 * Abstract Evo Service.
 * @param <T> the Evo model (AbstractEvoModel).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
public abstract class AbstractEvoService <T extends AbstractEvoModel> implements ApplicationEventPublisherAware {
    private static final Logger logger = LoggerFactory.getLogger(AbstractEvoService.class);
    protected static final String ERROR_NAME_ALREADY_REGISTERED = "Name already registered!";

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * Creates a evoModel in the database.
     * @param evoModel must not be null.
     * @return the saved entity.
     * @throws IllegalArgumentException in case the given evoModel is null.
     */
    public T create(@NotNull T evoModel) {
        ObjectValidator.validateObject(evoModel);
        return this.save(evoModel);
    }

    /**
     * Updates a evoModel in the database.
     * @param evoModel must not be null.
     * @return the saved entity.
     * @throws IllegalArgumentException in case the given evoModel is null.
     */
    public T update(@NotNull T evoModel) {
        ObjectValidator.validateObject(evoModel);
        return this.save(evoModel);
    }

    /**
     * Create duplicate name Exception to EvoModel with a name already registered in the database.
     * @param evoModel the EvoModel entity.
     * @param duplicatedNameText property value duplicated in the database.
     * @return an exception object.
     */
    protected IllegalArgumentException createDuplicatedNameException(AbstractEvoModel evoModel, String duplicatedNameText) {
        String errorMessage = String.format("%s %s Name: %s", ERROR_NAME_ALREADY_REGISTERED,
                evoModel.getClass().getSimpleName(), duplicatedNameText);

        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(errorMessage);
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    /**
     * Saves a given evoModel.
     * @param evoModel must not be null.
     * @return the saved entity.
     * @throws IllegalArgumentException in case the given evoModel is null.
     */
    protected abstract T save(@NotNull T evoModel);

    /**
     * Returns whether an AbstractEvoModel with the given id exists.
     * @param id must not be null.
     * @return true if an AbstractEvoModel with the given id exists, false otherwise.
     * @throws IllegalArgumentException in case the given id is null.
     */
    public abstract boolean existsById(@NotNull Long id);

    /**
     * Deletes the AbstractEvoModel with the given id. If the AbstractEvoModel is not found in the persistence
     * store it is silently ignored.
     * @param id must not be null.
     * @throws IllegalArgumentException in case the given id is null.
     */
    public abstract void deleteById(@NotNull Long id);

    /**
     * Retrieves an AbstractEvoModel by its id.
     * @param id must not be null.
     * @return the AbstractEvoModel with the given id.
     * @throws IllegalArgumentException in case the given id is null.
     */
    public abstract T findById(@NotNull Long id);

    /**
     * Returns all instances of the AbstractEvoModel.
     * @return all AbstractEvoModel.
     */
    public abstract List<T> findAll();

    /**
     * Sets the application event publisher for the service. This allows the service to publish application-wide
     * events using the provided {@link ApplicationEventPublisher}.
     * @param applicationEventPublisher the event publisher to be set. Must not be null.
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * Constructs an {@code IllegalArgumentException} with an error message indicating that the
     * application event publisher is null and cannot publish the provided event.
     * @param event the event that could not be published because the application event publisher is null.
     * @return an {@code IllegalArgumentException} constructed with the appropriate error message.
     */
    private IllegalArgumentException buildEventException(ApplicationEvent event){
        String errorMessage = String.format("Application Event Publisher is null. Cannot publish event: %s", event);
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(errorMessage);
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    /**
     * Publishes an event for the given evoModel using the application's event publisher.
     * @param evoModel the model for which the event is published. Must not be null.
     * @throws IllegalArgumentException if the {@code applicationEventPublisher} is null, logging the relevant error.
     */
    public final void publishEvent(@NotNull T evoModel) {
        this.publishEvent(evoModel, TimeCycle.UNSPECIFIED);
    }

    /**
     * Publishes an event for the given evoModel using the application's event publisher.
     * This event is published after an update ot the ExecutionStatus of the evoModel.
     * @param evoModel the updated model for which the event is published. Must not be null.
     * @param status The old execution status, its value before the update. Must not be null.
     * @throws IllegalArgumentException if the {@code applicationEventPublisher} is null, logging the relevant error.
     */
    public final void publishEvent(@NotNull T evoModel, @NotNull ExecutionStatus status) {
        //TODO
        this.publishEvent(evoModel, TimeCycle.UNSPECIFIED);
    }

    /**
     * Publishes an event for the given evoModel and timeCycle using the application's event publisher.
     * @param evoModel the model for which the event is published. Must not be null.
     * @param timeCycle the timeCycle instance used to define the status.
     * @throws IllegalArgumentException if the {@code applicationEventPublisher} or {@code timeCycle} is null, logging
     * the relevant error.
     */
    public final void publishEvent(@NotNull T evoModel, @NotNull TimeCycle timeCycle) {
        EvoEvent<T> event = null;

        if (this.applicationEventPublisher != null) {
            event = new EvoEvent<>(evoModel, timeCycle);
            this.applicationEventPublisher.publishEvent(event);
            logger.info("{} created with event: {} and TimeCycle: {}", evoModel.getClass().getSimpleName(), event,
                    timeCycle);
        } else {
           throw this.buildEventException(event);
        }
    }

    /**
     * Publishes an event for the given evoModel and clock using the application's event publisher.
     * @param evoModel the model associated with this event, which must not be null.
     *                 The type of the model must extend {@code AbstractEvoModel}.
     * @param clock the clock instance used to timestamp this event, which must not be null.
     * @throws NullPointerException if {@code evoModel} or {@code clock} is null.
     */
    public final void publishEvent(@NotNull T evoModel, @NotNull Clock clock) {
        this.publishEvent(evoModel, clock, TimeCycle.UNSPECIFIED);
    }

    /**
     * Publishes an event for the given evoModel, clock, and timeCycle using the application's event publisher.
     * @param evoModel the model associated with this event, which must not be null.
     *                 The type of the model must extend {@code AbstractEvoModel}.
     * @param clock the clock instance used to timestamp this event, which must not be null.
     * @param timeCycle the timeCycle instance used to define the status.
     * @throws NullPointerException if {@code evoModel} or {@code clock} is null.
     */
    public final void publishEvent(@NotNull T evoModel, @NotNull Clock clock, TimeCycle timeCycle) {
        EvoEvent<T> event = null;

        if (this.applicationEventPublisher != null) {
            event = new EvoEvent<>(evoModel, clock, timeCycle);
            this.applicationEventPublisher.publishEvent(event);
            logger.info("{} created with event: {} || Clock: {} || TimeCycle: {}", evoModel.getClass().getSimpleName(),
                    event, clock, timeCycle);
        } else {
            throw this.buildEventException(event);
        }
    }

    /**
     * Publishes the given {@code EvoEvent} using the application's event publisher.
     * @param event the event to be published.
     * @throws IllegalArgumentException if the {@code applicationEventPublisher} is not initialized, logging the
     * relevant error message.
     */
    public final void publishEvent(@NotNull EvoEvent<T> event) {
        if (this.applicationEventPublisher != null) {
            this.applicationEventPublisher.publishEvent(event);
            logger.info("{} created with event: {} ", event.getEvoModel().getClass().getSimpleName(), event);
        } else {
            throw this.buildEventException(event);
        }
    }

    /**
     * Publishes the given event using the application event publisher.
     * The event is associated with a specified time cycle before being published.
     * If the application event publisher is not initialized, an exception is thrown.
     * @param event the EvoEvent to be published.
     * @param timeCycle  the TimeCycle to associate with the event.
     */
    public final void publishEvent(@NotNull EvoEvent event, @NotNull TimeCycle timeCycle) {
        if (this.applicationEventPublisher != null) {
            event.setTimeCycle(timeCycle);
            this.applicationEventPublisher.publishEvent(event);
            logger.info("{} created with event: {} || TimeCycle: {}", event.getEvoModel().getClass().getSimpleName(),
                    event, timeCycle);
        } else {
            throw this.buildEventException(event);
        }
    }

    /**
     * Publishes the given event using the application event publisher.
     * If the application event publisher is not initialized, an exception is thrown.
     * @param event the EvoClientEvent to be published.
     */
    public final void publishEvent(@NotNull EvoClientEvent event) {
        if (this.applicationEventPublisher != null) {
            this.applicationEventPublisher.publishEvent(event);
            logger.info("{} created with event: {}", event.getActivityInstance().getClass().getSimpleName(), event);
        } else {
            throw this.buildEventException(event);
        }
    }
}
