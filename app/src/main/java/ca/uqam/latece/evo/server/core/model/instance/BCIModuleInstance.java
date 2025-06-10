package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.enumeration.OutcomeType;
import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * BCIModule instance class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "bci_module_instance")
@JsonPropertyOrder("outcome")
public class BCIModuleInstance extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bci_module_instance_id")
    private Long id;

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

    public BCIModuleInstance() {}

    public BCIModuleInstance(OutcomeType outcome) {
        this.outcome = outcome;
        this.activities = new ArrayList<>();
    }

    public BCIModuleInstance(OutcomeType outcome, List<BCIActivityInstance> activities) {
        this.outcome = outcome;
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
}
