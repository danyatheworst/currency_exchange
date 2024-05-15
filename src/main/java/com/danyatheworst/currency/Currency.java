package main.java.com.danyatheworst.currency;

public class Currency {
    private final int id;
    private final String code;
    public final String fullName;
    private final String sign;

    public Currency(int id, String code, String fullName, String sign) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }
}
