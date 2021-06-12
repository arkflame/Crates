package dev._2lstudios.crates.command;

import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.interfaces.CratesCommand;
import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class ListCommand implements CratesCommand {
  private final CrateManager crateManager;
  
  ListCommand(CrateManager crateManager) {
    this.crateManager = crateManager;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(ChatColor.RED + "No tienes permiso para usar ese comando!");
    } else {
      String[] crates = (String[])this.crateManager.getCratesNames().toArray((Object[])new String[0]);
      sender.sendMessage(ChatColor.GREEN + "Crates: " + ChatColor.AQUA + Arrays.toString((Object[])crates));
    } 
  }
}
