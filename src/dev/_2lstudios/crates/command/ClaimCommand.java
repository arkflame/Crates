package dev._2lstudios.crates.command;

import dev._2lstudios.crates.interfaces.CratesCommand;
import dev._2lstudios.crates.player.CratesPlayer;
import dev._2lstudios.crates.player.CratesPlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

class ClaimCommand implements CratesCommand {
  private final CratesPlayerManager cratesPlayerManager;
  
  ClaimCommand(CratesPlayerManager cratesPlayerManager) {
    this.cratesPlayerManager = cratesPlayerManager;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof org.bukkit.entity.Player)) {
      sender.sendMessage(ChatColor.RED + "No puedes utilizar este comando desde la consola!");
    } else {
      CratesPlayer cratesPlayer = this.cratesPlayerManager.getPlayer(((Entity)sender).getUniqueId());
      if (cratesPlayer.getPendingKeys().isEmpty()) {
        sender.sendMessage(ChatColor.RED + "No tienes keys pendientes para reclamar!");
      } else {
        int result = cratesPlayer.claimKeys();
        if (result > 0) {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aReclamaste un total de &b" + result + "&a keys!"));
        } else {
          sender.sendMessage(ChatColor.RED + "No tienes espacio suficiente en tu inventario!");
        } 
      } 
    } 
  }
}
