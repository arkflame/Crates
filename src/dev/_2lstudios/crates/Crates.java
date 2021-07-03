package dev._2lstudios.crates;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

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

  public void onEnable() {
    final Server server = getServer();
    final ConfigurationUtil configurationUtil = new ConfigurationUtil(plugin);

    configurationUtil.createConfiguration("%datafolder%/config.yml");

    final CratesConfig cratesConfig = new CratesConfig(configurationUtil.getConfiguration("%datafolder%/config.yml"));

    crateManager = new CrateManager(configurationUtil, cratesConfig, plugin);
    cratesPlayerManager = new CratesPlayerManager(crateManager, configurationUtil, server);

    getCommand("crates")
        .setExecutor(new CratesCommandExecutor(crateManager, cratesPlayerManager, cratesConfig, this));

    crateManager.loadCrates();
    crateManager.spawnHolograms();
    listenerInitializer = new ListenerInitializer(plugin, crateManager, cratesPlayerManager, cratesConfig);
    listenerInitializer.initialize();
  }

  public void onDisable() {
    for (final Player player : getServer().getOnlinePlayers()) {
      cratesPlayerManager.savePlayer(player.getUniqueId(), false);
    }

    if (crateManager != null) {
      crateManager.saveCrates(false);
      crateManager.despawnHolograms();
    }

    if (listenerInitializer != null) {
      listenerInitializer.deinitialize();
    }
  }
}
