package dev._2lstudios.crates.crate;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.util.ConfigurationUtil;

public class CrateManager {
  private final Map<String, Crate> crates;
  private final ConfigurationUtil configurationUtil;
  private final CratesConfig cratesConfig;
  private final Plugin plugin;

  public CrateManager(final ConfigurationUtil configurationUtil, final CratesConfig cratesConfig, final Plugin plugin) {
    this.crates = new HashMap<>();
    this.configurationUtil = configurationUtil;
    this.cratesConfig = cratesConfig;
    this.plugin = plugin;
  }

  public Collection<String> getCratesNames() {
    return this.crates.keySet();
  }

  public Collection<Crate> getCrates() {
    return this.crates.values();
  }

  public Crate getCrate(final Location location) {
    for (final Crate crate : getCrates()) {
      if (crate.checkLocation(location))
        return crate;
    }
    return null;
  }

  public Crate getCrate(final ItemStack itemStack) {
    for (final Crate crate : getCrates()) {
      if (crate.isKey(itemStack))
        return crate;
    }
    return null;
  }

  public Crate getCrate(final String crateName) {
    return this.crates.getOrDefault(crateName, null);
  }

  public void loadCrates() {
    final File[] cratesFileList = (new File(this.plugin.getDataFolder() + "/crates")).listFiles();
    if (cratesFileList != null)
      for (final File crateFile : cratesFileList)
        loadCrate(crateFile.getName().replace(".yml", ""));
  }

  public void saveCrates(final boolean async) {
    for (final String crateName : this.crates.keySet())
      saveCrate(crateName, async);
  }

  public void loadCrate(final String name) {
    String displayName;
    final YamlConfiguration yamlConfiguration = this.configurationUtil
        .getConfiguration("%datafolder%/crates/" + name + ".yml");
    final ConfigurationSection contentsSection = yamlConfiguration.getConfigurationSection("contents");
    final ConfigurationSection locationsSection = yamlConfiguration.getConfigurationSection("locations");

    if (yamlConfiguration.contains("displayname")) {
      displayName = yamlConfiguration.getString("displayname");
    } else {
      displayName = name;
    }

    final Inventory inventory = this.plugin.getServer().createInventory(null, 27,
        cratesConfig.getInventoryTitle(displayName));
    final Collection<Location> locations = new HashSet<>();

    if (contentsSection != null) {
      for (final String key : contentsSection.getKeys(false)) {
        inventory.addItem(new ItemStack[] { yamlConfiguration.getItemStack("contents." + key) });
      }
    }

    if (locationsSection != null) {
      for (final String key : locationsSection.getKeys(false)) {
        locations.add((Location) yamlConfiguration.get("locations." + key));
      }
    }

    this.crates.put(name, new Crate(plugin, cratesConfig, locations, name, displayName, inventory));
  }

  public void saveCrate(final String name, final boolean async) {
    final Crate crate = getCrate(name);
    final YamlConfiguration config = new YamlConfiguration();
    int index = 0;
    for (final ItemStack itemStack : crate.getInventory().getContents()) {
      config.set("contents." + index, itemStack);
      index++;
    }
    index = 0;
    for (final Location location : crate.getLocations()) {
      config.set("locations." + index, location);
      index++;
    }
    config.set("displayname", crate.getDisplayName());
    this.configurationUtil.saveConfiguration(config, "%datafolder%/crates/" + name + ".yml", async);
  }

  public void removeCrate(final String crateName) {
    if (this.crates.containsKey(crateName)) {
      final Crate crate = this.crates.get(crateName);
      crate.despawnHolograms();
      this.crates.remove(crateName);
      this.configurationUtil.deleteConfiguration("%datafolder%/crates/" + crateName + ".yml", true);
    }
  }

  public void spawnHolograms() {
    for (final Crate crate : this.crates.values())
      crate.spawnHolograms();
  }

  public void despawnHolograms() {
    for (final Crate crate : this.crates.values())
      crate.despawnHolograms();
  }
}
