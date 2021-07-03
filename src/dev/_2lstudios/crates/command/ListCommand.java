package dev._2lstudios.crates.command;

import org.bukkit.command.CommandSender;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.CrateManager;

class ListCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;

  ListCommand(CrateManager crateManager, CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }

  public void execute(CommandSender sender, String label, String[] args) {
    String crates = this.crateManager.getCratesNames();

    sender.sendMessage(cratesConfig.getListSuccess(crates));
  }

  @Override
  public String getName() {
    return "list";
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
  public boolean requireAdmin() {
    return true;
  }

  @Override
  public boolean requirePlayer() {
    return false;
  }

  @Override
  public int getArgCount() {
    return 0;
  }
}
