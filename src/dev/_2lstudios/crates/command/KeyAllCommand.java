package dev._2lstudios.crates.command;

import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.interfaces.CratesCommand;
import dev._2lstudios.crates.player.CratesPlayer;
import dev._2lstudios.crates.player.CratesPlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class KeyAllCommand implements CratesCommand {
  private final CrateManager crateManager;
  
  private final CratesPlayerManager cratesPlayerManager;
  
  private final Server server;
  
  KeyAllCommand(CrateManager crateManager, CratesPlayerManager cratesPlayerManager, Server server) {
    this.crateManager = crateManager;
    this.cratesPlayerManager = cratesPlayerManager;
    this.server = server;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(ChatColor.RED + "No tienes permiso para usar ese comando!");
    } else if (args.length < 3) {
      sender.sendMessage(ChatColor.RED + "/crate keyall <key> <amount>");
    } else {
      try {
        String crateName = args[1];
        Crate crate = this.crateManager.getCrate(crateName);
        if (crate == null) {
          sender.sendMessage(ChatColor.RED + "No existe un crate con ese nombre!");
        } else {
          int amount = Integer.parseInt(args[2]);
          for (Player player : this.server.getOnlinePlayers()) {
            CratesPlayer cratesPlayer = this.cratesPlayerManager.getPlayer(player.getUniqueId());
            cratesPlayer.giveKeys(crate, amount);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aRecibiste &b" + amount + "&a llaves " + crate
                  .getDisplayName() + "&a por un &dKeyAll&a!"));
          } 
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aEntregaste &b" + amount + "&a llaves " + crate
                .getDisplayName() + "&a a todos los jugadores en linea!"));
        } 
      } catch (NumberFormatException exception) {
        sender.sendMessage(ChatColor.RED + "Ingresaste un numero invalido!");
      } 
    } 
  }

  public String getDescription() {
    return "Gives keys to all players";
  }
}
