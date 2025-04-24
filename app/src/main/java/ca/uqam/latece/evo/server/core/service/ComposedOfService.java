package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.ComposedOf;
import ca.uqam.latece.evo.server.core.repository.ComposedOfRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * ComposedOf Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class ComposedOfService extends AbstractEvoService<ComposedOf> {
    private static final Logger logger = LoggerFactory.getLogger(ComposedOfService.class);

    @Autowired
    private ComposedOfRepository composedOfRepository;

    /**
     * Creates and saves a new ComposedOf if it does not already exist in the repository.
     * @param evoModel the ComposedOf object to be created and saved.
     * @return the saved ComposedOf object.
     * @throws IllegalArgumentException if the ComposedOf name already exists or
     * if the ComposedOf object or its name is null.
     */
    @Override
    @Transactional
    protected ComposedOf save(ComposedOf evoModel) {
        ObjectValidator.validateObject(evoModel);
        return composedOfRepository.save(evoModel);
    }

    /**
     * Checks if an entity with the specified identifier exists in the repository.
     * @param id the unique identifier of the entity to be checked for existence.
     * @return true if an entity with the given identifier exists, false otherwise.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return composedOfRepository.existsById(id);
    }

    /**
     * Deletes a ComposedOf from the repository by its unique identifier.
     * <p>Note: <p/>Validates the identifier before proceeding with the deletion.
     * @param id the unique identifier of the behavior performance to be deleted.
     * @throws IllegalArgumentException if the provided id is null.
     */
    @Override
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        composedOfRepository.deleteById(id);
        logger.info("ComposedOf deleted: {}", id);
    }

    /**
     * Retrieves a composedOf by id.
     * @param id the unique identifier of the composedOf to be retrieved.
     * @return the composedOf corresponding to the specified identifier.
     * @throws IllegalArgumentException if the provided id is null or invalid.
     * @throws EntityNotFoundException if the composedOf not found.
     */
    @Override
    public ComposedOf findById(Long id) {
        ObjectValidator.validateId(id);
        return composedOfRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("ComposedOf not found!"));
    }

    /**
     * Finds a list of ComposedOf entities by their timing.
     * @param timing the timing of the ComposedOf to search for.
     * @return a list of ComposedOf entities matching the specified timing.
     */
    public List<ComposedOf> findByTiming(TimeCycle timing) {
        return composedOfRepository.findByTiming(timing);
    }

    /**
     * Finds a list of ComposedOf entities by their order.
     * @param order the order of the ComposedOf to search for.
     * @return a list of ComposedOf entities matching the specified order.
     */
    public List<ComposedOf> findByOrder(int order) {
        return composedOfRepository.findByOrder(order);
    }

    /**
     * Retrieves all ComposedOf from the repository.
     * @return a list of all ComposedOf present in the repository.
     */
    @Override
    public List<ComposedOf> findAll() {
        return composedOfRepository.findAll().stream().toList();
    }
}
