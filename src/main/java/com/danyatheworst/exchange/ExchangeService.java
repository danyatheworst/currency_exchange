package main.java.com.danyatheworst.exchange;

import main.java.com.danyatheworst.currency.Currency;
import main.java.com.danyatheworst.currency.CurrencyRepository;
import main.java.com.danyatheworst.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static java.math.MathContext.DECIMAL64;

public class ExchangeService {
    private final CurrencyRepository currencyRepository = new CurrencyRepository();
    private final ExchangeRateRepository exchangeRateRepository = new ExchangeRateRepository();

    public ExchangeDto exchange(ExchangeRequestDto exchangeRequestDto) {
        String baseCurrencyCode = exchangeRequestDto.baseCurrencyCode;
        String targetCurrencyCode = exchangeRequestDto.targetCurrencyCode;
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

        BigDecimal amount = exchangeRequestDto.amount;

        //B-T is present
        Optional<ExchangeRate> baseTargetExchangeRate =
                this.exchangeRateRepository.findByCodes(baseCurrencyCode, targetCurrencyCode);
        if (baseTargetExchangeRate.isPresent()) {
            BigDecimal rate = baseTargetExchangeRate.get().rate;
            return new ExchangeDto(baseCurrency, targetCurrency, rate, amount, convertСurrency(amount, rate));
        }

        //T-B is present
        Optional<ExchangeRate> targetBaseExchangeRate =
                this.exchangeRateRepository.findByCodes(targetCurrencyCode, baseCurrencyCode);
        if (targetBaseExchangeRate.isPresent()) {
            BigDecimal reversedRate = targetBaseExchangeRate.get().rate;
            BigDecimal rate = BigDecimal.ONE.divide(reversedRate, DECIMAL64)
                    .setScale(6, RoundingMode.HALF_EVEN);
            return new ExchangeDto(baseCurrency, targetCurrency, rate, amount, convertСurrency(amount, rate));
        }

        //USD-A and USD-B are present
        Optional<ExchangeRate> crossBaseExchangeRate =
                this.exchangeRateRepository.findByCodes("USD", baseCurrencyCode);
        Optional<ExchangeRate> crossTargetExchangeRate =
                this.exchangeRateRepository.findByCodes("USD", targetCurrencyCode);

        if (crossBaseExchangeRate.isPresent() && crossTargetExchangeRate.isPresent()) {
            BigDecimal rate = crossTargetExchangeRate.get()
                    .rate
                    .divide(crossBaseExchangeRate.get().rate, DECIMAL64)
                    .setScale(6, RoundingMode.HALF_EVEN);
            return new ExchangeDto(baseCurrency, targetCurrency, rate, amount, convertСurrency(amount, rate));
        }

        throw new NotFoundException("asd");
    }

    private BigDecimal convertСurrency(BigDecimal amount, BigDecimal rate) {
        return rate.multiply(amount).setScale(2, RoundingMode.HALF_EVEN);
    }
}
