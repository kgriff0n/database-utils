package dev.kgriffon.databaseutils.database;

import dev.kgriffon.databaseutils.Type;

public class MySQL extends Database {

    /**
     * Initializes a MySQL database.
     * @param host database host
     * @param port database port
     * @param base database name
     * @param user user with access to this database
     * @param password user password
     */
    public MySQL(String host, String port, String base, String user, String password) {
        super(Type.MYSQL, "jdbc:mysql://" + host + ":" + port + "/" + base, user, password);
    }

}
