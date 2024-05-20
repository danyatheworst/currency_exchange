package main.java.com.danyatheworst.exceptions;

import java.sql.SQLException;

public class UnknownException extends ApplicationException {
    public UnknownException() {
        super("Something happened to database", 500);
    }
}
