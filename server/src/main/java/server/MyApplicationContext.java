package server;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Contains all the bean configuration that are inserted (by Spring) into the application context of this application.
 */
@Component
public class MyApplicationContext {
    @Bean
    public Connection dbConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        Properties connectionProperties = new Properties();
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        connectionProperties = config.toProperties();
        String connectionString = "jdbc:sqlite:KK-SwedenDB.db";
        try {
            conn = DriverManager.getConnection(connectionString, connectionProperties);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}