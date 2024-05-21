package main.java.com.danyatheworst.currency;

public class Currency {
    public Integer id;
    public String code;
    public String fullName;
    public String sign;

    public Currency(Integer id, String code, String fullName, String sign) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getSign() {
        return sign;
    }

    public String getFullName() {
        return fullName;
    }
}
