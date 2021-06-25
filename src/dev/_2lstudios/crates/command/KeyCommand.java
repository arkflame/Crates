package dev._2lstudios.crates.command;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.player.CratesPlayer;
import dev._2lstudios.crates.player.CratesPlayerManager;

class KeyCommand implements CratesCommand {
  private final CratesPlayerManager cratesPlayerManager;
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;
  private final Server server;
  
  KeyCommand(CratesPlayerManager cratesPlayerManager, CrateManager crateManager, CratesConfig cratesConfig, Server server) {
    this.cratesPlayerManager = cratesPlayerManager;
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
    this.server = server;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(cratesConfig.getNoPermission());
    } else if (args.length < 4) {
      sender.sendMessage(cratesConfig.getCommandUsage(label, getName(), getArgs()));
    } else {
      try {
        String playerName = args[1];
        String keyName = args[2];
        int amount = Integer.parseInt(args[3]);
        Crate crate = this.crateManager.getCrate(keyName);
        if (crate == null) {
          sender.sendMessage(cratesConfig.getNoCrate());
        } else {
          Player player = this.server.getPlayer(playerName);
          if (player != null && player.isOnline()) {
            CratesPlayer cratesPlayer = this.cratesPlayerManager.getPlayer(player.getUniqueId());
            
            cratesPlayer.giveKeys(crate, amount);
            player.sendMessage(cratesConfig.getReceivedKeys(sender.getName(), amount, crate.getDisplayName()));
            sender.sendMessage(cratesConfig.getKeySuccess(amount, crate.getDisplayName(), playerName));
          } 
        } 
      } catch (NumberFormatException exception) {
        sender.sendMessage(cratesConfig.getInvalidNumber());
      } 
    } 
  }

  @Override
  public String getName() {
    return "key";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getKeyDescription();
  }

  @Override
  public String getArgs() {
    return "<player> <crate> <amount>";
  }
}
