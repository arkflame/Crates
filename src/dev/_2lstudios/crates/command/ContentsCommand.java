package dev._2lstudios.crates.command;

import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.interfaces.CratesCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

class ContentsCommand implements CratesCommand {
  private final CrateManager crateManager;
  
  ContentsCommand(CrateManager crateManager) {
    this.crateManager = crateManager;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof org.bukkit.entity.Player)) {
      sender.sendMessage(ChatColor.RED + "No puedes utilizar este comando desde la consola!");
    } else if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(ChatColor.RED + "No tienes permiso para usar ese comando!");
    } else if (args.length < 2) {
      sender.sendMessage(ChatColor.RED + "/crate contents <name>");
    } else {
      String crateName = args[1];
      Crate crate = this.crateManager.getCrate(crateName);
      if (crate != null) {
        ((HumanEntity)sender).openInventory(crate.getInventory());
        sender.sendMessage(ChatColor.GREEN + "Estas editando el crate " + ChatColor.AQUA + crateName + ChatColor.GREEN + "!");
      } else {
        sender.sendMessage(ChatColor.RED + "El crate solicitado no existe!");
      } 
    } 
  }

  public String getDescription() {
    return "Edits contents of crates";
  }
}
