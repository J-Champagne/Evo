package ca.uqam.latece.evo.server.core.request;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import lombok.Getter;

import java.io.Serial;

/**
 * Represents a base request model extending the {@link AbstractEvoModel}.
 * </p>
 * The EvoRequest class serves as a foundational model for defining request entities in the system. It includes common
 * properties such as an identifier (id) and provides implementations for setting and retrieving this identifier.
 * </p>
 * Subclasses of EvoRequest can inherit its properties and behavior to represent specific request types, allowing for
 * standardized handling of identifiers across different models.
 * </p>
 * It is designed to be used as a request payload for the REST API. Also, this class uses the Builder Patter to build a
 * Request object. The Builder Pattern helps to construct complex objects step-by-step without writing multiple
 * constructors or using bigger of lists parameters.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Getter
public class EvoRequest extends AbstractEvoModel {
    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public EvoRequest(Long id) {
        this.id = id;
    }
}
