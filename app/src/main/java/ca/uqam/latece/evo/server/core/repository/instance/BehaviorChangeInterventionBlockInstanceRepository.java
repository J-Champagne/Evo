package ca.uqam.latece.evo.server.core.repository.instance;

import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.instance.BehaviorChangeInterventionBlockInstance;
import ca.uqam.latece.evo.server.core.repository.EvoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BehaviorChangeInterventionBlockInstance repository creates CRUD implementation at runtime automatically.
 * @author Julien Champagne.
 */
@Repository
public interface BehaviorChangeInterventionBlockInstanceRepository extends EvoRepository<BehaviorChangeInterventionBlockInstance> {
    /**
     * Finds BehaviorChangeInterventionBlockInstance entities by their stage.
     * @param stage TimeCycle.
     * @return List<BehaviorChangeInterventionBlockInstance> with the given stage.
     * @throws IllegalArgumentException if stage is null.
     */
    List<BehaviorChangeInterventionBlockInstance> findByStage(TimeCycle stage);

    /**
     * Finds BehaviorChangeInterventionBlockInstance entities by their BCIActivityInstance id.
     * @param id Long.
     * @return List<BehaviorChangeInterventionBlockInstance> with the given BCIActivityInstance id.
     * @throws IllegalArgumentException if id is null.
     */
    List<BehaviorChangeInterventionBlockInstance> findByActivitiesId(Long id);

    /**
     * Finds BehaviorChangeInterventionBlockInstance entities by their associated BehaviorChangeInterventionBlock id.
     * @param id the id of the BehaviorChangeInterventionBlock associated with the intervention instances.
     * @return a list of BehaviorChangeInterventionBlockInstance objects associated with the specified BehaviorChangeInterventionBlock id.
     * @throws IllegalArgumentException if the id is null.
     */
    List<BehaviorChangeInterventionBlockInstance> findByBehaviorChangeInterventionBlockId(Long id);
}
