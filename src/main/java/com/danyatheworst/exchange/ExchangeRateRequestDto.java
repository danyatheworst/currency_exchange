package main.java.com.danyatheworst.exchange;

public class ExchangeRateRequestDto {
    public String baseCurrencyCode;
    public String targetCurrencyCode;
    public String rate;

    public ExchangeRateRequestDto(String rate, String targetCurrencyCode, String baseCurrencyCode) {
        this.rate = rate;
        this.targetCurrencyCode = targetCurrencyCode;
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public String getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public String getTargetCurrencyCode() {
        return targetCurrencyCode;
    }

    public void setTargetCurrencyCode(String targetCurrencyCode) {
        this.targetCurrencyCode = targetCurrencyCode;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
