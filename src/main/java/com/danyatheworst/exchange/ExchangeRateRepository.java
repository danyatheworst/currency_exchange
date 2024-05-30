package main.java.com.danyatheworst.exchange;

import main.java.com.danyatheworst.BaseRepository;
import main.java.com.danyatheworst.currency.CrudRepository;
import main.java.com.danyatheworst.currency.Currency;
import main.java.com.danyatheworst.exceptions.EntityAlreadyExistsException;
import main.java.com.danyatheworst.exceptions.DatabaseOperationException;
import main.java.com.danyatheworst.exceptions.NotFoundException;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRateRepository extends BaseRepository implements CrudRepository<ExchangeRate> {
    public List<ExchangeRate> findAll() {
        String query =
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ExchangeRate> exchangeRates = new ArrayList<>();

            while (resultSet.next()) {
                exchangeRates.add(getExchangeRate(resultSet));
            }
            return exchangeRates;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to read all currency exchanges from the database");
        }
    }

    public Optional<ExchangeRate> findByCodes(String baseCurrencyCode, String targetCurrencyCode) {
        String query = """
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
                        INNER JOIN main.Currencies tc on er.TargetCurrencyId=tc.ID
                        WHERE bc.Code = ? AND tc.Code = ?;
                        """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, baseCurrencyCode);
            preparedStatement.setString(2, targetCurrencyCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(getExchangeRate(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to get the currency exchange from the database");
        }
    }

    public ExchangeRate save(ExchangeRate exchangeRate) {
        String query = "INSERT INTO ExchangeRates (baseCurrencyId, targetCurrencyId, Rate) VALUES (?, ?, ?) RETURNING id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, exchangeRate.getBaseCurrency().getId());
            preparedStatement.setInt(2, exchangeRate.getTargetCurrency().getId());
            preparedStatement.setBigDecimal(3, exchangeRate.getRate());
            ResultSet resultSet = preparedStatement.executeQuery();

            exchangeRate.setId(resultSet.getInt("ID"));
            return exchangeRate;
        } catch (SQLException e) {
            if (e instanceof SQLiteException) {
                if (((SQLiteException) e).getResultCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE) {
                    throw new EntityAlreadyExistsException("Exchange rate " + exchangeRate.getBaseCurrency().getCode() + " - "
                            + exchangeRate.getTargetCurrency().getCode() + " already exists");
                }
            }
            throw new DatabaseOperationException("Failed to save the currency exchange in the database");
        }
    }

    public ExchangeRate update(ExchangeRate exchangeRate) {
        Currency baseCurrency = exchangeRate.getBaseCurrency();
        Currency targetCurrency = exchangeRate.getTargetCurrency();
        String query = "UPDATE ExchangeRates SET rate = ? WHERE BaseCurrencyId = ? AND TargetCurrencyId = ? RETURNING ID";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBigDecimal(1, exchangeRate.getRate());
            preparedStatement.setInt(2, baseCurrency.getId());
            preparedStatement.setInt(3, targetCurrency.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            exchangeRate.setId(resultSet.getInt("ID"));
            exchangeRate.setRate(exchangeRate.getRate());
            return exchangeRate;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update the currency exchange + "
                    + baseCurrency.getCode() + " - " + targetCurrency.getCode() + " in the database");
        }
    }

    private static ExchangeRate getExchangeRate(ResultSet resultSet) throws SQLException {
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
