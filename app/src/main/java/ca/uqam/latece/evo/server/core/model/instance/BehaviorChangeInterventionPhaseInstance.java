package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BehaviorChangeInterventionPhase instance class.
 * @author Julien Champagne.
 */
@Entity
@Table(name = "bci_phase_instance")
@JsonPropertyOrder()
public class BehaviorChangeInterventionPhaseInstance extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bci_phase_instance_id")
    private Long id;

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
    private List<BehaviorChangeInterventionBlockInstance> blocks;

    @NotNull
    @ManyToMany
    @JoinTable(
            name = "bci_phase_instance_modules",
            joinColumns = @JoinColumn(name = "bci_phase_instance_modules_phase_id", referencedColumnName="bci_phase_instance_id"),
            inverseJoinColumns = @JoinColumn(name = "bci_phase_instance_modules_module_id", referencedColumnName="bci_module_instance_id"))
    private List<BCIModuleInstance> modules;

    public BehaviorChangeInterventionPhaseInstance() {
        blocks = new ArrayList<>();
        modules = new ArrayList<>();
    }

    public BehaviorChangeInterventionPhaseInstance(@NotNull BehaviorChangeInterventionBlockInstance currentBlock,
                                                   @NotNull List<BehaviorChangeInterventionBlockInstance> blocks,
                                                   @NotNull List<BCIModuleInstance> modules) {
        this.currentBlock = currentBlock;
        this.blocks = blocks;
        this.modules = modules;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
        return Objects.hash(this.getId(), this.getCurrentBlock(), this.getBlocks(), this.getModules());
    }
}
