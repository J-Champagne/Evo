package ca.uqam.latece.evo.server.core.model.instance;

import ca.uqam.latece.evo.server.core.model.AbstractEvoModel;
import ca.uqam.latece.evo.server.core.model.Activity;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

/**
 * ActivityInstance class.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@MappedSuperclass
public abstract class ActivityInstance extends AbstractEvoModel {
    /**
     * Gets the Activity Instance Status.
     */
    public abstract String getStatus();

    /**
     * Sets the Activity Instance Status.
     */
    public abstract void setStatus(String status);

    /**
     * Gets the Entry Date of the Activity Instance.
     */
    public abstract LocalDate getEntryDate();

    /**
     * Sets the Entry Date of the Activity Instance.
     */
    public abstract void setEntryDate(LocalDate entryDate);

    /**
     * Gets the Exit Date of the Activity Instance.
     */
    public abstract LocalDate getExitDate();

    /**
     * Sets the Exit Date of the Activity Instance.
     */
    public abstract void setExitDate(LocalDate exitDate);


//    /**
//     * Sets an Activity.
//     */
    //TBD needs evaluation
    //public abstract void setActivity(Activity activity);

}
