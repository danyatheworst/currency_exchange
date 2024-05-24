package main.java.com.danyatheworst.utils;

import main.java.com.danyatheworst.currency.Currency;
import main.java.com.danyatheworst.currency.CurrencyRequestDto;
import main.java.com.danyatheworst.currency.CurrencyResponseDto;
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
}
