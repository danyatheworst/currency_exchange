package main.java.com.danyatheworst.exchange;

import main.java.com.danyatheworst.currency.Currency;
import main.java.com.danyatheworst.currency.CurrencyRepository;
import main.java.com.danyatheworst.exceptions.NotFoundException;
import main.java.com.danyatheworst.exchange.dto.ExchangeRatesRequestDto;

public class ExchangeRateService {
    private final CurrencyRepository currencyRepository = new CurrencyRepository();
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();

    public ExchangeRate save(ExchangeRatesRequestDto exchangeRateRequestDto) {
        String baseCurrencyCode = exchangeRateRequestDto.getBaseCurrencyCode();
        String targetCurrencyCode = exchangeRateRequestDto.getTargetCurrencyCode();
        Currency baseCurrency = this.currencyRepository
                .findByCode(baseCurrencyCode)
                .orElseThrow(() -> new NotFoundException(
                        "Currency with code " + baseCurrencyCode + " is not present in the database")
                );
        Currency targetCurrency = this.currencyRepository
                .findByCode(targetCurrencyCode)
                .orElseThrow(() -> new NotFoundException(
                        "Currency with code " + targetCurrencyCode + " is not present in the database")
                );


        return exchangeRateRepository.save(
                new ExchangeRate(baseCurrency, targetCurrency, exchangeRateRequestDto.getRate())
        );
    }

    public ExchangeRate update(ExchangeRatesRequestDto exchangeRateRequestDto) {
        //Duplicated code fragment — don't rush with refactoring
        String baseCurrencyCode = exchangeRateRequestDto.getBaseCurrencyCode();
        String targetCurrencyCode = exchangeRateRequestDto.getTargetCurrencyCode();
        Currency baseCurrency = this.currencyRepository
                .findByCode(baseCurrencyCode)
                .orElseThrow(() -> new NotFoundException(
                        "Currency with code " + baseCurrencyCode + " is not present in the database")
                );
        Currency targetCurrency = this.currencyRepository
                .findByCode(targetCurrencyCode)
                .orElseThrow(() -> new NotFoundException(
                        "Currency with code " + targetCurrencyCode + " is not present in the database")
                );

        return exchangeRateRepository.update(
                new ExchangeRate(baseCurrency, targetCurrency, exchangeRateRequestDto.getRate())
        );
    }
}
