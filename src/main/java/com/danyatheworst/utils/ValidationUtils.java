package main.java.com.danyatheworst.utils;

import main.java.com.danyatheworst.currency.CurrencyRequestDto;
import main.java.com.danyatheworst.exceptions.InvalidParameterException;

import java.util.Currency;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationUtils {

    private static Set<String> currencyCodes;

    public static void validateCurrencyCode(String code) {
        if (code == null || code.length() != 3) {
            throw new InvalidParameterException("Code must contain exactly 3 letters");
        }

        if (currencyCodes == null) {
            currencyCodes =   Currency.getAvailableCurrencies()
                    .stream()
                    .map(Currency::getCurrencyCode)
                    .collect(Collectors.toSet());
        }

        if (!currencyCodes.contains(code)) {
            throw new InvalidParameterException("Currency code must be in ISO 4217 format");
        }
    }

    private static void validatePresence(String parameter) {
        if (parameter.isBlank()) {
            throw new InvalidParameterException(parameter + " parameter missing");
        }
    }

    public static void validate(CurrencyRequestDto currencyRequestDto) {
        validateCurrencyCode(currencyRequestDto.code);
        validatePresence(currencyRequestDto.name);
        validatePresence(currencyRequestDto.sign);
    }
}
