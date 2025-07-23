package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * ActivityInstance class.
 * @author Edilton Lima dos Santos.
 * @author Julien Champagne
 */
@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "sub_type", include = JsonTypeInfo.As.PROPERTY, visible = true)
@JsonSubTypes({@JsonSubTypes.Type(value = BCIActivityInstance.class, name = "BCIActivityInstance")})
@JsonPropertyOrder({"id", "status", "entryDate", "exitDate"})
@Inheritance(strategy = InheritanceType.JOINED)
public class ActivityInstance extends AbstractEvoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_instance_id")
    private Long id;

    @Column(name = "activity_instance_status", length = 128)
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "activity_instance_entry_date")
    private LocalDate entryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "activity_instance_exit_date")
    private LocalDate exitDate;

    public ActivityInstance() {}

    public ActivityInstance(String status) {
        this.status = status;
        this.entryDate = LocalDate.now();
    }

    public ActivityInstance(@NotNull String status, LocalDate entryDate, LocalDate exitDate) {
        this.status = status;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getExitDate() {
        return this.exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            ActivityInstance activityInstance = (ActivityInstance) object;
            return Objects.equals(this.getStatus(), activityInstance.getStatus()) &&
                    Objects.equals(this.getEntryDate(), activityInstance.getEntryDate()) &&
                    Objects.equals(this.getExitDate(), activityInstance.getExitDate());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getStatus(), this.getEntryDate(), this.getExitDate());
    }
}