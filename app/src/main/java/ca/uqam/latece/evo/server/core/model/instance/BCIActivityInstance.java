package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BCIActivityInstance model class.
 * @version 1.0
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@Entity
@Table(name = "bci_activity_instance")
@JsonPropertyOrder({"id", "status", "entryDate", "exitDate", "participants"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@PrimaryKeyJoinColumn(name="bci_activity_instance_id", referencedColumnName = "activity_instance_id")
@Inheritance(strategy = InheritanceType.JOINED)
public class BCIActivityInstance extends ActivityInstance {
    @ManyToMany
    @JoinTable(
            name = "bci_activity_instance_participants",
            joinColumns = @JoinColumn(name = "bci_activity_instance_participants_bci_activity_instance_id", referencedColumnName="bci_activity_instance_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_activity_instance_participants_participant_id", referencedColumnName="participant_id"))
    private List<Participant> participants = new ArrayList<>(3);

    //@NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bci_activity_instance_bci_activity_id", referencedColumnName = "bci_activity_id",
            nullable = false)
    private BCIActivity bciActivity;

    public BCIActivityInstance() {}

    public BCIActivityInstance(@NotNull ExecutionStatus status,
                               @NotNull BCIActivity bciActivity) {
        super(status);
        this.bciActivity = bciActivity;
    }

    public BCIActivityInstance(@NotNull ExecutionStatus status, LocalDate entryDate, LocalDate exitDate,
                               @NotNull BCIActivity bciActivity) {
        super(status, entryDate, exitDate);
        this.bciActivity = bciActivity;
    }

    public BCIActivityInstance(@NotNull ExecutionStatus status,
                               @NotNull List<Participant> participants,
                               @NotNull BCIActivity bciActivity) {
        this(status, bciActivity);
        for (Participant participant : participants) {
            this.addParticipant(participant);
        }
    }

    public BCIActivityInstance(@NotNull ExecutionStatus status, LocalDate entryDate, LocalDate exitDate,
                               @NotNull List<Participant> participants,
                               @NotNull BCIActivity bciActivity) {
        this(status, entryDate, exitDate, bciActivity);
        for (Participant participant : participants) {
            this.addParticipant(participant);
        }
    }

    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }

    public Participant getParticipants(int index) {
        return participants.get(index);
    }

    public BCIActivity getBciActivity() {
        return this.bciActivity;
    }

    public void setBciActivity(BCIActivity bciActivity) {
        this.bciActivity = bciActivity;
    }

    public void addParticipant(Participant participant) {
        if (participants.size() < 3) {
            participants.add(participant);
        } else {
            throw new IndexOutOfBoundsException("Can only contain a maximum of 3 participants!");
        }
    }

    public void removeParticipant(Participant participant) {
        participants.remove(participant);
    }

    public void removeParticipant(int index) {
        participants.remove(index);
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            BCIActivityInstance bciActivityInstance = (BCIActivityInstance) object;
            return Objects.equals(this.getParticipants(), bciActivityInstance.getParticipants()) &&
                    Objects.equals(this.getBciActivity(), bciActivityInstance.getBciActivity());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getParticipants(), this.getBciActivity());
    }
}