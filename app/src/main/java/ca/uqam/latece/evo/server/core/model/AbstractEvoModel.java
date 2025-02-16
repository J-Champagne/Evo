package ca.uqam.latece.evo.server.core.model;

import jakarta.persistence.MappedSuperclass;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * Evo model class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@MappedSuperclass
public abstract class AbstractEvoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -2420346134960559062L;


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
}
