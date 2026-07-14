package dev.kgriffon.databaseutils.database.listener;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.kgriffon.databaseutils.DatabaseUtils;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class PostgresListener implements Runnable {

    private final String uri;
    private final Properties props;
    private final ChannelRegistry registry;

    private boolean running = true;

    public PostgresListener(String uri, String user, String password, ChannelRegistry registry) {
        this.uri = uri;
        this.registry = registry;

        this.props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("tcpKeepAlive", "true");
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try (Connection conn = DriverManager.getConnection(uri, props);
                 Statement stmt = conn.createStatement()) {

                PGConnection pgConn = conn.unwrap(PGConnection.class);

                for (String channel : registry.getChannels()) {
                    stmt.execute("LISTEN " + channel);
                    DatabaseUtils.LOGGER.info("Listening on {}", channel);
                }

                while (running) {
                    PGNotification[] notifications = pgConn.getNotifications(5000);

                    if (notifications != null) {
                        for (PGNotification notification : notifications) {
                            NotificationHandler handler = registry.getHandler(notification.getName());
                            if (handler != null) {
                                JsonObject payload = JsonParser.parseString(notification.getParameter()).getAsJsonObject();
                                handler.handle(payload);
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                DatabaseUtils.LOGGER.warn("Lost connection, retrying in 5 seconds: {}", e.getMessage());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ie) {
                    running = false;
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}