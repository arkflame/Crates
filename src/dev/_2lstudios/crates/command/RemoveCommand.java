package dev._2lstudios.crates.command;

import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.interfaces.CratesCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class RemoveCommand implements CratesCommand {
  private final CrateManager crateManager;
  
  RemoveCommand(CrateManager crateManager) {
    this.crateManager = crateManager;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(ChatColor.RED + "No tienes permiso para usar ese comando!");
    } else if (args.length < 2) {
      sender.sendMessage(ChatColor.RED + "/crate delete <name>");
    } else {
      String crateName = args[1];
      this.crateManager.removeCrate(crateName);
      sender.sendMessage(ChatColor.GREEN + "Eliminaste el crate " + ChatColor.AQUA + crateName + ChatColor.GREEN + " correctamente!");
    } 
  }
}
