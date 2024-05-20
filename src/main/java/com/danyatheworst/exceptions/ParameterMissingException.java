package main.java.com.danyatheworst.exceptions;

public class ParameterMissingException extends ApplicationException {
    public ParameterMissingException(String message) {
        super(message + " " + "parameter is missing", 404);
    }
}
