package main.java.com.danyatheworst.currency;

import main.java.com.danyatheworst.BaseRepository;
import main.java.com.danyatheworst.exceptions.CurrencyAlreadyExistsException;
import main.java.com.danyatheworst.DataSource;
import main.java.com.danyatheworst.exceptions.UnknownException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepository extends BaseRepository {
    public List<Currency> getAll() {
        try {
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
        } catch (SQLException e) {
            throw new UnknownException();
        }
    }

    public Optional<Currency> getBy(String code) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from Currencies where Code = ?"
            );
            preparedStatement.setString(1, code);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Currency currency = new Currency(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("fullName"),
                        rs.getString("sign"));
                preparedStatement.close();
                return Optional.of(currency);
            }
            preparedStatement.close();
            return Optional.empty();
        } catch (SQLException e) {
            throw new UnknownException();
        }
    }

    public int create(Currency currency) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO currencies (Code, FullName, Sign) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
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
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                throw new CurrencyAlreadyExistsException();
            }
            throw new UnknownException();
        }
    }
}
