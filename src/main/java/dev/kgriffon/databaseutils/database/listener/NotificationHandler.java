package dev.kgriffon.databaseutils.database.listener;

import com.google.gson.JsonObject;

public interface NotificationHandler {
    void handle(JsonObject payload);
}