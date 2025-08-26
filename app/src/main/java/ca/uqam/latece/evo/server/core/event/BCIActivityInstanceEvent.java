package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;

import jakarta.validation.constraints.NotNull;

import java.time.Clock;

/**
 * Represents an event corresponding to a {@code BCIActivityInstance}.
 * This class inherits from {@code EvoEvent} and is designed to handle events specifically tied
 * to the {@code BCIActivityInstance} entity within the Evo+ framework.
 * @version 1.0
 * @author Julien Champagne.
 */
public class BCIActivityInstanceEvent extends EvoEvent<BCIActivityInstance> {

    public BCIActivityInstanceEvent(@NotNull BCIActivityInstance evoModel) {
        super(evoModel);
    }

    public BCIActivityInstanceEvent(@NotNull BCIActivityInstance evoModel, @NotNull Clock clock) {
        super(evoModel);
    }

    @Override
    public BCIActivityInstance getEvoModel() {
        return super.getEvoModel();
    }
}