/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.prj2.group99.myprojectname.dataservices;

import org.postgresql.ds.PGConnectionPoolDataSource;

import java.sql.*;

/**
 * The type Postgres.
 *
 * @author Geert Monsieur {@code g.monsieur@fontys.nl}
 */
public class Postgres {
    private final String SERVER_NAME;
    private static final String DB_NAME = "postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PWD = "mypassword";
    private static final int DB_PORT = 5432;

    private PGConnectionPoolDataSource source;



    /**
     * Instantiates a new Postgres.
     *
     * @param server_name the server name
     */
    public Postgres(String server_name) {
        SERVER_NAME = server_name;
        createPGDataSource();
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    protected Connection getConnection() throws SQLException {
        return source.getPooledConnection().getConnection();
    }

    private void createPGDataSource() {
        source = new PGConnectionPoolDataSource();
        source.setServerName(SERVER_NAME);
        source.setDatabaseName(DB_NAME);
        source.setUser(DB_USER);
        source.setPassword(DB_PWD);
        source.setPortNumber(DB_PORT);
    }

    /**
     * Create prepared statement with keys returned prepared statement.
     *
     * @param sql the sql
     * @return the prepared statement
     * @throws SQLException the sql exception
     */
    protected PreparedStatement createPreparedStatementWithKeysReturned(String sql) throws SQLException {
        return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    /**
     * Execute query result set.
     *
     * @param sql the sql
     * @return the result set
     * @throws SQLException the sql exception
     */
    protected ResultSet executeQuery(String sql) throws SQLException {
        return getConnection().createStatement().executeQuery(sql);
    }
}
