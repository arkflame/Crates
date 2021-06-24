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

class KeyCommand implements CratesCommand {
  private final CratesPlayerManager cratesPlayerManager;
  
  private final CrateManager crateManager;
  
  private final Server server;
  
  KeyCommand(CratesPlayerManager cratesPlayerManager, CrateManager crateManager, Server server) {
    this.cratesPlayerManager = cratesPlayerManager;
    this.crateManager = crateManager;
    this.server = server;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(ChatColor.RED + "No tienes permiso para usar ese comando!");
    } else if (args.length < 4) {
      sender.sendMessage(ChatColor.RED + "/crate key <player> <key> <amount>");
    } else {
      try {
        String playerName = args[1];
        String keyName = args[2];
        int amount = Integer.parseInt(args[3]);
        Crate crate = this.crateManager.getCrate(keyName);
        if (crate == null) {
          sender.sendMessage(ChatColor.RED + "No existe un crate con ese nombre!");
        } else {
          Player player = this.server.getPlayer(playerName);
          if (player != null && player.isOnline()) {
            CratesPlayer cratesPlayer = this.cratesPlayerManager.getPlayer(player.getUniqueId());
            cratesPlayer.giveKeys(crate, amount);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aEntregaste &b" + amount + "&a llaves " + keyName + "&a a &b" + playerName + "&a!"));
          } 
        } 
      } catch (NumberFormatException exception) {
        sender.sendMessage(ChatColor.RED + "Ingresaste un numero invalido!");
      } 
    } 
  }

  public String getDescription() {
    return "Gives keys to a player";
  }
}
