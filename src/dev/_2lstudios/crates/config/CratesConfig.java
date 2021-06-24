package dev._2lstudios.crates.config;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.Configuration;

import dev._2lstudios.crates.util.ConfigWrapper;

public class CratesConfig {
    private final List<String> hologramLines;

    private final String keyName;
    private final List<String> keyLore;

    private final String inventoryTitle;

    private final String commandUsage;

    private final String noConsole;
    private final String noPermission;
    private final String noChest;
    private final String noKeys;

    private final String addLocationSuccess;
    private final String addLocationDescription;

    private final String checkSuccess;
    private final String checkDescription;

    public CratesConfig(final Configuration config) {
        final ConfigWrapper configWrapper = new ConfigWrapper(config);

        hologramLines = configWrapper.getOrDefault("hologram.lines", new ArrayList<>());

        keyName = configWrapper.getOrDefault("key.name", "");
        keyLore = configWrapper.getOrDefault("key.lore", new ArrayList<>());

        inventoryTitle = configWrapper.getOrDefault("inventory.title", "");

        commandUsage = configWrapper.getOrDefault("command_usage", "");

        noConsole = configWrapper.getOrDefault("no_console", "");
        noPermission = configWrapper.getOrDefault("no_permission", "");
        noChest = configWrapper.getOrDefault("no_chest", "");
        noKeys = configWrapper.getOrDefault("no_keys", "");

        addLocationSuccess = configWrapper.getOrDefault("add_location.success", "");
        addLocationDescription = configWrapper.getOrDefault("add_location.description", "");

        checkSuccess = configWrapper.getOrDefault("check.success", "");
        checkDescription = configWrapper.getOrDefault("check.description", "");
    }
}
