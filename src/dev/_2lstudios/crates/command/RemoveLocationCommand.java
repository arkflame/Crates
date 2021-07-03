package dev._2lstudios.crates.command;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.Crate;
import dev._2lstudios.crates.crate.CrateManager;

class RemoveLocationCommand implements CratesCommand {
  private final CrateManager crateManager;
  private final CratesConfig cratesConfig;

  RemoveLocationCommand(CrateManager crateManager, CratesConfig cratesConfig) {
    this.crateManager = crateManager;
    this.cratesConfig = cratesConfig;
  }

  public void execute(CommandSender sender, String label, String[] args) {
    Block block = ((Player) sender).getTargetBlock(null, 10);

    if (block == null) {
      sender.sendMessage(cratesConfig.getNoBlock());
    } else {
      String crateName = args[1];
      Crate crate = this.crateManager.getCrate(crateName);

      if (crate != null) {
        Location blockLocation = block.getLocation().add(new Vector(0.5f, 0.0f, 0.5f));

        if (crate.checkLocation(blockLocation)) {
          crate.removeLocation(blockLocation);
          sender.sendMessage(cratesConfig.getRemoveLocationSuccess(crateName));
        } else {
          sender.sendMessage(cratesConfig.getRemoveLocationNoCrateAt(crate.getName()));
        }
      } else {
        sender.sendMessage(cratesConfig.getNoCrate());
      }
    }
  }

  @Override
  public String getName() {
    return "removelocation";
  }

  @Override
  public String getDescription() {
    return cratesConfig.getRemoveLocationDescription();
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
