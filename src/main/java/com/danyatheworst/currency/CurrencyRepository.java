package main.java.com.danyatheworst.currency;

import main.java.com.danyatheworst.BaseRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRepository extends BaseRepository {

    public List<Currency> get() throws SQLException, ClassNotFoundException {
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
}
