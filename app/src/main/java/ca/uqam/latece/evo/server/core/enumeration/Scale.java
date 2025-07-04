package ca.uqam.latece.evo.server.core.enumeration;

/**
 * Represents the Scale of Assessment.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public enum Scale {
    LETTER,
    _20,
    _100;

    /**
     * Convert the Scale to String.
     * @param scale The selected scale.
     * @return Return the Scale in string format.
     */
    public static String toString(Scale scale) {
        return switch (scale) {
            case LETTER -> "Letter";
            case _20 -> "20";
            case _100 -> "100";
        };
    }

}
