package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.interfaces.ProcessInstance;
import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BCIModule instance class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "bci_module_instance")
@JsonPropertyOrder("id, status, entrydate, exitdate, outcome")
public class BCIModuleInstance extends AbstractEvoModel implements ProcessInstance<BCIActivityInstance> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bci_module_instance_id")
    private Long id;

    @NotNull
    @Column(name="bci_module_instance_status", nullable = false, length = 128)
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "bci_module_instance_entrydate")
    private LocalDate entryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "bci_module_instance_exitdate")
    private LocalDate exitDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "bci_module_instance_outcome")
    private OutcomeType outcome;

    @NotNull
    @ManyToMany
    @JoinTable(
            name = "bci_module_instance_activities",
            joinColumns = @JoinColumn(name = "bci_module_instance_activities_module_id", referencedColumnName="bci_module_instance_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_module_instance_activities_activity_id", referencedColumnName="bci_activity_instance_id"))
    private List<BCIActivityInstance> activities = new ArrayList<>();

    public BCIModuleInstance() {}

    public BCIModuleInstance(String status, OutcomeType outcome) {
        this.status = status;
        this.outcome = outcome;
    }

    public BCIModuleInstance(String status, OutcomeType outcome, List<BCIActivityInstance> activities) {
        this(status, outcome);
        this.activities = activities;
    }

    public BCIModuleInstance(String status, LocalDate entryDate, LocalDate exitDate, OutcomeType outcome, List<BCIActivityInstance> activities) {
        this(status, outcome);
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.activities = activities;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public LocalDate getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getExitDate() {
        return this.exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public OutcomeType getOutcome() {
        return outcome;
    }

    public void setOutcome(OutcomeType outcome) {
        this.outcome = outcome;
    }

    @Override
    public List<BCIActivityInstance> getActivities() {
        return activities;
    }

    @Override
    public void addActivity(BCIActivityInstance activityInstance) {
        if (activityInstance != null) {
            this.activities.add(activityInstance);
        }
    }

    @Override
    public void addActivities(List<BCIActivityInstance> activityInstances) {
        if (activityInstances != null) {
            this.activities.addAll(activityInstances);
        }
    }

    @Override
    public boolean removeActivity(BCIActivityInstance activityInstance) {
        boolean removed = false;

        if (activities != null) {
           removed = activities.remove(activityInstance);
       }
        return removed;
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            BCIModuleInstance moduleInstance = (BCIModuleInstance) object;
            return Objects.equals(this.getOutcome(), moduleInstance.getOutcome()) &&
                    Objects.equals(this.getActivities(), moduleInstance.getActivities());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getOutcome(), this.getActivities());
    }
}
