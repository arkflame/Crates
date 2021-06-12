package dev._2lstudios.crates.crate;

import dev._2lstudios.crates.util.ConfigurationUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CrateManager {
  private final Map<String, Crate> crates;
  private final ConfigurationUtil configurationUtil;
  private final Plugin plugin;
  private final List<String> genericKeyLore;
  private final List<String> genericHologramLines;
  private final String genericKeyName;
  private final String genericChestName;

  public CrateManager(ConfigurationUtil configurationUtil, Plugin plugin, List<String> genericKeyLore,
      List<String> genericHologramLines, String genericKeyName, String genericChestName) {
    this.crates = new HashMap<>();
    this.configurationUtil = configurationUtil;
    this.plugin = plugin;
    this.genericKeyLore = genericKeyLore;
    this.genericHologramLines = genericHologramLines;
    this.genericKeyName = genericKeyName;
    this.genericChestName = genericChestName;
  }

  public Collection<String> getCratesNames() {
    return this.crates.keySet();
  }

  public Collection<Crate> getCrates() {
    return this.crates.values();
  }

  public Crate getCrate(Location location) {
    for (Crate crate : getCrates()) {
      if (crate.checkLocation(location))
        return crate;
    }
    return null;
  }

  public Crate getCrate(ItemStack itemStack) {
    for (Crate crate : getCrates()) {
      if (crate.isKey(itemStack))
        return crate;
    }
    return null;
  }

  public Crate getCrate(String crateName) {
    return this.crates.getOrDefault(crateName, null);
  }

  public void loadCrates() {
    File[] cratesFileList = (new File(this.plugin.getDataFolder() + "/crates")).listFiles();
    if (cratesFileList != null)
      for (File crateFile : cratesFileList)
        loadCrate(crateFile.getName().replace(".yml", ""));
  }

  public void saveCrates(boolean async) {
    for (String crateName : this.crates.keySet())
      saveCrate(crateName, async);
  }

  public void loadCrate(String name) {
    String displayName;
    YamlConfiguration yamlConfiguration = this.configurationUtil
        .getConfiguration("%datafolder%/crates/" + name + ".yml");
    ConfigurationSection contentsSection = yamlConfiguration.getConfigurationSection("contents");
    ConfigurationSection locationsSection = yamlConfiguration.getConfigurationSection("locations");
    if (yamlConfiguration.contains("displayname")) {
      displayName = yamlConfiguration.getString("displayname");
    } else {
      displayName = name;
    }
    Inventory inventory = this.plugin.getServer().createInventory(null, 27,
        this.genericChestName.replace("%name%", displayName));
    Collection<Location> locations = new HashSet<>();
    List<String> lore = new ArrayList<>(this.genericKeyLore);
    List<String> hologramLines = new ArrayList<>(this.genericHologramLines);
    lore.add("ID: " + name);
    if (contentsSection != null)
      for (String key : contentsSection.getKeys(false)) {
        inventory.addItem(new ItemStack[] { yamlConfiguration.getItemStack("contents." + key) });
      }
    if (locationsSection != null)
      for (String key : locationsSection.getKeys(false))
        locations.add((Location) yamlConfiguration.get("locations." + key));
    this.crates.put(name,
        new Crate(plugin, locations, hologramLines, name, displayName, this.genericKeyName, lore, inventory));
  }

  public void saveCrate(String name, boolean async) {
    Crate crate = getCrate(name);
    YamlConfiguration config = new YamlConfiguration();
    int index = 0;
    for (ItemStack itemStack : crate.getInventory().getContents()) {
      config.set("contents." + index, itemStack);
      index++;
    }
    index = 0;
    for (Location location : crate.getLocations()) {
      config.set("locations." + index, location);
      index++;
    }
    config.set("displayname", crate.getDisplayName());
    this.configurationUtil.saveConfiguration(config, "%datafolder%/crates/" + name + ".yml", async);
  }

  public void removeCrate(String crateName) {
    if (this.crates.containsKey(crateName)) {
      Crate crate = this.crates.get(crateName);
      crate.despawnHolograms();
      this.crates.remove(crateName);
      this.configurationUtil.deleteConfiguration("%datafolder%/crates/" + crateName + ".yml", true);
    }
  }

  public void spawnHolograms() {
    for (Crate crate : this.crates.values())
      crate.spawnHolograms();
  }

  public void despawnHolograms() {
    for (Crate crate : this.crates.values())
      crate.despawnHolograms();
  }
}
