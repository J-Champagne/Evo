package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.ClientEvent;
import ca.uqam.latece.evo.server.core.response.ClientEventResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.ApplicationEvent;

/**
 * Represents an event corresponding to a ClientEvent in relation to an ActivityInstance.
 * This Event represents an action that the client side of Evo+ wishes to perform on the related ActivityInstance.
 *
 */
public class EvoClientEvent extends ApplicationEvent {
    private ClientEvent clientEvent;

    private ClientEventResponse response;

    public EvoClientEvent(@NotNull ClientEvent clientEvent, @NotNull ClientEventResponse response) {
        super(clientEvent);
        this.clientEvent = clientEvent;
        this.response = response;
    }

    public ClientEvent getClientEvent() {
        return clientEvent;
    }

    public ClientEventResponse getResponse() {
        return response;
    }
}