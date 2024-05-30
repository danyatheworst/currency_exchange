package main.java.com.danyatheworst.utils;

import main.java.com.danyatheworst.currency.Currency;
import main.java.com.danyatheworst.currency.dto.CurrencyRequestDto;
import main.java.com.danyatheworst.currency.dto.CurrencyResponseDto;
import main.java.com.danyatheworst.exchange.ExchangeRate;
import main.java.com.danyatheworst.exchange.dto.ExchangeRateResponseDto;
import org.modelmapper.ModelMapper;

public class MappingUtils {
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.createTypeMap(CurrencyRequestDto.class, Currency.class)
                .addMapping(CurrencyRequestDto::getName, Currency::setFullName);
    }

    public static Currency convertToEntity(CurrencyRequestDto currencyRequestDto) {
        return modelMapper.map(currencyRequestDto, Currency.class);
    }

    public static CurrencyResponseDto convertToDto(Currency currency) {
        return modelMapper.map(currency, CurrencyResponseDto.class);
    }

    public static ExchangeRateResponseDto convertToDto(ExchangeRate exchangeRate) {
        return modelMapper.map(exchangeRate, ExchangeRateResponseDto.class);
    }
}
