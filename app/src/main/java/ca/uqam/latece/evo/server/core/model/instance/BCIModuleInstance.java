package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.model.Activity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.micrometer.observation.annotation.Observed;
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
public class BCIModuleInstance extends ProcessInstance {
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
    private List<BCIActivityInstance> activities;

    @Transient
    private List<ActivityInstance> activityInstances;

    public BCIModuleInstance() {
        this.activities = new ArrayList<>();
        this.activityInstances = new ArrayList<>();
    }

    public BCIModuleInstance(String status, OutcomeType outcome) {
        this.status = status;
        this.outcome = outcome;
    }

    public BCIModuleInstance(String status, OutcomeType outcome, List<BCIActivityInstance> activities) {
        this(status, outcome);
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

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(String status) {

        this.status = status;
    }

    @Override
    public LocalDate getEntryDate() {
        return this.entryDate;
    }

    @Override
    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public LocalDate getExitDate() {
        return this.exitDate;
    }

    @Override
    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public OutcomeType getOutcome() {
        return outcome;
    }

    public void setOutcome(OutcomeType outcome) {
        this.outcome = outcome;
    }

    public List<BCIActivityInstance> getActivities() {
        return activities;
    }

    public void setActivities(List<BCIActivityInstance> activities) {
        this.activities = activities;
    }

    @Override
    public List<ActivityInstance> getActivityInstances() {
        return activityInstances;
    }

    @Override
    public void setActivityInstances(List<ActivityInstance> activityInstances) {
        if (activityInstances != null && !activityInstances.isEmpty()) {
            this.activityInstances.addAll(activityInstances);
        }
    }

    @Override
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
