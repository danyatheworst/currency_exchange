package main.java.com.danyatheworst.currency;

public class Currency {
    public Integer id;
    public final String code;
    public final String fullName;
    public final String sign;

    public Currency(Integer id, String code, String fullName, String sign) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }
}
