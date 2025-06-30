package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import ca.uqam.latece.evo.server.core.model.Skill;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BehaviorChangeIntervention instance class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "bci_instance")
@JsonPropertyOrder({"id", "patient", "currentPhase", "phases"})
public class BehaviorChangeInterventionInstance extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bci_instance_id")
    private Long id;

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
            name = "bci_instance_phases",
            joinColumns = @JoinColumn(name = "bci_instance_phases_bci_id", referencedColumnName="bci_instance_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_instance_phases_phase_id", referencedColumnName="bci_phase_instance_id"))
    private List<BehaviorChangeInterventionPhaseInstance> phases;

    public BehaviorChangeInterventionInstance() {
        phases = new ArrayList<>();
    }

    public BehaviorChangeInterventionInstance(Patient patient, BehaviorChangeInterventionPhaseInstance currentPhase,
                                              List<BehaviorChangeInterventionPhaseInstance> phases) {
        this.patient = patient;
        this.currentPhase = currentPhase;
        this.phases = phases;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    public List<BehaviorChangeInterventionPhaseInstance> getPhases() {
        return phases;
    }

    public void setPhases(List<BehaviorChangeInterventionPhaseInstance> phases) {
        this.phases = phases;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            BehaviorChangeInterventionInstance bciInstance = (BehaviorChangeInterventionInstance) object;
            return Objects.equals(this.getPatient(), bciInstance.getPatient()) &&
                    Objects.equals(this.getCurrentPhase(), bciInstance.getCurrentPhase()) &&
                    Objects.equals(this.getPhases(), bciInstance.getPhases());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getPatient(), this.getCurrentPhase(), this.getPhases());
    }
}
