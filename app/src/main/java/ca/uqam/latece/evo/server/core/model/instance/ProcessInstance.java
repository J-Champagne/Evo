package ca.uqam.latece.evo.server.core.model.instance;

import jakarta.persistence.MappedSuperclass;

import java.util.ArrayList;
import java.util.List;

/**
 * ProcessInstance class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@MappedSuperclass
public abstract class ProcessInstance extends ActivityInstance {

    private List<ActivityInstance> activityInstanceList;

    public ProcessInstance(ActivityInstance... activityInstance) {
        activityInstanceList = new ArrayList<>();
        this.setActivityInstanceList(activityInstance);
    }

    public List<ActivityInstance> getActivityInstanceList() {
        return activityInstanceList;
    }

    public void setActivityInstanceList(ActivityInstance... activityInstance) {
        if (activityInstance != null) {
            if (activityInstance.length > 0) {
                this.activityInstanceList.addAll(List.of(activityInstance));
            }
        }
    }
}
