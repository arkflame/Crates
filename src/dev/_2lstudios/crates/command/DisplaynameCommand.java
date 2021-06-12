package dev._2lstudios.crates.command;

import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.interfaces.CratesCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

class DisplaynameCommand implements CratesCommand {
  private final CrateManager crateManager;
  
  DisplaynameCommand(CrateManager crateManager) {
    this.crateManager = crateManager;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(ChatColor.RED + "No tienes permiso para usar ese comando!");
    } else if (args.length < 3) {
      sender.sendMessage(ChatColor.RED + "/crate displayname <name> <displayname>");
    } else {
      String crateName = args[1];
      Crate crate = this.crateManager.getCrate(crateName);
      if (crate != null) {
        StringBuilder displayNameBuilder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
          String arg = ChatColor.translateAlternateColorCodes('&', args[i]);
          displayNameBuilder.append(arg).append(" ");
        } 
        String displayName = displayNameBuilder.toString().trim();
        crate.setDisplayName(displayName);
        crate.spawnHolograms();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aModificaste el nombre de&b " + crateName + "&a a " + displayName + "&a!"));
      } else {
        sender.sendMessage(ChatColor.RED + "El crate solicitado no existe!");
      } 
    } 
  }
}
