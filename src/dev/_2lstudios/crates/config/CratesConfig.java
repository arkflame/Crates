package dev._2lstudios.crates.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.Configuration;

import dev._2lstudios.crates.util.ConfigWrapper;
import dev._2lstudios.crates.util.Placeholder;
import dev._2lstudios.crates.util.StringUtil;

public class CratesConfig {
    private final ConfigWrapper configWrapper;
    private final Map<String, List<String>> listMap = new HashMap<>();
    private final Map<String, String> stringMap = new HashMap<>();

    public CratesConfig(final Configuration config) {
        configWrapper = new ConfigWrapper(config);
    }

    public String getString(final String key) {
        if (stringMap.containsKey("key")) {
            return stringMap.get(key);
        } else {
            final String value = configWrapper.getOrDefault(key, "undefined");

            stringMap.put(key, value);

            return value;
        }
    }

    public List<String> getStringList(final String key) {
        if (stringMap.containsKey("key")) {
            return listMap.get(key);
        } else {
            final List<String> value = configWrapper.getOrDefault(key, new ArrayList<>());

            listMap.put(key, value);

            return value;
        }
    }

    public List<String> getHologramLines(final String displayName) {
        return StringUtil.replace(getStringList("hologram_lines"), new Placeholder("%displayname%", displayName));
    }

    public String getItemName(final String displayName) {
        return StringUtil.replace(getString("item.name"), new Placeholder("%displayname%", displayName));
    }

    public List<String> getItemLore(final String displayName) {
        return StringUtil.replace(getStringList("item.lore"), new Placeholder("%displayname%", displayName));
    }

    public String getInventoryTitle(final String displayName) {
        return StringUtil.replace(getString("inventory.title"), new Placeholder("%displayname%", displayName));
    }

    public String getCommandUsage(final String label, final String cmd, final String args) {
        return StringUtil.replace(getString("command_usage"), new Placeholder("%label%", label), new Placeholder("%cmd%", cmd),
                new Placeholder("%args%", args));
    }

    public String getError() {
        return getString("error");
    }

    public String getNoConsole() {
        return getString("no_console");
    }

    public String getNoPermission() {
        return getString("no_permission");
    }

    public String getNoBlock() {
        return getString("no_block");
    }

    public String getNoKeys() {
        return getString("no_keys");
    }

    public String getNoKeysPending() {
        return getString("no_keys_pending");
    }

    public String getNoSpace() {
        return getString("no_space");
    }

    public String getNoCrate() {
        return getString("no_crate");
    }

    public String getNoInteract() {
        return getString("no_interact");
    }

    public String getInvalidKey() {
        return getString("invalid_key");
    }

    public String getInvalidNumber() {
        return getString("invalid_number");
    }

    public String getValidKey(final String crateName) {
        return StringUtil.replace(getString("valid_key"), new Placeholder("%crate_name%", crateName));
    }

    public String getReceivedKeys(final String giverName, final int amount, final String crateName) {
        return StringUtil.replace(getString("received_keys"), new Placeholder("%giver_name%", giverName),
                new Placeholder("%amount%", amount), new Placeholder("%crate_name%", crateName));
    }

    public String getHelpTitle() {
        return getString("help.title");
    }

    public String getHelpCommand(final String label, final String cmd, final String args, final String description) {
        return StringUtil.replace(getString("help.command"), new Placeholder("%label%", label), new Placeholder("%cmd%", cmd),
                new Placeholder("%args%", args), new Placeholder("%description%", description));
    }

    public String getHelpSubtitle() {
        return getString("help.subtitle");
    }

    public String getAddLocationSuccess(final String crateName) {
        return StringUtil.replace(getString("add_location.success"), new Placeholder("%crate_name%", crateName));
    }

    public String getAddLocationDescription() {
        return getString("add_location.description");
    }

    public String getCheckSuccess(final int amount) {
        return StringUtil.replace(getString("check.success"), new Placeholder("%amount%", amount));
    }

    public String getCheckDescription() {
        return getString("check.description");
    }

    public String getClaimSuccess(final int amount) {
        return StringUtil.replace(getString("claim.success"), new Placeholder("%amount%", amount));
    }

    public String getClaimDescription() {
        return getString("claim.description");
    }

    public String getContentsSuccess(final String crateName) {
        return StringUtil.replace(getString("contents.success"), new Placeholder("%crate_name%", crateName));
    }

    public String getContentsDescription() {
        return getString("contents.description");
    }

    public String getCreateSuccess(final String crateName) {
        return StringUtil.replace(getString("create.success"), new Placeholder("%crate_name%", crateName));
    }

    public String getCreateDescription() {
        return getString("create.description");
    }

    public String getDisplaynameSuccess(final String crateName, final String newName) {
        return StringUtil.replace(getString("displayname.success"), new Placeholder("%crate_name%", crateName),
                new Placeholder("%new_name%", newName));
    }

    public String getDisplaynameDescription() {
        return getString("displayname.description");
    }

    public String getKeyallSuccess(final int amount, final String crateName) {
        return StringUtil.replace(getString("keyall.success"), new Placeholder("%amount%", amount),
                new Placeholder("%crate_name%", crateName));
    }

    public String getKeyallDescription() {
        return getString("keyall.description");
    }

    public String getKeySuccess(final int amount, final String crateName, final String playerName) {
        return StringUtil.replace(getString("key.success"), new Placeholder("%amount%", amount),
                new Placeholder("%crate_name%", crateName), new Placeholder("%player_name%", playerName));
    }

    public String getKeyDescription() {
        return getString("key.description");
    }

    public String getListSuccess(final String cratesList) {
        return StringUtil.replace(getString("list.success"), new Placeholder("%crates_list%", cratesList));
    }

    public String getListDescription() {
        return getString("list.description");
    }

    public String getRemoveSuccess(final String crateName) {
        return StringUtil.replace(getString("remove.success"), new Placeholder("%crate_name%", crateName));
    }

    public String getRemoveDescription() {
        return getString("remove.description");
    }

    public String getRemoveLocationSuccess(final String crateName) {
        return StringUtil.replace(getString("remove_location.success"), new Placeholder("%crate_name%", crateName));
    }

    public String getRemoveLocationDescription() {
        return getString("remove_location.description");
    }

   public String getAddLocationAlreadySet() {
        return getString("add_location.already_set");
   }

    public String getRemoveLocationNoCrateAt(String crateName) {
        return getString("remove_location.no_crate_at_location").replace("%crate_name%", crateName);
    }

    public String getRowsSuccess(String crateName, int slots) {
        return getString("rows.success");
    }

    public String getRowsDescription() {
        return getString("rows.description");
    }
}
