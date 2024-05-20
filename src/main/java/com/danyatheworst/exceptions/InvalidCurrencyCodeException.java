package main.java.com.danyatheworst.exceptions;

public class InvalidCurrencyCodeException extends ApplicationException {
    public InvalidCurrencyCodeException(String message) {
        super(message + " " + "is not a valid code", 404);
    }
}
