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
@JsonPropertyOrder("outcome")
@PrimaryKeyJoinColumn(name="bci_module_instance_id", referencedColumnName = "activity_instance_id")
public class BCIModuleInstance extends ActivityInstance implements ProcessInstance<BCIActivityInstance> {
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
        super(status);
        this.outcome = outcome;
    }

    public BCIModuleInstance(String status, OutcomeType outcome, List<BCIActivityInstance> activities) {
        this(status, outcome);
        this.addActivities(activities);
    }

    public BCIModuleInstance(String status, LocalDate entryDate, LocalDate exitDate, OutcomeType outcome,
                             List<BCIActivityInstance> activities) {
        super(status, entryDate, exitDate);
        this.outcome = outcome;
        this.addActivities(activities);
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
        return Objects.hash(super.hashCode(), this.getOutcome(), this.getActivities());
    }
}
