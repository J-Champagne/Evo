package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

/**
 * InteractionInstance model class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "interaction_instance")
@PrimaryKeyJoinColumn(name="interaction_instance_id", referencedColumnName = "bci_activity_instance_id")
@JsonPropertyOrder({"id", "status", "entryDate", "exitDate", "participants"})
public class InteractionInstance extends BCIActivityInstance {
    public InteractionInstance() {}

    public InteractionInstance(ExecutionStatus status) {
        super(status);
    }

    public InteractionInstance(ExecutionStatus status, LocalDate entryDate, LocalDate exitDate) {
        super(status, entryDate, exitDate);
    }

    public InteractionInstance(ExecutionStatus status, List<Participant> participants) {
        super(status, participants);
    }

    public InteractionInstance(ExecutionStatus status, LocalDate entryDate, LocalDate exitDate, List<Participant> participants) {
        super(status, entryDate, exitDate, participants);
    }
}
