package server;

import com.mchange.v2.c3p0.AbstractConnectionCustomizer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class that customizes the c3p0 connection. In this case, it sets foreign key checking for each connection made to the
 * sqlite database. This is because sqlite does not set foreign checking to on, it has to be done manually on each
 * connection.
 */
public class ConnectionCustomizer extends AbstractConnectionCustomizer {
    @Override
    public void onAcquire(final Connection c, final String parentDataSourceIdentityToken) throws Exception {
        c.prepareStatement("PRAGMA foreign_keys = ON").executeUpdate();
    }
}
