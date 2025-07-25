package ca.uqam.latece.evo.server.core.event;

import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.model.instance.BCIActivityInstance;
import ca.uqam.latece.evo.server.core.model.instance.BCIModuleInstance;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents an event specific to a BCI Module Instance in the Evo+ framework. This class extends
 * {@code EvoEvent<BCIModuleInstance>} and provides additional methods for retrieving information about the associated
 * BCI Module Instance.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public class BCIModuleInstanceEvent extends EvoEvent<BCIModuleInstance> {

    public BCIModuleInstanceEvent(@NotNull BCIModuleInstance evoModel) {
        super(evoModel);
    }

    public String getStatus() {
        return this.getEvoModel().getStatus();
    }

    public OutcomeType getOutcome() {
        return this.getEvoModel().getOutcome();
    }

    public List<BCIActivityInstance> getActivities() {
        return this.getEvoModel().getActivities();
    }

    public LocalDate getEntryDate() {
        return this.getEvoModel().getEntryDate();
    }

    public LocalDate getExitDate() {
        return this.getEvoModel().getExitDate();
    }

    @Override
    public BCIModuleInstance getEvoModel() {
        return super.getEvoModel();
    }
}
