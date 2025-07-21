package ca.uqam.latece.evo.server.core.interfaces;

import ca.uqam.latece.evo.server.core.model.instance.ActivityInstance;

import java.util.List;

public interface ProcessInstance<A extends ActivityInstance> {
    /**
     *  Retrieves the collection of ActivityInstance.
     *  (i.e. a collection of BCIActivityInstance, BCIModuleInstance,
     *   BCIBlockInstance, BCIPhaseInstance, or BCIInstance)
     * @return List<ActivityInstance>
     */
    List<A> getActivities();

    /**
     * Inserts an ActivityInstance to a collection of ActivityInstance
     * @param activityInstance The activityInstance to add
     */
    void addActivity(A activityInstance);

    /**
     * Inserts many ActivityInstance entities to a collection of ActivityInstance
     * @param activityInstances The activityInstance entities to add
     */
    void addActivities(List<A> activityInstances);

    /**
     * Removes an ActivityInstance from a collection of ActivityInstance
     * @param activityInstance The activityInstance to be removed
     * @return boolean True if the activityInstance was removed from the collection of activities,
     *                 False otherwise
     */
    boolean removeActivity(A activityInstance);
}
