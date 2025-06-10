package ca.uqam.latece.evo.server.core.enumeration;

/**
 * Represents the type of outcome.
 * <p>
 * SUCCESSFUL - indicates a success, all/most objectives were achieved
 * UNSUCCESSFUL - indicates a failure, all/most objectives were not achieved.
 * PARTIALLYSUCCESSFUL - indicates a partial success, some objectives were achieved and failed.
 *
 * @author Julien Champagne.
 */
public enum OutcomeType {
    SUCCESSFUL,
    UNSUCCESSFUL,
    PARTIALLYSUCCESSFUL
}
