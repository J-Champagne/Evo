package ca.uqam.latece.evo.server.core.repository;

import ca.uqam.latece.evo.server.core.enumeration.TimeCycle;
import ca.uqam.latece.evo.server.core.model.BCIActivity;
import ca.uqam.latece.evo.server.core.model.ComposedOf;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ComposedOf repository creates CRUD implementation at runtime automatically.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@Repository
public interface ComposedOfRepository extends EvoRepository<ComposedOf> {

    /**
     * Finds a list of ComposedOf entities by their timing.
     * @param timing the timing of the ComposedOf to search for.
     * @return a list of ComposedOf entities matching the specified timing.
     */
    List<ComposedOf> findByTiming(TimeCycle timing);

    /**
     * Finds a list of ComposedOf entities by their order.
     * @param order the order of the ComposedOf to search for.
     * @return a list of ComposedOf entities matching the specified order.
     */
    List<ComposedOf> findByOrder(int order);


    /**
     * Finds a list of ComposedOf entities based on the provided BCIActivity ID and BCI Block ID.
     * @param composedOfBciActivityId the ID of the BCIActivity associated with ComposedOf.
     * @param composedOfBciBlockId the ID of the Behavior Change Intervention Block associated with ComposedOf.
     * @return a list of ComposedOf entities matching the given BCIActivity ID and BCI Block ID.
     */
    List<ComposedOf> findByBciActivityComposedOf_Id_AndBciBlockComposedOf_Id(Long composedOfBciActivityId, Long composedOfBciBlockId);
}
