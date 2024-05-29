package main.java.com.danyatheworst.utils;

import main.java.com.danyatheworst.currency.CurrencyRequestDto;
import main.java.com.danyatheworst.exceptions.InvalidParameterException;
import main.java.com.danyatheworst.exchange.ExchangeRatesRequestDto;
import main.java.com.danyatheworst.exchange.ExchangeRequestDto;

import java.math.BigDecimal;
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

    private static void validatePresence(String parameter, String parameterName) {
        if (parameter == null || parameter.isBlank()) {
            throw new InvalidParameterException(parameterName + " parameter missing");
        }
    }

    public static void validate(CurrencyRequestDto currencyRequestDto) {
        validateCurrencyCode(currencyRequestDto.code);
        validatePresence(currencyRequestDto.name, "name");
        validatePresence(currencyRequestDto.sign, "sign");
    }

    public static void validate(ExchangeRatesRequestDto exchangeRateRequestDto) {
        validateCurrencyCode(exchangeRateRequestDto.baseCurrencyCode);
        validateCurrencyCode(exchangeRateRequestDto.targetCurrencyCode);
    }

    public static void validate(ExchangeRequestDto exchangeRequestDto) {
        validateCurrencyCode(exchangeRequestDto.baseCurrencyCode);
        validateCurrencyCode(exchangeRequestDto.targetCurrencyCode);
        if (exchangeRequestDto.amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidParameterException("Amount to exchange must be represented as a positive number");
        }
    }
}
