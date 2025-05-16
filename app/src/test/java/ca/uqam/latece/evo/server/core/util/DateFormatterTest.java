package ca.uqam.latece.evo.server.core.util;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The DateFormatter test class for the {@link DateFormatter}, responsible for testing its various functionalities.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@SpringBootTest
@ContextConfiguration(classes = {DateFormatter.class})
public class DateFormatterTest {
    // Date formats.
    public static final DateTimeFormatter MM_dd_yyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public static final DateTimeFormatter yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Test
    public void convertDateStrToLocalDate() {
        LocalDate convertToLocalDate = DateFormatter.convertDateStrToLocalDate("01/26/2023", MM_dd_yyyy);
        Assertions.assertNotNull(convertToLocalDate);
        Assertions.assertEquals(26, convertToLocalDate.getDayOfMonth());
        Assertions.assertEquals(1, convertToLocalDate.getMonthValue());
        Assertions.assertEquals(2023, convertToLocalDate.getYear());
    }

    @Test
    public void convertDateStrToLocalDateParseException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> DateFormatter.convertDateStrToLocalDate("01-26", yyyy_MM_dd));
    }

    @Test
    public void convertDateStrTo_MM_dd_yyyy(){
        LocalDate convertToLocalDate = DateFormatter.convertDateStrTo_MM_dd_yyyy("01/26/2023");
        Assertions.assertNotNull(convertToLocalDate);
        Assertions.assertEquals(26, convertToLocalDate.getDayOfMonth());
        Assertions.assertEquals(1, convertToLocalDate.getMonthValue());
        Assertions.assertEquals(2023, convertToLocalDate.getYear());
    }

    @Test
    public void convertDateStrTo_yyyy_MM_dd(){
        LocalDate convertToLocalDate = DateFormatter.convertDateStrTo_yyyy_MM_dd("2023/01/26");
        Assertions.assertNotNull(convertToLocalDate);
        Assertions.assertEquals(26, convertToLocalDate.getDayOfMonth());
        Assertions.assertEquals(1, convertToLocalDate.getMonthValue());
        Assertions.assertEquals(2023, convertToLocalDate.getYear());
    }

    @Test
    public void convertDateStrTo_dd_MM_yyyy(){
        LocalDate convertToLocalDate = DateFormatter.convertDateStrTo_dd_MM_yyyy("26/01/2023");
        Assertions.assertNotNull(convertToLocalDate);
        Assertions.assertEquals(26, convertToLocalDate.getDayOfMonth());
        Assertions.assertEquals(1, convertToLocalDate.getMonthValue());
        Assertions.assertEquals(2023, convertToLocalDate.getYear());
    }
}
