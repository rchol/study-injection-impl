package api.transactions;

import java.sql.ResultSet;

public abstract class SimpleDatasource {

    public abstract void beginTransaction();

    public abstract void commit();

    public abstract void rollback();

    public abstract ResultSet executeQuery(String query);

    public abstract long executeUpdate(String query, Object... params);

    public abstract long executeUpdate(String query);
}