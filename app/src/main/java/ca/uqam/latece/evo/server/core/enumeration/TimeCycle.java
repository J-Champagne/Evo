package ca.uqam.latece.evo.server.core.enumeration;

/**
 * Represents the Time Cycle of Composed Of class.
 * <p>
 * The TimeCycle enum defines three levels of time
 * BEGINNING - for the beginning of the time cycle.
 * MIDDLE - for the middle of the time cycle.
 * END - for the end of the time cycle.
 * UNSPECIFIED - for the unspecified time cycle.
 * <p>
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public enum TimeCycle {
    BEGINNING,
    MIDDLE,
    END,
    UNSPECIFIED;

    /**
     * Convert the TimeCycle to String.
     * @param timeCycle The selected TimeCycle.
     * @return Return the TimeCycle in string format.
     */
    public static String toString(TimeCycle timeCycle) {
        return switch (timeCycle) {
            case BEGINNING -> "Beginning";
            case MIDDLE -> "Middle";
            case END -> "End";
            case UNSPECIFIED -> "Unspecified";
        };
    }

}
