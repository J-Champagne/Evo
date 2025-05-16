package ca.uqam.latece.evo.server.core.util;

import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The DateFormatter class provides a set of static utility methods for formatter the date in string format to LocalDate object.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
public class DateFormatter {
    private static final Logger logger = LogManager.getLogger(DateFormatter.class);

    // Date formats.
    private static final DateTimeFormatter MM_dd_yyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter dd_MM_yyyy = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Get an instance of LocalDate from a text string using the MM-dd-yyyy format.
     * @param date The date to parse, not null.
     * @return The parsed local date, not null.
     */
    public static @NotNull LocalDate convertDateStrTo_MM_dd_yyyy(@NotNull String date) {
        return convertDateStrToLocalDate(date, MM_dd_yyyy);
    }

    /**
     * Get an instance of LocalDate from a text string using the yyyy-MM-dd format.
     * @param date The date to parse, not null.
     * @return The parsed local date, not null.
     */
    public static @NotNull LocalDate convertDateStrTo_yyyy_MM_dd(@NotNull String date) {
        return convertDateStrToLocalDate(date, yyyy_MM_dd);
    }

    /**
     * Get an instance of LocalDate from a text string using the dd-MM-yyyy format.
     * @param date The date to parse, not null.
     * @return The parsed local date, not null.
     */
    public static @NotNull LocalDate convertDateStrTo_dd_MM_yyyy(@NotNull String date) {
        return convertDateStrToLocalDate(date, dd_MM_yyyy);
    }

    /**
     * Get an instance of LocalDate from a text string using a specific formatter.
     * The Date in text is parsed using the formatter, returning a date.
     * @param dateStr The date to parse, not null.
     * @param dateFormatter The formatter to use, not null.
     * @return The parsed local date, not null.
     * @throws IllegalArgumentException An exception thrown when an error occurs during parsing. This exception includes
     * the date in String format being parsed and the error index.
     */
    public static @NotNull LocalDate convertDateStrToLocalDate(@NotNull String dateStr, @NotNull DateTimeFormatter dateFormatter) {
        LocalDate date = null;

        try {
            date = LocalDate.parse(dateStr, dateFormatter);
            logger.info("Date formatted successfully: {}", date);
        } catch (DateTimeParseException e) {
            logger.error(e.getMessage());
            throw new IllegalArgumentException("The date is invalid!", e);
        }

        return date;
    }
}
