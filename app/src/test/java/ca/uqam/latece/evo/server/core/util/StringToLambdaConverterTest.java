package ca.uqam.latece.evo.server.core.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration(classes = {StringToLambdaConverter.class})
public class StringToLambdaConverterTest {

    @Test
    void convertConditionStringToLambdaTest() {
        String condition = "x -> true";
        Assertions.assertTrue(StringToLambdaConverter.convertConditionStringToLambda(condition));
    }

    @Test
    void convertConditionStringToLambdaTestBlankString() {
        String condition = "";
        Assertions.assertTrue(StringToLambdaConverter.convertConditionStringToLambda(condition));
    }

    @Test
    void convertConditionStringToLambdaTestIncorrectString() {
        String condition = "AAA";
        Assertions.assertFalse(StringToLambdaConverter.convertConditionStringToLambda(condition));
    }
}
