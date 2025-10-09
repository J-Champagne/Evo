package ca.uqam.latece.evo.server.core.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.MappedSuperclass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Evo model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@MappedSuperclass
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY, setterVisibility = JsonAutoDetect.Visibility.NONE, fieldVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class AbstractEvoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -2420346134960559062L;
    private static final Logger logger = LogManager.getLogger(AbstractEvoModel.class);


    public abstract void setId(Long id);
    public abstract Long getId();

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractEvoModel model = (AbstractEvoModel) object;
        return Objects.equals(this.getId(), model.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    /**
     * Converts the AbstractEvoModel object into its JSON string representation.
     * The JSON includes all properties annotated with @JsonPropertyOrder or @JsonProperty.
     * @return A string representing the entity object in JSON format.
     */
    public String toString() {
        return this.evoModelToJson(this);
    }

    /**
     * Converts the AbstractEvoModel object into its JSON string representation.
     * @param object the AbstractEvoModel.
     * @return A string representing the entity object in JSON format.
     */
    private String evoModelToJson(AbstractEvoModel object)  {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try{
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }

        return json;
    }
}
