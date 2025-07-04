package ca.uqam.latece.evo.server.core.model;

import ca.uqam.latece.evo.server.core.enumeration.ActivityType;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMedium;
import ca.uqam.latece.evo.server.core.enumeration.InteractionMode;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.*;


/**
 * Interaction model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Entity
@Table(name = "interaction")
@PrimaryKeyJoinColumn(name="interaction_id", referencedColumnName = "bci_activity_id")
@JsonPropertyOrder({"id", "name", "description", "type", "preconditions", "postconditions","interactionMode",
"interactionInitiatorRole", "interactionMedium1", "interactionMedium2", "interactionMedium3", "interactionMedium4"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Interaction extends BCIActivity {

    @NotNull
    @JsonProperty("interactionMode")
    @Enumerated(EnumType.STRING)
    @Column(name = "interaction_mode", nullable = false, length = 12)
    private InteractionMode interactionMode;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonProperty("interactionInitiatorRole")
    @JoinColumn(name = "interaction_initiator_role_id", nullable = false)
    private Role interactionInitiatorRole;

    @NotNull
    @JsonProperty("interactionMedium1")
    @Enumerated(EnumType.STRING)
    @Column(name = "interaction_medium1", nullable = false, length = 9)
    private InteractionMedium interactionMedium1;

    @JsonProperty("interactionMedium2")
    @Enumerated(EnumType.STRING)
    @Column(name = "interaction_medium2", nullable = true, length = 9)
    private InteractionMedium interactionMedium2;

    @JsonProperty("interactionMedium3")
    @Enumerated(EnumType.STRING)
    @Column(name = "interaction_medium3", nullable = true, length = 9)
    private InteractionMedium interactionMedium3;

    @JsonProperty("interactionMedium4")
    @Enumerated(EnumType.STRING)
    @Column(name = "interaction_medium4", nullable = true, length = 9)
    private InteractionMedium interactionMedium4;


    public Interaction() { }

    public Interaction(@NotNull String name, @NotNull ActivityType type, @NotNull String preconditions,
                       @NotNull String postconditions, @NotNull Develops develop, @NotNull InteractionMode interactionMode,
                       @NotNull Role interactionInitiatorRole, @NotNull InteractionMedium interactionMedium1,
                       Role... parties) {
        super(name, type, preconditions, postconditions, develop, parties);
        this.interactionMode = interactionMode;
        this.interactionInitiatorRole = interactionInitiatorRole;
        this.interactionMedium1 = interactionMedium1;
    }


    public void setInteractionMode(InteractionMode interactionMode) {
        this.interactionMode = interactionMode;
    }

    public InteractionMode getInteractionMode() {
        return interactionMode;
    }

    public void setInteractionInitiatorRole(Role interactionInitiatorRole) {
        this.interactionInitiatorRole = interactionInitiatorRole;
    }

    public Role getInteractionInitiatorRole() {
        return interactionInitiatorRole;
    }

    public void setInteractionMedium1(InteractionMedium interactionMedium) {
        this.interactionMedium1 = interactionMedium;
    }

    public InteractionMedium getInteractionMedium1() {
        return interactionMedium1;
    }

    public void setInteractionMedium2(InteractionMedium interactionMedium2) {
        this.interactionMedium2 = interactionMedium2;
    }

    public InteractionMedium getInteractionMedium2() {
        return interactionMedium2;
    }

    public void setInteractionMedium3(InteractionMedium interactionMedium3) {
        this.interactionMedium3 = interactionMedium3;
    }

    public InteractionMedium getInteractionMedium3() {
        return interactionMedium3;
    }

    public void setInteractionMedium4(InteractionMedium interactionMedium4) {
        this.interactionMedium4 = interactionMedium4;
    }

    public InteractionMedium getInteractionMedium4() {
        return interactionMedium4;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            Interaction interaction = (Interaction) object;
            return Objects.equals(this.getInteractionMode(), interaction.getInteractionMode()) &&
                    Objects.equals(this.getInteractionInitiatorRole(), interaction.getInteractionInitiatorRole()) &&
                    Objects.equals(this.getInteractionMedium1(), interaction.getInteractionMedium1()) &&
                    Objects.equals(this.getInteractionMedium2(), interaction.getInteractionMedium2()) &&
                    Objects.equals(this.getInteractionMedium3(), interaction.getInteractionMedium3()) &&
                    Objects.equals(this.getInteractionMedium4(), interaction.getInteractionMedium4());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.getInteractionMode(), this.getInteractionInitiatorRole(),
                this.getInteractionMedium1(), this.getInteractionMedium2(), this.getInteractionMedium3(),
                this.getInteractionMedium4());
    }
}