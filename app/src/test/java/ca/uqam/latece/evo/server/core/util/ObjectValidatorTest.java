package ca.uqam.latece.evo.server.core.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * The ObjectValidator test class for the {@link ObjectValidator}, responsible for testing its various functionalities.
 * @version 1.0
 * @author Edilton Lima dos Santos.
 */
@SpringBootTest
@ContextConfiguration(classes = {ObjectValidator.class})
public class ObjectValidatorTest {

    @Autowired
    private ObjectValidator objectValidator;


    @Test
    public void validateIdNull() {
        Assert.assertThrows(IllegalArgumentException.class, () -> objectValidator.validateId(null));
    }

    @Test
    public void validateObjectNull() {
        Assert.assertThrows(IllegalArgumentException.class, () -> ObjectValidator.validateObject(null));
    }

    @Test
    public void validateStringEmpty() {
        Assert.assertThrows(IllegalArgumentException.class, () -> objectValidator.validateString(""));
    }

    @Test
    public void validateStringNull() {
        Assert.assertThrows(IllegalArgumentException.class, () -> objectValidator.validateString(null));
    }

    @Test
    public void validateEmailTest() {
        Assert.assertThrows(IllegalArgumentException.class, () -> objectValidator.validateEmail(null));
        Assert.assertThrows(IllegalArgumentException.class, () -> objectValidator.validateEmail("mail"));
    }
}
