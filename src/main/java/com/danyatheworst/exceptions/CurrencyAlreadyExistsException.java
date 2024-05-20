package main.java.com.danyatheworst.exceptions;

import java.sql.SQLException;

public class CurrencyAlreadyExistsException extends RuntimeException {
    public CurrencyAlreadyExistsException() {
        super("Currency with such code already exists");
    }
}
