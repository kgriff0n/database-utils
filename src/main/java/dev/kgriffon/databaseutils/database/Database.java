package dev.kgriffon.databaseutils.database;

import dev.kgriffon.databaseutils.Type;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Abstract class representing any type of database.
 */
public abstract class Database {

    protected final ExecutorService executor;

    private final Type type;
    private final String uri;
    private final String user;
    private final String password;

    /**
     * Initializes a database.
     * @param type type of the database
     * @param uri connection string (URI) to the database
     * @param user username, can be null
     * @param password user's password, can be null
     */
    protected Database(Type type, String uri, @Nullable String user, @Nullable String password) {
        executor = Executors.newSingleThreadExecutor();
        this.type = type;
        this.uri = uri;
        this.user = user;
        this.password = password;
    }

    /**
     * @return the connection to the database, necessary for executing queries
     * @throws SQLException if a database access error occurs
     */
    public Connection getConnection() throws SQLException {
        if (user == null || password == null) {
            return DriverManager.getConnection(uri);
        }
        return DriverManager.getConnection(uri, user, password);
    }

    /**
     * Shuts down the executor.
     */
    public void disconnect() {
        executor.shutdown();
    }

    /**
     * @return the database type
     */
    public Type getType() {
        return type;
    }

    /**
     * @return the ExecutorService instance
     */
    public ExecutorService getExecutor() {
        return executor;
    }
}
