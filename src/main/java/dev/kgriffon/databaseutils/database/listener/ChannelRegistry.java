package dev.kgriffon.databaseutils.database.listener;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelRegistry {

    private final Map<String, NotificationHandler> handlers = new ConcurrentHashMap<>();

    public void register(String channel, NotificationHandler handler) {
        handlers.put(channel, handler);
    }

    public NotificationHandler getHandler(String channel) {
        return handlers.get(channel);
    }

    public Set<String> getChannels() {
        return handlers.keySet();
    }
}