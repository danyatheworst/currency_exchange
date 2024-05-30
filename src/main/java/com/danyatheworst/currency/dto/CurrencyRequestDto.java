package main.java.com.danyatheworst.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CurrencyRequestDto {
    private String code;
    private String name;
    private String sign;
}
