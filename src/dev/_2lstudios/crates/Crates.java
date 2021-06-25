package dev._2lstudios.crates;

import java.util.List;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import dev._2lstudios.crates.command.CratesCommandExecutor;
import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.listeners.ListenerInitializer;
import dev._2lstudios.crates.player.CratesPlayerManager;
import dev._2lstudios.crates.util.ConfigurationUtil;

public class Crates extends JavaPlugin {
  private final Plugin plugin = this;
  private CrateManager crateManager;
  private CratesPlayerManager cratesPlayerManager;
  private ListenerInitializer listenerInitializer;
  
  private void enable() {
    final Server server = getServer();
    final ConfigurationUtil configurationUtil = new ConfigurationUtil(plugin);

    configurationUtil.createConfiguration("%datafolder%/config.yml");

    final CratesConfig cratesConfig = new CratesConfig(configurationUtil.getConfiguration("%datafolder%/config.yml"));
    final List<String> keyLore = cratesConfig.getKeyLore();
    final List<String> hologramLines = cratesConfig.getHologramLines();
    final String keyName = cratesConfig.getKeyName();
    final String chestName = cratesConfig.getInventoryTitle();

    crateManager = new CrateManager(configurationUtil, plugin, keyLore, hologramLines, keyName, chestName);
    cratesPlayerManager = new CratesPlayerManager(crateManager, configurationUtil, server);

    getCommand("crates").setExecutor(new CratesCommandExecutor(crateManager, cratesPlayerManager, cratesConfig, server));

    crateManager.loadCrates();
    crateManager.spawnHolograms();
    listenerInitializer = new ListenerInitializer(plugin, crateManager, cratesPlayerManager);
    listenerInitializer.initialize();
  }

  public void onEnable() {
    final BukkitScheduler scheduler = getServer().getScheduler();

    scheduler.runTaskAsynchronously(this, this::enable);
  }
  
  public void onDisable() {
    for (final Player player : getServer().getOnlinePlayers())
      cratesPlayerManager.savePlayer(player.getUniqueId(), false); 
    if (crateManager != null) {
      crateManager.saveCrates(false);
      crateManager.despawnHolograms();
    } 
    if (listenerInitializer != null)
      listenerInitializer.deinitialize(); 
  }
}
