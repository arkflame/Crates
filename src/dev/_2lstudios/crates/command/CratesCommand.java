package dev._2lstudios.crates.command;

import org.bukkit.command.CommandSender;

public interface CratesCommand {
  void execute(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString);

  String getName();

  String getDescription();

  String getArgs();
}
