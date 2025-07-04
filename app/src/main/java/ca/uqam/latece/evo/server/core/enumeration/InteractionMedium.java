package ca.uqam.latece.evo.server.core.enumeration;

/**
 * Represents the Interaction Medium of Interaction.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public enum InteractionMedium {
    MESSAGING,
    EMAIL,
    VOICE,
    VIDEO;

    /**
     * Convert the InteractionMedium to String.
     * @param interactionMedium The selected type.
     * @return Return the InteractionMedium in string format.
     */
    public static String toString(InteractionMedium interactionMedium) {
        return switch (interactionMedium) {
            case MESSAGING -> "Messaging";
            case EMAIL -> "Email";
            case VOICE -> "Voice";
            case VIDEO -> "Video";
        };
    }

}
