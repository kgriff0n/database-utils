package dev.kgriffon.databaseutils.database;

import dev.kgriffon.databaseutils.Type;

public class TestDB extends Database {

    /**
     * A fake database, should only be used for testing.
     */
    public TestDB() {
        super(Type.TEST, null, null, null);
    }

}
