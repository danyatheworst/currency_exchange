package main.java.com.danyatheworst.exceptions;

public class ApplicationException extends RuntimeException {
    public final int status;
    public ApplicationException(String message, int status) {
        super(message);
        this.status = status;
    }
}
