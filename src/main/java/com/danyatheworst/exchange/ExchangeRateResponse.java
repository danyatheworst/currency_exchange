package main.java.com.danyatheworst.exchange;

import main.java.com.danyatheworst.currency.CurrencyResponseDto;

public class ExchangeRateResponse {
    public int id;
    public CurrencyResponseDto baseCurrency;
    public CurrencyResponseDto targetCurrency;
    public double rate;
}
