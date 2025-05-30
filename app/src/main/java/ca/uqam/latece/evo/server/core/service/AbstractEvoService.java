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

    /**
     * Creates a evoModel in the database.
     * @param evoModel must not be null.
     * @return the saved entity.
     * @throws IllegalArgumentException in case the given evoModel is null.
     */
    public T create(@NotNull T evoModel) {
        ObjectValidator.validateObject(evoModel);
        return this.save(evoModel);
    }

    /**
     * Updates a evoModel in the database.
     * @param evoModel must not be null.
     * @return the saved entity.
     * @throws IllegalArgumentException in case the given evoModel is null.
     */
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
        final String EMPTY_SPACE = " ";
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(ERROR_NAME_ALREADY_REGISTERED +
                EMPTY_SPACE + evoModel.getClass().getSimpleName() + " Name: " + duplicatedNameText);
        logger.error(illegalArgumentException.getMessage(), illegalArgumentException);
        return illegalArgumentException;
    }

    /**
     * Saves a given evoModel.
     * @param evoModel must not be null.
     * @return the saved entity.
     * @throws IllegalArgumentException in case the given evoModel is null.
     */
    protected abstract T save(@NotNull T evoModel);

    /**
     * Returns whether an AbstractEvoModel with the given id exists.
     * @param id must not be null.
     * @return true if an AbstractEvoModel with the given id exists, false otherwise.
     * @throws IllegalArgumentException in case the given id is null.
     */
    public abstract boolean existsById(@NotNull Long id);

    /**
     * Deletes the AbstractEvoModel with the given id. If the AbstractEvoModel is not found in the persistence
     * store it is silently ignored.
     * @param id must not be null.
     * @throws IllegalArgumentException in case the given id is null.
     */
    public abstract void deleteById(@NotNull Long id);

    /**
     * Retrieves an AbstractEvoModel by its id.
     * @param id must not be null.
     * @return the AbstractEvoModel with the given id.
     * @throws IllegalArgumentException in case the given id is null.
     */
    public abstract T findById(@NotNull Long id);

    /**
     * Returns all instances of the AbstractEvoModel.
     * @return all AbstractEvoModel.
     */
    public abstract List<T> findAll();
}
