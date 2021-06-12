package dev._2lstudios.crates.command;

import dev._2lstudios.crates.crate.CrateManager;
import dev._2lstudios.crates.interfaces.CratesCommand;
import dev._2lstudios.crates.player.CratesPlayerManager;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CratesCommandExecutor implements CommandExecutor {
  private final Map<String, CratesCommand> cratesCommands;
  
  public CratesCommandExecutor(CrateManager crateManager, CratesPlayerManager cratesPlayerManager, Server server) {
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
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length > 0) {
      String subCommand = args[0].toLowerCase();
      if (this.cratesCommands.containsKey(subCommand))
        ((CratesCommand)this.cratesCommands.get(subCommand)).execute(sender, label, args); 
    } else {
      StringBuilder message = new StringBuilder("&aCrates &b0.0.1&a by &b2LS&r\n");
      for (String cratesCommand : this.cratesCommands.keySet())
        message.append("&e /crates " + cratesCommand + "&7 > &bCrates Command!\n"); 
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.toString()));
    } 
    return true;
  }
}
