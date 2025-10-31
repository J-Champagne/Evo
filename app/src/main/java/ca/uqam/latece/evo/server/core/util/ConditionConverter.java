package ca.uqam.latece.evo.server.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConditionConverter {

    @Autowired
    private  StringToLambdaConverter stringToLambdaConverterUtil;

     public String convertCondition(String condition){
        // todo change logic when requirements are updated
        boolean convertedCondition = stringToLambdaConverterUtil.convertConditionStringToLambda(condition);
        return Boolean.toString(convertedCondition);

    }
}
