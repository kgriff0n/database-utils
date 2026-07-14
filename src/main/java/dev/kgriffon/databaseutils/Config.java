package dev.kgriffon.databaseutils;

import dev.kgriffon.databaseutils.database.*;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public class Config {

    private final String id;
    private final String properties;
    private String storage;
    private String host;
    private String port;
    private String database;
    private String user;
    private String password;

    public Config(String id) {
        this.id = id;
        Path configPath = FabricLoader.getInstance().getConfigDir();
        properties = configPath + "/" + id + ".properties";
        if (!exist()) {
            createConfigFile();
            writeDefaultConfig();
            DatabaseUtils.LOGGER.warn("Configuration file not found, loading default configuration...");
        }
        loadFile();
    }

    private void loadFile() {
        Properties configs = new Properties();
        try {
            configs.load(new FileInputStream(properties));
        } catch (IOException e) {
            DatabaseUtils.LOGGER.error("Can't load file.");
        }

        storage = configs.getProperty("storage");
        host = configs.getProperty("host");
        port = configs.getProperty("port");
        database = configs.getProperty("database");
        user = configs.getProperty("user");
        password = configs.getProperty("password");
    }

    private boolean exist() {
        return new File(properties).exists();
    }

    private void writeDefaultConfig() {

        try (Writer writer = new FileWriter(properties)) {
            writer.write("storage=mariadb\n");
            writer.write("host=127.0.0.1\n");
            writer.write("port=3306\n");
            writer.write(String.format("database=%s\n", id));
            writer.write("user=user\n");
            writer.write("password=1234\n");
        } catch (IOException e) {
            DatabaseUtils.LOGGER.error("Can't write file.");
        }
    }

    private void createConfigFile() {
        File file = new File(properties);
        try {
            file.createNewFile();
        } catch (IOException e) {
            DatabaseUtils.LOGGER.error("Can't create file.");
        }
    }

    public String getStorage() {
        return storage;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
