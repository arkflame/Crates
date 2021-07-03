package dev._2lstudios.crates.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;

class ContentsCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;

  ContentsCommand(CrateManager crateManager, CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }

  public void execute(CommandSender sender, String label, String[] args) {
    String crateName = args[1];
    Crate crate = this.crateManager.getCrate(crateName);
    if (crate != null) {
      ((HumanEntity) sender).openInventory(crate.getInventory());
      sender.sendMessage(cratesConfig.getContentsSuccess(crateName));
    } else {
      sender.sendMessage(cratesConfig.getNoCrate());
    }
  }

  @Override
  public String getName() {
    return "contents";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getContentsDescription();
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
    return true;
  }

  @Override
  public int getArgCount() {
    return 2;
  }
}
