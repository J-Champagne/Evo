package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BehaviorChangeIntervention instance class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "bci_instance")
@JsonPropertyOrder({"patient", "currentPhase", "activities"})
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@PrimaryKeyJoinColumn(name="bci_instance_id", referencedColumnName = "activity_instance_id")
public class BehaviorChangeInterventionInstance extends ActivityInstance {
    @NotNull
    @ManyToOne
    @JoinColumn(name = "bci_instance_patient_id")
    private Patient patient;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "bci_instance_currentphase_id", referencedColumnName = "bci_phase_instance_id", nullable = false)
    private BehaviorChangeInterventionPhaseInstance currentPhase;

    @NotNull
    @OneToMany
    @JoinTable(
            name = "bci_instance_activities",
            joinColumns = @JoinColumn(name = "bci_instance_activities_bci_id", referencedColumnName="bci_instance_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_instance_activities_phase_id", referencedColumnName="bci_phase_instance_id"))
    private List<BehaviorChangeInterventionPhaseInstance> activities = new ArrayList<>();

    public BehaviorChangeInterventionInstance() {}

    public BehaviorChangeInterventionInstance(ExecutionStatus status) {
        super(status);
    }

    public BehaviorChangeInterventionInstance(ExecutionStatus status, Patient patient,
                                              BehaviorChangeInterventionPhaseInstance currentPhase,
                                              List<BehaviorChangeInterventionPhaseInstance> activities) {
        this(status);
        this.patient = patient;
        this.currentPhase = currentPhase;
        this.activities = activities;
    }

    public BehaviorChangeInterventionInstance(ExecutionStatus status, LocalDate entryDate, LocalDate exitDate,
                                              Patient patient, BehaviorChangeInterventionPhaseInstance currentPhase,
                                              List<BehaviorChangeInterventionPhaseInstance> activities) {
        super(status, entryDate, exitDate);
        this.patient = patient;
        this.currentPhase = currentPhase;
        this.activities = activities;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public BehaviorChangeInterventionPhaseInstance getCurrentPhase() {
        return this.currentPhase;
    }

    public void setCurrentPhase(BehaviorChangeInterventionPhaseInstance currentPhase) {
        this.currentPhase = currentPhase;
    }

    public List<BehaviorChangeInterventionPhaseInstance> getActivities() {
        return activities;
    }

    public void setActivities(List<BehaviorChangeInterventionPhaseInstance> activities) {
        this.activities = activities;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            BehaviorChangeInterventionInstance bciInstance = (BehaviorChangeInterventionInstance) object;
            return Objects.equals(this.getPatient(), bciInstance.getPatient()) &&
                    Objects.equals(this.getCurrentPhase(), bciInstance.getCurrentPhase()) &&
                    Objects.equals(this.getActivities(), bciInstance.getActivities());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getPatient(), this.getCurrentPhase(), this.getActivities());
    }
}
