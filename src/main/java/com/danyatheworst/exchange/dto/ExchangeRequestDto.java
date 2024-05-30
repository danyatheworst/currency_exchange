package main.java.com.danyatheworst.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeRequestDto {
    private String baseCurrencyCode;
    private String targetCurrencyCode;
    private BigDecimal amount;
}
