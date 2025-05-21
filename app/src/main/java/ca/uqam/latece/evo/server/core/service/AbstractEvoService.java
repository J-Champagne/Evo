package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(AbstractEvoService.class);
    protected static final String ERROR_NAME_ALREADY_REGISTERED = "Name already registered!";

    public T create(@NotNull T evoModel) {
        ObjectValidator.validateObject(evoModel);
        return this.save(evoModel);
    }

    public T update(@NotNull T evoModel) {
        ObjectValidator.validateObject(evoModel);
        return this.save(evoModel);
    }

    /**
     * Create duplicate name Exception to EvoModel with a name already registered in the database.
     * @param evoModel the EvoModel entity.
     * @param duplicatedNameText property value duplicated in the database.
     * @return an exception object.
     */
    protected IllegalArgumentException createDuplicatedNameException(AbstractEvoModel evoModel, String duplicatedNameText) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_NAME_ALREADY_REGISTERED +
                this.getClass().getSimpleName() + " Name: " + duplicatedNameText);
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    protected abstract T save(@NotNull T evoModel);
    public abstract boolean existsById(@NotNull Long id);
    public abstract void deleteById(@NotNull Long id);
    public abstract T findById(@NotNull Long id);
    public abstract List<T> findAll();
}
