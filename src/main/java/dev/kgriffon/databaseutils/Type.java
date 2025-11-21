package dev.kgriffon.databaseutils;

/**
 * All types of databases
 * supported by this mod.
 */
public enum Type {
    MYSQL("mysql"),
    MARIADB("mariadb"),
    POSTGRESQL("postgres"),
    SQLITE("sqlite"),
    TEST("test");

    private final String name;

    Type(String name) {
        this.name = name;
    }

    /**
     * @return type name
     */
    public String getName() {
        return name;
    }
}
