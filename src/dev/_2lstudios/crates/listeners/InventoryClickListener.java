package dev._2lstudios.crates.listeners;

import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

class InventoryClickListener implements Listener {
  private final CrateManager crateManager;
  
  InventoryClickListener(CrateManager crateManager) {
    this.crateManager = crateManager;
  }
  
  @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
  public void onInventoryClick(InventoryClickEvent event) {
    HumanEntity humanEntity = event.getWhoClicked();
    Inventory inventory = event.getView().getTopInventory();
    if (inventory != null && !humanEntity.hasPermission("crates.admin"))
      for (Crate crate : this.crateManager.getCrates()) {
        if (inventory.equals(crate.getInventory())) {
          event.setCancelled(true);
          break;
        } 
      }  
  }
}
