package ca.uqam.latece.evo.server.core.service;

import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.BCIModule;
import ca.uqam.latece.evo.server.core.model.ModuleComposedActivity;
import ca.uqam.latece.evo.server.core.repository.ModuleComposedActivityRepository;
import ca.uqam.latece.evo.server.core.util.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * ModuleComposedActivity Service.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Service
@Transactional
public class ModuleComposedActivityService extends AbstractEvoService<ModuleComposedActivity> {
    private static final Logger logger = LoggerFactory.getLogger(ModuleComposedActivityService.class);

    @Autowired
    private ModuleComposedActivityRepository moduleComposedActivityRepository;

    /**
     * Creates a ModuleComposedActivity in the database.
     * @param evoModel The ModuleComposedActivity entity.
     * @return The saved ModuleComposedActivity.
     * @throws IllegalArgumentException in case the given ModuleComposedActivity is null.
     */
    @Override
    public ModuleComposedActivity create(ModuleComposedActivity evoModel) {
        ModuleComposedActivity saved = this.save(evoModel);
        logger.info("ModuleComposedActivity created: {}", saved);
        return saved;
    }

    /**
     * Update a ModuleComposedActivity in the database.
     * @param evoModel The ModuleComposedActivity entity.
     * @return The saved ModuleComposedActivity.
     * @throws IllegalArgumentException in case the given ModuleComposedActivity is null.
     */
    @Override
    public ModuleComposedActivity update(ModuleComposedActivity evoModel) {
        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateId(evoModel.getId());
        ModuleComposedActivity updated = this.save(evoModel);
        logger.info("ModuleComposedActivity updated: {}", updated);
        return updated;
    }

    /**
     * Method used to create or update an ModuleComposedActivity.
     * @param evoModel he ModuleComposedActivity entity.
     * @return The ModuleComposedActivity.
     */
    @Transactional
    @Override
    protected ModuleComposedActivity save(ModuleComposedActivity evoModel) {
        ObjectValidator.validateObject(evoModel);
        ObjectValidator.validateObject(evoModel.getComposedActivityBciModule());
        ObjectValidator.validateId(evoModel.getComposedActivityBciModule().getId());
        ObjectValidator.validateObject(evoModel.getComposedModuleBciActivity());
        ObjectValidator.validateId(evoModel.getComposedModuleBciActivity().getId());

        if (evoModel.getOrder() <= 0) {
            throw new IllegalArgumentException("Order must be greater than 0!");
        }

        return moduleComposedActivityRepository.save(evoModel);
    }

    /**
     * Checks if a ModuleComposedActivity entity with the specified id exists in the repository.
     * @param id the id of the ModuleComposedActivity to check for existence, must not be null.
     * @return true if a ModuleComposedActivity with the specified id exists, false otherwise.
     * @throws IllegalArgumentException if the id is null.
     */
    @Override
    public boolean existsById(Long id) {
        ObjectValidator.validateId(id);
        return moduleComposedActivityRepository.existsById(id);
    }

    /**
     * Deletes the ModuleComposedActivity with the given id.
     * <p>
     * If the ModuleComposedActivity is not found in the persistence store it is silently ignored.
     * @param id the unique identifier of the ModuleComposedActivity to be retrieved; must not be null or invalid.
     * @throws IllegalArgumentException in case the given id is null.
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        ObjectValidator.validateId(id);
        moduleComposedActivityRepository.deleteById(id);
        logger.info("ModuleComposedActivity deleted: {}", id);
    }

    /**
     * Retrieves a ModuleComposedActivity by its id.
     * @param id The ModuleComposedActivity Id to filter ModuleComposedActivity entities by, must not be null.
     * @return the ModuleComposedActivity with the given id or Optional#empty() if none found.
     * @throws IllegalArgumentException – if id is null.
     */
    @Override
    public ModuleComposedActivity findById(Long id) {
        ObjectValidator.validateObject(id);
        return moduleComposedActivityRepository.findById(id).orElse(null);
    }

     /**
     * Finds a list of ModuleComposedActivity entities by the BCIModule association.
     * @param bciModule The BCIModule object.
     * @return the ModuleComposedActivity with the given BCIModule.
     * @throws IllegalArgumentException if module is null.
     */
     public List<ModuleComposedActivity> findByComposedActivityBciModule(BCIModule bciModule) {
         List<ModuleComposedActivity> found = new ArrayList<>();

         if (bciModule == null) {
             throw new IllegalArgumentException("BCIModule cannot be null!");
         } else {
             found = moduleComposedActivityRepository.findByComposedActivityBciModule(bciModule);
         }

         return found;
     }

    /**
     * Finds a list of ModuleComposedActivity entities by the BCIModule Id.
     * @param id The BCIModule Id.
     * @return the ModuleComposedActivity with the given id.
     * @throws IllegalArgumentException if id is null.
     */
     public List<ModuleComposedActivity> findByComposedActivityBciModuleId(Long id) {
         ObjectValidator.validateObject(id);
         return moduleComposedActivityRepository.findByComposedActivityBciModuleId(id);
     }

    /**
     * Finds a list of ModuleComposedActivity entities by the BCIActivity association.
     * @param bciActivity The BCIActivity object.
     * @return the ModuleComposedActivity with the given BCIActivity.
     * @throws IllegalArgumentException if module is null.
     */
    public List<ModuleComposedActivity> findByComposedModuleBciActivity(BCIActivity bciActivity) {
        List<ModuleComposedActivity> found = new ArrayList<>();

        if (bciActivity == null) {
            throw new IllegalArgumentException("BCIActivity cannot be null!");
        } else {
            found = moduleComposedActivityRepository.findByComposedModuleBciActivity(bciActivity);
        }

        return found;
    }

    /**
     * Finds a list of ModuleComposedActivity entities by the BCIActivity Id.
     * @param id The BCIActivity Id.
     * @return the ModuleComposedActivity with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    public List<ModuleComposedActivity> findByComposedModuleBciActivityId(Long id) {
        ObjectValidator.validateObject(id);
        return moduleComposedActivityRepository.findByComposedModuleBciActivityId(id);
    }

    /**
     * Gets all ModuleComposedActivity.
     * @return all ModuleComposedActivity.
     */
    @Override
    public List<ModuleComposedActivity> findAll() {
        return moduleComposedActivityRepository.findAll().stream().toList();
    }
}
