package server.Repositories;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.SQLException;

public class Repository {
    @Autowired
    private ComboPooledDataSource dataSource;

    /**
     * Contains the database connection that the repository uses.
     */
    protected Connection connection;

    /**
     * Retrieves the connection from the datasource. Is run by spring after the class has been constructed.
     * <p>
     * Autowired beans are not available before an object is constructed, so it has to be done after.
     */
    @PostConstruct
    private void createConnection() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
