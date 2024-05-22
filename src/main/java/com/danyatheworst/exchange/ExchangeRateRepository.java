package main.java.com.danyatheworst.exchange;

import main.java.com.danyatheworst.BaseRepository;
import main.java.com.danyatheworst.currency.Currency;
import main.java.com.danyatheworst.exceptions.UnknownException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateRepository extends BaseRepository {
    public List<ExchangeRate> getAll() {
        try {
            String sql =
                    """
                            SELECT
                                er.ID as id,
                                bc.ID as baseId,
                                bc.Code as baseCode,
                                bc.FullName as baseName,
                                bc.Sign as baseSign,
                                tc.ID as targetId,
                                tc.Code as targetCode,
                                tc.FullName as targetName,
                                tc.Sign as targetSign,
                                er.Rate as rate
                            from ExchangeRates er
                            INNER JOIN main.Currencies bc on er.BaseCurrencyId=bc.ID
                            INNER JOIN main.Currencies tc on er.TargetCurrencyId=tc.ID;
                            """;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ExchangeRate> exchangeRates = new ArrayList<>();

            while (resultSet.next()) {
                exchangeRates.add(parseExchangeRate(resultSet));
            }
            return exchangeRates;
        } catch (SQLException e) {
            throw new UnknownException();
        }

    }
    private static ExchangeRate parseExchangeRate(ResultSet resultSet) throws SQLException {
        return new ExchangeRate(
                resultSet.getInt("id"),
                new Currency(
                        resultSet.getInt("baseId"),
                        resultSet.getString("baseCode"),
                        resultSet.getString("baseName"),
                        resultSet.getString("baseSign")
                ),
                new Currency(
                        resultSet.getInt("targetId"),
                        resultSet.getString("targetCode"),
                        resultSet.getString("targetName"),
                        resultSet.getString("targetSign")
                ),
                resultSet.getBigDecimal("rate")
        );
    }
}
