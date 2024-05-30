package main.java.com.danyatheworst.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class ExchangeRatesRequestDto {
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private BigDecimal rate;
}
