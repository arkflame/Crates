package dev._2lstudios.crates.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.interfaces.CratesCommand;
import dev._2lstudios.crates.player.CratesPlayer;
import dev._2lstudios.crates.player.CratesPlayerManager;

class ClaimCommand implements CratesCommand {
  private final CratesPlayerManager cratesPlayerManager;
  private final CratesConfig cratesConfig;

  ClaimCommand(final CratesPlayerManager cratesPlayerManager, final CratesConfig cratesConfig) {
    this.cratesPlayerManager = cratesPlayerManager;
    this.cratesConfig = cratesConfig;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof org.bukkit.entity.Player)) {
      sender.sendMessage(cratesConfig.getNoConsole());
    } else {
      CratesPlayer cratesPlayer = this.cratesPlayerManager.getPlayer(((Entity)sender).getUniqueId());
      if (cratesPlayer.getPendingKeys().isEmpty()) {
        sender.sendMessage(cratesConfig.getNoKeys());
      } else {
        int result = cratesPlayer.claimKeys();
        if (result > 0) {
          sender.sendMessage(cratesConfig.getClaimSuccess());
        } else {
          sender.sendMessage(cratesConfig.getNoSpace());
        } 
      } 
    } 
  }

  @Override
  public String getDescription() {
    return cratesConfig.getClaimDescription();
  }

  @Override
  public String getArgs() {
    return "";
  }

  @Override
  public String getName() {
    return "claim";
  }
}
