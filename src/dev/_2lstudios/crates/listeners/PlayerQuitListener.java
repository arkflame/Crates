package dev._2lstudios.crates.listeners;

import dev._2lstudios.crates.player.CratesPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

class PlayerQuitListener implements Listener {
  private final CratesPlayerManager cratesPlayerManager;
  
  PlayerQuitListener(CratesPlayerManager cratesPlayerManager) {
    this.cratesPlayerManager = cratesPlayerManager;
  }
  
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
  public void onPlayerquit(PlayerQuitEvent event) {
    this.cratesPlayerManager.savePlayer(event.getPlayer().getUniqueId(), true);
  }
}
