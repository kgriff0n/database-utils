package dev.kgriffon.databaseutils;

import dev.kgriffon.databaseutils.database.*;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseUtils implements ModInitializer {
	public static final String MOD_ID = "database-utils";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        DatabaseUtils.LOGGER.info("Loaded!");
	}

    /**
     * @param name name of a database
     * @return type associated with this name,
     *         {@link Type#TEST} is returned if the name does not match any type
     */
    public static Type getType(String name) {
        for (Type type : Type.values()) {
            if (type.getName().equals(name.toLowerCase())) {
                return type;
            }
        }
        return Type.TEST;
    }
}