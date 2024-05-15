package main.java.com.danyatheworst;

import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;


public class BaseRepository {
    protected static Connection connection;

    protected void makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        URL url = getClass().getClassLoader().getResource("currency_exchange.db");
        if (url != null) {
            try {
                connection = DriverManager.getConnection(String.format("jdbc:sqlite:" + url.toURI()));
            } catch (URISyntaxException e) {
                throw new RuntimeException("uri syntax error");
            }
        }
    }
}
