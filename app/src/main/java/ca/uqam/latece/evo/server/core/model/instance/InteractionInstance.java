package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;

import ca.uqam.latece.evo.server.core.model.Interaction;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * InteractionInstance model class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "interaction_instance")
@PrimaryKeyJoinColumn(name="interaction_instance_id", referencedColumnName = "bci_activity_instance_id")
@JsonPropertyOrder({"id", "status", "entryDate", "exitDate", "participants", "interaction"})
public class InteractionInstance extends BCIActivityInstance {
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "interaction_instance_interaction_id", referencedColumnName = "interaction_id",
            nullable = false)
    private Interaction interaction;

    public InteractionInstance() {}

    public InteractionInstance(@NotNull ExecutionStatus status,
                               @NotNull Interaction interaction) {
        super(status, interaction);
        this.interaction = interaction;
    }

    public InteractionInstance(@NotNull ExecutionStatus status, LocalDate entryDate, LocalDate exitDate,
                               @NotNull Interaction interaction) {
        super(status, entryDate, exitDate, interaction);
        this.interaction = interaction;
    }

    public InteractionInstance(@NotNull ExecutionStatus status,
                               @NotNull List<Participant> participants,
                               @NotNull Interaction interaction) {
        super(status, participants, interaction);
        this.interaction = interaction;
    }

    public InteractionInstance(@NotNull ExecutionStatus status, LocalDate entryDate, LocalDate exitDate,
                               @NotNull List<Participant> participants,
                               @NotNull Interaction interaction) {
        super(status, entryDate, exitDate, participants, interaction);
        this.interaction = interaction;
    }

    public Interaction getInteraction() {
        return this.interaction;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }
}
