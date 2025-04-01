package ca.uqam.latece.evo.server.core.service.instance;

import ca.uqam.latece.evo.server.core.model.instance.AbstractEvoInstance;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Abstract Evo Service Instance.
 * @param <T> the Evo instance (AbstractEvoInstance).
 * @version 1.0
 * @author Julien Champagne.
 */
@Service
public abstract class AbstractEvoServiceInstance <T extends AbstractEvoInstance>{
    public T create(@NotNull T evoInstance) {
        ObjectValidator.validateObject(evoInstance);
        return this.save(evoInstance);
    }

    public T update(@NotNull T evoInstance) {
        ObjectValidator.validateObject(evoInstance);
        return this.save(evoInstance);
    }

    protected abstract T save(@NotNull T evoInstance);
    public abstract boolean existsById(@NotNull Long id);
    public abstract void deleteById(@NotNull Long id);
    public abstract T findById(@NotNull Long id);
    public abstract List<T> findAll();
}