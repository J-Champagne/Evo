package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.BCIModule;
import ca.uqam.latece.evo.server.core.model.ModuleComposedActivity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ModuleComposedActivity repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface ModuleComposedActivityRepository extends EvoRepository<ModuleComposedActivity> {

    /**
     * Finds a list of ModuleComposedActivity entities by the BCIModule association.
     * @param composedActivityBciModule The BCIModule object.
     * @return the ModuleComposedActivity with the given BCIModule.
     * @throws IllegalArgumentException if module is null.
     */
    List<ModuleComposedActivity> findByComposedActivityBciModule(BCIModule composedActivityBciModule);

    /**
     * Finds a list of ModuleComposedActivity entities by the BCIModule Id.
     * @param composedActivityBciModuleId The BCIModule Id.
     * @return the ModuleComposedActivity with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    List<ModuleComposedActivity> findByComposedActivityBciModuleId(Long composedActivityBciModuleId);

    /**
     * Finds a list of ModuleComposedActivity entities by the BCIActivity association.
     * @param composedModuleBciActivity The BCIActivity object.
     * @return the ModuleComposedActivity with the given BCIActivity.
     * @throws IllegalArgumentException if module is null.
     */
    List<ModuleComposedActivity> findByComposedModuleBciActivity(BCIActivity composedModuleBciActivity);

    /**
     * Finds a list of ModuleComposedActivity entities by the BCIActivity Id.
     * @param composedModuleBciActivityId The BCIActivity Id.
     * @return the ModuleComposedActivity with the given id.
     * @throws IllegalArgumentException if id is null.
     */
    List<ModuleComposedActivity> findByComposedModuleBciActivityId(Long composedModuleBciActivityId);
}
