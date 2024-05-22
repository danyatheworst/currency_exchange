package main.java.com.danyatheworst.exchange;

import main.java.com.danyatheworst.currency.Currency;

import java.math.BigDecimal;

public class ExchangeRate {
    public int id;
    public Currency baseCurrency;
    public Currency targetCurrency;
    public BigDecimal rate;

    public ExchangeRate(int id, Currency baseCurrency, Currency targetCurrency, BigDecimal rate) {
        this.id = id;
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }
}
