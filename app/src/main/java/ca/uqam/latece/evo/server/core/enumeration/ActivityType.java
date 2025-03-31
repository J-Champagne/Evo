package ca.uqam.latece.evo.server.core.enumeration;

/**
 * Represents the type of activity.
 * The ActivityType enum defines four types of activity:
 * GOAL_SETTING - for goal setting activity.
 * GOAL_MONITORING - for goal monitoring activity.
 * DIAGNOSING - for diagnosing activity.
 * LEARNING - for learning activity.
 * PERFORMING - for performing activity.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public enum ActivityType {
    GOAL_SETTING,
    GOAL_MONITORING,
    DIAGNOSING,
    LEARNING,
    PERFORMING,
    BCI_ACTIVITY;


    /**
     * Convert the ActivityType to String.
     * @param activityType The selected type.
     * @return Return the ActivityType in string format.
     */
    public static String toString(ActivityType activityType) {
        return switch (activityType) {
            case GOAL_SETTING -> "GOAL_SETTING";
            case GOAL_MONITORING -> "GOAL_MONITORING";
            case DIAGNOSING -> "DIAGNOSING";
            case LEARNING -> "LEARNING";
            case PERFORMING -> "PERFORMING";
            case BCI_ACTIVITY -> "BCI_ACTIVITY";
        };
    }
}
