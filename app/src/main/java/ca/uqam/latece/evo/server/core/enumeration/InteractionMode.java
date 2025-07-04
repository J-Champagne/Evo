package ca.uqam.latece.evo.server.core.enumeration;

/**
 * Represents the Interaction Mode of Interaction.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public enum InteractionMode {
    SYNCHRONOUS,
    ASYNCHRONOUS,
    HYBRID;

    /**
     * Convert the InteractionMode to String.
     * @param interactionMode The selected type.
     * @return Return the InteractionMode in string format.
     */
    public static String toString(InteractionMode interactionMode) {
        return switch (interactionMode) {
            case SYNCHRONOUS -> "Synchronous";
            case ASYNCHRONOUS -> "Asynchronous";
            case HYBRID -> "Hybrid";
        };
    }

}
