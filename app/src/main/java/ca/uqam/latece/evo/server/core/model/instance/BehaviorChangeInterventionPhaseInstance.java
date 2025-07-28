package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import ca.uqam.latece.evo.server.core.interfaces.ProcessInstance;
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
 * BehaviorChangeInterventionPhase instance class.
 * @version 1.0
 * @author Julien Champagne.
 * @Edilton Lima dos Santos
 */
@Entity
@Table(name = "bci_phase_instance")
@JsonPropertyOrder({"currentBlock", "blocks", "modules"})
@PrimaryKeyJoinColumn(name="bci_phase_instance_id", referencedColumnName = "activity_instance_id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BehaviorChangeInterventionPhaseInstance extends ActivityInstance implements ProcessInstance<BehaviorChangeInterventionBlockInstance> {
    @NotNull
    @ManyToOne
    @JoinColumn(name = "bci_phase_instance_currentblock_id", referencedColumnName = "bci_block_instance_id", nullable = false)
    private BehaviorChangeInterventionBlockInstance currentBlock;

    @NotNull
    @ManyToMany
    @JoinTable(
            name = "bci_phase_instance_blocks",
            joinColumns = @JoinColumn(name = "bci_phase_instance_blocks_phase_id", referencedColumnName="bci_phase_instance_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_phase_instance_blocks_block_id", referencedColumnName="bci_block_instance_id"))
    private List<BehaviorChangeInterventionBlockInstance> activities = new ArrayList<>();

    @NotNull
    @ManyToMany
    @JoinTable(
            name = "bci_phase_instance_modules",
            joinColumns = @JoinColumn(name = "bci_phase_instance_modules_phase_id", referencedColumnName="bci_phase_instance_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_phase_instance_modules_module_id", referencedColumnName="bci_module_instance_id"))
    private List<BCIModuleInstance> modules = new ArrayList<>();

    public BehaviorChangeInterventionPhaseInstance() {}

    public BehaviorChangeInterventionPhaseInstance(ExecutionStatus status) {
        super(status);
    }

    public BehaviorChangeInterventionPhaseInstance(@NotNull ExecutionStatus status, LocalDate entryDate, LocalDate exitDate) {
        super(status, entryDate, exitDate);
    }

    public BehaviorChangeInterventionPhaseInstance(@NotNull ExecutionStatus status,
                                                   @NotNull BehaviorChangeInterventionBlockInstance currentBlock,
                                                   @NotNull List<BehaviorChangeInterventionBlockInstance> activities,
                                                   @NotNull List<BCIModuleInstance> modules) {
        this(status);
        this.currentBlock = currentBlock;
        this.addActivities(activities);
        this.modules = modules;
    }

    public BehaviorChangeInterventionPhaseInstance(@NotNull ExecutionStatus status, LocalDate entryDate, LocalDate exitDate,
                                                   @NotNull BehaviorChangeInterventionBlockInstance currentBlock,
                                                   @NotNull List<BehaviorChangeInterventionBlockInstance> activities,
                                                   @NotNull List<BCIModuleInstance> modules) {
        this(status, entryDate, exitDate);
        this.currentBlock = currentBlock;
        this.addActivities(activities);
        this.modules = modules;
    }

    @Override
    public List<BehaviorChangeInterventionBlockInstance> getActivities() {
        return activities;
    }

    @Override
    public void addActivity(BehaviorChangeInterventionBlockInstance blockInstance) {
        if (blockInstance != null) {
            this.activities.add(blockInstance);
        }
    }

    @Override
    public void addActivities(List<BehaviorChangeInterventionBlockInstance> blockInstances){
        if (blockInstances != null) {
            this.activities.addAll(blockInstances);
        }
    }

    @Override
    public boolean removeActivity(BehaviorChangeInterventionBlockInstance blockInstance) {
        boolean removed = false;

        if (activities != null) {
            removed = activities.remove(blockInstance);
        }
        return removed;
    }

    public List<BCIModuleInstance> getModules() {
        return modules;
    }

    public void setModules(List<BCIModuleInstance> modules) {
        this.modules = modules;
    }

    public BehaviorChangeInterventionBlockInstance getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(BehaviorChangeInterventionBlockInstance currentBlock) {
        this.currentBlock = currentBlock;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            BehaviorChangeInterventionPhaseInstance bciPhaseInstance = (BehaviorChangeInterventionPhaseInstance) object;
            return Objects.equals(this.getCurrentBlock(), bciPhaseInstance.getCurrentBlock()) &&
                    Objects.equals(this.getActivities(), bciPhaseInstance.getActivities()) &&
                    Objects.equals(this.getModules(), bciPhaseInstance.getModules());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getCurrentBlock(), this.getActivities(), this.getModules());
    }
}
