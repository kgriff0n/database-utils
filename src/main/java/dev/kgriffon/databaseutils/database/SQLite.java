package dev.kgriffon.databaseutils.database;

import dev.kgriffon.databaseutils.DatabaseUtils;
import dev.kgriffon.databaseutils.Type;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SQLite extends Database {

    /**
     * Initializes a SQLite database.
     * The database file is automatically created.
     * @param base path to the database file
     */
    public SQLite(String base) {
        super(Type.SQLITE, "jdbc:sqlite:" + base, null, null);
        Path path = Paths.get(base);
        try {
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.createFile(path);
        } catch (FileAlreadyExistsException ignored) {
            // database already exist
        } catch (IOException e) {
            DatabaseUtils.LOGGER.error("Cannot create file {}", base);
        }
    }

}
