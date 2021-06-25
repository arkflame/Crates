package dev._2lstudios.crates.listeners;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

class PlayerInteractListener implements Listener {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;
  
  PlayerInteractListener(CrateManager crateManager, CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }
  
  @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
  public void onPlayerInteract(PlayerInteractEvent event) {
    Block block = event.getClickedBlock();
    if (block != null) {
      Action action = event.getAction();
      Location location = block.getLocation();
      Player player = event.getPlayer();
      if (action == Action.LEFT_CLICK_BLOCK) {
        Crate crate = this.crateManager.getCrate(location);
        if (crate != null) {
          player.openInventory(crate.getInventory());
          event.setCancelled(true);
        } 
      } else if (action == Action.RIGHT_CLICK_BLOCK) {
        ItemStack itemStack = event.getItem();
        Crate keyCrate = this.crateManager.getCrate(itemStack);
        if (keyCrate != null) {
          Crate crate = this.crateManager.getCrate(location);
          if (crate == keyCrate) {
            PlayerInventory playerInventory = player.getInventory();
            if (playerInventory.firstEmpty() == -1) {
              player.sendMessage(cratesConfig.getNoSpace());
            } else if (crate.openKey(player, itemStack)) {
              player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                    "&aAbriste tu key de tipo &b" + crate.getDisplayName() + "&a!"));
            } else {
              player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo se pudo abrir la key " + 
                    keyCrate.getDisplayName() + "&c por un error inesperado!"));
            } 
          } else if (crate != null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                  "&cEstas intentando abrir un crate " + crate.getDisplayName() + "&c con una llave " + 
                  keyCrate.getDisplayName() + "&c!"));
          } else {
            player.sendMessage(ChatColor.RED + "No puedes interactuar con keys en la mano!");
          } 
          event.setCancelled(true);
        } else if (this.crateManager.getCrate(location) != null) {
          player.sendMessage(ChatColor.RED + "No tienes ninguna key en la mano para usar!");
          event.setCancelled(true);
        } 
      } 
    } 
  }
}
