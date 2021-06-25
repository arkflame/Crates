package dev._2lstudios.crates.interfaces;

import org.bukkit.command.CommandSender;

public interface CratesCommand {
  void execute(CommandSender paramCommandSender, String paramString, String[] paramArrayOfString);

  String getDescription();

  String getArgs();

  String getName();
}
