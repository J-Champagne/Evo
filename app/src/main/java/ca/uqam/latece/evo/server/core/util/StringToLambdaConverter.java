package ca.uqam.latece.evo.server.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import pl.joegreen.lambdaFromString.LambdaCreationException;
import pl.joegreen.lambdaFromString.LambdaFactory;
import pl.joegreen.lambdaFromString.TypeReference;

import java.util.function.Function;

/**
 * This class is a wrapper for the pl.joegreen library that provides methods for converting a String to a lambda expression.
 * Should properly handle exceptions thrown by this library.
 */
@Component
public class StringToLambdaConverter {
    private static final Logger logger = LogManager.getLogger(StringToLambdaConverter.class);

    private static final String ERROR_CONDITION_TO_LAMBDA = "ERROR, entry or exit condition could not be converted to lambda expression";

    /**
     * Converts a String to a lambda expression.
     * @param condition the String representation of an entry or exit condition
     * @return true if the condition is blank. Returns false if the condition cannot be converted to a lambda expression.
     */
    public static boolean convertConditionStringToLambda(String condition) {
        boolean result = false;
        String TrueCondition = "x -> true";
        LambdaFactory lambdaFactory = LambdaFactory.get();
        Function<Boolean, Boolean> conditionLambda = null;

        try {
            if (!condition.isBlank()) {
                conditionLambda = lambdaFactory.createLambda(condition, new TypeReference<>() {});

            } else {
                conditionLambda = lambdaFactory.createLambda(TrueCondition, new TypeReference<>() {});
            }

            result = conditionLambda.apply(true);

        } catch (LambdaCreationException | NullPointerException e) {
            logger.error(ERROR_CONDITION_TO_LAMBDA);
        }

        return result;
    }
}