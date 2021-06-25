package dev._2lstudios.crates.command;

import dev._2lstudios.crates.config.CratesConfig;
import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.interfaces.CratesCommand;
import dev._2lstudios.crates.player.CratesPlayerManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CratesCommandExecutor implements CommandExecutor {
  private final CratesConfig cratesConfig;
  private final Map<String, CratesCommand> cratesCommands;
  
  public CratesCommandExecutor(final CrateManager crateManager, final CratesPlayerManager cratesPlayerManager, final CratesConfig cratesConfig, final Server server) {
    this.cratesConfig = cratesConfig;
    this.cratesCommands = new HashMap<>();
    this.cratesCommands.put("addlocation", new AddLocationCommand(crateManager, cratesConfig));
    this.cratesCommands.put("check", new CheckCommand(cratesPlayerManager, cratesConfig));
    this.cratesCommands.put("claim", new ClaimCommand(cratesPlayerManager, cratesConfig));
    this.cratesCommands.put("contents", new ContentsCommand(crateManager, cratesConfig));
    this.cratesCommands.put("create", new CreateCommand(crateManager, cratesConfig));
    this.cratesCommands.put("displayname", new DisplaynameCommand(crateManager, cratesConfig));
    this.cratesCommands.put("remove", new RemoveCommand(crateManager, cratesConfig));
    this.cratesCommands.put("keyall", new KeyAllCommand(crateManager, cratesPlayerManager, cratesConfig, server));
    this.cratesCommands.put("key", new KeyCommand(cratesPlayerManager, crateManager, cratesConfig, server));
    this.cratesCommands.put("list", new ListCommand(crateManager, cratesConfig));
    this.cratesCommands.put("removelocation", new RemoveLocationCommand(crateManager, cratesConfig));
  }
  
  public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
    if (args.length > 0) {
      final String subCommand = args[0].toLowerCase();

      if (this.cratesCommands.containsKey(subCommand)) {
        this.cratesCommands.get(subCommand).execute(sender, label, args); 
      }
    } else {
      final StringBuilder message = new StringBuilder(cratesConfig.getHelpTitle());

      for (final Entry<String, CratesCommand> entry : this.cratesCommands.entrySet()) {
        final String key = entry.getKey();
        final CratesCommand cratesCommand = entry.getValue();

        message.append(cratesConfig.getHelpCommand(label, key, cratesCommand.getArgs(), cratesCommand.getDescription())); 
      }

      message.append(cratesConfig.getHelpSubtitle());
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.toString()));
    } 
    return true;
  }
}
