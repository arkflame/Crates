package dev._2lstudios.crates.command;

import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.interfaces.CratesCommand;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

class AddLocationCommand implements CratesCommand {
  private final CrateManager crateManager;
  
  AddLocationCommand(CrateManager crateManager) {
    this.crateManager = crateManager;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(ChatColor.RED + "No puedes utilizar este comando desde la consola!");
    } else if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(ChatColor.RED + "No tienes permiso para usar ese comando!");
    } else if (args.length < 2) {
      sender.sendMessage(ChatColor.RED + "/crate addlocation <name>");
    } else {
      Block block = ((Player)sender).getTargetBlock((Set)null, 10);
      if (block == null) {
        sender.sendMessage(ChatColor.RED + "No estas apuntando a ningun cofre!");
      } else {
        String crateName = args[1];
        Crate crate = this.crateManager.getCrate(crateName);
        crate.addLocation(block.getLocation().add(new Vector(0.5D, 0.0D, 0.5D)));
        sender.sendMessage(ChatColor.GREEN + "Creaste un cofre para el crate " + ChatColor.AQUA + crateName + ChatColor.GREEN + " correctamente!");
      } 
    } 
  }
}
