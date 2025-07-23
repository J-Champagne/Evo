package ca.uqam.latece.evo.server.core.enumeration;

/**
 * Represents the aspects of change in a given context.
 * STARTED - indicates that the change process has begun.
 * TERMINATED - indicates that the change process has concluded.
 * FAILED - indicates that the change process ended unsuccessfully.
 * STALLED - indicates that the change process is currently halted or stuck.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public enum ChangeAspect {
    STARTED,
    TERMINATED,
    FAILED,
    STALLED;

    /**
     * Converts the specified ChangeAspect to its corresponding string representation.
     * @param changeAspect The ChangeAspect to be converted.
     * @return The string representation of the specified ChangeAspect.
     */
    public static String toString(ChangeAspect changeAspect){
        return switch (changeAspect) {
            case STARTED -> "Started";
            case TERMINATED -> "Terminated";
            case FAILED -> "Failed";
            case STALLED -> "Stalled";
        };
    }
}
