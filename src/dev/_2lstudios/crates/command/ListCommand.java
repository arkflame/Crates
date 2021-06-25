package dev._2lstudios.crates.command;

import org.bukkit.command.CommandSender;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.interfaces.CratesCommand;

class ListCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;
  
  ListCommand(CrateManager crateManager, CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(cratesConfig.getNoPermission());
    } else {
      String crates = this.crateManager.getCratesNames().toArray(new String[0]).toString();

      sender.sendMessage(cratesConfig.getListSuccess());
    } 
  }

  @Override
  public String getDescription() {
    return cratesConfig.getListDescription();
  }

  @Override
  public String getArgs() {
    return "";
  }

  @Override
  public String getName() {
    return "list";
  }
}
