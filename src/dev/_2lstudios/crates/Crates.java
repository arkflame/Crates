package dev._2lstudios.crates;

import dev._2lstudios.crates.command.CratesCommandExecutor;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.listeners.ListenerInitializer;
import dev._2lstudios.crates.player.CratesPlayerManager;
import dev._2lstudios.crates.util.ConfigurationUtil;
import dev._2lstudios.crates.util.StringUtil;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Crates extends JavaPlugin {
  private final Plugin plugin = (Plugin)this;
  
  private CrateManager crateManager;
  
  private CratesPlayerManager cratesPlayerManager;
  
  private ListenerInitializer listenerInitializer;
  
  public void onEnable() {
    Server server = getServer();
    ConfigurationUtil configurationUtil = new ConfigurationUtil(this.plugin);
    configurationUtil.createConfiguration("%datafolder%/config.yml");
    YamlConfiguration yamlConfiguration = configurationUtil.getConfiguration("%datafolder%/config.yml");
    List<String> keyLore = StringUtil.translateAlternateColorCodes('&', yamlConfiguration.getStringList("key.lore"));
    List<String> hologramLines = StringUtil.translateAlternateColorCodes('&', yamlConfiguration.getStringList("hologram.lines"));
    String keyName = ChatColor.translateAlternateColorCodes('&', yamlConfiguration.getString("key.name"));
    String chestName = ChatColor.translateAlternateColorCodes('&', yamlConfiguration.getString("inventory.title"));
    this.crateManager = new CrateManager(configurationUtil, this.plugin, keyLore, hologramLines, keyName, chestName);
    this.cratesPlayerManager = new CratesPlayerManager(this.crateManager, configurationUtil, server);
    getCommand("crates").setExecutor((CommandExecutor)new CratesCommandExecutor(this.crateManager, this.cratesPlayerManager, server));
    this.crateManager.loadCrates();
    this.crateManager.spawnHolograms();
    this.listenerInitializer = new ListenerInitializer(this.plugin, this.crateManager, this.cratesPlayerManager);
    this.listenerInitializer.initialize();
  }
  
  public void onDisable() {
    for (Player player : getServer().getOnlinePlayers())
      this.cratesPlayerManager.savePlayer(player.getUniqueId(), false); 
    if (this.crateManager != null) {
      this.crateManager.saveCrates(false);
      this.crateManager.despawnHolograms();
    } 
    if (this.listenerInitializer != null)
      this.listenerInitializer.deinitialize(); 
  }
}
