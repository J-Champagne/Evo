package ca.uqam.latece.evo.server.core.model;

import jakarta.persistence.MappedSuperclass;

/**
 * PlannedProcess class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@MappedSuperclass
public abstract class PlannedProcess extends Activity {

    /**
     * Sets the Activity.
     */
    public abstract void setActivity(Activity activity);

    /**
     * Gets the Activity.
     */
   public abstract Activity getActivity();
}
