package ca.uqam.latece.evo.server.core.util;

import java.util.regex.Pattern;

/**
 * The ObjectValidator class provides a set of static utility methods
 * for validating objects, strings, and email addresses. It includes mechanisms
 * to ensure that inputs are not null, blank, or invalid according to certain rules.
 *
 * @since 22.01.2025
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public class ObjectValidator {

    private static final String ERROR_ID_NULL = "The Id is null!";
    private static final String ERROR_OBJECT_NULL = "The Object is null!";
    private static final String ERROR_EMPTY_MESSAGE = "The string is empty!";
    private static final String ERROR_INVALID_EMAIL = "The email is invalid!";

    /**
     * A compiled regular expression pattern used to validate email addresses.
     *
     * The pattern ensures that:
     * - An email address has a valid format with a local part (before the '@') and domain part (after the '@').
     * - The local part allows alphanumeric characters, underscores, hyphens, Unicode letters,
     *   and dots (not consecutively or at the start or end).
     * - The domain part does not start with a hyphen, allows alphanumeric characters, hyphens,
     *   and Unicode letters, and ends with a top-level domain of at least two characters.
     * - The total length of the local part is limited to 64 characters.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
                    + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$"
    );


    /**
     * Validates that the provided ID is not null.
     * Throws an IllegalArgumentException if the ID is null.
     * @param id the ID to validate.
     * @throws IllegalArgumentException if the ID is null.
     */
    public static void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(ERROR_ID_NULL);
        }
    }

    /**
     * Validates that the provided object is not null.
     * Throws an IllegalArgumentException if the object is null.
     * @param object the object to validate.
     * @throws IllegalArgumentException if the object is null.
     */
    public static void validateObject(Object object) {
        if (object == null) {
            throw new IllegalArgumentException(ERROR_OBJECT_NULL);
        }
    }

    /**
     * Validates a String by checking if it is null or blank and throws an exception with specified messages.
     * @param value the string to validate.
     * @param nullMessage the message to include in the exception if the string is null.
     * @param emptyMessage the message to include in the exception if the string is blank.
     * @throws IllegalArgumentException if the string is null or blank.
     */
    public static void validateString(String value, String nullMessage, String emptyMessage) {
        if (value == null) {
            throw new IllegalArgumentException(nullMessage);
        } else if (value.isBlank()) {
            throw new IllegalArgumentException(emptyMessage);
        }
    }

    /**
     * Validates a String by checking if it is null or blank and throws an exception with default messages.
     * Utilizes predefined error messages for null and empty string validation.
     * @param value the string to validate.
     * @throws IllegalArgumentException if the string is null or blank.
     */
    public static void validateString(String value){
        validateString(value, ERROR_OBJECT_NULL, ERROR_EMPTY_MESSAGE);
    }

    /**
     * Validates that the provided email address is not null, not blank,
     * and matches a valid email address format. Throws an IllegalArgumentException
     * if the email is invalid.
     * @param email the email address to validate.
     * @throws IllegalArgumentException if the email is null, blank, or invalid.
     */
    public static void validateEmail(String email) {
        ObjectValidator.validateString(email);

        if (!EMAIL_PATTERN.matcher(email).matches()){
            throw new IllegalArgumentException(ERROR_INVALID_EMAIL);
        }
    }

}
