package dev._2lstudios.crates.listeners;

import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.player.CratesPlayerManager;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class ListenerInitializer {
  private final Plugin plugin;
  
  private final CrateManager crateManager;
  
  private final CratesPlayerManager cratesPlayerManager;
  
  private boolean initialized;
  
  public ListenerInitializer(Plugin plugin, CrateManager crateManager, CratesPlayerManager cratesPlayerManager) {
    this.plugin = plugin;
    this.crateManager = crateManager;
    this.cratesPlayerManager = cratesPlayerManager;
    this.initialized = false;
  }
  
  public void initialize() {
    if (!this.initialized) {
      this.initialized = true;
      PluginManager pluginManager = this.plugin.getServer().getPluginManager();
      pluginManager.registerEvents(new InventoryClickListener(this.crateManager), this.plugin);
      pluginManager.registerEvents(new PlayerInteractListener(this.crateManager), this.plugin);
      pluginManager.registerEvents(new PlayerQuitListener(this.cratesPlayerManager), this.plugin);
    } 
  }
  
  public void deinitialize() {
    if (this.initialized) {
      this.initialized = false;
      HandlerList.unregisterAll(this.plugin);
    } 
  }
}
