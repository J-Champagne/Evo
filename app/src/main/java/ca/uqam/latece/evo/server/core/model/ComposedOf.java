package ca.uqam.latece.evo.server.core.model;

import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * ComposedOf model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "composed_of")
@JsonPropertyOrder({"id", "timing", "order"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ComposedOf extends AbstractEvoModel {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="composed_of_id")
    private Long id;

    @NotNull
    @JsonProperty("timing")
    @Enumerated(EnumType.STRING)
    @Column(name="composed_of_time_cycle", nullable = false, length = 128)
    private TimeCycle timing;

    @JsonProperty("order")
    @Column(name="composed_of_order", nullable = true)
    private int order;

    @JsonProperty("BCIActivity")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "composed_of_bci_activity_id", referencedColumnName = "bci_activity_id", nullable = false) // Ensures foreign key setup in the database
    private BCIActivity bciActivityComposedOf;

    @JsonProperty("BehaviorChangeInterventionBlock")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "composed_of_bci_block_id", referencedColumnName = "behavior_change_intervention_block_id", nullable = false)
    private BehaviorChangeInterventionBlock bciBlockComposedOf;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public void setTiming(@NotNull TimeCycle timing) {
        this.timing = timing;
    }

    public TimeCycle getTiming() {
        return this.timing;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    public void setBciActivity(@NotNull BCIActivity bciActivity) {
        this.bciActivityComposedOf = bciActivity;
    }

    public BCIActivity getBciActivity() {
        return this.bciActivityComposedOf;
    }

    public void setBciBlock(@NotNull BehaviorChangeInterventionBlock bciBlock) {
        this.bciBlockComposedOf = bciBlock;
    }

    public BehaviorChangeInterventionBlock getBciBlock() {
        return this.bciBlockComposedOf;
    }
}
