package ca.uqam.latece.evo.server.core.request;

import ca.uqam.latece.evo.server.core.enumeration.ExecutionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Represents a request for an activity instance in the system. ActivityInstanceRequest extends the base class EvoRequest,
 * inheriting its properties and behaviors. It includes specific details related to an activity instance, such as its
 * execution status, entry date, and exit date.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Getter
public class ActivityInstanceRequest extends EvoRequest {

    protected ExecutionStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    protected LocalDate entryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    protected LocalDate exitDate;

    @Builder(builderMethodName = "activityInstanceRequestBuilder")
    public ActivityInstanceRequest(Long id, ExecutionStatus status, LocalDate entryDate, LocalDate exitDate) {
        super(id);
        this.status = status;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }
}
