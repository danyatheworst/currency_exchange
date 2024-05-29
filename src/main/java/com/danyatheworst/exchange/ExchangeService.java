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
        ExchangeRate exchangeRate = this.findExchangeRate(exchangeRequestDto)
                .orElseThrow(() -> new NotFoundException("Exchange rate " + exchangeRequestDto.getBaseCurrencyCode() + " - "
                        + exchangeRequestDto.getTargetCurrencyCode() + " is not found the database"));

        BigDecimal rate = exchangeRate.getRate();
        BigDecimal convertedAmount = exchangeRequestDto.getAmount()
                .multiply(rate, DECIMAL64)
                .setScale(2, RoundingMode.HALF_EVEN);
        return new ExchangeDto(
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                rate,
                exchangeRequestDto.getAmount(),
                convertedAmount);
    }

    private Optional<ExchangeRate> findExchangeRate(ExchangeRequestDto exchangeRequestDto) {
        String baseCurrencyCode = exchangeRequestDto.getBaseCurrencyCode();
        String targetCurrencyCode = exchangeRequestDto.getTargetCurrencyCode();
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

        Optional<BigDecimal> rate = this.getDirectRate(baseCurrencyCode, targetCurrencyCode);

        if (rate.isEmpty()) {
            rate = this.getIndirectRate(targetCurrencyCode, baseCurrencyCode);
        }

        if (rate.isEmpty()) {
            rate = this.getCrossRate(baseCurrencyCode, targetCurrencyCode);
        }

        return rate.map(bigDecimal -> new ExchangeRate(baseCurrency, targetCurrency, bigDecimal));
    };

    private Optional<BigDecimal> getDirectRate(String baseCurrencyCode, String targetCurrencyCode) {
        return this.exchangeRateRepository
                .findByCodes(baseCurrencyCode, targetCurrencyCode)
                .map(ExchangeRate::getRate);
    }

    private Optional<BigDecimal> getIndirectRate(String targetCurrencyCode, String baseCurrencyCode)  {
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository
                .findByCodes(targetCurrencyCode, baseCurrencyCode);
        if (exchangeRate.isPresent()) {
            BigDecimal rate = exchangeRate.get().getRate();
            return Optional.of(BigDecimal.ONE.divide(rate, DECIMAL64).setScale(6, RoundingMode.HALF_EVEN));
        }
        return Optional.empty();
    }

    private Optional<BigDecimal> getCrossRate(String baseCurrencyCode, String targetCurrencyCode) {
        Optional<ExchangeRate> crossBaseExchangeRate =
                this.exchangeRateRepository.findByCodes("USD", baseCurrencyCode);
        Optional<ExchangeRate> crossTargetExchangeRate =
                this.exchangeRateRepository.findByCodes("USD", targetCurrencyCode);

        if (crossBaseExchangeRate.isPresent() && crossTargetExchangeRate.isPresent()) {
            BigDecimal rate = crossTargetExchangeRate.get()
                    .getRate()
                    .divide(crossBaseExchangeRate.get().getRate(), DECIMAL64)
                    .setScale(6, RoundingMode.HALF_EVEN);
            return Optional.of(rate);
        }
        return Optional.empty();
    }
}
