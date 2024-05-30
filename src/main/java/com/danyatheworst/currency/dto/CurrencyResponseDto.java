package main.java.com.danyatheworst.currency.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CurrencyResponseDto {
    private int id;
    private String code;
    private String name;
    private String sign;
}
