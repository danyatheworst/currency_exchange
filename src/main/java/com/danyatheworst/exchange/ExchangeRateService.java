package main.java.com.danyatheworst.exchange;

import main.java.com.danyatheworst.currency.Currency;
import main.java.com.danyatheworst.currency.CurrencyRepository;
import main.java.com.danyatheworst.exceptions.NotFoundException;

import java.math.BigDecimal;

public class ExchangeRateService {
    private final CurrencyRepository currencyRepository = new CurrencyRepository();
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();

    public ExchangeRate save(ExchangeRateRequestDto exchangeRateRequestDto) {
        String baseCurrencyCode = exchangeRateRequestDto.baseCurrencyCode;
        String targetCurrencyCode = exchangeRateRequestDto.targetCurrencyCode;
        Currency baseCurrency = currencyRepository
                .findByCode(baseCurrencyCode)
                .orElseThrow(() -> new NotFoundException(
                        "Currency with code " + baseCurrencyCode + " is not presented in the database")
                );
        Currency targetCurrency = currencyRepository
                .findByCode(exchangeRateRequestDto.targetCurrencyCode)
                .orElseThrow(() -> new NotFoundException(
                        "Currency with code " + targetCurrencyCode + " is not presented in the database")
                );

        BigDecimal rate = new BigDecimal(exchangeRateRequestDto.rate);

        return exchangeRateRepository.save(new ExchangeRate(baseCurrency, targetCurrency, rate));
    }
}
