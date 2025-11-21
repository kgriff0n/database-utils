package dev.kgriffon.databaseutils;

import dev.kgriffon.databaseutils.database.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

/**
 * This class is abstract and should be extended by another class.
 * The subclass is intended to contain all your SQL queries.
 */
public abstract class DatabaseQueries {

    private Database db;

    public DatabaseQueries() {
        db = null;
    }

    /**
     * Links a database in order to execute queries.
     * @param db a database
     */
    public void link(Database db) {
        if (db != null) {
            DatabaseUtils.LOGGER.warn("A new database was linked after one had already been linked.");
        }
        this.db = db;
    }

    /**
     * @return the database connection
     * @throws DatabaseLinkException if no database is linked
     * @throws SQLException if a database access error occurs
     */
    public Connection getConnection() throws DatabaseLinkException, SQLException {
        checkLink();
        return db.getConnection();
    }

    /**
     * @return the ExecutorService instance
     */
    public ExecutorService getExecutor() {
        return db.getExecutor();
    }

    /**
     * @return the database type
     * @throws DatabaseLinkException if no database is linked
     */
    public Type getType() throws DatabaseLinkException {
        checkLink();
        return db.getType();
    }

    /**
     * @param type a database type
     * @return true if the database is of the specified type, false otherwise
     * @throws DatabaseLinkException if no database is linked
     */
    public boolean is(Type type) throws DatabaseLinkException {
        checkLink();
        return db.getType().equals(type);
    }

    /**
     * Initialization method.
     * This method is intended to contain
     * the creation of database tables.
     */
    public abstract void init();

    private void checkLink() throws DatabaseLinkException {
        if (db == null) throw new DatabaseLinkException();
    }
}
