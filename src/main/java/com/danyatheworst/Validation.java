package main.java.com.danyatheworst;

import main.java.com.danyatheworst.exceptions.InvalidCurrencyCodeException;
import main.java.com.danyatheworst.exceptions.ParameterMissingException;

public class Validation {

    public static void isCodeValid(String code) throws InvalidCurrencyCodeException {
        if (code == null || code.length() != 3) {
            throw new InvalidCurrencyCodeException(code);
        }
        for (char ch : code.toCharArray()) {
            if (!Character.isLetter(ch) || Character.UnicodeBlock.of(ch) != Character.UnicodeBlock.BASIC_LATIN) {
                throw new InvalidCurrencyCodeException(code);
            }
        }
    }

    public static void parameterPresence(String parameter, String name) throws ParameterMissingException {
        if (parameter.isBlank()) {
            throw new ParameterMissingException(name);
        }
    }
}
