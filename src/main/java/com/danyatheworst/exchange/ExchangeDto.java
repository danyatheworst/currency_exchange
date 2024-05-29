package main.java.com.danyatheworst.exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.java.com.danyatheworst.currency.Currency;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeDto {
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
}