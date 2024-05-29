package main.java.com.danyatheworst.utils;

import main.java.com.danyatheworst.exceptions.InvalidParameterException;

import java.math.BigDecimal;

public class ConvertingUtils {
    public static BigDecimal convert(String string, String exceptionMessage) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(string));
        } catch (NumberFormatException e) {
            throw new InvalidParameterException(exceptionMessage);
        }
    }
}
