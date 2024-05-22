package main.java.com.danyatheworst.exchange;

import main.java.com.danyatheworst.currency.CurrencyResponse;

public class ExchangeRateResponse {
    public int id;
    public CurrencyResponse baseCurrency;
    public CurrencyResponse targetCurrency;
    public double rate;
}
