package dev.kgriffon.databaseutils;

public class DatabaseLinkException extends Exception {
    public DatabaseLinkException() {
        super("No database has been linked");
    }
}
