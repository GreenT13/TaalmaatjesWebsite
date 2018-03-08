package com.apon.database.jooq;

import com.apon.log.MyLogger;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.JDBCUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SuppressWarnings("UnusedReturnValue")
public class DbContext {
    private Connection connection;
    private Configuration configuration;

    public DbContext(DataSource dataSource) throws SQLException {
        // Configure the context based on the dataSource.
        connection = dataSource.getConnection();

        // Always without auto commit.
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        configuration = DSL.using(connection, JDBCUtils.dialect(connection)).configuration();
    }

    public DbContext(Connection connection) throws SQLException {
        this.connection = connection;
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);

        configuration = DSL.using(connection, JDBCUtils.dialect(connection)).configuration();
    }

    public boolean commit() {
        try {
            connection.commit();
            return true;
        } catch (SQLException e) {
            MyLogger.logError("Could not commit connection.", e);
            return false;
        }
    }

    public boolean rollback() {
        try {
            connection.rollback();
            return true;
        } catch (SQLException e) {
            MyLogger.logError("Could not rollback connection.", e);
            return false;
        }
    }

    public boolean close() {
        try {
            connection.close();
            return true;
        } catch (SQLException e) {
            MyLogger.logError("Could not close connection.", e);
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
