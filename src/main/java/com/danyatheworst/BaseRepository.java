package main.java.com.danyatheworst;

import main.java.com.danyatheworst.exceptions.DatabaseOperationException;

import java.sql.Connection;
import java.sql.SQLException;

public class BaseRepository {
    protected static final Connection connection;

    static {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new DatabaseOperationException("something wrong with database");
        }
    }

}
