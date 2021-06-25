package dev._2lstudios.crates.util;

import java.util.List;

import org.bukkit.configuration.Configuration;

public class ConfigWrapper {
    private final Configuration config;

    public ConfigWrapper(final Configuration config) {
        this.config = config;
    }

    public List<String> getOrDefault(final String key, final List<String> def) {
        final List<String> value;

        if (config.contains(key)) {
            value = config.getStringList(key);
        } else {
            value = def;
        }

        return StringUtil.translateColorCodes(value);
    }

    public String getOrDefault(final String key, final String def) {
        final String value;

        if (config.contains(key)) {
            value = config.getString(key);
        } else {
            value = def;
        }

        return StringUtil.translateColorCodes(value);
    }

    public int getOrDefault(final String key, final int def) {
        if (config.contains(key)) {
            return config.getInt(key);
        } else {
            return def;
        }
    }
}
