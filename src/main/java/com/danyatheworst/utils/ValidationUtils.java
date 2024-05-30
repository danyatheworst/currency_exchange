package main.java.com.danyatheworst.utils;

import main.java.com.danyatheworst.currency.dto.CurrencyRequestDto;
import main.java.com.danyatheworst.exceptions.InvalidParameterException;
import main.java.com.danyatheworst.exchange.dto.ExchangeRatesRequestDto;
import main.java.com.danyatheworst.exchange.dto.ExchangeRequestDto;

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
        validateCurrencyCode(currencyRequestDto.getCode());
        validatePresence(currencyRequestDto.getName(), "name");
        validatePresence(currencyRequestDto.getSign(), "sign");
    }

    public static void validate(ExchangeRatesRequestDto exchangeRateRequestDto) {
        validateCurrencyCode(exchangeRateRequestDto.getBaseCurrencyCode());
        validateCurrencyCode(exchangeRateRequestDto.getTargetCurrencyCode());
    }

    public static void validate(ExchangeRequestDto exchangeRequestDto) {
        validateCurrencyCode(exchangeRequestDto.getBaseCurrencyCode());
        validateCurrencyCode(exchangeRequestDto.getTargetCurrencyCode());
        if (exchangeRequestDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidParameterException("Amount to exchange must be represented as a positive number");
        }
    }
}
