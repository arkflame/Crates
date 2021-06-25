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
    private final String noBlock;
    private final String noKeys;
    private final String noSpace;
    private final String noCrate;
    private final String invalidNumber;
    private final String receivedKeys;

    private final String addLocationSuccess;
    private final String addLocationDescription;

    private final String checkSuccess;
    private final String checkDescription;

    private final String claimSuccess;
    private final String claimDescription;

    private final String contentsSuccess;
    private final String contentsDescription;

    private final String createSuccess;
    private final String createDescription;

    private final String displaynameSuccess;
    private final String displaynameDescription;

    private final String keyallSuccess;
    private final String keyallDescription;

    private final String keySuccess;
    private final String keyDescription;

    private final String listSuccess;
    private final String listDescription;

    private final String removeSuccess;
    private final String removeDescription;

    private final String removeLocationSuccess;
    private final String removeLocationDescription;

    public CratesConfig(final Configuration config) {
        final ConfigWrapper configWrapper = new ConfigWrapper(config);

        hologramLines = configWrapper.getOrDefault("hologram.lines", new ArrayList<>());

        keyName = configWrapper.getOrDefault("key.name", "");
        keyLore = configWrapper.getOrDefault("key.lore", new ArrayList<>());

        inventoryTitle = configWrapper.getOrDefault("inventory.title", "");

        commandUsage = configWrapper.getOrDefault("command_usage", "");
        noConsole = configWrapper.getOrDefault("no_console", "");
        noPermission = configWrapper.getOrDefault("no_permission", "");
        noBlock = configWrapper.getOrDefault("no_block", "");
        noKeys = configWrapper.getOrDefault("no_keys", "");
        noSpace = configWrapper.getOrDefault("no_space", "");
        noCrate = configWrapper.getOrDefault("no_crate", "");
        invalidNumber = configWrapper.getOrDefault("invalid_number", "");
        receivedKeys = configWrapper.getOrDefault("received_keys", "");

        addLocationSuccess = configWrapper.getOrDefault("add_location.success", "");
        addLocationDescription = configWrapper.getOrDefault("add_location.description", "");

        checkSuccess = configWrapper.getOrDefault("check.success", "");
        checkDescription = configWrapper.getOrDefault("check.description", "");
    
        claimSuccess = configWrapper.getOrDefault("claim.success", "");
        claimDescription = configWrapper.getOrDefault("claim.description", "");
        
        contentsSuccess = configWrapper.getOrDefault("contents.success", "");
        contentsDescription = configWrapper.getOrDefault("contents.description", "");
    
        createSuccess = configWrapper.getOrDefault("create.success", "");
        createDescription = configWrapper.getOrDefault("create.description", "");

        displaynameSuccess = configWrapper.getOrDefault("displayname.success", "");
        displaynameDescription = configWrapper.getOrDefault("displayname.description", "");

        keyallSuccess = configWrapper.getOrDefault("keyall.success", "");
        keyallDescription = configWrapper.getOrDefault("keyall.description", "");

        keySuccess = configWrapper.getOrDefault("key.success", "");
        keyDescription = configWrapper.getOrDefault("key.description", "");

        listSuccess = configWrapper.getOrDefault("list.success", "");
        listDescription = configWrapper.getOrDefault("list.description", "");

        removeSuccess = configWrapper.getOrDefault("remove.success", "");
        removeDescription = configWrapper.getOrDefault("remove.description", "");

        removeLocationSuccess = configWrapper.getOrDefault("remove_location.success", "");
        removeLocationDescription = configWrapper.getOrDefault("remove_location.description", "");
    }

    public List<String> getHologramLines() {
        return hologramLines;
    }

    public String getKeyName() {
        return keyName;
    }

    public List<String> getKeyLore() {
        return keyLore;
    }

    public String getInventoryTitle() {
        return inventoryTitle;
    }

    public String getCommandUsage() {
        return commandUsage;
    }

    public String getNoConsole() {
        return noConsole;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getNoBlock() {
        return noBlock;
    }

    public String getNoKeys() {
        return noKeys;
    }

    public String getNoSpace() {
        return noSpace;
    }

    public String getNoCrate() {
        return noCrate;
    }

    public String getInvalidNumber() {
        return invalidNumber;
    }

    public String getReceivedKeys() {
        return receivedKeys;
    }

    public String getAddLocationSuccess() {
        return addLocationSuccess;
    }

    public String getAddLocationDescription() {
        return addLocationDescription;
    }

    public String getCheckSuccess() {
        return checkSuccess;
    }

    public String getCheckDescription() {
        return checkDescription;
    }

    public String getClaimSuccess() {
        return claimSuccess;
    }

    public String getClaimDescription() {
        return claimDescription;
    }

    public String getContentsSuccess() {
        return contentsSuccess;
    }

    public String getContentsDescription() {
        return contentsDescription;
    }

    public String getCreateSuccess() {
        return createSuccess;
    }

    public String getCreateDescription() {
        return createDescription;
    }

    public String getDisplaynameSuccess() {
        return displaynameSuccess;
    }

    public String getDisplaynameDescription() {
        return displaynameDescription;
    }

    public String getKeyallSuccess() {
        return keyallSuccess;
    }

    public String getKeyallDescription() {
        return keyallDescription;
    }

    public String getKeySuccess() {
        return keySuccess;
    }

    public String getKeyDescription() {
        return keyDescription;
    }

    public String getListSuccess() {
        return listSuccess;
    }

    public String getListDescription() {
        return listDescription;
    }

    public String getRemoveSuccess() {
        return removeSuccess;
    }

    public String getRemoveDescription() {
        return removeDescription;
    }

    public String getRemoveLocationSuccess() {
        return removeLocationSuccess;
    }

    public String getRemoveLocationDescription() {
        return removeLocationDescription;
    }
}
