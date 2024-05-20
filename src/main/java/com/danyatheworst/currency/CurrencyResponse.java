package main.java.com.danyatheworst.currency;

public class CurrencyResponse {
    public int id;
    public final String code;
    public final String name;
    public final String sign;

    public CurrencyResponse(Integer id, String code, String name, String sign) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.sign = sign;
    }
}
