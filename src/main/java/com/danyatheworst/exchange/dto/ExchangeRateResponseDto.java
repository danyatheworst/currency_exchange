package main.java.com.danyatheworst.exchange.dto;

import lombok.Getter;
import lombok.Setter;
import main.java.com.danyatheworst.currency.dto.CurrencyResponseDto;

import java.math.BigDecimal;

@Setter
@Getter
public class ExchangeRateResponseDto {
    private int id;
    private CurrencyResponseDto baseCurrency;
    private CurrencyResponseDto targetCurrency;
    private BigDecimal rate;
}
