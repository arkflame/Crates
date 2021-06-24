package dev._2lstudios.crates.util;

import java.util.List;

import org.bukkit.configuration.Configuration;

public class ConfigWrapper {
    private final Configuration config;

    public ConfigWrapper(final Configuration config) {
        this.config = config;
    }

    public List<String> getOrDefault(final String key, final List<String> def) {
        if (config.contains(key)) {
            return config.getStringList(key);
        } else {
            return def;
        }
    }

    public String getOrDefault(final String key, final String def) {
        if (config.contains(key)) {
            return config.getString(key);
        } else {
            return def;
        }
    }

    public int getOrDefault(final String key, final int def) {
        if (config.contains(key)) {
            return config.getInt(key);
        } else {
            return def;
        }
    }
}
