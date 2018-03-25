package server;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.beans.PropertyVetoException;

/**
 * Contains all the bean configuration that are inserted (by Spring) into the application context of this application.
 */
@Component
public class MyApplicationContext {
    /**
     * Create the bean that contains the connection to the database pool.
     */
    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass("org.sqlite.JDBC");
        comboPooledDataSource.setJdbcUrl("jdbc:sqlite:KK-SwedenDB.db");
        return comboPooledDataSource;
    }
}