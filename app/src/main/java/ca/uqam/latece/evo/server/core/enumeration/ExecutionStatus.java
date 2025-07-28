package ca.uqam.latece.evo.server.core.enumeration;

/**
 * Represents the execution status of a process or activity.
 * The ExecutionStatus enum defines the following states:
 * READY - indicates that the process or activity is prepared to start.
 * IN_PROGRESS - indicates that the process or activity is currently ongoing.
 * SUSPENDED - indicates that the process or activity has been temporarily paused.
 * STALLED - indicates that the process or activity is stuck or not progressing.
 * FINISHED - indicates that the process or activity has been completed.
 * UNKNOWN - indicates an undefined or unspecified status.
 *
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public enum ExecutionStatus {
    READY,
    IN_PROGRESS,
    SUSPENDED,
    STALLED,
    FINISHED,
    UNKNOWN;

    /**
     * Converts the specified ExecutionStatus to its corresponding string representation.
     * @param executionStatus The ExecutionStatus to be converted.
     * @return The string representation of the specified ExecutionStatus.
     */
    public static String toString(ExecutionStatus executionStatus){
        return switch (executionStatus) {
            case READY -> "Ready";
            case IN_PROGRESS -> "In Progress";
            case SUSPENDED -> "Suspended";
            case STALLED -> "Stalled";
            case FINISHED -> "Finished";
            case UNKNOWN -> "Unknown";
        };
    }
}
