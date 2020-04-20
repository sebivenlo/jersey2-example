/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.prj2.group99.myprojectname.dataservices;

import nl.fontys.prj2.group99.myprojectname.model.SimplePresident;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type PostgresDAOlite.
 *
 * @author dos
 */
public class PostgresDAOlite extends Postgres implements DAOlite<SimplePresident> {

    public static final String INSERT_PRESIDENT_STATEMENT = "insert into president(name,birth_year) values(?,?)";

    public static final String SELECT_PRESIDENT_STATEMENT = "select * from president where id=";

    public static final String SELECT_ALL_STATEMENT = "select * from president";

    /**
     * Instantiates a new Postgres databaselite.
     *
     * @param server_name the server name
     */
    public PostgresDAOlite(String server_name) {
        super(server_name);
    }

    @Override
    public SimplePresident save(SimplePresident p) {
        try (PreparedStatement pst = createPreparedStatementWithKeysReturned(INSERT_PRESIDENT_STATEMENT)) {
            pst.setString(1, p.getName());
            pst.setInt(2, p.getBirth_year());
            pst.execute();
            ResultSet keyset = pst.getGeneratedKeys();
            if (keyset.next()) {
                p.setId(keyset.getInt(1));
            }

            return p;
        } catch (SQLException ex) {
            Logger.getLogger(PostgresDAOlite.class.getName()).
                    log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public SimplePresident get(int id) {
        // be aware that this is unsafe! Use prepared statements instead.
        try (ResultSet rs = executeQuery(SELECT_PRESIDENT_STATEMENT + id)) {
            return createPresident(id, rs);
        } catch (SQLException ex) {
            Logger.getLogger(PostgresDAOlite.class.getName()).
                    log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<SimplePresident> getAll() {
        List<SimplePresident> result = new ArrayList<>();
        try (ResultSet rs = executeQuery(SELECT_ALL_STATEMENT)) {
            getPresidentsFromResult(result, rs);
        } catch (SQLException ex) {
            Logger.getLogger(PostgresDAOlite.class.getName()).
                    log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    private List<SimplePresident> getPresidentsFromResult(List<SimplePresident> result, ResultSet rs) throws SQLException {
        List<SimplePresident> presidents = new ArrayList<SimplePresident>();
        while (rs.next()) {
            int id = rs.getInt("id");

            String name = rs.getString("name");
            int birth_year = rs.getInt("birth_year");
            SimplePresident president = new SimplePresident(id, name, birth_year);
            presidents.add(president);
        }
        return presidents;
    }

    @Override
    public SimplePresident update(SimplePresident p) {
        return null; // exercise for the student
    }

    @Override
    public void delete(int id) {
        String sql = "delete from president where id = ?";
        try (
                Connection con = getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(PostgresDAOlite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private SimplePresident createPresident(int id, ResultSet rs) throws SQLException {
        if (rs.next()) {
            String name = rs.getString("name");
            int birth_year = rs.getInt("birth_year");
            return new SimplePresident(id, name, birth_year);
        } else {
            return null;
        }

    }

}
