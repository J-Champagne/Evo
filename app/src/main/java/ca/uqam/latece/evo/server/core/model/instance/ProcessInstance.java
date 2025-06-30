package ca.uqam.latece.evo.server.core.model.instance;

import jakarta.persistence.MappedSuperclass;

import java.util.List;

/**
 * ProcessInstance class.
 * @author Edilton Lima dos Santos.
 * @author Julien Champagne
 */
@MappedSuperclass
public abstract class ProcessInstance extends ActivityInstance {

    public abstract List<ActivityInstance> getActivityInstances();

    public abstract void setActivityInstances(List<ActivityInstance> activityInstance);
}
