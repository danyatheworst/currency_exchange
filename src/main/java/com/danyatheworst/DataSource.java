package main.java.com.danyatheworst;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        URL url = DataSource.class.getClassLoader().getResource("currency_exchange.db");
        if (url != null) {
            try {
               String jdbcUrl = String.format("jdbc:sqlite:" + url.toURI());
                config.setJdbcUrl(jdbcUrl);
            } catch (URISyntaxException e) {
                throw new RuntimeException("uri syntax error");
            }
        }
        config.setDriverClassName("org.sqlite.JDBC");

        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}