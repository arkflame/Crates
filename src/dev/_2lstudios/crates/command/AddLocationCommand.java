package dev._2lstudios.crates.command;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;

class AddLocationCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;

  AddLocationCommand(final CrateManager crateManager, final CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }

  public void execute(final CommandSender sender, final String label, final String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(cratesConfig.getNoConsole());
    } else if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(cratesConfig.getNoPermission());
    } else if (args.length < 2) {
      sender.sendMessage(cratesConfig.getCommandUsage(label, getName(), getArgs()));
    } else {
      final Block block = ((Player) sender).getTargetBlock(null, 10);
      if (block == null) {
        sender.sendMessage(cratesConfig.getNoBlock());
      } else {
        final String crateName = args[1];
        final Crate crate = this.crateManager.getCrate(crateName);

        crate.addLocation(block.getLocation().add(new Vector(0.5D, 0.0D, 0.5D)));
        sender.sendMessage(cratesConfig.getCreateSuccess(crateName));
      }
    }
  }
  
  @Override
  public String getName() {
    return "addlocation";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getAddLocationDescription();
  }

  @Override
  public String getArgs() {
    return "<crate>";
  }
}
