package dev._2lstudios.crates.command;

import org.bukkit.command.CommandSender;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.CrateManager;

class RemoveCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;

  RemoveCommand(CrateManager crateManager, CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }

  public void execute(CommandSender sender, String label, String[] args) {
    final String crateName = args[1];

    if (this.crateManager.removeCrate(crateName)) {
      sender.sendMessage(cratesConfig.getRemoveSuccess(crateName));
    } else {
      sender.sendMessage(cratesConfig.getNoCrate());
    }
  }

  @Override
  public String getName() {
    return "remove";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getRemoveDescription();
  }

  @Override
  public String getArgs() {
    return "<crate>";
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
    return 2;
  }
}
