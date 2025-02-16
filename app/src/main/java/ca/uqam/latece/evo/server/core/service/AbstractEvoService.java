package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Abstract Evo Service.
 * @param <T> the Evo model (AbstractEvoModel).
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
public abstract class AbstractEvoService <T extends AbstractEvoModel> {
    public abstract T create(T evoModel);
    public abstract T update(T evoModel);
    public abstract boolean existsById(@NotNull Long id);
    public abstract void deleteById(@NotNull Long id);
    public abstract T findById(@NotNull Long id);
    public abstract List<T> findAll();
}
