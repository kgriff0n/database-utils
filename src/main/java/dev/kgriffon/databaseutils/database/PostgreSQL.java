package dev.kgriffon.databaseutils.database;

import dev.kgriffon.databaseutils.Type;

public class PostgreSQL extends Database {

    /**
     * Initializes a PostgreSQL database.
     * @param host database host
     * @param port database port
     * @param base database name
     * @param user user with access to this database
     * @param password user password
     */
    public PostgreSQL(String host, String port, String base, String user, String password) {
        super(Type.POSTGRESQL, "jdbc:postgresql://" + host + ":" + port + "/" + base, user, password);
    }

    /**
     * Initializes a PostgreSQL database.
     * @param host database host
     * @param port database port
     * @param base database name
     * @param searchPath name of the schema to use
     * @param user user with access to this database
     * @param password user password
     */
    public PostgreSQL(String host, String port, String base, String searchPath, String user, String password) {
        super(Type.POSTGRESQL, "jdbc:postgresql://" + host + ":" + port + "/" + base + "?currentSchema=" + searchPath, user, password);
    }

    //TODO implements listeners with an interface
}
