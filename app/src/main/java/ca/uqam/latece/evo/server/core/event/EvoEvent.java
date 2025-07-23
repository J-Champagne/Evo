package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.time.Clock;

/**
 * Represents an event in the Evo+ framework that is associated with a specific Evo model.
 * The event is based on a generic type {@code T} that must extend {@code AbstractEvoModel}.
 * This class extends the {@code ApplicationEvent} class and provides additional context
 * for Evo model-based event handling, including optionally specifying {@code TimeCycle}
 * and {@code Clock} information.
 *
 * @param <T> the type of the Evo model associated with this event, which must extend {@code AbstractEvoModel}.
 */
public class EvoEvent<T extends AbstractEvoModel> extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = 194568719L;
    private T evoModel;
    private TimeCycle timeCycle = TimeCycle.UNSPECIFIED;

    /**
     * Constructs a new EvoEvent with the specified Evo model.
     * This constructor initializes the event using the provided Evo model and associates it as the source of the event.
     * @param evoModel the model associated with this event, which must not be null. The type of the model must extend {
     * @code AbstractEvoModel}.
     * @throws NullPointerException if the {@code evoModel} is null.
     */
    public EvoEvent(@NotNull T evoModel) {
        super(evoModel);
        this.evoModel = evoModel;
    }

    /**
     * Constructs a new EvoEvent with the specified Evo model.
     * This constructor initializes the event using the provided Evo model and associates it as the source of the event.
     * @param evoModel the model associated with this event, which must not be null. The type of the model must extend {
     * @code AbstractEvoModel}.
     * @param timeCycle the timeCycle instance used to define the status.
     * @throws NullPointerException if the {@code evoModel} is null.
     */
    public EvoEvent(@NotNull T evoModel, TimeCycle timeCycle) {
        this(evoModel);
        this.timeCycle = timeCycle;
    }

    /**
     * Constructs a new EvoEvent with the specified Evo model and clock.
     * This constructor initializes the event using the provided Evo model and a specific clock instance,
     * associating the Evo model as the source of the event.
     * @param evoModel the model associated with this event, which must not be null.
     *                 The type of the model must extend {@code AbstractEvoModel}.
     * @param clock the clock instance used to timestamp this event, which must not be null.
     * @throws NullPointerException if {@code evoModel} or {@code clock} is null.
     */
    public EvoEvent(@NotNull T evoModel, @NotNull Clock clock) {
        super(evoModel, clock);
        this.evoModel = evoModel;
    }

    /**
     * Constructs a new EvoEvent with the specified Evo model, clock, timeCycle.
     * This constructor initializes the event using the provided Evo model and a specific clock instance,
     * associating the Evo model as the source of the event.
     * @param evoModel the model associated with this event, which must not be null.
     *                 The type of the model must extend {@code AbstractEvoModel}.
     * @param clock the clock instance used to timestamp this event, which must not be null.
     * @param timeCycle the timeCycle instance used to define the status.
     * @throws NullPointerException if {@code evoModel} or {@code clock} is null.
     */
    public EvoEvent(@NotNull T evoModel, @NotNull Clock clock, TimeCycle timeCycle) {
        this(evoModel, clock);
        this.timeCycle = timeCycle;
    }

    /**
     * Gets the unique identifier of the Evo model associated with this event.
     * @return the unique ID of the associated Evo model, represented as a {@code Long}.
     */
    public Long getEvoModelId() {
        return this.evoModel.getId();
    }

    /**
     * Retrieves the Evo model associated with this event.
     * @return the {@code AbstractEvoModel} instance associated with this event.
     */
    public T getEvoModel() {
        return this.evoModel;
    }

    public TimeCycle getTimeCycle() {
        return this.timeCycle;
    }

    /**
     * Returns the string representation of the underlying {@link AbstractEvoModel} associated with this event.
     * The string representation is derived from the {@code toString()} method of the {@code AbstractEvoModel}.
     * @return A JSON-formatted string that represents the current state of the {@code AbstractEvoModel}.
     */
    public String toString() {
        return this.evoModel.toString();
    }
}
