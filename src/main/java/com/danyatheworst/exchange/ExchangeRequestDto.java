package main.java.com.danyatheworst.exchange;

import java.math.BigDecimal;

public class ExchangeRequestDto {
    public String baseCurrencyCode;
    public String targetCurrencyCode;
    public BigDecimal amount;

    public ExchangeRequestDto(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) {
        this.baseCurrencyCode = baseCurrencyCode;
        this.targetCurrencyCode = targetCurrencyCode;
        this.amount = amount;
    }
}
