package ca.uqam.latece.evo.server.core.enumeration;

/**
 * Represents the signals sent by the frontend to the server.
 * Typically, these are to update the state of a BCIActivityInstance
 * FINISH - indicates that the client wants to end a specific BCIActivity.
 * SELECT - indicates that the client wants to select and work on a specific BCIActivity.
 *
 * @author Julien Champagne.
 */
public enum ClientEvent {
    FINISH,
    SELECT;

    /**
     * Converts the specified ClientEvent to its corresponding string representation.
     * @param clientEvent The ClientEvent to be converted.
     * @return The string representation of the specified ClientEvent.
     */
    public static String toString(ClientEvent clientEvent){
        return switch (clientEvent) {
            case FINISH -> "Finish";
            case SELECT -> "Select";
        };
    }
}