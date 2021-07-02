package dev._2lstudios.crates.command;

import org.bukkit.command.CommandSender;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.util.InventoryUtil;

class RowsCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;

  RowsCommand(CrateManager crateManager, CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }

  public void execute(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(cratesConfig.getNoPermission());
    } else if (args.length < 3) {
      sender.sendMessage(cratesConfig.getCommandUsage(label, getName(), getArgs()));
    } else {
      String crateName = args[1];
      final Crate crate = crateManager.getCrate(args[1]);

      if (crate != null) {
        final int rows = InventoryUtil.getValidRow(args[2]);

        crate.setRows(rows);
        sender.sendMessage(cratesConfig.getRowsSuccess(crateName, rows));
      } else {
        sender.sendMessage(cratesConfig.getNoCrate());
      }
    }
  }

  @Override
  public String getName() {
    return "rows";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getRowsDescription();
  }

  @Override
  public String getArgs() {
    return "<crate> <rows>";
  }
}
