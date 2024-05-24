package main.java.com.danyatheworst.exchange;

import main.java.com.danyatheworst.currency.CurrencyResponseDto;

import java.math.BigDecimal;

public class ExchangeRateResponseDto {
    public int id;
    public CurrencyResponseDto baseCurrency;
    public CurrencyResponseDto targetCurrency;
    public BigDecimal rate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CurrencyResponseDto getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(CurrencyResponseDto baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public CurrencyResponseDto getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(CurrencyResponseDto targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
