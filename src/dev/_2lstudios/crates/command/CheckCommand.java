package dev._2lstudios.crates.command;

import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.interfaces.CratesCommand;
import dev._2lstudios.crates.player.CratesPlayer;
import dev._2lstudios.crates.player.CratesPlayerManager;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

class CheckCommand implements CratesCommand {
  private final CratesPlayerManager cratesPlayerManager;
  
  CheckCommand(CratesPlayerManager cratesPlayerManager) {
    this.cratesPlayerManager = cratesPlayerManager;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof org.bukkit.entity.Player)) {
      sender.sendMessage(ChatColor.RED + "No puedes utilizar este comando desde la consola!");
    } else {
      CratesPlayer cratesPlayer = this.cratesPlayerManager.getPlayer(((Entity)sender).getUniqueId());
      Map<Crate, Integer> pendingKeys = cratesPlayer.getPendingKeys();
      if (pendingKeys.isEmpty()) {
        sender.sendMessage(ChatColor.RED + "No tienes keys pendientes para reclamar!");
      } else {
        int amount = 0;
        for (Iterator<Integer> iterator = pendingKeys.values().iterator(); iterator.hasNext(); ) {
          int amount1 = ((Integer)iterator.next()).intValue();
          amount += amount1;
        } 
        sender.sendMessage(ChatColor.GREEN + "Tienes " + ChatColor.AQUA + amount + ChatColor.GREEN + " keys para reclamar!");
      } 
    } 
  }

  public String getDescription() {
    return "Checks your pending keys";
  }
}
