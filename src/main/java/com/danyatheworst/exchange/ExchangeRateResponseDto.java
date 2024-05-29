package main.java.com.danyatheworst.exchange;

import lombok.Getter;
import lombok.Setter;
import main.java.com.danyatheworst.currency.CurrencyResponseDto;

import java.math.BigDecimal;

@Setter
@Getter
public class ExchangeRateResponseDto {
    private int id;
    private CurrencyResponseDto baseCurrency;
    private CurrencyResponseDto targetCurrency;
    private BigDecimal rate;
}
