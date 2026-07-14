package dev.kgriffon.databaseutils.database;

import dev.kgriffon.databaseutils.Type;
import dev.kgriffon.databaseutils.database.listener.ChannelRegistry;
import dev.kgriffon.databaseutils.database.listener.NotificationHandler;
import dev.kgriffon.databaseutils.database.listener.PostgresListener;

public class PostgreSQL extends Database {

    private final ChannelRegistry channelRegistry = new ChannelRegistry();

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

    public PostgresListener startListener() {
        PostgresListener listener = new PostgresListener(uri, user, password, channelRegistry);
        Thread listenerThread = new Thread(listener, "postgres-listener");
        listenerThread.setDaemon(true);
        listenerThread.start();
        return listener;
    }

    public void listen(String channel, NotificationHandler handler) {
        channelRegistry.register(channel, handler);
    }

}
