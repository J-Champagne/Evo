package ca.uqam.latece.evo.server.core.model.instance;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.MappedSuperclass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Evo Instance class.
 * @version 1.0
 * @author Julien Champagne.
 */
@MappedSuperclass
public abstract class AbstractEvoInstance implements Serializable {
    @Serial
    private static final long serialVersionUID = -2420346134960559062L;
    private static final Logger logger = LogManager.getLogger(AbstractEvoInstance.class);

    public abstract void setId(Long id);

    public abstract Long getId();

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || this.getClass() != object.getClass()) return false;
        AbstractEvoInstance instance = (AbstractEvoInstance) object;
        return Objects.equals(this.getId(), instance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

    /**
     * Converts the AbstractEvoInstance object into its JSON string representation.
     * The JSON includes all properties annotated with @JsonPropertyOrder or @JsonProperty.
     * @return A string representing the entity object in JSON format.
     */
    public String toString() {
        return this.evoInstanceToJson(this);
    }

    /**
     * Converts the AbstractEvoModel object into its JSON string representation.
     * @param evoInstance the AbstractEvoInstance.
     * @return A string representing the entity object in JSON format.
     */
    private String evoInstanceToJson(AbstractEvoInstance evoInstance)  {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try{
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(evoInstance);
        }
        catch (IOException e) {
            logger.error(e.getMessage());
        }

        return json;
    }
}