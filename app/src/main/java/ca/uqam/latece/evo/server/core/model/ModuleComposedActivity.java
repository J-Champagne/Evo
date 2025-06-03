package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

/**
 * ModuleComposedActivity model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "module_composed_activity")
@JsonPropertyOrder({"id", "composedActivityBciModule", "composedModuleBciActivity", "order"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ModuleComposedActivity extends AbstractEvoModel {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_composed_activity_id")
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "module_composed_activity_bci_module_id", nullable = false)
    private BCIModule composedActivityBciModule;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "module_composed_activity_bci_activity_id", nullable = false)
    private BCIActivity composedModuleBciActivity;

    @NotNull
    @Column(name = "module_composed_activity_order", nullable = false)
    private int order;


    public ModuleComposedActivity() {}

    public ModuleComposedActivity(@NotNull BCIModule module, @NotNull BCIActivity activity, @NotNull int order) {
        this.composedActivityBciModule = module;
        this.composedModuleBciActivity = activity;
        this.order = order;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setComposedActivityBciModule(BCIModule module) {
        this.composedActivityBciModule = module;
    }

    public BCIModule getComposedActivityBciModule() {
        return composedActivityBciModule;
    }

    public void setComposedModuleBciActivity(BCIActivity activity) {
        this.composedModuleBciActivity = activity;
    }

    public BCIActivity getComposedModuleBciActivity() {
        return composedModuleBciActivity;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            ModuleComposedActivity module = (ModuleComposedActivity) object;
            return Objects.equals(this.getComposedActivityBciModule(), module.getComposedActivityBciModule()) &&
                    Objects.equals(this.getComposedModuleBciActivity(), module.getComposedModuleBciActivity()) &&
                    Objects.equals(this.getOrder(), module.getOrder());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getComposedActivityBciModule(),
                this.getComposedModuleBciActivity(), this.getOrder());
    }
}