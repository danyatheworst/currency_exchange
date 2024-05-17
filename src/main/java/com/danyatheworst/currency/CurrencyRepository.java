package main.java.com.danyatheworst.currency;

import main.java.com.danyatheworst.BaseRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepository extends BaseRepository {
//        TODO: close connection

    public List<Currency> getAll() throws SQLException, ClassNotFoundException {
        this.makeConnection();
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
        connection.close();
        return currencies;
    }

    public Optional<Currency> getBy(String code) throws SQLException, ClassNotFoundException {
        this.makeConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from Currencies where Code = ?"
        );
        preparedStatement.setString(1, code);
        ResultSet rs = preparedStatement.executeQuery();
        preparedStatement.close();
        connection.close();

        if (rs.next()) {
            return Optional.of(new Currency(
                    rs.getInt("id"),
                    rs.getString("code"),
                    rs.getString("fullName"),
                    rs.getString("sign")));
        }

        return Optional.empty();
    }

    public int create(Currency currency) throws SQLException, ClassNotFoundException {
        this.makeConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO currencies (Code, FullName, Sign) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS
        );
        preparedStatement.setString(1, currency.code);
        preparedStatement.setString(2, currency.fullName);
        preparedStatement.setString(3, currency.sign);

        int affectedRows = preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();

        if (affectedRows == 0 || !rs.next()) {
            //throw unique error exception
            throw new SQLException();
        }

        int id = rs.getInt("ID");
        preparedStatement.close();
        connection.close();
        return id;
    }
}
