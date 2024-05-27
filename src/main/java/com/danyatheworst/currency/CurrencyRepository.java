package main.java.com.danyatheworst.currency;

import main.java.com.danyatheworst.BaseRepository;
import main.java.com.danyatheworst.exceptions.EntityAlreadyExistsException;
import main.java.com.danyatheworst.exceptions.DatabaseOperationException;
import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepository extends BaseRepository implements CrudRepository<Currency> {
    public List<Currency> findAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Currencies");) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Currency> currencies = new ArrayList<>();
            while (resultSet.next()) {
                currencies.add(getCurrency(resultSet));
            }
            return currencies;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to read all currencies from the database");
        }
    }

    public Optional<Currency> findByCode(String code) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from Currencies where Code = ?"
        );) {
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getCurrency(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to read currency with code " + code + " from the database");
        }
    }

    public Currency save(Currency currency) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO currencies (Code, FullName, Sign) VALUES (?, ?, ?) RETURNING *"
        )) {
            preparedStatement.setString(1, currency.code);
            preparedStatement.setString(2, currency.fullName);
            preparedStatement.setString(3, currency.sign);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new DatabaseOperationException(
                        "Failed to save currency with code " + currency.getCode() + " to the database"
                );
            }

            return getCurrency(resultSet);
        } catch (SQLException e) {
            if (e instanceof SQLiteException) {
                if (((SQLiteException) e).getResultCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE) {
                    throw new EntityAlreadyExistsException("Currency with code " + currency.getCode() + " already" +
                            " exists");
                }
            }
            throw new DatabaseOperationException(
                    "Failed to save currency with code " + currency.getCode() + " to the database"
            );
        }
    }

    public static Currency getCurrency(ResultSet resultSet) throws SQLException {
        return new Currency(resultSet.getInt("id"),
                resultSet.getString("code"),
                resultSet.getString("fullName"),
                resultSet.getString("sign"));
    }
}
