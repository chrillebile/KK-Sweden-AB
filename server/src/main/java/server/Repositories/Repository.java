package server.Repositories;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Connection;

public class Repository {

    @Autowired
    private Connection dbConnection;

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
        connection = dbConnection;
    }
}
