package ca.uqam.latece.evo.server.core.enumeration;

/**
 * Represents the status of a bci instance.
 * <p>
 * ONGOING - indicates that the bci instance is currently being done
 * COMPLETED - indicates that the bci instance was accomplished.
 * NOTSTARTED - indicates that the bci instance was not yet started.
 *
 * @author Julien Champagne.
 */
public enum Status {
    ONGOING,
    COMPLETED,
    NOTSTARTED
}
