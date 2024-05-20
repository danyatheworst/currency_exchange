package main.java.com.danyatheworst.exceptions;

import java.sql.SQLException;

public class UnknownException extends RuntimeException {
    public UnknownException() {
        super("Something happened to database");
    }
}
