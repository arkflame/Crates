package dev._2lstudios.crates.command;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.interfaces.CratesCommand;
import dev._2lstudios.crates.player.CratesPlayer;
import dev._2lstudios.crates.player.CratesPlayerManager;

class CheckCommand implements CratesCommand {
  private final CratesPlayerManager cratesPlayerManager;
  private final CratesConfig cratesConfig;

  CheckCommand(final CratesPlayerManager cratesPlayerManager, final CratesConfig cratesConfig) {
    this.cratesPlayerManager = cratesPlayerManager;
    this.cratesConfig = cratesConfig;
  }
  
  public void execute(final CommandSender sender, final String label, final String[] args) {
    if (!(sender instanceof org.bukkit.entity.Player)) {
      sender.sendMessage(cratesConfig.getNoConsole());
    } else {
      final CratesPlayer cratesPlayer = this.cratesPlayerManager.getPlayer(((Entity)sender).getUniqueId());
      final Map<Crate, Integer> pendingKeys = cratesPlayer.getPendingKeys();
      if (pendingKeys.isEmpty()) {
        sender.sendMessage(cratesConfig.getNoKeys());
      } else {
        int amount = 0;

        for (final Iterator<Integer> iterator = pendingKeys.values().iterator(); iterator.hasNext(); ) {
          final int amount1 = ((Integer)iterator.next()).intValue();
          amount += amount1;
        } 
        
        sender.sendMessage(cratesConfig.getCheckSuccess(amount));
      } 
    } 
  }

  @Override
  public String getDescription() {
    return cratesConfig.getCheckDescription();
  }

  @Override
  public String getArgs() {
    return "";
  }
}
