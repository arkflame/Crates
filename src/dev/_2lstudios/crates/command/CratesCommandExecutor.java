package dev._2lstudios.crates.command;

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
  private final Map<String, CratesCommand> cratesCommands;
  
  public CratesCommandExecutor(final CrateManager crateManager, final CratesPlayerManager cratesPlayerManager, final Server server) {
    this.cratesCommands = new HashMap<>();
    this.cratesCommands.put("addlocation", new AddLocationCommand(crateManager));
    this.cratesCommands.put("check", new CheckCommand(cratesPlayerManager));
    this.cratesCommands.put("claim", new ClaimCommand(cratesPlayerManager));
    this.cratesCommands.put("contents", new ContentsCommand(crateManager));
    this.cratesCommands.put("create", new CreateCommand(crateManager));
    this.cratesCommands.put("displayname", new DisplaynameCommand(crateManager));
    this.cratesCommands.put("remove", new RemoveCommand(crateManager));
    this.cratesCommands.put("keyall", new KeyAllCommand(crateManager, cratesPlayerManager, server));
    this.cratesCommands.put("key", new KeyCommand(cratesPlayerManager, crateManager, server));
    this.cratesCommands.put("list", new ListCommand(crateManager));
    this.cratesCommands.put("removelocation", new RemoveLocationCommand(crateManager));
  }
  
  public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
    if (args.length > 0) {
      final String subCommand = args[0].toLowerCase();

      if (this.cratesCommands.containsKey(subCommand)) {
        this.cratesCommands.get(subCommand).execute(sender, label, args); 
      }
    } else {
      final StringBuilder message = new StringBuilder("&aCrates &b0.0.1&a by &b2LS&r\n");

      for (final Entry<String, CratesCommand> entry : this.cratesCommands.entrySet()) {
        final String key = entry.getKey();
        final CratesCommand cratesCommand = entry.getValue();

        message.append("&e /crates " + key + "&7 > &b" + cratesCommand.getDescription() + "!\n"); 
      }

      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.toString()));
    } 
    return true;
  }
}
