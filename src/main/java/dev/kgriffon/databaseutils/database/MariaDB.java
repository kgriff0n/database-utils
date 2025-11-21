package dev.kgriffon.databaseutils.database;

import dev.kgriffon.databaseutils.Type;

public class MariaDB extends Database {

    /**
     * Initializes a MariaDB database.
     * @param host database host
     * @param port database port
     * @param base database name
     * @param user user with access to this database
     * @param password user password
     */
    public MariaDB(String host, String port, String base, String user, String password) {
        super(Type.MARIADB, "jdbc:mariadb://" + host + ":" + port + "/" + base, user, password);
    }

}
