package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.model.instance.ActivityInstance;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * Represents an event corresponding to a ClientEvent in relation to an ActivityInstance.
 * This Event represents an action that the client side of Evo+ wishes to perform on the related ActivityInstance.
 *
 * @param <T> the subtype of ActivityInstance
 */
public class EvoClientEvent<T extends ActivityInstance> extends ApplicationEvent {
    private T activityInstance = null;

    private ClientEvent clientEvent;

    private ClientEventResponse response;

    public EvoClientEvent(@NotNull ClientEvent clientEvent, @NotNull ClientEventResponse response) {
        super(clientEvent);
        this.clientEvent = clientEvent;
        this.response = response;
    }

    public EvoClientEvent(@NotNull ClientEvent clientEvent, @NotNull ClientEventResponse response, @NotNull Clock clock) {
        super(clientEvent, clock);
        this.clientEvent = clientEvent;
        this.response = response;
    }

    public EvoClientEvent(@NotNull T activityInstance, @NotNull ClientEvent clientEvent, @NotNull ClientEventResponse response) {
        super(clientEvent);
        this.activityInstance = activityInstance;
        this.clientEvent = clientEvent;
        this.response = response;
    }

    public EvoClientEvent(T activityInstance, @NotNull ClientEvent clientEvent, @NotNull ClientEventResponse response,
                          @NotNull Clock clock) {
        super(clientEvent, clock);
        this.activityInstance = activityInstance;
        this.clientEvent = clientEvent;
        this.response = response;
    }

    public T getActivityInstance() {
        return activityInstance;
    }

    public ClientEvent getClientEvent() {
        return clientEvent;
    }

    public ClientEventResponse getResponse() {
        return response;
    }

    /**
     * Returns the string representation of the ActivityInstance associated with this event.
     * @return A JSON-formatted string that represents the current state of the ActivityInstance.
     */
    public String toString() {
        if (clientEvent != null) {
            return this.activityInstance.toString();
        } else {
            return "NULL";
        }
    }
}