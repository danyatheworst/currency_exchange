package main.java.com.danyatheworst.currency;

import main.java.com.danyatheworst.BaseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepository extends BaseRepository {

    public List<Currency> getAll() throws SQLException, ClassNotFoundException {
        this.makeConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from Currencies");

        List<Currency> currencies = new ArrayList<>();
        while (rs.next()) {
            Currency currency = new Currency(
                    rs.getInt("id"),
                    rs.getString("code"),
                    rs.getString("fullName"),
                    rs.getString("sign"));

            currencies.add(currency);
        }
        return currencies;
    }

    public Optional<Currency> getBy(String code) throws SQLException, ClassNotFoundException {
        this.makeConnection();
        Statement statement = connection.createStatement();
        code = "'" + code + "'";
        ResultSet rs = statement.executeQuery("select * from Currencies where Code == " + code);

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
        Statement statement = connection.createStatement();
        String code = "'" + currency.code + "'";
        String fullName = "'" + currency.fullName + "'";
        String sign = "'" + currency.sign + "'";
        int affectedRows = statement.executeUpdate(
                "insert into Currencies (code, fullName, sign) VALUES (" + code + ", " + fullName + ", " + sign + ")"
        );

        if (affectedRows > 0) {
            ResultSet resultSet = statement.executeQuery("select last_insert_rowid()");
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 2222;
    }
}
