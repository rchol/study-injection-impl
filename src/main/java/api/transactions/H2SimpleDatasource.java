package api.transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcConnectionPool;

@Singleton
public class H2SimpleDatasource extends SimpleDatasource {

    private final HueThreadLocalConnection hueThreadLocalConnection;

    @Inject
    public H2SimpleDatasource() {
        DataSource ds = JdbcConnectionPool
            .create("jdbc:h2:mem:default", "", "");
        this.hueThreadLocalConnection = new HueThreadLocalConnection(ds);
    }

    private void closeConnectionSilently(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void beginTransaction() {
        try {
            Connection connection = hueThreadLocalConnection.get();
            connection.setAutoCommit(false);
            try (Statement st = connection.createStatement()) {
                st.execute("BEGIN TRANSACTION");
            }
        } catch (SQLException se) {
            closeConnectionSilently(hueThreadLocalConnection.get());
            hueThreadLocalConnection.set(null);
            throw new RuntimeException(se);
        }
    }

    @Override
    public void commit() {
        try (Statement st = hueThreadLocalConnection.get().createStatement()) {
            st.execute("COMMIT");
        } catch (SQLException se) {
            closeConnectionSilently(hueThreadLocalConnection.get());
            hueThreadLocalConnection.set(null);
            throw new RuntimeException(se);
        }
    }

    @Override
    public void rollback() {
        try (Statement st = hueThreadLocalConnection.get().createStatement()) {
            st.execute("ROLLBACK");
        } catch (SQLException se) {
            closeConnectionSilently(hueThreadLocalConnection.get());
            hueThreadLocalConnection.set(null);
            throw new RuntimeException(se);
        }
    }

    @Override
    public ResultSet executeQuery(String query) {
        try (Statement st = hueThreadLocalConnection.get().createStatement()) {
            return st.executeQuery(query);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    @Override
    public long executeUpdate(String query, Object... params) {
        try (PreparedStatement ps = hueThreadLocalConnection.get()
            .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int ix = 1;
            for (Object p : params) {
                ps.setObject(ix++, p);
            }
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getLong(1) : -1L;
            }
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    @Override
    public long executeUpdate(String query) {
        try (Statement st = hueThreadLocalConnection.get().createStatement()) {
            //FIXME OMAGAD
            return st.executeUpdate(query);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    static class HueThreadLocalConnection extends ThreadLocal<Connection> {

        private final DataSource ds;

        HueThreadLocalConnection(DataSource ds) {
            this.ds = ds;
        }

        @Override
        protected Connection initialValue() {
            try {
                return ds.getConnection();
            } catch (SQLException se) {
                throw new RuntimeException(se);
            }
        }
    }
}