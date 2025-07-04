package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.Activity;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * BCIActivityInstance model class.
 * @author Edilton Lima dos Santos
 * @author Julien Champagne.
 */
@Entity
@Table(name = "bci_activity_instance")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonPropertyOrder({"id", "status", "entryDate", "exitDate", "BCIActivity", "participants"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BCIActivityInstance extends ActivityInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bci_activity_instance_id")
    private Long id;

    @NotNull
    @Column(name="bci_activity_instance_status", nullable = false, length = 128)
    private String status;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="bci_activity_instance_entry_date", nullable = false)
    private LocalDate entryDate;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name="bci_activity_instance_exit_date", nullable = false)
    private LocalDate exitDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bci_activity_instance_bci_id", referencedColumnName = "bci_activity_id", nullable = false)
    private BCIActivity bciActivity;

    @NotNull
    @ManyToMany
    @JoinColumn(name = "bci_activity_instance_participant", referencedColumnName = "participant_id", nullable = false)
    private List<Participant> participants = new ArrayList<>(3);

    public BCIActivityInstance() {}

    public BCIActivityInstance(String status, LocalDate entryDate, LocalDate exitDate) {
        this.status = status;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public BCIActivityInstance(String status, LocalDate entryDate, LocalDate exitDate, List<Participant> participants) {
        this(status, entryDate, exitDate);
        for (Participant participant : participants) {
            this.addParticipant(participant);
        }
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public LocalDate getEntryDate() {
        return entryDate;
    }

    @Override
    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public LocalDate getExitDate() {
        return exitDate;
    }

    @Override
    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public BCIActivity getBciActivity() {
        return bciActivity;
    }

    public void setBciActivity(BCIActivity bciActivity) {
        this.bciActivity = bciActivity;
    }

    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }

    public Participant getParticipants(int index) {
        return participants.get(index);
    }

    public void addParticipant(Participant participant) {
        if (participants.size() < 3) {
            participants.add(participant);
        } else {
            throw new IndexOutOfBoundsException("Can only contain a maximum of 3 participants");
        }
    }

    public void removeParticipant(Participant participant) {
        participants.remove(participant);
    }

    public void removeParticipant(int index) {
        participants.remove(index);
    }
}