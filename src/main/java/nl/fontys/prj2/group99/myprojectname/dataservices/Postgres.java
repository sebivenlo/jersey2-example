package nl.fontys.prj2.group99.myprojectname.dataservices;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.postgresql.ds.PGConnectionPoolDataSource;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Postgres.
 *
 * @author Geert Monsieur {@code g.monsieur@fontys.nl}
 */
public class Postgres {

    private String server_name = "localhost";
    private String db_name = "postgres";
    private String db_user = "postgres";
    private String db_passwd = "mypassword";
    private int db_port = 5432;

    private PGConnectionPoolDataSource source;

    /**
     * Instantiates a new Postgres.
     *
     * @param server_name the server name
     */
    public Postgres( String server_name ) {

        try {
            Properties props = new Properties();
            props.load( Files.newInputStream( Paths.get( "connection.properties" ) ) );
            server_name = props.getProperty( "SERVER_NAME", server_name );
            db_name = props.getProperty( "DB_NAME", db_name );
            db_user = props.getProperty( "DB_USER", db_user );
            db_passwd = props.getProperty( "DB_PASSWD", db_passwd );
            db_port = Integer.parseInt( props.getProperty( "DB_PORT", Integer.toString( db_port ) ) );
        } catch ( IOException ex ) {
            Logger.getLogger( Postgres.class.getName() ).log( Level.SEVERE, null, ex );
        }
        System.out.println( "db_name = " + db_name);
        System.out.println( "db_user = " + db_user);
        System.out.println( "db_port = " + db_port);
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
        source.setServerName( server_name );
        source.setDatabaseName( db_name );
        source.setUser( db_user );
        source.setPassword( db_passwd );
        source.setPortNumber( db_port );
    }

    /**
     * Create prepared statement with keys returned prepared statement.
     *
     * @param sql the sql
     * @return the prepared statement
     * @throws SQLException the sql exception
     */
    protected PreparedStatement createPreparedStatementWithKeysReturned( String sql ) throws SQLException {
        return getConnection().prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
    }

    /**
     * Execute query result set.
     *
     * @param sql the sql
     * @return the result set
     * @throws SQLException the sql exception
     */
    protected ResultSet executeQuery( String sql ) throws SQLException {
        return getConnection().createStatement().executeQuery( sql );
    }
}
