package ca.uqam.latece.evo.server.core.model.instance;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BehaviorChangeInterventionPhase instance class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "bci_phase_instance")
@JsonPropertyOrder({"currentBlock", "blocks", "modules"})
@PrimaryKeyJoinColumn(name="bci_phase_instance_id", referencedColumnName = "activity_instance_id")
public class BehaviorChangeInterventionPhaseInstance extends ActivityInstance {
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
    private List<BehaviorChangeInterventionBlockInstance> blocks = new ArrayList<>();

    @NotNull
    @ManyToMany
    @JoinTable(
            name = "bci_phase_instance_modules",
            joinColumns = @JoinColumn(name = "bci_phase_instance_modules_phase_id", referencedColumnName="bci_phase_instance_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_phase_instance_modules_module_id", referencedColumnName="bci_module_instance_id"))
    private List<BCIModuleInstance> modules = new ArrayList<>();

    public BehaviorChangeInterventionPhaseInstance() {}

    public BehaviorChangeInterventionPhaseInstance(@NotNull String status) {
        super(status);
    }

    public BehaviorChangeInterventionPhaseInstance(@NotNull String status, LocalDate entryDate, LocalDate exitDate) {
        super(status, entryDate, exitDate);
    }

    public BehaviorChangeInterventionPhaseInstance(@NotNull String status,
                                                   @NotNull BehaviorChangeInterventionBlockInstance currentBlock,
                                                   @NotNull List<BehaviorChangeInterventionBlockInstance> blocks,
                                                   @NotNull List<BCIModuleInstance> modules) {
        this(status);
        this.currentBlock = currentBlock;
        this.blocks = blocks;
        this.modules = modules;
    }

    public BehaviorChangeInterventionPhaseInstance(@NotNull String status, LocalDate entryDate, LocalDate exitDate,
                                                   @NotNull BehaviorChangeInterventionBlockInstance currentBlock,
                                                   @NotNull List<BehaviorChangeInterventionBlockInstance> blocks,
                                                   @NotNull List<BCIModuleInstance> modules) {
        this(status, entryDate, exitDate);
        this.currentBlock = currentBlock;
        this.blocks = blocks;
        this.modules = modules;
    }

    public List<BehaviorChangeInterventionBlockInstance> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BehaviorChangeInterventionBlockInstance> blocks) {
        this.blocks = blocks;
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
                    Objects.equals(this.getBlocks(), bciPhaseInstance.getBlocks()) &&
                    Objects.equals(this.getModules(), bciPhaseInstance.getModules());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getCurrentBlock(), this.getBlocks(), this.getModules());
    }
}
