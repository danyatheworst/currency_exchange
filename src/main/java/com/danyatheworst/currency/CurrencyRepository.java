package main.java.com.danyatheworst.currency;

import main.java.com.danyatheworst.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepository {
    private static final Connection connection;

    static {
        try {
            connection = DataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Currency> getAll() throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("select * from Currencies");
        ResultSet rs = preparedStatement.executeQuery();

        List<Currency> currencies = new ArrayList<>();
        while (rs.next()) {
            Currency currency = new Currency(
                    rs.getInt("id"),
                    rs.getString("code"),
                    rs.getString("fullName"),
                    rs.getString("sign")
            );
            currencies.add(currency);
        }
        preparedStatement.close();
        return currencies;
    }

    public Optional<Currency> getBy(String code) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from Currencies where Code = ?"
        );
        preparedStatement.setString(1, code);
        ResultSet rs = preparedStatement.executeQuery();
        preparedStatement.close();

        if (rs.next()) {
            return Optional.of(new Currency(
                    rs.getInt("id"),
                    rs.getString("code"),
                    rs.getString("fullName"),
                    rs.getString("sign")));
        }

        return Optional.empty();
    }

    public int create(Currency currency) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO currencies (Code, FullName, Sign) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, currency.code);
        preparedStatement.setString(2, currency.fullName);
        preparedStatement.setString(3, currency.sign);
        preparedStatement.executeUpdate();

        ResultSet rs = preparedStatement.getGeneratedKeys();
        rs.next();

        int id = rs.getInt(1);
        preparedStatement.close();
        return id;
    }
}
