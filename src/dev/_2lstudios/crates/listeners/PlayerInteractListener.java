package dev._2lstudios.crates.listeners;

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

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;

class PlayerInteractListener implements Listener {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;

  PlayerInteractListener(final CrateManager crateManager, final CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }

  private void tryGiveKey(final Player player, final Crate crate, final ItemStack itemStack) {
    final PlayerInventory playerInventory = player.getInventory();

    if (playerInventory.firstEmpty() == -1) {
      player.sendMessage(cratesConfig.getNoSpace());
    } else if (crate.openKey(player, itemStack)) {
      player.sendMessage(cratesConfig.getValidKey(crate.getDisplayName()));
    } else {
      player.sendMessage(cratesConfig.getError());
    }
  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onPlayerInteract(final PlayerInteractEvent event) {
    final Block block = event.getClickedBlock();

    if (block != null) {
      final Action action = event.getAction();
      final Location location = block.getLocation();
      final Player player = event.getPlayer();

      if (action == Action.LEFT_CLICK_BLOCK) {
        final Crate crate = this.crateManager.getCrate(location);

        if (crate != null) {
          player.openInventory(crate.getInventory());
          event.setCancelled(true);
        }
      } else if (action == Action.RIGHT_CLICK_BLOCK) {
        final ItemStack itemStack = event.getItem();
        final Crate keyCrate = this.crateManager.getCrate(itemStack);

        if (keyCrate != null) {
          final Crate crate = this.crateManager.getCrate(location);

          if (crate == keyCrate) {
            tryGiveKey(player, crate, itemStack);
          } else if (crate != null) {
            player.sendMessage(cratesConfig.getInvalidKey());
          } else {
            if (player.hasPermission("crates.crateless")) {
              tryGiveKey(player, keyCrate, itemStack);
            } else {
              player.sendMessage(cratesConfig.getNoInteract());
            }
          }
          event.setCancelled(true);
        } else if (this.crateManager.getCrate(location) != null) {
          player.sendMessage(cratesConfig.getNoKeys());
          event.setCancelled(true);
        }
      }
    }
  }
}
