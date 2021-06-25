package dev._2lstudios.crates.command;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.interfaces.CratesCommand;

class RemoveLocationCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;
  
  RemoveLocationCommand(CrateManager crateManager, CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }
  
  public void execute(CommandSender sender, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(cratesConfig.getNoConsole());
    } else if (!sender.hasPermission("crates.admin")) {
      sender.sendMessage(cratesConfig.getNoPermission());
    } else if (args.length < 2) {
      sender.sendMessage(cratesConfig.getCommandUsage());
    } else {
      Block block = ((Player) sender).getTargetBlock(null, 10);

      if (block == null) {
        sender.sendMessage(cratesConfig.getNoBlock());
      } else {
        String crateName = args[1];
        Crate crate = this.crateManager.getCrate(crateName);
        
        crate.removeLocation(block.getLocation().add(new Vector(0.5D, -0.5D, 0.5D)));
        sender.sendMessage(cratesConfig.getRemoveLocationSuccess());
      } 
    } 
  }

  @Override
  public String getDescription() {
    return cratesConfig.getRemoveLocationDescription();
  }

  @Override
  public String getArgs() {
    return "";
  }
}
